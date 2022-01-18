// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.lib.gnu.trove.map.hash;

import java.util.ConcurrentModificationException;
import think.rpgitems.lib.gnu.trove.impl.hash.THashPrimitiveIterator;
import think.rpgitems.lib.gnu.trove.iterator.TByteIterator;
import think.rpgitems.lib.gnu.trove.TIntCollection;
import java.util.Collection;
import think.rpgitems.lib.gnu.trove.impl.hash.TPrimitiveHash;
import think.rpgitems.lib.gnu.trove.iterator.TIntIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import think.rpgitems.lib.gnu.trove.impl.HashFunctions;
import think.rpgitems.lib.gnu.trove.function.TByteFunction;
import think.rpgitems.lib.gnu.trove.procedure.TIntByteProcedure;
import think.rpgitems.lib.gnu.trove.procedure.TByteProcedure;
import think.rpgitems.lib.gnu.trove.procedure.TIntProcedure;
import think.rpgitems.lib.gnu.trove.TByteCollection;
import think.rpgitems.lib.gnu.trove.set.TIntSet;
import think.rpgitems.lib.gnu.trove.iterator.TIntByteIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.io.Externalizable;
import think.rpgitems.lib.gnu.trove.map.TIntByteMap;
import think.rpgitems.lib.gnu.trove.impl.hash.TIntByteHash;

public class TIntByteHashMap extends TIntByteHash implements TIntByteMap, Externalizable
{
    static final long serialVersionUID = 1L;
    protected transient byte[] _values;
    
    public TIntByteHashMap() {
    }
    
    public TIntByteHashMap(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public TIntByteHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public TIntByteHashMap(final int initialCapacity, final float loadFactor, final int noEntryKey, final byte noEntryValue) {
        super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
    }
    
    public TIntByteHashMap(final int[] keys, final byte[] values) {
        super(Math.max(keys.length, values.length));
        for (int size = Math.min(keys.length, values.length), i = 0; i < size; ++i) {
            this.put(keys[i], values[i]);
        }
    }
    
    public TIntByteHashMap(final TIntByteMap map) {
        super(map.size());
        if (map instanceof TIntByteHashMap) {
            final TIntByteHashMap hashmap = (TIntByteHashMap)map;
            this._loadFactor = hashmap._loadFactor;
            this.no_entry_key = hashmap.no_entry_key;
            this.no_entry_value = hashmap.no_entry_value;
            if (this.no_entry_key != 0) {
                Arrays.fill(this._set, this.no_entry_key);
            }
            if (this.no_entry_value != 0) {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(map);
    }
    
    protected int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._values = new byte[capacity];
        return capacity;
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final int[] oldKeys = this._set;
        final byte[] oldVals = this._values;
        final byte[] oldStates = this._states;
        this._set = new int[newCapacity];
        this._values = new byte[newCapacity];
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
    
    public byte put(final int key, final byte value) {
        final int index = this.insertKey(key);
        return this.doPut(key, value, index);
    }
    
    public byte putIfAbsent(final int key, final byte value) {
        final int index = this.insertKey(key);
        if (index < 0) {
            return this._values[-index - 1];
        }
        return this.doPut(key, value, index);
    }
    
    private byte doPut(final int key, final byte value, int index) {
        byte previous = this.no_entry_value;
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
    
    public void putAll(final Map<? extends Integer, ? extends Byte> map) {
        this.ensureCapacity(map.size());
        for (final Map.Entry<? extends Integer, ? extends Byte> entry : map.entrySet()) {
            this.put((int)entry.getKey(), (byte)entry.getValue());
        }
    }
    
    public void putAll(final TIntByteMap map) {
        this.ensureCapacity(map.size());
        final TIntByteIterator iter = map.iterator();
        while (iter.hasNext()) {
            iter.advance();
            this.put(iter.key(), iter.value());
        }
    }
    
    public byte get(final int key) {
        final int index = this.index(key);
        return (index < 0) ? this.no_entry_value : this._values[index];
    }
    
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, this.no_entry_key);
        Arrays.fill(this._values, 0, this._values.length, this.no_entry_value);
        Arrays.fill(this._states, 0, this._states.length, (byte)0);
    }
    
    public boolean isEmpty() {
        return 0 == this._size;
    }
    
    public byte remove(final int key) {
        byte prev = this.no_entry_value;
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
    
    public TIntSet keySet() {
        return new TKeyView();
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
    
    public int[] keys(int[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new int[size];
        }
        final int[] keys = this._set;
        final byte[] states = this._states;
        int i = keys.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                array[j++] = keys[i];
            }
        }
        return array;
    }
    
    public TByteCollection valueCollection() {
        return new TValueView();
    }
    
    public byte[] values() {
        final byte[] vals = new byte[this.size()];
        final byte[] v = this._values;
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
    
    public byte[] values(byte[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new byte[size];
        }
        final byte[] v = this._values;
        final byte[] states = this._states;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                array[j++] = v[i];
            }
        }
        return array;
    }
    
    public boolean containsValue(final byte val) {
        final byte[] states = this._states;
        final byte[] vals = this._values;
        int i = vals.length;
        while (i-- > 0) {
            if (states[i] == 1 && val == vals[i]) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsKey(final int key) {
        return this.contains(key);
    }
    
    public TIntByteIterator iterator() {
        return new TIntByteHashIterator(this);
    }
    
    public boolean forEachKey(final TIntProcedure procedure) {
        return this.forEach(procedure);
    }
    
    public boolean forEachValue(final TByteProcedure procedure) {
        final byte[] states = this._states;
        final byte[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachEntry(final TIntByteProcedure procedure) {
        final byte[] states = this._states;
        final int[] keys = this._set;
        final byte[] values = this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public void transformValues(final TByteFunction function) {
        final byte[] states = this._states;
        final byte[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                values[i] = function.execute(values[i]);
            }
        }
    }
    
    public boolean retainEntries(final TIntByteProcedure procedure) {
        boolean modified = false;
        final byte[] states = this._states;
        final int[] keys = this._set;
        final byte[] values = this._values;
        this.tempDisableAutoCompaction();
        try {
            int i = keys.length;
            while (i-- > 0) {
                if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
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
    
    public boolean increment(final int key) {
        return this.adjustValue(key, (byte)1);
    }
    
    public boolean adjustValue(final int key, final byte amount) {
        final int index = this.index(key);
        if (index < 0) {
            return false;
        }
        final byte[] values = this._values;
        final int n = index;
        values[n] += amount;
        return true;
    }
    
    public byte adjustOrPutValue(final int key, final byte adjust_amount, final byte put_amount) {
        int index = this.insertKey(key);
        byte newValue;
        boolean isNewMapping;
        if (index < 0) {
            index = -index - 1;
            final byte[] values = this._values;
            final int n = index;
            final byte b = (byte)(values[n] + adjust_amount);
            values[n] = b;
            newValue = b;
            isNewMapping = false;
        }
        else {
            this._values[index] = put_amount;
            newValue = put_amount;
            isNewMapping = true;
        }
        final byte previousState = this._states[index];
        if (isNewMapping) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return newValue;
    }
    
    public boolean equals(final Object other) {
        if (!(other instanceof TIntByteMap)) {
            return false;
        }
        final TIntByteMap that = (TIntByteMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        final byte[] values = this._values;
        final byte[] states = this._states;
        final byte this_no_entry_value = this.getNoEntryValue();
        final byte that_no_entry_value = that.getNoEntryValue();
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                final int key = this._set[i];
                final byte that_value = that.get(key);
                final byte this_value = values[i];
                if (this_value != that_value && this_value != this_no_entry_value && that_value != that_no_entry_value) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    public int hashCode() {
        int hashcode = 0;
        final byte[] states = this._states;
        int i = this._values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                hashcode += (HashFunctions.hash(this._set[i]) ^ HashFunctions.hash(this._values[i]));
            }
        }
        return hashcode;
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.forEachEntry(new TIntByteProcedure() {
            private boolean first = true;
            
            public boolean execute(final int key, final byte value) {
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
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeInt(this._size);
        int i = this._states.length;
        while (i-- > 0) {
            if (this._states[i] == 1) {
                out.writeInt(this._set[i]);
                out.writeByte(this._values[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        int size = in.readInt();
        this.setUp(size);
        while (size-- > 0) {
            final int key = in.readInt();
            final byte val = in.readByte();
            this.put(key, val);
        }
    }
    
    protected class TKeyView implements TIntSet
    {
        public TIntIterator iterator() {
            return new TIntByteKeyHashIterator(TIntByteHashMap.this);
        }
        
        public int getNoEntryValue() {
            return TIntByteHashMap.this.no_entry_key;
        }
        
        public int size() {
            return TIntByteHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TIntByteHashMap.this._size;
        }
        
        public boolean contains(final int entry) {
            return TIntByteHashMap.this.contains(entry);
        }
        
        public int[] toArray() {
            return TIntByteHashMap.this.keys();
        }
        
        public int[] toArray(final int[] dest) {
            return TIntByteHashMap.this.keys(dest);
        }
        
        public boolean add(final int entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final int entry) {
            return TIntByteHashMap.this.no_entry_value != TIntByteHashMap.this.remove(entry);
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Integer)) {
                    return false;
                }
                final int ele = (int)element;
                if (!TIntByteHashMap.this.containsKey(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TIntCollection collection) {
            final TIntIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TIntByteHashMap.this.containsKey(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final int[] array) {
            for (final int element : array) {
                if (!TIntByteHashMap.this.contains(element)) {
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
            final int[] set = TIntByteHashMap.this._set;
            final byte[] states = TIntByteHashMap.this._states;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    TIntByteHashMap.this.removeAt(i);
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
            TIntByteHashMap.this.clear();
        }
        
        public boolean forEach(final TIntProcedure procedure) {
            return TIntByteHashMap.this.forEachKey(procedure);
        }
        
        public boolean equals(final Object other) {
            if (!(other instanceof TIntSet)) {
                return false;
            }
            final TIntSet that = (TIntSet)other;
            if (that.size() != this.size()) {
                return false;
            }
            int i = TIntByteHashMap.this._states.length;
            while (i-- > 0) {
                if (TIntByteHashMap.this._states[i] == 1 && !that.contains(TIntByteHashMap.this._set[i])) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hashcode = 0;
            int i = TIntByteHashMap.this._states.length;
            while (i-- > 0) {
                if (TIntByteHashMap.this._states[i] == 1) {
                    hashcode += HashFunctions.hash(TIntByteHashMap.this._set[i]);
                }
            }
            return hashcode;
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TIntByteHashMap.this.forEachKey(new TIntProcedure() {
                private boolean first = true;
                
                public boolean execute(final int key) {
                    if (this.first) {
                        this.first = false;
                    }
                    else {
                        buf.append(", ");
                    }
                    buf.append(key);
                    return true;
                }
            });
            buf.append("}");
            return buf.toString();
        }
    }
    
    protected class TValueView implements TByteCollection
    {
        public TByteIterator iterator() {
            return new TIntByteValueHashIterator(TIntByteHashMap.this);
        }
        
        public byte getNoEntryValue() {
            return TIntByteHashMap.this.no_entry_value;
        }
        
        public int size() {
            return TIntByteHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TIntByteHashMap.this._size;
        }
        
        public boolean contains(final byte entry) {
            return TIntByteHashMap.this.containsValue(entry);
        }
        
        public byte[] toArray() {
            return TIntByteHashMap.this.values();
        }
        
        public byte[] toArray(final byte[] dest) {
            return TIntByteHashMap.this.values(dest);
        }
        
        public boolean add(final byte entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final byte entry) {
            final byte[] values = TIntByteHashMap.this._values;
            final int[] set = TIntByteHashMap.this._set;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] != 0 && set[i] != 2 && entry == values[i]) {
                    TIntByteHashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Byte)) {
                    return false;
                }
                final byte ele = (byte)element;
                if (!TIntByteHashMap.this.containsValue(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TByteCollection collection) {
            final TByteIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TIntByteHashMap.this.containsValue(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final byte[] array) {
            for (final byte element : array) {
                if (!TIntByteHashMap.this.containsValue(element)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean addAll(final Collection<? extends Byte> collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final TByteCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final byte[] array) {
            throw new UnsupportedOperationException();
        }
        
        public boolean retainAll(final Collection<?> collection) {
            boolean modified = false;
            final TByteIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final TByteCollection collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            final TByteIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final byte[] array) {
            boolean changed = false;
            Arrays.sort(array);
            final byte[] values = TIntByteHashMap.this._values;
            final byte[] states = TIntByteHashMap.this._states;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, values[i]) < 0) {
                    TIntByteHashMap.this.removeAt(i);
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final Collection<?> collection) {
            boolean changed = false;
            for (final Object element : collection) {
                if (element instanceof Byte) {
                    final byte c = (byte)element;
                    if (!this.remove(c)) {
                        continue;
                    }
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final TByteCollection collection) {
            if (this == collection) {
                this.clear();
                return true;
            }
            boolean changed = false;
            for (final byte element : collection) {
                if (this.remove(element)) {
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final byte[] array) {
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
            TIntByteHashMap.this.clear();
        }
        
        public boolean forEach(final TByteProcedure procedure) {
            return TIntByteHashMap.this.forEachValue(procedure);
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TIntByteHashMap.this.forEachValue(new TByteProcedure() {
                private boolean first = true;
                
                public boolean execute(final byte value) {
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
    }
    
    class TIntByteKeyHashIterator extends THashPrimitiveIterator implements TIntIterator
    {
        TIntByteKeyHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public int next() {
            this.moveToNextIndex();
            return TIntByteHashMap.this._set[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TIntByteHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TIntByteValueHashIterator extends THashPrimitiveIterator implements TByteIterator
    {
        TIntByteValueHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public byte next() {
            this.moveToNextIndex();
            return TIntByteHashMap.this._values[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TIntByteHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TIntByteHashIterator extends THashPrimitiveIterator implements TIntByteIterator
    {
        TIntByteHashIterator(final TIntByteHashMap map) {
            super(map);
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public int key() {
            return TIntByteHashMap.this._set[this._index];
        }
        
        public byte value() {
            return TIntByteHashMap.this._values[this._index];
        }
        
        public byte setValue(final byte val) {
            final byte old = this.value();
            TIntByteHashMap.this._values[this._index] = val;
            return old;
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TIntByteHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
}
