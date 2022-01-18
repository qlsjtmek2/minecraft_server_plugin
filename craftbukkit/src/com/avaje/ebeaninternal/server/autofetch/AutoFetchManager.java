// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.autofetch;

import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.config.AutofetchMode;
import java.util.Iterator;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebean.bean.NodeUsageListener;

public interface AutoFetchManager extends NodeUsageListener
{
    void setOwner(final SpiEbeanServer p0, final ServerConfig p1);
    
    void clearQueryStatistics();
    
    int clearTunedQueryInfo();
    
    int clearProfilingInfo();
    
    void shutdown();
    
    TunedQueryInfo getTunedQueryInfo(final String p0);
    
    Statistics getStatistics(final String p0);
    
    Iterator<TunedQueryInfo> iterateTunedQueryInfo();
    
    Iterator<Statistics> iterateStatistics();
    
    boolean isProfiling();
    
    void setProfiling(final boolean p0);
    
    boolean isQueryTuning();
    
    void setQueryTuning(final boolean p0);
    
    AutofetchMode getMode();
    
    void setMode(final AutofetchMode p0);
    
    double getProfilingRate();
    
    void setProfilingRate(final double p0);
    
    int getProfilingBase();
    
    void setProfilingBase(final int p0);
    
    int getProfilingMin();
    
    void setProfilingMin(final int p0);
    
    String collectUsageViaGC(final long p0);
    
    String updateTunedQueryInfo();
    
    boolean tuneQuery(final SpiQuery<?> p0);
    
    void collectQueryInfo(final ObjectGraphNode p0, final int p1, final int p2);
    
    int getTotalTunedQueryCount();
    
    int getTotalTunedQuerySize();
    
    int getTotalProfileSize();
}
