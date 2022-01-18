// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util;

import java.util.concurrent.atomic.AtomicReference;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultAttributeMap implements AttributeMap
{
    private static final AtomicReferenceFieldUpdater<DefaultAttributeMap, Map> updater;
    private volatile Map<AttributeKey<?>, Attribute<?>> map;
    
    @Override
    public <T> Attribute<T> attr(final AttributeKey<T> key) {
        Map<AttributeKey<?>, Attribute<?>> map = this.map;
        if (map == null) {
            map = new IdentityHashMap<AttributeKey<?>, Attribute<?>>(2);
            if (!DefaultAttributeMap.updater.compareAndSet(this, null, map)) {
                map = this.map;
            }
        }
        synchronized (map) {
            Attribute<T> attr = (Attribute<T>)map.get(key);
            if (attr == null) {
                attr = new DefaultAttribute<T>(map, key);
                map.put(key, attr);
            }
            return attr;
        }
    }
    
    static {
        updater = AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, Map.class, "map");
    }
    
    private static final class DefaultAttribute<T> extends AtomicReference<T> implements Attribute<T>
    {
        private static final long serialVersionUID = -2661411462200283011L;
        private final Map<AttributeKey<?>, Attribute<?>> map;
        private final AttributeKey<T> key;
        
        DefaultAttribute(final Map<AttributeKey<?>, Attribute<?>> map, final AttributeKey<T> key) {
            this.map = map;
            this.key = key;
        }
        
        @Override
        public AttributeKey<T> key() {
            return this.key;
        }
        
        @Override
        public T setIfAbsent(final T value) {
            while (!this.compareAndSet(null, value)) {
                final T old = this.get();
                if (old != null) {
                    return old;
                }
            }
            return null;
        }
        
        @Override
        public T getAndRemove() {
            final T oldValue = this.getAndSet(null);
            this.remove0();
            return oldValue;
        }
        
        @Override
        public void remove() {
            this.set(null);
            this.remove0();
        }
        
        private void remove0() {
            synchronized (this.map) {
                this.map.remove(this.key);
            }
        }
    }
}
