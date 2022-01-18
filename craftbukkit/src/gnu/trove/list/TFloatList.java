// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.list;

import gnu.trove.procedure.TFloatProcedure;
import java.util.Random;
import gnu.trove.function.TFloatFunction;
import gnu.trove.TFloatCollection;

public interface TFloatList extends TFloatCollection
{
    float getNoEntryValue();
    
    int size();
    
    boolean isEmpty();
    
    boolean add(final float p0);
    
    void add(final float[] p0);
    
    void add(final float[] p0, final int p1, final int p2);
    
    void insert(final int p0, final float p1);
    
    void insert(final int p0, final float[] p1);
    
    void insert(final int p0, final float[] p1, final int p2, final int p3);
    
    float get(final int p0);
    
    float set(final int p0, final float p1);
    
    void set(final int p0, final float[] p1);
    
    void set(final int p0, final float[] p1, final int p2, final int p3);
    
    float replace(final int p0, final float p1);
    
    void clear();
    
    boolean remove(final float p0);
    
    float removeAt(final int p0);
    
    void remove(final int p0, final int p1);
    
    void transformValues(final TFloatFunction p0);
    
    void reverse();
    
    void reverse(final int p0, final int p1);
    
    void shuffle(final Random p0);
    
    TFloatList subList(final int p0, final int p1);
    
    float[] toArray();
    
    float[] toArray(final int p0, final int p1);
    
    float[] toArray(final float[] p0);
    
    float[] toArray(final float[] p0, final int p1, final int p2);
    
    float[] toArray(final float[] p0, final int p1, final int p2, final int p3);
    
    boolean forEach(final TFloatProcedure p0);
    
    boolean forEachDescending(final TFloatProcedure p0);
    
    void sort();
    
    void sort(final int p0, final int p1);
    
    void fill(final float p0);
    
    void fill(final int p0, final int p1, final float p2);
    
    int binarySearch(final float p0);
    
    int binarySearch(final float p0, final int p1, final int p2);
    
    int indexOf(final float p0);
    
    int indexOf(final int p0, final float p1);
    
    int lastIndexOf(final float p0);
    
    int lastIndexOf(final int p0, final float p1);
    
    boolean contains(final float p0);
    
    TFloatList grep(final TFloatProcedure p0);
    
    TFloatList inverseGrep(final TFloatProcedure p0);
    
    float max();
    
    float min();
    
    float sum();
}
