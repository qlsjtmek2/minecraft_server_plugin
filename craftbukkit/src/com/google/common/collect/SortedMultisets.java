// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.NoSuchElementException;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
final class SortedMultisets
{
    private static <E> E getElementOrThrow(final Multiset.Entry<E> entry) {
        if (entry == null) {
            throw new NoSuchElementException();
        }
        return entry.getElement();
    }
    
    abstract static class ElementSet<E> extends Multisets.ElementSet<E> implements SortedSet<E>
    {
        abstract SortedMultiset<E> multiset();
        
        public Comparator<? super E> comparator() {
            return this.multiset().comparator();
        }
        
        public SortedSet<E> subSet(final E fromElement, final E toElement) {
            return this.multiset().subMultiset(fromElement, BoundType.CLOSED, toElement, BoundType.OPEN).elementSet();
        }
        
        public SortedSet<E> headSet(final E toElement) {
            return this.multiset().headMultiset(toElement, BoundType.OPEN).elementSet();
        }
        
        public SortedSet<E> tailSet(final E fromElement) {
            return this.multiset().tailMultiset(fromElement, BoundType.CLOSED).elementSet();
        }
        
        public E first() {
            return (E)getElementOrThrow((Multiset.Entry<Object>)this.multiset().firstEntry());
        }
        
        public E last() {
            return (E)getElementOrThrow((Multiset.Entry<Object>)this.multiset().lastEntry());
        }
    }
    
    abstract static class DescendingMultiset<E> extends ForwardingMultiset<E> implements SortedMultiset<E>
    {
        private transient Comparator<? super E> comparator;
        private transient SortedSet<E> elementSet;
        private transient Set<Multiset.Entry<E>> entrySet;
        
        abstract SortedMultiset<E> forwardMultiset();
        
        public Comparator<? super E> comparator() {
            final Comparator<? super E> result = this.comparator;
            if (result == null) {
                return this.comparator = Ordering.from(this.forwardMultiset().comparator()).reverse();
            }
            return result;
        }
        
        public SortedSet<E> elementSet() {
            final SortedSet<E> result = this.elementSet;
            if (result == null) {
                return this.elementSet = new ElementSet<E>() {
                    SortedMultiset<E> multiset() {
                        return (SortedMultiset<E>)DescendingMultiset.this;
                    }
                };
            }
            return result;
        }
        
        public Multiset.Entry<E> pollFirstEntry() {
            return this.forwardMultiset().pollLastEntry();
        }
        
        public Multiset.Entry<E> pollLastEntry() {
            return this.forwardMultiset().pollFirstEntry();
        }
        
        public SortedMultiset<E> headMultiset(final E toElement, final BoundType boundType) {
            return this.forwardMultiset().tailMultiset(toElement, boundType).descendingMultiset();
        }
        
        public SortedMultiset<E> subMultiset(final E fromElement, final BoundType fromBoundType, final E toElement, final BoundType toBoundType) {
            return this.forwardMultiset().subMultiset(toElement, toBoundType, fromElement, fromBoundType).descendingMultiset();
        }
        
        public SortedMultiset<E> tailMultiset(final E fromElement, final BoundType boundType) {
            return this.forwardMultiset().headMultiset(fromElement, boundType).descendingMultiset();
        }
        
        protected Multiset<E> delegate() {
            return this.forwardMultiset();
        }
        
        public SortedMultiset<E> descendingMultiset() {
            return this.forwardMultiset();
        }
        
        public Multiset.Entry<E> firstEntry() {
            return this.forwardMultiset().lastEntry();
        }
        
        public Multiset.Entry<E> lastEntry() {
            return this.forwardMultiset().firstEntry();
        }
        
        abstract Iterator<Multiset.Entry<E>> entryIterator();
        
        public Set<Multiset.Entry<E>> entrySet() {
            final Set<Multiset.Entry<E>> result = this.entrySet;
            return (result == null) ? (this.entrySet = this.createEntrySet()) : result;
        }
        
        Set<Multiset.Entry<E>> createEntrySet() {
            return (Set<Multiset.Entry<E>>)new Multisets.EntrySet<E>() {
                Multiset<E> multiset() {
                    return (Multiset<E>)DescendingMultiset.this;
                }
                
                public Iterator<Multiset.Entry<E>> iterator() {
                    return DescendingMultiset.this.entryIterator();
                }
                
                public int size() {
                    return DescendingMultiset.this.forwardMultiset().entrySet().size();
                }
            };
        }
        
        public Iterator<E> iterator() {
            return Multisets.iteratorImpl((Multiset<E>)this);
        }
        
        public Object[] toArray() {
            return this.standardToArray();
        }
        
        public <T> T[] toArray(final T[] array) {
            return this.standardToArray(array);
        }
        
        public String toString() {
            return this.entrySet().toString();
        }
    }
}
