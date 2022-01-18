// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;

public interface EventLoop extends EventExecutor, EventLoopGroup
{
    EventLoopGroup parent();
}
