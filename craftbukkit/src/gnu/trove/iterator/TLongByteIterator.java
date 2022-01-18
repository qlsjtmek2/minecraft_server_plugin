// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TLongByteIterator extends TAdvancingIterator
{
    long key();
    
    byte value();
    
    byte setValue(final byte p0);
}
