// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.bytes;

import io.netty.buffer.BufType;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOutboundMessageHandlerAdapter;

@ChannelHandler.Sharable
public class ByteArrayEncoder extends ChannelOutboundMessageHandlerAdapter<byte[]>
{
    @Override
    public void flush(final ChannelHandlerContext ctx, final byte[] msg) throws Exception {
        if (msg.length == 0) {
            return;
        }
        switch (ctx.nextOutboundBufferType()) {
            case BYTE: {
                ctx.nextOutboundByteBuffer().writeBytes(msg);
                break;
            }
            case MESSAGE: {
                ctx.nextOutboundMessageBuffer().add(Unpooled.wrappedBuffer(msg));
                break;
            }
            default: {
                throw new Error();
            }
        }
    }
}
