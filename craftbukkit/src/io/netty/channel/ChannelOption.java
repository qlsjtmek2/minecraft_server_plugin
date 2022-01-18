// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.util.internal.PlatformDependent;
import java.net.NetworkInterface;
import java.net.InetAddress;
import io.netty.buffer.ByteBufAllocator;
import java.util.concurrent.ConcurrentMap;
import io.netty.util.UniqueName;

public class ChannelOption<T> extends UniqueName
{
    private static final ConcurrentMap<String, Boolean> names;
    public static final ChannelOption<ByteBufAllocator> ALLOCATOR;
    public static final ChannelOption<Integer> CONNECT_TIMEOUT_MILLIS;
    public static final ChannelOption<Integer> WRITE_SPIN_COUNT;
    public static final ChannelOption<Boolean> ALLOW_HALF_CLOSURE;
    public static final ChannelOption<Boolean> AUTO_READ;
    public static final ChannelOption<ChannelConfig.ChannelHandlerByteBufType> DEFAULT_HANDLER_BYTEBUF_TYPE;
    public static final ChannelOption<Boolean> SO_BROADCAST;
    public static final ChannelOption<Boolean> SO_KEEPALIVE;
    public static final ChannelOption<Integer> SO_SNDBUF;
    public static final ChannelOption<Integer> SO_RCVBUF;
    public static final ChannelOption<Boolean> SO_REUSEADDR;
    public static final ChannelOption<Integer> SO_LINGER;
    public static final ChannelOption<Integer> SO_BACKLOG;
    public static final ChannelOption<Integer> SO_TIMEOUT;
    public static final ChannelOption<Integer> IP_TOS;
    public static final ChannelOption<InetAddress> IP_MULTICAST_ADDR;
    public static final ChannelOption<NetworkInterface> IP_MULTICAST_IF;
    public static final ChannelOption<Integer> IP_MULTICAST_TTL;
    public static final ChannelOption<Boolean> IP_MULTICAST_LOOP_DISABLED;
    public static final ChannelOption<Integer> UDP_RECEIVE_PACKET_SIZE;
    public static final ChannelOption<Boolean> TCP_NODELAY;
    public static final ChannelOption<Long> AIO_READ_TIMEOUT;
    public static final ChannelOption<Long> AIO_WRITE_TIMEOUT;
    
    protected ChannelOption(final String name) {
        super(ChannelOption.names, name, new Object[0]);
    }
    
    public void validate(final T value) {
        if (value == null) {
            throw new NullPointerException("value");
        }
    }
    
    static {
        names = PlatformDependent.newConcurrentHashMap();
        ALLOCATOR = new ChannelOption<ByteBufAllocator>("ALLOCATOR");
        CONNECT_TIMEOUT_MILLIS = new ChannelOption<Integer>("CONNECT_TIMEOUT_MILLIS");
        WRITE_SPIN_COUNT = new ChannelOption<Integer>("WRITE_SPIN_COUNT");
        ALLOW_HALF_CLOSURE = new ChannelOption<Boolean>("ALLOW_HALF_CLOSURE");
        AUTO_READ = new ChannelOption<Boolean>("AUTO_READ");
        DEFAULT_HANDLER_BYTEBUF_TYPE = new ChannelOption<ChannelConfig.ChannelHandlerByteBufType>("DEFAULT_HANDLER_BYTEBUF_TYPE");
        SO_BROADCAST = new ChannelOption<Boolean>("SO_BROADCAST");
        SO_KEEPALIVE = new ChannelOption<Boolean>("SO_KEEPALIVE");
        SO_SNDBUF = new ChannelOption<Integer>("SO_SNDBUF");
        SO_RCVBUF = new ChannelOption<Integer>("SO_RCVBUF");
        SO_REUSEADDR = new ChannelOption<Boolean>("SO_REUSEADDR");
        SO_LINGER = new ChannelOption<Integer>("SO_LINGER");
        SO_BACKLOG = new ChannelOption<Integer>("SO_BACKLOG");
        SO_TIMEOUT = new ChannelOption<Integer>("SO_TIMEOUT");
        IP_TOS = new ChannelOption<Integer>("IP_TOS");
        IP_MULTICAST_ADDR = new ChannelOption<InetAddress>("IP_MULTICAST_ADDR");
        IP_MULTICAST_IF = new ChannelOption<NetworkInterface>("IP_MULTICAST_IF");
        IP_MULTICAST_TTL = new ChannelOption<Integer>("IP_MULTICAST_TTL");
        IP_MULTICAST_LOOP_DISABLED = new ChannelOption<Boolean>("IP_MULTICAST_LOOP_DISABLED");
        UDP_RECEIVE_PACKET_SIZE = new ChannelOption<Integer>("UDP_RECEIVE_PACKET_SIZE");
        TCP_NODELAY = new ChannelOption<Boolean>("TCP_NODELAY");
        AIO_READ_TIMEOUT = new ChannelOption<Long>("AIO_READ_TIMEOUT");
        AIO_WRITE_TIMEOUT = new ChannelOption<Long>("AIO_WRITE_TIMEOUT");
    }
}
