// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;

interface ChannelPropertyAccess
{
    ChannelPipeline pipeline();
    
    ByteBufAllocator alloc();
    
    ChannelPromise newPromise();
    
    ChannelFuture newSucceededFuture();
    
    ChannelFuture newFailedFuture(final Throwable p0);
}
