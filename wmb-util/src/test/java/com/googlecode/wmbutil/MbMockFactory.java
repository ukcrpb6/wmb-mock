package com.googlecode.wmbutil;

import com.ibm.broker.plugin.IMbElement;
import com.ibm.broker.plugin.IMbMessage;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;
import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;
import org.mockito.internal.creation.jmock.ClassImposterizer;

import java.lang.reflect.Method;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public final class MbMockFactory {

    public static interface IMockMbMessage {
        MockMbMessage getMockMbMessage();
    }

    public static interface IMockMbElement {
        MockMbElement getMockMbElement();
    }

    private static class InstanceHolder {
        private static final MbMockFactory instance = new MbMockFactory();
    }

    public static MbMockFactory getInstance() {
        return InstanceHolder.instance;
    }

    private MbMockFactory() {}

    public MbMessage createMbMessage() {
        final MockMbMessage mock = new MockMbMessage();
        MbMessage mbMessage = ClassImposterizer.INSTANCE.imposterise(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                Method mockMethod = null;
                try {
                    mockMethod = IMockMbMessage.class.getMethod(method.getName(), method.getParameterTypes());
                } catch (Exception ignored) {}
                if (mockMethod != null) {
                    return mock;
                }
                return MockMbMessage.class.getMethod(method.getName(), method.getParameterTypes()).invoke(mock, args);
            }
        }, MbMessage.class, new Class[]{IMbMessage.class, IMockMbMessage.class});
        mock.setMbMessage(mbMessage);
        return mbMessage;
    }

    public MbElement createMbElement() {
        final MockMbElement mock = new MockMbElement();

        MbElement mbElement = ClassImposterizer.INSTANCE.imposterise(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                Method mockMethod = null;
                try {
                    mockMethod = IMockMbElement.class.getMethod(method.getName(), method.getParameterTypes());
                } catch (Exception ignored) {}
                if (mockMethod != null) {
                    return mock;
                }
                return MockMbElement.class.getMethod(method.getName(), method.getParameterTypes()).invoke(mock, args);
            }
        }, MbElement.class, new Class[]{IMbElement.class, IMockMbElement.class});
        mock.setMbElement(mbElement);
        return mbElement;
    }

    public MbElement createMbElement(MockMbMessage parent) {
        final MockMbElement mock = new MockMbElement();

        MbElement mbElement = ClassImposterizer.INSTANCE.imposterise(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                Method mockMethod = null;
                try {
                    mockMethod = IMockMbElement.class.getMethod(method.getName(), method.getParameterTypes());
                } catch (Exception ignored) {}
                if (mockMethod != null) {
                    return mock;
                }
                return MockMbElement.class.getMethod(method.getName(), method.getParameterTypes()).invoke(mock, args);
            }
        }, MbElement.class, new Class[]{IMbElement.class, IMockMbElement.class});
        mock.setMbElement(mbElement);
        mock.setMockMessage(parent);
        return mbElement;
    }
}
