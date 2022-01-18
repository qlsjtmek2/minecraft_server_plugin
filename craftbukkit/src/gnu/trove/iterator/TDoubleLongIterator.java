// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TDoubleLongIterator extends TAdvancingIterator
{
    double key();
    
    long value();
    
    long setValue(final long p0);
}
