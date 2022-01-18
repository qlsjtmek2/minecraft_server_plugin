// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

public interface SpdyPingFrame extends SpdyControlFrame
{
    int getId();
    
    SpdyPingFrame setId(final int p0);
}
