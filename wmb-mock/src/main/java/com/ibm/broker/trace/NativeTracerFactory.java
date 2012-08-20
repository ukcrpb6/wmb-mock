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
package com.ibm.broker.trace;

import com.google.common.base.Throwables;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class NativeTracerFactory {

    private static Class<?> classUnderTest;
    private static Class<? extends INativeTracer> tracerClass = NoopNativeTracer.class;

    private static class InstanceHolder {
        private static final NativeTracerFactory INSTANCE = new NativeTracerFactory();
    }

    public static NativeTracerFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public synchronized INativeTracer newTracer() {
        if (classUnderTest != null) {
            return withTargetClassTracer();
        } else {
            return noArgsTracer();
        }
    }

    private INativeTracer noArgsTracer() {
        try {
            return tracerClass.newInstance();
        } catch (InstantiationException e) {
            throw Throwables.propagate(e);
        } catch (IllegalAccessException e) {
            throw Throwables.propagate(e);
        }
    }

    private INativeTracer withTargetClassTracer() {
        try {
            Constructor<?> constructor = tracerClass.getDeclaredConstructor(Class.class);
            return (INativeTracer) constructor.newInstance(classUnderTest);
        } catch (InstantiationException e) {
            throw Throwables.propagate(e);
        } catch (IllegalAccessException e) {
            throw Throwables.propagate(e);
        } catch (NoSuchMethodException e) {
            throw Throwables.propagate(e);
        } catch (InvocationTargetException e) {
            throw Throwables.propagate(e);
        }
    }

    public synchronized static void setTracerClass(Class<? extends INativeTracer> tracerClass) {
        NativeTracerFactory.tracerClass = tracerClass;
    }

    public synchronized static void setClassUnderTest(Class<?> tracerClass) {
        NativeTracerFactory.classUnderTest = tracerClass;
    }
}
