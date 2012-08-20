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

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public interface NativeTracer {
    void logNamedDebugEntry(String s);

    void logNamedDebugEntryData(String s, String s1);

    void logNamedDebugExit(String s);

    void logNamedDebugExitData(String s, String s1);

    void logNamedDebugTrace(String s, String paramString2);

    void logNamedDebugTraceData(String s, String paramString2, String s1);

    void logNamedEntry(String s);

    void logNamedEntryData(String s, String s1);

    void logNamedExit(String s);

    void logNamedExitData(String s, String s1);

    void logSourceNamedDebugEntryData(String s, String paramString2, String s1);

    void logSourceNamedEntryData(String s, String paramString2, String s1);

    void logNamedTrace(String s, String paramString3);

    void logNamedTraceData(String s, String paramString2, String s1);

    void logNamedUserDebugTrace(String s, String paramString2, long paramLong, String paramString3);

    void logNamedUserDebugTraceData(String s, String paramString2, long paramLong, String paramString3, String[] strings);

    void logNamedUserTrace(String s, String paramString2, long paramLong, String paramString3);

    void logNamedUserTraceData(String s, String paramString2, long paramLong, String paramString3, String[] strings);

    void logSourceNamedDebugEntry(String s, String paramString2);

    void logSourceNamedEntry(String s, String paramString2);

    void logStackTrace(String s, Throwable paramThrowable);

    void logNamedSpecialEntry(String s);

    void logNamedSpecialEntryData(String s, String s1);

    void logNamedSpecialExit(String s);

    void logNamedSpecialExitData(String s, String s1);

    void logNamedSpecialTrace(String s, String paramString2);

    void logNamedSpecialTraceData(String s, String paramString2, String s1);

    void setTraceFileName(String componentName);

    String getTraceFileName();

    void setLogLevel(int level);

    void setLogSize(int paramInt);
}
