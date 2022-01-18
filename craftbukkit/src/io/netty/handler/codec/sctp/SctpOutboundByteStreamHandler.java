// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.sctp;

import io.netty.buffer.MessageBuf;
import io.netty.handler.codec.EncoderException;
import io.netty.channel.sctp.SctpMessage;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelPromise;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundByteHandlerAdapter;

public class SctpOutboundByteStreamHandler extends ChannelOutboundByteHandlerAdapter
{
    private final int streamIdentifier;
    private final int protocolIdentifier;
    
    public SctpOutboundByteStreamHandler(final int streamIdentifier, final int protocolIdentifier) {
        this.streamIdentifier = streamIdentifier;
        this.protocolIdentifier = protocolIdentifier;
    }
    
    @Override
    protected void flush(final ChannelHandlerContext ctx, final ByteBuf in, final ChannelPromise promise) throws Exception {
        try {
            final MessageBuf<Object> out = ctx.nextOutboundMessageBuffer();
            final ByteBuf payload = Unpooled.buffer(in.readableBytes());
            payload.writeBytes(in);
            out.add(new SctpMessage(this.streamIdentifier, this.protocolIdentifier, payload));
        }
        catch (Throwable t) {
            ctx.fireExceptionCaught((Throwable)new EncoderException(t));
        }
        ctx.flush(promise);
    }
}
