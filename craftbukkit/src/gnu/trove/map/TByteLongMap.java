// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TLongFunction;
import gnu.trove.procedure.TByteLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.iterator.TByteLongIterator;
import gnu.trove.TLongCollection;
import gnu.trove.set.TByteSet;
import java.util.Map;

public interface TByteLongMap
{
    byte getNoEntryKey();
    
    long getNoEntryValue();
    
    long put(final byte p0, final long p1);
    
    long putIfAbsent(final byte p0, final long p1);
    
    void putAll(final Map<? extends Byte, ? extends Long> p0);
    
    void putAll(final TByteLongMap p0);
    
    long get(final byte p0);
    
    void clear();
    
    boolean isEmpty();
    
    long remove(final byte p0);
    
    int size();
    
    TByteSet keySet();
    
    byte[] keys();
    
    byte[] keys(final byte[] p0);
    
    TLongCollection valueCollection();
    
    long[] values();
    
    long[] values(final long[] p0);
    
    boolean containsValue(final long p0);
    
    boolean containsKey(final byte p0);
    
    TByteLongIterator iterator();
    
    boolean forEachKey(final TByteProcedure p0);
    
    boolean forEachValue(final TLongProcedure p0);
    
    boolean forEachEntry(final TByteLongProcedure p0);
    
    void transformValues(final TLongFunction p0);
    
    boolean retainEntries(final TByteLongProcedure p0);
    
    boolean increment(final byte p0);
    
    boolean adjustValue(final byte p0, final long p1);
    
    long adjustOrPutValue(final byte p0, final long p1, final long p2);
}
