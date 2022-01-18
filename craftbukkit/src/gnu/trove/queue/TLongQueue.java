// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.queue;

import gnu.trove.TLongCollection;

public interface TLongQueue extends TLongCollection
{
    long element();
    
    boolean offer(final long p0);
    
    long peek();
    
    long poll();
}
