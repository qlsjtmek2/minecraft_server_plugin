// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.AbstractCollection;
import com.google.common.base.Predicates;
import java.util.Iterator;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.util.Collection;
import com.google.common.base.Joiner;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public final class Collections2
{
    static final Joiner STANDARD_JOINER;
    
    public static <E> Collection<E> filter(final Collection<E> unfiltered, final Predicate<? super E> predicate) {
        if (unfiltered instanceof FilteredCollection) {
            return (Collection<E>)((FilteredCollection)unfiltered).createCombined(predicate);
        }
        return new FilteredCollection<E>(Preconditions.checkNotNull(unfiltered), Preconditions.checkNotNull(predicate));
    }
    
    static boolean safeContains(final Collection<?> collection, final Object object) {
        try {
            return collection.contains(object);
        }
        catch (ClassCastException e) {
            return false;
        }
    }
    
    public static <F, T> Collection<T> transform(final Collection<F> fromCollection, final Function<? super F, T> function) {
        return (Collection<T>)new TransformedCollection((Collection<Object>)fromCollection, (Function<? super Object, ?>)function);
    }
    
    static boolean containsAllImpl(final Collection<?> self, final Collection<?> c) {
        Preconditions.checkNotNull(self);
        for (final Object o : c) {
            if (!self.contains(o)) {
                return false;
            }
        }
        return true;
    }
    
    static String toStringImpl(final Collection<?> collection) {
        final StringBuilder sb = newStringBuilderForCollection(collection.size()).append('[');
        Collections2.STANDARD_JOINER.appendTo(sb, Iterables.transform((Iterable<?>)collection, (Function<?, ?>)new Function<Object, Object>() {
            public Object apply(final Object input) {
                return (input == collection) ? "(this Collection)" : input;
            }
        }));
        return sb.append(']').toString();
    }
    
    static StringBuilder newStringBuilderForCollection(final int size) {
        Preconditions.checkArgument(size >= 0, (Object)"size must be non-negative");
        return new StringBuilder((int)Math.min(size * 8L, 1073741824L));
    }
    
    static <T> Collection<T> cast(final Iterable<T> iterable) {
        return (Collection<T>)(Collection)iterable;
    }
    
    static {
        STANDARD_JOINER = Joiner.on(", ");
    }
    
    static class FilteredCollection<E> implements Collection<E>
    {
        final Collection<E> unfiltered;
        final Predicate<? super E> predicate;
        
        FilteredCollection(final Collection<E> unfiltered, final Predicate<? super E> predicate) {
            this.unfiltered = unfiltered;
            this.predicate = predicate;
        }
        
        FilteredCollection<E> createCombined(final Predicate<? super E> newPredicate) {
            return new FilteredCollection<E>(this.unfiltered, Predicates.and(this.predicate, newPredicate));
        }
        
        public boolean add(final E element) {
            Preconditions.checkArgument(this.predicate.apply((Object)element));
            return this.unfiltered.add(element);
        }
        
        public boolean addAll(final Collection<? extends E> collection) {
            for (final E element : collection) {
                Preconditions.checkArgument(this.predicate.apply((Object)element));
            }
            return this.unfiltered.addAll(collection);
        }
        
        public void clear() {
            Iterables.removeIf(this.unfiltered, this.predicate);
        }
        
        public boolean contains(final Object element) {
            try {
                return this.predicate.apply((Object)element) && this.unfiltered.contains(element);
            }
            catch (NullPointerException e) {
                return false;
            }
            catch (ClassCastException e2) {
                return false;
            }
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!this.contains(element)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean isEmpty() {
            return !Iterators.any(this.unfiltered.iterator(), this.predicate);
        }
        
        public Iterator<E> iterator() {
            return Iterators.filter(this.unfiltered.iterator(), this.predicate);
        }
        
        public boolean remove(final Object element) {
            try {
                return this.predicate.apply((Object)element) && this.unfiltered.remove(element);
            }
            catch (NullPointerException e) {
                return false;
            }
            catch (ClassCastException e2) {
                return false;
            }
        }
        
        public boolean removeAll(final Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            final Predicate<E> combinedPredicate = new Predicate<E>() {
                public boolean apply(final E input) {
                    return FilteredCollection.this.predicate.apply((Object)input) && collection.contains(input);
                }
            };
            return Iterables.removeIf(this.unfiltered, combinedPredicate);
        }
        
        public boolean retainAll(final Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            final Predicate<E> combinedPredicate = new Predicate<E>() {
                public boolean apply(final E input) {
                    return FilteredCollection.this.predicate.apply((Object)input) && !collection.contains(input);
                }
            };
            return Iterables.removeIf(this.unfiltered, combinedPredicate);
        }
        
        public int size() {
            return Iterators.size(this.iterator());
        }
        
        public Object[] toArray() {
            return Lists.newArrayList(this.iterator()).toArray();
        }
        
        public <T> T[] toArray(final T[] array) {
            return Lists.newArrayList(this.iterator()).toArray(array);
        }
        
        public String toString() {
            return Iterators.toString(this.iterator());
        }
    }
    
    static class TransformedCollection<F, T> extends AbstractCollection<T>
    {
        final Collection<F> fromCollection;
        final Function<? super F, ? extends T> function;
        
        TransformedCollection(final Collection<F> fromCollection, final Function<? super F, ? extends T> function) {
            this.fromCollection = Preconditions.checkNotNull(fromCollection);
            this.function = Preconditions.checkNotNull(function);
        }
        
        public void clear() {
            this.fromCollection.clear();
        }
        
        public boolean isEmpty() {
            return this.fromCollection.isEmpty();
        }
        
        public Iterator<T> iterator() {
            return Iterators.transform(this.fromCollection.iterator(), this.function);
        }
        
        public int size() {
            return this.fromCollection.size();
        }
    }
}
