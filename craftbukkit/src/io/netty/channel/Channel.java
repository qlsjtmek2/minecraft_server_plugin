// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import java.net.SocketAddress;
import io.netty.buffer.MessageBuf;
import io.netty.buffer.ByteBuf;
import io.netty.util.AttributeMap;

public interface Channel extends AttributeMap, ChannelOutboundInvoker, ChannelPropertyAccess, Comparable<Channel>
{
    Integer id();
    
    EventLoop eventLoop();
    
    Channel parent();
    
    ChannelConfig config();
    
    boolean isOpen();
    
    boolean isRegistered();
    
    boolean isActive();
    
    ChannelMetadata metadata();
    
    ByteBuf outboundByteBuffer();
    
     <T> MessageBuf<T> outboundMessageBuffer();
    
    SocketAddress localAddress();
    
    SocketAddress remoteAddress();
    
    ChannelFuture closeFuture();
    
    Unsafe unsafe();
    
    public interface Unsafe
    {
        ChannelHandlerContext directOutboundContext();
        
        ChannelPromise voidFuture();
        
        SocketAddress localAddress();
        
        SocketAddress remoteAddress();
        
        void register(final EventLoop p0, final ChannelPromise p1);
        
        void bind(final SocketAddress p0, final ChannelPromise p1);
        
        void connect(final SocketAddress p0, final SocketAddress p1, final ChannelPromise p2);
        
        void disconnect(final ChannelPromise p0);
        
        void close(final ChannelPromise p0);
        
        void closeForcibly();
        
        void deregister(final ChannelPromise p0);
        
        void beginRead();
        
        void flush(final ChannelPromise p0);
        
        void flushNow();
        
        void sendFile(final FileRegion p0, final ChannelPromise p1);
    }
}
