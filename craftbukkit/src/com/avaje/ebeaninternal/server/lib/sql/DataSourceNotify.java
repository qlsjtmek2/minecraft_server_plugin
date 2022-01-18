// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

public interface DataSourceNotify
{
    void notifyDataSourceUp(final String p0);
    
    void notifyDataSourceDown(final String p0);
    
    void notifyWarning(final String p0, final String p1);
}
