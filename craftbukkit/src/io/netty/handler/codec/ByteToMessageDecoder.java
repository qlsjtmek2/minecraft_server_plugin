// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.buffer.MessageBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundByteHandlerAdapter;

public abstract class ByteToMessageDecoder extends ChannelInboundByteHandlerAdapter
{
    private volatile boolean singleDecode;
    private boolean decodeWasNull;
    
    public void setSingleDecode(final boolean singleDecode) {
        this.singleDecode = singleDecode;
    }
    
    public boolean isSingleDecode() {
        return this.singleDecode;
    }
    
    public void inboundBufferUpdated(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        this.callDecode(ctx, in);
    }
    
    @Override
    public void channelReadSuspended(final ChannelHandlerContext ctx) throws Exception {
        if (this.decodeWasNull) {
            this.decodeWasNull = false;
            if (!ctx.channel().config().isAutoRead()) {
                ctx.read();
            }
        }
        super.channelReadSuspended(ctx);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        try {
            final ByteBuf in = ctx.inboundByteBuffer();
            if (in.isReadable()) {
                this.callDecode(ctx, in);
            }
            if (ctx.nextInboundMessageBuffer().unfoldAndAdd(this.decodeLast(ctx, in))) {
                ctx.fireInboundBufferUpdated();
            }
        }
        catch (Throwable t) {
            if (t instanceof CodecException) {
                ctx.fireExceptionCaught(t);
            }
            else {
                ctx.fireExceptionCaught((Throwable)new DecoderException(t));
            }
        }
        finally {
            ctx.fireChannelInactive();
        }
    }
    
    protected void callDecode(final ChannelHandlerContext ctx, final ByteBuf in) {
        boolean wasNull = false;
        boolean decoded = false;
        final MessageBuf<Object> out = ctx.nextInboundMessageBuffer();
        while (in.isReadable()) {
            try {
                final int oldInputLength = in.readableBytes();
                final Object o = this.decode(ctx, in);
                if (o == null) {
                    wasNull = true;
                    if (oldInputLength != in.readableBytes()) {
                        continue;
                    }
                }
                else {
                    wasNull = false;
                    if (oldInputLength == in.readableBytes()) {
                        throw new IllegalStateException("decode() did not read anything but decoded a message.");
                    }
                    if (out.unfoldAndAdd(o)) {
                        decoded = true;
                        if (!this.isSingleDecode()) {
                            continue;
                        }
                    }
                }
            }
            catch (Throwable t) {
                if (decoded) {
                    decoded = false;
                    ctx.fireInboundBufferUpdated();
                }
                if (t instanceof CodecException) {
                    ctx.fireExceptionCaught(t);
                }
                else {
                    ctx.fireExceptionCaught((Throwable)new DecoderException(t));
                }
            }
            break;
        }
        if (decoded) {
            this.decodeWasNull = false;
            ctx.fireInboundBufferUpdated();
        }
        else if (wasNull) {
            this.decodeWasNull = true;
        }
    }
    
    protected abstract Object decode(final ChannelHandlerContext p0, final ByteBuf p1) throws Exception;
    
    protected Object decodeLast(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        return this.decode(ctx, in);
    }
}
