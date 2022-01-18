// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.LinkedHashSet;
import com.google.common.base.Preconditions;
import java.util.LinkedHashMap;
import com.google.common.annotations.GwtIncompatible;
import java.util.Map;
import java.util.Collection;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
public final class LinkedHashMultimap<K, V> extends AbstractSetMultimap<K, V>
{
    private static final int DEFAULT_VALUES_PER_KEY = 8;
    @VisibleForTesting
    transient int expectedValuesPerKey;
    transient Collection<Map.Entry<K, V>> linkedEntries;
    @GwtIncompatible("java serialization not supported")
    private static final long serialVersionUID = 0L;
    
    public static <K, V> LinkedHashMultimap<K, V> create() {
        return new LinkedHashMultimap<K, V>();
    }
    
    public static <K, V> LinkedHashMultimap<K, V> create(final int expectedKeys, final int expectedValuesPerKey) {
        return new LinkedHashMultimap<K, V>(expectedKeys, expectedValuesPerKey);
    }
    
    public static <K, V> LinkedHashMultimap<K, V> create(final Multimap<? extends K, ? extends V> multimap) {
        return new LinkedHashMultimap<K, V>(multimap);
    }
    
    private LinkedHashMultimap() {
        super(new LinkedHashMap());
        this.expectedValuesPerKey = 8;
        this.linkedEntries = (Collection<Map.Entry<K, V>>)Sets.newLinkedHashSet();
    }
    
    private LinkedHashMultimap(final int expectedKeys, final int expectedValuesPerKey) {
        super(new LinkedHashMap(expectedKeys));
        this.expectedValuesPerKey = 8;
        Preconditions.checkArgument(expectedValuesPerKey >= 0);
        this.expectedValuesPerKey = expectedValuesPerKey;
        this.linkedEntries = new LinkedHashSet<Map.Entry<K, V>>((int)Math.min(1073741824L, expectedKeys * expectedValuesPerKey));
    }
    
    private LinkedHashMultimap(final Multimap<? extends K, ? extends V> multimap) {
        super(new LinkedHashMap(Maps.capacity(multimap.keySet().size())));
        this.expectedValuesPerKey = 8;
        this.linkedEntries = new LinkedHashSet<Map.Entry<K, V>>(Maps.capacity(multimap.size()));
        this.putAll(multimap);
    }
    
    Set<V> createCollection() {
        return new LinkedHashSet<V>(Maps.capacity(this.expectedValuesPerKey));
    }
    
    Collection<V> createCollection(@Nullable final K key) {
        return new SetDecorator(key, this.createCollection());
    }
    
    Iterator<Map.Entry<K, V>> createEntryIterator() {
        final Iterator<Map.Entry<K, V>> delegateIterator = this.linkedEntries.iterator();
        return new Iterator<Map.Entry<K, V>>() {
            Map.Entry<K, V> entry;
            
            public boolean hasNext() {
                return delegateIterator.hasNext();
            }
            
            public Map.Entry<K, V> next() {
                return this.entry = delegateIterator.next();
            }
            
            public void remove() {
                delegateIterator.remove();
                LinkedHashMultimap.this.remove(this.entry.getKey(), this.entry.getValue());
            }
        };
    }
    
    public Set<V> replaceValues(@Nullable final K key, final Iterable<? extends V> values) {
        return super.replaceValues(key, values);
    }
    
    public Set<Map.Entry<K, V>> entries() {
        return super.entries();
    }
    
    public Collection<V> values() {
        return super.values();
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(this.expectedValuesPerKey);
        Serialization.writeMultimap((Multimap<Object, Object>)this, stream);
        for (final Map.Entry<K, V> entry : this.linkedEntries) {
            stream.writeObject(entry.getKey());
            stream.writeObject(entry.getValue());
        }
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.expectedValuesPerKey = stream.readInt();
        final int distinctKeys = Serialization.readCount(stream);
        this.setMap(new LinkedHashMap<K, Collection<V>>(Maps.capacity(distinctKeys)));
        this.linkedEntries = new LinkedHashSet<Map.Entry<K, V>>(distinctKeys * this.expectedValuesPerKey);
        Serialization.populateMultimap((Multimap<Object, Object>)this, stream, distinctKeys);
        this.linkedEntries.clear();
        for (int i = 0; i < this.size(); ++i) {
            final K key = (K)stream.readObject();
            final V value = (V)stream.readObject();
            this.linkedEntries.add(Maps.immutableEntry(key, value));
        }
    }
    
    private class SetDecorator extends ForwardingSet<V>
    {
        final Set<V> delegate;
        final K key;
        
        SetDecorator(final K key, final Set<V> delegate) {
            this.delegate = delegate;
            this.key = key;
        }
        
        protected Set<V> delegate() {
            return this.delegate;
        }
        
         <E> Map.Entry<K, E> createEntry(@Nullable final E value) {
            return Maps.immutableEntry(this.key, value);
        }
        
         <E> Collection<Map.Entry<K, E>> createEntries(final Collection<E> values) {
            final Collection<Map.Entry<K, E>> entries = (Collection<Map.Entry<K, E>>)Lists.newArrayListWithExpectedSize(values.size());
            for (final E value : values) {
                entries.add(this.createEntry(value));
            }
            return entries;
        }
        
        public boolean add(@Nullable final V value) {
            final boolean changed = this.delegate.add(value);
            if (changed) {
                LinkedHashMultimap.this.linkedEntries.add(this.createEntry(value));
            }
            return changed;
        }
        
        public boolean addAll(final Collection<? extends V> values) {
            final boolean changed = this.delegate.addAll(values);
            if (changed) {
                LinkedHashMultimap.this.linkedEntries.addAll(this.createEntries(this.delegate()));
            }
            return changed;
        }
        
        public void clear() {
            for (final V value : this.delegate) {
                LinkedHashMultimap.this.linkedEntries.remove(this.createEntry(value));
            }
            this.delegate.clear();
        }
        
        public Iterator<V> iterator() {
            final Iterator<V> delegateIterator = this.delegate.iterator();
            return new Iterator<V>() {
                V value;
                
                public boolean hasNext() {
                    return delegateIterator.hasNext();
                }
                
                public V next() {
                    return this.value = delegateIterator.next();
                }
                
                public void remove() {
                    delegateIterator.remove();
                    LinkedHashMultimap.this.linkedEntries.remove(SetDecorator.this.createEntry(this.value));
                }
            };
        }
        
        public boolean remove(@Nullable final Object value) {
            final boolean changed = this.delegate.remove(value);
            if (changed) {
                LinkedHashMultimap.this.linkedEntries.remove(this.createEntry(value));
            }
            return changed;
        }
        
        public boolean removeAll(final Collection<?> values) {
            final boolean changed = this.delegate.removeAll(values);
            if (changed) {
                LinkedHashMultimap.this.linkedEntries.removeAll(this.createEntries(values));
            }
            return changed;
        }
        
        public boolean retainAll(final Collection<?> values) {
            boolean changed = false;
            final Iterator<V> iterator = this.delegate.iterator();
            while (iterator.hasNext()) {
                final V value = iterator.next();
                if (!values.contains(value)) {
                    iterator.remove();
                    LinkedHashMultimap.this.linkedEntries.remove(Maps.immutableEntry(this.key, value));
                    changed = true;
                }
            }
            return changed;
        }
    }
}
