// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket.aio;

import io.netty.channel.ChannelConfig;
import io.netty.buffer.ByteBufAllocator;
import java.util.Iterator;
import java.io.IOException;
import io.netty.channel.ChannelException;
import java.net.StandardSocketOptions;
import io.netty.channel.ChannelOption;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.NetUtil;
import io.netty.channel.Channel;
import java.net.SocketOption;
import java.util.Map;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.atomic.AtomicReference;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.channel.DefaultChannelConfig;

final class AioServerSocketChannelConfig extends DefaultChannelConfig implements ServerSocketChannelConfig
{
    private final AtomicReference<AsynchronousServerSocketChannel> javaChannel;
    private volatile int backlog;
    private Map<SocketOption<?>, Object> options;
    private static final int DEFAULT_SND_BUF_SIZE = 32768;
    private static final boolean DEFAULT_SO_REUSEADDR = false;
    
    AioServerSocketChannelConfig(final AioServerSocketChannel channel) {
        super(channel);
        this.javaChannel = new AtomicReference<AsynchronousServerSocketChannel>();
        this.backlog = NetUtil.SOMAXCONN;
        this.options = (Map<SocketOption<?>, Object>)PlatformDependent.newConcurrentHashMap();
    }
    
    AioServerSocketChannelConfig(final AioServerSocketChannel channel, final AsynchronousServerSocketChannel javaChannel) {
        super(channel);
        this.javaChannel = new AtomicReference<AsynchronousServerSocketChannel>();
        this.backlog = NetUtil.SOMAXCONN;
        this.options = (Map<SocketOption<?>, Object>)PlatformDependent.newConcurrentHashMap();
        this.javaChannel.set(javaChannel);
    }
    
    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG);
    }
    
    @Override
    public <T> T getOption(final ChannelOption<T> option) {
        if (option == ChannelOption.SO_RCVBUF) {
            return (T)this.getReceiveBufferSize();
        }
        if (option == ChannelOption.SO_REUSEADDR) {
            return (T)this.isReuseAddress();
        }
        if (option == ChannelOption.SO_BACKLOG) {
            return (T)this.getBacklog();
        }
        return super.getOption(option);
    }
    
    @Override
    public <T> boolean setOption(final ChannelOption<T> option, final T value) {
        this.validate(option, value);
        if (option == ChannelOption.SO_RCVBUF) {
            this.setReceiveBufferSize((int)value);
        }
        else if (option == ChannelOption.SO_REUSEADDR) {
            this.setReuseAddress((boolean)value);
        }
        else {
            if (option != ChannelOption.SO_BACKLOG) {
                return super.setOption(option, value);
            }
            this.setBacklog((int)value);
        }
        return true;
    }
    
    @Override
    public boolean isReuseAddress() {
        return (boolean)this.getOption(StandardSocketOptions.SO_REUSEADDR, false);
    }
    
    @Override
    public AioServerSocketChannelConfig setReuseAddress(final boolean reuseAddress) {
        this.setOption(StandardSocketOptions.SO_REUSEADDR, reuseAddress);
        return this;
    }
    
    @Override
    public int getReceiveBufferSize() {
        return (int)this.getOption(StandardSocketOptions.SO_RCVBUF, 32768);
    }
    
    @Override
    public AioServerSocketChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        this.setOption(StandardSocketOptions.SO_RCVBUF, receiveBufferSize);
        return this;
    }
    
    @Override
    public AioServerSocketChannelConfig setPerformancePreferences(final int connectionTime, final int latency, final int bandwidth) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int getBacklog() {
        return this.backlog;
    }
    
    @Override
    public AioServerSocketChannelConfig setBacklog(final int backlog) {
        if (backlog < 0) {
            throw new IllegalArgumentException("backlog: " + backlog);
        }
        this.backlog = backlog;
        return this;
    }
    
    private Object getOption(final SocketOption option, final Object defaultValue) {
        if (this.javaChannel.get() == null) {
            final Object value = this.options.get(option);
            if (value == null) {
                return defaultValue;
            }
            return value;
        }
        else {
            try {
                return this.javaChannel.get().getOption((SocketOption<Object>)option);
            }
            catch (IOException e) {
                throw new ChannelException(e);
            }
        }
    }
    
    private void setOption(final SocketOption option, final Object defaultValue) {
        if (this.javaChannel.get() == null) {
            this.options.put(option, defaultValue);
            return;
        }
        try {
            this.javaChannel.get().setOption((SocketOption<Object>)option, defaultValue);
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
    }
    
    void assign(final AsynchronousServerSocketChannel javaChannel) {
        if (javaChannel == null) {
            throw new NullPointerException("javaChannel");
        }
        if (this.javaChannel.compareAndSet(null, javaChannel)) {
            this.propagateOptions();
        }
    }
    
    private void propagateOptions() {
        for (final SocketOption option : this.options.keySet()) {
            final Object value = this.options.remove(option);
            if (value != null) {
                try {
                    this.javaChannel.get().setOption((SocketOption<Object>)option, value);
                }
                catch (IOException e) {
                    throw new ChannelException(e);
                }
            }
        }
        this.options = null;
    }
    
    @Override
    public AioServerSocketChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public AioServerSocketChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public AioServerSocketChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public AioServerSocketChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public ServerSocketChannelConfig setDefaultHandlerByteBufType(final ChannelConfig.ChannelHandlerByteBufType type) {
        super.setDefaultHandlerByteBufType(type);
        return this;
    }
}
