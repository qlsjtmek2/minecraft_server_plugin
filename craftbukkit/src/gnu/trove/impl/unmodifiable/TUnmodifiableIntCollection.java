// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Collection;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.procedure.TIntProcedure;
import java.io.Serializable;
import gnu.trove.TIntCollection;

public class TUnmodifiableIntCollection implements TIntCollection, Serializable
{
    private static final long serialVersionUID = 1820017752578914078L;
    final TIntCollection c;
    
    public TUnmodifiableIntCollection(final TIntCollection c) {
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
    
    public boolean contains(final int o) {
        return this.c.contains(o);
    }
    
    public int[] toArray() {
        return this.c.toArray();
    }
    
    public int[] toArray(final int[] a) {
        return this.c.toArray(a);
    }
    
    public String toString() {
        return this.c.toString();
    }
    
    public int getNoEntryValue() {
        return this.c.getNoEntryValue();
    }
    
    public boolean forEach(final TIntProcedure procedure) {
        return this.c.forEach(procedure);
    }
    
    public TIntIterator iterator() {
        return new TIntIterator() {
            TIntIterator i = TUnmodifiableIntCollection.this.c.iterator();
            
            public boolean hasNext() {
                return this.i.hasNext();
            }
            
            public int next() {
                return this.i.next();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public boolean add(final int e) {
        throw new UnsupportedOperationException();
    }
    
    public boolean remove(final int o) {
        throw new UnsupportedOperationException();
    }
    
    public boolean containsAll(final Collection<?> coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final TIntCollection coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final int[] array) {
        return this.c.containsAll(array);
    }
    
    public boolean addAll(final TIntCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final Collection<? extends Integer> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final int[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final TIntCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final int[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final TIntCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final int[] array) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
