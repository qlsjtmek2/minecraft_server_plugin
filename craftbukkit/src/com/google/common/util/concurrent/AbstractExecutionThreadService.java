// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.concurrent.Executor;
import com.google.common.base.Throwables;
import com.google.common.annotations.Beta;

@Beta
public abstract class AbstractExecutionThreadService implements Service
{
    private final Service delegate;
    
    public AbstractExecutionThreadService() {
        this.delegate = new AbstractService() {
            protected final void doStart() {
                AbstractExecutionThreadService.this.executor().execute(new Runnable() {
                    public void run() {
                        try {
                            AbstractExecutionThreadService.this.startUp();
                            AbstractService.this.notifyStarted();
                            if (AbstractService.this.isRunning()) {
                                try {
                                    AbstractExecutionThreadService.this.run();
                                }
                                catch (Throwable t) {
                                    try {
                                        AbstractExecutionThreadService.this.shutDown();
                                    }
                                    catch (Exception ex) {}
                                    throw t;
                                }
                            }
                            AbstractExecutionThreadService.this.shutDown();
                            AbstractService.this.notifyStopped();
                        }
                        catch (Throwable t) {
                            AbstractService.this.notifyFailed(t);
                            throw Throwables.propagate(t);
                        }
                    }
                });
            }
            
            protected void doStop() {
                AbstractExecutionThreadService.this.triggerShutdown();
            }
        };
    }
    
    protected void startUp() throws Exception {
    }
    
    protected abstract void run() throws Exception;
    
    protected void shutDown() throws Exception {
    }
    
    protected void triggerShutdown() {
    }
    
    protected Executor executor() {
        return new Executor() {
            public void execute(final Runnable command) {
                new Thread(command, AbstractExecutionThreadService.this.getServiceName()).start();
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
    
    protected String getServiceName() {
        return this.getClass().getSimpleName();
    }
}
