// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TFloatDoubleIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TFloatDoubleMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TFloatDoubleMapDecorator extends AbstractMap<Float, Double> implements Map<Float, Double>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TFloatDoubleMap _map;
    
    public TFloatDoubleMapDecorator() {
    }
    
    public TFloatDoubleMapDecorator(final TFloatDoubleMap map) {
        this._map = map;
    }
    
    public TFloatDoubleMap getMap() {
        return this._map;
    }
    
    public Double put(final Float key, final Double value) {
        float k;
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
        float k;
        if (key != null) {
            if (!(key instanceof Float)) {
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
        float k;
        if (key != null) {
            if (!(key instanceof Float)) {
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
    
    public Set<Entry<Float, Double>> entrySet() {
        return new AbstractSet<Entry<Float, Double>>() {
            public int size() {
                return TFloatDoubleMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TFloatDoubleMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TFloatDoubleMapDecorator.this.containsKey(k) && TFloatDoubleMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Float, Double>> iterator() {
                return new Iterator<Entry<Float, Double>>() {
                    private final TFloatDoubleIterator it = TFloatDoubleMapDecorator.this._map.iterator();
                    
                    public Entry<Float, Double> next() {
                        this.it.advance();
                        final float ik = this.it.key();
                        final Float key = (ik == TFloatDoubleMapDecorator.this._map.getNoEntryKey()) ? null : TFloatDoubleMapDecorator.this.wrapKey(ik);
                        final double iv = this.it.value();
                        final Double v = (iv == TFloatDoubleMapDecorator.this._map.getNoEntryValue()) ? null : TFloatDoubleMapDecorator.this.wrapValue(iv);
                        return new Entry<Float, Double>() {
                            private Double val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Float getKey() {
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
                                return TFloatDoubleMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Float, Double> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Float key = ((Entry)o).getKey();
                    TFloatDoubleMapDecorator.this._map.remove(TFloatDoubleMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Float, Double>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TFloatDoubleMapDecorator.this.clear();
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
        return key instanceof Float && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Float, ? extends Double> map) {
        final Iterator<? extends Entry<? extends Float, ? extends Double>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Float, ? extends Double> e = (Entry<? extends Float, ? extends Double>)it.next();
            this.put((Float)e.getKey(), (Double)e.getValue());
        }
    }
    
    protected Float wrapKey(final float k) {
        return k;
    }
    
    protected float unwrapKey(final Object key) {
        return (float)key;
    }
    
    protected Double wrapValue(final double k) {
        return k;
    }
    
    protected double unwrapValue(final Object value) {
        return (double)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TFloatDoubleMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
