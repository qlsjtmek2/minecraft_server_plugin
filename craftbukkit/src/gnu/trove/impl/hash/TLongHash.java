// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.hash;

import gnu.trove.impl.HashFunctions;
import gnu.trove.procedure.TLongProcedure;
import java.util.Arrays;
import gnu.trove.impl.Constants;

public abstract class TLongHash extends TPrimitiveHash
{
    static final long serialVersionUID = 1L;
    public transient long[] _set;
    protected long no_entry_value;
    protected boolean consumeFreeSlot;
    
    public TLongHash() {
        this.no_entry_value = Constants.DEFAULT_LONG_NO_ENTRY_VALUE;
        if (this.no_entry_value != 0L) {
            Arrays.fill(this._set, this.no_entry_value);
        }
    }
    
    public TLongHash(final int initialCapacity) {
        super(initialCapacity);
        this.no_entry_value = Constants.DEFAULT_LONG_NO_ENTRY_VALUE;
        if (this.no_entry_value != 0L) {
            Arrays.fill(this._set, this.no_entry_value);
        }
    }
    
    public TLongHash(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
        this.no_entry_value = Constants.DEFAULT_LONG_NO_ENTRY_VALUE;
        if (this.no_entry_value != 0L) {
            Arrays.fill(this._set, this.no_entry_value);
        }
    }
    
    public TLongHash(final int initialCapacity, final float loadFactor, final long no_entry_value) {
        super(initialCapacity, loadFactor);
        this.no_entry_value = no_entry_value;
        if (no_entry_value != 0L) {
            Arrays.fill(this._set, no_entry_value);
        }
    }
    
    public long getNoEntryValue() {
        return this.no_entry_value;
    }
    
    protected int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._set = new long[capacity];
        return capacity;
    }
    
    public boolean contains(final long val) {
        return this.index(val) >= 0;
    }
    
    public boolean forEach(final TLongProcedure procedure) {
        final byte[] states = this._states;
        final long[] set = this._set;
        int i = set.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(set[i])) {
                return false;
            }
        }
        return true;
    }
    
    protected void removeAt(final int index) {
        this._set[index] = this.no_entry_value;
        super.removeAt(index);
    }
    
    protected int index(final long val) {
        final byte[] states = this._states;
        final long[] set = this._set;
        final int length = states.length;
        final int hash = HashFunctions.hash(val) & Integer.MAX_VALUE;
        final int index = hash % length;
        final byte state = states[index];
        if (state == 0) {
            return -1;
        }
        if (state == 1 && set[index] == val) {
            return index;
        }
        return this.indexRehashed(val, index, hash, state);
    }
    
    int indexRehashed(final long key, int index, final int hash, byte state) {
        final int length = this._set.length;
        final int probe = 1 + hash % (length - 2);
        final int loopIndex = index;
        do {
            index -= probe;
            if (index < 0) {
                index += length;
            }
            state = this._states[index];
            if (state == 0) {
                return -1;
            }
            if (key == this._set[index] && state != 2) {
                return index;
            }
        } while (index != loopIndex);
        return -1;
    }
    
    protected int insertKey(final long val) {
        final int hash = HashFunctions.hash(val) & Integer.MAX_VALUE;
        final int index = hash % this._states.length;
        final byte state = this._states[index];
        this.consumeFreeSlot = false;
        if (state == 0) {
            this.consumeFreeSlot = true;
            this.insertKeyAt(index, val);
            return index;
        }
        if (state == 1 && this._set[index] == val) {
            return -index - 1;
        }
        return this.insertKeyRehash(val, index, hash, state);
    }
    
    int insertKeyRehash(final long val, int index, final int hash, byte state) {
        final int length = this._set.length;
        final int probe = 1 + hash % (length - 2);
        final int loopIndex = index;
        int firstRemoved = -1;
        do {
            if (state == 2 && firstRemoved == -1) {
                firstRemoved = index;
            }
            index -= probe;
            if (index < 0) {
                index += length;
            }
            state = this._states[index];
            if (state == 0) {
                if (firstRemoved != -1) {
                    this.insertKeyAt(firstRemoved, val);
                    return firstRemoved;
                }
                this.consumeFreeSlot = true;
                this.insertKeyAt(index, val);
                return index;
            }
            else {
                if (state == 1 && this._set[index] == val) {
                    return -index - 1;
                }
                continue;
            }
        } while (index != loopIndex);
        if (firstRemoved != -1) {
            this.insertKeyAt(firstRemoved, val);
            return firstRemoved;
        }
        throw new IllegalStateException("No free or removed slots available. Key set full?!!");
    }
    
    void insertKeyAt(final int index, final long val) {
        this._set[index] = val;
        this._states[index] = 1;
    }
}
