// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TCharFunction;
import gnu.trove.procedure.TFloatCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.iterator.TFloatCharIterator;
import gnu.trove.TCharCollection;
import gnu.trove.set.TFloatSet;
import java.util.Map;

public interface TFloatCharMap
{
    float getNoEntryKey();
    
    char getNoEntryValue();
    
    char put(final float p0, final char p1);
    
    char putIfAbsent(final float p0, final char p1);
    
    void putAll(final Map<? extends Float, ? extends Character> p0);
    
    void putAll(final TFloatCharMap p0);
    
    char get(final float p0);
    
    void clear();
    
    boolean isEmpty();
    
    char remove(final float p0);
    
    int size();
    
    TFloatSet keySet();
    
    float[] keys();
    
    float[] keys(final float[] p0);
    
    TCharCollection valueCollection();
    
    char[] values();
    
    char[] values(final char[] p0);
    
    boolean containsValue(final char p0);
    
    boolean containsKey(final float p0);
    
    TFloatCharIterator iterator();
    
    boolean forEachKey(final TFloatProcedure p0);
    
    boolean forEachValue(final TCharProcedure p0);
    
    boolean forEachEntry(final TFloatCharProcedure p0);
    
    void transformValues(final TCharFunction p0);
    
    boolean retainEntries(final TFloatCharProcedure p0);
    
    boolean increment(final float p0);
    
    boolean adjustValue(final float p0, final char p1);
    
    char adjustOrPutValue(final float p0, final char p1, final char p2);
}
