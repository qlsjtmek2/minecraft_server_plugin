// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;

public class DefaultSpdyRstStreamFrame implements SpdyRstStreamFrame
{
    private int streamId;
    private SpdyStreamStatus status;
    
    public DefaultSpdyRstStreamFrame(final int streamId, final int statusCode) {
        this(streamId, SpdyStreamStatus.valueOf(statusCode));
    }
    
    public DefaultSpdyRstStreamFrame(final int streamId, final SpdyStreamStatus status) {
        this.setStreamId(streamId);
        this.setStatus(status);
    }
    
    @Override
    public int getStreamId() {
        return this.streamId;
    }
    
    @Override
    public SpdyRstStreamFrame setStreamId(final int streamId) {
        if (streamId <= 0) {
            throw new IllegalArgumentException("Stream-ID must be positive: " + streamId);
        }
        this.streamId = streamId;
        return this;
    }
    
    @Override
    public SpdyStreamStatus getStatus() {
        return this.status;
    }
    
    @Override
    public SpdyRstStreamFrame setStatus(final SpdyStreamStatus status) {
        this.status = status;
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
        buf.append("--> Status: ");
        buf.append(this.status.toString());
        return buf.toString();
    }
}
