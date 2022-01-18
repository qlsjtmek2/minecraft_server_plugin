// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

public class DefaultSpdyWindowUpdateFrame implements SpdyWindowUpdateFrame
{
    private int streamId;
    private int deltaWindowSize;
    
    public DefaultSpdyWindowUpdateFrame(final int streamId, final int deltaWindowSize) {
        this.setStreamId(streamId);
        this.setDeltaWindowSize(deltaWindowSize);
    }
    
    @Override
    public int getStreamId() {
        return this.streamId;
    }
    
    @Override
    public SpdyWindowUpdateFrame setStreamId(final int streamId) {
        if (streamId <= 0) {
            throw new IllegalArgumentException("Stream-ID must be positive: " + streamId);
        }
        this.streamId = streamId;
        return this;
    }
    
    @Override
    public int getDeltaWindowSize() {
        return this.deltaWindowSize;
    }
    
    @Override
    public SpdyWindowUpdateFrame setDeltaWindowSize(final int deltaWindowSize) {
        if (deltaWindowSize <= 0) {
            throw new IllegalArgumentException("Delta-Window-Size must be positive: " + deltaWindowSize);
        }
        this.deltaWindowSize = deltaWindowSize;
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(this.getClass().getSimpleName());
        buf.append(StringUtil.NEWLINE);
        buf.append("--> Stream-ID = ");
        buf.append(this.streamId);
        buf.append(StringUtil.NEWLINE);
        buf.append("--> Delta-Window-Size = ");
        buf.append(this.deltaWindowSize);
        return buf.toString();
    }
}
