// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TDoubleFunction;
import gnu.trove.procedure.TShortDoubleProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.iterator.TShortDoubleIterator;
import gnu.trove.TDoubleCollection;
import gnu.trove.set.TShortSet;
import java.util.Map;

public interface TShortDoubleMap
{
    short getNoEntryKey();
    
    double getNoEntryValue();
    
    double put(final short p0, final double p1);
    
    double putIfAbsent(final short p0, final double p1);
    
    void putAll(final Map<? extends Short, ? extends Double> p0);
    
    void putAll(final TShortDoubleMap p0);
    
    double get(final short p0);
    
    void clear();
    
    boolean isEmpty();
    
    double remove(final short p0);
    
    int size();
    
    TShortSet keySet();
    
    short[] keys();
    
    short[] keys(final short[] p0);
    
    TDoubleCollection valueCollection();
    
    double[] values();
    
    double[] values(final double[] p0);
    
    boolean containsValue(final double p0);
    
    boolean containsKey(final short p0);
    
    TShortDoubleIterator iterator();
    
    boolean forEachKey(final TShortProcedure p0);
    
    boolean forEachValue(final TDoubleProcedure p0);
    
    boolean forEachEntry(final TShortDoubleProcedure p0);
    
    void transformValues(final TDoubleFunction p0);
    
    boolean retainEntries(final TShortDoubleProcedure p0);
    
    boolean increment(final short p0);
    
    boolean adjustValue(final short p0, final double p1);
    
    double adjustOrPutValue(final short p0, final double p1, final double p2);
}
