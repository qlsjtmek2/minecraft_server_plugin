// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TShortFunction;
import gnu.trove.procedure.TByteShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.iterator.TByteShortIterator;
import gnu.trove.TShortCollection;
import gnu.trove.set.TByteSet;
import java.util.Map;

public interface TByteShortMap
{
    byte getNoEntryKey();
    
    short getNoEntryValue();
    
    short put(final byte p0, final short p1);
    
    short putIfAbsent(final byte p0, final short p1);
    
    void putAll(final Map<? extends Byte, ? extends Short> p0);
    
    void putAll(final TByteShortMap p0);
    
    short get(final byte p0);
    
    void clear();
    
    boolean isEmpty();
    
    short remove(final byte p0);
    
    int size();
    
    TByteSet keySet();
    
    byte[] keys();
    
    byte[] keys(final byte[] p0);
    
    TShortCollection valueCollection();
    
    short[] values();
    
    short[] values(final short[] p0);
    
    boolean containsValue(final short p0);
    
    boolean containsKey(final byte p0);
    
    TByteShortIterator iterator();
    
    boolean forEachKey(final TByteProcedure p0);
    
    boolean forEachValue(final TShortProcedure p0);
    
    boolean forEachEntry(final TByteShortProcedure p0);
    
    void transformValues(final TShortFunction p0);
    
    boolean retainEntries(final TByteShortProcedure p0);
    
    boolean increment(final byte p0);
    
    boolean adjustValue(final byte p0, final short p1);
    
    short adjustOrPutValue(final byte p0, final short p1, final short p2);
}
