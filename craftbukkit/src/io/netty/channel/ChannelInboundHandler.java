// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.Buf;

interface ChannelInboundHandler extends ChannelStateHandler
{
    Buf newInboundBuffer(final ChannelHandlerContext p0) throws Exception;
    
    void freeInboundBuffer(final ChannelHandlerContext p0) throws Exception;
}
