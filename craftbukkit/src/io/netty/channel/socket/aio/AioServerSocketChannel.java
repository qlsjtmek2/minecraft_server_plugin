// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket.aio;

import java.nio.channels.AsynchronousCloseException;
import io.netty.channel.aio.AioCompletionHandler;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.buffer.BufType;
import io.netty.channel.ChannelConfig;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.channel.aio.AioEventLoopGroup;
import io.netty.channel.ChannelPromise;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannel;
import io.netty.channel.Channel;
import java.io.IOException;
import io.netty.channel.ChannelException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousChannelGroup;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.aio.AbstractAioChannel;

public class AioServerSocketChannel extends AbstractAioChannel implements ServerSocketChannel
{
    private static final ChannelMetadata METADATA;
    private static final AcceptHandler ACCEPT_HANDLER;
    private static final InternalLogger logger;
    private final AioServerSocketChannelConfig config;
    private boolean acceptInProgress;
    private boolean closed;
    
    private static AsynchronousServerSocketChannel newSocket(final AsynchronousChannelGroup group) {
        try {
            return AsynchronousServerSocketChannel.open(group);
        }
        catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }
    
    public AioServerSocketChannel() {
        super(null, null, null);
        this.config = new AioServerSocketChannelConfig(this);
    }
    
    public AioServerSocketChannel(final AsynchronousServerSocketChannel channel) {
        super(null, null, channel);
        this.config = new AioServerSocketChannelConfig(this, channel);
    }
    
    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }
    
    @Override
    protected AsynchronousServerSocketChannel javaChannel() {
        return (AsynchronousServerSocketChannel)super.javaChannel();
    }
    
    @Override
    public boolean isActive() {
        return this.ch != null && this.javaChannel().isOpen() && this.localAddress0() != null;
    }
    
    @Override
    public ChannelMetadata metadata() {
        return AioServerSocketChannel.METADATA;
    }
    
    @Override
    protected SocketAddress localAddress0() {
        if (this.ch == null) {
            return null;
        }
        try {
            return this.javaChannel().getLocalAddress();
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }
    
    @Override
    protected void doBind(final SocketAddress localAddress) throws Exception {
        final AsynchronousServerSocketChannel ch = this.javaChannel();
        ch.bind(localAddress, this.config.getBacklog());
    }
    
    @Override
    protected void doBeginRead() {
        if (this.acceptInProgress) {
            return;
        }
        this.acceptInProgress = true;
        this.javaChannel().accept(this, AioServerSocketChannel.ACCEPT_HANDLER);
    }
    
    @Override
    protected void doClose() throws Exception {
        if (!this.closed) {
            this.closed = true;
            this.javaChannel().close();
        }
    }
    
    @Override
    protected boolean isFlushPending() {
        return false;
    }
    
    @Override
    protected void doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
        promise.setFailure((Throwable)new UnsupportedOperationException());
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected Runnable doRegister() throws Exception {
        final Runnable task = super.doRegister();
        if (this.ch == null) {
            final AsynchronousServerSocketChannel channel = newSocket(((AioEventLoopGroup)this.eventLoop().parent()).channelGroup());
            this.ch = channel;
            this.config.assign(channel);
        }
        return task;
    }
    
    @Override
    public ServerSocketChannelConfig config() {
        return this.config;
    }
    
    static {
        METADATA = new ChannelMetadata(BufType.MESSAGE, false);
        ACCEPT_HANDLER = new AcceptHandler();
        logger = InternalLoggerFactory.getInstance(AioServerSocketChannel.class);
    }
    
    private static final class AcceptHandler extends AioCompletionHandler<AsynchronousSocketChannel, AioServerSocketChannel>
    {
        @Override
        protected void completed0(final AsynchronousSocketChannel ch, final AioServerSocketChannel channel) {
            channel.acceptInProgress = false;
            channel.pipeline().inboundMessageBuffer().add(new AioSocketChannel(channel, null, ch));
            channel.pipeline().fireInboundBufferUpdated();
            channel.pipeline().fireChannelReadSuspended();
        }
        
        @Override
        protected void failed0(final Throwable t, final AioServerSocketChannel channel) {
            channel.acceptInProgress = false;
            boolean asyncClosed = false;
            if (t instanceof AsynchronousCloseException) {
                asyncClosed = true;
                channel.closed = true;
            }
            if (channel.isOpen() && !asyncClosed) {
                AioServerSocketChannel.logger.warn("Failed to create a new channel from an accepted socket.", t);
            }
        }
    }
}
