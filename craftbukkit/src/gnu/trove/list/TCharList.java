// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list;

import gnu.trove.procedure.TCharProcedure;
import java.util.Random;
import gnu.trove.function.TCharFunction;
import gnu.trove.TCharCollection;

public interface TCharList extends TCharCollection
{
    char getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean add(final char p0);
    
    void add(final char[] p0);
    
    void add(final char[] p0, final int p1, final int p2);
    
    void insert(final int p0, final char p1);
    
    void insert(final int p0, final char[] p1);
    
    void insert(final int p0, final char[] p1, final int p2, final int p3);
    
    char get(final int p0);
    
    char set(final int p0, final char p1);
    
    void set(final int p0, final char[] p1);
    
    void set(final int p0, final char[] p1, final int p2, final int p3);
    
    char replace(final int p0, final char p1);
    
    void clear();
    
    boolean remove(final char p0);
    
    char removeAt(final int p0);
    
    void remove(final int p0, final int p1);
    
    void transformValues(final TCharFunction p0);
    
    void reverse();
    
    void reverse(final int p0, final int p1);
    
    void shuffle(final Random p0);
    
    TCharList subList(final int p0, final int p1);
    
    char[] toArray();
    
    char[] toArray(final int p0, final int p1);
    
    char[] toArray(final char[] p0);
    
    char[] toArray(final char[] p0, final int p1, final int p2);
    
    char[] toArray(final char[] p0, final int p1, final int p2, final int p3);
    
    boolean forEach(final TCharProcedure p0);
    
    boolean forEachDescending(final TCharProcedure p0);
    
    void sort();
    
    void sort(final int p0, final int p1);
    
    void fill(final char p0);
    
    void fill(final int p0, final int p1, final char p2);
    
    int binarySearch(final char p0);
    
    int binarySearch(final char p0, final int p1, final int p2);
    
    int indexOf(final char p0);
    
    int indexOf(final int p0, final char p1);
    
    int lastIndexOf(final char p0);
    
    int lastIndexOf(final int p0, final char p1);
    
    boolean contains(final char p0);
    
    TCharList grep(final TCharProcedure p0);
    
    TCharList inverseGrep(final TCharProcedure p0);
    
    char max();
    
    char min();
    
    char sum();
}
