// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TByteFunction;
import gnu.trove.procedure.TDoubleByteProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.iterator.TDoubleByteIterator;
import gnu.trove.TByteCollection;
import gnu.trove.set.TDoubleSet;
import java.util.Map;

public interface TDoubleByteMap
{
    double getNoEntryKey();
    
    byte getNoEntryValue();
    
    byte put(final double p0, final byte p1);
    
    byte putIfAbsent(final double p0, final byte p1);
    
    void putAll(final Map<? extends Double, ? extends Byte> p0);
    
    void putAll(final TDoubleByteMap p0);
    
    byte get(final double p0);
    
    void clear();
    
    boolean isEmpty();
    
    byte remove(final double p0);
    
    int size();
    
    TDoubleSet keySet();
    
    double[] keys();
    
    double[] keys(final double[] p0);
    
    TByteCollection valueCollection();
    
    byte[] values();
    
    byte[] values(final byte[] p0);
    
    boolean containsValue(final byte p0);
    
    boolean containsKey(final double p0);
    
    TDoubleByteIterator iterator();
    
    boolean forEachKey(final TDoubleProcedure p0);
    
    boolean forEachValue(final TByteProcedure p0);
    
    boolean forEachEntry(final TDoubleByteProcedure p0);
    
    void transformValues(final TByteFunction p0);
    
    boolean retainEntries(final TDoubleByteProcedure p0);
    
    boolean increment(final double p0);
    
    boolean adjustValue(final double p0, final byte p1);
    
    byte adjustOrPutValue(final double p0, final byte p1, final byte p2);
}
