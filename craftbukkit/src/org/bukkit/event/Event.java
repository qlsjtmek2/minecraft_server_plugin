// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event;

public abstract class Event
{
    private String name;
    private final boolean async;
    
    public Event() {
        this(false);
    }
    
    public Event(final boolean isAsync) {
        this.async = isAsync;
    }
    
    public String getEventName() {
        if (this.name == null) {
            this.name = this.getClass().getSimpleName();
        }
        return this.name;
    }
    
    public abstract HandlerList getHandlers();
    
    public final boolean isAsynchronous() {
        return this.async;
    }
    
    public enum Result
    {
        DENY, 
        DEFAULT, 
        ALLOW;
    }
}
