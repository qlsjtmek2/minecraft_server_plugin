// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.internal;

import java.io.PrintStream;

public final class Log
{
    public static final boolean DEBUG;
    public static final boolean TRACE;
    private static PrintStream output;
    
    public static PrintStream getOutput() {
        return Log.output;
    }
    
    public static void setOutput(final PrintStream out) {
        assert out != null;
        Log.output = out;
    }
    
    private static void print(final Object message) {
        if (message instanceof Throwable) {
            ((Throwable)message).printStackTrace(Log.output);
        }
        else if (message.getClass().isArray()) {
            final Object[] array = (Object[])message;
            for (int i = 0; i < array.length; ++i) {
                Log.output.print(array[i]);
                if (i + 1 < array.length) {
                    Log.output.print(",");
                }
            }
        }
        else {
            Log.output.print(message);
        }
    }
    
    private static void log(final Level level, final Object[] messages) {
        synchronized (Log.output) {
            Log.output.format("[%s] ", level);
            for (final Object message : messages) {
                print(message);
            }
            Log.output.println();
            Log.output.flush();
        }
    }
    
    public static void trace(final Object... messages) {
        if (Log.TRACE) {
            log(Level.TRACE, messages);
        }
    }
    
    public static void debug(final Object... messages) {
        if (Log.TRACE || Log.DEBUG) {
            log(Level.DEBUG, messages);
        }
    }
    
    public static void warn(final Object... messages) {
        log(Level.WARN, messages);
    }
    
    public static void error(final Object... messages) {
        log(Level.ERROR, messages);
    }
    
    static {
        DEBUG = Boolean.getBoolean(Log.class.getName() + ".debug");
        TRACE = Boolean.getBoolean(Log.class.getName() + ".trace");
        Log.output = System.err;
    }
    
    public enum Level
    {
        TRACE, 
        DEBUG, 
        INFO, 
        WARN, 
        ERROR;
    }
}
