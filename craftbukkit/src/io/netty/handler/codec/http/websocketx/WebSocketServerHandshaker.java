// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.channel.ChannelOutboundMessageHandler;
import io.netty.channel.ChannelInboundByteHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.channel.Channel;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;

public abstract class WebSocketServerHandshaker
{
    protected static final InternalLogger logger;
    private static final String[] EMPTY_ARRAY;
    private final String uri;
    private final String[] subprotocols;
    private final WebSocketVersion version;
    private final int maxFramePayloadLength;
    private String selectedSubprotocol;
    
    protected WebSocketServerHandshaker(final WebSocketVersion version, final String uri, final String subprotocols, final int maxFramePayloadLength) {
        this.version = version;
        this.uri = uri;
        if (subprotocols != null) {
            final String[] subprotocolArray = StringUtil.split(subprotocols, ',');
            for (int i = 0; i < subprotocolArray.length; ++i) {
                subprotocolArray[i] = subprotocolArray[i].trim();
            }
            this.subprotocols = subprotocolArray;
        }
        else {
            this.subprotocols = WebSocketServerHandshaker.EMPTY_ARRAY;
        }
        this.maxFramePayloadLength = maxFramePayloadLength;
    }
    
    public String uri() {
        return this.uri;
    }
    
    public Set<String> subprotocols() {
        final Set<String> ret = new LinkedHashSet<String>();
        Collections.addAll(ret, this.subprotocols);
        return ret;
    }
    
    public WebSocketVersion version() {
        return this.version;
    }
    
    public int maxFramePayloadLength() {
        return this.maxFramePayloadLength;
    }
    
    public ChannelFuture handshake(final Channel channel, final FullHttpRequest req) {
        return this.handshake(channel, req, null, channel.newPromise());
    }
    
    public final ChannelFuture handshake(final Channel channel, final FullHttpRequest req, final HttpHeaders responseHeaders, final ChannelPromise promise) {
        if (WebSocketServerHandshaker.logger.isDebugEnabled()) {
            WebSocketServerHandshaker.logger.debug(String.format("Channel %s WS Version %s server handshake", this.version(), channel.id()));
        }
        final FullHttpResponse response = this.newHandshakeResponse(req, responseHeaders);
        channel.write(response).addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    final ChannelPipeline p = future.channel().pipeline();
                    if (p.get(HttpObjectAggregator.class) != null) {
                        p.remove(HttpObjectAggregator.class);
                    }
                    ChannelHandlerContext ctx = p.context(HttpRequestDecoder.class);
                    if (ctx == null) {
                        ctx = p.context(HttpServerCodec.class);
                        if (ctx == null) {
                            promise.setFailure((Throwable)new IllegalStateException("No HttpDecoder and no HttpServerCodec in the pipeline"));
                            return;
                        }
                        p.addBefore(ctx.name(), "wsencoder", WebSocketServerHandshaker.this.newWebsocketDecoder());
                        p.replaceAndForward(ctx.name(), "wsdecoder", WebSocketServerHandshaker.this.newWebSocketEncoder());
                    }
                    else {
                        p.replaceAndForward(ctx.name(), "wsdecoder", WebSocketServerHandshaker.this.newWebsocketDecoder());
                        p.replace(HttpResponseEncoder.class, "wsencoder", WebSocketServerHandshaker.this.newWebSocketEncoder());
                    }
                    promise.setSuccess();
                }
                else {
                    promise.setFailure(future.cause());
                }
            }
        });
        return promise;
    }
    
    protected abstract FullHttpResponse newHandshakeResponse(final FullHttpRequest p0, final HttpHeaders p1);
    
    public ChannelFuture close(final Channel channel, final CloseWebSocketFrame frame) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        return this.close(channel, frame, channel.newPromise());
    }
    
    public ChannelFuture close(final Channel channel, final CloseWebSocketFrame frame, final ChannelPromise promise) {
        return channel.write(frame, promise).addListener((GenericFutureListener<? extends Future<Void>>)ChannelFutureListener.CLOSE);
    }
    
    protected String selectSubprotocol(final String requestedSubprotocols) {
        if (requestedSubprotocols == null || this.subprotocols.length == 0) {
            return null;
        }
        final String[] arr$;
        final String[] requestedSubprotocolArray = arr$ = StringUtil.split(requestedSubprotocols, ',');
        for (final String p : arr$) {
            final String requestedSubprotocol = p.trim();
            for (final String supportedSubprotocol : this.subprotocols) {
                if (requestedSubprotocol.equals(supportedSubprotocol)) {
                    return requestedSubprotocol;
                }
            }
        }
        return null;
    }
    
    public String selectedSubprotocol() {
        return this.selectedSubprotocol;
    }
    
    protected void setSelectedSubprotocol(final String value) {
        this.selectedSubprotocol = value;
    }
    
    protected abstract ChannelInboundByteHandler newWebsocketDecoder();
    
    protected abstract ChannelOutboundMessageHandler<WebSocketFrame> newWebSocketEncoder();
    
    static {
        logger = InternalLoggerFactory.getInstance(WebSocketServerHandshaker.class);
        EMPTY_ARRAY = new String[0];
    }
}
