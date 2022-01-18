// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.iterator;

public interface TCharLongIterator extends TAdvancingIterator
{
    char key();
    
    long value();
    
    long setValue(final long p0);
}
