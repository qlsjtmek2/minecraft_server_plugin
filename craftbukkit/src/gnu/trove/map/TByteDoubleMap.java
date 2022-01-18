// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TDoubleFunction;
import gnu.trove.procedure.TByteDoubleProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.iterator.TByteDoubleIterator;
import gnu.trove.TDoubleCollection;
import gnu.trove.set.TByteSet;
import java.util.Map;

public interface TByteDoubleMap
{
    byte getNoEntryKey();
    
    double getNoEntryValue();
    
    double put(final byte p0, final double p1);
    
    double putIfAbsent(final byte p0, final double p1);
    
    void putAll(final Map<? extends Byte, ? extends Double> p0);
    
    void putAll(final TByteDoubleMap p0);
    
    double get(final byte p0);
    
    void clear();
    
    boolean isEmpty();
    
    double remove(final byte p0);
    
    int size();
    
    TByteSet keySet();
    
    byte[] keys();
    
    byte[] keys(final byte[] p0);
    
    TDoubleCollection valueCollection();
    
    double[] values();
    
    double[] values(final double[] p0);
    
    boolean containsValue(final double p0);
    
    boolean containsKey(final byte p0);
    
    TByteDoubleIterator iterator();
    
    boolean forEachKey(final TByteProcedure p0);
    
    boolean forEachValue(final TDoubleProcedure p0);
    
    boolean forEachEntry(final TByteDoubleProcedure p0);
    
    void transformValues(final TDoubleFunction p0);
    
    boolean retainEntries(final TByteDoubleProcedure p0);
    
    boolean increment(final byte p0);
    
    boolean adjustValue(final byte p0, final double p1);
    
    double adjustOrPutValue(final byte p0, final double p1, final double p2);
}
