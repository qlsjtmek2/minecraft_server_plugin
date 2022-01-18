// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import com.google.common.annotations.VisibleForTesting;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.annotations.Beta;
import java.util.Collections;
import java.util.Arrays;
import javax.annotation.Nullable;
import com.google.common.base.Function;
import java.util.List;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;
import java.util.Comparator;

@GwtCompatible
public abstract class Ordering<T> implements Comparator<T>
{
    static final int LEFT_IS_GREATER = 1;
    static final int RIGHT_IS_GREATER = -1;
    
    @GwtCompatible(serializable = true)
    public static <C extends Comparable> Ordering<C> natural() {
        return (Ordering<C>)NaturalOrdering.INSTANCE;
    }
    
    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> from(final Comparator<T> comparator) {
        return (comparator instanceof Ordering) ? ((Ordering)comparator) : new ComparatorOrdering<T>(comparator);
    }
    
    @Deprecated
    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> from(final Ordering<T> ordering) {
        return Preconditions.checkNotNull(ordering);
    }
    
    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> explicit(final List<T> valuesInOrder) {
        return new ExplicitOrdering<T>(valuesInOrder);
    }
    
    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> explicit(final T leastValue, final T... remainingValuesInOrder) {
        return explicit((List<T>)Lists.asList(leastValue, (T[])remainingValuesInOrder));
    }
    
    public static Ordering<Object> arbitrary() {
        return ArbitraryOrderingHolder.ARBITRARY_ORDERING;
    }
    
    @GwtCompatible(serializable = true)
    public static Ordering<Object> usingToString() {
        return UsingToStringOrdering.INSTANCE;
    }
    
    @GwtCompatible(serializable = true)
    public static <T> Ordering<T> compound(final Iterable<? extends Comparator<? super T>> comparators) {
        return new CompoundOrdering<T>(comparators);
    }
    
    @GwtCompatible(serializable = true)
    public <U extends T> Ordering<U> compound(final Comparator<? super U> secondaryComparator) {
        return new CompoundOrdering<U>(this, Preconditions.checkNotNull(secondaryComparator));
    }
    
    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<S> reverse() {
        return new ReverseOrdering<S>(this);
    }
    
    @GwtCompatible(serializable = true)
    public <F> Ordering<F> onResultOf(final Function<F, ? extends T> function) {
        return (Ordering<F>)new ByFunctionOrdering((Function<Object, ?>)function, (Ordering<Object>)this);
    }
    
    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<Iterable<S>> lexicographical() {
        return (Ordering<Iterable<S>>)new LexicographicalOrdering((Ordering<? super Object>)this);
    }
    
    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<S> nullsFirst() {
        return new NullsFirstOrdering<S>(this);
    }
    
    @GwtCompatible(serializable = true)
    public <S extends T> Ordering<S> nullsLast() {
        return new NullsLastOrdering<S>(this);
    }
    
    public abstract int compare(@Nullable final T p0, @Nullable final T p1);
    
    @Beta
    public <E extends T> List<E> leastOf(final Iterable<E> iterable, final int k) {
        Preconditions.checkArgument(k >= 0, "%d is negative", k);
        final E[] values = (E[])Iterables.toArray(iterable);
        E[] resultArray;
        if (values.length <= k) {
            Arrays.sort(values, this);
            resultArray = values;
        }
        else {
            this.quicksortLeastK(values, 0, values.length - 1, k);
            final E[] tmp = resultArray = (E[])new Object[k];
            System.arraycopy(values, 0, resultArray, 0, k);
        }
        return Collections.unmodifiableList((List<? extends E>)Arrays.asList((T[])resultArray));
    }
    
    @Beta
    public <E extends T> List<E> greatestOf(final Iterable<E> iterable, final int k) {
        return (List<E>)this.reverse().leastOf((Iterable<Object>)iterable, k);
    }
    
    private <E extends T> void quicksortLeastK(final E[] values, final int left, final int right, final int k) {
        if (right > left) {
            final int pivotIndex = left + right >>> 1;
            final int pivotNewIndex = this.partition(values, left, right, pivotIndex);
            this.quicksortLeastK((Object[])values, left, pivotNewIndex - 1, k);
            if (pivotNewIndex < k) {
                this.quicksortLeastK((Object[])values, pivotNewIndex + 1, right, k);
            }
        }
    }
    
    private <E extends T> int partition(final E[] values, final int left, final int right, final int pivotIndex) {
        final E pivotValue = values[pivotIndex];
        values[pivotIndex] = values[right];
        values[right] = pivotValue;
        int storeIndex = left;
        for (int i = left; i < right; ++i) {
            if (this.compare(values[i], pivotValue) < 0) {
                ObjectArrays.swap(values, storeIndex, i);
                ++storeIndex;
            }
        }
        ObjectArrays.swap(values, right, storeIndex);
        return storeIndex;
    }
    
    public int binarySearch(final List<? extends T> sortedList, @Nullable final T key) {
        return Collections.binarySearch(sortedList, key, this);
    }
    
    public <E extends T> List<E> sortedCopy(final Iterable<E> iterable) {
        final List<E> list = (List<E>)Lists.newArrayList((Iterable<?>)iterable);
        Collections.sort(list, this);
        return list;
    }
    
    public <E extends T> ImmutableList<E> immutableSortedCopy(final Iterable<E> iterable) {
        return ImmutableList.copyOf((Collection<? extends E>)this.sortedCopy(iterable));
    }
    
    public boolean isOrdered(final Iterable<? extends T> iterable) {
        final Iterator<? extends T> it = iterable.iterator();
        if (it.hasNext()) {
            T prev = (T)it.next();
            while (it.hasNext()) {
                final T next = (T)it.next();
                if (this.compare(prev, next) > 0) {
                    return false;
                }
                prev = next;
            }
        }
        return true;
    }
    
    public boolean isStrictlyOrdered(final Iterable<? extends T> iterable) {
        final Iterator<? extends T> it = iterable.iterator();
        if (it.hasNext()) {
            T prev = (T)it.next();
            while (it.hasNext()) {
                final T next = (T)it.next();
                if (this.compare(prev, next) >= 0) {
                    return false;
                }
                prev = next;
            }
        }
        return true;
    }
    
    public <E extends T> E max(final Iterable<E> iterable) {
        final Iterator<E> iterator = iterable.iterator();
        E maxSoFar = iterator.next();
        while (iterator.hasNext()) {
            maxSoFar = this.max(maxSoFar, iterator.next());
        }
        return maxSoFar;
    }
    
    public <E extends T> E max(@Nullable final E a, @Nullable final E b, @Nullable final E c, final E... rest) {
        E maxSoFar = this.max(this.max(a, b), c);
        for (final E r : rest) {
            maxSoFar = this.max(maxSoFar, r);
        }
        return maxSoFar;
    }
    
    public <E extends T> E max(@Nullable final E a, @Nullable final E b) {
        return (this.compare(a, b) >= 0) ? a : b;
    }
    
    public <E extends T> E min(final Iterable<E> iterable) {
        final Iterator<E> iterator = iterable.iterator();
        E minSoFar = iterator.next();
        while (iterator.hasNext()) {
            minSoFar = this.min(minSoFar, iterator.next());
        }
        return minSoFar;
    }
    
    public <E extends T> E min(@Nullable final E a, @Nullable final E b, @Nullable final E c, final E... rest) {
        E minSoFar = this.min(this.min(a, b), c);
        for (final E r : rest) {
            minSoFar = this.min(minSoFar, r);
        }
        return minSoFar;
    }
    
    public <E extends T> E min(@Nullable final E a, @Nullable final E b) {
        return (this.compare(a, b) <= 0) ? a : b;
    }
    
    @VisibleForTesting
    static class IncomparableValueException extends ClassCastException
    {
        final Object value;
        private static final long serialVersionUID = 0L;
        
        IncomparableValueException(final Object value) {
            super("Cannot compare value: " + value);
            this.value = value;
        }
    }
    
    private static class ArbitraryOrderingHolder
    {
        static final Ordering<Object> ARBITRARY_ORDERING;
        
        static {
            ARBITRARY_ORDERING = new ArbitraryOrdering();
        }
    }
    
    @VisibleForTesting
    static class ArbitraryOrdering extends Ordering<Object>
    {
        private Map<Object, Integer> uids;
        
        ArbitraryOrdering() {
            this.uids = (Map<Object, Integer>)Platform.tryWeakKeys(new MapMaker()).makeComputingMap((Function<? super Object, ?>)new Function<Object, Integer>() {
                final AtomicInteger counter = new AtomicInteger(0);
                
                public Integer apply(final Object from) {
                    return this.counter.getAndIncrement();
                }
            });
        }
        
        public int compare(final Object left, final Object right) {
            if (left == right) {
                return 0;
            }
            final int leftCode = this.identityHashCode(left);
            final int rightCode = this.identityHashCode(right);
            if (leftCode != rightCode) {
                return (leftCode < rightCode) ? -1 : 1;
            }
            final int result = this.uids.get(left).compareTo(this.uids.get(right));
            if (result == 0) {
                throw new AssertionError();
            }
            return result;
        }
        
        public String toString() {
            return "Ordering.arbitrary()";
        }
        
        int identityHashCode(final Object object) {
            return System.identityHashCode(object);
        }
    }
}
