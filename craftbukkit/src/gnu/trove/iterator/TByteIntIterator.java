// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TByteIntIterator extends TAdvancingIterator
{
    byte key();
    
    int value();
    
    int setValue(final int p0);
}
