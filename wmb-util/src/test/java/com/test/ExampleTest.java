package com.test;

import com.google.common.base.Throwables;
import javassist.*;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

@PreMock({EasyToMock.class})
@RunWith(PreMockJUnit4ClassRunner.class)
//@RunWith(MockitoJUnitRunner.class)
public class ExampleTest {

    @Mock
    HardToMock hardToMock;

    @Mock
    EasyToMock easyToMock;

    @CtTransform({HardToMock.class})
    public static CtTransformer stupidMethod() {
        CtTransformerChain chain = new CtTransformerChain(CtTransformers.allowMockingOfFinalClasses(), createGetterTransformer());
        chain.add(CtTransformers.nativeToDelegateTransformer("java.lang.String"));
        return chain;
//        return CtTransformers.allowMockingOfFinalClasses();
    }

    public interface IValue {
        String getValue();
    }

    private static CtTransformer createGetterTransformer() {
        return new CtTransformer() {
            @Override public void transform(CtClass klass) {
                try {
                    CtClass stringCtClass = klass.getClassPool().get(String.class.getName());
                    klass.addInterface(klass.getClassPool().get(IValue.class.getName()));
                    CtField f = new CtField(stringCtClass, "value", klass);
                    f.setModifiers(Modifier.PRIVATE);
                    klass.addField(f);
                    klass.addMethod(CtNewMethod.getter("getValue", klass.getField("value")));
                    CtConstructor constructor = klass.getDeclaredConstructor(new CtClass[] {stringCtClass});
                    constructor.setBody("{System.out.println($1); $0.value = $1;}");
                } catch (CannotCompileException e) {
                    throw Throwables.propagate(e);
                } catch (NotFoundException e) {
                    throw Throwables.propagate(e);
                }
            }
        };
    }

    @Test
    public void testIt() {
        try {
            HardToMock v = new HardToMock("value");
            v.nativeMethod();
            if(IValue.class.isAssignableFrom(v.getClass())) {
                System.out.println((IValue.class.cast(v)).getValue());
            }
            Assert.assertNotNull(hardToMock.getClass().getMethod("getValue"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Mockito.when(hardToMock.finalMethod()).thenReturn("mock result from final method");
        Mockito.when(hardToMock.nativeMethod()).thenReturn("mock result from native method");
        Mockito.when(easyToMock.method()).thenReturn("PreMock does not get in the way");

        Assert.assertEquals("mock result from final method", hardToMock.finalMethod());
        Assert.assertEquals("mock result from native method", hardToMock.nativeMethod());
        Assert.assertEquals("PreMock does not get in the way", easyToMock.method());
    }
}
