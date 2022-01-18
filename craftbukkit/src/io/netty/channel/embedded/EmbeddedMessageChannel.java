// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.embedded;

import io.netty.buffer.BufType;
import io.netty.buffer.Buf;
import java.util.Collection;
import io.netty.buffer.MessageBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelMetadata;

public class EmbeddedMessageChannel extends AbstractEmbeddedChannel<Object>
{
    private static final ChannelMetadata METADATA;
    
    public EmbeddedMessageChannel(final ChannelHandler... handlers) {
        super(Unpooled.messageBuffer(), handlers);
    }
    
    @Override
    public ChannelMetadata metadata() {
        return EmbeddedMessageChannel.METADATA;
    }
    
    @Override
    public MessageBuf<Object> inboundBuffer() {
        return this.pipeline().inboundMessageBuffer();
    }
    
    @Override
    public MessageBuf<Object> lastOutboundBuffer() {
        return (MessageBuf<Object>)this.lastOutboundBuffer;
    }
    
    @Override
    public Object readOutbound() {
        return this.lastOutboundBuffer().poll();
    }
    
    @Override
    protected void writeInbound0(final Object data) {
        this.inboundBuffer().add(data);
    }
    
    @Override
    protected boolean hasReadableOutboundBuffer() {
        return !this.lastOutboundBuffer().isEmpty();
    }
    
    @Override
    protected void doFlushMessageBuffer(final MessageBuf<Object> buf) throws Exception {
        buf.drainTo(this.lastOutboundBuffer());
    }
    
    static {
        METADATA = new ChannelMetadata(BufType.MESSAGE, false);
    }
}
