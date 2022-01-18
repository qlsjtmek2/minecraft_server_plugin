// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base;

import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.Beta;
import java.io.Serializable;

@Beta
@GwtCompatible
public abstract class Optional<T> implements Serializable
{
    private static final long serialVersionUID = 0L;
    
    public static <T> Optional<T> absent() {
        return (Optional<T>)Absent.INSTANCE;
    }
    
    public static <T> Optional<T> of(final T reference) {
        return new Present<T>(Preconditions.checkNotNull(reference));
    }
    
    public static <T> Optional<T> fromNullable(@Nullable final T nullableReference) {
        return (nullableReference == null) ? absent() : new Present<T>(nullableReference);
    }
    
    public abstract boolean isPresent();
    
    public abstract T get();
    
    public abstract T or(final T p0);
    
    public abstract Optional<T> or(final Optional<? extends T> p0);
    
    @Nullable
    public abstract T or(final Supplier<? extends T> p0);
    
    @Nullable
    public abstract T orNull();
    
    public abstract boolean equals(@Nullable final Object p0);
    
    public abstract int hashCode();
    
    public abstract String toString();
    
    private static final class Present<T> extends Optional<T>
    {
        private final T reference;
        private static final long serialVersionUID = 0L;
        
        Present(final T reference) {
            super(null);
            this.reference = reference;
        }
        
        public boolean isPresent() {
            return true;
        }
        
        public T get() {
            return this.reference;
        }
        
        public T or(final T defaultValue) {
            Preconditions.checkNotNull(defaultValue, (Object)"use orNull() instead of or(null)");
            return this.reference;
        }
        
        public Optional<T> or(final Optional<? extends T> secondChoice) {
            Preconditions.checkNotNull(secondChoice);
            return this;
        }
        
        public T or(final Supplier<? extends T> supplier) {
            Preconditions.checkNotNull(supplier);
            return this.reference;
        }
        
        public T orNull() {
            return this.reference;
        }
        
        public boolean equals(@Nullable final Object object) {
            if (object instanceof Present) {
                final Present<?> other = (Present<?>)object;
                return this.reference.equals(other.reference);
            }
            return false;
        }
        
        public int hashCode() {
            return 1502476572 + this.reference.hashCode();
        }
        
        public String toString() {
            return "Optional.of(" + this.reference + ")";
        }
    }
    
    private static final class Absent extends Optional<Object>
    {
        private static final Absent INSTANCE;
        private static final long serialVersionUID = 0L;
        
        private Absent() {
            super(null);
        }
        
        public boolean isPresent() {
            return false;
        }
        
        public Object get() {
            throw new IllegalStateException("value is absent");
        }
        
        public Object or(final Object defaultValue) {
            return Preconditions.checkNotNull(defaultValue, (Object)"use orNull() instead of or(null)");
        }
        
        public Optional<Object> or(final Optional<?> secondChoice) {
            return Preconditions.checkNotNull(secondChoice);
        }
        
        @Nullable
        public Object or(final Supplier<?> supplier) {
            return supplier.get();
        }
        
        @Nullable
        public Object orNull() {
            return null;
        }
        
        public boolean equals(@Nullable final Object object) {
            return object == this;
        }
        
        public int hashCode() {
            return 1502476572;
        }
        
        public String toString() {
            return "Optional.absent()";
        }
        
        private Object readResolve() {
            return Absent.INSTANCE;
        }
        
        static {
            INSTANCE = new Absent();
        }
    }
}
