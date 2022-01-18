// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TFloatObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.iterator.TFloatObjectIterator;
import java.util.Collection;
import gnu.trove.set.TFloatSet;
import java.util.Map;

public interface TFloatObjectMap<V>
{
    float getNoEntryKey();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final float p0);
    
    boolean containsValue(final Object p0);
    
    V get(final float p0);
    
    V put(final float p0, final V p1);
    
    V putIfAbsent(final float p0, final V p1);
    
    V remove(final float p0);
    
    void putAll(final Map<? extends Float, ? extends V> p0);
    
    void putAll(final TFloatObjectMap<? extends V> p0);
    
    void clear();
    
    TFloatSet keySet();
    
    float[] keys();
    
    float[] keys(final float[] p0);
    
    Collection<V> valueCollection();
    
    V[] values();
    
    V[] values(final V[] p0);
    
    TFloatObjectIterator<V> iterator();
    
    boolean forEachKey(final TFloatProcedure p0);
    
    boolean forEachValue(final TObjectProcedure<? super V> p0);
    
    boolean forEachEntry(final TFloatObjectProcedure<? super V> p0);
    
    void transformValues(final TObjectFunction<V, V> p0);
    
    boolean retainEntries(final TFloatObjectProcedure<? super V> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
