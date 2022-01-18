// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import gnu.trove.iterator.TDoubleIterator;
import java.util.Iterator;
import gnu.trove.set.TDoubleSet;
import java.io.Externalizable;
import java.util.Set;
import java.util.AbstractSet;

public class TDoubleSetDecorator extends AbstractSet<Double> implements Set<Double>, Externalizable
{
    static final long serialVersionUID = 1L;
    protected TDoubleSet _set;
    
    public TDoubleSetDecorator() {
    }
    
    public TDoubleSetDecorator(final TDoubleSet set) {
        this._set = set;
    }
    
    public TDoubleSet getSet() {
        return this._set;
    }
    
    public boolean add(final Double value) {
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
            if (!(val instanceof Double)) {
                return false;
            }
            final double v = (double)val;
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
        return value instanceof Double && this._set.remove((double)value);
    }
    
    public Iterator<Double> iterator() {
        return new Iterator<Double>() {
            private final TDoubleIterator it = TDoubleSetDecorator.this._set.iterator();
            
            public Double next() {
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
        return o instanceof Double && this._set.contains((double)o);
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._set = (TDoubleSet)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._set);
    }
}
