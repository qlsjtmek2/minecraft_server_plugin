// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TObjectLongIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TObjectLongMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TObjectLongMapDecorator<K> extends AbstractMap<K, Long> implements Map<K, Long>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TObjectLongMap<K> _map;
    
    public TObjectLongMapDecorator() {
    }
    
    public TObjectLongMapDecorator(final TObjectLongMap<K> map) {
        this._map = map;
    }
    
    public TObjectLongMap<K> getMap() {
        return this._map;
    }
    
    public Long put(final K key, final Long value) {
        if (value == null) {
            return this.wrapValue(this._map.put(key, this._map.getNoEntryValue()));
        }
        return this.wrapValue(this._map.put(key, this.unwrapValue(value)));
    }
    
    public Long get(final Object key) {
        final long v = this._map.get(key);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Long remove(final Object key) {
        final long v = this._map.remove(key);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<K, Long>> entrySet() {
        return new AbstractSet<Entry<K, Long>>() {
            public int size() {
                return TObjectLongMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TObjectLongMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TObjectLongMapDecorator.this.containsKey(k) && TObjectLongMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<K, Long>> iterator() {
                return new Iterator<Entry<K, Long>>() {
                    private final TObjectLongIterator<K> it = TObjectLongMapDecorator.this._map.iterator();
                    
                    public Entry<K, Long> next() {
                        this.it.advance();
                        final K key = this.it.key();
                        final Long v = TObjectLongMapDecorator.this.wrapValue(this.it.value());
                        return new Entry<K, Long>() {
                            private Long val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public K getKey() {
                                return key;
                            }
                            
                            public Long getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Long setValue(final Long value) {
                                this.val = value;
                                return TObjectLongMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<K, Long> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final K key = ((Entry)o).getKey();
                    TObjectLongMapDecorator.this._map.remove(key);
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<K, Long>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TObjectLongMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Long && this._map.containsValue(this.unwrapValue(val));
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
    
    public void putAll(final Map<? extends K, ? extends Long> map) {
        final Iterator<? extends Entry<? extends K, ? extends Long>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends K, ? extends Long> e = (Entry<? extends K, ? extends Long>)it.next();
            this.put(e.getKey(), (Long)e.getValue());
        }
    }
    
    protected Long wrapValue(final long k) {
        return k;
    }
    
    protected long unwrapValue(final Object value) {
        return (long)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TObjectLongMap<K>)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
