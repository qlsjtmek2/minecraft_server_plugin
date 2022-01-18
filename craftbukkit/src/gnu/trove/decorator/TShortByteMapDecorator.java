// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TShortByteIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TShortByteMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TShortByteMapDecorator extends AbstractMap<Short, Byte> implements Map<Short, Byte>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TShortByteMap _map;
    
    public TShortByteMapDecorator() {
    }
    
    public TShortByteMapDecorator(final TShortByteMap map) {
        this._map = map;
    }
    
    public TShortByteMap getMap() {
        return this._map;
    }
    
    public Byte put(final Short key, final Byte value) {
        short k;
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
        short k;
        if (key != null) {
            if (!(key instanceof Short)) {
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
        short k;
        if (key != null) {
            if (!(key instanceof Short)) {
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
    
    public Set<Entry<Short, Byte>> entrySet() {
        return new AbstractSet<Entry<Short, Byte>>() {
            public int size() {
                return TShortByteMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TShortByteMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TShortByteMapDecorator.this.containsKey(k) && TShortByteMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Short, Byte>> iterator() {
                return new Iterator<Entry<Short, Byte>>() {
                    private final TShortByteIterator it = TShortByteMapDecorator.this._map.iterator();
                    
                    public Entry<Short, Byte> next() {
                        this.it.advance();
                        final short ik = this.it.key();
                        final Short key = (ik == TShortByteMapDecorator.this._map.getNoEntryKey()) ? null : TShortByteMapDecorator.this.wrapKey(ik);
                        final byte iv = this.it.value();
                        final Byte v = (iv == TShortByteMapDecorator.this._map.getNoEntryValue()) ? null : TShortByteMapDecorator.this.wrapValue(iv);
                        return new Entry<Short, Byte>() {
                            private Byte val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Short getKey() {
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
                                return TShortByteMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Short, Byte> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Short key = ((Entry)o).getKey();
                    TShortByteMapDecorator.this._map.remove(TShortByteMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Short, Byte>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TShortByteMapDecorator.this.clear();
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
        return key instanceof Short && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Short, ? extends Byte> map) {
        final Iterator<? extends Entry<? extends Short, ? extends Byte>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Short, ? extends Byte> e = (Entry<? extends Short, ? extends Byte>)it.next();
            this.put((Short)e.getKey(), (Byte)e.getValue());
        }
    }
    
    protected Short wrapKey(final short k) {
        return k;
    }
    
    protected short unwrapKey(final Object key) {
        return (short)key;
    }
    
    protected Byte wrapValue(final byte k) {
        return k;
    }
    
    protected byte unwrapValue(final Object value) {
        return (byte)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TShortByteMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
