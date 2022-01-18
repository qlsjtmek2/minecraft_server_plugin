// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public class ComputationException extends RuntimeException
{
    private static final long serialVersionUID = 0L;
    
    public ComputationException(final Throwable cause) {
        super(cause);
    }
}
