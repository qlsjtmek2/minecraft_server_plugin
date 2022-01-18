// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import gnu.trove.list.TIntList;
import java.io.Externalizable;
import java.util.List;
import java.util.AbstractList;

public class TIntListDecorator extends AbstractList<Integer> implements List<Integer>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TIntList list;
    
    public TIntListDecorator() {
    }
    
    public TIntListDecorator(final TIntList list) {
        this.list = list;
    }
    
    public TIntList getList() {
        return this.list;
    }
    
    public int size() {
        return this.list.size();
    }
    
    public Integer get(final int index) {
        final int value = this.list.get(index);
        if (value == this.list.getNoEntryValue()) {
            return null;
        }
        return value;
    }
    
    public Integer set(final int index, final Integer value) {
        final int previous_value = this.list.set(index, value);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void add(final int index, final Integer value) {
        this.list.insert(index, value);
    }
    
    public Integer remove(final int index) {
        final int previous_value = this.list.removeAt(index);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.list = (TIntList)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this.list);
    }
}
