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
import gnu.trove.iterator.TLongIterator;
import gnu.trove.TLongCollection;
import java.util.Collection;
import java.util.Arrays;
import java.io.Externalizable;
import gnu.trove.set.TLongSet;
import gnu.trove.impl.hash.TLongHash;

public class TLongHashSet extends TLongHash implements TLongSet, Externalizable
{
    static final long serialVersionUID = 1L;
    
    public TLongHashSet() {
    }
    
    public TLongHashSet(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public TLongHashSet(final int initialCapacity, final float load_factor) {
        super(initialCapacity, load_factor);
    }
    
    public TLongHashSet(final int initial_capacity, final float load_factor, final long no_entry_value) {
        super(initial_capacity, load_factor, no_entry_value);
        if (no_entry_value != 0L) {
            Arrays.fill(this._set, no_entry_value);
        }
    }
    
    public TLongHashSet(final Collection<? extends Long> collection) {
        this(Math.max(collection.size(), 10));
        this.addAll(collection);
    }
    
    public TLongHashSet(final TLongCollection collection) {
        this(Math.max(collection.size(), 10));
        if (collection instanceof TLongHashSet) {
            final TLongHashSet hashset = (TLongHashSet)collection;
            this._loadFactor = hashset._loadFactor;
            this.no_entry_value = hashset.no_entry_value;
            if (this.no_entry_value != 0L) {
                Arrays.fill(this._set, this.no_entry_value);
            }
            this.setUp((int)Math.ceil(10.0f / this._loadFactor));
        }
        this.addAll(collection);
    }
    
    public TLongHashSet(final long[] array) {
        this(Math.max(array.length, 10));
        this.addAll(array);
    }
    
    public TLongIterator iterator() {
        return new TLongHashIterator(this);
    }
    
    public long[] toArray() {
        final long[] result = new long[this.size()];
        final long[] set = this._set;
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
    
    public long[] toArray(final long[] dest) {
        final long[] set = this._set;
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
    
    public boolean add(final long val) {
        final int index = this.insertKey(val);
        if (index < 0) {
            return false;
        }
        this.postInsertHook(this.consumeFreeSlot);
        return true;
    }
    
    public boolean remove(final long val) {
        final int index = this.index(val);
        if (index >= 0) {
            this.removeAt(index);
            return true;
        }
        return false;
    }
    
    public boolean containsAll(final Collection<?> collection) {
        for (final Object element : collection) {
            if (!(element instanceof Long)) {
                return false;
            }
            final long c = (long)element;
            if (!this.contains(c)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final TLongCollection collection) {
        for (final long element : collection) {
            if (!this.contains(element)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean containsAll(final long[] array) {
        int i = array.length;
        while (i-- > 0) {
            if (!this.contains(array[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addAll(final Collection<? extends Long> collection) {
        boolean changed = false;
        for (final Long element : collection) {
            final long e = element;
            if (this.add(e)) {
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean addAll(final TLongCollection collection) {
        boolean changed = false;
        for (final long element : collection) {
            if (this.add(element)) {
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean addAll(final long[] array) {
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
        final long[] set = this._set;
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
        super.clear();
        final long[] set = this._set;
        final byte[] states = this._states;
        int i = set.length;
        while (i-- > 0) {
            set[i] = this.no_entry_value;
            states[i] = 0;
        }
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final long[] oldSet = this._set;
        final byte[] oldStates = this._states;
        this._set = new long[newCapacity];
        this._states = new byte[newCapacity];
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldStates[i] == 1) {
                final long o = oldSet[i];
                final int index = this.insertKey(o);
            }
        }
    }
    
    public boolean equals(final Object other) {
        if (!(other instanceof TLongSet)) {
            return false;
        }
        final TLongSet that = (TLongSet)other;
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
        out.writeLong(this.no_entry_value);
        int i = this._states.length;
        while (i-- > 0) {
            if (this._states[i] == 1) {
                out.writeLong(this._set[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        final int version = in.readByte();
        super.readExternal(in);
        int size = in.readInt();
        if (version >= 1) {
            this._loadFactor = in.readFloat();
            this.no_entry_value = in.readLong();
            if (this.no_entry_value != 0L) {
                Arrays.fill(this._set, this.no_entry_value);
            }
        }
        this.setUp(size);
        while (size-- > 0) {
            final long val = in.readLong();
            this.add(val);
        }
    }
    
    class TLongHashIterator extends THashPrimitiveIterator implements TLongIterator
    {
        private final TLongHash _hash;
        
        public TLongHashIterator(final TLongHash hash) {
            super(hash);
            this._hash = hash;
        }
        
        public long next() {
            this.moveToNextIndex();
            return this._hash._set[this._index];
        }
    }
}
