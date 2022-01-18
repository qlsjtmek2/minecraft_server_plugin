// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import gnu.trove.list.TLongList;
import java.io.Externalizable;
import java.util.List;
import java.util.AbstractList;

public class TLongListDecorator extends AbstractList<Long> implements List<Long>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TLongList list;
    
    public TLongListDecorator() {
    }
    
    public TLongListDecorator(final TLongList list) {
        this.list = list;
    }
    
    public TLongList getList() {
        return this.list;
    }
    
    public int size() {
        return this.list.size();
    }
    
    public Long get(final int index) {
        final long value = this.list.get(index);
        if (value == this.list.getNoEntryValue()) {
            return null;
        }
        return value;
    }
    
    public Long set(final int index, final Long value) {
        final long previous_value = this.list.set(index, value);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void add(final int index, final Long value) {
        this.list.insert(index, value);
    }
    
    public Long remove(final int index) {
        final long previous_value = this.list.removeAt(index);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.list = (TLongList)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this.list);
    }
}
