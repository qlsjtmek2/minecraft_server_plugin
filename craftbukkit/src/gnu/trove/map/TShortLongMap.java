// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TLongFunction;
import gnu.trove.procedure.TShortLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.iterator.TShortLongIterator;
import gnu.trove.TLongCollection;
import gnu.trove.set.TShortSet;
import java.util.Map;

public interface TShortLongMap
{
    short getNoEntryKey();
    
    long getNoEntryValue();
    
    long put(final short p0, final long p1);
    
    long putIfAbsent(final short p0, final long p1);
    
    void putAll(final Map<? extends Short, ? extends Long> p0);
    
    void putAll(final TShortLongMap p0);
    
    long get(final short p0);
    
    void clear();
    
    boolean isEmpty();
    
    long remove(final short p0);
    
    int size();
    
    TShortSet keySet();
    
    short[] keys();
    
    short[] keys(final short[] p0);
    
    TLongCollection valueCollection();
    
    long[] values();
    
    long[] values(final long[] p0);
    
    boolean containsValue(final long p0);
    
    boolean containsKey(final short p0);
    
    TShortLongIterator iterator();
    
    boolean forEachKey(final TShortProcedure p0);
    
    boolean forEachValue(final TLongProcedure p0);
    
    boolean forEachEntry(final TShortLongProcedure p0);
    
    void transformValues(final TLongFunction p0);
    
    boolean retainEntries(final TShortLongProcedure p0);
    
    boolean increment(final short p0);
    
    boolean adjustValue(final short p0, final long p1);
    
    long adjustOrPutValue(final short p0, final long p1, final long p2);
}
