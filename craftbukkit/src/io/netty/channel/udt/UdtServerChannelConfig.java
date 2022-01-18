// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.udt;

import io.netty.channel.ChannelConfig;
import io.netty.buffer.ByteBufAllocator;

public interface UdtServerChannelConfig extends UdtChannelConfig
{
    int getBacklog();
    
    UdtServerChannelConfig setBacklog(final int p0);
    
    UdtServerChannelConfig setConnectTimeoutMillis(final int p0);
    
    UdtServerChannelConfig setWriteSpinCount(final int p0);
    
    UdtServerChannelConfig setAllocator(final ByteBufAllocator p0);
    
    UdtServerChannelConfig setAutoRead(final boolean p0);
    
    UdtServerChannelConfig setProtocolReceiveBufferSize(final int p0);
    
    UdtServerChannelConfig setProtocolSendBufferSize(final int p0);
    
    UdtServerChannelConfig setReceiveBufferSize(final int p0);
    
    UdtServerChannelConfig setReuseAddress(final boolean p0);
    
    UdtServerChannelConfig setSendBufferSize(final int p0);
    
    UdtServerChannelConfig setSoLinger(final int p0);
    
    UdtServerChannelConfig setSystemReceiveBufferSize(final int p0);
    
    UdtServerChannelConfig setSystemSendBufferSize(final int p0);
    
    UdtServerChannelConfig setDefaultHandlerByteBufType(final ChannelConfig.ChannelHandlerByteBufType p0);
}
