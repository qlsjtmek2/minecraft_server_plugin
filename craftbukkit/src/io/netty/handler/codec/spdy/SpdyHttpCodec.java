// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.buffer.Buf;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOperationHandler;
import io.netty.channel.ChannelStateHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.channel.ChannelOutboundMessageHandler;
import io.netty.channel.ChannelInboundMessageHandler;
import io.netty.channel.CombinedChannelDuplexHandler;

public final class SpdyHttpCodec extends CombinedChannelDuplexHandler implements ChannelInboundMessageHandler<SpdyDataOrControlFrame>, ChannelOutboundMessageHandler<HttpObject>
{
    public SpdyHttpCodec(final int version, final int maxContentLength) {
        super(new SpdyHttpDecoder(version, maxContentLength), new SpdyHttpEncoder(version));
    }
    
    private SpdyHttpDecoder decoder() {
        return (SpdyHttpDecoder)this.stateHandler();
    }
    
    private SpdyHttpEncoder encoder() {
        return (SpdyHttpEncoder)this.operationHandler();
    }
    
    @Override
    public MessageBuf<SpdyDataOrControlFrame> newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return this.decoder().newInboundBuffer(ctx);
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.decoder().freeInboundBuffer(ctx);
    }
    
    @Override
    public MessageBuf<HttpObject> newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return this.encoder().newOutboundBuffer(ctx);
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.encoder().freeOutboundBuffer(ctx);
    }
}
