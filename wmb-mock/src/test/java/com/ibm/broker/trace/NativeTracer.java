package com.ibm.broker.trace;

/**
 * Created with IntelliJ IDEA.
 * User: bobb
 * Date: 18/08/2012
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
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
