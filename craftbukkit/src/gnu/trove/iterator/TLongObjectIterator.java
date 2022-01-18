// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TLongObjectIterator<V> extends TAdvancingIterator
{
    long key();
    
    V value();
    
    V setValue(final V p0);
}
