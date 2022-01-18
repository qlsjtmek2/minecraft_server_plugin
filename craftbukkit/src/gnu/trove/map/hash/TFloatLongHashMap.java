// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.hash;

import java.util.ConcurrentModificationException;
import gnu.trove.impl.hash.THashPrimitiveIterator;
import gnu.trove.iterator.TLongIterator;
import gnu.trove.TFloatCollection;
import java.util.Collection;
import gnu.trove.impl.hash.TPrimitiveHash;
import gnu.trove.iterator.TFloatIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.function.TLongFunction;
import gnu.trove.procedure.TFloatLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.TLongCollection;
import gnu.trove.set.TFloatSet;
import gnu.trove.iterator.TFloatLongIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.io.Externalizable;
import gnu.trove.map.TFloatLongMap;
import gnu.trove.impl.hash.TFloatLongHash;

public class TFloatLongHashMap extends TFloatLongHash implements TFloatLongMap, Externalizable
{
    static final long serialVersionUID = 1L;
    protected transient long[] _values;
    
    public TFloatLongHashMap() {
    }
    
    public TFloatLongHashMap(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public TFloatLongHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public TFloatLongHashMap(final int initialCapacity, final float loadFactor, final float noEntryKey, final long noEntryValue) {
        super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
    }
    
    public TFloatLongHashMap(final float[] keys, final long[] values) {
        super(Math.max(keys.length, values.length));
        for (int size = Math.min(keys.length, values.length), i = 0; i < size; ++i) {
            this.put(keys[i], values[i]);
        }
    }
    
    public TFloatLongHashMap(final TFloatLongMap map) {
        super(map.size());
        if (map instanceof TFloatLongHashMap) {
            final TFloatLongHashMap hashmap = (TFloatLongHashMap)map;
            this._loadFactor = hashmap._loadFactor;
            this.no_entry_key = hashmap.no_entry_key;
            this.no_entry_value = hashmap.no_entry_value;
            if (this.no_entry_key != 0.0f) {
                Arrays.fill(this._set, this.no_entry_key);
            }
            if (this.no_entry_value != 0L) {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(map);
    }
    
    protected int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._values = new long[capacity];
        return capacity;
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final float[] oldKeys = this._set;
        final long[] oldVals = this._values;
        final byte[] oldStates = this._states;
        this._set = new float[newCapacity];
        this._values = new long[newCapacity];
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
    
    public long put(final float key, final long value) {
        final int index = this.insertKey(key);
        return this.doPut(key, value, index);
    }
    
    public long putIfAbsent(final float key, final long value) {
        final int index = this.insertKey(key);
        if (index < 0) {
            return this._values[-index - 1];
        }
        return this.doPut(key, value, index);
    }
    
    private long doPut(final float key, final long value, int index) {
        long previous = this.no_entry_value;
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
    
    public void putAll(final Map<? extends Float, ? extends Long> map) {
        this.ensureCapacity(map.size());
        for (final Map.Entry<? extends Float, ? extends Long> entry : map.entrySet()) {
            this.put((float)entry.getKey(), (long)entry.getValue());
        }
    }
    
    public void putAll(final TFloatLongMap map) {
        this.ensureCapacity(map.size());
        final TFloatLongIterator iter = map.iterator();
        while (iter.hasNext()) {
            iter.advance();
            this.put(iter.key(), iter.value());
        }
    }
    
    public long get(final float key) {
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
    
    public long remove(final float key) {
        long prev = this.no_entry_value;
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
    
    public TFloatSet keySet() {
        return new TKeyView();
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
    
    public float[] keys(float[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new float[size];
        }
        final float[] keys = this._set;
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
    
    public TLongCollection valueCollection() {
        return new TValueView();
    }
    
    public long[] values() {
        final long[] vals = new long[this.size()];
        final long[] v = this._values;
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
    
    public long[] values(long[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new long[size];
        }
        final long[] v = this._values;
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
    
    public boolean containsValue(final long val) {
        final byte[] states = this._states;
        final long[] vals = this._values;
        int i = vals.length;
        while (i-- > 0) {
            if (states[i] == 1 && val == vals[i]) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsKey(final float key) {
        return this.contains(key);
    }
    
    public TFloatLongIterator iterator() {
        return new TFloatLongHashIterator(this);
    }
    
    public boolean forEachKey(final TFloatProcedure procedure) {
        return this.forEach(procedure);
    }
    
    public boolean forEachValue(final TLongProcedure procedure) {
        final byte[] states = this._states;
        final long[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachEntry(final TFloatLongProcedure procedure) {
        final byte[] states = this._states;
        final float[] keys = this._set;
        final long[] values = this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public void transformValues(final TLongFunction function) {
        final byte[] states = this._states;
        final long[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                values[i] = function.execute(values[i]);
            }
        }
    }
    
    public boolean retainEntries(final TFloatLongProcedure procedure) {
        boolean modified = false;
        final byte[] states = this._states;
        final float[] keys = this._set;
        final long[] values = this._values;
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
    
    public boolean increment(final float key) {
        return this.adjustValue(key, 1L);
    }
    
    public boolean adjustValue(final float key, final long amount) {
        final int index = this.index(key);
        if (index < 0) {
            return false;
        }
        final long[] values = this._values;
        final int n = index;
        values[n] += amount;
        return true;
    }
    
    public long adjustOrPutValue(final float key, final long adjust_amount, final long put_amount) {
        int index = this.insertKey(key);
        long newValue;
        boolean isNewMapping;
        if (index < 0) {
            index = -index - 1;
            final long[] values = this._values;
            final int n = index;
            final long n2 = values[n] + adjust_amount;
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
        if (!(other instanceof TFloatLongMap)) {
            return false;
        }
        final TFloatLongMap that = (TFloatLongMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        final long[] values = this._values;
        final byte[] states = this._states;
        final long this_no_entry_value = this.getNoEntryValue();
        final long that_no_entry_value = that.getNoEntryValue();
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                final float key = this._set[i];
                final long that_value = that.get(key);
                final long this_value = values[i];
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
        this.forEachEntry(new TFloatLongProcedure() {
            private boolean first = true;
            
            public boolean execute(final float key, final long value) {
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
                out.writeFloat(this._set[i]);
                out.writeLong(this._values[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        int size = in.readInt();
        this.setUp(size);
        while (size-- > 0) {
            final float key = in.readFloat();
            final long val = in.readLong();
            this.put(key, val);
        }
    }
    
    protected class TKeyView implements TFloatSet
    {
        public TFloatIterator iterator() {
            return new TFloatLongKeyHashIterator(TFloatLongHashMap.this);
        }
        
        public float getNoEntryValue() {
            return TFloatLongHashMap.this.no_entry_key;
        }
        
        public int size() {
            return TFloatLongHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TFloatLongHashMap.this._size;
        }
        
        public boolean contains(final float entry) {
            return TFloatLongHashMap.this.contains(entry);
        }
        
        public float[] toArray() {
            return TFloatLongHashMap.this.keys();
        }
        
        public float[] toArray(final float[] dest) {
            return TFloatLongHashMap.this.keys(dest);
        }
        
        public boolean add(final float entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final float entry) {
            return TFloatLongHashMap.this.no_entry_value != TFloatLongHashMap.this.remove(entry);
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Float)) {
                    return false;
                }
                final float ele = (float)element;
                if (!TFloatLongHashMap.this.containsKey(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TFloatCollection collection) {
            final TFloatIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TFloatLongHashMap.this.containsKey(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final float[] array) {
            for (final float element : array) {
                if (!TFloatLongHashMap.this.contains(element)) {
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
            final float[] set = TFloatLongHashMap.this._set;
            final byte[] states = TFloatLongHashMap.this._states;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    TFloatLongHashMap.this.removeAt(i);
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
            TFloatLongHashMap.this.clear();
        }
        
        public boolean forEach(final TFloatProcedure procedure) {
            return TFloatLongHashMap.this.forEachKey(procedure);
        }
        
        public boolean equals(final Object other) {
            if (!(other instanceof TFloatSet)) {
                return false;
            }
            final TFloatSet that = (TFloatSet)other;
            if (that.size() != this.size()) {
                return false;
            }
            int i = TFloatLongHashMap.this._states.length;
            while (i-- > 0) {
                if (TFloatLongHashMap.this._states[i] == 1 && !that.contains(TFloatLongHashMap.this._set[i])) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hashcode = 0;
            int i = TFloatLongHashMap.this._states.length;
            while (i-- > 0) {
                if (TFloatLongHashMap.this._states[i] == 1) {
                    hashcode += HashFunctions.hash(TFloatLongHashMap.this._set[i]);
                }
            }
            return hashcode;
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TFloatLongHashMap.this.forEachKey(new TFloatProcedure() {
                private boolean first = true;
                
                public boolean execute(final float key) {
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
    
    protected class TValueView implements TLongCollection
    {
        public TLongIterator iterator() {
            return new TFloatLongValueHashIterator(TFloatLongHashMap.this);
        }
        
        public long getNoEntryValue() {
            return TFloatLongHashMap.this.no_entry_value;
        }
        
        public int size() {
            return TFloatLongHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TFloatLongHashMap.this._size;
        }
        
        public boolean contains(final long entry) {
            return TFloatLongHashMap.this.containsValue(entry);
        }
        
        public long[] toArray() {
            return TFloatLongHashMap.this.values();
        }
        
        public long[] toArray(final long[] dest) {
            return TFloatLongHashMap.this.values(dest);
        }
        
        public boolean add(final long entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final long entry) {
            final long[] values = TFloatLongHashMap.this._values;
            final float[] set = TFloatLongHashMap.this._set;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] != 0.0f && set[i] != 2.0f && entry == values[i]) {
                    TFloatLongHashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Long)) {
                    return false;
                }
                final long ele = (long)element;
                if (!TFloatLongHashMap.this.containsValue(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TLongCollection collection) {
            final TLongIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TFloatLongHashMap.this.containsValue(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final long[] array) {
            for (final long element : array) {
                if (!TFloatLongHashMap.this.containsValue(element)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean addAll(final Collection<? extends Long> collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final TLongCollection collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final long[] array) {
            throw new UnsupportedOperationException();
        }
        
        public boolean retainAll(final Collection<?> collection) {
            boolean modified = false;
            final TLongIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final TLongCollection collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            final TLongIterator iter = this.iterator();
            while (iter.hasNext()) {
                if (!collection.contains(iter.next())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        public boolean retainAll(final long[] array) {
            boolean changed = false;
            Arrays.sort(array);
            final long[] values = TFloatLongHashMap.this._values;
            final byte[] states = TFloatLongHashMap.this._states;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, values[i]) < 0) {
                    TFloatLongHashMap.this.removeAt(i);
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final Collection<?> collection) {
            boolean changed = false;
            for (final Object element : collection) {
                if (element instanceof Long) {
                    final long c = (long)element;
                    if (!this.remove(c)) {
                        continue;
                    }
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final TLongCollection collection) {
            if (this == collection) {
                this.clear();
                return true;
            }
            boolean changed = false;
            for (final long element : collection) {
                if (this.remove(element)) {
                    changed = true;
                }
            }
            return changed;
        }
        
        public boolean removeAll(final long[] array) {
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
            TFloatLongHashMap.this.clear();
        }
        
        public boolean forEach(final TLongProcedure procedure) {
            return TFloatLongHashMap.this.forEachValue(procedure);
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TFloatLongHashMap.this.forEachValue(new TLongProcedure() {
                private boolean first = true;
                
                public boolean execute(final long value) {
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
    
    class TFloatLongKeyHashIterator extends THashPrimitiveIterator implements TFloatIterator
    {
        TFloatLongKeyHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public float next() {
            this.moveToNextIndex();
            return TFloatLongHashMap.this._set[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TFloatLongHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TFloatLongValueHashIterator extends THashPrimitiveIterator implements TLongIterator
    {
        TFloatLongValueHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public long next() {
            this.moveToNextIndex();
            return TFloatLongHashMap.this._values[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TFloatLongHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TFloatLongHashIterator extends THashPrimitiveIterator implements TFloatLongIterator
    {
        TFloatLongHashIterator(final TFloatLongHashMap map) {
            super(map);
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public float key() {
            return TFloatLongHashMap.this._set[this._index];
        }
        
        public long value() {
            return TFloatLongHashMap.this._values[this._index];
        }
        
        public long setValue(final long val) {
            final long old = this.value();
            TFloatLongHashMap.this._values[this._index] = val;
            return old;
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TFloatLongHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
}
