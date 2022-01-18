// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Collection;
import gnu.trove.iterator.TLongIterator;
import gnu.trove.procedure.TLongProcedure;
import java.io.Serializable;
import gnu.trove.TLongCollection;

public class TUnmodifiableLongCollection implements TLongCollection, Serializable
{
    private static final long serialVersionUID = 1820017752578914078L;
    final TLongCollection c;
    
    public TUnmodifiableLongCollection(final TLongCollection c) {
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
    
    public boolean contains(final long o) {
        return this.c.contains(o);
    }
    
    public long[] toArray() {
        return this.c.toArray();
    }
    
    public long[] toArray(final long[] a) {
        return this.c.toArray(a);
    }
    
    public String toString() {
        return this.c.toString();
    }
    
    public long getNoEntryValue() {
        return this.c.getNoEntryValue();
    }
    
    public boolean forEach(final TLongProcedure procedure) {
        return this.c.forEach(procedure);
    }
    
    public TLongIterator iterator() {
        return new TLongIterator() {
            TLongIterator i = TUnmodifiableLongCollection.this.c.iterator();
            
            public boolean hasNext() {
                return this.i.hasNext();
            }
            
            public long next() {
                return this.i.next();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public boolean add(final long e) {
        throw new UnsupportedOperationException();
    }
    
    public boolean remove(final long o) {
        throw new UnsupportedOperationException();
    }
    
    public boolean containsAll(final Collection<?> coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final TLongCollection coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final long[] array) {
        return this.c.containsAll(array);
    }
    
    public boolean addAll(final TLongCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final Collection<? extends Long> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final long[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final TLongCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final long[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final TLongCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final long[] array) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
