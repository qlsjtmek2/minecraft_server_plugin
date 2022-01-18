// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import java.net.SocketAddress;

public abstract class ChannelDuplexHandler extends ChannelStateHandlerAdapter implements ChannelOperationHandler
{
    @Override
    public void bind(final ChannelHandlerContext ctx, final SocketAddress localAddress, final ChannelPromise future) throws Exception {
        ctx.bind(localAddress, future);
    }
    
    @Override
    public void connect(final ChannelHandlerContext ctx, final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise future) throws Exception {
        ctx.connect(remoteAddress, localAddress, future);
    }
    
    @Override
    public void disconnect(final ChannelHandlerContext ctx, final ChannelPromise future) throws Exception {
        ctx.disconnect(future);
    }
    
    @Override
    public void close(final ChannelHandlerContext ctx, final ChannelPromise future) throws Exception {
        ctx.close(future);
    }
    
    @Override
    public void deregister(final ChannelHandlerContext ctx, final ChannelPromise future) throws Exception {
        ctx.deregister(future);
    }
    
    @Override
    public void read(final ChannelHandlerContext ctx) {
        ctx.read();
    }
    
    @Override
    public void sendFile(final ChannelHandlerContext ctx, final FileRegion region, final ChannelPromise future) throws Exception {
        ctx.sendFile(region, future);
    }
}
