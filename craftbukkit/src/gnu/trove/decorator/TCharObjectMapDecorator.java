// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TCharObjectIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TCharObjectMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TCharObjectMapDecorator<V> extends AbstractMap<Character, V> implements Map<Character, V>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TCharObjectMap<V> _map;
    
    public TCharObjectMapDecorator() {
    }
    
    public TCharObjectMapDecorator(final TCharObjectMap<V> map) {
        this._map = map;
    }
    
    public TCharObjectMap<V> getMap() {
        return this._map;
    }
    
    public V put(final Character key, final V value) {
        char k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        return this._map.put(k, value);
    }
    
    public V get(final Object key) {
        char k;
        if (key != null) {
            if (!(key instanceof Character)) {
                return null;
            }
            k = this.unwrapKey((Character)key);
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
        char k;
        if (key != null) {
            if (!(key instanceof Character)) {
                return null;
            }
            k = this.unwrapKey((Character)key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        return this._map.remove(k);
    }
    
    public Set<Entry<Character, V>> entrySet() {
        return new AbstractSet<Entry<Character, V>>() {
            public int size() {
                return TCharObjectMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TCharObjectMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TCharObjectMapDecorator.this.containsKey(k) && TCharObjectMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Character, V>> iterator() {
                return new Iterator<Entry<Character, V>>() {
                    private final TCharObjectIterator<V> it = TCharObjectMapDecorator.this._map.iterator();
                    
                    public Entry<Character, V> next() {
                        this.it.advance();
                        final char k = this.it.key();
                        final Character key = (k == TCharObjectMapDecorator.this._map.getNoEntryKey()) ? null : TCharObjectMapDecorator.this.wrapKey(k);
                        final V v = this.it.value();
                        return new Entry<Character, V>() {
                            private V val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Character getKey() {
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
                                return TCharObjectMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Character, V> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Character key = ((Entry)o).getKey();
                    TCharObjectMapDecorator.this._map.remove(TCharObjectMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Character, V>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TCharObjectMapDecorator.this.clear();
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
        return key instanceof Character && this._map.containsKey((char)key);
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Character, ? extends V> map) {
        final Iterator<? extends Entry<? extends Character, ? extends V>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Character, ? extends V> e = (Entry<? extends Character, ? extends V>)it.next();
            this.put((Character)e.getKey(), e.getValue());
        }
    }
    
    protected Character wrapKey(final char k) {
        return k;
    }
    
    protected char unwrapKey(final Character key) {
        return key;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TCharObjectMap<V>)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
