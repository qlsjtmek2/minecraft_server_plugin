// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TFloatFunction;
import gnu.trove.procedure.TFloatFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.iterator.TFloatFloatIterator;
import gnu.trove.TFloatCollection;
import gnu.trove.set.TFloatSet;
import java.util.Map;

public interface TFloatFloatMap
{
    float getNoEntryKey();
    
    float getNoEntryValue();
    
    float put(final float p0, final float p1);
    
    float putIfAbsent(final float p0, final float p1);
    
    void putAll(final Map<? extends Float, ? extends Float> p0);
    
    void putAll(final TFloatFloatMap p0);
    
    float get(final float p0);
    
    void clear();
    
    boolean isEmpty();
    
    float remove(final float p0);
    
    int size();
    
    TFloatSet keySet();
    
    float[] keys();
    
    float[] keys(final float[] p0);
    
    TFloatCollection valueCollection();
    
    float[] values();
    
    float[] values(final float[] p0);
    
    boolean containsValue(final float p0);
    
    boolean containsKey(final float p0);
    
    TFloatFloatIterator iterator();
    
    boolean forEachKey(final TFloatProcedure p0);
    
    boolean forEachValue(final TFloatProcedure p0);
    
    boolean forEachEntry(final TFloatFloatProcedure p0);
    
    void transformValues(final TFloatFunction p0);
    
    boolean retainEntries(final TFloatFloatProcedure p0);
    
    boolean increment(final float p0);
    
    boolean adjustValue(final float p0, final float p1);
    
    float adjustOrPutValue(final float p0, final float p1, final float p2);
}
