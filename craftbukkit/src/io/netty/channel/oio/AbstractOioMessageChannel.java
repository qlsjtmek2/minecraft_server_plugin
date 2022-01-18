// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.oio;

import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelPipeline;
import java.io.IOException;
import io.netty.channel.Channel;

public abstract class AbstractOioMessageChannel extends AbstractOioChannel
{
    protected AbstractOioMessageChannel(final Channel parent, final Integer id) {
        super(parent, id);
    }
    
    @Override
    protected void doRead() {
        final ChannelPipeline pipeline = this.pipeline();
        final MessageBuf<Object> msgBuf = pipeline.inboundMessageBuffer();
        boolean closed = false;
        boolean read = false;
        boolean firedChannelReadSuspended = false;
        try {
            final int localReadAmount = this.doReadMessages(msgBuf);
            if (localReadAmount > 0) {
                read = true;
            }
            else if (localReadAmount < 0) {
                closed = true;
            }
        }
        catch (Throwable t) {
            if (read) {
                read = false;
                pipeline.fireInboundBufferUpdated();
            }
            firedChannelReadSuspended = true;
            pipeline.fireChannelReadSuspended();
            pipeline.fireExceptionCaught(t);
            if (t instanceof IOException) {
                this.unsafe().close(this.unsafe().voidFuture());
            }
        }
        finally {
            if (read) {
                pipeline.fireInboundBufferUpdated();
            }
            if (!firedChannelReadSuspended) {
                pipeline.fireChannelReadSuspended();
            }
            if (closed && this.isOpen()) {
                this.unsafe().close(this.unsafe().voidFuture());
            }
        }
    }
    
    @Override
    protected void doFlushMessageBuffer(final MessageBuf<Object> buf) throws Exception {
        while (!buf.isEmpty()) {
            this.doWriteMessages(buf);
        }
    }
    
    protected abstract int doReadMessages(final MessageBuf<Object> p0) throws Exception;
    
    protected abstract void doWriteMessages(final MessageBuf<Object> p0) throws Exception;
}
