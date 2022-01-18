// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map;

import gnu.trove.function.TFloatFunction;
import gnu.trove.procedure.TCharFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.iterator.TCharFloatIterator;
import gnu.trove.TFloatCollection;
import gnu.trove.set.TCharSet;
import java.util.Map;

public interface TCharFloatMap
{
    char getNoEntryKey();
    
    float getNoEntryValue();
    
    float put(final char p0, final float p1);
    
    float putIfAbsent(final char p0, final float p1);
    
    void putAll(final Map<? extends Character, ? extends Float> p0);
    
    void putAll(final TCharFloatMap p0);
    
    float get(final char p0);
    
    void clear();
    
    boolean isEmpty();
    
    float remove(final char p0);
    
    int size();
    
    TCharSet keySet();
    
    char[] keys();
    
    char[] keys(final char[] p0);
    
    TFloatCollection valueCollection();
    
    float[] values();
    
    float[] values(final float[] p0);
    
    boolean containsValue(final float p0);
    
    boolean containsKey(final char p0);
    
    TCharFloatIterator iterator();
    
    boolean forEachKey(final TCharProcedure p0);
    
    boolean forEachValue(final TFloatProcedure p0);
    
    boolean forEachEntry(final TCharFloatProcedure p0);
    
    void transformValues(final TFloatFunction p0);
    
    boolean retainEntries(final TCharFloatProcedure p0);
    
    boolean increment(final char p0);
    
    boolean adjustValue(final char p0, final float p1);
    
    float adjustOrPutValue(final char p0, final float p1, final float p2);
}
