// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

public interface SpdyRstStreamFrame extends SpdyControlFrame
{
    int getStreamId();
    
    SpdyControlFrame setStreamId(final int p0);
    
    SpdyStreamStatus getStatus();
    
    SpdyControlFrame setStatus(final SpdyStreamStatus p0);
}
