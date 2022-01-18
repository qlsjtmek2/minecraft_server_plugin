// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TObjectByteIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TObjectByteMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TObjectByteMapDecorator<K> extends AbstractMap<K, Byte> implements Map<K, Byte>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TObjectByteMap<K> _map;
    
    public TObjectByteMapDecorator() {
    }
    
    public TObjectByteMapDecorator(final TObjectByteMap<K> map) {
        this._map = map;
    }
    
    public TObjectByteMap<K> getMap() {
        return this._map;
    }
    
    public Byte put(final K key, final Byte value) {
        if (value == null) {
            return this.wrapValue(this._map.put(key, this._map.getNoEntryValue()));
        }
        return this.wrapValue(this._map.put(key, this.unwrapValue(value)));
    }
    
    public Byte get(final Object key) {
        final byte v = this._map.get(key);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Byte remove(final Object key) {
        final byte v = this._map.remove(key);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<K, Byte>> entrySet() {
        return new AbstractSet<Entry<K, Byte>>() {
            public int size() {
                return TObjectByteMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TObjectByteMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TObjectByteMapDecorator.this.containsKey(k) && TObjectByteMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<K, Byte>> iterator() {
                return new Iterator<Entry<K, Byte>>() {
                    private final TObjectByteIterator<K> it = TObjectByteMapDecorator.this._map.iterator();
                    
                    public Entry<K, Byte> next() {
                        this.it.advance();
                        final K key = this.it.key();
                        final Byte v = TObjectByteMapDecorator.this.wrapValue(this.it.value());
                        return new Entry<K, Byte>() {
                            private Byte val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public K getKey() {
                                return key;
                            }
                            
                            public Byte getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Byte setValue(final Byte value) {
                                this.val = value;
                                return TObjectByteMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<K, Byte> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final K key = ((Entry)o).getKey();
                    TObjectByteMapDecorator.this._map.remove(key);
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<K, Byte>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TObjectByteMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Byte && this._map.containsValue(this.unwrapValue(val));
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
    
    public void putAll(final Map<? extends K, ? extends Byte> map) {
        final Iterator<? extends Entry<? extends K, ? extends Byte>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends K, ? extends Byte> e = (Entry<? extends K, ? extends Byte>)it.next();
            this.put(e.getKey(), (Byte)e.getValue());
        }
    }
    
    protected Byte wrapValue(final byte k) {
        return k;
    }
    
    protected byte unwrapValue(final Object value) {
        return (byte)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TObjectByteMap<K>)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
