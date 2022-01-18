// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TDoubleObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.iterator.TDoubleObjectIterator;
import java.util.Collection;
import gnu.trove.set.TDoubleSet;
import java.util.Map;

public interface TDoubleObjectMap<V>
{
    double getNoEntryKey();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final double p0);
    
    boolean containsValue(final Object p0);
    
    V get(final double p0);
    
    V put(final double p0, final V p1);
    
    V putIfAbsent(final double p0, final V p1);
    
    V remove(final double p0);
    
    void putAll(final Map<? extends Double, ? extends V> p0);
    
    void putAll(final TDoubleObjectMap<? extends V> p0);
    
    void clear();
    
    TDoubleSet keySet();
    
    double[] keys();
    
    double[] keys(final double[] p0);
    
    Collection<V> valueCollection();
    
    V[] values();
    
    V[] values(final V[] p0);
    
    TDoubleObjectIterator<V> iterator();
    
    boolean forEachKey(final TDoubleProcedure p0);
    
    boolean forEachValue(final TObjectProcedure<? super V> p0);
    
    boolean forEachEntry(final TDoubleObjectProcedure<? super V> p0);
    
    void transformValues(final TObjectFunction<V, V> p0);
    
    boolean retainEntries(final TDoubleObjectProcedure<? super V> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
