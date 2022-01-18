// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TObjectShortIterator<K> extends TAdvancingIterator
{
    K key();
    
    short value();
    
    short setValue(final short p0);
}
