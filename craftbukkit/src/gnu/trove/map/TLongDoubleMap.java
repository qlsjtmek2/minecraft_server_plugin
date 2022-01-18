// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TDoubleFunction;
import gnu.trove.procedure.TLongDoubleProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.iterator.TLongDoubleIterator;
import gnu.trove.TDoubleCollection;
import gnu.trove.set.TLongSet;
import java.util.Map;

public interface TLongDoubleMap
{
    long getNoEntryKey();
    
    double getNoEntryValue();
    
    double put(final long p0, final double p1);
    
    double putIfAbsent(final long p0, final double p1);
    
    void putAll(final Map<? extends Long, ? extends Double> p0);
    
    void putAll(final TLongDoubleMap p0);
    
    double get(final long p0);
    
    void clear();
    
    boolean isEmpty();
    
    double remove(final long p0);
    
    int size();
    
    TLongSet keySet();
    
    long[] keys();
    
    long[] keys(final long[] p0);
    
    TDoubleCollection valueCollection();
    
    double[] values();
    
    double[] values(final double[] p0);
    
    boolean containsValue(final double p0);
    
    boolean containsKey(final long p0);
    
    TLongDoubleIterator iterator();
    
    boolean forEachKey(final TLongProcedure p0);
    
    boolean forEachValue(final TDoubleProcedure p0);
    
    boolean forEachEntry(final TLongDoubleProcedure p0);
    
    void transformValues(final TDoubleFunction p0);
    
    boolean retainEntries(final TLongDoubleProcedure p0);
    
    boolean increment(final long p0);
    
    boolean adjustValue(final long p0, final double p1);
    
    double adjustOrPutValue(final long p0, final double p1, final double p2);
}
