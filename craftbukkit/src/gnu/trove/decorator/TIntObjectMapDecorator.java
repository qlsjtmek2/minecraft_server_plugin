// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TIntObjectIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TIntObjectMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TIntObjectMapDecorator<V> extends AbstractMap<Integer, V> implements Map<Integer, V>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TIntObjectMap<V> _map;
    
    public TIntObjectMapDecorator() {
    }
    
    public TIntObjectMapDecorator(final TIntObjectMap<V> map) {
        this._map = map;
    }
    
    public TIntObjectMap<V> getMap() {
        return this._map;
    }
    
    public V put(final Integer key, final V value) {
        int k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        return this._map.put(k, value);
    }
    
    public V get(final Object key) {
        int k;
        if (key != null) {
            if (!(key instanceof Integer)) {
                return null;
            }
            k = this.unwrapKey((Integer)key);
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
        int k;
        if (key != null) {
            if (!(key instanceof Integer)) {
                return null;
            }
            k = this.unwrapKey((Integer)key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        return this._map.remove(k);
    }
    
    public Set<Entry<Integer, V>> entrySet() {
        return new AbstractSet<Entry<Integer, V>>() {
            public int size() {
                return TIntObjectMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TIntObjectMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TIntObjectMapDecorator.this.containsKey(k) && TIntObjectMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Integer, V>> iterator() {
                return new Iterator<Entry<Integer, V>>() {
                    private final TIntObjectIterator<V> it = TIntObjectMapDecorator.this._map.iterator();
                    
                    public Entry<Integer, V> next() {
                        this.it.advance();
                        final int k = this.it.key();
                        final Integer key = (k == TIntObjectMapDecorator.this._map.getNoEntryKey()) ? null : TIntObjectMapDecorator.this.wrapKey(k);
                        final V v = this.it.value();
                        return new Entry<Integer, V>() {
                            private V val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Integer getKey() {
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
                                return TIntObjectMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Integer, V> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Integer key = ((Entry)o).getKey();
                    TIntObjectMapDecorator.this._map.remove(TIntObjectMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Integer, V>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TIntObjectMapDecorator.this.clear();
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
        return key instanceof Integer && this._map.containsKey((int)key);
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Integer, ? extends V> map) {
        final Iterator<? extends Entry<? extends Integer, ? extends V>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Integer, ? extends V> e = (Entry<? extends Integer, ? extends V>)it.next();
            this.put((Integer)e.getKey(), e.getValue());
        }
    }
    
    protected Integer wrapKey(final int k) {
        return k;
    }
    
    protected int unwrapKey(final Integer key) {
        return key;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TIntObjectMap<V>)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
