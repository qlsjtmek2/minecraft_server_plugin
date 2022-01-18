// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

public class DataSourceException extends RuntimeException
{
    static final long serialVersionUID = 7061559938704539844L;
    
    public DataSourceException(final Exception cause) {
        super(cause);
    }
    
    public DataSourceException(final String s, final Exception cause) {
        super(s, cause);
    }
    
    public DataSourceException(final String s) {
        super(s);
    }
}
