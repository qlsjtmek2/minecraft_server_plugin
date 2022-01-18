// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import java.util.Map;

public interface ChannelConfig
{
    Map<ChannelOption<?>, Object> getOptions();
    
    boolean setOptions(final Map<ChannelOption<?>, ?> p0);
    
     <T> T getOption(final ChannelOption<T> p0);
    
     <T> boolean setOption(final ChannelOption<T> p0, final T p1);
    
    int getConnectTimeoutMillis();
    
    ChannelConfig setConnectTimeoutMillis(final int p0);
    
    int getWriteSpinCount();
    
    ChannelConfig setWriteSpinCount(final int p0);
    
    ByteBufAllocator getAllocator();
    
    ChannelConfig setAllocator(final ByteBufAllocator p0);
    
    boolean isAutoRead();
    
    ChannelConfig setAutoRead(final boolean p0);
    
    ChannelHandlerByteBufType getDefaultHandlerByteBufType();
    
    ChannelConfig setDefaultHandlerByteBufType(final ChannelHandlerByteBufType p0);
    
    public enum ChannelHandlerByteBufType
    {
        HEAP, 
        DIRECT, 
        PREFER_DIRECT;
    }
}
