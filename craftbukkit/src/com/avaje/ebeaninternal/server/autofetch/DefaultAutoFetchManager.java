// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.autofetch;

import com.avaje.ebean.bean.NodeUsageCollector;
import com.avaje.ebean.bean.CallStack;
import com.avaje.ebean.bean.ObjectGraphNode;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.bean.ObjectGraphOrigin;
import com.avaje.ebeaninternal.api.ClassUtil;
import java.util.Iterator;
import java.util.logging.Level;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import com.avaje.ebean.config.AutofetchConfig;
import com.avaje.ebean.config.ServerConfig;
import java.util.concurrent.ConcurrentHashMap;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebean.config.AutofetchMode;
import java.util.Map;
import java.io.Serializable;

public class DefaultAutoFetchManager implements AutoFetchManager, Serializable
{
    private static final long serialVersionUID = -6826119882781771722L;
    private final String statisticsMonitor;
    private final String fileName;
    private Map<String, Statistics> statisticsMap;
    private Map<String, TunedQueryInfo> tunedQueryInfoMap;
    private transient long defaultGarbageCollectionWait;
    private transient int tunedQueryCount;
    private transient double profilingRate;
    private transient int profilingBase;
    private transient int profilingMin;
    private transient boolean profiling;
    private transient boolean queryTuning;
    private transient boolean queryTuningAddVersion;
    private transient AutofetchMode mode;
    private transient boolean useFileLogging;
    private transient SpiEbeanServer server;
    private transient DefaultAutoFetchManagerLogging logging;
    
    public DefaultAutoFetchManager(final String fileName) {
        this.statisticsMonitor = new String();
        this.statisticsMap = new ConcurrentHashMap<String, Statistics>();
        this.tunedQueryInfoMap = new ConcurrentHashMap<String, TunedQueryInfo>();
        this.defaultGarbageCollectionWait = 100L;
        this.profilingRate = 0.1;
        this.profilingBase = 10;
        this.profilingMin = 1;
        this.fileName = fileName;
    }
    
    public void setOwner(final SpiEbeanServer server, final ServerConfig serverConfig) {
        this.server = server;
        this.logging = new DefaultAutoFetchManagerLogging(serverConfig, this);
        final AutofetchConfig autofetchConfig = serverConfig.getAutofetchConfig();
        this.useFileLogging = autofetchConfig.isUseFileLogging();
        this.queryTuning = autofetchConfig.isQueryTuning();
        this.queryTuningAddVersion = autofetchConfig.isQueryTuningAddVersion();
        this.profiling = autofetchConfig.isProfiling();
        this.profilingMin = autofetchConfig.getProfilingMin();
        this.profilingBase = autofetchConfig.getProfilingBase();
        this.setProfilingRate(autofetchConfig.getProfilingRate());
        this.defaultGarbageCollectionWait = autofetchConfig.getGarbageCollectionWait();
        this.mode = autofetchConfig.getMode();
        if (this.profiling || this.queryTuning) {
            final String msg = "AutoFetch queryTuning[" + this.queryTuning + "] profiling[" + this.profiling + "] mode[" + this.mode + "]  profiling rate[" + this.profilingRate + "] min[" + this.profilingMin + "] base[" + this.profilingBase + "]";
            this.logging.logToJavaLogger(msg);
        }
    }
    
    public void clearQueryStatistics() {
        this.server.clearQueryStatistics();
    }
    
    public int getTotalTunedQueryCount() {
        return this.tunedQueryCount;
    }
    
    public int getTotalTunedQuerySize() {
        return this.tunedQueryInfoMap.size();
    }
    
    public int getTotalProfileSize() {
        return this.statisticsMap.size();
    }
    
    public int clearTunedQueryInfo() {
        this.tunedQueryCount = 0;
        final int size = this.tunedQueryInfoMap.size();
        this.tunedQueryInfoMap.clear();
        return size;
    }
    
    public int clearProfilingInfo() {
        final int size = this.statisticsMap.size();
        this.statisticsMap.clear();
        return size;
    }
    
    public void serialize() {
        final File autoFetchFile = new File(this.fileName);
        try {
            final FileOutputStream fout = new FileOutputStream(autoFetchFile);
            final ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(this);
            oout.flush();
            oout.close();
        }
        catch (Exception e) {
            final String msg = "Error serializing autofetch file";
            this.logging.logError(Level.SEVERE, msg, e);
        }
    }
    
    public TunedQueryInfo getTunedQueryInfo(final String originKey) {
        return this.tunedQueryInfoMap.get(originKey);
    }
    
    public Statistics getStatistics(final String originKey) {
        return this.statisticsMap.get(originKey);
    }
    
    public Iterator<TunedQueryInfo> iterateTunedQueryInfo() {
        return this.tunedQueryInfoMap.values().iterator();
    }
    
    public Iterator<Statistics> iterateStatistics() {
        return this.statisticsMap.values().iterator();
    }
    
    public boolean isProfiling() {
        return this.profiling;
    }
    
    public void setProfiling(final boolean profiling) {
        this.profiling = profiling;
    }
    
    public boolean isQueryTuning() {
        return this.queryTuning;
    }
    
    public void setQueryTuning(final boolean queryTuning) {
        this.queryTuning = queryTuning;
    }
    
    public double getProfilingRate() {
        return this.profilingRate;
    }
    
    public AutofetchMode getMode() {
        return this.mode;
    }
    
    public void setMode(final AutofetchMode mode) {
        this.mode = mode;
    }
    
    public void setProfilingRate(double rate) {
        if (rate < 0.0) {
            rate = 0.0;
        }
        else if (rate > 1.0) {
            rate = 1.0;
        }
        this.profilingRate = rate;
    }
    
    public int getProfilingBase() {
        return this.profilingBase;
    }
    
    public void setProfilingBase(final int profilingBase) {
        this.profilingBase = profilingBase;
    }
    
    public int getProfilingMin() {
        return this.profilingMin;
    }
    
    public void setProfilingMin(final int profilingMin) {
        this.profilingMin = profilingMin;
    }
    
    public void shutdown() {
        this.collectUsageViaGC(-1L);
        if (this.useFileLogging) {
            this.serialize();
        }
    }
    
    public String collectUsageViaGC(long waitMillis) {
        System.gc();
        try {
            if (waitMillis < 0L) {
                waitMillis = this.defaultGarbageCollectionWait;
            }
            Thread.sleep(waitMillis);
        }
        catch (InterruptedException e) {
            final String msg = "Error while sleeping after System.gc() request.";
            this.logging.logError(Level.SEVERE, msg, e);
            return msg;
        }
        return this.updateTunedQueryInfo();
    }
    
    public String updateTunedQueryInfo() {
        if (!this.profiling) {
            return "Not profiling";
        }
        synchronized (this.statisticsMonitor) {
            final Counters counters = new Counters();
            for (final Statistics queryPointStatistics : this.statisticsMap.values()) {
                if (!queryPointStatistics.hasUsage()) {
                    counters.incrementNoUsage();
                }
                else {
                    this.updateTunedQueryFromUsage(counters, queryPointStatistics);
                }
            }
            final String summaryInfo = counters.toString();
            if (counters.isInteresting()) {
                this.logging.logSummary(summaryInfo);
            }
            return summaryInfo;
        }
    }
    
    private void updateTunedQueryFromUsage(final Counters counters, final Statistics statistics) {
        final ObjectGraphOrigin queryPoint = statistics.getOrigin();
        final String beanType = queryPoint.getBeanType();
        try {
            final Class<?> beanClass = ClassUtil.forName(beanType, this.getClass());
            final BeanDescriptor<?> beanDescriptor = this.server.getBeanDescriptor(beanClass);
            if (beanDescriptor != null) {
                final OrmQueryDetail newFetchDetail = statistics.buildTunedFetch(beanDescriptor);
                TunedQueryInfo currentFetch = this.tunedQueryInfoMap.get(queryPoint.getKey());
                if (currentFetch == null) {
                    counters.incrementNew();
                    currentFetch = statistics.createTunedFetch(newFetchDetail);
                    this.logging.logNew(currentFetch);
                    this.tunedQueryInfoMap.put(queryPoint.getKey(), currentFetch);
                }
                else if (!currentFetch.isSame(newFetchDetail)) {
                    counters.incrementModified();
                    this.logging.logChanged(currentFetch, newFetchDetail);
                    currentFetch.setTunedDetail(newFetchDetail);
                }
                else {
                    counters.incrementUnchanged();
                }
                currentFetch.setProfileCount(statistics.getCounter());
            }
        }
        catch (ClassNotFoundException e) {
            final String msg = e.toString() + " updating autoFetch tuned query for " + beanType + ". It isLikely this bean has been renamed or moved";
            this.logging.logError(Level.INFO, msg, null);
            this.statisticsMap.remove(statistics.getOrigin().getKey());
        }
    }
    
    private boolean useAutoFetch(final SpiQuery<?> query) {
        if (query.isLoadBeanCache() || query.isSharedInstance()) {
            return false;
        }
        final Boolean autoFetch = query.isAutofetch();
        if (autoFetch != null) {
            return autoFetch;
        }
        switch (this.mode) {
            case DEFAULT_ON: {
                return true;
            }
            case DEFAULT_OFF: {
                return false;
            }
            case DEFAULT_ONIFEMPTY: {
                return query.isDetailEmpty();
            }
            default: {
                throw new PersistenceException("Invalid autoFetchMode " + this.mode);
            }
        }
    }
    
    public boolean tuneQuery(final SpiQuery<?> query) {
        if (!this.queryTuning && !this.profiling) {
            return false;
        }
        if (!this.useAutoFetch(query)) {
            return false;
        }
        final ObjectGraphNode parentAutoFetchNode = query.getParentNode();
        if (parentAutoFetchNode != null) {
            query.setAutoFetchManager(this);
            return true;
        }
        final CallStack stack = this.server.createCallStack();
        final ObjectGraphNode origin = query.setOrigin(stack);
        final TunedQueryInfo tunedFetch = this.tunedQueryInfoMap.get(origin.getOriginQueryPoint().getKey());
        final int profileCount = (tunedFetch == null) ? 0 : tunedFetch.getProfileCount();
        if (this.profiling) {
            if (tunedFetch == null) {
                query.setAutoFetchManager(this);
            }
            else if (profileCount < this.profilingBase) {
                query.setAutoFetchManager(this);
            }
            else if (tunedFetch.isPercentageProfile(this.profilingRate)) {
                query.setAutoFetchManager(this);
            }
        }
        if (this.queryTuning && tunedFetch != null && profileCount >= this.profilingMin) {
            if (tunedFetch.autoFetchTune(query)) {
                ++this.tunedQueryCount;
            }
            return true;
        }
        return false;
    }
    
    public void collectQueryInfo(final ObjectGraphNode node, final int beans, final int micros) {
        if (node != null) {
            final ObjectGraphOrigin origin = node.getOriginQueryPoint();
            if (origin != null) {
                final Statistics stats = this.getQueryPointStats(origin);
                stats.collectQueryInfo(node, beans, micros);
            }
        }
    }
    
    public void collectNodeUsage(final NodeUsageCollector usageCollector) {
        final ObjectGraphOrigin origin = usageCollector.getNode().getOriginQueryPoint();
        final Statistics stats = this.getQueryPointStats(origin);
        if (this.logging.isTraceUsageCollection()) {
            System.out.println("... NodeUsageCollector " + usageCollector);
        }
        stats.collectUsageInfo(usageCollector);
        if (this.logging.isTraceUsageCollection()) {
            System.out.println("stats\n" + stats);
        }
    }
    
    private Statistics getQueryPointStats(final ObjectGraphOrigin originQueryPoint) {
        synchronized (this.statisticsMonitor) {
            Statistics stats = this.statisticsMap.get(originQueryPoint.getKey());
            if (stats == null) {
                stats = new Statistics(originQueryPoint, this.queryTuningAddVersion);
                this.statisticsMap.put(originQueryPoint.getKey(), stats);
            }
            return stats;
        }
    }
    
    public String toString() {
        synchronized (this.statisticsMonitor) {
            return this.statisticsMap.values().toString();
        }
    }
    
    private static class Counters
    {
        int newPlan;
        int modified;
        int unchanged;
        int noUsage;
        
        void incrementNoUsage() {
            ++this.noUsage;
        }
        
        void incrementNew() {
            ++this.newPlan;
        }
        
        void incrementModified() {
            ++this.modified;
        }
        
        void incrementUnchanged() {
            ++this.unchanged;
        }
        
        boolean isInteresting() {
            return this.newPlan > 0 || this.modified > 0;
        }
        
        public String toString() {
            return "new[" + this.newPlan + "] modified[" + this.modified + "] unchanged[" + this.unchanged + "] nousage[" + this.noUsage + "]";
        }
    }
}
