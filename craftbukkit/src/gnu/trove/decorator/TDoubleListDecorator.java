// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import gnu.trove.list.TDoubleList;
import java.io.Externalizable;
import java.util.List;
import java.util.AbstractList;

public class TDoubleListDecorator extends AbstractList<Double> implements List<Double>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TDoubleList list;
    
    public TDoubleListDecorator() {
    }
    
    public TDoubleListDecorator(final TDoubleList list) {
        this.list = list;
    }
    
    public TDoubleList getList() {
        return this.list;
    }
    
    public int size() {
        return this.list.size();
    }
    
    public Double get(final int index) {
        final double value = this.list.get(index);
        if (value == this.list.getNoEntryValue()) {
            return null;
        }
        return value;
    }
    
    public Double set(final int index, final Double value) {
        final double previous_value = this.list.set(index, value);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void add(final int index, final Double value) {
        this.list.insert(index, value);
    }
    
    public Double remove(final int index) {
        final double previous_value = this.list.removeAt(index);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.list = (TDoubleList)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this.list);
    }
}
