// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TByteFunction;
import gnu.trove.procedure.TObjectByteProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.iterator.TObjectByteIterator;
import gnu.trove.TByteCollection;
import java.util.Set;
import java.util.Map;

public interface TObjectByteMap<K>
{
    byte getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final Object p0);
    
    boolean containsValue(final byte p0);
    
    byte get(final Object p0);
    
    byte put(final K p0, final byte p1);
    
    byte putIfAbsent(final K p0, final byte p1);
    
    byte remove(final Object p0);
    
    void putAll(final Map<? extends K, ? extends Byte> p0);
    
    void putAll(final TObjectByteMap<? extends K> p0);
    
    void clear();
    
    Set<K> keySet();
    
    Object[] keys();
    
    K[] keys(final K[] p0);
    
    TByteCollection valueCollection();
    
    byte[] values();
    
    byte[] values(final byte[] p0);
    
    TObjectByteIterator<K> iterator();
    
    boolean increment(final K p0);
    
    boolean adjustValue(final K p0, final byte p1);
    
    byte adjustOrPutValue(final K p0, final byte p1, final byte p2);
    
    boolean forEachKey(final TObjectProcedure<? super K> p0);
    
    boolean forEachValue(final TByteProcedure p0);
    
    boolean forEachEntry(final TObjectByteProcedure<? super K> p0);
    
    void transformValues(final TByteFunction p0);
    
    boolean retainEntries(final TObjectByteProcedure<? super K> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
