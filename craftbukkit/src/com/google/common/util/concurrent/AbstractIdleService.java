// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.concurrent.Executor;
import com.google.common.base.Throwables;
import com.google.common.annotations.Beta;

@Beta
public abstract class AbstractIdleService implements Service
{
    private final Service delegate;
    
    public AbstractIdleService() {
        this.delegate = new AbstractService() {
            protected final void doStart() {
                AbstractIdleService.this.executor(State.STARTING).execute(new Runnable() {
                    public void run() {
                        try {
                            AbstractIdleService.this.startUp();
                            AbstractService.this.notifyStarted();
                        }
                        catch (Throwable t) {
                            AbstractService.this.notifyFailed(t);
                            throw Throwables.propagate(t);
                        }
                    }
                });
            }
            
            protected final void doStop() {
                AbstractIdleService.this.executor(State.STOPPING).execute(new Runnable() {
                    public void run() {
                        try {
                            AbstractIdleService.this.shutDown();
                            AbstractService.this.notifyStopped();
                        }
                        catch (Throwable t) {
                            AbstractService.this.notifyFailed(t);
                            throw Throwables.propagate(t);
                        }
                    }
                });
            }
        };
    }
    
    protected abstract void startUp() throws Exception;
    
    protected abstract void shutDown() throws Exception;
    
    protected Executor executor(final State state) {
        return new Executor() {
            public void execute(final Runnable command) {
                new Thread(command, AbstractIdleService.this.getServiceName() + " " + state).start();
            }
        };
    }
    
    public String toString() {
        return this.getServiceName() + " [" + this.state() + "]";
    }
    
    public final ListenableFuture<State> start() {
        return this.delegate.start();
    }
    
    public final State startAndWait() {
        return this.delegate.startAndWait();
    }
    
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }
    
    public final State state() {
        return this.delegate.state();
    }
    
    public final ListenableFuture<State> stop() {
        return this.delegate.stop();
    }
    
    public final State stopAndWait() {
        return this.delegate.stopAndWait();
    }
    
    private String getServiceName() {
        return this.getClass().getSimpleName();
    }
}
