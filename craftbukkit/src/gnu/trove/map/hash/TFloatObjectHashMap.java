// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.hash;

import java.util.AbstractSet;
import gnu.trove.impl.hash.TPrimitiveHash;
import gnu.trove.impl.hash.THashPrimitiveIterator;
import gnu.trove.TFloatCollection;
import gnu.trove.iterator.TFloatIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.iterator.TFloatObjectIterator;
import java.lang.reflect.Array;
import java.util.Collection;
import gnu.trove.set.TFloatSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import gnu.trove.impl.Constants;
import gnu.trove.procedure.TFloatObjectProcedure;
import java.io.Externalizable;
import gnu.trove.map.TFloatObjectMap;
import gnu.trove.impl.hash.TFloatHash;

public class TFloatObjectHashMap<V> extends TFloatHash implements TFloatObjectMap<V>, Externalizable
{
    static final long serialVersionUID = 1L;
    private final TFloatObjectProcedure<V> PUT_ALL_PROC;
    protected transient V[] _values;
    protected float no_entry_key;
    
    public TFloatObjectHashMap() {
        this.PUT_ALL_PROC = new TFloatObjectProcedure<V>() {
            public boolean execute(final float key, final V value) {
                TFloatObjectHashMap.this.put(key, value);
                return true;
            }
        };
    }
    
    public TFloatObjectHashMap(final int initialCapacity) {
        super(initialCapacity);
        this.PUT_ALL_PROC = new TFloatObjectProcedure<V>() {
            public boolean execute(final float key, final V value) {
                TFloatObjectHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_key = Constants.DEFAULT_FLOAT_NO_ENTRY_VALUE;
    }
    
    public TFloatObjectHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TFloatObjectProcedure<V>() {
            public boolean execute(final float key, final V value) {
                TFloatObjectHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_key = Constants.DEFAULT_FLOAT_NO_ENTRY_VALUE;
    }
    
    public TFloatObjectHashMap(final int initialCapacity, final float loadFactor, final float noEntryKey) {
        super(initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TFloatObjectProcedure<V>() {
            public boolean execute(final float key, final V value) {
                TFloatObjectHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_key = noEntryKey;
    }
    
    public TFloatObjectHashMap(final TFloatObjectMap<? extends V> map) {
        this(map.size(), 0.5f, map.getNoEntryKey());
        this.putAll(map);
    }
    
    protected int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._values = new Object[capacity];
        return capacity;
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final float[] oldKeys = this._set;
        final V[] oldVals = (V[])this._values;
        final byte[] oldStates = this._states;
        this._set = new float[newCapacity];
        this._values = new Object[newCapacity];
        this._states = new byte[newCapacity];
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldStates[i] == 1) {
                final float o = oldKeys[i];
                final int index = this.insertKey(o);
                this._values[index] = oldVals[i];
            }
        }
    }
    
    public float getNoEntryKey() {
        return this.no_entry_key;
    }
    
    public boolean containsKey(final float key) {
        return this.contains(key);
    }
    
    public boolean containsValue(final Object val) {
        final byte[] states = this._states;
        final V[] vals = (V[])this._values;
        if (null == val) {
            int i = vals.length;
            while (i-- > 0) {
                if (states[i] == 1 && null == vals[i]) {
                    return true;
                }
            }
        }
        else {
            int i = vals.length;
            while (i-- > 0) {
                if (states[i] == 1 && (val == vals[i] || val.equals(vals[i]))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public V get(final float key) {
        final int index = this.index(key);
        return (V)((index < 0) ? null : this._values[index]);
    }
    
    public V put(final float key, final V value) {
        final int index = this.insertKey(key);
        return this.doPut(value, index);
    }
    
    public V putIfAbsent(final float key, final V value) {
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
    
    public V remove(final float key) {
        V prev = null;
        final int index = this.index(key);
        if (index >= 0) {
            prev = (V)this._values[index];
            this.removeAt(index);
        }
        return prev;
    }
    
    protected void removeAt(final int index) {
        this._values[index] = null;
        super.removeAt(index);
    }
    
    public void putAll(final Map<? extends Float, ? extends V> map) {
        final Set<? extends Map.Entry<? extends Float, ? extends V>> set = map.entrySet();
        for (final Map.Entry<? extends Float, ? extends V> entry : set) {
            this.put((float)entry.getKey(), entry.getValue());
        }
    }
    
    public void putAll(final TFloatObjectMap<? extends V> map) {
        map.forEachEntry(this.PUT_ALL_PROC);
    }
    
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, this.no_entry_key);
        Arrays.fill(this._states, 0, this._states.length, (byte)0);
        Arrays.fill(this._values, 0, this._values.length, null);
    }
    
    public TFloatSet keySet() {
        return new KeyView();
    }
    
    public float[] keys() {
        final float[] keys = new float[this.size()];
        final float[] k = this._set;
        final byte[] states = this._states;
        int i = k.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                keys[j++] = k[i];
            }
        }
        return keys;
    }
    
    public float[] keys(float[] dest) {
        if (dest.length < this._size) {
            dest = new float[this._size];
        }
        final float[] k = this._set;
        final byte[] states = this._states;
        int i = k.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                dest[j++] = k[i];
            }
        }
        return dest;
    }
    
    public Collection<V> valueCollection() {
        return new ValueView();
    }
    
    public V[] values() {
        final V[] vals = (V[])new Object[this.size()];
        final V[] v = (V[])this._values;
        final byte[] states = this._states;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                vals[j++] = v[i];
            }
        }
        return vals;
    }
    
    public V[] values(V[] dest) {
        if (dest.length < this._size) {
            dest = (V[])Array.newInstance(dest.getClass().getComponentType(), this._size);
        }
        final V[] v = (V[])this._values;
        final byte[] states = this._states;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                dest[j++] = v[i];
            }
        }
        return dest;
    }
    
    public TFloatObjectIterator<V> iterator() {
        return new TFloatObjectHashIterator<V>(this);
    }
    
    public boolean forEachKey(final TFloatProcedure procedure) {
        return this.forEach(procedure);
    }
    
    public boolean forEachValue(final TObjectProcedure<? super V> procedure) {
        final byte[] states = this._states;
        final V[] values = (V[])this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute((Object)values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachEntry(final TFloatObjectProcedure<? super V> procedure) {
        final byte[] states = this._states;
        final float[] keys = this._set;
        final V[] values = (V[])this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(keys[i], (Object)values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean retainEntries(final TFloatObjectProcedure<? super V> procedure) {
        boolean modified = false;
        final byte[] states = this._states;
        final float[] keys = this._set;
        final V[] values = (V[])this._values;
        this.tempDisableAutoCompaction();
        try {
            int i = keys.length;
            while (i-- > 0) {
                if (states[i] == 1 && !procedure.execute(keys[i], (Object)values[i])) {
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
        final byte[] states = this._states;
        final V[] values = (V[])this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                values[i] = function.execute(values[i]);
            }
        }
    }
    
    public boolean equals(final Object other) {
        if (!(other instanceof TFloatObjectMap)) {
            return false;
        }
        final TFloatObjectMap that = (TFloatObjectMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        try {
            final TFloatObjectIterator iter = this.iterator();
            while (iter.hasNext()) {
                iter.advance();
                final float key = iter.key();
                final Object value = iter.value();
                if (value == null) {
                    if (that.get(key) != null || !that.containsKey(key)) {
                        return false;
                    }
                    continue;
                }
                else {
                    if (!value.equals(that.get(key))) {
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
        final V[] values = (V[])this._values;
        final byte[] states = this._states;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                hashcode += (HashFunctions.hash(this._set[i]) ^ ((values[i] == null) ? 0 : values[i].hashCode()));
            }
        }
        return hashcode;
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeFloat(this.no_entry_key);
        out.writeInt(this._size);
        int i = this._states.length;
        while (i-- > 0) {
            if (this._states[i] == 1) {
                out.writeFloat(this._set[i]);
                out.writeObject(this._values[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.no_entry_key = in.readFloat();
        int size = in.readInt();
        this.setUp(size);
        while (size-- > 0) {
            final float key = in.readFloat();
            final V val = (V)in.readObject();
            this.put(key, val);
        }
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.forEachEntry(new TFloatObjectProcedure<V>() {
            private boolean first = true;
            
            public boolean execute(final float key, final Object value) {
                if (this.first) {
                    this.first = false;
                }
                else {
                    buf.append(",");
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
    
    class KeyView implements TFloatSet
    {
        public float getNoEntryValue() {
            return TFloatObjectHashMap.this.no_entry_key;
        }
        
        public int size() {
            return TFloatObjectHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return TFloatObjectHashMap.this._size == 0;
        }
        
        public boolean contains(final float entry) {
            return TFloatObjectHashMap.this.containsKey(entry);
        }
        
        public TFloatIterator iterator() {
            return new TFloatHashIterator(TFloatObjectHashMap.this);
        }
        
        public float[] toArray() {
            return TFloatObjectHashMap.this.keys();
        }
        
        public float[] toArray(final float[] dest) {
            return TFloatObjectHashMap.this.keys(dest);
        }
        
        public boolean add(final float entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final float entry) {
            return null != TFloatObjectHashMap.this.remove(entry);
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!TFloatObjectHashMap.this.containsKey((float)element)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TFloatCollection collection) {
            if (collection == this) {
                return true;
            }
            final TFloatIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TFloatObjectHashMap.this.containsKey(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final float[] array) {
            for (final float element : array) {
                if (!TFloatObjectHashMap.this.containsKey(element)) {
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
            final float[] set = TFloatObjectHashMap.this._set;
            final byte[] states = TFloatObjectHashMap.this._states;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    TFloatObjectHashMap.this.removeAt(i);
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
            if (collection == this) {
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
            TFloatObjectHashMap.this.clear();
        }
        
        public boolean forEach(final TFloatProcedure procedure) {
            return TFloatObjectHashMap.this.forEachKey(procedure);
        }
        
        public boolean equals(final Object other) {
            if (!(other instanceof TFloatSet)) {
                return false;
            }
            final TFloatSet that = (TFloatSet)other;
            if (that.size() != this.size()) {
                return false;
            }
            int i = TFloatObjectHashMap.this._states.length;
            while (i-- > 0) {
                if (TFloatObjectHashMap.this._states[i] == 1 && !that.contains(TFloatObjectHashMap.this._set[i])) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hashcode = 0;
            int i = TFloatObjectHashMap.this._states.length;
            while (i-- > 0) {
                if (TFloatObjectHashMap.this._states[i] == 1) {
                    hashcode += HashFunctions.hash(TFloatObjectHashMap.this._set[i]);
                }
            }
            return hashcode;
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            boolean first = true;
            int i = TFloatObjectHashMap.this._states.length;
            while (i-- > 0) {
                if (TFloatObjectHashMap.this._states[i] == 1) {
                    if (first) {
                        first = false;
                    }
                    else {
                        buf.append(",");
                    }
                    buf.append(TFloatObjectHashMap.this._set[i]);
                }
            }
            return buf.toString();
        }
        
        class TFloatHashIterator extends THashPrimitiveIterator implements TFloatIterator
        {
            private final TFloatHash _hash;
            
            public TFloatHashIterator(final TFloatHash hash) {
                super(hash);
                this._hash = hash;
            }
            
            public float next() {
                this.moveToNextIndex();
                return this._hash._set[this._index];
            }
        }
    }
    
    protected class ValueView extends MapBackedView<V>
    {
        public Iterator<V> iterator() {
            return new TFloatObjectValueHashIterator(TFloatObjectHashMap.this) {
                protected V objectAtIndex(final int index) {
                    return (V)TFloatObjectHashMap.this._values[index];
                }
            };
        }
        
        public boolean containsElement(final V value) {
            return TFloatObjectHashMap.this.containsValue(value);
        }
        
        public boolean removeElement(final V value) {
            final V[] values = (V[])TFloatObjectHashMap.this._values;
            final byte[] states = TFloatObjectHashMap.this._states;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && (value == values[i] || (null != values[i] && values[i].equals(value)))) {
                    TFloatObjectHashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
        
        class TFloatObjectValueHashIterator extends THashPrimitiveIterator implements Iterator<V>
        {
            protected final TFloatObjectHashMap _map;
            
            public TFloatObjectValueHashIterator(final TFloatObjectHashMap map) {
                super(map);
                this._map = map;
            }
            
            protected V objectAtIndex(final int index) {
                final byte[] states = TFloatObjectHashMap.this._states;
                final Object value = this._map._values[index];
                if (states[index] != 1) {
                    return null;
                }
                return (V)value;
            }
            
            public V next() {
                this.moveToNextIndex();
                return (V)this._map._values[this._index];
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
            return this.removeElement(o);
        }
        
        public void clear() {
            TFloatObjectHashMap.this.clear();
        }
        
        public boolean add(final E obj) {
            throw new UnsupportedOperationException();
        }
        
        public int size() {
            return TFloatObjectHashMap.this.size();
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
            return TFloatObjectHashMap.this.isEmpty();
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
    
    class TFloatObjectHashIterator<V> extends THashPrimitiveIterator implements TFloatObjectIterator<V>
    {
        private final TFloatObjectHashMap<V> _map;
        
        public TFloatObjectHashIterator(final TFloatObjectHashMap<V> map) {
            super(map);
            this._map = map;
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public float key() {
            return this._map._set[this._index];
        }
        
        public V value() {
            return (V)this._map._values[this._index];
        }
        
        public V setValue(final V val) {
            final V old = this.value();
            this._map._values[this._index] = val;
            return old;
        }
    }
}
