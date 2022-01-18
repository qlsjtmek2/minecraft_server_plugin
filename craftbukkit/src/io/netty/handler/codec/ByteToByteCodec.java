// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.buffer.Buf;
import io.netty.channel.ChannelPromise;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundByteHandler;
import io.netty.channel.ChannelInboundByteHandler;
import io.netty.channel.ChannelDuplexHandler;

public abstract class ByteToByteCodec extends ChannelDuplexHandler implements ChannelInboundByteHandler, ChannelOutboundByteHandler
{
    private final ByteToByteEncoder encoder;
    private final ByteToByteDecoder decoder;
    
    public ByteToByteCodec() {
        this.encoder = new ByteToByteEncoder() {
            @Override
            protected void encode(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) throws Exception {
                ByteToByteCodec.this.encode(ctx, in, out);
            }
        };
        this.decoder = new ByteToByteDecoder() {
            @Override
            protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) throws Exception {
                ByteToByteCodec.this.decode(ctx, in, out);
            }
            
            @Override
            protected void decodeLast(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) throws Exception {
                ByteToByteCodec.this.decodeLast(ctx, in, out);
            }
        };
    }
    
    @Override
    public ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return this.decoder.newInboundBuffer(ctx);
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        this.decoder.inboundBufferUpdated(ctx);
    }
    
    @Override
    public ByteBuf newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return this.encoder.newOutboundBuffer(ctx);
    }
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.encoder.flush(ctx, promise);
    }
    
    @Override
    public void discardInboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        this.decoder.discardInboundReadBytes(ctx);
    }
    
    @Override
    public void discardOutboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        this.encoder.discardOutboundReadBytes(ctx);
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.decoder.freeInboundBuffer(ctx);
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.encoder.freeOutboundBuffer(ctx);
    }
    
    protected abstract void encode(final ChannelHandlerContext p0, final ByteBuf p1, final ByteBuf p2) throws Exception;
    
    protected abstract void decode(final ChannelHandlerContext p0, final ByteBuf p1, final ByteBuf p2) throws Exception;
    
    protected void decodeLast(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) throws Exception {
        this.decode(ctx, in, out);
    }
}
