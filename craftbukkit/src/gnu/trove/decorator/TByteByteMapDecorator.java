// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TByteByteIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TByteByteMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TByteByteMapDecorator extends AbstractMap<Byte, Byte> implements Map<Byte, Byte>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TByteByteMap _map;
    
    public TByteByteMapDecorator() {
    }
    
    public TByteByteMapDecorator(final TByteByteMap map) {
        this._map = map;
    }
    
    public TByteByteMap getMap() {
        return this._map;
    }
    
    public Byte put(final Byte key, final Byte value) {
        byte k;
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
        byte k;
        if (key != null) {
            if (!(key instanceof Byte)) {
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
        byte k;
        if (key != null) {
            if (!(key instanceof Byte)) {
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
    
    public Set<Entry<Byte, Byte>> entrySet() {
        return new AbstractSet<Entry<Byte, Byte>>() {
            public int size() {
                return TByteByteMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TByteByteMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TByteByteMapDecorator.this.containsKey(k) && TByteByteMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Byte, Byte>> iterator() {
                return new Iterator<Entry<Byte, Byte>>() {
                    private final TByteByteIterator it = TByteByteMapDecorator.this._map.iterator();
                    
                    public Entry<Byte, Byte> next() {
                        this.it.advance();
                        final byte ik = this.it.key();
                        final Byte key = (ik == TByteByteMapDecorator.this._map.getNoEntryKey()) ? null : TByteByteMapDecorator.this.wrapKey(ik);
                        final byte iv = this.it.value();
                        final Byte v = (iv == TByteByteMapDecorator.this._map.getNoEntryValue()) ? null : TByteByteMapDecorator.this.wrapValue(iv);
                        return new Entry<Byte, Byte>() {
                            private Byte val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Byte getKey() {
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
                                return TByteByteMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Byte, Byte> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Byte key = ((Entry)o).getKey();
                    TByteByteMapDecorator.this._map.remove(TByteByteMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Byte, Byte>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TByteByteMapDecorator.this.clear();
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
        return key instanceof Byte && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Byte, ? extends Byte> map) {
        final Iterator<? extends Entry<? extends Byte, ? extends Byte>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Byte, ? extends Byte> e = (Entry<? extends Byte, ? extends Byte>)it.next();
            this.put((Byte)e.getKey(), (Byte)e.getValue());
        }
    }
    
    protected Byte wrapKey(final byte k) {
        return k;
    }
    
    protected byte unwrapKey(final Object key) {
        return (byte)key;
    }
    
    protected Byte wrapValue(final byte k) {
        return k;
    }
    
    protected byte unwrapValue(final Object value) {
        return (byte)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TByteByteMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
