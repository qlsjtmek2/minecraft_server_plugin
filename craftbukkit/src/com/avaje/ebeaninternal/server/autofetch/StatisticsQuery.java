// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.autofetch;

import com.avaje.ebean.meta.MetaAutoFetchStatistic;
import java.io.Serializable;

public class StatisticsQuery implements Serializable
{
    private static final long serialVersionUID = -1133958958072778811L;
    private final String path;
    private int exeCount;
    private int totalBeanLoaded;
    private int totalMicros;
    
    public StatisticsQuery(final String path) {
        this.path = path;
    }
    
    public MetaAutoFetchStatistic.QueryStats createPublicMeta() {
        return new MetaAutoFetchStatistic.QueryStats(this.path, this.exeCount, this.totalBeanLoaded, this.totalMicros);
    }
    
    public void add(final int beansLoaded, final int micros) {
        ++this.exeCount;
        this.totalBeanLoaded += beansLoaded;
        this.totalMicros += micros;
    }
    
    public String toString() {
        final long avgMicros = (this.exeCount == 0) ? 0L : (this.totalMicros / this.exeCount);
        return "queryExe path[" + this.path + "] count[" + this.exeCount + "] totalBeansLoaded[" + this.totalBeanLoaded + "] avgMicros[" + avgMicros + "] totalMicros[" + this.totalMicros + "]";
    }
}
