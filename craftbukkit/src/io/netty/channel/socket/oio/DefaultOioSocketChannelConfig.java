// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket.oio;

import io.netty.channel.socket.SocketChannelConfig;
import io.netty.channel.ChannelConfig;
import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import java.util.Map;
import java.net.Socket;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.DefaultSocketChannelConfig;

public class DefaultOioSocketChannelConfig extends DefaultSocketChannelConfig implements OioSocketChannelConfig
{
    public DefaultOioSocketChannelConfig(final SocketChannel channel, final Socket javaSocket) {
        super(channel, javaSocket);
    }
    
    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_TIMEOUT);
    }
    
    @Override
    public <T> T getOption(final ChannelOption<T> option) {
        if (option == ChannelOption.SO_TIMEOUT) {
            return (T)this.getSoTimeout();
        }
        return super.getOption(option);
    }
    
    @Override
    public <T> boolean setOption(final ChannelOption<T> option, final T value) {
        this.validate(option, value);
        if (option == ChannelOption.SO_TIMEOUT) {
            this.setSoTimeout((int)value);
            return true;
        }
        return super.setOption(option, value);
    }
    
    @Override
    public OioSocketChannelConfig setSoTimeout(final int timeout) {
        try {
            this.javaSocket.setSoTimeout(timeout);
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
        return this;
    }
    
    @Override
    public int getSoTimeout() {
        try {
            return this.javaSocket.getSoTimeout();
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
    }
    
    @Override
    public OioSocketChannelConfig setTcpNoDelay(final boolean tcpNoDelay) {
        super.setTcpNoDelay(tcpNoDelay);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setSoLinger(final int soLinger) {
        super.setSoLinger(soLinger);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setSendBufferSize(final int sendBufferSize) {
        super.setSendBufferSize(sendBufferSize);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        super.setReceiveBufferSize(receiveBufferSize);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setKeepAlive(final boolean keepAlive) {
        super.setKeepAlive(keepAlive);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setTrafficClass(final int trafficClass) {
        super.setTrafficClass(trafficClass);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setReuseAddress(final boolean reuseAddress) {
        super.setReuseAddress(reuseAddress);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setPerformancePreferences(final int connectionTime, final int latency, final int bandwidth) {
        super.setPerformancePreferences(connectionTime, latency, bandwidth);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setAllowHalfClosure(final boolean allowHalfClosure) {
        super.setAllowHalfClosure(true);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setWriteSpinCount(final int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setAllocator(final ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setAutoRead(final boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }
    
    @Override
    public OioSocketChannelConfig setDefaultHandlerByteBufType(final ChannelConfig.ChannelHandlerByteBufType type) {
        super.setDefaultHandlerByteBufType(type);
        return this;
    }
}
