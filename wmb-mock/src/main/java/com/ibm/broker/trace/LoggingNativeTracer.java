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

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import static com.ibm.broker.trace.Trace.*;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class LoggingNativeTracer extends AbstractNativeTracer {

    public static final Marker EVENT_MARKER  = MarkerFactory.getMarker("EVENT");
    public static final Marker SYSTEM_MARKER = MarkerFactory.getMarker("SYSTEM");
    public static final Marker USER_MARKER   = MarkerFactory.getMarker("USER");

    private static class InstanceHolder {
        private static final LoggingNativeTracer INSTANCE = new LoggingNativeTracer();
    }

    public static LoggingNativeTracer getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private final ThreadLocal<Logger> logger = new ThreadLocal<Logger>() {
        @Override protected Logger initialValue() {
            return LoggerFactory.getLogger(getTestClass());
        }
    };

    private final ThreadLocal<Class<?>> testClass = new ThreadLocal<Class<?>>() {
        @Override protected Class<?> initialValue() {
            return LoggingNativeTracer.class;
        }
    };

    private final ThreadLocal<Integer> level = new ThreadLocal<Integer>();

    public LoggingNativeTracer() {
        this.level.set(NONE);
    }

    public LoggingNativeTracer(Class<?> testClass) {
        this.testClass.set(testClass);
        this.level.set(NONE);
    }

    public Logger getLogger() {
        return logger.get();
    }

    public Class<?> getTestClass() {
        return testClass.get();
    }

    public void setTestClass(Class<?> testClass) {
        this.testClass.set(testClass);
    }

    public int getLevel() {
        return level.get();
    }

    public static String join(String s, Object... additional) {
        if (additional.length == 0) {
            return s;
        }
        StringBuilder b = new StringBuilder(Preconditions.checkNotNull(s));
        for (Object a : additional) {
            b.append(' ');
            b.append(String.valueOf(a));
        }
        return b.toString();
    }

    @Override public String getTraceFileName() {
        return getTestClass().getName();
    }

    @Override public void logNamedDebugEntry(String s) {
        log(EVENT, s);
    }

    @Override public void logNamedDebugEntryData(String s, String s1) {
        log(EVENT, join(s, s1));
    }

    @Override public void logNamedDebugExit(String s) {
        log(EVENT, s);
    }

    @Override public void logNamedDebugExitData(String s, String s1) {
        log(EVENT, join(s, s1));
    }

    @Override public void logNamedDebugTrace(String s, String paramString2) {
        log(DEBUGTRACE, join(s, paramString2));
    }

    @Override public void logNamedDebugTraceData(String s, String paramString2, String s1) {
        log(DEBUGTRACE, join(s, s1));
    }

    @Override public void logNamedEntry(String s) {
        log(EVENT, s);
    }

    @Override public void logNamedEntryData(String s, String s1) {
        log(EVENT, join(s, s1));
    }

    @Override public void logNamedExit(String s) {
        log(EVENT, s);
    }

    @Override public void logNamedExitData(String s, String s1) {
        log(EVENT, join(s, s1));
    }

    @Override public void logNamedSpecialEntry(String s) {
        log(EVENT, join(s));
    }

    @Override public void logNamedSpecialEntryData(String s, String s1) {
        log(EVENT, join(s, s1));
    }

    @Override public void logNamedSpecialExit(String s) {
        log(EVENT, join(s));
    }

    @Override public void logNamedSpecialExitData(String s, String s1) {
        log(EVENT, join(s, s1));
    }

    @Override public void logNamedSpecialTrace(String s, String paramString2) {
        log(TRACE, join(s, paramString2));
    }

    @Override public void logNamedSpecialTraceData(String s, String paramString2, String s1) {
        log(TRACE, join(s, paramString2, s1));
    }

    @Override public void logNamedTrace(String s, String paramString3) {
        log(TRACE, join(s, paramString3));
    }

    @Override public void logNamedTraceData(String s, String paramString2, String s1) {
        log(TRACE, join(s, paramString2, s1));
    }

    @Override public void logNamedUserDebugTrace(String s, String paramString2, long paramLong, String paramString3) {
        log(USERDEBUGTRACE, join(s, paramString2, paramLong, paramString3));
    }

    @Override
    public void logNamedUserDebugTraceData(String s, String paramString2, long paramLong, String paramString3, String[] strings) {
        log(USERDEBUGTRACE, join(s, paramString2, paramLong, paramString3, strings));
    }

    @Override public void logNamedUserTrace(String s, String paramString2, long paramLong, String paramString3) {
        log(USERTRACE, join(s, paramString2, paramLong, paramString3));
    }

    @Override
    public void logNamedUserTraceData(String s, String paramString2, long paramLong, String paramString3, String[] strings) {
        log(USERTRACE, join(s, paramString2, paramLong, paramString3, strings));
    }

    @Override public void logSourceNamedDebugEntry(String s, String paramString2) {
        log(EVENT, join(s, paramString2));
    }

    @Override public void logSourceNamedDebugEntryData(String s, String paramString2, String s1) {
        log(EVENT, join(s, paramString2, s1));
    }

    @Override public void logSourceNamedEntry(String s, String paramString2) {
        log(EVENT, join(s, paramString2));
    }

    @Override public void logSourceNamedEntryData(String s, String paramString2, String s1) {
        log(EVENT, join(s, paramString2));
    }

    @Override public void setLogLevel(int level) {
        this.level.set(level);
    }

    /*
        public static final int NONE = 0;
        public static final int EVENT = 1;
        public static final int USERTRACE = 2;
        public static final int USERDEBUGTRACE = 3;
        public static final int TRACE = 4;
        public static final int DEBUGTRACE = 5;
     */
    private void log(int level, String message) {
        if(level <= getLevel()) {
            switch(level) {
                case EVENT:
                    getLogger().info(EVENT_MARKER, message);
                    break;
                case USERTRACE:
                    getLogger().info(USER_MARKER, message);
                    break;
                case TRACE:
                    getLogger().info(SYSTEM_MARKER, message);
                    break;
                case USERDEBUGTRACE:
                    getLogger().debug(USER_MARKER, message);
                    break;
                case DEBUGTRACE:
                    getLogger().debug(SYSTEM_MARKER, message);
                    break;
            }
        }
    }
}
