// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class SynchronizedEventHandler extends EventHandler
{
    public SynchronizedEventHandler(final Object target, final Method method) {
        super(target, method);
    }
    
    public synchronized void handleEvent(final Object event) throws InvocationTargetException {
        super.handleEvent(event);
    }
}
