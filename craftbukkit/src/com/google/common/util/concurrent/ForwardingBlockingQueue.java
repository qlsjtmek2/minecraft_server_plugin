// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import com.google.common.collect.ForwardingQueue;

public abstract class ForwardingBlockingQueue<E> extends ForwardingQueue<E> implements BlockingQueue<E>
{
    protected abstract BlockingQueue<E> delegate();
    
    public int drainTo(final Collection<? super E> c, final int maxElements) {
        return this.delegate().drainTo(c, maxElements);
    }
    
    public int drainTo(final Collection<? super E> c) {
        return this.delegate().drainTo(c);
    }
    
    public boolean offer(final E e, final long timeout, final TimeUnit unit) throws InterruptedException {
        return this.delegate().offer(e, timeout, unit);
    }
    
    public E poll(final long timeout, final TimeUnit unit) throws InterruptedException {
        return this.delegate().poll(timeout, unit);
    }
    
    public void put(final E e) throws InterruptedException {
        this.delegate().put(e);
    }
    
    public int remainingCapacity() {
        return this.delegate().remainingCapacity();
    }
    
    public E take() throws InterruptedException {
        return this.delegate().take();
    }
}
