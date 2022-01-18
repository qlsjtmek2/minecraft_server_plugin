// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TByteObjectIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TByteObjectMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TByteObjectMapDecorator<V> extends AbstractMap<Byte, V> implements Map<Byte, V>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TByteObjectMap<V> _map;
    
    public TByteObjectMapDecorator() {
    }
    
    public TByteObjectMapDecorator(final TByteObjectMap<V> map) {
        this._map = map;
    }
    
    public TByteObjectMap<V> getMap() {
        return this._map;
    }
    
    public V put(final Byte key, final V value) {
        byte k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        return this._map.put(k, value);
    }
    
    public V get(final Object key) {
        byte k;
        if (key != null) {
            if (!(key instanceof Byte)) {
                return null;
            }
            k = this.unwrapKey((Byte)key);
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
        byte k;
        if (key != null) {
            if (!(key instanceof Byte)) {
                return null;
            }
            k = this.unwrapKey((Byte)key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        return this._map.remove(k);
    }
    
    public Set<Entry<Byte, V>> entrySet() {
        return new AbstractSet<Entry<Byte, V>>() {
            public int size() {
                return TByteObjectMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TByteObjectMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TByteObjectMapDecorator.this.containsKey(k) && TByteObjectMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Byte, V>> iterator() {
                return new Iterator<Entry<Byte, V>>() {
                    private final TByteObjectIterator<V> it = TByteObjectMapDecorator.this._map.iterator();
                    
                    public Entry<Byte, V> next() {
                        this.it.advance();
                        final byte k = this.it.key();
                        final Byte key = (k == TByteObjectMapDecorator.this._map.getNoEntryKey()) ? null : TByteObjectMapDecorator.this.wrapKey(k);
                        final V v = this.it.value();
                        return new Entry<Byte, V>() {
                            private V val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Byte getKey() {
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
                                return TByteObjectMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Byte, V> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Byte key = ((Entry)o).getKey();
                    TByteObjectMapDecorator.this._map.remove(TByteObjectMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Byte, V>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TByteObjectMapDecorator.this.clear();
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
        return key instanceof Byte && this._map.containsKey((byte)key);
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Byte, ? extends V> map) {
        final Iterator<? extends Entry<? extends Byte, ? extends V>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Byte, ? extends V> e = (Entry<? extends Byte, ? extends V>)it.next();
            this.put((Byte)e.getKey(), e.getValue());
        }
    }
    
    protected Byte wrapKey(final byte k) {
        return k;
    }
    
    protected byte unwrapKey(final Byte key) {
        return key;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TByteObjectMap<V>)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
