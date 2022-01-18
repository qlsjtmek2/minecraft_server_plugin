// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

public interface DataSourceAlertListener
{
    void dataSourceDown(final String p0);
    
    void dataSourceUp(final String p0);
    
    void warning(final String p0, final String p1);
}
