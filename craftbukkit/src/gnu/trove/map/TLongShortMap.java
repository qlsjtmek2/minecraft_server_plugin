// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TShortFunction;
import gnu.trove.procedure.TLongShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.iterator.TLongShortIterator;
import gnu.trove.TShortCollection;
import gnu.trove.set.TLongSet;
import java.util.Map;

public interface TLongShortMap
{
    long getNoEntryKey();
    
    short getNoEntryValue();
    
    short put(final long p0, final short p1);
    
    short putIfAbsent(final long p0, final short p1);
    
    void putAll(final Map<? extends Long, ? extends Short> p0);
    
    void putAll(final TLongShortMap p0);
    
    short get(final long p0);
    
    void clear();
    
    boolean isEmpty();
    
    short remove(final long p0);
    
    int size();
    
    TLongSet keySet();
    
    long[] keys();
    
    long[] keys(final long[] p0);
    
    TShortCollection valueCollection();
    
    short[] values();
    
    short[] values(final short[] p0);
    
    boolean containsValue(final short p0);
    
    boolean containsKey(final long p0);
    
    TLongShortIterator iterator();
    
    boolean forEachKey(final TLongProcedure p0);
    
    boolean forEachValue(final TShortProcedure p0);
    
    boolean forEachEntry(final TLongShortProcedure p0);
    
    void transformValues(final TShortFunction p0);
    
    boolean retainEntries(final TLongShortProcedure p0);
    
    boolean increment(final long p0);
    
    boolean adjustValue(final long p0, final short p1);
    
    short adjustOrPutValue(final long p0, final short p1, final short p2);
}
