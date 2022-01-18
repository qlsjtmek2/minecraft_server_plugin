// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import java.net.SocketAddress;

public abstract class ChannelOperationHandlerAdapter extends ChannelHandlerAdapter implements ChannelOperationHandler
{
    @Override
    public void bind(final ChannelHandlerContext ctx, final SocketAddress localAddress, final ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }
    
    @Override
    public void connect(final ChannelHandlerContext ctx, final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) throws Exception {
        ctx.connect(remoteAddress, localAddress, promise);
    }
    
    @Override
    public void disconnect(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        ctx.disconnect(promise);
    }
    
    @Override
    public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        ctx.close(promise);
    }
    
    @Override
    public void deregister(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        ctx.deregister(promise);
    }
    
    @Override
    public void read(final ChannelHandlerContext ctx) {
        ctx.read();
    }
    
    @Override
    public void sendFile(final ChannelHandlerContext ctx, final FileRegion region, final ChannelPromise promise) throws Exception {
        ctx.sendFile(region, promise);
    }
}
