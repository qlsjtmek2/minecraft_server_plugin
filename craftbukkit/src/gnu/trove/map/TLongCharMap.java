// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TCharFunction;
import gnu.trove.procedure.TLongCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.iterator.TLongCharIterator;
import gnu.trove.TCharCollection;
import gnu.trove.set.TLongSet;
import java.util.Map;

public interface TLongCharMap
{
    long getNoEntryKey();
    
    char getNoEntryValue();
    
    char put(final long p0, final char p1);
    
    char putIfAbsent(final long p0, final char p1);
    
    void putAll(final Map<? extends Long, ? extends Character> p0);
    
    void putAll(final TLongCharMap p0);
    
    char get(final long p0);
    
    void clear();
    
    boolean isEmpty();
    
    char remove(final long p0);
    
    int size();
    
    TLongSet keySet();
    
    long[] keys();
    
    long[] keys(final long[] p0);
    
    TCharCollection valueCollection();
    
    char[] values();
    
    char[] values(final char[] p0);
    
    boolean containsValue(final char p0);
    
    boolean containsKey(final long p0);
    
    TLongCharIterator iterator();
    
    boolean forEachKey(final TLongProcedure p0);
    
    boolean forEachValue(final TCharProcedure p0);
    
    boolean forEachEntry(final TLongCharProcedure p0);
    
    void transformValues(final TCharFunction p0);
    
    boolean retainEntries(final TLongCharProcedure p0);
    
    boolean increment(final long p0);
    
    boolean adjustValue(final long p0, final char p1);
    
    char adjustOrPutValue(final long p0, final char p1, final char p2);
}
