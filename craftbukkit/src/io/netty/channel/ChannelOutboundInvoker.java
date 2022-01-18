// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import java.net.SocketAddress;

interface ChannelOutboundInvoker
{
    ChannelFuture bind(final SocketAddress p0);
    
    ChannelFuture connect(final SocketAddress p0);
    
    ChannelFuture connect(final SocketAddress p0, final SocketAddress p1);
    
    ChannelFuture disconnect();
    
    ChannelFuture close();
    
    ChannelFuture deregister();
    
    ChannelFuture flush();
    
    ChannelFuture write(final Object p0);
    
    ChannelFuture sendFile(final FileRegion p0);
    
    ChannelFuture bind(final SocketAddress p0, final ChannelPromise p1);
    
    ChannelFuture connect(final SocketAddress p0, final ChannelPromise p1);
    
    ChannelFuture connect(final SocketAddress p0, final SocketAddress p1, final ChannelPromise p2);
    
    ChannelFuture disconnect(final ChannelPromise p0);
    
    ChannelFuture close(final ChannelPromise p0);
    
    ChannelFuture deregister(final ChannelPromise p0);
    
    void read();
    
    ChannelFuture flush(final ChannelPromise p0);
    
    ChannelFuture write(final Object p0, final ChannelPromise p1);
    
    ChannelFuture sendFile(final FileRegion p0, final ChannelPromise p1);
}
