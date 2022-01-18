// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Random;
import gnu.trove.function.TShortFunction;
import java.util.RandomAccess;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.TShortCollection;
import gnu.trove.list.TShortList;

public class TUnmodifiableShortList extends TUnmodifiableShortCollection implements TShortList
{
    static final long serialVersionUID = -283967356065247728L;
    final TShortList list;
    
    public TUnmodifiableShortList(final TShortList list) {
        super(list);
        this.list = list;
    }
    
    public boolean equals(final Object o) {
        return o == this || this.list.equals(o);
    }
    
    public int hashCode() {
        return this.list.hashCode();
    }
    
    public short get(final int index) {
        return this.list.get(index);
    }
    
    public int indexOf(final short o) {
        return this.list.indexOf(o);
    }
    
    public int lastIndexOf(final short o) {
        return this.list.lastIndexOf(o);
    }
    
    public short[] toArray(final int offset, final int len) {
        return this.list.toArray(offset, len);
    }
    
    public short[] toArray(final short[] dest, final int offset, final int len) {
        return this.list.toArray(dest, offset, len);
    }
    
    public short[] toArray(final short[] dest, final int source_pos, final int dest_pos, final int len) {
        return this.list.toArray(dest, source_pos, dest_pos, len);
    }
    
    public boolean forEachDescending(final TShortProcedure procedure) {
        return this.list.forEachDescending(procedure);
    }
    
    public int binarySearch(final short value) {
        return this.list.binarySearch(value);
    }
    
    public int binarySearch(final short value, final int fromIndex, final int toIndex) {
        return this.list.binarySearch(value, fromIndex, toIndex);
    }
    
    public int indexOf(final int offset, final short value) {
        return this.list.indexOf(offset, value);
    }
    
    public int lastIndexOf(final int offset, final short value) {
        return this.list.lastIndexOf(offset, value);
    }
    
    public TShortList grep(final TShortProcedure condition) {
        return this.list.grep(condition);
    }
    
    public TShortList inverseGrep(final TShortProcedure condition) {
        return this.list.inverseGrep(condition);
    }
    
    public short max() {
        return this.list.max();
    }
    
    public short min() {
        return this.list.min();
    }
    
    public short sum() {
        return this.list.sum();
    }
    
    public TShortList subList(final int fromIndex, final int toIndex) {
        return new TUnmodifiableShortList(this.list.subList(fromIndex, toIndex));
    }
    
    private Object readResolve() {
        return (this.list instanceof RandomAccess) ? new TUnmodifiableRandomAccessShortList(this.list) : this;
    }
    
    public void add(final short[] vals) {
        throw new UnsupportedOperationException();
    }
    
    public void add(final short[] vals, final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public short removeAt(final int offset) {
        throw new UnsupportedOperationException();
    }
    
    public void remove(final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final short value) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final short[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final short[] values, final int valOffset, final int len) {
        throw new UnsupportedOperationException();
    }
    
    public short set(final int offset, final short val) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final short[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final short[] values, final int valOffset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public short replace(final int offset, final short val) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TShortFunction function) {
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
    
    public void fill(final short val) {
        throw new UnsupportedOperationException();
    }
    
    public void fill(final int fromIndex, final int toIndex, final short val) {
        throw new UnsupportedOperationException();
    }
}
