// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson.stream;

import java.io.IOException;

public final class MalformedJsonException extends IOException
{
    private static final long serialVersionUID = 1L;
    
    public MalformedJsonException(final String msg) {
        super(msg);
    }
    
    public MalformedJsonException(final String msg, final Throwable throwable) {
        super(msg);
        this.initCause(throwable);
    }
    
    public MalformedJsonException(final Throwable throwable) {
        this.initCause(throwable);
    }
}
