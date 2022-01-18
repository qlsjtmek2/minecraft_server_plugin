// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TShortObjectIterator<V> extends TAdvancingIterator
{
    short key();
    
    V value();
    
    V setValue(final V p0);
}
