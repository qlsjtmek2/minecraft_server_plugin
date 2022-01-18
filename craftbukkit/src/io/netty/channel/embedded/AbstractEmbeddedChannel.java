// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.embedded;

import io.netty.channel.ChannelInboundByteHandler;
import io.netty.channel.ChannelInboundMessageHandler;
import io.netty.channel.ChannelStateHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.channel.ChannelFuture;
import io.netty.buffer.Buf;
import io.netty.channel.EventLoop;
import java.nio.channels.ClosedChannelException;
import io.netty.util.internal.PlatformDependent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.buffer.Unpooled;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import java.net.SocketAddress;
import io.netty.channel.ChannelConfig;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.channel.AbstractChannel;

public abstract class AbstractEmbeddedChannel<O> extends AbstractChannel
{
    private static final InternalLogger logger;
    private final EmbeddedEventLoop loop;
    private final ChannelConfig config;
    private final SocketAddress localAddress;
    private final SocketAddress remoteAddress;
    private final MessageBuf<Object> lastInboundMessageBuffer;
    private final ByteBuf lastInboundByteBuffer;
    protected final Object lastOutboundBuffer;
    private Throwable lastException;
    private int state;
    
    AbstractEmbeddedChannel(final Object lastOutboundBuffer, final ChannelHandler... handlers) {
        super(null, null);
        this.loop = new EmbeddedEventLoop();
        this.config = new DefaultChannelConfig(this);
        this.localAddress = new EmbeddedSocketAddress();
        this.remoteAddress = new EmbeddedSocketAddress();
        this.lastInboundMessageBuffer = Unpooled.messageBuffer();
        this.lastInboundByteBuffer = Unpooled.buffer();
        if (handlers == null) {
            throw new NullPointerException("handlers");
        }
        this.lastOutboundBuffer = lastOutboundBuffer;
        int nHandlers = 0;
        boolean hasBuffer = false;
        final ChannelPipeline p = this.pipeline();
        for (final ChannelHandler h : handlers) {
            if (h == null) {
                break;
            }
            ++nHandlers;
            final ChannelHandlerContext ctx = p.addLast(h).context(h);
            if (ctx.hasInboundByteBuffer() || ctx.hasOutboundByteBuffer() || ctx.hasInboundMessageBuffer() || ctx.hasOutboundMessageBuffer()) {
                hasBuffer = true;
            }
        }
        if (nHandlers == 0) {
            throw new IllegalArgumentException("handlers is empty.");
        }
        if (!hasBuffer) {
            throw new IllegalArgumentException("handlers does not provide any buffers.");
        }
        p.addLast(new LastInboundMessageHandler(), new LastInboundByteHandler());
        this.loop.register(this);
    }
    
    @Override
    public ChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isOpen() {
        return this.state < 2;
    }
    
    @Override
    public boolean isActive() {
        return this.state == 1;
    }
    
    public MessageBuf<Object> lastInboundMessageBuffer() {
        return this.lastInboundMessageBuffer;
    }
    
    public ByteBuf lastInboundByteBuffer() {
        return this.lastInboundByteBuffer;
    }
    
    public Object readInbound() {
        if (this.lastInboundByteBuffer.isReadable()) {
            try {
                return this.lastInboundByteBuffer.readBytes(this.lastInboundByteBuffer.readableBytes());
            }
            finally {
                this.lastInboundByteBuffer.clear();
            }
        }
        return this.lastInboundMessageBuffer.poll();
    }
    
    public void runPendingTasks() {
        try {
            this.loop.runTasks();
        }
        catch (Exception e) {
            this.recordException(e);
        }
    }
    
    private void recordException(final Throwable cause) {
        if (this.lastException == null) {
            this.lastException = cause;
        }
        else {
            AbstractEmbeddedChannel.logger.warn("More than one exception was raised. Will report only the first one and log others.", cause);
        }
    }
    
    public void checkException() {
        final Throwable t = this.lastException;
        if (t == null) {
            return;
        }
        this.lastException = null;
        PlatformDependent.throwException(t);
    }
    
    protected final void ensureOpen() {
        if (!this.isOpen()) {
            this.recordException(new ClosedChannelException());
            this.checkException();
        }
    }
    
    @Override
    protected boolean isCompatible(final EventLoop loop) {
        return loop instanceof EmbeddedEventLoop;
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.isActive() ? this.localAddress : null;
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return this.isActive() ? this.remoteAddress : null;
    }
    
    @Override
    protected Runnable doRegister() throws Exception {
        this.state = 1;
        return null;
    }
    
    @Override
    protected void doBind(final SocketAddress localAddress) throws Exception {
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doClose() throws Exception {
        this.state = 2;
    }
    
    @Override
    protected Runnable doDeregister() throws Exception {
        return new Runnable() {
            @Override
            public void run() {
                AbstractEmbeddedChannel.this.runPendingTasks();
            }
        };
    }
    
    @Override
    protected void doBeginRead() throws Exception {
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return new DefaultUnsafe();
    }
    
    @Override
    protected boolean isFlushPending() {
        return false;
    }
    
    public abstract O readOutbound();
    
    public abstract Buf inboundBuffer();
    
    public abstract Buf lastOutboundBuffer();
    
    public boolean finish() {
        this.close();
        this.runPendingTasks();
        this.checkException();
        return this.lastInboundByteBuffer().isReadable() || !this.lastInboundMessageBuffer().isEmpty() || this.hasReadableOutboundBuffer();
    }
    
    public boolean writeInbound(final O data) {
        this.ensureOpen();
        this.writeInbound0(data);
        this.pipeline().fireInboundBufferUpdated();
        this.runPendingTasks();
        this.checkException();
        return this.lastInboundByteBuffer().isReadable() || !this.lastInboundMessageBuffer().isEmpty();
    }
    
    public boolean writeOutbound(final Object data) {
        this.ensureOpen();
        final ChannelFuture future = this.write(data);
        assert future.isDone();
        if (future.cause() != null) {
            this.recordException(future.cause());
        }
        this.runPendingTasks();
        this.checkException();
        return this.hasReadableOutboundBuffer();
    }
    
    protected abstract boolean hasReadableOutboundBuffer();
    
    protected abstract void writeInbound0(final O p0);
    
    static {
        logger = InternalLoggerFactory.getInstance(AbstractEmbeddedChannel.class);
    }
    
    private class DefaultUnsafe extends AbstractUnsafe
    {
        @Override
        public void connect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
            promise.setSuccess();
        }
    }
    
    private final class LastInboundMessageHandler extends ChannelStateHandlerAdapter implements ChannelInboundMessageHandler<Object>
    {
        @Override
        public MessageBuf<Object> newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
            return AbstractEmbeddedChannel.this.lastInboundMessageBuffer;
        }
        
        @Override
        public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        }
        
        @Override
        public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        }
        
        @Override
        public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
            AbstractEmbeddedChannel.this.recordException(cause);
        }
    }
    
    private final class LastInboundByteHandler extends ChannelStateHandlerAdapter implements ChannelInboundByteHandler
    {
        @Override
        public ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
            return AbstractEmbeddedChannel.this.lastInboundByteBuffer;
        }
        
        @Override
        public void discardInboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        }
        
        @Override
        public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        }
        
        @Override
        public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        }
    }
}
