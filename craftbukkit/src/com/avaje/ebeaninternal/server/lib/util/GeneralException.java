// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

public class GeneralException extends RuntimeException
{
    private static final long serialVersionUID = 5783084420007103280L;
    
    public GeneralException(final Exception cause) {
        super(cause);
    }
    
    public GeneralException(final String s, final Exception cause) {
        super(s, cause);
    }
    
    public GeneralException(final String s) {
        super(s);
    }
}
