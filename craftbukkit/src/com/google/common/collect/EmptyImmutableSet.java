// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import java.util.Collection;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
final class EmptyImmutableSet extends ImmutableSet<Object>
{
    static final EmptyImmutableSet INSTANCE;
    private static final Object[] EMPTY_ARRAY;
    private static final long serialVersionUID = 0L;
    
    public int size() {
        return 0;
    }
    
    public boolean isEmpty() {
        return true;
    }
    
    public boolean contains(final Object target) {
        return false;
    }
    
    public UnmodifiableIterator<Object> iterator() {
        return Iterators.emptyIterator();
    }
    
    boolean isPartialView() {
        return false;
    }
    
    public Object[] toArray() {
        return EmptyImmutableSet.EMPTY_ARRAY;
    }
    
    public <T> T[] toArray(final T[] a) {
        if (a.length > 0) {
            a[0] = null;
        }
        return a;
    }
    
    public boolean containsAll(final Collection<?> targets) {
        return targets.isEmpty();
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object instanceof Set) {
            final Set<?> that = (Set<?>)object;
            return that.isEmpty();
        }
        return false;
    }
    
    public final int hashCode() {
        return 0;
    }
    
    boolean isHashCodeFast() {
        return true;
    }
    
    public String toString() {
        return "[]";
    }
    
    Object readResolve() {
        return EmptyImmutableSet.INSTANCE;
    }
    
    static {
        INSTANCE = new EmptyImmutableSet();
        EMPTY_ARRAY = new Object[0];
    }
}
