package com.pressassociation.bus.junit;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

public class PreMockJUnit4ClassRunner extends BlockJUnit4ClassRunner {

    private static final ClassLoader classLoader = new PreMockClassLoader(
            PreMockJUnit4ClassRunner.class.getClassLoader());

    public PreMockJUnit4ClassRunner(Class<?> unitTestClass) throws InitializationError {
        super(unitTestClass);

        PreMockClassLoader.addClasses(getClassesToAlter(unitTestClass));

        try {
            // Important, we must load our test class with our class loader,
            // and then replace the TestClass in the BaseJUnit4ClassRunner,
            // with our own.
            Class<?> clazz = classLoader.loadClass(unitTestClass.getName());
            TestClass testClass = new TestClass(clazz);
            replaceTestClass(testClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, CtTransformer> getClassesToAlter(Class<?> unitTestClass) {
        ImmutableMap.Builder<String, CtTransformer> builder = ImmutableMap.builder();

        // The actual JUnit test must be loaded with the UnitTestClassLoader
        builder.put(unitTestClass.getName(), CtTransformers.noopTransformer());

        List<Method> methods = Lists.newArrayList(unitTestClass.getMethods());

        for(final Method method : methods) {
            CtTransform annotation = method.getAnnotation(CtTransform.class);
            if(annotation != null) {
                if (!CtTransformer.class.equals(method.getReturnType())) {
                    throw new RuntimeException(
                            "Method annotated with CtTransform must return instance of CtTransformer");
                }
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("Method annotated with CtTransform must be static");
                }

                for(Class<?> c : annotation.value()) {
                    try {
                        builder.put(c.getName(), (CtTransformer) method.invoke(null));
                    } catch (IllegalAccessException e) {
                        throw Throwables.propagate(e);
                    } catch (InvocationTargetException e) {
                        throw Throwables.propagate(e);
                    }
                }
            }
        }

        PreMock annotation = unitTestClass.getAnnotation(PreMock.class);

        if (annotation != null) {
            for (Class<?> c : annotation.value()) {
                builder.put(c.getName(), CtTransformers.allowMockingOfFinalClasses());
            }
        }

        builder.putAll(getAdditionalClassesToAlter(unitTestClass));

        return builder.build();
    }

    protected Map<? extends String, ? extends CtTransformer> getAdditionalClassesToAlter(Class<?> unitTestClass) {
        return ImmutableMap.of();
    }

    /**
     * This takes the test class and replaces it in the BlockJUnit4ClassRunner.
     * This is to allow a TestClass to be constructed with a class loaded by our
     * class loader, instead of the default class loader. This way any class
     * referenced by our unit test can be loaded by our class loader.
     */
    private void replaceTestClass(TestClass testClass) {
        // Since member fTestClass, and method validate are private in
        // BlockJunit4ClassRunner, using reflection to replace fTestClass
        // with one loaded by our class-loader. We need to then call
        // validate to mimic behavior done by the construction of
        // BlockJunit4ClassRunner.
        //
        // This is done avoid duplicating code in BlockJunit4ClassRunner.
        // If you wanted to be less tricking or prone to future changes in
        // BlockJunit4ClassRunner, you would extend ParentRunner and
        // redo/duplicate what BlockJunit4ClassRunner is doing.
        try {
            for (Field field : ParentRunner.class.getDeclaredFields()) {
                if ("fTestClass".equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(this, testClass);
                    break;
                }
            }

            for (Method method : ParentRunner.class.getDeclaredMethods()) {
                if ("validate".equals(method.getName())) {
                    method.setAccessible(true);
                    method.invoke(this);
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Borrowed from MockitoJUnitRunner so @Mock and @InjectMocks will work.
    @SuppressWarnings("deprecation")
    @Override
    protected Statement withBefores(FrameworkMethod method, Object target,
                                    Statement statement) {
        MockitoAnnotations.initMocks(target);
        return super.withBefores(method, target, statement);
    }
}
