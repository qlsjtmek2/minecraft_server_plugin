// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

public class LIndexSync
{
    private final String masterHost;
    private final LIndex index;
    
    public LIndexSync(final LIndex index, final String masterHost) {
        this.index = index;
        this.masterHost = masterHost;
    }
    
    public String getMasterHost() {
        return this.masterHost;
    }
    
    public LIndex getIndex() {
        return this.index;
    }
}
