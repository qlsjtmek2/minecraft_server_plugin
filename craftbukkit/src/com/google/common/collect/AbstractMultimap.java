// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.AbstractMap;
import java.util.ListIterator;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.AbstractCollection;
import java.util.SortedMap;
import java.util.RandomAccess;
import java.util.List;
import java.util.Collections;
import java.util.SortedSet;
import javax.annotation.Nullable;
import java.util.Iterator;
import com.google.common.base.Preconditions;
import java.util.Set;
import java.util.Collection;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;

@GwtCompatible
abstract class AbstractMultimap<K, V> implements Multimap<K, V>, Serializable
{
    private transient Map<K, Collection<V>> map;
    private transient int totalSize;
    private transient Set<K> keySet;
    private transient Multiset<K> multiset;
    private transient Collection<V> valuesCollection;
    private transient Collection<Map.Entry<K, V>> entries;
    private transient Map<K, Collection<V>> asMap;
    private static final long serialVersionUID = 2447537837011683357L;
    
    protected AbstractMultimap(final Map<K, Collection<V>> map) {
        Preconditions.checkArgument(map.isEmpty());
        this.map = map;
    }
    
    final void setMap(final Map<K, Collection<V>> map) {
        this.map = map;
        this.totalSize = 0;
        for (final Collection<V> values : map.values()) {
            Preconditions.checkArgument(!values.isEmpty());
            this.totalSize += values.size();
        }
    }
    
    abstract Collection<V> createCollection();
    
    Collection<V> createCollection(@Nullable final K key) {
        return this.createCollection();
    }
    
    Map<K, Collection<V>> backingMap() {
        return this.map;
    }
    
    public int size() {
        return this.totalSize;
    }
    
    public boolean isEmpty() {
        return this.totalSize == 0;
    }
    
    public boolean containsKey(@Nullable final Object key) {
        return this.map.containsKey(key);
    }
    
    public boolean containsValue(@Nullable final Object value) {
        for (final Collection<V> collection : this.map.values()) {
            if (collection.contains(value)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsEntry(@Nullable final Object key, @Nullable final Object value) {
        final Collection<V> collection = this.map.get(key);
        return collection != null && collection.contains(value);
    }
    
    public boolean put(@Nullable final K key, @Nullable final V value) {
        final Collection<V> collection = this.getOrCreateCollection(key);
        if (collection.add(value)) {
            ++this.totalSize;
            return true;
        }
        return false;
    }
    
    private Collection<V> getOrCreateCollection(@Nullable final K key) {
        Collection<V> collection = this.map.get(key);
        if (collection == null) {
            collection = this.createCollection(key);
            this.map.put(key, collection);
        }
        return collection;
    }
    
    public boolean remove(@Nullable final Object key, @Nullable final Object value) {
        final Collection<V> collection = this.map.get(key);
        if (collection == null) {
            return false;
        }
        final boolean changed = collection.remove(value);
        if (changed) {
            --this.totalSize;
            if (collection.isEmpty()) {
                this.map.remove(key);
            }
        }
        return changed;
    }
    
    public boolean putAll(@Nullable final K key, final Iterable<? extends V> values) {
        if (!values.iterator().hasNext()) {
            return false;
        }
        final Collection<V> collection = this.getOrCreateCollection(key);
        final int oldSize = collection.size();
        boolean changed = false;
        if (values instanceof Collection) {
            final Collection<? extends V> c = Collections2.cast(values);
            changed = collection.addAll(c);
        }
        else {
            for (final V value : values) {
                changed |= collection.add(value);
            }
        }
        this.totalSize += collection.size() - oldSize;
        return changed;
    }
    
    public boolean putAll(final Multimap<? extends K, ? extends V> multimap) {
        boolean changed = false;
        for (final Map.Entry<? extends K, ? extends V> entry : multimap.entries()) {
            changed |= this.put(entry.getKey(), entry.getValue());
        }
        return changed;
    }
    
    public Collection<V> replaceValues(@Nullable final K key, final Iterable<? extends V> values) {
        final Iterator<? extends V> iterator = values.iterator();
        if (!iterator.hasNext()) {
            return this.removeAll(key);
        }
        final Collection<V> collection = this.getOrCreateCollection(key);
        final Collection<V> oldValues = this.createCollection();
        oldValues.addAll((Collection<? extends V>)collection);
        this.totalSize -= collection.size();
        collection.clear();
        while (iterator.hasNext()) {
            if (collection.add((V)iterator.next())) {
                ++this.totalSize;
            }
        }
        return this.unmodifiableCollectionSubclass(oldValues);
    }
    
    public Collection<V> removeAll(@Nullable final Object key) {
        final Collection<V> collection = this.map.remove(key);
        final Collection<V> output = this.createCollection();
        if (collection != null) {
            output.addAll((Collection<? extends V>)collection);
            this.totalSize -= collection.size();
            collection.clear();
        }
        return this.unmodifiableCollectionSubclass(output);
    }
    
    private Collection<V> unmodifiableCollectionSubclass(final Collection<V> collection) {
        if (collection instanceof SortedSet) {
            return (Collection<V>)Collections.unmodifiableSortedSet((SortedSet<Object>)(SortedSet)collection);
        }
        if (collection instanceof Set) {
            return (Collection<V>)Collections.unmodifiableSet((Set<?>)(Set)collection);
        }
        if (collection instanceof List) {
            return (Collection<V>)Collections.unmodifiableList((List<?>)(List)collection);
        }
        return Collections.unmodifiableCollection((Collection<? extends V>)collection);
    }
    
    public void clear() {
        for (final Collection<V> collection : this.map.values()) {
            collection.clear();
        }
        this.map.clear();
        this.totalSize = 0;
    }
    
    public Collection<V> get(@Nullable final K key) {
        Collection<V> collection = this.map.get(key);
        if (collection == null) {
            collection = this.createCollection(key);
        }
        return this.wrapCollection(key, collection);
    }
    
    private Collection<V> wrapCollection(@Nullable final K key, final Collection<V> collection) {
        if (collection instanceof SortedSet) {
            return new WrappedSortedSet(key, (SortedSet)collection, null);
        }
        if (collection instanceof Set) {
            return new WrappedSet(key, (Set)collection);
        }
        if (collection instanceof List) {
            return this.wrapList(key, (List)collection, null);
        }
        return new WrappedCollection(key, collection, null);
    }
    
    private List<V> wrapList(@Nullable final K key, final List<V> list, @Nullable final WrappedCollection ancestor) {
        return (list instanceof RandomAccess) ? new RandomAccessWrappedList(key, list, ancestor) : new WrappedList(key, list, ancestor);
    }
    
    private Iterator<V> iteratorOrListIterator(final Collection<V> collection) {
        return (collection instanceof List) ? ((List)collection).listIterator() : collection.iterator();
    }
    
    public Set<K> keySet() {
        final Set<K> result = this.keySet;
        return (result == null) ? (this.keySet = this.createKeySet()) : result;
    }
    
    private Set<K> createKeySet() {
        return (Set<K>)((this.map instanceof SortedMap) ? new SortedKeySet((SortedMap)this.map) : new KeySet(this.map));
    }
    
    public Multiset<K> keys() {
        final Multiset<K> result = this.multiset;
        if (result == null) {
            return this.multiset = (Multiset<K>)new Multimaps.Keys<K, V>() {
                Multimap<K, V> multimap() {
                    return (Multimap<K, V>)AbstractMultimap.this;
                }
            };
        }
        return result;
    }
    
    private int removeValuesForKey(final Object key) {
        Collection<V> collection;
        try {
            collection = this.map.remove(key);
        }
        catch (NullPointerException e) {
            return 0;
        }
        catch (ClassCastException e2) {
            return 0;
        }
        int count = 0;
        if (collection != null) {
            count = collection.size();
            collection.clear();
            this.totalSize -= count;
        }
        return count;
    }
    
    public Collection<V> values() {
        final Collection<V> result = this.valuesCollection;
        if (result == null) {
            return this.valuesCollection = (Collection<V>)new Multimaps.Values<K, V>() {
                Multimap<K, V> multimap() {
                    return (Multimap<K, V>)AbstractMultimap.this;
                }
            };
        }
        return result;
    }
    
    public Collection<Map.Entry<K, V>> entries() {
        final Collection<Map.Entry<K, V>> result = this.entries;
        return (result == null) ? (this.entries = this.createEntries()) : result;
    }
    
    Collection<Map.Entry<K, V>> createEntries() {
        if (this instanceof SetMultimap) {
            return (Collection<Map.Entry<K, V>>)new Multimaps.EntrySet<K, V>() {
                Multimap<K, V> multimap() {
                    return (Multimap<K, V>)AbstractMultimap.this;
                }
                
                public Iterator<Map.Entry<K, V>> iterator() {
                    return AbstractMultimap.this.createEntryIterator();
                }
            };
        }
        return (Collection<Map.Entry<K, V>>)new Multimaps.Entries<K, V>() {
            Multimap<K, V> multimap() {
                return (Multimap<K, V>)AbstractMultimap.this;
            }
            
            public Iterator<Map.Entry<K, V>> iterator() {
                return AbstractMultimap.this.createEntryIterator();
            }
        };
    }
    
    Iterator<Map.Entry<K, V>> createEntryIterator() {
        return new EntryIterator();
    }
    
    public Map<K, Collection<V>> asMap() {
        final Map<K, Collection<V>> result = this.asMap;
        return (result == null) ? (this.asMap = this.createAsMap()) : result;
    }
    
    private Map<K, Collection<V>> createAsMap() {
        return (this.map instanceof SortedMap) ? new SortedAsMap((SortedMap)this.map) : new AsMap(this.map);
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof Multimap) {
            final Multimap<?, ?> that = (Multimap<?, ?>)object;
            return this.map.equals(that.asMap());
        }
        return false;
    }
    
    public int hashCode() {
        return this.map.hashCode();
    }
    
    public String toString() {
        return this.map.toString();
    }
    
    private class WrappedCollection extends AbstractCollection<V>
    {
        final K key;
        Collection<V> delegate;
        final WrappedCollection ancestor;
        final Collection<V> ancestorDelegate;
        final /* synthetic */ AbstractMultimap this$0;
        
        WrappedCollection(final K key, @Nullable final Collection<V> delegate, final WrappedCollection ancestor) {
            this.key = key;
            this.delegate = delegate;
            this.ancestor = ancestor;
            this.ancestorDelegate = ((ancestor == null) ? null : ancestor.getDelegate());
        }
        
        void refreshIfEmpty() {
            if (this.ancestor != null) {
                this.ancestor.refreshIfEmpty();
                if (this.ancestor.getDelegate() != this.ancestorDelegate) {
                    throw new ConcurrentModificationException();
                }
            }
            else if (this.delegate.isEmpty()) {
                final Collection<V> newDelegate = AbstractMultimap.this.map.get(this.key);
                if (newDelegate != null) {
                    this.delegate = newDelegate;
                }
            }
        }
        
        void removeIfEmpty() {
            if (this.ancestor != null) {
                this.ancestor.removeIfEmpty();
            }
            else if (this.delegate.isEmpty()) {
                AbstractMultimap.this.map.remove(this.key);
            }
        }
        
        K getKey() {
            return this.key;
        }
        
        void addToMap() {
            if (this.ancestor != null) {
                this.ancestor.addToMap();
            }
            else {
                AbstractMultimap.this.map.put(this.key, this.delegate);
            }
        }
        
        public int size() {
            this.refreshIfEmpty();
            return this.delegate.size();
        }
        
        public boolean equals(@Nullable final Object object) {
            if (object == this) {
                return true;
            }
            this.refreshIfEmpty();
            return this.delegate.equals(object);
        }
        
        public int hashCode() {
            this.refreshIfEmpty();
            return this.delegate.hashCode();
        }
        
        public String toString() {
            this.refreshIfEmpty();
            return this.delegate.toString();
        }
        
        Collection<V> getDelegate() {
            return this.delegate;
        }
        
        public Iterator<V> iterator() {
            this.refreshIfEmpty();
            return new WrappedIterator();
        }
        
        public boolean add(final V value) {
            this.refreshIfEmpty();
            final boolean wasEmpty = this.delegate.isEmpty();
            final boolean changed = this.delegate.add(value);
            if (changed) {
                AbstractMultimap.this.totalSize++;
                if (wasEmpty) {
                    this.addToMap();
                }
            }
            return changed;
        }
        
        WrappedCollection getAncestor() {
            return this.ancestor;
        }
        
        public boolean addAll(final Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            final int oldSize = this.size();
            final boolean changed = this.delegate.addAll(collection);
            if (changed) {
                final int newSize = this.delegate.size();
                AbstractMultimap.this.totalSize += newSize - oldSize;
                if (oldSize == 0) {
                    this.addToMap();
                }
            }
            return changed;
        }
        
        public boolean contains(final Object o) {
            this.refreshIfEmpty();
            return this.delegate.contains(o);
        }
        
        public boolean containsAll(final Collection<?> c) {
            this.refreshIfEmpty();
            return this.delegate.containsAll(c);
        }
        
        public void clear() {
            final int oldSize = this.size();
            if (oldSize == 0) {
                return;
            }
            this.delegate.clear();
            AbstractMultimap.this.totalSize -= oldSize;
            this.removeIfEmpty();
        }
        
        public boolean remove(final Object o) {
            this.refreshIfEmpty();
            final boolean changed = this.delegate.remove(o);
            if (changed) {
                AbstractMultimap.this.totalSize--;
                this.removeIfEmpty();
            }
            return changed;
        }
        
        public boolean removeAll(final Collection<?> c) {
            if (c.isEmpty()) {
                return false;
            }
            final int oldSize = this.size();
            final boolean changed = this.delegate.removeAll(c);
            if (changed) {
                final int newSize = this.delegate.size();
                AbstractMultimap.this.totalSize += newSize - oldSize;
                this.removeIfEmpty();
            }
            return changed;
        }
        
        public boolean retainAll(final Collection<?> c) {
            Preconditions.checkNotNull(c);
            final int oldSize = this.size();
            final boolean changed = this.delegate.retainAll(c);
            if (changed) {
                final int newSize = this.delegate.size();
                AbstractMultimap.this.totalSize += newSize - oldSize;
                this.removeIfEmpty();
            }
            return changed;
        }
        
        class WrappedIterator implements Iterator<V>
        {
            final Iterator<V> delegateIterator;
            final Collection<V> originalDelegate;
            
            WrappedIterator() {
                this.originalDelegate = WrappedCollection.this.delegate;
                this.delegateIterator = (Iterator<V>)WrappedCollection.this.this$0.iteratorOrListIterator(WrappedCollection.this.delegate);
            }
            
            WrappedIterator(final Iterator<V> delegateIterator) {
                this.originalDelegate = WrappedCollection.this.delegate;
                this.delegateIterator = delegateIterator;
            }
            
            void validateIterator() {
                WrappedCollection.this.refreshIfEmpty();
                if (WrappedCollection.this.delegate != this.originalDelegate) {
                    throw new ConcurrentModificationException();
                }
            }
            
            public boolean hasNext() {
                this.validateIterator();
                return this.delegateIterator.hasNext();
            }
            
            public V next() {
                this.validateIterator();
                return this.delegateIterator.next();
            }
            
            public void remove() {
                this.delegateIterator.remove();
                AbstractMultimap.this.totalSize--;
                WrappedCollection.this.removeIfEmpty();
            }
            
            Iterator<V> getDelegateIterator() {
                this.validateIterator();
                return this.delegateIterator;
            }
        }
    }
    
    private class WrappedSet extends WrappedCollection implements Set<V>
    {
        WrappedSet(final K key, final Set<V> delegate) {
            super(key, delegate, null);
        }
    }
    
    private class WrappedSortedSet extends WrappedCollection implements SortedSet<V>
    {
        WrappedSortedSet(final K key, @Nullable final SortedSet<V> delegate, final WrappedCollection ancestor) {
            super(key, delegate, ancestor);
        }
        
        SortedSet<V> getSortedSetDelegate() {
            return (SortedSet<V>)(SortedSet)this.getDelegate();
        }
        
        public Comparator<? super V> comparator() {
            return this.getSortedSetDelegate().comparator();
        }
        
        public V first() {
            this.refreshIfEmpty();
            return this.getSortedSetDelegate().first();
        }
        
        public V last() {
            this.refreshIfEmpty();
            return this.getSortedSetDelegate().last();
        }
        
        public SortedSet<V> headSet(final V toElement) {
            this.refreshIfEmpty();
            return new WrappedSortedSet(this.getKey(), this.getSortedSetDelegate().headSet(toElement), (this.getAncestor() == null) ? this : this.getAncestor());
        }
        
        public SortedSet<V> subSet(final V fromElement, final V toElement) {
            this.refreshIfEmpty();
            return new WrappedSortedSet(this.getKey(), this.getSortedSetDelegate().subSet(fromElement, toElement), (this.getAncestor() == null) ? this : this.getAncestor());
        }
        
        public SortedSet<V> tailSet(final V fromElement) {
            this.refreshIfEmpty();
            return new WrappedSortedSet(this.getKey(), this.getSortedSetDelegate().tailSet(fromElement), (this.getAncestor() == null) ? this : this.getAncestor());
        }
    }
    
    private class WrappedList extends WrappedCollection implements List<V>
    {
        WrappedList(final K key, @Nullable final List<V> delegate, final WrappedCollection ancestor) {
            super(key, delegate, ancestor);
        }
        
        List<V> getListDelegate() {
            return (List<V>)(List)this.getDelegate();
        }
        
        public boolean addAll(final int index, final Collection<? extends V> c) {
            if (c.isEmpty()) {
                return false;
            }
            final int oldSize = this.size();
            final boolean changed = this.getListDelegate().addAll(index, c);
            if (changed) {
                final int newSize = this.getDelegate().size();
                AbstractMultimap.this.totalSize += newSize - oldSize;
                if (oldSize == 0) {
                    this.addToMap();
                }
            }
            return changed;
        }
        
        public V get(final int index) {
            this.refreshIfEmpty();
            return this.getListDelegate().get(index);
        }
        
        public V set(final int index, final V element) {
            this.refreshIfEmpty();
            return this.getListDelegate().set(index, element);
        }
        
        public void add(final int index, final V element) {
            this.refreshIfEmpty();
            final boolean wasEmpty = this.getDelegate().isEmpty();
            this.getListDelegate().add(index, element);
            AbstractMultimap.this.totalSize++;
            if (wasEmpty) {
                this.addToMap();
            }
        }
        
        public V remove(final int index) {
            this.refreshIfEmpty();
            final V value = this.getListDelegate().remove(index);
            AbstractMultimap.this.totalSize--;
            this.removeIfEmpty();
            return value;
        }
        
        public int indexOf(final Object o) {
            this.refreshIfEmpty();
            return this.getListDelegate().indexOf(o);
        }
        
        public int lastIndexOf(final Object o) {
            this.refreshIfEmpty();
            return this.getListDelegate().lastIndexOf(o);
        }
        
        public ListIterator<V> listIterator() {
            this.refreshIfEmpty();
            return new WrappedListIterator();
        }
        
        public ListIterator<V> listIterator(final int index) {
            this.refreshIfEmpty();
            return new WrappedListIterator(index);
        }
        
        public List<V> subList(final int fromIndex, final int toIndex) {
            this.refreshIfEmpty();
            return AbstractMultimap.this.wrapList(this.getKey(), this.getListDelegate().subList(fromIndex, toIndex), (this.getAncestor() == null) ? this : this.getAncestor());
        }
        
        private class WrappedListIterator extends WrappedIterator implements ListIterator<V>
        {
            WrappedListIterator() {
            }
            
            public WrappedListIterator(final int index) {
                super(WrappedList.this.getListDelegate().listIterator(index));
            }
            
            private ListIterator<V> getDelegateListIterator() {
                return (ListIterator<V>)(ListIterator)this.getDelegateIterator();
            }
            
            public boolean hasPrevious() {
                return this.getDelegateListIterator().hasPrevious();
            }
            
            public V previous() {
                return this.getDelegateListIterator().previous();
            }
            
            public int nextIndex() {
                return this.getDelegateListIterator().nextIndex();
            }
            
            public int previousIndex() {
                return this.getDelegateListIterator().previousIndex();
            }
            
            public void set(final V value) {
                this.getDelegateListIterator().set(value);
            }
            
            public void add(final V value) {
                final boolean wasEmpty = WrappedList.this.isEmpty();
                this.getDelegateListIterator().add(value);
                AbstractMultimap.this.totalSize++;
                if (wasEmpty) {
                    WrappedList.this.addToMap();
                }
            }
        }
    }
    
    private class RandomAccessWrappedList extends WrappedList implements RandomAccess
    {
        RandomAccessWrappedList(final K key, @Nullable final List<V> delegate, final WrappedCollection ancestor) {
            super(key, delegate, ancestor);
        }
    }
    
    private class KeySet extends Maps.KeySet<K, Collection<V>>
    {
        final Map<K, Collection<V>> subMap;
        
        KeySet(final Map<K, Collection<V>> subMap) {
            this.subMap = subMap;
        }
        
        Map<K, Collection<V>> map() {
            return this.subMap;
        }
        
        public Iterator<K> iterator() {
            return new Iterator<K>() {
                final Iterator<Map.Entry<K, Collection<V>>> entryIterator = KeySet.this.subMap.entrySet().iterator();
                Map.Entry<K, Collection<V>> entry;
                
                public boolean hasNext() {
                    return this.entryIterator.hasNext();
                }
                
                public K next() {
                    this.entry = this.entryIterator.next();
                    return this.entry.getKey();
                }
                
                public void remove() {
                    Preconditions.checkState(this.entry != null);
                    final Collection<V> collection = this.entry.getValue();
                    this.entryIterator.remove();
                    AbstractMultimap.this.totalSize -= collection.size();
                    collection.clear();
                }
            };
        }
        
        public boolean remove(final Object key) {
            int count = 0;
            final Collection<V> collection = this.subMap.remove(key);
            if (collection != null) {
                count = collection.size();
                collection.clear();
                AbstractMultimap.this.totalSize -= count;
            }
            return count > 0;
        }
        
        public void clear() {
            Iterators.clear(this.iterator());
        }
        
        public boolean containsAll(final Collection<?> c) {
            return this.subMap.keySet().containsAll(c);
        }
        
        public boolean equals(@Nullable final Object object) {
            return this == object || this.subMap.keySet().equals(object);
        }
        
        public int hashCode() {
            return this.subMap.keySet().hashCode();
        }
    }
    
    private class SortedKeySet extends KeySet implements SortedSet<K>
    {
        SortedKeySet(final SortedMap<K, Collection<V>> subMap) {
            super(subMap);
        }
        
        SortedMap<K, Collection<V>> sortedMap() {
            return (SortedMap<K, Collection<V>>)(SortedMap)this.subMap;
        }
        
        public Comparator<? super K> comparator() {
            return this.sortedMap().comparator();
        }
        
        public K first() {
            return this.sortedMap().firstKey();
        }
        
        public SortedSet<K> headSet(final K toElement) {
            return new SortedKeySet(this.sortedMap().headMap(toElement));
        }
        
        public K last() {
            return this.sortedMap().lastKey();
        }
        
        public SortedSet<K> subSet(final K fromElement, final K toElement) {
            return new SortedKeySet(this.sortedMap().subMap(fromElement, toElement));
        }
        
        public SortedSet<K> tailSet(final K fromElement) {
            return new SortedKeySet(this.sortedMap().tailMap(fromElement));
        }
    }
    
    private class EntryIterator implements Iterator<Map.Entry<K, V>>
    {
        final Iterator<Map.Entry<K, Collection<V>>> keyIterator;
        K key;
        Collection<V> collection;
        Iterator<V> valueIterator;
        
        EntryIterator() {
            this.keyIterator = AbstractMultimap.this.map.entrySet().iterator();
            if (this.keyIterator.hasNext()) {
                this.findValueIteratorAndKey();
            }
            else {
                this.valueIterator = Iterators.emptyModifiableIterator();
            }
        }
        
        void findValueIteratorAndKey() {
            final Map.Entry<K, Collection<V>> entry = this.keyIterator.next();
            this.key = entry.getKey();
            this.collection = entry.getValue();
            this.valueIterator = this.collection.iterator();
        }
        
        public boolean hasNext() {
            return this.keyIterator.hasNext() || this.valueIterator.hasNext();
        }
        
        public Map.Entry<K, V> next() {
            if (!this.valueIterator.hasNext()) {
                this.findValueIteratorAndKey();
            }
            return Maps.immutableEntry(this.key, this.valueIterator.next());
        }
        
        public void remove() {
            this.valueIterator.remove();
            if (this.collection.isEmpty()) {
                this.keyIterator.remove();
            }
            AbstractMultimap.this.totalSize--;
        }
    }
    
    private class AsMap extends AbstractMap<K, Collection<V>>
    {
        final transient Map<K, Collection<V>> submap;
        transient Set<Map.Entry<K, Collection<V>>> entrySet;
        
        AsMap(final Map<K, Collection<V>> submap) {
            this.submap = submap;
        }
        
        public Set<Map.Entry<K, Collection<V>>> entrySet() {
            final Set<Map.Entry<K, Collection<V>>> result = this.entrySet;
            return (result == null) ? (this.entrySet = (Set<Map.Entry<K, Collection<V>>>)new AsMapEntries()) : result;
        }
        
        public boolean containsKey(final Object key) {
            return Maps.safeContainsKey(this.submap, key);
        }
        
        public Collection<V> get(final Object key) {
            final Collection<V> collection = Maps.safeGet(this.submap, key);
            if (collection == null) {
                return null;
            }
            return AbstractMultimap.this.wrapCollection(key, collection);
        }
        
        public Set<K> keySet() {
            return AbstractMultimap.this.keySet();
        }
        
        public int size() {
            return this.submap.size();
        }
        
        public Collection<V> remove(final Object key) {
            final Collection<V> collection = this.submap.remove(key);
            if (collection == null) {
                return null;
            }
            final Collection<V> output = AbstractMultimap.this.createCollection();
            output.addAll((Collection<? extends V>)collection);
            AbstractMultimap.this.totalSize -= collection.size();
            collection.clear();
            return output;
        }
        
        public boolean equals(@Nullable final Object object) {
            return this == object || this.submap.equals(object);
        }
        
        public int hashCode() {
            return this.submap.hashCode();
        }
        
        public String toString() {
            return this.submap.toString();
        }
        
        public void clear() {
            if (this.submap == AbstractMultimap.this.map) {
                AbstractMultimap.this.clear();
            }
            else {
                Iterators.clear(new AsMapIterator());
            }
        }
        
        class AsMapEntries extends Maps.EntrySet<K, Collection<V>>
        {
            Map<K, Collection<V>> map() {
                return AsMap.this;
            }
            
            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return new AsMapIterator();
            }
            
            public boolean contains(final Object o) {
                return Collections2.safeContains(AsMap.this.submap.entrySet(), o);
            }
            
            public boolean remove(final Object o) {
                if (!this.contains(o)) {
                    return false;
                }
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                AbstractMultimap.this.removeValuesForKey(entry.getKey());
                return true;
            }
        }
        
        class AsMapIterator implements Iterator<Map.Entry<K, Collection<V>>>
        {
            final Iterator<Map.Entry<K, Collection<V>>> delegateIterator;
            Collection<V> collection;
            
            AsMapIterator() {
                this.delegateIterator = AsMap.this.submap.entrySet().iterator();
            }
            
            public boolean hasNext() {
                return this.delegateIterator.hasNext();
            }
            
            public Map.Entry<K, Collection<V>> next() {
                final Map.Entry<K, Collection<V>> entry = this.delegateIterator.next();
                final K key = entry.getKey();
                this.collection = entry.getValue();
                return Maps.immutableEntry(key, AbstractMultimap.this.wrapCollection(key, this.collection));
            }
            
            public void remove() {
                this.delegateIterator.remove();
                AbstractMultimap.this.totalSize -= this.collection.size();
                this.collection.clear();
            }
        }
    }
    
    private class SortedAsMap extends AsMap implements SortedMap<K, Collection<V>>
    {
        SortedSet<K> sortedKeySet;
        
        SortedAsMap(final SortedMap<K, Collection<V>> submap) {
            super(submap);
        }
        
        SortedMap<K, Collection<V>> sortedMap() {
            return (SortedMap<K, Collection<V>>)(SortedMap)this.submap;
        }
        
        public Comparator<? super K> comparator() {
            return this.sortedMap().comparator();
        }
        
        public K firstKey() {
            return this.sortedMap().firstKey();
        }
        
        public K lastKey() {
            return this.sortedMap().lastKey();
        }
        
        public SortedMap<K, Collection<V>> headMap(final K toKey) {
            return new SortedAsMap(this.sortedMap().headMap(toKey));
        }
        
        public SortedMap<K, Collection<V>> subMap(final K fromKey, final K toKey) {
            return new SortedAsMap(this.sortedMap().subMap(fromKey, toKey));
        }
        
        public SortedMap<K, Collection<V>> tailMap(final K fromKey) {
            return new SortedAsMap(this.sortedMap().tailMap(fromKey));
        }
        
        public SortedSet<K> keySet() {
            final SortedSet<K> result = this.sortedKeySet;
            return (result == null) ? (this.sortedKeySet = new SortedKeySet(this.sortedMap())) : result;
        }
    }
}
