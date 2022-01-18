// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TObjectCharIterator<K> extends TAdvancingIterator
{
    K key();
    
    char value();
    
    char setValue(final char p0);
}
