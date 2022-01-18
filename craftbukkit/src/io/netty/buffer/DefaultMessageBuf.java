// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import java.util.NoSuchElementException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.ConcurrentModificationException;

final class DefaultMessageBuf<T> extends AbstractMessageBuf<T>
{
    private static final int MIN_INITIAL_CAPACITY = 8;
    private static final Object[] PLACEHOLDER;
    private T[] elements;
    private int head;
    private int tail;
    static final /* synthetic */ boolean $assertionsDisabled;
    
    DefaultMessageBuf() {
        this(16);
    }
    
    DefaultMessageBuf(final int initialCapacity) {
        this(initialCapacity, Integer.MAX_VALUE);
    }
    
    DefaultMessageBuf(int initialCapacity, final int maxCapacity) {
        super(maxCapacity);
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity: " + initialCapacity + " (expected: >= 0)");
        }
        if (maxCapacity < initialCapacity) {
            throw new IllegalArgumentException("maxCapacity: " + maxCapacity + " (expected: >= initialCapacity(" + initialCapacity + ')');
        }
        if (initialCapacity >= 8) {
            initialCapacity |= initialCapacity >>> 1;
            initialCapacity |= initialCapacity >>> 2;
            initialCapacity |= initialCapacity >>> 4;
            initialCapacity |= initialCapacity >>> 8;
            initialCapacity |= initialCapacity >>> 16;
            if (++initialCapacity < 0) {
                initialCapacity >>>= 1;
            }
        }
        else {
            initialCapacity = 8;
        }
        this.elements = cast(new Object[initialCapacity]);
    }
    
    @Override
    protected void deallocate() {
        this.head = 0;
        this.tail = 0;
        this.elements = cast(DefaultMessageBuf.PLACEHOLDER);
    }
    
    @Override
    public boolean offer(final T e) {
        if (e == null) {
            throw new NullPointerException();
        }
        this.ensureAccessible();
        if (!this.isWritable()) {
            return false;
        }
        this.elements[this.tail] = e;
        if ((this.tail = (this.tail + 1 & this.elements.length - 1)) == this.head) {
            this.doubleCapacity();
        }
        return true;
    }
    
    private void doubleCapacity() {
        assert this.head == this.tail;
        final int p = this.head;
        final int n = this.elements.length;
        final int r = n - p;
        final int newCapacity = n << 1;
        if (newCapacity < 0) {
            throw new IllegalStateException("Sorry, deque too big");
        }
        final Object[] a = new Object[newCapacity];
        System.arraycopy(this.elements, p, a, 0, r);
        System.arraycopy(this.elements, 0, a, r, p);
        this.elements = cast(a);
        this.head = 0;
        this.tail = n;
    }
    
    @Override
    public T poll() {
        this.ensureAccessible();
        final int h = this.head;
        final T result = (T)this.elements[h];
        if (result == null) {
            return null;
        }
        this.elements[h] = null;
        this.head = (h + 1 & this.elements.length - 1);
        return result;
    }
    
    @Override
    public T peek() {
        this.ensureAccessible();
        return (T)this.elements[this.head];
    }
    
    @Override
    public boolean remove(final Object o) {
        if (o == null) {
            return false;
        }
        this.ensureAccessible();
        T x;
        for (int mask = this.elements.length - 1, i = this.head; (x = (T)this.elements[i]) != null; i = (i + 1 & mask)) {
            if (o.equals(x)) {
                this.delete(i);
                return true;
            }
        }
        return false;
    }
    
    private boolean delete(final int i) {
        assert this.elements[this.tail] == null;
        Label_0100: {
            if (!DefaultMessageBuf.$assertionsDisabled) {
                if (this.head == this.tail) {
                    if (this.elements[this.head] == null) {
                        break Label_0100;
                    }
                }
                else if (this.elements[this.head] != null && this.elements[this.tail - 1 & this.elements.length - 1] != null) {
                    break Label_0100;
                }
                throw new AssertionError();
            }
        }
        assert this.elements[this.head - 1 & this.elements.length - 1] == null;
        final T[] elements = (T[])this.elements;
        final int mask = elements.length - 1;
        final int h = this.head;
        final int t = this.tail;
        final int front = i - h & mask;
        final int back = t - i & mask;
        if (front >= (t - h & mask)) {
            throw new ConcurrentModificationException();
        }
        if (front < back) {
            if (h <= i) {
                System.arraycopy(elements, h, elements, h + 1, front);
            }
            else {
                System.arraycopy(elements, 0, elements, 1, i);
                elements[0] = elements[mask];
                System.arraycopy(elements, h, elements, h + 1, mask - h);
            }
            elements[h] = null;
            this.head = (h + 1 & mask);
            return false;
        }
        if (i < t) {
            System.arraycopy(elements, i + 1, elements, i, back);
            this.tail = t - 1;
        }
        else {
            System.arraycopy(elements, i + 1, elements, i, mask - i);
            elements[mask] = elements[0];
            System.arraycopy(elements, 1, elements, 0, t);
            this.tail = (t - 1 & mask);
        }
        return true;
    }
    
    @Override
    public int size() {
        return this.tail - this.head & this.elements.length - 1;
    }
    
    @Override
    public boolean isEmpty() {
        return this.head == this.tail;
    }
    
    @Override
    public Iterator<T> iterator() {
        this.ensureAccessible();
        return new Itr();
    }
    
    @Override
    public boolean contains(final Object o) {
        if (o == null) {
            return false;
        }
        this.ensureAccessible();
        Object e;
        for (int mask = this.elements.length - 1, i = this.head; (e = this.elements[i]) != null; i = (i + 1 & mask)) {
            if (o.equals(e)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void clear() {
        this.ensureAccessible();
        final int head = this.head;
        final int tail = this.tail;
        if (head != tail) {
            final boolean b = false;
            this.tail = (b ? 1 : 0);
            this.head = (b ? 1 : 0);
            final int mask = this.elements.length - 1;
            int i = head;
            do {
                this.elements[i] = null;
                i = (i + 1 & mask);
            } while (i != tail);
        }
    }
    
    @Override
    public Object[] toArray() {
        this.ensureAccessible();
        return this.copyElements(new Object[this.size()]);
    }
    
    @Override
    public <T> T[] toArray(T[] a) {
        this.ensureAccessible();
        final int size = this.size();
        if (a.length < size) {
            a = cast(Array.newInstance(a.getClass().getComponentType(), size));
        }
        this.copyElements(a);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }
    
    private <U> U[] copyElements(final U[] a) {
        if (this.head < this.tail) {
            System.arraycopy(this.elements, this.head, cast(a), 0, this.size());
        }
        else if (this.head > this.tail) {
            final int headPortionLen = this.elements.length - this.head;
            System.arraycopy(this.elements, this.head, cast(a), 0, headPortionLen);
            System.arraycopy(this.elements, 0, cast(a), headPortionLen, this.tail);
        }
        return a;
    }
    
    private static <T> T[] cast(final Object a) {
        return (T[])a;
    }
    
    static {
        PLACEHOLDER = new Object[2];
    }
    
    private class Itr implements Iterator<T>
    {
        private int cursor;
        private int fence;
        private int lastRet;
        
        private Itr() {
            this.cursor = DefaultMessageBuf.this.head;
            this.fence = DefaultMessageBuf.this.tail;
            this.lastRet = -1;
        }
        
        @Override
        public boolean hasNext() {
            DefaultMessageBuf.this.ensureAccessible();
            return this.cursor != this.fence;
        }
        
        @Override
        public T next() {
            DefaultMessageBuf.this.ensureAccessible();
            if (this.cursor == this.fence) {
                throw new NoSuchElementException();
            }
            final T result = (T)DefaultMessageBuf.this.elements[this.cursor];
            if (DefaultMessageBuf.this.tail != this.fence || result == null) {
                throw new ConcurrentModificationException();
            }
            this.lastRet = this.cursor;
            this.cursor = (this.cursor + 1 & DefaultMessageBuf.this.elements.length - 1);
            return result;
        }
        
        @Override
        public void remove() {
            DefaultMessageBuf.this.ensureAccessible();
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            if (DefaultMessageBuf.this.delete(this.lastRet)) {
                this.cursor = (this.cursor - 1 & DefaultMessageBuf.this.elements.length - 1);
                this.fence = DefaultMessageBuf.this.tail;
            }
            this.lastRet = -1;
        }
    }
}
