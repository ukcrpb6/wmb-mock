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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class LoggingNativeTracer extends AbstractNativeTracer {

    private final Logger logger;
    private final Class<?> classUnderTest;

    public LoggingNativeTracer(Class<?> classUnderTest) {
        this.classUnderTest = classUnderTest;
        this.logger = Logger.getLogger(classUnderTest.getName());
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
        return classUnderTest.getName();
    }

    @Override public void logNamedDebugEntry(String s) {
        logger.fine(s);
    }

    @Override public void logNamedDebugEntryData(String s, String s1) {
        logger.fine(join(s, s1));
    }

    @Override public void logNamedDebugExit(String s) {
        logger.fine(s);
    }

    @Override public void logNamedDebugExitData(String s, String s1) {
        logger.fine(join(s, s1));
    }

    @Override public void logNamedDebugTrace(String s, String paramString2) {
        logger.fine(join(s, paramString2));
    }

    @Override public void logNamedDebugTraceData(String s, String paramString2, String s1) {
        logger.fine(join(s, s1));
    }

    @Override public void logNamedEntry(String s) {
        logger.fine(s);
    }

    @Override public void logNamedEntryData(String s, String s1) {
        logger.fine(join(s, s1));
    }

    @Override public void logNamedExit(String s) {
        logger.fine(s);
    }

    @Override public void logNamedExitData(String s, String s1) {
        logger.fine(join(s, s1));
    }

    @Override public void logNamedSpecialEntry(String s) {
        logger.fine(join(s));
    }

    @Override public void logNamedSpecialEntryData(String s, String s1) {
        logger.fine(join(s, s1));
    }

    @Override public void logNamedSpecialExit(String s) {
        logger.fine(join(s));
    }

    @Override public void logNamedSpecialExitData(String s, String s1) {
        logger.fine(join(s, s1));
    }

    @Override public void logNamedSpecialTrace(String s, String paramString2) {
        logger.fine(join(s, paramString2));
    }

    @Override public void logNamedSpecialTraceData(String s, String paramString2, String s1) {
        logger.fine(join(s, paramString2, s1));
    }

    @Override public void logNamedTrace(String s, String paramString3) {
        logger.fine(join(s, paramString3));
    }

    @Override public void logNamedTraceData(String s, String paramString2, String s1) {
        logger.fine(join(s, paramString2, s1));
    }

    @Override public void logNamedUserDebugTrace(String s, String paramString2, long paramLong, String paramString3) {
        logger.fine(join(s, paramString2, paramLong, paramString3));
    }

    @Override
    public void logNamedUserDebugTraceData(String s, String paramString2, long paramLong, String paramString3, String[] strings) {
        logger.fine(join(s, paramString2, paramLong, paramString3, strings));
    }

    @Override public void logNamedUserTrace(String s, String paramString2, long paramLong, String paramString3) {
        logger.fine(join(s, paramString2, paramLong, paramString3));
    }

    @Override
    public void logNamedUserTraceData(String s, String paramString2, long paramLong, String paramString3, String[] strings) {
        logger.fine(join(s, paramString2, paramLong, paramString3, strings));
    }

    @Override public void logSourceNamedDebugEntry(String s, String paramString2) {
        logger.fine(join(s, paramString2));
    }

    @Override public void logSourceNamedDebugEntryData(String s, String paramString2, String s1) {
        logger.fine(join(s, paramString2, s1));
    }

    @Override public void logSourceNamedEntry(String s, String paramString2) {
        logger.fine(join(s, paramString2));
    }

    @Override public void logSourceNamedEntryData(String s, String paramString2, String s1) {
        logger.fine(join(s, paramString2));
    }

    @Override public void setLogLevel(int level) {
        logger.setLevel(Level.FINE);
    }
}
