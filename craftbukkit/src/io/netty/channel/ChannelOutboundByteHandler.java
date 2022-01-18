// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.ByteBuf;

public interface ChannelOutboundByteHandler extends ChannelOutboundHandler
{
    ByteBuf newOutboundBuffer(final ChannelHandlerContext p0) throws Exception;
    
    void discardOutboundReadBytes(final ChannelHandlerContext p0) throws Exception;
}
