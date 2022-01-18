// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TIntIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.TIntCollection;
import gnu.trove.set.TIntSet;
import java.util.Map;

public interface TIntIntMap
{
    int getNoEntryKey();
    
    int getNoEntryValue();
    
    int put(final int p0, final int p1);
    
    int putIfAbsent(final int p0, final int p1);
    
    void putAll(final Map<? extends Integer, ? extends Integer> p0);
    
    void putAll(final TIntIntMap p0);
    
    int get(final int p0);
    
    void clear();
    
    boolean isEmpty();
    
    int remove(final int p0);
    
    int size();
    
    TIntSet keySet();
    
    int[] keys();
    
    int[] keys(final int[] p0);
    
    TIntCollection valueCollection();
    
    int[] values();
    
    int[] values(final int[] p0);
    
    boolean containsValue(final int p0);
    
    boolean containsKey(final int p0);
    
    TIntIntIterator iterator();
    
    boolean forEachKey(final TIntProcedure p0);
    
    boolean forEachValue(final TIntProcedure p0);
    
    boolean forEachEntry(final TIntIntProcedure p0);
    
    void transformValues(final TIntFunction p0);
    
    boolean retainEntries(final TIntIntProcedure p0);
    
    boolean increment(final int p0);
    
    boolean adjustValue(final int p0, final int p1);
    
    int adjustOrPutValue(final int p0, final int p1, final int p2);
}
