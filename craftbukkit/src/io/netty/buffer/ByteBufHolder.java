// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

public interface ByteBufHolder extends ReferenceCounted
{
    ByteBuf data();
    
    ByteBufHolder copy();
    
    ByteBufHolder retain();
    
    ByteBufHolder retain(final int p0);
}
