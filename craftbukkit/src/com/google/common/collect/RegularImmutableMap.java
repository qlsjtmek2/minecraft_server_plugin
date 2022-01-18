// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import javax.annotation.concurrent.Immutable;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
final class RegularImmutableMap<K, V> extends ImmutableMap<K, V>
{
    private final transient LinkedEntry<K, V>[] entries;
    private final transient LinkedEntry<K, V>[] table;
    private final transient int mask;
    private final transient int keySetHashCode;
    private transient ImmutableSet<Map.Entry<K, V>> entrySet;
    private transient ImmutableSet<K> keySet;
    private transient ImmutableCollection<V> values;
    private static final long serialVersionUID = 0L;
    
    RegularImmutableMap(final Map.Entry<?, ?>... immutableEntries) {
        final int size = immutableEntries.length;
        this.entries = this.createEntryArray(size);
        final int tableSize = chooseTableSize(size);
        this.table = this.createEntryArray(tableSize);
        this.mask = tableSize - 1;
        int keySetHashCodeMutable = 0;
        for (int entryIndex = 0; entryIndex < size; ++entryIndex) {
            final Map.Entry<K, V> entry = (Map.Entry<K, V>)immutableEntries[entryIndex];
            final K key = entry.getKey();
            final int keyHashCode = key.hashCode();
            keySetHashCodeMutable += keyHashCode;
            final int tableIndex = Hashing.smear(keyHashCode) & this.mask;
            LinkedEntry<K, V> existing = this.table[tableIndex];
            final LinkedEntry<K, V> linkedEntry = newLinkedEntry(key, entry.getValue(), existing);
            this.table[tableIndex] = linkedEntry;
            this.entries[entryIndex] = linkedEntry;
            while (existing != null) {
                Preconditions.checkArgument(!key.equals(existing.getKey()), "duplicate key: %s", key);
                existing = existing.next();
            }
        }
        this.keySetHashCode = keySetHashCodeMutable;
    }
    
    private static int chooseTableSize(final int size) {
        final int tableSize = Integer.highestOneBit(size) << 1;
        Preconditions.checkArgument(tableSize > 0, "table too large: %s", size);
        return tableSize;
    }
    
    private LinkedEntry<K, V>[] createEntryArray(final int size) {
        return (LinkedEntry<K, V>[])new LinkedEntry[size];
    }
    
    private static <K, V> LinkedEntry<K, V> newLinkedEntry(final K key, final V value, @Nullable final LinkedEntry<K, V> next) {
        return (LinkedEntry<K, V>)((next == null) ? new TerminalEntry<Object, Object>(key, value) : new NonTerminalEntry<Object, Object>(key, value, next));
    }
    
    public V get(@Nullable final Object key) {
        if (key == null) {
            return null;
        }
        final int index = Hashing.smear(key.hashCode()) & this.mask;
        for (LinkedEntry<K, V> entry = this.table[index]; entry != null; entry = entry.next()) {
            final K candidateKey = entry.getKey();
            if (key.equals(candidateKey)) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    public int size() {
        return this.entries.length;
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public boolean containsValue(@Nullable final Object value) {
        if (value == null) {
            return false;
        }
        for (final Map.Entry<K, V> entry : this.entries) {
            if (entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
    
    boolean isPartialView() {
        return false;
    }
    
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        final ImmutableSet<Map.Entry<K, V>> es = this.entrySet;
        return (es == null) ? (this.entrySet = (ImmutableSet<Map.Entry<K, V>>)new EntrySet((RegularImmutableMap<Object, Object>)this)) : es;
    }
    
    public ImmutableSet<K> keySet() {
        final ImmutableSet<K> ks = this.keySet;
        return (ks == null) ? (this.keySet = (ImmutableSet<K>)new KeySet((RegularImmutableMap<Object, Object>)this)) : ks;
    }
    
    public ImmutableCollection<V> values() {
        final ImmutableCollection<V> v = this.values;
        return (v == null) ? (this.values = new Values<V>(this)) : v;
    }
    
    public String toString() {
        final StringBuilder result = Collections2.newStringBuilderForCollection(this.size()).append('{');
        Collections2.STANDARD_JOINER.appendTo(result, (Object[])this.entries);
        return result.append('}').toString();
    }
    
    @Immutable
    private static final class NonTerminalEntry<K, V> extends ImmutableEntry<K, V> implements LinkedEntry<K, V>
    {
        final LinkedEntry<K, V> next;
        
        NonTerminalEntry(final K key, final V value, final LinkedEntry<K, V> next) {
            super(key, value);
            this.next = next;
        }
        
        public LinkedEntry<K, V> next() {
            return this.next;
        }
    }
    
    @Immutable
    private static final class TerminalEntry<K, V> extends ImmutableEntry<K, V> implements LinkedEntry<K, V>
    {
        TerminalEntry(final K key, final V value) {
            super(key, value);
        }
        
        @Nullable
        public LinkedEntry<K, V> next() {
            return null;
        }
    }
    
    private static class EntrySet<K, V> extends ArrayImmutableSet<Map.Entry<K, V>>
    {
        final transient RegularImmutableMap<K, V> map;
        
        EntrySet(final RegularImmutableMap<K, V> map) {
            super(((RegularImmutableMap<Object, Object>)map).entries);
            this.map = map;
        }
        
        public boolean contains(final Object target) {
            if (target instanceof Map.Entry) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)target;
                final V mappedValue = this.map.get(entry.getKey());
                return mappedValue != null && mappedValue.equals(entry.getValue());
            }
            return false;
        }
    }
    
    private static class KeySet<K, V> extends TransformedImmutableSet<Map.Entry<K, V>, K>
    {
        final RegularImmutableMap<K, V> map;
        
        KeySet(final RegularImmutableMap<K, V> map) {
            super(((RegularImmutableMap<Object, Object>)map).entries, ((RegularImmutableMap<Object, Object>)map).keySetHashCode);
            this.map = map;
        }
        
        K transform(final Map.Entry<K, V> element) {
            return element.getKey();
        }
        
        public boolean contains(final Object target) {
            return this.map.containsKey(target);
        }
        
        boolean isPartialView() {
            return true;
        }
    }
    
    private static class Values<V> extends ImmutableCollection<V>
    {
        final RegularImmutableMap<?, V> map;
        
        Values(final RegularImmutableMap<?, V> map) {
            this.map = map;
        }
        
        public int size() {
            return ((RegularImmutableMap<Object, Object>)this.map).entries.length;
        }
        
        public UnmodifiableIterator<V> iterator() {
            return new AbstractIndexedListIterator<V>(((RegularImmutableMap<Object, Object>)this.map).entries.length) {
                protected V get(final int index) {
                    return (V)((RegularImmutableMap<Object, Object>)Values.this.map).entries[index].getValue();
                }
            };
        }
        
        public boolean contains(final Object target) {
            return this.map.containsValue(target);
        }
        
        boolean isPartialView() {
            return true;
        }
    }
    
    private interface LinkedEntry<K, V> extends Map.Entry<K, V>
    {
        @Nullable
        LinkedEntry<K, V> next();
    }
}
