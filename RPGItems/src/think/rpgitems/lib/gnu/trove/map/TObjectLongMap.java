// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.lib.gnu.trove.map;

import think.rpgitems.lib.gnu.trove.function.TLongFunction;
import think.rpgitems.lib.gnu.trove.procedure.TObjectLongProcedure;
import think.rpgitems.lib.gnu.trove.procedure.TLongProcedure;
import think.rpgitems.lib.gnu.trove.procedure.TObjectProcedure;
import think.rpgitems.lib.gnu.trove.iterator.TObjectLongIterator;
import think.rpgitems.lib.gnu.trove.TLongCollection;
import java.util.Set;
import java.util.Map;

public interface TObjectLongMap<K>
{
    long getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final Object p0);
    
    boolean containsValue(final long p0);
    
    long get(final Object p0);
    
    long put(final K p0, final long p1);
    
    long putIfAbsent(final K p0, final long p1);
    
    long remove(final Object p0);
    
    void putAll(final Map<? extends K, ? extends Long> p0);
    
    void putAll(final TObjectLongMap<? extends K> p0);
    
    void clear();
    
    Set<K> keySet();
    
    Object[] keys();
    
    K[] keys(final K[] p0);
    
    TLongCollection valueCollection();
    
    long[] values();
    
    long[] values(final long[] p0);
    
    TObjectLongIterator<K> iterator();
    
    boolean increment(final K p0);
    
    boolean adjustValue(final K p0, final long p1);
    
    long adjustOrPutValue(final K p0, final long p1, final long p2);
    
    boolean forEachKey(final TObjectProcedure<? super K> p0);
    
    boolean forEachValue(final TLongProcedure p0);
    
    boolean forEachEntry(final TObjectLongProcedure<? super K> p0);
    
    void transformValues(final TLongFunction p0);
    
    boolean retainEntries(final TObjectLongProcedure<? super K> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
