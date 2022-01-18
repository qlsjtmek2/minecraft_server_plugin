// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import java.util.Iterator;
import java.util.Collection;

public abstract class FilteredMessageBuf implements MessageBuf<Object>
{
    protected final MessageBuf<Object> buf;
    
    protected FilteredMessageBuf(final MessageBuf<?> buf) {
        if (buf == null) {
            throw new NullPointerException("buf");
        }
        this.buf = (MessageBuf<Object>)buf;
    }
    
    protected abstract Object filter(final Object p0);
    
    @Override
    public int drainTo(final Collection<? super Object> c) {
        return this.buf.drainTo(c);
    }
    
    @Override
    public int drainTo(final Collection<? super Object> c, final int maxElements) {
        return this.buf.drainTo(c, maxElements);
    }
    
    @Override
    public BufType type() {
        return this.buf.type();
    }
    
    @Override
    public int maxCapacity() {
        return this.buf.maxCapacity();
    }
    
    @Override
    public boolean isReadable() {
        return this.buf.isReadable();
    }
    
    @Override
    public boolean isReadable(final int size) {
        return this.buf.isReadable(size);
    }
    
    @Override
    public boolean isWritable() {
        return this.buf.isWritable();
    }
    
    @Override
    public boolean isWritable(final int size) {
        return this.buf.isWritable(size);
    }
    
    @Override
    public boolean add(Object e) {
        if (e == null) {
            throw new NullPointerException("e");
        }
        e = this.filter(e);
        this.ensureNonNull(e);
        return this.buf.add(e);
    }
    
    @Override
    public boolean offer(Object e) {
        if (e == null) {
            throw new NullPointerException("e");
        }
        e = this.filter(e);
        this.ensureNonNull(e);
        return this.buf.offer(e);
    }
    
    private void ensureNonNull(final Object e) {
        if (e == null) {
            throw new IllegalStateException(this.getClass().getSimpleName() + ".filter() returned null");
        }
    }
    
    @Override
    public Object remove() {
        return this.buf.remove();
    }
    
    @Override
    public Object poll() {
        return this.buf.poll();
    }
    
    @Override
    public Object element() {
        return this.buf.element();
    }
    
    @Override
    public Object peek() {
        return this.buf.peek();
    }
    
    @Override
    public int size() {
        return this.buf.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.buf.isEmpty();
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.buf.contains(o);
    }
    
    @Override
    public Iterator<Object> iterator() {
        return this.buf.iterator();
    }
    
    @Override
    public Object[] toArray() {
        return this.buf.toArray();
    }
    
    @Override
    public <T> T[] toArray(final T[] a) {
        return this.buf.toArray(a);
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.buf.remove(o);
    }
    
    @Override
    public boolean containsAll(final Collection<?> c) {
        return this.buf.containsAll(c);
    }
    
    @Override
    public boolean addAll(final Collection<?> c) {
        final int i = 0;
        boolean added = false;
        for (Object e : c) {
            if (e == null) {
                throw new NullPointerException("c[" + i + ']');
            }
            e = this.filter(e);
            this.ensureNonNull(e);
            added |= this.buf.add(e);
        }
        return added;
    }
    
    @Override
    public boolean removeAll(final Collection<?> c) {
        return this.buf.removeAll(c);
    }
    
    @Override
    public boolean retainAll(final Collection<?> c) {
        return this.buf.retainAll(c);
    }
    
    @Override
    public void clear() {
        this.buf.clear();
    }
    
    @Override
    public boolean unfoldAndAdd(final Object o) {
        return this.buf.unfoldAndAdd(o);
    }
    
    @Override
    public int refCnt() {
        return this.buf.refCnt();
    }
    
    @Override
    public MessageBuf<Object> retain() {
        this.buf.retain();
        return this;
    }
    
    @Override
    public MessageBuf<Object> retain(final int increment) {
        this.buf.retain(increment);
        return this;
    }
    
    @Override
    public boolean release() {
        return this.buf.release();
    }
    
    @Override
    public boolean release(final int decrement) {
        return this.buf.release(decrement);
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '(' + this.buf + ')';
    }
}
