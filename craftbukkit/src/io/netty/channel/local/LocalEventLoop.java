// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.local;

import io.netty.channel.EventLoopGroup;
import java.util.concurrent.ThreadFactory;
import io.netty.channel.SingleThreadEventLoop;

final class LocalEventLoop extends SingleThreadEventLoop
{
    LocalEventLoop(final LocalEventLoopGroup parent, final ThreadFactory threadFactory) {
        super(parent, threadFactory);
    }
    
    @Override
    protected void run() {
        do {
            try {
                final Runnable task = this.takeTask();
                task.run();
            }
            catch (InterruptedException ex) {}
        } while (!this.isShutdown() || !this.confirmShutdown());
    }
}
