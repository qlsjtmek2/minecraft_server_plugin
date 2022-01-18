// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TLongObjectIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TLongObjectMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TLongObjectMapDecorator<V> extends AbstractMap<Long, V> implements Map<Long, V>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TLongObjectMap<V> _map;
    
    public TLongObjectMapDecorator() {
    }
    
    public TLongObjectMapDecorator(final TLongObjectMap<V> map) {
        this._map = map;
    }
    
    public TLongObjectMap<V> getMap() {
        return this._map;
    }
    
    public V put(final Long key, final V value) {
        long k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        return this._map.put(k, value);
    }
    
    public V get(final Object key) {
        long k;
        if (key != null) {
            if (!(key instanceof Long)) {
                return null;
            }
            k = this.unwrapKey((Long)key);
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
        long k;
        if (key != null) {
            if (!(key instanceof Long)) {
                return null;
            }
            k = this.unwrapKey((Long)key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        return this._map.remove(k);
    }
    
    public Set<Entry<Long, V>> entrySet() {
        return new AbstractSet<Entry<Long, V>>() {
            public int size() {
                return TLongObjectMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TLongObjectMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TLongObjectMapDecorator.this.containsKey(k) && TLongObjectMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Long, V>> iterator() {
                return new Iterator<Entry<Long, V>>() {
                    private final TLongObjectIterator<V> it = TLongObjectMapDecorator.this._map.iterator();
                    
                    public Entry<Long, V> next() {
                        this.it.advance();
                        final long k = this.it.key();
                        final Long key = (k == TLongObjectMapDecorator.this._map.getNoEntryKey()) ? null : TLongObjectMapDecorator.this.wrapKey(k);
                        final V v = this.it.value();
                        return new Entry<Long, V>() {
                            private V val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Long getKey() {
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
                                return TLongObjectMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Long, V> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Long key = ((Entry)o).getKey();
                    TLongObjectMapDecorator.this._map.remove(TLongObjectMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Long, V>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TLongObjectMapDecorator.this.clear();
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
        return key instanceof Long && this._map.containsKey((long)key);
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Long, ? extends V> map) {
        final Iterator<? extends Entry<? extends Long, ? extends V>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Long, ? extends V> e = (Entry<? extends Long, ? extends V>)it.next();
            this.put((Long)e.getKey(), e.getValue());
        }
    }
    
    protected Long wrapKey(final long k) {
        return k;
    }
    
    protected long unwrapKey(final Long key) {
        return key;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TLongObjectMap<V>)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
