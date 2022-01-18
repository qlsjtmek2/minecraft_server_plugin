// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TFloatFunction;
import gnu.trove.procedure.TLongFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.iterator.TLongFloatIterator;
import gnu.trove.TFloatCollection;
import gnu.trove.set.TLongSet;
import java.util.Map;

public interface TLongFloatMap
{
    long getNoEntryKey();
    
    float getNoEntryValue();
    
    float put(final long p0, final float p1);
    
    float putIfAbsent(final long p0, final float p1);
    
    void putAll(final Map<? extends Long, ? extends Float> p0);
    
    void putAll(final TLongFloatMap p0);
    
    float get(final long p0);
    
    void clear();
    
    boolean isEmpty();
    
    float remove(final long p0);
    
    int size();
    
    TLongSet keySet();
    
    long[] keys();
    
    long[] keys(final long[] p0);
    
    TFloatCollection valueCollection();
    
    float[] values();
    
    float[] values(final float[] p0);
    
    boolean containsValue(final float p0);
    
    boolean containsKey(final long p0);
    
    TLongFloatIterator iterator();
    
    boolean forEachKey(final TLongProcedure p0);
    
    boolean forEachValue(final TFloatProcedure p0);
    
    boolean forEachEntry(final TLongFloatProcedure p0);
    
    void transformValues(final TFloatFunction p0);
    
    boolean retainEntries(final TLongFloatProcedure p0);
    
    boolean increment(final long p0);
    
    boolean adjustValue(final long p0, final float p1);
    
    float adjustOrPutValue(final long p0, final float p1, final float p2);
}
