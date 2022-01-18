// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.BufType;
import io.netty.buffer.MessageBuf;
import io.netty.buffer.ByteBuf;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.AttributeMap;

public interface ChannelHandlerContext extends AttributeMap, ChannelPropertyAccess, ChannelInboundInvoker, ChannelOutboundInvoker
{
    Channel channel();
    
    EventExecutor executor();
    
    String name();
    
    ChannelHandler handler();
    
    boolean hasInboundByteBuffer();
    
    boolean hasInboundMessageBuffer();
    
    ByteBuf inboundByteBuffer();
    
     <T> MessageBuf<T> inboundMessageBuffer();
    
    boolean hasOutboundByteBuffer();
    
    boolean hasOutboundMessageBuffer();
    
    ByteBuf outboundByteBuffer();
    
     <T> MessageBuf<T> outboundMessageBuffer();
    
    ByteBuf nextInboundByteBuffer();
    
    MessageBuf<Object> nextInboundMessageBuffer();
    
    ByteBuf nextOutboundByteBuffer();
    
    MessageBuf<Object> nextOutboundMessageBuffer();
    
    BufType nextInboundBufferType();
    
    BufType nextOutboundBufferType();
    
    ChannelHandlerContext fireChannelRegistered();
    
    ChannelHandlerContext fireChannelUnregistered();
    
    ChannelHandlerContext fireChannelActive();
    
    ChannelHandlerContext fireChannelInactive();
    
    ChannelHandlerContext fireExceptionCaught(final Throwable p0);
    
    ChannelHandlerContext fireUserEventTriggered(final Object p0);
    
    ChannelHandlerContext fireInboundBufferUpdated();
    
    ChannelHandlerContext fireChannelReadSuspended();
}
