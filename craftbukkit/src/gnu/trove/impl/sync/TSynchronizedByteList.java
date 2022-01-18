// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.util.RandomAccess;
import gnu.trove.function.TByteFunction;
import gnu.trove.procedure.TByteProcedure;
import java.util.Random;
import gnu.trove.TByteCollection;
import gnu.trove.list.TByteList;

public class TSynchronizedByteList extends TSynchronizedByteCollection implements TByteList
{
    static final long serialVersionUID = -7754090372962971524L;
    final TByteList list;
    
    public TSynchronizedByteList(final TByteList list) {
        super(list);
        this.list = list;
    }
    
    public TSynchronizedByteList(final TByteList list, final Object mutex) {
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
    
    public byte get(final int index) {
        synchronized (this.mutex) {
            return this.list.get(index);
        }
    }
    
    public byte set(final int index, final byte element) {
        synchronized (this.mutex) {
            return this.list.set(index, element);
        }
    }
    
    public void set(final int offset, final byte[] values) {
        synchronized (this.mutex) {
            this.list.set(offset, values);
        }
    }
    
    public void set(final int offset, final byte[] values, final int valOffset, final int length) {
        synchronized (this.mutex) {
            this.list.set(offset, values, valOffset, length);
        }
    }
    
    public byte replace(final int offset, final byte val) {
        synchronized (this.mutex) {
            return this.list.replace(offset, val);
        }
    }
    
    public void remove(final int offset, final int length) {
        synchronized (this.mutex) {
            this.list.remove(offset, length);
        }
    }
    
    public byte removeAt(final int offset) {
        synchronized (this.mutex) {
            return this.list.removeAt(offset);
        }
    }
    
    public void add(final byte[] vals) {
        synchronized (this.mutex) {
            this.list.add(vals);
        }
    }
    
    public void add(final byte[] vals, final int offset, final int length) {
        synchronized (this.mutex) {
            this.list.add(vals, offset, length);
        }
    }
    
    public void insert(final int offset, final byte value) {
        synchronized (this.mutex) {
            this.list.insert(offset, value);
        }
    }
    
    public void insert(final int offset, final byte[] values) {
        synchronized (this.mutex) {
            this.list.insert(offset, values);
        }
    }
    
    public void insert(final int offset, final byte[] values, final int valOffset, final int len) {
        synchronized (this.mutex) {
            this.list.insert(offset, values, valOffset, len);
        }
    }
    
    public int indexOf(final byte o) {
        synchronized (this.mutex) {
            return this.list.indexOf(o);
        }
    }
    
    public int lastIndexOf(final byte o) {
        synchronized (this.mutex) {
            return this.list.lastIndexOf(o);
        }
    }
    
    public TByteList subList(final int fromIndex, final int toIndex) {
        synchronized (this.mutex) {
            return new TSynchronizedByteList(this.list.subList(fromIndex, toIndex), this.mutex);
        }
    }
    
    public byte[] toArray(final int offset, final int len) {
        synchronized (this.mutex) {
            return this.list.toArray(offset, len);
        }
    }
    
    public byte[] toArray(final byte[] dest, final int offset, final int len) {
        synchronized (this.mutex) {
            return this.list.toArray(dest, offset, len);
        }
    }
    
    public byte[] toArray(final byte[] dest, final int source_pos, final int dest_pos, final int len) {
        synchronized (this.mutex) {
            return this.list.toArray(dest, source_pos, dest_pos, len);
        }
    }
    
    public int indexOf(final int offset, final byte value) {
        synchronized (this.mutex) {
            return this.list.indexOf(offset, value);
        }
    }
    
    public int lastIndexOf(final int offset, final byte value) {
        synchronized (this.mutex) {
            return this.list.lastIndexOf(offset, value);
        }
    }
    
    public void fill(final byte val) {
        synchronized (this.mutex) {
            this.list.fill(val);
        }
    }
    
    public void fill(final int fromIndex, final int toIndex, final byte val) {
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
    
    public int binarySearch(final byte value) {
        synchronized (this.mutex) {
            return this.list.binarySearch(value);
        }
    }
    
    public int binarySearch(final byte value, final int fromIndex, final int toIndex) {
        synchronized (this.mutex) {
            return this.list.binarySearch(value, fromIndex, toIndex);
        }
    }
    
    public TByteList grep(final TByteProcedure condition) {
        synchronized (this.mutex) {
            return this.list.grep(condition);
        }
    }
    
    public TByteList inverseGrep(final TByteProcedure condition) {
        synchronized (this.mutex) {
            return this.list.inverseGrep(condition);
        }
    }
    
    public byte max() {
        synchronized (this.mutex) {
            return this.list.max();
        }
    }
    
    public byte min() {
        synchronized (this.mutex) {
            return this.list.min();
        }
    }
    
    public byte sum() {
        synchronized (this.mutex) {
            return this.list.sum();
        }
    }
    
    public boolean forEachDescending(final TByteProcedure procedure) {
        synchronized (this.mutex) {
            return this.list.forEachDescending(procedure);
        }
    }
    
    public void transformValues(final TByteFunction function) {
        synchronized (this.mutex) {
            this.list.transformValues(function);
        }
    }
    
    private Object readResolve() {
        return (this.list instanceof RandomAccess) ? new TSynchronizedRandomAccessByteList(this.list) : this;
    }
}
