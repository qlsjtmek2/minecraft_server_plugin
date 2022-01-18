// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.locks.Condition;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import com.google.common.base.Throwables;
import java.util.concurrent.TimeUnit;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import com.google.common.annotations.Beta;

@Beta
public final class Monitor
{
    private final ReentrantLock lock;
    private final ArrayList<Guard> activeGuards;
    
    public Monitor() {
        this(false);
    }
    
    public Monitor(final boolean fair) {
        this.activeGuards = Lists.newArrayListWithCapacity(1);
        this.lock = new ReentrantLock(fair);
    }
    
    public void enter() {
        this.lock.lock();
    }
    
    public void enterInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }
    
    public boolean enter(final long time, final TimeUnit unit) {
        final ReentrantLock lock = this.lock;
        final long startNanos = System.nanoTime();
        long remainingNanos;
        final long timeoutNanos = remainingNanos = unit.toNanos(time);
        boolean interruptIgnored = false;
        try {
            return lock.tryLock(remainingNanos, TimeUnit.NANOSECONDS);
        }
        catch (InterruptedException ignored) {
            interruptIgnored = true;
            remainingNanos = timeoutNanos - (System.nanoTime() - startNanos);
            return lock.tryLock(remainingNanos, TimeUnit.NANOSECONDS);
        }
        finally {
            if (interruptIgnored) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public boolean enterInterruptibly(final long time, final TimeUnit unit) throws InterruptedException {
        return this.lock.tryLock(time, unit);
    }
    
    public boolean tryEnter() {
        return this.lock.tryLock();
    }
    
    public void enterWhen(final Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        final boolean reentrant = lock.isHeldByCurrentThread();
        lock.lockInterruptibly();
        try {
            this.waitInterruptibly(guard, reentrant);
        }
        catch (Throwable throwable) {
            lock.unlock();
            throw Throwables.propagate(throwable);
        }
    }
    
    public void enterWhenUninterruptibly(final Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        final boolean reentrant = lock.isHeldByCurrentThread();
        lock.lock();
        try {
            this.waitUninterruptibly(guard, reentrant);
        }
        catch (Throwable throwable) {
            lock.unlock();
            throw Throwables.propagate(throwable);
        }
    }
    
    public boolean enterWhen(final Guard guard, final long time, final TimeUnit unit) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        final boolean reentrant = lock.isHeldByCurrentThread();
        final long startNanos = System.nanoTime();
        if (!lock.tryLock(time, unit)) {
            return false;
        }
        boolean satisfied;
        try {
            final long remainingNanos = unit.toNanos(time) - (System.nanoTime() - startNanos);
            satisfied = this.waitInterruptibly(guard, remainingNanos, reentrant);
        }
        catch (Throwable throwable) {
            lock.unlock();
            throw Throwables.propagate(throwable);
        }
        if (satisfied) {
            return true;
        }
        lock.unlock();
        return false;
    }
    
    public boolean enterWhenUninterruptibly(final Guard guard, final long time, final TimeUnit unit) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        final boolean reentrant = lock.isHeldByCurrentThread();
        final long startNanos = System.nanoTime();
        long remainingNanos;
        final long timeoutNanos = remainingNanos = unit.toNanos(time);
        boolean interruptIgnored = false;
        try {
            while (true) {
                try {
                    if (lock.tryLock(remainingNanos, TimeUnit.NANOSECONDS)) {
                        break;
                    }
                    return false;
                }
                catch (InterruptedException ignored) {
                    interruptIgnored = true;
                }
                finally {
                    remainingNanos = timeoutNanos - (System.nanoTime() - startNanos);
                }
            }
            boolean satisfied;
            try {
                satisfied = this.waitUninterruptibly(guard, remainingNanos, reentrant);
            }
            catch (Throwable throwable) {
                lock.unlock();
                throw Throwables.propagate(throwable);
            }
            if (satisfied) {
                return true;
            }
            lock.unlock();
            return false;
        }
        finally {
            if (interruptIgnored) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public boolean enterIf(final Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        lock.lock();
        boolean satisfied;
        try {
            satisfied = guard.isSatisfied();
        }
        catch (Throwable throwable) {
            lock.unlock();
            throw Throwables.propagate(throwable);
        }
        if (satisfied) {
            return true;
        }
        lock.unlock();
        return false;
    }
    
    public boolean enterIfInterruptibly(final Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        boolean satisfied;
        try {
            satisfied = guard.isSatisfied();
        }
        catch (Throwable throwable) {
            lock.unlock();
            throw Throwables.propagate(throwable);
        }
        if (satisfied) {
            return true;
        }
        lock.unlock();
        return false;
    }
    
    public boolean enterIf(final Guard guard, final long time, final TimeUnit unit) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        if (!this.enter(time, unit)) {
            return false;
        }
        boolean satisfied;
        try {
            satisfied = guard.isSatisfied();
        }
        catch (Throwable throwable) {
            lock.unlock();
            throw Throwables.propagate(throwable);
        }
        if (satisfied) {
            return true;
        }
        lock.unlock();
        return false;
    }
    
    public boolean enterIfInterruptibly(final Guard guard, final long time, final TimeUnit unit) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        if (!lock.tryLock(time, unit)) {
            return false;
        }
        boolean satisfied;
        try {
            satisfied = guard.isSatisfied();
        }
        catch (Throwable throwable) {
            lock.unlock();
            throw Throwables.propagate(throwable);
        }
        if (satisfied) {
            return true;
        }
        lock.unlock();
        return false;
    }
    
    public boolean tryEnterIf(final Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        final ReentrantLock lock = this.lock;
        if (!lock.tryLock()) {
            return false;
        }
        boolean satisfied;
        try {
            satisfied = guard.isSatisfied();
        }
        catch (Throwable throwable) {
            lock.unlock();
            throw Throwables.propagate(throwable);
        }
        if (satisfied) {
            return true;
        }
        lock.unlock();
        return false;
    }
    
    @GuardedBy("lock")
    public void waitFor(final Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        if (!this.lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        }
        this.waitInterruptibly(guard, true);
    }
    
    @GuardedBy("lock")
    public void waitForUninterruptibly(final Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        if (!this.lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        }
        this.waitUninterruptibly(guard, true);
    }
    
    @GuardedBy("lock")
    public boolean waitFor(final Guard guard, final long time, final TimeUnit unit) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        if (!this.lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        }
        return this.waitInterruptibly(guard, unit.toNanos(time), true);
    }
    
    @GuardedBy("lock")
    public boolean waitForUninterruptibly(final Guard guard, final long time, final TimeUnit unit) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        if (!this.lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        }
        return this.waitUninterruptibly(guard, unit.toNanos(time), true);
    }
    
    @GuardedBy("lock")
    public void leave() {
        final ReentrantLock lock = this.lock;
        if (!lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        }
        try {
            this.signalConditionsOfSatisfiedGuards(null);
        }
        finally {
            lock.unlock();
        }
    }
    
    public void reevaluateGuards() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            this.signalConditionsOfSatisfiedGuards(null);
        }
        finally {
            lock.unlock();
        }
    }
    
    public boolean isFair() {
        return this.lock.isFair();
    }
    
    public boolean isOccupied() {
        return this.lock.isLocked();
    }
    
    public boolean isOccupiedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }
    
    public int getOccupiedDepth() {
        return this.lock.getHoldCount();
    }
    
    public int getQueueLength() {
        return this.lock.getQueueLength();
    }
    
    public boolean hasQueuedThreads() {
        return this.lock.hasQueuedThreads();
    }
    
    public boolean hasQueuedThread(final Thread thread) {
        return this.lock.hasQueuedThread(thread);
    }
    
    public boolean hasWaiters(final Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        this.lock.lock();
        try {
            return guard.waiterCount > 0;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public int getWaitQueueLength(final Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        this.lock.lock();
        try {
            return guard.waiterCount;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @GuardedBy("lock")
    private void signalConditionsOfSatisfiedGuards(@Nullable final Guard interruptedGuard) {
        final ArrayList<Guard> guards = this.activeGuards;
        final int guardCount = guards.size();
        try {
            for (int i = 0; i < guardCount; ++i) {
                final Guard guard = guards.get(i);
                if (guard != interruptedGuard || guard.waiterCount != 1) {
                    if (guard.isSatisfied()) {
                        guard.condition.signal();
                        return;
                    }
                }
            }
        }
        catch (Throwable throwable) {
            for (int j = 0; j < guardCount; ++j) {
                final Guard guard2 = guards.get(j);
                guard2.condition.signalAll();
            }
            throw Throwables.propagate(throwable);
        }
    }
    
    @GuardedBy("lock")
    private void incrementWaiters(final Guard guard) {
        final int waiters = guard.waiterCount++;
        if (waiters == 0) {
            this.activeGuards.add(guard);
        }
    }
    
    @GuardedBy("lock")
    private void decrementWaiters(final Guard guard) {
        final int waiterCount = guard.waiterCount - 1;
        guard.waiterCount = waiterCount;
        final int waiters = waiterCount;
        if (waiters == 0) {
            this.activeGuards.remove(guard);
        }
    }
    
    @GuardedBy("lock")
    private void waitInterruptibly(final Guard guard, final boolean signalBeforeWaiting) throws InterruptedException {
        if (!guard.isSatisfied()) {
            if (signalBeforeWaiting) {
                this.signalConditionsOfSatisfiedGuards(null);
            }
            this.incrementWaiters(guard);
            try {
                final Condition condition = guard.condition;
                do {
                    try {
                        condition.await();
                    }
                    catch (InterruptedException interrupt) {
                        try {
                            this.signalConditionsOfSatisfiedGuards(guard);
                        }
                        catch (Throwable throwable) {
                            Thread.currentThread().interrupt();
                            throw Throwables.propagate(throwable);
                        }
                        throw interrupt;
                    }
                } while (!guard.isSatisfied());
            }
            finally {
                this.decrementWaiters(guard);
            }
        }
    }
    
    @GuardedBy("lock")
    private void waitUninterruptibly(final Guard guard, final boolean signalBeforeWaiting) {
        if (!guard.isSatisfied()) {
            if (signalBeforeWaiting) {
                this.signalConditionsOfSatisfiedGuards(null);
            }
            this.incrementWaiters(guard);
            try {
                final Condition condition = guard.condition;
                do {
                    condition.awaitUninterruptibly();
                } while (!guard.isSatisfied());
            }
            finally {
                this.decrementWaiters(guard);
            }
        }
    }
    
    @GuardedBy("lock")
    private boolean waitInterruptibly(final Guard guard, long remainingNanos, final boolean signalBeforeWaiting) throws InterruptedException {
        if (!guard.isSatisfied()) {
            if (signalBeforeWaiting) {
                this.signalConditionsOfSatisfiedGuards(null);
            }
            this.incrementWaiters(guard);
            try {
                final Condition condition = guard.condition;
                while (remainingNanos > 0L) {
                    try {
                        remainingNanos = condition.awaitNanos(remainingNanos);
                    }
                    catch (InterruptedException interrupt) {
                        try {
                            this.signalConditionsOfSatisfiedGuards(guard);
                        }
                        catch (Throwable throwable) {
                            Thread.currentThread().interrupt();
                            throw Throwables.propagate(throwable);
                        }
                        throw interrupt;
                    }
                    if (guard.isSatisfied()) {
                        return true;
                    }
                }
                return false;
            }
            finally {
                this.decrementWaiters(guard);
            }
        }
        return true;
    }
    
    @GuardedBy("lock")
    private boolean waitUninterruptibly(final Guard guard, final long timeoutNanos, final boolean signalBeforeWaiting) {
        if (!guard.isSatisfied()) {
            final long startNanos = System.nanoTime();
            if (signalBeforeWaiting) {
                this.signalConditionsOfSatisfiedGuards(null);
            }
            boolean interruptIgnored = false;
            try {
                this.incrementWaiters(guard);
                try {
                    final Condition condition = guard.condition;
                    long remainingNanos = timeoutNanos;
                    while (remainingNanos > 0L) {
                        try {
                            remainingNanos = condition.awaitNanos(remainingNanos);
                        }
                        catch (InterruptedException ignored) {
                            try {
                                this.signalConditionsOfSatisfiedGuards(guard);
                            }
                            catch (Throwable throwable) {
                                Thread.currentThread().interrupt();
                                throw Throwables.propagate(throwable);
                            }
                            interruptIgnored = true;
                            remainingNanos = timeoutNanos - (System.nanoTime() - startNanos);
                        }
                        if (guard.isSatisfied()) {
                            return true;
                        }
                    }
                    return false;
                }
                finally {
                    this.decrementWaiters(guard);
                }
            }
            finally {
                if (interruptIgnored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return true;
    }
    
    @Beta
    public abstract static class Guard
    {
        final Monitor monitor;
        final Condition condition;
        @GuardedBy("monitor.lock")
        int waiterCount;
        
        protected Guard(final Monitor monitor) {
            this.waiterCount = 0;
            this.monitor = Preconditions.checkNotNull(monitor, (Object)"monitor");
            this.condition = monitor.lock.newCondition();
        }
        
        public abstract boolean isSatisfied();
        
        public final boolean equals(final Object other) {
            return this == other;
        }
        
        public final int hashCode() {
            return super.hashCode();
        }
    }
}
