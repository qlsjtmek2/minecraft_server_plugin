// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TShortFunction;
import gnu.trove.procedure.TDoubleShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.iterator.TDoubleShortIterator;
import gnu.trove.TShortCollection;
import gnu.trove.set.TDoubleSet;
import java.util.Map;

public interface TDoubleShortMap
{
    double getNoEntryKey();
    
    short getNoEntryValue();
    
    short put(final double p0, final short p1);
    
    short putIfAbsent(final double p0, final short p1);
    
    void putAll(final Map<? extends Double, ? extends Short> p0);
    
    void putAll(final TDoubleShortMap p0);
    
    short get(final double p0);
    
    void clear();
    
    boolean isEmpty();
    
    short remove(final double p0);
    
    int size();
    
    TDoubleSet keySet();
    
    double[] keys();
    
    double[] keys(final double[] p0);
    
    TShortCollection valueCollection();
    
    short[] values();
    
    short[] values(final short[] p0);
    
    boolean containsValue(final short p0);
    
    boolean containsKey(final double p0);
    
    TDoubleShortIterator iterator();
    
    boolean forEachKey(final TDoubleProcedure p0);
    
    boolean forEachValue(final TShortProcedure p0);
    
    boolean forEachEntry(final TDoubleShortProcedure p0);
    
    void transformValues(final TShortFunction p0);
    
    boolean retainEntries(final TDoubleShortProcedure p0);
    
    boolean increment(final double p0);
    
    boolean adjustValue(final double p0, final short p1);
    
    short adjustOrPutValue(final double p0, final short p1, final short p2);
}
