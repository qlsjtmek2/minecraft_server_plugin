// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import io.netty.util.internal.StringUtil;

public class DefaultSpdyHeadersFrame extends DefaultSpdyHeaderBlock implements SpdyHeadersFrame
{
    private int streamId;
    private boolean last;
    
    public DefaultSpdyHeadersFrame(final int streamId) {
        this.setStreamId(streamId);
    }
    
    @Override
    public int getStreamId() {
        return this.streamId;
    }
    
    @Override
    public SpdyHeadersFrame setStreamId(final int streamId) {
        if (streamId <= 0) {
            throw new IllegalArgumentException("Stream-ID must be positive: " + streamId);
        }
        this.streamId = streamId;
        return this;
    }
    
    @Override
    public boolean isLast() {
        return this.last;
    }
    
    @Override
    public SpdyHeadersFrame setLast(final boolean last) {
        this.last = last;
        return this;
    }
    
    @Override
    public SpdyHeadersFrame setInvalid() {
        super.setInvalid();
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(this.getClass().getSimpleName());
        buf.append("(last: ");
        buf.append(this.isLast());
        buf.append(')');
        buf.append(StringUtil.NEWLINE);
        buf.append("--> Stream-ID = ");
        buf.append(this.streamId);
        buf.append(StringUtil.NEWLINE);
        buf.append("--> Headers:");
        buf.append(StringUtil.NEWLINE);
        this.appendHeaders(buf);
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }
}
