// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TDoubleFunction;
import gnu.trove.procedure.TCharDoubleProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.iterator.TCharDoubleIterator;
import gnu.trove.TDoubleCollection;
import gnu.trove.set.TCharSet;
import java.util.Map;

public interface TCharDoubleMap
{
    char getNoEntryKey();
    
    double getNoEntryValue();
    
    double put(final char p0, final double p1);
    
    double putIfAbsent(final char p0, final double p1);
    
    void putAll(final Map<? extends Character, ? extends Double> p0);
    
    void putAll(final TCharDoubleMap p0);
    
    double get(final char p0);
    
    void clear();
    
    boolean isEmpty();
    
    double remove(final char p0);
    
    int size();
    
    TCharSet keySet();
    
    char[] keys();
    
    char[] keys(final char[] p0);
    
    TDoubleCollection valueCollection();
    
    double[] values();
    
    double[] values(final double[] p0);
    
    boolean containsValue(final double p0);
    
    boolean containsKey(final char p0);
    
    TCharDoubleIterator iterator();
    
    boolean forEachKey(final TCharProcedure p0);
    
    boolean forEachValue(final TDoubleProcedure p0);
    
    boolean forEachEntry(final TCharDoubleProcedure p0);
    
    void transformValues(final TDoubleFunction p0);
    
    boolean retainEntries(final TCharDoubleProcedure p0);
    
    boolean increment(final char p0);
    
    boolean adjustValue(final char p0, final double p1);
    
    double adjustOrPutValue(final char p0, final double p1, final double p2);
}
