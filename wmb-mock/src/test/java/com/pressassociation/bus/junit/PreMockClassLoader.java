package com.pressassociation.bus.junit;

import com.google.common.collect.Maps;
import javassist.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class PreMockClassLoader extends ClassLoader {

    private static Map<String, CtTransformer> classTransformers = Maps.newHashMap();

    private ClassPool pool = new ClassPool();

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
        pool.appendClassPath(new ClassClassPath(this.getClass())); // pool = ClassPool.getDefault();
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
            if (classTransformers.containsKey(name)) {
                return loadMockedClass(name);
            } else {
                return loadUnmockedClass(name);
            }
        }
    }

    public Class<?> loadMockedClass(String name) {
        byte[] clazz;
        ClassPool.doPruning = false;
        try {
            CtClass type = pool.get(name);

            CtTransformer transformer = classTransformers.get(name);
            ((transformer == null) ? CtTransformers.noopTransformer() : transformer).transform(type);

            clazz = type.toBytecode();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to transform class with name " + name + ". Reason: " + e.getMessage(), e);
        }
        return defineClass(name, clazz, 0, clazz.length);
    }

    private Class<?> loadUnmockedClass(String name) throws ClassFormatError, ClassNotFoundException {
        byte bytes[] = null;
        try {
            String ignoredClass = "net.sf.cglib.proxy.Enhancer$EnhancerKey$$KeyFactoryByCGLIB$$";
            String ignoredClass2 = "net.sf.cglib.core.MethodWrapper$MethodWrapperKey$$KeyFactoryByCGLIB";
            if (name.startsWith(ignoredClass) || name.startsWith(ignoredClass2)) {
                // ignore
            } else {
                final CtClass ctClass = pool.get(name);
                if (ctClass.isFrozen()) {
                    ctClass.defrost();
                }
                bytes = ctClass.toBytecode();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load class with name " + name + ". Reason: " + e.getMessage(), e);
        }
        return bytes == null ? null : defineClass(name, bytes, 0, bytes.length);
    }

}
