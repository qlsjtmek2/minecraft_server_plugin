// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Random;
import gnu.trove.function.TIntFunction;
import java.util.RandomAccess;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.TIntCollection;
import gnu.trove.list.TIntList;

public class TUnmodifiableIntList extends TUnmodifiableIntCollection implements TIntList
{
    static final long serialVersionUID = -283967356065247728L;
    final TIntList list;
    
    public TUnmodifiableIntList(final TIntList list) {
        super(list);
        this.list = list;
    }
    
    public boolean equals(final Object o) {
        return o == this || this.list.equals(o);
    }
    
    public int hashCode() {
        return this.list.hashCode();
    }
    
    public int get(final int index) {
        return this.list.get(index);
    }
    
    public int indexOf(final int o) {
        return this.list.indexOf(o);
    }
    
    public int lastIndexOf(final int o) {
        return this.list.lastIndexOf(o);
    }
    
    public int[] toArray(final int offset, final int len) {
        return this.list.toArray(offset, len);
    }
    
    public int[] toArray(final int[] dest, final int offset, final int len) {
        return this.list.toArray(dest, offset, len);
    }
    
    public int[] toArray(final int[] dest, final int source_pos, final int dest_pos, final int len) {
        return this.list.toArray(dest, source_pos, dest_pos, len);
    }
    
    public boolean forEachDescending(final TIntProcedure procedure) {
        return this.list.forEachDescending(procedure);
    }
    
    public int binarySearch(final int value) {
        return this.list.binarySearch(value);
    }
    
    public int binarySearch(final int value, final int fromIndex, final int toIndex) {
        return this.list.binarySearch(value, fromIndex, toIndex);
    }
    
    public int indexOf(final int offset, final int value) {
        return this.list.indexOf(offset, value);
    }
    
    public int lastIndexOf(final int offset, final int value) {
        return this.list.lastIndexOf(offset, value);
    }
    
    public TIntList grep(final TIntProcedure condition) {
        return this.list.grep(condition);
    }
    
    public TIntList inverseGrep(final TIntProcedure condition) {
        return this.list.inverseGrep(condition);
    }
    
    public int max() {
        return this.list.max();
    }
    
    public int min() {
        return this.list.min();
    }
    
    public int sum() {
        return this.list.sum();
    }
    
    public TIntList subList(final int fromIndex, final int toIndex) {
        return new TUnmodifiableIntList(this.list.subList(fromIndex, toIndex));
    }
    
    private Object readResolve() {
        return (this.list instanceof RandomAccess) ? new TUnmodifiableRandomAccessIntList(this.list) : this;
    }
    
    public void add(final int[] vals) {
        throw new UnsupportedOperationException();
    }
    
    public void add(final int[] vals, final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public int removeAt(final int offset) {
        throw new UnsupportedOperationException();
    }
    
    public void remove(final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final int value) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final int[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final int[] values, final int valOffset, final int len) {
        throw new UnsupportedOperationException();
    }
    
    public int set(final int offset, final int val) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final int[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final int[] values, final int valOffset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public int replace(final int offset, final int val) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TIntFunction function) {
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
    
    public void fill(final int val) {
        throw new UnsupportedOperationException();
    }
    
    public void fill(final int fromIndex, final int toIndex, final int val) {
        throw new UnsupportedOperationException();
    }
}
