// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.lib.gnu.trove.iterator;

public interface TObjectDoubleIterator<K> extends TAdvancingIterator
{
    K key();
    
    double value();
    
    double setValue(final double p0);
}
