// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.buffer.Buf;
import io.netty.buffer.MessageBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOperationHandler;
import io.netty.channel.ChannelStateHandler;
import io.netty.channel.ChannelOutboundMessageHandler;
import io.netty.channel.ChannelInboundByteHandler;
import io.netty.channel.CombinedChannelDuplexHandler;

public final class SpdyFrameCodec extends CombinedChannelDuplexHandler implements ChannelInboundByteHandler, ChannelOutboundMessageHandler<SpdyDataOrControlFrame>
{
    public SpdyFrameCodec(final int version) {
        this(version, 8192, 16384, 6, 15, 8);
    }
    
    public SpdyFrameCodec(final int version, final int maxChunkSize, final int maxHeaderSize, final int compressionLevel, final int windowBits, final int memLevel) {
        super(new SpdyFrameDecoder(version, maxChunkSize, maxHeaderSize), new SpdyFrameEncoder(version, compressionLevel, windowBits, memLevel));
    }
    
    private SpdyFrameDecoder decoder() {
        return (SpdyFrameDecoder)this.stateHandler();
    }
    
    private SpdyFrameEncoder encoder() {
        return (SpdyFrameEncoder)this.operationHandler();
    }
    
    @Override
    public ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return this.decoder().newInboundBuffer(ctx);
    }
    
    @Override
    public void discardInboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        this.decoder().discardInboundReadBytes(ctx);
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.decoder().freeInboundBuffer(ctx);
    }
    
    @Override
    public MessageBuf<SpdyDataOrControlFrame> newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return this.encoder().newOutboundBuffer(ctx);
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.encoder().freeOutboundBuffer(ctx);
    }
}
