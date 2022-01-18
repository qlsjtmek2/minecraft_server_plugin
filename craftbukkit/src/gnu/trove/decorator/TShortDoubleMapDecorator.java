// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TShortDoubleIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TShortDoubleMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TShortDoubleMapDecorator extends AbstractMap<Short, Double> implements Map<Short, Double>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TShortDoubleMap _map;
    
    public TShortDoubleMapDecorator() {
    }
    
    public TShortDoubleMapDecorator(final TShortDoubleMap map) {
        this._map = map;
    }
    
    public TShortDoubleMap getMap() {
        return this._map;
    }
    
    public Double put(final Short key, final Double value) {
        short k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        double v;
        if (value == null) {
            v = this._map.getNoEntryValue();
        }
        else {
            v = this.unwrapValue(value);
        }
        final double retval = this._map.put(k, v);
        if (retval == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(retval);
    }
    
    public Double get(final Object key) {
        short k;
        if (key != null) {
            if (!(key instanceof Short)) {
                return null;
            }
            k = this.unwrapKey(key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        final double v = this._map.get(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Double remove(final Object key) {
        short k;
        if (key != null) {
            if (!(key instanceof Short)) {
                return null;
            }
            k = this.unwrapKey(key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        final double v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Short, Double>> entrySet() {
        return new AbstractSet<Entry<Short, Double>>() {
            public int size() {
                return TShortDoubleMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TShortDoubleMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TShortDoubleMapDecorator.this.containsKey(k) && TShortDoubleMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Short, Double>> iterator() {
                return new Iterator<Entry<Short, Double>>() {
                    private final TShortDoubleIterator it = TShortDoubleMapDecorator.this._map.iterator();
                    
                    public Entry<Short, Double> next() {
                        this.it.advance();
                        final short ik = this.it.key();
                        final Short key = (ik == TShortDoubleMapDecorator.this._map.getNoEntryKey()) ? null : TShortDoubleMapDecorator.this.wrapKey(ik);
                        final double iv = this.it.value();
                        final Double v = (iv == TShortDoubleMapDecorator.this._map.getNoEntryValue()) ? null : TShortDoubleMapDecorator.this.wrapValue(iv);
                        return new Entry<Short, Double>() {
                            private Double val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Short getKey() {
                                return key;
                            }
                            
                            public Double getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Double setValue(final Double value) {
                                this.val = value;
                                return TShortDoubleMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Short, Double> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Short key = ((Entry)o).getKey();
                    TShortDoubleMapDecorator.this._map.remove(TShortDoubleMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Short, Double>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TShortDoubleMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Double && this._map.containsValue(this.unwrapValue(val));
    }
    
    public boolean containsKey(final Object key) {
        if (key == null) {
            return this._map.containsKey(this._map.getNoEntryKey());
        }
        return key instanceof Short && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Short, ? extends Double> map) {
        final Iterator<? extends Entry<? extends Short, ? extends Double>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Short, ? extends Double> e = (Entry<? extends Short, ? extends Double>)it.next();
            this.put((Short)e.getKey(), (Double)e.getValue());
        }
    }
    
    protected Short wrapKey(final short k) {
        return k;
    }
    
    protected short unwrapKey(final Object key) {
        return (short)key;
    }
    
    protected Double wrapValue(final double k) {
        return k;
    }
    
    protected double unwrapValue(final Object value) {
        return (double)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TShortDoubleMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
