// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TByteShortIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TByteShortMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TByteShortMapDecorator extends AbstractMap<Byte, Short> implements Map<Byte, Short>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TByteShortMap _map;
    
    public TByteShortMapDecorator() {
    }
    
    public TByteShortMapDecorator(final TByteShortMap map) {
        this._map = map;
    }
    
    public TByteShortMap getMap() {
        return this._map;
    }
    
    public Short put(final Byte key, final Short value) {
        byte k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        short v;
        if (value == null) {
            v = this._map.getNoEntryValue();
        }
        else {
            v = this.unwrapValue(value);
        }
        final short retval = this._map.put(k, v);
        if (retval == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(retval);
    }
    
    public Short get(final Object key) {
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
        final short v = this._map.get(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Short remove(final Object key) {
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
        final short v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Byte, Short>> entrySet() {
        return new AbstractSet<Entry<Byte, Short>>() {
            public int size() {
                return TByteShortMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TByteShortMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TByteShortMapDecorator.this.containsKey(k) && TByteShortMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Byte, Short>> iterator() {
                return new Iterator<Entry<Byte, Short>>() {
                    private final TByteShortIterator it = TByteShortMapDecorator.this._map.iterator();
                    
                    public Entry<Byte, Short> next() {
                        this.it.advance();
                        final byte ik = this.it.key();
                        final Byte key = (ik == TByteShortMapDecorator.this._map.getNoEntryKey()) ? null : TByteShortMapDecorator.this.wrapKey(ik);
                        final short iv = this.it.value();
                        final Short v = (iv == TByteShortMapDecorator.this._map.getNoEntryValue()) ? null : TByteShortMapDecorator.this.wrapValue(iv);
                        return new Entry<Byte, Short>() {
                            private Short val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Byte getKey() {
                                return key;
                            }
                            
                            public Short getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Short setValue(final Short value) {
                                this.val = value;
                                return TByteShortMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Byte, Short> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Byte key = ((Entry)o).getKey();
                    TByteShortMapDecorator.this._map.remove(TByteShortMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Byte, Short>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TByteShortMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Short && this._map.containsValue(this.unwrapValue(val));
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
    
    public void putAll(final Map<? extends Byte, ? extends Short> map) {
        final Iterator<? extends Entry<? extends Byte, ? extends Short>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Byte, ? extends Short> e = (Entry<? extends Byte, ? extends Short>)it.next();
            this.put((Byte)e.getKey(), (Short)e.getValue());
        }
    }
    
    protected Byte wrapKey(final byte k) {
        return k;
    }
    
    protected byte unwrapKey(final Object key) {
        return (byte)key;
    }
    
    protected Short wrapValue(final short k) {
        return k;
    }
    
    protected short unwrapValue(final Object value) {
        return (short)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TByteShortMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
