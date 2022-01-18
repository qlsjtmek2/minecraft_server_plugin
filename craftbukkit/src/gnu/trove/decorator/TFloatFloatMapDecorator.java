// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TFloatFloatIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TFloatFloatMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TFloatFloatMapDecorator extends AbstractMap<Float, Float> implements Map<Float, Float>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TFloatFloatMap _map;
    
    public TFloatFloatMapDecorator() {
    }
    
    public TFloatFloatMapDecorator(final TFloatFloatMap map) {
        this._map = map;
    }
    
    public TFloatFloatMap getMap() {
        return this._map;
    }
    
    public Float put(final Float key, final Float value) {
        float k;
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
        final float v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Float, Float>> entrySet() {
        return new AbstractSet<Entry<Float, Float>>() {
            public int size() {
                return TFloatFloatMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TFloatFloatMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TFloatFloatMapDecorator.this.containsKey(k) && TFloatFloatMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Float, Float>> iterator() {
                return new Iterator<Entry<Float, Float>>() {
                    private final TFloatFloatIterator it = TFloatFloatMapDecorator.this._map.iterator();
                    
                    public Entry<Float, Float> next() {
                        this.it.advance();
                        final float ik = this.it.key();
                        final Float key = (ik == TFloatFloatMapDecorator.this._map.getNoEntryKey()) ? null : TFloatFloatMapDecorator.this.wrapKey(ik);
                        final float iv = this.it.value();
                        final Float v = (iv == TFloatFloatMapDecorator.this._map.getNoEntryValue()) ? null : TFloatFloatMapDecorator.this.wrapValue(iv);
                        return new Entry<Float, Float>() {
                            private Float val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Float getKey() {
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
                                return TFloatFloatMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Float, Float> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Float key = ((Entry)o).getKey();
                    TFloatFloatMapDecorator.this._map.remove(TFloatFloatMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Float, Float>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TFloatFloatMapDecorator.this.clear();
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
        return key instanceof Float && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Float, ? extends Float> map) {
        final Iterator<? extends Entry<? extends Float, ? extends Float>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Float, ? extends Float> e = (Entry<? extends Float, ? extends Float>)it.next();
            this.put((Float)e.getKey(), (Float)e.getValue());
        }
    }
    
    protected Float wrapKey(final float k) {
        return k;
    }
    
    protected float unwrapKey(final Object key) {
        return (float)key;
    }
    
    protected Float wrapValue(final float k) {
        return k;
    }
    
    protected float unwrapValue(final Object value) {
        return (float)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TFloatFloatMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
