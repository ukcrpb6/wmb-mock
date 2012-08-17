package com.googlecode.wmbutil;

import junit.framework.Assert;
import org.junit.Test;
import org.mockito.cglib.transform.ClassFilter;
import org.mockito.cglib.transform.ClassTransformer;
import org.mockito.cglib.transform.ClassTransformerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class ClassLoaderTestCase {

    public static class InnerTestCase {
        @Test
        public void aTest() {
        }
    }

    @Test
    public void testClassLoader() throws Exception {
        MockingClassLoader cl = new MockingClassLoader(ClassLoader.getSystemClassLoader(), new ClassFilter() {
            @Override public boolean accept(String className) {
                return false;
            }
        }, new ClassTransformerFactory() {
            @Override public ClassTransformer newInstance() {
                return null;
            }
        });

        Class<?> testCase = cl.loadClass(InnerTestCase.class.getName());

        Assert.assertEquals(cl, testCase.getClassLoader());
        Assert.assertEquals(1, testCase.getDeclaredMethods().length);
        Method m = testCase.getDeclaredMethods()[0];
        Assert.assertEquals(1, m.getAnnotations().length);

        Class<? extends Annotation> testAnnotationClass = cl.loadClass(Test.class.getName());
        Annotation t = m.getAnnotation(testAnnotationClass);
        Assert.assertNotNull(t);
    }
}
