// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.buffer.Buf;
import io.netty.channel.ChannelPromise;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.TypeParameterMatcher;
import io.netty.channel.ChannelOutboundMessageHandler;
import io.netty.channel.ChannelInboundMessageHandler;
import io.netty.channel.ChannelDuplexHandler;

public abstract class MessageToMessageCodec<INBOUND_IN, OUTBOUND_IN> extends ChannelDuplexHandler implements ChannelInboundMessageHandler<INBOUND_IN>, ChannelOutboundMessageHandler<OUTBOUND_IN>
{
    private final MessageToMessageEncoder<Object> encoder;
    private final MessageToMessageDecoder<Object> decoder;
    private final TypeParameterMatcher inboundMsgMatcher;
    private final TypeParameterMatcher outboundMsgMatcher;
    
    protected MessageToMessageCodec() {
        this.encoder = new MessageToMessageEncoder<Object>() {
            @Override
            public boolean acceptOutboundMessage(final Object msg) throws Exception {
                return MessageToMessageCodec.this.acceptOutboundMessage(msg);
            }
            
            @Override
            protected Object encode(final ChannelHandlerContext ctx, final Object msg) throws Exception {
                return MessageToMessageCodec.this.encode(ctx, msg);
            }
        };
        this.decoder = new MessageToMessageDecoder<Object>() {
            @Override
            public boolean acceptInboundMessage(final Object msg) throws Exception {
                return MessageToMessageCodec.this.acceptInboundMessage(msg);
            }
            
            @Override
            protected Object decode(final ChannelHandlerContext ctx, final Object msg) throws Exception {
                return MessageToMessageCodec.this.decode(ctx, msg);
            }
        };
        this.inboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "INBOUND_IN");
        this.outboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "OUTBOUND_IN");
    }
    
    protected MessageToMessageCodec(final Class<? extends INBOUND_IN> inboundMessageType, final Class<? extends OUTBOUND_IN> outboundMessageType) {
        this.encoder = new MessageToMessageEncoder<Object>() {
            @Override
            public boolean acceptOutboundMessage(final Object msg) throws Exception {
                return MessageToMessageCodec.this.acceptOutboundMessage(msg);
            }
            
            @Override
            protected Object encode(final ChannelHandlerContext ctx, final Object msg) throws Exception {
                return MessageToMessageCodec.this.encode(ctx, msg);
            }
        };
        this.decoder = new MessageToMessageDecoder<Object>() {
            @Override
            public boolean acceptInboundMessage(final Object msg) throws Exception {
                return MessageToMessageCodec.this.acceptInboundMessage(msg);
            }
            
            @Override
            protected Object decode(final ChannelHandlerContext ctx, final Object msg) throws Exception {
                return MessageToMessageCodec.this.decode(ctx, msg);
            }
        };
        this.inboundMsgMatcher = TypeParameterMatcher.get(inboundMessageType);
        this.outboundMsgMatcher = TypeParameterMatcher.get(outboundMessageType);
    }
    
    @Override
    public MessageBuf<INBOUND_IN> newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return (MessageBuf<INBOUND_IN>)this.decoder.newInboundBuffer(ctx);
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.decoder.freeInboundBuffer(ctx);
    }
    
    @Override
    public MessageBuf<OUTBOUND_IN> newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return (MessageBuf<OUTBOUND_IN>)this.encoder.newOutboundBuffer(ctx);
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
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise future) throws Exception {
        this.encoder.flush(ctx, future);
    }
    
    public boolean acceptInboundMessage(final Object msg) throws Exception {
        return this.inboundMsgMatcher.match(msg);
    }
    
    public boolean acceptOutboundMessage(final Object msg) throws Exception {
        return this.outboundMsgMatcher.match(msg);
    }
    
    protected abstract Object encode(final ChannelHandlerContext p0, final OUTBOUND_IN p1) throws Exception;
    
    protected abstract Object decode(final ChannelHandlerContext p0, final INBOUND_IN p1) throws Exception;
}
