// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;

public interface ServerSocketChannelConfig extends ChannelConfig
{
    int getBacklog();
    
    ServerSocketChannelConfig setBacklog(final int p0);
    
    boolean isReuseAddress();
    
    ServerSocketChannelConfig setReuseAddress(final boolean p0);
    
    int getReceiveBufferSize();
    
    ServerSocketChannelConfig setReceiveBufferSize(final int p0);
    
    ServerSocketChannelConfig setPerformancePreferences(final int p0, final int p1, final int p2);
    
    ServerSocketChannelConfig setConnectTimeoutMillis(final int p0);
    
    ServerSocketChannelConfig setWriteSpinCount(final int p0);
    
    ServerSocketChannelConfig setAllocator(final ByteBufAllocator p0);
    
    ServerSocketChannelConfig setAutoRead(final boolean p0);
    
    ServerSocketChannelConfig setDefaultHandlerByteBufType(final ChannelHandlerByteBufType p0);
}
