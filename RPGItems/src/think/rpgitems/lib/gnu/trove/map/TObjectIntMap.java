// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.lib.gnu.trove.map;

import think.rpgitems.lib.gnu.trove.function.TIntFunction;
import think.rpgitems.lib.gnu.trove.procedure.TObjectIntProcedure;
import think.rpgitems.lib.gnu.trove.procedure.TIntProcedure;
import think.rpgitems.lib.gnu.trove.procedure.TObjectProcedure;
import think.rpgitems.lib.gnu.trove.iterator.TObjectIntIterator;
import think.rpgitems.lib.gnu.trove.TIntCollection;
import java.util.Set;
import java.util.Map;

public interface TObjectIntMap<K>
{
    int getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final Object p0);
    
    boolean containsValue(final int p0);
    
    int get(final Object p0);
    
    int put(final K p0, final int p1);
    
    int putIfAbsent(final K p0, final int p1);
    
    int remove(final Object p0);
    
    void putAll(final Map<? extends K, ? extends Integer> p0);
    
    void putAll(final TObjectIntMap<? extends K> p0);
    
    void clear();
    
    Set<K> keySet();
    
    Object[] keys();
    
    K[] keys(final K[] p0);
    
    TIntCollection valueCollection();
    
    int[] values();
    
    int[] values(final int[] p0);
    
    TObjectIntIterator<K> iterator();
    
    boolean increment(final K p0);
    
    boolean adjustValue(final K p0, final int p1);
    
    int adjustOrPutValue(final K p0, final int p1, final int p2);
    
    boolean forEachKey(final TObjectProcedure<? super K> p0);
    
    boolean forEachValue(final TIntProcedure p0);
    
    boolean forEachEntry(final TObjectIntProcedure<? super K> p0);
    
    void transformValues(final TIntFunction p0);
    
    boolean retainEntries(final TObjectIntProcedure<? super K> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
