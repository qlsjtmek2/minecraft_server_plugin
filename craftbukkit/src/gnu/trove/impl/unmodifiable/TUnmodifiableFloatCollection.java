// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Collection;
import gnu.trove.iterator.TFloatIterator;
import gnu.trove.procedure.TFloatProcedure;
import java.io.Serializable;
import gnu.trove.TFloatCollection;

public class TUnmodifiableFloatCollection implements TFloatCollection, Serializable
{
    private static final long serialVersionUID = 1820017752578914078L;
    final TFloatCollection c;
    
    public TUnmodifiableFloatCollection(final TFloatCollection c) {
        if (c == null) {
            throw new NullPointerException();
        }
        this.c = c;
    }
    
    public int size() {
        return this.c.size();
    }
    
    public boolean isEmpty() {
        return this.c.isEmpty();
    }
    
    public boolean contains(final float o) {
        return this.c.contains(o);
    }
    
    public float[] toArray() {
        return this.c.toArray();
    }
    
    public float[] toArray(final float[] a) {
        return this.c.toArray(a);
    }
    
    public String toString() {
        return this.c.toString();
    }
    
    public float getNoEntryValue() {
        return this.c.getNoEntryValue();
    }
    
    public boolean forEach(final TFloatProcedure procedure) {
        return this.c.forEach(procedure);
    }
    
    public TFloatIterator iterator() {
        return new TFloatIterator() {
            TFloatIterator i = TUnmodifiableFloatCollection.this.c.iterator();
            
            public boolean hasNext() {
                return this.i.hasNext();
            }
            
            public float next() {
                return this.i.next();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public boolean add(final float e) {
        throw new UnsupportedOperationException();
    }
    
    public boolean remove(final float o) {
        throw new UnsupportedOperationException();
    }
    
    public boolean containsAll(final Collection<?> coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final TFloatCollection coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final float[] array) {
        return this.c.containsAll(array);
    }
    
    public boolean addAll(final TFloatCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final Collection<? extends Float> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final float[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final TFloatCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final float[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final TFloatCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final float[] array) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
