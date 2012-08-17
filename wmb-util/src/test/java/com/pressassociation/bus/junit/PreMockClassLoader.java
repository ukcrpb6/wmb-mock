package com.pressassociation.bus.junit;

import com.google.common.collect.Maps;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class PreMockClassLoader extends ClassLoader { // TODO: Use javaassist.Loader ?

    private static Map<String, CtTransformer> classTransformers = Maps.newHashMap();

    private ClassPool pool;

    public synchronized static void addClasses(Map<String, CtTransformer> transforms) {
        for(Map.Entry<String, CtTransformer> entry : transforms.entrySet()) {
            addClass(entry.getKey(), entry.getValue());
        }
    }

    public synchronized static void addClasses(Set<String> set, CtTransformer transformer) {
        for (String className : set) {
            addClass(className, transformer);
        }
    }

    public synchronized static void addClass(String className, CtTransformer transformer) {
        CtTransformer ct = classTransformers.get(className);
        if (ct == null) {
            classTransformers.put(className, transformer);
        } else {
            if (ct instanceof CtTransformerChain) {
                ((CtTransformerChain) ct).add(transformer);
            } else {
                classTransformers.put(className, new CtTransformerChain(ct, transformer));
            }
        }

    }

    public PreMockClassLoader(ClassLoader parent) {
        super(parent);
        pool = ClassPool.getDefault();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        // no mater what, do not allow certain classes to be loaded by this
        // class loader. change this as you see fit (and are able to).
        if (name.startsWith("java.")) {
            return super.loadClass(name);
        } else if (name.startsWith("javax.")) {
            return super.loadClass(name);
        } else if (name.startsWith("sun.")) {
            return super.loadClass(name);
        } else if (name.startsWith("org.junit.")) {
            return super.loadClass(name);
        } else if (name.startsWith("org.mockito.")) {
            return super.loadClass(name);
        } else if (name.startsWith("com.pressassociation.bus.junit.")) {
            return super.loadClass(name);
        } else {
            String classKey = name;
            if(classKey.indexOf('$') > 0) {
                classKey = classKey.substring(0, classKey.indexOf('$'));
            }
            if (classTransformers.containsKey(classKey)) {
                // only load the classes specified with the class loader,
                // otherwise leave it up to the parent.
                if(!classTransformers.containsKey(classKey)) {
                    classTransformers.put(classKey, CtTransformers.noopTransformer());
                }
                return findClass(name);
            } else {
                return super.loadClass(name);
            }
        }
    }

    public Class<?> findClass(String name) throws ClassNotFoundException {

        try {
            CtClass cc = pool.get(name);

            CtTransformer transformer = classTransformers.get(name);
            ((transformer == null) ? CtTransformers.noopTransformer() : transformer).transform(cc);

            // TODO THIS IS JUST A TEST
//            if(MbMessage.class.getName().equals(name)) {
//                cc = pool.get(MockMbMessage.class.getName());
//                cc.setName(name);
//            }
            byte[] b = cc.toBytecode();

            return defineClass(name, b, 0, b.length);
        } catch (NotFoundException e) {
            throw new ClassNotFoundException();
        } catch (IOException e) {
            throw new ClassNotFoundException();
        } catch (CannotCompileException e) {
            throw new ClassNotFoundException();
        }
    }

}
