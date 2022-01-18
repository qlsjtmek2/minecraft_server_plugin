// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.io.ObjectStreamException;
import java.io.InvalidObjectException;
import javax.annotation.Nullable;
import com.google.common.primitives.Ints;
import java.util.Iterator;
import java.util.Set;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtIncompatible;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;

@GwtCompatible(emulated = true)
abstract class AbstractMapBasedMultiset<E> extends AbstractMultiset<E> implements Serializable
{
    private transient Map<E, Count> backingMap;
    private transient long size;
    @GwtIncompatible("not needed in emulated source.")
    private static final long serialVersionUID = -2250766705698539974L;
    
    protected AbstractMapBasedMultiset(final Map<E, Count> backingMap) {
        this.backingMap = Preconditions.checkNotNull(backingMap);
        this.size = super.size();
    }
    
    Map<E, Count> backingMap() {
        return this.backingMap;
    }
    
    void setBackingMap(final Map<E, Count> backingMap) {
        this.backingMap = backingMap;
    }
    
    public Set<Multiset.Entry<E>> entrySet() {
        return super.entrySet();
    }
    
    Iterator<Multiset.Entry<E>> entryIterator() {
        final Iterator<Map.Entry<E, Count>> backingEntries = this.backingMap.entrySet().iterator();
        return new Iterator<Multiset.Entry<E>>() {
            Map.Entry<E, Count> toRemove;
            
            public boolean hasNext() {
                return backingEntries.hasNext();
            }
            
            public Multiset.Entry<E> next() {
                final Map.Entry<E, Count> mapEntry = backingEntries.next();
                this.toRemove = mapEntry;
                return new Multisets.AbstractEntry<E>() {
                    public E getElement() {
                        return mapEntry.getKey();
                    }
                    
                    public int getCount() {
                        int count = mapEntry.getValue().get();
                        if (count == 0) {
                            final Count frequency = AbstractMapBasedMultiset.this.backingMap.get(this.getElement());
                            if (frequency != null) {
                                count = frequency.get();
                            }
                        }
                        return count;
                    }
                };
            }
            
            public void remove() {
                Preconditions.checkState(this.toRemove != null, (Object)"no calls to next() since the last call to remove()");
                AbstractMapBasedMultiset.this.size -= this.toRemove.getValue().getAndSet(0);
                backingEntries.remove();
                this.toRemove = null;
            }
        };
    }
    
    public void clear() {
        for (final Count frequency : this.backingMap.values()) {
            frequency.set(0);
        }
        this.backingMap.clear();
        this.size = 0L;
    }
    
    int distinctElements() {
        return this.backingMap.size();
    }
    
    public int size() {
        return Ints.saturatedCast(this.size);
    }
    
    public Iterator<E> iterator() {
        return new MapBasedMultisetIterator();
    }
    
    public int count(@Nullable final Object element) {
        try {
            final Count frequency = this.backingMap.get(element);
            return (frequency == null) ? 0 : frequency.get();
        }
        catch (NullPointerException e) {
            return 0;
        }
        catch (ClassCastException e2) {
            return 0;
        }
    }
    
    public int add(@Nullable final E element, final int occurrences) {
        if (occurrences == 0) {
            return this.count(element);
        }
        Preconditions.checkArgument(occurrences > 0, "occurrences cannot be negative: %s", occurrences);
        final Count frequency = this.backingMap.get(element);
        int oldCount;
        if (frequency == null) {
            oldCount = 0;
            this.backingMap.put(element, new Count(occurrences));
        }
        else {
            oldCount = frequency.get();
            final long newCount = oldCount + occurrences;
            Preconditions.checkArgument(newCount <= 2147483647L, "too many occurrences: %s", newCount);
            frequency.getAndAdd(occurrences);
        }
        this.size += occurrences;
        return oldCount;
    }
    
    public int remove(@Nullable final Object element, final int occurrences) {
        if (occurrences == 0) {
            return this.count(element);
        }
        Preconditions.checkArgument(occurrences > 0, "occurrences cannot be negative: %s", occurrences);
        final Count frequency = this.backingMap.get(element);
        if (frequency == null) {
            return 0;
        }
        final int oldCount = frequency.get();
        int numberRemoved;
        if (oldCount > occurrences) {
            numberRemoved = occurrences;
        }
        else {
            numberRemoved = oldCount;
            this.backingMap.remove(element);
        }
        frequency.addAndGet(-numberRemoved);
        this.size -= numberRemoved;
        return oldCount;
    }
    
    public int setCount(final E element, final int count) {
        Multisets.checkNonnegative(count, "count");
        int oldCount;
        if (count == 0) {
            final Count existingCounter = this.backingMap.remove(element);
            oldCount = getAndSet(existingCounter, count);
        }
        else {
            final Count existingCounter = this.backingMap.get(element);
            oldCount = getAndSet(existingCounter, count);
            if (existingCounter == null) {
                this.backingMap.put(element, new Count(count));
            }
        }
        this.size += count - oldCount;
        return oldCount;
    }
    
    private static int getAndSet(final Count i, final int count) {
        if (i == null) {
            return 0;
        }
        return i.getAndSet(count);
    }
    
    private int removeAllOccurrences(@Nullable final Object element, final Map<E, Count> map) {
        final Count frequency = map.remove(element);
        if (frequency == null) {
            return 0;
        }
        final int numberRemoved = frequency.getAndSet(0);
        this.size -= numberRemoved;
        return numberRemoved;
    }
    
    Set<E> createElementSet() {
        return new MapBasedElementSet(this.backingMap);
    }
    
    @GwtIncompatible("java.io.ObjectStreamException")
    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream data required");
    }
    
    private class MapBasedMultisetIterator implements Iterator<E>
    {
        final Iterator<Map.Entry<E, Count>> entryIterator;
        Map.Entry<E, Count> currentEntry;
        int occurrencesLeft;
        boolean canRemove;
        
        MapBasedMultisetIterator() {
            this.entryIterator = AbstractMapBasedMultiset.this.backingMap.entrySet().iterator();
        }
        
        public boolean hasNext() {
            return this.occurrencesLeft > 0 || this.entryIterator.hasNext();
        }
        
        public E next() {
            if (this.occurrencesLeft == 0) {
                this.currentEntry = this.entryIterator.next();
                this.occurrencesLeft = this.currentEntry.getValue().get();
            }
            --this.occurrencesLeft;
            this.canRemove = true;
            return this.currentEntry.getKey();
        }
        
        public void remove() {
            Preconditions.checkState(this.canRemove, (Object)"no calls to next() since the last call to remove()");
            final int frequency = this.currentEntry.getValue().get();
            if (frequency <= 0) {
                throw new ConcurrentModificationException();
            }
            if (this.currentEntry.getValue().addAndGet(-1) == 0) {
                this.entryIterator.remove();
            }
            AbstractMapBasedMultiset.this.size--;
            this.canRemove = false;
        }
    }
    
    class MapBasedElementSet extends ForwardingSet<E>
    {
        private final Map<E, Count> map;
        private final Set<E> delegate;
        
        MapBasedElementSet(final Map<E, Count> map) {
            this.map = map;
            this.delegate = map.keySet();
        }
        
        protected Set<E> delegate() {
            return this.delegate;
        }
        
        public Iterator<E> iterator() {
            final Iterator<Map.Entry<E, Count>> entries = this.map.entrySet().iterator();
            return new Iterator<E>() {
                Map.Entry<E, Count> toRemove;
                
                public boolean hasNext() {
                    return entries.hasNext();
                }
                
                public E next() {
                    this.toRemove = entries.next();
                    return this.toRemove.getKey();
                }
                
                public void remove() {
                    Preconditions.checkState(this.toRemove != null, (Object)"no calls to next() since the last call to remove()");
                    AbstractMapBasedMultiset.this.size -= this.toRemove.getValue().getAndSet(0);
                    entries.remove();
                    this.toRemove = null;
                }
            };
        }
        
        public boolean remove(final Object element) {
            return AbstractMapBasedMultiset.this.removeAllOccurrences(element, this.map) != 0;
        }
        
        public boolean removeAll(final Collection<?> elementsToRemove) {
            return Iterators.removeAll(this.iterator(), elementsToRemove);
        }
        
        public boolean retainAll(final Collection<?> elementsToRetain) {
            return Iterators.retainAll(this.iterator(), elementsToRetain);
        }
        
        public void clear() {
            if (this.map == AbstractMapBasedMultiset.this.backingMap) {
                AbstractMapBasedMultiset.this.clear();
            }
            else {
                final Iterator<E> i = this.iterator();
                while (i.hasNext()) {
                    i.next();
                    i.remove();
                }
            }
        }
        
        public Map<E, Count> getMap() {
            return this.map;
        }
    }
}
