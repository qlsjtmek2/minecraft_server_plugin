// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TLongIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.iterator.TLongIntIterator;
import gnu.trove.TIntCollection;
import gnu.trove.set.TLongSet;
import java.util.Map;

public interface TLongIntMap
{
    long getNoEntryKey();
    
    int getNoEntryValue();
    
    int put(final long p0, final int p1);
    
    int putIfAbsent(final long p0, final int p1);
    
    void putAll(final Map<? extends Long, ? extends Integer> p0);
    
    void putAll(final TLongIntMap p0);
    
    int get(final long p0);
    
    void clear();
    
    boolean isEmpty();
    
    int remove(final long p0);
    
    int size();
    
    TLongSet keySet();
    
    long[] keys();
    
    long[] keys(final long[] p0);
    
    TIntCollection valueCollection();
    
    int[] values();
    
    int[] values(final int[] p0);
    
    boolean containsValue(final int p0);
    
    boolean containsKey(final long p0);
    
    TLongIntIterator iterator();
    
    boolean forEachKey(final TLongProcedure p0);
    
    boolean forEachValue(final TIntProcedure p0);
    
    boolean forEachEntry(final TLongIntProcedure p0);
    
    void transformValues(final TIntFunction p0);
    
    boolean retainEntries(final TLongIntProcedure p0);
    
    boolean increment(final long p0);
    
    boolean adjustValue(final long p0, final int p1);
    
    int adjustOrPutValue(final long p0, final int p1, final int p2);
}
