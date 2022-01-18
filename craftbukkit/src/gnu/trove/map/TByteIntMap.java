// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TByteIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.iterator.TByteIntIterator;
import gnu.trove.TIntCollection;
import gnu.trove.set.TByteSet;
import java.util.Map;

public interface TByteIntMap
{
    byte getNoEntryKey();
    
    int getNoEntryValue();
    
    int put(final byte p0, final int p1);
    
    int putIfAbsent(final byte p0, final int p1);
    
    void putAll(final Map<? extends Byte, ? extends Integer> p0);
    
    void putAll(final TByteIntMap p0);
    
    int get(final byte p0);
    
    void clear();
    
    boolean isEmpty();
    
    int remove(final byte p0);
    
    int size();
    
    TByteSet keySet();
    
    byte[] keys();
    
    byte[] keys(final byte[] p0);
    
    TIntCollection valueCollection();
    
    int[] values();
    
    int[] values(final int[] p0);
    
    boolean containsValue(final int p0);
    
    boolean containsKey(final byte p0);
    
    TByteIntIterator iterator();
    
    boolean forEachKey(final TByteProcedure p0);
    
    boolean forEachValue(final TIntProcedure p0);
    
    boolean forEachEntry(final TByteIntProcedure p0);
    
    void transformValues(final TIntFunction p0);
    
    boolean retainEntries(final TByteIntProcedure p0);
    
    boolean increment(final byte p0);
    
    boolean adjustValue(final byte p0, final int p1);
    
    int adjustOrPutValue(final byte p0, final int p1, final int p2);
}
