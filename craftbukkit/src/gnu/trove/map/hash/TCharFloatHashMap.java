// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.hash;

import java.util.ConcurrentModificationException;
import gnu.trove.impl.hash.THashPrimitiveIterator;
import gnu.trove.iterator.TFloatIterator;
import gnu.trove.TCharCollection;
import java.util.Collection;
import gnu.trove.impl.hash.TPrimitiveHash;
import gnu.trove.iterator.TCharIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.function.TFloatFunction;
import gnu.trove.procedure.TCharFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.TFloatCollection;
import gnu.trove.set.TCharSet;
import gnu.trove.iterator.TCharFloatIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.io.Externalizable;
import gnu.trove.map.TCharFloatMap;
import gnu.trove.impl.hash.TCharFloatHash;

public class TCharFloatHashMap extends TCharFloatHash implements TCharFloatMap, Externalizable
{
    static final long serialVersionUID = 1L;
    protected transient float[] _values;
    
    public TCharFloatHashMap() {
    }
    
    public TCharFloatHashMap(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public TCharFloatHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public TCharFloatHashMap(final int initialCapacity, final float loadFactor, final char noEntryKey, final float noEntryValue) {
        super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
    }
    
    public TCharFloatHashMap(final char[] keys, final float[] values) {
        super(Math.max(keys.length, values.length));
        for (int size = Math.min(keys.length, values.length), i = 0; i < size; ++i) {
            this.put(keys[i], values[i]);
        }
    }
    
    public TCharFloatHashMap(final TCharFloatMap map) {
        super(map.size());
        if (map instanceof TCharFloatHashMap) {
            final TCharFloatHashMap hashmap = (TCharFloatHashMap)map;
            this._loadFactor = hashmap._loadFactor;
            this.no_entry_key = hashmap.no_entry_key;
            this.no_entry_value = hashmap.no_entry_value;
            if (this.no_entry_key != '\0') {
                Arrays.fill(this._set, this.no_entry_key);
            }
            if (this.no_entry_value != 0.0f) {
                Arrays.fill(this._values, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.putAll(map);
    }
    
    protected int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._values = new float[capacity];
        return capacity;
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final char[] oldKeys = this._set;
        final float[] oldVals = this._values;
        final byte[] oldStates = this._states;
        this._set = new char[newCapacity];
        this._values = new float[newCapacity];
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
    
    public float put(final char key, final float value) {
        final int index = this.insertKey(key);
        return this.doPut(key, value, index);
    }
    
    public float putIfAbsent(final char key, final float value) {
        final int index = this.insertKey(key);
        if (index < 0) {
            return this._values[-index - 1];
        }
        return this.doPut(key, value, index);
    }
    
    private float doPut(final char key, final float value, int index) {
        float previous = this.no_entry_value;
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
    
    public void putAll(final Map<? extends Character, ? extends Float> map) {
        this.ensureCapacity(map.size());
        for (final Map.Entry<? extends Character, ? extends Float> entry : map.entrySet()) {
            this.put((char)entry.getKey(), (float)entry.getValue());
        }
    }
    
    public void putAll(final TCharFloatMap map) {
        this.ensureCapacity(map.size());
        final TCharFloatIterator iter = map.iterator();
        while (iter.hasNext()) {
            iter.advance();
            this.put(iter.key(), iter.value());
        }
    }
    
    public float get(final char key) {
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
    
    public float remove(final char key) {
        float prev = this.no_entry_value;
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
    
    public TCharSet keySet() {
        return new TKeyView();
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
    
    public char[] keys(char[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new char[size];
        }
        final char[] keys = this._set;
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
    
    public TFloatCollection valueCollection() {
        return new TValueView();
    }
    
    public float[] values() {
        final float[] vals = new float[this.size()];
        final float[] v = this._values;
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
    
    public float[] values(float[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new float[size];
        }
        final float[] v = this._values;
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
    
    public boolean containsValue(final float val) {
        final byte[] states = this._states;
        final float[] vals = this._values;
        int i = vals.length;
        while (i-- > 0) {
            if (states[i] == 1 && val == vals[i]) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsKey(final char key) {
        return this.contains(key);
    }
    
    public TCharFloatIterator iterator() {
        return new TCharFloatHashIterator(this);
    }
    
    public boolean forEachKey(final TCharProcedure procedure) {
        return this.forEach(procedure);
    }
    
    public boolean forEachValue(final TFloatProcedure procedure) {
        final byte[] states = this._states;
        final float[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachEntry(final TCharFloatProcedure procedure) {
        final byte[] states = this._states;
        final char[] keys = this._set;
        final float[] values = this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public void transformValues(final TFloatFunction function) {
        final byte[] states = this._states;
        final float[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                values[i] = function.execute(values[i]);
            }
        }
    }
    
    public boolean retainEntries(final TCharFloatProcedure procedure) {
        boolean modified = false;
        final byte[] states = this._states;
        final char[] keys = this._set;
        final float[] values = this._values;
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
    
    public boolean increment(final char key) {
        return this.adjustValue(key, 1.0f);
    }
    
    public boolean adjustValue(final char key, final float amount) {
        final int index = this.index(key);
        if (index < 0) {
            return false;
        }
        final float[] values = this._values;
        final int n = index;
        values[n] += amount;
        return true;
    }
    
    public float adjustOrPutValue(final char key, final float adjust_amount, final float put_amount) {
        int index = this.insertKey(key);
        float newValue;
        boolean isNewMapping;
        if (index < 0) {
            index = -index - 1;
            final float[] values = this._values;
            final int n = index;
            final float n2 = values[n] + adjust_amount;
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
        if (!(other instanceof TCharFloatMap)) {
            return false;
        }
        final TCharFloatMap that = (TCharFloatMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        final float[] values = this._values;
        final byte[] states = this._states;
        final float this_no_entry_value = this.getNoEntryValue();
        final float that_no_entry_value = that.getNoEntryValue();
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                final char key = this._set[i];
                final float that_value = that.get(key);
                final float this_value = values[i];
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
        this.forEachEntry(new TCharFloatProcedure() {
            private boolean first = true;
            
            public boolean execute(final char key, final float value) {
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
                out.writeChar(this._set[i]);
                out.writeFloat(this._values[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        int size = in.readInt();
        this.setUp(size);
        while (size-- > 0) {
            final char key = in.readChar();
            final float val = in.readFloat();
            this.put(key, val);
        }
    }
    
    protected class TKeyView implements TCharSet
    {
        public TCharIterator iterator() {
            return new TCharFloatKeyHashIterator(TCharFloatHashMap.this);
        }
        
        public char getNoEntryValue() {
            return TCharFloatHashMap.this.no_entry_key;
        }
        
        public int size() {
            return TCharFloatHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TCharFloatHashMap.this._size;
        }
        
        public boolean contains(final char entry) {
            return TCharFloatHashMap.this.contains(entry);
        }
        
        public char[] toArray() {
            return TCharFloatHashMap.this.keys();
        }
        
        public char[] toArray(final char[] dest) {
            return TCharFloatHashMap.this.keys(dest);
        }
        
        public boolean add(final char entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final char entry) {
            return TCharFloatHashMap.this.no_entry_value != TCharFloatHashMap.this.remove(entry);
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Character)) {
                    return false;
                }
                final char ele = (char)element;
                if (!TCharFloatHashMap.this.containsKey(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TCharCollection collection) {
            final TCharIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TCharFloatHashMap.this.containsKey(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final char[] array) {
            for (final char element : array) {
                if (!TCharFloatHashMap.this.contains(element)) {
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
            final char[] set = TCharFloatHashMap.this._set;
            final byte[] states = TCharFloatHashMap.this._states;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    TCharFloatHashMap.this.removeAt(i);
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
            TCharFloatHashMap.this.clear();
        }
        
        public boolean forEach(final TCharProcedure procedure) {
            return TCharFloatHashMap.this.forEachKey(procedure);
        }
        
        public boolean equals(final Object other) {
            if (!(other instanceof TCharSet)) {
                return false;
            }
            final TCharSet that = (TCharSet)other;
            if (that.size() != this.size()) {
                return false;
            }
            int i = TCharFloatHashMap.this._states.length;
            while (i-- > 0) {
                if (TCharFloatHashMap.this._states[i] == 1 && !that.contains(TCharFloatHashMap.this._set[i])) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hashcode = 0;
            int i = TCharFloatHashMap.this._states.length;
            while (i-- > 0) {
                if (TCharFloatHashMap.this._states[i] == 1) {
                    hashcode += HashFunctions.hash(TCharFloatHashMap.this._set[i]);
                }
            }
            return hashcode;
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TCharFloatHashMap.this.forEachKey(new TCharProcedure() {
                private boolean first = true;
                
                public boolean execute(final char key) {
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
    
    protected class TValueView implements TFloatCollection
    {
        public TFloatIterator iterator() {
            return new TCharFloatValueHashIterator(TCharFloatHashMap.this);
        }
        
        public float getNoEntryValue() {
            return TCharFloatHashMap.this.no_entry_value;
        }
        
        public int size() {
            return TCharFloatHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TCharFloatHashMap.this._size;
        }
        
        public boolean contains(final float entry) {
            return TCharFloatHashMap.this.containsValue(entry);
        }
        
        public float[] toArray() {
            return TCharFloatHashMap.this.values();
        }
        
        public float[] toArray(final float[] dest) {
            return TCharFloatHashMap.this.values(dest);
        }
        
        public boolean add(final float entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final float entry) {
            final float[] values = TCharFloatHashMap.this._values;
            final char[] set = TCharFloatHashMap.this._set;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] != '\0' && set[i] != '\u0002' && entry == values[i]) {
                    TCharFloatHashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Float)) {
                    return false;
                }
                final float ele = (float)element;
                if (!TCharFloatHashMap.this.containsValue(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TFloatCollection collection) {
            final TFloatIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TCharFloatHashMap.this.containsValue(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final float[] array) {
            for (final float element : array) {
                if (!TCharFloatHashMap.this.containsValue(element)) {
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
            final float[] values = TCharFloatHashMap.this._values;
            final byte[] states = TCharFloatHashMap.this._states;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, values[i]) < 0) {
                    TCharFloatHashMap.this.removeAt(i);
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
            TCharFloatHashMap.this.clear();
        }
        
        public boolean forEach(final TFloatProcedure procedure) {
            return TCharFloatHashMap.this.forEachValue(procedure);
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TCharFloatHashMap.this.forEachValue(new TFloatProcedure() {
                private boolean first = true;
                
                public boolean execute(final float value) {
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
    
    class TCharFloatKeyHashIterator extends THashPrimitiveIterator implements TCharIterator
    {
        TCharFloatKeyHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public char next() {
            this.moveToNextIndex();
            return TCharFloatHashMap.this._set[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TCharFloatHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TCharFloatValueHashIterator extends THashPrimitiveIterator implements TFloatIterator
    {
        TCharFloatValueHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public float next() {
            this.moveToNextIndex();
            return TCharFloatHashMap.this._values[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TCharFloatHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TCharFloatHashIterator extends THashPrimitiveIterator implements TCharFloatIterator
    {
        TCharFloatHashIterator(final TCharFloatHashMap map) {
            super(map);
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public char key() {
            return TCharFloatHashMap.this._set[this._index];
        }
        
        public float value() {
            return TCharFloatHashMap.this._values[this._index];
        }
        
        public float setValue(final float val) {
            final float old = this.value();
            TCharFloatHashMap.this._values[this._index] = val;
            return old;
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TCharFloatHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
}
