// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TLongObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.iterator.TLongObjectIterator;
import java.util.Collection;
import gnu.trove.set.TLongSet;
import java.util.Map;

public interface TLongObjectMap<V>
{
    long getNoEntryKey();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final long p0);
    
    boolean containsValue(final Object p0);
    
    V get(final long p0);
    
    V put(final long p0, final V p1);
    
    V putIfAbsent(final long p0, final V p1);
    
    V remove(final long p0);
    
    void putAll(final Map<? extends Long, ? extends V> p0);
    
    void putAll(final TLongObjectMap<? extends V> p0);
    
    void clear();
    
    TLongSet keySet();
    
    long[] keys();
    
    long[] keys(final long[] p0);
    
    Collection<V> valueCollection();
    
    V[] values();
    
    V[] values(final V[] p0);
    
    TLongObjectIterator<V> iterator();
    
    boolean forEachKey(final TLongProcedure p0);
    
    boolean forEachValue(final TObjectProcedure<? super V> p0);
    
    boolean forEachEntry(final TLongObjectProcedure<? super V> p0);
    
    void transformValues(final TObjectFunction<V, V> p0);
    
    boolean retainEntries(final TLongObjectProcedure<? super V> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
