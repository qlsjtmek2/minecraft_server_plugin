// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.buffer.ReferenceCounted;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.internal.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.DefaultByteBufHolder;

public class DefaultSpdyDataFrame extends DefaultByteBufHolder implements SpdyDataFrame
{
    private int streamId;
    private boolean last;
    
    public DefaultSpdyDataFrame(final int streamId) {
        this(streamId, Unpooled.buffer(0));
    }
    
    public DefaultSpdyDataFrame(final int streamId, final ByteBuf data) {
        super(validate(data));
        this.setStreamId(streamId);
    }
    
    private static ByteBuf validate(final ByteBuf data) {
        if (data.readableBytes() > 16777215) {
            throw new IllegalArgumentException("data payload cannot exceed 16777215 bytes");
        }
        return data;
    }
    
    @Override
    public int getStreamId() {
        return this.streamId;
    }
    
    @Override
    public SpdyDataFrame setStreamId(final int streamId) {
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
    public SpdyDataFrame setLast(final boolean last) {
        this.last = last;
        return this;
    }
    
    @Override
    public DefaultSpdyDataFrame copy() {
        final DefaultSpdyDataFrame frame = new DefaultSpdyDataFrame(this.getStreamId(), this.data().copy());
        frame.setLast(this.isLast());
        return frame;
    }
    
    @Override
    public SpdyDataFrame retain() {
        super.retain();
        return this;
    }
    
    @Override
    public SpdyDataFrame retain(final int increment) {
        super.retain(increment);
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
        buf.append("--> Size = ");
        if (this.refCnt() == 0) {
            buf.append("(freed)");
        }
        else {
            buf.append(this.data().readableBytes());
        }
        return buf.toString();
    }
}
