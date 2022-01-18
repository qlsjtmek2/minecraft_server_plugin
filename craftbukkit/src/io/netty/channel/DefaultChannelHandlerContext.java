// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import java.util.concurrent.ConcurrentLinkedQueue;
import io.netty.buffer.Unpooled;
import java.util.Queue;
import io.netty.buffer.BufType;
import java.nio.channels.ClosedChannelException;
import java.net.SocketAddress;
import io.netty.buffer.ByteBufAllocator;
import java.util.Collection;
import io.netty.buffer.Buf;
import io.netty.util.concurrent.EventExecutorGroup;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.DefaultAttributeMap;

final class DefaultChannelHandlerContext extends DefaultAttributeMap implements ChannelHandlerContext
{
    private static final int FLAG_REMOVED = 1;
    private static final int FLAG_FREED = 2;
    volatile DefaultChannelHandlerContext next;
    volatile DefaultChannelHandlerContext prev;
    private final Channel channel;
    private final DefaultChannelPipeline pipeline;
    private final String name;
    private final ChannelHandler handler;
    final EventExecutor executor;
    private ChannelFuture succeededFuture;
    private final MessageBuf<Object> inMsgBuf;
    private final ByteBuf inByteBuf;
    private MessageBuf<Object> outMsgBuf;
    private ByteBuf outByteBuf;
    private int flags;
    private volatile MessageBridge inMsgBridge;
    private volatile MessageBridge outMsgBridge;
    private volatile ByteBridge inByteBridge;
    private volatile ByteBridge outByteBridge;
    private static final AtomicReferenceFieldUpdater<DefaultChannelHandlerContext, MessageBridge> IN_MSG_BRIDGE_UPDATER;
    private static final AtomicReferenceFieldUpdater<DefaultChannelHandlerContext, MessageBridge> OUT_MSG_BRIDGE_UPDATER;
    private static final AtomicReferenceFieldUpdater<DefaultChannelHandlerContext, ByteBridge> IN_BYTE_BRIDGE_UPDATER;
    private static final AtomicReferenceFieldUpdater<DefaultChannelHandlerContext, ByteBridge> OUT_BYTE_BRIDGE_UPDATER;
    private Runnable invokeInboundBufferUpdatedTask;
    private Runnable fireInboundBufferUpdated0Task;
    private Runnable invokeChannelReadSuspendedTask;
    private Runnable invokeRead0Task;
    
    DefaultChannelHandlerContext(final DefaultChannelPipeline pipeline, final EventExecutorGroup group, final String name, final ChannelHandler handler) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (handler == null) {
            throw new NullPointerException("handler");
        }
        this.channel = pipeline.channel;
        this.pipeline = pipeline;
        this.name = name;
        this.handler = handler;
        if (group != null) {
            EventExecutor childExecutor = pipeline.childExecutors.get(group);
            if (childExecutor == null) {
                childExecutor = group.next();
                pipeline.childExecutors.put(group, childExecutor);
            }
            this.executor = childExecutor;
        }
        else {
            this.executor = null;
        }
        if (handler instanceof ChannelInboundHandler) {
            Buf buf;
            try {
                buf = ((ChannelInboundHandler)handler).newInboundBuffer(this);
            }
            catch (Exception e) {
                throw new ChannelPipelineException(handler.getClass().getSimpleName() + ".newInboundBuffer() raised an exception.", e);
            }
            if (buf instanceof ByteBuf) {
                this.inByteBuf = (ByteBuf)buf;
                this.inMsgBuf = null;
            }
            else {
                if (!(buf instanceof MessageBuf)) {
                    throw new ChannelPipelineException(handler.getClass().getSimpleName() + ".newInboundBuffer() returned neither " + ByteBuf.class.getSimpleName() + " nor " + MessageBuf.class.getSimpleName() + ": " + buf);
                }
                this.inMsgBuf = (MessageBuf<Object>)buf;
                this.inByteBuf = null;
            }
        }
        else {
            this.inByteBuf = null;
            this.inMsgBuf = null;
        }
        if (handler instanceof ChannelOutboundHandler) {
            Buf buf;
            try {
                buf = ((ChannelOutboundHandler)handler).newOutboundBuffer(this);
            }
            catch (Exception e) {
                throw new ChannelPipelineException(handler.getClass().getSimpleName() + ".newOutboundBuffer() raised an exception.", e);
            }
            if (buf instanceof ByteBuf) {
                this.outByteBuf = (ByteBuf)buf;
            }
            else {
                if (!(buf instanceof MessageBuf)) {
                    throw new ChannelPipelineException(handler.getClass().getSimpleName() + ".newOutboundBuffer() returned neither " + ByteBuf.class.getSimpleName() + " nor " + MessageBuf.class.getSimpleName() + ": " + buf);
                }
                final MessageBuf<Object> msgBuf = (MessageBuf<Object>)buf;
                this.outMsgBuf = msgBuf;
            }
        }
    }
    
    DefaultChannelHandlerContext(final DefaultChannelPipeline pipeline, final String name, final DefaultChannelPipeline.HeadHandler handler) {
        this.channel = pipeline.channel;
        this.pipeline = pipeline;
        this.name = name;
        this.handler = handler;
        this.executor = null;
        this.inByteBuf = null;
        this.inMsgBuf = null;
    }
    
    DefaultChannelHandlerContext(final DefaultChannelPipeline pipeline, final String name, final DefaultChannelPipeline.TailHandler handler) {
        this.channel = pipeline.channel;
        this.pipeline = pipeline;
        this.name = name;
        this.handler = handler;
        this.executor = null;
        this.inByteBuf = handler.byteSink;
        this.inMsgBuf = handler.msgSink;
        this.outByteBuf = null;
        this.outMsgBuf = null;
    }
    
    void forwardBufferContent(final DefaultChannelHandlerContext forwardPrev, final DefaultChannelHandlerContext forwardNext) {
        boolean flush = false;
        boolean inboundBufferUpdated = false;
        if (this.hasOutboundByteBuffer() && this.outboundByteBuffer().isReadable()) {
            ByteBuf forwardPrevBuf;
            if (forwardPrev.hasOutboundByteBuffer()) {
                forwardPrevBuf = forwardPrev.outboundByteBuffer();
            }
            else {
                forwardPrevBuf = forwardPrev.nextOutboundByteBuffer();
            }
            forwardPrevBuf.writeBytes(this.outboundByteBuffer());
            flush = true;
        }
        if (this.hasOutboundMessageBuffer() && !this.outboundMessageBuffer().isEmpty()) {
            MessageBuf<Object> forwardPrevBuf2;
            if (forwardPrev.hasOutboundMessageBuffer()) {
                forwardPrevBuf2 = forwardPrev.outboundMessageBuffer();
            }
            else {
                forwardPrevBuf2 = forwardPrev.nextOutboundMessageBuffer();
            }
            if (this.outboundMessageBuffer().drainTo(forwardPrevBuf2) > 0) {
                flush = true;
            }
        }
        if (this.hasInboundByteBuffer() && this.inboundByteBuffer().isReadable()) {
            ByteBuf forwardNextBuf;
            if (forwardNext.hasInboundByteBuffer()) {
                forwardNextBuf = forwardNext.inboundByteBuffer();
            }
            else {
                forwardNextBuf = forwardNext.nextInboundByteBuffer();
            }
            forwardNextBuf.writeBytes(this.inboundByteBuffer());
            inboundBufferUpdated = true;
        }
        if (this.hasInboundMessageBuffer() && !this.inboundMessageBuffer().isEmpty()) {
            MessageBuf<Object> forwardNextBuf2;
            if (forwardNext.hasInboundMessageBuffer()) {
                forwardNextBuf2 = forwardNext.inboundMessageBuffer();
            }
            else {
                forwardNextBuf2 = forwardNext.nextInboundMessageBuffer();
            }
            if (this.inboundMessageBuffer().drainTo(forwardNextBuf2) > 0) {
                inboundBufferUpdated = true;
            }
        }
        if (flush) {
            final EventExecutor executor = this.executor();
            final Thread currentThread = Thread.currentThread();
            if (executor.inEventLoop(currentThread)) {
                this.invokePrevFlush(this.newPromise(), currentThread, findContextOutboundInclusive(forwardPrev));
            }
            else {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        DefaultChannelHandlerContext.this.invokePrevFlush(DefaultChannelHandlerContext.this.newPromise(), Thread.currentThread(), findContextOutboundInclusive(forwardPrev));
                    }
                });
            }
        }
        if (inboundBufferUpdated) {
            final EventExecutor executor = this.executor();
            if (executor.inEventLoop()) {
                this.fireInboundBufferUpdated0(findContextInboundInclusive(forwardNext));
            }
            else {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        DefaultChannelHandlerContext.this.fireInboundBufferUpdated0(findContextInboundInclusive(forwardNext));
                    }
                });
            }
        }
    }
    
    private static DefaultChannelHandlerContext findContextOutboundInclusive(final DefaultChannelHandlerContext ctx) {
        if (ctx.handler() instanceof ChannelOperationHandler) {
            return ctx;
        }
        return ctx.findContextOutbound();
    }
    
    private static DefaultChannelHandlerContext findContextInboundInclusive(final DefaultChannelHandlerContext ctx) {
        if (ctx.handler() instanceof ChannelStateHandler) {
            return ctx;
        }
        return ctx.findContextInbound();
    }
    
    void clearBuffer() {
        if (this.hasOutboundByteBuffer()) {
            this.outboundByteBuffer().clear();
        }
        if (this.hasOutboundMessageBuffer()) {
            this.outboundMessageBuffer().clear();
        }
        if (this.hasInboundByteBuffer()) {
            this.inboundByteBuffer().clear();
        }
        if (this.hasInboundMessageBuffer()) {
            this.inboundMessageBuffer().clear();
        }
    }
    
    void initHeadHandler() {
        final DefaultChannelPipeline.HeadHandler h = (DefaultChannelPipeline.HeadHandler)this.handler;
        if (h.initialized) {
            return;
        }
        assert this.executor().inEventLoop();
        h.init(this);
        h.initialized = true;
        this.outByteBuf = h.byteSink;
        this.outMsgBuf = h.msgSink;
    }
    
    private void fillInboundBridge() {
        if (!(this.handler instanceof ChannelInboundHandler)) {
            return;
        }
        if (this.inMsgBridge != null) {
            final MessageBridge bridge = this.inMsgBridge;
            if (bridge != null) {
                bridge.fill();
            }
        }
        else if (this.inByteBridge != null) {
            final ByteBridge bridge2 = this.inByteBridge;
            if (bridge2 != null) {
                bridge2.fill();
            }
        }
    }
    
    private void fillOutboundBridge() {
        if (!(this.handler instanceof ChannelOutboundHandler)) {
            return;
        }
        if (this.outMsgBridge != null) {
            final MessageBridge bridge = this.outMsgBridge;
            if (bridge != null) {
                bridge.fill();
            }
        }
        else if (this.outByteBridge != null) {
            final ByteBridge bridge2 = this.outByteBridge;
            if (bridge2 != null) {
                bridge2.fill();
            }
        }
    }
    
    private boolean flushInboundBridge() {
        if (this.inMsgBridge != null) {
            final MessageBridge bridge = this.inMsgBridge;
            if (bridge != null) {
                return bridge.flush(this.inMsgBuf);
            }
        }
        else if (this.inByteBridge != null) {
            final ByteBridge bridge2 = this.inByteBridge;
            if (bridge2 != null) {
                return bridge2.flush(this.inByteBuf);
            }
        }
        return true;
    }
    
    private boolean flushOutboundBridge() {
        if (this.outMsgBridge != null) {
            final MessageBridge bridge = this.outMsgBridge;
            if (bridge != null) {
                return bridge.flush(this.outMsgBuf);
            }
        }
        else if (this.outByteBridge != null) {
            final ByteBridge bridge2 = this.outByteBridge;
            if (bridge2 != null) {
                return bridge2.flush(this.outByteBuf);
            }
        }
        return true;
    }
    
    void setRemoved() {
        this.flags |= 0x1;
        if (!this.channel.isRegistered()) {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    private void freeHandlerBuffersAfterRemoval() {
        if (this.flags == 1) {
            this.flags |= 0x2;
            final ChannelHandler handler = this.handler();
            if (handler instanceof ChannelInboundHandler) {
                try {
                    ((ChannelInboundHandler)handler).freeInboundBuffer(this);
                }
                catch (Exception e) {
                    this.notifyHandlerException(e);
                }
                finally {
                    this.freeInboundBridge();
                }
            }
            if (handler instanceof ChannelOutboundHandler) {
                try {
                    ((ChannelOutboundHandler)handler).freeOutboundBuffer(this);
                }
                catch (Exception e) {
                    this.notifyHandlerException(e);
                }
                finally {
                    this.freeOutboundBridge();
                }
            }
        }
    }
    
    private void freeInboundBridge() {
        final ByteBridge inByteBridge = this.inByteBridge;
        if (inByteBridge != null) {
            inByteBridge.release();
        }
        final MessageBridge inMsgBridge = this.inMsgBridge;
        if (inMsgBridge != null) {
            inMsgBridge.release();
        }
    }
    
    private void freeOutboundBridge() {
        final ByteBridge outByteBridge = this.outByteBridge;
        if (outByteBridge != null) {
            outByteBridge.release();
        }
        final MessageBridge outMsgBridge = this.outMsgBridge;
        if (outMsgBridge != null) {
            outMsgBridge.release();
        }
    }
    
    @Override
    public Channel channel() {
        return this.channel;
    }
    
    @Override
    public ChannelPipeline pipeline() {
        return this.pipeline;
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.channel().config().getAllocator();
    }
    
    @Override
    public EventExecutor executor() {
        if (this.executor == null) {
            return this.channel().eventLoop();
        }
        return this.executor;
    }
    
    @Override
    public ChannelHandler handler() {
        return this.handler;
    }
    
    @Override
    public String name() {
        return this.name;
    }
    
    @Override
    public boolean hasInboundByteBuffer() {
        return this.inByteBuf != null;
    }
    
    @Override
    public boolean hasInboundMessageBuffer() {
        return this.inMsgBuf != null;
    }
    
    @Override
    public ByteBuf inboundByteBuffer() {
        if (this.inByteBuf == null) {
            throw new NoSuchBufferException(String.format("the handler '%s' has no inbound byte buffer; it does not implement %s.", this.name, ChannelInboundByteHandler.class.getSimpleName()));
        }
        return this.inByteBuf;
    }
    
    @Override
    public <T> MessageBuf<T> inboundMessageBuffer() {
        if (this.inMsgBuf == null) {
            throw new NoSuchBufferException(String.format("the handler '%s' has no inbound message buffer; it does not implement %s.", this.name, ChannelInboundMessageHandler.class.getSimpleName()));
        }
        return (MessageBuf<T>)this.inMsgBuf;
    }
    
    @Override
    public boolean hasOutboundByteBuffer() {
        return this.outByteBuf != null;
    }
    
    @Override
    public boolean hasOutboundMessageBuffer() {
        return this.outMsgBuf != null;
    }
    
    @Override
    public ByteBuf outboundByteBuffer() {
        if (this.outByteBuf == null) {
            throw new NoSuchBufferException(String.format("the handler '%s' has no outbound byte buffer; it does not implement %s.", this.name, ChannelOutboundByteHandler.class.getSimpleName()));
        }
        return this.outByteBuf;
    }
    
    @Override
    public <T> MessageBuf<T> outboundMessageBuffer() {
        if (this.outMsgBuf == null) {
            throw new NoSuchBufferException(String.format("the handler '%s' has no outbound message buffer; it does not implement %s.", this.name, ChannelOutboundMessageHandler.class.getSimpleName()));
        }
        return (MessageBuf<T>)this.outMsgBuf;
    }
    
    @Override
    public ByteBuf nextInboundByteBuffer() {
        DefaultChannelHandlerContext ctx;
        for (ctx = this.next; !ctx.hasInboundByteBuffer(); ctx = ctx.next) {}
        final Thread currentThread = Thread.currentThread();
        if (ctx.executor().inEventLoop(currentThread)) {
            return ctx.inByteBuf;
        }
        if (this.executor().inEventLoop(currentThread)) {
            ByteBridge bridge = ctx.inByteBridge;
            if (bridge == null) {
                bridge = new ByteBridge(ctx, true);
                if (!DefaultChannelHandlerContext.IN_BYTE_BRIDGE_UPDATER.compareAndSet(ctx, null, bridge)) {
                    bridge.release();
                    bridge = ctx.inByteBridge;
                }
            }
            return bridge.byteBuf;
        }
        throw new IllegalStateException("nextInboundByteBuffer() called from outside the eventLoop");
    }
    
    @Override
    public MessageBuf<Object> nextInboundMessageBuffer() {
        DefaultChannelHandlerContext ctx;
        for (ctx = this.next; !ctx.hasInboundMessageBuffer(); ctx = ctx.next) {}
        final Thread currentThread = Thread.currentThread();
        if (ctx.executor().inEventLoop(currentThread)) {
            return ctx.inMsgBuf;
        }
        if (this.executor().inEventLoop(currentThread)) {
            MessageBridge bridge = ctx.inMsgBridge;
            if (bridge == null) {
                bridge = new MessageBridge();
                if (!DefaultChannelHandlerContext.IN_MSG_BRIDGE_UPDATER.compareAndSet(ctx, null, bridge)) {
                    bridge.release();
                    bridge = ctx.inMsgBridge;
                }
            }
            return bridge.msgBuf;
        }
        throw new IllegalStateException("nextInboundMessageBuffer() called from outside the eventLoop");
    }
    
    @Override
    public ByteBuf nextOutboundByteBuffer() {
        DefaultChannelHandlerContext ctx;
        for (ctx = this.prev; !ctx.hasOutboundByteBuffer(); ctx = ctx.prev) {}
        final Thread currentThread = Thread.currentThread();
        if (ctx.executor().inEventLoop(currentThread)) {
            return ctx.outboundByteBuffer();
        }
        if (this.executor().inEventLoop(currentThread)) {
            ByteBridge bridge = ctx.outByteBridge;
            if (bridge == null) {
                bridge = new ByteBridge(ctx, false);
                if (!DefaultChannelHandlerContext.OUT_BYTE_BRIDGE_UPDATER.compareAndSet(ctx, null, bridge)) {
                    bridge.release();
                    bridge = ctx.outByteBridge;
                }
            }
            return bridge.byteBuf;
        }
        throw new IllegalStateException("nextOutboundByteBuffer() called from outside the eventLoop");
    }
    
    @Override
    public MessageBuf<Object> nextOutboundMessageBuffer() {
        DefaultChannelHandlerContext ctx;
        for (ctx = this.prev; !ctx.hasOutboundMessageBuffer(); ctx = ctx.prev) {}
        final Thread currentThread = Thread.currentThread();
        if (ctx.executor().inEventLoop(currentThread)) {
            return ctx.outboundMessageBuffer();
        }
        if (this.executor().inEventLoop(currentThread)) {
            MessageBridge bridge = ctx.outMsgBridge;
            if (bridge == null) {
                bridge = new MessageBridge();
                if (!DefaultChannelHandlerContext.OUT_MSG_BRIDGE_UPDATER.compareAndSet(ctx, null, bridge)) {
                    bridge.release();
                    bridge = ctx.outMsgBridge;
                }
            }
            return bridge.msgBuf;
        }
        throw new IllegalStateException("nextOutboundMessageBuffer() called from outside the eventLoop");
    }
    
    @Override
    public ChannelHandlerContext fireChannelRegistered() {
        final DefaultChannelHandlerContext next = this.findContextInbound();
        final EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeChannelRegistered();
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    next.invokeChannelRegistered();
                }
            });
        }
        return this;
    }
    
    private void invokeChannelRegistered() {
        try {
            ((ChannelStateHandler)this.handler()).channelRegistered(this);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelHandlerContext fireChannelUnregistered() {
        final DefaultChannelHandlerContext next = this.findContextInbound();
        final EventExecutor executor = next.executor();
        if (this.prev != null && executor.inEventLoop()) {
            next.invokeChannelUnregistered();
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    next.invokeChannelUnregistered();
                }
            });
        }
        return this;
    }
    
    private void invokeChannelUnregistered() {
        try {
            ((ChannelStateHandler)this.handler()).channelUnregistered(this);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
    }
    
    @Override
    public ChannelHandlerContext fireChannelActive() {
        final DefaultChannelHandlerContext next = this.findContextInbound();
        final EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeChannelActive();
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    next.invokeChannelActive();
                }
            });
        }
        return this;
    }
    
    private void invokeChannelActive() {
        try {
            ((ChannelStateHandler)this.handler()).channelActive(this);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelHandlerContext fireChannelInactive() {
        final DefaultChannelHandlerContext next = this.findContextInbound();
        final EventExecutor executor = next.executor();
        if (this.prev != null && executor.inEventLoop()) {
            next.invokeChannelInactive();
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    next.invokeChannelInactive();
                }
            });
        }
        return this;
    }
    
    private void invokeChannelInactive() {
        try {
            ((ChannelStateHandler)this.handler()).channelInactive(this);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelHandlerContext fireExceptionCaught(final Throwable cause) {
        if (cause == null) {
            throw new NullPointerException("cause");
        }
        this.next.invokeExceptionCaught(cause);
        return this;
    }
    
    private void invokeExceptionCaught(final Throwable cause) {
        final EventExecutor executor = this.executor();
        if (this.prev != null && executor.inEventLoop()) {
            this.invokeExceptionCaught0(cause);
        }
        else {
            try {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        DefaultChannelHandlerContext.this.invokeExceptionCaught0(cause);
                    }
                });
            }
            catch (Throwable t) {
                if (DefaultChannelPipeline.logger.isWarnEnabled()) {
                    DefaultChannelPipeline.logger.warn("Failed to submit an exceptionCaught() event.", t);
                    DefaultChannelPipeline.logger.warn("The exceptionCaught() event that was failed to submit was:", cause);
                }
            }
        }
    }
    
    private void invokeExceptionCaught0(final Throwable cause) {
        final ChannelHandler handler = this.handler();
        try {
            handler.exceptionCaught(this, cause);
        }
        catch (Throwable t) {
            if (DefaultChannelPipeline.logger.isWarnEnabled()) {
                DefaultChannelPipeline.logger.warn("An exception was thrown by a user handler's exceptionCaught() method while handling the following exception:", cause);
            }
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelHandlerContext fireUserEventTriggered(final Object event) {
        if (event == null) {
            throw new NullPointerException("event");
        }
        final DefaultChannelHandlerContext next = this.findContextInbound();
        final EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeUserEventTriggered(event);
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    next.invokeUserEventTriggered(event);
                }
            });
        }
        return this;
    }
    
    private void invokeUserEventTriggered(final Object event) {
        final ChannelStateHandler handler = (ChannelStateHandler)this.handler();
        try {
            handler.userEventTriggered(this, event);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelHandlerContext fireInboundBufferUpdated() {
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            this.fireInboundBufferUpdated0(this.findContextInbound());
        }
        else {
            Runnable task = this.fireInboundBufferUpdated0Task;
            if (task == null) {
                task = (this.fireInboundBufferUpdated0Task = new Runnable() {
                    @Override
                    public void run() {
                        DefaultChannelHandlerContext.this.fireInboundBufferUpdated0(DefaultChannelHandlerContext.this.findContextInbound());
                    }
                });
            }
            executor.execute(task);
        }
        return this;
    }
    
    private void fireInboundBufferUpdated0(final DefaultChannelHandlerContext next) {
        if (!this.pipeline.isInboundShutdown()) {
            next.fillInboundBridge();
            if (next.executor == this.executor) {
                next.invokeInboundBufferUpdated();
            }
            else {
                Runnable task = next.invokeInboundBufferUpdatedTask;
                if (task == null) {
                    task = (next.invokeInboundBufferUpdatedTask = new Runnable() {
                        @Override
                        public void run() {
                            if (DefaultChannelHandlerContext.this.pipeline.isInboundShutdown()) {
                                return;
                            }
                            if (DefaultChannelHandlerContext.this.findContextInbound() == next) {
                                next.invokeInboundBufferUpdated();
                            }
                            else {
                                DefaultChannelHandlerContext.this.fireInboundBufferUpdated0(next);
                            }
                        }
                    });
                }
                next.executor().execute(task);
            }
        }
    }
    
    private void invokeInboundBufferUpdated() {
        final ChannelStateHandler handler = (ChannelStateHandler)this.handler();
        if (handler instanceof ChannelInboundHandler) {
            while (true) {
                try {
                    final boolean flushedAll = this.flushInboundBridge();
                    handler.inboundBufferUpdated(this);
                    if (flushedAll) {
                        break;
                    }
                }
                catch (Throwable t) {
                    this.notifyHandlerException(t);
                }
                finally {
                    if ((this.flags & 0x2) == 0x0) {
                        if (handler instanceof ChannelInboundByteHandler && !this.pipeline.isInboundShutdown()) {
                            try {
                                ((ChannelInboundByteHandler)handler).discardInboundReadBytes(this);
                            }
                            catch (Throwable t2) {
                                this.notifyHandlerException(t2);
                            }
                        }
                        this.freeHandlerBuffersAfterRemoval();
                    }
                }
            }
        }
        else {
            try {
                handler.inboundBufferUpdated(this);
            }
            catch (Throwable t) {
                this.notifyHandlerException(t);
            }
        }
    }
    
    @Override
    public ChannelHandlerContext fireChannelReadSuspended() {
        final DefaultChannelHandlerContext next = this.findContextInbound();
        final EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeChannelReadSuspended();
        }
        else {
            Runnable task = next.invokeChannelReadSuspendedTask;
            if (task == null) {
                task = (next.invokeChannelReadSuspendedTask = new Runnable() {
                    @Override
                    public void run() {
                        if (DefaultChannelHandlerContext.this.findContextInbound() == next) {
                            next.invokeChannelReadSuspended();
                        }
                        else {
                            DefaultChannelHandlerContext.this.fireChannelReadSuspended();
                        }
                    }
                });
            }
            executor.execute(task);
        }
        return this;
    }
    
    private void invokeChannelReadSuspended() {
        try {
            ((ChannelStateHandler)this.handler()).channelReadSuspended(this);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress localAddress) {
        return this.bind(localAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress remoteAddress) {
        return this.connect(remoteAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress remoteAddress, final SocketAddress localAddress) {
        return this.connect(remoteAddress, localAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture disconnect() {
        return this.disconnect(this.newPromise());
    }
    
    @Override
    public ChannelFuture close() {
        return this.close(this.newPromise());
    }
    
    @Override
    public ChannelFuture deregister() {
        return this.deregister(this.newPromise());
    }
    
    @Override
    public ChannelFuture flush() {
        return this.flush(this.newPromise());
    }
    
    @Override
    public ChannelFuture write(final Object message) {
        return this.write(message, this.newPromise());
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress localAddress, final ChannelPromise promise) {
        if (localAddress == null) {
            throw new NullPointerException("localAddress");
        }
        this.validateFuture(promise);
        return this.findContextOutbound().invokeBind(localAddress, promise);
    }
    
    private ChannelFuture invokeBind(final SocketAddress localAddress, final ChannelPromise promise) {
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            this.invokeBind0(localAddress, promise);
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    DefaultChannelHandlerContext.this.invokeBind0(localAddress, promise);
                }
            });
        }
        return promise;
    }
    
    private void invokeBind0(final SocketAddress localAddress, final ChannelPromise promise) {
        try {
            ((ChannelOperationHandler)this.handler()).bind(this, localAddress, promise);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress remoteAddress, final ChannelPromise promise) {
        return this.connect(remoteAddress, null, promise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
        if (remoteAddress == null) {
            throw new NullPointerException("remoteAddress");
        }
        this.validateFuture(promise);
        return this.findContextOutbound().invokeConnect(remoteAddress, localAddress, promise);
    }
    
    private ChannelFuture invokeConnect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            this.invokeConnect0(remoteAddress, localAddress, promise);
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    DefaultChannelHandlerContext.this.invokeConnect0(remoteAddress, localAddress, promise);
                }
            });
        }
        return promise;
    }
    
    private void invokeConnect0(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
        try {
            ((ChannelOperationHandler)this.handler()).connect(this, remoteAddress, localAddress, promise);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelFuture disconnect(final ChannelPromise promise) {
        this.validateFuture(promise);
        if (!this.channel().metadata().hasDisconnect()) {
            return this.findContextOutbound().invokeClose(promise);
        }
        return this.findContextOutbound().invokeDisconnect(promise);
    }
    
    private ChannelFuture invokeDisconnect(final ChannelPromise promise) {
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            this.invokeDisconnect0(promise);
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    DefaultChannelHandlerContext.this.invokeDisconnect0(promise);
                }
            });
        }
        return promise;
    }
    
    private void invokeDisconnect0(final ChannelPromise promise) {
        try {
            ((ChannelOperationHandler)this.handler()).disconnect(this, promise);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelFuture close(final ChannelPromise promise) {
        this.validateFuture(promise);
        return this.findContextOutbound().invokeClose(promise);
    }
    
    private ChannelFuture invokeClose(final ChannelPromise promise) {
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            this.invokeClose0(promise);
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    DefaultChannelHandlerContext.this.invokeClose0(promise);
                }
            });
        }
        return promise;
    }
    
    private void invokeClose0(final ChannelPromise promise) {
        try {
            ((ChannelOperationHandler)this.handler()).close(this, promise);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelFuture deregister(final ChannelPromise promise) {
        this.validateFuture(promise);
        return this.findContextOutbound().invokeDeregister(promise);
    }
    
    private ChannelFuture invokeDeregister(final ChannelPromise promise) {
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            this.invokeDeregister0(promise);
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    DefaultChannelHandlerContext.this.invokeDeregister0(promise);
                }
            });
        }
        return promise;
    }
    
    private void invokeDeregister0(final ChannelPromise promise) {
        try {
            ((ChannelOperationHandler)this.handler()).deregister(this, promise);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public void read() {
        this.findContextOutbound().invokeRead();
    }
    
    private void invokeRead() {
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            this.invokeRead0();
        }
        else {
            Runnable task = this.invokeRead0Task;
            if (task == null) {
                task = (this.invokeRead0Task = new Runnable() {
                    @Override
                    public void run() {
                        DefaultChannelHandlerContext.this.invokeRead0();
                    }
                });
            }
            executor.execute(task);
        }
    }
    
    private void invokeRead0() {
        try {
            ((ChannelOperationHandler)this.handler()).read(this);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelFuture flush(final ChannelPromise promise) {
        this.validateFuture(promise);
        final EventExecutor executor = this.executor();
        final Thread currentThread = Thread.currentThread();
        if (executor.inEventLoop(currentThread)) {
            this.invokePrevFlush(promise, currentThread, this.findContextOutbound());
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    DefaultChannelHandlerContext.this.invokePrevFlush(promise, Thread.currentThread(), DefaultChannelHandlerContext.this.findContextOutbound());
                }
            });
        }
        return promise;
    }
    
    private void invokePrevFlush(final ChannelPromise promise, final Thread currentThread, final DefaultChannelHandlerContext prev) {
        if (this.pipeline.isOutboundShutdown()) {
            promise.setFailure((Throwable)new ChannelPipelineException("Unable to flush as outbound buffer of next handler was freed already"));
            return;
        }
        prev.fillOutboundBridge();
        prev.invokeFlush(promise, currentThread);
    }
    
    private ChannelFuture invokeFlush(final ChannelPromise promise, final Thread currentThread) {
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop(currentThread)) {
            this.invokeFlush0(promise);
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    DefaultChannelHandlerContext.this.invokeFlush0(promise);
                }
            });
        }
        return promise;
    }
    
    private void invokeFlush0(final ChannelPromise promise) {
        final Channel channel = this.channel();
        if (!channel.isRegistered() && !channel.isActive()) {
            promise.setFailure((Throwable)new ClosedChannelException());
            return;
        }
        final ChannelOperationHandler handler = (ChannelOperationHandler)this.handler();
        if (handler instanceof ChannelOutboundHandler) {
            this.flushOutboundBridge();
        }
        try {
            handler.flush(this, promise);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            if (handler instanceof ChannelOutboundByteHandler && !this.pipeline.isOutboundShutdown()) {
                try {
                    ((ChannelOutboundByteHandler)handler).discardOutboundReadBytes(this);
                }
                catch (Throwable t2) {
                    this.notifyHandlerException(t2);
                }
            }
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelFuture sendFile(final FileRegion region) {
        return this.sendFile(region, this.newPromise());
    }
    
    @Override
    public ChannelFuture sendFile(final FileRegion region, final ChannelPromise promise) {
        if (region == null) {
            throw new NullPointerException("region");
        }
        this.validateFuture(promise);
        return this.findContextOutbound().invokeSendFile(region, promise);
    }
    
    private ChannelFuture invokeSendFile(final FileRegion region, final ChannelPromise promise) {
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            this.invokeSendFile0(region, promise);
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    DefaultChannelHandlerContext.this.invokeSendFile0(region, promise);
                }
            });
        }
        return promise;
    }
    
    private void invokeSendFile0(final FileRegion region, final ChannelPromise promise) {
        final ChannelOperationHandler handler = (ChannelOperationHandler)this.handler();
        if (handler instanceof ChannelOutboundHandler) {
            this.flushOutboundBridge();
        }
        try {
            handler.sendFile(this, region, promise);
        }
        catch (Throwable t) {
            this.notifyHandlerException(t);
        }
        finally {
            this.freeHandlerBuffersAfterRemoval();
        }
    }
    
    @Override
    public ChannelFuture write(final Object message, final ChannelPromise promise) {
        if (message instanceof FileRegion) {
            return this.sendFile((FileRegion)message, promise);
        }
        if (message == null) {
            throw new NullPointerException("message");
        }
        this.validateFuture(promise);
        DefaultChannelHandlerContext ctx = this.prev;
        boolean msgBuf = false;
        EventExecutor executor = null;
        Label_0122: {
            if (message instanceof ByteBuf) {
                while (!ctx.hasOutboundByteBuffer()) {
                    if (ctx.hasOutboundMessageBuffer()) {
                        msgBuf = true;
                        executor = ctx.executor();
                        break Label_0122;
                    }
                    ctx = ctx.prev;
                }
                msgBuf = false;
                executor = ctx.executor();
            }
            else {
                msgBuf = true;
                while (!ctx.hasOutboundMessageBuffer()) {
                    ctx = ctx.prev;
                }
                executor = ctx.executor();
            }
        }
        if (executor.inEventLoop()) {
            ctx.write0(message, promise, msgBuf);
            return promise;
        }
        final DefaultChannelHandlerContext ctx2 = ctx;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ctx2.write0(message, promise, msgBuf);
            }
        });
        return promise;
    }
    
    private void write0(final Object message, final ChannelPromise promise, final boolean msgBuf) {
        final Channel channel = this.channel();
        if (!channel.isRegistered() && !channel.isActive()) {
            promise.setFailure((Throwable)new ClosedChannelException());
            return;
        }
        if (this.pipeline.isOutboundShutdown()) {
            promise.setFailure((Throwable)new ChannelPipelineException("Unable to write as outbound buffer of next handler was freed already"));
            return;
        }
        if (msgBuf) {
            this.outboundMessageBuffer().add(message);
        }
        else {
            final ByteBuf buf = (ByteBuf)message;
            this.outboundByteBuffer().writeBytes(buf, buf.readerIndex(), buf.readableBytes());
        }
        this.invokeFlush0(promise);
    }
    
    void invokeFreeInboundBuffer() {
        final EventExecutor executor = this.executor();
        if (this.prev != null && executor.inEventLoop()) {
            this.invokeFreeInboundBuffer0();
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    DefaultChannelHandlerContext.this.pipeline.shutdownInbound();
                    DefaultChannelHandlerContext.this.invokeFreeInboundBuffer0();
                }
            });
        }
    }
    
    private void invokeFreeInboundBuffer0() {
        final ChannelHandler handler = this.handler();
        if (handler instanceof ChannelInboundHandler) {
            final ChannelInboundHandler h = (ChannelInboundHandler)handler;
            try {
                h.freeInboundBuffer(this);
            }
            catch (Throwable t) {
                this.notifyHandlerException(t);
            }
            finally {
                this.freeInboundBridge();
            }
        }
        if (this.next != null) {
            final DefaultChannelHandlerContext nextCtx = this.findContextInbound();
            nextCtx.invokeFreeInboundBuffer();
        }
        else {
            this.findContextOutbound().invokeFreeOutboundBuffer();
        }
    }
    
    private void invokeFreeOutboundBuffer() {
        final EventExecutor executor = this.executor();
        if (this.next == null) {
            if (executor.inEventLoop()) {
                this.pipeline.shutdownOutbound();
                this.invokeFreeOutboundBuffer0();
            }
            else {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        DefaultChannelHandlerContext.this.pipeline.shutdownOutbound();
                        DefaultChannelHandlerContext.this.invokeFreeOutboundBuffer0();
                    }
                });
            }
        }
        else if (executor.inEventLoop()) {
            this.invokeFreeOutboundBuffer0();
        }
        else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    DefaultChannelHandlerContext.this.invokeFreeOutboundBuffer0();
                }
            });
        }
    }
    
    private void invokeFreeOutboundBuffer0() {
        final ChannelHandler handler = this.handler();
        if (handler instanceof ChannelOutboundHandler) {
            final ChannelOutboundHandler h = (ChannelOutboundHandler)handler;
            try {
                h.freeOutboundBuffer(this);
            }
            catch (Throwable t) {
                this.notifyHandlerException(t);
            }
            finally {
                this.freeOutboundBridge();
            }
        }
        if (this.prev != null) {
            this.findContextOutbound().invokeFreeOutboundBuffer();
        }
    }
    
    private void notifyHandlerException(final Throwable cause) {
        if (inExceptionCaught(cause)) {
            if (DefaultChannelPipeline.logger.isWarnEnabled()) {
                DefaultChannelPipeline.logger.warn("An exception was thrown by a user handler while handling an exceptionCaught event", cause);
            }
            return;
        }
        if (this.handler() instanceof ChannelStateHandler) {
            this.invokeExceptionCaught(cause);
        }
        else {
            this.findContextInbound().invokeExceptionCaught(cause);
        }
    }
    
    private static boolean inExceptionCaught(Throwable cause) {
        do {
            final StackTraceElement[] trace = cause.getStackTrace();
            if (trace != null) {
                for (final StackTraceElement t : trace) {
                    if (t == null) {
                        break;
                    }
                    if ("exceptionCaught".equals(t.getMethodName())) {
                        return true;
                    }
                }
            }
            cause = cause.getCause();
        } while (cause != null);
        return false;
    }
    
    @Override
    public ChannelPromise newPromise() {
        return new DefaultChannelPromise(this.channel(), this.executor());
    }
    
    @Override
    public ChannelFuture newSucceededFuture() {
        ChannelFuture succeededFuture = this.succeededFuture;
        if (succeededFuture == null) {
            succeededFuture = (this.succeededFuture = new SucceededChannelFuture(this.channel(), this.executor()));
        }
        return succeededFuture;
    }
    
    @Override
    public ChannelFuture newFailedFuture(final Throwable cause) {
        return new FailedChannelFuture(this.channel(), this.executor(), cause);
    }
    
    private void validateFuture(final ChannelFuture future) {
        if (future == null) {
            throw new NullPointerException("future");
        }
        if (future.channel() != this.channel()) {
            throw new IllegalArgumentException(String.format("future.channel does not match: %s (expected: %s)", future.channel(), this.channel()));
        }
        if (future.isDone()) {
            throw new IllegalArgumentException("future already done");
        }
        if (future instanceof ChannelFuture.Unsafe) {
            throw new IllegalArgumentException("internal use only future not allowed");
        }
    }
    
    private DefaultChannelHandlerContext findContextInbound() {
        DefaultChannelHandlerContext ctx = this;
        do {
            ctx = ctx.next;
        } while (!(ctx.handler() instanceof ChannelStateHandler));
        return ctx;
    }
    
    @Override
    public BufType nextInboundBufferType() {
        DefaultChannelHandlerContext ctx = this;
        do {
            ctx = ctx.next;
        } while (!(ctx.handler() instanceof ChannelInboundHandler));
        if (ctx.handler() instanceof ChannelInboundByteHandler) {
            return BufType.BYTE;
        }
        return BufType.MESSAGE;
    }
    
    @Override
    public BufType nextOutboundBufferType() {
        DefaultChannelHandlerContext ctx = this;
        do {
            ctx = ctx.prev;
        } while (!(ctx.handler() instanceof ChannelOutboundHandler));
        if (ctx.handler() instanceof ChannelOutboundByteHandler) {
            return BufType.BYTE;
        }
        return BufType.MESSAGE;
    }
    
    private DefaultChannelHandlerContext findContextOutbound() {
        DefaultChannelHandlerContext ctx = this;
        do {
            ctx = ctx.prev;
        } while (!(ctx.handler() instanceof ChannelOperationHandler));
        return ctx;
    }
    
    static {
        IN_MSG_BRIDGE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultChannelHandlerContext.class, MessageBridge.class, "inMsgBridge");
        OUT_MSG_BRIDGE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultChannelHandlerContext.class, MessageBridge.class, "outMsgBridge");
        IN_BYTE_BRIDGE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultChannelHandlerContext.class, ByteBridge.class, "inByteBridge");
        OUT_BYTE_BRIDGE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultChannelHandlerContext.class, ByteBridge.class, "outByteBridge");
    }
    
    private static final class MessageBridge
    {
        private final MessageBuf<Object> msgBuf;
        private final Queue<Object[]> exchangeBuf;
        
        private MessageBridge() {
            this.msgBuf = Unpooled.messageBuffer();
            this.exchangeBuf = new ConcurrentLinkedQueue<Object[]>();
        }
        
        private void fill() {
            if (this.msgBuf.isEmpty()) {
                return;
            }
            final Object[] data = this.msgBuf.toArray();
            this.msgBuf.clear();
            this.exchangeBuf.add(data);
        }
        
        private boolean flush(final MessageBuf<Object> out) {
            while (true) {
                final Object[] data = this.exchangeBuf.peek();
                if (data == null) {
                    return true;
                }
                for (int i = 0; i < data.length; ++i) {
                    final Object m = data[i];
                    if (m == null) {
                        break;
                    }
                    if (!out.offer(m)) {
                        System.arraycopy(data, i, data, 0, data.length - i);
                        for (int j = i + 1; j < data.length; ++j) {
                            data[j] = null;
                        }
                        return false;
                    }
                    data[i] = null;
                }
                this.exchangeBuf.remove();
            }
        }
        
        private void release() {
            this.msgBuf.release();
        }
    }
    
    private static final class ByteBridge
    {
        private final ByteBuf byteBuf;
        private final Queue<ByteBuf> exchangeBuf;
        private final ChannelHandlerContext ctx;
        
        ByteBridge(final ChannelHandlerContext ctx, final boolean inbound) {
            this.exchangeBuf = new ConcurrentLinkedQueue<ByteBuf>();
            this.ctx = ctx;
            if (inbound) {
                if (ctx.inboundByteBuffer().isDirect()) {
                    this.byteBuf = ctx.alloc().directBuffer();
                }
                else {
                    this.byteBuf = ctx.alloc().heapBuffer();
                }
            }
            else if (ctx.outboundByteBuffer().isDirect()) {
                this.byteBuf = ctx.alloc().directBuffer();
            }
            else {
                this.byteBuf = ctx.alloc().heapBuffer();
            }
        }
        
        private void fill() {
            if (!this.byteBuf.isReadable()) {
                return;
            }
            final int dataLen = this.byteBuf.readableBytes();
            ByteBuf data;
            if (this.byteBuf.isDirect()) {
                data = this.ctx.alloc().directBuffer(dataLen, dataLen);
            }
            else {
                data = this.ctx.alloc().buffer(dataLen, dataLen);
            }
            this.byteBuf.readBytes(data).discardSomeReadBytes();
            this.exchangeBuf.add(data);
        }
        
        private boolean flush(final ByteBuf out) {
            while (true) {
                final ByteBuf data = this.exchangeBuf.peek();
                if (data == null) {
                    return true;
                }
                if (out.writerIndex() > out.maxCapacity() - data.readableBytes()) {
                    out.capacity(out.maxCapacity());
                    out.writeBytes(data, out.writableBytes());
                    return false;
                }
                this.exchangeBuf.remove();
                try {
                    out.writeBytes(data);
                }
                finally {
                    data.release();
                }
            }
        }
        
        private void release() {
            this.byteBuf.release();
        }
    }
}
