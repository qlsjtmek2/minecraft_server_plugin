// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.buffer.Buf;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.buffer.ByteBuf;
import io.netty.util.Signal;

public abstract class ReplayingDecoder<S> extends ByteToMessageDecoder
{
    static final Signal REPLAY;
    private ByteBuf cumulation;
    private ReplayingDecoderBuffer replayable;
    private S state;
    private int checkpoint;
    private boolean decodeWasNull;
    
    protected ReplayingDecoder() {
        this(null);
    }
    
    protected ReplayingDecoder(final S initialState) {
        this.checkpoint = -1;
        this.state = initialState;
    }
    
    protected void checkpoint() {
        this.checkpoint = this.cumulation.readerIndex();
    }
    
    protected void checkpoint(final S state) {
        this.checkpoint();
        this.state(state);
    }
    
    protected S state() {
        return this.state;
    }
    
    protected S state(final S newState) {
        final S oldState = this.state;
        this.state = newState;
        return oldState;
    }
    
    protected int actualReadableBytes() {
        return this.internalBuffer().readableBytes();
    }
    
    protected ByteBuf internalBuffer() {
        return this.cumulation;
    }
    
    @Override
    public final ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.cumulation = this.newInboundBuffer0(ctx);
        this.replayable = new ReplayingDecoderBuffer(this.cumulation);
        return this.cumulation;
    }
    
    protected ByteBuf newInboundBuffer0(final ChannelHandlerContext ctx) throws Exception {
        return super.newInboundBuffer(ctx);
    }
    
    @Override
    public final void discardInboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        final ByteBuf in = ctx.inboundByteBuffer();
        final int oldReaderIndex = in.readerIndex();
        this.discardInboundReadBytes0(ctx);
        final int newReaderIndex = in.readerIndex();
        this.checkpoint -= oldReaderIndex - newReaderIndex;
    }
    
    protected void discardInboundReadBytes0(final ChannelHandlerContext ctx) throws Exception {
        super.discardInboundReadBytes(ctx);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        try {
            this.replayable.terminate();
            final ByteBuf in = this.cumulation;
            if (in.isReadable()) {
                this.callDecode(ctx, in);
            }
            if (ctx.nextInboundMessageBuffer().unfoldAndAdd(this.decodeLast(ctx, this.replayable))) {
                ctx.fireInboundBufferUpdated();
            }
        }
        catch (Signal replay) {
            replay.expect(ReplayingDecoder.REPLAY);
        }
        catch (Throwable t) {
            if (t instanceof CodecException) {
                ctx.fireExceptionCaught(t);
            }
            else {
                ctx.fireExceptionCaught((Throwable)new DecoderException(t));
            }
        }
        finally {
            ctx.fireChannelInactive();
        }
    }
    
    @Override
    protected void callDecode(final ChannelHandlerContext ctx, final ByteBuf buf) {
        boolean wasNull = false;
        final ByteBuf in = this.cumulation;
        final MessageBuf<Object> out = ctx.nextInboundMessageBuffer();
        boolean decoded = false;
        while (in.isReadable()) {
            try {
                final int readerIndex = in.readerIndex();
                this.checkpoint = readerIndex;
                final int oldReaderIndex = readerIndex;
                Object result = null;
                final S oldState = this.state;
                try {
                    result = this.decode(ctx, this.replayable);
                    if (result == null) {
                        if (oldReaderIndex == in.readerIndex() && oldState == this.state) {
                            throw new IllegalStateException("null cannot be returned if no data is consumed and state didn't change.");
                        }
                        continue;
                    }
                }
                catch (Signal replay) {
                    replay.expect(ReplayingDecoder.REPLAY);
                    final int checkpoint = this.checkpoint;
                    if (checkpoint >= 0) {
                        in.readerIndex(checkpoint);
                    }
                }
                if (result == null) {
                    wasNull = true;
                }
                else {
                    wasNull = false;
                    if (oldReaderIndex == in.readerIndex() && oldState == this.state) {
                        throw new IllegalStateException("decode() method must consume at least one byte if it returned a decoded message (caused by: " + this.getClass() + ')');
                    }
                    if (!out.unfoldAndAdd(result)) {
                        continue;
                    }
                    decoded = true;
                    if (this.isSingleDecode()) {
                        break;
                    }
                    continue;
                    continue;
                }
            }
            catch (Throwable t) {
                if (decoded) {
                    decoded = false;
                    ctx.fireInboundBufferUpdated();
                }
                if (t instanceof CodecException) {
                    ctx.fireExceptionCaught(t);
                }
                else {
                    ctx.fireExceptionCaught((Throwable)new DecoderException(t));
                }
            }
            break;
        }
        if (decoded) {
            this.decodeWasNull = false;
            ctx.fireInboundBufferUpdated();
        }
        else if (wasNull) {
            this.decodeWasNull = true;
        }
    }
    
    @Override
    public void channelReadSuspended(final ChannelHandlerContext ctx) throws Exception {
        if (this.decodeWasNull) {
            this.decodeWasNull = false;
            if (!ctx.channel().config().isAutoRead()) {
                ctx.read();
            }
        }
        super.channelReadSuspended(ctx);
    }
    
    static {
        REPLAY = new Signal(ReplayingDecoder.class.getName() + ".REPLAY");
    }
}
