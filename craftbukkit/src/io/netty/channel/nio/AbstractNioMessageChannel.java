// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.nio;

import io.netty.channel.ChannelPipeline;
import java.nio.channels.SelectionKey;
import java.io.IOException;
import io.netty.channel.AbstractChannel;
import io.netty.buffer.MessageBuf;
import java.nio.channels.SelectableChannel;
import io.netty.channel.Channel;

public abstract class AbstractNioMessageChannel extends AbstractNioChannel
{
    protected AbstractNioMessageChannel(final Channel parent, final Integer id, final SelectableChannel ch, final int readInterestOp) {
        super(parent, id, ch, readInterestOp);
    }
    
    @Override
    protected AbstractNioUnsafe newUnsafe() {
        return new NioMessageUnsafe();
    }
    
    @Override
    protected void doFlushMessageBuffer(final MessageBuf<Object> buf) throws Exception {
        final int writeSpinCount = this.config().getWriteSpinCount() - 1;
        while (!buf.isEmpty()) {
            boolean wrote = false;
            for (int i = writeSpinCount; i >= 0; --i) {
                final int localFlushedAmount = this.doWriteMessages(buf, i == 0);
                if (localFlushedAmount > 0) {
                    wrote = true;
                    break;
                }
            }
            if (!wrote) {
                break;
            }
        }
    }
    
    protected abstract int doReadMessages(final MessageBuf<Object> p0) throws Exception;
    
    protected abstract int doWriteMessages(final MessageBuf<Object> p0, final boolean p1) throws Exception;
    
    private final class NioMessageUnsafe extends AbstractNioUnsafe
    {
        @Override
        public void read() {
            assert AbstractNioMessageChannel.this.eventLoop().inEventLoop();
            final SelectionKey key = AbstractNioMessageChannel.this.selectionKey();
            if (!AbstractNioMessageChannel.this.config().isAutoRead()) {
                key.interestOps(key.interestOps() & ~AbstractNioMessageChannel.this.readInterestOp);
            }
            final ChannelPipeline pipeline = AbstractNioMessageChannel.this.pipeline();
            final MessageBuf<Object> msgBuf = pipeline.inboundMessageBuffer();
            boolean closed = false;
            boolean read = false;
            boolean firedChannelReadSuspended = false;
            try {
                while (true) {
                    final int localReadAmount = AbstractNioMessageChannel.this.doReadMessages(msgBuf);
                    if (localReadAmount > 0) {
                        read = true;
                    }
                    else {
                        if (localReadAmount == 0) {
                            break;
                        }
                        if (localReadAmount < 0) {
                            closed = true;
                            break;
                        }
                        continue;
                    }
                }
            }
            catch (Throwable t) {
                if (read) {
                    read = false;
                    pipeline.fireInboundBufferUpdated();
                }
                if (t instanceof IOException) {
                    closed = true;
                }
                else if (!closed) {
                    firedChannelReadSuspended = true;
                    pipeline.fireChannelReadSuspended();
                }
                AbstractNioMessageChannel.this.pipeline().fireExceptionCaught(t);
            }
            finally {
                if (read) {
                    pipeline.fireInboundBufferUpdated();
                }
                if (closed && AbstractNioMessageChannel.this.isOpen()) {
                    this.close(this.voidFuture());
                }
                else if (!firedChannelReadSuspended) {
                    pipeline.fireChannelReadSuspended();
                }
            }
        }
    }
}
