// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TLongFunction;
import gnu.trove.procedure.TIntLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.iterator.TIntLongIterator;
import gnu.trove.TLongCollection;
import gnu.trove.set.TIntSet;
import java.util.Map;

public interface TIntLongMap
{
    int getNoEntryKey();
    
    long getNoEntryValue();
    
    long put(final int p0, final long p1);
    
    long putIfAbsent(final int p0, final long p1);
    
    void putAll(final Map<? extends Integer, ? extends Long> p0);
    
    void putAll(final TIntLongMap p0);
    
    long get(final int p0);
    
    void clear();
    
    boolean isEmpty();
    
    long remove(final int p0);
    
    int size();
    
    TIntSet keySet();
    
    int[] keys();
    
    int[] keys(final int[] p0);
    
    TLongCollection valueCollection();
    
    long[] values();
    
    long[] values(final long[] p0);
    
    boolean containsValue(final long p0);
    
    boolean containsKey(final int p0);
    
    TIntLongIterator iterator();
    
    boolean forEachKey(final TIntProcedure p0);
    
    boolean forEachValue(final TLongProcedure p0);
    
    boolean forEachEntry(final TIntLongProcedure p0);
    
    void transformValues(final TLongFunction p0);
    
    boolean retainEntries(final TIntLongProcedure p0);
    
    boolean increment(final int p0);
    
    boolean adjustValue(final int p0, final long p1);
    
    long adjustOrPutValue(final int p0, final long p1, final long p2);
}
