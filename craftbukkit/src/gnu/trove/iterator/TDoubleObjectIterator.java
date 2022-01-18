// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TDoubleObjectIterator<V> extends TAdvancingIterator
{
    double key();
    
    V value();
    
    V setValue(final V p0);
}
