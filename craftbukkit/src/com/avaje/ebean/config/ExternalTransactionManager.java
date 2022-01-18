// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

public interface ExternalTransactionManager
{
    void setTransactionManager(final Object p0);
    
    Object getCurrentTransaction();
}
