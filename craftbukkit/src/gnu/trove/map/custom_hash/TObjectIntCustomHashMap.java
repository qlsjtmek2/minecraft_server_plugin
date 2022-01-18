// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.custom_hash;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import gnu.trove.impl.hash.THash;
import gnu.trove.iterator.TIntIterator;
import java.util.Collection;
import java.util.AbstractSet;
import gnu.trove.impl.hash.TObjectHash;
import gnu.trove.iterator.hash.TObjectHashIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.TIntCollection;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.Arrays;
import gnu.trove.impl.Constants;
import gnu.trove.strategy.HashingStrategy;
import gnu.trove.procedure.TObjectIntProcedure;
import java.io.Externalizable;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.impl.hash.TCustomObjectHash;

public class TObjectIntCustomHashMap<K> extends TCustomObjectHash<K> implements TObjectIntMap<K>, Externalizable
{
    static final long serialVersionUID = 1L;
    private final TObjectIntProcedure<K> PUT_ALL_PROC;
    protected transient int[] _values;
    protected int no_entry_value;
    
    public TObjectIntCustomHashMap() {
        this.PUT_ALL_PROC = new TObjectIntProcedure<K>() {
            public boolean execute(final K key, final int value) {
                TObjectIntCustomHashMap.this.put(key, value);
                return true;
            }
        };
    }
    
    public TObjectIntCustomHashMap(final HashingStrategy<? super K> strategy) {
        super(strategy);
        this.PUT_ALL_PROC = new TObjectIntProcedure<K>() {
            public boolean execute(final K key, final int value) {
                TObjectIntCustomHashMap.this.put(key, value);
                return true;
            }
        };
    }
    
    public TObjectIntCustomHashMap(final HashingStrategy<? super K> strategy, final int initialCapacity) {
        super(strategy, initialCapacity);
        this.PUT_ALL_PROC = new TObjectIntProcedure<K>() {
            public boolean execute(final K key, final int value) {
                TObjectIntCustomHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_INT_NO_ENTRY_VALUE;
    }
    
    public TObjectIntCustomHashMap(final HashingStrategy<? super K> strategy, final int initialCapacity, final float loadFactor) {
        super(strategy, initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TObjectIntProcedure<K>() {
            public boolean execute(final K key, final int value) {
                TObjectIntCustomHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_INT_NO_ENTRY_VALUE;
    }
    
    public TObjectIntCustomHashMap(final HashingStrategy<? super K> strategy, final int initialCapacity, final float loadFactor, final int noEntryValue) {
        super(strategy, initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TObjectIntProcedure<K>() {
            public boolean execute(final K key, final int value) {
                TObjectIntCustomHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_value = noEntryValue;
        if (this.no_entry_value != 0) {
            Arrays.fill(this._values, this.no_entry_value);
        }
    }
    
    public TObjectIntCustomHashMap(final HashingStrategy<? super K> strategy, final TObjectIntMap<? extends K> map) {
        this(strategy, map.size(), 0.5f, map.getNoEntryValue());
        if (map instanceof TObjectIntCustomHashMap) {
            final TObjectIntCustomHashMap hashmap = (TObjectIntCustomHashMap)map;
            this._loadFactor = hashmap._loadFactor;
            this.no_entry_value = hashmap.no_entry_value;
            this.strategy = (HashingStrategy<? super K>)hashmap.strategy;
            if (this.no_entry_value != 0) {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(map);
    }
    
    public int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._values = new int[capacity];
        return capacity;
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final K[] oldKeys = (K[])this._set;
        final int[] oldVals = this._values;
        Arrays.fill(this._set = new Object[newCapacity], TObjectIntCustomHashMap.FREE);
        Arrays.fill(this._values = new int[newCapacity], this.no_entry_value);
        int i = oldCapacity;
        while (i-- > 0) {
            final K o = oldKeys[i];
            if (o != TObjectIntCustomHashMap.FREE && o != TObjectIntCustomHashMap.REMOVED) {
                final int index = this.insertKey(o);
                if (index < 0) {
                    this.throwObjectContractViolation(this._set[-index - 1], o);
                }
                this._values[index] = oldVals[i];
            }
        }
    }
    
    public int getNoEntryValue() {
        return this.no_entry_value;
    }
    
    public boolean containsKey(final Object key) {
        return this.contains(key);
    }
    
    public boolean containsValue(final int val) {
        final Object[] keys = this._set;
        final int[] vals = this._values;
        int i = vals.length;
        while (i-- > 0) {
            if (keys[i] != TObjectIntCustomHashMap.FREE && keys[i] != TObjectIntCustomHashMap.REMOVED && val == vals[i]) {
                return true;
            }
        }
        return false;
    }
    
    public int get(final Object key) {
        final int index = this.index(key);
        return (index < 0) ? this.no_entry_value : this._values[index];
    }
    
    public int put(final K key, final int value) {
        final int index = this.insertKey(key);
        return this.doPut(value, index);
    }
    
    public int putIfAbsent(final K key, final int value) {
        final int index = this.insertKey(key);
        if (index < 0) {
            return this._values[-index - 1];
        }
        return this.doPut(value, index);
    }
    
    private int doPut(final int value, int index) {
        int previous = this.no_entry_value;
        boolean isNewMapping = true;
        if (index < 0) {
            index = -index - 1;
            previous = this._values[index];
            isNewMapping = false;
        }
        this._values[index] = value;
        if (isNewMapping) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return previous;
    }
    
    public int remove(final Object key) {
        int prev = this.no_entry_value;
        final int index = this.index(key);
        if (index >= 0) {
            prev = this._values[index];
            this.removeAt(index);
        }
        return prev;
    }
    
    protected void removeAt(final int index) {
        this._values[index] = this.no_entry_value;
        super.removeAt(index);
    }
    
    public void putAll(final Map<? extends K, ? extends Integer> map) {
        final Set<? extends Map.Entry<? extends K, ? extends Integer>> set = map.entrySet();
        for (final Map.Entry<? extends K, ? extends Integer> entry : set) {
            this.put(entry.getKey(), (int)entry.getValue());
        }
    }
    
    public void putAll(final TObjectIntMap<? extends K> map) {
        map.forEachEntry(this.PUT_ALL_PROC);
    }
    
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, TObjectIntCustomHashMap.FREE);
        Arrays.fill(this._values, 0, this._values.length, this.no_entry_value);
    }
    
    public Set<K> keySet() {
        return new KeyView();
    }
    
    public Object[] keys() {
        final K[] keys = (K[])new Object[this.size()];
        final Object[] k = this._set;
        int i = k.length;
        int j = 0;
        while (i-- > 0) {
            if (k[i] != TObjectIntCustomHashMap.FREE && k[i] != TObjectIntCustomHashMap.REMOVED) {
                keys[j++] = (K)k[i];
            }
        }
        return keys;
    }
    
    public K[] keys(K[] a) {
        final int size = this.size();
        if (a.length < size) {
            a = (K[])Array.newInstance(a.getClass().getComponentType(), size);
        }
        final Object[] k = this._set;
        int i = k.length;
        int j = 0;
        while (i-- > 0) {
            if (k[i] != TObjectIntCustomHashMap.FREE && k[i] != TObjectIntCustomHashMap.REMOVED) {
                a[j++] = (K)k[i];
            }
        }
        return a;
    }
    
    public TIntCollection valueCollection() {
        return new TIntValueCollection();
    }
    
    public int[] values() {
        final int[] vals = new int[this.size()];
        final int[] v = this._values;
        final Object[] keys = this._set;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (keys[i] != TObjectIntCustomHashMap.FREE && keys[i] != TObjectIntCustomHashMap.REMOVED) {
                vals[j++] = v[i];
            }
        }
        return vals;
    }
    
    public int[] values(int[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new int[size];
        }
        final int[] v = this._values;
        final Object[] keys = this._set;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (keys[i] != TObjectIntCustomHashMap.FREE && keys[i] != TObjectIntCustomHashMap.REMOVED) {
                array[j++] = v[i];
            }
        }
        if (array.length > size) {
            array[size] = this.no_entry_value;
        }
        return array;
    }
    
    public TObjectIntIterator<K> iterator() {
        return new TObjectIntHashIterator<K>(this);
    }
    
    public boolean increment(final K key) {
        return this.adjustValue(key, 1);
    }
    
    public boolean adjustValue(final K key, final int amount) {
        final int index = this.index(key);
        if (index < 0) {
            return false;
        }
        final int[] values = this._values;
        final int n = index;
        values[n] += amount;
        return true;
    }
    
    public int adjustOrPutValue(final K key, final int adjust_amount, final int put_amount) {
        int index = this.insertKey(key);
        int newValue;
        boolean isNewMapping;
        if (index < 0) {
            index = -index - 1;
            final int[] values = this._values;
            final int n = index;
            final int n2 = values[n] + adjust_amount;
            values[n] = n2;
            newValue = n2;
            isNewMapping = false;
        }
        else {
            this._values[index] = put_amount;
            newValue = put_amount;
            isNewMapping = true;
        }
        if (isNewMapping) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return newValue;
    }
    
    public boolean forEachKey(final TObjectProcedure<? super K> procedure) {
        return this.forEach(procedure);
    }
    
    public boolean forEachValue(final TIntProcedure procedure) {
        final Object[] keys = this._set;
        final int[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] != TObjectIntCustomHashMap.FREE && keys[i] != TObjectIntCustomHashMap.REMOVED && !procedure.execute(values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachEntry(final TObjectIntProcedure<? super K> procedure) {
        final Object[] keys = this._set;
        final int[] values = this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (keys[i] != TObjectIntCustomHashMap.FREE && keys[i] != TObjectIntCustomHashMap.REMOVED && !procedure.execute((Object)keys[i], values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean retainEntries(final TObjectIntProcedure<? super K> procedure) {
        boolean modified = false;
        final K[] keys = (K[])this._set;
        final int[] values = this._values;
        this.tempDisableAutoCompaction();
        try {
            int i = keys.length;
            while (i-- > 0) {
                if (keys[i] != TObjectIntCustomHashMap.FREE && keys[i] != TObjectIntCustomHashMap.REMOVED && !procedure.execute((Object)keys[i], values[i])) {
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
    
    public void transformValues(final TIntFunction function) {
        final Object[] keys = this._set;
        final int[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] != null && keys[i] != TObjectIntCustomHashMap.REMOVED) {
                values[i] = function.execute(values[i]);
            }
        }
    }
    
    public boolean equals(final Object other) {
        if (!(other instanceof TObjectIntMap)) {
            return false;
        }
        final TObjectIntMap that = (TObjectIntMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        try {
            final TObjectIntIterator iter = this.iterator();
            while (iter.hasNext()) {
                iter.advance();
                final Object key = iter.key();
                final int value = iter.value();
                if (value == this.no_entry_value) {
                    if (that.get(key) != that.getNoEntryValue() || !that.containsKey(key)) {
                        return false;
                    }
                    continue;
                }
                else {
                    if (value != that.get(key)) {
                        return false;
                    }
                    continue;
                }
            }
        }
        catch (ClassCastException ex) {}
        return true;
    }
    
    public int hashCode() {
        int hashcode = 0;
        final Object[] keys = this._set;
        final int[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] != TObjectIntCustomHashMap.FREE && keys[i] != TObjectIntCustomHashMap.REMOVED) {
                hashcode += (HashFunctions.hash(values[i]) ^ ((keys[i] == null) ? 0 : keys[i].hashCode()));
            }
        }
        return hashcode;
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeObject(this.strategy);
        out.writeInt(this.no_entry_value);
        out.writeInt(this._size);
        int i = this._set.length;
        while (i-- > 0) {
            if (this._set[i] != TObjectIntCustomHashMap.REMOVED && this._set[i] != TObjectIntCustomHashMap.FREE) {
                out.writeObject(this._set[i]);
                out.writeInt(this._values[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.strategy = (HashingStrategy<? super K>)in.readObject();
        this.no_entry_value = in.readInt();
        int size = in.readInt();
        this.setUp(size);
        while (size-- > 0) {
            final K key = (K)in.readObject();
            final int val = in.readInt();
            this.put(key, val);
        }
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.forEachEntry(new TObjectIntProcedure<K>() {
            private boolean first = true;
            
            public boolean execute(final K key, final int value) {
                if (this.first) {
                    this.first = false;
                }
                else {
                    buf.append(",");
                }
                buf.append(key).append("=").append(value);
                return true;
            }
        });
        buf.append("}");
        return buf.toString();
    }
    
    protected class KeyView extends MapBackedView<K>
    {
        public Iterator<K> iterator() {
            return new TObjectHashIterator<K>(TObjectIntCustomHashMap.this);
        }
        
        public boolean removeElement(final K key) {
            return TObjectIntCustomHashMap.this.no_entry_value != TObjectIntCustomHashMap.this.remove(key);
        }
        
        public boolean containsElement(final K key) {
            return TObjectIntCustomHashMap.this.contains(key);
        }
    }
    
    private abstract class MapBackedView<E> extends AbstractSet<E> implements Set<E>, Iterable<E>
    {
        public abstract boolean removeElement(final E p0);
        
        public abstract boolean containsElement(final E p0);
        
        public boolean contains(final Object key) {
            return this.containsElement(key);
        }
        
        public boolean remove(final Object o) {
            return this.removeElement(o);
        }
        
        public void clear() {
            TObjectIntCustomHashMap.this.clear();
        }
        
        public boolean add(final E obj) {
            throw new UnsupportedOperationException();
        }
        
        public int size() {
            return TObjectIntCustomHashMap.this.size();
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
            return TObjectIntCustomHashMap.this.isEmpty();
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
    }
    
    class TIntValueCollection implements TIntCollection
    {
        public TIntIterator iterator() {
            return new TObjectIntValueHashIterator();
        }
        
        public int getNoEntryValue() {
            return TObjectIntCustomHashMap.this.no_entry_value;
        }
        
        public int size() {
            return TObjectIntCustomHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TObjectIntCustomHashMap.this._size;
        }
        
        public boolean contains(final int entry) {
            return TObjectIntCustomHashMap.this.containsValue(entry);
        }
        
        public int[] toArray() {
            return TObjectIntCustomHashMap.this.values();
        }
        
        public int[] toArray(final int[] dest) {
            return TObjectIntCustomHashMap.this.values(dest);
        }
        
        public boolean add(final int entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final int entry) {
            final int[] values = TObjectIntCustomHashMap.this._values;
            final Object[] set = TObjectIntCustomHashMap.this._set;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] != TObjectHash.FREE && set[i] != TObjectHash.REMOVED && entry == values[i]) {
                    TObjectIntCustomHashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Integer)) {
                    return false;
                }
                final int ele = (int)element;
                if (!TObjectIntCustomHashMap.this.containsValue(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TIntCollection collection) {
            final TIntIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TObjectIntCustomHashMap.this.containsValue(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final int[] array) {
            for (final int element : array) {
                if (!TObjectIntCustomHashMap.this.containsValue(element)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean addAll(final Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final TIntCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final int[] array) {
            throw new UnsupportedOperationException();
        }
        
        public boolean retainAll(final Collection<?> collection) {
            boolean modified = false;
            final TIntIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final TIntCollection collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            final TIntIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final int[] array) {
            boolean changed = false;
            Arrays.sort(array);
            final int[] values = TObjectIntCustomHashMap.this._values;
            final Object[] set = TObjectIntCustomHashMap.this._set;
            int i = set.length;
            while (i-- > 0) {
                if (set[i] != TObjectHash.FREE && set[i] != TObjectHash.REMOVED && Arrays.binarySearch(array, values[i]) < 0) {
                    TObjectIntCustomHashMap.this.removeAt(i);
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final Collection<?> collection) {
            boolean changed = false;
            for (final Object element : collection) {
                if (element instanceof Integer) {
                    final int c = (int)element;
                    if (!this.remove(c)) {
                        continue;
                    }
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final TIntCollection collection) {
            if (this == collection) {
                this.clear();
                return true;
            }
            boolean changed = false;
            for (final int element : collection) {
                if (this.remove(element)) {
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final int[] array) {
            boolean changed = false;
            int i = array.length;
            while (i-- > 0) {
                if (this.remove(array[i])) {
                    changed = true;
                }
            }
            return changed;
        }
        
        public void clear() {
            TObjectIntCustomHashMap.this.clear();
        }
        
        public boolean forEach(final TIntProcedure procedure) {
            return TObjectIntCustomHashMap.this.forEachValue(procedure);
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TObjectIntCustomHashMap.this.forEachValue(new TIntProcedure() {
                private boolean first = true;
                
                public boolean execute(final int value) {
                    if (this.first) {
                        this.first = false;
                    }
                    else {
                        buf.append(", ");
                    }
                    buf.append(value);
                    return true;
                }
            });
            buf.append("}");
            return buf.toString();
        }
        
        class TObjectIntValueHashIterator implements TIntIterator
        {
            protected THash _hash;
            protected int _expectedSize;
            protected int _index;
            
            TObjectIntValueHashIterator() {
                this._hash = TObjectIntCustomHashMap.this;
                this._expectedSize = this._hash.size();
                this._index = this._hash.capacity();
            }
            
            public boolean hasNext() {
                return this.nextIndex() >= 0;
            }
            
            public int next() {
                this.moveToNextIndex();
                return TObjectIntCustomHashMap.this._values[this._index];
            }
            
            public void remove() {
                if (this._expectedSize != this._hash.size()) {
                    throw new ConcurrentModificationException();
                }
                try {
                    this._hash.tempDisableAutoCompaction();
                    TObjectIntCustomHashMap.this.removeAt(this._index);
                }
                finally {
                    this._hash.reenableAutoCompaction(false);
                }
                --this._expectedSize;
            }
            
            protected final void moveToNextIndex() {
                final int nextIndex = this.nextIndex();
                this._index = nextIndex;
                if (nextIndex < 0) {
                    throw new NoSuchElementException();
                }
            }
            
            protected final int nextIndex() {
                if (this._expectedSize != this._hash.size()) {
                    throw new ConcurrentModificationException();
                }
                final Object[] set = TObjectIntCustomHashMap.this._set;
                int i = this._index;
                while (i-- > 0) {
                    if (set[i] != TCustomObjectHash.FREE) {
                        if (set[i] == TCustomObjectHash.REMOVED) {
                            continue;
                        }
                        break;
                    }
                }
                return i;
            }
        }
    }
    
    class TObjectIntHashIterator<K> extends TObjectHashIterator<K> implements TObjectIntIterator<K>
    {
        private final TObjectIntCustomHashMap<K> _map;
        
        public TObjectIntHashIterator(final TObjectIntCustomHashMap<K> map) {
            super(map);
            this._map = map;
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public K key() {
            return (K)this._map._set[this._index];
        }
        
        public int value() {
            return this._map._values[this._index];
        }
        
        public int setValue(final int val) {
            final int old = this.value();
            this._map._values[this._index] = val;
            return old;
        }
    }
}
