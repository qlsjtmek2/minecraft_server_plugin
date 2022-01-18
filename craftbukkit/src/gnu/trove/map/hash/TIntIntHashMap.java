// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.hash;

import java.util.ConcurrentModificationException;
import gnu.trove.impl.hash.THashPrimitiveIterator;
import java.util.Collection;
import gnu.trove.impl.hash.TPrimitiveHash;
import gnu.trove.iterator.TIntIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TIntIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.TIntCollection;
import gnu.trove.set.TIntSet;
import gnu.trove.iterator.TIntIntIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.io.Externalizable;
import gnu.trove.map.TIntIntMap;
import gnu.trove.impl.hash.TIntIntHash;

public class TIntIntHashMap extends TIntIntHash implements TIntIntMap, Externalizable
{
    static final long serialVersionUID = 1L;
    protected transient int[] _values;
    
    public TIntIntHashMap() {
    }
    
    public TIntIntHashMap(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public TIntIntHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public TIntIntHashMap(final int initialCapacity, final float loadFactor, final int noEntryKey, final int noEntryValue) {
        super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
    }
    
    public TIntIntHashMap(final int[] keys, final int[] values) {
        super(Math.max(keys.length, values.length));
        for (int size = Math.min(keys.length, values.length), i = 0; i < size; ++i) {
            this.put(keys[i], values[i]);
        }
    }
    
    public TIntIntHashMap(final TIntIntMap map) {
        super(map.size());
        if (map instanceof TIntIntHashMap) {
            final TIntIntHashMap hashmap = (TIntIntHashMap)map;
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
        this._values = new int[capacity];
        return capacity;
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final int[] oldKeys = this._set;
        final int[] oldVals = this._values;
        final byte[] oldStates = this._states;
        this._set = new int[newCapacity];
        this._values = new int[newCapacity];
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
    
    public int put(final int key, final int value) {
        final int index = this.insertKey(key);
        return this.doPut(key, value, index);
    }
    
    public int putIfAbsent(final int key, final int value) {
        final int index = this.insertKey(key);
        if (index < 0) {
            return this._values[-index - 1];
        }
        return this.doPut(key, value, index);
    }
    
    private int doPut(final int key, final int value, int index) {
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
    
    public void putAll(final Map<? extends Integer, ? extends Integer> map) {
        this.ensureCapacity(map.size());
        for (final Map.Entry<? extends Integer, ? extends Integer> entry : map.entrySet()) {
            this.put((int)entry.getKey(), (int)entry.getValue());
        }
    }
    
    public void putAll(final TIntIntMap map) {
        this.ensureCapacity(map.size());
        final TIntIntIterator iter = map.iterator();
        while (iter.hasNext()) {
            iter.advance();
            this.put(iter.key(), iter.value());
        }
    }
    
    public int get(final int key) {
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
    
    public int remove(final int key) {
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
    
    public TIntCollection valueCollection() {
        return new TValueView();
    }
    
    public int[] values() {
        final int[] vals = new int[this.size()];
        final int[] v = this._values;
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
    
    public int[] values(int[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new int[size];
        }
        final int[] v = this._values;
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
    
    public boolean containsValue(final int val) {
        final byte[] states = this._states;
        final int[] vals = this._values;
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
    
    public TIntIntIterator iterator() {
        return new TIntIntHashIterator(this);
    }
    
    public boolean forEachKey(final TIntProcedure procedure) {
        return this.forEach(procedure);
    }
    
    public boolean forEachValue(final TIntProcedure procedure) {
        final byte[] states = this._states;
        final int[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachEntry(final TIntIntProcedure procedure) {
        final byte[] states = this._states;
        final int[] keys = this._set;
        final int[] values = this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public void transformValues(final TIntFunction function) {
        final byte[] states = this._states;
        final int[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                values[i] = function.execute(values[i]);
            }
        }
    }
    
    public boolean retainEntries(final TIntIntProcedure procedure) {
        boolean modified = false;
        final byte[] states = this._states;
        final int[] keys = this._set;
        final int[] values = this._values;
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
        return this.adjustValue(key, 1);
    }
    
    public boolean adjustValue(final int key, final int amount) {
        final int index = this.index(key);
        if (index < 0) {
            return false;
        }
        final int[] values = this._values;
        final int n = index;
        values[n] += amount;
        return true;
    }
    
    public int adjustOrPutValue(final int key, final int adjust_amount, final int put_amount) {
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
        final byte previousState = this._states[index];
        if (isNewMapping) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return newValue;
    }
    
    public boolean equals(final Object other) {
        if (!(other instanceof TIntIntMap)) {
            return false;
        }
        final TIntIntMap that = (TIntIntMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        final int[] values = this._values;
        final byte[] states = this._states;
        final int this_no_entry_value = this.getNoEntryValue();
        final int that_no_entry_value = that.getNoEntryValue();
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                final int key = this._set[i];
                final int that_value = that.get(key);
                final int this_value = values[i];
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
        this.forEachEntry(new TIntIntProcedure() {
            private boolean first = true;
            
            public boolean execute(final int key, final int value) {
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
                out.writeInt(this._values[i]);
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
            final int val = in.readInt();
            this.put(key, val);
        }
    }
    
    protected class TKeyView implements TIntSet
    {
        public TIntIterator iterator() {
            return new TIntIntKeyHashIterator(TIntIntHashMap.this);
        }
        
        public int getNoEntryValue() {
            return TIntIntHashMap.this.no_entry_key;
        }
        
        public int size() {
            return TIntIntHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TIntIntHashMap.this._size;
        }
        
        public boolean contains(final int entry) {
            return TIntIntHashMap.this.contains(entry);
        }
        
        public int[] toArray() {
            return TIntIntHashMap.this.keys();
        }
        
        public int[] toArray(final int[] dest) {
            return TIntIntHashMap.this.keys(dest);
        }
        
        public boolean add(final int entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final int entry) {
            return TIntIntHashMap.this.no_entry_value != TIntIntHashMap.this.remove(entry);
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Integer)) {
                    return false;
                }
                final int ele = (int)element;
                if (!TIntIntHashMap.this.containsKey(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TIntCollection collection) {
            final TIntIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TIntIntHashMap.this.containsKey(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final int[] array) {
            for (final int element : array) {
                if (!TIntIntHashMap.this.contains(element)) {
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
            final int[] set = TIntIntHashMap.this._set;
            final byte[] states = TIntIntHashMap.this._states;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    TIntIntHashMap.this.removeAt(i);
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
            TIntIntHashMap.this.clear();
        }
        
        public boolean forEach(final TIntProcedure procedure) {
            return TIntIntHashMap.this.forEachKey(procedure);
        }
        
        public boolean equals(final Object other) {
            if (!(other instanceof TIntSet)) {
                return false;
            }
            final TIntSet that = (TIntSet)other;
            if (that.size() != this.size()) {
                return false;
            }
            int i = TIntIntHashMap.this._states.length;
            while (i-- > 0) {
                if (TIntIntHashMap.this._states[i] == 1 && !that.contains(TIntIntHashMap.this._set[i])) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hashcode = 0;
            int i = TIntIntHashMap.this._states.length;
            while (i-- > 0) {
                if (TIntIntHashMap.this._states[i] == 1) {
                    hashcode += HashFunctions.hash(TIntIntHashMap.this._set[i]);
                }
            }
            return hashcode;
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TIntIntHashMap.this.forEachKey(new TIntProcedure() {
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
    
    protected class TValueView implements TIntCollection
    {
        public TIntIterator iterator() {
            return new TIntIntValueHashIterator(TIntIntHashMap.this);
        }
        
        public int getNoEntryValue() {
            return TIntIntHashMap.this.no_entry_value;
        }
        
        public int size() {
            return TIntIntHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TIntIntHashMap.this._size;
        }
        
        public boolean contains(final int entry) {
            return TIntIntHashMap.this.containsValue(entry);
        }
        
        public int[] toArray() {
            return TIntIntHashMap.this.values();
        }
        
        public int[] toArray(final int[] dest) {
            return TIntIntHashMap.this.values(dest);
        }
        
        public boolean add(final int entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final int entry) {
            final int[] values = TIntIntHashMap.this._values;
            final int[] set = TIntIntHashMap.this._set;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] != 0 && set[i] != 2 && entry == values[i]) {
                    TIntIntHashMap.this.removeAt(i);
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
                if (!TIntIntHashMap.this.containsValue(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TIntCollection collection) {
            final TIntIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TIntIntHashMap.this.containsValue(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final int[] array) {
            for (final int element : array) {
                if (!TIntIntHashMap.this.containsValue(element)) {
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
            final int[] values = TIntIntHashMap.this._values;
            final byte[] states = TIntIntHashMap.this._states;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, values[i]) < 0) {
                    TIntIntHashMap.this.removeAt(i);
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
            TIntIntHashMap.this.clear();
        }
        
        public boolean forEach(final TIntProcedure procedure) {
            return TIntIntHashMap.this.forEachValue(procedure);
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TIntIntHashMap.this.forEachValue(new TIntProcedure() {
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
    }
    
    class TIntIntKeyHashIterator extends THashPrimitiveIterator implements TIntIterator
    {
        TIntIntKeyHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public int next() {
            this.moveToNextIndex();
            return TIntIntHashMap.this._set[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TIntIntHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TIntIntValueHashIterator extends THashPrimitiveIterator implements TIntIterator
    {
        TIntIntValueHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public int next() {
            this.moveToNextIndex();
            return TIntIntHashMap.this._values[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TIntIntHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TIntIntHashIterator extends THashPrimitiveIterator implements TIntIntIterator
    {
        TIntIntHashIterator(final TIntIntHashMap map) {
            super(map);
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public int key() {
            return TIntIntHashMap.this._set[this._index];
        }
        
        public int value() {
            return TIntIntHashMap.this._values[this._index];
        }
        
        public int setValue(final int val) {
            final int old = this.value();
            TIntIntHashMap.this._values[this._index] = val;
            return old;
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TIntIntHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
}
