// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list;

import gnu.trove.procedure.TByteProcedure;
import java.util.Random;
import gnu.trove.function.TByteFunction;
import gnu.trove.TByteCollection;

public interface TByteList extends TByteCollection
{
    byte getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean add(final byte p0);
    
    void add(final byte[] p0);
    
    void add(final byte[] p0, final int p1, final int p2);
    
    void insert(final int p0, final byte p1);
    
    void insert(final int p0, final byte[] p1);
    
    void insert(final int p0, final byte[] p1, final int p2, final int p3);
    
    byte get(final int p0);
    
    byte set(final int p0, final byte p1);
    
    void set(final int p0, final byte[] p1);
    
    void set(final int p0, final byte[] p1, final int p2, final int p3);
    
    byte replace(final int p0, final byte p1);
    
    void clear();
    
    boolean remove(final byte p0);
    
    byte removeAt(final int p0);
    
    void remove(final int p0, final int p1);
    
    void transformValues(final TByteFunction p0);
    
    void reverse();
    
    void reverse(final int p0, final int p1);
    
    void shuffle(final Random p0);
    
    TByteList subList(final int p0, final int p1);
    
    byte[] toArray();
    
    byte[] toArray(final int p0, final int p1);
    
    byte[] toArray(final byte[] p0);
    
    byte[] toArray(final byte[] p0, final int p1, final int p2);
    
    byte[] toArray(final byte[] p0, final int p1, final int p2, final int p3);
    
    boolean forEach(final TByteProcedure p0);
    
    boolean forEachDescending(final TByteProcedure p0);
    
    void sort();
    
    void sort(final int p0, final int p1);
    
    void fill(final byte p0);
    
    void fill(final int p0, final int p1, final byte p2);
    
    int binarySearch(final byte p0);
    
    int binarySearch(final byte p0, final int p1, final int p2);
    
    int indexOf(final byte p0);
    
    int indexOf(final int p0, final byte p1);
    
    int lastIndexOf(final byte p0);
    
    int lastIndexOf(final int p0, final byte p1);
    
    boolean contains(final byte p0);
    
    TByteList grep(final TByteProcedure p0);
    
    TByteList inverseGrep(final TByteProcedure p0);
    
    byte max();
    
    byte min();
    
    byte sum();
}
