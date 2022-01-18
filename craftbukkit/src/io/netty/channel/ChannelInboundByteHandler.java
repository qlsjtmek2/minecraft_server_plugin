// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.ByteBuf;

public interface ChannelInboundByteHandler extends ChannelInboundHandler
{
    ByteBuf newInboundBuffer(final ChannelHandlerContext p0) throws Exception;
    
    void discardInboundReadBytes(final ChannelHandlerContext p0) throws Exception;
}
