// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.SortedMap;
import java.util.Set;
import java.util.SortedSet;
import java.util.Map;
import java.util.TreeMap;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtIncompatible;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
public final class TreeMultiset<E> extends AbstractMapBasedMultiset<E> implements SortedIterable<E>
{
    private final Comparator<? super E> comparator;
    @GwtIncompatible("not needed in emulated source")
    private static final long serialVersionUID = 0L;
    
    public static <E extends Comparable> TreeMultiset<E> create() {
        return new TreeMultiset<E>();
    }
    
    public static <E> TreeMultiset<E> create(@Nullable final Comparator<? super E> comparator) {
        return (comparator == null) ? new TreeMultiset<E>() : new TreeMultiset<E>(comparator);
    }
    
    public Iterator<E> iterator() {
        return super.iterator();
    }
    
    public static <E extends Comparable> TreeMultiset<E> create(final Iterable<? extends E> elements) {
        final TreeMultiset<E> multiset = create();
        Iterables.addAll(multiset, elements);
        return multiset;
    }
    
    private TreeMultiset() {
        this((Comparator)Ordering.natural());
    }
    
    private TreeMultiset(@Nullable final Comparator<? super E> comparator) {
        super(new TreeMap(Preconditions.checkNotNull(comparator)));
        this.comparator = comparator;
    }
    
    public Comparator<? super E> comparator() {
        return this.comparator;
    }
    
    public SortedSet<E> elementSet() {
        return (SortedSet<E>)(SortedSet)super.elementSet();
    }
    
    public int count(@Nullable final Object element) {
        try {
            return super.count(element);
        }
        catch (NullPointerException e) {
            return 0;
        }
        catch (ClassCastException e2) {
            return 0;
        }
    }
    
    public int add(final E element, final int occurrences) {
        if (element == null) {
            this.comparator.compare((Object)element, (Object)element);
        }
        return super.add(element, occurrences);
    }
    
    Set<E> createElementSet() {
        return (Set<E>)new SortedMapBasedElementSet((SortedMap)this.backingMap());
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(this.elementSet().comparator());
        Serialization.writeMultiset((Multiset<Object>)this, stream);
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        final Comparator<? super E> comparator = (Comparator<? super E>)stream.readObject();
        this.setBackingMap(new TreeMap<E, Count>(comparator));
        Serialization.populateMultiset((Multiset<Object>)this, stream);
    }
    
    private class SortedMapBasedElementSet extends MapBasedElementSet implements SortedSet<E>, SortedIterable<E>
    {
        SortedMapBasedElementSet(final SortedMap<E, Count> map) {
            super((AbstractMapBasedMultiset<E>)TreeMultiset.this, (Map<E, Count>)map);
        }
        
        SortedMap<E, Count> sortedMap() {
            return (SortedMap<E, Count>)(SortedMap)this.getMap();
        }
        
        public Comparator<? super E> comparator() {
            return this.sortedMap().comparator();
        }
        
        public E first() {
            return this.sortedMap().firstKey();
        }
        
        public E last() {
            return this.sortedMap().lastKey();
        }
        
        public SortedSet<E> headSet(final E toElement) {
            return new SortedMapBasedElementSet(this.sortedMap().headMap(toElement));
        }
        
        public SortedSet<E> subSet(final E fromElement, final E toElement) {
            return new SortedMapBasedElementSet(this.sortedMap().subMap(fromElement, toElement));
        }
        
        public SortedSet<E> tailSet(final E fromElement) {
            return new SortedMapBasedElementSet(this.sortedMap().tailMap(fromElement));
        }
        
        public boolean remove(final Object element) {
            try {
                return super.remove(element);
            }
            catch (NullPointerException e) {
                return false;
            }
            catch (ClassCastException e2) {
                return false;
            }
        }
    }
}
