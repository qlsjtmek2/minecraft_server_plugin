// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.autofetch;

import com.avaje.ebean.bean.NodeUsageCollector;
import com.avaje.ebean.bean.ObjectGraphNode;
import java.util.Collection;
import java.util.Iterator;
import com.avaje.ebean.FetchConfig;
import com.avaje.ebean.text.PathProperties;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;
import java.util.ArrayList;
import com.avaje.ebean.meta.MetaAutoFetchStatistic;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import java.util.LinkedHashMap;
import java.util.Map;
import com.avaje.ebean.bean.ObjectGraphOrigin;
import java.io.Serializable;

public class Statistics implements Serializable
{
    private static final long serialVersionUID = -5586783791097230766L;
    private final ObjectGraphOrigin origin;
    private final boolean queryTuningAddVersion;
    private int counter;
    private Map<String, StatisticsQuery> queryStatsMap;
    private Map<String, StatisticsNodeUsage> nodeUsageMap;
    private final String monitor;
    
    public Statistics(final ObjectGraphOrigin origin, final boolean queryTuningAddVersion) {
        this.queryStatsMap = new LinkedHashMap<String, StatisticsQuery>();
        this.nodeUsageMap = new LinkedHashMap<String, StatisticsNodeUsage>();
        this.monitor = new String();
        this.origin = origin;
        this.queryTuningAddVersion = queryTuningAddVersion;
    }
    
    public ObjectGraphOrigin getOrigin() {
        return this.origin;
    }
    
    public TunedQueryInfo createTunedFetch(final OrmQueryDetail newFetchDetail) {
        synchronized (this.monitor) {
            return new TunedQueryInfo(this.origin, newFetchDetail, this.counter);
        }
    }
    
    public MetaAutoFetchStatistic createPublicMeta() {
        synchronized (this.monitor) {
            final StatisticsQuery[] sourceQueryStats = this.queryStatsMap.values().toArray(new StatisticsQuery[this.queryStatsMap.size()]);
            final List<MetaAutoFetchStatistic.QueryStats> destQueryStats = new ArrayList<MetaAutoFetchStatistic.QueryStats>(sourceQueryStats.length);
            for (int i = 0; i < sourceQueryStats.length; ++i) {
                destQueryStats.add(sourceQueryStats[i].createPublicMeta());
            }
            final StatisticsNodeUsage[] sourceNodeUsage = this.nodeUsageMap.values().toArray(new StatisticsNodeUsage[this.nodeUsageMap.size()]);
            final List<MetaAutoFetchStatistic.NodeUsageStats> destNodeUsage = new ArrayList<MetaAutoFetchStatistic.NodeUsageStats>(sourceNodeUsage.length);
            for (int j = 0; j < sourceNodeUsage.length; ++j) {
                destNodeUsage.add(sourceNodeUsage[j].createPublicMeta());
            }
            return new MetaAutoFetchStatistic(this.origin, this.counter, destQueryStats, destNodeUsage);
        }
    }
    
    public int getCounter() {
        return this.counter;
    }
    
    public boolean hasUsage() {
        synchronized (this.monitor) {
            return !this.nodeUsageMap.isEmpty();
        }
    }
    
    public OrmQueryDetail buildTunedFetch(final BeanDescriptor<?> rootDesc) {
        synchronized (this.monitor) {
            if (this.nodeUsageMap.isEmpty()) {
                return null;
            }
            final PathProperties pathProps = new PathProperties();
            for (final StatisticsNodeUsage statsNode : this.nodeUsageMap.values()) {
                statsNode.buildTunedFetch(pathProps, rootDesc);
            }
            final OrmQueryDetail detail = new OrmQueryDetail();
            final Collection<PathProperties.Props> pathProperties = pathProps.getPathProps();
            for (final PathProperties.Props props : pathProperties) {
                if (!props.isEmpty()) {
                    detail.addFetch(props.getPath(), props.getPropertiesAsString(), null);
                }
            }
            detail.sortFetchPaths(rootDesc);
            return detail;
        }
    }
    
    public void collectQueryInfo(final ObjectGraphNode node, final int beansLoaded, final int micros) {
        synchronized (this.monitor) {
            String key = node.getPath();
            if (key == null) {
                key = "";
                ++this.counter;
            }
            StatisticsQuery stats = this.queryStatsMap.get(key);
            if (stats == null) {
                stats = new StatisticsQuery(key);
                this.queryStatsMap.put(key, stats);
            }
            stats.add(beansLoaded, micros);
        }
    }
    
    public void collectUsageInfo(final NodeUsageCollector profile) {
        if (!profile.isEmpty()) {
            final ObjectGraphNode node = profile.getNode();
            final StatisticsNodeUsage nodeStats = this.getNodeStats(node.getPath());
            nodeStats.publish(profile);
        }
    }
    
    private StatisticsNodeUsage getNodeStats(final String path) {
        synchronized (this.monitor) {
            StatisticsNodeUsage nodeStats = this.nodeUsageMap.get(path);
            if (nodeStats == null) {
                nodeStats = new StatisticsNodeUsage(path, this.queryTuningAddVersion);
                this.nodeUsageMap.put(path, nodeStats);
            }
            return nodeStats;
        }
    }
    
    public String getUsageDebug() {
        synchronized (this.monitor) {
            final StringBuilder sb = new StringBuilder();
            sb.append("root[").append(this.origin.getBeanType()).append("] ");
            for (final StatisticsNodeUsage node : this.nodeUsageMap.values()) {
                sb.append(node.toString()).append("\n");
            }
            return sb.toString();
        }
    }
    
    public String getQueryStatDebug() {
        synchronized (this.monitor) {
            final StringBuilder sb = new StringBuilder();
            for (final StatisticsQuery queryStat : this.queryStatsMap.values()) {
                sb.append(queryStat.toString()).append("\n");
            }
            return sb.toString();
        }
    }
    
    public String toString() {
        synchronized (this.monitor) {
            return this.getUsageDebug();
        }
    }
}
