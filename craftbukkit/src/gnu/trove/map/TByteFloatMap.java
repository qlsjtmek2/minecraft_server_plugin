// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TFloatFunction;
import gnu.trove.procedure.TByteFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.iterator.TByteFloatIterator;
import gnu.trove.TFloatCollection;
import gnu.trove.set.TByteSet;
import java.util.Map;

public interface TByteFloatMap
{
    byte getNoEntryKey();
    
    float getNoEntryValue();
    
    float put(final byte p0, final float p1);
    
    float putIfAbsent(final byte p0, final float p1);
    
    void putAll(final Map<? extends Byte, ? extends Float> p0);
    
    void putAll(final TByteFloatMap p0);
    
    float get(final byte p0);
    
    void clear();
    
    boolean isEmpty();
    
    float remove(final byte p0);
    
    int size();
    
    TByteSet keySet();
    
    byte[] keys();
    
    byte[] keys(final byte[] p0);
    
    TFloatCollection valueCollection();
    
    float[] values();
    
    float[] values(final float[] p0);
    
    boolean containsValue(final float p0);
    
    boolean containsKey(final byte p0);
    
    TByteFloatIterator iterator();
    
    boolean forEachKey(final TByteProcedure p0);
    
    boolean forEachValue(final TFloatProcedure p0);
    
    boolean forEachEntry(final TByteFloatProcedure p0);
    
    void transformValues(final TFloatFunction p0);
    
    boolean retainEntries(final TByteFloatProcedure p0);
    
    boolean increment(final byte p0);
    
    boolean adjustValue(final byte p0, final float p1);
    
    float adjustOrPutValue(final byte p0, final float p1, final float p2);
}
