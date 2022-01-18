// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TIntObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.iterator.TIntObjectIterator;
import java.util.Collection;
import gnu.trove.set.TIntSet;
import java.util.Map;

public interface TIntObjectMap<V>
{
    int getNoEntryKey();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final int p0);
    
    boolean containsValue(final Object p0);
    
    V get(final int p0);
    
    V put(final int p0, final V p1);
    
    V putIfAbsent(final int p0, final V p1);
    
    V remove(final int p0);
    
    void putAll(final Map<? extends Integer, ? extends V> p0);
    
    void putAll(final TIntObjectMap<? extends V> p0);
    
    void clear();
    
    TIntSet keySet();
    
    int[] keys();
    
    int[] keys(final int[] p0);
    
    Collection<V> valueCollection();
    
    V[] values();
    
    V[] values(final V[] p0);
    
    TIntObjectIterator<V> iterator();
    
    boolean forEachKey(final TIntProcedure p0);
    
    boolean forEachValue(final TObjectProcedure<? super V> p0);
    
    boolean forEachEntry(final TIntObjectProcedure<? super V> p0);
    
    void transformValues(final TObjectFunction<V, V> p0);
    
    boolean retainEntries(final TIntObjectProcedure<? super V> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
