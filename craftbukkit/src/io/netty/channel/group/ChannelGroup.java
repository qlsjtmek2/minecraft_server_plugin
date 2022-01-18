// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.group;

import io.netty.channel.FileRegion;
import io.netty.channel.Channel;
import java.util.Set;

public interface ChannelGroup extends Set<Channel>, Comparable<ChannelGroup>
{
    String name();
    
    Channel find(final Integer p0);
    
    ChannelGroupFuture write(final Object p0);
    
    ChannelGroupFuture sendFile(final FileRegion p0);
    
    ChannelGroupFuture flush();
    
    ChannelGroupFuture disconnect();
    
    ChannelGroupFuture close();
    
    ChannelGroupFuture deregister();
}
