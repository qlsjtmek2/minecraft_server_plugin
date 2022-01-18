// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster;

import java.io.Serializable;

public class DataHolder implements Serializable
{
    private static final long serialVersionUID = 9090748723571322192L;
    private final byte[] data;
    
    public DataHolder(final byte[] data) {
        this.data = data;
    }
    
    public byte[] getData() {
        return this.data;
    }
}
