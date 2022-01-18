// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.Map;
import java.util.logging.Logger;
import java.util.LinkedHashMap;

public class PstmtCache extends LinkedHashMap<String, ExtendedPreparedStatement>
{
    private static final Logger logger;
    static final long serialVersionUID = -3096406924865550697L;
    final String cacheName;
    final int maxSize;
    int removeCounter;
    int hitCounter;
    int missCounter;
    int putCounter;
    
    public PstmtCache(final String cacheName, final int maxCacheSize) {
        super(maxCacheSize * 3, 0.75f, true);
        this.cacheName = cacheName;
        this.maxSize = maxCacheSize;
    }
    
    public String getDescription() {
        return this.cacheName + " size:" + this.size() + " max:" + this.maxSize + " totalHits:" + this.hitCounter + " hitRatio:" + this.getHitRatio() + " removes:" + this.removeCounter;
    }
    
    public int getMaxSize() {
        return this.maxSize;
    }
    
    public int getHitRatio() {
        if (this.hitCounter == 0) {
            return 0;
        }
        return this.hitCounter * 100 / (this.hitCounter + this.missCounter);
    }
    
    public int getHitCounter() {
        return this.hitCounter;
    }
    
    public int getMissCounter() {
        return this.missCounter;
    }
    
    public int getPutCounter() {
        return this.putCounter;
    }
    
    public ExtendedPreparedStatement get(final Object key) {
        final ExtendedPreparedStatement o = super.get(key);
        if (o == null) {
            ++this.missCounter;
        }
        else {
            ++this.hitCounter;
        }
        return o;
    }
    
    public ExtendedPreparedStatement remove(final Object key) {
        final ExtendedPreparedStatement o = super.remove(key);
        if (o == null) {
            ++this.missCounter;
        }
        else {
            ++this.hitCounter;
        }
        return o;
    }
    
    public ExtendedPreparedStatement put(final String key, final ExtendedPreparedStatement value) {
        ++this.putCounter;
        return super.put(key, value);
    }
    
    protected boolean removeEldestEntry(final Map.Entry<String, ExtendedPreparedStatement> eldest) {
        if (this.size() < this.maxSize) {
            return false;
        }
        ++this.removeCounter;
        try {
            final ExtendedPreparedStatement pstmt = eldest.getValue();
            pstmt.closeDestroy();
        }
        catch (SQLException e) {
            PstmtCache.logger.log(Level.SEVERE, "Error closing ExtendedPreparedStatement", e);
        }
        return true;
    }
    
    static {
        logger = Logger.getLogger(PstmtCache.class.getName());
    }
}
