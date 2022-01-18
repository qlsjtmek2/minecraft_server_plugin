// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import com.google.common.base.Preconditions;
import java.util.concurrent.RejectedExecutionException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import com.google.common.annotations.Beta;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;

public final class MoreExecutors
{
    @Beta
    public static ExecutorService getExitingExecutorService(final ThreadPoolExecutor executor, final long terminationTimeout, final TimeUnit timeUnit) {
        executor.setThreadFactory(new ThreadFactoryBuilder().setDaemon(true).setThreadFactory(executor.getThreadFactory()).build());
        final ExecutorService service = Executors.unconfigurableExecutorService(executor);
        addDelayedShutdownHook(service, terminationTimeout, timeUnit);
        return service;
    }
    
    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(final ScheduledThreadPoolExecutor executor, final long terminationTimeout, final TimeUnit timeUnit) {
        executor.setThreadFactory(new ThreadFactoryBuilder().setDaemon(true).setThreadFactory(executor.getThreadFactory()).build());
        final ScheduledExecutorService service = Executors.unconfigurableScheduledExecutorService(executor);
        addDelayedShutdownHook(service, terminationTimeout, timeUnit);
        return service;
    }
    
    @Beta
    public static void addDelayedShutdownHook(final ExecutorService service, final long terminationTimeout, final TimeUnit timeUnit) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    service.shutdown();
                    service.awaitTermination(terminationTimeout, timeUnit);
                }
                catch (InterruptedException ex) {}
            }
        }));
    }
    
    @Beta
    public static ExecutorService getExitingExecutorService(final ThreadPoolExecutor executor) {
        return getExitingExecutorService(executor, 120L, TimeUnit.SECONDS);
    }
    
    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(final ScheduledThreadPoolExecutor executor) {
        return getExitingScheduledExecutorService(executor, 120L, TimeUnit.SECONDS);
    }
    
    public static ListeningExecutorService sameThreadExecutor() {
        return new SameThreadExecutorService();
    }
    
    public static ListeningExecutorService listeningDecorator(final ExecutorService delegate) {
        return (delegate instanceof ListeningExecutorService) ? ((ListeningExecutorService)delegate) : ((delegate instanceof ScheduledExecutorService) ? new ScheduledListeningDecorator((ScheduledExecutorService)delegate) : new ListeningDecorator(delegate));
    }
    
    public static ListeningScheduledExecutorService listeningDecorator(final ScheduledExecutorService delegate) {
        return (delegate instanceof ListeningScheduledExecutorService) ? ((ListeningScheduledExecutorService)delegate) : new ScheduledListeningDecorator(delegate);
    }
    
    private static class SameThreadExecutorService extends AbstractListeningExecutorService
    {
        private final Lock lock;
        private final Condition termination;
        private int runningTasks;
        private boolean shutdown;
        
        private SameThreadExecutorService() {
            this.lock = new ReentrantLock();
            this.termination = this.lock.newCondition();
            this.runningTasks = 0;
            this.shutdown = false;
        }
        
        public void execute(final Runnable command) {
            this.startTask();
            try {
                command.run();
            }
            finally {
                this.endTask();
            }
        }
        
        public boolean isShutdown() {
            this.lock.lock();
            try {
                return this.shutdown;
            }
            finally {
                this.lock.unlock();
            }
        }
        
        public void shutdown() {
            this.lock.lock();
            try {
                this.shutdown = true;
            }
            finally {
                this.lock.unlock();
            }
        }
        
        public List<Runnable> shutdownNow() {
            this.shutdown();
            return Collections.emptyList();
        }
        
        public boolean isTerminated() {
            this.lock.lock();
            try {
                return this.shutdown && this.runningTasks == 0;
            }
            finally {
                this.lock.unlock();
            }
        }
        
        public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
            long nanos = unit.toNanos(timeout);
            this.lock.lock();
            try {
                while (!this.isTerminated()) {
                    if (nanos <= 0L) {
                        return false;
                    }
                    nanos = this.termination.awaitNanos(nanos);
                }
                return true;
            }
            finally {
                this.lock.unlock();
            }
        }
        
        private void startTask() {
            this.lock.lock();
            try {
                if (this.isShutdown()) {
                    throw new RejectedExecutionException("Executor already shutdown");
                }
                ++this.runningTasks;
            }
            finally {
                this.lock.unlock();
            }
        }
        
        private void endTask() {
            this.lock.lock();
            try {
                --this.runningTasks;
                if (this.isTerminated()) {
                    this.termination.signalAll();
                }
            }
            finally {
                this.lock.unlock();
            }
        }
    }
    
    private static class ListeningDecorator extends AbstractListeningExecutorService
    {
        final ExecutorService delegate;
        
        ListeningDecorator(final ExecutorService delegate) {
            this.delegate = Preconditions.checkNotNull(delegate);
        }
        
        public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
            return this.delegate.awaitTermination(timeout, unit);
        }
        
        public boolean isShutdown() {
            return this.delegate.isShutdown();
        }
        
        public boolean isTerminated() {
            return this.delegate.isTerminated();
        }
        
        public void shutdown() {
            this.delegate.shutdown();
        }
        
        public List<Runnable> shutdownNow() {
            return this.delegate.shutdownNow();
        }
        
        public void execute(final Runnable command) {
            this.delegate.execute(command);
        }
    }
    
    private static class ScheduledListeningDecorator extends ListeningDecorator implements ListeningScheduledExecutorService
    {
        final ScheduledExecutorService delegate;
        
        ScheduledListeningDecorator(final ScheduledExecutorService delegate) {
            super(delegate);
            this.delegate = Preconditions.checkNotNull(delegate);
        }
        
        public ScheduledFuture<?> schedule(final Runnable command, final long delay, final TimeUnit unit) {
            return this.delegate.schedule(command, delay, unit);
        }
        
        public <V> ScheduledFuture<V> schedule(final Callable<V> callable, final long delay, final TimeUnit unit) {
            return this.delegate.schedule(callable, delay, unit);
        }
        
        public ScheduledFuture<?> scheduleAtFixedRate(final Runnable command, final long initialDelay, final long period, final TimeUnit unit) {
            return this.delegate.scheduleAtFixedRate(command, initialDelay, period, unit);
        }
        
        public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable command, final long initialDelay, final long delay, final TimeUnit unit) {
            return this.delegate.scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }
    }
}
