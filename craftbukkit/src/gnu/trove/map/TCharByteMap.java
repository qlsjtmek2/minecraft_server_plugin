// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TByteFunction;
import gnu.trove.procedure.TCharByteProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.iterator.TCharByteIterator;
import gnu.trove.TByteCollection;
import gnu.trove.set.TCharSet;
import java.util.Map;

public interface TCharByteMap
{
    char getNoEntryKey();
    
    byte getNoEntryValue();
    
    byte put(final char p0, final byte p1);
    
    byte putIfAbsent(final char p0, final byte p1);
    
    void putAll(final Map<? extends Character, ? extends Byte> p0);
    
    void putAll(final TCharByteMap p0);
    
    byte get(final char p0);
    
    void clear();
    
    boolean isEmpty();
    
    byte remove(final char p0);
    
    int size();
    
    TCharSet keySet();
    
    char[] keys();
    
    char[] keys(final char[] p0);
    
    TByteCollection valueCollection();
    
    byte[] values();
    
    byte[] values(final byte[] p0);
    
    boolean containsValue(final byte p0);
    
    boolean containsKey(final char p0);
    
    TCharByteIterator iterator();
    
    boolean forEachKey(final TCharProcedure p0);
    
    boolean forEachValue(final TByteProcedure p0);
    
    boolean forEachEntry(final TCharByteProcedure p0);
    
    void transformValues(final TByteFunction p0);
    
    boolean retainEntries(final TCharByteProcedure p0);
    
    boolean increment(final char p0);
    
    boolean adjustValue(final char p0, final byte p1);
    
    byte adjustOrPutValue(final char p0, final byte p1, final byte p2);
}
