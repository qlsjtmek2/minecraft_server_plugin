// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import com.google.common.primitives.Ints;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.annotations.Beta;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentMap;
import java.io.Serializable;

public final class ConcurrentHashMultiset<E> extends AbstractMultiset<E> implements Serializable
{
    private final transient ConcurrentMap<E, AtomicInteger> countMap;
    private transient EntrySet entrySet;
    private static final long serialVersionUID = 1L;
    
    public static <E> ConcurrentHashMultiset<E> create() {
        return new ConcurrentHashMultiset<E>(new ConcurrentHashMap<E, AtomicInteger>());
    }
    
    public static <E> ConcurrentHashMultiset<E> create(final Iterable<? extends E> elements) {
        final ConcurrentHashMultiset<E> multiset = create();
        Iterables.addAll(multiset, elements);
        return multiset;
    }
    
    @Beta
    public static <E> ConcurrentHashMultiset<E> create(final GenericMapMaker<? super E, ? super Number> mapMaker) {
        return new ConcurrentHashMultiset<E>(mapMaker.makeMap());
    }
    
    ConcurrentHashMultiset(final ConcurrentMap<E, AtomicInteger> countMap) {
        Preconditions.checkArgument(countMap.isEmpty());
        this.countMap = countMap;
    }
    
    public int count(@Nullable final Object element) {
        final AtomicInteger existingCounter = this.safeGet(element);
        return (existingCounter == null) ? 0 : existingCounter.get();
    }
    
    private AtomicInteger safeGet(final Object element) {
        try {
            return this.countMap.get(element);
        }
        catch (NullPointerException e) {
            return null;
        }
        catch (ClassCastException e2) {
            return null;
        }
    }
    
    public int size() {
        long sum = 0L;
        for (final AtomicInteger value : this.countMap.values()) {
            sum += value.get();
        }
        return Ints.saturatedCast(sum);
    }
    
    public Object[] toArray() {
        return this.snapshot().toArray();
    }
    
    public <T> T[] toArray(final T[] array) {
        return this.snapshot().toArray(array);
    }
    
    private List<E> snapshot() {
        final List<E> list = (List<E>)Lists.newArrayListWithExpectedSize(this.size());
        for (final Multiset.Entry<E> entry : this.entrySet()) {
            final E element = entry.getElement();
            for (int i = entry.getCount(); i > 0; --i) {
                list.add(element);
            }
        }
        return list;
    }
    
    public int add(final E element, final int occurrences) {
        if (occurrences == 0) {
            return this.count(element);
        }
        Preconditions.checkArgument(occurrences > 0, "Invalid occurrences: %s", occurrences);
        while (true) {
            AtomicInteger existingCounter = this.safeGet(element);
            if (existingCounter == null) {
                existingCounter = this.countMap.putIfAbsent(element, new AtomicInteger(occurrences));
                if (existingCounter == null) {
                    return 0;
                }
            }
            while (true) {
                final int oldValue = existingCounter.get();
                if (oldValue != 0) {
                    Preconditions.checkArgument(occurrences <= Integer.MAX_VALUE - oldValue, "Overflow adding %s occurrences to a count of %s", occurrences, oldValue);
                    final int newValue = oldValue + occurrences;
                    if (existingCounter.compareAndSet(oldValue, newValue)) {
                        return oldValue;
                    }
                    continue;
                }
                else {
                    final AtomicInteger newCounter = new AtomicInteger(occurrences);
                    if (this.countMap.putIfAbsent(element, newCounter) == null || this.countMap.replace(element, existingCounter, newCounter)) {
                        return 0;
                    }
                    break;
                }
            }
        }
    }
    
    public int remove(@Nullable final Object element, final int occurrences) {
        if (occurrences == 0) {
            return this.count(element);
        }
        Preconditions.checkArgument(occurrences > 0, "Invalid occurrences: %s", occurrences);
        final AtomicInteger existingCounter = this.safeGet(element);
        if (existingCounter == null) {
            return 0;
        }
        while (true) {
            final int oldValue = existingCounter.get();
            if (oldValue == 0) {
                return 0;
            }
            final int newValue = Math.max(0, oldValue - occurrences);
            if (existingCounter.compareAndSet(oldValue, newValue)) {
                if (newValue == 0) {
                    this.countMap.remove(element, existingCounter);
                }
                return oldValue;
            }
        }
    }
    
    public boolean removeExactly(@Nullable final Object element, final int occurrences) {
        if (occurrences == 0) {
            return true;
        }
        Preconditions.checkArgument(occurrences > 0, "Invalid occurrences: %s", occurrences);
        final AtomicInteger existingCounter = this.safeGet(element);
        if (existingCounter == null) {
            return false;
        }
        while (true) {
            final int oldValue = existingCounter.get();
            if (oldValue < occurrences) {
                return false;
            }
            final int newValue = oldValue - occurrences;
            if (existingCounter.compareAndSet(oldValue, newValue)) {
                if (newValue == 0) {
                    this.countMap.remove(element, existingCounter);
                }
                return true;
            }
        }
    }
    
    public int setCount(final E element, final int count) {
        Multisets.checkNonnegative(count, "count");
        while (true) {
            AtomicInteger existingCounter = this.safeGet(element);
            if (existingCounter == null) {
                if (count == 0) {
                    return 0;
                }
                existingCounter = this.countMap.putIfAbsent(element, new AtomicInteger(count));
                if (existingCounter == null) {
                    return 0;
                }
            }
            while (true) {
                final int oldValue = existingCounter.get();
                if (oldValue == 0) {
                    if (count == 0) {
                        return 0;
                    }
                    final AtomicInteger newCounter = new AtomicInteger(count);
                    if (this.countMap.putIfAbsent(element, newCounter) == null || this.countMap.replace(element, existingCounter, newCounter)) {
                        return 0;
                    }
                    break;
                }
                else {
                    if (existingCounter.compareAndSet(oldValue, count)) {
                        if (count == 0) {
                            this.countMap.remove(element, existingCounter);
                        }
                        return oldValue;
                    }
                    continue;
                }
            }
        }
    }
    
    public boolean setCount(final E element, final int expectedOldCount, final int newCount) {
        Multisets.checkNonnegative(expectedOldCount, "oldCount");
        Multisets.checkNonnegative(newCount, "newCount");
        final AtomicInteger existingCounter = this.safeGet(element);
        if (existingCounter == null) {
            return expectedOldCount == 0 && (newCount == 0 || this.countMap.putIfAbsent(element, new AtomicInteger(newCount)) == null);
        }
        final int oldValue = existingCounter.get();
        if (oldValue == expectedOldCount) {
            if (oldValue == 0) {
                if (newCount == 0) {
                    this.countMap.remove(element, existingCounter);
                    return true;
                }
                final AtomicInteger newCounter = new AtomicInteger(newCount);
                return this.countMap.putIfAbsent(element, newCounter) == null || this.countMap.replace(element, existingCounter, newCounter);
            }
            else if (existingCounter.compareAndSet(oldValue, newCount)) {
                if (newCount == 0) {
                    this.countMap.remove(element, existingCounter);
                }
                return true;
            }
        }
        return false;
    }
    
    Set<E> createElementSet() {
        final Set<E> delegate = this.countMap.keySet();
        return new ForwardingSet<E>() {
            protected Set<E> delegate() {
                return delegate;
            }
            
            public boolean remove(final Object object) {
                try {
                    return delegate.remove(object);
                }
                catch (NullPointerException e) {
                    return false;
                }
                catch (ClassCastException e2) {
                    return false;
                }
            }
        };
    }
    
    public Set<Multiset.Entry<E>> entrySet() {
        EntrySet result = this.entrySet;
        if (result == null) {
            result = (this.entrySet = new EntrySet());
        }
        return (Set<Multiset.Entry<E>>)result;
    }
    
    int distinctElements() {
        return this.countMap.size();
    }
    
    public boolean isEmpty() {
        return this.countMap.isEmpty();
    }
    
    Iterator<Multiset.Entry<E>> entryIterator() {
        final Iterator<Multiset.Entry<E>> readOnlyIterator = new AbstractIterator<Multiset.Entry<E>>() {
            private Iterator<Map.Entry<E, AtomicInteger>> mapEntries = ConcurrentHashMultiset.this.countMap.entrySet().iterator();
            
            protected Multiset.Entry<E> computeNext() {
                while (this.mapEntries.hasNext()) {
                    final Map.Entry<E, AtomicInteger> mapEntry = this.mapEntries.next();
                    final int count = mapEntry.getValue().get();
                    if (count != 0) {
                        return Multisets.immutableEntry(mapEntry.getKey(), count);
                    }
                }
                return this.endOfData();
            }
        };
        return new ForwardingIterator<Multiset.Entry<E>>() {
            private Multiset.Entry<E> last;
            
            protected Iterator<Multiset.Entry<E>> delegate() {
                return readOnlyIterator;
            }
            
            public Multiset.Entry<E> next() {
                return this.last = super.next();
            }
            
            public void remove() {
                Preconditions.checkState(this.last != null);
                ConcurrentHashMultiset.this.setCount(this.last.getElement(), 0);
                this.last = null;
            }
        };
    }
    
    public void clear() {
        this.countMap.clear();
    }
    
    private void writeObject(final ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(this.countMap);
    }
    
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        final ConcurrentMap<E, Integer> deserializedCountMap = (ConcurrentMap<E, Integer>)stream.readObject();
        FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, deserializedCountMap);
    }
    
    private static class FieldSettersHolder
    {
        static final Serialization.FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER;
        
        static {
            COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");
        }
    }
    
    private class EntrySet extends AbstractMultiset.EntrySet
    {
        ConcurrentHashMultiset<E> multiset() {
            return ConcurrentHashMultiset.this;
        }
        
        public Object[] toArray() {
            return this.snapshot().toArray();
        }
        
        public <T> T[] toArray(final T[] array) {
            return this.snapshot().toArray(array);
        }
        
        private List<Multiset.Entry<E>> snapshot() {
            final List<Multiset.Entry<E>> list = (List<Multiset.Entry<E>>)Lists.newArrayListWithExpectedSize(this.size());
            Iterators.addAll(list, (Iterator<? extends Multiset.Entry<E>>)this.iterator());
            return list;
        }
        
        public boolean remove(final Object object) {
            if (object instanceof Multiset.Entry) {
                final Multiset.Entry<?> entry = (Multiset.Entry<?>)object;
                final Object element = entry.getElement();
                final int entryCount = entry.getCount();
                if (entryCount != 0) {
                    final Multiset<Object> multiset = (Multiset<Object>)this.multiset();
                    return multiset.setCount(element, entryCount, 0);
                }
            }
            return false;
        }
    }
}
