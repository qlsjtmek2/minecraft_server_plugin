// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

interface ChannelInboundInvoker
{
    ChannelInboundInvoker fireChannelRegistered();
    
    ChannelInboundInvoker fireChannelUnregistered();
    
    ChannelInboundInvoker fireChannelActive();
    
    ChannelInboundInvoker fireChannelInactive();
    
    ChannelInboundInvoker fireExceptionCaught(final Throwable p0);
    
    ChannelInboundInvoker fireUserEventTriggered(final Object p0);
    
    ChannelInboundInvoker fireInboundBufferUpdated();
    
    ChannelInboundInvoker fireChannelReadSuspended();
}
