// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.MessageBuf;

public interface ChannelOutboundMessageHandler<I> extends ChannelOutboundHandler
{
    MessageBuf<I> newOutboundBuffer(final ChannelHandlerContext p0) throws Exception;
}
