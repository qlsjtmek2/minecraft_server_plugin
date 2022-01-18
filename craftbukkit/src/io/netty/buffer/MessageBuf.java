// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import java.util.Collection;
import java.util.Queue;

public interface MessageBuf<T> extends Buf, Queue<T>
{
    boolean unfoldAndAdd(final Object p0);
    
    int drainTo(final Collection<? super T> p0);
    
    int drainTo(final Collection<? super T> p0, final int p1);
    
    MessageBuf<T> retain(final int p0);
    
    MessageBuf<T> retain();
}
