// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Random;
import gnu.trove.function.TDoubleFunction;
import java.util.RandomAccess;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.TDoubleCollection;
import gnu.trove.list.TDoubleList;

public class TUnmodifiableDoubleList extends TUnmodifiableDoubleCollection implements TDoubleList
{
    static final long serialVersionUID = -283967356065247728L;
    final TDoubleList list;
    
    public TUnmodifiableDoubleList(final TDoubleList list) {
        super(list);
        this.list = list;
    }
    
    public boolean equals(final Object o) {
        return o == this || this.list.equals(o);
    }
    
    public int hashCode() {
        return this.list.hashCode();
    }
    
    public double get(final int index) {
        return this.list.get(index);
    }
    
    public int indexOf(final double o) {
        return this.list.indexOf(o);
    }
    
    public int lastIndexOf(final double o) {
        return this.list.lastIndexOf(o);
    }
    
    public double[] toArray(final int offset, final int len) {
        return this.list.toArray(offset, len);
    }
    
    public double[] toArray(final double[] dest, final int offset, final int len) {
        return this.list.toArray(dest, offset, len);
    }
    
    public double[] toArray(final double[] dest, final int source_pos, final int dest_pos, final int len) {
        return this.list.toArray(dest, source_pos, dest_pos, len);
    }
    
    public boolean forEachDescending(final TDoubleProcedure procedure) {
        return this.list.forEachDescending(procedure);
    }
    
    public int binarySearch(final double value) {
        return this.list.binarySearch(value);
    }
    
    public int binarySearch(final double value, final int fromIndex, final int toIndex) {
        return this.list.binarySearch(value, fromIndex, toIndex);
    }
    
    public int indexOf(final int offset, final double value) {
        return this.list.indexOf(offset, value);
    }
    
    public int lastIndexOf(final int offset, final double value) {
        return this.list.lastIndexOf(offset, value);
    }
    
    public TDoubleList grep(final TDoubleProcedure condition) {
        return this.list.grep(condition);
    }
    
    public TDoubleList inverseGrep(final TDoubleProcedure condition) {
        return this.list.inverseGrep(condition);
    }
    
    public double max() {
        return this.list.max();
    }
    
    public double min() {
        return this.list.min();
    }
    
    public double sum() {
        return this.list.sum();
    }
    
    public TDoubleList subList(final int fromIndex, final int toIndex) {
        return new TUnmodifiableDoubleList(this.list.subList(fromIndex, toIndex));
    }
    
    private Object readResolve() {
        return (this.list instanceof RandomAccess) ? new TUnmodifiableRandomAccessDoubleList(this.list) : this;
    }
    
    public void add(final double[] vals) {
        throw new UnsupportedOperationException();
    }
    
    public void add(final double[] vals, final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public double removeAt(final int offset) {
        throw new UnsupportedOperationException();
    }
    
    public void remove(final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final double value) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final double[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final double[] values, final int valOffset, final int len) {
        throw new UnsupportedOperationException();
    }
    
    public double set(final int offset, final double val) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final double[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final double[] values, final int valOffset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public double replace(final int offset, final double val) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TDoubleFunction function) {
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
    
    public void fill(final double val) {
        throw new UnsupportedOperationException();
    }
    
    public void fill(final int fromIndex, final int toIndex, final double val) {
        throw new UnsupportedOperationException();
    }
}
