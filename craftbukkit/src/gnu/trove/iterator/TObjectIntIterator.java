// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TObjectIntIterator<K> extends TAdvancingIterator
{
    K key();
    
    int value();
    
    int setValue(final int p0);
}
