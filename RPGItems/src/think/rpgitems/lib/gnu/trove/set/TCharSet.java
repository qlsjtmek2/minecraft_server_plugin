// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.lib.gnu.trove.set;

import think.rpgitems.lib.gnu.trove.procedure.TCharProcedure;
import java.util.Collection;
import think.rpgitems.lib.gnu.trove.iterator.TCharIterator;
import think.rpgitems.lib.gnu.trove.TCharCollection;

public interface TCharSet extends TCharCollection
{
    char getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean contains(final char p0);
    
    TCharIterator iterator();
    
    char[] toArray();
    
    char[] toArray(final char[] p0);
    
    boolean add(final char p0);
    
    boolean remove(final char p0);
    
    boolean containsAll(final Collection<?> p0);
    
    boolean containsAll(final TCharCollection p0);
    
    boolean containsAll(final char[] p0);
    
    boolean addAll(final Collection<? extends Character> p0);
    
    boolean addAll(final TCharCollection p0);
    
    boolean addAll(final char[] p0);
    
    boolean retainAll(final Collection<?> p0);
    
    boolean retainAll(final TCharCollection p0);
    
    boolean retainAll(final char[] p0);
    
    boolean removeAll(final Collection<?> p0);
    
    boolean removeAll(final TCharCollection p0);
    
    boolean removeAll(final char[] p0);
    
    void clear();
    
    boolean forEach(final TCharProcedure p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
