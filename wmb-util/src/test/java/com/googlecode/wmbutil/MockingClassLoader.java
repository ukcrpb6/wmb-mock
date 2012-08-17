/*
 * Copyright 2003,2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wmbutil;

import org.mockito.asm.Attribute;
import org.mockito.asm.ClassReader;
import org.mockito.asm.ClassWriter;
import org.mockito.cglib.core.ClassGenerator;
import org.mockito.cglib.core.CodeGenerationException;
import org.mockito.cglib.core.DebuggingClassWriter;
import org.mockito.cglib.transform.*;

import java.io.IOException;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.ProtectionDomain;

public class MockingClassLoader extends URLClassLoader {
    private ClassFilter filter;
    private ClassLoader classPath;
    private final ClassTransformerFactory classTransformerFactory;
    private static java.security.ProtectionDomain DOMAIN;

    static {
        DOMAIN = (ProtectionDomain) AccessController.doPrivileged(
                new java.security.PrivilegedAction() {
                    public Object run() {
                        return MockingClassLoader.class.getProtectionDomain();
                    }
                });
    }

    MockingClassLoader(ClassLoader parent, ClassFilter filter, ClassTransformerFactory classTransformerFactory) {
        super(((URLClassLoader) parent).getURLs());
        this.filter = filter;
        this.classPath = parent;
        this.classTransformerFactory = classTransformerFactory;
    }

    public Class loadClass(String name) throws ClassNotFoundException {

        Class loaded = findLoadedClass(name);

        if (loaded != null) {
            if (loaded.getClassLoader() == this) {
                return loaded;
            }//else reload with this class loader
        }

        if (!filter.accept(name)) {
            String[] excluded = {
                    "sun.",
                    "com.sun.",
                    "org.omg.",
                    "javax.",
                    "sunw.",
                    "java.",
                    "org.w3c.dom.",
                    "org.xml.sax.",
                    "net.jini."};

            for (String s : excluded) {
                if (name.startsWith(s)) {
                    return getSystemClassLoader().loadClass(name);
                }
            }
            Class<?> c = super.findClass(name);
//            resolveClass(c);
            System.out.println("Delegating for " + name + " with classloader " + c.getClassLoader());
            return c;
        }
        ClassReader r;
        try {

            java.io.InputStream is = classPath.getResourceAsStream(
                    name.replace('.', '/') + ".class"
            );

            if (is == null) {

                throw new ClassNotFoundException(name);

            }
            try {

                r = new ClassReader(is);

            } finally {

                is.close();

            }
        } catch (IOException e) {
            throw new ClassNotFoundException(name + ":" + e.getMessage());
        }

        try {
            ClassWriter w = new DebuggingClassWriter(ClassWriter.COMPUTE_MAXS);
            getGenerator(r).generateClass(w);
            byte[] b = w.toByteArray();
            Class c = super.defineClass(name, b, 0, b.length, DOMAIN);
            postProcess(c);
            return c;
        } catch (RuntimeException e) {
            throw e;
        } catch (Error e) {
            throw e;
        } catch (Exception e) {
            throw new CodeGenerationException(e);
        }
    }

    protected ClassGenerator getGenerator(ClassReader r) {
        ClassTransformer t2 = classTransformerFactory.newInstance();
        return new TransformingClassGenerator(new ClassReaderGenerator(r, attributes(), getFlags()), t2);
    }

    protected int getFlags() {
        return 0;
    }

    protected Attribute[] attributes() {
        return null;
    }

    protected void postProcess(Class c) {
    }
}
