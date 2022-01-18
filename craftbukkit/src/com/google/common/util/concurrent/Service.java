// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;

@Beta
public interface Service
{
    ListenableFuture<State> start();
    
    State startAndWait();
    
    boolean isRunning();
    
    State state();
    
    ListenableFuture<State> stop();
    
    State stopAndWait();
    
    @Beta
    public enum State
    {
        NEW, 
        STARTING, 
        RUNNING, 
        STOPPING, 
        TERMINATED, 
        FAILED;
    }
}
