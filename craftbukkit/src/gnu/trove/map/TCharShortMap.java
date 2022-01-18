// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TShortFunction;
import gnu.trove.procedure.TCharShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.iterator.TCharShortIterator;
import gnu.trove.TShortCollection;
import gnu.trove.set.TCharSet;
import java.util.Map;

public interface TCharShortMap
{
    char getNoEntryKey();
    
    short getNoEntryValue();
    
    short put(final char p0, final short p1);
    
    short putIfAbsent(final char p0, final short p1);
    
    void putAll(final Map<? extends Character, ? extends Short> p0);
    
    void putAll(final TCharShortMap p0);
    
    short get(final char p0);
    
    void clear();
    
    boolean isEmpty();
    
    short remove(final char p0);
    
    int size();
    
    TCharSet keySet();
    
    char[] keys();
    
    char[] keys(final char[] p0);
    
    TShortCollection valueCollection();
    
    short[] values();
    
    short[] values(final short[] p0);
    
    boolean containsValue(final short p0);
    
    boolean containsKey(final char p0);
    
    TCharShortIterator iterator();
    
    boolean forEachKey(final TCharProcedure p0);
    
    boolean forEachValue(final TShortProcedure p0);
    
    boolean forEachEntry(final TCharShortProcedure p0);
    
    void transformValues(final TShortFunction p0);
    
    boolean retainEntries(final TCharShortProcedure p0);
    
    boolean increment(final char p0);
    
    boolean adjustValue(final char p0, final short p1);
    
    short adjustOrPutValue(final char p0, final short p1, final short p2);
}
