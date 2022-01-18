// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TFloatIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.iterator.TFloatIntIterator;
import gnu.trove.TIntCollection;
import gnu.trove.set.TFloatSet;
import java.util.Map;

public interface TFloatIntMap
{
    float getNoEntryKey();
    
    int getNoEntryValue();
    
    int put(final float p0, final int p1);
    
    int putIfAbsent(final float p0, final int p1);
    
    void putAll(final Map<? extends Float, ? extends Integer> p0);
    
    void putAll(final TFloatIntMap p0);
    
    int get(final float p0);
    
    void clear();
    
    boolean isEmpty();
    
    int remove(final float p0);
    
    int size();
    
    TFloatSet keySet();
    
    float[] keys();
    
    float[] keys(final float[] p0);
    
    TIntCollection valueCollection();
    
    int[] values();
    
    int[] values(final int[] p0);
    
    boolean containsValue(final int p0);
    
    boolean containsKey(final float p0);
    
    TFloatIntIterator iterator();
    
    boolean forEachKey(final TFloatProcedure p0);
    
    boolean forEachValue(final TIntProcedure p0);
    
    boolean forEachEntry(final TFloatIntProcedure p0);
    
    void transformValues(final TIntFunction p0);
    
    boolean retainEntries(final TFloatIntProcedure p0);
    
    boolean increment(final float p0);
    
    boolean adjustValue(final float p0, final int p1);
    
    int adjustOrPutValue(final float p0, final int p1, final int p2);
}
