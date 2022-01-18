// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.eventbus;

import java.lang.reflect.Method;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

class AnnotatedHandlerFinder implements HandlerFindingStrategy
{
    public Multimap<Class<?>, EventHandler> findAllHandlers(final Object listener) {
        final Multimap<Class<?>, EventHandler> methodsInListener = (Multimap<Class<?>, EventHandler>)HashMultimap.create();
        for (Class clazz = listener.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            for (final Method method : clazz.getMethods()) {
                final Subscribe annotation = method.getAnnotation(Subscribe.class);
                if (annotation != null) {
                    final Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 1) {
                        throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation, but requires " + parameterTypes.length + " arguments.  Event handler methods " + "must require a single argument.");
                    }
                    final Class<?> eventType = parameterTypes[0];
                    final EventHandler handler = makeHandler(listener, method);
                    methodsInListener.put(eventType, handler);
                }
            }
        }
        return methodsInListener;
    }
    
    private static EventHandler makeHandler(final Object listener, final Method method) {
        EventHandler wrapper;
        if (methodIsDeclaredThreadSafe(method)) {
            wrapper = new EventHandler(listener, method);
        }
        else {
            wrapper = new SynchronizedEventHandler(listener, method);
        }
        return wrapper;
    }
    
    private static boolean methodIsDeclaredThreadSafe(final Method method) {
        return method.getAnnotation(AllowConcurrentEvents.class) != null;
    }
}
