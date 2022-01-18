// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TLongIntIterator extends TAdvancingIterator
{
    long key();
    
    int value();
    
    int setValue(final int p0);
}
