// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.set;

import gnu.trove.procedure.TShortProcedure;
import java.util.Collection;
import gnu.trove.iterator.TShortIterator;
import gnu.trove.TShortCollection;

public interface TShortSet extends TShortCollection
{
    short getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean contains(final short p0);
    
    TShortIterator iterator();
    
    short[] toArray();
    
    short[] toArray(final short[] p0);
    
    boolean add(final short p0);
    
    boolean remove(final short p0);
    
    boolean containsAll(final Collection<?> p0);
    
    boolean containsAll(final TShortCollection p0);
    
    boolean containsAll(final short[] p0);
    
    boolean addAll(final Collection<? extends Short> p0);
    
    boolean addAll(final TShortCollection p0);
    
    boolean addAll(final short[] p0);
    
    boolean retainAll(final Collection<?> p0);
    
    boolean retainAll(final TShortCollection p0);
    
    boolean retainAll(final short[] p0);
    
    boolean removeAll(final Collection<?> p0);
    
    boolean removeAll(final TShortCollection p0);
    
    boolean removeAll(final short[] p0);
    
    void clear();
    
    boolean forEach(final TShortProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
