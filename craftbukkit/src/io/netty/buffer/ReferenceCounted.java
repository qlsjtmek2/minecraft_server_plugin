// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

public interface ReferenceCounted
{
    int refCnt();
    
    ReferenceCounted retain();
    
    ReferenceCounted retain(final int p0);
    
    boolean release();
    
    boolean release(final int p0);
}
