// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TObjectObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import java.util.Map;

public interface TMap<K, V> extends Map<K, V>
{
    V putIfAbsent(final K p0, final V p1);
    
    boolean forEachKey(final TObjectProcedure<? super K> p0);
    
    boolean forEachValue(final TObjectProcedure<? super V> p0);
    
    boolean forEachEntry(final TObjectObjectProcedure<? super K, ? super V> p0);
    
    boolean retainEntries(final TObjectObjectProcedure<? super K, ? super V> p0);
    
    void transformValues(final TObjectFunction<V, V> p0);
}
