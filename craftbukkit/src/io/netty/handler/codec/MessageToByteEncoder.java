// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundMessageHandlerAdapter;

public abstract class MessageToByteEncoder<I> extends ChannelOutboundMessageHandlerAdapter<I>
{
    protected MessageToByteEncoder() {
    }
    
    protected MessageToByteEncoder(final Class<? extends I> outboundMessageType) {
        super(outboundMessageType);
    }
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final I msg) throws Exception {
        try {
            this.encode(ctx, msg, ctx.nextOutboundByteBuffer());
        }
        catch (CodecException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new CodecException(e2);
        }
    }
    
    protected abstract void encode(final ChannelHandlerContext p0, final I p1, final ByteBuf p2) throws Exception;
}
