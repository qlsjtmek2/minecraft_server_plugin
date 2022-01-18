// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TCharDoubleIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TCharDoubleMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TCharDoubleMapDecorator extends AbstractMap<Character, Double> implements Map<Character, Double>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TCharDoubleMap _map;
    
    public TCharDoubleMapDecorator() {
    }
    
    public TCharDoubleMapDecorator(final TCharDoubleMap map) {
        this._map = map;
    }
    
    public TCharDoubleMap getMap() {
        return this._map;
    }
    
    public Double put(final Character key, final Double value) {
        char k;
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
        char k;
        if (key != null) {
            if (!(key instanceof Character)) {
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
        char k;
        if (key != null) {
            if (!(key instanceof Character)) {
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
    
    public Set<Entry<Character, Double>> entrySet() {
        return new AbstractSet<Entry<Character, Double>>() {
            public int size() {
                return TCharDoubleMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TCharDoubleMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TCharDoubleMapDecorator.this.containsKey(k) && TCharDoubleMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Character, Double>> iterator() {
                return new Iterator<Entry<Character, Double>>() {
                    private final TCharDoubleIterator it = TCharDoubleMapDecorator.this._map.iterator();
                    
                    public Entry<Character, Double> next() {
                        this.it.advance();
                        final char ik = this.it.key();
                        final Character key = (ik == TCharDoubleMapDecorator.this._map.getNoEntryKey()) ? null : TCharDoubleMapDecorator.this.wrapKey(ik);
                        final double iv = this.it.value();
                        final Double v = (iv == TCharDoubleMapDecorator.this._map.getNoEntryValue()) ? null : TCharDoubleMapDecorator.this.wrapValue(iv);
                        return new Entry<Character, Double>() {
                            private Double val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Character getKey() {
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
                                return TCharDoubleMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Character, Double> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Character key = ((Entry)o).getKey();
                    TCharDoubleMapDecorator.this._map.remove(TCharDoubleMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Character, Double>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TCharDoubleMapDecorator.this.clear();
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
        return key instanceof Character && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Character, ? extends Double> map) {
        final Iterator<? extends Entry<? extends Character, ? extends Double>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Character, ? extends Double> e = (Entry<? extends Character, ? extends Double>)it.next();
            this.put((Character)e.getKey(), (Double)e.getValue());
        }
    }
    
    protected Character wrapKey(final char k) {
        return k;
    }
    
    protected char unwrapKey(final Object key) {
        return (char)key;
    }
    
    protected Double wrapValue(final double k) {
        return k;
    }
    
    protected double unwrapValue(final Object value) {
        return (double)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TCharDoubleMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
