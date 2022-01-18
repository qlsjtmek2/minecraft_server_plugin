// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Random;
import gnu.trove.function.TFloatFunction;
import java.util.RandomAccess;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.TFloatCollection;
import gnu.trove.list.TFloatList;

public class TUnmodifiableFloatList extends TUnmodifiableFloatCollection implements TFloatList
{
    static final long serialVersionUID = -283967356065247728L;
    final TFloatList list;
    
    public TUnmodifiableFloatList(final TFloatList list) {
        super(list);
        this.list = list;
    }
    
    public boolean equals(final Object o) {
        return o == this || this.list.equals(o);
    }
    
    public int hashCode() {
        return this.list.hashCode();
    }
    
    public float get(final int index) {
        return this.list.get(index);
    }
    
    public int indexOf(final float o) {
        return this.list.indexOf(o);
    }
    
    public int lastIndexOf(final float o) {
        return this.list.lastIndexOf(o);
    }
    
    public float[] toArray(final int offset, final int len) {
        return this.list.toArray(offset, len);
    }
    
    public float[] toArray(final float[] dest, final int offset, final int len) {
        return this.list.toArray(dest, offset, len);
    }
    
    public float[] toArray(final float[] dest, final int source_pos, final int dest_pos, final int len) {
        return this.list.toArray(dest, source_pos, dest_pos, len);
    }
    
    public boolean forEachDescending(final TFloatProcedure procedure) {
        return this.list.forEachDescending(procedure);
    }
    
    public int binarySearch(final float value) {
        return this.list.binarySearch(value);
    }
    
    public int binarySearch(final float value, final int fromIndex, final int toIndex) {
        return this.list.binarySearch(value, fromIndex, toIndex);
    }
    
    public int indexOf(final int offset, final float value) {
        return this.list.indexOf(offset, value);
    }
    
    public int lastIndexOf(final int offset, final float value) {
        return this.list.lastIndexOf(offset, value);
    }
    
    public TFloatList grep(final TFloatProcedure condition) {
        return this.list.grep(condition);
    }
    
    public TFloatList inverseGrep(final TFloatProcedure condition) {
        return this.list.inverseGrep(condition);
    }
    
    public float max() {
        return this.list.max();
    }
    
    public float min() {
        return this.list.min();
    }
    
    public float sum() {
        return this.list.sum();
    }
    
    public TFloatList subList(final int fromIndex, final int toIndex) {
        return new TUnmodifiableFloatList(this.list.subList(fromIndex, toIndex));
    }
    
    private Object readResolve() {
        return (this.list instanceof RandomAccess) ? new TUnmodifiableRandomAccessFloatList(this.list) : this;
    }
    
    public void add(final float[] vals) {
        throw new UnsupportedOperationException();
    }
    
    public void add(final float[] vals, final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public float removeAt(final int offset) {
        throw new UnsupportedOperationException();
    }
    
    public void remove(final int offset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final float value) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final float[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void insert(final int offset, final float[] values, final int valOffset, final int len) {
        throw new UnsupportedOperationException();
    }
    
    public float set(final int offset, final float val) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final float[] values) {
        throw new UnsupportedOperationException();
    }
    
    public void set(final int offset, final float[] values, final int valOffset, final int length) {
        throw new UnsupportedOperationException();
    }
    
    public float replace(final int offset, final float val) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TFloatFunction function) {
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
    
    public void fill(final float val) {
        throw new UnsupportedOperationException();
    }
    
    public void fill(final int fromIndex, final int toIndex, final float val) {
        throw new UnsupportedOperationException();
    }
}
