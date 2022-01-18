// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TDoubleIntIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TDoubleIntMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TDoubleIntMapDecorator extends AbstractMap<Double, Integer> implements Map<Double, Integer>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TDoubleIntMap _map;
    
    public TDoubleIntMapDecorator() {
    }
    
    public TDoubleIntMapDecorator(final TDoubleIntMap map) {
        this._map = map;
    }
    
    public TDoubleIntMap getMap() {
        return this._map;
    }
    
    public Integer put(final Double key, final Integer value) {
        double k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        int v;
        if (value == null) {
            v = this._map.getNoEntryValue();
        }
        else {
            v = this.unwrapValue(value);
        }
        final int retval = this._map.put(k, v);
        if (retval == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(retval);
    }
    
    public Integer get(final Object key) {
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
        final int v = this._map.get(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Integer remove(final Object key) {
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
        final int v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Double, Integer>> entrySet() {
        return new AbstractSet<Entry<Double, Integer>>() {
            public int size() {
                return TDoubleIntMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TDoubleIntMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TDoubleIntMapDecorator.this.containsKey(k) && TDoubleIntMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Double, Integer>> iterator() {
                return new Iterator<Entry<Double, Integer>>() {
                    private final TDoubleIntIterator it = TDoubleIntMapDecorator.this._map.iterator();
                    
                    public Entry<Double, Integer> next() {
                        this.it.advance();
                        final double ik = this.it.key();
                        final Double key = (ik == TDoubleIntMapDecorator.this._map.getNoEntryKey()) ? null : TDoubleIntMapDecorator.this.wrapKey(ik);
                        final int iv = this.it.value();
                        final Integer v = (iv == TDoubleIntMapDecorator.this._map.getNoEntryValue()) ? null : TDoubleIntMapDecorator.this.wrapValue(iv);
                        return new Entry<Double, Integer>() {
                            private Integer val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Double getKey() {
                                return key;
                            }
                            
                            public Integer getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Integer setValue(final Integer value) {
                                this.val = value;
                                return TDoubleIntMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Double, Integer> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Double key = ((Entry)o).getKey();
                    TDoubleIntMapDecorator.this._map.remove(TDoubleIntMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Double, Integer>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TDoubleIntMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Integer && this._map.containsValue(this.unwrapValue(val));
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
    
    public void putAll(final Map<? extends Double, ? extends Integer> map) {
        final Iterator<? extends Entry<? extends Double, ? extends Integer>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Double, ? extends Integer> e = (Entry<? extends Double, ? extends Integer>)it.next();
            this.put((Double)e.getKey(), (Integer)e.getValue());
        }
    }
    
    protected Double wrapKey(final double k) {
        return k;
    }
    
    protected double unwrapKey(final Object key) {
        return (double)key;
    }
    
    protected Integer wrapValue(final int k) {
        return k;
    }
    
    protected int unwrapValue(final Object value) {
        return (int)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TDoubleIntMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
