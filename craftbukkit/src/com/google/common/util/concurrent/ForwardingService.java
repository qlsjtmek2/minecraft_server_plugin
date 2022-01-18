// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.concurrent.Future;
import com.google.common.annotations.Beta;
import com.google.common.collect.ForwardingObject;

@Beta
public abstract class ForwardingService extends ForwardingObject implements Service
{
    protected abstract Service delegate();
    
    public ListenableFuture<State> start() {
        return this.delegate().start();
    }
    
    public State state() {
        return this.delegate().state();
    }
    
    public ListenableFuture<State> stop() {
        return this.delegate().stop();
    }
    
    public State startAndWait() {
        return this.delegate().startAndWait();
    }
    
    public State stopAndWait() {
        return this.delegate().stopAndWait();
    }
    
    public boolean isRunning() {
        return this.delegate().isRunning();
    }
    
    protected State standardStartAndWait() {
        return Futures.getUnchecked(this.start());
    }
    
    protected State standardStopAndWait() {
        return Futures.getUnchecked(this.stop());
    }
}
