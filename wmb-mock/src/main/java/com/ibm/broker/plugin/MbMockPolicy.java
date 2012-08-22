/**
 * Copyright 2012 Bob Browning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.broker.plugin;

import com.ibm.broker.trace.INativeTracer;
import com.ibm.broker.trace.NativeTracer;
import com.ibm.broker.trace.NativeTracerFactory;
import com.ibm.broker.trace.Trace;
import org.powermock.api.support.SuppressCode;
import org.powermock.core.spi.PowerMockPolicy;
import org.powermock.mockpolicies.MockPolicyClassLoadingSettings;
import org.powermock.mockpolicies.MockPolicyInterceptionSettings;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MbMockPolicy implements PowerMockPolicy {
    @Override public void applyClassLoadingPolicy(MockPolicyClassLoadingSettings settings) {
        settings.addFullyQualifiedNamesOfClassesToLoadByMockClassloader(new String[]{
                MbMessageAssembly.class.getName(),
                MbMessage.class.getName(),
                MbElement.class.getName(),
                MbXPath.class.getName(),
                Trace.class.getName(),
                NativeTracer.class.getName()
        });
    }

    @Override public void applyInterceptionPolicy(MockPolicyInterceptionSettings settings) {
        PseudoNativeMbXPathManager.getInstance().clear();
        PseudoNativeMbElementManager.getInstance().clear();
        PseudoNativeMbMessageManager.getInstance().clear();
        PseudoNativeMbMessageAssemblyManager.getInstance().clear();

        proxyNativeTracerNativeMethods(settings);
        proxyMbMessageAssemblyNativeMethods(settings);
        proxyMbMessageNativeMethods(settings);
        proxyMbElementNativeMethods(settings);
        proxyMbXPathNativeMethods(settings);
    }

    public void proxyNativeTracerNativeMethods(MockPolicyInterceptionSettings settings) {
        final INativeTracer tracer = NativeTracerFactory.getInstance().newTracer();
        for (final Method m : INativeTracer.class.getDeclaredMethods()) {
            Method method = Whitebox.getMethod(NativeTracer.class, m.getName(), m.getParameterTypes());
            settings.proxyMethod(method, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                    return m.invoke(tracer, args);
                }
            });
        }
        SuppressCode.suppressConstructor(NativeTracer.class);
    }

    public void proxyMbMessageNativeMethods(MockPolicyInterceptionSettings settings) {
        for (final Method m : NativeMbMessageManager.class.getDeclaredMethods()) {
            Method method = Whitebox.getMethod(MbMessage.class, m.getName(), m.getParameterTypes());
            settings.proxyMethod(method, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                    return m.invoke(PseudoNativeMbMessageManager.getInstance(), args);
                }
            });
        }
    }

    public void proxyMbMessageAssemblyNativeMethods(MockPolicyInterceptionSettings settings) {
        for (final Method m : NativeMbMessageAssemblyManager.class.getDeclaredMethods()) {
            Method method = Whitebox.getMethod(MbMessageAssembly.class, m.getName(), m.getParameterTypes());
            settings.proxyMethod(method, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                    return m.invoke(PseudoNativeMbMessageAssemblyManager.getInstance(), args);
                }
            });
        }
    }

    public void proxyMbElementNativeMethods(MockPolicyInterceptionSettings settings) {
        for (final Method m : NativeMbElementManager.class.getDeclaredMethods()) {
            Method method = Whitebox.getMethod(MbElement.class, m.getName(), m.getParameterTypes());
            settings.proxyMethod(method, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                    return m.invoke(PseudoNativeMbElementManager.getInstance(), args);
                }
            });
        }
    }

    public void proxyMbXPathNativeMethods(MockPolicyInterceptionSettings settings) {
        for (final Method m : NativeMbXPathManager.class.getDeclaredMethods()) {
            Method method = Whitebox.getMethod(MbXPath.class, m.getName(), m.getParameterTypes());
            settings.proxyMethod(method, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                    return m.invoke(PseudoNativeMbXPathManager.getInstance(), args);
                }
            });
        }
    }


}
