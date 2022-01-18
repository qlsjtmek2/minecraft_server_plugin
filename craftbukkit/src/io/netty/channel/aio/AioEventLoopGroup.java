// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.aio;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.AbstractExecutorService;
import io.netty.util.concurrent.EventExecutor;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import io.netty.channel.EventLoopException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.nio.channels.AsynchronousChannelGroup;
import io.netty.channel.MultithreadEventLoopGroup;

public class AioEventLoopGroup extends MultithreadEventLoopGroup
{
    private final AioExecutorService groupExecutor;
    private final AsynchronousChannelGroup group;
    
    public AsynchronousChannelGroup channelGroup() {
        return this.group;
    }
    
    public AioEventLoopGroup() {
        this(0);
    }
    
    public AioEventLoopGroup(final int nThreads) {
        this(nThreads, null);
    }
    
    public AioEventLoopGroup(final int nThreads, final ThreadFactory threadFactory) {
        super(nThreads, threadFactory, new Object[0]);
        this.groupExecutor = new AioExecutorService();
        try {
            this.group = AsynchronousChannelGroup.withThreadPool(this.groupExecutor);
        }
        catch (IOException e) {
            throw new EventLoopException("Failed to create an AsynchronousChannelGroup", e);
        }
    }
    
    @Override
    public void shutdown() {
        boolean interrupted = false;
        try {
            this.group.shutdownNow();
        }
        catch (IOException e) {
            throw new EventLoopException("failed to shut down a channel group", e);
        }
        while (!this.groupExecutor.isTerminated()) {
            try {
                this.groupExecutor.awaitTermination(1L, TimeUnit.HOURS);
            }
            catch (InterruptedException e2) {
                interrupted = true;
            }
        }
        super.shutdown();
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Override
    protected EventExecutor newChild(final ThreadFactory threadFactory, final Object... args) throws Exception {
        return new AioEventLoop(this, threadFactory);
    }
    
    private static final class AioExecutorService extends AbstractExecutorService
    {
        private final CountDownLatch latch;
        
        private AioExecutorService() {
            this.latch = new CountDownLatch(1);
        }
        
        @Override
        public void shutdown() {
            this.latch.countDown();
        }
        
        @Override
        public List<Runnable> shutdownNow() {
            this.shutdown();
            return Collections.emptyList();
        }
        
        @Override
        public boolean isShutdown() {
            return this.latch.getCount() == 0L;
        }
        
        @Override
        public boolean isTerminated() {
            return this.isShutdown();
        }
        
        @Override
        public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
            return this.latch.await(timeout, unit);
        }
        
        @Override
        public void execute(final Runnable command) {
            command.run();
        }
    }
}
