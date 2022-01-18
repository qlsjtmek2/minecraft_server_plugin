// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class LineBasedFrameDecoder extends ByteToMessageDecoder
{
    private final int maxLength;
    private final boolean failFast;
    private final boolean stripDelimiter;
    private boolean discarding;
    
    public LineBasedFrameDecoder(final int maxLength) {
        this(maxLength, true, false);
    }
    
    public LineBasedFrameDecoder(final int maxLength, final boolean stripDelimiter, final boolean failFast) {
        this.maxLength = maxLength;
        this.failFast = failFast;
        this.stripDelimiter = stripDelimiter;
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final ByteBuf buffer) throws Exception {
        final int eol = findEndOfLine(buffer);
        if (eol == -1) {
            final int buffered = buffer.readableBytes();
            if (!this.discarding && buffered > this.maxLength) {
                this.discarding = true;
                if (this.failFast) {
                    this.fail(ctx, buffered + " bytes buffered already");
                }
            }
            if (this.discarding) {
                buffer.skipBytes(buffer.readableBytes());
            }
            return null;
        }
        final int length = eol - buffer.readerIndex();
        assert length >= 0 : "Invalid length=" + length;
        ByteBuf frame;
        if (this.discarding) {
            frame = null;
            buffer.skipBytes(length);
            if (!this.failFast) {
                this.fail(ctx, "over " + (this.maxLength + length) + " bytes");
            }
        }
        else {
            final byte delim = buffer.getByte(buffer.readerIndex() + length);
            int delimLength;
            if (delim == 13) {
                delimLength = 2;
            }
            else {
                delimLength = 1;
            }
            if (this.stripDelimiter) {
                frame = buffer.readBytes(length);
                buffer.skipBytes(delimLength);
            }
            else {
                frame = buffer.readBytes(length + delimLength);
            }
        }
        return frame;
    }
    
    private void fail(final ChannelHandlerContext ctx, final String msg) {
        ctx.fireExceptionCaught((Throwable)new TooLongFrameException("Frame length exceeds " + this.maxLength + " (" + msg + ')'));
    }
    
    private static int findEndOfLine(final ByteBuf buffer) {
        for (int n = buffer.writerIndex(), i = buffer.readerIndex(); i < n; ++i) {
            final byte b = buffer.getByte(i);
            if (b == 10) {
                return i;
            }
            if (b == 13 && i < n - 1 && buffer.getByte(i + 1) == 10) {
                return i;
            }
        }
        return -1;
    }
}
