// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.hash;

import java.util.ConcurrentModificationException;
import gnu.trove.impl.hash.THashPrimitiveIterator;
import gnu.trove.iterator.TCharIterator;
import gnu.trove.TFloatCollection;
import java.util.Collection;
import gnu.trove.impl.hash.TPrimitiveHash;
import gnu.trove.iterator.TFloatIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.function.TCharFunction;
import gnu.trove.procedure.TFloatCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.TCharCollection;
import gnu.trove.set.TFloatSet;
import gnu.trove.iterator.TFloatCharIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.io.Externalizable;
import gnu.trove.map.TFloatCharMap;
import gnu.trove.impl.hash.TFloatCharHash;

public class TFloatCharHashMap extends TFloatCharHash implements TFloatCharMap, Externalizable
{
    static final long serialVersionUID = 1L;
    protected transient char[] _values;
    
    public TFloatCharHashMap() {
    }
    
    public TFloatCharHashMap(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public TFloatCharHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public TFloatCharHashMap(final int initialCapacity, final float loadFactor, final float noEntryKey, final char noEntryValue) {
        super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
    }
    
    public TFloatCharHashMap(final float[] keys, final char[] values) {
        super(Math.max(keys.length, values.length));
        for (int size = Math.min(keys.length, values.length), i = 0; i < size; ++i) {
            this.put(keys[i], values[i]);
        }
    }
    
    public TFloatCharHashMap(final TFloatCharMap map) {
        super(map.size());
        if (map instanceof TFloatCharHashMap) {
            final TFloatCharHashMap hashmap = (TFloatCharHashMap)map;
            this._loadFactor = hashmap._loadFactor;
            this.no_entry_key = hashmap.no_entry_key;
            this.no_entry_value = hashmap.no_entry_value;
            if (this.no_entry_key != 0.0f) {
                Arrays.fill(this._set, this.no_entry_key);
            }
            if (this.no_entry_value != '\0') {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(map);
    }
    
    protected int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._values = new char[capacity];
        return capacity;
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final float[] oldKeys = this._set;
        final char[] oldVals = this._values;
        final byte[] oldStates = this._states;
        this._set = new float[newCapacity];
        this._values = new char[newCapacity];
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
    
    public char put(final float key, final char value) {
        final int index = this.insertKey(key);
        return this.doPut(key, value, index);
    }
    
    public char putIfAbsent(final float key, final char value) {
        final int index = this.insertKey(key);
        if (index < 0) {
            return this._values[-index - 1];
        }
        return this.doPut(key, value, index);
    }
    
    private char doPut(final float key, final char value, int index) {
        char previous = this.no_entry_value;
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
    
    public void putAll(final Map<? extends Float, ? extends Character> map) {
        this.ensureCapacity(map.size());
        for (final Map.Entry<? extends Float, ? extends Character> entry : map.entrySet()) {
            this.put((float)entry.getKey(), (char)entry.getValue());
        }
    }
    
    public void putAll(final TFloatCharMap map) {
        this.ensureCapacity(map.size());
        final TFloatCharIterator iter = map.iterator();
        while (iter.hasNext()) {
            iter.advance();
            this.put(iter.key(), iter.value());
        }
    }
    
    public char get(final float key) {
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
    
    public char remove(final float key) {
        char prev = this.no_entry_value;
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
    
    public TCharCollection valueCollection() {
        return new TValueView();
    }
    
    public char[] values() {
        final char[] vals = new char[this.size()];
        final char[] v = this._values;
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
    
    public char[] values(char[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new char[size];
        }
        final char[] v = this._values;
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
    
    public boolean containsValue(final char val) {
        final byte[] states = this._states;
        final char[] vals = this._values;
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
    
    public TFloatCharIterator iterator() {
        return new TFloatCharHashIterator(this);
    }
    
    public boolean forEachKey(final TFloatProcedure procedure) {
        return this.forEach(procedure);
    }
    
    public boolean forEachValue(final TCharProcedure procedure) {
        final byte[] states = this._states;
        final char[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachEntry(final TFloatCharProcedure procedure) {
        final byte[] states = this._states;
        final float[] keys = this._set;
        final char[] values = this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public void transformValues(final TCharFunction function) {
        final byte[] states = this._states;
        final char[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                values[i] = function.execute(values[i]);
            }
        }
    }
    
    public boolean retainEntries(final TFloatCharProcedure procedure) {
        boolean modified = false;
        final byte[] states = this._states;
        final float[] keys = this._set;
        final char[] values = this._values;
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
        return this.adjustValue(key, '\u0001');
    }
    
    public boolean adjustValue(final float key, final char amount) {
        final int index = this.index(key);
        if (index < 0) {
            return false;
        }
        final char[] values = this._values;
        final int n = index;
        values[n] += amount;
        return true;
    }
    
    public char adjustOrPutValue(final float key, final char adjust_amount, final char put_amount) {
        int index = this.insertKey(key);
        char newValue;
        boolean isNewMapping;
        if (index < 0) {
            index = -index - 1;
            final char[] values = this._values;
            final int n = index;
            final char c = (char)(values[n] + adjust_amount);
            values[n] = c;
            newValue = c;
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
        if (!(other instanceof TFloatCharMap)) {
            return false;
        }
        final TFloatCharMap that = (TFloatCharMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        final char[] values = this._values;
        final byte[] states = this._states;
        final char this_no_entry_value = this.getNoEntryValue();
        final char that_no_entry_value = that.getNoEntryValue();
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                final float key = this._set[i];
                final char that_value = that.get(key);
                final char this_value = values[i];
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
        this.forEachEntry(new TFloatCharProcedure() {
            private boolean first = true;
            
            public boolean execute(final float key, final char value) {
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
                out.writeChar(this._values[i]);
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
            final char val = in.readChar();
            this.put(key, val);
        }
    }
    
    protected class TKeyView implements TFloatSet
    {
        public TFloatIterator iterator() {
            return new TFloatCharKeyHashIterator(TFloatCharHashMap.this);
        }
        
        public float getNoEntryValue() {
            return TFloatCharHashMap.this.no_entry_key;
        }
        
        public int size() {
            return TFloatCharHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TFloatCharHashMap.this._size;
        }
        
        public boolean contains(final float entry) {
            return TFloatCharHashMap.this.contains(entry);
        }
        
        public float[] toArray() {
            return TFloatCharHashMap.this.keys();
        }
        
        public float[] toArray(final float[] dest) {
            return TFloatCharHashMap.this.keys(dest);
        }
        
        public boolean add(final float entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final float entry) {
            return TFloatCharHashMap.this.no_entry_value != TFloatCharHashMap.this.remove(entry);
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Float)) {
                    return false;
                }
                final float ele = (float)element;
                if (!TFloatCharHashMap.this.containsKey(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TFloatCollection collection) {
            final TFloatIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TFloatCharHashMap.this.containsKey(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final float[] array) {
            for (final float element : array) {
                if (!TFloatCharHashMap.this.contains(element)) {
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
            final float[] set = TFloatCharHashMap.this._set;
            final byte[] states = TFloatCharHashMap.this._states;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    TFloatCharHashMap.this.removeAt(i);
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
            TFloatCharHashMap.this.clear();
        }
        
        public boolean forEach(final TFloatProcedure procedure) {
            return TFloatCharHashMap.this.forEachKey(procedure);
        }
        
        public boolean equals(final Object other) {
            if (!(other instanceof TFloatSet)) {
                return false;
            }
            final TFloatSet that = (TFloatSet)other;
            if (that.size() != this.size()) {
                return false;
            }
            int i = TFloatCharHashMap.this._states.length;
            while (i-- > 0) {
                if (TFloatCharHashMap.this._states[i] == 1 && !that.contains(TFloatCharHashMap.this._set[i])) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hashcode = 0;
            int i = TFloatCharHashMap.this._states.length;
            while (i-- > 0) {
                if (TFloatCharHashMap.this._states[i] == 1) {
                    hashcode += HashFunctions.hash(TFloatCharHashMap.this._set[i]);
                }
            }
            return hashcode;
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TFloatCharHashMap.this.forEachKey(new TFloatProcedure() {
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
    
    protected class TValueView implements TCharCollection
    {
        public TCharIterator iterator() {
            return new TFloatCharValueHashIterator(TFloatCharHashMap.this);
        }
        
        public char getNoEntryValue() {
            return TFloatCharHashMap.this.no_entry_value;
        }
        
        public int size() {
            return TFloatCharHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TFloatCharHashMap.this._size;
        }
        
        public boolean contains(final char entry) {
            return TFloatCharHashMap.this.containsValue(entry);
        }
        
        public char[] toArray() {
            return TFloatCharHashMap.this.values();
        }
        
        public char[] toArray(final char[] dest) {
            return TFloatCharHashMap.this.values(dest);
        }
        
        public boolean add(final char entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final char entry) {
            final char[] values = TFloatCharHashMap.this._values;
            final float[] set = TFloatCharHashMap.this._set;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] != 0.0f && set[i] != 2.0f && entry == values[i]) {
                    TFloatCharHashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Character)) {
                    return false;
                }
                final char ele = (char)element;
                if (!TFloatCharHashMap.this.containsValue(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TCharCollection collection) {
            final TCharIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TFloatCharHashMap.this.containsValue(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final char[] array) {
            for (final char element : array) {
                if (!TFloatCharHashMap.this.containsValue(element)) {
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
            final char[] values = TFloatCharHashMap.this._values;
            final byte[] states = TFloatCharHashMap.this._states;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, values[i]) < 0) {
                    TFloatCharHashMap.this.removeAt(i);
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
            if (this == collection) {
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
            TFloatCharHashMap.this.clear();
        }
        
        public boolean forEach(final TCharProcedure procedure) {
            return TFloatCharHashMap.this.forEachValue(procedure);
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TFloatCharHashMap.this.forEachValue(new TCharProcedure() {
                private boolean first = true;
                
                public boolean execute(final char value) {
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
    
    class TFloatCharKeyHashIterator extends THashPrimitiveIterator implements TFloatIterator
    {
        TFloatCharKeyHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public float next() {
            this.moveToNextIndex();
            return TFloatCharHashMap.this._set[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TFloatCharHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TFloatCharValueHashIterator extends THashPrimitiveIterator implements TCharIterator
    {
        TFloatCharValueHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public char next() {
            this.moveToNextIndex();
            return TFloatCharHashMap.this._values[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TFloatCharHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TFloatCharHashIterator extends THashPrimitiveIterator implements TFloatCharIterator
    {
        TFloatCharHashIterator(final TFloatCharHashMap map) {
            super(map);
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public float key() {
            return TFloatCharHashMap.this._set[this._index];
        }
        
        public char value() {
            return TFloatCharHashMap.this._values[this._index];
        }
        
        public char setValue(final char val) {
            final char old = this.value();
            TFloatCharHashMap.this._values[this._index] = val;
            return old;
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TFloatCharHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
}
