// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.oio;

import io.netty.buffer.BufType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.ChannelOption;
import java.io.IOException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelMetadata;

public abstract class AbstractOioByteChannel extends AbstractOioChannel
{
    private volatile boolean inputShutdown;
    private static final ChannelMetadata METADATA;
    
    protected AbstractOioByteChannel(final Channel parent, final Integer id) {
        super(parent, id);
    }
    
    protected boolean isInputShutdown() {
        return this.inputShutdown;
    }
    
    @Override
    public ChannelMetadata metadata() {
        return AbstractOioByteChannel.METADATA;
    }
    
    protected boolean checkInputShutdown() {
        if (this.inputShutdown) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException ex) {}
            return true;
        }
        return false;
    }
    
    @Override
    protected void doRead() {
        if (this.checkInputShutdown()) {
            return;
        }
        final ChannelPipeline pipeline = this.pipeline();
        final ByteBuf byteBuf = pipeline.inboundByteBuffer();
        boolean closed = false;
        boolean read = false;
        boolean firedInboundBufferSuspeneded = false;
        try {
            while (true) {
                final int localReadAmount = this.doReadBytes(byteBuf);
                if (localReadAmount > 0) {
                    read = true;
                }
                else if (localReadAmount < 0) {
                    closed = true;
                }
                final int available = this.available();
                if (available <= 0) {
                    break;
                }
                if (byteBuf.isWritable()) {
                    continue;
                }
                final int capacity = byteBuf.capacity();
                final int maxCapacity = byteBuf.maxCapacity();
                if (capacity == maxCapacity) {
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
                else {
                    final int writerIndex = byteBuf.writerIndex();
                    if (writerIndex + available > maxCapacity) {
                        byteBuf.capacity(maxCapacity);
                    }
                    else {
                        byteBuf.ensureWritable(available);
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
                pipeline.fireExceptionCaught(t);
            }
            else {
                firedInboundBufferSuspeneded = true;
                pipeline.fireChannelReadSuspended();
                pipeline.fireExceptionCaught(t);
                this.unsafe().close(this.unsafe().voidFuture());
            }
        }
        finally {
            if (read) {
                pipeline.fireInboundBufferUpdated();
            }
            if (closed) {
                this.inputShutdown = true;
                if (this.isOpen()) {
                    if (Boolean.TRUE.equals(this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                        pipeline.fireUserEventTriggered((Object)ChannelInputShutdownEvent.INSTANCE);
                    }
                    else {
                        this.unsafe().close(this.unsafe().voidFuture());
                    }
                }
            }
            else if (!firedInboundBufferSuspeneded) {
                pipeline.fireChannelReadSuspended();
            }
        }
    }
    
    @Override
    protected void doFlushByteBuffer(final ByteBuf buf) throws Exception {
        while (buf.isReadable()) {
            this.doWriteBytes(buf);
        }
        buf.clear();
    }
    
    protected abstract int available();
    
    protected abstract int doReadBytes(final ByteBuf p0) throws Exception;
    
    protected abstract void doWriteBytes(final ByteBuf p0) throws Exception;
    
    static {
        METADATA = new ChannelMetadata(BufType.BYTE, false);
    }
}
