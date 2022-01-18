// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TFloatObjectIterator<V> extends TAdvancingIterator
{
    float key();
    
    V value();
    
    V setValue(final V p0);
}
