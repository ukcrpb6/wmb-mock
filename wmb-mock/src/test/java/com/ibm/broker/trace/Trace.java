package com.ibm.broker.trace;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Vector;

public class Trace {
    private static final String copyright = "Licensed Material - Property of IBM \n5648-C63 (c) Copyright IBM Corp. 2000, 2001 - All Rights Reserved. \nUS Government Users Restricted Rights - Use, duplication or disclosure \nrestricted by GSA ADP Schedule Contract with IBM Corp.";
    private static String componentName = null;
    public static final int NONE = 0;
    public static final int EVENT = 1;
    public static final int USERTRACE = 2;
    public static final int USERDEBUGTRACE = 3;
    public static final int TRACE = 4;
    public static final int DEBUGTRACE = 5;
    public static String[] iNullStrArray = {"NULL"};

    private static NativeTracer iTracer = new NoopTracer();

    public static boolean userTraceIsOn = false;

    public static boolean userDebugTraceIsOn = false;

    public static boolean traceIsOn = false;

    public static boolean isOn = false;
    public static final boolean devOnly = true;
    public static boolean researchTrace = false;

    private static Vector<TraceListener> iListeners = new Vector<TraceListener>();

    public static NativeTracer getTrace() {
        return iTracer;
    }

    public static void initTrace(String componentName) {
        if (iTracer == null) {
            iTracer = new NoopTracer();
        }

        if (componentName != null) {
            iTracer.setTraceFileName(componentName);
            Trace.componentName = componentName;
        }
    }

    public static void addListener(TraceListener listener) {
        iListeners.addElement(listener);
    }

    public static void informListeners(int level) {
        for (int i = 0; i < iListeners.size(); i++)
            ((TraceListener) iListeners.elementAt(i)).traceEvent(level);
    }

    public static synchronized String getTraceFileName() {
        if (componentName != null) {
            return iTracer.getTraceFileName();
        }
        return null;
    }

    public static void setLogLevel(int level) {
        isOn = level > 0;
        iTracer.setLogLevel(level);

        informListeners(level);
    }

    public static void setLogSize(int paramInt) {
        iTracer.setLogSize(paramInt);
    }

    public static void traceTurnedOn() {
        boolean bool = isOn;
        isOn = true;

        if (!bool)
            informListeners(5);
    }

    public static void traceTurnedOn(int level) {
        if (!researchTrace) {
            userTraceIsOn = false;
            userDebugTraceIsOn = false;
            traceIsOn = false;
            isOn = false;
            if (level >= 2) {
                userTraceIsOn = true;
                if (level >= 3) {
                    userDebugTraceIsOn = true;
                    if (level >= 4) {
                        traceIsOn = true;
                        if (level == 5) {
                            isOn = true;
                        }
                    }
                }
            }

            informListeners(level);
        }
    }

    public static void traceTurnedOff() {
        boolean bool = isOn | userTraceIsOn | userDebugTraceIsOn | traceIsOn;
        isOn = false;
        userTraceIsOn = false;
        userDebugTraceIsOn = false;
        traceIsOn = false;

        if (bool)
            informListeners(0);
    }

    public static boolean isOn() {
        return isOn;
    }

    public static String getStackTrace(Throwable paramThrowable) {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter localPrintWriter = new PrintWriter(localByteArrayOutputStream);
        paramThrowable.printStackTrace(localPrintWriter);
        localPrintWriter.flush();
        String str = localByteArrayOutputStream.toString();

        char[] arrayOfChar = new char[str.length()];
        str.getChars(0, str.length(), arrayOfChar, 0);

        int i = 1;
        int j = 0;
        for (int k = 0; k < str.length(); k++) {
            if (Character.isSpaceChar(arrayOfChar[k])) {
                if (i != 0)
                    arrayOfChar[(j++)] = arrayOfChar[k];
                i = 0;
            } else if (!Character.isWhitespace(arrayOfChar[k])) {
                i = 1;
                arrayOfChar[(j++)] = arrayOfChar[k];
            } else {
                if (i == 0)
                    continue;
                arrayOfChar[(j++)] = ' ';
                i = 0;
            }

        }

        return new String(arrayOfChar, 0, j);
    }

    private static Object[] nullToEmptyArray(Object[] arr) {
        return arr == null ? new Object[] {} : arr;
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] nullToEmptyArrayWithNarrowedType(T[] arr) {
        return (T[]) nullToEmptyArray(arr);
    }

    private static String dataValueOf(Object o) {
        return o == null ? "NULL" : String.valueOf(o);
    }

    private static String methodCallString(Object o, String method) {
        return methodCallString(o.getClass().getName() + "@" + Integer.toString(o.hashCode(), 16), method);
    }

    private static String methodCallString(String o, String method) {
        return o + "." + method;
    }

    public static synchronized void logNamedDebugEntry(Object object, String method) {
        iTracer.logNamedDebugEntry(methodCallString(object, method));
    }

    public static synchronized void logNamedDebugEntry(String paramString1, String method) {
        iTracer.logNamedDebugEntry(methodCallString(paramString1, method));
    }

    public static synchronized void logNamedDebugEntryData(Object object, String method, String message) {
        iTracer.logNamedDebugEntryData(methodCallString(object, method), dataValueOf(message));
    }

    public static synchronized void logNamedDebugEntryData(String paramString1, String method, String message) {
        iTracer.logNamedDebugEntryData(methodCallString(paramString1, method), dataValueOf(message));
    }

    public static synchronized void logNamedDebugExit(Object object, String method) {
        iTracer.logNamedDebugExit(methodCallString(object, method));
    }

    public static synchronized void logNamedDebugExit(String paramString1, String method) {
        iTracer.logNamedDebugExit(methodCallString(paramString1, method));
    }

    public static synchronized void logNamedDebugExitData(Object object, String method, String message) {
        iTracer.logNamedDebugExitData(methodCallString(object, method), dataValueOf(message));
    }

    public static synchronized void logNamedDebugExitData(String paramString1, String method, String message) {
        iTracer.logNamedDebugExitData(methodCallString(paramString1, method), dataValueOf(message));
    }

    public static synchronized void logNamedDebugTrace(Object object, String method, String paramString2) {
        iTracer.logNamedDebugTrace(methodCallString(object, method), paramString2);
    }

    public static synchronized void logNamedDebugTrace(String paramString1, String method, String paramString3) {
        iTracer.logNamedDebugTrace(methodCallString(paramString1, method), paramString3);
    }

    public static synchronized void logNamedDebugTraceData(Object object, String method, String paramString2, String message) {
        iTracer.logNamedDebugTraceData(methodCallString(object, method), paramString2, dataValueOf(message));
    }

    public static synchronized void logNamedDebugTraceData(String paramString1, String method, String paramString3, String message) {
        iTracer.logNamedDebugTraceData(methodCallString(paramString1, method), paramString3, dataValueOf(message));
    }

    public static synchronized void logNamedEntry(Object object, String method) {
        iTracer.logNamedEntry(methodCallString(object, method));
    }

    public static synchronized void logNamedEntry(String paramString1, String method) {
        iTracer.logNamedEntry(methodCallString(paramString1, method));
    }

    public static synchronized void logNamedEntryData(Object object, String method, String message) {
        iTracer.logNamedEntryData(methodCallString(object, method), dataValueOf(message));
    }

    public static synchronized void logNamedEntryData(String paramString1, String method, String message) {
        iTracer.logNamedEntryData(methodCallString(paramString1, method), dataValueOf(message));
    }

    public static synchronized void logNamedExit(Object object, String method) {
        iTracer.logNamedExit(methodCallString(object, method));
    }

    public static synchronized void logNamedExit(String paramString1, String method) {
        iTracer.logNamedExit(methodCallString(paramString1, method));
    }

    public static synchronized void logNamedExitData(Object object, String method, String message) {
        iTracer.logNamedExitData(methodCallString(object, method), dataValueOf(message));
    }

    public static synchronized void logNamedExitData(String paramString1, String method, String message) {
        iTracer.logNamedExitData(methodCallString(paramString1, method), dataValueOf(message));
    }

    public static synchronized void logNamedSourceDebugEntryData(Object object, String method, String paramString2, String message) {
        iTracer.logSourceNamedDebugEntryData(methodCallString(object, method), paramString2, dataValueOf(message));
    }

    public static synchronized void logNamedSourceDebugEntryData(String paramString1, String method, String paramString3, String message) {
        iTracer.logSourceNamedDebugEntryData(methodCallString(paramString1, method), paramString3, dataValueOf(message));
    }

    public static synchronized void logNamedSourceEntryData(Object object, String method, String paramString2, String message) {
        iTracer.logSourceNamedEntryData(methodCallString(object, method), paramString2, dataValueOf(message));
    }

    public static synchronized void logNamedSourceEntryData(String paramString1, String method, String paramString3, String message) {
        iTracer.logSourceNamedEntryData(methodCallString(paramString1, method), paramString3, dataValueOf(message));
    }

    public static synchronized void logNamedTrace(Object object, String method, String paramString2) {
        iTracer.logNamedTrace(methodCallString(object, method), paramString2);
    }

    public static synchronized void logNamedTrace(String paramString1, String method, String paramString3) {
        iTracer.logNamedTrace(methodCallString(paramString1, method), paramString3);
    }

    public static synchronized void logNamedTraceData(Object object, String method, String paramString2, String message) {
        iTracer.logNamedTraceData(methodCallString(object, method), paramString2, dataValueOf(message));
    }

    public static synchronized void logNamedTraceData(String paramString1, String method, String paramString3, String message) {
        iTracer.logNamedTraceData(methodCallString(paramString1, method), paramString3, dataValueOf(message));
    }

    public static synchronized void logNamedUserDebugTrace(Object object, String method, String paramString2, long paramLong, String paramString3) {
        iTracer.logNamedUserDebugTrace(methodCallString(object, method), paramString2, paramLong, paramString3);
    }

    public static synchronized void logNamedUserDebugTrace(String paramString1, String method, String paramString3, long paramLong, String paramString4) {
        iTracer.logNamedUserDebugTrace(methodCallString(paramString1, method), paramString3, paramLong, paramString4);
    }

    public static synchronized void logNamedUserDebugTraceData(Object object, String method, String paramString2, long paramLong, String paramString3, String[] paramArrayOfString) {
        iTracer.logNamedUserDebugTraceData(methodCallString(object, method), paramString2, paramLong, paramString3, nullToEmptyArrayWithNarrowedType(paramArrayOfString));
    }

    public static synchronized void logNamedUserDebugTraceData(String paramString1, String method, String paramString3, long paramLong, String paramString4, String[] paramArrayOfString) {
        iTracer.logNamedUserDebugTraceData(methodCallString(paramString1, method), paramString3, paramLong, paramString4, nullToEmptyArrayWithNarrowedType(paramArrayOfString));
    }

    public static synchronized void logNamedUserTrace(Object object, String method, String paramString2, long paramLong, String paramString3) {
        iTracer.logNamedUserTrace(methodCallString(object, method), paramString2, paramLong, paramString3);
    }

    public static synchronized void logNamedUserTrace(String paramString1, String method, String paramString3, long paramLong, String paramString4) {
        iTracer.logNamedUserTrace(methodCallString(paramString1, method), paramString3, paramLong, paramString4);
    }

    public static synchronized void logNamedUserTraceData(Object object, String method, String paramString2, long paramLong, String paramString3, String[] paramArrayOfString) {
        iTracer.logNamedUserTraceData(methodCallString(object, method), paramString2, paramLong, paramString3, nullToEmptyArrayWithNarrowedType(paramArrayOfString));
    }

    public static synchronized void logNamedUserTraceData(String paramString1, String method, String paramString3, long paramLong, String paramString4, String[] paramArrayOfString) {
        iTracer.logNamedUserTraceData(methodCallString(paramString1, method), paramString3, paramLong, paramString4, nullToEmptyArrayWithNarrowedType(paramArrayOfString));
    }

    public static synchronized void logSourceNamedDebugEntry(Object object, String method, String paramString2) {
        iTracer.logSourceNamedDebugEntry(methodCallString(object, method), paramString2);
    }

    public static synchronized void logSourceNamedDebugEntry(String paramString1, String method, String paramString3) {
        iTracer.logSourceNamedDebugEntry(methodCallString(paramString1, method), paramString3);
    }

    public static synchronized void logSourceNamedEntry(Object object, String method, String paramString2) {
        iTracer.logSourceNamedEntry(methodCallString(object, method), paramString2);
    }

    public static synchronized void logSourceNamedEntry(String paramString1, String method, String paramString3) {
        iTracer.logSourceNamedEntry(methodCallString(paramString1, method), paramString3);
    }

    public static synchronized void logStackTrace(Object object, String method, Throwable paramThrowable) {
        iTracer.logStackTrace(methodCallString(object, method), paramThrowable);
    }

    public static synchronized void logStackTrace(String paramString1, String method, Throwable paramThrowable) {
        iTracer.logStackTrace(methodCallString(paramString1, method), paramThrowable);
    }

    public static synchronized void logNamedSpecialEntry(Object object, String method) {
        iTracer.logNamedSpecialEntry(methodCallString(object, method));
    }

    public static synchronized void logNamedSpecialEntry(String paramString1, String method) {
        iTracer.logNamedSpecialEntry(methodCallString(paramString1, method));
    }

    public static synchronized void logNamedSpecialEntryData(Object object, String method, String message) {
        iTracer.logNamedSpecialEntryData(methodCallString(object, method), dataValueOf(message));
    }

    public static synchronized void logNamedSpecialEntryData(String paramString1, String method, String message) {
        iTracer.logNamedSpecialEntryData(methodCallString(paramString1, method), dataValueOf(message));
    }

    public static synchronized void logNamedSpecialExit(Object object, String method) {
        iTracer.logNamedSpecialExit(methodCallString(object, method));
    }

    public static synchronized void logNamedSpecialExit(String paramString1, String method) {
        iTracer.logNamedSpecialExit(methodCallString(paramString1, method));
    }

    public static synchronized void logNamedSpecialExitData(Object object, String method, String message) {
        iTracer.logNamedSpecialExitData(methodCallString(object, method), dataValueOf(message));
    }

    public static synchronized void logNamedSpecialExitData(String paramString1, String method, String message) {
        iTracer.logNamedSpecialExitData(methodCallString(paramString1, method), dataValueOf(message));
    }

    public static synchronized void logNamedSpecialTrace(Object object, String method, String paramString2) {
        iTracer.logNamedSpecialTrace(methodCallString(object, method), paramString2);
    }

    public static synchronized void logNamedSpecialTrace(String paramString1, String method, String paramString3) {
        iTracer.logNamedSpecialTrace(methodCallString(paramString1, method), paramString3);
    }

    public static synchronized void logNamedSpecialTraceData(Object object, String method, String paramString2, String message) {
        iTracer.logNamedSpecialTraceData(methodCallString(object, method), paramString2, dataValueOf(message));
    }

    public static synchronized void logNamedSpecialTraceData(String paramString1, String method, String paramString3, String message) {
        iTracer.logNamedSpecialTraceData(methodCallString(paramString1, method), paramString3, dataValueOf(message));
    }
}