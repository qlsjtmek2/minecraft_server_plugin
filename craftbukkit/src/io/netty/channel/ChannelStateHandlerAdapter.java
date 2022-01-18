// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

public abstract class ChannelStateHandlerAdapter extends ChannelHandlerAdapter implements ChannelStateHandler
{
    @Override
    public void channelRegistered(final ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
    }
    
    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
    }
    
    @Override
    public void channelReadSuspended(final ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadSuspended();
    }
    
    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt) throws Exception {
        ctx.fireUserEventTriggered(evt);
    }
}
