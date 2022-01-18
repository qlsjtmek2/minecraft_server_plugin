// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.embedded;

import io.netty.buffer.BufType;
import io.netty.buffer.Buf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelMetadata;
import io.netty.buffer.ByteBuf;

public class EmbeddedByteChannel extends AbstractEmbeddedChannel<ByteBuf>
{
    private static final ChannelMetadata METADATA;
    
    public EmbeddedByteChannel(final ChannelHandler... handlers) {
        super(Unpooled.buffer(), handlers);
    }
    
    @Override
    public ChannelMetadata metadata() {
        return EmbeddedByteChannel.METADATA;
    }
    
    @Override
    public ByteBuf inboundBuffer() {
        return this.pipeline().inboundByteBuffer();
    }
    
    @Override
    public ByteBuf lastOutboundBuffer() {
        return (ByteBuf)this.lastOutboundBuffer;
    }
    
    @Override
    public ByteBuf readOutbound() {
        if (!this.lastOutboundBuffer().isReadable()) {
            return null;
        }
        try {
            return this.lastOutboundBuffer().readBytes(this.lastOutboundBuffer().readableBytes());
        }
        finally {
            this.lastOutboundBuffer().clear();
        }
    }
    
    @Override
    protected void writeInbound0(final ByteBuf data) {
        this.inboundBuffer().writeBytes(data);
    }
    
    @Override
    protected boolean hasReadableOutboundBuffer() {
        return this.lastOutboundBuffer().isReadable();
    }
    
    @Override
    protected void doFlushByteBuffer(final ByteBuf buf) throws Exception {
        this.lastOutboundBuffer().writeBytes(buf);
    }
    
    static {
        METADATA = new ChannelMetadata(BufType.BYTE, false);
    }
}
