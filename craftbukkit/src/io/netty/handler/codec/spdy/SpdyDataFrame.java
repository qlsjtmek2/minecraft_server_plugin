// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface SpdyDataFrame extends ByteBufHolder, SpdyStreamFrame, SpdyDataOrControlFrame
{
    SpdyDataFrame setStreamId(final int p0);
    
    SpdyDataFrame setLast(final boolean p0);
    
    ByteBuf data();
    
    SpdyDataFrame copy();
    
    SpdyDataFrame retain();
    
    SpdyDataFrame retain(final int p0);
}
