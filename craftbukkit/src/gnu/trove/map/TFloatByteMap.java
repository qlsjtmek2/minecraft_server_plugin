// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TByteFunction;
import gnu.trove.procedure.TFloatByteProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.iterator.TFloatByteIterator;
import gnu.trove.TByteCollection;
import gnu.trove.set.TFloatSet;
import java.util.Map;

public interface TFloatByteMap
{
    float getNoEntryKey();
    
    byte getNoEntryValue();
    
    byte put(final float p0, final byte p1);
    
    byte putIfAbsent(final float p0, final byte p1);
    
    void putAll(final Map<? extends Float, ? extends Byte> p0);
    
    void putAll(final TFloatByteMap p0);
    
    byte get(final float p0);
    
    void clear();
    
    boolean isEmpty();
    
    byte remove(final float p0);
    
    int size();
    
    TFloatSet keySet();
    
    float[] keys();
    
    float[] keys(final float[] p0);
    
    TByteCollection valueCollection();
    
    byte[] values();
    
    byte[] values(final byte[] p0);
    
    boolean containsValue(final byte p0);
    
    boolean containsKey(final float p0);
    
    TFloatByteIterator iterator();
    
    boolean forEachKey(final TFloatProcedure p0);
    
    boolean forEachValue(final TByteProcedure p0);
    
    boolean forEachEntry(final TFloatByteProcedure p0);
    
    void transformValues(final TByteFunction p0);
    
    boolean retainEntries(final TFloatByteProcedure p0);
    
    boolean increment(final float p0);
    
    boolean adjustValue(final float p0, final byte p1);
    
    byte adjustOrPutValue(final float p0, final byte p1, final byte p2);
}
