// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TFloatFunction;
import gnu.trove.procedure.TObjectFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.TFloatCollection;
import java.util.Set;
import java.util.Map;

public interface TObjectFloatMap<K>
{
    float getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final Object p0);
    
    boolean containsValue(final float p0);
    
    float get(final Object p0);
    
    float put(final K p0, final float p1);
    
    float putIfAbsent(final K p0, final float p1);
    
    float remove(final Object p0);
    
    void putAll(final Map<? extends K, ? extends Float> p0);
    
    void putAll(final TObjectFloatMap<? extends K> p0);
    
    void clear();
    
    Set<K> keySet();
    
    Object[] keys();
    
    K[] keys(final K[] p0);
    
    TFloatCollection valueCollection();
    
    float[] values();
    
    float[] values(final float[] p0);
    
    TObjectFloatIterator<K> iterator();
    
    boolean increment(final K p0);
    
    boolean adjustValue(final K p0, final float p1);
    
    float adjustOrPutValue(final K p0, final float p1, final float p2);
    
    boolean forEachKey(final TObjectProcedure<? super K> p0);
    
    boolean forEachValue(final TFloatProcedure p0);
    
    boolean forEachEntry(final TObjectFloatProcedure<? super K> p0);
    
    void transformValues(final TFloatFunction p0);
    
    boolean retainEntries(final TObjectFloatProcedure<? super K> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
