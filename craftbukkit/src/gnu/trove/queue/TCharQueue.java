// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.queue;

import gnu.trove.TCharCollection;

public interface TCharQueue extends TCharCollection
{
    char element();
    
    boolean offer(final char p0);
    
    char peek();
    
    char poll();
}
