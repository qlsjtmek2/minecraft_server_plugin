// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.Buf;
import io.netty.buffer.ByteBuf;

public abstract class ChannelInboundByteHandlerAdapter extends ChannelStateHandlerAdapter implements ChannelInboundByteHandler
{
    @Override
    public ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return ChannelHandlerUtil.allocate(ctx);
    }
    
    @Override
    public void discardInboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        ctx.inboundByteBuffer().discardSomeReadBytes();
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        ctx.inboundByteBuffer().release();
    }
    
    @Override
    public final void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        this.inboundBufferUpdated(ctx, ctx.inboundByteBuffer());
    }
    
    protected abstract void inboundBufferUpdated(final ChannelHandlerContext p0, final ByteBuf p1) throws Exception;
}
