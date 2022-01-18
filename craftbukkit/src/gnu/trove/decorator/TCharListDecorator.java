// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import gnu.trove.list.TCharList;
import java.io.Externalizable;
import java.util.List;
import java.util.AbstractList;

public class TCharListDecorator extends AbstractList<Character> implements List<Character>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TCharList list;
    
    public TCharListDecorator() {
    }
    
    public TCharListDecorator(final TCharList list) {
        this.list = list;
    }
    
    public TCharList getList() {
        return this.list;
    }
    
    public int size() {
        return this.list.size();
    }
    
    public Character get(final int index) {
        final char value = this.list.get(index);
        if (value == this.list.getNoEntryValue()) {
            return null;
        }
        return value;
    }
    
    public Character set(final int index, final Character value) {
        final char previous_value = this.list.set(index, value);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void add(final int index, final Character value) {
        this.list.insert(index, value);
    }
    
    public Character remove(final int index) {
        final char previous_value = this.list.removeAt(index);
        if (previous_value == this.list.getNoEntryValue()) {
            return null;
        }
        return previous_value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this.list = (TCharList)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this.list);
    }
}
