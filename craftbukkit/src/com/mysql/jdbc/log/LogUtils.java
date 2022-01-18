// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.log;

import com.mysql.jdbc.Util;
import com.mysql.jdbc.profiler.ProfilerEvent;

public class LogUtils
{
    public static final String CALLER_INFORMATION_NOT_AVAILABLE = "Caller information not available";
    private static final String LINE_SEPARATOR;
    private static final int LINE_SEPARATOR_LENGTH;
    
    public static Object expandProfilerEventIfNecessary(final Object possibleProfilerEvent) {
        if (possibleProfilerEvent instanceof ProfilerEvent) {
            final StringBuffer msgBuf = new StringBuffer();
            final ProfilerEvent evt = (ProfilerEvent)possibleProfilerEvent;
            Throwable locationException = evt.getEventCreationPoint();
            if (locationException == null) {
                locationException = new Throwable();
            }
            msgBuf.append("Profiler Event: [");
            boolean appendLocationInfo = false;
            switch (evt.getEventType()) {
                case 4: {
                    msgBuf.append("EXECUTE");
                    break;
                }
                case 5: {
                    msgBuf.append("FETCH");
                    break;
                }
                case 1: {
                    msgBuf.append("CONSTRUCT");
                    break;
                }
                case 2: {
                    msgBuf.append("PREPARE");
                    break;
                }
                case 3: {
                    msgBuf.append("QUERY");
                    break;
                }
                case 0: {
                    msgBuf.append("WARN");
                    appendLocationInfo = true;
                    break;
                }
                case 6: {
                    msgBuf.append("SLOW QUERY");
                    appendLocationInfo = false;
                    break;
                }
                default: {
                    msgBuf.append("UNKNOWN");
                    break;
                }
            }
            msgBuf.append("] ");
            msgBuf.append(findCallingClassAndMethod(locationException));
            msgBuf.append(" duration: ");
            msgBuf.append(evt.getEventDuration());
            msgBuf.append(" ");
            msgBuf.append(evt.getDurationUnits());
            msgBuf.append(", connection-id: ");
            msgBuf.append(evt.getConnectionId());
            msgBuf.append(", statement-id: ");
            msgBuf.append(evt.getStatementId());
            msgBuf.append(", resultset-id: ");
            msgBuf.append(evt.getResultSetId());
            final String evtMessage = evt.getMessage();
            if (evtMessage != null) {
                msgBuf.append(", message: ");
                msgBuf.append(evtMessage);
            }
            if (appendLocationInfo) {
                msgBuf.append("\n\nFull stack trace of location where event occurred:\n\n");
                msgBuf.append(Util.stackTraceToString(locationException));
                msgBuf.append("\n");
            }
            return msgBuf;
        }
        return possibleProfilerEvent;
    }
    
    public static String findCallingClassAndMethod(final Throwable t) {
        final String stackTraceAsString = Util.stackTraceToString(t);
        String callingClassAndMethod = "Caller information not available";
        final int endInternalMethods = stackTraceAsString.lastIndexOf("com.mysql.jdbc");
        if (endInternalMethods != -1) {
            int endOfLine = -1;
            final int compliancePackage = stackTraceAsString.indexOf("com.mysql.jdbc.compliance", endInternalMethods);
            if (compliancePackage != -1) {
                endOfLine = compliancePackage - LogUtils.LINE_SEPARATOR_LENGTH;
            }
            else {
                endOfLine = stackTraceAsString.indexOf(LogUtils.LINE_SEPARATOR, endInternalMethods);
            }
            if (endOfLine != -1) {
                final int nextEndOfLine = stackTraceAsString.indexOf(LogUtils.LINE_SEPARATOR, endOfLine + LogUtils.LINE_SEPARATOR_LENGTH);
                if (nextEndOfLine != -1) {
                    callingClassAndMethod = stackTraceAsString.substring(endOfLine + LogUtils.LINE_SEPARATOR_LENGTH, nextEndOfLine);
                }
                else {
                    callingClassAndMethod = stackTraceAsString.substring(endOfLine + LogUtils.LINE_SEPARATOR_LENGTH);
                }
            }
        }
        if (!callingClassAndMethod.startsWith("\tat ") && !callingClassAndMethod.startsWith("at ")) {
            return "at " + callingClassAndMethod;
        }
        return callingClassAndMethod;
    }
    
    static {
        LINE_SEPARATOR = System.getProperty("line.separator");
        LINE_SEPARATOR_LENGTH = LogUtils.LINE_SEPARATOR.length();
    }
}
