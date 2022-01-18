// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.queue;

import gnu.trove.TDoubleCollection;

public interface TDoubleQueue extends TDoubleCollection
{
    double element();
    
    boolean offer(final double p0);
    
    double peek();
    
    double poll();
}
