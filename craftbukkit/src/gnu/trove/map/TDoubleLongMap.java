// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TLongFunction;
import gnu.trove.procedure.TDoubleLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.iterator.TDoubleLongIterator;
import gnu.trove.TLongCollection;
import gnu.trove.set.TDoubleSet;
import java.util.Map;

public interface TDoubleLongMap
{
    double getNoEntryKey();
    
    long getNoEntryValue();
    
    long put(final double p0, final long p1);
    
    long putIfAbsent(final double p0, final long p1);
    
    void putAll(final Map<? extends Double, ? extends Long> p0);
    
    void putAll(final TDoubleLongMap p0);
    
    long get(final double p0);
    
    void clear();
    
    boolean isEmpty();
    
    long remove(final double p0);
    
    int size();
    
    TDoubleSet keySet();
    
    double[] keys();
    
    double[] keys(final double[] p0);
    
    TLongCollection valueCollection();
    
    long[] values();
    
    long[] values(final long[] p0);
    
    boolean containsValue(final long p0);
    
    boolean containsKey(final double p0);
    
    TDoubleLongIterator iterator();
    
    boolean forEachKey(final TDoubleProcedure p0);
    
    boolean forEachValue(final TLongProcedure p0);
    
    boolean forEachEntry(final TDoubleLongProcedure p0);
    
    void transformValues(final TLongFunction p0);
    
    boolean retainEntries(final TDoubleLongProcedure p0);
    
    boolean increment(final double p0);
    
    boolean adjustValue(final double p0, final long p1);
    
    long adjustOrPutValue(final double p0, final long p1, final long p2);
}
