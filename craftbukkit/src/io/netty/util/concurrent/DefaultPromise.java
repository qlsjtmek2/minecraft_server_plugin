// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.TimeUnit;
import io.netty.util.internal.PlatformDependent;
import java.util.EventListener;
import io.netty.util.Signal;
import io.netty.util.internal.logging.InternalLogger;

public class DefaultPromise<V> extends AbstractFuture<V> implements Promise<V>
{
    private static final InternalLogger logger;
    private static final int MAX_LISTENER_STACK_DEPTH = 8;
    private static final ThreadLocal<Integer> LISTENER_STACK_DEPTH;
    private static final Signal SUCCESS;
    private final EventExecutor executor;
    private volatile Object result;
    private Object listeners;
    protected long state;
    
    public DefaultPromise(final EventExecutor executor) {
        if (executor == null) {
            throw new NullPointerException("executor");
        }
        this.executor = executor;
    }
    
    protected DefaultPromise() {
        this.executor = null;
    }
    
    protected EventExecutor executor() {
        return this.executor;
    }
    
    @Override
    public boolean isDone() {
        return this.result != null;
    }
    
    @Override
    public boolean isSuccess() {
        final Object result = this.result;
        return result != null && !(result instanceof CauseHolder);
    }
    
    @Override
    public Throwable cause() {
        final Object cause = this.result;
        if (cause instanceof CauseHolder) {
            return ((CauseHolder)cause).cause;
        }
        return null;
    }
    
    @Override
    public Promise<V> addListener(final GenericFutureListener<? extends Future<V>> listener) {
        if (listener == null) {
            throw new NullPointerException("listener");
        }
        if (this.isDone()) {
            notifyListener(this.executor(), this, listener);
            return this;
        }
        synchronized (this) {
            if (!this.isDone()) {
                if (this.listeners == null) {
                    this.listeners = listener;
                }
                else if (this.listeners instanceof DefaultPromiseListeners) {
                    ((DefaultPromiseListeners)this.listeners).add(listener);
                }
                else {
                    this.listeners = new DefaultPromiseListeners((GenericFutureListener<? extends Future<?>>)this.listeners, listener);
                }
                return this;
            }
        }
        notifyListener(this.executor(), this, listener);
        return this;
    }
    
    @Override
    public Promise<V> addListeners(final GenericFutureListener<? extends Future<V>>... listeners) {
        if (listeners == null) {
            throw new NullPointerException("listeners");
        }
        for (final GenericFutureListener<? extends Future<V>> l : listeners) {
            if (l == null) {
                break;
            }
            this.addListener(l);
        }
        return this;
    }
    
    @Override
    public Promise<V> removeListener(final GenericFutureListener<? extends Future<V>> listener) {
        if (listener == null) {
            throw new NullPointerException("listener");
        }
        if (this.isDone()) {
            return this;
        }
        synchronized (this) {
            if (!this.isDone()) {
                if (this.listeners instanceof DefaultPromiseListeners) {
                    ((DefaultPromiseListeners)this.listeners).remove(listener);
                }
                else if (this.listeners == listener) {
                    this.listeners = null;
                }
            }
        }
        return this;
    }
    
    @Override
    public Promise<V> removeListeners(final GenericFutureListener<? extends Future<V>>... listeners) {
        if (listeners == null) {
            throw new NullPointerException("listeners");
        }
        for (final GenericFutureListener<? extends Future<V>> l : listeners) {
            if (l == null) {
                break;
            }
            this.removeListener(l);
        }
        return this;
    }
    
    @Override
    public Promise<V> sync() throws InterruptedException {
        this.await();
        this.rethrowIfFailed();
        return this;
    }
    
    @Override
    public Promise<V> syncUninterruptibly() {
        this.awaitUninterruptibly();
        this.rethrowIfFailed();
        return this;
    }
    
    private void rethrowIfFailed() {
        final Throwable cause = this.cause();
        if (cause == null) {
            return;
        }
        PlatformDependent.throwException(cause);
    }
    
    @Override
    public Promise<V> await() throws InterruptedException {
        if (this.isDone()) {
            return this;
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        synchronized (this) {
            while (!this.isDone()) {
                this.checkDeadLock();
                this.incWaiters();
                try {
                    this.wait();
                }
                finally {
                    this.decWaiters();
                }
            }
        }
        return this;
    }
    
    @Override
    public boolean await(final long timeout, final TimeUnit unit) throws InterruptedException {
        return this.await0(unit.toNanos(timeout), true);
    }
    
    @Override
    public boolean await(final long timeoutMillis) throws InterruptedException {
        return this.await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), true);
    }
    
    @Override
    public Promise<V> awaitUninterruptibly() {
        if (this.isDone()) {
            return this;
        }
        boolean interrupted = false;
        synchronized (this) {
            while (!this.isDone()) {
                this.checkDeadLock();
                this.incWaiters();
                try {
                    this.wait();
                }
                catch (InterruptedException e) {
                    interrupted = true;
                }
                finally {
                    this.decWaiters();
                }
            }
        }
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
        return this;
    }
    
    @Override
    public boolean awaitUninterruptibly(final long timeout, final TimeUnit unit) {
        try {
            return this.await0(unit.toNanos(timeout), false);
        }
        catch (InterruptedException e) {
            throw new InternalError();
        }
    }
    
    @Override
    public boolean awaitUninterruptibly(final long timeoutMillis) {
        try {
            return this.await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), false);
        }
        catch (InterruptedException e) {
            throw new InternalError();
        }
    }
    
    private boolean await0(final long timeoutNanos, final boolean interruptable) throws InterruptedException {
        if (this.isDone()) {
            return true;
        }
        if (timeoutNanos <= 0L) {
            return this.isDone();
        }
        if (interruptable && Thread.interrupted()) {
            throw new InterruptedException();
        }
        final long startTime = (timeoutNanos <= 0L) ? 0L : System.nanoTime();
        long waitTime = timeoutNanos;
        boolean interrupted = false;
        try {
            synchronized (this) {
                if (this.isDone()) {
                    return true;
                }
                if (waitTime <= 0L) {
                    return this.isDone();
                }
                this.checkDeadLock();
                this.incWaiters();
                try {
                    while (true) {
                        try {
                            this.wait(waitTime / 1000000L, (int)(waitTime % 1000000L));
                        }
                        catch (InterruptedException e) {
                            if (interruptable) {
                                throw e;
                            }
                            interrupted = true;
                        }
                        if (this.isDone()) {
                            return true;
                        }
                        waitTime = timeoutNanos - (System.nanoTime() - startTime);
                        if (waitTime <= 0L) {
                            return this.isDone();
                        }
                    }
                }
                finally {
                    this.decWaiters();
                }
            }
        }
        finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    protected void checkDeadLock() {
        final EventExecutor e = this.executor();
        if (e != null && e.inEventLoop()) {
            throw new BlockingOperationException();
        }
    }
    
    @Override
    public Promise<V> setSuccess(final V result) {
        if (this.setSuccess0(result)) {
            this.notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already");
    }
    
    @Override
    public boolean trySuccess(final V result) {
        if (this.setSuccess0(result)) {
            this.notifyListeners();
            return true;
        }
        return false;
    }
    
    @Override
    public Promise<V> setFailure(final Throwable cause) {
        if (this.setFailure0(cause)) {
            this.notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already", cause);
    }
    
    @Override
    public boolean tryFailure(final Throwable cause) {
        if (this.setFailure0(cause)) {
            this.notifyListeners();
            return true;
        }
        return false;
    }
    
    private boolean setFailure0(final Throwable cause) {
        if (this.isDone()) {
            return false;
        }
        synchronized (this) {
            if (this.isDone()) {
                return false;
            }
            this.result = new CauseHolder(cause);
            if (this.hasWaiters()) {
                this.notifyAll();
            }
        }
        return true;
    }
    
    private boolean setSuccess0(final V result) {
        if (this.isDone()) {
            return false;
        }
        synchronized (this) {
            if (this.isDone()) {
                return false;
            }
            if (result == null) {
                this.result = DefaultPromise.SUCCESS;
            }
            else {
                this.result = result;
            }
            if (this.hasWaiters()) {
                this.notifyAll();
            }
        }
        return true;
    }
    
    @Override
    public V getNow() {
        final Object result = this.result;
        if (result instanceof CauseHolder || result == DefaultPromise.SUCCESS) {
            return null;
        }
        return (V)result;
    }
    
    private boolean hasWaiters() {
        return (this.state & 0xFFFFFF0000000000L) != 0x0L;
    }
    
    private void incWaiters() {
        final long newState = this.state + 1099511627776L;
        if ((newState & 0xFFFFFF0000000000L) == 0x0L) {
            throw new IllegalStateException("too many waiters");
        }
        this.state = newState;
    }
    
    private void decWaiters() {
        this.state -= 1099511627776L;
    }
    
    private void notifyListeners() {
        if (this.listeners == null) {
            return;
        }
        final EventExecutor executor = this.executor();
        if (executor.inEventLoop()) {
            if (this.listeners instanceof DefaultPromiseListeners) {
                notifyListeners0(this, (DefaultPromiseListeners)this.listeners);
            }
            else {
                notifyListener0(this, (GenericFutureListener)this.listeners);
            }
            this.listeners = null;
        }
        else {
            final Object listeners = this.listeners;
            this.listeners = null;
            try {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (listeners instanceof DefaultPromiseListeners) {
                            notifyListeners0(DefaultPromise.this, (DefaultPromiseListeners)listeners);
                        }
                        else {
                            notifyListener0(DefaultPromise.this, (GenericFutureListener)listeners);
                        }
                    }
                });
            }
            catch (Throwable t) {
                DefaultPromise.logger.error("Failed to notify listener(s). Event loop terminated?", t);
            }
        }
    }
    
    private static void notifyListeners0(final Future<?> future, final DefaultPromiseListeners listeners) {
        final GenericFutureListener<? extends Future<?>>[] a = listeners.listeners();
        for (int size = listeners.size(), i = 0; i < size; ++i) {
            notifyListener0(future, a[i]);
        }
    }
    
    protected static void notifyListener(final EventExecutor eventExecutor, final Future<?> future, final GenericFutureListener<? extends Future<?>> l) {
        if (eventExecutor.inEventLoop()) {
            final Integer stackDepth = DefaultPromise.LISTENER_STACK_DEPTH.get();
            if (stackDepth < 8) {
                DefaultPromise.LISTENER_STACK_DEPTH.set(stackDepth + 1);
                try {
                    notifyListener0(future, l);
                }
                finally {
                    DefaultPromise.LISTENER_STACK_DEPTH.set(stackDepth);
                }
                return;
            }
        }
        try {
            eventExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    DefaultPromise.notifyListener(eventExecutor, future, l);
                }
            });
        }
        catch (Throwable t) {
            DefaultPromise.logger.error("Failed to notify a listener. Event loop terminated?", t);
        }
    }
    
    private static void notifyListener0(final Future future, final GenericFutureListener l) {
        try {
            l.operationComplete(future);
        }
        catch (Throwable t) {
            if (DefaultPromise.logger.isWarnEnabled()) {
                DefaultPromise.logger.warn("An exception was thrown by " + GenericFutureListener.class.getSimpleName() + '.', t);
            }
        }
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(DefaultPromise.class);
        LISTENER_STACK_DEPTH = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return 0;
            }
        };
        SUCCESS = new Signal(DefaultPromise.class.getName() + ".SUCCESS");
    }
    
    private static final class CauseHolder
    {
        final Throwable cause;
        
        private CauseHolder(final Throwable cause) {
            this.cause = cause;
        }
    }
}
