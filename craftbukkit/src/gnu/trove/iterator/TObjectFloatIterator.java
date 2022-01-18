// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TObjectFloatIterator<K> extends TAdvancingIterator
{
    K key();
    
    float value();
    
    float setValue(final float p0);
}
