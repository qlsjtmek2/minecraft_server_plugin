// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.channel.ChannelConfig;
import io.netty.buffer.ByteBufAllocator;
import java.io.IOException;
import java.net.MulticastSocket;
import io.netty.util.internal.PlatformDependent;
import java.net.SocketException;
import io.netty.channel.ChannelException;
import java.net.NetworkInterface;
import java.net.InetAddress;
import io.netty.channel.ChannelOption;
import java.util.Map;
import io.netty.channel.Channel;
import java.net.DatagramSocket;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.channel.DefaultChannelConfig;

public class DefaultDatagramChannelConfig extends DefaultChannelConfig implements DatagramChannelConfig
{
    private static final InternalLogger logger;
    private static final int DEFAULT_RECEIVE_PACKET_SIZE = 2048;
    private final DatagramSocket javaSocket;
    private volatile int receivePacketSize;
    
    public DefaultDatagramChannelConfig(final DatagramChannel channel, final DatagramSocket javaSocket) {
        super(channel);
        this.receivePacketSize = 2048;
        if (javaSocket == null) {
            throw new NullPointerException("javaSocket");
        }
        this.javaSocket = javaSocket;
    }
    
    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.UDP_RECEIVE_PACKET_SIZE);
    }
    
    @Override
    public <T> T getOption(final ChannelOption<T> option) {
        if (option == ChannelOption.SO_BROADCAST) {
            return (T)this.isBroadcast();
        }
        if (option == ChannelOption.SO_RCVBUF) {
            return (T)this.getReceiveBufferSize();
        }
        if (option == ChannelOption.SO_SNDBUF) {
            return (T)this.getSendBufferSize();
        }
        if (option == ChannelOption.UDP_RECEIVE_PACKET_SIZE) {
            return (T)this.getReceivePacketSize();
        }
        if (option == ChannelOption.SO_REUSEADDR) {
            return (T)this.isReuseAddress();
        }
        if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
            return (T)this.isLoopbackModeDisabled();
        }
        if (option == ChannelOption.IP_MULTICAST_ADDR) {
            final T i = (T)this.getInterface();
            return i;
        }
        if (option == ChannelOption.IP_MULTICAST_IF) {
            final T i = (T)this.getNetworkInterface();
            return i;
        }
        if (option == ChannelOption.IP_MULTICAST_TTL) {
            return (T)this.getTimeToLive();
        }
        if (option == ChannelOption.IP_TOS) {
            return (T)this.getTrafficClass();
        }
        return super.getOption(option);
    }
    
    @Override
    public <T> boolean setOption(final ChannelOption<T> option, final T value) {
        this.validate(option, value);
        if (option == ChannelOption.SO_BROADCAST) {
            this.setBroadcast((boolean)value);
        }
        else if (option == ChannelOption.SO_RCVBUF) {
            this.setReceiveBufferSize((int)value);
        }
        else if (option == ChannelOption.SO_SNDBUF) {
            this.setSendBufferSize((int)value);
        }
        else if (option == ChannelOption.SO_REUSEADDR) {
            this.setReuseAddress((boolean)value);
        }
        else if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
            this.setLoopbackModeDisabled((boolean)value);
        }
        else if (option == ChannelOption.IP_MULTICAST_ADDR) {
            this.setInterface((InetAddress)value);
        }
        else if (option == ChannelOption.IP_MULTICAST_IF) {
            this.setNetworkInterface((NetworkInterface)value);
        }
        else if (option == ChannelOption.IP_MULTICAST_TTL) {
            this.setTimeToLive((int)value);
        }
        else if (option == ChannelOption.IP_TOS) {
            this.setTrafficClass((int)value);
        }
        else {
            if (option != ChannelOption.UDP_RECEIVE_PACKET_SIZE) {
                return super.setOption(option, value);
            }
            this.setReceivePacketSize((int)value);
        }
        return true;
    }
    
    @Override
    public boolean isBroadcast() {
        try {
            return this.javaSocket.getBroadcast();
        }
        catch (SocketException e) {
            throw new ChannelException(e);
        }
    }
    
    @Override
    public DatagramChannelConfig setBroadcast(final boolean broadcast) {
        try {
            if (broadcast && !PlatformDependent.isWindows() && !PlatformDependent.isRoot() && !this.javaSocket.getLocalAddress().isAnyLocalAddress()) {
                DefaultDatagramChannelConfig.logger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; setting the SO_BROADCAST flag anyway as requested on the socket which is bound to " + this.javaSocket.getLocalSocketAddress() + '.');
            }
            this.javaSocket.setBroadcast(broadcast);
        }
        catch (SocketException e) {
            throw new ChannelException(e);
        }
        return this;
    }
    
    @Override
    public InetAddress getInterface() {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket)this.javaSocket).getInterface();
            }
            catch (SocketException e) {
                throw new ChannelException(e);
            }
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public DatagramChannelConfig setInterface(final InetAddress interfaceAddress) {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket)this.javaSocket).setInterface(interfaceAddress);
                return this;
            }
            catch (SocketException e) {
                throw new ChannelException(e);
            }
            throw new UnsupportedOperationException();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isLoopbackModeDisabled() {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket)this.javaSocket).getLoopbackMode();
            }
            catch (SocketException e) {
                throw new ChannelException(e);
            }
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public DatagramChannelConfig setLoopbackModeDisabled(final boolean loopbackModeDisabled) {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket)this.javaSocket).setLoopbackMode(loopbackModeDisabled);
                return this;
            }
            catch (SocketException e) {
                throw new ChannelException(e);
            }
            throw new UnsupportedOperationException();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public NetworkInterface getNetworkInterface() {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket)this.javaSocket).getNetworkInterface();
            }
            catch (SocketException e) {
                throw new ChannelException(e);
            }
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public DatagramChannelConfig setNetworkInterface(final NetworkInterface networkInterface) {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket)this.javaSocket).setNetworkInterface(networkInterface);
                return this;
            }
            catch (SocketException e) {
                throw new ChannelException(e);
            }
            throw new UnsupportedOperationException();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isReuseAddress() {
        try {
            return this.javaSocket.getReuseAddress();
        }
        catch (SocketException e) {
            throw new ChannelException(e);
        }
    }
    
    @Override
    public DatagramChannelConfig setReuseAddress(final boolean reuseAddress) {
        try {
            this.javaSocket.setReuseAddress(reuseAddress);
        }
        catch (SocketException e) {
            throw new ChannelException(e);
        }
        return this;
    }
    
    @Override
    public int getReceiveBufferSize() {
        try {
            return this.javaSocket.getReceiveBufferSize();
        }
        catch (SocketException e) {
            throw new ChannelException(e);
        }
    }
    
    @Override
    public DatagramChannelConfig setReceiveBufferSize(final int receiveBufferSize) {
        try {
            this.javaSocket.setReceiveBufferSize(receiveBufferSize);
        }
        catch (SocketException e) {
            throw new ChannelException(e);
        }
        return this;
    }
    
    @Override
    public int getSendBufferSize() {
        try {
            return this.javaSocket.getSendBufferSize();
        }
        catch (SocketException e) {
            throw new ChannelException(e);
        }
    }
    
    @Override
    public DatagramChannelConfig setSendBufferSize(final int sendBufferSize) {
        try {
            this.javaSocket.setSendBufferSize(sendBufferSize);
        }
        catch (SocketException e) {
            throw new ChannelException(e);
        }
        return this;
    }
    
    @Override
    public int getReceivePacketSize() {
        return this.receivePacketSize;
    }
    
    @Override
    public DatagramChannelConfig setReceivePacketSize(final int receivePacketSize) {
        if (receivePacketSize <= 0) {
            throw new IllegalArgumentException(String.format("receivePacketSize: %d (expected: > 0)", receivePacketSize));
        }
        this.receivePacketSize = receivePacketSize;
        return this;
    }
    
    @Override
    public int getTimeToLive() {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket)this.javaSocket).getTimeToLive();
            }
            catch (IOException e) {
                throw new ChannelException(e);
            }
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public DatagramChannelConfig setTimeToLive(final int ttl) {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket)this.javaSocket).setTimeToLive(ttl);
                return this;
            }
            catch (IOException e) {
                throw new ChannelException(e);
            }
            throw new UnsupportedOperationException();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int getTrafficClass() {
        try {
            return this.javaSocket.getTrafficClass();
        }
        catch (SocketException e) {
            throw new ChannelException(e);
        }
    }
    
    @Override
    public DatagramChannelConfig setTrafficClass(final int trafficClass) {
        try {
            this.javaSocket.setTrafficClass(trafficClass);
        }
        catch (SocketException e) {
            throw new ChannelException(e);
        }
        return this;
    }
    
    @Override
    public DatagramChannelConfig setWriteSpinCount(final int writeSpinCount) {
        return (DatagramChannelConfig)super.setWriteSpinCount(writeSpinCount);
    }
    
    @Override
    public DatagramChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        return (DatagramChannelConfig)super.setConnectTimeoutMillis(connectTimeoutMillis);
    }
    
    @Override
    public DatagramChannelConfig setAllocator(final ByteBufAllocator allocator) {
        return (DatagramChannelConfig)super.setAllocator(allocator);
    }
    
    @Override
    public DatagramChannelConfig setAutoRead(final boolean autoRead) {
        return (DatagramChannelConfig)super.setAutoRead(autoRead);
    }
    
    @Override
    public DatagramChannelConfig setDefaultHandlerByteBufType(final ChannelConfig.ChannelHandlerByteBufType type) {
        return (DatagramChannelConfig)super.setDefaultHandlerByteBufType(type);
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(DefaultDatagramChannelConfig.class);
    }
}
