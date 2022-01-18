// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TFloatFunction;
import gnu.trove.procedure.TShortFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.iterator.TShortFloatIterator;
import gnu.trove.TFloatCollection;
import gnu.trove.set.TShortSet;
import java.util.Map;

public interface TShortFloatMap
{
    short getNoEntryKey();
    
    float getNoEntryValue();
    
    float put(final short p0, final float p1);
    
    float putIfAbsent(final short p0, final float p1);
    
    void putAll(final Map<? extends Short, ? extends Float> p0);
    
    void putAll(final TShortFloatMap p0);
    
    float get(final short p0);
    
    void clear();
    
    boolean isEmpty();
    
    float remove(final short p0);
    
    int size();
    
    TShortSet keySet();
    
    short[] keys();
    
    short[] keys(final short[] p0);
    
    TFloatCollection valueCollection();
    
    float[] values();
    
    float[] values(final float[] p0);
    
    boolean containsValue(final float p0);
    
    boolean containsKey(final short p0);
    
    TShortFloatIterator iterator();
    
    boolean forEachKey(final TShortProcedure p0);
    
    boolean forEachValue(final TFloatProcedure p0);
    
    boolean forEachEntry(final TShortFloatProcedure p0);
    
    void transformValues(final TFloatFunction p0);
    
    boolean retainEntries(final TShortFloatProcedure p0);
    
    boolean increment(final short p0);
    
    boolean adjustValue(final short p0, final float p1);
    
    float adjustOrPutValue(final short p0, final float p1, final float p2);
}
