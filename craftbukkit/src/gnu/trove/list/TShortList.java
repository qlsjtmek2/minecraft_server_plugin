// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list;

import gnu.trove.procedure.TShortProcedure;
import java.util.Random;
import gnu.trove.function.TShortFunction;
import gnu.trove.TShortCollection;

public interface TShortList extends TShortCollection
{
    short getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean add(final short p0);
    
    void add(final short[] p0);
    
    void add(final short[] p0, final int p1, final int p2);
    
    void insert(final int p0, final short p1);
    
    void insert(final int p0, final short[] p1);
    
    void insert(final int p0, final short[] p1, final int p2, final int p3);
    
    short get(final int p0);
    
    short set(final int p0, final short p1);
    
    void set(final int p0, final short[] p1);
    
    void set(final int p0, final short[] p1, final int p2, final int p3);
    
    short replace(final int p0, final short p1);
    
    void clear();
    
    boolean remove(final short p0);
    
    short removeAt(final int p0);
    
    void remove(final int p0, final int p1);
    
    void transformValues(final TShortFunction p0);
    
    void reverse();
    
    void reverse(final int p0, final int p1);
    
    void shuffle(final Random p0);
    
    TShortList subList(final int p0, final int p1);
    
    short[] toArray();
    
    short[] toArray(final int p0, final int p1);
    
    short[] toArray(final short[] p0);
    
    short[] toArray(final short[] p0, final int p1, final int p2);
    
    short[] toArray(final short[] p0, final int p1, final int p2, final int p3);
    
    boolean forEach(final TShortProcedure p0);
    
    boolean forEachDescending(final TShortProcedure p0);
    
    void sort();
    
    void sort(final int p0, final int p1);
    
    void fill(final short p0);
    
    void fill(final int p0, final int p1, final short p2);
    
    int binarySearch(final short p0);
    
    int binarySearch(final short p0, final int p1, final int p2);
    
    int indexOf(final short p0);
    
    int indexOf(final int p0, final short p1);
    
    int lastIndexOf(final short p0);
    
    int lastIndexOf(final int p0, final short p1);
    
    boolean contains(final short p0);
    
    TShortList grep(final TShortProcedure p0);
    
    TShortList inverseGrep(final TShortProcedure p0);
    
    short max();
    
    short min();
    
    short sum();
}
