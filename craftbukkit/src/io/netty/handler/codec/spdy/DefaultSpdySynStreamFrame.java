// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import io.netty.util.internal.StringUtil;

public class DefaultSpdySynStreamFrame extends DefaultSpdyHeaderBlock implements SpdySynStreamFrame
{
    private int streamId;
    private int associatedToStreamId;
    private byte priority;
    private boolean last;
    private boolean unidirectional;
    
    public DefaultSpdySynStreamFrame(final int streamId, final int associatedToStreamId, final byte priority) {
        this.setStreamId(streamId);
        this.setAssociatedToStreamId(associatedToStreamId);
        this.setPriority(priority);
    }
    
    @Override
    public int getStreamId() {
        return this.streamId;
    }
    
    @Override
    public SpdySynStreamFrame setStreamId(final int streamId) {
        if (streamId <= 0) {
            throw new IllegalArgumentException("Stream-ID must be positive: " + streamId);
        }
        this.streamId = streamId;
        return this;
    }
    
    @Override
    public int getAssociatedToStreamId() {
        return this.associatedToStreamId;
    }
    
    @Override
    public SpdySynStreamFrame setAssociatedToStreamId(final int associatedToStreamId) {
        if (associatedToStreamId < 0) {
            throw new IllegalArgumentException("Associated-To-Stream-ID cannot be negative: " + associatedToStreamId);
        }
        this.associatedToStreamId = associatedToStreamId;
        return this;
    }
    
    @Override
    public byte getPriority() {
        return this.priority;
    }
    
    @Override
    public SpdySynStreamFrame setPriority(final byte priority) {
        if (priority < 0 || priority > 7) {
            throw new IllegalArgumentException("Priority must be between 0 and 7 inclusive: " + priority);
        }
        this.priority = priority;
        return this;
    }
    
    @Override
    public boolean isLast() {
        return this.last;
    }
    
    @Override
    public SpdySynStreamFrame setLast(final boolean last) {
        this.last = last;
        return this;
    }
    
    @Override
    public boolean isUnidirectional() {
        return this.unidirectional;
    }
    
    @Override
    public SpdySynStreamFrame setUnidirectional(final boolean unidirectional) {
        this.unidirectional = unidirectional;
        return this;
    }
    
    @Override
    public SpdySynStreamFrame setInvalid() {
        super.setInvalid();
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(this.getClass().getSimpleName());
        buf.append("(last: ");
        buf.append(this.isLast());
        buf.append("; unidirectional: ");
        buf.append(this.isUnidirectional());
        buf.append(')');
        buf.append(StringUtil.NEWLINE);
        buf.append("--> Stream-ID = ");
        buf.append(this.streamId);
        buf.append(StringUtil.NEWLINE);
        if (this.associatedToStreamId != 0) {
            buf.append("--> Associated-To-Stream-ID = ");
            buf.append(this.associatedToStreamId);
            buf.append(StringUtil.NEWLINE);
        }
        buf.append("--> Priority = ");
        buf.append(this.priority);
        buf.append(StringUtil.NEWLINE);
        buf.append("--> Headers:");
        buf.append(StringUtil.NEWLINE);
        this.appendHeaders(buf);
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }
}
