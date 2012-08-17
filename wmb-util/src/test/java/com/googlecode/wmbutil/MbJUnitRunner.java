package com.googlecode.wmbutil;

import com.ibm.broker.plugin.MbXPath;
import org.mockito.cglib.transform.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MbJUnitRunner extends MockitoJUnitRunner {

    public MbJUnitRunner(Class<?> klass) throws InvocationTargetException {
        super(getFromMbTestClassloader(klass));
    }

    public static interface GetXPathExpression {
        String getXPathExpression();
    }

    private static ClassFilter proxiedClasses() {
        return new ClassFilter() {
            @Override public boolean accept(String className) {
                return MbXPath.class.getName().equals(className);
            }
        };
    }

    private static boolean isClassOrInnerClassOf(Class<?> clazz, String actualClassName) {
        return clazz.getName().equals(actualClassName) || actualClassName.startsWith(clazz.getName() + "$");
    }

    private static Class<?> getFromMbTestClassloader(Class<?> klass) {
        try {
//            ClassLoader parentClassLoader = Thread.currentThread().getContextClassLoader();
            ClassLoader test_classLoader = new MockingClassLoader(ClassLoader.getSystemClassLoader(), new ClassFilter() {
                @Override public boolean accept(String className) {
                    return isClassOrInnerClassOf(MbXPath.class, className);
                }
            }, new ClassTransformerFactory() {
                @Override public ClassTransformer newInstance() {
                    return new StoreMbXPathExpressionTransformer();
                }
            });
            return Class.forName(klass.getName(), true, test_classLoader);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static class MbTestClassLoader extends ClassLoader {
        private MbTestClassLoader() {
            super(getSystemClassLoader());
        }

        public MbTestClassLoader(ClassLoader classLoader) {
            super(classLoader);
        }

        @Override public Class<?> loadClass(String className) throws ClassNotFoundException {
            System.out.println("Loading " + className);
            if (MbXPath.class.getName().equals(className)) {
                return null;
            }
            return super.loadClass(className);
        }
    }
}