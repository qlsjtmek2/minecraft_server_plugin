// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.Serializable;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import com.google.common.annotations.GwtCompatible;
import java.util.Set;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableSet<E> extends ImmutableCollection<E> implements Set<E>
{
    static final int MAX_TABLE_SIZE = 1073741824;
    static final int CUTOFF = 536870912;
    
    public static <E> ImmutableSet<E> of() {
        return (ImmutableSet<E>)EmptyImmutableSet.INSTANCE;
    }
    
    public static <E> ImmutableSet<E> of(final E element) {
        return new SingletonImmutableSet<E>(element);
    }
    
    public static <E> ImmutableSet<E> of(final E e1, final E e2) {
        return construct(e1, e2);
    }
    
    public static <E> ImmutableSet<E> of(final E e1, final E e2, final E e3) {
        return construct(e1, e2, e3);
    }
    
    public static <E> ImmutableSet<E> of(final E e1, final E e2, final E e3, final E e4) {
        return construct(e1, e2, e3, e4);
    }
    
    public static <E> ImmutableSet<E> of(final E e1, final E e2, final E e3, final E e4, final E e5) {
        return construct(e1, e2, e3, e4, e5);
    }
    
    public static <E> ImmutableSet<E> of(final E e1, final E e2, final E e3, final E e4, final E e5, final E e6, final E... others) {
        final int paramCount = 6;
        final Object[] elements = new Object[6 + others.length];
        elements[0] = e1;
        elements[1] = e2;
        elements[2] = e3;
        elements[3] = e4;
        elements[4] = e5;
        elements[5] = e6;
        for (int i = 6; i < elements.length; ++i) {
            elements[i] = others[i - 6];
        }
        return construct(elements);
    }
    
    private static <E> ImmutableSet<E> construct(final Object... elements) {
        final int tableSize = chooseTableSize(elements.length);
        final Object[] table = new Object[tableSize];
        final int mask = tableSize - 1;
        ArrayList<Object> uniqueElementsList = null;
        int hashCode = 0;
        for (int i = 0; i < elements.length; ++i) {
            final Object element = elements[i];
            final int hash = element.hashCode();
            int j = Hashing.smear(hash);
            while (true) {
                final int index = j & mask;
                final Object value = table[index];
                if (value == null) {
                    if (uniqueElementsList != null) {
                        uniqueElementsList.add(element);
                    }
                    table[index] = element;
                    hashCode += hash;
                    break;
                }
                if (value.equals(element)) {
                    if (uniqueElementsList == null) {
                        uniqueElementsList = new ArrayList<Object>(elements.length);
                        for (final Object previous : elements) {
                            uniqueElementsList.add(previous);
                        }
                        break;
                    }
                    break;
                }
                else {
                    ++j;
                }
            }
        }
        final Object[] uniqueElements = (uniqueElementsList == null) ? elements : uniqueElementsList.toArray();
        if (uniqueElements.length == 1) {
            final E element2 = (E)uniqueElements[0];
            return new SingletonImmutableSet<E>(element2, hashCode);
        }
        if (tableSize > 2 * chooseTableSize(uniqueElements.length)) {
            return (ImmutableSet<E>)construct(uniqueElements);
        }
        return new RegularImmutableSet<E>(uniqueElements, hashCode, table, mask);
    }
    
    static int chooseTableSize(final int setSize) {
        if (setSize < 536870912) {
            return Integer.highestOneBit(setSize) << 2;
        }
        Preconditions.checkArgument(setSize < 1073741824, (Object)"collection too large");
        return 1073741824;
    }
    
    @Deprecated
    public static <E> ImmutableSet<E> of(final E[] elements) {
        return (ImmutableSet<E>)copyOf((Object[])elements);
    }
    
    public static <E> ImmutableSet<E> copyOf(final E[] elements) {
        switch (elements.length) {
            case 0: {
                return of();
            }
            case 1: {
                return of(elements[0]);
            }
            default: {
                return (ImmutableSet<E>)construct((Object[])elements.clone());
            }
        }
    }
    
    public static <E> ImmutableSet<E> copyOf(final Iterable<? extends E> elements) {
        return (elements instanceof Collection) ? copyOf((Collection<? extends E>)Collections2.cast((Iterable<? extends E>)elements)) : copyOf(elements.iterator());
    }
    
    public static <E> ImmutableSet<E> copyOf(final Iterator<? extends E> elements) {
        return copyFromCollection((Collection<? extends E>)Lists.newArrayList((Iterator<?>)elements));
    }
    
    public static <E> ImmutableSet<E> copyOf(final Collection<? extends E> elements) {
        if (elements instanceof ImmutableSet && !(elements instanceof ImmutableSortedSet)) {
            final ImmutableSet<E> set = (ImmutableSet<E>)(ImmutableSet)elements;
            if (!set.isPartialView()) {
                return set;
            }
        }
        return (ImmutableSet<E>)copyFromCollection((Collection<?>)elements);
    }
    
    private static <E> ImmutableSet<E> copyFromCollection(final Collection<? extends E> collection) {
        final Object[] elements = collection.toArray();
        switch (elements.length) {
            case 0: {
                return of();
            }
            case 1: {
                final E onlyElement = (E)elements[0];
                return of(onlyElement);
            }
            default: {
                return construct(elements);
            }
        }
    }
    
    boolean isHashCodeFast() {
        return false;
    }
    
    public boolean equals(@Nullable final Object object) {
        return object == this || ((!(object instanceof ImmutableSet) || !this.isHashCodeFast() || !((ImmutableSet)object).isHashCodeFast() || this.hashCode() == object.hashCode()) && Sets.equalsImpl(this, object));
    }
    
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }
    
    public abstract UnmodifiableIterator<E> iterator();
    
    Object writeReplace() {
        return new SerializedForm(this.toArray());
    }
    
    public static <E> Builder<E> builder() {
        return new Builder<E>();
    }
    
    abstract static class ArrayImmutableSet<E> extends ImmutableSet<E>
    {
        final transient Object[] elements;
        
        ArrayImmutableSet(final Object[] elements) {
            this.elements = elements;
        }
        
        public int size() {
            return this.elements.length;
        }
        
        public boolean isEmpty() {
            return false;
        }
        
        public UnmodifiableIterator<E> iterator() {
            return Iterators.forArray((E[])this.elements);
        }
        
        public Object[] toArray() {
            final Object[] array = new Object[this.size()];
            System.arraycopy(this.elements, 0, array, 0, this.size());
            return array;
        }
        
        public <T> T[] toArray(T[] array) {
            final int size = this.size();
            if (array.length < size) {
                array = ObjectArrays.newArray(array, size);
            }
            else if (array.length > size) {
                array[size] = null;
            }
            System.arraycopy(this.elements, 0, array, 0, size);
            return array;
        }
        
        public boolean containsAll(final Collection<?> targets) {
            if (targets == this) {
                return true;
            }
            if (!(targets instanceof ArrayImmutableSet)) {
                return super.containsAll(targets);
            }
            if (targets.size() > this.size()) {
                return false;
            }
            for (final Object target : ((ArrayImmutableSet)targets).elements) {
                if (!this.contains(target)) {
                    return false;
                }
            }
            return true;
        }
        
        boolean isPartialView() {
            return false;
        }
        
        ImmutableList<E> createAsList() {
            return new ImmutableAsList<E>(this.elements, this);
        }
    }
    
    abstract static class TransformedImmutableSet<D, E> extends ImmutableSet<E>
    {
        final D[] source;
        final int hashCode;
        
        TransformedImmutableSet(final D[] source, final int hashCode) {
            this.source = source;
            this.hashCode = hashCode;
        }
        
        abstract E transform(final D p0);
        
        public int size() {
            return this.source.length;
        }
        
        public boolean isEmpty() {
            return false;
        }
        
        public UnmodifiableIterator<E> iterator() {
            return new AbstractIndexedListIterator<E>(this.source.length) {
                protected E get(final int index) {
                    return TransformedImmutableSet.this.transform(TransformedImmutableSet.this.source[index]);
                }
            };
        }
        
        public Object[] toArray() {
            return this.toArray(new Object[this.size()]);
        }
        
        public <T> T[] toArray(T[] array) {
            final int size = this.size();
            if (array.length < size) {
                array = ObjectArrays.newArray(array, size);
            }
            else if (array.length > size) {
                array[size] = null;
            }
            final Object[] objectArray = array;
            for (int i = 0; i < this.source.length; ++i) {
                objectArray[i] = this.transform(this.source[i]);
            }
            return array;
        }
        
        public final int hashCode() {
            return this.hashCode;
        }
        
        boolean isHashCodeFast() {
            return true;
        }
    }
    
    private static class SerializedForm implements Serializable
    {
        final Object[] elements;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final Object[] elements) {
            this.elements = elements;
        }
        
        Object readResolve() {
            return ImmutableSet.copyOf(this.elements);
        }
    }
    
    public static class Builder<E> extends ImmutableCollection.Builder<E>
    {
        final ArrayList<E> contents;
        
        public Builder() {
            this.contents = Lists.newArrayList();
        }
        
        public Builder<E> add(final E element) {
            this.contents.add(Preconditions.checkNotNull(element));
            return this;
        }
        
        public Builder<E> add(final E... elements) {
            this.contents.ensureCapacity(this.contents.size() + elements.length);
            super.add(elements);
            return this;
        }
        
        public Builder<E> addAll(final Iterable<? extends E> elements) {
            if (elements instanceof Collection) {
                final Collection<?> collection = (Collection<?>)(Collection)elements;
                this.contents.ensureCapacity(this.contents.size() + collection.size());
            }
            super.addAll(elements);
            return this;
        }
        
        public Builder<E> addAll(final Iterator<? extends E> elements) {
            super.addAll(elements);
            return this;
        }
        
        public ImmutableSet<E> build() {
            return ImmutableSet.copyOf((Collection<? extends E>)this.contents);
        }
    }
}
