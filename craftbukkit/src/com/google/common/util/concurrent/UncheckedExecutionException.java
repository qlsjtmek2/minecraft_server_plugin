// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;

@Beta
public class UncheckedExecutionException extends RuntimeException
{
    private static final long serialVersionUID = 0L;
    
    protected UncheckedExecutionException() {
    }
    
    protected UncheckedExecutionException(final String message) {
        super(message);
    }
    
    public UncheckedExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public UncheckedExecutionException(final Throwable cause) {
        super(cause);
    }
}
