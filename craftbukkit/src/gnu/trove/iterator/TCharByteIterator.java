// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TCharByteIterator extends TAdvancingIterator
{
    char key();
    
    byte value();
    
    byte setValue(final byte p0);
}
