// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class WebSocketServerProtocolHandler extends WebSocketProtocolHandler
{
    private static final AttributeKey<WebSocketServerHandshaker> HANDSHAKER_ATTR_KEY;
    private final String websocketPath;
    private final String subprotocols;
    private final boolean allowExtensions;
    
    public WebSocketServerProtocolHandler(final String websocketPath) {
        this(websocketPath, null, false);
    }
    
    public WebSocketServerProtocolHandler(final String websocketPath, final String subprotocols) {
        this(websocketPath, subprotocols, false);
    }
    
    public WebSocketServerProtocolHandler(final String websocketPath, final String subprotocols, final boolean allowExtensions) {
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
    }
    
    @Override
    public void afterAdd(final ChannelHandlerContext ctx) {
        final ChannelPipeline cp = ctx.pipeline();
        if (cp.get(WebSocketServerProtocolHandshakeHandler.class) == null) {
            ctx.pipeline().addBefore(ctx.name(), WebSocketServerProtocolHandshakeHandler.class.getName(), new WebSocketServerProtocolHandshakeHandler(this.websocketPath, this.subprotocols, this.allowExtensions));
        }
    }
    
    @Override
    public void messageReceived(final ChannelHandlerContext ctx, final WebSocketFrame frame) throws Exception {
        if (frame instanceof CloseWebSocketFrame) {
            final WebSocketServerHandshaker handshaker = getHandshaker(ctx);
            frame.retain();
            handshaker.close(ctx.channel(), (CloseWebSocketFrame)frame);
            return;
        }
        super.messageReceived(ctx, frame);
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        if (cause instanceof WebSocketHandshakeException) {
            final FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST, Unpooled.wrappedBuffer(cause.getMessage().getBytes()));
            ctx.channel().write(response).addListener((GenericFutureListener<? extends Future<Void>>)ChannelFutureListener.CLOSE);
        }
        else {
            ctx.close();
        }
    }
    
    static WebSocketServerHandshaker getHandshaker(final ChannelHandlerContext ctx) {
        return ctx.attr(WebSocketServerProtocolHandler.HANDSHAKER_ATTR_KEY).get();
    }
    
    static void setHandshaker(final ChannelHandlerContext ctx, final WebSocketServerHandshaker handshaker) {
        ctx.attr(WebSocketServerProtocolHandler.HANDSHAKER_ATTR_KEY).set(handshaker);
    }
    
    static ChannelHandler forbiddenHttpRequestResponder() {
        return new ChannelInboundMessageHandlerAdapter<FullHttpRequest>() {
            @Override
            public void messageReceived(final ChannelHandlerContext ctx, final FullHttpRequest msg) throws Exception {
                final FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN);
                ctx.channel().write(response);
            }
        };
    }
    
    static {
        HANDSHAKER_ATTR_KEY = new AttributeKey<WebSocketServerHandshaker>(WebSocketServerHandshaker.class.getName());
    }
}
