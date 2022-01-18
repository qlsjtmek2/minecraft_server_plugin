// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.Serializable;
import java.util.Iterator;
import com.google.common.annotations.GwtIncompatible;
import java.util.Set;
import com.google.common.base.Preconditions;
import java.util.Collection;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
final class RegularContiguousSet<C extends Comparable> extends ContiguousSet<C>
{
    private final Range<C> range;
    private static final long serialVersionUID = 0L;
    
    RegularContiguousSet(final Range<C> range, final DiscreteDomain<C> domain) {
        super(domain);
        this.range = range;
    }
    
    ContiguousSet<C> headSetImpl(final C toElement, final boolean inclusive) {
        return this.range.intersection(Ranges.upTo(toElement, BoundType.forBoolean(inclusive))).asSet(this.domain);
    }
    
    int indexOf(final Object target) {
        return this.contains(target) ? ((int)this.domain.distance(this.first(), (C)target)) : -1;
    }
    
    ContiguousSet<C> subSetImpl(final C fromElement, final boolean fromInclusive, final C toElement, final boolean toInclusive) {
        return this.range.intersection(Ranges.range(fromElement, BoundType.forBoolean(fromInclusive), toElement, BoundType.forBoolean(toInclusive))).asSet(this.domain);
    }
    
    ContiguousSet<C> tailSetImpl(final C fromElement, final boolean inclusive) {
        return this.range.intersection(Ranges.downTo(fromElement, BoundType.forBoolean(inclusive))).asSet(this.domain);
    }
    
    public UnmodifiableIterator<C> iterator() {
        return new AbstractLinkedIterator<C>(this.first()) {
            final C last = RegularContiguousSet.this.last();
            
            protected C computeNext(final C previous) {
                return (C)(equalsOrThrow(previous, this.last) ? null : RegularContiguousSet.this.domain.next((C)previous));
            }
        };
    }
    
    private static boolean equalsOrThrow(final Comparable<?> left, @Nullable final Comparable<?> right) {
        return right != null && Range.compareOrThrow(left, right) == 0;
    }
    
    boolean isPartialView() {
        return false;
    }
    
    public C first() {
        return this.range.lowerBound.leastValueAbove(this.domain);
    }
    
    public C last() {
        return this.range.upperBound.greatestValueBelow(this.domain);
    }
    
    public int size() {
        final long distance = this.domain.distance(this.first(), this.last());
        return (distance >= 2147483647L) ? Integer.MAX_VALUE : ((int)distance + 1);
    }
    
    public boolean contains(final Object object) {
        try {
            return this.range.contains((C)object);
        }
        catch (ClassCastException e) {
            return false;
        }
    }
    
    public boolean containsAll(final Collection<?> targets) {
        try {
            return this.range.containsAll((Iterable<? extends C>)targets);
        }
        catch (ClassCastException e) {
            return false;
        }
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public Object[] toArray() {
        return ObjectArrays.toArrayImpl(this);
    }
    
    public <T> T[] toArray(final T[] other) {
        return ObjectArrays.toArrayImpl(this, other);
    }
    
    public ContiguousSet<C> intersection(final ContiguousSet<C> other) {
        Preconditions.checkNotNull(other);
        Preconditions.checkArgument(this.domain.equals(other.domain));
        if (other.isEmpty()) {
            return other;
        }
        final C lowerEndpoint = Ordering.natural().max(this.first(), other.first());
        final C upperEndpoint = Ordering.natural().min(this.last(), other.last());
        return (lowerEndpoint.compareTo(upperEndpoint) < 0) ? Ranges.closed(lowerEndpoint, upperEndpoint).asSet(this.domain) : new EmptyContiguousSet<C>(this.domain);
    }
    
    public Range<C> range() {
        return this.range(BoundType.CLOSED, BoundType.CLOSED);
    }
    
    public Range<C> range(final BoundType lowerBoundType, final BoundType upperBoundType) {
        return Ranges.create(this.range.lowerBound.withLowerBoundType(lowerBoundType, this.domain), this.range.upperBound.withUpperBoundType(upperBoundType, this.domain));
    }
    
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof RegularContiguousSet) {
            final RegularContiguousSet<?> that = (RegularContiguousSet<?>)object;
            if (this.domain.equals(that.domain)) {
                return this.first().equals(that.first()) && this.last().equals(that.last());
            }
        }
        return super.equals(object);
    }
    
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }
    
    @GwtIncompatible("serialization")
    Object writeReplace() {
        return new SerializedForm((Range)this.range, (DiscreteDomain)this.domain);
    }
    
    @GwtIncompatible("serialization")
    private static final class SerializedForm<C extends Comparable> implements Serializable
    {
        final Range<C> range;
        final DiscreteDomain<C> domain;
        
        private SerializedForm(final Range<C> range, final DiscreteDomain<C> domain) {
            this.range = range;
            this.domain = domain;
        }
        
        private Object readResolve() {
            return new RegularContiguousSet((Range<Comparable>)this.range, (DiscreteDomain<Comparable>)this.domain);
        }
    }
}
