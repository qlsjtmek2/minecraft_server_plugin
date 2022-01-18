// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

public interface SpdyHeadersFrame extends SpdyHeaderBlock, SpdyControlFrame, SpdyStreamFrame
{
    SpdyHeadersFrame setStreamId(final int p0);
    
    SpdyHeadersFrame setLast(final boolean p0);
    
    SpdyHeadersFrame setInvalid();
}
