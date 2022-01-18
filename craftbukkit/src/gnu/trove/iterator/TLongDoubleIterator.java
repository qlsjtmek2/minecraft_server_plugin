// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TLongDoubleIterator extends TAdvancingIterator
{
    long key();
    
    double value();
    
    double setValue(final double p0);
}
