// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Collections;
import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import io.netty.util.internal.logging.InternalLogger;

public class HashedWheelTimer implements Timer
{
    static final InternalLogger logger;
    private static final ResourceLeakDetector<HashedWheelTimer> leakDetector;
    private final ResourceLeak leak;
    private final Worker worker;
    final Thread workerThread;
    public static final int WORKER_STATE_INIT = 0;
    public static final int WORKER_STATE_STARTED = 1;
    public static final int WORKER_STATE_SHUTDOWN = 2;
    final AtomicInteger workerState;
    final long tickDuration;
    final Set<HashedWheelTimeout>[] wheel;
    final int mask;
    final ReadWriteLock lock;
    volatile int wheelCursor;
    
    public HashedWheelTimer() {
        this(Executors.defaultThreadFactory());
    }
    
    public HashedWheelTimer(final long tickDuration, final TimeUnit unit) {
        this(Executors.defaultThreadFactory(), tickDuration, unit);
    }
    
    public HashedWheelTimer(final long tickDuration, final TimeUnit unit, final int ticksPerWheel) {
        this(Executors.defaultThreadFactory(), tickDuration, unit, ticksPerWheel);
    }
    
    public HashedWheelTimer(final ThreadFactory threadFactory) {
        this(threadFactory, 100L, TimeUnit.MILLISECONDS);
    }
    
    public HashedWheelTimer(final ThreadFactory threadFactory, final long tickDuration, final TimeUnit unit) {
        this(threadFactory, tickDuration, unit, 512);
    }
    
    public HashedWheelTimer(final ThreadFactory threadFactory, long tickDuration, final TimeUnit unit, final int ticksPerWheel) {
        this.leak = HashedWheelTimer.leakDetector.open(this);
        this.worker = new Worker();
        this.workerState = new AtomicInteger();
        this.lock = new ReentrantReadWriteLock();
        if (threadFactory == null) {
            throw new NullPointerException("threadFactory");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (tickDuration <= 0L) {
            throw new IllegalArgumentException("tickDuration must be greater than 0: " + tickDuration);
        }
        if (ticksPerWheel <= 0) {
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
        }
        this.wheel = createWheel(ticksPerWheel);
        this.mask = this.wheel.length - 1;
        tickDuration = (this.tickDuration = unit.toMillis(tickDuration));
        if (tickDuration == Long.MAX_VALUE || tickDuration >= Long.MAX_VALUE / this.wheel.length) {
            throw new IllegalArgumentException("tickDuration is too long: " + tickDuration + ' ' + unit);
        }
        this.workerThread = threadFactory.newThread(this.worker);
    }
    
    private static Set<HashedWheelTimeout>[] createWheel(int ticksPerWheel) {
        if (ticksPerWheel <= 0) {
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
        }
        if (ticksPerWheel > 1073741824) {
            throw new IllegalArgumentException("ticksPerWheel may not be greater than 2^30: " + ticksPerWheel);
        }
        ticksPerWheel = normalizeTicksPerWheel(ticksPerWheel);
        final Set<HashedWheelTimeout>[] wheel = (Set<HashedWheelTimeout>[])new Set[ticksPerWheel];
        for (int i = 0; i < wheel.length; ++i) {
            wheel[i] = Collections.newSetFromMap((Map<HashedWheelTimeout, Boolean>)PlatformDependent.newConcurrentHashMap());
        }
        return wheel;
    }
    
    private static int normalizeTicksPerWheel(final int ticksPerWheel) {
        int normalizedTicksPerWheel;
        for (normalizedTicksPerWheel = 1; normalizedTicksPerWheel < ticksPerWheel; normalizedTicksPerWheel <<= 1) {}
        return normalizedTicksPerWheel;
    }
    
    public void start() {
        switch (this.workerState.get()) {
            case 0: {
                if (this.workerState.compareAndSet(0, 1)) {
                    this.workerThread.start();
                    break;
                }
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                throw new IllegalStateException("cannot be started once stopped");
            }
            default: {
                throw new Error("Invalid WorkerState");
            }
        }
    }
    
    @Override
    public Set<Timeout> stop() {
        if (Thread.currentThread() == this.workerThread) {
            throw new IllegalStateException(HashedWheelTimer.class.getSimpleName() + ".stop() cannot be called from " + TimerTask.class.getSimpleName());
        }
        if (!this.workerState.compareAndSet(1, 2)) {
            this.workerState.set(2);
            return Collections.emptySet();
        }
        boolean interrupted = false;
        while (this.workerThread.isAlive()) {
            this.workerThread.interrupt();
            try {
                this.workerThread.join(100L);
            }
            catch (InterruptedException e) {
                interrupted = true;
            }
        }
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
        this.leak.close();
        final Set<Timeout> unprocessedTimeouts = new HashSet<Timeout>();
        for (final Set<HashedWheelTimeout> bucket : this.wheel) {
            unprocessedTimeouts.addAll(bucket);
            bucket.clear();
        }
        return Collections.unmodifiableSet((Set<? extends Timeout>)unprocessedTimeouts);
    }
    
    @Override
    public Timeout newTimeout(final TimerTask task, long delay, final TimeUnit unit) {
        final long currentTime = System.currentTimeMillis();
        if (task == null) {
            throw new NullPointerException("task");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        this.start();
        delay = unit.toMillis(delay);
        final HashedWheelTimeout timeout = new HashedWheelTimeout(task, currentTime + delay);
        this.scheduleTimeout(timeout, delay);
        return timeout;
    }
    
    void scheduleTimeout(final HashedWheelTimeout timeout, final long delay) {
        long relativeIndex = (delay + this.tickDuration - 1L) / this.tickDuration;
        if (relativeIndex < 0L) {
            relativeIndex = delay / this.tickDuration;
        }
        if (relativeIndex == 0L) {
            relativeIndex = 1L;
        }
        if ((relativeIndex & this.mask) == 0x0L) {
            --relativeIndex;
        }
        final long remainingRounds = relativeIndex / this.wheel.length;
        this.lock.readLock().lock();
        try {
            if (this.workerState.get() == 2) {
                throw new IllegalStateException("Cannot enqueue after shutdown");
            }
            final int stopIndex = (int)(this.wheelCursor + relativeIndex & this.mask);
            timeout.stopIndex = stopIndex;
            timeout.remainingRounds = remainingRounds;
            this.wheel[stopIndex].add(timeout);
        }
        finally {
            this.lock.readLock().unlock();
        }
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(HashedWheelTimer.class);
        leakDetector = new ResourceLeakDetector<HashedWheelTimer>(HashedWheelTimer.class, 1, Runtime.getRuntime().availableProcessors() * 4);
    }
    
    private final class Worker implements Runnable
    {
        private long startTime;
        private long tick;
        
        @Override
        public void run() {
            final List<HashedWheelTimeout> expiredTimeouts = new ArrayList<HashedWheelTimeout>();
            this.startTime = System.currentTimeMillis();
            this.tick = 1L;
            while (HashedWheelTimer.this.workerState.get() == 1) {
                final long deadline = this.waitForNextTick();
                if (deadline > 0L) {
                    this.fetchExpiredTimeouts(expiredTimeouts, deadline);
                    this.notifyExpiredTimeouts(expiredTimeouts);
                }
            }
        }
        
        private void fetchExpiredTimeouts(final List<HashedWheelTimeout> expiredTimeouts, final long deadline) {
            HashedWheelTimer.this.lock.writeLock().lock();
            try {
                final HashedWheelTimer this$0 = HashedWheelTimer.this;
                final int wheelCursor = HashedWheelTimer.this.wheelCursor + 1 & HashedWheelTimer.this.mask;
                this$0.wheelCursor = wheelCursor;
                final int newWheelCursor = wheelCursor;
                this.fetchExpiredTimeouts(expiredTimeouts, HashedWheelTimer.this.wheel[newWheelCursor].iterator(), deadline);
            }
            finally {
                HashedWheelTimer.this.lock.writeLock().unlock();
            }
        }
        
        private void fetchExpiredTimeouts(final List<HashedWheelTimeout> expiredTimeouts, final Iterator<HashedWheelTimeout> i, final long deadline) {
            List<HashedWheelTimeout> slipped = null;
            while (i.hasNext()) {
                final HashedWheelTimeout timeout = i.next();
                if (timeout.remainingRounds <= 0L) {
                    i.remove();
                    if (timeout.deadline <= deadline) {
                        expiredTimeouts.add(timeout);
                    }
                    else {
                        if (slipped == null) {
                            slipped = new ArrayList<HashedWheelTimeout>();
                        }
                        slipped.add(timeout);
                    }
                }
                else {
                    final HashedWheelTimeout hashedWheelTimeout = timeout;
                    --hashedWheelTimeout.remainingRounds;
                }
            }
            if (slipped != null) {
                for (final HashedWheelTimeout timeout2 : slipped) {
                    HashedWheelTimer.this.scheduleTimeout(timeout2, timeout2.deadline - deadline);
                }
            }
        }
        
        private void notifyExpiredTimeouts(final List<HashedWheelTimeout> expiredTimeouts) {
            for (int i = expiredTimeouts.size() - 1; i >= 0; --i) {
                expiredTimeouts.get(i).expire();
            }
            expiredTimeouts.clear();
        }
        
        private long waitForNextTick() {
            final long deadline = this.startTime + HashedWheelTimer.this.tickDuration * this.tick;
            long currentTime;
            while (true) {
                currentTime = System.currentTimeMillis();
                long sleepTimeMs = (deadline - currentTime + 999999L) / 1000000L;
                if (sleepTimeMs <= 0L) {
                    break;
                }
                if (PlatformDependent.isWindows()) {
                    sleepTimeMs = sleepTimeMs / 10L * 10L;
                }
                try {
                    Thread.sleep(sleepTimeMs);
                }
                catch (InterruptedException e) {
                    if (HashedWheelTimer.this.workerState.get() == 2) {
                        return Long.MIN_VALUE;
                    }
                    continue;
                }
            }
            ++this.tick;
            if (currentTime == Long.MIN_VALUE) {
                return -9223372036854775807L;
            }
            return currentTime;
        }
    }
    
    private final class HashedWheelTimeout implements Timeout
    {
        private static final int ST_INIT = 0;
        private static final int ST_CANCELLED = 1;
        private static final int ST_EXPIRED = 2;
        private final TimerTask task;
        final long deadline;
        volatile int stopIndex;
        volatile long remainingRounds;
        private final AtomicInteger state;
        
        HashedWheelTimeout(final TimerTask task, final long deadline) {
            this.state = new AtomicInteger(0);
            this.task = task;
            this.deadline = deadline;
        }
        
        @Override
        public Timer timer() {
            return HashedWheelTimer.this;
        }
        
        @Override
        public TimerTask task() {
            return this.task;
        }
        
        @Override
        public boolean cancel() {
            if (!this.state.compareAndSet(0, 1)) {
                return false;
            }
            HashedWheelTimer.this.wheel[this.stopIndex].remove(this);
            return true;
        }
        
        @Override
        public boolean isCancelled() {
            return this.state.get() == 1;
        }
        
        @Override
        public boolean isExpired() {
            return this.state.get() != 0;
        }
        
        public void expire() {
            if (!this.state.compareAndSet(0, 2)) {
                return;
            }
            try {
                this.task.run(this);
            }
            catch (Throwable t) {
                if (HashedWheelTimer.logger.isWarnEnabled()) {
                    HashedWheelTimer.logger.warn("An exception was thrown by " + TimerTask.class.getSimpleName() + '.', t);
                }
            }
        }
        
        @Override
        public String toString() {
            final long currentTime = System.currentTimeMillis();
            final long remaining = this.deadline - currentTime;
            final StringBuilder buf = new StringBuilder(192);
            buf.append(this.getClass().getSimpleName());
            buf.append('(');
            buf.append("deadline: ");
            if (remaining > 0L) {
                buf.append(remaining);
                buf.append(" ms later, ");
            }
            else if (remaining < 0L) {
                buf.append(-remaining);
                buf.append(" ms ago, ");
            }
            else {
                buf.append("now, ");
            }
            if (this.isCancelled()) {
                buf.append(", cancelled");
            }
            return buf.append(')').toString();
        }
    }
}
