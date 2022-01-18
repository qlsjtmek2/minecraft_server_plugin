// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TLongByteIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TLongByteMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TLongByteMapDecorator extends AbstractMap<Long, Byte> implements Map<Long, Byte>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TLongByteMap _map;
    
    public TLongByteMapDecorator() {
    }
    
    public TLongByteMapDecorator(final TLongByteMap map) {
        this._map = map;
    }
    
    public TLongByteMap getMap() {
        return this._map;
    }
    
    public Byte put(final Long key, final Byte value) {
        long k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        byte v;
        if (value == null) {
            v = this._map.getNoEntryValue();
        }
        else {
            v = this.unwrapValue(value);
        }
        final byte retval = this._map.put(k, v);
        if (retval == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(retval);
    }
    
    public Byte get(final Object key) {
        long k;
        if (key != null) {
            if (!(key instanceof Long)) {
                return null;
            }
            k = this.unwrapKey(key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        final byte v = this._map.get(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Byte remove(final Object key) {
        long k;
        if (key != null) {
            if (!(key instanceof Long)) {
                return null;
            }
            k = this.unwrapKey(key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        final byte v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Long, Byte>> entrySet() {
        return new AbstractSet<Entry<Long, Byte>>() {
            public int size() {
                return TLongByteMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TLongByteMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TLongByteMapDecorator.this.containsKey(k) && TLongByteMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Long, Byte>> iterator() {
                return new Iterator<Entry<Long, Byte>>() {
                    private final TLongByteIterator it = TLongByteMapDecorator.this._map.iterator();
                    
                    public Entry<Long, Byte> next() {
                        this.it.advance();
                        final long ik = this.it.key();
                        final Long key = (ik == TLongByteMapDecorator.this._map.getNoEntryKey()) ? null : TLongByteMapDecorator.this.wrapKey(ik);
                        final byte iv = this.it.value();
                        final Byte v = (iv == TLongByteMapDecorator.this._map.getNoEntryValue()) ? null : TLongByteMapDecorator.this.wrapValue(iv);
                        return new Entry<Long, Byte>() {
                            private Byte val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Long getKey() {
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
                                return TLongByteMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Long, Byte> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Long key = ((Entry)o).getKey();
                    TLongByteMapDecorator.this._map.remove(TLongByteMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Long, Byte>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TLongByteMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Byte && this._map.containsValue(this.unwrapValue(val));
    }
    
    public boolean containsKey(final Object key) {
        if (key == null) {
            return this._map.containsKey(this._map.getNoEntryKey());
        }
        return key instanceof Long && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Long, ? extends Byte> map) {
        final Iterator<? extends Entry<? extends Long, ? extends Byte>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Long, ? extends Byte> e = (Entry<? extends Long, ? extends Byte>)it.next();
            this.put((Long)e.getKey(), (Byte)e.getValue());
        }
    }
    
    protected Long wrapKey(final long k) {
        return k;
    }
    
    protected long unwrapKey(final Object key) {
        return (long)key;
    }
    
    protected Byte wrapValue(final byte k) {
        return k;
    }
    
    protected byte unwrapValue(final Object value) {
        return (byte)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TLongByteMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
