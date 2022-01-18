// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

public interface SpdyGoAwayFrame extends SpdyControlFrame
{
    int getLastGoodStreamId();
    
    SpdyGoAwayFrame setLastGoodStreamId(final int p0);
    
    SpdySessionStatus getStatus();
    
    SpdyGoAwayFrame setStatus(final SpdySessionStatus p0);
}
