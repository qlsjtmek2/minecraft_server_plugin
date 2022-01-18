// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.eventbus;

import com.google.common.annotations.VisibleForTesting;
import java.util.concurrent.ExecutionException;
import com.google.common.base.Throwables;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.Iterator;
import com.google.common.collect.Multimap;
import java.util.List;
import com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheBuilder;
import java.util.Map;
import com.google.common.collect.Multimaps;
import java.util.concurrent.CopyOnWriteArraySet;
import com.google.common.base.Supplier;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import com.google.common.cache.Cache;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;
import com.google.common.collect.SetMultimap;
import com.google.common.annotations.Beta;

@Beta
public class EventBus
{
    private final SetMultimap<Class<?>, EventHandler> handlersByType;
    private final Logger logger;
    private final HandlerFindingStrategy finder;
    private final ThreadLocal<ConcurrentLinkedQueue<EventWithHandler>> eventsToDispatch;
    private final ThreadLocal<Boolean> isDispatching;
    private Cache<Class<?>, Set<Class<?>>> flattenHierarchyCache;
    
    public EventBus() {
        this("default");
    }
    
    public EventBus(final String identifier) {
        this.handlersByType = Multimaps.newSetMultimap(new ConcurrentHashMap<Class<?>, Collection<EventHandler>>(), new Supplier<Set<EventHandler>>() {
            public Set<EventHandler> get() {
                return new CopyOnWriteArraySet<EventHandler>();
            }
        });
        this.finder = new AnnotatedHandlerFinder();
        this.eventsToDispatch = new ThreadLocal<ConcurrentLinkedQueue<EventWithHandler>>() {
            protected ConcurrentLinkedQueue<EventWithHandler> initialValue() {
                return new ConcurrentLinkedQueue<EventWithHandler>();
            }
        };
        this.isDispatching = new ThreadLocal<Boolean>() {
            protected Boolean initialValue() {
                return false;
            }
        };
        this.flattenHierarchyCache = CacheBuilder.newBuilder().weakKeys().build((CacheLoader<? super Class<?>, Set<Class<?>>>)new CacheLoader<Class<?>, Set<Class<?>>>() {
            public Set<Class<?>> load(final Class<?> concreteClass) throws Exception {
                final List<Class<?>> parents = (List<Class<?>>)Lists.newLinkedList();
                final Set<Class<?>> classes = (Set<Class<?>>)Sets.newHashSet();
                parents.add(concreteClass);
                while (!parents.isEmpty()) {
                    final Class<?> clazz = parents.remove(0);
                    classes.add(clazz);
                    final Class<?> parent = clazz.getSuperclass();
                    if (parent != null) {
                        parents.add(parent);
                    }
                    for (final Class<?> iface : clazz.getInterfaces()) {
                        parents.add(iface);
                    }
                }
                return classes;
            }
        });
        this.logger = Logger.getLogger(EventBus.class.getName() + "." + identifier);
    }
    
    public void register(final Object object) {
        this.handlersByType.putAll((Multimap<?, ?>)this.finder.findAllHandlers(object));
    }
    
    public void unregister(final Object object) {
        final Multimap<Class<?>, EventHandler> methodsInListener = this.finder.findAllHandlers(object);
        for (final Map.Entry<Class<?>, Collection<EventHandler>> entry : methodsInListener.asMap().entrySet()) {
            final Set<EventHandler> currentHandlers = this.getHandlersForEventType(entry.getKey());
            final Collection<EventHandler> eventMethodsInListener = entry.getValue();
            if (currentHandlers == null || !currentHandlers.containsAll(entry.getValue())) {
                throw new IllegalArgumentException("missing event handler for an annotated method. Is " + object + " registered?");
            }
            currentHandlers.removeAll(eventMethodsInListener);
        }
    }
    
    public void post(final Object event) {
        final Set<Class<?>> dispatchTypes = this.flattenHierarchy(event.getClass());
        boolean dispatched = false;
        for (final Class<?> eventType : dispatchTypes) {
            final Set<EventHandler> wrappers = this.getHandlersForEventType(eventType);
            if (wrappers != null && !wrappers.isEmpty()) {
                dispatched = true;
                for (final EventHandler wrapper : wrappers) {
                    this.enqueueEvent(event, wrapper);
                }
            }
        }
        if (!dispatched && !(event instanceof DeadEvent)) {
            this.post(new DeadEvent(this, event));
        }
        this.dispatchQueuedEvents();
    }
    
    protected void enqueueEvent(final Object event, final EventHandler handler) {
        this.eventsToDispatch.get().offer(new EventWithHandler(event, handler));
    }
    
    protected void dispatchQueuedEvents() {
        if (this.isDispatching.get()) {
            return;
        }
        this.isDispatching.set(true);
        try {
            while (true) {
                final EventWithHandler eventWithHandler = this.eventsToDispatch.get().poll();
                if (eventWithHandler == null) {
                    break;
                }
                this.dispatch(eventWithHandler.event, eventWithHandler.handler);
            }
        }
        finally {
            this.isDispatching.set(false);
        }
    }
    
    protected void dispatch(final Object event, final EventHandler wrapper) {
        try {
            wrapper.handleEvent(event);
        }
        catch (InvocationTargetException e) {
            this.logger.log(Level.SEVERE, "Could not dispatch event: " + event + " to handler " + wrapper, e);
        }
    }
    
    Set<EventHandler> getHandlersForEventType(final Class<?> type) {
        return this.handlersByType.get(type);
    }
    
    protected Set<EventHandler> newHandlerSet() {
        return new CopyOnWriteArraySet<EventHandler>();
    }
    
    @VisibleForTesting
    Set<Class<?>> flattenHierarchy(final Class<?> concreteClass) {
        try {
            return this.flattenHierarchyCache.get(concreteClass);
        }
        catch (ExecutionException e) {
            throw Throwables.propagate(e.getCause());
        }
    }
    
    static class EventWithHandler
    {
        final Object event;
        final EventHandler handler;
        
        public EventWithHandler(final Object event, final EventHandler handler) {
            this.event = event;
            this.handler = handler;
        }
    }
}
