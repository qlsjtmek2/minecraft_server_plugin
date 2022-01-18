// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base;

import java.io.Serializable;
import com.google.common.annotations.Beta;
import javax.annotation.Nullable;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public final class Functions
{
    public static Function<Object, String> toStringFunction() {
        return ToStringFunction.INSTANCE;
    }
    
    public static <E> Function<E, E> identity() {
        return (Function<E, E>)IdentityFunction.INSTANCE;
    }
    
    public static <K, V> Function<K, V> forMap(final Map<K, V> map) {
        return new FunctionForMapNoDefault<K, V>(map);
    }
    
    public static <K, V> Function<K, V> forMap(final Map<K, ? extends V> map, @Nullable final V defaultValue) {
        return new ForMapWithDefault<K, V>(map, defaultValue);
    }
    
    public static <A, B, C> Function<A, C> compose(final Function<B, C> g, final Function<A, ? extends B> f) {
        return new FunctionComposition<A, Object, C>(g, f);
    }
    
    public static <T> Function<T, Boolean> forPredicate(final Predicate<T> predicate) {
        return new PredicateFunction<T>((Predicate)predicate);
    }
    
    public static <E> Function<Object, E> constant(@Nullable final E value) {
        return new ConstantFunction<E>(value);
    }
    
    @Beta
    public static <T> Function<Object, T> forSupplier(final Supplier<T> supplier) {
        return new SupplierFunction<T>((Supplier)supplier);
    }
    
    private enum ToStringFunction implements Function<Object, String>
    {
        INSTANCE;
        
        public String apply(final Object o) {
            Preconditions.checkNotNull(o);
            return o.toString();
        }
        
        public String toString() {
            return "toString";
        }
    }
    
    private enum IdentityFunction implements Function<Object, Object>
    {
        INSTANCE;
        
        public Object apply(final Object o) {
            return o;
        }
        
        public String toString() {
            return "identity";
        }
    }
    
    private static class FunctionForMapNoDefault<K, V> implements Function<K, V>, Serializable
    {
        final Map<K, V> map;
        private static final long serialVersionUID = 0L;
        
        FunctionForMapNoDefault(final Map<K, V> map) {
            this.map = Preconditions.checkNotNull(map);
        }
        
        public V apply(final K key) {
            final V result = this.map.get(key);
            Preconditions.checkArgument(result != null || this.map.containsKey(key), "Key '%s' not present in map", key);
            return result;
        }
        
        public boolean equals(@Nullable final Object o) {
            if (o instanceof FunctionForMapNoDefault) {
                final FunctionForMapNoDefault<?, ?> that = (FunctionForMapNoDefault<?, ?>)o;
                return this.map.equals(that.map);
            }
            return false;
        }
        
        public int hashCode() {
            return this.map.hashCode();
        }
        
        public String toString() {
            return "forMap(" + this.map + ")";
        }
    }
    
    private static class ForMapWithDefault<K, V> implements Function<K, V>, Serializable
    {
        final Map<K, ? extends V> map;
        final V defaultValue;
        private static final long serialVersionUID = 0L;
        
        ForMapWithDefault(final Map<K, ? extends V> map, @Nullable final V defaultValue) {
            this.map = Preconditions.checkNotNull(map);
            this.defaultValue = defaultValue;
        }
        
        public V apply(final K key) {
            final V result = (V)this.map.get(key);
            return (result != null || this.map.containsKey(key)) ? result : this.defaultValue;
        }
        
        public boolean equals(@Nullable final Object o) {
            if (o instanceof ForMapWithDefault) {
                final ForMapWithDefault<?, ?> that = (ForMapWithDefault<?, ?>)o;
                return this.map.equals(that.map) && Objects.equal(this.defaultValue, that.defaultValue);
            }
            return false;
        }
        
        public int hashCode() {
            return Objects.hashCode(this.map, this.defaultValue);
        }
        
        public String toString() {
            return "forMap(" + this.map + ", defaultValue=" + this.defaultValue + ")";
        }
    }
    
    private static class FunctionComposition<A, B, C> implements Function<A, C>, Serializable
    {
        private final Function<B, C> g;
        private final Function<A, ? extends B> f;
        private static final long serialVersionUID = 0L;
        
        public FunctionComposition(final Function<B, C> g, final Function<A, ? extends B> f) {
            this.g = Preconditions.checkNotNull(g);
            this.f = Preconditions.checkNotNull(f);
        }
        
        public C apply(final A a) {
            return this.g.apply((B)this.f.apply(a));
        }
        
        public boolean equals(@Nullable final Object obj) {
            if (obj instanceof FunctionComposition) {
                final FunctionComposition<?, ?, ?> that = (FunctionComposition<?, ?, ?>)obj;
                return this.f.equals(that.f) && this.g.equals(that.g);
            }
            return false;
        }
        
        public int hashCode() {
            return this.f.hashCode() ^ this.g.hashCode();
        }
        
        public String toString() {
            return this.g.toString() + "(" + this.f.toString() + ")";
        }
    }
    
    private static class PredicateFunction<T> implements Function<T, Boolean>, Serializable
    {
        private final Predicate<T> predicate;
        private static final long serialVersionUID = 0L;
        
        private PredicateFunction(final Predicate<T> predicate) {
            this.predicate = Preconditions.checkNotNull(predicate);
        }
        
        public Boolean apply(final T t) {
            return this.predicate.apply(t);
        }
        
        public boolean equals(@Nullable final Object obj) {
            if (obj instanceof PredicateFunction) {
                final PredicateFunction<?> that = (PredicateFunction<?>)obj;
                return this.predicate.equals(that.predicate);
            }
            return false;
        }
        
        public int hashCode() {
            return this.predicate.hashCode();
        }
        
        public String toString() {
            return "forPredicate(" + this.predicate + ")";
        }
    }
    
    private static class ConstantFunction<E> implements Function<Object, E>, Serializable
    {
        private final E value;
        private static final long serialVersionUID = 0L;
        
        public ConstantFunction(@Nullable final E value) {
            this.value = value;
        }
        
        public E apply(@Nullable final Object from) {
            return this.value;
        }
        
        public boolean equals(@Nullable final Object obj) {
            if (obj instanceof ConstantFunction) {
                final ConstantFunction<?> that = (ConstantFunction<?>)obj;
                return Objects.equal(this.value, that.value);
            }
            return false;
        }
        
        public int hashCode() {
            return (this.value == null) ? 0 : this.value.hashCode();
        }
        
        public String toString() {
            return "constant(" + this.value + ")";
        }
    }
    
    private static class SupplierFunction<T> implements Function<Object, T>, Serializable
    {
        private final Supplier<T> supplier;
        private static final long serialVersionUID = 0L;
        
        private SupplierFunction(final Supplier<T> supplier) {
            this.supplier = Preconditions.checkNotNull(supplier);
        }
        
        public T apply(@Nullable final Object input) {
            return this.supplier.get();
        }
        
        public boolean equals(@Nullable final Object obj) {
            if (obj instanceof SupplierFunction) {
                final SupplierFunction<?> that = (SupplierFunction<?>)obj;
                return this.supplier.equals(that.supplier);
            }
            return false;
        }
        
        public int hashCode() {
            return this.supplier.hashCode();
        }
        
        public String toString() {
            return "forSupplier(" + this.supplier + ")";
        }
    }
}
