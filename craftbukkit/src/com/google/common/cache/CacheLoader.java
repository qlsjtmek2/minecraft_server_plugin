// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.cache;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import com.google.common.base.Supplier;
import com.google.common.base.Function;
import com.google.common.annotations.Beta;

@Beta
public abstract class CacheLoader<K, V>
{
    public static <K, V> CacheLoader<K, V> from(final Function<K, V> function) {
        return new FunctionToCacheLoader<K, V>(function);
    }
    
    public static <V> CacheLoader<Object, V> from(final Supplier<V> supplier) {
        return (CacheLoader<Object, V>)new SupplierToCacheLoader((Supplier<Object>)supplier);
    }
    
    public abstract V load(final K p0) throws Exception;
    
    private static final class FunctionToCacheLoader<K, V> extends CacheLoader<K, V> implements Serializable
    {
        private final Function<K, V> computingFunction;
        private static final long serialVersionUID = 0L;
        
        public FunctionToCacheLoader(final Function<K, V> computingFunction) {
            this.computingFunction = Preconditions.checkNotNull(computingFunction);
        }
        
        public V load(final K key) {
            return this.computingFunction.apply(key);
        }
    }
    
    private static final class SupplierToCacheLoader<V> extends CacheLoader<Object, V> implements Serializable
    {
        private final Supplier<V> computingSupplier;
        private static final long serialVersionUID = 0L;
        
        public SupplierToCacheLoader(final Supplier<V> computingSupplier) {
            this.computingSupplier = Preconditions.checkNotNull(computingSupplier);
        }
        
        public V load(final Object key) {
            return this.computingSupplier.get();
        }
    }
}
