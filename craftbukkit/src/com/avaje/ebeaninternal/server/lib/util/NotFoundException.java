// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

public class NotFoundException extends RuntimeException
{
    static final long serialVersionUID = 7061559938704539845L;
    
    public NotFoundException(final Exception cause) {
        super(cause);
    }
    
    public NotFoundException(final String s, final Exception cause) {
        super(s, cause);
    }
    
    public NotFoundException(final String s) {
        super(s);
    }
}
