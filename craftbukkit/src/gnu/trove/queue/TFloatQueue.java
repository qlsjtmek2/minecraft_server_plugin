// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.queue;

import gnu.trove.TFloatCollection;

public interface TFloatQueue extends TFloatCollection
{
    float element();
    
    boolean offer(final float p0);
    
    float peek();
    
    float poll();
}
