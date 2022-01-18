// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TFloatByteIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TFloatByteMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TFloatByteMapDecorator extends AbstractMap<Float, Byte> implements Map<Float, Byte>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TFloatByteMap _map;
    
    public TFloatByteMapDecorator() {
    }
    
    public TFloatByteMapDecorator(final TFloatByteMap map) {
        this._map = map;
    }
    
    public TFloatByteMap getMap() {
        return this._map;
    }
    
    public Byte put(final Float key, final Byte value) {
        float k;
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
        float k;
        if (key != null) {
            if (!(key instanceof Float)) {
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
        float k;
        if (key != null) {
            if (!(key instanceof Float)) {
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
    
    public Set<Entry<Float, Byte>> entrySet() {
        return new AbstractSet<Entry<Float, Byte>>() {
            public int size() {
                return TFloatByteMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TFloatByteMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TFloatByteMapDecorator.this.containsKey(k) && TFloatByteMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Float, Byte>> iterator() {
                return new Iterator<Entry<Float, Byte>>() {
                    private final TFloatByteIterator it = TFloatByteMapDecorator.this._map.iterator();
                    
                    public Entry<Float, Byte> next() {
                        this.it.advance();
                        final float ik = this.it.key();
                        final Float key = (ik == TFloatByteMapDecorator.this._map.getNoEntryKey()) ? null : TFloatByteMapDecorator.this.wrapKey(ik);
                        final byte iv = this.it.value();
                        final Byte v = (iv == TFloatByteMapDecorator.this._map.getNoEntryValue()) ? null : TFloatByteMapDecorator.this.wrapValue(iv);
                        return new Entry<Float, Byte>() {
                            private Byte val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Float getKey() {
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
                                return TFloatByteMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Float, Byte> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Float key = ((Entry)o).getKey();
                    TFloatByteMapDecorator.this._map.remove(TFloatByteMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Float, Byte>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TFloatByteMapDecorator.this.clear();
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
        return key instanceof Float && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Float, ? extends Byte> map) {
        final Iterator<? extends Entry<? extends Float, ? extends Byte>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Float, ? extends Byte> e = (Entry<? extends Float, ? extends Byte>)it.next();
            this.put((Float)e.getKey(), (Byte)e.getValue());
        }
    }
    
    protected Float wrapKey(final float k) {
        return k;
    }
    
    protected float unwrapKey(final Object key) {
        return (float)key;
    }
    
    protected Byte wrapValue(final byte k) {
        return k;
    }
    
    protected byte unwrapValue(final Object value) {
        return (byte)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TFloatByteMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
