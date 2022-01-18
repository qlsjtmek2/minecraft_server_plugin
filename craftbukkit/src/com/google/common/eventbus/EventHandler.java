// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.eventbus;

import java.lang.reflect.InvocationTargetException;
import com.google.common.base.Preconditions;
import java.lang.reflect.Method;

class EventHandler
{
    private final Object target;
    private final Method method;
    
    EventHandler(final Object target, final Method method) {
        Preconditions.checkNotNull(target, (Object)"EventHandler target cannot be null.");
        Preconditions.checkNotNull(method, (Object)"EventHandler method cannot be null.");
        this.target = target;
        (this.method = method).setAccessible(true);
    }
    
    public void handleEvent(final Object event) throws InvocationTargetException {
        try {
            this.method.invoke(this.target, event);
        }
        catch (IllegalArgumentException e) {
            throw new Error("Method rejected target/argument: " + event, e);
        }
        catch (IllegalAccessException e2) {
            throw new Error("Method became inaccessible: " + event, e2);
        }
        catch (InvocationTargetException e3) {
            if (e3.getCause() instanceof Error) {
                throw (Error)e3.getCause();
            }
            throw e3;
        }
    }
    
    public String toString() {
        return "[wrapper " + this.method + "]";
    }
    
    public int hashCode() {
        final int PRIME = 31;
        return (31 + this.method.hashCode()) * 31 + this.target.hashCode();
    }
    
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final EventHandler other = (EventHandler)obj;
        return this.method.equals(other.method) && this.target == other.target;
    }
}
