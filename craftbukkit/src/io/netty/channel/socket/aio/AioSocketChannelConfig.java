// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket.aio;

import io.netty.channel.ChannelConfig;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.socket.SocketChannelConfig;

public interface AioSocketChannelConfig extends SocketChannelConfig
{
    AioSocketChannelConfig setReadTimeout(final long p0);
    
    AioSocketChannelConfig setWriteTimeout(final long p0);
    
    long getReadTimeout();
    
    long getWriteTimeout();
    
    AioSocketChannelConfig setTcpNoDelay(final boolean p0);
    
    AioSocketChannelConfig setSoLinger(final int p0);
    
    AioSocketChannelConfig setSendBufferSize(final int p0);
    
    AioSocketChannelConfig setReceiveBufferSize(final int p0);
    
    AioSocketChannelConfig setKeepAlive(final boolean p0);
    
    AioSocketChannelConfig setTrafficClass(final int p0);
    
    AioSocketChannelConfig setReuseAddress(final boolean p0);
    
    AioSocketChannelConfig setPerformancePreferences(final int p0, final int p1, final int p2);
    
    AioSocketChannelConfig setAllowHalfClosure(final boolean p0);
    
    AioSocketChannelConfig setWriteSpinCount(final int p0);
    
    AioSocketChannelConfig setConnectTimeoutMillis(final int p0);
    
    AioSocketChannelConfig setAllocator(final ByteBufAllocator p0);
    
    AioSocketChannelConfig setAutoRead(final boolean p0);
    
    AioSocketChannelConfig setDefaultHandlerByteBufType(final ChannelConfig.ChannelHandlerByteBufType p0);
}
