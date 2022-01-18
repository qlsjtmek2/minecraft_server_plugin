// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class LongHashSet
{
    private static final int INITIAL_SIZE = 3;
    private static final double LOAD_FACTOR = 0.75;
    private static final long FREE = 0L;
    private static final long REMOVED = Long.MIN_VALUE;
    private int freeEntries;
    private int elements;
    private long[] values;
    private int modCount;
    private static final Object PRESENT;
    private final FlatMap<Object> flat;
    
    public LongHashSet() {
        this(3);
    }
    
    public LongHashSet(final int size) {
        this.flat = new FlatMap<Object>();
        this.values = new long[(size == 0) ? 1 : size];
        this.elements = 0;
        this.freeEntries = this.values.length;
        this.modCount = 0;
    }
    
    public Iterator iterator() {
        return new Itr();
    }
    
    public int size() {
        return this.elements;
    }
    
    public boolean isEmpty() {
        return this.elements == 0;
    }
    
    public boolean contains(final int msw, final int lsw) {
        return this.flat.get(msw, lsw) != null || this.contains(LongHash.toLong(msw, lsw));
    }
    
    public boolean contains(final long value) {
        if (this.flat.get(value) != null) {
            return true;
        }
        final int hash = this.hash(value);
        int index = (hash & Integer.MAX_VALUE) % this.values.length;
        int offset = 1;
        while (this.values[index] != 0L && (this.hash(this.values[index]) != hash || this.values[index] != value)) {
            index = (index + offset & Integer.MAX_VALUE) % this.values.length;
            offset = offset * 2 + 1;
            if (offset == -1) {
                offset = 2;
            }
        }
        return this.values[index] != 0L;
    }
    
    public boolean add(final int msw, final int lsw) {
        return this.add(LongHash.toLong(msw, lsw));
    }
    
    public boolean add(final long value) {
        this.flat.put(value, LongHashSet.PRESENT);
        final int hash = this.hash(value);
        int index = (hash & Integer.MAX_VALUE) % this.values.length;
        int offset = 1;
        int deletedix = -1;
        while (this.values[index] != 0L && (this.hash(this.values[index]) != hash || this.values[index] != value)) {
            if (this.values[index] == Long.MIN_VALUE) {
                deletedix = index;
            }
            index = (index + offset & Integer.MAX_VALUE) % this.values.length;
            offset = offset * 2 + 1;
            if (offset == -1) {
                offset = 2;
            }
        }
        if (this.values[index] == 0L) {
            if (deletedix != -1) {
                index = deletedix;
            }
            else {
                --this.freeEntries;
            }
            ++this.modCount;
            ++this.elements;
            this.values[index] = value;
            if (1.0 - this.freeEntries / this.values.length > 0.75) {
                this.rehash();
            }
            return true;
        }
        return false;
    }
    
    public void remove(final int msw, final int lsw) {
        this.flat.put(msw, lsw, null);
        this.remove(LongHash.toLong(msw, lsw));
    }
    
    private boolean remove(final long value) {
        final int hash = this.hash(value);
        int index = (hash & Integer.MAX_VALUE) % this.values.length;
        int offset = 1;
        while (this.values[index] != 0L && (this.hash(this.values[index]) != hash || this.values[index] != value)) {
            index = (index + offset & Integer.MAX_VALUE) % this.values.length;
            offset = offset * 2 + 1;
            if (offset == -1) {
                offset = 2;
            }
        }
        if (this.values[index] != 0L) {
            this.values[index] = Long.MIN_VALUE;
            ++this.modCount;
            --this.elements;
            return true;
        }
        return false;
    }
    
    public void clear() {
        this.elements = 0;
        for (int ix = 0; ix < this.values.length; ++ix) {
            this.values[ix] = 0L;
        }
        this.freeEntries = this.values.length;
        ++this.modCount;
    }
    
    public long[] toArray() {
        final long[] result = new long[this.elements];
        final long[] values = Java15Compat.Arrays_copyOf(this.values, this.values.length);
        int pos = 0;
        for (final long value : values) {
            if (value != 0L && value != Long.MIN_VALUE) {
                result[pos++] = value;
            }
        }
        return result;
    }
    
    public long popFirst() {
        for (final long value : this.values) {
            if (value != 0L && value != Long.MIN_VALUE) {
                this.remove(value);
                return value;
            }
        }
        return 0L;
    }
    
    public long[] popAll() {
        final long[] ret = this.toArray();
        this.clear();
        return ret;
    }
    
    private int hash(long value) {
        value ^= value >>> 33;
        value *= -49064778989728563L;
        value ^= value >>> 33;
        value *= -4265267296055464877L;
        value ^= value >>> 33;
        return (int)value;
    }
    
    private void rehash() {
        final int gargagecells = this.values.length - (this.elements + this.freeEntries);
        if (gargagecells / this.values.length > 0.05) {
            this.rehash(this.values.length);
        }
        else {
            this.rehash(this.values.length * 2 + 1);
        }
    }
    
    private void rehash(final int newCapacity) {
        final long[] newValues = new long[newCapacity];
        for (final long value : this.values) {
            if (value != 0L) {
                if (value != Long.MIN_VALUE) {
                    final int hash = this.hash(value);
                    int index = (hash & Integer.MAX_VALUE) % newCapacity;
                    int offset = 1;
                    while (newValues[index] != 0L) {
                        index = (index + offset & Integer.MAX_VALUE) % newCapacity;
                        offset = offset * 2 + 1;
                        if (offset == -1) {
                            offset = 2;
                        }
                    }
                    newValues[index] = value;
                }
            }
        }
        this.values = newValues;
        this.freeEntries = this.values.length - this.elements;
    }
    
    static {
        PRESENT = new Object();
    }
    
    private class Itr implements Iterator
    {
        private int index;
        private int lastReturned;
        private int expectedModCount;
        
        public Itr() {
            this.lastReturned = -1;
            this.index = 0;
            while (this.index < LongHashSet.this.values.length && (LongHashSet.this.values[this.index] == 0L || LongHashSet.this.values[this.index] == Long.MIN_VALUE)) {
                ++this.index;
            }
            this.expectedModCount = LongHashSet.this.modCount;
        }
        
        public boolean hasNext() {
            return this.index != LongHashSet.this.values.length;
        }
        
        public Long next() {
            if (LongHashSet.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            final int length = LongHashSet.this.values.length;
            if (this.index >= length) {
                this.lastReturned = -2;
                throw new NoSuchElementException();
            }
            this.lastReturned = this.index;
            ++this.index;
            while (this.index < length && (LongHashSet.this.values[this.index] == 0L || LongHashSet.this.values[this.index] == Long.MIN_VALUE)) {
                ++this.index;
            }
            if (LongHashSet.this.values[this.lastReturned] == 0L) {
                return 0L;
            }
            return LongHashSet.this.values[this.lastReturned];
        }
        
        public void remove() {
            if (LongHashSet.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (this.lastReturned == -1 || this.lastReturned == -2) {
                throw new IllegalStateException();
            }
            if (LongHashSet.this.values[this.lastReturned] != 0L && LongHashSet.this.values[this.lastReturned] != Long.MIN_VALUE) {
                LongHashSet.this.values[this.lastReturned] = Long.MIN_VALUE;
                LongHashSet.this.elements--;
                LongHashSet.this.modCount++;
                this.expectedModCount = LongHashSet.this.modCount;
            }
        }
    }
}
