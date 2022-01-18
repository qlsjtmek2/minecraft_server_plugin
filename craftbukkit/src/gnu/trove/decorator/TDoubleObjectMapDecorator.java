// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TDoubleObjectIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TDoubleObjectMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TDoubleObjectMapDecorator<V> extends AbstractMap<Double, V> implements Map<Double, V>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TDoubleObjectMap<V> _map;
    
    public TDoubleObjectMapDecorator() {
    }
    
    public TDoubleObjectMapDecorator(final TDoubleObjectMap<V> map) {
        this._map = map;
    }
    
    public TDoubleObjectMap<V> getMap() {
        return this._map;
    }
    
    public V put(final Double key, final V value) {
        double k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        return this._map.put(k, value);
    }
    
    public V get(final Object key) {
        double k;
        if (key != null) {
            if (!(key instanceof Double)) {
                return null;
            }
            k = this.unwrapKey((Double)key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        return this._map.get(k);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public V remove(final Object key) {
        double k;
        if (key != null) {
            if (!(key instanceof Double)) {
                return null;
            }
            k = this.unwrapKey((Double)key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        return this._map.remove(k);
    }
    
    public Set<Entry<Double, V>> entrySet() {
        return new AbstractSet<Entry<Double, V>>() {
            public int size() {
                return TDoubleObjectMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TDoubleObjectMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TDoubleObjectMapDecorator.this.containsKey(k) && TDoubleObjectMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Double, V>> iterator() {
                return new Iterator<Entry<Double, V>>() {
                    private final TDoubleObjectIterator<V> it = TDoubleObjectMapDecorator.this._map.iterator();
                    
                    public Entry<Double, V> next() {
                        this.it.advance();
                        final double k = this.it.key();
                        final Double key = (k == TDoubleObjectMapDecorator.this._map.getNoEntryKey()) ? null : TDoubleObjectMapDecorator.this.wrapKey(k);
                        final V v = this.it.value();
                        return new Entry<Double, V>() {
                            private V val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Double getKey() {
                                return key;
                            }
                            
                            public V getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public V setValue(final V value) {
                                this.val = value;
                                return TDoubleObjectMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Double, V> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Double key = ((Entry)o).getKey();
                    TDoubleObjectMapDecorator.this._map.remove(TDoubleObjectMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Double, V>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TDoubleObjectMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return this._map.containsValue(val);
    }
    
    public boolean containsKey(final Object key) {
        if (key == null) {
            return this._map.containsKey(this._map.getNoEntryKey());
        }
        return key instanceof Double && this._map.containsKey((double)key);
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Double, ? extends V> map) {
        final Iterator<? extends Entry<? extends Double, ? extends V>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Double, ? extends V> e = (Entry<? extends Double, ? extends V>)it.next();
            this.put((Double)e.getKey(), e.getValue());
        }
    }
    
    protected Double wrapKey(final double k) {
        return k;
    }
    
    protected double unwrapKey(final Double key) {
        return key;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TDoubleObjectMap<V>)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
