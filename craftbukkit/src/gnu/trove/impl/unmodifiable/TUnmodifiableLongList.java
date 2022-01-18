// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Random;
import gnu.trove.function.TLongFunction;
import java.util.RandomAccess;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.TLongCollection;
import gnu.trove.list.TLongList;

public class TUnmodifiableLongList extends TUnmodifiableLongCollection implements TLongList
{
    static final long serialVersionUID = -283967356065247728L;
    final TLongList list;
    
    public TUnmodifiableLongList(final TLongList list) {
        super(list);
        this.list = list;
    }
    
    public boolean equals(final Object o) {
        return o == this || this.list.equals(o);
    }
    
    public int hashCode() {
        return this.list.hashCode();
    }
    
    public long get(final int index) {
        return this.list.get(index);
    }
    
    public int indexOf(final long o) {
        return this.list.indexOf(o);
    }
    
    public int lastIndexOf(final long o) {
        return this.list.lastIndexOf(o);
    }
    
    public long[] toArray(final int offset, final int len) {
        return this.list.toArray(offset, len);
    }
    
    public long[] toArray(final long[] dest, final int offset, final int len) {
        return this.list.toArray(dest, offset, len);
    }
    
    public long[] toArray(final long[] dest, final int source_pos, final int dest_pos, final int len) {
        return this.list.toArray(dest, source_pos, dest_pos, len);
    }
    
    public boolean forEachDescending(final TLongProcedure procedure) {
        return this.list.forEachDescending(procedure);
    }
    
    public int binarySearch(final long value) {
        return this.list.binarySearch(value);
    }
    
    public int binarySearch(final long value, final int fromIndex, final int toIndex) {
        return this.list.binarySearch(value, fromIndex, toIndex);
    }
    
    public int indexOf(final int offset, final long value) {
        return this.list.indexOf(offset, value);
    }
    
    public int lastIndexOf(final int offset, final long value) {
        return this.list.lastIndexOf(offset, value);
    }
    
    public TLongList grep(final TLongProcedure condition) {
        return this.list.grep(condition);
    }
    
    public TLongList inverseGrep(final TLongProcedure condition) {
        return this.list.inverseGrep(condition);
    }
    
    public long max() {
        return this.list.max();
    }
    
    public long min() {
        return this.list.min();
    }
    
    public long sum() {
        return this.list.sum();
    }
    
    public TLongList subList(final int fromIndex, final int toIndex) {
        return new TUnmodifiableLongList(this.list.subList(fromIndex, toIndex));
    }
    
    private Object readResolve() {
        return (this.list instanceof RandomAccess) ? new TUnmodifiableRandomAccessLongList(this.list) : this;
    }
    
    public void add(final long[] vals) {
        throw new UnsupportedOperationException();
    }
    
    public void add(final long[] vals, final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public long removeAt(final int offset) {
        throw new UnsupportedOperationException();
    }
    
    public void remove(final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final long value) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final long[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final long[] values, final int valOffset, final int len) {
        throw new UnsupportedOperationException();
    }
    
    public long set(final int offset, final long val) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final long[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final long[] values, final int valOffset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public long replace(final int offset, final long val) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TLongFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public void reverse() {
        throw new UnsupportedOperationException();
    }
    
    public void reverse(final int from, final int to) {
        throw new UnsupportedOperationException();
    }
    
    public void shuffle(final Random rand) {
        throw new UnsupportedOperationException();
    }
    
    public void sort() {
        throw new UnsupportedOperationException();
    }
    
    public void sort(final int fromIndex, final int toIndex) {
        throw new UnsupportedOperationException();
    }
    
    public void fill(final long val) {
        throw new UnsupportedOperationException();
    }
    
    public void fill(final int fromIndex, final int toIndex, final long val) {
        throw new UnsupportedOperationException();
    }
}
