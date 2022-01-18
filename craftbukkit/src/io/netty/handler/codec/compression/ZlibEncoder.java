// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.compression;

import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.ByteToByteEncoder;

public abstract class ZlibEncoder extends ByteToByteEncoder
{
    public abstract boolean isClosed();
    
    public abstract ChannelFuture close();
    
    public abstract ChannelFuture close(final ChannelPromise p0);
}
