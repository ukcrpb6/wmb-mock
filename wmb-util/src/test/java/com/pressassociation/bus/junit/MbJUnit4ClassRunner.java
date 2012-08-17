package com.pressassociation.bus.junit;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.googlecode.wmbutil.MbMockFactory;
import com.ibm.broker.plugin.*;
import javassist.*;
import org.junit.runners.model.InitializationError;

import java.util.Map;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MbJUnit4ClassRunner extends PreMockJUnit4ClassRunner {
    public MbJUnit4ClassRunner(Class<?> unitTestClass) throws InitializationError {
        super(unitTestClass);
    }

    @Override
    protected Map<String, CtTransformer> getAdditionalClassesToAlter(Class<?> unitTestClass) {
        ImmutableMap.Builder<String, CtTransformer> builder = ImmutableMap.builder();
        builder.put(MbMessage.class.getName(), CtTransformers.nativeToDelegateTransformer("java.lang.String"));
        builder.put(MbElement.class.getName(), CtTransformers.nativeToDelegateTransformer("java.lang.String"));
//        builder.put(MbElement.class.getName(), CtTransformers.nativeToDelegateTransformer());
        builder.put(MbMockFactory.class.getName(), CtTransformers.noopTransformer());
//        builder.put(MockMbElement.class.getName(), CtTransformers.noopTransformer());
//        builder.put(MockMbMessage.class.getName(), CtTransformers.noopTransformer());
        builder.put(MbXPathSupport.class.getName(), CtTransformers.noopTransformer());
        builder.put(IMbElement.class.getName(), CtTransformers.noopTransformer());
        builder.put(IMbMessage.class.getName(), CtTransformers.noopTransformer());
        builder.put(MbXPath.class.getName(), getMbXPathTransformer());
        return builder.build();
    }

    private CtTransformer getMbXPathTransformer() {
        return new CtTransformer() {
            @Override public void transform(CtClass klass) {
                try {
                    CtClass stringCtClass = klass.getClassPool().get(String.class.getName());
                    klass.addInterface(klass.getClassPool().get(MbXPathValue.class.getName()));
                    CtField f = new CtField(stringCtClass, "value", klass);
                    f.setModifiers(Modifier.PRIVATE);
                    klass.addField(f);
                    klass.addMethod(CtNewMethod.getter("getValue", klass.getField("value")));
                    CtConstructor constructor = klass.getDeclaredConstructor(new CtClass[]{stringCtClass});
                    constructor.setBody("{System.out.println(\"Setting xpath value to \" + $1); $0.value = $1;}");
                } catch (CannotCompileException e) {
                    throw Throwables.propagate(e);
                } catch (NotFoundException e) {
                    throw Throwables.propagate(e);
                }
            }
        };
    }
}
