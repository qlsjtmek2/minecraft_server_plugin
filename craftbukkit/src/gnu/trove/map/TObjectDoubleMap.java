// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TDoubleFunction;
import gnu.trove.procedure.TObjectDoubleProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.iterator.TObjectDoubleIterator;
import gnu.trove.TDoubleCollection;
import java.util.Set;
import java.util.Map;

public interface TObjectDoubleMap<K>
{
    double getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean containsKey(final Object p0);
    
    boolean containsValue(final double p0);
    
    double get(final Object p0);
    
    double put(final K p0, final double p1);
    
    double putIfAbsent(final K p0, final double p1);
    
    double remove(final Object p0);
    
    void putAll(final Map<? extends K, ? extends Double> p0);
    
    void putAll(final TObjectDoubleMap<? extends K> p0);
    
    void clear();
    
    Set<K> keySet();
    
    Object[] keys();
    
    K[] keys(final K[] p0);
    
    TDoubleCollection valueCollection();
    
    double[] values();
    
    double[] values(final double[] p0);
    
    TObjectDoubleIterator<K> iterator();
    
    boolean increment(final K p0);
    
    boolean adjustValue(final K p0, final double p1);
    
    double adjustOrPutValue(final K p0, final double p1, final double p2);
    
    boolean forEachKey(final TObjectProcedure<? super K> p0);
    
    boolean forEachValue(final TDoubleProcedure p0);
    
    boolean forEachEntry(final TObjectDoubleProcedure<? super K> p0);
    
    void transformValues(final TDoubleFunction p0);
    
    boolean retainEntries(final TObjectDoubleProcedure<? super K> p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
}
