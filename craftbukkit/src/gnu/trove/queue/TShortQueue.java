// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.queue;

import gnu.trove.TShortCollection;

public interface TShortQueue extends TShortCollection
{
    short element();
    
    boolean offer(final short p0);
    
    short peek();
    
    short poll();
}
