// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TCharFunction;
import gnu.trove.procedure.TByteCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.iterator.TByteCharIterator;
import gnu.trove.TCharCollection;
import gnu.trove.set.TByteSet;
import java.util.Map;

public interface TByteCharMap
{
    byte getNoEntryKey();
    
    char getNoEntryValue();
    
    char put(final byte p0, final char p1);
    
    char putIfAbsent(final byte p0, final char p1);
    
    void putAll(final Map<? extends Byte, ? extends Character> p0);
    
    void putAll(final TByteCharMap p0);
    
    char get(final byte p0);
    
    void clear();
    
    boolean isEmpty();
    
    char remove(final byte p0);
    
    int size();
    
    TByteSet keySet();
    
    byte[] keys();
    
    byte[] keys(final byte[] p0);
    
    TCharCollection valueCollection();
    
    char[] values();
    
    char[] values(final char[] p0);
    
    boolean containsValue(final char p0);
    
    boolean containsKey(final byte p0);
    
    TByteCharIterator iterator();
    
    boolean forEachKey(final TByteProcedure p0);
    
    boolean forEachValue(final TCharProcedure p0);
    
    boolean forEachEntry(final TByteCharProcedure p0);
    
    void transformValues(final TCharFunction p0);
    
    boolean retainEntries(final TByteCharProcedure p0);
    
    boolean increment(final byte p0);
    
    boolean adjustValue(final byte p0, final char p1);
    
    char adjustOrPutValue(final byte p0, final char p1, final char p2);
}
