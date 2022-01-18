// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TLongFunction;
import gnu.trove.procedure.TLongLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.iterator.TLongLongIterator;
import gnu.trove.TLongCollection;
import gnu.trove.set.TLongSet;
import java.util.Map;

public interface TLongLongMap
{
    long getNoEntryKey();
    
    long getNoEntryValue();
    
    long put(final long p0, final long p1);
    
    long putIfAbsent(final long p0, final long p1);
    
    void putAll(final Map<? extends Long, ? extends Long> p0);
    
    void putAll(final TLongLongMap p0);
    
    long get(final long p0);
    
    void clear();
    
    boolean isEmpty();
    
    long remove(final long p0);
    
    int size();
    
    TLongSet keySet();
    
    long[] keys();
    
    long[] keys(final long[] p0);
    
    TLongCollection valueCollection();
    
    long[] values();
    
    long[] values(final long[] p0);
    
    boolean containsValue(final long p0);
    
    boolean containsKey(final long p0);
    
    TLongLongIterator iterator();
    
    boolean forEachKey(final TLongProcedure p0);
    
    boolean forEachValue(final TLongProcedure p0);
    
    boolean forEachEntry(final TLongLongProcedure p0);
    
    void transformValues(final TLongFunction p0);
    
    boolean retainEntries(final TLongLongProcedure p0);
    
    boolean increment(final long p0);
    
    boolean adjustValue(final long p0, final long p1);
    
    long adjustOrPutValue(final long p0, final long p1, final long p2);
}
