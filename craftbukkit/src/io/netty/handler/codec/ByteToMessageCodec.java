// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.buffer.Buf;
import io.netty.channel.ChannelPromise;
import io.netty.buffer.MessageBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.TypeParameterMatcher;
import io.netty.channel.ChannelOutboundMessageHandler;
import io.netty.channel.ChannelInboundByteHandler;
import io.netty.channel.ChannelDuplexHandler;

public abstract class ByteToMessageCodec<I> extends ChannelDuplexHandler implements ChannelInboundByteHandler, ChannelOutboundMessageHandler<I>
{
    private final TypeParameterMatcher outboundMsgMatcher;
    private final MessageToByteEncoder<I> encoder;
    private final ByteToMessageDecoder decoder;
    
    protected ByteToMessageCodec() {
        this.encoder = new MessageToByteEncoder<I>() {
            @Override
            public boolean acceptOutboundMessage(final Object msg) throws Exception {
                return ByteToMessageCodec.this.acceptOutboundMessage(msg);
            }
            
            @Override
            protected void encode(final ChannelHandlerContext ctx, final I msg, final ByteBuf out) throws Exception {
                ByteToMessageCodec.this.encode(ctx, msg, out);
            }
        };
        this.decoder = new ByteToMessageDecoder() {
            public Object decode(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
                return ByteToMessageCodec.this.decode(ctx, in);
            }
            
            @Override
            protected Object decodeLast(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
                return ByteToMessageCodec.this.decodeLast(ctx, in);
            }
        };
        this.outboundMsgMatcher = TypeParameterMatcher.find(this, ByteToMessageCodec.class, "I");
    }
    
    protected ByteToMessageCodec(final Class<? extends I> outboundMessageType) {
        this.encoder = new MessageToByteEncoder<I>() {
            @Override
            public boolean acceptOutboundMessage(final Object msg) throws Exception {
                return ByteToMessageCodec.this.acceptOutboundMessage(msg);
            }
            
            @Override
            protected void encode(final ChannelHandlerContext ctx, final I msg, final ByteBuf out) throws Exception {
                ByteToMessageCodec.this.encode(ctx, msg, out);
            }
        };
        this.decoder = new ByteToMessageDecoder() {
            public Object decode(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
                return ByteToMessageCodec.this.decode(ctx, in);
            }
            
            @Override
            protected Object decodeLast(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
                return ByteToMessageCodec.this.decodeLast(ctx, in);
            }
        };
        this.outboundMsgMatcher = TypeParameterMatcher.get(outboundMessageType);
    }
    
    @Override
    public void beforeAdd(final ChannelHandlerContext ctx) throws Exception {
        this.decoder.beforeAdd(ctx);
    }
    
    @Override
    public ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return this.decoder.newInboundBuffer(ctx);
    }
    
    @Override
    public void discardInboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        this.decoder.discardInboundReadBytes(ctx);
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.decoder.freeInboundBuffer(ctx);
    }
    
    @Override
    public MessageBuf<I> newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return this.encoder.newOutboundBuffer(ctx);
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.encoder.freeOutboundBuffer(ctx);
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        this.decoder.inboundBufferUpdated(ctx);
    }
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.encoder.flush(ctx, promise);
    }
    
    public boolean acceptOutboundMessage(final Object msg) throws Exception {
        return this.outboundMsgMatcher.match(msg);
    }
    
    protected abstract void encode(final ChannelHandlerContext p0, final I p1, final ByteBuf p2) throws Exception;
    
    protected abstract Object decode(final ChannelHandlerContext p0, final ByteBuf p1) throws Exception;
    
    protected Object decodeLast(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        return this.decode(ctx, in);
    }
}
