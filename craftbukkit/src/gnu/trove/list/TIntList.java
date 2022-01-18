// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list;

import gnu.trove.procedure.TIntProcedure;
import java.util.Random;
import gnu.trove.function.TIntFunction;
import gnu.trove.TIntCollection;

public interface TIntList extends TIntCollection
{
    int getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean add(final int p0);
    
    void add(final int[] p0);
    
    void add(final int[] p0, final int p1, final int p2);
    
    void insert(final int p0, final int p1);
    
    void insert(final int p0, final int[] p1);
    
    void insert(final int p0, final int[] p1, final int p2, final int p3);
    
    int get(final int p0);
    
    int set(final int p0, final int p1);
    
    void set(final int p0, final int[] p1);
    
    void set(final int p0, final int[] p1, final int p2, final int p3);
    
    int replace(final int p0, final int p1);
    
    void clear();
    
    boolean remove(final int p0);
    
    int removeAt(final int p0);
    
    void remove(final int p0, final int p1);
    
    void transformValues(final TIntFunction p0);
    
    void reverse();
    
    void reverse(final int p0, final int p1);
    
    void shuffle(final Random p0);
    
    TIntList subList(final int p0, final int p1);
    
    int[] toArray();
    
    int[] toArray(final int p0, final int p1);
    
    int[] toArray(final int[] p0);
    
    int[] toArray(final int[] p0, final int p1, final int p2);
    
    int[] toArray(final int[] p0, final int p1, final int p2, final int p3);
    
    boolean forEach(final TIntProcedure p0);
    
    boolean forEachDescending(final TIntProcedure p0);
    
    void sort();
    
    void sort(final int p0, final int p1);
    
    void fill(final int p0);
    
    void fill(final int p0, final int p1, final int p2);
    
    int binarySearch(final int p0);
    
    int binarySearch(final int p0, final int p1, final int p2);
    
    int indexOf(final int p0);
    
    int indexOf(final int p0, final int p1);
    
    int lastIndexOf(final int p0);
    
    int lastIndexOf(final int p0, final int p1);
    
    boolean contains(final int p0);
    
    TIntList grep(final TIntProcedure p0);
    
    TIntList inverseGrep(final TIntProcedure p0);
    
    int max();
    
    int min();
    
    int sum();
}
