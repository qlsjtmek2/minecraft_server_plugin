// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundByteHandlerAdapter;

public abstract class ByteToByteDecoder extends ChannelInboundByteHandlerAdapter
{
    private volatile boolean singleDecode;
    
    public void setSingleDecode(final boolean singleDecode) {
        this.singleDecode = singleDecode;
    }
    
    public boolean isSingleDecode() {
        return this.singleDecode;
    }
    
    public void inboundBufferUpdated(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        this.callDecode(ctx, in, ctx.nextInboundByteBuffer());
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        final ByteBuf in = ctx.inboundByteBuffer();
        final ByteBuf out = ctx.nextInboundByteBuffer();
        if (!in.isReadable()) {
            this.callDecode(ctx, in, out);
        }
        final int oldOutSize = out.readableBytes();
        try {
            this.decodeLast(ctx, in, out);
        }
        catch (Throwable t) {
            if (t instanceof CodecException) {
                ctx.fireExceptionCaught(t);
            }
            else {
                ctx.fireExceptionCaught((Throwable)new DecoderException(t));
            }
        }
        if (out.readableBytes() > oldOutSize) {
            ctx.fireInboundBufferUpdated();
        }
        ctx.fireChannelInactive();
    }
    
    private void callDecode(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) {
        final int oldOutSize = out.readableBytes();
        while (in.isReadable()) {
            final int oldInSize = in.readableBytes();
            try {
                this.decode(ctx, in, out);
            }
            catch (Throwable t) {
                if (t instanceof CodecException) {
                    ctx.fireExceptionCaught(t);
                }
                else {
                    ctx.fireExceptionCaught((Throwable)new DecoderException(t));
                }
            }
            if (oldInSize == in.readableBytes()) {
                break;
            }
            if (this.isSingleDecode()) {
                break;
            }
        }
        if (out.readableBytes() > oldOutSize) {
            ctx.fireInboundBufferUpdated();
        }
    }
    
    protected abstract void decode(final ChannelHandlerContext p0, final ByteBuf p1, final ByteBuf p2) throws Exception;
    
    protected void decodeLast(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) throws Exception {
        this.decode(ctx, in, out);
    }
}
