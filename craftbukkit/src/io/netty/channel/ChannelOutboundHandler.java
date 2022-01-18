// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.Buf;

interface ChannelOutboundHandler extends ChannelOperationHandler
{
    Buf newOutboundBuffer(final ChannelHandlerContext p0) throws Exception;
    
    void freeOutboundBuffer(final ChannelHandlerContext p0) throws Exception;
}
