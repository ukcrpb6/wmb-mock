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
public interface INativeTracer {
    void setTraceFileName(String paramString);

    String getTraceFileName();

    void setLogLevel(int paramInt);

    void setLogSize(int paramInt);

    void logNamedUserTrace(String paramString1, String paramString2, long paramLong, String paramString3);

    void logNamedUserDebugTrace(String paramString1, String paramString2, long paramLong, String paramString3);

    void logNamedUserTraceData(String paramString1, String paramString2, long paramLong, String paramString3, String[] paramArrayOfString);

    void logNamedUserDebugTraceData(String paramString1, String paramString2, long paramLong, String paramString3, String[] paramArrayOfString);

    void logSourceNamedEntry(String paramString1, String paramString2);

    void logSourceNamedDebugEntry(String paramString1, String paramString2);

    void logSourceNamedEntryData(String paramString1, String paramString2, String paramString3);

    void logSourceNamedDebugEntryData(String paramString1, String paramString2, String paramString3);

    void logNamedEntry(String paramString);

    void logNamedDebugEntry(String paramString);

    void logNamedEntryData(String paramString1, String paramString2);

    void logNamedDebugEntryData(String paramString1, String paramString2);

    void logNamedExit(String paramString);

    void logNamedDebugExit(String paramString);

    void logNamedExitData(String paramString1, String paramString2);

    void logNamedDebugExitData(String paramString1, String paramString2);

    void logNamedTrace(String paramString1, String paramString2);

    void logNamedDebugTrace(String paramString1, String paramString2);

    void logNamedTraceData(String paramString1, String paramString2, String paramString3);

    void logNamedDebugTraceData(String paramString1, String paramString2, String paramString3);

    void logNamedSpecialEntry(String paramString);

    void logNamedSpecialEntryData(String paramString1, String paramString2);

    void logNamedSpecialExit(String paramString);

    void logNamedSpecialExitData(String paramString1, String paramString2);

    void logNamedSpecialTrace(String paramString1, String paramString2);

    void logNamedSpecialTraceData(String paramString1, String paramString2, String paramString3);

}
