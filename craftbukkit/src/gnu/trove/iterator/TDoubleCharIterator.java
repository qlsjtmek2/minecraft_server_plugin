// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TDoubleCharIterator extends TAdvancingIterator
{
    double key();
    
    char value();
    
    char setValue(final char p0);
}