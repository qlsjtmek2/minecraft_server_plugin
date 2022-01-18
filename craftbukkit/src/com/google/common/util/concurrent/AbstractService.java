// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import com.google.common.base.Preconditions;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;
import com.google.common.annotations.Beta;

@Beta
public abstract class AbstractService implements Service
{
    private final ReentrantLock lock;
    private final Transition startup;
    private final Transition shutdown;
    private State state;
    private boolean shutdownWhenStartupFinishes;
    
    public AbstractService() {
        this.lock = new ReentrantLock();
        this.startup = new Transition();
        this.shutdown = new Transition();
        this.state = State.NEW;
        this.shutdownWhenStartupFinishes = false;
    }
    
    protected abstract void doStart();
    
    protected abstract void doStop();
    
    public final ListenableFuture<State> start() {
        this.lock.lock();
        try {
            if (this.state == State.NEW) {
                this.state = State.STARTING;
                this.doStart();
            }
        }
        catch (Throwable startupFailure) {
            this.notifyFailed(startupFailure);
        }
        finally {
            this.lock.unlock();
        }
        return this.startup;
    }
    
    public final ListenableFuture<State> stop() {
        this.lock.lock();
        try {
            if (this.state == State.NEW) {
                this.state = State.TERMINATED;
                this.startup.set(State.TERMINATED);
                this.shutdown.set(State.TERMINATED);
            }
            else if (this.state == State.STARTING) {
                this.shutdownWhenStartupFinishes = true;
                this.startup.set(State.STOPPING);
            }
            else if (this.state == State.RUNNING) {
                this.state = State.STOPPING;
                this.doStop();
            }
        }
        catch (Throwable shutdownFailure) {
            this.notifyFailed(shutdownFailure);
        }
        finally {
            this.lock.unlock();
        }
        return this.shutdown;
    }
    
    public State startAndWait() {
        return Futures.getUnchecked(this.start());
    }
    
    public State stopAndWait() {
        return Futures.getUnchecked(this.stop());
    }
    
    protected final void notifyStarted() {
        this.lock.lock();
        try {
            if (this.state != State.STARTING) {
                final IllegalStateException failure = new IllegalStateException("Cannot notifyStarted() when the service is " + this.state);
                this.notifyFailed(failure);
                throw failure;
            }
            this.state = State.RUNNING;
            if (this.shutdownWhenStartupFinishes) {
                this.stop();
            }
            else {
                this.startup.set(State.RUNNING);
            }
        }
        finally {
            this.lock.unlock();
        }
    }
    
    protected final void notifyStopped() {
        this.lock.lock();
        try {
            if (this.state != State.STOPPING && this.state != State.RUNNING) {
                final IllegalStateException failure = new IllegalStateException("Cannot notifyStopped() when the service is " + this.state);
                this.notifyFailed(failure);
                throw failure;
            }
            this.state = State.TERMINATED;
            this.shutdown.set(State.TERMINATED);
        }
        finally {
            this.lock.unlock();
        }
    }
    
    protected final void notifyFailed(final Throwable cause) {
        Preconditions.checkNotNull(cause);
        this.lock.lock();
        try {
            if (this.state == State.STARTING) {
                this.startup.setException(cause);
                this.shutdown.setException(new Exception("Service failed to start.", cause));
            }
            else if (this.state == State.STOPPING) {
                this.shutdown.setException(cause);
            }
            this.state = State.FAILED;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public final boolean isRunning() {
        return this.state() == State.RUNNING;
    }
    
    public final State state() {
        this.lock.lock();
        try {
            if (this.shutdownWhenStartupFinishes && this.state == State.STARTING) {
                return State.STOPPING;
            }
            return this.state;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public String toString() {
        return this.getClass().getSimpleName() + " [" + this.state() + "]";
    }
    
    private class Transition extends AbstractFuture<State>
    {
        public State get(final long timeout, final TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
            try {
                return super.get(timeout, unit);
            }
            catch (TimeoutException e) {
                throw new TimeoutException(AbstractService.this.toString());
            }
        }
    }
}
