// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.util;

import java.util.Map;
import java.util.LinkedHashMap;

public class LRUCache extends LinkedHashMap
{
    private static final long serialVersionUID = 1L;
    protected int maxElements;
    
    public LRUCache(final int maxSize) {
        super(maxSize);
        this.maxElements = maxSize;
    }
    
    protected boolean removeEldestEntry(final Map.Entry eldest) {
        return this.size() > this.maxElements;
    }
}
