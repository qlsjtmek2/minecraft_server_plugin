// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Random;
import gnu.trove.function.TCharFunction;
import java.util.RandomAccess;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.TCharCollection;
import gnu.trove.list.TCharList;

public class TUnmodifiableCharList extends TUnmodifiableCharCollection implements TCharList
{
    static final long serialVersionUID = -283967356065247728L;
    final TCharList list;
    
    public TUnmodifiableCharList(final TCharList list) {
        super(list);
        this.list = list;
    }
    
    public boolean equals(final Object o) {
        return o == this || this.list.equals(o);
    }
    
    public int hashCode() {
        return this.list.hashCode();
    }
    
    public char get(final int index) {
        return this.list.get(index);
    }
    
    public int indexOf(final char o) {
        return this.list.indexOf(o);
    }
    
    public int lastIndexOf(final char o) {
        return this.list.lastIndexOf(o);
    }
    
    public char[] toArray(final int offset, final int len) {
        return this.list.toArray(offset, len);
    }
    
    public char[] toArray(final char[] dest, final int offset, final int len) {
        return this.list.toArray(dest, offset, len);
    }
    
    public char[] toArray(final char[] dest, final int source_pos, final int dest_pos, final int len) {
        return this.list.toArray(dest, source_pos, dest_pos, len);
    }
    
    public boolean forEachDescending(final TCharProcedure procedure) {
        return this.list.forEachDescending(procedure);
    }
    
    public int binarySearch(final char value) {
        return this.list.binarySearch(value);
    }
    
    public int binarySearch(final char value, final int fromIndex, final int toIndex) {
        return this.list.binarySearch(value, fromIndex, toIndex);
    }
    
    public int indexOf(final int offset, final char value) {
        return this.list.indexOf(offset, value);
    }
    
    public int lastIndexOf(final int offset, final char value) {
        return this.list.lastIndexOf(offset, value);
    }
    
    public TCharList grep(final TCharProcedure condition) {
        return this.list.grep(condition);
    }
    
    public TCharList inverseGrep(final TCharProcedure condition) {
        return this.list.inverseGrep(condition);
    }
    
    public char max() {
        return this.list.max();
    }
    
    public char min() {
        return this.list.min();
    }
    
    public char sum() {
        return this.list.sum();
    }
    
    public TCharList subList(final int fromIndex, final int toIndex) {
        return new TUnmodifiableCharList(this.list.subList(fromIndex, toIndex));
    }
    
    private Object readResolve() {
        return (this.list instanceof RandomAccess) ? new TUnmodifiableRandomAccessCharList(this.list) : this;
    }
    
    public void add(final char[] vals) {
        throw new UnsupportedOperationException();
    }
    
    public void add(final char[] vals, final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public char removeAt(final int offset) {
        throw new UnsupportedOperationException();
    }
    
    public void remove(final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final char value) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final char[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final char[] values, final int valOffset, final int len) {
        throw new UnsupportedOperationException();
    }
    
    public char set(final int offset, final char val) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final char[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final char[] values, final int valOffset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public char replace(final int offset, final char val) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TCharFunction function) {
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
    
    public void fill(final char val) {
        throw new UnsupportedOperationException();
    }
    
    public void fill(final int fromIndex, final int toIndex, final char val) {
        throw new UnsupportedOperationException();
    }
}
