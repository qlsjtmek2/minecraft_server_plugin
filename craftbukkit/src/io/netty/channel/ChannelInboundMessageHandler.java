// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.MessageBuf;

public interface ChannelInboundMessageHandler<I> extends ChannelInboundHandler
{
    MessageBuf<I> newInboundBuffer(final ChannelHandlerContext p0) throws Exception;
}
