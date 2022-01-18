// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.timeout;

import io.netty.util.concurrent.EventExecutor;
import io.netty.channel.FileRegion;
import java.net.SocketAddress;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;
import io.netty.channel.ChannelOperationHandler;
import io.netty.channel.ChannelStateHandlerAdapter;

public class IdleStateHandler extends ChannelStateHandlerAdapter implements ChannelOperationHandler
{
    private final long readerIdleTimeMillis;
    private final long writerIdleTimeMillis;
    private final long allIdleTimeMillis;
    volatile ScheduledFuture<?> readerIdleTimeout;
    volatile long lastReadTime;
    private boolean firstReaderIdleEvent;
    volatile ScheduledFuture<?> writerIdleTimeout;
    volatile long lastWriteTime;
    private boolean firstWriterIdleEvent;
    volatile ScheduledFuture<?> allIdleTimeout;
    private boolean firstAllIdleEvent;
    private volatile int state;
    
    public IdleStateHandler(final int readerIdleTimeSeconds, final int writerIdleTimeSeconds, final int allIdleTimeSeconds) {
        this(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds, TimeUnit.SECONDS);
    }
    
    public IdleStateHandler(final long readerIdleTime, final long writerIdleTime, final long allIdleTime, final TimeUnit unit) {
        this.firstReaderIdleEvent = true;
        this.firstWriterIdleEvent = true;
        this.firstAllIdleEvent = true;
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (readerIdleTime <= 0L) {
            this.readerIdleTimeMillis = 0L;
        }
        else {
            this.readerIdleTimeMillis = Math.max(unit.toMillis(readerIdleTime), 1L);
        }
        if (writerIdleTime <= 0L) {
            this.writerIdleTimeMillis = 0L;
        }
        else {
            this.writerIdleTimeMillis = Math.max(unit.toMillis(writerIdleTime), 1L);
        }
        if (allIdleTime <= 0L) {
            this.allIdleTimeMillis = 0L;
        }
        else {
            this.allIdleTimeMillis = Math.max(unit.toMillis(allIdleTime), 1L);
        }
    }
    
    public long getReaderIdleTimeInMillis() {
        return this.readerIdleTimeMillis;
    }
    
    public long getWriterIdleTimeInMillis() {
        return this.writerIdleTimeMillis;
    }
    
    public long getAllIdleTimeInMillis() {
        return this.allIdleTimeMillis;
    }
    
    @Override
    public void beforeAdd(final ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isActive() & ctx.channel().isRegistered()) {
            this.initialize(ctx);
        }
    }
    
    @Override
    public void beforeRemove(final ChannelHandlerContext ctx) throws Exception {
        this.destroy();
    }
    
    @Override
    public void channelRegistered(final ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isActive()) {
            this.initialize(ctx);
        }
        super.channelRegistered(ctx);
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        this.initialize(ctx);
        super.channelActive(ctx);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        this.destroy();
        super.channelInactive(ctx);
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        this.lastReadTime = System.currentTimeMillis();
        final boolean b = true;
        this.firstAllIdleEvent = b;
        this.firstReaderIdleEvent = b;
        ctx.fireInboundBufferUpdated();
    }
    
    @Override
    public void read(final ChannelHandlerContext ctx) {
        ctx.read();
    }
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        promise.addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture future) throws Exception {
                IdleStateHandler.this.lastWriteTime = System.currentTimeMillis();
                IdleStateHandler.this.firstWriterIdleEvent = (IdleStateHandler.this.firstAllIdleEvent = true);
            }
        });
        ctx.flush(promise);
    }
    
    @Override
    public void bind(final ChannelHandlerContext ctx, final SocketAddress localAddress, final ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }
    
    @Override
    public void connect(final ChannelHandlerContext ctx, final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) throws Exception {
        ctx.connect(remoteAddress, localAddress, promise);
    }
    
    @Override
    public void disconnect(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        ctx.disconnect(promise);
    }
    
    @Override
    public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        ctx.close(promise);
    }
    
    @Override
    public void deregister(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        ctx.deregister(promise);
    }
    
    @Override
    public void sendFile(final ChannelHandlerContext ctx, final FileRegion region, final ChannelPromise promise) throws Exception {
        promise.addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture future) throws Exception {
                IdleStateHandler.this.lastWriteTime = System.currentTimeMillis();
                IdleStateHandler.this.firstWriterIdleEvent = (IdleStateHandler.this.firstAllIdleEvent = true);
            }
        });
        ctx.sendFile(region, promise);
    }
    
    private void initialize(final ChannelHandlerContext ctx) {
        switch (this.state) {
            case 1:
            case 2: {}
            default: {
                this.state = 1;
                final EventExecutor loop = ctx.executor();
                final long currentTimeMillis = System.currentTimeMillis();
                this.lastWriteTime = currentTimeMillis;
                this.lastReadTime = currentTimeMillis;
                if (this.readerIdleTimeMillis > 0L) {
                    this.readerIdleTimeout = loop.schedule((Runnable)new ReaderIdleTimeoutTask(ctx), this.readerIdleTimeMillis, TimeUnit.MILLISECONDS);
                }
                if (this.writerIdleTimeMillis > 0L) {
                    this.writerIdleTimeout = loop.schedule((Runnable)new WriterIdleTimeoutTask(ctx), this.writerIdleTimeMillis, TimeUnit.MILLISECONDS);
                }
                if (this.allIdleTimeMillis > 0L) {
                    this.allIdleTimeout = loop.schedule((Runnable)new AllIdleTimeoutTask(ctx), this.allIdleTimeMillis, TimeUnit.MILLISECONDS);
                }
            }
        }
    }
    
    private void destroy() {
        this.state = 2;
        if (this.readerIdleTimeout != null) {
            this.readerIdleTimeout.cancel(false);
            this.readerIdleTimeout = null;
        }
        if (this.writerIdleTimeout != null) {
            this.writerIdleTimeout.cancel(false);
            this.writerIdleTimeout = null;
        }
        if (this.allIdleTimeout != null) {
            this.allIdleTimeout.cancel(false);
            this.allIdleTimeout = null;
        }
    }
    
    protected void channelIdle(final ChannelHandlerContext ctx, final IdleStateEvent evt) throws Exception {
        ctx.fireUserEventTriggered((Object)evt);
    }
    
    private final class ReaderIdleTimeoutTask implements Runnable
    {
        private final ChannelHandlerContext ctx;
        
        ReaderIdleTimeoutTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }
        
        @Override
        public void run() {
            if (!this.ctx.channel().isOpen()) {
                return;
            }
            final long currentTime = System.currentTimeMillis();
            final long lastReadTime = IdleStateHandler.this.lastReadTime;
            final long nextDelay = IdleStateHandler.this.readerIdleTimeMillis - (currentTime - lastReadTime);
            if (nextDelay <= 0L) {
                IdleStateHandler.this.readerIdleTimeout = this.ctx.executor().schedule((Runnable)this, IdleStateHandler.this.readerIdleTimeMillis, TimeUnit.MILLISECONDS);
                try {
                    IdleStateEvent event;
                    if (IdleStateHandler.this.firstReaderIdleEvent) {
                        IdleStateHandler.this.firstReaderIdleEvent = false;
                        event = IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT;
                    }
                    else {
                        event = IdleStateEvent.READER_IDLE_STATE_EVENT;
                    }
                    IdleStateHandler.this.channelIdle(this.ctx, event);
                }
                catch (Throwable t) {
                    this.ctx.fireExceptionCaught(t);
                }
            }
            else {
                IdleStateHandler.this.readerIdleTimeout = this.ctx.executor().schedule((Runnable)this, nextDelay, TimeUnit.MILLISECONDS);
            }
        }
    }
    
    private final class WriterIdleTimeoutTask implements Runnable
    {
        private final ChannelHandlerContext ctx;
        
        WriterIdleTimeoutTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }
        
        @Override
        public void run() {
            if (!this.ctx.channel().isOpen()) {
                return;
            }
            final long currentTime = System.currentTimeMillis();
            final long lastWriteTime = IdleStateHandler.this.lastWriteTime;
            final long nextDelay = IdleStateHandler.this.writerIdleTimeMillis - (currentTime - lastWriteTime);
            if (nextDelay <= 0L) {
                IdleStateHandler.this.writerIdleTimeout = this.ctx.executor().schedule((Runnable)this, IdleStateHandler.this.writerIdleTimeMillis, TimeUnit.MILLISECONDS);
                try {
                    IdleStateEvent event;
                    if (IdleStateHandler.this.firstWriterIdleEvent) {
                        IdleStateHandler.this.firstWriterIdleEvent = false;
                        event = IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT;
                    }
                    else {
                        event = IdleStateEvent.WRITER_IDLE_STATE_EVENT;
                    }
                    IdleStateHandler.this.channelIdle(this.ctx, event);
                }
                catch (Throwable t) {
                    this.ctx.fireExceptionCaught(t);
                }
            }
            else {
                IdleStateHandler.this.writerIdleTimeout = this.ctx.executor().schedule((Runnable)this, nextDelay, TimeUnit.MILLISECONDS);
            }
        }
    }
    
    private final class AllIdleTimeoutTask implements Runnable
    {
        private final ChannelHandlerContext ctx;
        
        AllIdleTimeoutTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }
        
        @Override
        public void run() {
            if (!this.ctx.channel().isOpen()) {
                return;
            }
            final long currentTime = System.currentTimeMillis();
            final long lastIoTime = Math.max(IdleStateHandler.this.lastReadTime, IdleStateHandler.this.lastWriteTime);
            final long nextDelay = IdleStateHandler.this.allIdleTimeMillis - (currentTime - lastIoTime);
            if (nextDelay <= 0L) {
                IdleStateHandler.this.allIdleTimeout = this.ctx.executor().schedule((Runnable)this, IdleStateHandler.this.allIdleTimeMillis, TimeUnit.MILLISECONDS);
                try {
                    IdleStateEvent event;
                    if (IdleStateHandler.this.firstAllIdleEvent) {
                        IdleStateHandler.this.firstAllIdleEvent = false;
                        event = IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT;
                    }
                    else {
                        event = IdleStateEvent.ALL_IDLE_STATE_EVENT;
                    }
                    IdleStateHandler.this.channelIdle(this.ctx, event);
                }
                catch (Throwable t) {
                    this.ctx.fireExceptionCaught(t);
                }
            }
            else {
                IdleStateHandler.this.allIdleTimeout = this.ctx.executor().schedule((Runnable)this, nextDelay, TimeUnit.MILLISECONDS);
            }
        }
    }
}
