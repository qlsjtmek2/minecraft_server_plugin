// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import gnu.trove.iterator.TShortIterator;
import java.util.Iterator;
import gnu.trove.set.TShortSet;
import java.io.Externalizable;
import java.util.Set;
import java.util.AbstractSet;

public class TShortSetDecorator extends AbstractSet<Short> implements Set<Short>, Externalizable
{
    static final long serialVersionUID = 1L;
    protected TShortSet _set;
    
    public TShortSetDecorator() {
    }
    
    public TShortSetDecorator(final TShortSet set) {
        this._set = set;
    }
    
    public TShortSet getSet() {
        return this._set;
    }
    
    public boolean add(final Short value) {
        return value != null && this._set.add(value);
    }
    
    public boolean equals(final Object other) {
        if (this._set.equals(other)) {
            return true;
        }
        if (!(other instanceof Set)) {
            return false;
        }
        final Set that = (Set)other;
        if (that.size() != this._set.size()) {
            return false;
        }
        final Iterator it = that.iterator();
        int i = that.size();
        while (i-- > 0) {
            final Object val = it.next();
            if (!(val instanceof Short)) {
                return false;
            }
            final short v = (short)val;
            if (this._set.contains(v)) {
                continue;
            }
            return false;
        }
        return true;
    }
    
    public void clear() {
        this._set.clear();
    }
    
    public boolean remove(final Object value) {
        return value instanceof Short && this._set.remove((short)value);
    }
    
    public Iterator<Short> iterator() {
        return new Iterator<Short>() {
            private final TShortIterator it = TShortSetDecorator.this._set.iterator();
            
            public Short next() {
                return this.it.next();
            }
            
            public boolean hasNext() {
                return this.it.hasNext();
            }
            
            public void remove() {
                this.it.remove();
            }
        };
    }
    
    public int size() {
        return this._set.size();
    }
    
    public boolean isEmpty() {
        return this._set.size() == 0;
    }
    
    public boolean contains(final Object o) {
        return o instanceof Short && this._set.contains((short)o);
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._set = (TShortSet)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._set);
    }
}
