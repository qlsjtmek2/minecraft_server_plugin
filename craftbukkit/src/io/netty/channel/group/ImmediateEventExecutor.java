// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.group;

import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.AbstractEventExecutor;

final class ImmediateEventExecutor extends AbstractEventExecutor
{
    @Override
    public EventExecutorGroup parent() {
        return null;
    }
    
    @Override
    public boolean inEventLoop() {
        return true;
    }
    
    @Override
    public boolean inEventLoop(final Thread thread) {
        return true;
    }
    
    @Override
    public void shutdown() {
    }
    
    @Override
    public boolean isShutdown() {
        return false;
    }
    
    @Override
    public boolean isTerminated() {
        return false;
    }
    
    @Override
    public boolean awaitTermination(final long timeout, final TimeUnit unit) {
        return false;
    }
    
    @Override
    public List<Runnable> shutdownNow() {
        return Collections.emptyList();
    }
    
    @Override
    public void execute(final Runnable command) {
        if (command == null) {
            throw new NullPointerException("command");
        }
        command.run();
    }
    
    @Override
    public <V> Promise<V> newPromise() {
        return new ImmediatePromise<V>(this);
    }
    
    static class ImmediatePromise<V> extends DefaultPromise<V>
    {
        ImmediatePromise(final EventExecutor executor) {
            super(executor);
        }
        
        @Override
        protected void checkDeadLock() {
        }
    }
}
