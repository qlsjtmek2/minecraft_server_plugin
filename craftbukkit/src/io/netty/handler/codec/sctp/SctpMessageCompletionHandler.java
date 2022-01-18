// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.sctp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import java.util.HashMap;
import io.netty.buffer.ByteBuf;
import java.util.Map;
import io.netty.channel.sctp.SctpMessage;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

public class SctpMessageCompletionHandler extends ChannelInboundMessageHandlerAdapter<SctpMessage>
{
    private final Map<Integer, ByteBuf> fragments;
    private boolean assembled;
    
    public SctpMessageCompletionHandler() {
        this.fragments = new HashMap<Integer, ByteBuf>();
    }
    
    @Override
    public boolean beginMessageReceived(final ChannelHandlerContext ctx) throws Exception {
        this.assembled = false;
        return super.beginMessageReceived(ctx);
    }
    
    @Override
    public void endMessageReceived(final ChannelHandlerContext ctx) throws Exception {
        if (this.assembled) {
            this.assembled = false;
            ctx.fireInboundBufferUpdated();
        }
        super.endMessageReceived(ctx);
    }
    
    @Override
    public void messageReceived(final ChannelHandlerContext ctx, final SctpMessage msg) throws Exception {
        final ByteBuf byteBuf = msg.data();
        final int protocolIdentifier = msg.protocolIdentifier();
        final int streamIdentifier = msg.streamIdentifier();
        final boolean isComplete = msg.isComplete();
        ByteBuf frag;
        if (this.fragments.containsKey(streamIdentifier)) {
            frag = this.fragments.remove(streamIdentifier);
        }
        else {
            frag = Unpooled.EMPTY_BUFFER;
        }
        if (isComplete && !frag.isReadable()) {
            this.handleAssembledMessage(ctx, msg);
        }
        else if (!isComplete && frag.isReadable()) {
            this.fragments.put(streamIdentifier, Unpooled.wrappedBuffer(frag, byteBuf));
        }
        else if (isComplete && frag.isReadable()) {
            this.fragments.remove(streamIdentifier);
            final SctpMessage assembledMsg = new SctpMessage(protocolIdentifier, streamIdentifier, Unpooled.wrappedBuffer(frag, byteBuf));
            this.handleAssembledMessage(ctx, assembledMsg);
        }
        else {
            this.fragments.put(streamIdentifier, byteBuf);
        }
        byteBuf.retain();
    }
    
    private void handleAssembledMessage(final ChannelHandlerContext ctx, final SctpMessage assembledMsg) {
        ctx.nextInboundMessageBuffer().add(assembledMsg);
        this.assembled = true;
    }
}
