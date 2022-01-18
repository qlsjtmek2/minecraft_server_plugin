// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TCharFunction;
import gnu.trove.procedure.TDoubleCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.iterator.TDoubleCharIterator;
import gnu.trove.TCharCollection;
import gnu.trove.set.TDoubleSet;
import java.util.Map;

public interface TDoubleCharMap
{
    double getNoEntryKey();
    
    char getNoEntryValue();
    
    char put(final double p0, final char p1);
    
    char putIfAbsent(final double p0, final char p1);
    
    void putAll(final Map<? extends Double, ? extends Character> p0);
    
    void putAll(final TDoubleCharMap p0);
    
    char get(final double p0);
    
    void clear();
    
    boolean isEmpty();
    
    char remove(final double p0);
    
    int size();
    
    TDoubleSet keySet();
    
    double[] keys();
    
    double[] keys(final double[] p0);
    
    TCharCollection valueCollection();
    
    char[] values();
    
    char[] values(final char[] p0);
    
    boolean containsValue(final char p0);
    
    boolean containsKey(final double p0);
    
    TDoubleCharIterator iterator();
    
    boolean forEachKey(final TDoubleProcedure p0);
    
    boolean forEachValue(final TCharProcedure p0);
    
    boolean forEachEntry(final TDoubleCharProcedure p0);
    
    void transformValues(final TCharFunction p0);
    
    boolean retainEntries(final TDoubleCharProcedure p0);
    
    boolean increment(final double p0);
    
    boolean adjustValue(final double p0, final char p1);
    
    char adjustOrPutValue(final double p0, final char p1, final char p2);
}
