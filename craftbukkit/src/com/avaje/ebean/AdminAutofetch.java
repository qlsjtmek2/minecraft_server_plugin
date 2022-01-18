// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

public interface AdminAutofetch
{
    boolean isProfiling();
    
    void setProfiling(final boolean p0);
    
    boolean isQueryTuning();
    
    void setQueryTuning(final boolean p0);
    
    double getProfilingRate();
    
    void setProfilingRate(final double p0);
    
    int getProfilingBase();
    
    void setProfilingBase(final int p0);
    
    int getProfilingMin();
    
    void setProfilingMin(final int p0);
    
    String collectUsageViaGC();
    
    String updateTunedQueryInfo();
    
    int clearTunedQueryInfo();
    
    int clearProfilingInfo();
    
    void clearQueryStatistics();
    
    int getTotalTunedQueryCount();
    
    int getTotalTunedQuerySize();
    
    int getTotalProfileSize();
}
