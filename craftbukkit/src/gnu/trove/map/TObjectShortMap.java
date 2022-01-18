// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TShortFunction;
import gnu.trove.procedure.TObjectShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.iterator.TObjectShortIterator;
import gnu.trove.TShortCollection;
import java.util.Set;
import java.util.Map;

public interface TObjectShortMap<K>
{
    short getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final Object p0);
    
    boolean containsValue(final short p0);
    
    short get(final Object p0);
    
    short put(final K p0, final short p1);
    
    short putIfAbsent(final K p0, final short p1);
    
    short remove(final Object p0);
    
    void putAll(final Map<? extends K, ? extends Short> p0);
    
    void putAll(final TObjectShortMap<? extends K> p0);
    
    void clear();
    
    Set<K> keySet();
    
    Object[] keys();
    
    K[] keys(final K[] p0);
    
    TShortCollection valueCollection();
    
    short[] values();
    
    short[] values(final short[] p0);
    
    TObjectShortIterator<K> iterator();
    
    boolean increment(final K p0);
    
    boolean adjustValue(final K p0, final short p1);
    
    short adjustOrPutValue(final K p0, final short p1, final short p2);
    
    boolean forEachKey(final TObjectProcedure<? super K> p0);
    
    boolean forEachValue(final TShortProcedure p0);
    
    boolean forEachEntry(final TObjectShortProcedure<? super K> p0);
    
    void transformValues(final TShortFunction p0);
    
    boolean retainEntries(final TObjectShortProcedure<? super K> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
