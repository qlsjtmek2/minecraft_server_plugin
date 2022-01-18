// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Collection;
import gnu.trove.iterator.TCharIterator;
import gnu.trove.procedure.TCharProcedure;
import java.io.Serializable;
import gnu.trove.TCharCollection;

public class TUnmodifiableCharCollection implements TCharCollection, Serializable
{
    private static final long serialVersionUID = 1820017752578914078L;
    final TCharCollection c;
    
    public TUnmodifiableCharCollection(final TCharCollection c) {
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
    
    public boolean contains(final char o) {
        return this.c.contains(o);
    }
    
    public char[] toArray() {
        return this.c.toArray();
    }
    
    public char[] toArray(final char[] a) {
        return this.c.toArray(a);
    }
    
    public String toString() {
        return this.c.toString();
    }
    
    public char getNoEntryValue() {
        return this.c.getNoEntryValue();
    }
    
    public boolean forEach(final TCharProcedure procedure) {
        return this.c.forEach(procedure);
    }
    
    public TCharIterator iterator() {
        return new TCharIterator() {
            TCharIterator i = TUnmodifiableCharCollection.this.c.iterator();
            
            public boolean hasNext() {
                return this.i.hasNext();
            }
            
            public char next() {
                return this.i.next();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public boolean add(final char e) {
        throw new UnsupportedOperationException();
    }
    
    public boolean remove(final char o) {
        throw new UnsupportedOperationException();
    }
    
    public boolean containsAll(final Collection<?> coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final TCharCollection coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final char[] array) {
        return this.c.containsAll(array);
    }
    
    public boolean addAll(final TCharCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final Collection<? extends Character> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final char[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final TCharCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final char[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final TCharCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final char[] array) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
