// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TDoubleIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.iterator.TDoubleIntIterator;
import gnu.trove.TIntCollection;
import gnu.trove.set.TDoubleSet;
import java.util.Map;

public interface TDoubleIntMap
{
    double getNoEntryKey();
    
    int getNoEntryValue();
    
    int put(final double p0, final int p1);
    
    int putIfAbsent(final double p0, final int p1);
    
    void putAll(final Map<? extends Double, ? extends Integer> p0);
    
    void putAll(final TDoubleIntMap p0);
    
    int get(final double p0);
    
    void clear();
    
    boolean isEmpty();
    
    int remove(final double p0);
    
    int size();
    
    TDoubleSet keySet();
    
    double[] keys();
    
    double[] keys(final double[] p0);
    
    TIntCollection valueCollection();
    
    int[] values();
    
    int[] values(final int[] p0);
    
    boolean containsValue(final int p0);
    
    boolean containsKey(final double p0);
    
    TDoubleIntIterator iterator();
    
    boolean forEachKey(final TDoubleProcedure p0);
    
    boolean forEachValue(final TIntProcedure p0);
    
    boolean forEachEntry(final TDoubleIntProcedure p0);
    
    void transformValues(final TIntFunction p0);
    
    boolean retainEntries(final TDoubleIntProcedure p0);
    
    boolean increment(final double p0);
    
    boolean adjustValue(final double p0, final int p1);
    
    int adjustOrPutValue(final double p0, final int p1, final int p2);
}
