// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import java.util.Collection;
import gnu.trove.iterator.TByteIterator;
import gnu.trove.procedure.TByteProcedure;
import java.io.Serializable;
import gnu.trove.TByteCollection;

public class TUnmodifiableByteCollection implements TByteCollection, Serializable
{
    private static final long serialVersionUID = 1820017752578914078L;
    final TByteCollection c;
    
    public TUnmodifiableByteCollection(final TByteCollection c) {
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
    
    public boolean contains(final byte o) {
        return this.c.contains(o);
    }
    
    public byte[] toArray() {
        return this.c.toArray();
    }
    
    public byte[] toArray(final byte[] a) {
        return this.c.toArray(a);
    }
    
    public String toString() {
        return this.c.toString();
    }
    
    public byte getNoEntryValue() {
        return this.c.getNoEntryValue();
    }
    
    public boolean forEach(final TByteProcedure procedure) {
        return this.c.forEach(procedure);
    }
    
    public TByteIterator iterator() {
        return new TByteIterator() {
            TByteIterator i = TUnmodifiableByteCollection.this.c.iterator();
            
            public boolean hasNext() {
                return this.i.hasNext();
            }
            
            public byte next() {
                return this.i.next();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public boolean add(final byte e) {
        throw new UnsupportedOperationException();
    }
    
    public boolean remove(final byte o) {
        throw new UnsupportedOperationException();
    }
    
    public boolean containsAll(final Collection<?> coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final TByteCollection coll) {
        return this.c.containsAll(coll);
    }
    
    public boolean containsAll(final byte[] array) {
        return this.c.containsAll(array);
    }
    
    public boolean addAll(final TByteCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final Collection<? extends Byte> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean addAll(final byte[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final TByteCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final byte[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final TByteCollection coll) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(final byte[] array) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
