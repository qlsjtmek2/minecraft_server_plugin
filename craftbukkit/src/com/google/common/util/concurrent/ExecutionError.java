// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;

@Beta
public class ExecutionError extends Error
{
    private static final long serialVersionUID = 0L;
    
    protected ExecutionError() {
    }
    
    protected ExecutionError(final String message) {
        super(message);
    }
    
    public ExecutionError(final String message, final Error cause) {
        super(message, cause);
    }
    
    public ExecutionError(final Error cause) {
        super(cause);
    }
}
