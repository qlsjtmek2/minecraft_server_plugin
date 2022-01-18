// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TFloatFunction;
import gnu.trove.procedure.TDoubleFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.iterator.TDoubleFloatIterator;
import gnu.trove.TFloatCollection;
import gnu.trove.set.TDoubleSet;
import java.util.Map;

public interface TDoubleFloatMap
{
    double getNoEntryKey();
    
    float getNoEntryValue();
    
    float put(final double p0, final float p1);
    
    float putIfAbsent(final double p0, final float p1);
    
    void putAll(final Map<? extends Double, ? extends Float> p0);
    
    void putAll(final TDoubleFloatMap p0);
    
    float get(final double p0);
    
    void clear();
    
    boolean isEmpty();
    
    float remove(final double p0);
    
    int size();
    
    TDoubleSet keySet();
    
    double[] keys();
    
    double[] keys(final double[] p0);
    
    TFloatCollection valueCollection();
    
    float[] values();
    
    float[] values(final float[] p0);
    
    boolean containsValue(final float p0);
    
    boolean containsKey(final double p0);
    
    TDoubleFloatIterator iterator();
    
    boolean forEachKey(final TDoubleProcedure p0);
    
    boolean forEachValue(final TFloatProcedure p0);
    
    boolean forEachEntry(final TDoubleFloatProcedure p0);
    
    void transformValues(final TFloatFunction p0);
    
    boolean retainEntries(final TDoubleFloatProcedure p0);
    
    boolean increment(final double p0);
    
    boolean adjustValue(final double p0, final float p1);
    
    float adjustOrPutValue(final double p0, final float p1, final float p2);
}
