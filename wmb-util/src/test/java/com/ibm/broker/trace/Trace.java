package com.ibm.broker.trace;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Vector;

public class Trace
{
  private static final String copyright = "Licensed Material - Property of IBM \n5648-C63 (c) Copyright IBM Corp. 2000, 2001 - All Rights Reserved. \nUS Government Users Restricted Rights - Use, duplication or disclosure \nrestricted by GSA ADP Schedule Contract with IBM Corp.";
  private static String componentName = null;
  public static final int NONE = 0;
  public static final int EVENT = 1;
  public static final int USERTRACE = 2;
  public static final int USERDEBUGTRACE = 3;
  public static final int TRACE = 4;
  public static final int DEBUGTRACE = 5;
  public static String[] iNullStrArray = { "NULL" };

//  private static NativeTracer iTracer = new NativeTracer();

  private static NativeTracer iTracer = null; // Todo mock out

  public static boolean userTraceIsOn = false;

  public static boolean userDebugTraceIsOn = false;

  public static boolean traceIsOn = false;

  public static boolean isOn = false;
  public static final boolean devOnly = true;
  public static boolean researchTrace = false;

  private static Vector iListeners = new Vector();

  public static NativeTracer getTrace()
  {
    return iTracer;
  }

  public static final void initTrace(String paramString)
  {
    if (iTracer == null) {
      iTracer = new NativeTracer();
    }

    if (paramString != null)
    {
      iTracer.setTraceFileName(paramString);
      componentName = paramString;
    }
  }

  public static final void addListener(TraceListener paramTraceListener)
  {
    iListeners.addElement(paramTraceListener);
  }

  public static final void informListeners(int paramInt)
  {
    for (int i = 0; i < iListeners.size(); i++)
      ((TraceListener)iListeners.elementAt(i)).traceEvent(paramInt);
  }

  public static synchronized String getTraceFileName()
  {
    if (componentName != null) {
      return iTracer.getTraceFileName();
    }
    return null;
  }

  public static void setLogLevel(int paramInt)
  {
    isOn = paramInt > 0;
    iTracer.setLogLevel(paramInt);

    informListeners(paramInt);
  }

  public static void setLogSize(int paramInt)
  {
    iTracer.setLogSize(paramInt);
  }

  public static void traceTurnedOn()
  {
    boolean bool = isOn;
    isOn = true;

    if (!bool)
      informListeners(5);
  }

  public static void traceTurnedOn(int paramInt)
  {
    if (!researchTrace)
    {
      userTraceIsOn = false;
      userDebugTraceIsOn = false;
      traceIsOn = false;
      isOn = false;
      if (paramInt >= 2)
      {
        userTraceIsOn = true;
        if (paramInt >= 3)
        {
          userDebugTraceIsOn = true;
          if (paramInt >= 4)
          {
            traceIsOn = true;
            if (paramInt == 5) {
              isOn = true;
            }
          }
        }
      }

      informListeners(paramInt);
    }
  }

  public static void traceTurnedOff()
  {
    boolean bool = isOn | userTraceIsOn | userDebugTraceIsOn | traceIsOn;
    isOn = false;
    userTraceIsOn = false;
    userDebugTraceIsOn = false;
    traceIsOn = false;

    if (bool)
      informListeners(0);
  }

  public static boolean isOn()
  {
    return isOn;
  }

  public static String getStackTrace(Throwable paramThrowable)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    PrintWriter localPrintWriter = new PrintWriter(localByteArrayOutputStream);
    paramThrowable.printStackTrace(localPrintWriter);
    localPrintWriter.flush();
    String str = localByteArrayOutputStream.toString();

    char[] arrayOfChar = new char[str.length()];
    str.getChars(0, str.length(), arrayOfChar, 0);

    int i = 1;
    int j = 0;
    for (int k = 0; k < str.length(); k++)
    {
      if (Character.isSpaceChar(arrayOfChar[k]))
      {
        if (i != 0)
          arrayOfChar[(j++)] = arrayOfChar[k];
        i = 0;
      }
      else if (!Character.isWhitespace(arrayOfChar[k]))
      {
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

  public static synchronized void logNamedDebugEntry(Object paramObject, String paramString)
  {
    iTracer.logNamedDebugEntry(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString);
  }
  public static synchronized void logNamedDebugEntry(String paramString1, String paramString2) { iTracer.logNamedDebugEntry(paramString1 + "." + paramString2); } 
  public static synchronized void logNamedDebugEntryData(Object paramObject, String paramString1, String paramString2) {
    iTracer.logNamedDebugEntryData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2 == null ? "NULL" : paramString2);
  }
  public static synchronized void logNamedDebugEntryData(String paramString1, String paramString2, String paramString3) { iTracer.logNamedDebugEntryData(paramString1 + "." + paramString2, paramString3 == null ? "NULL" : paramString3); } 
  public static synchronized void logNamedDebugExit(Object paramObject, String paramString) {
    iTracer.logNamedDebugExit(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString);
  }
  public static synchronized void logNamedDebugExit(String paramString1, String paramString2) { iTracer.logNamedDebugExit(paramString1 + "." + paramString2); } 
  public static synchronized void logNamedDebugExitData(Object paramObject, String paramString1, String paramString2) {
    iTracer.logNamedDebugExitData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2 == null ? "NULL" : paramString2);
  }
  public static synchronized void logNamedDebugExitData(String paramString1, String paramString2, String paramString3) { iTracer.logNamedDebugExitData(paramString1 + "." + paramString2, paramString3 == null ? "NULL" : paramString3); } 
  public static synchronized void logNamedDebugTrace(Object paramObject, String paramString1, String paramString2) {
    iTracer.logNamedDebugTrace(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2);
  }
  public static synchronized void logNamedDebugTrace(String paramString1, String paramString2, String paramString3) { iTracer.logNamedDebugTrace(paramString1 + "." + paramString2, paramString3); } 
  public static synchronized void logNamedDebugTraceData(Object paramObject, String paramString1, String paramString2, String paramString3) {
    iTracer.logNamedDebugTraceData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2, paramString3 == null ? "NULL" : paramString3);
  }
  public static synchronized void logNamedDebugTraceData(String paramString1, String paramString2, String paramString3, String paramString4) { iTracer.logNamedDebugTraceData(paramString1 + "." + paramString2, paramString3, paramString4 == null ? "NULL" : paramString4); } 
  public static synchronized void logNamedEntry(Object paramObject, String paramString) {
    iTracer.logNamedEntry(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString);
  }
  public static synchronized void logNamedEntry(String paramString1, String paramString2) { iTracer.logNamedEntry(paramString1 + "." + paramString2); } 
  public static synchronized void logNamedEntryData(Object paramObject, String paramString1, String paramString2) {
    iTracer.logNamedEntryData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2 == null ? "NULL" : paramString2);
  }
  public static synchronized void logNamedEntryData(String paramString1, String paramString2, String paramString3) { iTracer.logNamedEntryData(paramString1 + "." + paramString2, paramString3 == null ? "NULL" : paramString3); } 
  public static synchronized void logNamedExit(Object paramObject, String paramString) {
    iTracer.logNamedExit(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString);
  }
  public static synchronized void logNamedExit(String paramString1, String paramString2) { iTracer.logNamedExit(paramString1 + "." + paramString2); } 
  public static synchronized void logNamedExitData(Object paramObject, String paramString1, String paramString2) {
    iTracer.logNamedExitData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2 == null ? "NULL" : paramString2);
  }
  public static synchronized void logNamedExitData(String paramString1, String paramString2, String paramString3) { iTracer.logNamedExitData(paramString1 + "." + paramString2, paramString3 == null ? "NULL" : paramString3); } 
  public static synchronized void logNamedSourceDebugEntryData(Object paramObject, String paramString1, String paramString2, String paramString3) {
    iTracer.logSourceNamedDebugEntryData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2, paramString3 == null ? "NULL" : paramString3);
  }
  public static synchronized void logNamedSourceDebugEntryData(String paramString1, String paramString2, String paramString3, String paramString4) { iTracer.logSourceNamedDebugEntryData(paramString1 + "." + paramString2, paramString3, paramString4 == null ? "NULL" : paramString4); } 
  public static synchronized void logNamedSourceEntryData(Object paramObject, String paramString1, String paramString2, String paramString3) {
    iTracer.logSourceNamedEntryData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2, paramString3 == null ? "NULL" : paramString3);
  }
  public static synchronized void logNamedSourceEntryData(String paramString1, String paramString2, String paramString3, String paramString4) { iTracer.logSourceNamedEntryData(paramString1 + "." + paramString2, paramString3, paramString4 == null ? "NULL" : paramString4); } 
  public static synchronized void logNamedTrace(Object paramObject, String paramString1, String paramString2) {
    iTracer.logNamedTrace(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2);
  }
  public static synchronized void logNamedTrace(String paramString1, String paramString2, String paramString3) { iTracer.logNamedTrace(paramString1 + "." + paramString2, paramString3); } 
  public static synchronized void logNamedTraceData(Object paramObject, String paramString1, String paramString2, String paramString3) {
    iTracer.logNamedTraceData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2, paramString3 == null ? "NULL" : paramString3);
  }
  public static synchronized void logNamedTraceData(String paramString1, String paramString2, String paramString3, String paramString4) { iTracer.logNamedTraceData(paramString1 + "." + paramString2, paramString3, paramString4 == null ? "NULL" : paramString4); } 
  public static synchronized void logNamedUserDebugTrace(Object paramObject, String paramString1, String paramString2, long paramLong, String paramString3) {
    iTracer.logNamedUserDebugTrace(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2, paramLong, paramString3);
  }
  public static synchronized void logNamedUserDebugTrace(String paramString1, String paramString2, String paramString3, long paramLong, String paramString4) { iTracer.logNamedUserDebugTrace(paramString1 + "." + paramString2, paramString3, paramLong, paramString4); } 
  public static synchronized void logNamedUserDebugTraceData(Object paramObject, String paramString1, String paramString2, long paramLong, String paramString3, String[] paramArrayOfString) {
    iTracer.logNamedUserDebugTraceData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2, paramLong, paramString3, paramArrayOfString == null ? iNullStrArray : paramArrayOfString);
  }
  public static synchronized void logNamedUserDebugTraceData(String paramString1, String paramString2, String paramString3, long paramLong, String paramString4, String[] paramArrayOfString) { iTracer.logNamedUserDebugTraceData(paramString1 + "." + paramString2, paramString3, paramLong, paramString4, paramArrayOfString == null ? iNullStrArray : paramArrayOfString); }

  public static synchronized void logNamedUserTrace(Object paramObject, String paramString1, String paramString2, long paramLong, String paramString3)
  {
    iTracer.logNamedUserTrace(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2, paramLong, paramString3);
  }

  public static synchronized void logNamedUserTrace(String paramString1, String paramString2, String paramString3, long paramLong, String paramString4) {
    iTracer.logNamedUserTrace(paramString1 + "." + paramString2, paramString3, paramLong, paramString4);
  }
  public static synchronized void logNamedUserTraceData(Object paramObject, String paramString1, String paramString2, long paramLong, String paramString3, String[] paramArrayOfString) { iTracer.logNamedUserTraceData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2, paramLong, paramString3, paramArrayOfString == null ? iNullStrArray : paramArrayOfString); } 
  public static synchronized void logNamedUserTraceData(String paramString1, String paramString2, String paramString3, long paramLong, String paramString4, String[] paramArrayOfString) {
    iTracer.logNamedUserTraceData(paramString1 + "." + paramString2, paramString3, paramLong, paramString4, paramArrayOfString == null ? iNullStrArray : paramArrayOfString);
  }
  public static synchronized void logSourceNamedDebugEntry(Object paramObject, String paramString1, String paramString2) { iTracer.logSourceNamedDebugEntry(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2); } 
  public static synchronized void logSourceNamedDebugEntry(String paramString1, String paramString2, String paramString3) {
    iTracer.logSourceNamedDebugEntry(paramString1 + "." + paramString2, paramString3);
  }

  public static synchronized void logSourceNamedEntry(Object paramObject, String paramString1, String paramString2) {
    iTracer.logSourceNamedEntry(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2);
  }

  public static synchronized void logSourceNamedEntry(String paramString1, String paramString2, String paramString3) {
    iTracer.logSourceNamedEntry(paramString1 + "." + paramString2, paramString3);
  }
  public static synchronized void logStackTrace(Object paramObject, String paramString, Throwable paramThrowable) {
    iTracer.logStackTrace(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString, paramThrowable);
  }
  public static synchronized void logStackTrace(String paramString1, String paramString2, Throwable paramThrowable) { iTracer.logStackTrace(paramString1 + "." + paramString2, paramThrowable); }

  public static synchronized void logNamedSpecialEntry(Object paramObject, String paramString)
  {
    iTracer.logNamedSpecialEntry(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString);
  }
  public static synchronized void logNamedSpecialEntry(String paramString1, String paramString2) { iTracer.logNamedSpecialEntry(paramString1 + "." + paramString2); } 
  public static synchronized void logNamedSpecialEntryData(Object paramObject, String paramString1, String paramString2) {
    iTracer.logNamedSpecialEntryData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2 == null ? "NULL" : paramString2);
  }
  public static synchronized void logNamedSpecialEntryData(String paramString1, String paramString2, String paramString3) { iTracer.logNamedSpecialEntryData(paramString1 + "." + paramString2, paramString3 == null ? "NULL" : paramString3); }

  public static synchronized void logNamedSpecialExit(Object paramObject, String paramString)
  {
    iTracer.logNamedSpecialExit(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString);
  }
  public static synchronized void logNamedSpecialExit(String paramString1, String paramString2) { iTracer.logNamedSpecialExit(paramString1 + "." + paramString2); } 
  public static synchronized void logNamedSpecialExitData(Object paramObject, String paramString1, String paramString2) {
    iTracer.logNamedSpecialExitData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2 == null ? "NULL" : paramString2);
  }
  public static synchronized void logNamedSpecialExitData(String paramString1, String paramString2, String paramString3) { iTracer.logNamedSpecialExitData(paramString1 + "." + paramString2, paramString3 == null ? "NULL" : paramString3); }

  public static synchronized void logNamedSpecialTrace(Object paramObject, String paramString1, String paramString2)
  {
    iTracer.logNamedSpecialTrace(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2);
  }
  public static synchronized void logNamedSpecialTrace(String paramString1, String paramString2, String paramString3) { iTracer.logNamedSpecialTrace(paramString1 + "." + paramString2, paramString3); } 
  public static synchronized void logNamedSpecialTraceData(Object paramObject, String paramString1, String paramString2, String paramString3) {
    iTracer.logNamedSpecialTraceData(paramObject.getClass().getName() + "@" + Integer.toString(paramObject.hashCode(), 16) + "." + paramString1, paramString2, paramString3 == null ? "NULL" : paramString3);
  }
  public static synchronized void logNamedSpecialTraceData(String paramString1, String paramString2, String paramString3, String paramString4) { iTracer.logNamedSpecialTraceData(paramString1 + "." + paramString2, paramString3, paramString4 == null ? "NULL" : paramString4);
  }
}