// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.meta;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class MetaQueryStatistic implements Serializable
{
    private static final long serialVersionUID = -8746524372894472583L;
    boolean autofetchTuned;
    String beanType;
    int origQueryPlanHash;
    int finalQueryPlanHash;
    String sql;
    int executionCount;
    int totalLoadedBeans;
    int totalTimeMicros;
    long collectionStart;
    long lastQueryTime;
    int avgTimeMicros;
    int avgLoadedBeans;
    
    public MetaQueryStatistic() {
    }
    
    public MetaQueryStatistic(final boolean autofetchTuned, final String beanType, final int plan, final String sql, final int executionCount, final int totalLoadedBeans, final int totalTimeMicros, final long collectionStart, final long lastQueryTime) {
        this.autofetchTuned = autofetchTuned;
        this.beanType = beanType;
        this.finalQueryPlanHash = plan;
        this.sql = sql;
        this.executionCount = executionCount;
        this.totalLoadedBeans = totalLoadedBeans;
        this.totalTimeMicros = totalTimeMicros;
        this.collectionStart = collectionStart;
        this.lastQueryTime = lastQueryTime;
        this.avgTimeMicros = ((executionCount == 0) ? 0 : (totalTimeMicros / executionCount));
        this.avgLoadedBeans = ((executionCount == 0) ? 0 : (totalLoadedBeans / executionCount));
    }
    
    public String toString() {
        return "type=" + this.beanType + " tuned:" + this.autofetchTuned + " origHash=" + this.origQueryPlanHash + " count=" + this.executionCount + " avgMicros=" + this.getAvgTimeMicros();
    }
    
    public boolean isAutofetchTuned() {
        return this.autofetchTuned;
    }
    
    public int getOrigQueryPlanHash() {
        return this.origQueryPlanHash;
    }
    
    public int getFinalQueryPlanHash() {
        return this.finalQueryPlanHash;
    }
    
    public String getBeanType() {
        return this.beanType;
    }
    
    public String getSql() {
        return this.sql;
    }
    
    public int getExecutionCount() {
        return this.executionCount;
    }
    
    public int getTotalLoadedBeans() {
        return this.totalLoadedBeans;
    }
    
    public int getTotalTimeMicros() {
        return this.totalTimeMicros;
    }
    
    public long getCollectionStart() {
        return this.collectionStart;
    }
    
    public long getLastQueryTime() {
        return this.lastQueryTime;
    }
    
    public int getAvgTimeMicros() {
        return this.avgTimeMicros;
    }
    
    public int getAvgLoadedBeans() {
        return this.avgLoadedBeans;
    }
}
