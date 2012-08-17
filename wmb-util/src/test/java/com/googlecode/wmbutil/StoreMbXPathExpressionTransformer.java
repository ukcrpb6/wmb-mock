package com.googlecode.wmbutil;

import org.mockito.asm.Type;
import org.mockito.cglib.core.*;
import org.mockito.cglib.transform.ClassEmitterTransformer;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
* @author Bob Browning <bob.browning@pressassociation.com>
*/
class StoreMbXPathExpressionTransformer extends ClassEmitterTransformer {
    private static final String XPATH_EXPRESSION = "$CGLIB_XPATH_EXPRESSION";
    private static final Type XPATH_EXPRESSION_TYPE = Type.getType(String.class);

    private Class[] delegateIf = new Class[]{MbJUnitRunner.GetXPathExpression.class};

    @Override
    public void begin_class(int version, int access, String className, Type superType, Type[] interfaces, String source) {
        if (!TypeUtils.isInterface(access)) {
            Type[] all = TypeUtils.add(interfaces, TypeUtils.getTypes(delegateIf));
            super.begin_class(version, access, className, superType, all, source);

            declare_field(Constants.ACC_PRIVATE | Constants.ACC_TRANSIENT, XPATH_EXPRESSION, XPATH_EXPRESSION_TYPE, null);

            for (Class aDelegateIf : delegateIf) {
                Method[] methods = aDelegateIf.getMethods();
                for (Method method : methods) {
                    if (Modifier.isAbstract(method.getModifiers())) {
                        addGetter(method);
                    }
                }
            }
        } else {
            super.begin_class(version, access, className, superType, interfaces, source);
        }
    }

    @Override public CodeEmitter begin_method(int access, Signature sig, Type[] exceptions) {
        final CodeEmitter e = super.begin_method(access, sig, exceptions);
        if (sig.getName().equals(Constants.CONSTRUCTOR_NAME) &&
                sig.getArgumentTypes().length == 1 &&
                sig.getArgumentTypes()[0].equals(XPATH_EXPRESSION_TYPE)) {
            return new CodeEmitter(e) {
                private boolean transformInit = true;

                public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                    if (transformInit && opcode == Constants.INVOKESPECIAL) {
                        super_invoke_constructor();
                        load_this();
                        load_arg(0);
                        putfield(XPATH_EXPRESSION);
                        return_value();
                        transformInit = false;
                    } else {
                        super.visitMethodInsn(opcode, owner, name, desc);
                    }
                }
            };
        }
        return e;
    }

    private void addGetter(Method m) {
        final Signature sig = ReflectUtils.getSignature(m);
        Type[] exceptions = TypeUtils.getTypes(m.getExceptionTypes());
        CodeEmitter e = super.begin_method(Constants.ACC_PUBLIC, sig, exceptions);
        e.load_this();
        e.getfield(XPATH_EXPRESSION);
        e.return_value();
        e.end_method();
    }

}
