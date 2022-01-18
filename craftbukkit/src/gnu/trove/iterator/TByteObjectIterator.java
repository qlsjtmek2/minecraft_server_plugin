// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TByteObjectIterator<V> extends TAdvancingIterator
{
    byte key();
    
    V value();
    
    V setValue(final V p0);
}
