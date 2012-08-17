package com.pressassociation.bus.junit;

import com.google.common.base.Throwables;
import javassist.*;

import java.lang.reflect.Modifier;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class CtTransformers {

    public static CtTransformer noopTransformer() {
        return new CtTransformer() {
            @Override public void transform(CtClass klass) {
                // Does nothing
            }
        };
    }

    public static CtTransformer allowMockingOfFinalClasses() {
        return new CtTransformer() {
            @Override public void transform(CtClass cc) {
                // remove final modifier from the class
                if (Modifier.isFinal(cc.getModifiers())) {
                    cc.setModifiers(cc.getModifiers() & ~Modifier.FINAL);
                }

                // remove final modifiers from all methods
                CtMethod[] methods = cc.getDeclaredMethods();
                for (CtMethod method : methods) {
                    if (Modifier.isFinal(method.getModifiers())) {
                        method.setModifiers(method.getModifiers() & ~Modifier.FINAL);
                    }
                }
            }
        };
    }

    public static CtTransformer nativeToDelegateTransformer(final String delegateClassName) {
        return new CtTransformer() {
            @Override public void transform(CtClass klass) {
                try {
                    CtClass delegateClass = klass.getClassPool().get(delegateClassName);
                    CtField field = new CtField(delegateClass, "delegate", klass);
                    field.setModifiers(Modifier.FINAL | Modifier.PUBLIC);
                    klass.addField(field);

                    CtMethod[] methods = klass.getDeclaredMethods();
                    for (CtMethod method : methods) {
                        if (Modifier.isNative(method.getModifiers())) {

                            try {
                                method.setModifiers(method.getModifiers() & ~Modifier.NATIVE);

                                CtClass rt = method.getReturnType();
                                System.out.println(rt);
                                if (rt.isPrimitive()) {
                                    if (rt.equals(CtClass.voidType)) {
                                        method.setBody("{ }");
                                    } else if (rt.equals(CtClass.booleanType)) {
                                        method.setBody("{ return false; }");
                                    } else if (rt.equals(CtClass.byteType)) {
                                        method.setBody("{ return 0x0; }");
                                    } else if (rt.equals(CtClass.charType)) {
                                        method.setBody("{ return 0; }");
                                    } else if (rt.equals(CtClass.doubleType)) {
                                        method.setBody("{ return 0d; }");
                                    } else if (rt.equals(CtClass.floatType)) {
                                        method.setBody("{ return 0f; }");
                                    } else if (rt.equals(CtClass.intType)) {
                                        method.setBody("{ return 0; }");
                                    } else if (rt.equals(CtClass.longType)) {
                                        method.setBody("{ return 0L; }");
                                    } else if (rt.equals(CtClass.shortType)) {
                                        method.setBody("{ return 0; }");
                                    } else {
                                        throw new IllegalArgumentException("Unexpected primative");
                                    }
                                } else {
                                    method.setBody("{ throw new RuntimeException(\"XX\"); }");
                                }
                            } catch (CannotCompileException e) {
                                throw Throwables.propagate(e);
                            }
                        }
                    }
                } catch (NotFoundException e) {
                    throw Throwables.propagate(e);
                } catch (CannotCompileException e) {
                    throw Throwables.propagate(e);
                }
            }
        };
    }
}
