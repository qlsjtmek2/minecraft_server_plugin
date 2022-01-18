// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Function;
import java.util.NoSuchElementException;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.AbstractSet;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Map;
import java.util.Set;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.LinkedHashSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Collection;
import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.EnumSet;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
public final class Sets
{
    @GwtCompatible(serializable = true)
    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(final E anElement, final E... otherElements) {
        return new ImmutableEnumSet<E>(EnumSet.of(anElement, otherElements));
    }
    
    @GwtCompatible(serializable = true)
    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(final Iterable<E> elements) {
        final Iterator<E> iterator = elements.iterator();
        if (!iterator.hasNext()) {
            return ImmutableSet.of();
        }
        if (elements instanceof EnumSet) {
            final EnumSet<E> enumSetClone = EnumSet.copyOf((EnumSet<E>)(EnumSet)elements);
            return new ImmutableEnumSet<E>(enumSetClone);
        }
        final E first = iterator.next();
        final EnumSet<E> set = EnumSet.of(first);
        while (iterator.hasNext()) {
            set.add(iterator.next());
        }
        return new ImmutableEnumSet<E>(set);
    }
    
    public static <E extends Enum<E>> EnumSet<E> newEnumSet(final Iterable<E> iterable, final Class<E> elementType) {
        Preconditions.checkNotNull(iterable);
        final EnumSet<E> set = EnumSet.noneOf(elementType);
        Iterables.addAll(set, (Iterable<? extends E>)iterable);
        return set;
    }
    
    public static <E> HashSet<E> newHashSet() {
        return new HashSet<E>();
    }
    
    public static <E> HashSet<E> newHashSet(final E... elements) {
        final HashSet<E> set = newHashSetWithExpectedSize(elements.length);
        Collections.addAll(set, elements);
        return set;
    }
    
    public static <E> HashSet<E> newHashSetWithExpectedSize(final int expectedSize) {
        return new HashSet<E>(Maps.capacity(expectedSize));
    }
    
    public static <E> HashSet<E> newHashSet(final Iterable<? extends E> elements) {
        return (elements instanceof Collection) ? new HashSet<E>(Collections2.cast(elements)) : newHashSet(elements.iterator());
    }
    
    public static <E> HashSet<E> newHashSet(final Iterator<? extends E> elements) {
        final HashSet<E> set = newHashSet();
        while (elements.hasNext()) {
            set.add((E)elements.next());
        }
        return set;
    }
    
    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet<E>();
    }
    
    public static <E> LinkedHashSet<E> newLinkedHashSet(final Iterable<? extends E> elements) {
        if (elements instanceof Collection) {
            return new LinkedHashSet<E>((Collection<? extends E>)Collections2.cast((Iterable<? extends E>)elements));
        }
        final LinkedHashSet<E> set = newLinkedHashSet();
        for (final E element : elements) {
            set.add(element);
        }
        return set;
    }
    
    public static <E extends Comparable> TreeSet<E> newTreeSet() {
        return new TreeSet<E>();
    }
    
    public static <E extends Comparable> TreeSet<E> newTreeSet(final Iterable<? extends E> elements) {
        final TreeSet<E> set = newTreeSet();
        for (final E element : elements) {
            set.add(element);
        }
        return set;
    }
    
    public static <E> TreeSet<E> newTreeSet(final Comparator<? super E> comparator) {
        return new TreeSet<E>(Preconditions.checkNotNull(comparator));
    }
    
    public static <E> Set<E> newIdentityHashSet() {
        return newSetFromMap((Map<E, Boolean>)Maps.newIdentityHashMap());
    }
    
    public static <E extends Enum<E>> EnumSet<E> complementOf(final Collection<E> collection) {
        if (collection instanceof EnumSet) {
            return EnumSet.complementOf((EnumSet<E>)(EnumSet)collection);
        }
        Preconditions.checkArgument(!collection.isEmpty(), (Object)"collection is empty; use the other version of this method");
        final Class<E> type = collection.iterator().next().getDeclaringClass();
        return makeComplementByHand(collection, type);
    }
    
    public static <E extends Enum<E>> EnumSet<E> complementOf(final Collection<E> collection, final Class<E> type) {
        Preconditions.checkNotNull(collection);
        return (EnumSet<E>)((collection instanceof EnumSet) ? EnumSet.complementOf((EnumSet<E>)(EnumSet)collection) : makeComplementByHand((Collection<Enum>)collection, (Class<Enum>)type));
    }
    
    private static <E extends Enum<E>> EnumSet<E> makeComplementByHand(final Collection<E> collection, final Class<E> type) {
        final EnumSet<E> result = EnumSet.allOf(type);
        result.removeAll(collection);
        return result;
    }
    
    public static <E> Set<E> newSetFromMap(final Map<E, Boolean> map) {
        return new SetFromMap<E>(map);
    }
    
    public static <E> SetView<E> union(final Set<? extends E> set1, final Set<? extends E> set2) {
        Preconditions.checkNotNull(set1, (Object)"set1");
        Preconditions.checkNotNull(set2, (Object)"set2");
        final Set<? extends E> set2minus1 = difference(set2, set1);
        return new SetView<E>() {
            public int size() {
                return set1.size() + set2minus1.size();
            }
            
            public boolean isEmpty() {
                return set1.isEmpty() && set2.isEmpty();
            }
            
            public Iterator<E> iterator() {
                return (Iterator<E>)Iterators.unmodifiableIterator(Iterators.concat(set1.iterator(), set2minus1.iterator()));
            }
            
            public boolean contains(final Object object) {
                return set1.contains(object) || set2.contains(object);
            }
            
            public <S extends Set<E>> S copyInto(final S set) {
                set.addAll(set1);
                set.addAll(set2);
                return set;
            }
            
            public ImmutableSet<E> immutableCopy() {
                return new ImmutableSet.Builder<E>().addAll(set1).addAll(set2).build();
            }
        };
    }
    
    public static <E> SetView<E> intersection(final Set<E> set1, final Set<?> set2) {
        Preconditions.checkNotNull(set1, (Object)"set1");
        Preconditions.checkNotNull(set2, (Object)"set2");
        final Predicate<Object> inSet2 = Predicates.in((Collection<?>)set2);
        return new SetView<E>() {
            public Iterator<E> iterator() {
                return (Iterator<E>)Iterators.filter(set1.iterator(), inSet2);
            }
            
            public int size() {
                return Iterators.size(this.iterator());
            }
            
            public boolean isEmpty() {
                return !this.iterator().hasNext();
            }
            
            public boolean contains(final Object object) {
                return set1.contains(object) && set2.contains(object);
            }
            
            public boolean containsAll(final Collection<?> collection) {
                return set1.containsAll(collection) && set2.containsAll(collection);
            }
        };
    }
    
    public static <E> SetView<E> difference(final Set<E> set1, final Set<?> set2) {
        Preconditions.checkNotNull(set1, (Object)"set1");
        Preconditions.checkNotNull(set2, (Object)"set2");
        final Predicate<Object> notInSet2 = Predicates.not(Predicates.in((Collection<?>)set2));
        return new SetView<E>() {
            public Iterator<E> iterator() {
                return (Iterator<E>)Iterators.filter(set1.iterator(), notInSet2);
            }
            
            public int size() {
                return Iterators.size(this.iterator());
            }
            
            public boolean isEmpty() {
                return set2.containsAll(set1);
            }
            
            public boolean contains(final Object element) {
                return set1.contains(element) && !set2.contains(element);
            }
        };
    }
    
    public static <E> SetView<E> symmetricDifference(final Set<? extends E> set1, final Set<? extends E> set2) {
        Preconditions.checkNotNull(set1, (Object)"set1");
        Preconditions.checkNotNull(set2, (Object)"set2");
        return (SetView<E>)difference(union((Set<?>)set1, (Set<?>)set2), intersection(set1, set2));
    }
    
    public static <E> Set<E> filter(final Set<E> unfiltered, final Predicate<? super E> predicate) {
        if (unfiltered instanceof FilteredSet) {
            final FilteredSet<E> filtered = (FilteredSet<E>)(FilteredSet)unfiltered;
            final Predicate<E> combinedPredicate = Predicates.and(filtered.predicate, predicate);
            return new FilteredSet<E>((Set)filtered.unfiltered, combinedPredicate);
        }
        return new FilteredSet<E>(Preconditions.checkNotNull(unfiltered), Preconditions.checkNotNull(predicate));
    }
    
    public static <B> Set<List<B>> cartesianProduct(final List<? extends Set<? extends B>> sets) {
        for (final Set<? extends B> set : sets) {
            if (set.isEmpty()) {
                return (Set<List<B>>)ImmutableSet.of();
            }
        }
        final CartesianSet<B> cartesianSet = new CartesianSet<B>(sets);
        return (Set<List<B>>)cartesianSet;
    }
    
    public static <B> Set<List<B>> cartesianProduct(final Set<? extends B>... sets) {
        return cartesianProduct((List<? extends Set<? extends B>>)Arrays.asList((Set<? extends B>[])sets));
    }
    
    @GwtCompatible(serializable = false)
    public static <E> Set<Set<E>> powerSet(final Set<E> set) {
        final ImmutableSet<E> input = ImmutableSet.copyOf((Collection<? extends E>)set);
        Preconditions.checkArgument(input.size() <= 30, "Too many elements to create power set: %s > 30", input.size());
        return (Set<Set<E>>)new PowerSet((ImmutableSet<Object>)input);
    }
    
    static int hashCodeImpl(final Set<?> s) {
        int hashCode = 0;
        for (final Object o : s) {
            hashCode += ((o != null) ? o.hashCode() : 0);
        }
        return hashCode;
    }
    
    static boolean equalsImpl(final Set<?> s, @Nullable final Object object) {
        if (s == object) {
            return true;
        }
        if (object instanceof Set) {
            final Set<?> o = (Set<?>)object;
            try {
                return s.size() == o.size() && s.containsAll(o);
            }
            catch (NullPointerException ignored) {
                return false;
            }
            catch (ClassCastException ignored2) {
                return false;
            }
        }
        return false;
    }
    
    static <A, B> Set<B> transform(final Set<A> set, final InvertibleFunction<A, B> bijection) {
        return (Set<B>)new TransformedSet((Set<Object>)Preconditions.checkNotNull(set, (Object)"set"), (InvertibleFunction<Object, Object>)Preconditions.checkNotNull(bijection, (Object)"bijection"));
    }
    
    private static class SetFromMap<E> extends AbstractSet<E> implements Set<E>, Serializable
    {
        private final Map<E, Boolean> m;
        private transient Set<E> s;
        @GwtIncompatible("not needed in emulated source")
        private static final long serialVersionUID = 0L;
        
        SetFromMap(final Map<E, Boolean> map) {
            Preconditions.checkArgument(map.isEmpty(), (Object)"Map is non-empty");
            this.m = map;
            this.s = map.keySet();
        }
        
        public void clear() {
            this.m.clear();
        }
        
        public int size() {
            return this.m.size();
        }
        
        public boolean isEmpty() {
            return this.m.isEmpty();
        }
        
        public boolean contains(final Object o) {
            return this.m.containsKey(o);
        }
        
        public boolean remove(final Object o) {
            return this.m.remove(o) != null;
        }
        
        public boolean add(final E e) {
            return this.m.put(e, Boolean.TRUE) == null;
        }
        
        public Iterator<E> iterator() {
            return this.s.iterator();
        }
        
        public Object[] toArray() {
            return this.s.toArray();
        }
        
        public <T> T[] toArray(final T[] a) {
            return this.s.toArray(a);
        }
        
        public String toString() {
            return this.s.toString();
        }
        
        public int hashCode() {
            return this.s.hashCode();
        }
        
        public boolean equals(@Nullable final Object object) {
            return this == object || this.s.equals(object);
        }
        
        public boolean containsAll(final Collection<?> c) {
            return this.s.containsAll(c);
        }
        
        public boolean removeAll(final Collection<?> c) {
            return this.s.removeAll(c);
        }
        
        public boolean retainAll(final Collection<?> c) {
            return this.s.retainAll(c);
        }
        
        @GwtIncompatible("java.io.ObjectInputStream")
        private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.s = this.m.keySet();
        }
    }
    
    public abstract static class SetView<E> extends AbstractSet<E>
    {
        public ImmutableSet<E> immutableCopy() {
            return ImmutableSet.copyOf((Collection<? extends E>)this);
        }
        
        public <S extends Set<E>> S copyInto(final S set) {
            set.addAll((Collection<? extends E>)this);
            return set;
        }
    }
    
    private static class FilteredSet<E> extends Collections2.FilteredCollection<E> implements Set<E>
    {
        FilteredSet(final Set<E> unfiltered, final Predicate<? super E> predicate) {
            super(unfiltered, predicate);
        }
        
        public boolean equals(@Nullable final Object object) {
            return Sets.equalsImpl(this, object);
        }
        
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }
    
    private static class CartesianSet<B> extends AbstractSet<List<B>>
    {
        final ImmutableList<Axis> axes;
        final int size;
        
        CartesianSet(final List<? extends Set<? extends B>> sets) {
            long dividend = 1L;
            final ImmutableList.Builder<Axis> builder = ImmutableList.builder();
            for (final Set<? extends B> set : sets) {
                final Axis axis = new Axis(set, (int)dividend);
                builder.add(axis);
                dividend *= axis.size();
                Preconditions.checkArgument(dividend <= 2147483647L, (Object)"cartesian product is too big");
            }
            this.axes = builder.build();
            this.size = Ints.checkedCast(dividend);
        }
        
        public int size() {
            return this.size;
        }
        
        public UnmodifiableIterator<List<B>> iterator() {
            return new UnmodifiableIterator<List<B>>() {
                int index;
                
                public boolean hasNext() {
                    return this.index < CartesianSet.this.size;
                }
                
                public List<B> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final Object[] tuple = new Object[CartesianSet.this.axes.size()];
                    for (int i = 0; i < tuple.length; ++i) {
                        tuple[i] = CartesianSet.this.axes.get(i).getForIndex(this.index);
                    }
                    ++this.index;
                    final List<B> result = (List<B>)ImmutableList.copyOf(tuple);
                    return result;
                }
            };
        }
        
        public boolean contains(final Object element) {
            if (!(element instanceof List)) {
                return false;
            }
            final List<?> tuple = (List<?>)element;
            final int dimensions = this.axes.size();
            if (tuple.size() != dimensions) {
                return false;
            }
            for (int i = 0; i < dimensions; ++i) {
                if (!this.axes.get(i).contains(tuple.get(i))) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean equals(@Nullable final Object object) {
            if (object instanceof CartesianSet) {
                final CartesianSet<?> that = (CartesianSet<?>)object;
                return this.axes.equals(that.axes);
            }
            return super.equals(object);
        }
        
        public int hashCode() {
            int adjust = this.size - 1;
            for (int i = 0; i < this.axes.size(); ++i) {
                adjust *= 31;
            }
            return this.axes.hashCode() + adjust;
        }
        
        private class Axis
        {
            final ImmutableSet<? extends B> choices;
            final ImmutableList<? extends B> choicesList;
            final int dividend;
            
            Axis(final Set<? extends B> set, final int dividend) {
                this.choices = ImmutableSet.copyOf((Collection<? extends B>)set);
                this.choicesList = this.choices.asList();
                this.dividend = dividend;
            }
            
            int size() {
                return this.choices.size();
            }
            
            B getForIndex(final int index) {
                return (B)this.choicesList.get(index / this.dividend % this.size());
            }
            
            boolean contains(final Object target) {
                return this.choices.contains(target);
            }
            
            public boolean equals(final Object obj) {
                if (obj instanceof Axis) {
                    final Axis that = (Axis)obj;
                    return this.choices.equals(that.choices);
                }
                return false;
            }
            
            public int hashCode() {
                return CartesianSet.this.size / this.choices.size() * this.choices.hashCode();
            }
        }
    }
    
    private static final class PowerSet<E> extends AbstractSet<Set<E>>
    {
        final ImmutableSet<E> inputSet;
        final ImmutableList<E> inputList;
        final int powerSetSize;
        
        PowerSet(final ImmutableSet<E> input) {
            this.inputSet = input;
            this.inputList = input.asList();
            this.powerSetSize = 1 << input.size();
        }
        
        public int size() {
            return this.powerSetSize;
        }
        
        public boolean isEmpty() {
            return false;
        }
        
        public Iterator<Set<E>> iterator() {
            return new AbstractIndexedListIterator<Set<E>>(this.powerSetSize) {
                protected Set<E> get(final int setBits) {
                    return new AbstractSet<E>() {
                        public int size() {
                            return Integer.bitCount(setBits);
                        }
                        
                        public Iterator<E> iterator() {
                            return new BitFilteredSetIterator<E>(PowerSet.this.inputList, setBits);
                        }
                    };
                }
            };
        }
        
        public boolean contains(@Nullable final Object obj) {
            if (obj instanceof Set) {
                final Set<?> set = (Set<?>)obj;
                return this.inputSet.containsAll(set);
            }
            return false;
        }
        
        public boolean equals(@Nullable final Object obj) {
            if (obj instanceof PowerSet) {
                final PowerSet<?> that = (PowerSet<?>)obj;
                return this.inputSet.equals(that.inputSet);
            }
            return super.equals(obj);
        }
        
        public int hashCode() {
            return this.inputSet.hashCode() << this.inputSet.size() - 1;
        }
        
        public String toString() {
            return "powerSet(" + this.inputSet + ")";
        }
        
        private static final class BitFilteredSetIterator<E> extends UnmodifiableIterator<E>
        {
            final ImmutableList<E> input;
            int remainingSetBits;
            
            BitFilteredSetIterator(final ImmutableList<E> input, final int allSetBits) {
                this.input = input;
                this.remainingSetBits = allSetBits;
            }
            
            public boolean hasNext() {
                return this.remainingSetBits != 0;
            }
            
            public E next() {
                final int index = Integer.numberOfTrailingZeros(this.remainingSetBits);
                if (index == 32) {
                    throw new NoSuchElementException();
                }
                final int currentElementMask = 1 << index;
                this.remainingSetBits &= ~currentElementMask;
                return this.input.get(index);
            }
        }
    }
    
    abstract static class InvertibleFunction<A, B> implements Function<A, B>
    {
        abstract A invert(final B p0);
        
        public InvertibleFunction<B, A> inverse() {
            return new InvertibleFunction<B, A>() {
                public A apply(final B b) {
                    return InvertibleFunction.this.invert(b);
                }
                
                B invert(final A a) {
                    return InvertibleFunction.this.apply(a);
                }
                
                public InvertibleFunction<A, B> inverse() {
                    return InvertibleFunction.this;
                }
            };
        }
    }
    
    private static class TransformedSet<A, B> extends AbstractSet<B>
    {
        final Set<A> delegate;
        final InvertibleFunction<A, B> bijection;
        
        TransformedSet(final Set<A> delegate, final InvertibleFunction<A, B> bijection) {
            this.delegate = delegate;
            this.bijection = bijection;
        }
        
        public Iterator<B> iterator() {
            return Iterators.transform(this.delegate.iterator(), (Function<? super A, ? extends B>)this.bijection);
        }
        
        public int size() {
            return this.delegate.size();
        }
        
        public boolean contains(final Object o) {
            final A a = this.bijection.invert((B)o);
            return this.delegate.contains(a) && Objects.equal(this.bijection.apply(a), o);
        }
        
        public boolean add(final B b) {
            return this.delegate.add(this.bijection.invert(b));
        }
        
        public boolean remove(final Object o) {
            return this.contains(o) && this.delegate.remove(this.bijection.invert((B)o));
        }
        
        public void clear() {
            this.delegate.clear();
        }
    }
}
