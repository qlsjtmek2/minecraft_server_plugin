// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.lib.gnu.trove.map;

import think.rpgitems.lib.gnu.trove.function.TByteFunction;
import think.rpgitems.lib.gnu.trove.procedure.TIntByteProcedure;
import think.rpgitems.lib.gnu.trove.procedure.TByteProcedure;
import think.rpgitems.lib.gnu.trove.procedure.TIntProcedure;
import think.rpgitems.lib.gnu.trove.iterator.TIntByteIterator;
import think.rpgitems.lib.gnu.trove.TByteCollection;
import think.rpgitems.lib.gnu.trove.set.TIntSet;
import java.util.Map;

public interface TIntByteMap
{
    int getNoEntryKey();
    
    byte getNoEntryValue();
    
    byte put(final int p0, final byte p1);
    
    byte putIfAbsent(final int p0, final byte p1);
    
    void putAll(final Map<? extends Integer, ? extends Byte> p0);
    
    void putAll(final TIntByteMap p0);
    
    byte get(final int p0);
    
    void clear();
    
    boolean isEmpty();
    
    byte remove(final int p0);
    
    int size();
    
    TIntSet keySet();
    
    int[] keys();
    
    int[] keys(final int[] p0);
    
    TByteCollection valueCollection();
    
    byte[] values();
    
    byte[] values(final byte[] p0);
    
    boolean containsValue(final byte p0);
    
    boolean containsKey(final int p0);
    
    TIntByteIterator iterator();
    
    boolean forEachKey(final TIntProcedure p0);
    
    boolean forEachValue(final TByteProcedure p0);
    
    boolean forEachEntry(final TIntByteProcedure p0);
    
    void transformValues(final TByteFunction p0);
    
    boolean retainEntries(final TIntByteProcedure p0);
    
    boolean increment(final int p0);
    
    boolean adjustValue(final int p0, final byte p1);
    
    byte adjustOrPutValue(final int p0, final byte p1, final byte p2);
}
