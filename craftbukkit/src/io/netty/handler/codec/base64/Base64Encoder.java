// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.base64;

import io.netty.channel.ChannelHandlerUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelOutboundMessageHandlerAdapter;

@ChannelHandler.Sharable
public class Base64Encoder extends ChannelOutboundMessageHandlerAdapter<ByteBuf>
{
    private final boolean breakLines;
    private final Base64Dialect dialect;
    
    public Base64Encoder() {
        this(true);
    }
    
    public Base64Encoder(final boolean breakLines) {
        this(breakLines, Base64Dialect.STANDARD);
    }
    
    public Base64Encoder(final boolean breakLines, final Base64Dialect dialect) {
        if (dialect == null) {
            throw new NullPointerException("dialect");
        }
        this.breakLines = breakLines;
        this.dialect = dialect;
    }
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final ByteBuf msg) throws Exception {
        final ByteBuf buf = Base64.encode(msg, msg.readerIndex(), msg.readableBytes(), this.breakLines, this.dialect);
        ChannelHandlerUtil.addToNextOutboundBuffer(ctx, buf);
    }
}
