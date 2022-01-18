// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list;

import gnu.trove.procedure.TDoubleProcedure;
import java.util.Random;
import gnu.trove.function.TDoubleFunction;
import gnu.trove.TDoubleCollection;

public interface TDoubleList extends TDoubleCollection
{
    double getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean add(final double p0);
    
    void add(final double[] p0);
    
    void add(final double[] p0, final int p1, final int p2);
    
    void insert(final int p0, final double p1);
    
    void insert(final int p0, final double[] p1);
    
    void insert(final int p0, final double[] p1, final int p2, final int p3);
    
    double get(final int p0);
    
    double set(final int p0, final double p1);
    
    void set(final int p0, final double[] p1);
    
    void set(final int p0, final double[] p1, final int p2, final int p3);
    
    double replace(final int p0, final double p1);
    
    void clear();
    
    boolean remove(final double p0);
    
    double removeAt(final int p0);
    
    void remove(final int p0, final int p1);
    
    void transformValues(final TDoubleFunction p0);
    
    void reverse();
    
    void reverse(final int p0, final int p1);
    
    void shuffle(final Random p0);
    
    TDoubleList subList(final int p0, final int p1);
    
    double[] toArray();
    
    double[] toArray(final int p0, final int p1);
    
    double[] toArray(final double[] p0);
    
    double[] toArray(final double[] p0, final int p1, final int p2);
    
    double[] toArray(final double[] p0, final int p1, final int p2, final int p3);
    
    boolean forEach(final TDoubleProcedure p0);
    
    boolean forEachDescending(final TDoubleProcedure p0);
    
    void sort();
    
    void sort(final int p0, final int p1);
    
    void fill(final double p0);
    
    void fill(final int p0, final int p1, final double p2);
    
    int binarySearch(final double p0);
    
    int binarySearch(final double p0, final int p1, final int p2);
    
    int indexOf(final double p0);
    
    int indexOf(final int p0, final double p1);
    
    int lastIndexOf(final double p0);
    
    int lastIndexOf(final int p0, final double p1);
    
    boolean contains(final double p0);
    
    TDoubleList grep(final TDoubleProcedure p0);
    
    TDoubleList inverseGrep(final TDoubleProcedure p0);
    
    double max();
    
    double min();
    
    double sum();
}
