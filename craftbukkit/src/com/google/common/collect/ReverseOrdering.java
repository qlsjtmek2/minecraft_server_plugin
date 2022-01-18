// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;

@GwtCompatible(serializable = true)
final class ReverseOrdering<T> extends Ordering<T> implements Serializable
{
    final Ordering<? super T> forwardOrder;
    private static final long serialVersionUID = 0L;
    
    ReverseOrdering(final Ordering<? super T> forwardOrder) {
        this.forwardOrder = Preconditions.checkNotNull(forwardOrder);
    }
    
    public int compare(final T a, final T b) {
        return this.forwardOrder.compare((Object)b, (Object)a);
    }
    
    public <S extends T> Ordering<S> reverse() {
        return (Ordering<S>)this.forwardOrder;
    }
    
    public <E extends T> E min(final E a, final E b) {
        return this.forwardOrder.max(a, b);
    }
    
    public <E extends T> E min(final E a, final E b, final E c, final E... rest) {
        return this.forwardOrder.max(a, b, c, rest);
    }
    
    public <E extends T> E min(final Iterable<E> iterable) {
        return this.forwardOrder.max(iterable);
    }
    
    public <E extends T> E max(final E a, final E b) {
        return this.forwardOrder.min(a, b);
    }
    
    public <E extends T> E max(final E a, final E b, final E c, final E... rest) {
        return this.forwardOrder.min(a, b, c, rest);
    }
    
    public <E extends T> E max(final Iterable<E> iterable) {
        return this.forwardOrder.min(iterable);
    }
    
    public int hashCode() {
        return -this.forwardOrder.hashCode();
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof ReverseOrdering) {
            final ReverseOrdering<?> that = (ReverseOrdering<?>)object;
            return this.forwardOrder.equals(that.forwardOrder);
        }
        return false;
    }
    
    public String toString() {
        return this.forwardOrder + ".reverse()";
    }
}
