// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TByteFunction;
import gnu.trove.procedure.TLongByteProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.iterator.TLongByteIterator;
import gnu.trove.TByteCollection;
import gnu.trove.set.TLongSet;
import java.util.Map;

public interface TLongByteMap
{
    long getNoEntryKey();
    
    byte getNoEntryValue();
    
    byte put(final long p0, final byte p1);
    
    byte putIfAbsent(final long p0, final byte p1);
    
    void putAll(final Map<? extends Long, ? extends Byte> p0);
    
    void putAll(final TLongByteMap p0);
    
    byte get(final long p0);
    
    void clear();
    
    boolean isEmpty();
    
    byte remove(final long p0);
    
    int size();
    
    TLongSet keySet();
    
    long[] keys();
    
    long[] keys(final long[] p0);
    
    TByteCollection valueCollection();
    
    byte[] values();
    
    byte[] values(final byte[] p0);
    
    boolean containsValue(final byte p0);
    
    boolean containsKey(final long p0);
    
    TLongByteIterator iterator();
    
    boolean forEachKey(final TLongProcedure p0);
    
    boolean forEachValue(final TByteProcedure p0);
    
    boolean forEachEntry(final TLongByteProcedure p0);
    
    void transformValues(final TByteFunction p0);
    
    boolean retainEntries(final TLongByteProcedure p0);
    
    boolean increment(final long p0);
    
    boolean adjustValue(final long p0, final byte p1);
    
    byte adjustOrPutValue(final long p0, final byte p1, final byte p2);
}
