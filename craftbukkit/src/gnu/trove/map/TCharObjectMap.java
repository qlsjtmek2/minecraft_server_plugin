// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TCharObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.iterator.TCharObjectIterator;
import java.util.Collection;
import gnu.trove.set.TCharSet;
import java.util.Map;

public interface TCharObjectMap<V>
{
    char getNoEntryKey();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final char p0);
    
    boolean containsValue(final Object p0);
    
    V get(final char p0);
    
    V put(final char p0, final V p1);
    
    V putIfAbsent(final char p0, final V p1);
    
    V remove(final char p0);
    
    void putAll(final Map<? extends Character, ? extends V> p0);
    
    void putAll(final TCharObjectMap<? extends V> p0);
    
    void clear();
    
    TCharSet keySet();
    
    char[] keys();
    
    char[] keys(final char[] p0);
    
    Collection<V> valueCollection();
    
    V[] values();
    
    V[] values(final V[] p0);
    
    TCharObjectIterator<V> iterator();
    
    boolean forEachKey(final TCharProcedure p0);
    
    boolean forEachValue(final TObjectProcedure<? super V> p0);
    
    boolean forEachEntry(final TCharObjectProcedure<? super V> p0);
    
    void transformValues(final TObjectFunction<V, V> p0);
    
    boolean retainEntries(final TCharObjectProcedure<? super V> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
