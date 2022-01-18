// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TObjectByteIterator<K> extends TAdvancingIterator
{
    K key();
    
    byte value();
    
    byte setValue(final byte p0);
}
