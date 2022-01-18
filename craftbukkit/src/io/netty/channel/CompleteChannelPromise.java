// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.EventExecutor;

abstract class CompleteChannelPromise extends CompleteChannelFuture implements ChannelPromise
{
    protected CompleteChannelPromise(final Channel channel, final EventExecutor executor) {
        super(channel, executor);
    }
    
    @Override
    public ChannelPromise setFailure(final Throwable cause) {
        throw new IllegalStateException();
    }
    
    @Override
    public boolean tryFailure(final Throwable cause) {
        return false;
    }
    
    @Override
    public ChannelPromise setSuccess() {
        throw new IllegalStateException();
    }
    
    @Override
    public boolean trySuccess() {
        return false;
    }
    
    @Override
    public boolean trySuccess(final Void result) {
        return false;
    }
    
    @Override
    public ChannelPromise setSuccess(final Void result) {
        throw new IllegalStateException();
    }
    
    @Override
    public ChannelPromise await() throws InterruptedException {
        return (ChannelPromise)super.await();
    }
    
    @Override
    public ChannelPromise awaitUninterruptibly() {
        return (ChannelPromise)super.awaitUninterruptibly();
    }
    
    @Override
    public ChannelPromise addListener(final GenericFutureListener<? extends Future<Void>> listener) {
        return (ChannelPromise)super.addListener(listener);
    }
    
    @Override
    public ChannelPromise addListeners(final GenericFutureListener<? extends Future<Void>>... listeners) {
        return (ChannelPromise)super.addListeners(listeners);
    }
    
    @Override
    public ChannelPromise removeListener(final GenericFutureListener<? extends Future<Void>> listener) {
        return (ChannelPromise)super.removeListener(listener);
    }
    
    @Override
    public ChannelPromise removeListeners(final GenericFutureListener<? extends Future<Void>>... listeners) {
        return (ChannelPromise)super.removeListeners(listeners);
    }
    
    @Override
    public ChannelPromise sync() throws InterruptedException {
        return this;
    }
    
    @Override
    public ChannelPromise syncUninterruptibly() {
        return this;
    }
}
