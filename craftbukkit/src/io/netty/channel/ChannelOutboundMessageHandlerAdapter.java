// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.Buf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.MessageBuf;
import io.netty.util.internal.TypeParameterMatcher;
import io.netty.util.Signal;

public abstract class ChannelOutboundMessageHandlerAdapter<I> extends ChannelOperationHandlerAdapter implements ChannelOutboundMessageHandler<I>, ChannelHandlerUtil.SingleOutboundMessageHandler<I>
{
    protected static final Signal ABORT;
    private final TypeParameterMatcher msgMatcher;
    private boolean closeOnFailedFlush;
    
    protected ChannelOutboundMessageHandlerAdapter() {
        this.closeOnFailedFlush = true;
        this.msgMatcher = TypeParameterMatcher.find(this, ChannelOutboundMessageHandlerAdapter.class, "I");
    }
    
    protected ChannelOutboundMessageHandlerAdapter(final Class<? extends I> outboundMessageType) {
        this.closeOnFailedFlush = true;
        this.msgMatcher = TypeParameterMatcher.get(outboundMessageType);
    }
    
    protected final boolean isCloseOnFailedFlush() {
        return this.closeOnFailedFlush;
    }
    
    protected final void setCloseOnFailedFlush(final boolean closeOnFailedFlush) {
        this.closeOnFailedFlush = closeOnFailedFlush;
    }
    
    @Override
    public MessageBuf<I> newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return Unpooled.messageBuffer();
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        ctx.outboundMessageBuffer().release();
    }
    
    @Override
    public final void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        ChannelHandlerUtil.handleFlush(ctx, promise, this.isCloseOnFailedFlush(), (ChannelHandlerUtil.SingleOutboundMessageHandler<Object>)this);
    }
    
    @Override
    public boolean acceptOutboundMessage(final Object msg) throws Exception {
        return this.msgMatcher.match(msg);
    }
    
    @Override
    public boolean beginFlush(final ChannelHandlerContext ctx) throws Exception {
        return true;
    }
    
    @Override
    public void endFlush(final ChannelHandlerContext ctx) throws Exception {
    }
    
    static {
        ABORT = ChannelHandlerUtil.ABORT;
    }
}
