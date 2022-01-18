// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TCharIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.iterator.TCharIntIterator;
import gnu.trove.TIntCollection;
import gnu.trove.set.TCharSet;
import java.util.Map;

public interface TCharIntMap
{
    char getNoEntryKey();
    
    int getNoEntryValue();
    
    int put(final char p0, final int p1);
    
    int putIfAbsent(final char p0, final int p1);
    
    void putAll(final Map<? extends Character, ? extends Integer> p0);
    
    void putAll(final TCharIntMap p0);
    
    int get(final char p0);
    
    void clear();
    
    boolean isEmpty();
    
    int remove(final char p0);
    
    int size();
    
    TCharSet keySet();
    
    char[] keys();
    
    char[] keys(final char[] p0);
    
    TIntCollection valueCollection();
    
    int[] values();
    
    int[] values(final int[] p0);
    
    boolean containsValue(final int p0);
    
    boolean containsKey(final char p0);
    
    TCharIntIterator iterator();
    
    boolean forEachKey(final TCharProcedure p0);
    
    boolean forEachValue(final TIntProcedure p0);
    
    boolean forEachEntry(final TCharIntProcedure p0);
    
    void transformValues(final TIntFunction p0);
    
    boolean retainEntries(final TCharIntProcedure p0);
    
    boolean increment(final char p0);
    
    boolean adjustValue(final char p0, final int p1);
    
    int adjustOrPutValue(final char p0, final int p1, final int p2);
}
