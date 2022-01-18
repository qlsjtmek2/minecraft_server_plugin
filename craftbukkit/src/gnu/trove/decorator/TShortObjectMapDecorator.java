// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TShortObjectIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TShortObjectMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TShortObjectMapDecorator<V> extends AbstractMap<Short, V> implements Map<Short, V>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TShortObjectMap<V> _map;
    
    public TShortObjectMapDecorator() {
    }
    
    public TShortObjectMapDecorator(final TShortObjectMap<V> map) {
        this._map = map;
    }
    
    public TShortObjectMap<V> getMap() {
        return this._map;
    }
    
    public V put(final Short key, final V value) {
        short k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        return this._map.put(k, value);
    }
    
    public V get(final Object key) {
        short k;
        if (key != null) {
            if (!(key instanceof Short)) {
                return null;
            }
            k = this.unwrapKey((Short)key);
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
        short k;
        if (key != null) {
            if (!(key instanceof Short)) {
                return null;
            }
            k = this.unwrapKey((Short)key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        return this._map.remove(k);
    }
    
    public Set<Entry<Short, V>> entrySet() {
        return new AbstractSet<Entry<Short, V>>() {
            public int size() {
                return TShortObjectMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TShortObjectMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TShortObjectMapDecorator.this.containsKey(k) && TShortObjectMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Short, V>> iterator() {
                return new Iterator<Entry<Short, V>>() {
                    private final TShortObjectIterator<V> it = TShortObjectMapDecorator.this._map.iterator();
                    
                    public Entry<Short, V> next() {
                        this.it.advance();
                        final short k = this.it.key();
                        final Short key = (k == TShortObjectMapDecorator.this._map.getNoEntryKey()) ? null : TShortObjectMapDecorator.this.wrapKey(k);
                        final V v = this.it.value();
                        return new Entry<Short, V>() {
                            private V val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Short getKey() {
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
                                return TShortObjectMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Short, V> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Short key = ((Entry)o).getKey();
                    TShortObjectMapDecorator.this._map.remove(TShortObjectMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Short, V>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TShortObjectMapDecorator.this.clear();
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
        return key instanceof Short && this._map.containsKey((short)key);
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Short, ? extends V> map) {
        final Iterator<? extends Entry<? extends Short, ? extends V>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Short, ? extends V> e = (Entry<? extends Short, ? extends V>)it.next();
            this.put((Short)e.getKey(), e.getValue());
        }
    }
    
    protected Short wrapKey(final short k) {
        return k;
    }
    
    protected short unwrapKey(final Short key) {
        return key;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TShortObjectMap<V>)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
