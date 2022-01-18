// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TCharObjectIterator<V> extends TAdvancingIterator
{
    char key();
    
    V value();
    
    V setValue(final V p0);
}
