// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TLongShortIterator extends TAdvancingIterator
{
    long key();
    
    short value();
    
    short setValue(final short p0);
}
