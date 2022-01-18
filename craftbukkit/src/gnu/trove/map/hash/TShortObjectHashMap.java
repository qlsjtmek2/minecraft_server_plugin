// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.hash;

import java.util.AbstractSet;
import gnu.trove.impl.hash.TPrimitiveHash;
import gnu.trove.impl.hash.THashPrimitiveIterator;
import gnu.trove.TShortCollection;
import gnu.trove.iterator.TShortIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.iterator.TShortObjectIterator;
import java.lang.reflect.Array;
import java.util.Collection;
import gnu.trove.set.TShortSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import gnu.trove.impl.Constants;
import gnu.trove.procedure.TShortObjectProcedure;
import java.io.Externalizable;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.impl.hash.TShortHash;

public class TShortObjectHashMap<V> extends TShortHash implements TShortObjectMap<V>, Externalizable
{
    static final long serialVersionUID = 1L;
    private final TShortObjectProcedure<V> PUT_ALL_PROC;
    protected transient V[] _values;
    protected short no_entry_key;
    
    public TShortObjectHashMap() {
        this.PUT_ALL_PROC = new TShortObjectProcedure<V>() {
            public boolean execute(final short key, final V value) {
                TShortObjectHashMap.this.put(key, value);
                return true;
            }
        };
    }
    
    public TShortObjectHashMap(final int initialCapacity) {
        super(initialCapacity);
        this.PUT_ALL_PROC = new TShortObjectProcedure<V>() {
            public boolean execute(final short key, final V value) {
                TShortObjectHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_key = Constants.DEFAULT_SHORT_NO_ENTRY_VALUE;
    }
    
    public TShortObjectHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TShortObjectProcedure<V>() {
            public boolean execute(final short key, final V value) {
                TShortObjectHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_key = Constants.DEFAULT_SHORT_NO_ENTRY_VALUE;
    }
    
    public TShortObjectHashMap(final int initialCapacity, final float loadFactor, final short noEntryKey) {
        super(initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TShortObjectProcedure<V>() {
            public boolean execute(final short key, final V value) {
                TShortObjectHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_key = noEntryKey;
    }
    
    public TShortObjectHashMap(final TShortObjectMap<? extends V> map) {
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
        final short[] oldKeys = this._set;
        final V[] oldVals = (V[])this._values;
        final byte[] oldStates = this._states;
        this._set = new short[newCapacity];
        this._values = new Object[newCapacity];
        this._states = new byte[newCapacity];
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldStates[i] == 1) {
                final short o = oldKeys[i];
                final int index = this.insertKey(o);
                this._values[index] = oldVals[i];
            }
        }
    }
    
    public short getNoEntryKey() {
        return this.no_entry_key;
    }
    
    public boolean containsKey(final short key) {
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
    
    public V get(final short key) {
        final int index = this.index(key);
        return (V)((index < 0) ? null : this._values[index]);
    }
    
    public V put(final short key, final V value) {
        final int index = this.insertKey(key);
        return this.doPut(value, index);
    }
    
    public V putIfAbsent(final short key, final V value) {
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
    
    public V remove(final short key) {
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
    
    public void putAll(final Map<? extends Short, ? extends V> map) {
        final Set<? extends Map.Entry<? extends Short, ? extends V>> set = map.entrySet();
        for (final Map.Entry<? extends Short, ? extends V> entry : set) {
            this.put((short)entry.getKey(), entry.getValue());
        }
    }
    
    public void putAll(final TShortObjectMap<? extends V> map) {
        map.forEachEntry(this.PUT_ALL_PROC);
    }
    
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, this.no_entry_key);
        Arrays.fill(this._states, 0, this._states.length, (byte)0);
        Arrays.fill(this._values, 0, this._values.length, null);
    }
    
    public TShortSet keySet() {
        return new KeyView();
    }
    
    public short[] keys() {
        final short[] keys = new short[this.size()];
        final short[] k = this._set;
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
    
    public short[] keys(short[] dest) {
        if (dest.length < this._size) {
            dest = new short[this._size];
        }
        final short[] k = this._set;
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
    
    public TShortObjectIterator<V> iterator() {
        return new TShortObjectHashIterator<V>(this);
    }
    
    public boolean forEachKey(final TShortProcedure procedure) {
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
    
    public boolean forEachEntry(final TShortObjectProcedure<? super V> procedure) {
        final byte[] states = this._states;
        final short[] keys = this._set;
        final V[] values = (V[])this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(keys[i], (Object)values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean retainEntries(final TShortObjectProcedure<? super V> procedure) {
        boolean modified = false;
        final byte[] states = this._states;
        final short[] keys = this._set;
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
        if (!(other instanceof TShortObjectMap)) {
            return false;
        }
        final TShortObjectMap that = (TShortObjectMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        try {
            final TShortObjectIterator iter = this.iterator();
            while (iter.hasNext()) {
                iter.advance();
                final short key = iter.key();
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
        out.writeShort(this.no_entry_key);
        out.writeInt(this._size);
        int i = this._states.length;
        while (i-- > 0) {
            if (this._states[i] == 1) {
                out.writeShort(this._set[i]);
                out.writeObject(this._values[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.no_entry_key = in.readShort();
        int size = in.readInt();
        this.setUp(size);
        while (size-- > 0) {
            final short key = in.readShort();
            final V val = (V)in.readObject();
            this.put(key, val);
        }
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.forEachEntry(new TShortObjectProcedure<V>() {
            private boolean first = true;
            
            public boolean execute(final short key, final Object value) {
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
    
    class KeyView implements TShortSet
    {
        public short getNoEntryValue() {
            return TShortObjectHashMap.this.no_entry_key;
        }
        
        public int size() {
            return TShortObjectHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return TShortObjectHashMap.this._size == 0;
        }
        
        public boolean contains(final short entry) {
            return TShortObjectHashMap.this.containsKey(entry);
        }
        
        public TShortIterator iterator() {
            return new TShortHashIterator(TShortObjectHashMap.this);
        }
        
        public short[] toArray() {
            return TShortObjectHashMap.this.keys();
        }
        
        public short[] toArray(final short[] dest) {
            return TShortObjectHashMap.this.keys(dest);
        }
        
        public boolean add(final short entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final short entry) {
            return null != TShortObjectHashMap.this.remove(entry);
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!TShortObjectHashMap.this.containsKey((short)element)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TShortCollection collection) {
            if (collection == this) {
                return true;
            }
            final TShortIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TShortObjectHashMap.this.containsKey(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final short[] array) {
            for (final short element : array) {
                if (!TShortObjectHashMap.this.containsKey(element)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean addAll(final Collection<? extends Short> collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final TShortCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final short[] array) {
            throw new UnsupportedOperationException();
        }
        
        public boolean retainAll(final Collection<?> collection) {
            boolean modified = false;
            final TShortIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final TShortCollection collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            final TShortIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final short[] array) {
            boolean changed = false;
            Arrays.sort(array);
            final short[] set = TShortObjectHashMap.this._set;
            final byte[] states = TShortObjectHashMap.this._states;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    TShortObjectHashMap.this.removeAt(i);
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final Collection<?> collection) {
            boolean changed = false;
            for (final Object element : collection) {
                if (element instanceof Short) {
                    final short c = (short)element;
                    if (!this.remove(c)) {
                        continue;
                    }
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final TShortCollection collection) {
            if (collection == this) {
                this.clear();
                return true;
            }
            boolean changed = false;
            for (final short element : collection) {
                if (this.remove(element)) {
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final short[] array) {
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
            TShortObjectHashMap.this.clear();
        }
        
        public boolean forEach(final TShortProcedure procedure) {
            return TShortObjectHashMap.this.forEachKey(procedure);
        }
        
        public boolean equals(final Object other) {
            if (!(other instanceof TShortSet)) {
                return false;
            }
            final TShortSet that = (TShortSet)other;
            if (that.size() != this.size()) {
                return false;
            }
            int i = TShortObjectHashMap.this._states.length;
            while (i-- > 0) {
                if (TShortObjectHashMap.this._states[i] == 1 && !that.contains(TShortObjectHashMap.this._set[i])) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hashcode = 0;
            int i = TShortObjectHashMap.this._states.length;
            while (i-- > 0) {
                if (TShortObjectHashMap.this._states[i] == 1) {
                    hashcode += HashFunctions.hash(TShortObjectHashMap.this._set[i]);
                }
            }
            return hashcode;
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            boolean first = true;
            int i = TShortObjectHashMap.this._states.length;
            while (i-- > 0) {
                if (TShortObjectHashMap.this._states[i] == 1) {
                    if (first) {
                        first = false;
                    }
                    else {
                        buf.append(",");
                    }
                    buf.append(TShortObjectHashMap.this._set[i]);
                }
            }
            return buf.toString();
        }
        
        class TShortHashIterator extends THashPrimitiveIterator implements TShortIterator
        {
            private final TShortHash _hash;
            
            public TShortHashIterator(final TShortHash hash) {
                super(hash);
                this._hash = hash;
            }
            
            public short next() {
                this.moveToNextIndex();
                return this._hash._set[this._index];
            }
        }
    }
    
    protected class ValueView extends MapBackedView<V>
    {
        public Iterator<V> iterator() {
            return new TShortObjectValueHashIterator(TShortObjectHashMap.this) {
                protected V objectAtIndex(final int index) {
                    return (V)TShortObjectHashMap.this._values[index];
                }
            };
        }
        
        public boolean containsElement(final V value) {
            return TShortObjectHashMap.this.containsValue(value);
        }
        
        public boolean removeElement(final V value) {
            final V[] values = (V[])TShortObjectHashMap.this._values;
            final byte[] states = TShortObjectHashMap.this._states;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && (value == values[i] || (null != values[i] && values[i].equals(value)))) {
                    TShortObjectHashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
        
        class TShortObjectValueHashIterator extends THashPrimitiveIterator implements Iterator<V>
        {
            protected final TShortObjectHashMap _map;
            
            public TShortObjectValueHashIterator(final TShortObjectHashMap map) {
                super(map);
                this._map = map;
            }
            
            protected V objectAtIndex(final int index) {
                final byte[] states = TShortObjectHashMap.this._states;
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
            TShortObjectHashMap.this.clear();
        }
        
        public boolean add(final E obj) {
            throw new UnsupportedOperationException();
        }
        
        public int size() {
            return TShortObjectHashMap.this.size();
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
            return TShortObjectHashMap.this.isEmpty();
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
    
    class TShortObjectHashIterator<V> extends THashPrimitiveIterator implements TShortObjectIterator<V>
    {
        private final TShortObjectHashMap<V> _map;
        
        public TShortObjectHashIterator(final TShortObjectHashMap<V> map) {
            super(map);
            this._map = map;
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public short key() {
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
