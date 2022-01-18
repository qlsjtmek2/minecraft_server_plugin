// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TCharFunction;
import gnu.trove.procedure.TCharCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.iterator.TCharCharIterator;
import gnu.trove.TCharCollection;
import gnu.trove.set.TCharSet;
import java.util.Map;

public interface TCharCharMap
{
    char getNoEntryKey();
    
    char getNoEntryValue();
    
    char put(final char p0, final char p1);
    
    char putIfAbsent(final char p0, final char p1);
    
    void putAll(final Map<? extends Character, ? extends Character> p0);
    
    void putAll(final TCharCharMap p0);
    
    char get(final char p0);
    
    void clear();
    
    boolean isEmpty();
    
    char remove(final char p0);
    
    int size();
    
    TCharSet keySet();
    
    char[] keys();
    
    char[] keys(final char[] p0);
    
    TCharCollection valueCollection();
    
    char[] values();
    
    char[] values(final char[] p0);
    
    boolean containsValue(final char p0);
    
    boolean containsKey(final char p0);
    
    TCharCharIterator iterator();
    
    boolean forEachKey(final TCharProcedure p0);
    
    boolean forEachValue(final TCharProcedure p0);
    
    boolean forEachEntry(final TCharCharProcedure p0);
    
    void transformValues(final TCharFunction p0);
    
    boolean retainEntries(final TCharCharProcedure p0);
    
    boolean increment(final char p0);
    
    boolean adjustValue(final char p0, final char p1);
    
    char adjustOrPutValue(final char p0, final char p1, final char p2);
}
