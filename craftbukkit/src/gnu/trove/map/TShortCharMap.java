// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TCharFunction;
import gnu.trove.procedure.TShortCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.iterator.TShortCharIterator;
import gnu.trove.TCharCollection;
import gnu.trove.set.TShortSet;
import java.util.Map;

public interface TShortCharMap
{
    short getNoEntryKey();
    
    char getNoEntryValue();
    
    char put(final short p0, final char p1);
    
    char putIfAbsent(final short p0, final char p1);
    
    void putAll(final Map<? extends Short, ? extends Character> p0);
    
    void putAll(final TShortCharMap p0);
    
    char get(final short p0);
    
    void clear();
    
    boolean isEmpty();
    
    char remove(final short p0);
    
    int size();
    
    TShortSet keySet();
    
    short[] keys();
    
    short[] keys(final short[] p0);
    
    TCharCollection valueCollection();
    
    char[] values();
    
    char[] values(final char[] p0);
    
    boolean containsValue(final char p0);
    
    boolean containsKey(final short p0);
    
    TShortCharIterator iterator();
    
    boolean forEachKey(final TShortProcedure p0);
    
    boolean forEachValue(final TCharProcedure p0);
    
    boolean forEachEntry(final TShortCharProcedure p0);
    
    void transformValues(final TCharFunction p0);
    
    boolean retainEntries(final TShortCharProcedure p0);
    
    boolean increment(final short p0);
    
    boolean adjustValue(final short p0, final char p1);
    
    char adjustOrPutValue(final short p0, final char p1, final char p2);
}
