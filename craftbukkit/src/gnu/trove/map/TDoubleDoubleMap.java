// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TDoubleFunction;
import gnu.trove.procedure.TDoubleDoubleProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.iterator.TDoubleDoubleIterator;
import gnu.trove.TDoubleCollection;
import gnu.trove.set.TDoubleSet;
import java.util.Map;

public interface TDoubleDoubleMap
{
    double getNoEntryKey();
    
    double getNoEntryValue();
    
    double put(final double p0, final double p1);
    
    double putIfAbsent(final double p0, final double p1);
    
    void putAll(final Map<? extends Double, ? extends Double> p0);
    
    void putAll(final TDoubleDoubleMap p0);
    
    double get(final double p0);
    
    void clear();
    
    boolean isEmpty();
    
    double remove(final double p0);
    
    int size();
    
    TDoubleSet keySet();
    
    double[] keys();
    
    double[] keys(final double[] p0);
    
    TDoubleCollection valueCollection();
    
    double[] values();
    
    double[] values(final double[] p0);
    
    boolean containsValue(final double p0);
    
    boolean containsKey(final double p0);
    
    TDoubleDoubleIterator iterator();
    
    boolean forEachKey(final TDoubleProcedure p0);
    
    boolean forEachValue(final TDoubleProcedure p0);
    
    boolean forEachEntry(final TDoubleDoubleProcedure p0);
    
    void transformValues(final TDoubleFunction p0);
    
    boolean retainEntries(final TDoubleDoubleProcedure p0);
    
    boolean increment(final double p0);
    
    boolean adjustValue(final double p0, final double p1);
    
    double adjustOrPutValue(final double p0, final double p1, final double p2);
}
