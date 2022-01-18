// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TShortObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.iterator.TShortObjectIterator;
import java.util.Collection;
import gnu.trove.set.TShortSet;
import java.util.Map;

public interface TShortObjectMap<V>
{
    short getNoEntryKey();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final short p0);
    
    boolean containsValue(final Object p0);
    
    V get(final short p0);
    
    V put(final short p0, final V p1);
    
    V putIfAbsent(final short p0, final V p1);
    
    V remove(final short p0);
    
    void putAll(final Map<? extends Short, ? extends V> p0);
    
    void putAll(final TShortObjectMap<? extends V> p0);
    
    void clear();
    
    TShortSet keySet();
    
    short[] keys();
    
    short[] keys(final short[] p0);
    
    Collection<V> valueCollection();
    
    V[] values();
    
    V[] values(final V[] p0);
    
    TShortObjectIterator<V> iterator();
    
    boolean forEachKey(final TShortProcedure p0);
    
    boolean forEachValue(final TObjectProcedure<? super V> p0);
    
    boolean forEachEntry(final TShortObjectProcedure<? super V> p0);
    
    void transformValues(final TObjectFunction<V, V> p0);
    
    boolean retainEntries(final TShortObjectProcedure<? super V> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
