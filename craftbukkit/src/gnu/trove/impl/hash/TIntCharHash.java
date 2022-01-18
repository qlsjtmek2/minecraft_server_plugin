// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.hash;

import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.impl.HashFunctions;
import gnu.trove.procedure.TIntProcedure;

public abstract class TIntCharHash extends TPrimitiveHash
{
    static final long serialVersionUID = 1L;
    public transient int[] _set;
    protected int no_entry_key;
    protected char no_entry_value;
    protected boolean consumeFreeSlot;
    
    public TIntCharHash() {
        this.no_entry_key = 0;
        this.no_entry_value = '\0';
    }
    
    public TIntCharHash(final int initialCapacity) {
        super(initialCapacity);
        this.no_entry_key = 0;
        this.no_entry_value = '\0';
    }
    
    public TIntCharHash(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
        this.no_entry_key = 0;
        this.no_entry_value = '\0';
    }
    
    public TIntCharHash(final int initialCapacity, final float loadFactor, final int no_entry_key, final char no_entry_value) {
        super(initialCapacity, loadFactor);
        this.no_entry_key = no_entry_key;
        this.no_entry_value = no_entry_value;
    }
    
    public int getNoEntryKey() {
        return this.no_entry_key;
    }
    
    public char getNoEntryValue() {
        return this.no_entry_value;
    }
    
    protected int setUp(final int initialCapacity) {
        final int capacity = super.setUp(initialCapacity);
        this._set = new int[capacity];
        return capacity;
    }
    
    public boolean contains(final int val) {
        return this.index(val) >= 0;
    }
    
    public boolean forEach(final TIntProcedure procedure) {
        final byte[] states = this._states;
        final int[] set = this._set;
        int i = set.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.execute(set[i])) {
                return false;
            }
        }
        return true;
    }
    
    protected void removeAt(final int index) {
        this._set[index] = this.no_entry_key;
        super.removeAt(index);
    }
    
    protected int index(final int key) {
        final byte[] states = this._states;
        final int[] set = this._set;
        final int length = states.length;
        final int hash = HashFunctions.hash(key) & Integer.MAX_VALUE;
        final int index = hash % length;
        final byte state = states[index];
        if (state == 0) {
            return -1;
        }
        if (state == 1 && set[index] == key) {
            return index;
        }
        return this.indexRehashed(key, index, hash, state);
    }
    
    int indexRehashed(final int key, int index, final int hash, byte state) {
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
    
    protected int insertKey(final int val) {
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
    
    int insertKeyRehash(final int val, int index, final int hash, byte state) {
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
    
    void insertKeyAt(final int index, final int val) {
        this._set[index] = val;
        this._states[index] = 1;
    }
    
    protected int XinsertKey(final int key) {
        final byte[] states = this._states;
        final int[] set = this._set;
        final int length = states.length;
        final int hash = HashFunctions.hash(key) & Integer.MAX_VALUE;
        int index = hash % length;
        byte state = states[index];
        this.consumeFreeSlot = false;
        if (state == 0) {
            this.consumeFreeSlot = true;
            set[index] = key;
            states[index] = 1;
            return index;
        }
        if (state == 1 && set[index] == key) {
            return -index - 1;
        }
        final int probe = 1 + hash % (length - 2);
        if (state != 2) {
            do {
                index -= probe;
                if (index < 0) {
                    index += length;
                }
                state = states[index];
            } while (state == 1 && set[index] != key);
        }
        if (state == 2) {
            final int firstRemoved = index;
            while (state != 0 && (state == 2 || set[index] != key)) {
                index -= probe;
                if (index < 0) {
                    index += length;
                }
                state = states[index];
            }
            if (state == 1) {
                return -index - 1;
            }
            set[index] = key;
            states[index] = 1;
            return firstRemoved;
        }
        else {
            if (state == 1) {
                return -index - 1;
            }
            this.consumeFreeSlot = true;
            set[index] = key;
            states[index] = 1;
            return index;
        }
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeInt(this.no_entry_key);
        out.writeChar(this.no_entry_value);
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.no_entry_key = in.readInt();
        this.no_entry_value = in.readChar();
    }
}
