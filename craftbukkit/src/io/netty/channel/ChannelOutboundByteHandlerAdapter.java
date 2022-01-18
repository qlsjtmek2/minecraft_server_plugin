// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.Buf;
import io.netty.buffer.ByteBuf;

public abstract class ChannelOutboundByteHandlerAdapter extends ChannelOperationHandlerAdapter implements ChannelOutboundByteHandler
{
    @Override
    public ByteBuf newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return ChannelHandlerUtil.allocate(ctx);
    }
    
    @Override
    public void discardOutboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        ctx.outboundByteBuffer().discardSomeReadBytes();
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        ctx.outboundByteBuffer().release();
    }
    
    @Override
    public final void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.flush(ctx, ctx.outboundByteBuffer(), promise);
    }
    
    protected abstract void flush(final ChannelHandlerContext p0, final ByteBuf p1, final ChannelPromise p2) throws Exception;
}
