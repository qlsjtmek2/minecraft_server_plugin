// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TByteObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.iterator.TByteObjectIterator;
import java.util.Collection;
import gnu.trove.set.TByteSet;
import java.util.Map;

public interface TByteObjectMap<V>
{
    byte getNoEntryKey();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final byte p0);
    
    boolean containsValue(final Object p0);
    
    V get(final byte p0);
    
    V put(final byte p0, final V p1);
    
    V putIfAbsent(final byte p0, final V p1);
    
    V remove(final byte p0);
    
    void putAll(final Map<? extends Byte, ? extends V> p0);
    
    void putAll(final TByteObjectMap<? extends V> p0);
    
    void clear();
    
    TByteSet keySet();
    
    byte[] keys();
    
    byte[] keys(final byte[] p0);
    
    Collection<V> valueCollection();
    
    V[] values();
    
    V[] values(final V[] p0);
    
    TByteObjectIterator<V> iterator();
    
    boolean forEachKey(final TByteProcedure p0);
    
    boolean forEachValue(final TObjectProcedure<? super V> p0);
    
    boolean forEachEntry(final TByteObjectProcedure<? super V> p0);
    
    void transformValues(final TObjectFunction<V, V> p0);
    
    boolean retainEntries(final TByteObjectProcedure<? super V> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
