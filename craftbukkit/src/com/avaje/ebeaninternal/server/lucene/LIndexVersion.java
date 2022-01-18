// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

public class LIndexVersion
{
    private final long generation;
    private final long version;
    
    public LIndexVersion(final long generation, final long version) {
        this.generation = generation;
        this.version = version;
    }
    
    public long getGeneration() {
        return this.generation;
    }
    
    public long getVersion() {
        return this.version;
    }
    
    public String toString() {
        return "gen[" + this.generation + "] ver[" + this.version + "]";
    }
}
