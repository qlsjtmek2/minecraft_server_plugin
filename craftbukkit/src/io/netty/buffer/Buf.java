// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

public interface Buf extends ReferenceCounted
{
    BufType type();
    
    int maxCapacity();
    
    boolean isReadable();
    
    boolean isReadable(final int p0);
    
    boolean isWritable();
    
    boolean isWritable(final int p0);
    
    Buf retain();
    
    Buf retain(final int p0);
}
