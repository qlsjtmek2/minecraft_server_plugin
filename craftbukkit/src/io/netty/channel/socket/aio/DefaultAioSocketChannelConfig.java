// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket.aio;

import io.netty.channel.socket.SocketChannelConfig;
import io.netty.channel.ChannelConfig;
import io.netty.buffer.ByteBufAllocator;
import java.util.Iterator;
import java.io.IOException;
import io.netty.channel.ChannelException;
import java.net.StandardSocketOptions;
import io.netty.channel.ChannelOption;
import io.netty.util.internal.PlatformDependent;
import io.netty.channel.Channel;
import java.net.SocketOption;
import java.util.Map;
import java.nio.channels.NetworkChannel;
import java.util.concurrent.atomic.AtomicReference;
import io.netty.channel.DefaultChannelConfig;

final class DefaultAioSocketChannelConfig extends DefaultChannelConfig implements AioSocketChannelConfig
{
    private final AtomicReference<NetworkChannel> javaChannel;
    private volatile boolean allowHalfClosure;
    private volatile long readTimeoutInMillis;
    private volatile long writeTimeoutInMillis;
    private Map<SocketOption<?>, Object> options;
    private static final int DEFAULT_RCV_BUF_SIZE = 32768;
    private static final int DEFAULT_SND_BUF_SIZE = 32768;
    private static final int DEFAULT_SO_LINGER = -1;
    private static final boolean DEFAULT_SO_KEEP_ALIVE = false;
    private static final int DEFAULT_IP_TOS = 0;
    private static final boolean DEFAULT_SO_REUSEADDR = false;
    private static final boolean DEFAULT_TCP_NODELAY = false;
    
    DefaultAioSocketChannelConfig(final AioSocketChannel channel) {
        super(channel);
        this.javaChannel = new AtomicReference<NetworkChannel>();
        this.options = (Map<SocketOption<?>, Object>)PlatformDependent.newConcurrentHashMap();
        this.enableTcpNoDelay();
    }
    
    DefaultAioSocketChannelConfig(final AioSocketChannel channel, final NetworkChannel javaChannel) {
        super(channel);
        this.javaChannel = new AtomicReference<NetworkChannel>();
        this.options = (Map<SocketOption<?>, Object>)PlatformDependent.newConcurrentHashMap();
        this.javaChannel.set(javaChannel);
        this.enableTcpNoDelay();
    }
    
    private void enableTcpNoDelay() {
        if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
            try {
                this.setTcpNoDelay(true);
            }
            catch (Exception ex) {}
        }
    }
    
    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.AIO_READ_TIMEOUT, ChannelOption.AIO_WRITE_TIMEOUT, ChannelOption.ALLOW_HALF_CLOSURE);
    }
    
    @Override
    public <T> T getOption(final ChannelOption<T> option) {
        if (option == ChannelOption.SO_RCVBUF) {
            return (T)this.getReceiveBufferSize();
        }
        if (option == ChannelOption.SO_SNDBUF) {
            return (T)this.getSendBufferSize();
        }
        if (option == ChannelOption.TCP_NODELAY) {
            return (T)this.isTcpNoDelay();
        }
        if (option == ChannelOption.SO_KEEPALIVE) {
            return (T)this.isKeepAlive();
        }
        if (option == ChannelOption.SO_REUSEADDR) {
            return (T)this.isReuseAddress();
        }
        if (option == ChannelOption.SO_LINGER) {
            return (T)this.getSoLinger();
        }
        if (option == ChannelOption.IP_TOS) {
            return (T)this.getTrafficClass();
        }
        if (option == ChannelOption.AIO_READ_TIMEOUT) {
            return (T)this.getReadTimeout();
        }
        if (option == ChannelOption.AIO_WRITE_TIMEOUT) {
            return (T)this.getWriteTimeout();
        }
        if (option == ChannelOption.ALLOW_HALF_CLOSURE) {
            return (T)this.isAllowHalfClosure();
        }
        return super.getOption(option);
    }
    
    @Override
    public <T> boolean setOption(final ChannelOption<T> option, final T value) {
        this.validate(option, value);
        if (option == ChannelOption.SO_RCVBUF) {
            this.setReceiveBufferSize((int)value);
        }
        else if (option == ChannelOption.SO_SNDBUF) {
            this.setSendBufferSize((int)value);
        }
        else if (option == ChannelOption.TCP_NODELAY) {
            this.setTcpNoDelay((boolean)value);
        }
        else if (option == ChannelOption.SO_KEEPALIVE) {
            this.setKeepAlive((boolean)value);
        }
        else if (option == ChannelOption.SO_REUSEADDR) {
            this.setReuseAddress((boolean)value);
        }
        else if (option == ChannelOption.SO_LINGER) {
            this.setSoLinger((int)value);
        }
        else if (option == ChannelOption.IP_TOS) {
            this.setTrafficClass((int)value);
        }
        else if (option == ChannelOption.AIO_READ_TIMEOUT) {
            this.setReadTimeout((long)value);
        }
        else if (option == ChannelOption.AIO_WRITE_TIMEOUT) {
            this.setWriteTimeout((long)value);
        }
        else {
            if (option != ChannelOption.ALLOW_HALF_CLOSURE) {
                return super.setOption(option, value);
            }
            this.setAllowHalfClosure((boolean)value);
        }
        return true;
    }
    
    @Override
    public int getReceiveBufferSize() {
        return (int)this.getOption(StandardSocketOptions.SO_RCVBUF, 32768);
    }
    
    @Override
    public int getSendBufferSize() {
        return (int)this.getOption(StandardSocketOptions.SO_SNDBUF, 32768);
    }
    
    @Override
    public int getSoLinger() {
        return (int)this.getOption(StandardSocketOptions.SO_LINGER, -1);
    }
    
    @Override
    public int getTrafficClass() {
        return (int)this.getOption(StandardSocketOptions.IP_TOS, 0);
    }
    
    @Override
    public boolean isKeepAlive() {
        return (boolean)this.getOption(StandardSocketOptions.SO_KEEPALIVE, false);
    }
    
    @Override
    public boolean isReuseAddress() {
        return (boolean)this.getOption(StandardSocketOptions.SO_REUSEADDR, false);
    }
    
    @Override
    public boolean isTcpNoDelay() {
        return (boolean)this.getOption(StandardSocketOptions.TCP_NODELAY, false);
    }
    
    @Override
    public AioSocketChannelConfig setKeepAlive(final boolean keepAlive) {
        this.setOption(StandardSocketOptions.SO_KEEPALIVE, keepAlive);
        return this;
    }
    
    @Override
    public AioSocketChannelConfig setPerformancePreferences(final int connectionTime, final int latency, final int bandwidth) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public AioSocketChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        this.setOption(StandardSocketOptions.SO_RCVBUF, receiveBufferSize);
        return this;
    }
    
    @Override
    public AioSocketChannelConfig setReuseAddress(final boolean reuseAddress) {
        this.setOption(StandardSocketOptions.SO_REUSEADDR, reuseAddress);
        return this;
    }
    
    @Override
    public AioSocketChannelConfig setSendBufferSize(final int sendBufferSize) {
        this.setOption(StandardSocketOptions.SO_SNDBUF, sendBufferSize);
        return this;
    }
    
    @Override
    public AioSocketChannelConfig setSoLinger(final int soLinger) {
        this.setOption(StandardSocketOptions.SO_LINGER, soLinger);
        return this;
    }
    
    @Override
    public AioSocketChannelConfig setTcpNoDelay(final boolean tcpNoDelay) {
        this.setOption(StandardSocketOptions.TCP_NODELAY, tcpNoDelay);
        return this;
    }
    
    @Override
    public AioSocketChannelConfig setTrafficClass(final int trafficClass) {
        this.setOption(StandardSocketOptions.IP_TOS, trafficClass);
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
            this.javaChannel.get().setOption(option, defaultValue);
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
    }
    
    @Override
    public AioSocketChannelConfig setReadTimeout(final long readTimeoutInMillis) {
        if (readTimeoutInMillis < 0L) {
            throw new IllegalArgumentException("readTimeoutInMillis: " + readTimeoutInMillis);
        }
        this.readTimeoutInMillis = readTimeoutInMillis;
        return this;
    }
    
    @Override
    public AioSocketChannelConfig setWriteTimeout(final long writeTimeoutInMillis) {
        if (writeTimeoutInMillis < 0L) {
            throw new IllegalArgumentException("writeTimeoutInMillis: " + writeTimeoutInMillis);
        }
        this.writeTimeoutInMillis = writeTimeoutInMillis;
        return this;
    }
    
    @Override
    public long getReadTimeout() {
        return this.readTimeoutInMillis;
    }
    
    @Override
    public long getWriteTimeout() {
        return this.writeTimeoutInMillis;
    }
    
    @Override
    public boolean isAllowHalfClosure() {
        return this.allowHalfClosure;
    }
    
    @Override
    public AioSocketChannelConfig setAllowHalfClosure(final boolean allowHalfClosure) {
        this.allowHalfClosure = allowHalfClosure;
        return this;
    }
    
    void assign(final NetworkChannel javaChannel) {
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
                    this.javaChannel.get().setOption(option, value);
                }
                catch (IOException e) {
                    throw new ChannelException(e);
                }
            }
        }
        this.options = null;
    }
    
    @Override
    public AioSocketChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        return (AioSocketChannelConfig)super.setConnectTimeoutMillis(connectTimeoutMillis);
    }
    
    @Override
    public AioSocketChannelConfig setWriteSpinCount(final int writeSpinCount) {
        return (AioSocketChannelConfig)super.setWriteSpinCount(writeSpinCount);
    }
    
    @Override
    public AioSocketChannelConfig setAllocator(final ByteBufAllocator allocator) {
        return (AioSocketChannelConfig)super.setAllocator(allocator);
    }
    
    @Override
    public AioSocketChannelConfig setAutoRead(final boolean autoRead) {
        return (AioSocketChannelConfig)super.setAutoRead(autoRead);
    }
    
    @Override
    public AioSocketChannelConfig setDefaultHandlerByteBufType(final ChannelConfig.ChannelHandlerByteBufType type) {
        return (AioSocketChannelConfig)super.setDefaultHandlerByteBufType(type);
    }
}
