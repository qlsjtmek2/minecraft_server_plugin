// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.string;

import io.netty.channel.ChannelHandlerContext;
import java.nio.charset.Charset;
import io.netty.channel.ChannelHandler;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToMessageDecoder;

@ChannelHandler.Sharable
public class StringDecoder extends MessageToMessageDecoder<ByteBuf>
{
    private final Charset charset;
    
    public StringDecoder() {
        this(Charset.defaultCharset());
    }
    
    public StringDecoder(final Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final ByteBuf msg) throws Exception {
        return msg.toString(this.charset);
    }
}
