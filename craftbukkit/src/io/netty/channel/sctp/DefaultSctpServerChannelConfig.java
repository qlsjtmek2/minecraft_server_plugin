// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.sctp;

import io.netty.channel.ChannelConfig;
import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import io.netty.channel.ChannelException;
import com.sun.nio.sctp.SctpStandardSocketOptions;
import io.netty.channel.ChannelOption;
import java.util.Map;
import io.netty.util.NetUtil;
import io.netty.channel.Channel;
import com.sun.nio.sctp.SctpServerChannel;
import io.netty.channel.DefaultChannelConfig;

public class DefaultSctpServerChannelConfig extends DefaultChannelConfig implements SctpServerChannelConfig
{
    private final SctpServerChannel javaChannel;
    private volatile int backlog;
    
    public DefaultSctpServerChannelConfig(final io.netty.channel.sctp.SctpServerChannel channel, final SctpServerChannel javaChannel) {
        super(channel);
        this.backlog = NetUtil.SOMAXCONN;
        if (javaChannel == null) {
            throw new NullPointerException("javaChannel");
        }
        this.javaChannel = javaChannel;
    }
    
    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, SctpChannelOption.SCTP_INIT_MAXSTREAMS);
    }
    
    @Override
    public <T> T getOption(final ChannelOption<T> option) {
        if (option == ChannelOption.SO_RCVBUF) {
            return (T)this.getReceiveBufferSize();
        }
        if (option == ChannelOption.SO_SNDBUF) {
            return (T)this.getSendBufferSize();
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
        else {
            if (option != SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS) {
                return super.setOption(option, value);
            }
            this.setInitMaxStreams((SctpStandardSocketOptions.InitMaxStreams)value);
        }
        return true;
    }
    
    @Override
    public int getSendBufferSize() {
        try {
            return this.javaChannel.getOption(SctpStandardSocketOptions.SO_SNDBUF);
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
    }
    
    @Override
    public SctpServerChannelConfig setSendBufferSize(final int sendBufferSize) {
        try {
            this.javaChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, sendBufferSize);
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
        return this;
    }
    
    @Override
    public int getReceiveBufferSize() {
        try {
            return this.javaChannel.getOption(SctpStandardSocketOptions.SO_RCVBUF);
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
    }
    
    @Override
    public SctpServerChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        try {
            this.javaChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, receiveBufferSize);
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
        return this;
    }
    
    @Override
    public SctpStandardSocketOptions.InitMaxStreams getInitMaxStreams() {
        try {
            return this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS);
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
    }
    
    @Override
    public SctpServerChannelConfig setInitMaxStreams(final SctpStandardSocketOptions.InitMaxStreams initMaxStreams) {
        try {
            this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, initMaxStreams);
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
        return this;
    }
    
    @Override
    public int getBacklog() {
        return this.backlog;
    }
    
    @Override
    public SctpServerChannelConfig setBacklog(final int backlog) {
        if (backlog < 0) {
            throw new IllegalArgumentException("backlog: " + backlog);
        }
        this.backlog = backlog;
        return this;
    }
    
    @Override
    public SctpServerChannelConfig setWriteSpinCount(final int writeSpinCount) {
        return (SctpServerChannelConfig)super.setWriteSpinCount(writeSpinCount);
    }
    
    @Override
    public SctpServerChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        return (SctpServerChannelConfig)super.setConnectTimeoutMillis(connectTimeoutMillis);
    }
    
    @Override
    public SctpServerChannelConfig setAllocator(final ByteBufAllocator allocator) {
        return (SctpServerChannelConfig)super.setAllocator(allocator);
    }
    
    @Override
    public SctpServerChannelConfig setAutoRead(final boolean autoRead) {
        return (SctpServerChannelConfig)super.setAutoRead(autoRead);
    }
    
    @Override
    public SctpServerChannelConfig setDefaultHandlerByteBufType(final ChannelConfig.ChannelHandlerByteBufType type) {
        return (SctpServerChannelConfig)super.setDefaultHandlerByteBufType(type);
    }
}
