// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cache;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.Iterator;
import com.avaje.ebean.cache.ServerCacheStatistics;
import com.avaje.ebean.BackgroundExecutor;
import java.util.concurrent.TimeUnit;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.cache.ServerCacheOptions;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import com.avaje.ebean.cache.ServerCache;

public class DefaultServerCache implements ServerCache
{
    private static final Logger logger;
    private static final CacheEntryComparator comparator;
    private final ConcurrentHashMap<Object, CacheEntry> map;
    private final AtomicInteger missCount;
    private final AtomicInteger removedHitCount;
    private final Object monitor;
    private final String name;
    private int maxSize;
    private long trimFrequency;
    private int maxIdleSecs;
    private int maxSecsToLive;
    
    public DefaultServerCache(final String name, final ServerCacheOptions options) {
        this(name, options.getMaxSize(), options.getMaxIdleSecs(), options.getMaxSecsToLive());
    }
    
    public DefaultServerCache(final String name, final int maxSize, final int maxIdleSecs, final int maxSecsToLive) {
        this.map = new ConcurrentHashMap<Object, CacheEntry>();
        this.missCount = new AtomicInteger();
        this.removedHitCount = new AtomicInteger();
        this.monitor = new Object();
        this.name = name;
        this.maxSize = maxSize;
        this.maxIdleSecs = maxIdleSecs;
        this.maxSecsToLive = maxSecsToLive;
        this.trimFrequency = 60L;
    }
    
    public void init(final EbeanServer server) {
        final TrimTask trim = new TrimTask();
        final BackgroundExecutor executor = server.getBackgroundExecutor();
        executor.executePeriodically(trim, this.trimFrequency, TimeUnit.SECONDS);
    }
    
    public ServerCacheStatistics getStatistics(final boolean reset) {
        final ServerCacheStatistics s = new ServerCacheStatistics();
        s.setCacheName(this.name);
        s.setMaxSize(this.maxSize);
        final int mc = reset ? this.missCount.getAndSet(0) : this.missCount.get();
        final int hc = this.getHitCount(reset);
        final int size = this.size();
        s.setSize(size);
        s.setHitCount(hc);
        s.setMissCount(mc);
        return s;
    }
    
    public int getHitRatio() {
        final int mc = this.missCount.get();
        final int hc = this.getHitCount(false);
        final int totalCount = hc + mc;
        if (totalCount == 0) {
            return 0;
        }
        return hc * 100 / totalCount;
    }
    
    private int getHitCount(final boolean reset) {
        int hc = reset ? this.removedHitCount.getAndSet(0) : this.removedHitCount.get();
        for (final CacheEntry cacheEntry : this.map.values()) {
            hc += cacheEntry.getHitCount(reset);
        }
        return hc;
    }
    
    public ServerCacheOptions getOptions() {
        synchronized (this.monitor) {
            final ServerCacheOptions o = new ServerCacheOptions();
            o.setMaxIdleSecs(this.maxIdleSecs);
            o.setMaxSize(this.maxSize);
            o.setMaxSecsToLive(this.maxSecsToLive);
            return o;
        }
    }
    
    public void setOptions(final ServerCacheOptions o) {
        synchronized (this.monitor) {
            this.maxIdleSecs = o.getMaxIdleSecs();
            this.maxSize = o.getMaxSize();
            this.maxSecsToLive = o.getMaxSecsToLive();
        }
    }
    
    public int getMaxSize() {
        return this.maxSize;
    }
    
    public void setMaxSize(final int maxSize) {
        synchronized (this.monitor) {
            this.maxSize = maxSize;
        }
    }
    
    public long getMaxIdleSecs() {
        return this.maxIdleSecs;
    }
    
    public void setMaxIdleSecs(final int maxIdleSecs) {
        synchronized (this.monitor) {
            this.maxIdleSecs = maxIdleSecs;
        }
    }
    
    public long getMaxSecsToLive() {
        return this.maxSecsToLive;
    }
    
    public void setMaxSecsToLive(final int maxSecsToLive) {
        synchronized (this.monitor) {
            this.maxSecsToLive = maxSecsToLive;
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public void clear() {
        this.map.clear();
    }
    
    public Object get(final Object key) {
        final CacheEntry entry = this.map.get(key);
        if (entry == null) {
            this.missCount.incrementAndGet();
            return null;
        }
        return entry.getValue();
    }
    
    public Object put(final Object key, final Object value) {
        final CacheEntry entry = this.map.put(key, new CacheEntry(key, value));
        if (entry == null) {
            return null;
        }
        final int removedHits = entry.getHitCount(true);
        this.removedHitCount.addAndGet(removedHits);
        return entry.getValue();
    }
    
    public Object putIfAbsent(final Object key, final Object value) {
        final CacheEntry entry = this.map.putIfAbsent(key, new CacheEntry(key, value));
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }
    
    public Object remove(final Object key) {
        final CacheEntry entry = this.map.remove(key);
        if (entry == null) {
            return null;
        }
        final int removedHits = entry.getHitCount(true);
        this.removedHitCount.addAndGet(removedHits);
        return entry.getValue();
    }
    
    public int size() {
        return this.map.size();
    }
    
    private Iterator<CacheEntry> cacheEntries() {
        return this.map.values().iterator();
    }
    
    static {
        logger = Logger.getLogger(DefaultServerCache.class.getName());
        comparator = new CacheEntryComparator();
    }
    
    private class TrimTask implements Runnable
    {
        public void run() {
            final long startTime = System.currentTimeMillis();
            if (DefaultServerCache.logger.isLoggable(Level.FINER)) {
                DefaultServerCache.logger.finer("trimming cache " + DefaultServerCache.this.name);
            }
            int trimmedByIdle = 0;
            int trimmedByTTL = 0;
            int trimmedByLRU = 0;
            final boolean trimMaxSize = DefaultServerCache.this.maxSize > 0 && DefaultServerCache.this.maxSize < DefaultServerCache.this.size();
            final ArrayList<CacheEntry> activeList = new ArrayList<CacheEntry>();
            final long idleExpire = System.currentTimeMillis() - DefaultServerCache.this.maxIdleSecs * 1000;
            final long ttlExpire = System.currentTimeMillis() - DefaultServerCache.this.maxSecsToLive * 1000;
            final Iterator<CacheEntry> it = DefaultServerCache.this.cacheEntries();
            while (it.hasNext()) {
                final CacheEntry cacheEntry = it.next();
                if (DefaultServerCache.this.maxIdleSecs > 0 && idleExpire > cacheEntry.getLastAccessTime()) {
                    it.remove();
                    ++trimmedByIdle;
                }
                else if (DefaultServerCache.this.maxSecsToLive > 0 && ttlExpire > cacheEntry.getCreateTime()) {
                    it.remove();
                    ++trimmedByTTL;
                }
                else {
                    if (!trimMaxSize) {
                        continue;
                    }
                    activeList.add(cacheEntry);
                }
            }
            if (trimMaxSize) {
                trimmedByLRU = activeList.size() - DefaultServerCache.this.maxSize;
                if (trimmedByLRU > 0) {
                    Collections.sort(activeList, DefaultServerCache.comparator);
                    for (int i = DefaultServerCache.this.maxSize; i < activeList.size(); ++i) {
                        DefaultServerCache.this.map.remove(activeList.get(i).getKey());
                    }
                }
            }
            final long exeTime = System.currentTimeMillis() - startTime;
            if (DefaultServerCache.logger.isLoggable(Level.FINE)) {
                DefaultServerCache.logger.fine("Executed trim of cache " + DefaultServerCache.this.name + " in [" + exeTime + "]millis  idle[" + trimmedByIdle + "] timeToLive[" + trimmedByTTL + "] accessTime[" + trimmedByLRU + "]");
            }
        }
    }
    
    private static class CacheEntryComparator implements Comparator<CacheEntry>, Serializable
    {
        private static final long serialVersionUID = 1L;
        
        public int compare(final CacheEntry o1, final CacheEntry o2) {
            return o1.getLastAccessLong().compareTo(o2.getLastAccessLong());
        }
    }
    
    public static class CacheEntry
    {
        private final Object key;
        private final Object value;
        private final long createTime;
        private final AtomicInteger hitCount;
        private Long lastAccessTime;
        
        public CacheEntry(final Object key, final Object value) {
            this.hitCount = new AtomicInteger();
            this.key = key;
            this.value = value;
            this.createTime = System.currentTimeMillis();
            this.lastAccessTime = this.createTime;
        }
        
        public Object getKey() {
            return this.key;
        }
        
        public Object getValue() {
            this.hitCount.incrementAndGet();
            this.lastAccessTime = System.currentTimeMillis();
            return this.value;
        }
        
        public long getCreateTime() {
            return this.createTime;
        }
        
        public long getLastAccessTime() {
            return this.lastAccessTime;
        }
        
        public Long getLastAccessLong() {
            return this.lastAccessTime;
        }
        
        public int getHitCount(final boolean reset) {
            if (reset) {
                return this.hitCount.getAndSet(0);
            }
            return this.hitCount.get();
        }
        
        public int getHitCount() {
            return this.hitCount.get();
        }
    }
}
