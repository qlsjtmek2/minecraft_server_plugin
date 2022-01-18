// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TDoubleFunction;
import gnu.trove.procedure.TIntDoubleProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.TDoubleCollection;
import gnu.trove.set.TIntSet;
import java.util.Map;

public interface TIntDoubleMap
{
    int getNoEntryKey();
    
    double getNoEntryValue();
    
    double put(final int p0, final double p1);
    
    double putIfAbsent(final int p0, final double p1);
    
    void putAll(final Map<? extends Integer, ? extends Double> p0);
    
    void putAll(final TIntDoubleMap p0);
    
    double get(final int p0);
    
    void clear();
    
    boolean isEmpty();
    
    double remove(final int p0);
    
    int size();
    
    TIntSet keySet();
    
    int[] keys();
    
    int[] keys(final int[] p0);
    
    TDoubleCollection valueCollection();
    
    double[] values();
    
    double[] values(final double[] p0);
    
    boolean containsValue(final double p0);
    
    boolean containsKey(final int p0);
    
    TIntDoubleIterator iterator();
    
    boolean forEachKey(final TIntProcedure p0);
    
    boolean forEachValue(final TDoubleProcedure p0);
    
    boolean forEachEntry(final TIntDoubleProcedure p0);
    
    void transformValues(final TDoubleFunction p0);
    
    boolean retainEntries(final TIntDoubleProcedure p0);
    
    boolean increment(final int p0);
    
    boolean adjustValue(final int p0, final double p1);
    
    double adjustOrPutValue(final int p0, final double p1, final double p2);
}
