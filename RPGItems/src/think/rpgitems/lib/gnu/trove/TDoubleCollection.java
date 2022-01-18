// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.lib.gnu.trove;

import think.rpgitems.lib.gnu.trove.procedure.TDoubleProcedure;
import java.util.Collection;
import think.rpgitems.lib.gnu.trove.iterator.TDoubleIterator;

public interface TDoubleCollection
{
    public static final long serialVersionUID = 1L;
    
    double getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean contains(final double p0);
    
    TDoubleIterator iterator();
    
    double[] toArray();
    
    double[] toArray(final double[] p0);
    
    boolean add(final double p0);
    
    boolean remove(final double p0);
    
    boolean containsAll(final Collection<?> p0);
    
    boolean containsAll(final TDoubleCollection p0);
    
    boolean containsAll(final double[] p0);
    
    boolean addAll(final Collection<? extends Double> p0);
    
    boolean addAll(final TDoubleCollection p0);
    
    boolean addAll(final double[] p0);
    
    boolean retainAll(final Collection<?> p0);
    
    boolean retainAll(final TDoubleCollection p0);
    
    boolean retainAll(final double[] p0);
    
    boolean removeAll(final Collection<?> p0);
    
    boolean removeAll(final TDoubleCollection p0);
    
    boolean removeAll(final double[] p0);
    
    void clear();
    
    boolean forEach(final TDoubleProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
