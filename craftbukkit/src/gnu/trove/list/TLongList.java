// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list;

import gnu.trove.procedure.TLongProcedure;
import java.util.Random;
import gnu.trove.function.TLongFunction;
import gnu.trove.TLongCollection;

public interface TLongList extends TLongCollection
{
    long getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean add(final long p0);
    
    void add(final long[] p0);
    
    void add(final long[] p0, final int p1, final int p2);
    
    void insert(final int p0, final long p1);
    
    void insert(final int p0, final long[] p1);
    
    void insert(final int p0, final long[] p1, final int p2, final int p3);
    
    long get(final int p0);
    
    long set(final int p0, final long p1);
    
    void set(final int p0, final long[] p1);
    
    void set(final int p0, final long[] p1, final int p2, final int p3);
    
    long replace(final int p0, final long p1);
    
    void clear();
    
    boolean remove(final long p0);
    
    long removeAt(final int p0);
    
    void remove(final int p0, final int p1);
    
    void transformValues(final TLongFunction p0);
    
    void reverse();
    
    void reverse(final int p0, final int p1);
    
    void shuffle(final Random p0);
    
    TLongList subList(final int p0, final int p1);
    
    long[] toArray();
    
    long[] toArray(final int p0, final int p1);
    
    long[] toArray(final long[] p0);
    
    long[] toArray(final long[] p0, final int p1, final int p2);
    
    long[] toArray(final long[] p0, final int p1, final int p2, final int p3);
    
    boolean forEach(final TLongProcedure p0);
    
    boolean forEachDescending(final TLongProcedure p0);
    
    void sort();
    
    void sort(final int p0, final int p1);
    
    void fill(final long p0);
    
    void fill(final int p0, final int p1, final long p2);
    
    int binarySearch(final long p0);
    
    int binarySearch(final long p0, final int p1, final int p2);
    
    int indexOf(final long p0);
    
    int indexOf(final int p0, final long p1);
    
    int lastIndexOf(final long p0);
    
    int lastIndexOf(final int p0, final long p1);
    
    boolean contains(final long p0);
    
    TLongList grep(final TLongProcedure p0);
    
    TLongList inverseGrep(final TLongProcedure p0);
    
    long max();
    
    long min();
    
    long sum();
}
