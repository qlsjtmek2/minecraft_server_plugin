// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TLongFunction;
import gnu.trove.procedure.TCharLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.iterator.TCharLongIterator;
import gnu.trove.TLongCollection;
import gnu.trove.set.TCharSet;
import java.util.Map;

public interface TCharLongMap
{
    char getNoEntryKey();
    
    long getNoEntryValue();
    
    long put(final char p0, final long p1);
    
    long putIfAbsent(final char p0, final long p1);
    
    void putAll(final Map<? extends Character, ? extends Long> p0);
    
    void putAll(final TCharLongMap p0);
    
    long get(final char p0);
    
    void clear();
    
    boolean isEmpty();
    
    long remove(final char p0);
    
    int size();
    
    TCharSet keySet();
    
    char[] keys();
    
    char[] keys(final char[] p0);
    
    TLongCollection valueCollection();
    
    long[] values();
    
    long[] values(final long[] p0);
    
    boolean containsValue(final long p0);
    
    boolean containsKey(final char p0);
    
    TCharLongIterator iterator();
    
    boolean forEachKey(final TCharProcedure p0);
    
    boolean forEachValue(final TLongProcedure p0);
    
    boolean forEachEntry(final TCharLongProcedure p0);
    
    void transformValues(final TLongFunction p0);
    
    boolean retainEntries(final TCharLongProcedure p0);
    
    boolean increment(final char p0);
    
    boolean adjustValue(final char p0, final long p1);
    
    long adjustOrPutValue(final char p0, final long p1, final long p2);
}
