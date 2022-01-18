// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.hash;

import java.util.ConcurrentModificationException;
import gnu.trove.impl.hash.THashPrimitiveIterator;
import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.TByteCollection;
import java.util.Collection;
import gnu.trove.impl.hash.TPrimitiveHash;
import gnu.trove.iterator.TByteIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.function.TDoubleFunction;
import gnu.trove.procedure.TByteDoubleProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.TDoubleCollection;
import gnu.trove.set.TByteSet;
import gnu.trove.iterator.TByteDoubleIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.io.Externalizable;
import gnu.trove.map.TByteDoubleMap;
import gnu.trove.impl.hash.TByteDoubleHash;

public class TByteDoubleHashMap extends TByteDoubleHash implements TByteDoubleMap, Externalizable
{
    static final long serialVersionUID = 1L;
    protected transient double[] _values;
    
    public TByteDoubleHashMap() {
    }
    
    public TByteDoubleHashMap(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public TByteDoubleHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public TByteDoubleHashMap(final int initialCapacity, final float loadFactor, final byte noEntryKey, final double noEntryValue) {
        super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
    }
    
    public TByteDoubleHashMap(final byte[] keys, final double[] values) {
        super(Math.max(keys.length, values.length));
        for (int size = Math.min(keys.length, values.length), i = 0; i < size; ++i) {
            this.put(keys[i], values[i]);
        }
    }
    
    public TByteDoubleHashMap(final TByteDoubleMap map) {
        super(map.size());
        if (map instanceof TByteDoubleHashMap) {
            final TByteDoubleHashMap hashmap = (TByteDoubleHashMap)map;
            this._loadFactor = hashmap._loadFactor;
            this.no_entry_key = hashmap.no_entry_key;
            this.no_entry_value = hashmap.no_entry_value;
            if (this.no_entry_key != 0) {
                Arrays.fill(this._set, this.no_entry_key);
            }
            if (this.no_entry_value != 0.0) {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(map);
    }
    
    protected int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._values = new double[capacity];
        return capacity;
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final byte[] oldKeys = this._set;
        final double[] oldVals = this._values;
        final byte[] oldStates = this._states;
        this._set = new byte[newCapacity];
        this._values = new double[newCapacity];
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
    
    public double put(final byte key, final double value) {
        final int index = this.insertKey(key);
        return this.doPut(key, value, index);
    }
    
    public double putIfAbsent(final byte key, final double value) {
        final int index = this.insertKey(key);
        if (index < 0) {
            return this._values[-index - 1];
        }
        return this.doPut(key, value, index);
    }
    
    private double doPut(final byte key, final double value, int index) {
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
    
    public void putAll(final Map<? extends Byte, ? extends Double> map) {
        this.ensureCapacity(map.size());
        for (final Map.Entry<? extends Byte, ? extends Double> entry : map.entrySet()) {
            this.put((byte)entry.getKey(), (double)entry.getValue());
        }
    }
    
    public void putAll(final TByteDoubleMap map) {
        this.ensureCapacity(map.size());
        final TByteDoubleIterator iter = map.iterator();
        while (iter.hasNext()) {
            iter.advance();
            this.put(iter.key(), iter.value());
        }
    }
    
    public double get(final byte key) {
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
    
    public double remove(final byte key) {
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
    
    public TDoubleCollection valueCollection() {
        return new TValueView();
    }
    
    public double[] values() {
        final double[] vals = new double[this.size()];
        final double[] v = this._values;
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
    
    public double[] values(double[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new double[size];
        }
        final double[] v = this._values;
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
    
    public boolean containsValue(final double val) {
        final byte[] states = this._states;
        final double[] vals = this._values;
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
    
    public TByteDoubleIterator iterator() {
        return new TByteDoubleHashIterator(this);
    }
    
    public boolean forEachKey(final TByteProcedure procedure) {
        return this.forEach(procedure);
    }
    
    public boolean forEachValue(final TDoubleProcedure procedure) {
        final byte[] states = this._states;
        final double[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachEntry(final TByteDoubleProcedure procedure) {
        final byte[] states = this._states;
        final byte[] keys = this._set;
        final double[] values = this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public void transformValues(final TDoubleFunction function) {
        final byte[] states = this._states;
        final double[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                values[i] = function.execute(values[i]);
            }
        }
    }
    
    public boolean retainEntries(final TByteDoubleProcedure procedure) {
        boolean modified = false;
        final byte[] states = this._states;
        final byte[] keys = this._set;
        final double[] values = this._values;
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
        return this.adjustValue(key, 1.0);
    }
    
    public boolean adjustValue(final byte key, final double amount) {
        final int index = this.index(key);
        if (index < 0) {
            return false;
        }
        final double[] values = this._values;
        final int n = index;
        values[n] += amount;
        return true;
    }
    
    public double adjustOrPutValue(final byte key, final double adjust_amount, final double put_amount) {
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
        final byte previousState = this._states[index];
        if (isNewMapping) {
            this.postInsertHook(this.consumeFreeSlot);
        }
        return newValue;
    }
    
    public boolean equals(final Object other) {
        if (!(other instanceof TByteDoubleMap)) {
            return false;
        }
        final TByteDoubleMap that = (TByteDoubleMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        final double[] values = this._values;
        final byte[] states = this._states;
        final double this_no_entry_value = this.getNoEntryValue();
        final double that_no_entry_value = that.getNoEntryValue();
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                final byte key = this._set[i];
                final double that_value = that.get(key);
                final double this_value = values[i];
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
        this.forEachEntry(new TByteDoubleProcedure() {
            private boolean first = true;
            
            public boolean execute(final byte key, final double value) {
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
                out.writeDouble(this._values[i]);
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
            final double val = in.readDouble();
            this.put(key, val);
        }
    }
    
    protected class TKeyView implements TByteSet
    {
        public TByteIterator iterator() {
            return new TByteDoubleKeyHashIterator(TByteDoubleHashMap.this);
        }
        
        public byte getNoEntryValue() {
            return TByteDoubleHashMap.this.no_entry_key;
        }
        
        public int size() {
            return TByteDoubleHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TByteDoubleHashMap.this._size;
        }
        
        public boolean contains(final byte entry) {
            return TByteDoubleHashMap.this.contains(entry);
        }
        
        public byte[] toArray() {
            return TByteDoubleHashMap.this.keys();
        }
        
        public byte[] toArray(final byte[] dest) {
            return TByteDoubleHashMap.this.keys(dest);
        }
        
        public boolean add(final byte entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final byte entry) {
            return TByteDoubleHashMap.this.no_entry_value != TByteDoubleHashMap.this.remove(entry);
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Byte)) {
                    return false;
                }
                final byte ele = (byte)element;
                if (!TByteDoubleHashMap.this.containsKey(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TByteCollection collection) {
            final TByteIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TByteDoubleHashMap.this.containsKey(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final byte[] array) {
            for (final byte element : array) {
                if (!TByteDoubleHashMap.this.contains(element)) {
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
            final byte[] set = TByteDoubleHashMap.this._set;
            final byte[] states = TByteDoubleHashMap.this._states;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    TByteDoubleHashMap.this.removeAt(i);
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
            TByteDoubleHashMap.this.clear();
        }
        
        public boolean forEach(final TByteProcedure procedure) {
            return TByteDoubleHashMap.this.forEachKey(procedure);
        }
        
        public boolean equals(final Object other) {
            if (!(other instanceof TByteSet)) {
                return false;
            }
            final TByteSet that = (TByteSet)other;
            if (that.size() != this.size()) {
                return false;
            }
            int i = TByteDoubleHashMap.this._states.length;
            while (i-- > 0) {
                if (TByteDoubleHashMap.this._states[i] == 1 && !that.contains(TByteDoubleHashMap.this._set[i])) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hashcode = 0;
            int i = TByteDoubleHashMap.this._states.length;
            while (i-- > 0) {
                if (TByteDoubleHashMap.this._states[i] == 1) {
                    hashcode += HashFunctions.hash(TByteDoubleHashMap.this._set[i]);
                }
            }
            return hashcode;
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TByteDoubleHashMap.this.forEachKey(new TByteProcedure() {
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
    
    protected class TValueView implements TDoubleCollection
    {
        public TDoubleIterator iterator() {
            return new TByteDoubleValueHashIterator(TByteDoubleHashMap.this);
        }
        
        public double getNoEntryValue() {
            return TByteDoubleHashMap.this.no_entry_value;
        }
        
        public int size() {
            return TByteDoubleHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TByteDoubleHashMap.this._size;
        }
        
        public boolean contains(final double entry) {
            return TByteDoubleHashMap.this.containsValue(entry);
        }
        
        public double[] toArray() {
            return TByteDoubleHashMap.this.values();
        }
        
        public double[] toArray(final double[] dest) {
            return TByteDoubleHashMap.this.values(dest);
        }
        
        public boolean add(final double entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final double entry) {
            final double[] values = TByteDoubleHashMap.this._values;
            final byte[] set = TByteDoubleHashMap.this._set;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] != 0 && set[i] != 2 && entry == values[i]) {
                    TByteDoubleHashMap.this.removeAt(i);
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
                if (!TByteDoubleHashMap.this.containsValue(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TDoubleCollection collection) {
            final TDoubleIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TByteDoubleHashMap.this.containsValue(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final double[] array) {
            for (final double element : array) {
                if (!TByteDoubleHashMap.this.containsValue(element)) {
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
            final double[] values = TByteDoubleHashMap.this._values;
            final byte[] states = TByteDoubleHashMap.this._states;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, values[i]) < 0) {
                    TByteDoubleHashMap.this.removeAt(i);
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
            TByteDoubleHashMap.this.clear();
        }
        
        public boolean forEach(final TDoubleProcedure procedure) {
            return TByteDoubleHashMap.this.forEachValue(procedure);
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TByteDoubleHashMap.this.forEachValue(new TDoubleProcedure() {
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
    }
    
    class TByteDoubleKeyHashIterator extends THashPrimitiveIterator implements TByteIterator
    {
        TByteDoubleKeyHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public byte next() {
            this.moveToNextIndex();
            return TByteDoubleHashMap.this._set[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TByteDoubleHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TByteDoubleValueHashIterator extends THashPrimitiveIterator implements TDoubleIterator
    {
        TByteDoubleValueHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public double next() {
            this.moveToNextIndex();
            return TByteDoubleHashMap.this._values[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TByteDoubleHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TByteDoubleHashIterator extends THashPrimitiveIterator implements TByteDoubleIterator
    {
        TByteDoubleHashIterator(final TByteDoubleHashMap map) {
            super(map);
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public byte key() {
            return TByteDoubleHashMap.this._set[this._index];
        }
        
        public double value() {
            return TByteDoubleHashMap.this._values[this._index];
        }
        
        public double setValue(final double val) {
            final double old = this.value();
            TByteDoubleHashMap.this._values[this._index] = val;
            return old;
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TByteDoubleHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
}
