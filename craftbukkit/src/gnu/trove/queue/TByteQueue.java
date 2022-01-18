// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.queue;

import gnu.trove.TByteCollection;

public interface TByteQueue extends TByteCollection
{
    byte element();
    
    boolean offer(final byte p0);
    
    byte peek();
    
    byte poll();
}
