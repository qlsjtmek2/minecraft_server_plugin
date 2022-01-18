// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.set.hash;

import gnu.trove.impl.hash.TPrimitiveHash;
import gnu.trove.impl.hash.THashPrimitiveIterator;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import java.util.Iterator;
import gnu.trove.iterator.TCharIterator;
import gnu.trove.TCharCollection;
import java.util.Collection;
import java.util.Arrays;
import java.io.Externalizable;
import gnu.trove.set.TCharSet;
import gnu.trove.impl.hash.TCharHash;

public class TCharHashSet extends TCharHash implements TCharSet, Externalizable
{
    static final long serialVersionUID = 1L;
    
    public TCharHashSet() {
    }
    
    public TCharHashSet(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public TCharHashSet(final int initialCapacity, final float load_factor) {
        super(initialCapacity, load_factor);
    }
    
    public TCharHashSet(final int initial_capacity, final float load_factor, final char no_entry_value) {
        super(initial_capacity, load_factor, no_entry_value);
        if (no_entry_value != '\0') {
            Arrays.fill(this._set, no_entry_value);
        }
    }
    
    public TCharHashSet(final Collection<? extends Character> collection) {
        this(Math.max(collection.size(), 10));
        this.addAll(collection);
    }
    
    public TCharHashSet(final TCharCollection collection) {
        this(Math.max(collection.size(), 10));
        if (collection instanceof TCharHashSet) {
            final TCharHashSet hashset = (TCharHashSet)collection;
            this._loadFactor = hashset._loadFactor;
            this.no_entry_value = hashset.no_entry_value;
            if (this.no_entry_value != '\0') {
                Arrays.fill(this._set, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.addAll(collection);
    }
    
    public TCharHashSet(final char[] array) {
        this(Math.max(array.length, 10));
        this.addAll(array);
    }
    
    public TCharIterator iterator() {
        return new TCharHashIterator(this);
    }
    
    public char[] toArray() {
        final char[] result = new char[this.size()];
        final char[] set = this._set;
        final byte[] states = this._states;
        int i = states.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                result[j++] = set[i];
            }
        }
        return result;
    }
    
    public char[] toArray(final char[] dest) {
        final char[] set = this._set;
        final byte[] states = this._states;
        int i = states.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                dest[j++] = set[i];
            }
        }
        if (dest.length > this._size) {
            dest[this._size] = this.no_entry_value;
        }
        return dest;
    }
    
    public boolean add(final char val) {
        final int index = this.insertKey(val);
        if (index < 0) {
            return false;
        }
        this.postInsertHook(this.consumeFreeSlot);
        return true;
    }
    
    public boolean remove(final char val) {
        final int index = this.index(val);
        if (index >= 0) {
            this.removeAt(index);
            return true;
        }
        return false;
    }
    
    public boolean containsAll(final Collection<?> collection) {
        for (final Object element : collection) {
            if (!(element instanceof Character)) {
                return false;
            }
            final char c = (char)element;
            if (!this.contains(c)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final TCharCollection collection) {
        for (final char element : collection) {
            if (!this.contains(element)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final char[] array) {
        int i = array.length;
        while (i-- > 0) {
            if (!this.contains(array[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addAll(final Collection<? extends Character> collection) {
        boolean changed = false;
        for (final Character element : collection) {
            final char e = element;
            if (this.add(e)) {
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean addAll(final TCharCollection collection) {
        boolean changed = false;
        for (final char element : collection) {
            if (this.add(element)) {
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean addAll(final char[] array) {
        boolean changed = false;
        int i = array.length;
        while (i-- > 0) {
            if (this.add(array[i])) {
                changed = true;
            }
        }
        return changed;
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
        final char[] set = this._set;
        final byte[] states = this._states;
        int i = set.length;
        while (i-- > 0) {
            if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                this.removeAt(i);
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
        super.clear();
        final char[] set = this._set;
        final byte[] states = this._states;
        int i = set.length;
        while (i-- > 0) {
            set[i] = this.no_entry_value;
            states[i] = 0;
        }
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final char[] oldSet = this._set;
        final byte[] oldStates = this._states;
        this._set = new char[newCapacity];
        this._states = new byte[newCapacity];
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldStates[i] == 1) {
                final char o = oldSet[i];
                final int index = this.insertKey(o);
            }
        }
    }
    
    public boolean equals(final Object other) {
        if (!(other instanceof TCharSet)) {
            return false;
        }
        final TCharSet that = (TCharSet)other;
        if (that.size() != this.size()) {
            return false;
        }
        int i = this._states.length;
        while (i-- > 0) {
            if (this._states[i] == 1 && !that.contains(this._set[i])) {
                return false;
            }
        }
        return true;
    }
    
    public int hashCode() {
        int hashcode = 0;
        int i = this._states.length;
        while (i-- > 0) {
            if (this._states[i] == 1) {
                hashcode += HashFunctions.hash(this._set[i]);
            }
        }
        return hashcode;
    }
    
    public String toString() {
        final StringBuilder buffy = new StringBuilder(this._size * 2 + 2);
        buffy.append("{");
        int i = this._states.length;
        int j = 1;
        while (i-- > 0) {
            if (this._states[i] == 1) {
                buffy.append(this._set[i]);
                if (j++ >= this._size) {
                    continue;
                }
                buffy.append(",");
            }
        }
        buffy.append("}");
        return buffy.toString();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(1);
        super.writeExternal(out);
        out.writeInt(this._size);
        out.writeFloat(this._loadFactor);
        out.writeChar(this.no_entry_value);
        int i = this._states.length;
        while (i-- > 0) {
            if (this._states[i] == 1) {
                out.writeChar(this._set[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        final int version = in.readByte();
        super.readExternal(in);
        int size = in.readInt();
        if (version >= 1) {
            this._loadFactor = in.readFloat();
            this.no_entry_value = in.readChar();
            if (this.no_entry_value != '\0') {
                Arrays.fill(this._set, this.no_entry_value);
            }
        }
        this.setUp(size);
        while (size-- > 0) {
            final char val = in.readChar();
            this.add(val);
        }
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
