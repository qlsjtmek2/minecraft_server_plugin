// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TFloatFunction;
import gnu.trove.procedure.TIntFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.iterator.TIntFloatIterator;
import gnu.trove.TFloatCollection;
import gnu.trove.set.TIntSet;
import java.util.Map;

public interface TIntFloatMap
{
    int getNoEntryKey();
    
    float getNoEntryValue();
    
    float put(final int p0, final float p1);
    
    float putIfAbsent(final int p0, final float p1);
    
    void putAll(final Map<? extends Integer, ? extends Float> p0);
    
    void putAll(final TIntFloatMap p0);
    
    float get(final int p0);
    
    void clear();
    
    boolean isEmpty();
    
    float remove(final int p0);
    
    int size();
    
    TIntSet keySet();
    
    int[] keys();
    
    int[] keys(final int[] p0);
    
    TFloatCollection valueCollection();
    
    float[] values();
    
    float[] values(final float[] p0);
    
    boolean containsValue(final float p0);
    
    boolean containsKey(final int p0);
    
    TIntFloatIterator iterator();
    
    boolean forEachKey(final TIntProcedure p0);
    
    boolean forEachValue(final TFloatProcedure p0);
    
    boolean forEachEntry(final TIntFloatProcedure p0);
    
    void transformValues(final TFloatFunction p0);
    
    boolean retainEntries(final TIntFloatProcedure p0);
    
    boolean increment(final int p0);
    
    boolean adjustValue(final int p0, final float p1);
    
    float adjustOrPutValue(final int p0, final float p1, final float p2);
}
