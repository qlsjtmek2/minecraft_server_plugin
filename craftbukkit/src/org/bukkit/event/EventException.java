// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event;

public class EventException extends Exception
{
    private static final long serialVersionUID = 3532808232324183999L;
    private final Throwable cause;
    
    public EventException(final Throwable throwable) {
        this.cause = throwable;
    }
    
    public EventException() {
        this.cause = null;
    }
    
    public EventException(final Throwable cause, final String message) {
        super(message);
        this.cause = cause;
    }
    
    public EventException(final String message) {
        super(message);
        this.cause = null;
    }
    
    public Throwable getCause() {
        return this.cause;
    }
}
