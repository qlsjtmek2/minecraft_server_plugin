// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.set;

import gnu.trove.procedure.TFloatProcedure;
import java.util.Collection;
import gnu.trove.iterator.TFloatIterator;
import gnu.trove.TFloatCollection;

public interface TFloatSet extends TFloatCollection
{
    float getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean contains(final float p0);
    
    TFloatIterator iterator();
    
    float[] toArray();
    
    float[] toArray(final float[] p0);
    
    boolean add(final float p0);
    
    boolean remove(final float p0);
    
    boolean containsAll(final Collection<?> p0);
    
    boolean containsAll(final TFloatCollection p0);
    
    boolean containsAll(final float[] p0);
    
    boolean addAll(final Collection<? extends Float> p0);
    
    boolean addAll(final TFloatCollection p0);
    
    boolean addAll(final float[] p0);
    
    boolean retainAll(final Collection<?> p0);
    
    boolean retainAll(final TFloatCollection p0);
    
    boolean retainAll(final float[] p0);
    
    boolean removeAll(final Collection<?> p0);
    
    boolean removeAll(final TFloatCollection p0);
    
    boolean removeAll(final float[] p0);
    
    void clear();
    
    boolean forEach(final TFloatProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
