// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

public interface SpdyWindowUpdateFrame extends SpdyControlFrame
{
    int getStreamId();
    
    SpdyWindowUpdateFrame setStreamId(final int p0);
    
    int getDeltaWindowSize();
    
    SpdyWindowUpdateFrame setDeltaWindowSize(final int p0);
}
