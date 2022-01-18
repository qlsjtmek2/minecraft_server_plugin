// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.lib.gnu.trove.map.hash;

import java.util.AbstractSet;
import think.rpgitems.lib.gnu.trove.impl.hash.TPrimitiveHash;
import think.rpgitems.lib.gnu.trove.impl.hash.THashPrimitiveIterator;
import think.rpgitems.lib.gnu.trove.TCharCollection;
import think.rpgitems.lib.gnu.trove.iterator.TCharIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import think.rpgitems.lib.gnu.trove.impl.HashFunctions;
import think.rpgitems.lib.gnu.trove.function.TObjectFunction;
import think.rpgitems.lib.gnu.trove.procedure.TObjectProcedure;
import think.rpgitems.lib.gnu.trove.procedure.TCharProcedure;
import think.rpgitems.lib.gnu.trove.iterator.TCharObjectIterator;
import java.lang.reflect.Array;
import java.util.Collection;
import think.rpgitems.lib.gnu.trove.set.TCharSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import think.rpgitems.lib.gnu.trove.impl.Constants;
import think.rpgitems.lib.gnu.trove.procedure.TCharObjectProcedure;
import java.io.Externalizable;
import think.rpgitems.lib.gnu.trove.map.TCharObjectMap;
import think.rpgitems.lib.gnu.trove.impl.hash.TCharHash;

public class TCharObjectHashMap<V> extends TCharHash implements TCharObjectMap<V>, Externalizable
{
    static final long serialVersionUID = 1L;
    private final TCharObjectProcedure<V> PUT_ALL_PROC;
    protected transient V[] _values;
    protected char no_entry_key;
    
    public TCharObjectHashMap() {
        this.PUT_ALL_PROC = new TCharObjectProcedure<V>() {
            public boolean execute(final char key, final V value) {
                TCharObjectHashMap.this.put(key, value);
                return true;
            }
        };
    }
    
    public TCharObjectHashMap(final int initialCapacity) {
        super(initialCapacity);
        this.PUT_ALL_PROC = new TCharObjectProcedure<V>() {
            public boolean execute(final char key, final V value) {
                TCharObjectHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_key = Constants.DEFAULT_CHAR_NO_ENTRY_VALUE;
    }
    
    public TCharObjectHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TCharObjectProcedure<V>() {
            public boolean execute(final char key, final V value) {
                TCharObjectHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_key = Constants.DEFAULT_CHAR_NO_ENTRY_VALUE;
    }
    
    public TCharObjectHashMap(final int initialCapacity, final float loadFactor, final char noEntryKey) {
        super(initialCapacity, loadFactor);
        this.PUT_ALL_PROC = new TCharObjectProcedure<V>() {
            public boolean execute(final char key, final V value) {
                TCharObjectHashMap.this.put(key, value);
                return true;
            }
        };
        this.no_entry_key = noEntryKey;
    }
    
    public TCharObjectHashMap(final TCharObjectMap<? extends V> map) {
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
        final char[] oldKeys = this._set;
        final V[] oldVals = (V[])this._values;
        final byte[] oldStates = this._states;
        this._set = new char[newCapacity];
        this._values = new Object[newCapacity];
        this._states = new byte[newCapacity];
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldStates[i] == 1) {
                final char o = oldKeys[i];
                final int index = this.insertKey(o);
                this._values[index] = oldVals[i];
            }
        }
    }
    
    public char getNoEntryKey() {
        return this.no_entry_key;
    }
    
    public boolean containsKey(final char key) {
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
    
    public V get(final char key) {
        final int index = this.index(key);
        return (V)((index < 0) ? null : this._values[index]);
    }
    
    public V put(final char key, final V value) {
        final int index = this.insertKey(key);
        return this.doPut(value, index);
    }
    
    public V putIfAbsent(final char key, final V value) {
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
    
    public V remove(final char key) {
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
    
    public void putAll(final Map<? extends Character, ? extends V> map) {
        final Set<? extends Map.Entry<? extends Character, ? extends V>> set = map.entrySet();
        for (final Map.Entry<? extends Character, ? extends V> entry : set) {
            this.put((char)entry.getKey(), entry.getValue());
        }
    }
    
    public void putAll(final TCharObjectMap<? extends V> map) {
        map.forEachEntry(this.PUT_ALL_PROC);
    }
    
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, this.no_entry_key);
        Arrays.fill(this._states, 0, this._states.length, (byte)0);
        Arrays.fill(this._values, 0, this._values.length, null);
    }
    
    public TCharSet keySet() {
        return new KeyView();
    }
    
    public char[] keys() {
        final char[] keys = new char[this.size()];
        final char[] k = this._set;
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
    
    public char[] keys(char[] dest) {
        if (dest.length < this._size) {
            dest = new char[this._size];
        }
        final char[] k = this._set;
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
    
    public TCharObjectIterator<V> iterator() {
        return new TCharObjectHashIterator<V>(this);
    }
    
    public boolean forEachKey(final TCharProcedure procedure) {
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
    
    public boolean forEachEntry(final TCharObjectProcedure<? super V> procedure) {
        final byte[] states = this._states;
        final char[] keys = this._set;
        final V[] values = (V[])this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(keys[i], (Object)values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean retainEntries(final TCharObjectProcedure<? super V> procedure) {
        boolean modified = false;
        final byte[] states = this._states;
        final char[] keys = this._set;
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
        if (!(other instanceof TCharObjectMap)) {
            return false;
        }
        final TCharObjectMap that = (TCharObjectMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        try {
            final TCharObjectIterator iter = this.iterator();
            while (iter.hasNext()) {
                iter.advance();
                final char key = iter.key();
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
        out.writeChar(this.no_entry_key);
        out.writeInt(this._size);
        int i = this._states.length;
        while (i-- > 0) {
            if (this._states[i] == 1) {
                out.writeChar(this._set[i]);
                out.writeObject(this._values[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.no_entry_key = in.readChar();
        int size = in.readInt();
        this.setUp(size);
        while (size-- > 0) {
            final char key = in.readChar();
            final V val = (V)in.readObject();
            this.put(key, val);
        }
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.forEachEntry(new TCharObjectProcedure<V>() {
            private boolean first = true;
            
            public boolean execute(final char key, final Object value) {
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
    
    class KeyView implements TCharSet
    {
        public char getNoEntryValue() {
            return TCharObjectHashMap.this.no_entry_key;
        }
        
        public int size() {
            return TCharObjectHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return TCharObjectHashMap.this._size == 0;
        }
        
        public boolean contains(final char entry) {
            return TCharObjectHashMap.this.containsKey(entry);
        }
        
        public TCharIterator iterator() {
            return new TCharHashIterator(TCharObjectHashMap.this);
        }
        
        public char[] toArray() {
            return TCharObjectHashMap.this.keys();
        }
        
        public char[] toArray(final char[] dest) {
            return TCharObjectHashMap.this.keys(dest);
        }
        
        public boolean add(final char entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final char entry) {
            return null != TCharObjectHashMap.this.remove(entry);
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!TCharObjectHashMap.this.containsKey((char)element)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TCharCollection collection) {
            if (collection == this) {
                return true;
            }
            final TCharIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TCharObjectHashMap.this.containsKey(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final char[] array) {
            for (final char element : array) {
                if (!TCharObjectHashMap.this.containsKey(element)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean addAll(final Collection<? extends Character> collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final TCharCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final char[] array) {
            throw new UnsupportedOperationException();
        }
        
        public boolean retainAll(final Collection<?> collection) {
            boolean modified = false;
            final TCharIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final TCharCollection collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            final TCharIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final char[] array) {
            boolean changed = false;
            Arrays.sort(array);
            final char[] set = TCharObjectHashMap.this._set;
            final byte[] states = TCharObjectHashMap.this._states;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    TCharObjectHashMap.this.removeAt(i);
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final Collection<?> collection) {
            boolean changed = false;
            for (final Object element : collection) {
                if (element instanceof Character) {
                    final char c = (char)element;
                    if (!this.remove(c)) {
                        continue;
                    }
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final TCharCollection collection) {
            if (collection == this) {
                this.clear();
                return true;
            }
            boolean changed = false;
            for (final char element : collection) {
                if (this.remove(element)) {
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final char[] array) {
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
            TCharObjectHashMap.this.clear();
        }
        
        public boolean forEach(final TCharProcedure procedure) {
            return TCharObjectHashMap.this.forEachKey(procedure);
        }
        
        public boolean equals(final Object other) {
            if (!(other instanceof TCharSet)) {
                return false;
            }
            final TCharSet that = (TCharSet)other;
            if (that.size() != this.size()) {
                return false;
            }
            int i = TCharObjectHashMap.this._states.length;
            while (i-- > 0) {
                if (TCharObjectHashMap.this._states[i] == 1 && !that.contains(TCharObjectHashMap.this._set[i])) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hashcode = 0;
            int i = TCharObjectHashMap.this._states.length;
            while (i-- > 0) {
                if (TCharObjectHashMap.this._states[i] == 1) {
                    hashcode += HashFunctions.hash(TCharObjectHashMap.this._set[i]);
                }
            }
            return hashcode;
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            boolean first = true;
            int i = TCharObjectHashMap.this._states.length;
            while (i-- > 0) {
                if (TCharObjectHashMap.this._states[i] == 1) {
                    if (first) {
                        first = false;
                    }
                    else {
                        buf.append(",");
                    }
                    buf.append(TCharObjectHashMap.this._set[i]);
                }
            }
            return buf.toString();
        }
        
        class TCharHashIterator extends THashPrimitiveIterator implements TCharIterator
        {
            private final TCharHash _hash;
            
            public TCharHashIterator(final TCharHash hash) {
                super(hash);
                this._hash = hash;
            }
            
            public char next() {
                this.moveToNextIndex();
                return this._hash._set[this._index];
            }
        }
    }
    
    protected class ValueView extends MapBackedView<V>
    {
        public Iterator<V> iterator() {
            return new TCharObjectValueHashIterator(TCharObjectHashMap.this) {
                protected V objectAtIndex(final int index) {
                    return (V)TCharObjectHashMap.this._values[index];
                }
            };
        }
        
        public boolean containsElement(final V value) {
            return TCharObjectHashMap.this.containsValue(value);
        }
        
        public boolean removeElement(final V value) {
            final V[] values = (V[])TCharObjectHashMap.this._values;
            final byte[] states = TCharObjectHashMap.this._states;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && (value == values[i] || (null != values[i] && values[i].equals(value)))) {
                    TCharObjectHashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
        
        class TCharObjectValueHashIterator extends THashPrimitiveIterator implements Iterator<V>
        {
            protected final TCharObjectHashMap _map;
            
            public TCharObjectValueHashIterator(final TCharObjectHashMap map) {
                super(map);
                this._map = map;
            }
            
            protected V objectAtIndex(final int index) {
                final byte[] states = TCharObjectHashMap.this._states;
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
            TCharObjectHashMap.this.clear();
        }
        
        public boolean add(final E obj) {
            throw new UnsupportedOperationException();
        }
        
        public int size() {
            return TCharObjectHashMap.this.size();
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
            return TCharObjectHashMap.this.isEmpty();
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
    
    class TCharObjectHashIterator<V> extends THashPrimitiveIterator implements TCharObjectIterator<V>
    {
        private final TCharObjectHashMap<V> _map;
        
        public TCharObjectHashIterator(final TCharObjectHashMap<V> map) {
            super(map);
            this._map = map;
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public char key() {
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
