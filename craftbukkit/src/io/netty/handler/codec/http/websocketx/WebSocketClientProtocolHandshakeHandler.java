// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

class WebSocketClientProtocolHandshakeHandler extends ChannelInboundMessageHandlerAdapter<FullHttpResponse>
{
    private final WebSocketClientHandshaker handshaker;
    
    public WebSocketClientProtocolHandshakeHandler(final WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.handshaker.handshake(ctx.channel()).addListener((GenericFutureListener<? extends Future<Void>>)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
    
    @Override
    public void messageReceived(final ChannelHandlerContext ctx, final FullHttpResponse msg) throws Exception {
        if (!this.handshaker.isHandshakeComplete()) {
            this.handshaker.finishHandshake(ctx.channel(), msg);
            ctx.pipeline().removeAndForward(this);
            return;
        }
        throw new IllegalStateException("WebSocketClientHandshaker should have been non finished yet");
    }
}
