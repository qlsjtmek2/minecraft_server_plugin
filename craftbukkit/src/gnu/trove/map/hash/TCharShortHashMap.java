// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.map.hash;

import java.util.ConcurrentModificationException;
import gnu.trove.impl.hash.THashPrimitiveIterator;
import gnu.trove.iterator.TShortIterator;
import gnu.trove.TCharCollection;
import java.util.Collection;
import gnu.trove.impl.hash.TPrimitiveHash;
import gnu.trove.iterator.TCharIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.function.TShortFunction;
import gnu.trove.procedure.TCharShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.TShortCollection;
import gnu.trove.set.TCharSet;
import gnu.trove.iterator.TCharShortIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.io.Externalizable;
import gnu.trove.map.TCharShortMap;
import gnu.trove.impl.hash.TCharShortHash;

public class TCharShortHashMap extends TCharShortHash implements TCharShortMap, Externalizable
{
    static final long serialVersionUID = 1L;
    protected transient short[] _values;
    
    public TCharShortHashMap() {
    }
    
    public TCharShortHashMap(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public TCharShortHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public TCharShortHashMap(final int initialCapacity, final float loadFactor, final char noEntryKey, final short noEntryValue) {
        super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
    }
    
    public TCharShortHashMap(final char[] keys, final short[] values) {
        super(Math.max(keys.length, values.length));
        for (int size = Math.min(keys.length, values.length), i = 0; i < size; ++i) {
            this.put(keys[i], values[i]);
        }
    }
    
    public TCharShortHashMap(final TCharShortMap map) {
        super(map.size());
        if (map instanceof TCharShortHashMap) {
            final TCharShortHashMap hashmap = (TCharShortHashMap)map;
            this._loadFactor = hashmap._loadFactor;
            this.no_entry_key = hashmap.no_entry_key;
            this.no_entry_value = hashmap.no_entry_value;
            if (this.no_entry_key != '\0') {
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
        this._values = new short[capacity];
        return capacity;
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final char[] oldKeys = this._set;
        final short[] oldVals = this._values;
        final byte[] oldStates = this._states;
        this._set = new char[newCapacity];
        this._values = new short[newCapacity];
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
    
    public short put(final char key, final short value) {
        final int index = this.insertKey(key);
        return this.doPut(key, value, index);
    }
    
    public short putIfAbsent(final char key, final short value) {
        final int index = this.insertKey(key);
        if (index < 0) {
            return this._values[-index - 1];
        }
        return this.doPut(key, value, index);
    }
    
    private short doPut(final char key, final short value, int index) {
        short previous = this.no_entry_value;
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
    
    public void putAll(final Map<? extends Character, ? extends Short> map) {
        this.ensureCapacity(map.size());
        for (final Map.Entry<? extends Character, ? extends Short> entry : map.entrySet()) {
            this.put((char)entry.getKey(), (short)entry.getValue());
        }
    }
    
    public void putAll(final TCharShortMap map) {
        this.ensureCapacity(map.size());
        final TCharShortIterator iter = map.iterator();
        while (iter.hasNext()) {
            iter.advance();
            this.put(iter.key(), iter.value());
        }
    }
    
    public short get(final char key) {
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
    
    public short remove(final char key) {
        short prev = this.no_entry_value;
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
    
    public TShortCollection valueCollection() {
        return new TValueView();
    }
    
    public short[] values() {
        final short[] vals = new short[this.size()];
        final short[] v = this._values;
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
    
    public short[] values(short[] array) {
        final int size = this.size();
        if (array.length < size) {
            array = new short[size];
        }
        final short[] v = this._values;
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
    
    public boolean containsValue(final short val) {
        final byte[] states = this._states;
        final short[] vals = this._values;
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
    
    public TCharShortIterator iterator() {
        return new TCharShortHashIterator(this);
    }
    
    public boolean forEachKey(final TCharProcedure procedure) {
        return this.forEach(procedure);
    }
    
    public boolean forEachValue(final TShortProcedure procedure) {
        final byte[] states = this._states;
        final short[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean forEachEntry(final TCharShortProcedure procedure) {
        final byte[] states = this._states;
        final char[] keys = this._set;
        final short[] values = this._values;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(keys[i], values[i])) {
                return false;
            }
        }
        return true;
    }
    
    public void transformValues(final TShortFunction function) {
        final byte[] states = this._states;
        final short[] values = this._values;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                values[i] = function.execute(values[i]);
            }
        }
    }
    
    public boolean retainEntries(final TCharShortProcedure procedure) {
        boolean modified = false;
        final byte[] states = this._states;
        final char[] keys = this._set;
        final short[] values = this._values;
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
        return this.adjustValue(key, (short)1);
    }
    
    public boolean adjustValue(final char key, final short amount) {
        final int index = this.index(key);
        if (index < 0) {
            return false;
        }
        final short[] values = this._values;
        final int n = index;
        values[n] += amount;
        return true;
    }
    
    public short adjustOrPutValue(final char key, final short adjust_amount, final short put_amount) {
        int index = this.insertKey(key);
        short newValue;
        boolean isNewMapping;
        if (index < 0) {
            index = -index - 1;
            final short[] values = this._values;
            final int n = index;
            final short n2 = (short)(values[n] + adjust_amount);
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
        if (!(other instanceof TCharShortMap)) {
            return false;
        }
        final TCharShortMap that = (TCharShortMap)other;
        if (that.size() != this.size()) {
            return false;
        }
        final short[] values = this._values;
        final byte[] states = this._states;
        final short this_no_entry_value = this.getNoEntryValue();
        final short that_no_entry_value = that.getNoEntryValue();
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                final char key = this._set[i];
                final short that_value = that.get(key);
                final short this_value = values[i];
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
        this.forEachEntry(new TCharShortProcedure() {
            private boolean first = true;
            
            public boolean execute(final char key, final short value) {
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
                out.writeShort(this._values[i]);
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
            final short val = in.readShort();
            this.put(key, val);
        }
    }
    
    protected class TKeyView implements TCharSet
    {
        public TCharIterator iterator() {
            return new TCharShortKeyHashIterator(TCharShortHashMap.this);
        }
        
        public char getNoEntryValue() {
            return TCharShortHashMap.this.no_entry_key;
        }
        
        public int size() {
            return TCharShortHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TCharShortHashMap.this._size;
        }
        
        public boolean contains(final char entry) {
            return TCharShortHashMap.this.contains(entry);
        }
        
        public char[] toArray() {
            return TCharShortHashMap.this.keys();
        }
        
        public char[] toArray(final char[] dest) {
            return TCharShortHashMap.this.keys(dest);
        }
        
        public boolean add(final char entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final char entry) {
            return TCharShortHashMap.this.no_entry_value != TCharShortHashMap.this.remove(entry);
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Character)) {
                    return false;
                }
                final char ele = (char)element;
                if (!TCharShortHashMap.this.containsKey(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TCharCollection collection) {
            final TCharIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TCharShortHashMap.this.containsKey(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final char[] array) {
            for (final char element : array) {
                if (!TCharShortHashMap.this.contains(element)) {
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
            final char[] set = TCharShortHashMap.this._set;
            final byte[] states = TCharShortHashMap.this._states;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    TCharShortHashMap.this.removeAt(i);
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
            TCharShortHashMap.this.clear();
        }
        
        public boolean forEach(final TCharProcedure procedure) {
            return TCharShortHashMap.this.forEachKey(procedure);
        }
        
        public boolean equals(final Object other) {
            if (!(other instanceof TCharSet)) {
                return false;
            }
            final TCharSet that = (TCharSet)other;
            if (that.size() != this.size()) {
                return false;
            }
            int i = TCharShortHashMap.this._states.length;
            while (i-- > 0) {
                if (TCharShortHashMap.this._states[i] == 1 && !that.contains(TCharShortHashMap.this._set[i])) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hashcode = 0;
            int i = TCharShortHashMap.this._states.length;
            while (i-- > 0) {
                if (TCharShortHashMap.this._states[i] == 1) {
                    hashcode += HashFunctions.hash(TCharShortHashMap.this._set[i]);
                }
            }
            return hashcode;
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TCharShortHashMap.this.forEachKey(new TCharProcedure() {
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
    
    protected class TValueView implements TShortCollection
    {
        public TShortIterator iterator() {
            return new TCharShortValueHashIterator(TCharShortHashMap.this);
        }
        
        public short getNoEntryValue() {
            return TCharShortHashMap.this.no_entry_value;
        }
        
        public int size() {
            return TCharShortHashMap.this._size;
        }
        
        public boolean isEmpty() {
            return 0 == TCharShortHashMap.this._size;
        }
        
        public boolean contains(final short entry) {
            return TCharShortHashMap.this.containsValue(entry);
        }
        
        public short[] toArray() {
            return TCharShortHashMap.this.values();
        }
        
        public short[] toArray(final short[] dest) {
            return TCharShortHashMap.this.values(dest);
        }
        
        public boolean add(final short entry) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final short entry) {
            final short[] values = TCharShortHashMap.this._values;
            final char[] set = TCharShortHashMap.this._set;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] != '\0' && set[i] != '\u0002' && entry == values[i]) {
                    TCharShortHashMap.this.removeAt(i);
                    return true;
                }
            }
            return false;
        }
        
        public boolean containsAll(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Short)) {
                    return false;
                }
                final short ele = (short)element;
                if (!TCharShortHashMap.this.containsValue(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final TShortCollection collection) {
            final TShortIterator iter = collection.iterator();
            while (iter.hasNext()) {
                if (!TCharShortHashMap.this.containsValue(iter.next())) {
                    return false;
                }
            }
            return true;
        }
        
        public boolean containsAll(final short[] array) {
            for (final short element : array) {
                if (!TCharShortHashMap.this.containsValue(element)) {
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
            final short[] values = TCharShortHashMap.this._values;
            final byte[] states = TCharShortHashMap.this._states;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, values[i]) < 0) {
                    TCharShortHashMap.this.removeAt(i);
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
            if (this == collection) {
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
            TCharShortHashMap.this.clear();
        }
        
        public boolean forEach(final TShortProcedure procedure) {
            return TCharShortHashMap.this.forEachValue(procedure);
        }
        
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            TCharShortHashMap.this.forEachValue(new TShortProcedure() {
                private boolean first = true;
                
                public boolean execute(final short value) {
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
    
    class TCharShortKeyHashIterator extends THashPrimitiveIterator implements TCharIterator
    {
        TCharShortKeyHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public char next() {
            this.moveToNextIndex();
            return TCharShortHashMap.this._set[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TCharShortHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TCharShortValueHashIterator extends THashPrimitiveIterator implements TShortIterator
    {
        TCharShortValueHashIterator(final TPrimitiveHash hash) {
            super(hash);
        }
        
        public short next() {
            this.moveToNextIndex();
            return TCharShortHashMap.this._values[this._index];
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TCharShortHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
    
    class TCharShortHashIterator extends THashPrimitiveIterator implements TCharShortIterator
    {
        TCharShortHashIterator(final TCharShortHashMap map) {
            super(map);
        }
        
        public void advance() {
            this.moveToNextIndex();
        }
        
        public char key() {
            return TCharShortHashMap.this._set[this._index];
        }
        
        public short value() {
            return TCharShortHashMap.this._values[this._index];
        }
        
        public short setValue(final short val) {
            final short old = this.value();
            TCharShortHashMap.this._values[this._index] = val;
            return old;
        }
        
        public void remove() {
            if (this._expectedSize != this._hash.size()) {
                throw new ConcurrentModificationException();
            }
            try {
                this._hash.tempDisableAutoCompaction();
                TCharShortHashMap.this.removeAt(this._index);
            }
            finally {
                this._hash.reenableAutoCompaction(false);
            }
            --this._expectedSize;
        }
    }
}
