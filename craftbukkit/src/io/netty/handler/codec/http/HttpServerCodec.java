// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import io.netty.buffer.Buf;
import io.netty.buffer.MessageBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOperationHandler;
import io.netty.channel.ChannelStateHandler;
import io.netty.channel.ChannelOutboundMessageHandler;
import io.netty.channel.ChannelInboundByteHandler;
import io.netty.channel.CombinedChannelDuplexHandler;

public final class HttpServerCodec extends CombinedChannelDuplexHandler implements ChannelInboundByteHandler, ChannelOutboundMessageHandler<HttpObject>
{
    public HttpServerCodec() {
        this(4096, 8192, 8192);
    }
    
    public HttpServerCodec(final int maxInitialLineLength, final int maxHeaderSize, final int maxChunkSize) {
        super(new HttpRequestDecoder(maxInitialLineLength, maxHeaderSize, maxChunkSize), new HttpResponseEncoder());
    }
    
    private HttpRequestDecoder decoder() {
        return (HttpRequestDecoder)this.stateHandler();
    }
    
    private HttpResponseEncoder encoder() {
        return (HttpResponseEncoder)this.operationHandler();
    }
    
    @Override
    public ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return this.decoder().newInboundBuffer(ctx);
    }
    
    @Override
    public void discardInboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        this.decoder().discardInboundReadBytes(ctx);
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
