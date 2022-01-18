// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Collection;
import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.procedure.TDoubleProcedure;
import java.io.Serializable;
import gnu.trove.TDoubleCollection;

public class TUnmodifiableDoubleCollection implements TDoubleCollection, Serializable
{
    private static final long serialVersionUID = 1820017752578914078L;
    final TDoubleCollection c;
    
    public TUnmodifiableDoubleCollection(final TDoubleCollection c) {
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
    
    public boolean contains(final double o) {
        return this.c.contains(o);
    }
    
    public double[] toArray() {
        return this.c.toArray();
    }
    
    public double[] toArray(final double[] a) {
        return this.c.toArray(a);
    }
    
    public String toString() {
        return this.c.toString();
    }
    
    public double getNoEntryValue() {
        return this.c.getNoEntryValue();
    }
    
    public boolean forEach(final TDoubleProcedure procedure) {
        return this.c.forEach(procedure);
    }
    
    public TDoubleIterator iterator() {
        return new TDoubleIterator() {
            TDoubleIterator i = TUnmodifiableDoubleCollection.this.c.iterator();
            
            public boolean hasNext() {
                return this.i.hasNext();
            }
            
            public double next() {
                return this.i.next();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public boolean add(final double e) {
        throw new UnsupportedOperationException();
    }
    
    public boolean remove(final double o) {
        throw new UnsupportedOperationException();
    }
    
    public boolean containsAll(final Collection<?> coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final TDoubleCollection coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final double[] array) {
        return this.c.containsAll(array);
    }
    
    public boolean addAll(final TDoubleCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final Collection<? extends Double> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final double[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final TDoubleCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final double[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final TDoubleCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final double[] array) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
