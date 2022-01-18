// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TByteFloatIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TByteFloatMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TByteFloatMapDecorator extends AbstractMap<Byte, Float> implements Map<Byte, Float>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TByteFloatMap _map;
    
    public TByteFloatMapDecorator() {
    }
    
    public TByteFloatMapDecorator(final TByteFloatMap map) {
        this._map = map;
    }
    
    public TByteFloatMap getMap() {
        return this._map;
    }
    
    public Float put(final Byte key, final Float value) {
        byte k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        float v;
        if (value == null) {
            v = this._map.getNoEntryValue();
        }
        else {
            v = this.unwrapValue(value);
        }
        final float retval = this._map.put(k, v);
        if (retval == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(retval);
    }
    
    public Float get(final Object key) {
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
        final float v = this._map.get(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Float remove(final Object key) {
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
        final float v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Byte, Float>> entrySet() {
        return new AbstractSet<Entry<Byte, Float>>() {
            public int size() {
                return TByteFloatMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TByteFloatMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TByteFloatMapDecorator.this.containsKey(k) && TByteFloatMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Byte, Float>> iterator() {
                return new Iterator<Entry<Byte, Float>>() {
                    private final TByteFloatIterator it = TByteFloatMapDecorator.this._map.iterator();
                    
                    public Entry<Byte, Float> next() {
                        this.it.advance();
                        final byte ik = this.it.key();
                        final Byte key = (ik == TByteFloatMapDecorator.this._map.getNoEntryKey()) ? null : TByteFloatMapDecorator.this.wrapKey(ik);
                        final float iv = this.it.value();
                        final Float v = (iv == TByteFloatMapDecorator.this._map.getNoEntryValue()) ? null : TByteFloatMapDecorator.this.wrapValue(iv);
                        return new Entry<Byte, Float>() {
                            private Float val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Byte getKey() {
                                return key;
                            }
                            
                            public Float getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Float setValue(final Float value) {
                                this.val = value;
                                return TByteFloatMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Byte, Float> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Byte key = ((Entry)o).getKey();
                    TByteFloatMapDecorator.this._map.remove(TByteFloatMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Byte, Float>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TByteFloatMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Float && this._map.containsValue(this.unwrapValue(val));
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
    
    public void putAll(final Map<? extends Byte, ? extends Float> map) {
        final Iterator<? extends Entry<? extends Byte, ? extends Float>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Byte, ? extends Float> e = (Entry<? extends Byte, ? extends Float>)it.next();
            this.put((Byte)e.getKey(), (Float)e.getValue());
        }
    }
    
    protected Byte wrapKey(final byte k) {
        return k;
    }
    
    protected byte unwrapKey(final Object key) {
        return (byte)key;
    }
    
    protected Float wrapValue(final float k) {
        return k;
    }
    
    protected float unwrapValue(final Object value) {
        return (float)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TByteFloatMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
