// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove;

import gnu.trove.procedure.TIntProcedure;
import java.util.Collection;
import gnu.trove.iterator.TIntIterator;

public interface TIntCollection
{
    public static final long serialVersionUID = 1L;
    
    int getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean contains(final int p0);
    
    TIntIterator iterator();
    
    int[] toArray();
    
    int[] toArray(final int[] p0);
    
    boolean add(final int p0);
    
    boolean remove(final int p0);
    
    boolean containsAll(final Collection<?> p0);
    
    boolean containsAll(final TIntCollection p0);
    
    boolean containsAll(final int[] p0);
    
    boolean addAll(final Collection<? extends Integer> p0);
    
    boolean addAll(final TIntCollection p0);
    
    boolean addAll(final int[] p0);
    
    boolean retainAll(final Collection<?> p0);
    
    boolean retainAll(final TIntCollection p0);
    
    boolean retainAll(final int[] p0);
    
    boolean removeAll(final Collection<?> p0);
    
    boolean removeAll(final TIntCollection p0);
    
    boolean removeAll(final int[] p0);
    
    void clear();
    
    boolean forEach(final TIntProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
