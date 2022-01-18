// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Objects;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.base.Optional;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;

@GwtCompatible(serializable = true)
final class GeneralRange<T> implements Serializable
{
    private final Comparator<? super T> comparator;
    private final Optional<T> lowerEndpoint;
    private final BoundType lowerBoundType;
    private final Optional<T> upperEndpoint;
    private final BoundType upperBoundType;
    private transient GeneralRange<T> reverse;
    
    static <T extends Comparable> GeneralRange<T> from(final Range<T> range) {
        final Optional<T> lowerEndpoint = range.hasLowerBound() ? Optional.of(range.lowerEndpoint()) : Optional.absent();
        final BoundType lowerBoundType = range.hasLowerBound() ? range.lowerBoundType() : BoundType.OPEN;
        final Optional<T> upperEndpoint = range.hasUpperBound() ? Optional.of(range.upperEndpoint()) : Optional.absent();
        final BoundType upperBoundType = range.hasUpperBound() ? range.upperBoundType() : BoundType.OPEN;
        return new GeneralRange<T>(Ordering.natural(), lowerEndpoint, lowerBoundType, upperEndpoint, upperBoundType);
    }
    
    static <T> GeneralRange<T> all(final Comparator<? super T> comparator) {
        return new GeneralRange<T>(comparator, Optional.absent(), BoundType.OPEN, Optional.absent(), BoundType.OPEN);
    }
    
    static <T> GeneralRange<T> downTo(final Comparator<? super T> comparator, final T endpoint, final BoundType boundType) {
        return new GeneralRange<T>(comparator, Optional.of(endpoint), boundType, Optional.absent(), BoundType.OPEN);
    }
    
    static <T> GeneralRange<T> upTo(final Comparator<? super T> comparator, final T endpoint, final BoundType boundType) {
        return new GeneralRange<T>(comparator, Optional.absent(), BoundType.OPEN, Optional.of(endpoint), boundType);
    }
    
    static <T> GeneralRange<T> range(final Comparator<? super T> comparator, final T lower, final BoundType lowerType, final T upper, final BoundType upperType) {
        return new GeneralRange<T>(comparator, Optional.of(lower), lowerType, Optional.of(upper), upperType);
    }
    
    private GeneralRange(final Comparator<? super T> comparator, final Optional<T> lowerEndpoint, final BoundType lowerBoundType, final Optional<T> upperEndpoint, final BoundType upperBoundType) {
        this.comparator = Preconditions.checkNotNull(comparator);
        this.lowerEndpoint = Preconditions.checkNotNull(lowerEndpoint);
        this.lowerBoundType = Preconditions.checkNotNull(lowerBoundType);
        this.upperEndpoint = Preconditions.checkNotNull(upperEndpoint);
        this.upperBoundType = Preconditions.checkNotNull(upperBoundType);
        if (lowerEndpoint.isPresent() && upperEndpoint.isPresent()) {
            final int cmp = comparator.compare((Object)lowerEndpoint.get(), (Object)upperEndpoint.get());
            Preconditions.checkArgument(cmp <= 0, "lowerEndpoint (%s) > upperEndpoint (%s)", lowerEndpoint, upperEndpoint);
            if (cmp == 0) {
                Preconditions.checkArgument(lowerBoundType != BoundType.OPEN | upperBoundType != BoundType.OPEN);
            }
        }
    }
    
    Comparator<? super T> comparator() {
        return this.comparator;
    }
    
    boolean hasLowerBound() {
        return this.lowerEndpoint.isPresent();
    }
    
    boolean hasUpperBound() {
        return this.upperEndpoint.isPresent();
    }
    
    boolean isEmpty() {
        return (this.hasUpperBound() && this.tooLow(this.upperEndpoint.get())) || (this.hasLowerBound() && this.tooHigh(this.lowerEndpoint.get()));
    }
    
    boolean tooLow(final T t) {
        if (!this.hasLowerBound()) {
            return false;
        }
        final T lbound = this.lowerEndpoint.get();
        final int cmp = this.comparator.compare((Object)t, (Object)lbound);
        return cmp < 0 | (cmp == 0 & this.lowerBoundType == BoundType.OPEN);
    }
    
    boolean tooHigh(final T t) {
        if (!this.hasUpperBound()) {
            return false;
        }
        final T ubound = this.upperEndpoint.get();
        final int cmp = this.comparator.compare((Object)t, (Object)ubound);
        return cmp > 0 | (cmp == 0 & this.upperBoundType == BoundType.OPEN);
    }
    
    boolean contains(final T t) {
        Preconditions.checkNotNull(t);
        return !this.tooLow(t) && !this.tooHigh(t);
    }
    
    GeneralRange<T> intersect(final GeneralRange<T> other) {
        Preconditions.checkNotNull(other);
        Preconditions.checkArgument(this.comparator.equals(other.comparator));
        Optional<T> lowEnd = this.lowerEndpoint;
        BoundType lowType = this.lowerBoundType;
        if (!this.hasLowerBound()) {
            lowEnd = other.lowerEndpoint;
            lowType = other.lowerBoundType;
        }
        else if (other.hasLowerBound()) {
            final int cmp = this.comparator.compare((Object)this.lowerEndpoint.get(), (Object)other.lowerEndpoint.get());
            if (cmp < 0 || (cmp == 0 && other.lowerBoundType == BoundType.OPEN)) {
                lowEnd = other.lowerEndpoint;
                lowType = other.lowerBoundType;
            }
        }
        Optional<T> upEnd = this.upperEndpoint;
        BoundType upType = this.upperBoundType;
        if (!this.hasUpperBound()) {
            upEnd = other.upperEndpoint;
            upType = other.upperBoundType;
        }
        else if (other.hasUpperBound()) {
            final int cmp2 = this.comparator.compare((Object)this.upperEndpoint.get(), (Object)other.upperEndpoint.get());
            if (cmp2 > 0 || (cmp2 == 0 && other.upperBoundType == BoundType.OPEN)) {
                upEnd = other.upperEndpoint;
                upType = other.upperBoundType;
            }
        }
        if (lowEnd.isPresent() && upEnd.isPresent()) {
            final int cmp2 = this.comparator.compare((Object)lowEnd.get(), (Object)upEnd.get());
            if (cmp2 > 0 || (cmp2 == 0 && lowType == BoundType.OPEN && upType == BoundType.OPEN)) {
                lowEnd = upEnd;
                lowType = BoundType.OPEN;
                upType = BoundType.CLOSED;
            }
        }
        return new GeneralRange<T>(this.comparator, lowEnd, lowType, upEnd, upType);
    }
    
    public boolean equals(@Nullable final Object obj) {
        if (obj instanceof GeneralRange) {
            final GeneralRange<?> r = (GeneralRange<?>)obj;
            return this.comparator.equals(r.comparator) && this.lowerEndpoint.equals(r.lowerEndpoint) && this.lowerBoundType.equals(r.lowerBoundType) && this.upperEndpoint.equals(r.upperEndpoint) && this.upperBoundType.equals(r.upperBoundType);
        }
        return false;
    }
    
    public int hashCode() {
        return Objects.hashCode(this.comparator, this.lowerEndpoint, this.lowerBoundType, this.upperEndpoint, this.upperBoundType);
    }
    
    public GeneralRange<T> reverse() {
        GeneralRange<T> result = this.reverse;
        if (result == null) {
            result = new GeneralRange<T>(Ordering.from(this.comparator).reverse(), this.upperEndpoint, this.upperBoundType, this.lowerEndpoint, this.lowerBoundType);
            result.reverse = this;
            return this.reverse = result;
        }
        return result;
    }
    
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.comparator).append(":");
        switch (this.lowerBoundType) {
            case CLOSED: {
                builder.append('[');
                break;
            }
            case OPEN: {
                builder.append('(');
                break;
            }
        }
        if (this.hasLowerBound()) {
            builder.append(this.lowerEndpoint.get());
        }
        else {
            builder.append("-\u221e");
        }
        builder.append(',');
        if (this.hasUpperBound()) {
            builder.append(this.upperEndpoint.get());
        }
        else {
            builder.append("\u221e");
        }
        switch (this.upperBoundType) {
            case CLOSED: {
                builder.append(']');
                break;
            }
            case OPEN: {
                builder.append(')');
                break;
            }
        }
        return builder.toString();
    }
}
