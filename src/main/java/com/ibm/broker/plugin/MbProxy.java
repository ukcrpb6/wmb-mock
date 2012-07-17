package com.ibm.broker.plugin;


import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class MbProxy {
    public static IMbElement wrap(final MbElement delegate) {
        return (IMbElement) Proxy.newProxyInstance(
                MbProxy.class.getClassLoader(), new Class<?>[]{MbXPathSupport.class, IMbElement.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        if(method.getName().equals("getMbElement") && method.getReturnType().equals(MbElement.class)) {
                            return delegate;
                        }
                        Object r = method.invoke(delegate, objects);
                        if (r instanceof MbElement) {
                            return wrap((MbElement) r);
                        }
                        if (r.getClass().isArray() &&
                                r.getClass().getComponentType().equals(MbElement.class)) {
                            return Iterables.toArray(Iterables.transform(Lists.newArrayList((MbElement[]) r),
                                    new Function<MbElement, IMbElement>() {
                                        @Override
                                        public IMbElement apply(@Nullable MbElement input) {
                                            Preconditions.checkNotNull(input);
                                            return wrap(input);
                                        }
                                    }), IMbElement.class);
                        }
                        return r;
                    }
                });
    }

    public static IMbMessage wrap(final MbMessage delegate) {
        return (IMbMessage) Proxy.newProxyInstance(
                MbProxy.class.getClassLoader(), new Class<?>[]{MbXPathSupport.class, IMbMessage.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        if(method.getName().equals("getMbMessage") && method.getReturnType().equals(MbMessage.class)) {
                            return delegate;
                        }
                        Object r = method.invoke(delegate, objects);
                        if (r instanceof MbMessage) {
                            return wrap((MbMessage) r);
                        }
                        if (r.getClass().isArray() &&
                                r.getClass().getComponentType().equals(MbMessage.class)) {
                            return Iterables.toArray(Iterables.transform(Lists.newArrayList((MbMessage[]) r),
                                    new Function<MbMessage, IMbMessage>() {
                                        @Override
                                        public IMbMessage apply(@Nullable MbMessage input) {
                                            Preconditions.checkNotNull(input);
                                            return wrap(input);
                                        }
                                    }), IMbMessage.class);
                        }
                        return r;
                    }
                });
    }
}
