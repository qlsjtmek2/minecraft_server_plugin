// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Delayed;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.Queue;
import io.netty.util.internal.logging.InternalLogger;

public abstract class SingleThreadEventExecutor extends AbstractEventExecutor
{
    private static final InternalLogger logger;
    private static final long SHUTDOWN_DELAY_NANOS;
    static final ThreadLocal<SingleThreadEventExecutor> CURRENT_EVENT_LOOP;
    private static final int ST_NOT_STARTED = 1;
    private static final int ST_STARTED = 2;
    private static final int ST_SHUTDOWN = 3;
    private static final int ST_TERMINATED = 4;
    private static final Runnable WAKEUP_TASK;
    private final EventExecutorGroup parent;
    private final Queue<Runnable> taskQueue;
    final Queue<ScheduledFutureTask<?>> delayedTaskQueue;
    private final Thread thread;
    private final Object stateLock;
    private final Semaphore threadLock;
    private final Set<Runnable> shutdownHooks;
    private volatile int state;
    private long lastAccessTimeNanos;
    private static final long SCHEDULE_PURGE_INTERVAL;
    private static final long START_TIME;
    private static final AtomicLong nextTaskId;
    
    public static SingleThreadEventExecutor currentEventLoop() {
        return SingleThreadEventExecutor.CURRENT_EVENT_LOOP.get();
    }
    
    protected SingleThreadEventExecutor(final EventExecutorGroup parent, final ThreadFactory threadFactory) {
        this.delayedTaskQueue = new PriorityQueue<ScheduledFutureTask<?>>();
        this.stateLock = new Object();
        this.threadLock = new Semaphore(0);
        this.shutdownHooks = new LinkedHashSet<Runnable>();
        this.state = 1;
        if (threadFactory == null) {
            throw new NullPointerException("threadFactory");
        }
        this.parent = parent;
        this.thread = threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                SingleThreadEventExecutor.CURRENT_EVENT_LOOP.set(SingleThreadEventExecutor.this);
                boolean success = false;
                try {
                    SingleThreadEventExecutor.this.run();
                    success = true;
                }
                catch (Throwable t) {
                    SingleThreadEventExecutor.logger.warn("Unexpected exception from an event executor: ", t);
                    SingleThreadEventExecutor.this.shutdown();
                    if (success && SingleThreadEventExecutor.this.lastAccessTimeNanos == 0L) {
                        SingleThreadEventExecutor.logger.error("Buggy " + EventExecutor.class.getSimpleName() + " implementation; " + SingleThreadEventExecutor.class.getSimpleName() + ".confirmShutdown() must be called " + "before run() implementation terminates.");
                    }
                    try {
                        while (!SingleThreadEventExecutor.this.confirmShutdown()) {}
                        synchronized (SingleThreadEventExecutor.this.stateLock) {
                            SingleThreadEventExecutor.this.state = 4;
                        }
                    }
                    finally {
                        try {
                            SingleThreadEventExecutor.this.cleanup();
                        }
                        finally {
                            SingleThreadEventExecutor.this.threadLock.release();
                            if (!SingleThreadEventExecutor.this.taskQueue.isEmpty()) {
                                SingleThreadEventExecutor.logger.warn("An event executor terminated with non-empty task queue (" + SingleThreadEventExecutor.this.taskQueue.size() + ')');
                            }
                        }
                    }
                }
                finally {
                    if (success && SingleThreadEventExecutor.this.lastAccessTimeNanos == 0L) {
                        SingleThreadEventExecutor.logger.error("Buggy " + EventExecutor.class.getSimpleName() + " implementation; " + SingleThreadEventExecutor.class.getSimpleName() + ".confirmShutdown() must be called " + "before run() implementation terminates.");
                    }
                    try {
                        while (!SingleThreadEventExecutor.this.confirmShutdown()) {}
                        synchronized (SingleThreadEventExecutor.this.stateLock) {
                            SingleThreadEventExecutor.this.state = 4;
                        }
                    }
                    finally {
                        try {
                            SingleThreadEventExecutor.this.cleanup();
                        }
                        finally {
                            SingleThreadEventExecutor.this.threadLock.release();
                            if (!SingleThreadEventExecutor.this.taskQueue.isEmpty()) {
                                SingleThreadEventExecutor.logger.warn("An event executor terminated with non-empty task queue (" + SingleThreadEventExecutor.this.taskQueue.size() + ')');
                            }
                        }
                    }
                }
            }
        });
        this.taskQueue = this.newTaskQueue();
    }
    
    protected Queue<Runnable> newTaskQueue() {
        return new LinkedBlockingQueue<Runnable>();
    }
    
    @Override
    public EventExecutorGroup parent() {
        return this.parent;
    }
    
    protected void interruptThread() {
        this.thread.interrupt();
    }
    
    protected Runnable pollTask() {
        assert this.inEventLoop();
        Runnable task;
        do {
            task = this.taskQueue.poll();
        } while (task == SingleThreadEventExecutor.WAKEUP_TASK);
        return task;
    }
    
    protected Runnable takeTask() throws InterruptedException {
        assert this.inEventLoop();
        if (!(this.taskQueue instanceof BlockingQueue)) {
            throw new UnsupportedOperationException();
        }
        final BlockingQueue<Runnable> taskQueue = (BlockingQueue<Runnable>)(BlockingQueue)this.taskQueue;
        while (true) {
            final ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
            if (delayedTask == null) {
                return taskQueue.take();
            }
            final long delayNanos = delayedTask.delayNanos();
            Runnable task;
            if (delayNanos > 0L) {
                task = taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
            }
            else {
                task = taskQueue.poll();
            }
            if (task == null) {
                this.fetchFromDelayedQueue();
                task = taskQueue.poll();
            }
            if (task != null) {
                return task;
            }
        }
    }
    
    private void fetchFromDelayedQueue() {
        long nanoTime = 0L;
        while (true) {
            final ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
            if (delayedTask == null) {
                break;
            }
            if (nanoTime == 0L) {
                nanoTime = nanoTime();
            }
            if (delayedTask.deadlineNanos() > nanoTime) {
                break;
            }
            this.delayedTaskQueue.remove();
            this.taskQueue.add(delayedTask);
        }
    }
    
    protected Runnable peekTask() {
        assert this.inEventLoop();
        return this.taskQueue.peek();
    }
    
    protected boolean hasTasks() {
        assert this.inEventLoop();
        return !this.taskQueue.isEmpty();
    }
    
    protected void addTask(final Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        }
        if (this.isTerminated()) {
            reject();
        }
        this.taskQueue.add(task);
    }
    
    protected boolean removeTask(final Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        }
        return this.taskQueue.remove(task);
    }
    
    protected boolean runAllTasks() {
        this.fetchFromDelayedQueue();
        Runnable task = this.pollTask();
        if (task == null) {
            return false;
        }
        do {
            try {
                task.run();
            }
            catch (Throwable t) {
                SingleThreadEventExecutor.logger.warn("A task raised an exception.", t);
            }
            task = this.pollTask();
        } while (task != null);
        return true;
    }
    
    protected boolean runAllTasks(final long timeoutNanos) {
        this.fetchFromDelayedQueue();
        Runnable task = this.pollTask();
        if (task == null) {
            return false;
        }
        final long deadline = System.nanoTime() + timeoutNanos;
        long runTasks = 0L;
        do {
            try {
                task.run();
            }
            catch (Throwable t) {
                SingleThreadEventExecutor.logger.warn("A task raised an exception.", t);
            }
            ++runTasks;
            if ((runTasks & 0x3FL) == 0x0L && System.nanoTime() >= deadline) {
                break;
            }
            task = this.pollTask();
        } while (task != null);
        return true;
    }
    
    protected long delayNanos(final long currentTimeNanos) {
        final ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
        if (delayedTask == null) {
            return SingleThreadEventExecutor.SCHEDULE_PURGE_INTERVAL;
        }
        return delayedTask.delayNanos(currentTimeNanos);
    }
    
    protected abstract void run();
    
    protected void cleanup() {
    }
    
    protected void wakeup(final boolean inEventLoop) {
        if (!inEventLoop || this.state == 3) {
            this.addTask(SingleThreadEventExecutor.WAKEUP_TASK);
        }
    }
    
    @Override
    public boolean inEventLoop() {
        return this.inEventLoop(Thread.currentThread());
    }
    
    @Override
    public boolean inEventLoop(final Thread thread) {
        return thread == this.thread;
    }
    
    public void addShutdownHook(final Runnable task) {
        if (this.inEventLoop()) {
            this.shutdownHooks.add(task);
        }
        else {
            this.execute(new Runnable() {
                @Override
                public void run() {
                    SingleThreadEventExecutor.this.shutdownHooks.add(task);
                }
            });
        }
    }
    
    public void removeShutdownHook(final Runnable task) {
        if (this.inEventLoop()) {
            this.shutdownHooks.remove(task);
        }
        else {
            this.execute(new Runnable() {
                @Override
                public void run() {
                    SingleThreadEventExecutor.this.shutdownHooks.remove(task);
                }
            });
        }
    }
    
    private boolean runShutdownHooks() {
        boolean ran = false;
        while (!this.shutdownHooks.isEmpty()) {
            final List<Runnable> copy = new ArrayList<Runnable>(this.shutdownHooks);
            this.shutdownHooks.clear();
            for (final Runnable task : copy) {
                try {
                    task.run();
                    ran = true;
                }
                catch (Throwable t) {
                    SingleThreadEventExecutor.logger.warn("Shutdown hook raised an exception.", t);
                }
            }
        }
        return ran;
    }
    
    @Override
    public void shutdown() {
        if (this.isShutdown()) {
            return;
        }
        final boolean inEventLoop = this.inEventLoop();
        boolean wakeup = true;
        if (inEventLoop) {
            synchronized (this.stateLock) {
                assert this.state == 2;
                this.state = 3;
            }
        }
        else {
            synchronized (this.stateLock) {
                switch (this.state) {
                    case 1: {
                        this.state = 3;
                        this.thread.start();
                        break;
                    }
                    case 2: {
                        this.state = 3;
                        break;
                    }
                    default: {
                        wakeup = false;
                        break;
                    }
                }
            }
        }
        if (wakeup) {
            this.wakeup(inEventLoop);
        }
    }
    
    @Override
    public List<Runnable> shutdownNow() {
        this.shutdown();
        return Collections.emptyList();
    }
    
    @Override
    public boolean isShutdown() {
        return this.state >= 3;
    }
    
    @Override
    public boolean isTerminated() {
        return this.state == 4;
    }
    
    protected boolean confirmShutdown() {
        if (!this.isShutdown()) {
            throw new IllegalStateException("must be invoked after shutdown()");
        }
        if (!this.inEventLoop()) {
            throw new IllegalStateException("must be invoked from an event loop");
        }
        this.cancelDelayedTasks();
        if (this.runAllTasks() || this.runShutdownHooks()) {
            this.lastAccessTimeNanos = 0L;
            this.wakeup(true);
            return false;
        }
        if (this.lastAccessTimeNanos == 0L || System.nanoTime() - this.lastAccessTimeNanos < SingleThreadEventExecutor.SHUTDOWN_DELAY_NANOS) {
            if (this.lastAccessTimeNanos == 0L) {
                this.lastAccessTimeNanos = System.nanoTime();
            }
            this.wakeup(true);
            try {
                Thread.sleep(100L);
            }
            catch (InterruptedException ex) {}
            return false;
        }
        return true;
    }
    
    private void cancelDelayedTasks() {
        if (this.delayedTaskQueue.isEmpty()) {
            return;
        }
        final ScheduledFutureTask[] arr$;
        final ScheduledFutureTask<?>[] delayedTasks = (ScheduledFutureTask<?>[])(arr$ = this.delayedTaskQueue.toArray(new ScheduledFutureTask[this.delayedTaskQueue.size()]));
        for (final ScheduledFutureTask<?> task : arr$) {
            task.cancel(false);
        }
        this.delayedTaskQueue.clear();
    }
    
    @Override
    public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (this.inEventLoop()) {
            throw new IllegalStateException("cannot await termination of the current thread");
        }
        if (this.threadLock.tryAcquire(timeout, unit)) {
            this.threadLock.release();
        }
        return this.isTerminated();
    }
    
    @Override
    public void execute(final Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        }
        if (this.inEventLoop()) {
            this.addTask(task);
            this.wakeup(true);
        }
        else {
            this.startThread();
            this.addTask(task);
            if (this.isTerminated() && this.removeTask(task)) {
                reject();
            }
            this.wakeup(false);
        }
    }
    
    protected static void reject() {
        throw new RejectedExecutionException("event executor terminated");
    }
    
    private static long nanoTime() {
        return System.nanoTime() - SingleThreadEventExecutor.START_TIME;
    }
    
    private static long deadlineNanos(final long delay) {
        return nanoTime() + delay;
    }
    
    @Override
    public ScheduledFuture<?> schedule(final Runnable command, final long delay, final TimeUnit unit) {
        if (command == null) {
            throw new NullPointerException("command");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (delay < 0L) {
            throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", delay));
        }
        return this.schedule((ScheduledFutureTask<?>)new ScheduledFutureTask<Object>(this, command, null, deadlineNanos(unit.toNanos(delay))));
    }
    
    @Override
    public <V> ScheduledFuture<V> schedule(final Callable<V> callable, final long delay, final TimeUnit unit) {
        if (callable == null) {
            throw new NullPointerException("callable");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (delay < 0L) {
            throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", delay));
        }
        return this.schedule(new ScheduledFutureTask<V>(this, callable, deadlineNanos(unit.toNanos(delay))));
    }
    
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(final Runnable command, final long initialDelay, final long period, final TimeUnit unit) {
        if (command == null) {
            throw new NullPointerException("command");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (initialDelay < 0L) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", initialDelay));
        }
        if (period <= 0L) {
            throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", period));
        }
        return this.schedule((ScheduledFutureTask<?>)new ScheduledFutureTask<Object>(this, Executors.callable(command, (V)null), deadlineNanos(unit.toNanos(initialDelay)), unit.toNanos(period)));
    }
    
    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable command, final long initialDelay, final long delay, final TimeUnit unit) {
        if (command == null) {
            throw new NullPointerException("command");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (initialDelay < 0L) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", initialDelay));
        }
        if (delay <= 0L) {
            throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", delay));
        }
        return this.schedule((ScheduledFutureTask<?>)new ScheduledFutureTask<Object>(this, Executors.callable(command, (V)null), deadlineNanos(unit.toNanos(initialDelay)), -unit.toNanos(delay)));
    }
    
    private <V> ScheduledFuture<V> schedule(final ScheduledFutureTask<V> task) {
        if (task == null) {
            throw new NullPointerException("task");
        }
        if (this.inEventLoop()) {
            this.delayedTaskQueue.add(task);
        }
        else {
            this.execute(new Runnable() {
                @Override
                public void run() {
                    SingleThreadEventExecutor.this.delayedTaskQueue.add(task);
                }
            });
        }
        return task;
    }
    
    private void startThread() {
        synchronized (this.stateLock) {
            if (this.state == 1) {
                this.state = 2;
                this.delayedTaskQueue.add(new ScheduledFutureTask<Object>(this, Executors.callable(new PurgeTask(), (Object)null), deadlineNanos(SingleThreadEventExecutor.SCHEDULE_PURGE_INTERVAL), -SingleThreadEventExecutor.SCHEDULE_PURGE_INTERVAL));
                this.thread.start();
            }
        }
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(SingleThreadEventExecutor.class);
        SHUTDOWN_DELAY_NANOS = TimeUnit.SECONDS.toNanos(2L);
        CURRENT_EVENT_LOOP = new ThreadLocal<SingleThreadEventExecutor>();
        WAKEUP_TASK = new Runnable() {
            @Override
            public void run() {
            }
        };
        SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
        START_TIME = System.nanoTime();
        nextTaskId = new AtomicLong();
    }
    
    private static final class ScheduledFutureTask<V> extends PromiseTask<V> implements ScheduledFuture<V>
    {
        private static final AtomicIntegerFieldUpdater<ScheduledFutureTask> uncancellableUpdater;
        private final long id;
        private long deadlineNanos;
        private final long periodNanos;
        private volatile int uncancellable;
        
        ScheduledFutureTask(final SingleThreadEventExecutor executor, final Runnable runnable, final V result, final long nanoTime) {
            this(executor, Executors.callable(runnable, result), nanoTime);
        }
        
        ScheduledFutureTask(final SingleThreadEventExecutor executor, final Callable<V> callable, final long nanoTime, final long period) {
            super(executor, callable);
            this.id = SingleThreadEventExecutor.nextTaskId.getAndIncrement();
            if (period == 0L) {
                throw new IllegalArgumentException("period: 0 (expected: != 0)");
            }
            this.deadlineNanos = nanoTime;
            this.periodNanos = period;
        }
        
        ScheduledFutureTask(final SingleThreadEventExecutor executor, final Callable<V> callable, final long nanoTime) {
            super(executor, callable);
            this.id = SingleThreadEventExecutor.nextTaskId.getAndIncrement();
            this.deadlineNanos = nanoTime;
            this.periodNanos = 0L;
        }
        
        @Override
        protected SingleThreadEventExecutor executor() {
            return (SingleThreadEventExecutor)super.executor();
        }
        
        public long deadlineNanos() {
            return this.deadlineNanos;
        }
        
        public long delayNanos() {
            return Math.max(0L, this.deadlineNanos() - nanoTime());
        }
        
        public long delayNanos(final long currentTimeNanos) {
            return Math.max(0L, this.deadlineNanos() - (currentTimeNanos - SingleThreadEventExecutor.START_TIME));
        }
        
        @Override
        public long getDelay(final TimeUnit unit) {
            return unit.convert(this.delayNanos(), TimeUnit.NANOSECONDS);
        }
        
        @Override
        public int compareTo(final Delayed o) {
            if (this == o) {
                return 0;
            }
            final ScheduledFutureTask<?> that = (ScheduledFutureTask<?>)o;
            final long d = this.deadlineNanos() - that.deadlineNanos();
            if (d < 0L) {
                return -1;
            }
            if (d > 0L) {
                return 1;
            }
            if (this.id < that.id) {
                return -1;
            }
            if (this.id == that.id) {
                throw new Error();
            }
            return 1;
        }
        
        @Override
        public int hashCode() {
            return super.hashCode();
        }
        
        @Override
        public boolean equals(final Object obj) {
            return super.equals(obj);
        }
        
        @Override
        public void run() {
            assert this.executor().inEventLoop();
            try {
                if (this.periodNanos == 0L) {
                    if (this.setUncancellable()) {
                        final V result = this.task.call();
                        this.setSuccessInternal(result);
                    }
                }
                else if (!this.isDone()) {
                    this.task.call();
                    if (!this.executor().isShutdown()) {
                        final long p = this.periodNanos;
                        if (p > 0L) {
                            this.deadlineNanos += p;
                        }
                        else {
                            this.deadlineNanos = nanoTime() - p;
                        }
                        if (!this.isDone()) {
                            this.executor().delayedTaskQueue.add(this);
                        }
                    }
                }
            }
            catch (Throwable cause) {
                this.setFailureInternal(cause);
            }
        }
        
        @Override
        public boolean isCancelled() {
            return this.cause() instanceof CancellationException;
        }
        
        @Override
        public boolean cancel(final boolean mayInterruptIfRunning) {
            return !this.isDone() && this.setUncancellable() && this.tryFailureInternal(new CancellationException());
        }
        
        private boolean setUncancellable() {
            return ScheduledFutureTask.uncancellableUpdater.compareAndSet(this, 0, 1);
        }
        
        static {
            uncancellableUpdater = AtomicIntegerFieldUpdater.newUpdater(ScheduledFutureTask.class, "uncancellable");
        }
    }
    
    private final class PurgeTask implements Runnable
    {
        @Override
        public void run() {
            final Iterator<ScheduledFutureTask<?>> i = SingleThreadEventExecutor.this.delayedTaskQueue.iterator();
            while (i.hasNext()) {
                final ScheduledFutureTask<?> task = i.next();
                if (task.isCancelled()) {
                    i.remove();
                }
            }
        }
    }
}
