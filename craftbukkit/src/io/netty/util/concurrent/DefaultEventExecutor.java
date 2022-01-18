// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

import java.util.concurrent.ThreadFactory;

final class DefaultEventExecutor extends SingleThreadEventExecutor
{
    DefaultEventExecutor(final DefaultEventExecutorGroup parent, final ThreadFactory threadFactory) {
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
