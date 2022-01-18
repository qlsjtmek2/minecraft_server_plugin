// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

abstract class WebSocketProtocolHandler extends ChannelInboundMessageHandlerAdapter<WebSocketFrame>
{
    @Override
    public void messageReceived(final ChannelHandlerContext ctx, final WebSocketFrame frame) throws Exception {
        if (frame instanceof PingWebSocketFrame) {
            frame.data().retain();
            ctx.channel().write(new PongWebSocketFrame(frame.data()));
            return;
        }
        if (frame instanceof PongWebSocketFrame) {
            return;
        }
        frame.retain();
        ctx.nextInboundMessageBuffer().add(frame);
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        ctx.close();
    }
}
