// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.NoSuchElementException;
import java.util.AbstractSet;
import com.google.common.base.Objects;
import com.google.common.base.Function;
import java.util.Collections;
import java.io.Serializable;
import com.google.common.primitives.Ints;
import java.util.Collection;
import com.google.common.annotations.Beta;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public final class Multisets
{
    public static <E> Multiset<E> unmodifiableMultiset(final Multiset<? extends E> multiset) {
        if (multiset instanceof UnmodifiableMultiset || multiset instanceof ImmutableMultiset) {
            return (Multiset<E>)multiset;
        }
        return new UnmodifiableMultiset<E>(Preconditions.checkNotNull(multiset));
    }
    
    @Deprecated
    public static <E> Multiset<E> unmodifiableMultiset(final ImmutableMultiset<E> multiset) {
        return Preconditions.checkNotNull(multiset);
    }
    
    public static <E> Multiset.Entry<E> immutableEntry(@Nullable final E e, final int n) {
        return new ImmutableEntry<E>(e, n);
    }
    
    static <E> Multiset<E> forSet(final Set<E> set) {
        return new SetMultiset<E>(set);
    }
    
    static int inferDistinctElements(final Iterable<?> elements) {
        if (elements instanceof Multiset) {
            return ((Multiset)elements).elementSet().size();
        }
        return 11;
    }
    
    public static <E> Multiset<E> intersection(final Multiset<E> multiset1, final Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset1);
        Preconditions.checkNotNull(multiset2);
        return new AbstractMultiset<E>() {
            public int count(final Object element) {
                final int count1 = multiset1.count(element);
                return (count1 == 0) ? 0 : Math.min(count1, multiset2.count(element));
            }
            
            Set<E> createElementSet() {
                return (Set<E>)Sets.intersection(multiset1.elementSet(), multiset2.elementSet());
            }
            
            Iterator<Multiset.Entry<E>> entryIterator() {
                final Iterator<Multiset.Entry<E>> iterator1 = multiset1.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() {
                    protected Multiset.Entry<E> computeNext() {
                        while (iterator1.hasNext()) {
                            final Multiset.Entry<E> entry1 = iterator1.next();
                            final E element = entry1.getElement();
                            final int count = Math.min(entry1.getCount(), multiset2.count(element));
                            if (count > 0) {
                                return Multisets.immutableEntry(element, count);
                            }
                        }
                        return this.endOfData();
                    }
                };
            }
            
            int distinctElements() {
                return this.elementSet().size();
            }
        };
    }
    
    @Beta
    public static boolean containsOccurrences(final Multiset<?> superMultiset, final Multiset<?> subMultiset) {
        Preconditions.checkNotNull(superMultiset);
        Preconditions.checkNotNull(subMultiset);
        for (final Multiset.Entry<?> entry : subMultiset.entrySet()) {
            final int superCount = superMultiset.count(entry.getElement());
            if (superCount < entry.getCount()) {
                return false;
            }
        }
        return true;
    }
    
    @Beta
    public static boolean retainOccurrences(final Multiset<?> multisetToModify, final Multiset<?> multisetToRetain) {
        return retainOccurrencesImpl(multisetToModify, multisetToRetain);
    }
    
    private static <E> boolean retainOccurrencesImpl(final Multiset<E> multisetToModify, final Multiset<?> occurrencesToRetain) {
        Preconditions.checkNotNull(multisetToModify);
        Preconditions.checkNotNull(occurrencesToRetain);
        final Iterator<Multiset.Entry<E>> entryIterator = multisetToModify.entrySet().iterator();
        boolean changed = false;
        while (entryIterator.hasNext()) {
            final Multiset.Entry<E> entry = entryIterator.next();
            final int retainCount = occurrencesToRetain.count(entry.getElement());
            if (retainCount == 0) {
                entryIterator.remove();
                changed = true;
            }
            else {
                if (retainCount >= entry.getCount()) {
                    continue;
                }
                multisetToModify.setCount(entry.getElement(), retainCount);
                changed = true;
            }
        }
        return changed;
    }
    
    @Beta
    public static boolean removeOccurrences(final Multiset<?> multisetToModify, final Multiset<?> occurrencesToRemove) {
        return removeOccurrencesImpl(multisetToModify, occurrencesToRemove);
    }
    
    private static <E> boolean removeOccurrencesImpl(final Multiset<E> multisetToModify, final Multiset<?> occurrencesToRemove) {
        Preconditions.checkNotNull(multisetToModify);
        Preconditions.checkNotNull(occurrencesToRemove);
        boolean changed = false;
        final Iterator<Multiset.Entry<E>> entryIterator = multisetToModify.entrySet().iterator();
        while (entryIterator.hasNext()) {
            final Multiset.Entry<E> entry = entryIterator.next();
            final int removeCount = occurrencesToRemove.count(entry.getElement());
            if (removeCount >= entry.getCount()) {
                entryIterator.remove();
                changed = true;
            }
            else {
                if (removeCount <= 0) {
                    continue;
                }
                multisetToModify.remove(entry.getElement(), removeCount);
                changed = true;
            }
        }
        return changed;
    }
    
    static boolean equalsImpl(final Multiset<?> multiset, @Nullable final Object object) {
        if (object == multiset) {
            return true;
        }
        if (!(object instanceof Multiset)) {
            return false;
        }
        final Multiset<?> that = (Multiset<?>)object;
        if (multiset.size() != that.size() || multiset.entrySet().size() != that.entrySet().size()) {
            return false;
        }
        for (final Multiset.Entry<?> entry : that.entrySet()) {
            if (multiset.count(entry.getElement()) != entry.getCount()) {
                return false;
            }
        }
        return true;
    }
    
    static <E> boolean addAllImpl(final Multiset<E> self, final Collection<? extends E> elements) {
        if (elements.isEmpty()) {
            return false;
        }
        if (elements instanceof Multiset) {
            final Multiset<? extends E> that = cast(elements);
            for (final Multiset.Entry<? extends E> entry : that.entrySet()) {
                self.add((E)entry.getElement(), entry.getCount());
            }
        }
        else {
            Iterators.addAll(self, elements.iterator());
        }
        return true;
    }
    
    static boolean removeAllImpl(final Multiset<?> self, final Collection<?> elementsToRemove) {
        final Collection<?> collection = (elementsToRemove instanceof Multiset) ? ((Multiset)elementsToRemove).elementSet() : elementsToRemove;
        return self.elementSet().removeAll(collection);
    }
    
    static boolean retainAllImpl(final Multiset<?> self, final Collection<?> elementsToRetain) {
        final Collection<?> collection = (elementsToRetain instanceof Multiset) ? ((Multiset)elementsToRetain).elementSet() : elementsToRetain;
        return self.elementSet().retainAll(collection);
    }
    
    static <E> int setCountImpl(final Multiset<E> self, final E element, final int count) {
        checkNonnegative(count, "count");
        final int oldCount = self.count(element);
        final int delta = count - oldCount;
        if (delta > 0) {
            self.add(element, delta);
        }
        else if (delta < 0) {
            self.remove(element, -delta);
        }
        return oldCount;
    }
    
    static <E> boolean setCountImpl(final Multiset<E> self, final E element, final int oldCount, final int newCount) {
        checkNonnegative(oldCount, "oldCount");
        checkNonnegative(newCount, "newCount");
        if (self.count(element) == oldCount) {
            self.setCount(element, newCount);
            return true;
        }
        return false;
    }
    
    static <E> Iterator<E> iteratorImpl(final Multiset<E> multiset) {
        return new MultisetIteratorImpl<E>(multiset, multiset.entrySet().iterator());
    }
    
    static int sizeImpl(final Multiset<?> multiset) {
        long size = 0L;
        for (final Multiset.Entry<?> entry : multiset.entrySet()) {
            size += entry.getCount();
        }
        return Ints.saturatedCast(size);
    }
    
    static void checkNonnegative(final int count, final String name) {
        Preconditions.checkArgument(count >= 0, "%s cannot be negative: %s", name, count);
    }
    
    static <T> Multiset<T> cast(final Iterable<T> iterable) {
        return (Multiset<T>)(Multiset)iterable;
    }
    
    static class UnmodifiableMultiset<E> extends ForwardingMultiset<E> implements Serializable
    {
        final Multiset<? extends E> delegate;
        transient Set<E> elementSet;
        transient Set<Multiset.Entry<E>> entrySet;
        private static final long serialVersionUID = 0L;
        
        UnmodifiableMultiset(final Multiset<? extends E> delegate) {
            this.delegate = delegate;
        }
        
        protected Multiset<E> delegate() {
            return (Multiset<E>)this.delegate;
        }
        
        Set<E> createElementSet() {
            return Collections.unmodifiableSet(this.delegate.elementSet());
        }
        
        public Set<E> elementSet() {
            final Set<E> es = this.elementSet;
            return (es == null) ? (this.elementSet = this.createElementSet()) : es;
        }
        
        public Set<Multiset.Entry<E>> entrySet() {
            final Set<Multiset.Entry<E>> es = this.entrySet;
            return (es == null) ? (this.entrySet = Collections.unmodifiableSet((Set<? extends Multiset.Entry<E>>)this.delegate.entrySet())) : es;
        }
        
        public Iterator<E> iterator() {
            return (Iterator<E>)Iterators.unmodifiableIterator(this.delegate.iterator());
        }
        
        public boolean add(final E element) {
            throw new UnsupportedOperationException();
        }
        
        public int add(final E element, final int occurences) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final Collection<? extends E> elementsToAdd) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final Object element) {
            throw new UnsupportedOperationException();
        }
        
        public int remove(final Object element, final int occurrences) {
            throw new UnsupportedOperationException();
        }
        
        public boolean removeAll(final Collection<?> elementsToRemove) {
            throw new UnsupportedOperationException();
        }
        
        public boolean retainAll(final Collection<?> elementsToRetain) {
            throw new UnsupportedOperationException();
        }
        
        public void clear() {
            throw new UnsupportedOperationException();
        }
        
        public int setCount(final E element, final int count) {
            throw new UnsupportedOperationException();
        }
        
        public boolean setCount(final E element, final int oldCount, final int newCount) {
            throw new UnsupportedOperationException();
        }
    }
    
    static final class ImmutableEntry<E> extends AbstractEntry<E> implements Serializable
    {
        @Nullable
        final E element;
        final int count;
        private static final long serialVersionUID = 0L;
        
        ImmutableEntry(@Nullable final E element, final int count) {
            this.element = element;
            this.count = count;
            Preconditions.checkArgument(count >= 0);
        }
        
        @Nullable
        public E getElement() {
            return this.element;
        }
        
        public int getCount() {
            return this.count;
        }
    }
    
    private static class SetMultiset<E> extends ForwardingCollection<E> implements Multiset<E>, Serializable
    {
        final Set<E> delegate;
        transient Set<E> elementSet;
        transient Set<Entry<E>> entrySet;
        private static final long serialVersionUID = 0L;
        
        SetMultiset(final Set<E> set) {
            this.delegate = Preconditions.checkNotNull(set);
        }
        
        protected Set<E> delegate() {
            return this.delegate;
        }
        
        public int count(final Object element) {
            return this.delegate.contains(element) ? 1 : 0;
        }
        
        public int add(final E element, final int occurrences) {
            throw new UnsupportedOperationException();
        }
        
        public int remove(final Object element, final int occurrences) {
            if (occurrences == 0) {
                return this.count(element);
            }
            Preconditions.checkArgument(occurrences > 0);
            return this.delegate.remove(element) ? 1 : 0;
        }
        
        public Set<E> elementSet() {
            final Set<E> es = this.elementSet;
            return (es == null) ? (this.elementSet = new ElementSet()) : es;
        }
        
        public Set<Entry<E>> entrySet() {
            Set<Entry<E>> es = this.entrySet;
            if (es == null) {
                final EntrySet<E> entrySet = new EntrySet<E>() {
                    Multiset<E> multiset() {
                        return (Multiset<E>)SetMultiset.this;
                    }
                    
                    public Iterator<Entry<E>> iterator() {
                        return Iterators.transform(SetMultiset.this.delegate.iterator(), (Function<? super E, ? extends Entry<E>>)new Function<E, Entry<E>>() {
                            public Entry<E> apply(final E elem) {
                                return Multisets.immutableEntry(elem, 1);
                            }
                        });
                    }
                    
                    public int size() {
                        return SetMultiset.this.delegate.size();
                    }
                };
                this.entrySet = (Set<Entry<E>>)entrySet;
                es = (Set<Entry<E>>)entrySet;
            }
            return es;
        }
        
        public boolean add(final E o) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final Collection<? extends E> c) {
            throw new UnsupportedOperationException();
        }
        
        public int setCount(final E element, final int count) {
            Multisets.checkNonnegative(count, "count");
            if (count == this.count(element)) {
                return count;
            }
            if (count == 0) {
                this.remove(element);
                return 1;
            }
            throw new UnsupportedOperationException();
        }
        
        public boolean setCount(final E element, final int oldCount, final int newCount) {
            return Multisets.setCountImpl(this, element, oldCount, newCount);
        }
        
        public boolean equals(@Nullable final Object object) {
            if (object instanceof Multiset) {
                final Multiset<?> that = (Multiset<?>)object;
                return this.size() == that.size() && this.delegate.equals(that.elementSet());
            }
            return false;
        }
        
        public int hashCode() {
            int sum = 0;
            for (final E e : this) {
                sum += (((e == null) ? 0 : e.hashCode()) ^ 0x1);
            }
            return sum;
        }
        
        class ElementSet extends ForwardingSet<E>
        {
            protected Set<E> delegate() {
                return SetMultiset.this.delegate;
            }
            
            public boolean add(final E o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean addAll(final Collection<? extends E> c) {
                throw new UnsupportedOperationException();
            }
        }
    }
    
    abstract static class AbstractEntry<E> implements Multiset.Entry<E>
    {
        public boolean equals(@Nullable final Object object) {
            if (object instanceof Multiset.Entry) {
                final Multiset.Entry<?> that = (Multiset.Entry<?>)object;
                return this.getCount() == that.getCount() && Objects.equal(this.getElement(), that.getElement());
            }
            return false;
        }
        
        public int hashCode() {
            final E e = this.getElement();
            return ((e == null) ? 0 : e.hashCode()) ^ this.getCount();
        }
        
        public String toString() {
            final String text = String.valueOf(this.getElement());
            final int n = this.getCount();
            return (n == 1) ? text : (text + " x " + n);
        }
    }
    
    abstract static class ElementSet<E> extends AbstractSet<E>
    {
        abstract Multiset<E> multiset();
        
        public void clear() {
            this.multiset().clear();
        }
        
        public boolean contains(final Object o) {
            return this.multiset().contains(o);
        }
        
        public boolean containsAll(final Collection<?> c) {
            return this.multiset().containsAll(c);
        }
        
        public boolean isEmpty() {
            return this.multiset().isEmpty();
        }
        
        public Iterator<E> iterator() {
            return Iterators.transform(this.multiset().entrySet().iterator(), (Function<? super Multiset.Entry<E>, ? extends E>)new Function<Multiset.Entry<E>, E>() {
                public E apply(final Multiset.Entry<E> entry) {
                    return entry.getElement();
                }
            });
        }
        
        public boolean remove(final Object o) {
            final int count = this.multiset().count(o);
            if (count > 0) {
                this.multiset().remove(o, count);
                return true;
            }
            return false;
        }
        
        public int size() {
            return this.multiset().entrySet().size();
        }
    }
    
    abstract static class EntrySet<E> extends AbstractSet<Multiset.Entry<E>>
    {
        abstract Multiset<E> multiset();
        
        public boolean contains(@Nullable final Object o) {
            if (!(o instanceof Multiset.Entry)) {
                return false;
            }
            final Multiset.Entry<?> entry = (Multiset.Entry<?>)o;
            if (entry.getCount() <= 0) {
                return false;
            }
            final int count = this.multiset().count(entry.getElement());
            return count == entry.getCount();
        }
        
        public boolean remove(final Object o) {
            return this.contains(o) && this.multiset().elementSet().remove(((Multiset.Entry)o).getElement());
        }
        
        public void clear() {
            this.multiset().clear();
        }
    }
    
    static final class MultisetIteratorImpl<E> implements Iterator<E>
    {
        private final Multiset<E> multiset;
        private final Iterator<Multiset.Entry<E>> entryIterator;
        private Multiset.Entry<E> currentEntry;
        private int laterCount;
        private int totalCount;
        private boolean canRemove;
        
        MultisetIteratorImpl(final Multiset<E> multiset, final Iterator<Multiset.Entry<E>> entryIterator) {
            this.multiset = multiset;
            this.entryIterator = entryIterator;
        }
        
        public boolean hasNext() {
            return this.laterCount > 0 || this.entryIterator.hasNext();
        }
        
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            if (this.laterCount == 0) {
                this.currentEntry = this.entryIterator.next();
                final int count = this.currentEntry.getCount();
                this.laterCount = count;
                this.totalCount = count;
            }
            --this.laterCount;
            this.canRemove = true;
            return this.currentEntry.getElement();
        }
        
        public void remove() {
            Preconditions.checkState(this.canRemove, (Object)"no calls to next() since the last call to remove()");
            if (this.totalCount == 1) {
                this.entryIterator.remove();
            }
            else {
                this.multiset.remove(this.currentEntry.getElement());
            }
            --this.totalCount;
            this.canRemove = false;
        }
    }
}
