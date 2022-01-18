// 
// Decompiled by Procyon v0.5.30
// 

package org.apache.commons.lang.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public interface Nestable
{
    Throwable getCause();
    
    String getMessage();
    
    String getMessage(final int p0);
    
    String[] getMessages();
    
    Throwable getThrowable(final int p0);
    
    int getThrowableCount();
    
    Throwable[] getThrowables();
    
    int indexOfThrowable(final Class p0);
    
    int indexOfThrowable(final Class p0, final int p1);
    
    void printStackTrace(final PrintWriter p0);
    
    void printStackTrace(final PrintStream p0);
    
    void printPartialStackTrace(final PrintWriter p0);
}
