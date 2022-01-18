// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TShortFunction;
import gnu.trove.procedure.TFloatShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.iterator.TFloatShortIterator;
import gnu.trove.TShortCollection;
import gnu.trove.set.TFloatSet;
import java.util.Map;

public interface TFloatShortMap
{
    float getNoEntryKey();
    
    short getNoEntryValue();
    
    short put(final float p0, final short p1);
    
    short putIfAbsent(final float p0, final short p1);
    
    void putAll(final Map<? extends Float, ? extends Short> p0);
    
    void putAll(final TFloatShortMap p0);
    
    short get(final float p0);
    
    void clear();
    
    boolean isEmpty();
    
    short remove(final float p0);
    
    int size();
    
    TFloatSet keySet();
    
    float[] keys();
    
    float[] keys(final float[] p0);
    
    TShortCollection valueCollection();
    
    short[] values();
    
    short[] values(final short[] p0);
    
    boolean containsValue(final short p0);
    
    boolean containsKey(final float p0);
    
    TFloatShortIterator iterator();
    
    boolean forEachKey(final TFloatProcedure p0);
    
    boolean forEachValue(final TShortProcedure p0);
    
    boolean forEachEntry(final TFloatShortProcedure p0);
    
    void transformValues(final TShortFunction p0);
    
    boolean retainEntries(final TFloatShortProcedure p0);
    
    boolean increment(final float p0);
    
    boolean adjustValue(final float p0, final short p1);
    
    short adjustOrPutValue(final float p0, final short p1, final short p2);
}
