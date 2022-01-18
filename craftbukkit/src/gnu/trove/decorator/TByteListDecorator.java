// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import gnu.trove.list.TByteList;
import java.io.Externalizable;
import java.util.List;
import java.util.AbstractList;

public class TByteListDecorator extends AbstractList<Byte> implements List<Byte>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TByteList list;
    
    public TByteListDecorator() {
    }
    
    public TByteListDecorator(final TByteList list) {
        this.list = list;
    }
    
    public TByteList getList() {
        return this.list;
    }
    
    public int size() {
        return this.list.size();
    }
    
    public Byte get(final int index) {
        final byte value = this.list.get(index);
        if (value == this.list.getNoEntryValue()) {
            return null;
        }
        return value;
    }
    
    public Byte set(final int index, final Byte value) {
        final byte previous_value = this.list.set(index, value);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void add(final int index, final Byte value) {
        this.list.insert(index, value);
    }
    
    public Byte remove(final int index) {
        final byte previous_value = this.list.removeAt(index);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.list = (TByteList)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this.list);
    }
}
