// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

public abstract class MessageToMessageDecoder<I> extends ChannelInboundMessageHandlerAdapter<I>
{
    protected MessageToMessageDecoder() {
    }
    
    protected MessageToMessageDecoder(final Class<? extends I> inboundMessageType) {
        super(inboundMessageType);
    }
    
    @Override
    public final void messageReceived(final ChannelHandlerContext ctx, final I msg) throws Exception {
        ctx.nextInboundMessageBuffer().unfoldAndAdd(this.decode(ctx, msg));
    }
    
    protected abstract Object decode(final ChannelHandlerContext p0, final I p1) throws Exception;
}
