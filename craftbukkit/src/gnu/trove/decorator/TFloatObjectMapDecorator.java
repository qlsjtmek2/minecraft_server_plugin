// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TFloatObjectIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TFloatObjectMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TFloatObjectMapDecorator<V> extends AbstractMap<Float, V> implements Map<Float, V>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TFloatObjectMap<V> _map;
    
    public TFloatObjectMapDecorator() {
    }
    
    public TFloatObjectMapDecorator(final TFloatObjectMap<V> map) {
        this._map = map;
    }
    
    public TFloatObjectMap<V> getMap() {
        return this._map;
    }
    
    public V put(final Float key, final V value) {
        float k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        return this._map.put(k, value);
    }
    
    public V get(final Object key) {
        float k;
        if (key != null) {
            if (!(key instanceof Float)) {
                return null;
            }
            k = this.unwrapKey((Float)key);
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
        float k;
        if (key != null) {
            if (!(key instanceof Float)) {
                return null;
            }
            k = this.unwrapKey((Float)key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        return this._map.remove(k);
    }
    
    public Set<Entry<Float, V>> entrySet() {
        return new AbstractSet<Entry<Float, V>>() {
            public int size() {
                return TFloatObjectMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TFloatObjectMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TFloatObjectMapDecorator.this.containsKey(k) && TFloatObjectMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Float, V>> iterator() {
                return new Iterator<Entry<Float, V>>() {
                    private final TFloatObjectIterator<V> it = TFloatObjectMapDecorator.this._map.iterator();
                    
                    public Entry<Float, V> next() {
                        this.it.advance();
                        final float k = this.it.key();
                        final Float key = (k == TFloatObjectMapDecorator.this._map.getNoEntryKey()) ? null : TFloatObjectMapDecorator.this.wrapKey(k);
                        final V v = this.it.value();
                        return new Entry<Float, V>() {
                            private V val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Float getKey() {
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
                                return TFloatObjectMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Float, V> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Float key = ((Entry)o).getKey();
                    TFloatObjectMapDecorator.this._map.remove(TFloatObjectMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Float, V>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TFloatObjectMapDecorator.this.clear();
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
        return key instanceof Float && this._map.containsKey((float)key);
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Float, ? extends V> map) {
        final Iterator<? extends Entry<? extends Float, ? extends V>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Float, ? extends V> e = (Entry<? extends Float, ? extends V>)it.next();
            this.put((Float)e.getKey(), e.getValue());
        }
    }
    
    protected Float wrapKey(final float k) {
        return k;
    }
    
    protected float unwrapKey(final Float key) {
        return key;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TFloatObjectMap<V>)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
