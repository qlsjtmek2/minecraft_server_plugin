// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TLongCharIterator extends TAdvancingIterator
{
    long key();
    
    char value();
    
    char setValue(final char p0);
}
