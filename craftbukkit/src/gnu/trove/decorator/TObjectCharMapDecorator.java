// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TObjectCharIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TObjectCharMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TObjectCharMapDecorator<K> extends AbstractMap<K, Character> implements Map<K, Character>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TObjectCharMap<K> _map;
    
    public TObjectCharMapDecorator() {
    }
    
    public TObjectCharMapDecorator(final TObjectCharMap<K> map) {
        this._map = map;
    }
    
    public TObjectCharMap<K> getMap() {
        return this._map;
    }
    
    public Character put(final K key, final Character value) {
        if (value == null) {
            return this.wrapValue(this._map.put(key, this._map.getNoEntryValue()));
        }
        return this.wrapValue(this._map.put(key, this.unwrapValue(value)));
    }
    
    public Character get(final Object key) {
        final char v = this._map.get(key);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Character remove(final Object key) {
        final char v = this._map.remove(key);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<K, Character>> entrySet() {
        return new AbstractSet<Entry<K, Character>>() {
            public int size() {
                return TObjectCharMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TObjectCharMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TObjectCharMapDecorator.this.containsKey(k) && TObjectCharMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<K, Character>> iterator() {
                return new Iterator<Entry<K, Character>>() {
                    private final TObjectCharIterator<K> it = TObjectCharMapDecorator.this._map.iterator();
                    
                    public Entry<K, Character> next() {
                        this.it.advance();
                        final K key = this.it.key();
                        final Character v = TObjectCharMapDecorator.this.wrapValue(this.it.value());
                        return new Entry<K, Character>() {
                            private Character val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public K getKey() {
                                return key;
                            }
                            
                            public Character getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Character setValue(final Character value) {
                                this.val = value;
                                return TObjectCharMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<K, Character> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final K key = ((Entry)o).getKey();
                    TObjectCharMapDecorator.this._map.remove(key);
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<K, Character>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TObjectCharMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Character && this._map.containsValue(this.unwrapValue(val));
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
    
    public void putAll(final Map<? extends K, ? extends Character> map) {
        final Iterator<? extends Entry<? extends K, ? extends Character>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends K, ? extends Character> e = (Entry<? extends K, ? extends Character>)it.next();
            this.put(e.getKey(), (Character)e.getValue());
        }
    }
    
    protected Character wrapValue(final char k) {
        return k;
    }
    
    protected char unwrapValue(final Object value) {
        return (char)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TObjectCharMap<K>)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
