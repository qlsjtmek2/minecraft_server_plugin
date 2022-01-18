// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TCharFunction;
import gnu.trove.procedure.TObjectCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.iterator.TObjectCharIterator;
import gnu.trove.TCharCollection;
import java.util.Set;
import java.util.Map;

public interface TObjectCharMap<K>
{
    char getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final Object p0);
    
    boolean containsValue(final char p0);
    
    char get(final Object p0);
    
    char put(final K p0, final char p1);
    
    char putIfAbsent(final K p0, final char p1);
    
    char remove(final Object p0);
    
    void putAll(final Map<? extends K, ? extends Character> p0);
    
    void putAll(final TObjectCharMap<? extends K> p0);
    
    void clear();
    
    Set<K> keySet();
    
    Object[] keys();
    
    K[] keys(final K[] p0);
    
    TCharCollection valueCollection();
    
    char[] values();
    
    char[] values(final char[] p0);
    
    TObjectCharIterator<K> iterator();
    
    boolean increment(final K p0);
    
    boolean adjustValue(final K p0, final char p1);
    
    char adjustOrPutValue(final K p0, final char p1, final char p2);
    
    boolean forEachKey(final TObjectProcedure<? super K> p0);
    
    boolean forEachValue(final TCharProcedure p0);
    
    boolean forEachEntry(final TObjectCharProcedure<? super K> p0);
    
    void transformValues(final TCharFunction p0);
    
    boolean retainEntries(final TObjectCharProcedure<? super K> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
