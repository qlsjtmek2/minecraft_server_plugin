// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

final class QueueBackedMessageBuf<T> extends AbstractMessageBuf<T>
{
    private Queue<T> queue;
    
    QueueBackedMessageBuf(final Queue<T> queue) {
        super(Integer.MAX_VALUE);
        if (queue == null) {
            throw new NullPointerException("queue");
        }
        this.queue = queue;
    }
    
    @Override
    public boolean offer(final T e) {
        if (e == null) {
            throw new NullPointerException("e");
        }
        this.ensureAccessible();
        return this.isWritable() && this.queue.offer(e);
    }
    
    @Override
    public T poll() {
        this.ensureAccessible();
        return this.queue.poll();
    }
    
    @Override
    public T peek() {
        this.ensureAccessible();
        return this.queue.peek();
    }
    
    @Override
    public int size() {
        return this.queue.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
    
    @Override
    public boolean contains(final Object o) {
        this.ensureAccessible();
        return this.queue.contains(o);
    }
    
    @Override
    public Iterator<T> iterator() {
        this.ensureAccessible();
        return this.queue.iterator();
    }
    
    @Override
    public Object[] toArray() {
        this.ensureAccessible();
        return this.queue.toArray();
    }
    
    @Override
    public <E> E[] toArray(final E[] a) {
        this.ensureAccessible();
        return this.queue.toArray(a);
    }
    
    @Override
    public boolean remove(final Object o) {
        this.ensureAccessible();
        return this.queue.remove(o);
    }
    
    @Override
    public boolean containsAll(final Collection<?> c) {
        this.ensureAccessible();
        return this.queue.containsAll(c);
    }
    
    @Override
    public boolean addAll(final Collection<? extends T> c) {
        this.ensureAccessible();
        return this.isWritable(c.size()) && this.queue.addAll((Collection<?>)c);
    }
    
    @Override
    public boolean removeAll(final Collection<?> c) {
        this.ensureAccessible();
        return this.queue.removeAll(c);
    }
    
    @Override
    public boolean retainAll(final Collection<?> c) {
        this.ensureAccessible();
        return this.queue.retainAll(c);
    }
    
    @Override
    public void clear() {
        this.ensureAccessible();
        this.queue.clear();
    }
    
    @Override
    protected void deallocate() {
        for (final T e : this.queue) {
            BufUtil.release(e);
        }
        this.queue = null;
    }
}
