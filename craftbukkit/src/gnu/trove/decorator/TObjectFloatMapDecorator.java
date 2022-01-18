// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TObjectFloatIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TObjectFloatMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TObjectFloatMapDecorator<K> extends AbstractMap<K, Float> implements Map<K, Float>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TObjectFloatMap<K> _map;
    
    public TObjectFloatMapDecorator() {
    }
    
    public TObjectFloatMapDecorator(final TObjectFloatMap<K> map) {
        this._map = map;
    }
    
    public TObjectFloatMap<K> getMap() {
        return this._map;
    }
    
    public Float put(final K key, final Float value) {
        if (value == null) {
            return this.wrapValue(this._map.put(key, this._map.getNoEntryValue()));
        }
        return this.wrapValue(this._map.put(key, this.unwrapValue(value)));
    }
    
    public Float get(final Object key) {
        final float v = this._map.get(key);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Float remove(final Object key) {
        final float v = this._map.remove(key);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<K, Float>> entrySet() {
        return new AbstractSet<Entry<K, Float>>() {
            public int size() {
                return TObjectFloatMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TObjectFloatMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TObjectFloatMapDecorator.this.containsKey(k) && TObjectFloatMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<K, Float>> iterator() {
                return new Iterator<Entry<K, Float>>() {
                    private final TObjectFloatIterator<K> it = TObjectFloatMapDecorator.this._map.iterator();
                    
                    public Entry<K, Float> next() {
                        this.it.advance();
                        final K key = this.it.key();
                        final Float v = TObjectFloatMapDecorator.this.wrapValue(this.it.value());
                        return new Entry<K, Float>() {
                            private Float val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public K getKey() {
                                return key;
                            }
                            
                            public Float getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Float setValue(final Float value) {
                                this.val = value;
                                return TObjectFloatMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<K, Float> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final K key = ((Entry)o).getKey();
                    TObjectFloatMapDecorator.this._map.remove(key);
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<K, Float>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TObjectFloatMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Float && this._map.containsValue(this.unwrapValue(val));
    }
    
    public boolean containsKey(final Object key) {
        return this._map.containsKey(key);
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this._map.size() == 0;
    }
    
    public void putAll(final Map<? extends K, ? extends Float> map) {
        final Iterator<? extends Entry<? extends K, ? extends Float>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends K, ? extends Float> e = (Entry<? extends K, ? extends Float>)it.next();
            this.put(e.getKey(), (Float)e.getValue());
        }
    }
    
    protected Float wrapValue(final float k) {
        return k;
    }
    
    protected float unwrapValue(final Object value) {
        return (float)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TObjectFloatMap<K>)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
