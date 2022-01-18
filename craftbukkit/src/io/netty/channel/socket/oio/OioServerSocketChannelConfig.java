// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket.oio;

import io.netty.channel.ChannelConfig;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.socket.ServerSocketChannelConfig;

public interface OioServerSocketChannelConfig extends ServerSocketChannelConfig
{
    OioServerSocketChannelConfig setSoTimeout(final int p0);
    
    int getSoTimeout();
    
    OioServerSocketChannelConfig setBacklog(final int p0);
    
    OioServerSocketChannelConfig setReuseAddress(final boolean p0);
    
    OioServerSocketChannelConfig setReceiveBufferSize(final int p0);
    
    OioServerSocketChannelConfig setPerformancePreferences(final int p0, final int p1, final int p2);
    
    OioServerSocketChannelConfig setConnectTimeoutMillis(final int p0);
    
    OioServerSocketChannelConfig setWriteSpinCount(final int p0);
    
    OioServerSocketChannelConfig setAllocator(final ByteBufAllocator p0);
    
    OioServerSocketChannelConfig setAutoRead(final boolean p0);
    
    OioServerSocketChannelConfig setDefaultHandlerByteBufType(final ChannelConfig.ChannelHandlerByteBufType p0);
}
