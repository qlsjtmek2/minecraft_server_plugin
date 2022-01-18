// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.AbstractCollection;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.AbstractMap;
import com.google.common.base.Preconditions;
import java.util.AbstractSet;
import java.util.AbstractSequentialList;
import java.util.Collections;
import java.util.ListIterator;
import com.google.common.base.Objects;
import java.util.NoSuchElementException;
import java.util.Iterator;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtIncompatible;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;

@GwtCompatible(serializable = true, emulated = true)
public class LinkedListMultimap<K, V> implements ListMultimap<K, V>, Serializable
{
    private transient Node<K, V> head;
    private transient Node<K, V> tail;
    private transient Multiset<K> keyCount;
    private transient Map<K, Node<K, V>> keyToKeyHead;
    private transient Map<K, Node<K, V>> keyToKeyTail;
    private transient Set<K> keySet;
    private transient Multiset<K> keys;
    private transient List<V> valuesList;
    private transient List<Map.Entry<K, V>> entries;
    private transient Map<K, Collection<V>> map;
    @GwtIncompatible("java serialization not supported")
    private static final long serialVersionUID = 0L;
    
    public static <K, V> LinkedListMultimap<K, V> create() {
        return new LinkedListMultimap<K, V>();
    }
    
    public static <K, V> LinkedListMultimap<K, V> create(final int expectedKeys) {
        return new LinkedListMultimap<K, V>(expectedKeys);
    }
    
    public static <K, V> LinkedListMultimap<K, V> create(final Multimap<? extends K, ? extends V> multimap) {
        return new LinkedListMultimap<K, V>(multimap);
    }
    
    LinkedListMultimap() {
        this.keyCount = (Multiset<K>)LinkedHashMultiset.create();
        this.keyToKeyHead = (Map<K, Node<K, V>>)Maps.newHashMap();
        this.keyToKeyTail = (Map<K, Node<K, V>>)Maps.newHashMap();
    }
    
    private LinkedListMultimap(final int expectedKeys) {
        this.keyCount = (Multiset<K>)LinkedHashMultiset.create(expectedKeys);
        this.keyToKeyHead = (Map<K, Node<K, V>>)Maps.newHashMapWithExpectedSize(expectedKeys);
        this.keyToKeyTail = (Map<K, Node<K, V>>)Maps.newHashMapWithExpectedSize(expectedKeys);
    }
    
    private LinkedListMultimap(final Multimap<? extends K, ? extends V> multimap) {
        this(multimap.keySet().size());
        this.putAll(multimap);
    }
    
    private Node<K, V> addNode(@Nullable final K key, @Nullable final V value, @Nullable final Node<K, V> nextSibling) {
        final Node<K, V> node = new Node<K, V>(key, value);
        if (this.head == null) {
            final Node<K, V> node2 = node;
            this.tail = node2;
            this.head = node2;
            this.keyToKeyHead.put(key, node);
            this.keyToKeyTail.put(key, node);
        }
        else if (nextSibling == null) {
            this.tail.next = node;
            node.previous = this.tail;
            final Node<K, V> keyTail = this.keyToKeyTail.get(key);
            if (keyTail == null) {
                this.keyToKeyHead.put(key, node);
            }
            else {
                keyTail.nextSibling = node;
                node.previousSibling = keyTail;
            }
            this.keyToKeyTail.put(key, node);
            this.tail = node;
        }
        else {
            node.previous = nextSibling.previous;
            node.previousSibling = nextSibling.previousSibling;
            node.next = nextSibling;
            node.nextSibling = nextSibling;
            if (nextSibling.previousSibling == null) {
                this.keyToKeyHead.put(key, node);
            }
            else {
                nextSibling.previousSibling.nextSibling = node;
            }
            if (nextSibling.previous == null) {
                this.head = node;
            }
            else {
                nextSibling.previous.next = node;
            }
            nextSibling.previous = node;
            nextSibling.previousSibling = node;
        }
        this.keyCount.add(key);
        return node;
    }
    
    private void removeNode(final Node<K, V> node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        }
        else {
            this.head = node.next;
        }
        if (node.next != null) {
            node.next.previous = node.previous;
        }
        else {
            this.tail = node.previous;
        }
        if (node.previousSibling != null) {
            node.previousSibling.nextSibling = node.nextSibling;
        }
        else if (node.nextSibling != null) {
            this.keyToKeyHead.put(node.key, node.nextSibling);
        }
        else {
            this.keyToKeyHead.remove(node.key);
        }
        if (node.nextSibling != null) {
            node.nextSibling.previousSibling = node.previousSibling;
        }
        else if (node.previousSibling != null) {
            this.keyToKeyTail.put(node.key, node.previousSibling);
        }
        else {
            this.keyToKeyTail.remove(node.key);
        }
        this.keyCount.remove(node.key);
    }
    
    private void removeAllNodes(@Nullable final Object key) {
        final Iterator<V> i = new ValueForKeyIterator(key);
        while (i.hasNext()) {
            i.next();
            i.remove();
        }
    }
    
    private static void checkElement(@Nullable final Object node) {
        if (node == null) {
            throw new NoSuchElementException();
        }
    }
    
    public int size() {
        return this.keyCount.size();
    }
    
    public boolean isEmpty() {
        return this.head == null;
    }
    
    public boolean containsKey(@Nullable final Object key) {
        return this.keyToKeyHead.containsKey(key);
    }
    
    public boolean containsValue(@Nullable final Object value) {
        final Iterator<Node<K, V>> i = new NodeIterator();
        while (i.hasNext()) {
            if (Objects.equal(i.next().value, value)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsEntry(@Nullable final Object key, @Nullable final Object value) {
        final Iterator<V> i = new ValueForKeyIterator(key);
        while (i.hasNext()) {
            if (Objects.equal(i.next(), value)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean put(@Nullable final K key, @Nullable final V value) {
        this.addNode(key, value, null);
        return true;
    }
    
    public boolean remove(@Nullable final Object key, @Nullable final Object value) {
        final Iterator<V> values = new ValueForKeyIterator(key);
        while (values.hasNext()) {
            if (Objects.equal(values.next(), value)) {
                values.remove();
                return true;
            }
        }
        return false;
    }
    
    public boolean putAll(@Nullable final K key, final Iterable<? extends V> values) {
        boolean changed = false;
        for (final V value : values) {
            changed |= this.put(key, value);
        }
        return changed;
    }
    
    public boolean putAll(final Multimap<? extends K, ? extends V> multimap) {
        boolean changed = false;
        for (final Map.Entry<? extends K, ? extends V> entry : multimap.entries()) {
            changed |= this.put(entry.getKey(), entry.getValue());
        }
        return changed;
    }
    
    public List<V> replaceValues(@Nullable final K key, final Iterable<? extends V> values) {
        final List<V> oldValues = this.getCopy(key);
        final ListIterator<V> keyValues = new ValueForKeyIterator(key);
        final Iterator<? extends V> newValues = values.iterator();
        while (keyValues.hasNext() && newValues.hasNext()) {
            keyValues.next();
            keyValues.set((V)newValues.next());
        }
        while (keyValues.hasNext()) {
            keyValues.next();
            keyValues.remove();
        }
        while (newValues.hasNext()) {
            keyValues.add((V)newValues.next());
        }
        return oldValues;
    }
    
    private List<V> getCopy(@Nullable final Object key) {
        return Collections.unmodifiableList((List<? extends V>)Lists.newArrayList((Iterator<?>)new ValueForKeyIterator(key)));
    }
    
    public List<V> removeAll(@Nullable final Object key) {
        final List<V> oldValues = this.getCopy(key);
        this.removeAllNodes(key);
        return oldValues;
    }
    
    public void clear() {
        this.head = null;
        this.tail = null;
        this.keyCount.clear();
        this.keyToKeyHead.clear();
        this.keyToKeyTail.clear();
    }
    
    public List<V> get(@Nullable final K key) {
        return new AbstractSequentialList<V>() {
            public int size() {
                return LinkedListMultimap.this.keyCount.count(key);
            }
            
            public ListIterator<V> listIterator(final int index) {
                return new ValueForKeyIterator(key, index);
            }
            
            public boolean removeAll(final Collection<?> c) {
                return Iterators.removeAll(this.iterator(), c);
            }
            
            public boolean retainAll(final Collection<?> c) {
                return Iterators.retainAll(this.iterator(), c);
            }
        };
    }
    
    public Set<K> keySet() {
        Set<K> result = this.keySet;
        if (result == null) {
            result = (this.keySet = new AbstractSet<K>() {
                public int size() {
                    return LinkedListMultimap.this.keyCount.elementSet().size();
                }
                
                public Iterator<K> iterator() {
                    return new DistinctKeyIterator();
                }
                
                public boolean contains(final Object key) {
                    return LinkedListMultimap.this.keyCount.contains(key);
                }
                
                public boolean removeAll(final Collection<?> c) {
                    Preconditions.checkNotNull(c);
                    return super.removeAll(c);
                }
            });
        }
        return result;
    }
    
    public Multiset<K> keys() {
        Multiset<K> result = this.keys;
        if (result == null) {
            result = (this.keys = new MultisetView());
        }
        return result;
    }
    
    public List<V> values() {
        List<V> result = this.valuesList;
        if (result == null) {
            result = (this.valuesList = new AbstractSequentialList<V>() {
                public int size() {
                    return LinkedListMultimap.this.keyCount.size();
                }
                
                public ListIterator<V> listIterator(final int index) {
                    final NodeIterator nodes = new NodeIterator(index);
                    return new ListIterator<V>() {
                        public boolean hasNext() {
                            return nodes.hasNext();
                        }
                        
                        public V next() {
                            return nodes.next().value;
                        }
                        
                        public boolean hasPrevious() {
                            return nodes.hasPrevious();
                        }
                        
                        public V previous() {
                            return nodes.previous().value;
                        }
                        
                        public int nextIndex() {
                            return nodes.nextIndex();
                        }
                        
                        public int previousIndex() {
                            return nodes.previousIndex();
                        }
                        
                        public void remove() {
                            nodes.remove();
                        }
                        
                        public void set(final V e) {
                            nodes.setValue(e);
                        }
                        
                        public void add(final V e) {
                            throw new UnsupportedOperationException();
                        }
                    };
                }
            });
        }
        return result;
    }
    
    private static <K, V> Map.Entry<K, V> createEntry(final Node<K, V> node) {
        return new AbstractMapEntry<K, V>() {
            public K getKey() {
                return node.key;
            }
            
            public V getValue() {
                return node.value;
            }
            
            public V setValue(final V value) {
                final V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        };
    }
    
    public List<Map.Entry<K, V>> entries() {
        List<Map.Entry<K, V>> result = this.entries;
        if (result == null) {
            result = (this.entries = new AbstractSequentialList<Map.Entry<K, V>>() {
                public int size() {
                    return LinkedListMultimap.this.keyCount.size();
                }
                
                public ListIterator<Map.Entry<K, V>> listIterator(final int index) {
                    final ListIterator<Node<K, V>> nodes = new NodeIterator(index);
                    return new ListIterator<Map.Entry<K, V>>() {
                        public boolean hasNext() {
                            return nodes.hasNext();
                        }
                        
                        public Map.Entry<K, V> next() {
                            return (Map.Entry<K, V>)createEntry((Node<Object, Object>)nodes.next());
                        }
                        
                        public void remove() {
                            nodes.remove();
                        }
                        
                        public boolean hasPrevious() {
                            return nodes.hasPrevious();
                        }
                        
                        public Map.Entry<K, V> previous() {
                            return (Map.Entry<K, V>)createEntry((Node<Object, Object>)nodes.previous());
                        }
                        
                        public int nextIndex() {
                            return nodes.nextIndex();
                        }
                        
                        public int previousIndex() {
                            return nodes.previousIndex();
                        }
                        
                        public void set(final Map.Entry<K, V> e) {
                            throw new UnsupportedOperationException();
                        }
                        
                        public void add(final Map.Entry<K, V> e) {
                            throw new UnsupportedOperationException();
                        }
                    };
                }
            });
        }
        return result;
    }
    
    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<V>> result = this.map;
        if (result == null) {
            result = (this.map = new AbstractMap<K, Collection<V>>() {
                Set<Map.Entry<K, Collection<V>>> entrySet;
                
                public Set<Map.Entry<K, Collection<V>>> entrySet() {
                    Set<Map.Entry<K, Collection<V>>> result = this.entrySet;
                    if (result == null) {
                        result = (this.entrySet = new AsMapEntries());
                    }
                    return result;
                }
                
                public boolean containsKey(@Nullable final Object key) {
                    return LinkedListMultimap.this.containsKey(key);
                }
                
                public Collection<V> get(@Nullable final Object key) {
                    final Collection<V> collection = LinkedListMultimap.this.get(key);
                    return collection.isEmpty() ? null : collection;
                }
                
                public Collection<V> remove(@Nullable final Object key) {
                    final Collection<V> collection = LinkedListMultimap.this.removeAll(key);
                    return collection.isEmpty() ? null : collection;
                }
            });
        }
        return result;
    }
    
    public boolean equals(@Nullable final Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Multimap) {
            final Multimap<?, ?> that = (Multimap<?, ?>)other;
            return this.asMap().equals(that.asMap());
        }
        return false;
    }
    
    public int hashCode() {
        return this.asMap().hashCode();
    }
    
    public String toString() {
        return this.asMap().toString();
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(this.size());
        for (final Map.Entry<K, V> entry : this.entries()) {
            stream.writeObject(entry.getKey());
            stream.writeObject(entry.getValue());
        }
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.keyCount = (Multiset<K>)LinkedHashMultiset.create();
        this.keyToKeyHead = (Map<K, Node<K, V>>)Maps.newHashMap();
        this.keyToKeyTail = (Map<K, Node<K, V>>)Maps.newHashMap();
        for (int size = stream.readInt(), i = 0; i < size; ++i) {
            final K key = (K)stream.readObject();
            final V value = (V)stream.readObject();
            this.put(key, value);
        }
    }
    
    private static final class Node<K, V>
    {
        final K key;
        V value;
        Node<K, V> next;
        Node<K, V> previous;
        Node<K, V> nextSibling;
        Node<K, V> previousSibling;
        
        Node(@Nullable final K key, @Nullable final V value) {
            this.key = key;
            this.value = value;
        }
        
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
    
    private class NodeIterator implements ListIterator<Node<K, V>>
    {
        int nextIndex;
        Node<K, V> next;
        Node<K, V> current;
        Node<K, V> previous;
        
        NodeIterator() {
            this.next = LinkedListMultimap.this.head;
        }
        
        NodeIterator(int index) {
            final int size = LinkedListMultimap.this.size();
            Preconditions.checkPositionIndex(index, size);
            if (index >= size / 2) {
                this.previous = LinkedListMultimap.this.tail;
                this.nextIndex = size;
                while (index++ < size) {
                    this.previous();
                }
            }
            else {
                this.next = LinkedListMultimap.this.head;
                while (index-- > 0) {
                    this.next();
                }
            }
            this.current = null;
        }
        
        public boolean hasNext() {
            return this.next != null;
        }
        
        public Node<K, V> next() {
            checkElement(this.next);
            final Node<K, V> next = this.next;
            this.current = next;
            this.previous = next;
            this.next = this.next.next;
            ++this.nextIndex;
            return this.current;
        }
        
        public void remove() {
            Preconditions.checkState(this.current != null);
            if (this.current != this.next) {
                this.previous = this.current.previous;
                --this.nextIndex;
            }
            else {
                this.next = this.current.next;
            }
            LinkedListMultimap.this.removeNode(this.current);
            this.current = null;
        }
        
        public boolean hasPrevious() {
            return this.previous != null;
        }
        
        public Node<K, V> previous() {
            checkElement(this.previous);
            final Node<K, V> previous = this.previous;
            this.current = previous;
            this.next = previous;
            this.previous = this.previous.previous;
            --this.nextIndex;
            return this.current;
        }
        
        public int nextIndex() {
            return this.nextIndex;
        }
        
        public int previousIndex() {
            return this.nextIndex - 1;
        }
        
        public void set(final Node<K, V> e) {
            throw new UnsupportedOperationException();
        }
        
        public void add(final Node<K, V> e) {
            throw new UnsupportedOperationException();
        }
        
        void setValue(final V value) {
            Preconditions.checkState(this.current != null);
            this.current.value = value;
        }
    }
    
    private class DistinctKeyIterator implements Iterator<K>
    {
        final Set<K> seenKeys;
        Node<K, V> next;
        Node<K, V> current;
        
        private DistinctKeyIterator() {
            this.seenKeys = (Set<K>)Sets.newHashSetWithExpectedSize(LinkedListMultimap.this.keySet().size());
            this.next = LinkedListMultimap.this.head;
        }
        
        public boolean hasNext() {
            return this.next != null;
        }
        
        public K next() {
            checkElement(this.next);
            this.current = this.next;
            this.seenKeys.add(this.current.key);
            do {
                this.next = this.next.next;
            } while (this.next != null && !this.seenKeys.add(this.next.key));
            return this.current.key;
        }
        
        public void remove() {
            Preconditions.checkState(this.current != null);
            LinkedListMultimap.this.removeAllNodes(this.current.key);
            this.current = null;
        }
    }
    
    private class ValueForKeyIterator implements ListIterator<V>
    {
        final Object key;
        int nextIndex;
        Node<K, V> next;
        Node<K, V> current;
        Node<K, V> previous;
        
        ValueForKeyIterator(final Object key) {
            this.key = key;
            this.next = LinkedListMultimap.this.keyToKeyHead.get(key);
        }
        
        public ValueForKeyIterator(final Object key, int index) {
            final int size = LinkedListMultimap.this.keyCount.count(key);
            Preconditions.checkPositionIndex(index, size);
            if (index >= size / 2) {
                this.previous = LinkedListMultimap.this.keyToKeyTail.get(key);
                this.nextIndex = size;
                while (index++ < size) {
                    this.previous();
                }
            }
            else {
                this.next = LinkedListMultimap.this.keyToKeyHead.get(key);
                while (index-- > 0) {
                    this.next();
                }
            }
            this.key = key;
            this.current = null;
        }
        
        public boolean hasNext() {
            return this.next != null;
        }
        
        public V next() {
            checkElement(this.next);
            final Node<K, V> next = this.next;
            this.current = next;
            this.previous = next;
            this.next = this.next.nextSibling;
            ++this.nextIndex;
            return this.current.value;
        }
        
        public boolean hasPrevious() {
            return this.previous != null;
        }
        
        public V previous() {
            checkElement(this.previous);
            final Node<K, V> previous = this.previous;
            this.current = previous;
            this.next = previous;
            this.previous = this.previous.previousSibling;
            --this.nextIndex;
            return this.current.value;
        }
        
        public int nextIndex() {
            return this.nextIndex;
        }
        
        public int previousIndex() {
            return this.nextIndex - 1;
        }
        
        public void remove() {
            Preconditions.checkState(this.current != null);
            if (this.current != this.next) {
                this.previous = this.current.previousSibling;
                --this.nextIndex;
            }
            else {
                this.next = this.current.nextSibling;
            }
            LinkedListMultimap.this.removeNode(this.current);
            this.current = null;
        }
        
        public void set(final V value) {
            Preconditions.checkState(this.current != null);
            this.current.value = value;
        }
        
        public void add(final V value) {
            this.previous = (Node<K, V>)LinkedListMultimap.this.addNode(this.key, value, this.next);
            ++this.nextIndex;
            this.current = null;
        }
    }
    
    private class MultisetView extends AbstractCollection<K> implements Multiset<K>
    {
        public int size() {
            return LinkedListMultimap.this.keyCount.size();
        }
        
        public Iterator<K> iterator() {
            final Iterator<Node<K, V>> nodes = new NodeIterator();
            return new Iterator<K>() {
                public boolean hasNext() {
                    return nodes.hasNext();
                }
                
                public K next() {
                    return (K)nodes.next().key;
                }
                
                public void remove() {
                    nodes.remove();
                }
            };
        }
        
        public int count(@Nullable final Object key) {
            return LinkedListMultimap.this.keyCount.count(key);
        }
        
        public int add(@Nullable final K key, final int occurrences) {
            throw new UnsupportedOperationException();
        }
        
        public int remove(@Nullable final Object key, int occurrences) {
            Preconditions.checkArgument(occurrences >= 0);
            final int oldCount = this.count(key);
            final Iterator<V> values = new ValueForKeyIterator(key);
            while (occurrences-- > 0 && values.hasNext()) {
                values.next();
                values.remove();
            }
            return oldCount;
        }
        
        public int setCount(final K element, final int count) {
            return Multisets.setCountImpl(this, element, count);
        }
        
        public boolean setCount(final K element, final int oldCount, final int newCount) {
            return Multisets.setCountImpl(this, element, oldCount, newCount);
        }
        
        public boolean removeAll(final Collection<?> c) {
            return Iterators.removeAll(this.iterator(), c);
        }
        
        public boolean retainAll(final Collection<?> c) {
            return Iterators.retainAll(this.iterator(), c);
        }
        
        public Set<K> elementSet() {
            return LinkedListMultimap.this.keySet();
        }
        
        public Set<Entry<K>> entrySet() {
            return new AbstractSet<Entry<K>>() {
                public int size() {
                    return LinkedListMultimap.this.keyCount.elementSet().size();
                }
                
                public Iterator<Entry<K>> iterator() {
                    final Iterator<K> keyIterator = new DistinctKeyIterator();
                    return new Iterator<Entry<K>>() {
                        public boolean hasNext() {
                            return keyIterator.hasNext();
                        }
                        
                        public Entry<K> next() {
                            final K key = keyIterator.next();
                            return new Multisets.AbstractEntry<K>() {
                                public K getElement() {
                                    return key;
                                }
                                
                                public int getCount() {
                                    return LinkedListMultimap.this.keyCount.count(key);
                                }
                            };
                        }
                        
                        public void remove() {
                            keyIterator.remove();
                        }
                    };
                }
            };
        }
        
        public boolean equals(@Nullable final Object object) {
            return LinkedListMultimap.this.keyCount.equals(object);
        }
        
        public int hashCode() {
            return LinkedListMultimap.this.keyCount.hashCode();
        }
        
        public String toString() {
            return LinkedListMultimap.this.keyCount.toString();
        }
    }
    
    private class AsMapEntries extends AbstractSet<Map.Entry<K, Collection<V>>>
    {
        public int size() {
            return LinkedListMultimap.this.keyCount.elementSet().size();
        }
        
        public Iterator<Map.Entry<K, Collection<V>>> iterator() {
            final Iterator<K> keyIterator = new DistinctKeyIterator();
            return new Iterator<Map.Entry<K, Collection<V>>>() {
                public boolean hasNext() {
                    return keyIterator.hasNext();
                }
                
                public Map.Entry<K, Collection<V>> next() {
                    final K key = keyIterator.next();
                    return new AbstractMapEntry<K, Collection<V>>() {
                        public K getKey() {
                            return key;
                        }
                        
                        public Collection<V> getValue() {
                            return LinkedListMultimap.this.get(key);
                        }
                    };
                }
                
                public void remove() {
                    keyIterator.remove();
                }
            };
        }
    }
}
