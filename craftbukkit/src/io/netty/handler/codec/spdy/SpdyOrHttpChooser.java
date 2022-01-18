// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.buffer.Buf;
import io.netty.channel.ChannelInboundMessageHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.ssl.SslHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.net.ssl.SSLEngine;
import io.netty.channel.ChannelInboundByteHandler;
import io.netty.channel.ChannelDuplexHandler;

public abstract class SpdyOrHttpChooser extends ChannelDuplexHandler implements ChannelInboundByteHandler
{
    private final int maxSpdyContentLength;
    private final int maxHttpContentLength;
    
    protected SpdyOrHttpChooser(final int maxSpdyContentLength, final int maxHttpContentLength) {
        this.maxSpdyContentLength = maxSpdyContentLength;
        this.maxHttpContentLength = maxHttpContentLength;
    }
    
    protected abstract SelectedProtocol getProtocol(final SSLEngine p0);
    
    @Override
    public ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return ChannelHandlerUtil.allocate(ctx);
    }
    
    @Override
    public void discardInboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        ctx.inboundByteBuffer().release();
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        if (this.initPipeline(ctx)) {
            ctx.nextInboundByteBuffer().writeBytes(ctx.inboundByteBuffer());
            ctx.pipeline().remove(this);
            ctx.fireInboundBufferUpdated();
        }
    }
    
    private boolean initPipeline(final ChannelHandlerContext ctx) {
        final SslHandler handler = ctx.pipeline().get(SslHandler.class);
        if (handler == null) {
            throw new IllegalStateException("SslHandler is needed for SPDY");
        }
        final SelectedProtocol protocol = this.getProtocol(handler.engine());
        switch (protocol) {
            case UNKNOWN: {
                return false;
            }
            case SPDY_2: {
                this.addSpdyHandlers(ctx, 2);
                break;
            }
            case SPDY_3: {
                this.addSpdyHandlers(ctx, 3);
                break;
            }
            case HTTP_1_0:
            case HTTP_1_1: {
                this.addHttpHandlers(ctx);
                break;
            }
            default: {
                throw new IllegalStateException("Unknown SelectedProtocol");
            }
        }
        return true;
    }
    
    protected void addSpdyHandlers(final ChannelHandlerContext ctx, final int version) {
        final ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast("spdyDecoder", new SpdyFrameDecoder(version));
        pipeline.addLast("spdyEncoder", new SpdyFrameEncoder(version));
        pipeline.addLast("spdySessionHandler", new SpdySessionHandler(version, true));
        pipeline.addLast("spdyHttpEncoder", new SpdyHttpEncoder(version));
        pipeline.addLast("spdyHttpDecoder", new SpdyHttpDecoder(version, this.maxSpdyContentLength));
        pipeline.addLast("spdyStreamIdHandler", new SpdyHttpResponseStreamIdHandler());
        pipeline.addLast("httpRquestHandler", this.createHttpRequestHandlerForSpdy());
    }
    
    protected void addHttpHandlers(final ChannelHandlerContext ctx) {
        final ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast("httpRquestDecoder", new HttpRequestDecoder());
        pipeline.addLast("httpResponseEncoder", new HttpResponseEncoder());
        pipeline.addLast("httpChunkAggregator", new HttpObjectAggregator(this.maxHttpContentLength));
        pipeline.addLast("httpRquestHandler", this.createHttpRequestHandlerForHttp());
    }
    
    protected abstract ChannelInboundMessageHandler<?> createHttpRequestHandlerForHttp();
    
    protected ChannelInboundMessageHandler<?> createHttpRequestHandlerForSpdy() {
        return this.createHttpRequestHandlerForHttp();
    }
    
    public enum SelectedProtocol
    {
        SPDY_2, 
        SPDY_3, 
        HTTP_1_1, 
        HTTP_1_0, 
        UNKNOWN;
    }
}
