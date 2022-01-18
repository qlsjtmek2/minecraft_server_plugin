// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.eventbus;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import com.google.common.annotations.Beta;

@Beta
public class AsyncEventBus extends EventBus
{
    private final Executor executor;
    private final ConcurrentLinkedQueue<EventWithHandler> eventsToDispatch;
    
    public AsyncEventBus(final String identifier, final Executor executor) {
        super(identifier);
        this.eventsToDispatch = new ConcurrentLinkedQueue<EventWithHandler>();
        this.executor = executor;
    }
    
    public AsyncEventBus(final Executor executor) {
        this.eventsToDispatch = new ConcurrentLinkedQueue<EventWithHandler>();
        this.executor = executor;
    }
    
    protected void enqueueEvent(final Object event, final EventHandler handler) {
        this.eventsToDispatch.offer(new EventWithHandler(event, handler));
    }
    
    protected void dispatchQueuedEvents() {
        while (true) {
            final EventWithHandler eventWithHandler = this.eventsToDispatch.poll();
            if (eventWithHandler == null) {
                break;
            }
            this.dispatch(eventWithHandler.event, eventWithHandler.handler);
        }
    }
    
    protected void dispatch(final Object event, final EventHandler handler) {
        this.executor.execute(new Runnable() {
            public void run() {
                AsyncEventBus.this.dispatch(event, handler);
            }
        });
    }
}
