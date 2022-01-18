// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.Serializable;
import java.util.Iterator;
import com.google.common.annotations.GwtIncompatible;
import java.util.Set;
import javax.annotation.Nullable;
import java.util.NoSuchElementException;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
final class EmptyContiguousSet<C extends Comparable> extends ContiguousSet<C>
{
    EmptyContiguousSet(final DiscreteDomain<C> domain) {
        super(domain);
    }
    
    public C first() {
        throw new NoSuchElementException();
    }
    
    public C last() {
        throw new NoSuchElementException();
    }
    
    public int size() {
        return 0;
    }
    
    public ContiguousSet<C> intersection(final ContiguousSet<C> other) {
        return this;
    }
    
    public Range<C> range() {
        throw new NoSuchElementException();
    }
    
    public Range<C> range(final BoundType lowerBoundType, final BoundType upperBoundType) {
        throw new NoSuchElementException();
    }
    
    ContiguousSet<C> headSetImpl(final C toElement, final boolean inclusive) {
        return this;
    }
    
    ContiguousSet<C> subSetImpl(final C fromElement, final boolean fromInclusive, final C toElement, final boolean toInclusive) {
        return this;
    }
    
    ContiguousSet<C> tailSetImpl(final C fromElement, final boolean fromInclusive) {
        return this;
    }
    
    int indexOf(final Object target) {
        return -1;
    }
    
    public UnmodifiableIterator<C> iterator() {
        return Iterators.emptyIterator();
    }
    
    boolean isPartialView() {
        return false;
    }
    
    public boolean isEmpty() {
        return true;
    }
    
    public ImmutableList<C> asList() {
        return ImmutableList.of();
    }
    
    public String toString() {
        return "[]";
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object instanceof Set) {
            final Set<?> that = (Set<?>)object;
            return that.isEmpty();
        }
        return false;
    }
    
    public int hashCode() {
        return 0;
    }
    
    @GwtIncompatible("serialization")
    Object writeReplace() {
        return new SerializedForm((DiscreteDomain)this.domain);
    }
    
    @GwtIncompatible("serialization")
    private static final class SerializedForm<C extends Comparable> implements Serializable
    {
        private final DiscreteDomain<C> domain;
        private static final long serialVersionUID = 0L;
        
        private SerializedForm(final DiscreteDomain<C> domain) {
            this.domain = domain;
        }
        
        private Object readResolve() {
            return new EmptyContiguousSet((DiscreteDomain<Comparable>)this.domain);
        }
    }
}
