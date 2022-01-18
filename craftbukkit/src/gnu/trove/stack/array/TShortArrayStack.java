// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.stack.array;

import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import gnu.trove.TShortCollection;
import gnu.trove.list.array.TShortArrayList;
import java.io.Externalizable;
import gnu.trove.stack.TShortStack;

public class TShortArrayStack implements TShortStack, Externalizable
{
    static final long serialVersionUID = 1L;
    protected TShortArrayList _list;
    public static final int DEFAULT_CAPACITY = 10;
    
    public TShortArrayStack() {
        this(10);
    }
    
    public TShortArrayStack(final int capacity) {
        this._list = new TShortArrayList(capacity);
    }
    
    public TShortArrayStack(final int capacity, final short no_entry_value) {
        this._list = new TShortArrayList(capacity, no_entry_value);
    }
    
    public TShortArrayStack(final TShortStack stack) {
        if (stack instanceof TShortArrayStack) {
            final TShortArrayStack array_stack = (TShortArrayStack)stack;
            this._list = new TShortArrayList(array_stack._list);
            return;
        }
        throw new UnsupportedOperationException("Only support TShortArrayStack");
    }
    
    public short getNoEntryValue() {
        return this._list.getNoEntryValue();
    }
    
    public void push(final short val) {
        this._list.add(val);
    }
    
    public short pop() {
        return this._list.removeAt(this._list.size() - 1);
    }
    
    public short peek() {
        return this._list.get(this._list.size() - 1);
    }
    
    public int size() {
        return this._list.size();
    }
    
    public void clear() {
        this._list.clear();
    }
    
    public short[] toArray() {
        final short[] retval = this._list.toArray();
        this.reverse(retval, 0, this.size());
        return retval;
    }
    
    public void toArray(final short[] dest) {
        final int size = this.size();
        int start = size - dest.length;
        if (start < 0) {
            start = 0;
        }
        final int length = Math.min(size, dest.length);
        this._list.toArray(dest, start, length);
        this.reverse(dest, 0, length);
        if (dest.length > size) {
            dest[size] = this._list.getNoEntryValue();
        }
    }
    
    private void reverse(final short[] dest, final int from, final int to) {
        if (from == to) {
            return;
        }
        if (from > to) {
            throw new IllegalArgumentException("from cannot be greater than to");
        }
        for (int i = from, j = to - 1; i < j; ++i, --j) {
            this.swap(dest, i, j);
        }
    }
    
    private void swap(final short[] dest, final int i, final int j) {
        final short tmp = dest[i];
        dest[i] = dest[j];
        dest[j] = tmp;
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        for (int i = this._list.size() - 1; i > 0; --i) {
            buf.append(this._list.get(i));
            buf.append(", ");
        }
        if (this.size() > 0) {
            buf.append(this._list.get(0));
        }
        buf.append("}");
        return buf.toString();
    }
    
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final TShortArrayStack that = (TShortArrayStack)o;
        return this._list.equals(that._list);
    }
    
    public int hashCode() {
        return this._list.hashCode();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._list);
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._list = (TShortArrayList)in.readObject();
    }
}
