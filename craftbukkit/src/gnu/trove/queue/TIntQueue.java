// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.queue;

import gnu.trove.TIntCollection;

public interface TIntQueue extends TIntCollection
{
    int element();
    
    boolean offer(final int p0);
    
    int peek();
    
    int poll();
}
