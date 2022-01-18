// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TDoubleShortIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TDoubleShortMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TDoubleShortMapDecorator extends AbstractMap<Double, Short> implements Map<Double, Short>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TDoubleShortMap _map;
    
    public TDoubleShortMapDecorator() {
    }
    
    public TDoubleShortMapDecorator(final TDoubleShortMap map) {
        this._map = map;
    }
    
    public TDoubleShortMap getMap() {
        return this._map;
    }
    
    public Short put(final Double key, final Short value) {
        double k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        short v;
        if (value == null) {
            v = this._map.getNoEntryValue();
        }
        else {
            v = this.unwrapValue(value);
        }
        final short retval = this._map.put(k, v);
        if (retval == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(retval);
    }
    
    public Short get(final Object key) {
        double k;
        if (key != null) {
            if (!(key instanceof Double)) {
                return null;
            }
            k = this.unwrapKey(key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        final short v = this._map.get(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Short remove(final Object key) {
        double k;
        if (key != null) {
            if (!(key instanceof Double)) {
                return null;
            }
            k = this.unwrapKey(key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        final short v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Double, Short>> entrySet() {
        return new AbstractSet<Entry<Double, Short>>() {
            public int size() {
                return TDoubleShortMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TDoubleShortMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TDoubleShortMapDecorator.this.containsKey(k) && TDoubleShortMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Double, Short>> iterator() {
                return new Iterator<Entry<Double, Short>>() {
                    private final TDoubleShortIterator it = TDoubleShortMapDecorator.this._map.iterator();
                    
                    public Entry<Double, Short> next() {
                        this.it.advance();
                        final double ik = this.it.key();
                        final Double key = (ik == TDoubleShortMapDecorator.this._map.getNoEntryKey()) ? null : TDoubleShortMapDecorator.this.wrapKey(ik);
                        final short iv = this.it.value();
                        final Short v = (iv == TDoubleShortMapDecorator.this._map.getNoEntryValue()) ? null : TDoubleShortMapDecorator.this.wrapValue(iv);
                        return new Entry<Double, Short>() {
                            private Short val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Double getKey() {
                                return key;
                            }
                            
                            public Short getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Short setValue(final Short value) {
                                this.val = value;
                                return TDoubleShortMapDecorator.this.put(key, value);
                            }
                        };
                    }
                    
                    public boolean hasNext() {
                        return this.it.hasNext();
                    }
                    
                    public void remove() {
                        this.it.remove();
                    }
                };
            }
            
            public boolean add(final Entry<Double, Short> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Double key = ((Entry)o).getKey();
                    TDoubleShortMapDecorator.this._map.remove(TDoubleShortMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Double, Short>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TDoubleShortMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Short && this._map.containsValue(this.unwrapValue(val));
    }
    
    public boolean containsKey(final Object key) {
        if (key == null) {
            return this._map.containsKey(this._map.getNoEntryKey());
        }
        return key instanceof Double && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Double, ? extends Short> map) {
        final Iterator<? extends Entry<? extends Double, ? extends Short>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Double, ? extends Short> e = (Entry<? extends Double, ? extends Short>)it.next();
            this.put((Double)e.getKey(), (Short)e.getValue());
        }
    }
    
    protected Double wrapKey(final double k) {
        return k;
    }
    
    protected double unwrapKey(final Object key) {
        return (double)key;
    }
    
    protected Short wrapValue(final short k) {
        return k;
    }
    
    protected short unwrapValue(final Object value) {
        return (short)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TDoubleShortMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
