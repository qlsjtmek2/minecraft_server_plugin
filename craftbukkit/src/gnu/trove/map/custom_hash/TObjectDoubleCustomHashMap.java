// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.custom_hash;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import gnu.trove.impl.hash.THash;
import gnu.trove.iterator.TDoubleIterator;
import java.util.Collection;
import java.util.AbstractSet;
import gnu.trove.impl.hash.TObjectHash;
import gnu.trove.iterator.hash.TObjectHashIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.function.TDoubleFunction;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.iterator.TObjectDoubleIterator;
import gnu.trove.TDoubleCollection;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.Arrays;
import gnu.trove.impl.Constants;
import gnu.trove.strategy.HashingStrategy;
import gnu.trove.procedure.TObjectDoubleProcedure;
import java.io.Externalizable;
import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.impl.hash.TCustomObjectHash;

public class TObjectDoubleCustomHashMap<K> extends TCustomObjectHash<K> implements TObjectDoubleMap<K>, Externalizable
{
    static final long serialVersionUID = 1L;
    private final TObjectDoubleProcedure<K> PUT_ALL_PROC;
    protected transient double[] _values;
    protected double no_entry_value;
    
    public TObjectDoubleCustomHashMap() {
        this.PUT_ALL_PROC = new TObjectDoubleProcedure<K>() {
            public boolean execute(final K key, final double value) {
                TObjectDoubleCustomHashMap.this.put(key, value);
                return true;
            }
        };
    }
    
    public TObjectDoubleCustomHashMap(final HashingStrategy<? super K> strategy) {
        super(strategy);
        this.PUT_ALL_PROC = new TObjectDoubleProcedure<K>() {
            public boolean execute(final K key, final double value) {
                TObjectDoubleCustomHashMap.this.put(key, value);
                return true;
            }
        };
    }
    
    public TObjectDoubleCustomHashMap(final HashingStrategy<? super K> strategy, final int initialCapacity) {
        super(strategy, initialCapacity);
        this.PUT_ALL_PROC = new TObjectDoubleProcedure<K>() {
            public boolean execute(final K key, final double value) {
                TObjectDoubleCustomHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
    }
    
    public TObjectDoubleCustomHashMap(final HashingStrategy<? super K> strategy, final int initialCapacity, final float loadFactor) {
        super(strategy, initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TObjectDoubleProcedure<K>() {
            public boolean execute(final K key, final double value) {
                TObjectDoubleCustomHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
    }
    
    public TObjectDoubleCustomHashMap(final HashingStrategy<? super K> strategy, final int initialCapacity, final float loadFactor, final double noEntryValue) {
        super(strategy, initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TObjectDoubleProcedure<K>() {
            public boolean execute(final K key, final double value) {
                TObjectDoubleCustomHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_value = noEntryValue;
        if (this.no_entry_value != 0.0) {
            Arrays.fill(this._values, this.no_entry_value);
        }
    }
    
    public TObjectDoubleCustomHashMap(final HashingStrategy<? super K> strategy, final TObjectDoubleMap<? extends K> map) {
        this(strategy, map.size(), 0.5f, map.getNoEntryValue());
        if (map instanceof TObjectDoubleCustomHashMap) {
            final TObjectDoubleCustomHashMap hashmap = (TObjectDoubleCustomHashMap)map;
            this._loadFactor = hashmap._loadFactor;
            this.no_entry_value = hashmap.no_entry_value;
            this.strategy = (HashingStrategy<? super K>)hashmap.strategy;
            if (this.no_entry_value != 0.0) {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(map);
    }
    
    public int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._values = new double[capacity];
        return capacity;
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final K[] oldKeys = (K[])this._set;
        final double[] oldVals = this._values;
        Arrays.fill(this._set = new Object[newCapacity], TObjectDoubleCustomHashMap.FREE);
        Arrays.fill(this._values = new double[newCapacity], this.no_entry_value);
        int i = oldCapacity;
        while (i-- > 0) {
            final K o = oldKeys[i];
            if (o != TObjectDoubleCustomHashMap.FREE && o != TObjectDoubleCustomHashMap.REMOVED) {
                final int index = this.insertKey(o);
                if (index < 0) {
                    this.throwObjectContractViolation(this._set[-index - 1], o);
                }
                this._values[index] = oldVals[i];
            }
        }
    }
    
    public double getNoEntryValue() {
        return this.no_entry_value;
    }
    
    public boolean containsKey(final Object key) {
        return this.contains(key);
    }
    
    public boolean containsValue(final double val) {
        final Object[] keys = this._set;
        final double[] vals = this._values;
        int i = vals.length;
        while (i-- > 0) {
            if (keys[i] != TObjectDoubleCustomHashMap.FREE && keys[i] != TObjectDoubleCustomHashMap.REMOVED && val == vals[i]) {
                return true;
            }
        }
        return false;
    }
    
    public double get(final Object key) {
        final int index = this.index(key);
        return (index < 0) ? this.no_entry_value : this._values[index];
    }
    
    public double put(final K key, final double value) {
        final int index = this.insertKey(key);
        return this.doPut(value, index);
    }
    
    public double putIfAbsent(final K key, final double value) {
        final int index = this.insertKey(key);
        if (index < 0) {
            return this._values[-index - 1];
        }
        return this.doPut(value, index);
    }
    
    private double doPut(final double value, int index) {
        double previous = this.no_entry_value;
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
    
    public double remove(final Object key) {
        double prev = this.no_entry_value;
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
    
    public void putAll(final Map<? extends K, ? extends Double> map) {
        final Set<? extends Map.Entry<? extends K, ? extends Double>> set = map.entrySet();
        for (final Map.Entry<? extends K, ? extends Double> entry : set) {
            this.put(entry.getKey(), (double)entry.getValue());
        }
    }
    
    public void putAll(final TObjectDoubleMap<? extends K> map) {
        map.forEachEntry(this.PUT_ALL_PROC);
    }
    
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, TObjectDoubleCustomHashMap.FREE);
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
            if (k[i] != TObjectDoubleCustomHashMap.FREE && k[i] != TObjectDoubleCustomHashMap.REMOVED) {
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
            if (k[i] != TObjectDoubleCustomHashMap.FREE && k[i] != TObjectDoubleCustomHashMap.REMOVED) {
                a[j++] = (K)k[i];
            }
        }
        return a;
    }
    
    public TDoubleCollection valueCollection() {
        return new TDoubleValueCollection();
    }
    
    public double[] values() {
        final double[] vals = new double[this.size()];
        final double[] v = this._values;
        final Object[] keys = this._set;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (keys[i] != TObjectDoubleCustomHashMap.FREE && keys[i] != TObjectDoubleCustomHashMap.REMOVED) {
                vals[j++] = v[i];
            }
        }
        return vals;
    }
    
    public double[] values(double[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new double[size];
        }
        final double[] v = this._values;
        final Object[] keys = this._set;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (keys[i] != TObjectDoubleCustomHashMap.FREE && keys[i] != TObjectDoubleCustomHashMap.REMOVED) {
                array[j++] = v[i];
            }
        }
        if (array.length > size) {
            array[size] = this.no_entry_value;
        }
        return array;
    }
    
    public TObjectDoubleIterator<K> iterator() {
        return new TObjectDoubleHashIterator<K>(this);
    }
    
    public boolean increment(final K key) {
        return this.adjustValue(key, 1.0);
    }
    
    public boolean adjustValue(final K key, final double amount) {
        final int index = this.index(key);
        if (index < 0) {
            return false;
        }
        final double[] values = this._values;
        final int n = index;
        values[n] += amount;
        return true;
    }
    
    public double adjustOrPutValue(final K key, final double adjust_amount, final double put_amount) {
        int index = this.insertKey(key);
        double newValue;
        boolean isNewMapping;
        if (index < 0) {
            index = -index - 1;
            final double[] values = this._values;
            final int n = index;
            final double n2 = values[n] + adjust_amount;
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
    
    public boolean forEachValue(final TDoubleProcedure procedure) {
        final Object[] keys = this._set;
        final double[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] != TObjectDoubleCustomHashMap.FREE && keys[i] != TObjectDoubleCustomHashMap.REMOVED && !procedure.execute(values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachEntry(final TObjectDoubleProcedure<? super K> procedure) {
        final Object[] keys = this._set;
        final double[] values = this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (keys[i] != TObjectDoubleCustomHashMap.FREE && keys[i] != TObjectDoubleCustomHashMap.REMOVED && !procedure.execute((Object)keys[i], values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean retainEntries(final TObjectDoubleProcedure<? super K> procedure) {
        boolean modified = false;
        final K[] keys = (K[])this._set;
        final double[] values = this._values;
        this.tempDisableAutoCompaction();
        try {
            int i = keys.length;
            while (i-- > 0) {
                if (keys[i] != TObjectDoubleCustomHashMap.FREE && keys[i] != TObjectDoubleCustomHashMap.REMOVED && !procedure.execute((Object)keys[i], values[i])) {
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
    
    public void transformValues(final TDoubleFunction function) {
        final Object[] keys = this._set;
        final double[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] != null && keys[i] != TObjectDoubleCustomHashMap.REMOVED) {
                values[i] = function.execute(values[i]);
            }
        }
    }
    
    public boolean equals(final Object other) {
        if (!(other instanceof TObjectDoubleMap)) {
            return false;
        }
        final TObjectDoubleMap that = (TObjectDoubleMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        try {
            final TObjectDoubleIterator iter = this.iterator();
            while (iter.hasNext()) {
                iter.advance();
                final Object key = iter.key();
                final double value = iter.value();
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
        final double[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] != TObjectDoubleCustomHashMap.FREE && keys[i] != TObjectDoubleCustomHashMap.REMOVED) {
                hashcode += (HashFunctions.hash(values[i]) ^ ((keys[i] == null) ? 0 : keys[i].hashCode()));
            }
        }
        return hashcode;
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeObject(this.strategy);
        out.writeDouble(this.no_entry_value);
        out.writeInt(this._size);
        int i = this._set.length;
        while (i-- > 0) {
            if (this._set[i] != TObjectDoubleCustomHashMap.REMOVED && this._set[i] != TObjectDoubleCustomHashMap.FREE) {
                out.writeObject(this._set[i]);
                out.writeDouble(this._values[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.strategy = (HashingStrategy<? super K>)in.readObject();
        this.no_entry_value = in.readDouble();
        int size = in.readInt();
        this.setUp(size);
        while (size-- > 0) {
            final K key = (K)in.readObject();
            final double val = in.readDouble();
            this.put(key, val);
        }
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.forEachEntry(new TObjectDoubleProcedure<K>() {
            private boolean first = true;
            
            public boolean execute(final K key, final double value) {
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
            return new TObjectHashIterator<K>(TObjectDoubleCustomHashMap.this);
        }
        
        public boolean removeElement(final K key) {
            return TObjectDoubleCustomHashMap.this.no_entry_value != TObjectDoubleCustomHashMap.this.remove(key);
        }
        
        public boolean containsElement(final K key) {
            return TObjectDoubleCustomHashMap.this.contains(key);
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
            TObjectDoubleCustomHashMap.this.clear();
        }
        
        public boolean add(final E obj) {
            throw new UnsupportedOperationException();
        }
        
        public int size() {
            return TObjectDoubleCustomHashMap.this.size();
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
            return TObjectDoubleCustomHashMap.this.isEmpty();
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
    
    class TDoubleValueCollection implements TDoubleCollection
    {
        public TDoubleIterator iterator() {
            return new TObjectDoubleValueHashIterator();
        }
        
        public double getNoEntryValue() {
            return TObjectDoubleCustomHashMap.this.no_entry_value;
        }
        
        public int size() {
            return TObjectDoubleCustomHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TObjectDoubleCustomHashMap.this._size;
        }
        
        public boolean contains(final double entry) {
            return TObjectDoubleCustomHashMap.this.containsValue(entry);
        }
        
        public double[] toArray() {
            return TObjectDoubleCustomHashMap.this.values();
        }
        
        public double[] toArray(final double[] dest) {
            return TObjectDoubleCustomHashMap.this.values(dest);
        }
        
        public boolean add(final double entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final double entry) {
            final double[] values = TObjectDoubleCustomHashMap.this._values;
            final Object[] set = TObjectDoubleCustomHashMap.this._set;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] != TObjectHash.FREE && set[i] != TObjectHash.REMOVED && entry == values[i]) {
                    TObjectDoubleCustomHashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Double)) {
                    return false;
                }
                final double ele = (double)element;
                if (!TObjectDoubleCustomHashMap.this.containsValue(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TDoubleCollection collection) {
            final TDoubleIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TObjectDoubleCustomHashMap.this.containsValue(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final double[] array) {
            for (final double element : array) {
                if (!TObjectDoubleCustomHashMap.this.containsValue(element)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean addAll(final Collection<? extends Double> collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final TDoubleCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final double[] array) {
            throw new UnsupportedOperationException();
        }
        
        public boolean retainAll(final Collection<?> collection) {
            boolean modified = false;
            final TDoubleIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final TDoubleCollection collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            final TDoubleIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final double[] array) {
            boolean changed = false;
            Arrays.sort(array);
            final double[] values = TObjectDoubleCustomHashMap.this._values;
            final Object[] set = TObjectDoubleCustomHashMap.this._set;
            int i = set.length;
            while (i-- > 0) {
                if (set[i] != TObjectHash.FREE && set[i] != TObjectHash.REMOVED && Arrays.binarySearch(array, values[i]) < 0) {
                    TObjectDoubleCustomHashMap.this.removeAt(i);
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final Collection<?> collection) {
            boolean changed = false;
            for (final Object element : collection) {
                if (element instanceof Double) {
                    final double c = (double)element;
                    if (!this.remove(c)) {
                        continue;
                    }
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final TDoubleCollection collection) {
            if (this == collection) {
                this.clear();
                return true;
            }
            boolean changed = false;
            for (final double element : collection) {
                if (this.remove(element)) {
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final double[] array) {
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
            TObjectDoubleCustomHashMap.this.clear();
        }
        
        public boolean forEach(final TDoubleProcedure procedure) {
            return TObjectDoubleCustomHashMap.this.forEachValue(procedure);
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TObjectDoubleCustomHashMap.this.forEachValue(new TDoubleProcedure() {
                private boolean first = true;
                
                public boolean execute(final double value) {
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
        
        class TObjectDoubleValueHashIterator implements TDoubleIterator
        {
            protected THash _hash;
            protected int _expectedSize;
            protected int _index;
            
            TObjectDoubleValueHashIterator() {
                this._hash = TObjectDoubleCustomHashMap.this;
                this._expectedSize = this._hash.size();
                this._index = this._hash.capacity();
            }
            
            public boolean hasNext() {
                return this.nextIndex() >= 0;
            }
            
            public double next() {
                this.moveToNextIndex();
                return TObjectDoubleCustomHashMap.this._values[this._index];
            }
            
            public void remove() {
                if (this._expectedSize != this._hash.size()) {
                    throw new ConcurrentModificationException();
                }
                try {
                    this._hash.tempDisableAutoCompaction();
                    TObjectDoubleCustomHashMap.this.removeAt(this._index);
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
                final Object[] set = TObjectDoubleCustomHashMap.this._set;
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
    
    class TObjectDoubleHashIterator<K> extends TObjectHashIterator<K> implements TObjectDoubleIterator<K>
    {
        private final TObjectDoubleCustomHashMap<K> _map;
        
        public TObjectDoubleHashIterator(final TObjectDoubleCustomHashMap<K> map) {
            super(map);
            this._map = map;
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public K key() {
            return (K)this._map._set[this._index];
        }
        
        public double value() {
            return this._map._values[this._index];
        }
        
        public double setValue(final double val) {
            final double old = this.value();
            this._map._values[this._index] = val;
            return old;
        }
    }
}
