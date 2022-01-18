// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.string;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import java.nio.charset.Charset;
import io.netty.buffer.BufType;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOutboundMessageHandlerAdapter;

@ChannelHandler.Sharable
public class StringEncoder extends ChannelOutboundMessageHandlerAdapter<CharSequence>
{
    private final BufType nextBufferType;
    private final Charset charset;
    
    public StringEncoder(final BufType nextBufferType) {
        this(nextBufferType, Charset.defaultCharset());
    }
    
    public StringEncoder(final BufType nextBufferType, final Charset charset) {
        if (nextBufferType == null) {
            throw new NullPointerException("nextBufferType");
        }
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.nextBufferType = nextBufferType;
        this.charset = charset;
    }
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final CharSequence msg) throws Exception {
        final ByteBuf encoded = Unpooled.copiedBuffer(msg, this.charset);
        switch (this.nextBufferType) {
            case BYTE: {
                ctx.nextOutboundByteBuffer().writeBytes(encoded);
                break;
            }
            case MESSAGE: {
                ctx.nextOutboundMessageBuffer().add(encoded);
                break;
            }
            default: {
                throw new Error();
            }
        }
    }
}
