// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TByteCharIterator extends TAdvancingIterator
{
    byte key();
    
    char value();
    
    char setValue(final char p0);
}
