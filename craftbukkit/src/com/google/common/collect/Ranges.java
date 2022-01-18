// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import com.google.common.base.Preconditions;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@Beta
public final class Ranges
{
    static <C extends Comparable<?>> Range<C> create(final Cut<C> lowerBound, final Cut<C> upperBound) {
        return new Range<C>(lowerBound, upperBound);
    }
    
    public static <C extends Comparable<?>> Range<C> open(final C lower, final C upper) {
        return create((Cut<C>)Cut.aboveValue((C)lower), (Cut<C>)Cut.belowValue((C)upper));
    }
    
    public static <C extends Comparable<?>> Range<C> closed(final C lower, final C upper) {
        return create((Cut<C>)Cut.belowValue((C)lower), (Cut<C>)Cut.aboveValue((C)upper));
    }
    
    public static <C extends Comparable<?>> Range<C> closedOpen(final C lower, final C upper) {
        return create((Cut<C>)Cut.belowValue((C)lower), (Cut<C>)Cut.belowValue((C)upper));
    }
    
    public static <C extends Comparable<?>> Range<C> openClosed(final C lower, final C upper) {
        return create((Cut<C>)Cut.aboveValue((C)lower), (Cut<C>)Cut.aboveValue((C)upper));
    }
    
    public static <C extends Comparable<?>> Range<C> range(final C lower, final BoundType lowerType, final C upper, final BoundType upperType) {
        Preconditions.checkNotNull(lowerType);
        Preconditions.checkNotNull(upperType);
        final Cut<C> lowerBound = (lowerType == BoundType.OPEN) ? Cut.aboveValue(lower) : Cut.belowValue(lower);
        final Cut<C> upperBound = (upperType == BoundType.OPEN) ? Cut.belowValue(upper) : Cut.aboveValue(upper);
        return create(lowerBound, upperBound);
    }
    
    public static <C extends Comparable<?>> Range<C> lessThan(final C endpoint) {
        return create(Cut.belowAll(), (Cut<C>)Cut.belowValue((C)endpoint));
    }
    
    public static <C extends Comparable<?>> Range<C> atMost(final C endpoint) {
        return create(Cut.belowAll(), (Cut<C>)Cut.aboveValue((C)endpoint));
    }
    
    public static <C extends Comparable<?>> Range<C> upTo(final C endpoint, final BoundType boundType) {
        switch (boundType) {
            case OPEN: {
                return lessThan(endpoint);
            }
            case CLOSED: {
                return atMost(endpoint);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    public static <C extends Comparable<?>> Range<C> greaterThan(final C endpoint) {
        return create((Cut<C>)Cut.aboveValue((C)endpoint), Cut.aboveAll());
    }
    
    public static <C extends Comparable<?>> Range<C> atLeast(final C endpoint) {
        return create((Cut<C>)Cut.belowValue((C)endpoint), Cut.aboveAll());
    }
    
    public static <C extends Comparable<?>> Range<C> downTo(final C endpoint, final BoundType boundType) {
        switch (boundType) {
            case OPEN: {
                return greaterThan(endpoint);
            }
            case CLOSED: {
                return atLeast(endpoint);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    public static <C extends Comparable<?>> Range<C> all() {
        return create(Cut.belowAll(), Cut.aboveAll());
    }
    
    public static <C extends Comparable<?>> Range<C> singleton(final C value) {
        return closed(value, value);
    }
    
    public static <C extends Comparable<?>> Range<C> encloseAll(final Iterable<C> values) {
        Preconditions.checkNotNull(values);
        if (values instanceof ContiguousSet) {
            return ((ContiguousSet)values).range();
        }
        final Iterator<C> valueIterator = values.iterator();
        C max;
        C min = max = Preconditions.checkNotNull(valueIterator.next());
        while (valueIterator.hasNext()) {
            final C value = Preconditions.checkNotNull(valueIterator.next());
            min = Ordering.natural().min(min, value);
            max = Ordering.natural().max(max, value);
        }
        return closed(min, max);
    }
}
