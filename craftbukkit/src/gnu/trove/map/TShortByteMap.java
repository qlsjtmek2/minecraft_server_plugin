// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TByteFunction;
import gnu.trove.procedure.TShortByteProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.iterator.TShortByteIterator;
import gnu.trove.TByteCollection;
import gnu.trove.set.TShortSet;
import java.util.Map;

public interface TShortByteMap
{
    short getNoEntryKey();
    
    byte getNoEntryValue();
    
    byte put(final short p0, final byte p1);
    
    byte putIfAbsent(final short p0, final byte p1);
    
    void putAll(final Map<? extends Short, ? extends Byte> p0);
    
    void putAll(final TShortByteMap p0);
    
    byte get(final short p0);
    
    void clear();
    
    boolean isEmpty();
    
    byte remove(final short p0);
    
    int size();
    
    TShortSet keySet();
    
    short[] keys();
    
    short[] keys(final short[] p0);
    
    TByteCollection valueCollection();
    
    byte[] values();
    
    byte[] values(final byte[] p0);
    
    boolean containsValue(final byte p0);
    
    boolean containsKey(final short p0);
    
    TShortByteIterator iterator();
    
    boolean forEachKey(final TShortProcedure p0);
    
    boolean forEachValue(final TByteProcedure p0);
    
    boolean forEachEntry(final TShortByteProcedure p0);
    
    void transformValues(final TByteFunction p0);
    
    boolean retainEntries(final TShortByteProcedure p0);
    
    boolean increment(final short p0);
    
    boolean adjustValue(final short p0, final byte p1);
    
    byte adjustOrPutValue(final short p0, final byte p1, final byte p2);
}
