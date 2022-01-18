// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import gnu.trove.list.TFloatList;
import java.io.Externalizable;
import java.util.List;
import java.util.AbstractList;

public class TFloatListDecorator extends AbstractList<Float> implements List<Float>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TFloatList list;
    
    public TFloatListDecorator() {
    }
    
    public TFloatListDecorator(final TFloatList list) {
        this.list = list;
    }
    
    public TFloatList getList() {
        return this.list;
    }
    
    public int size() {
        return this.list.size();
    }
    
    public Float get(final int index) {
        final float value = this.list.get(index);
        if (value == this.list.getNoEntryValue()) {
            return null;
        }
        return value;
    }
    
    public Float set(final int index, final Float value) {
        final float previous_value = this.list.set(index, value);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void add(final int index, final Float value) {
        this.list.insert(index, value);
    }
    
    public Float remove(final int index) {
        final float previous_value = this.list.removeAt(index);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.list = (TFloatList)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this.list);
    }
}
