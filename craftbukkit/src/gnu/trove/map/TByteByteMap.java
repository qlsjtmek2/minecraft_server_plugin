// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TByteFunction;
import gnu.trove.procedure.TByteByteProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.iterator.TByteByteIterator;
import gnu.trove.TByteCollection;
import gnu.trove.set.TByteSet;
import java.util.Map;

public interface TByteByteMap
{
    byte getNoEntryKey();
    
    byte getNoEntryValue();
    
    byte put(final byte p0, final byte p1);
    
    byte putIfAbsent(final byte p0, final byte p1);
    
    void putAll(final Map<? extends Byte, ? extends Byte> p0);
    
    void putAll(final TByteByteMap p0);
    
    byte get(final byte p0);
    
    void clear();
    
    boolean isEmpty();
    
    byte remove(final byte p0);
    
    int size();
    
    TByteSet keySet();
    
    byte[] keys();
    
    byte[] keys(final byte[] p0);
    
    TByteCollection valueCollection();
    
    byte[] values();
    
    byte[] values(final byte[] p0);
    
    boolean containsValue(final byte p0);
    
    boolean containsKey(final byte p0);
    
    TByteByteIterator iterator();
    
    boolean forEachKey(final TByteProcedure p0);
    
    boolean forEachValue(final TByteProcedure p0);
    
    boolean forEachEntry(final TByteByteProcedure p0);
    
    void transformValues(final TByteFunction p0);
    
    boolean retainEntries(final TByteByteProcedure p0);
    
    boolean increment(final byte p0);
    
    boolean adjustValue(final byte p0, final byte p1);
    
    byte adjustOrPutValue(final byte p0, final byte p1, final byte p2);
}
