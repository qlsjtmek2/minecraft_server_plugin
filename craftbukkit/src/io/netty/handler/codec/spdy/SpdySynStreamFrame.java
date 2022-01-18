// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

public interface SpdySynStreamFrame extends SpdyHeaderBlock, SpdyControlFrame, SpdyStreamFrame
{
    int getAssociatedToStreamId();
    
    SpdySynStreamFrame setAssociatedToStreamId(final int p0);
    
    byte getPriority();
    
    SpdySynStreamFrame setPriority(final byte p0);
    
    boolean isUnidirectional();
    
    SpdySynStreamFrame setUnidirectional(final boolean p0);
    
    SpdySynStreamFrame setStreamId(final int p0);
    
    SpdySynStreamFrame setLast(final boolean p0);
    
    SpdySynStreamFrame setInvalid();
}
