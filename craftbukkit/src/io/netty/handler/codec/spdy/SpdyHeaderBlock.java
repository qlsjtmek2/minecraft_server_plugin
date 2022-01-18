// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

public interface SpdyHeaderBlock
{
    boolean isInvalid();
    
    SpdyHeaderBlock setInvalid();
    
    SpdyHeaders headers();
}
