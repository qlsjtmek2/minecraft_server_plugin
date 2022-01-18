// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.custom_hash;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import gnu.trove.impl.hash.THash;
import gnu.trove.iterator.TFloatIterator;
import java.util.Collection;
import java.util.AbstractSet;
import gnu.trove.impl.hash.TObjectHash;
import gnu.trove.iterator.hash.TObjectHashIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.function.TFloatFunction;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.TFloatCollection;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.Arrays;
import gnu.trove.impl.Constants;
import gnu.trove.strategy.HashingStrategy;
import gnu.trove.procedure.TObjectFloatProcedure;
import java.io.Externalizable;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.impl.hash.TCustomObjectHash;

public class TObjectFloatCustomHashMap<K> extends TCustomObjectHash<K> implements TObjectFloatMap<K>, Externalizable
{
    static final long serialVersionUID = 1L;
    private final TObjectFloatProcedure<K> PUT_ALL_PROC;
    protected transient float[] _values;
    protected float no_entry_value;
    
    public TObjectFloatCustomHashMap() {
        this.PUT_ALL_PROC = new TObjectFloatProcedure<K>() {
            public boolean execute(final K key, final float value) {
                TObjectFloatCustomHashMap.this.put(key, value);
                return true;
            }
        };
    }
    
    public TObjectFloatCustomHashMap(final HashingStrategy<? super K> strategy) {
        super(strategy);
        this.PUT_ALL_PROC = new TObjectFloatProcedure<K>() {
            public boolean execute(final K key, final float value) {
                TObjectFloatCustomHashMap.this.put(key, value);
                return true;
            }
        };
    }
    
    public TObjectFloatCustomHashMap(final HashingStrategy<? super K> strategy, final int initialCapacity) {
        super(strategy, initialCapacity);
        this.PUT_ALL_PROC = new TObjectFloatProcedure<K>() {
            public boolean execute(final K key, final float value) {
                TObjectFloatCustomHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_FLOAT_NO_ENTRY_VALUE;
    }
    
    public TObjectFloatCustomHashMap(final HashingStrategy<? super K> strategy, final int initialCapacity, final float loadFactor) {
        super(strategy, initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TObjectFloatProcedure<K>() {
            public boolean execute(final K key, final float value) {
                TObjectFloatCustomHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_FLOAT_NO_ENTRY_VALUE;
    }
    
    public TObjectFloatCustomHashMap(final HashingStrategy<? super K> strategy, final int initialCapacity, final float loadFactor, final float noEntryValue) {
        super(strategy, initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TObjectFloatProcedure<K>() {
            public boolean execute(final K key, final float value) {
                TObjectFloatCustomHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_value = noEntryValue;
        if (this.no_entry_value != 0.0f) {
            Arrays.fill(this._values, this.no_entry_value);
        }
    }
    
    public TObjectFloatCustomHashMap(final HashingStrategy<? super K> strategy, final TObjectFloatMap<? extends K> map) {
        this(strategy, map.size(), 0.5f, map.getNoEntryValue());
        if (map instanceof TObjectFloatCustomHashMap) {
            final TObjectFloatCustomHashMap hashmap = (TObjectFloatCustomHashMap)map;
            this._loadFactor = hashmap._loadFactor;
            this.no_entry_value = hashmap.no_entry_value;
            this.strategy = (HashingStrategy<? super K>)hashmap.strategy;
            if (this.no_entry_value != 0.0f) {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(map);
    }
    
    public int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._values = new float[capacity];
        return capacity;
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final K[] oldKeys = (K[])this._set;
        final float[] oldVals = this._values;
        Arrays.fill(this._set = new Object[newCapacity], TObjectFloatCustomHashMap.FREE);
        Arrays.fill(this._values = new float[newCapacity], this.no_entry_value);
        int i = oldCapacity;
        while (i-- > 0) {
            final K o = oldKeys[i];
            if (o != TObjectFloatCustomHashMap.FREE && o != TObjectFloatCustomHashMap.REMOVED) {
                final int index = this.insertKey(o);
                if (index < 0) {
                    this.throwObjectContractViolation(this._set[-index - 1], o);
                }
                this._values[index] = oldVals[i];
            }
        }
    }
    
    public float getNoEntryValue() {
        return this.no_entry_value;
    }
    
    public boolean containsKey(final Object key) {
        return this.contains(key);
    }
    
    public boolean containsValue(final float val) {
        final Object[] keys = this._set;
        final float[] vals = this._values;
        int i = vals.length;
        while (i-- > 0) {
            if (keys[i] != TObjectFloatCustomHashMap.FREE && keys[i] != TObjectFloatCustomHashMap.REMOVED && val == vals[i]) {
                return true;
            }
        }
        return false;
    }
    
    public float get(final Object key) {
        final int index = this.index(key);
        return (index < 0) ? this.no_entry_value : this._values[index];
    }
    
    public float put(final K key, final float value) {
        final int index = this.insertKey(key);
        return this.doPut(value, index);
    }
    
    public float putIfAbsent(final K key, final float value) {
        final int index = this.insertKey(key);
        if (index < 0) {
            return this._values[-index - 1];
        }
        return this.doPut(value, index);
    }
    
    private float doPut(final float value, int index) {
        float previous = this.no_entry_value;
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
    
    public float remove(final Object key) {
        float prev = this.no_entry_value;
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
    
    public void putAll(final Map<? extends K, ? extends Float> map) {
        final Set<? extends Map.Entry<? extends K, ? extends Float>> set = map.entrySet();
        for (final Map.Entry<? extends K, ? extends Float> entry : set) {
            this.put(entry.getKey(), (float)entry.getValue());
        }
    }
    
    public void putAll(final TObjectFloatMap<? extends K> map) {
        map.forEachEntry(this.PUT_ALL_PROC);
    }
    
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, TObjectFloatCustomHashMap.FREE);
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
            if (k[i] != TObjectFloatCustomHashMap.FREE && k[i] != TObjectFloatCustomHashMap.REMOVED) {
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
            if (k[i] != TObjectFloatCustomHashMap.FREE && k[i] != TObjectFloatCustomHashMap.REMOVED) {
                a[j++] = (K)k[i];
            }
        }
        return a;
    }
    
    public TFloatCollection valueCollection() {
        return new TFloatValueCollection();
    }
    
    public float[] values() {
        final float[] vals = new float[this.size()];
        final float[] v = this._values;
        final Object[] keys = this._set;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (keys[i] != TObjectFloatCustomHashMap.FREE && keys[i] != TObjectFloatCustomHashMap.REMOVED) {
                vals[j++] = v[i];
            }
        }
        return vals;
    }
    
    public float[] values(float[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new float[size];
        }
        final float[] v = this._values;
        final Object[] keys = this._set;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (keys[i] != TObjectFloatCustomHashMap.FREE && keys[i] != TObjectFloatCustomHashMap.REMOVED) {
                array[j++] = v[i];
            }
        }
        if (array.length > size) {
            array[size] = this.no_entry_value;
        }
        return array;
    }
    
    public TObjectFloatIterator<K> iterator() {
        return new TObjectFloatHashIterator<K>(this);
    }
    
    public boolean increment(final K key) {
        return this.adjustValue(key, 1.0f);
    }
    
    public boolean adjustValue(final K key, final float amount) {
        final int index = this.index(key);
        if (index < 0) {
            return false;
        }
        final float[] values = this._values;
        final int n = index;
        values[n] += amount;
        return true;
    }
    
    public float adjustOrPutValue(final K key, final float adjust_amount, final float put_amount) {
        int index = this.insertKey(key);
        float newValue;
        boolean isNewMapping;
        if (index < 0) {
            index = -index - 1;
            final float[] values = this._values;
            final int n = index;
            final float n2 = values[n] + adjust_amount;
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
    
    public boolean forEachValue(final TFloatProcedure procedure) {
        final Object[] keys = this._set;
        final float[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] != TObjectFloatCustomHashMap.FREE && keys[i] != TObjectFloatCustomHashMap.REMOVED && !procedure.execute(values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachEntry(final TObjectFloatProcedure<? super K> procedure) {
        final Object[] keys = this._set;
        final float[] values = this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (keys[i] != TObjectFloatCustomHashMap.FREE && keys[i] != TObjectFloatCustomHashMap.REMOVED && !procedure.execute((Object)keys[i], values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean retainEntries(final TObjectFloatProcedure<? super K> procedure) {
        boolean modified = false;
        final K[] keys = (K[])this._set;
        final float[] values = this._values;
        this.tempDisableAutoCompaction();
        try {
            int i = keys.length;
            while (i-- > 0) {
                if (keys[i] != TObjectFloatCustomHashMap.FREE && keys[i] != TObjectFloatCustomHashMap.REMOVED && !procedure.execute((Object)keys[i], values[i])) {
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
    
    public void transformValues(final TFloatFunction function) {
        final Object[] keys = this._set;
        final float[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] != null && keys[i] != TObjectFloatCustomHashMap.REMOVED) {
                values[i] = function.execute(values[i]);
            }
        }
    }
    
    public boolean equals(final Object other) {
        if (!(other instanceof TObjectFloatMap)) {
            return false;
        }
        final TObjectFloatMap that = (TObjectFloatMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        try {
            final TObjectFloatIterator iter = this.iterator();
            while (iter.hasNext()) {
                iter.advance();
                final Object key = iter.key();
                final float value = iter.value();
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
        final float[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] != TObjectFloatCustomHashMap.FREE && keys[i] != TObjectFloatCustomHashMap.REMOVED) {
                hashcode += (HashFunctions.hash(values[i]) ^ ((keys[i] == null) ? 0 : keys[i].hashCode()));
            }
        }
        return hashcode;
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeObject(this.strategy);
        out.writeFloat(this.no_entry_value);
        out.writeInt(this._size);
        int i = this._set.length;
        while (i-- > 0) {
            if (this._set[i] != TObjectFloatCustomHashMap.REMOVED && this._set[i] != TObjectFloatCustomHashMap.FREE) {
                out.writeObject(this._set[i]);
                out.writeFloat(this._values[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.strategy = (HashingStrategy<? super K>)in.readObject();
        this.no_entry_value = in.readFloat();
        int size = in.readInt();
        this.setUp(size);
        while (size-- > 0) {
            final K key = (K)in.readObject();
            final float val = in.readFloat();
            this.put(key, val);
        }
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.forEachEntry(new TObjectFloatProcedure<K>() {
            private boolean first = true;
            
            public boolean execute(final K key, final float value) {
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
            return new TObjectHashIterator<K>(TObjectFloatCustomHashMap.this);
        }
        
        public boolean removeElement(final K key) {
            return TObjectFloatCustomHashMap.this.no_entry_value != TObjectFloatCustomHashMap.this.remove(key);
        }
        
        public boolean containsElement(final K key) {
            return TObjectFloatCustomHashMap.this.contains(key);
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
            TObjectFloatCustomHashMap.this.clear();
        }
        
        public boolean add(final E obj) {
            throw new UnsupportedOperationException();
        }
        
        public int size() {
            return TObjectFloatCustomHashMap.this.size();
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
            return TObjectFloatCustomHashMap.this.isEmpty();
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
    
    class TFloatValueCollection implements TFloatCollection
    {
        public TFloatIterator iterator() {
            return new TObjectFloatValueHashIterator();
        }
        
        public float getNoEntryValue() {
            return TObjectFloatCustomHashMap.this.no_entry_value;
        }
        
        public int size() {
            return TObjectFloatCustomHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TObjectFloatCustomHashMap.this._size;
        }
        
        public boolean contains(final float entry) {
            return TObjectFloatCustomHashMap.this.containsValue(entry);
        }
        
        public float[] toArray() {
            return TObjectFloatCustomHashMap.this.values();
        }
        
        public float[] toArray(final float[] dest) {
            return TObjectFloatCustomHashMap.this.values(dest);
        }
        
        public boolean add(final float entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final float entry) {
            final float[] values = TObjectFloatCustomHashMap.this._values;
            final Object[] set = TObjectFloatCustomHashMap.this._set;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] != TObjectHash.FREE && set[i] != TObjectHash.REMOVED && entry == values[i]) {
                    TObjectFloatCustomHashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Float)) {
                    return false;
                }
                final float ele = (float)element;
                if (!TObjectFloatCustomHashMap.this.containsValue(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TFloatCollection collection) {
            final TFloatIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TObjectFloatCustomHashMap.this.containsValue(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final float[] array) {
            for (final float element : array) {
                if (!TObjectFloatCustomHashMap.this.containsValue(element)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean addAll(final Collection<? extends Float> collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final TFloatCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final float[] array) {
            throw new UnsupportedOperationException();
        }
        
        public boolean retainAll(final Collection<?> collection) {
            boolean modified = false;
            final TFloatIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final TFloatCollection collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            final TFloatIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final float[] array) {
            boolean changed = false;
            Arrays.sort(array);
            final float[] values = TObjectFloatCustomHashMap.this._values;
            final Object[] set = TObjectFloatCustomHashMap.this._set;
            int i = set.length;
            while (i-- > 0) {
                if (set[i] != TObjectHash.FREE && set[i] != TObjectHash.REMOVED && Arrays.binarySearch(array, values[i]) < 0) {
                    TObjectFloatCustomHashMap.this.removeAt(i);
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final Collection<?> collection) {
            boolean changed = false;
            for (final Object element : collection) {
                if (element instanceof Float) {
                    final float c = (float)element;
                    if (!this.remove(c)) {
                        continue;
                    }
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final TFloatCollection collection) {
            if (this == collection) {
                this.clear();
                return true;
            }
            boolean changed = false;
            for (final float element : collection) {
                if (this.remove(element)) {
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final float[] array) {
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
            TObjectFloatCustomHashMap.this.clear();
        }
        
        public boolean forEach(final TFloatProcedure procedure) {
            return TObjectFloatCustomHashMap.this.forEachValue(procedure);
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TObjectFloatCustomHashMap.this.forEachValue(new TFloatProcedure() {
                private boolean first = true;
                
                public boolean execute(final float value) {
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
        
        class TObjectFloatValueHashIterator implements TFloatIterator
        {
            protected THash _hash;
            protected int _expectedSize;
            protected int _index;
            
            TObjectFloatValueHashIterator() {
                this._hash = TObjectFloatCustomHashMap.this;
                this._expectedSize = this._hash.size();
                this._index = this._hash.capacity();
            }
            
            public boolean hasNext() {
                return this.nextIndex() >= 0;
            }
            
            public float next() {
                this.moveToNextIndex();
                return TObjectFloatCustomHashMap.this._values[this._index];
            }
            
            public void remove() {
                if (this._expectedSize != this._hash.size()) {
                    throw new ConcurrentModificationException();
                }
                try {
                    this._hash.tempDisableAutoCompaction();
                    TObjectFloatCustomHashMap.this.removeAt(this._index);
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
                final Object[] set = TObjectFloatCustomHashMap.this._set;
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
    
    class TObjectFloatHashIterator<K> extends TObjectHashIterator<K> implements TObjectFloatIterator<K>
    {
        private final TObjectFloatCustomHashMap<K> _map;
        
        public TObjectFloatHashIterator(final TObjectFloatCustomHashMap<K> map) {
            super(map);
            this._map = map;
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public K key() {
            return (K)this._map._set[this._index];
        }
        
        public float value() {
            return this._map._values[this._index];
        }
        
        public float setValue(final float val) {
            final float old = this.value();
            this._map._values[this._index] = val;
            return old;
        }
    }
}
