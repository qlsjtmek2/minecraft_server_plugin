// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.lib.gnu.trove.map.hash;

import java.util.AbstractSet;
import think.rpgitems.lib.gnu.trove.impl.hash.TPrimitiveHash;
import think.rpgitems.lib.gnu.trove.impl.hash.THashPrimitiveIterator;
import think.rpgitems.lib.gnu.trove.TIntCollection;
import think.rpgitems.lib.gnu.trove.iterator.TIntIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import think.rpgitems.lib.gnu.trove.impl.HashFunctions;
import think.rpgitems.lib.gnu.trove.function.TObjectFunction;
import think.rpgitems.lib.gnu.trove.procedure.TObjectProcedure;
import think.rpgitems.lib.gnu.trove.procedure.TIntProcedure;
import think.rpgitems.lib.gnu.trove.iterator.TIntObjectIterator;
import java.lang.reflect.Array;
import java.util.Collection;
import think.rpgitems.lib.gnu.trove.set.TIntSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import think.rpgitems.lib.gnu.trove.impl.Constants;
import think.rpgitems.lib.gnu.trove.procedure.TIntObjectProcedure;
import java.io.Externalizable;
import think.rpgitems.lib.gnu.trove.map.TIntObjectMap;
import think.rpgitems.lib.gnu.trove.impl.hash.TIntHash;

public class TIntObjectHashMap<V> extends TIntHash implements TIntObjectMap<V>, Externalizable
{
    static final long serialVersionUID = 1L;
    private final TIntObjectProcedure<V> PUT_ALL_PROC;
    protected transient V[] _values;
    protected int no_entry_key;
    
    public TIntObjectHashMap() {
        this.PUT_ALL_PROC = new TIntObjectProcedure<V>() {
            public boolean execute(final int key, final V value) {
                TIntObjectHashMap.this.put(key, value);
                return true;
            }
        };
    }
    
    public TIntObjectHashMap(final int initialCapacity) {
        super(initialCapacity);
        this.PUT_ALL_PROC = new TIntObjectProcedure<V>() {
            public boolean execute(final int key, final V value) {
                TIntObjectHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_key = Constants.DEFAULT_INT_NO_ENTRY_VALUE;
    }
    
    public TIntObjectHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TIntObjectProcedure<V>() {
            public boolean execute(final int key, final V value) {
                TIntObjectHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_key = Constants.DEFAULT_INT_NO_ENTRY_VALUE;
    }
    
    public TIntObjectHashMap(final int initialCapacity, final float loadFactor, final int noEntryKey) {
        super(initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TIntObjectProcedure<V>() {
            public boolean execute(final int key, final V value) {
                TIntObjectHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_key = noEntryKey;
    }
    
    public TIntObjectHashMap(final TIntObjectMap<? extends V> map) {
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
        final int[] oldKeys = this._set;
        final V[] oldVals = (V[])this._values;
        final byte[] oldStates = this._states;
        this._set = new int[newCapacity];
        this._values = new Object[newCapacity];
        this._states = new byte[newCapacity];
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldStates[i] == 1) {
                final int o = oldKeys[i];
                final int index = this.insertKey(o);
                this._values[index] = oldVals[i];
            }
        }
    }
    
    public int getNoEntryKey() {
        return this.no_entry_key;
    }
    
    public boolean containsKey(final int key) {
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
    
    public V get(final int key) {
        final int index = this.index(key);
        return (V)((index < 0) ? null : this._values[index]);
    }
    
    public V put(final int key, final V value) {
        final int index = this.insertKey(key);
        return this.doPut(value, index);
    }
    
    public V putIfAbsent(final int key, final V value) {
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
    
    public V remove(final int key) {
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
    
    public void putAll(final Map<? extends Integer, ? extends V> map) {
        final Set<? extends Map.Entry<? extends Integer, ? extends V>> set = map.entrySet();
        for (final Map.Entry<? extends Integer, ? extends V> entry : set) {
            this.put((int)entry.getKey(), entry.getValue());
        }
    }
    
    public void putAll(final TIntObjectMap<? extends V> map) {
        map.forEachEntry(this.PUT_ALL_PROC);
    }
    
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, this.no_entry_key);
        Arrays.fill(this._states, 0, this._states.length, (byte)0);
        Arrays.fill(this._values, 0, this._values.length, null);
    }
    
    public TIntSet keySet() {
        return new KeyView();
    }
    
    public int[] keys() {
        final int[] keys = new int[this.size()];
        final int[] k = this._set;
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
    
    public int[] keys(int[] dest) {
        if (dest.length < this._size) {
            dest = new int[this._size];
        }
        final int[] k = this._set;
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
    
    public Object[] values() {
        final Object[] vals = new Object[this.size()];
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
    
    public TIntObjectIterator<V> iterator() {
        return new TIntObjectHashIterator<V>(this);
    }
    
    public boolean forEachKey(final TIntProcedure procedure) {
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
    
    public boolean forEachEntry(final TIntObjectProcedure<? super V> procedure) {
        final byte[] states = this._states;
        final int[] keys = this._set;
        final V[] values = (V[])this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(keys[i], (Object)values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean retainEntries(final TIntObjectProcedure<? super V> procedure) {
        boolean modified = false;
        final byte[] states = this._states;
        final int[] keys = this._set;
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
        if (!(other instanceof TIntObjectMap)) {
            return false;
        }
        final TIntObjectMap that = (TIntObjectMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        try {
            final TIntObjectIterator iter = this.iterator();
            while (iter.hasNext()) {
                iter.advance();
                final int key = iter.key();
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
        out.writeInt(this.no_entry_key);
        out.writeInt(this._size);
        int i = this._states.length;
        while (i-- > 0) {
            if (this._states[i] == 1) {
                out.writeInt(this._set[i]);
                out.writeObject(this._values[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.no_entry_key = in.readInt();
        int size = in.readInt();
        this.setUp(size);
        while (size-- > 0) {
            final int key = in.readInt();
            final V val = (V)in.readObject();
            this.put(key, val);
        }
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.forEachEntry(new TIntObjectProcedure<V>() {
            private boolean first = true;
            
            public boolean execute(final int key, final Object value) {
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
    
    class KeyView implements TIntSet
    {
        public int getNoEntryValue() {
            return TIntObjectHashMap.this.no_entry_key;
        }
        
        public int size() {
            return TIntObjectHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return TIntObjectHashMap.this._size == 0;
        }
        
        public boolean contains(final int entry) {
            return TIntObjectHashMap.this.containsKey(entry);
        }
        
        public TIntIterator iterator() {
            return new TIntHashIterator(TIntObjectHashMap.this);
        }
        
        public int[] toArray() {
            return TIntObjectHashMap.this.keys();
        }
        
        public int[] toArray(final int[] dest) {
            return TIntObjectHashMap.this.keys(dest);
        }
        
        public boolean add(final int entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final int entry) {
            return null != TIntObjectHashMap.this.remove(entry);
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!TIntObjectHashMap.this.containsKey((int)element)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TIntCollection collection) {
            if (collection == this) {
                return true;
            }
            final TIntIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TIntObjectHashMap.this.containsKey(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final int[] array) {
            for (final int element : array) {
                if (!TIntObjectHashMap.this.containsKey(element)) {
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
            final int[] set = TIntObjectHashMap.this._set;
            final byte[] states = TIntObjectHashMap.this._states;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    TIntObjectHashMap.this.removeAt(i);
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
            if (collection == this) {
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
            TIntObjectHashMap.this.clear();
        }
        
        public boolean forEach(final TIntProcedure procedure) {
            return TIntObjectHashMap.this.forEachKey(procedure);
        }
        
        public boolean equals(final Object other) {
            if (!(other instanceof TIntSet)) {
                return false;
            }
            final TIntSet that = (TIntSet)other;
            if (that.size() != this.size()) {
                return false;
            }
            int i = TIntObjectHashMap.this._states.length;
            while (i-- > 0) {
                if (TIntObjectHashMap.this._states[i] == 1 && !that.contains(TIntObjectHashMap.this._set[i])) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hashcode = 0;
            int i = TIntObjectHashMap.this._states.length;
            while (i-- > 0) {
                if (TIntObjectHashMap.this._states[i] == 1) {
                    hashcode += HashFunctions.hash(TIntObjectHashMap.this._set[i]);
                }
            }
            return hashcode;
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            boolean first = true;
            int i = TIntObjectHashMap.this._states.length;
            while (i-- > 0) {
                if (TIntObjectHashMap.this._states[i] == 1) {
                    if (first) {
                        first = false;
                    }
                    else {
                        buf.append(",");
                    }
                    buf.append(TIntObjectHashMap.this._set[i]);
                }
            }
            return buf.toString();
        }
        
        class TIntHashIterator extends THashPrimitiveIterator implements TIntIterator
        {
            private final TIntHash _hash;
            
            public TIntHashIterator(final TIntHash hash) {
                super(hash);
                this._hash = hash;
            }
            
            public int next() {
                this.moveToNextIndex();
                return this._hash._set[this._index];
            }
        }
    }
    
    protected class ValueView extends MapBackedView<V>
    {
        public Iterator<V> iterator() {
            return new TIntObjectValueHashIterator(TIntObjectHashMap.this) {
                protected V objectAtIndex(final int index) {
                    return (V)TIntObjectHashMap.this._values[index];
                }
            };
        }
        
        public boolean containsElement(final V value) {
            return TIntObjectHashMap.this.containsValue(value);
        }
        
        public boolean removeElement(final V value) {
            final V[] values = (V[])TIntObjectHashMap.this._values;
            final byte[] states = TIntObjectHashMap.this._states;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && (value == values[i] || (null != values[i] && values[i].equals(value)))) {
                    TIntObjectHashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
        
        class TIntObjectValueHashIterator extends THashPrimitiveIterator implements Iterator<V>
        {
            protected final TIntObjectHashMap _map;
            
            public TIntObjectValueHashIterator(final TIntObjectHashMap map) {
                super(map);
                this._map = map;
            }
            
            protected V objectAtIndex(final int index) {
                final byte[] states = TIntObjectHashMap.this._states;
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
            TIntObjectHashMap.this.clear();
        }
        
        public boolean add(final E obj) {
            throw new UnsupportedOperationException();
        }
        
        public int size() {
            return TIntObjectHashMap.this.size();
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
            return TIntObjectHashMap.this.isEmpty();
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
    
    class TIntObjectHashIterator<V> extends THashPrimitiveIterator implements TIntObjectIterator<V>
    {
        private final TIntObjectHashMap<V> _map;
        
        public TIntObjectHashIterator(final TIntObjectHashMap<V> map) {
            super(map);
            this._map = map;
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public int key() {
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
