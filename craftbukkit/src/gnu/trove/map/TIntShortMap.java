// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TShortFunction;
import gnu.trove.procedure.TIntShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.iterator.TIntShortIterator;
import gnu.trove.TShortCollection;
import gnu.trove.set.TIntSet;
import java.util.Map;

public interface TIntShortMap
{
    int getNoEntryKey();
    
    short getNoEntryValue();
    
    short put(final int p0, final short p1);
    
    short putIfAbsent(final int p0, final short p1);
    
    void putAll(final Map<? extends Integer, ? extends Short> p0);
    
    void putAll(final TIntShortMap p0);
    
    short get(final int p0);
    
    void clear();
    
    boolean isEmpty();
    
    short remove(final int p0);
    
    int size();
    
    TIntSet keySet();
    
    int[] keys();
    
    int[] keys(final int[] p0);
    
    TShortCollection valueCollection();
    
    short[] values();
    
    short[] values(final short[] p0);
    
    boolean containsValue(final short p0);
    
    boolean containsKey(final int p0);
    
    TIntShortIterator iterator();
    
    boolean forEachKey(final TIntProcedure p0);
    
    boolean forEachValue(final TShortProcedure p0);
    
    boolean forEachEntry(final TIntShortProcedure p0);
    
    void transformValues(final TShortFunction p0);
    
    boolean retainEntries(final TIntShortProcedure p0);
    
    boolean increment(final int p0);
    
    boolean adjustValue(final int p0, final short p1);
    
    short adjustOrPutValue(final int p0, final short p1, final short p2);
}
