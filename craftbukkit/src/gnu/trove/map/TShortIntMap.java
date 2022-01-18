// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TShortIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.iterator.TShortIntIterator;
import gnu.trove.TIntCollection;
import gnu.trove.set.TShortSet;
import java.util.Map;

public interface TShortIntMap
{
    short getNoEntryKey();
    
    int getNoEntryValue();
    
    int put(final short p0, final int p1);
    
    int putIfAbsent(final short p0, final int p1);
    
    void putAll(final Map<? extends Short, ? extends Integer> p0);
    
    void putAll(final TShortIntMap p0);
    
    int get(final short p0);
    
    void clear();
    
    boolean isEmpty();
    
    int remove(final short p0);
    
    int size();
    
    TShortSet keySet();
    
    short[] keys();
    
    short[] keys(final short[] p0);
    
    TIntCollection valueCollection();
    
    int[] values();
    
    int[] values(final int[] p0);
    
    boolean containsValue(final int p0);
    
    boolean containsKey(final short p0);
    
    TShortIntIterator iterator();
    
    boolean forEachKey(final TShortProcedure p0);
    
    boolean forEachValue(final TIntProcedure p0);
    
    boolean forEachEntry(final TShortIntProcedure p0);
    
    void transformValues(final TIntFunction p0);
    
    boolean retainEntries(final TShortIntProcedure p0);
    
    boolean increment(final short p0);
    
    boolean adjustValue(final short p0, final int p1);
    
    int adjustOrPutValue(final short p0, final int p1, final int p2);
}
