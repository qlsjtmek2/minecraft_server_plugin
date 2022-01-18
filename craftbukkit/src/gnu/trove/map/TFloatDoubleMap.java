// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TDoubleFunction;
import gnu.trove.procedure.TFloatDoubleProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.iterator.TFloatDoubleIterator;
import gnu.trove.TDoubleCollection;
import gnu.trove.set.TFloatSet;
import java.util.Map;

public interface TFloatDoubleMap
{
    float getNoEntryKey();
    
    double getNoEntryValue();
    
    double put(final float p0, final double p1);
    
    double putIfAbsent(final float p0, final double p1);
    
    void putAll(final Map<? extends Float, ? extends Double> p0);
    
    void putAll(final TFloatDoubleMap p0);
    
    double get(final float p0);
    
    void clear();
    
    boolean isEmpty();
    
    double remove(final float p0);
    
    int size();
    
    TFloatSet keySet();
    
    float[] keys();
    
    float[] keys(final float[] p0);
    
    TDoubleCollection valueCollection();
    
    double[] values();
    
    double[] values(final double[] p0);
    
    boolean containsValue(final double p0);
    
    boolean containsKey(final float p0);
    
    TFloatDoubleIterator iterator();
    
    boolean forEachKey(final TFloatProcedure p0);
    
    boolean forEachValue(final TDoubleProcedure p0);
    
    boolean forEachEntry(final TFloatDoubleProcedure p0);
    
    void transformValues(final TDoubleFunction p0);
    
    boolean retainEntries(final TFloatDoubleProcedure p0);
    
    boolean increment(final float p0);
    
    boolean adjustValue(final float p0, final double p1);
    
    double adjustOrPutValue(final float p0, final double p1, final double p2);
}
