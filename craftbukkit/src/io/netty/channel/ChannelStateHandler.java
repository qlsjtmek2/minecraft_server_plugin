// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

public interface ChannelStateHandler extends ChannelHandler
{
    void channelRegistered(final ChannelHandlerContext p0) throws Exception;
    
    void channelUnregistered(final ChannelHandlerContext p0) throws Exception;
    
    void channelActive(final ChannelHandlerContext p0) throws Exception;
    
    void channelInactive(final ChannelHandlerContext p0) throws Exception;
    
    void channelReadSuspended(final ChannelHandlerContext p0) throws Exception;
    
    void inboundBufferUpdated(final ChannelHandlerContext p0) throws Exception;
    
    void userEventTriggered(final ChannelHandlerContext p0, final Object p1) throws Exception;
}
