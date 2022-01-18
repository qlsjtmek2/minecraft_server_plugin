// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket.nio;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.buffer.BufType;
import io.netty.channel.ChannelConfig;
import java.nio.channels.SelectionKey;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import io.netty.buffer.ByteBuf;
import java.net.SocketAddress;
import io.netty.channel.EventLoop;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelFuture;
import java.net.InetSocketAddress;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.DefaultSocketChannelConfig;
import java.nio.channels.SelectableChannel;
import io.netty.channel.Channel;
import java.io.IOException;
import io.netty.channel.ChannelException;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.nio.AbstractNioByteChannel;

public class NioSocketChannel extends AbstractNioByteChannel implements SocketChannel
{
    private static final ChannelMetadata METADATA;
    private static final InternalLogger logger;
    private final SocketChannelConfig config;
    
    private static java.nio.channels.SocketChannel newSocket() {
        try {
            return java.nio.channels.SocketChannel.open();
        }
        catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }
    
    public NioSocketChannel() {
        this(newSocket());
    }
    
    public NioSocketChannel(final java.nio.channels.SocketChannel socket) {
        this(null, null, socket);
    }
    
    public NioSocketChannel(final Channel parent, final Integer id, final java.nio.channels.SocketChannel socket) {
        super(parent, id, socket);
        try {
            socket.configureBlocking(false);
        }
        catch (IOException e3) {
            try {
                socket.close();
            }
            catch (IOException e2) {
                if (NioSocketChannel.logger.isWarnEnabled()) {
                    NioSocketChannel.logger.warn("Failed to close a partially initialized socket.", e2);
                }
            }
            throw new ChannelException("Failed to enter non-blocking mode.", e3);
        }
        this.config = new DefaultSocketChannelConfig(this, socket.socket());
    }
    
    @Override
    public ServerSocketChannel parent() {
        return (ServerSocketChannel)super.parent();
    }
    
    @Override
    public ChannelMetadata metadata() {
        return NioSocketChannel.METADATA;
    }
    
    @Override
    public SocketChannelConfig config() {
        return this.config;
    }
    
    @Override
    protected java.nio.channels.SocketChannel javaChannel() {
        return (java.nio.channels.SocketChannel)super.javaChannel();
    }
    
    @Override
    public boolean isActive() {
        final java.nio.channels.SocketChannel ch = this.javaChannel();
        return ch.isOpen() && ch.isConnected();
    }
    
    @Override
    public boolean isInputShutdown() {
        return super.isInputShutdown();
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
    public boolean isOutputShutdown() {
        return this.javaChannel().socket().isOutputShutdown() || !this.isActive();
    }
    
    @Override
    public ChannelFuture shutdownOutput() {
        return this.shutdownOutput(this.newPromise());
    }
    
    @Override
    public ChannelFuture shutdownOutput(final ChannelPromise promise) {
        final EventLoop loop = this.eventLoop();
        if (loop.inEventLoop()) {
            try {
                this.javaChannel().socket().shutdownOutput();
                promise.setSuccess();
            }
            catch (Throwable t) {
                promise.setFailure(t);
            }
        }
        else {
            loop.execute(new Runnable() {
                @Override
                public void run() {
                    NioSocketChannel.this.shutdownOutput(promise);
                }
            });
        }
        return promise;
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.javaChannel().socket().getLocalSocketAddress();
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return this.javaChannel().socket().getRemoteSocketAddress();
    }
    
    @Override
    protected void doBind(final SocketAddress localAddress) throws Exception {
        this.javaChannel().socket().bind(localAddress);
    }
    
    @Override
    protected boolean doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            this.javaChannel().socket().bind(localAddress);
        }
        boolean success = false;
        try {
            final boolean connected = this.javaChannel().connect(remoteAddress);
            if (!connected) {
                this.selectionKey().interestOps(8);
            }
            success = true;
            return connected;
        }
        finally {
            if (!success) {
                this.doClose();
            }
        }
    }
    
    @Override
    protected void doFinishConnect() throws Exception {
        if (!this.javaChannel().finishConnect()) {
            throw new Error();
        }
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }
    
    @Override
    protected int doReadBytes(final ByteBuf byteBuf) throws Exception {
        return byteBuf.writeBytes(this.javaChannel(), byteBuf.writableBytes());
    }
    
    @Override
    protected int doWriteBytes(final ByteBuf buf, final boolean lastSpin) throws Exception {
        final int expectedWrittenBytes = buf.readableBytes();
        final int writtenBytes = buf.readBytes(this.javaChannel(), expectedWrittenBytes);
        final SelectionKey key = this.selectionKey();
        final int interestOps = key.interestOps();
        if (writtenBytes >= expectedWrittenBytes) {
            if ((interestOps & 0x4) != 0x0) {
                key.interestOps(interestOps & 0xFFFFFFFB);
            }
        }
        else if ((writtenBytes > 0 || lastSpin) && (interestOps & 0x4) == 0x0) {
            key.interestOps(interestOps | 0x4);
        }
        return writtenBytes;
    }
    
    static {
        METADATA = new ChannelMetadata(BufType.BYTE, false);
        logger = InternalLoggerFactory.getInstance(NioSocketChannel.class);
    }
}
