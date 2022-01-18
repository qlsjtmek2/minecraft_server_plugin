// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TShortFloatIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TShortFloatMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TShortFloatMapDecorator extends AbstractMap<Short, Float> implements Map<Short, Float>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TShortFloatMap _map;
    
    public TShortFloatMapDecorator() {
    }
    
    public TShortFloatMapDecorator(final TShortFloatMap map) {
        this._map = map;
    }
    
    public TShortFloatMap getMap() {
        return this._map;
    }
    
    public Float put(final Short key, final Float value) {
        short k;
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
        final float v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Short, Float>> entrySet() {
        return new AbstractSet<Entry<Short, Float>>() {
            public int size() {
                return TShortFloatMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TShortFloatMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TShortFloatMapDecorator.this.containsKey(k) && TShortFloatMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Short, Float>> iterator() {
                return new Iterator<Entry<Short, Float>>() {
                    private final TShortFloatIterator it = TShortFloatMapDecorator.this._map.iterator();
                    
                    public Entry<Short, Float> next() {
                        this.it.advance();
                        final short ik = this.it.key();
                        final Short key = (ik == TShortFloatMapDecorator.this._map.getNoEntryKey()) ? null : TShortFloatMapDecorator.this.wrapKey(ik);
                        final float iv = this.it.value();
                        final Float v = (iv == TShortFloatMapDecorator.this._map.getNoEntryValue()) ? null : TShortFloatMapDecorator.this.wrapValue(iv);
                        return new Entry<Short, Float>() {
                            private Float val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Short getKey() {
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
                                return TShortFloatMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Short, Float> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Short key = ((Entry)o).getKey();
                    TShortFloatMapDecorator.this._map.remove(TShortFloatMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Short, Float>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TShortFloatMapDecorator.this.clear();
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
        return key instanceof Short && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Short, ? extends Float> map) {
        final Iterator<? extends Entry<? extends Short, ? extends Float>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Short, ? extends Float> e = (Entry<? extends Short, ? extends Float>)it.next();
            this.put((Short)e.getKey(), (Float)e.getValue());
        }
    }
    
    protected Short wrapKey(final short k) {
        return k;
    }
    
    protected short unwrapKey(final Object key) {
        return (short)key;
    }
    
    protected Float wrapValue(final float k) {
        return k;
    }
    
    protected float unwrapValue(final Object value) {
        return (float)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TShortFloatMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
