// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.bootstrap;

import io.netty.channel.Channel;

public interface ChannelFactory<T extends Channel>
{
    T newChannel();
}
