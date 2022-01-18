// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.LinkedHashMap;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Set;
import javax.annotation.Nullable;
import java.util.Iterator;
import com.google.common.primitives.Ints;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true)
public abstract class ImmutableMultiset<E> extends ImmutableCollection<E> implements Multiset<E>
{
    private transient ImmutableSet<Entry<E>> entrySet;
    
    public static <E> ImmutableMultiset<E> of() {
        return (ImmutableMultiset<E>)EmptyImmutableMultiset.INSTANCE;
    }
    
    public static <E> ImmutableMultiset<E> of(final E element) {
        return copyOfInternal(element);
    }
    
    public static <E> ImmutableMultiset<E> of(final E e1, final E e2) {
        return copyOfInternal(e1, e2);
    }
    
    public static <E> ImmutableMultiset<E> of(final E e1, final E e2, final E e3) {
        return copyOfInternal(e1, e2, e3);
    }
    
    public static <E> ImmutableMultiset<E> of(final E e1, final E e2, final E e3, final E e4) {
        return copyOfInternal(e1, e2, e3, e4);
    }
    
    public static <E> ImmutableMultiset<E> of(final E e1, final E e2, final E e3, final E e4, final E e5) {
        return copyOfInternal(e1, e2, e3, e4, e5);
    }
    
    public static <E> ImmutableMultiset<E> of(final E e1, final E e2, final E e3, final E e4, final E e5, final E e6, final E... others) {
        final int size = others.length + 6;
        final List<E> all = new ArrayList<E>(size);
        Collections.addAll((Collection<? super Object>)all, e1, e2, e3, e4, e5, e6);
        Collections.addAll(all, others);
        return copyOf((Iterable<? extends E>)all);
    }
    
    @Deprecated
    public static <E> ImmutableMultiset<E> of(final E[] elements) {
        return copyOf((Iterable<? extends E>)Arrays.asList(elements));
    }
    
    public static <E> ImmutableMultiset<E> copyOf(final E[] elements) {
        return copyOf((Iterable<? extends E>)Arrays.asList(elements));
    }
    
    public static <E> ImmutableMultiset<E> copyOf(final Iterable<? extends E> elements) {
        if (elements instanceof ImmutableMultiset) {
            final ImmutableMultiset<E> result = (ImmutableMultiset<E>)(ImmutableMultiset)elements;
            if (!result.isPartialView()) {
                return result;
            }
        }
        final Multiset<? extends E> multiset = (Multiset<? extends E>)((elements instanceof Multiset) ? Multisets.cast(elements) : LinkedHashMultiset.create((Iterable<?>)elements));
        return copyOfInternal(multiset);
    }
    
    private static <E> ImmutableMultiset<E> copyOfInternal(final E... elements) {
        return copyOf((Iterable<? extends E>)Arrays.asList(elements));
    }
    
    private static <E> ImmutableMultiset<E> copyOfInternal(final Multiset<? extends E> multiset) {
        long size = 0L;
        final ImmutableMap.Builder<E, Integer> builder = ImmutableMap.builder();
        for (final Entry<? extends E> entry : multiset.entrySet()) {
            final int count = entry.getCount();
            if (count > 0) {
                builder.put((E)entry.getElement(), count);
                size += count;
            }
        }
        if (size == 0L) {
            return of();
        }
        return new RegularImmutableMultiset<E>(builder.build(), Ints.saturatedCast(size));
    }
    
    public static <E> ImmutableMultiset<E> copyOf(final Iterator<? extends E> elements) {
        final Multiset<E> multiset = (Multiset<E>)LinkedHashMultiset.create();
        Iterators.addAll(multiset, elements);
        return copyOfInternal((Multiset<? extends E>)multiset);
    }
    
    public UnmodifiableIterator<E> iterator() {
        final Iterator<Entry<E>> entryIterator = this.entryIterator();
        return new UnmodifiableIterator<E>() {
            int remaining;
            E element;
            
            public boolean hasNext() {
                return this.remaining > 0 || entryIterator.hasNext();
            }
            
            public E next() {
                if (this.remaining <= 0) {
                    final Entry<E> entry = entryIterator.next();
                    this.element = entry.getElement();
                    this.remaining = entry.getCount();
                }
                --this.remaining;
                return this.element;
            }
        };
    }
    
    public boolean contains(@Nullable final Object object) {
        return this.count(object) > 0;
    }
    
    public boolean containsAll(final Collection<?> targets) {
        return this.elementSet().containsAll(targets);
    }
    
    public final int add(final E element, final int occurrences) {
        throw new UnsupportedOperationException();
    }
    
    public final int remove(final Object element, final int occurrences) {
        throw new UnsupportedOperationException();
    }
    
    public final int setCount(final E element, final int count) {
        throw new UnsupportedOperationException();
    }
    
    public final boolean setCount(final E element, final int oldCount, final int newCount) {
        throw new UnsupportedOperationException();
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Multiset)) {
            return false;
        }
        final Multiset<?> that = (Multiset<?>)object;
        if (this.size() != that.size()) {
            return false;
        }
        for (final Entry<?> entry : that.entrySet()) {
            if (this.count(entry.getElement()) != entry.getCount()) {
                return false;
            }
        }
        return true;
    }
    
    public int hashCode() {
        return Sets.hashCodeImpl(this.entrySet());
    }
    
    public String toString() {
        return this.entrySet().toString();
    }
    
    public Set<Entry<E>> entrySet() {
        final ImmutableSet<Entry<E>> es = this.entrySet;
        return (es == null) ? (this.entrySet = this.createEntrySet()) : es;
    }
    
    abstract UnmodifiableIterator<Entry<E>> entryIterator();
    
    abstract int distinctElements();
    
    ImmutableSet<Entry<E>> createEntrySet() {
        return (ImmutableSet<Entry<E>>)new EntrySet((ImmutableMultiset<Object>)this);
    }
    
    Object writeReplace() {
        return new SerializedForm(this);
    }
    
    public static <E> Builder<E> builder() {
        return new Builder<E>();
    }
    
    static class EntrySet<E> extends ImmutableSet<Entry<E>>
    {
        final transient ImmutableMultiset<E> multiset;
        private static final long serialVersionUID = 0L;
        
        public EntrySet(final ImmutableMultiset<E> multiset) {
            this.multiset = multiset;
        }
        
        public UnmodifiableIterator<Entry<E>> iterator() {
            return this.multiset.entryIterator();
        }
        
        public int size() {
            return this.multiset.distinctElements();
        }
        
        boolean isPartialView() {
            return this.multiset.isPartialView();
        }
        
        public boolean contains(final Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            final Entry<?> entry = (Entry<?>)o;
            if (entry.getCount() <= 0) {
                return false;
            }
            final int count = this.multiset.count(entry.getElement());
            return count == entry.getCount();
        }
        
        public Object[] toArray() {
            final Object[] newArray = new Object[this.size()];
            return this.toArray(newArray);
        }
        
        public <T> T[] toArray(T[] other) {
            final int size = this.size();
            if (other.length < size) {
                other = ObjectArrays.newArray(other, size);
            }
            else if (other.length > size) {
                other[size] = null;
            }
            final Object[] otherAsObjectArray = other;
            int index = 0;
            for (final Entry<?> element : this) {
                otherAsObjectArray[index++] = element;
            }
            return other;
        }
        
        public int hashCode() {
            return this.multiset.hashCode();
        }
        
        Object writeReplace() {
            return new EntrySetSerializedForm((ImmutableMultiset<Object>)this.multiset);
        }
        
        static class EntrySetSerializedForm<E> implements Serializable
        {
            final ImmutableMultiset<E> multiset;
            
            EntrySetSerializedForm(final ImmutableMultiset<E> multiset) {
                this.multiset = multiset;
            }
            
            Object readResolve() {
                return this.multiset.entrySet();
            }
        }
    }
    
    private static class SerializedForm implements Serializable
    {
        final Object[] elements;
        final int[] counts;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final Multiset<?> multiset) {
            final int distinct = multiset.entrySet().size();
            this.elements = new Object[distinct];
            this.counts = new int[distinct];
            int i = 0;
            for (final Entry<?> entry : multiset.entrySet()) {
                this.elements[i] = entry.getElement();
                this.counts[i] = entry.getCount();
                ++i;
            }
        }
        
        Object readResolve() {
            final LinkedHashMultiset<Object> multiset = LinkedHashMultiset.create(this.elements.length);
            for (int i = 0; i < this.elements.length; ++i) {
                multiset.add(this.elements[i], this.counts[i]);
            }
            return ImmutableMultiset.copyOf((Iterable<?>)multiset);
        }
    }
    
    public static class Builder<E> extends ImmutableCollection.Builder<E>
    {
        final Multiset<E> contents;
        
        public Builder() {
            this(LinkedHashMultiset.create());
        }
        
        Builder(final Multiset<E> contents) {
            this.contents = contents;
        }
        
        public Builder<E> add(final E element) {
            this.contents.add(Preconditions.checkNotNull(element));
            return this;
        }
        
        public Builder<E> addCopies(final E element, final int occurrences) {
            this.contents.add(Preconditions.checkNotNull(element), occurrences);
            return this;
        }
        
        public Builder<E> setCount(final E element, final int count) {
            this.contents.setCount(Preconditions.checkNotNull(element), count);
            return this;
        }
        
        public Builder<E> add(final E... elements) {
            super.add(elements);
            return this;
        }
        
        public Builder<E> addAll(final Iterable<? extends E> elements) {
            if (elements instanceof Multiset) {
                final Multiset<? extends E> multiset = Multisets.cast(elements);
                for (final Entry<? extends E> entry : multiset.entrySet()) {
                    this.addCopies(entry.getElement(), entry.getCount());
                }
            }
            else {
                super.addAll(elements);
            }
            return this;
        }
        
        public Builder<E> addAll(final Iterator<? extends E> elements) {
            super.addAll(elements);
            return this;
        }
        
        public ImmutableMultiset<E> build() {
            return ImmutableMultiset.copyOf((Iterable<? extends E>)this.contents);
        }
    }
}
