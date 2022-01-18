// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

public class UncheckedTimeoutException extends RuntimeException
{
    private static final long serialVersionUID = 0L;
    
    public UncheckedTimeoutException() {
    }
    
    public UncheckedTimeoutException(final String message) {
        super(message);
    }
    
    public UncheckedTimeoutException(final Throwable cause) {
        super(cause);
    }
    
    public UncheckedTimeoutException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
