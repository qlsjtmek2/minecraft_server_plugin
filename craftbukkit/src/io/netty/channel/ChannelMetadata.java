// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.BufType;

public final class ChannelMetadata
{
    private final BufType bufferType;
    private final boolean hasDisconnect;
    
    public ChannelMetadata(final BufType bufferType, final boolean hasDisconnect) {
        if (bufferType == null) {
            throw new NullPointerException("bufferType");
        }
        this.bufferType = bufferType;
        this.hasDisconnect = hasDisconnect;
    }
    
    public BufType bufferType() {
        return this.bufferType;
    }
    
    public boolean hasDisconnect() {
        return this.hasDisconnect;
    }
}
