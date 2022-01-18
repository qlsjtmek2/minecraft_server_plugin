// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.lib.gnu.trove.iterator;

public interface TIntObjectIterator<V> extends TAdvancingIterator
{
    int key();
    
    V value();
    
    V setValue(final V p0);
}
