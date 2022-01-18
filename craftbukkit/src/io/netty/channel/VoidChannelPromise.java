// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.util.concurrent.Promise;
import java.util.concurrent.TimeUnit;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.AbstractFuture;

final class VoidChannelPromise extends AbstractFuture<Void> implements ChannelFuture.Unsafe, ChannelPromise
{
    private final Channel channel;
    
    public VoidChannelPromise(final Channel channel) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        this.channel = channel;
    }
    
    @Override
    public ChannelPromise addListener(final GenericFutureListener<? extends Future<Void>> listener) {
        fail();
        return this;
    }
    
    @Override
    public ChannelPromise addListeners(final GenericFutureListener<? extends Future<Void>>... listeners) {
        fail();
        return this;
    }
    
    @Override
    public ChannelPromise removeListener(final GenericFutureListener<? extends Future<Void>> listener) {
        return this;
    }
    
    @Override
    public ChannelPromise removeListeners(final GenericFutureListener<? extends Future<Void>>... listeners) {
        return this;
    }
    
    @Override
    public ChannelPromise await() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return this;
    }
    
    @Override
    public boolean await(final long timeout, final TimeUnit unit) {
        fail();
        return false;
    }
    
    @Override
    public boolean await(final long timeoutMillis) {
        fail();
        return false;
    }
    
    @Override
    public ChannelPromise awaitUninterruptibly() {
        fail();
        return this;
    }
    
    @Override
    public boolean awaitUninterruptibly(final long timeout, final TimeUnit unit) {
        fail();
        return false;
    }
    
    @Override
    public boolean awaitUninterruptibly(final long timeoutMillis) {
        fail();
        return false;
    }
    
    @Override
    public Channel channel() {
        return this.channel;
    }
    
    @Override
    public boolean isDone() {
        return false;
    }
    
    @Override
    public boolean isSuccess() {
        return false;
    }
    
    @Override
    public Throwable cause() {
        return null;
    }
    
    @Override
    public ChannelPromise sync() {
        fail();
        return this;
    }
    
    @Override
    public ChannelPromise syncUninterruptibly() {
        fail();
        return this;
    }
    
    @Override
    public ChannelPromise setFailure(final Throwable cause) {
        return this;
    }
    
    @Override
    public ChannelPromise setSuccess() {
        return this;
    }
    
    @Override
    public boolean tryFailure(final Throwable cause) {
        return false;
    }
    
    @Override
    public boolean trySuccess() {
        return false;
    }
    
    private static void fail() {
        throw new IllegalStateException("void future");
    }
    
    @Override
    public ChannelPromise setSuccess(final Void result) {
        return this;
    }
    
    @Override
    public boolean trySuccess(final Void result) {
        return false;
    }
    
    @Override
    public Void getNow() {
        return null;
    }
}
