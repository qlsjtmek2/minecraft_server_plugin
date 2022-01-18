// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.util.RandomAccess;
import gnu.trove.function.TLongFunction;
import gnu.trove.procedure.TLongProcedure;
import java.util.Random;
import gnu.trove.TLongCollection;
import gnu.trove.list.TLongList;

public class TSynchronizedLongList extends TSynchronizedLongCollection implements TLongList
{
    static final long serialVersionUID = -7754090372962971524L;
    final TLongList list;
    
    public TSynchronizedLongList(final TLongList list) {
        super(list);
        this.list = list;
    }
    
    public TSynchronizedLongList(final TLongList list, final Object mutex) {
        super(list, mutex);
        this.list = list;
    }
    
    public boolean equals(final Object o) {
        synchronized (this.mutex) {
            return this.list.equals(o);
        }
    }
    
    public int hashCode() {
        synchronized (this.mutex) {
            return this.list.hashCode();
        }
    }
    
    public long get(final int index) {
        synchronized (this.mutex) {
            return this.list.get(index);
        }
    }
    
    public long set(final int index, final long element) {
        synchronized (this.mutex) {
            return this.list.set(index, element);
        }
    }
    
    public void set(final int offset, final long[] values) {
        synchronized (this.mutex) {
            this.list.set(offset, values);
        }
    }
    
    public void set(final int offset, final long[] values, final int valOffset, final int length) {
        synchronized (this.mutex) {
            this.list.set(offset, values, valOffset, length);
        }
    }
    
    public long replace(final int offset, final long val) {
        synchronized (this.mutex) {
            return this.list.replace(offset, val);
        }
    }
    
    public void remove(final int offset, final int length) {
        synchronized (this.mutex) {
            this.list.remove(offset, length);
        }
    }
    
    public long removeAt(final int offset) {
        synchronized (this.mutex) {
            return this.list.removeAt(offset);
        }
    }
    
    public void add(final long[] vals) {
        synchronized (this.mutex) {
            this.list.add(vals);
        }
    }
    
    public void add(final long[] vals, final int offset, final int length) {
        synchronized (this.mutex) {
            this.list.add(vals, offset, length);
        }
    }
    
    public void insert(final int offset, final long value) {
        synchronized (this.mutex) {
            this.list.insert(offset, value);
        }
    }
    
    public void insert(final int offset, final long[] values) {
        synchronized (this.mutex) {
            this.list.insert(offset, values);
        }
    }
    
    public void insert(final int offset, final long[] values, final int valOffset, final int len) {
        synchronized (this.mutex) {
            this.list.insert(offset, values, valOffset, len);
        }
    }
    
    public int indexOf(final long o) {
        synchronized (this.mutex) {
            return this.list.indexOf(o);
        }
    }
    
    public int lastIndexOf(final long o) {
        synchronized (this.mutex) {
            return this.list.lastIndexOf(o);
        }
    }
    
    public TLongList subList(final int fromIndex, final int toIndex) {
        synchronized (this.mutex) {
            return new TSynchronizedLongList(this.list.subList(fromIndex, toIndex), this.mutex);
        }
    }
    
    public long[] toArray(final int offset, final int len) {
        synchronized (this.mutex) {
            return this.list.toArray(offset, len);
        }
    }
    
    public long[] toArray(final long[] dest, final int offset, final int len) {
        synchronized (this.mutex) {
            return this.list.toArray(dest, offset, len);
        }
    }
    
    public long[] toArray(final long[] dest, final int source_pos, final int dest_pos, final int len) {
        synchronized (this.mutex) {
            return this.list.toArray(dest, source_pos, dest_pos, len);
        }
    }
    
    public int indexOf(final int offset, final long value) {
        synchronized (this.mutex) {
            return this.list.indexOf(offset, value);
        }
    }
    
    public int lastIndexOf(final int offset, final long value) {
        synchronized (this.mutex) {
            return this.list.lastIndexOf(offset, value);
        }
    }
    
    public void fill(final long val) {
        synchronized (this.mutex) {
            this.list.fill(val);
        }
    }
    
    public void fill(final int fromIndex, final int toIndex, final long val) {
        synchronized (this.mutex) {
            this.list.fill(fromIndex, toIndex, val);
        }
    }
    
    public void reverse() {
        synchronized (this.mutex) {
            this.list.reverse();
        }
    }
    
    public void reverse(final int from, final int to) {
        synchronized (this.mutex) {
            this.list.reverse(from, to);
        }
    }
    
    public void shuffle(final Random rand) {
        synchronized (this.mutex) {
            this.list.shuffle(rand);
        }
    }
    
    public void sort() {
        synchronized (this.mutex) {
            this.list.sort();
        }
    }
    
    public void sort(final int fromIndex, final int toIndex) {
        synchronized (this.mutex) {
            this.list.sort(fromIndex, toIndex);
        }
    }
    
    public int binarySearch(final long value) {
        synchronized (this.mutex) {
            return this.list.binarySearch(value);
        }
    }
    
    public int binarySearch(final long value, final int fromIndex, final int toIndex) {
        synchronized (this.mutex) {
            return this.list.binarySearch(value, fromIndex, toIndex);
        }
    }
    
    public TLongList grep(final TLongProcedure condition) {
        synchronized (this.mutex) {
            return this.list.grep(condition);
        }
    }
    
    public TLongList inverseGrep(final TLongProcedure condition) {
        synchronized (this.mutex) {
            return this.list.inverseGrep(condition);
        }
    }
    
    public long max() {
        synchronized (this.mutex) {
            return this.list.max();
        }
    }
    
    public long min() {
        synchronized (this.mutex) {
            return this.list.min();
        }
    }
    
    public long sum() {
        synchronized (this.mutex) {
            return this.list.sum();
        }
    }
    
    public boolean forEachDescending(final TLongProcedure procedure) {
        synchronized (this.mutex) {
            return this.list.forEachDescending(procedure);
        }
    }
    
    public void transformValues(final TLongFunction function) {
        synchronized (this.mutex) {
            this.list.transformValues(function);
        }
    }
    
    private Object readResolve() {
        return (this.list instanceof RandomAccess) ? new TSynchronizedRandomAccessLongList(this.list) : this;
    }
}
