// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TShortFunction;
import gnu.trove.procedure.TShortShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.iterator.TShortShortIterator;
import gnu.trove.TShortCollection;
import gnu.trove.set.TShortSet;
import java.util.Map;

public interface TShortShortMap
{
    short getNoEntryKey();
    
    short getNoEntryValue();
    
    short put(final short p0, final short p1);
    
    short putIfAbsent(final short p0, final short p1);
    
    void putAll(final Map<? extends Short, ? extends Short> p0);
    
    void putAll(final TShortShortMap p0);
    
    short get(final short p0);
    
    void clear();
    
    boolean isEmpty();
    
    short remove(final short p0);
    
    int size();
    
    TShortSet keySet();
    
    short[] keys();
    
    short[] keys(final short[] p0);
    
    TShortCollection valueCollection();
    
    short[] values();
    
    short[] values(final short[] p0);
    
    boolean containsValue(final short p0);
    
    boolean containsKey(final short p0);
    
    TShortShortIterator iterator();
    
    boolean forEachKey(final TShortProcedure p0);
    
    boolean forEachValue(final TShortProcedure p0);
    
    boolean forEachEntry(final TShortShortProcedure p0);
    
    void transformValues(final TShortFunction p0);
    
    boolean retainEntries(final TShortShortProcedure p0);
    
    boolean increment(final short p0);
    
    boolean adjustValue(final short p0, final short p1);
    
    short adjustOrPutValue(final short p0, final short p1, final short p2);
}
