// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TObjectLongIterator<K> extends TAdvancingIterator
{
    K key();
    
    long value();
    
    long setValue(final long p0);
}
