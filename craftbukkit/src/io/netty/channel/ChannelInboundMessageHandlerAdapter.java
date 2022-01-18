// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.Buf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.MessageBuf;
import io.netty.util.internal.TypeParameterMatcher;
import io.netty.util.Signal;

public abstract class ChannelInboundMessageHandlerAdapter<I> extends ChannelStateHandlerAdapter implements ChannelInboundMessageHandler<I>, ChannelHandlerUtil.SingleInboundMessageHandler<I>
{
    protected static final Signal ABORT;
    private final TypeParameterMatcher msgMatcher;
    
    protected ChannelInboundMessageHandlerAdapter() {
        this.msgMatcher = TypeParameterMatcher.find(this, ChannelInboundMessageHandlerAdapter.class, "I");
    }
    
    protected ChannelInboundMessageHandlerAdapter(final Class<? extends I> inboundMessageType) {
        this.msgMatcher = TypeParameterMatcher.get(inboundMessageType);
    }
    
    @Override
    public MessageBuf<I> newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return Unpooled.messageBuffer();
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        ctx.inboundMessageBuffer().release();
    }
    
    @Override
    public final void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        ChannelHandlerUtil.handleInboundBufferUpdated(ctx, (ChannelHandlerUtil.SingleInboundMessageHandler<Object>)this);
    }
    
    @Override
    public boolean acceptInboundMessage(final Object msg) throws Exception {
        return this.msgMatcher.match(msg);
    }
    
    @Override
    public boolean beginMessageReceived(final ChannelHandlerContext ctx) throws Exception {
        return true;
    }
    
    @Override
    public void endMessageReceived(final ChannelHandlerContext ctx) throws Exception {
    }
    
    static {
        ABORT = ChannelHandlerUtil.ABORT;
    }
}
