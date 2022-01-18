// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.concurrent.CancellationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.ArrayList;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.concurrent.Callable;

abstract class AbstractListeningExecutorService implements ListeningExecutorService
{
    public ListenableFuture<?> submit(final Runnable task) {
        final ListenableFutureTask<Void> ftask = ListenableFutureTask.create(task, (Void)null);
        this.execute(ftask);
        return ftask;
    }
    
    public <T> ListenableFuture<T> submit(final Runnable task, final T result) {
        final ListenableFutureTask<T> ftask = ListenableFutureTask.create(task, result);
        this.execute(ftask);
        return ftask;
    }
    
    public <T> ListenableFuture<T> submit(final Callable<T> task) {
        final ListenableFutureTask<T> ftask = ListenableFutureTask.create(task);
        this.execute(ftask);
        return ftask;
    }
    
    private <T> T doInvokeAny(final Collection<? extends Callable<T>> tasks, final boolean timed, long nanos) throws InterruptedException, ExecutionException, TimeoutException {
        int ntasks = tasks.size();
        Preconditions.checkArgument(ntasks > 0);
        final List<Future<T>> futures = new ArrayList<Future<T>>(ntasks);
        final ExecutorCompletionService<T> ecs = new ExecutorCompletionService<T>(this);
        try {
            ExecutionException ee = null;
            long lastTime = timed ? System.nanoTime() : 0L;
            final Iterator<? extends Callable<T>> it = tasks.iterator();
            futures.add(ecs.submit((Callable<T>)it.next()));
            --ntasks;
            int active = 1;
            while (true) {
                Future<T> f = ecs.poll();
                if (f == null) {
                    if (ntasks > 0) {
                        --ntasks;
                        futures.add(ecs.submit((Callable<T>)it.next()));
                        ++active;
                    }
                    else {
                        if (active == 0) {
                            if (ee == null) {
                                ee = new ExecutionException((Throwable)null);
                            }
                            throw ee;
                        }
                        if (timed) {
                            f = ecs.poll(nanos, TimeUnit.NANOSECONDS);
                            if (f == null) {
                                throw new TimeoutException();
                            }
                            final long now = System.nanoTime();
                            nanos -= now - lastTime;
                            lastTime = now;
                        }
                        else {
                            f = ecs.take();
                        }
                    }
                }
                if (f != null) {
                    --active;
                    try {
                        return f.get();
                    }
                    catch (ExecutionException eex) {
                        ee = eex;
                    }
                    catch (RuntimeException rex) {
                        ee = new ExecutionException(rex);
                    }
                }
            }
        }
        finally {
            for (final Future<T> f2 : futures) {
                f2.cancel(true);
            }
        }
    }
    
    public <T> T invokeAny(final Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        try {
            return this.doInvokeAny(tasks, false, 0L);
        }
        catch (TimeoutException cannotHappen) {
            throw new AssertionError();
        }
    }
    
    public <T> T invokeAny(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.doInvokeAny(tasks, true, unit.toNanos(timeout));
    }
    
    public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks) throws InterruptedException {
        if (tasks == null) {
            throw new NullPointerException();
        }
        final List<Future<T>> futures = new ArrayList<Future<T>>(tasks.size());
        boolean done = false;
        try {
            for (final Callable<T> t : tasks) {
                final ListenableFutureTask<T> f = ListenableFutureTask.create(t);
                futures.add(f);
                this.execute(f);
            }
            for (final Future<T> f2 : futures) {
                if (!f2.isDone()) {
                    try {
                        f2.get();
                    }
                    catch (CancellationException ignore) {}
                    catch (ExecutionException ex) {}
                }
            }
            done = true;
            return futures;
        }
        finally {
            if (!done) {
                for (final Future<T> f3 : futures) {
                    f3.cancel(true);
                }
            }
        }
    }
    
    public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit) throws InterruptedException {
        if (tasks == null || unit == null) {
            throw new NullPointerException();
        }
        long nanos = unit.toNanos(timeout);
        final List<Future<T>> futures = new ArrayList<Future<T>>(tasks.size());
        boolean done = false;
        try {
            for (final Callable<T> t : tasks) {
                futures.add(ListenableFutureTask.create(t));
            }
            long lastTime = System.nanoTime();
            final Iterator<Future<T>> it = futures.iterator();
            while (it.hasNext()) {
                this.execute((Runnable)it.next());
                final long now = System.nanoTime();
                nanos -= now - lastTime;
                lastTime = now;
                if (nanos <= 0L) {
                    return futures;
                }
            }
            for (final Future<T> f : futures) {
                if (!f.isDone()) {
                    if (nanos <= 0L) {
                        return futures;
                    }
                    try {
                        f.get(nanos, TimeUnit.NANOSECONDS);
                    }
                    catch (CancellationException ignore) {}
                    catch (ExecutionException ignore2) {}
                    catch (TimeoutException toe) {
                        return futures;
                    }
                    final long now2 = System.nanoTime();
                    nanos -= now2 - lastTime;
                    lastTime = now2;
                }
            }
            done = true;
            return futures;
        }
        finally {
            if (!done) {
                for (final Future<T> f2 : futures) {
                    f2.cancel(true);
                }
            }
        }
    }
}
