// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TCharFunction;
import gnu.trove.procedure.TIntCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.iterator.TIntCharIterator;
import gnu.trove.TCharCollection;
import gnu.trove.set.TIntSet;
import java.util.Map;

public interface TIntCharMap
{
    int getNoEntryKey();
    
    char getNoEntryValue();
    
    char put(final int p0, final char p1);
    
    char putIfAbsent(final int p0, final char p1);
    
    void putAll(final Map<? extends Integer, ? extends Character> p0);
    
    void putAll(final TIntCharMap p0);
    
    char get(final int p0);
    
    void clear();
    
    boolean isEmpty();
    
    char remove(final int p0);
    
    int size();
    
    TIntSet keySet();
    
    int[] keys();
    
    int[] keys(final int[] p0);
    
    TCharCollection valueCollection();
    
    char[] values();
    
    char[] values(final char[] p0);
    
    boolean containsValue(final char p0);
    
    boolean containsKey(final int p0);
    
    TIntCharIterator iterator();
    
    boolean forEachKey(final TIntProcedure p0);
    
    boolean forEachValue(final TCharProcedure p0);
    
    boolean forEachEntry(final TIntCharProcedure p0);
    
    void transformValues(final TCharFunction p0);
    
    boolean retainEntries(final TIntCharProcedure p0);
    
    boolean increment(final int p0);
    
    boolean adjustValue(final int p0, final char p1);
    
    char adjustOrPutValue(final int p0, final char p1, final char p2);
}
