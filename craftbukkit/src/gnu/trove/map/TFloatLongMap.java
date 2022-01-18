// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TLongFunction;
import gnu.trove.procedure.TFloatLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.iterator.TFloatLongIterator;
import gnu.trove.TLongCollection;
import gnu.trove.set.TFloatSet;
import java.util.Map;

public interface TFloatLongMap
{
    float getNoEntryKey();
    
    long getNoEntryValue();
    
    long put(final float p0, final long p1);
    
    long putIfAbsent(final float p0, final long p1);
    
    void putAll(final Map<? extends Float, ? extends Long> p0);
    
    void putAll(final TFloatLongMap p0);
    
    long get(final float p0);
    
    void clear();
    
    boolean isEmpty();
    
    long remove(final float p0);
    
    int size();
    
    TFloatSet keySet();
    
    float[] keys();
    
    float[] keys(final float[] p0);
    
    TLongCollection valueCollection();
    
    long[] values();
    
    long[] values(final long[] p0);
    
    boolean containsValue(final long p0);
    
    boolean containsKey(final float p0);
    
    TFloatLongIterator iterator();
    
    boolean forEachKey(final TFloatProcedure p0);
    
    boolean forEachValue(final TLongProcedure p0);
    
    boolean forEachEntry(final TFloatLongProcedure p0);
    
    void transformValues(final TLongFunction p0);
    
    boolean retainEntries(final TFloatLongProcedure p0);
    
    boolean increment(final float p0);
    
    boolean adjustValue(final float p0, final long p1);
    
    long adjustOrPutValue(final float p0, final long p1, final long p2);
}
