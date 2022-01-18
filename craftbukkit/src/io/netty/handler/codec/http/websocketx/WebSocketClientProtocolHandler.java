// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaders;
import java.net.URI;

public class WebSocketClientProtocolHandler extends WebSocketProtocolHandler
{
    private final WebSocketClientHandshaker handshaker;
    private final boolean handleCloseFrames;
    
    public WebSocketClientProtocolHandler(final URI webSocketURL, final WebSocketVersion version, final String subprotocol, final boolean allowExtensions, final HttpHeaders customHeaders, final int maxFramePayloadLength, final boolean handleCloseFrames) {
        this(WebSocketClientHandshakerFactory.newHandshaker(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength), handleCloseFrames);
    }
    
    public WebSocketClientProtocolHandler(final URI webSocketURL, final WebSocketVersion version, final String subprotocol, final boolean allowExtensions, final HttpHeaders customHeaders, final int maxFramePayloadLength) {
        this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, true);
    }
    
    public WebSocketClientProtocolHandler(final WebSocketClientHandshaker handshaker, final boolean handleCloseFrames) {
        this.handshaker = handshaker;
        this.handleCloseFrames = handleCloseFrames;
    }
    
    public WebSocketClientProtocolHandler(final WebSocketClientHandshaker handshaker) {
        this(handshaker, true);
    }
    
    @Override
    public void messageReceived(final ChannelHandlerContext ctx, final WebSocketFrame frame) throws Exception {
        if (this.handleCloseFrames && frame instanceof CloseWebSocketFrame) {
            ctx.close();
            return;
        }
        super.messageReceived(ctx, frame);
    }
    
    @Override
    public void afterAdd(final ChannelHandlerContext ctx) {
        final ChannelPipeline cp = ctx.pipeline();
        if (cp.get(WebSocketClientProtocolHandshakeHandler.class) == null) {
            ctx.pipeline().addBefore(ctx.name(), WebSocketClientProtocolHandshakeHandler.class.getName(), new WebSocketClientProtocolHandshakeHandler(this.handshaker));
        }
    }
}
