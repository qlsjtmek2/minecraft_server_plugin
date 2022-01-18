// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base;

import com.google.common.annotations.VisibleForTesting;
import java.io.Serializable;
import com.google.common.annotations.Beta;
import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public final class Suppliers
{
    public static <F, T> Supplier<T> compose(final Function<? super F, T> function, final Supplier<F> supplier) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(supplier);
        return new SupplierComposition<Object, T>(function, supplier);
    }
    
    public static <T> Supplier<T> memoize(final Supplier<T> delegate) {
        return (delegate instanceof MemoizingSupplier) ? delegate : new MemoizingSupplier<T>(Preconditions.checkNotNull(delegate));
    }
    
    public static <T> Supplier<T> memoizeWithExpiration(final Supplier<T> delegate, final long duration, final TimeUnit unit) {
        return new ExpiringMemoizingSupplier<T>(delegate, duration, unit);
    }
    
    public static <T> Supplier<T> ofInstance(@Nullable final T instance) {
        return new SupplierOfInstance<T>(instance);
    }
    
    public static <T> Supplier<T> synchronizedSupplier(final Supplier<T> delegate) {
        return new ThreadSafeSupplier<T>(Preconditions.checkNotNull(delegate));
    }
    
    @Beta
    public static <T> Function<Supplier<T>, T> supplierFunction() {
        return (Function<Supplier<T>, T>)SupplierFunction.INSTANCE;
    }
    
    private static class SupplierComposition<F, T> implements Supplier<T>, Serializable
    {
        final Function<? super F, T> function;
        final Supplier<F> supplier;
        private static final long serialVersionUID = 0L;
        
        SupplierComposition(final Function<? super F, T> function, final Supplier<F> supplier) {
            this.function = function;
            this.supplier = supplier;
        }
        
        public T get() {
            return this.function.apply((Object)this.supplier.get());
        }
    }
    
    @VisibleForTesting
    static class MemoizingSupplier<T> implements Supplier<T>, Serializable
    {
        final Supplier<T> delegate;
        transient volatile boolean initialized;
        transient T value;
        private static final long serialVersionUID = 0L;
        
        MemoizingSupplier(final Supplier<T> delegate) {
            this.delegate = delegate;
        }
        
        public T get() {
            if (!this.initialized) {
                synchronized (this) {
                    if (!this.initialized) {
                        final T t = this.delegate.get();
                        this.value = t;
                        this.initialized = true;
                        return t;
                    }
                }
            }
            return this.value;
        }
    }
    
    @VisibleForTesting
    static class ExpiringMemoizingSupplier<T> implements Supplier<T>, Serializable
    {
        final Supplier<T> delegate;
        final long durationNanos;
        transient volatile T value;
        transient volatile long expirationNanos;
        private static final long serialVersionUID = 0L;
        
        ExpiringMemoizingSupplier(final Supplier<T> delegate, final long duration, final TimeUnit unit) {
            this.delegate = Preconditions.checkNotNull(delegate);
            this.durationNanos = unit.toNanos(duration);
            Preconditions.checkArgument(duration > 0L);
        }
        
        public T get() {
            long nanos = this.expirationNanos;
            final long now = Platform.systemNanoTime();
            if (nanos == 0L || now - nanos >= 0L) {
                synchronized (this) {
                    if (nanos == this.expirationNanos) {
                        final T t = this.delegate.get();
                        this.value = t;
                        nanos = now + this.durationNanos;
                        this.expirationNanos = ((nanos == 0L) ? 1L : nanos);
                        return t;
                    }
                }
            }
            return this.value;
        }
    }
    
    private static class SupplierOfInstance<T> implements Supplier<T>, Serializable
    {
        final T instance;
        private static final long serialVersionUID = 0L;
        
        SupplierOfInstance(@Nullable final T instance) {
            this.instance = instance;
        }
        
        public T get() {
            return this.instance;
        }
    }
    
    private static class ThreadSafeSupplier<T> implements Supplier<T>, Serializable
    {
        final Supplier<T> delegate;
        private static final long serialVersionUID = 0L;
        
        ThreadSafeSupplier(final Supplier<T> delegate) {
            this.delegate = delegate;
        }
        
        public T get() {
            synchronized (this.delegate) {
                return this.delegate.get();
            }
        }
    }
    
    private enum SupplierFunction implements Function<Supplier<?>, Object>
    {
        INSTANCE;
        
        public Object apply(final Supplier<?> input) {
            return input.get();
        }
    }
}
