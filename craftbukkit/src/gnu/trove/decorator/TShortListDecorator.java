// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import gnu.trove.list.TShortList;
import java.io.Externalizable;
import java.util.List;
import java.util.AbstractList;

public class TShortListDecorator extends AbstractList<Short> implements List<Short>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TShortList list;
    
    public TShortListDecorator() {
    }
    
    public TShortListDecorator(final TShortList list) {
        this.list = list;
    }
    
    public TShortList getList() {
        return this.list;
    }
    
    public int size() {
        return this.list.size();
    }
    
    public Short get(final int index) {
        final short value = this.list.get(index);
        if (value == this.list.getNoEntryValue()) {
            return null;
        }
        return value;
    }
    
    public Short set(final int index, final Short value) {
        final short previous_value = this.list.set(index, value);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void add(final int index, final Short value) {
        this.list.insert(index, value);
    }
    
    public Short remove(final int index) {
        final short previous_value = this.list.removeAt(index);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.list = (TShortList)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this.list);
    }
}
