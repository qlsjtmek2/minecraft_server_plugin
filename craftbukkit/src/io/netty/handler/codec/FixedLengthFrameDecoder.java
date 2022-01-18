// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.buffer.Buf;
import io.netty.channel.ChannelHandlerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class FixedLengthFrameDecoder extends ByteToMessageDecoder
{
    private final int frameLength;
    private final boolean allocateFullBuffer;
    
    public FixedLengthFrameDecoder(final int frameLength) {
        this(frameLength, false);
    }
    
    public FixedLengthFrameDecoder(final int frameLength, final boolean allocateFullBuffer) {
        if (frameLength <= 0) {
            throw new IllegalArgumentException("frameLength must be a positive integer: " + frameLength);
        }
        this.frameLength = frameLength;
        this.allocateFullBuffer = allocateFullBuffer;
    }
    
    @Override
    public ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        if (this.allocateFullBuffer) {
            return ChannelHandlerUtil.allocate(ctx, this.frameLength);
        }
        return super.newInboundBuffer(ctx);
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        if (in.readableBytes() < this.frameLength) {
            return null;
        }
        return in.readBytes(this.frameLength);
    }
}
