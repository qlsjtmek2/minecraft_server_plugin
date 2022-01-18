// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

public class InvalidDataException extends RuntimeException
{
    static final long serialVersionUID = 7061559938704539846L;
    
    public InvalidDataException(final Exception cause) {
        super(cause);
    }
    
    public InvalidDataException(final String s, final Exception cause) {
        super(s, cause);
    }
    
    public InvalidDataException(final String s) {
        super(s);
    }
}
