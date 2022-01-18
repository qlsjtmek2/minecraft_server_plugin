// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.local;

import io.netty.util.internal.PlatformDependent;
import io.netty.channel.ChannelException;
import java.net.SocketAddress;
import io.netty.channel.Channel;
import java.util.concurrent.ConcurrentMap;

final class LocalChannelRegistry
{
    private static final ConcurrentMap<LocalAddress, Channel> boundChannels;
    
    static LocalAddress register(final Channel channel, final LocalAddress oldLocalAddress, final SocketAddress localAddress) {
        if (oldLocalAddress != null) {
            throw new ChannelException("already bound");
        }
        if (!(localAddress instanceof LocalAddress)) {
            throw new ChannelException("unsupported address type: " + localAddress.getClass().getSimpleName());
        }
        LocalAddress addr = (LocalAddress)localAddress;
        if (LocalAddress.ANY.equals(addr)) {
            addr = new LocalAddress(channel);
        }
        final Channel boundChannel = LocalChannelRegistry.boundChannels.putIfAbsent(addr, channel);
        if (boundChannel != null) {
            throw new ChannelException("address already in use by: " + boundChannel);
        }
        return addr;
    }
    
    static Channel get(final SocketAddress localAddress) {
        return LocalChannelRegistry.boundChannels.get(localAddress);
    }
    
    static void unregister(final LocalAddress localAddress) {
        LocalChannelRegistry.boundChannels.remove(localAddress);
    }
    
    static {
        boundChannels = PlatformDependent.newConcurrentHashMap();
    }
}
