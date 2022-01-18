// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import javax.annotation.Nullable;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Comparator;
import java.util.SortedSet;
import com.google.common.base.Preconditions;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import com.google.common.base.Predicate;

@GwtCompatible
@Beta
public final class Range<C extends Comparable> implements Predicate<C>, Serializable
{
    final Cut<C> lowerBound;
    final Cut<C> upperBound;
    private static final long serialVersionUID = 0L;
    
    Range(final Cut<C> lowerBound, final Cut<C> upperBound) {
        if (lowerBound.compareTo(upperBound) > 0) {
            throw new IllegalArgumentException("Invalid range: " + toString(lowerBound, upperBound));
        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
    
    public boolean hasLowerBound() {
        return this.lowerBound != Cut.belowAll();
    }
    
    public C lowerEndpoint() {
        return this.lowerBound.endpoint();
    }
    
    public BoundType lowerBoundType() {
        return this.lowerBound.typeAsLowerBound();
    }
    
    public boolean hasUpperBound() {
        return this.upperBound != Cut.aboveAll();
    }
    
    public C upperEndpoint() {
        return this.upperBound.endpoint();
    }
    
    public BoundType upperBoundType() {
        return this.upperBound.typeAsUpperBound();
    }
    
    public boolean isEmpty() {
        return this.lowerBound.equals(this.upperBound);
    }
    
    public boolean contains(final C value) {
        Preconditions.checkNotNull(value);
        return this.lowerBound.isLessThan(value) && !this.upperBound.isLessThan(value);
    }
    
    public boolean apply(final C input) {
        return this.contains(input);
    }
    
    public boolean containsAll(final Iterable<? extends C> values) {
        if (Iterables.isEmpty(values)) {
            return true;
        }
        if (values instanceof SortedSet) {
            final SortedSet<? extends C> set = cast(values);
            final Comparator<?> comparator = set.comparator();
            if (Ordering.natural().equals(comparator) || comparator == null) {
                return this.contains((C)set.first()) && this.contains((C)set.last());
            }
        }
        for (final C value : values) {
            if (!this.contains(value)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean encloses(final Range<C> other) {
        return this.lowerBound.compareTo(other.lowerBound) <= 0 && this.upperBound.compareTo(other.upperBound) >= 0;
    }
    
    public Range<C> intersection(final Range<C> other) {
        final Cut<C> newLower = Ordering.natural().max(this.lowerBound, other.lowerBound);
        final Cut<C> newUpper = Ordering.natural().min(this.upperBound, other.upperBound);
        return Ranges.create(newLower, newUpper);
    }
    
    public boolean isConnected(final Range<C> other) {
        return this.lowerBound.compareTo(other.upperBound) <= 0 && other.lowerBound.compareTo(this.upperBound) <= 0;
    }
    
    public Range<C> span(final Range<C> other) {
        final Cut<C> newLower = Ordering.natural().min(this.lowerBound, other.lowerBound);
        final Cut<C> newUpper = Ordering.natural().max(this.upperBound, other.upperBound);
        return Ranges.create(newLower, newUpper);
    }
    
    @GwtCompatible(serializable = false)
    public ContiguousSet<C> asSet(final DiscreteDomain<C> domain) {
        Preconditions.checkNotNull(domain);
        Range<C> effectiveRange = this;
        try {
            if (!this.hasLowerBound()) {
                effectiveRange = effectiveRange.intersection(Ranges.atLeast(domain.minValue()));
            }
            if (!this.hasUpperBound()) {
                effectiveRange = effectiveRange.intersection(Ranges.atMost(domain.maxValue()));
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException(e);
        }
        final boolean empty = effectiveRange.isEmpty() || compareOrThrow(this.lowerBound.leastValueAbove(domain), this.upperBound.greatestValueBelow(domain)) > 0;
        return (ContiguousSet<C>)(empty ? new EmptyContiguousSet<Object>(domain) : new RegularContiguousSet<Object>(effectiveRange, domain));
    }
    
    public Range<C> canonical(final DiscreteDomain<C> domain) {
        Preconditions.checkNotNull(domain);
        final Cut<C> lower = this.lowerBound.canonical(domain);
        final Cut<C> upper = this.upperBound.canonical(domain);
        return (lower == this.lowerBound && upper == this.upperBound) ? this : Ranges.create(lower, upper);
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object instanceof Range) {
            final Range<?> other = (Range<?>)object;
            return this.lowerBound.equals(other.lowerBound) && this.upperBound.equals(other.upperBound);
        }
        return false;
    }
    
    public int hashCode() {
        return this.lowerBound.hashCode() * 31 + this.upperBound.hashCode();
    }
    
    public String toString() {
        return toString(this.lowerBound, this.upperBound);
    }
    
    private static String toString(final Cut<?> lowerBound, final Cut<?> upperBound) {
        final StringBuilder sb = new StringBuilder(16);
        lowerBound.describeAsLowerBound(sb);
        sb.append('\u2025');
        upperBound.describeAsUpperBound(sb);
        return sb.toString();
    }
    
    private static <T> SortedSet<T> cast(final Iterable<T> iterable) {
        return (SortedSet<T>)(SortedSet)iterable;
    }
    
    static int compareOrThrow(final Comparable left, final Comparable right) {
        return left.compareTo(right);
    }
}
