// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

public class CreateObjectException extends RuntimeException
{
    static final long serialVersionUID = 7061559938704539736L;
    
    public CreateObjectException(final Exception cause) {
        super(cause);
    }
    
    public CreateObjectException(final String s, final Exception cause) {
        super(s, cause);
    }
    
    public CreateObjectException(final String s) {
        super(s);
    }
}
