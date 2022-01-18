// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.util.concurrent.Promise;
import io.netty.buffer.BufType;
import java.net.InetSocketAddress;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.io.EOFException;
import io.netty.buffer.MessageBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.EventExecutor;
import java.nio.channels.ClosedChannelException;
import java.net.SocketAddress;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.DefaultAttributeMap;

public abstract class AbstractChannel extends DefaultAttributeMap implements Channel
{
    private static final InternalLogger logger;
    static final ConcurrentMap<Integer, Channel> allChannels;
    private static final Random random;
    private final Channel parent;
    private final Integer id;
    private final Unsafe unsafe;
    private final DefaultChannelPipeline pipeline;
    private final ChannelFuture succeededFuture;
    private final VoidChannelPromise voidPromise;
    private final CloseFuture closeFuture;
    protected final ChannelFlushPromiseNotifier flushFutureNotifier;
    private volatile SocketAddress localAddress;
    private volatile SocketAddress remoteAddress;
    private volatile EventLoop eventLoop;
    private volatile boolean registered;
    private ClosedChannelException closedChannelException;
    private boolean inFlushNow;
    private boolean flushNowPending;
    private boolean strValActive;
    private String strVal;
    
    private static Integer allocateId(final Channel channel) {
        int idVal = AbstractChannel.random.nextInt();
        if (idVal > 0) {
            idVal = -idVal;
        }
        else if (idVal == 0) {
            idVal = -1;
        }
        Integer id;
        while (true) {
            id = idVal;
            if (AbstractChannel.allChannels.putIfAbsent(id, channel) == null) {
                break;
            }
            if (--idVal < 0) {
                continue;
            }
            idVal = -1;
        }
        return id;
    }
    
    protected AbstractChannel(final Channel parent, Integer id) {
        this.succeededFuture = new SucceededChannelFuture(this, null);
        this.voidPromise = new VoidChannelPromise(this);
        this.closeFuture = new CloseFuture(this);
        this.flushFutureNotifier = new ChannelFlushPromiseNotifier();
        if (id == null) {
            id = allocateId(this);
        }
        else {
            if (id < 0) {
                throw new IllegalArgumentException("id: " + id + " (expected: >= 0)");
            }
            if (AbstractChannel.allChannels.putIfAbsent(id, this) != null) {
                throw new IllegalArgumentException("duplicate ID: " + id);
            }
        }
        this.parent = parent;
        this.id = id;
        this.unsafe = this.newUnsafe();
        this.pipeline = new DefaultChannelPipeline(this);
        this.closeFuture().addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture future) {
                AbstractChannel.allChannels.remove(AbstractChannel.this.id());
            }
        });
    }
    
    @Override
    public final Integer id() {
        return this.id;
    }
    
    @Override
    public Channel parent() {
        return this.parent;
    }
    
    @Override
    public ChannelPipeline pipeline() {
        return this.pipeline;
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.config().getAllocator();
    }
    
    @Override
    public EventLoop eventLoop() {
        final EventLoop eventLoop = this.eventLoop;
        if (eventLoop == null) {
            throw new IllegalStateException("channel not registered to an event loop");
        }
        return eventLoop;
    }
    
    @Override
    public SocketAddress localAddress() {
        SocketAddress localAddress = this.localAddress;
        if (localAddress == null) {
            try {
                localAddress = (this.localAddress = this.unsafe().localAddress());
            }
            catch (Throwable t) {
                return null;
            }
        }
        return localAddress;
    }
    
    protected void invalidateLocalAddress() {
        this.localAddress = null;
    }
    
    @Override
    public SocketAddress remoteAddress() {
        SocketAddress remoteAddress = this.remoteAddress;
        if (remoteAddress == null) {
            try {
                remoteAddress = (this.remoteAddress = this.unsafe().remoteAddress());
            }
            catch (Throwable t) {
                return null;
            }
        }
        return remoteAddress;
    }
    
    protected void invalidateRemoteAddress() {
        this.remoteAddress = null;
    }
    
    @Override
    public boolean isRegistered() {
        return this.registered;
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress localAddress) {
        return this.pipeline.bind(localAddress);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress remoteAddress) {
        return this.pipeline.connect(remoteAddress);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress remoteAddress, final SocketAddress localAddress) {
        return this.pipeline.connect(remoteAddress, localAddress);
    }
    
    @Override
    public ChannelFuture disconnect() {
        return this.pipeline.disconnect();
    }
    
    @Override
    public ChannelFuture close() {
        return this.pipeline.close();
    }
    
    @Override
    public ChannelFuture deregister() {
        return this.pipeline.deregister();
    }
    
    @Override
    public ChannelFuture flush() {
        return this.pipeline.flush();
    }
    
    @Override
    public ChannelFuture write(final Object message) {
        return this.pipeline.write(message);
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress localAddress, final ChannelPromise promise) {
        return this.pipeline.bind(localAddress, promise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress remoteAddress, final ChannelPromise promise) {
        return this.pipeline.connect(remoteAddress, promise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
        return this.pipeline.connect(remoteAddress, localAddress, promise);
    }
    
    @Override
    public ChannelFuture disconnect(final ChannelPromise promise) {
        return this.pipeline.disconnect(promise);
    }
    
    @Override
    public ChannelFuture close(final ChannelPromise promise) {
        return this.pipeline.close(promise);
    }
    
    @Override
    public ChannelFuture deregister(final ChannelPromise promise) {
        return this.pipeline.deregister(promise);
    }
    
    @Override
    public ByteBuf outboundByteBuffer() {
        return this.pipeline.outboundByteBuffer();
    }
    
    @Override
    public <T> MessageBuf<T> outboundMessageBuffer() {
        return this.pipeline.outboundMessageBuffer();
    }
    
    @Override
    public void read() {
        this.pipeline.read();
    }
    
    @Override
    public ChannelFuture flush(final ChannelPromise promise) {
        return this.pipeline.flush(promise);
    }
    
    @Override
    public ChannelFuture write(final Object message, final ChannelPromise promise) {
        return this.pipeline.write(message, promise);
    }
    
    @Override
    public ChannelPromise newPromise() {
        return new DefaultChannelPromise(this);
    }
    
    @Override
    public ChannelFuture newSucceededFuture() {
        return this.succeededFuture;
    }
    
    @Override
    public ChannelFuture newFailedFuture(final Throwable cause) {
        return new FailedChannelFuture(this, null, cause);
    }
    
    @Override
    public ChannelFuture closeFuture() {
        return this.closeFuture;
    }
    
    @Override
    public Unsafe unsafe() {
        return this.unsafe;
    }
    
    @Override
    public ChannelFuture sendFile(final FileRegion region) {
        return this.pipeline.sendFile(region);
    }
    
    @Override
    public ChannelFuture sendFile(final FileRegion region, final ChannelPromise promise) {
        return this.pipeline.sendFile(region, promise);
    }
    
    protected static int expandReadBuffer(final ByteBuf byteBuf) {
        final int writerIndex = byteBuf.writerIndex();
        final int capacity = byteBuf.capacity();
        if (capacity != writerIndex) {
            return 0;
        }
        final int maxCapacity = byteBuf.maxCapacity();
        if (capacity == maxCapacity) {
            return 2;
        }
        final int increment = 4096;
        if (writerIndex + 4096 > maxCapacity) {
            byteBuf.capacity(maxCapacity);
        }
        else {
            byteBuf.ensureWritable(4096);
        }
        return 1;
    }
    
    protected abstract AbstractUnsafe newUnsafe();
    
    @Override
    public final int hashCode() {
        return this.id;
    }
    
    @Override
    public final boolean equals(final Object o) {
        return this == o;
    }
    
    @Override
    public final int compareTo(final Channel o) {
        return this.id().compareTo(o.id());
    }
    
    @Override
    public String toString() {
        final boolean active = this.isActive();
        if (this.strValActive == active && this.strVal != null) {
            return this.strVal;
        }
        final SocketAddress remoteAddr = this.remoteAddress();
        final SocketAddress localAddr = this.localAddress();
        if (remoteAddr != null) {
            SocketAddress srcAddr;
            SocketAddress dstAddr;
            if (this.parent == null) {
                srcAddr = localAddr;
                dstAddr = remoteAddr;
            }
            else {
                srcAddr = remoteAddr;
                dstAddr = localAddr;
            }
            this.strVal = String.format("[id: 0x%08x, %s %s %s]", this.id, srcAddr, active ? "=>" : ":>", dstAddr);
        }
        else if (localAddr != null) {
            this.strVal = String.format("[id: 0x%08x, %s]", this.id, localAddr);
        }
        else {
            this.strVal = String.format("[id: 0x%08x]", this.id);
        }
        this.strValActive = active;
        return this.strVal;
    }
    
    protected abstract boolean isCompatible(final EventLoop p0);
    
    protected abstract SocketAddress localAddress0();
    
    protected abstract SocketAddress remoteAddress0();
    
    protected Runnable doRegister() throws Exception {
        return null;
    }
    
    protected abstract void doBind(final SocketAddress p0) throws Exception;
    
    protected abstract void doDisconnect() throws Exception;
    
    protected void doPreClose() throws Exception {
    }
    
    protected abstract void doClose() throws Exception;
    
    protected Runnable doDeregister() throws Exception {
        return null;
    }
    
    protected abstract void doBeginRead() throws Exception;
    
    protected void doFlushByteBuffer(final ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    protected void doFlushMessageBuffer(final MessageBuf<Object> buf) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    protected void doFlushFileRegion(final FileRegion region, final ChannelPromise promise) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    protected static void checkEOF(final FileRegion region, final long writtenBytes) throws IOException {
        if (writtenBytes < region.count()) {
            throw new EOFException("Expected to be able to write " + region.count() + " bytes, but only wrote " + writtenBytes);
        }
    }
    
    protected abstract boolean isFlushPending();
    
    static {
        logger = InternalLoggerFactory.getInstance(AbstractChannel.class);
        allChannels = PlatformDependent.newConcurrentHashMap();
        random = new Random();
    }
    
    protected abstract class AbstractUnsafe implements Unsafe
    {
        private final Runnable beginReadTask;
        private final Runnable flushLaterTask;
        private FlushTask flushTaskInProgress;
        
        protected AbstractUnsafe() {
            this.beginReadTask = new Runnable() {
                @Override
                public void run() {
                    AbstractUnsafe.this.beginRead();
                }
            };
            this.flushLaterTask = new Runnable() {
                @Override
                public void run() {
                    AbstractChannel.this.flushNowPending = false;
                    AbstractUnsafe.this.flush(AbstractUnsafe.this.voidFuture());
                }
            };
        }
        
        @Override
        public final void sendFile(final FileRegion region, final ChannelPromise promise) {
            if (AbstractChannel.this.eventLoop().inEventLoop()) {
                if (this.outboundBufSize() > 0) {
                    this.flushNotifier(AbstractChannel.this.newPromise()).addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
                        @Override
                        public void operationComplete(final ChannelFuture cf) throws Exception {
                            AbstractUnsafe.this.sendFile0(region, promise);
                        }
                    });
                }
                else {
                    this.sendFile0(region, promise);
                }
            }
            else {
                AbstractChannel.this.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        AbstractUnsafe.this.sendFile(region, promise);
                    }
                });
            }
        }
        
        private void sendFile0(final FileRegion region, final ChannelPromise promise) {
            FlushTask task = this.flushTaskInProgress;
            if (task == null) {
                this.flushTaskInProgress = new FlushTask(region, promise);
                try {
                    AbstractChannel.this.doFlushFileRegion(region, promise);
                }
                catch (Throwable cause) {
                    region.release();
                    promise.setFailure(cause);
                }
                return;
            }
            while (true) {
                final FlushTask next = task.next;
                if (next == null) {
                    break;
                }
                task = next;
            }
            task.next = new FlushTask(region, promise);
        }
        
        @Override
        public final ChannelHandlerContext directOutboundContext() {
            return AbstractChannel.this.pipeline.head;
        }
        
        @Override
        public final ChannelPromise voidFuture() {
            return AbstractChannel.this.voidPromise;
        }
        
        @Override
        public final SocketAddress localAddress() {
            return AbstractChannel.this.localAddress0();
        }
        
        @Override
        public final SocketAddress remoteAddress() {
            return AbstractChannel.this.remoteAddress0();
        }
        
        @Override
        public final void register(final EventLoop eventLoop, final ChannelPromise promise) {
            if (eventLoop == null) {
                throw new NullPointerException("eventLoop");
            }
            if (AbstractChannel.this.isRegistered()) {
                promise.setFailure((Throwable)new IllegalStateException("registered to an event loop already"));
                return;
            }
            if (!AbstractChannel.this.isCompatible(eventLoop)) {
                promise.setFailure((Throwable)new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
                return;
            }
            AbstractChannel.this.eventLoop = eventLoop;
            if (eventLoop.inEventLoop()) {
                this.register0(promise);
            }
            else {
                try {
                    eventLoop.execute(new Runnable() {
                        @Override
                        public void run() {
                            AbstractUnsafe.this.register0(promise);
                        }
                    });
                }
                catch (Throwable t) {
                    AbstractChannel.logger.warn("Force-closing a channel whose registration task was unaccepted by an event loop: {}", AbstractChannel.this, t);
                    this.closeForcibly();
                    promise.setFailure(t);
                }
            }
        }
        
        private void register0(final ChannelPromise promise) {
            try {
                if (!this.ensureOpen(promise)) {
                    return;
                }
                final Runnable postRegisterTask = AbstractChannel.this.doRegister();
                AbstractChannel.this.registered = true;
                promise.setSuccess();
                AbstractChannel.this.pipeline.fireChannelRegistered();
                if (postRegisterTask != null) {
                    postRegisterTask.run();
                }
                if (AbstractChannel.this.isActive()) {
                    AbstractChannel.this.pipeline.fireChannelActive();
                }
            }
            catch (Throwable t) {
                this.closeForcibly();
                promise.setFailure(t);
                AbstractChannel.this.closeFuture.setClosed();
            }
        }
        
        @Override
        public final void bind(final SocketAddress localAddress, final ChannelPromise promise) {
            if (AbstractChannel.this.eventLoop().inEventLoop()) {
                if (!this.ensureOpen(promise)) {
                    return;
                }
                try {
                    final boolean wasActive = AbstractChannel.this.isActive();
                    if (!PlatformDependent.isWindows() && !PlatformDependent.isRoot() && Boolean.TRUE.equals(AbstractChannel.this.config().getOption(ChannelOption.SO_BROADCAST)) && localAddress instanceof InetSocketAddress && !((InetSocketAddress)localAddress).getAddress().isAnyLocalAddress()) {
                        AbstractChannel.logger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; binding to a non-wildcard address (" + localAddress + ") anyway as requested.");
                    }
                    AbstractChannel.this.doBind(localAddress);
                    promise.setSuccess();
                    if (!wasActive && AbstractChannel.this.isActive()) {
                        AbstractChannel.this.pipeline.fireChannelActive();
                    }
                }
                catch (Throwable t) {
                    promise.setFailure(t);
                    this.closeIfClosed();
                }
            }
            else {
                AbstractChannel.this.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        AbstractUnsafe.this.bind(localAddress, promise);
                    }
                });
            }
        }
        
        @Override
        public final void disconnect(final ChannelPromise promise) {
            if (AbstractChannel.this.eventLoop().inEventLoop()) {
                try {
                    final boolean wasActive = AbstractChannel.this.isActive();
                    AbstractChannel.this.doDisconnect();
                    promise.setSuccess();
                    if (wasActive && !AbstractChannel.this.isActive()) {
                        AbstractChannel.this.pipeline.fireChannelInactive();
                    }
                }
                catch (Throwable t) {
                    promise.setFailure(t);
                    this.closeIfClosed();
                }
            }
            else {
                AbstractChannel.this.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        AbstractUnsafe.this.disconnect(promise);
                    }
                });
            }
        }
        
        @Override
        public final void close(final ChannelPromise promise) {
            if (AbstractChannel.this.eventLoop().inEventLoop()) {
                final boolean wasActive = AbstractChannel.this.isActive();
                if (AbstractChannel.this.closeFuture.setClosed()) {
                    try {
                        AbstractChannel.this.doClose();
                        promise.setSuccess();
                    }
                    catch (Throwable t) {
                        promise.setFailure(t);
                    }
                    if (AbstractChannel.this.closedChannelException != null) {
                        AbstractChannel.this.closedChannelException = new ClosedChannelException();
                    }
                    AbstractChannel.this.flushFutureNotifier.notifyFlushFutures(AbstractChannel.this.closedChannelException);
                    if (wasActive && !AbstractChannel.this.isActive()) {
                        AbstractChannel.this.pipeline.fireChannelInactive();
                    }
                    this.deregister(this.voidFuture());
                }
                else {
                    promise.setSuccess();
                }
            }
            else {
                AbstractChannel.this.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        AbstractUnsafe.this.close(promise);
                    }
                });
            }
        }
        
        @Override
        public final void closeForcibly() {
            try {
                AbstractChannel.this.doClose();
            }
            catch (Exception e) {
                AbstractChannel.logger.warn("Failed to close a channel.", e);
            }
        }
        
        @Override
        public final void deregister(final ChannelPromise promise) {
            if (AbstractChannel.this.eventLoop().inEventLoop()) {
                if (!AbstractChannel.this.registered) {
                    promise.setSuccess();
                    return;
                }
                Runnable postTask = null;
                try {
                    postTask = AbstractChannel.this.doDeregister();
                }
                catch (Throwable t) {
                    AbstractChannel.logger.warn("Unexpected exception occurred while deregistering a channel.", t);
                }
                finally {
                    if (AbstractChannel.this.registered) {
                        AbstractChannel.this.registered = false;
                        promise.setSuccess();
                        AbstractChannel.this.pipeline.fireChannelUnregistered();
                    }
                    else {
                        promise.setSuccess();
                    }
                    if (postTask != null) {
                        postTask.run();
                    }
                }
            }
            else {
                AbstractChannel.this.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        AbstractUnsafe.this.deregister(promise);
                    }
                });
            }
        }
        
        @Override
        public void beginRead() {
            if (!AbstractChannel.this.isActive()) {
                return;
            }
            if (AbstractChannel.this.eventLoop().inEventLoop()) {
                try {
                    AbstractChannel.this.doBeginRead();
                }
                catch (Exception e) {
                    AbstractChannel.this.pipeline().fireExceptionCaught((Throwable)e);
                    this.close(AbstractChannel.this.unsafe().voidFuture());
                }
            }
            else {
                AbstractChannel.this.eventLoop().execute(this.beginReadTask);
            }
        }
        
        @Override
        public void flush(final ChannelPromise promise) {
            if (AbstractChannel.this.eventLoop().inEventLoop()) {
                FlushTask task = this.flushTaskInProgress;
                if (task != null) {
                    while (true) {
                        final FlushTask t = task.next;
                        if (t == null) {
                            break;
                        }
                        task = t.next;
                    }
                    task.next = new FlushTask(null, promise);
                    return;
                }
                this.flushNotifierAndFlush(promise);
            }
            else {
                AbstractChannel.this.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        AbstractUnsafe.this.flush(promise);
                    }
                });
            }
        }
        
        private void flushNotifierAndFlush(final ChannelPromise promise) {
            this.flushNotifier(promise);
            this.flush0();
        }
        
        private int outboundBufSize() {
            final ChannelHandlerContext ctx = this.directOutboundContext();
            int bufSize;
            if (AbstractChannel.this.metadata().bufferType() == BufType.BYTE) {
                bufSize = ctx.outboundByteBuffer().readableBytes();
            }
            else {
                bufSize = ctx.outboundMessageBuffer().size();
            }
            return bufSize;
        }
        
        private ChannelFuture flushNotifier(final ChannelPromise promise) {
            if (promise != AbstractChannel.this.voidPromise) {
                AbstractChannel.this.flushFutureNotifier.add(promise, this.outboundBufSize());
            }
            return promise;
        }
        
        private void flush0() {
            if (!AbstractChannel.this.inFlushNow) {
                try {
                    if (!AbstractChannel.this.isFlushPending()) {
                        this.flushNow();
                    }
                }
                catch (Throwable t) {
                    AbstractChannel.this.flushFutureNotifier.notifyFlushFutures(t);
                    if (t instanceof IOException) {
                        this.close(this.voidFuture());
                    }
                }
            }
            else if (!AbstractChannel.this.flushNowPending) {
                AbstractChannel.this.flushNowPending = true;
                AbstractChannel.this.eventLoop().execute(this.flushLaterTask);
            }
        }
        
        @Override
        public final void flushNow() {
            if (AbstractChannel.this.inFlushNow || this.flushTaskInProgress != null) {
                return;
            }
            AbstractChannel.this.inFlushNow = true;
            final ChannelHandlerContext ctx = this.directOutboundContext();
            Throwable cause = null;
            try {
                if (AbstractChannel.this.metadata().bufferType() == BufType.BYTE) {
                    final ByteBuf out = ctx.outboundByteBuffer();
                    final int oldSize = out.readableBytes();
                    try {
                        AbstractChannel.this.doFlushByteBuffer(out);
                    }
                    catch (Throwable t) {
                        cause = t;
                    }
                    finally {
                        final int delta = oldSize - out.readableBytes();
                        out.discardSomeReadBytes();
                        AbstractChannel.this.flushFutureNotifier.increaseWriteCounter(delta);
                    }
                }
                else {
                    final MessageBuf<Object> out2 = ctx.outboundMessageBuffer();
                    final int oldSize = out2.size();
                    try {
                        AbstractChannel.this.doFlushMessageBuffer(out2);
                    }
                    catch (Throwable t) {
                        cause = t;
                    }
                    finally {
                        AbstractChannel.this.flushFutureNotifier.increaseWriteCounter(oldSize - out2.size());
                    }
                }
                if (cause == null) {
                    AbstractChannel.this.flushFutureNotifier.notifyFlushFutures();
                }
                else {
                    AbstractChannel.this.flushFutureNotifier.notifyFlushFutures(cause);
                    if (cause instanceof IOException) {
                        this.close(this.voidFuture());
                    }
                }
            }
            finally {
                AbstractChannel.this.inFlushNow = false;
            }
        }
        
        protected final boolean ensureOpen(final ChannelPromise promise) {
            if (AbstractChannel.this.isOpen()) {
                return true;
            }
            final Exception e = new ClosedChannelException();
            promise.setFailure((Throwable)e);
            return false;
        }
        
        protected final void closeIfClosed() {
            if (AbstractChannel.this.isOpen()) {
                return;
            }
            this.close(this.voidFuture());
        }
        
        private final class FlushTask
        {
            final FileRegion region;
            final ChannelPromise promise;
            FlushTask next;
            
            FlushTask(final FileRegion region, final ChannelPromise promise) {
                this.region = region;
                (this.promise = promise).addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
                    @Override
                    public void operationComplete(final ChannelFuture future) throws Exception {
                        AbstractUnsafe.this.flushTaskInProgress = FlushTask.this.next;
                        if (FlushTask.this.next != null) {
                            try {
                                final FileRegion region = FlushTask.this.next.region;
                                if (region == null) {
                                    AbstractUnsafe.this.flushNotifierAndFlush(FlushTask.this.next.promise);
                                }
                                else {
                                    AbstractChannel.this.doFlushFileRegion(region, FlushTask.this.next.promise);
                                }
                            }
                            catch (Throwable cause) {
                                FlushTask.this.next.promise.setFailure(cause);
                            }
                        }
                        else {
                            AbstractChannel.this.flushFutureNotifier.notifyFlushFutures();
                        }
                    }
                });
            }
        }
    }
    
    private final class CloseFuture extends DefaultChannelPromise implements ChannelFuture.Unsafe
    {
        CloseFuture(final AbstractChannel ch) {
            super(ch);
        }
        
        @Override
        public ChannelPromise setSuccess() {
            throw new IllegalStateException();
        }
        
        @Override
        public ChannelPromise setFailure(final Throwable cause) {
            throw new IllegalStateException();
        }
        
        @Override
        public boolean trySuccess() {
            throw new IllegalStateException();
        }
        
        @Override
        public boolean tryFailure(final Throwable cause) {
            throw new IllegalStateException();
        }
        
        boolean setClosed() {
            try {
                AbstractChannel.this.doPreClose();
            }
            catch (Exception e) {
                AbstractChannel.logger.warn("doPreClose() raised an exception.", e);
            }
            return super.trySuccess();
        }
    }
}
