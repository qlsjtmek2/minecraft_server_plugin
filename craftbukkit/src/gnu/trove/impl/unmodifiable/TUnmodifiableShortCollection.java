// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Collection;
import gnu.trove.iterator.TShortIterator;
import gnu.trove.procedure.TShortProcedure;
import java.io.Serializable;
import gnu.trove.TShortCollection;

public class TUnmodifiableShortCollection implements TShortCollection, Serializable
{
    private static final long serialVersionUID = 1820017752578914078L;
    final TShortCollection c;
    
    public TUnmodifiableShortCollection(final TShortCollection c) {
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
    
    public boolean contains(final short o) {
        return this.c.contains(o);
    }
    
    public short[] toArray() {
        return this.c.toArray();
    }
    
    public short[] toArray(final short[] a) {
        return this.c.toArray(a);
    }
    
    public String toString() {
        return this.c.toString();
    }
    
    public short getNoEntryValue() {
        return this.c.getNoEntryValue();
    }
    
    public boolean forEach(final TShortProcedure procedure) {
        return this.c.forEach(procedure);
    }
    
    public TShortIterator iterator() {
        return new TShortIterator() {
            TShortIterator i = TUnmodifiableShortCollection.this.c.iterator();
            
            public boolean hasNext() {
                return this.i.hasNext();
            }
            
            public short next() {
                return this.i.next();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public boolean add(final short e) {
        throw new UnsupportedOperationException();
    }
    
    public boolean remove(final short o) {
        throw new UnsupportedOperationException();
    }
    
    public boolean containsAll(final Collection<?> coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final TShortCollection coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final short[] array) {
        return this.c.containsAll(array);
    }
    
    public boolean addAll(final TShortCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final Collection<? extends Short> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final short[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final TShortCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final short[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final TShortCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final short[] array) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
