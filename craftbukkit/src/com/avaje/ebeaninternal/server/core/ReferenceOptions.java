// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

public class ReferenceOptions
{
    private final boolean readOnly;
    private final boolean useCache;
    private final String warmingQuery;
    
    public ReferenceOptions(final boolean useCache, final boolean readOnly, final String warmingQuery) {
        this.useCache = useCache;
        this.readOnly = readOnly;
        this.warmingQuery = warmingQuery;
    }
    
    public boolean isUseCache() {
        return this.useCache;
    }
    
    public boolean isReadOnly() {
        return this.readOnly;
    }
    
    public String getWarmingQuery() {
        return this.warmingQuery;
    }
}
