// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TObjectIntIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TObjectIntMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TObjectIntMapDecorator<K> extends AbstractMap<K, Integer> implements Map<K, Integer>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TObjectIntMap<K> _map;
    
    public TObjectIntMapDecorator() {
    }
    
    public TObjectIntMapDecorator(final TObjectIntMap<K> map) {
        this._map = map;
    }
    
    public TObjectIntMap<K> getMap() {
        return this._map;
    }
    
    public Integer put(final K key, final Integer value) {
        if (value == null) {
            return this.wrapValue(this._map.put(key, this._map.getNoEntryValue()));
        }
        return this.wrapValue(this._map.put(key, this.unwrapValue(value)));
    }
    
    public Integer get(final Object key) {
        final int v = this._map.get(key);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Integer remove(final Object key) {
        final int v = this._map.remove(key);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<K, Integer>> entrySet() {
        return new AbstractSet<Entry<K, Integer>>() {
            public int size() {
                return TObjectIntMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TObjectIntMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TObjectIntMapDecorator.this.containsKey(k) && TObjectIntMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<K, Integer>> iterator() {
                return new Iterator<Entry<K, Integer>>() {
                    private final TObjectIntIterator<K> it = TObjectIntMapDecorator.this._map.iterator();
                    
                    public Entry<K, Integer> next() {
                        this.it.advance();
                        final K key = this.it.key();
                        final Integer v = TObjectIntMapDecorator.this.wrapValue(this.it.value());
                        return new Entry<K, Integer>() {
                            private Integer val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public K getKey() {
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
                                return TObjectIntMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<K, Integer> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final K key = ((Entry)o).getKey();
                    TObjectIntMapDecorator.this._map.remove(key);
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<K, Integer>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TObjectIntMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Integer && this._map.containsValue(this.unwrapValue(val));
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
    
    public void putAll(final Map<? extends K, ? extends Integer> map) {
        final Iterator<? extends Entry<? extends K, ? extends Integer>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends K, ? extends Integer> e = (Entry<? extends K, ? extends Integer>)it.next();
            this.put(e.getKey(), (Integer)e.getValue());
        }
    }
    
    protected Integer wrapValue(final int k) {
        return k;
    }
    
    protected int unwrapValue(final Object value) {
        return (int)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TObjectIntMap<K>)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
