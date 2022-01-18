// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.hash;

import java.util.ConcurrentModificationException;
import java.lang.reflect.Array;
import java.util.AbstractSet;
import gnu.trove.iterator.hash.TObjectHashIterator;
import gnu.trove.impl.HashFunctions;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.Set;
import java.util.Collection;
import java.util.Arrays;
import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TObjectObjectProcedure;
import java.util.Map;
import java.io.Externalizable;
import gnu.trove.map.TMap;
import gnu.trove.impl.hash.TObjectHash;

public class THashMap<K, V> extends TObjectHash<K> implements TMap<K, V>, Externalizable
{
    static final long serialVersionUID = 1L;
    protected transient V[] _values;
    
    public THashMap() {
    }
    
    public THashMap(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public THashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public THashMap(final Map<? extends K, ? extends V> map) {
        this(map.size());
        this.putAll(map);
    }
    
    public THashMap(final THashMap<? extends K, ? extends V> map) {
        this(map.size());
        this.putAll(map);
    }
    
    public int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._values = new Object[capacity];
        return capacity;
    }
    
    public V put(final K key, final V value) {
        final int index = this.insertKey(key);
        return this.doPut(value, index);
    }
    
    public V putIfAbsent(final K key, final V value) {
        final int index = this.insertKey(key);
        if (index < 0) {
            return (V)this._values[-index - 1];
        }
        return this.doPut(value, index);
    }
    
    private V doPut(final V value, int index) {
        V previous = null;
        boolean isNewMapping = true;
        if (index < 0) {
            index = -index - 1;
            previous = (V)this._values[index];
            isNewMapping = false;
        }
        this._values[index] = value;
        if (isNewMapping) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return previous;
    }
    
    public boolean equals(final Object other) {
        if (!(other instanceof Map)) {
            return false;
        }
        final Map<K, V> that = (Map<K, V>)other;
        return that.size() == this.size() && this.forEachEntry(new EqProcedure<Object, Object>(that));
    }
    
    public int hashCode() {
        final HashProcedure p = new HashProcedure();
        this.forEachEntry(p);
        return p.getHashCode();
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.forEachEntry(new TObjectObjectProcedure<K, V>() {
            private boolean first = true;
            
            public boolean execute(final K key, final V value) {
                if (this.first) {
                    this.first = false;
                }
                else {
                    buf.append(", ");
                }
                buf.append(key);
                buf.append("=");
                buf.append(value);
                return true;
            }
        });
        buf.append("}");
        return buf.toString();
    }
    
    public boolean forEachKey(final TObjectProcedure<? super K> procedure) {
        return this.forEach(procedure);
    }
    
    public boolean forEachValue(final TObjectProcedure<? super V> procedure) {
        final V[] values = (V[])this._values;
        final Object[] set = this._set;
        int i = values.length;
        while (i-- > 0) {
            if (set[i] != THashMap.FREE && set[i] != THashMap.REMOVED && !procedure.execute((Object)values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachEntry(final TObjectObjectProcedure<? super K, ? super V> procedure) {
        final Object[] keys = this._set;
        final V[] values = (V[])this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (keys[i] != THashMap.FREE && keys[i] != THashMap.REMOVED && !procedure.execute((Object)keys[i], (Object)values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean retainEntries(final TObjectObjectProcedure<? super K, ? super V> procedure) {
        boolean modified = false;
        final Object[] keys = this._set;
        final V[] values = (V[])this._values;
        this.tempDisableAutoCompaction();
        try {
            int i = keys.length;
            while (i-- > 0) {
                if (keys[i] != THashMap.FREE && keys[i] != THashMap.REMOVED && !procedure.execute((Object)keys[i], (Object)values[i])) {
                    this.removeAt(i);
                    modified = true;
                }
            }
        }
        finally {
            this.reenableAutoCompaction(true);
        }
        return modified;
    }
    
    public void transformValues(final TObjectFunction<V, V> function) {
        final V[] values = (V[])this._values;
        final Object[] set = this._set;
        int i = values.length;
        while (i-- > 0) {
            if (set[i] != THashMap.FREE && set[i] != THashMap.REMOVED) {
                values[i] = function.execute(values[i]);
            }
        }
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final int oldSize = this.size();
        final Object[] oldKeys = this._set;
        final V[] oldVals = (V[])this._values;
        Arrays.fill(this._set = new Object[newCapacity], THashMap.FREE);
        this._values = new Object[newCapacity];
        int count = 0;
        int i = oldCapacity;
        while (i-- > 0) {
            final Object o = oldKeys[i];
            if (o != THashMap.FREE) {
                if (o == THashMap.REMOVED) {
                    continue;
                }
                final int index = this.insertKey((K)o);
                if (index < 0) {
                    this.throwObjectContractViolation(this._set[-index - 1], o, this.size(), oldSize, oldKeys);
                }
                this._values[index] = oldVals[i];
                ++count;
            }
        }
        TObjectHash.reportPotentialConcurrentMod(this.size(), oldSize);
    }
    
    public V get(final Object key) {
        final int index = this.index(key);
        return (V)((index < 0) ? null : this._values[index]);
    }
    
    public void clear() {
        if (this.size() == 0) {
            return;
        }
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, THashMap.FREE);
        Arrays.fill(this._values, 0, this._values.length, null);
    }
    
    public V remove(final Object key) {
        V prev = null;
        final int index = this.index(key);
        if (index >= 0) {
            prev = (V)this._values[index];
            this.removeAt(index);
        }
        return prev;
    }
    
    public void removeAt(final int index) {
        this._values[index] = null;
        super.removeAt(index);
    }
    
    public Collection<V> values() {
        return new ValueView();
    }
    
    public Set<K> keySet() {
        return new KeyView();
    }
    
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntryView();
    }
    
    public boolean containsValue(final Object val) {
        final Object[] set = this._set;
        final V[] vals = (V[])this._values;
        if (null == val) {
            int i = vals.length;
            while (i-- > 0) {
                if (set[i] != THashMap.FREE && set[i] != THashMap.REMOVED && val == vals[i]) {
                    return true;
                }
            }
        }
        else {
            int i = vals.length;
            while (i-- > 0) {
                if (set[i] != THashMap.FREE && set[i] != THashMap.REMOVED && (val == vals[i] || this.equals(val, vals[i]))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean containsKey(final Object key) {
        return this.contains(key);
    }
    
    public void putAll(final Map<? extends K, ? extends V> map) {
        this.ensureCapacity(map.size());
        for (final Map.Entry<? extends K, ? extends V> e : map.entrySet()) {
            this.put(e.getKey(), e.getValue());
        }
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(1);
        super.writeExternal(out);
        out.writeInt(this._size);
        int i = this._set.length;
        while (i-- > 0) {
            if (this._set[i] != THashMap.REMOVED && this._set[i] != THashMap.FREE) {
                out.writeObject(this._set[i]);
                out.writeObject(this._values[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        final byte version = in.readByte();
        if (version != 0) {
            super.readExternal(in);
        }
        int size = in.readInt();
        this.setUp(size);
        while (size-- > 0) {
            final K key = (K)in.readObject();
            final V val = (V)in.readObject();
            this.put(key, val);
        }
    }
    
    private final class HashProcedure implements TObjectObjectProcedure<K, V>
    {
        private int h;
        
        private HashProcedure() {
            this.h = 0;
        }
        
        public int getHashCode() {
            return this.h;
        }
        
        public final boolean execute(final K key, final V value) {
            this.h += (HashFunctions.hash(key) ^ ((value == null) ? 0 : value.hashCode()));
            return true;
        }
    }
    
    private final class EqProcedure<K, V> implements TObjectObjectProcedure<K, V>
    {
        private final Map<K, V> _otherMap;
        
        EqProcedure(final Map<K, V> otherMap) {
            this._otherMap = otherMap;
        }
        
        public final boolean execute(final K key, final V value) {
            if (value == null && !this._otherMap.containsKey(key)) {
                return false;
            }
            final V oValue = this._otherMap.get(key);
            return oValue == value || (oValue != null && THashMap.this.equals(oValue, value));
        }
    }
    
    protected class ValueView extends MapBackedView<V>
    {
        public Iterator<V> iterator() {
            return (Iterator<V>)new TObjectHashIterator(THashMap.this) {
                protected V objectAtIndex(final int index) {
                    return (V)THashMap.this._values[index];
                }
            };
        }
        
        public boolean containsElement(final V value) {
            return THashMap.this.containsValue(value);
        }
        
        public boolean removeElement(final V value) {
            final Object[] values = THashMap.this._values;
            final Object[] set = THashMap.this._set;
            int i = values.length;
            while (i-- > 0) {
                if ((set[i] != TObjectHash.FREE && set[i] != TObjectHash.REMOVED && value == values[i]) || (null != values[i] && THashMap.this.equals(values[i], value))) {
                    THashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
    }
    
    protected class EntryView extends MapBackedView<Map.Entry<K, V>>
    {
        public Iterator<Map.Entry<K, V>> iterator() {
            return (Iterator<Map.Entry<K, V>>)new EntryIterator(THashMap.this);
        }
        
        public boolean removeElement(final Map.Entry<K, V> entry) {
            if (entry == null) {
                return false;
            }
            final K key = this.keyForEntry(entry);
            final int index = THashMap.this.index(key);
            if (index >= 0) {
                final V val = this.valueForEntry(entry);
                if (val == THashMap.this._values[index] || (null != val && THashMap.this.equals(val, THashMap.this._values[index]))) {
                    THashMap.this.removeAt(index);
                    return true;
                }
            }
            return false;
        }
        
        public boolean containsElement(final Map.Entry<K, V> entry) {
            final V val = THashMap.this.get(this.keyForEntry((Map.Entry<Object, V>)entry));
            final V entryValue = entry.getValue();
            return entryValue == val || (null != val && THashMap.this.equals(val, entryValue));
        }
        
        protected V valueForEntry(final Map.Entry<K, V> entry) {
            return entry.getValue();
        }
        
        protected K keyForEntry(final Map.Entry<K, V> entry) {
            return entry.getKey();
        }
        
        private final class EntryIterator extends TObjectHashIterator
        {
            EntryIterator(final THashMap<K, V> map) {
                super(map);
            }
            
            public Entry objectAtIndex(final int index) {
                return new Entry((K)THashMap.this._set[index], (V)THashMap.this._values[index], index);
            }
        }
    }
    
    private abstract class MapBackedView<E> extends AbstractSet<E> implements Set<E>, Iterable<E>
    {
        public abstract Iterator<E> iterator();
        
        public abstract boolean removeElement(final E p0);
        
        public abstract boolean containsElement(final E p0);
        
        public boolean contains(final Object key) {
            return this.containsElement(key);
        }
        
        public boolean remove(final Object o) {
            try {
                return this.removeElement(o);
            }
            catch (ClassCastException ex) {
                return false;
            }
        }
        
        public void clear() {
            THashMap.this.clear();
        }
        
        public boolean add(final E obj) {
            throw new UnsupportedOperationException();
        }
        
        public int size() {
            return THashMap.this.size();
        }
        
        public Object[] toArray() {
            final Object[] result = new Object[this.size()];
            final Iterator<E> e = this.iterator();
            int i = 0;
            while (e.hasNext()) {
                result[i] = e.next();
                ++i;
            }
            return result;
        }
        
        public <T> T[] toArray(T[] a) {
            final int size = this.size();
            if (a.length < size) {
                a = (T[])Array.newInstance(a.getClass().getComponentType(), size);
            }
            final Iterator<E> it = this.iterator();
            final Object[] result = a;
            for (int i = 0; i < size; ++i) {
                result[i] = it.next();
            }
            if (a.length > size) {
                a[size] = null;
            }
            return a;
        }
        
        public boolean isEmpty() {
            return THashMap.this.isEmpty();
        }
        
        public boolean addAll(final Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean retainAll(final Collection<?> collection) {
            boolean changed = false;
            final Iterator<E> i = this.iterator();
            while (i.hasNext()) {
                if (!collection.contains(i.next())) {
                    i.remove();
                    changed = true;
                }
            }
            return changed;
        }
        
        public String toString() {
            final Iterator<E> i = this.iterator();
            if (!i.hasNext()) {
                return "{}";
            }
            final StringBuilder sb = new StringBuilder();
            sb.append('{');
            while (true) {
                final E e = i.next();
                sb.append((e == this) ? "(this Collection)" : e);
                if (!i.hasNext()) {
                    break;
                }
                sb.append(", ");
            }
            return sb.append('}').toString();
        }
    }
    
    protected class KeyView extends MapBackedView<K>
    {
        public Iterator<K> iterator() {
            return new TObjectHashIterator<K>(THashMap.this);
        }
        
        public boolean removeElement(final K key) {
            return null != THashMap.this.remove(key);
        }
        
        public boolean containsElement(final K key) {
            return THashMap.this.contains(key);
        }
    }
    
    final class Entry implements Map.Entry<K, V>
    {
        private K key;
        private V val;
        private final int index;
        
        Entry(final K key, final V value, final int index) {
            this.key = key;
            this.val = value;
            this.index = index;
        }
        
        public K getKey() {
            return this.key;
        }
        
        public V getValue() {
            return this.val;
        }
        
        public V setValue(final V o) {
            if (THashMap.this._values[this.index] != this.val) {
                throw new ConcurrentModificationException();
            }
            final V retval = this.val;
            THashMap.this._values[this.index] = o;
            this.val = o;
            return retval;
        }
        
        public boolean equals(final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry e2 = (Map.Entry)o;
                return THashMap.this.equals(super.getKey(), e2.getKey()) && THashMap.this.equals(super.getValue(), super.getValue());
            }
            return false;
        }
        
        public int hashCode() {
            return ((this.getKey() == null) ? 0 : this.getKey().hashCode()) ^ ((this.getValue() == null) ? 0 : this.getValue().hashCode());
        }
        
        public String toString() {
            return this.key + "=" + this.val;
        }
    }
}
