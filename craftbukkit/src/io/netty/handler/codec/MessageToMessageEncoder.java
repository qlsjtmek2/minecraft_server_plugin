// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundMessageHandlerAdapter;

public abstract class MessageToMessageEncoder<I> extends ChannelOutboundMessageHandlerAdapter<I>
{
    protected MessageToMessageEncoder() {
    }
    
    protected MessageToMessageEncoder(final Class<? extends I> outboundMessageType) {
        super(outboundMessageType);
    }
    
    @Override
    public final void flush(final ChannelHandlerContext ctx, final I msg) throws Exception {
        try {
            ctx.nextOutboundMessageBuffer().unfoldAndAdd(this.encode(ctx, msg));
        }
        catch (CodecException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new CodecException(e2);
        }
    }
    
    protected abstract Object encode(final ChannelHandlerContext p0, final I p1) throws Exception;
}
