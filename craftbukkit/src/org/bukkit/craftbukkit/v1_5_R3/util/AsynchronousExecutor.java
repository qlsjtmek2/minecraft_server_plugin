// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.Validate;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public final class AsynchronousExecutor<P, T, C, E extends Throwable>
{
    static final AtomicIntegerFieldUpdater STATE_FIELD;
    final CallBackProvider<P, T, C, E> provider;
    final Queue<Task> finished;
    final Map<P, Task> tasks;
    final ThreadPoolExecutor pool;
    
    private static boolean set(final Task $this, final int expected, final int value) {
        return AsynchronousExecutor.STATE_FIELD.compareAndSet($this, expected, value);
    }
    
    public AsynchronousExecutor(final CallBackProvider<P, T, C, E> provider, final int coreSize) {
        this.finished = new ConcurrentLinkedQueue<Task>();
        this.tasks = new HashMap<P, Task>();
        Validate.notNull(provider, "Provider cannot be null");
        this.provider = provider;
        this.pool = new ThreadPoolExecutor(coreSize, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), provider);
    }
    
    public void add(final P parameter, final C callback) {
        Task task = this.tasks.get(parameter);
        if (task == null) {
            this.tasks.put(parameter, task = new Task(parameter));
            this.pool.execute(task);
        }
        task.callbacks.add(callback);
    }
    
    public T get(final P parameter) throws E, IllegalStateException, Throwable {
        final Task task = this.tasks.get(parameter);
        if (task == null) {
            throw new IllegalStateException("Unknown " + parameter);
        }
        return task.get();
    }
    
    public T getSkipQueue(final P parameter) throws E, Throwable {
        return skipQueue(this.provider, parameter);
    }
    
    public T getSkipQueue(final P parameter, final C callback) throws E, Throwable {
        final T object = skipQueue(this.provider, parameter);
        this.provider.callStage3(parameter, object, callback);
        return object;
    }
    
    public T getSkipQueue(final P parameter, final C... callbacks) throws E, Throwable {
        final CallBackProvider<P, T, C, E> provider = this.provider;
        final T object = skipQueue(provider, parameter);
        for (final C callback : callbacks) {
            provider.callStage3(parameter, object, callback);
        }
        return object;
    }
    
    public T getSkipQueue(final P parameter, final Iterable<C> callbacks) throws E, Throwable {
        final CallBackProvider<P, T, C, E> provider = this.provider;
        final T object = skipQueue(provider, parameter);
        for (final C callback : callbacks) {
            provider.callStage3(parameter, object, callback);
        }
        return object;
    }
    
    private static <T, P, E extends Throwable> T skipQueue(final CallBackProvider<P, T, ?, E> provider, final P parameter) throws E, Throwable {
        final T object = provider.callStage1(parameter);
        provider.callStage2(parameter, object);
        return object;
    }
    
    public void finishActive() throws E, Throwable {
        final Queue<Task> finished = this.finished;
        while (!finished.isEmpty()) {
            finished.poll().finish();
        }
    }
    
    public void setActiveThreads(final int coreSize) {
        this.pool.setCorePoolSize(coreSize);
    }
    
    static {
        STATE_FIELD = AtomicIntegerFieldUpdater.newUpdater(Task.class, "state");
    }
    
    class Task implements Runnable
    {
        static final int PENDING = 0;
        static final int STAGE_1_ASYNC = 1;
        static final int STAGE_1_SYNC = 2;
        static final int STAGE_1_COMPLETE = 3;
        static final int FINISHED = 4;
        volatile int state;
        final P parameter;
        T object;
        final List<C> callbacks;
        E t;
        
        Task(final P parameter) {
            this.state = 0;
            this.callbacks = new LinkedList<C>();
            this.t = null;
            this.parameter = parameter;
        }
        
        public void run() {
            if (this.initAsync()) {
                AsynchronousExecutor.this.finished.add(this);
            }
        }
        
        boolean initAsync() {
            if (set(this, 0, 1)) {
                boolean ret = true;
                try {
                    this.init();
                }
                finally {
                    if (!set(this, 1, 3)) {
                        synchronized (this) {
                            if (this.state != 2) {
                                this.notifyAll();
                            }
                            this.state = 3;
                        }
                        ret = false;
                    }
                }
                return ret;
            }
            return false;
        }
        
        void initSync() {
            if (set(this, 0, 3)) {
                this.init();
            }
            else if (set(this, 1, 2)) {
                synchronized (this) {
                    if (set(this, 2, 0)) {
                        while (this.state != 3) {
                            try {
                                this.wait();
                                continue;
                            }
                            catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                throw new RuntimeException("Unable to handle interruption on " + this.parameter, e);
                            }
                            break;
                        }
                    }
                }
            }
        }
        
        void init() {
            try {
                this.object = AsynchronousExecutor.this.provider.callStage1(this.parameter);
            }
            catch (Throwable t) {
                this.t = (E)t;
            }
        }
        
        T get() throws E, Throwable {
            this.initSync();
            this.finish();
            return this.object;
        }
        
        void finish() throws E, Throwable {
            switch (this.state) {
                default: {
                    throw new IllegalStateException("Attempting to finish unprepared(" + this.state + ") task(" + this.parameter + ")");
                }
                case 3: {
                    try {
                        if (this.t != null) {
                            throw this.t;
                        }
                        final CallBackProvider<P, T, C, E> provider = AsynchronousExecutor.this.provider;
                        final P parameter = this.parameter;
                        final T object = this.object;
                        provider.callStage2(parameter, object);
                        for (final C callback : this.callbacks) {
                            provider.callStage3(parameter, object, callback);
                        }
                    }
                    finally {
                        AsynchronousExecutor.this.tasks.remove(this.parameter);
                        this.state = 4;
                    }
                }
                case 4: {}
            }
        }
    }
    
    public interface CallBackProvider<P, T, C, E extends Throwable> extends ThreadFactory
    {
        T callStage1(final P p0) throws E, Throwable;
        
        void callStage2(final P p0, final T p1) throws E, Throwable;
        
        void callStage3(final P p0, final T p1, final C p2) throws E, Throwable;
    }
}
