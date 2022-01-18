// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

public interface SqlUpdate
{
    int execute();
    
    boolean isAutoTableMod();
    
    SqlUpdate setAutoTableMod(final boolean p0);
    
    String getLabel();
    
    SqlUpdate setLabel(final String p0);
    
    String getSql();
    
    int getTimeout();
    
    SqlUpdate setTimeout(final int p0);
    
    SqlUpdate setParameter(final int p0, final Object p1);
    
    SqlUpdate setNull(final int p0, final int p1);
    
    SqlUpdate setNullParameter(final int p0, final int p1);
    
    SqlUpdate setParameter(final String p0, final Object p1);
    
    SqlUpdate setNull(final String p0, final int p1);
    
    SqlUpdate setNullParameter(final String p0, final int p1);
}
