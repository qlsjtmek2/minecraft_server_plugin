// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.stream;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.buffer.Buf;
import io.netty.channel.ChannelException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerUtil;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.nio.channels.ClosedChannelException;
import io.netty.channel.ChannelPromise;
import io.netty.buffer.Unpooled;
import java.util.concurrent.atomic.AtomicInteger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.buffer.MessageBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.channel.ChannelOutboundMessageHandler;
import io.netty.channel.ChannelDuplexHandler;

public class ChunkedWriteHandler extends ChannelDuplexHandler implements ChannelOutboundMessageHandler<Object>
{
    private static final InternalLogger logger;
    private final MessageBuf<Object> queue;
    private final int maxPendingWrites;
    private volatile ChannelHandlerContext ctx;
    private final AtomicInteger pendingWrites;
    private Object currentEvent;
    
    public ChunkedWriteHandler() {
        this(4);
    }
    
    public ChunkedWriteHandler(final int maxPendingWrites) {
        this.queue = Unpooled.messageBuffer();
        this.pendingWrites = new AtomicInteger();
        if (maxPendingWrites <= 0) {
            throw new IllegalArgumentException("maxPendingWrites: " + maxPendingWrites + " (expected: > 0)");
        }
        this.maxPendingWrites = maxPendingWrites;
    }
    
    @Override
    public MessageBuf<Object> newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        return this.queue;
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.queue.release();
    }
    
    private boolean isWritable() {
        return this.pendingWrites.get() < this.maxPendingWrites;
    }
    
    public void resumeTransfer() {
        final ChannelHandlerContext ctx = this.ctx;
        if (ctx == null) {
            return;
        }
        if (ctx.executor().inEventLoop()) {
            try {
                this.doFlush(ctx);
            }
            catch (Exception e) {
                if (ChunkedWriteHandler.logger.isWarnEnabled()) {
                    ChunkedWriteHandler.logger.warn("Unexpected exception while sending chunks.", e);
                }
            }
        }
        else {
            ctx.executor().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        ChunkedWriteHandler.this.doFlush(ctx);
                    }
                    catch (Exception e) {
                        if (ChunkedWriteHandler.logger.isWarnEnabled()) {
                            ChunkedWriteHandler.logger.warn("Unexpected exception while sending chunks.", e);
                        }
                    }
                }
            });
        }
    }
    
    @Override
    public void read(final ChannelHandlerContext ctx) {
        ctx.read();
    }
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.queue.add(promise);
        if (this.isWritable() || !ctx.channel().isActive()) {
            this.doFlush(ctx);
        }
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        ctx.fireInboundBufferUpdated();
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        this.doFlush(ctx);
        super.channelInactive(ctx);
    }
    
    private void discard(final ChannelHandlerContext ctx, Throwable cause) {
        boolean fireExceptionCaught = false;
        boolean success = true;
        while (true) {
            Object currentEvent = this.currentEvent;
            if (this.currentEvent == null) {
                currentEvent = this.queue.poll();
            }
            else {
                this.currentEvent = null;
            }
            if (currentEvent == null) {
                break;
            }
            if (currentEvent instanceof ChunkedInput) {
                final ChunkedInput<?> in = (ChunkedInput<?>)currentEvent;
                try {
                    if (!in.isEndOfInput()) {
                        success = false;
                    }
                }
                catch (Exception e) {
                    success = false;
                    ChunkedWriteHandler.logger.warn(ChunkedInput.class.getSimpleName() + ".isEndOfInput() failed", e);
                }
                closeInput(in);
            }
            else {
                if (!(currentEvent instanceof ChannelPromise)) {
                    continue;
                }
                final ChannelPromise f = (ChannelPromise)currentEvent;
                if (!success) {
                    fireExceptionCaught = true;
                    if (cause == null) {
                        cause = new ClosedChannelException();
                    }
                    f.setFailure(cause);
                }
                else {
                    f.setSuccess();
                }
            }
        }
        if (fireExceptionCaught) {
            ctx.fireExceptionCaught(cause);
        }
    }
    
    private void doFlush(final ChannelHandlerContext ctx) throws Exception {
        final Channel channel = ctx.channel();
        if (!channel.isActive()) {
            this.discard(ctx, null);
            return;
        }
        while (this.isWritable()) {
            if (this.currentEvent == null) {
                this.currentEvent = this.queue.poll();
            }
            if (this.currentEvent == null) {
                break;
            }
            final Object currentEvent = this.currentEvent;
            if (currentEvent instanceof ChannelPromise) {
                this.currentEvent = null;
                ctx.flush((ChannelPromise)currentEvent);
            }
            else if (currentEvent instanceof ChunkedInput) {
                final ChunkedInput<?> chunks = (ChunkedInput<?>)currentEvent;
                boolean endOfInput;
                boolean suspend;
                try {
                    final boolean read = this.readChunk(ctx, chunks);
                    endOfInput = chunks.isEndOfInput();
                    suspend = (!read && !endOfInput);
                }
                catch (Throwable t) {
                    this.currentEvent = null;
                    if (ctx.executor().inEventLoop()) {
                        ctx.fireExceptionCaught(t);
                    }
                    else {
                        ctx.executor().execute(new Runnable() {
                            @Override
                            public void run() {
                                ctx.fireExceptionCaught(t);
                            }
                        });
                    }
                    closeInput(chunks);
                    break;
                }
                if (suspend) {
                    break;
                }
                this.pendingWrites.incrementAndGet();
                final ChannelFuture f = ctx.flush();
                if (endOfInput) {
                    this.currentEvent = null;
                    f.addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
                        @Override
                        public void operationComplete(final ChannelFuture future) throws Exception {
                            ChunkedWriteHandler.this.pendingWrites.decrementAndGet();
                            ChunkedWriteHandler.closeInput(chunks);
                        }
                    });
                }
                else if (this.isWritable()) {
                    f.addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
                        @Override
                        public void operationComplete(final ChannelFuture future) throws Exception {
                            ChunkedWriteHandler.this.pendingWrites.decrementAndGet();
                            if (!future.isSuccess()) {
                                ChunkedWriteHandler.closeInput((ChunkedInput<?>)currentEvent);
                            }
                        }
                    });
                }
                else {
                    f.addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
                        @Override
                        public void operationComplete(final ChannelFuture future) throws Exception {
                            ChunkedWriteHandler.this.pendingWrites.decrementAndGet();
                            if (!future.isSuccess()) {
                                ChunkedWriteHandler.closeInput((ChunkedInput<?>)currentEvent);
                            }
                            else if (ChunkedWriteHandler.this.isWritable()) {
                                ChunkedWriteHandler.this.resumeTransfer();
                            }
                        }
                    });
                }
            }
            else {
                ChannelHandlerUtil.addToNextOutboundBuffer(ctx, currentEvent);
                this.currentEvent = null;
            }
            if (!channel.isActive()) {
                this.discard(ctx, new ClosedChannelException());
            }
        }
    }
    
    protected boolean readChunk(final ChannelHandlerContext ctx, final ChunkedInput<?> chunks) throws Exception {
        if (chunks instanceof ChunkedByteInput) {
            return ((ChunkedByteInput)chunks).readChunk(ctx.nextOutboundByteBuffer());
        }
        if (chunks instanceof ChunkedMessageInput) {
            return ((ChunkedMessageInput)chunks).readChunk(ctx.nextOutboundMessageBuffer());
        }
        throw new IllegalArgumentException("ChunkedInput instance " + chunks + " not supported");
    }
    
    static void closeInput(final ChunkedInput<?> chunks) {
        try {
            chunks.close();
        }
        catch (Throwable t) {
            if (ChunkedWriteHandler.logger.isWarnEnabled()) {
                ChunkedWriteHandler.logger.warn("Failed to close a chunked input.", t);
            }
        }
    }
    
    @Override
    public void beforeRemove(final ChannelHandlerContext ctx) throws Exception {
        this.doFlush(ctx);
    }
    
    @Override
    public void afterRemove(final ChannelHandlerContext ctx) throws Exception {
        this.discard(ctx, new ChannelException(ChunkedWriteHandler.class.getSimpleName() + " removed from pipeline."));
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(ChunkedWriteHandler.class);
    }
}
