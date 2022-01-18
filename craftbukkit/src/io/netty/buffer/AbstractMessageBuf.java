// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import java.util.Collection;
import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.AbstractQueue;

public abstract class AbstractMessageBuf<T> extends AbstractQueue<T> implements MessageBuf<T>
{
    private static final AtomicIntegerFieldUpdater<AbstractMessageBuf> refCntUpdater;
    private static final long REFCNT_FIELD_OFFSET;
    private final int maxCapacity;
    private volatile int refCnt;
    
    protected AbstractMessageBuf(final int maxCapacity) {
        this.refCnt = 1;
        if (maxCapacity < 0) {
            throw new IllegalArgumentException("maxCapacity: " + maxCapacity + " (expected: >= 0)");
        }
        this.maxCapacity = maxCapacity;
    }
    
    @Override
    public final BufType type() {
        return BufType.MESSAGE;
    }
    
    @Override
    public final int refCnt() {
        if (AbstractMessageBuf.REFCNT_FIELD_OFFSET >= 0L) {
            return PlatformDependent.getInt(this, AbstractMessageBuf.REFCNT_FIELD_OFFSET);
        }
        return this.refCnt;
    }
    
    @Override
    public MessageBuf<T> retain() {
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt == 0) {
                throw new IllegalBufferAccessException();
            }
            if (refCnt == Integer.MAX_VALUE) {
                throw new IllegalBufferAccessException("refCnt overflow");
            }
            if (AbstractMessageBuf.refCntUpdater.compareAndSet(this, refCnt, refCnt + 1)) {
                return this;
            }
        }
    }
    
    @Override
    public MessageBuf<T> retain(final int increment) {
        if (increment <= 0) {
            throw new IllegalArgumentException("increment: " + increment + " (expected: > 0)");
        }
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt == 0) {
                throw new IllegalBufferAccessException();
            }
            if (refCnt > Integer.MAX_VALUE - increment) {
                throw new IllegalBufferAccessException("refCnt overflow");
            }
            if (AbstractMessageBuf.refCntUpdater.compareAndSet(this, refCnt, refCnt + increment)) {
                return this;
            }
        }
    }
    
    @Override
    public final boolean release() {
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt == 0) {
                throw new IllegalBufferAccessException();
            }
            if (!AbstractMessageBuf.refCntUpdater.compareAndSet(this, refCnt, refCnt - 1)) {
                continue;
            }
            if (refCnt == 1) {
                this.deallocate();
                return true;
            }
            return false;
        }
    }
    
    @Override
    public final boolean release(final int decrement) {
        if (decrement <= 0) {
            throw new IllegalArgumentException("decrement: " + decrement + " (expected: > 0)");
        }
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt < decrement) {
                throw new IllegalBufferAccessException();
            }
            if (!AbstractMessageBuf.refCntUpdater.compareAndSet(this, refCnt, refCnt - decrement)) {
                continue;
            }
            if (refCnt == decrement) {
                this.deallocate();
                return true;
            }
            return false;
        }
    }
    
    protected abstract void deallocate();
    
    @Override
    public final int maxCapacity() {
        return this.maxCapacity;
    }
    
    @Override
    public final boolean isReadable() {
        return !this.isEmpty();
    }
    
    @Override
    public final boolean isReadable(final int size) {
        if (size < 0) {
            throw new IllegalArgumentException("size: " + size + " (expected: >= 0)");
        }
        return this.size() >= size;
    }
    
    @Override
    public final boolean isWritable() {
        return this.size() < this.maxCapacity;
    }
    
    @Override
    public final boolean isWritable(final int size) {
        if (size < 0) {
            throw new IllegalArgumentException("size: " + size + " (expected: >= 0)");
        }
        return this.size() <= this.maxCapacity - size;
    }
    
    protected final void ensureAccessible() {
        if (this.refCnt <= 0) {
            throw new IllegalBufferAccessException();
        }
    }
    
    @Override
    public final boolean add(final T t) {
        return super.add(t);
    }
    
    @Override
    public final T remove() {
        return super.remove();
    }
    
    @Override
    public final T element() {
        return super.element();
    }
    
    @Override
    public boolean unfoldAndAdd(final Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Object[]) {
            Object[] a;
            int i;
            Object m;
            for (a = (Object[])o, i = 0; i < a.length; ++i) {
                m = a[i];
                if (m == null) {
                    break;
                }
                this.add(m);
            }
            return i != 0;
        }
        return this.add(o);
    }
    
    @Override
    public int drainTo(final Collection<? super T> c) {
        this.ensureAccessible();
        int cnt = 0;
        while (true) {
            final T o = this.poll();
            if (o == null) {
                break;
            }
            c.add((Object)o);
            ++cnt;
        }
        return cnt;
    }
    
    @Override
    public int drainTo(final Collection<? super T> c, final int maxElements) {
        this.ensureAccessible();
        int cnt;
        for (cnt = 0; cnt < maxElements; ++cnt) {
            final T o = this.poll();
            if (o == null) {
                break;
            }
            c.add((Object)o);
        }
        return cnt;
    }
    
    @Override
    public String toString() {
        if (this.refCnt <= 0) {
            return this.getClass().getSimpleName() + "(freed)";
        }
        final StringBuilder buf = new StringBuilder();
        buf.append(this.getClass().getSimpleName());
        buf.append("(size: ");
        buf.append(this.size());
        if (this.maxCapacity != Integer.MAX_VALUE) {
            buf.append('/');
            buf.append(this.maxCapacity);
        }
        buf.append(')');
        return buf.toString();
    }
    
    static {
        refCntUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractMessageBuf.class, "refCnt");
        long refCntFieldOffset = -1L;
        try {
            if (PlatformDependent.hasUnsafe()) {
                refCntFieldOffset = PlatformDependent.objectFieldOffset(AbstractMessageBuf.class.getDeclaredField("refCnt"));
            }
        }
        catch (Throwable t) {}
        REFCNT_FIELD_OFFSET = refCntFieldOffset;
    }
}
