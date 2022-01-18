// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.hash;

import java.util.ConcurrentModificationException;
import gnu.trove.impl.hash.THashPrimitiveIterator;
import gnu.trove.iterator.TLongIterator;
import gnu.trove.TByteCollection;
import java.util.Collection;
import gnu.trove.impl.hash.TPrimitiveHash;
import gnu.trove.iterator.TByteIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.function.TLongFunction;
import gnu.trove.procedure.TByteLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.TLongCollection;
import gnu.trove.set.TByteSet;
import gnu.trove.iterator.TByteLongIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.io.Externalizable;
import gnu.trove.map.TByteLongMap;
import gnu.trove.impl.hash.TByteLongHash;

public class TByteLongHashMap extends TByteLongHash implements TByteLongMap, Externalizable
{
    static final long serialVersionUID = 1L;
    protected transient long[] _values;
    
    public TByteLongHashMap() {
    }
    
    public TByteLongHashMap(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public TByteLongHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public TByteLongHashMap(final int initialCapacity, final float loadFactor, final byte noEntryKey, final long noEntryValue) {
        super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
    }
    
    public TByteLongHashMap(final byte[] keys, final long[] values) {
        super(Math.max(keys.length, values.length));
        for (int size = Math.min(keys.length, values.length), i = 0; i < size; ++i) {
            this.put(keys[i], values[i]);
        }
    }
    
    public TByteLongHashMap(final TByteLongMap map) {
        super(map.size());
        if (map instanceof TByteLongHashMap) {
            final TByteLongHashMap hashmap = (TByteLongHashMap)map;
            this._loadFactor = hashmap._loadFactor;
            this.no_entry_key = hashmap.no_entry_key;
            this.no_entry_value = hashmap.no_entry_value;
            if (this.no_entry_key != 0) {
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
        final byte[] oldKeys = this._set;
        final long[] oldVals = this._values;
        final byte[] oldStates = this._states;
        this._set = new byte[newCapacity];
        this._values = new long[newCapacity];
        this._states = new byte[newCapacity];
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldStates[i] == 1) {
                final byte o = oldKeys[i];
                final int index = this.insertKey(o);
                this._values[index] = oldVals[i];
            }
        }
    }
    
    public long put(final byte key, final long value) {
        final int index = this.insertKey(key);
        return this.doPut(key, value, index);
    }
    
    public long putIfAbsent(final byte key, final long value) {
        final int index = this.insertKey(key);
        if (index < 0) {
            return this._values[-index - 1];
        }
        return this.doPut(key, value, index);
    }
    
    private long doPut(final byte key, final long value, int index) {
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
    
    public void putAll(final Map<? extends Byte, ? extends Long> map) {
        this.ensureCapacity(map.size());
        for (final Map.Entry<? extends Byte, ? extends Long> entry : map.entrySet()) {
            this.put((byte)entry.getKey(), (long)entry.getValue());
        }
    }
    
    public void putAll(final TByteLongMap map) {
        this.ensureCapacity(map.size());
        final TByteLongIterator iter = map.iterator();
        while (iter.hasNext()) {
            iter.advance();
            this.put(iter.key(), iter.value());
        }
    }
    
    public long get(final byte key) {
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
    
    public long remove(final byte key) {
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
    
    public TByteSet keySet() {
        return new TKeyView();
    }
    
    public byte[] keys() {
        final byte[] keys = new byte[this.size()];
        final byte[] k = this._set;
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
    
    public byte[] keys(byte[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new byte[size];
        }
        final byte[] keys = this._set;
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
    
    public boolean containsKey(final byte key) {
        return this.contains(key);
    }
    
    public TByteLongIterator iterator() {
        return new TByteLongHashIterator(this);
    }
    
    public boolean forEachKey(final TByteProcedure procedure) {
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
    
    public boolean forEachEntry(final TByteLongProcedure procedure) {
        final byte[] states = this._states;
        final byte[] keys = this._set;
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
    
    public boolean retainEntries(final TByteLongProcedure procedure) {
        boolean modified = false;
        final byte[] states = this._states;
        final byte[] keys = this._set;
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
    
    public boolean increment(final byte key) {
        return this.adjustValue(key, 1L);
    }
    
    public boolean adjustValue(final byte key, final long amount) {
        final int index = this.index(key);
        if (index < 0) {
            return false;
        }
        final long[] values = this._values;
        final int n = index;
        values[n] += amount;
        return true;
    }
    
    public long adjustOrPutValue(final byte key, final long adjust_amount, final long put_amount) {
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
        if (!(other instanceof TByteLongMap)) {
            return false;
        }
        final TByteLongMap that = (TByteLongMap)other;
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
                final byte key = this._set[i];
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
        this.forEachEntry(new TByteLongProcedure() {
            private boolean first = true;
            
            public boolean execute(final byte key, final long value) {
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
                out.writeByte(this._set[i]);
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
            final byte key = in.readByte();
            final long val = in.readLong();
            this.put(key, val);
        }
    }
    
    protected class TKeyView implements TByteSet
    {
        public TByteIterator iterator() {
            return new TByteLongKeyHashIterator(TByteLongHashMap.this);
        }
        
        public byte getNoEntryValue() {
            return TByteLongHashMap.this.no_entry_key;
        }
        
        public int size() {
            return TByteLongHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TByteLongHashMap.this._size;
        }
        
        public boolean contains(final byte entry) {
            return TByteLongHashMap.this.contains(entry);
        }
        
        public byte[] toArray() {
            return TByteLongHashMap.this.keys();
        }
        
        public byte[] toArray(final byte[] dest) {
            return TByteLongHashMap.this.keys(dest);
        }
        
        public boolean add(final byte entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final byte entry) {
            return TByteLongHashMap.this.no_entry_value != TByteLongHashMap.this.remove(entry);
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Byte)) {
                    return false;
                }
                final byte ele = (byte)element;
                if (!TByteLongHashMap.this.containsKey(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TByteCollection collection) {
            final TByteIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TByteLongHashMap.this.containsKey(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final byte[] array) {
            for (final byte element : array) {
                if (!TByteLongHashMap.this.contains(element)) {
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
            final byte[] set = TByteLongHashMap.this._set;
            final byte[] states = TByteLongHashMap.this._states;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    TByteLongHashMap.this.removeAt(i);
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
            TByteLongHashMap.this.clear();
        }
        
        public boolean forEach(final TByteProcedure procedure) {
            return TByteLongHashMap.this.forEachKey(procedure);
        }
        
        public boolean equals(final Object other) {
            if (!(other instanceof TByteSet)) {
                return false;
            }
            final TByteSet that = (TByteSet)other;
            if (that.size() != this.size()) {
                return false;
            }
            int i = TByteLongHashMap.this._states.length;
            while (i-- > 0) {
                if (TByteLongHashMap.this._states[i] == 1 && !that.contains(TByteLongHashMap.this._set[i])) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hashcode = 0;
            int i = TByteLongHashMap.this._states.length;
            while (i-- > 0) {
                if (TByteLongHashMap.this._states[i] == 1) {
                    hashcode += HashFunctions.hash(TByteLongHashMap.this._set[i]);
                }
            }
            return hashcode;
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TByteLongHashMap.this.forEachKey(new TByteProcedure() {
                private boolean first = true;
                
                public boolean execute(final byte key) {
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
            return new TByteLongValueHashIterator(TByteLongHashMap.this);
        }
        
        public long getNoEntryValue() {
            return TByteLongHashMap.this.no_entry_value;
        }
        
        public int size() {
            return TByteLongHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TByteLongHashMap.this._size;
        }
        
        public boolean contains(final long entry) {
            return TByteLongHashMap.this.containsValue(entry);
        }
        
        public long[] toArray() {
            return TByteLongHashMap.this.values();
        }
        
        public long[] toArray(final long[] dest) {
            return TByteLongHashMap.this.values(dest);
        }
        
        public boolean add(final long entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final long entry) {
            final long[] values = TByteLongHashMap.this._values;
            final byte[] set = TByteLongHashMap.this._set;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] != 0 && set[i] != 2 && entry == values[i]) {
                    TByteLongHashMap.this.removeAt(i);
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
                if (!TByteLongHashMap.this.containsValue(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TLongCollection collection) {
            final TLongIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TByteLongHashMap.this.containsValue(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final long[] array) {
            for (final long element : array) {
                if (!TByteLongHashMap.this.containsValue(element)) {
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
            final long[] values = TByteLongHashMap.this._values;
            final byte[] states = TByteLongHashMap.this._states;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, values[i]) < 0) {
                    TByteLongHashMap.this.removeAt(i);
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
            TByteLongHashMap.this.clear();
        }
        
        public boolean forEach(final TLongProcedure procedure) {
            return TByteLongHashMap.this.forEachValue(procedure);
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TByteLongHashMap.this.forEachValue(new TLongProcedure() {
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
    
    class TByteLongKeyHashIterator extends THashPrimitiveIterator implements TByteIterator
    {
        TByteLongKeyHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public byte next() {
            this.moveToNextIndex();
            return TByteLongHashMap.this._set[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TByteLongHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TByteLongValueHashIterator extends THashPrimitiveIterator implements TLongIterator
    {
        TByteLongValueHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public long next() {
            this.moveToNextIndex();
            return TByteLongHashMap.this._values[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TByteLongHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TByteLongHashIterator extends THashPrimitiveIterator implements TByteLongIterator
    {
        TByteLongHashIterator(final TByteLongHashMap map) {
            super(map);
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public byte key() {
            return TByteLongHashMap.this._set[this._index];
        }
        
        public long value() {
            return TByteLongHashMap.this._values[this._index];
        }
        
        public long setValue(final long val) {
            final long old = this.value();
            TByteLongHashMap.this._values[this._index] = val;
            return old;
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TByteLongHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
}
