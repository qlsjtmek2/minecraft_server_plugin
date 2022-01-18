// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

public interface SpdyStreamFrame
{
    int getStreamId();
    
    SpdyStreamFrame setStreamId(final int p0);
    
    boolean isLast();
    
    SpdyStreamFrame setLast(final boolean p0);
}
