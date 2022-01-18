// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.nio;

import java.nio.channels.ClosedChannelException;
import io.netty.channel.ChannelPipeline;
import java.nio.channels.SelectionKey;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.ChannelOption;
import java.io.IOException;
import io.netty.channel.AbstractChannel;
import java.nio.channels.WritableByteChannel;
import io.netty.channel.ChannelPromise;
import io.netty.channel.FileRegion;
import io.netty.buffer.ByteBuf;
import java.nio.channels.SelectableChannel;
import io.netty.channel.Channel;

public abstract class AbstractNioByteChannel extends AbstractNioChannel
{
    protected AbstractNioByteChannel(final Channel parent, final Integer id, final SelectableChannel ch) {
        super(parent, id, ch, 1);
    }
    
    @Override
    protected AbstractNioUnsafe newUnsafe() {
        return new NioByteUnsafe();
    }
    
    @Override
    protected void doFlushByteBuffer(final ByteBuf buf) throws Exception {
        if (!buf.isReadable()) {
            buf.clear();
            return;
        }
        for (int i = this.config().getWriteSpinCount() - 1; i >= 0; --i) {
            final int localFlushedAmount = this.doWriteBytes(buf, i == 0);
            if (localFlushedAmount > 0) {
                break;
            }
            if (!buf.isReadable()) {
                buf.clear();
                break;
            }
        }
    }
    
    @Override
    protected void doFlushFileRegion(final FileRegion region, final ChannelPromise promise) throws Exception {
        if (this.javaChannel() instanceof WritableByteChannel) {
            final TransferTask transferTask = new TransferTask(region, (WritableByteChannel)this.javaChannel(), promise);
            transferTask.transfer();
            return;
        }
        throw new UnsupportedOperationException("Underlying Channel is not of instance " + WritableByteChannel.class);
    }
    
    protected abstract int doReadBytes(final ByteBuf p0) throws Exception;
    
    protected abstract int doWriteBytes(final ByteBuf p0, final boolean p1) throws Exception;
    
    private final class NioByteUnsafe extends AbstractNioUnsafe
    {
        @Override
        public void read() {
            assert AbstractNioByteChannel.this.eventLoop().inEventLoop();
            final SelectionKey key = AbstractNioByteChannel.this.selectionKey();
            if (!AbstractNioByteChannel.this.config().isAutoRead()) {
                key.interestOps(key.interestOps() & ~AbstractNioByteChannel.this.readInterestOp);
            }
            final ChannelPipeline pipeline = AbstractNioByteChannel.this.pipeline();
            final ByteBuf byteBuf = pipeline.inboundByteBuffer();
            boolean closed = false;
            boolean read = false;
            boolean firedChannelReadSuspended = false;
            try {
                AbstractChannel.expandReadBuffer(byteBuf);
            Label_0203:
                while (true) {
                    final int localReadAmount = AbstractNioByteChannel.this.doReadBytes(byteBuf);
                    if (localReadAmount > 0) {
                        read = true;
                    }
                    else if (localReadAmount < 0) {
                        closed = true;
                        break;
                    }
                    switch (AbstractChannel.expandReadBuffer(byteBuf)) {
                        case 0: {
                            break Label_0203;
                        }
                        case 1: {
                            continue;
                        }
                        case 2: {
                            if (!read) {
                                continue;
                            }
                            read = false;
                            pipeline.fireInboundBufferUpdated();
                            if (!byteBuf.isWritable()) {
                                throw new IllegalStateException("an inbound handler whose buffer is full must consume at least one byte.");
                            }
                            continue;
                        }
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
                AbstractNioByteChannel.this.pipeline().fireExceptionCaught(t);
            }
            finally {
                if (read) {
                    pipeline.fireInboundBufferUpdated();
                }
                if (closed) {
                    AbstractNioByteChannel.this.setInputShutdown();
                    if (AbstractNioByteChannel.this.isOpen()) {
                        if (Boolean.TRUE.equals(AbstractNioByteChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                            key.interestOps(key.interestOps() & ~AbstractNioByteChannel.this.readInterestOp);
                            pipeline.fireUserEventTriggered((Object)ChannelInputShutdownEvent.INSTANCE);
                        }
                        else {
                            this.close(this.voidFuture());
                        }
                    }
                }
                else if (!firedChannelReadSuspended) {
                    pipeline.fireChannelReadSuspended();
                }
            }
        }
    }
    
    private final class TransferTask implements NioTask<SelectableChannel>
    {
        private long writtenBytes;
        private final FileRegion region;
        private final WritableByteChannel wch;
        private final ChannelPromise promise;
        
        TransferTask(final FileRegion region, final WritableByteChannel wch, final ChannelPromise promise) {
            this.region = region;
            this.wch = wch;
            this.promise = promise;
        }
        
        void transfer() {
            try {
                while (true) {
                    final long localWrittenBytes = this.region.transferTo(this.wch, this.writtenBytes);
                    if (localWrittenBytes == 0L) {
                        AbstractNioByteChannel.this.eventLoop().executeWhenWritable(AbstractNioByteChannel.this, this);
                        return;
                    }
                    if (localWrittenBytes == -1L) {
                        AbstractChannel.checkEOF(this.region, this.writtenBytes);
                        this.promise.setSuccess();
                        return;
                    }
                    this.writtenBytes += localWrittenBytes;
                    if (this.writtenBytes >= this.region.count()) {
                        this.region.release();
                        this.promise.setSuccess();
                    }
                }
            }
            catch (Throwable cause) {
                this.region.release();
                this.promise.setFailure(cause);
            }
        }
        
        @Override
        public void channelReady(final SelectableChannel ch, final SelectionKey key) throws Exception {
            this.transfer();
        }
        
        @Override
        public void channelUnregistered(final SelectableChannel ch, final Throwable cause) throws Exception {
            if (cause != null) {
                this.promise.setFailure(cause);
                return;
            }
            if (this.writtenBytes < this.region.count()) {
                this.region.release();
                if (!AbstractNioByteChannel.this.isOpen()) {
                    this.promise.setFailure((Throwable)new ClosedChannelException());
                }
                else {
                    this.promise.setFailure((Throwable)new IllegalStateException("Channel was unregistered before the region could be fully written"));
                }
            }
        }
    }
}
