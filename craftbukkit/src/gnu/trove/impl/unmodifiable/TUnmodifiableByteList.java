// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Random;
import gnu.trove.function.TByteFunction;
import java.util.RandomAccess;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.TByteCollection;
import gnu.trove.list.TByteList;

public class TUnmodifiableByteList extends TUnmodifiableByteCollection implements TByteList
{
    static final long serialVersionUID = -283967356065247728L;
    final TByteList list;
    
    public TUnmodifiableByteList(final TByteList list) {
        super(list);
        this.list = list;
    }
    
    public boolean equals(final Object o) {
        return o == this || this.list.equals(o);
    }
    
    public int hashCode() {
        return this.list.hashCode();
    }
    
    public byte get(final int index) {
        return this.list.get(index);
    }
    
    public int indexOf(final byte o) {
        return this.list.indexOf(o);
    }
    
    public int lastIndexOf(final byte o) {
        return this.list.lastIndexOf(o);
    }
    
    public byte[] toArray(final int offset, final int len) {
        return this.list.toArray(offset, len);
    }
    
    public byte[] toArray(final byte[] dest, final int offset, final int len) {
        return this.list.toArray(dest, offset, len);
    }
    
    public byte[] toArray(final byte[] dest, final int source_pos, final int dest_pos, final int len) {
        return this.list.toArray(dest, source_pos, dest_pos, len);
    }
    
    public boolean forEachDescending(final TByteProcedure procedure) {
        return this.list.forEachDescending(procedure);
    }
    
    public int binarySearch(final byte value) {
        return this.list.binarySearch(value);
    }
    
    public int binarySearch(final byte value, final int fromIndex, final int toIndex) {
        return this.list.binarySearch(value, fromIndex, toIndex);
    }
    
    public int indexOf(final int offset, final byte value) {
        return this.list.indexOf(offset, value);
    }
    
    public int lastIndexOf(final int offset, final byte value) {
        return this.list.lastIndexOf(offset, value);
    }
    
    public TByteList grep(final TByteProcedure condition) {
        return this.list.grep(condition);
    }
    
    public TByteList inverseGrep(final TByteProcedure condition) {
        return this.list.inverseGrep(condition);
    }
    
    public byte max() {
        return this.list.max();
    }
    
    public byte min() {
        return this.list.min();
    }
    
    public byte sum() {
        return this.list.sum();
    }
    
    public TByteList subList(final int fromIndex, final int toIndex) {
        return new TUnmodifiableByteList(this.list.subList(fromIndex, toIndex));
    }
    
    private Object readResolve() {
        return (this.list instanceof RandomAccess) ? new TUnmodifiableRandomAccessByteList(this.list) : this;
    }
    
    public void add(final byte[] vals) {
        throw new UnsupportedOperationException();
    }
    
    public void add(final byte[] vals, final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public byte removeAt(final int offset) {
        throw new UnsupportedOperationException();
    }
    
    public void remove(final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final byte value) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final byte[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final byte[] values, final int valOffset, final int len) {
        throw new UnsupportedOperationException();
    }
    
    public byte set(final int offset, final byte val) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final byte[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final byte[] values, final int valOffset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public byte replace(final int offset, final byte val) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TByteFunction function) {
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
    
    public void fill(final byte val) {
        throw new UnsupportedOperationException();
    }
    
    public void fill(final int fromIndex, final int toIndex, final byte val) {
        throw new UnsupportedOperationException();
    }
}
