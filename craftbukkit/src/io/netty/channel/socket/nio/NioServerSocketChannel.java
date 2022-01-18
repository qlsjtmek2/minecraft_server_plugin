// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket.nio;

import io.netty.buffer.BufType;
import io.netty.channel.ChannelConfig;
import java.nio.channels.SocketChannel;
import io.netty.buffer.MessageBuf;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import io.netty.channel.socket.DefaultServerSocketChannelConfig;
import java.nio.channels.SelectableChannel;
import io.netty.channel.Channel;
import java.io.IOException;
import io.netty.channel.ChannelException;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.nio.AbstractNioMessageChannel;

public class NioServerSocketChannel extends AbstractNioMessageChannel implements ServerSocketChannel
{
    private static final ChannelMetadata METADATA;
    private final ServerSocketChannelConfig config;
    
    private static java.nio.channels.ServerSocketChannel newSocket() {
        try {
            return java.nio.channels.ServerSocketChannel.open();
        }
        catch (IOException e) {
            throw new ChannelException("Failed to open a server socket.", e);
        }
    }
    
    public NioServerSocketChannel() {
        super(null, null, newSocket(), 16);
        this.config = new DefaultServerSocketChannelConfig(this, this.javaChannel().socket());
    }
    
    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    public ChannelMetadata metadata() {
        return NioServerSocketChannel.METADATA;
    }
    
    @Override
    public ServerSocketChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isActive() {
        return this.javaChannel().socket().isBound();
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return null;
    }
    
    @Override
    protected java.nio.channels.ServerSocketChannel javaChannel() {
        return (java.nio.channels.ServerSocketChannel)super.javaChannel();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.javaChannel().socket().getLocalSocketAddress();
    }
    
    @Override
    protected void doBind(final SocketAddress localAddress) throws Exception {
        this.javaChannel().socket().bind(localAddress, this.config.getBacklog());
    }
    
    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }
    
    @Override
    protected int doReadMessages(final MessageBuf<Object> buf) throws Exception {
        final SocketChannel ch = this.javaChannel().accept();
        if (ch == null) {
            return 0;
        }
        buf.add(new NioSocketChannel(this, null, ch));
        return 1;
    }
    
    @Override
    protected boolean doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected void doFinishConnect() throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected int doWriteMessages(final MessageBuf<Object> buf, final boolean lastSpin) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    static {
        METADATA = new ChannelMetadata(BufType.MESSAGE, false);
    }
}
