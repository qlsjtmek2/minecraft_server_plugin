// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TFloatShortIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TFloatShortMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TFloatShortMapDecorator extends AbstractMap<Float, Short> implements Map<Float, Short>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TFloatShortMap _map;
    
    public TFloatShortMapDecorator() {
    }
    
    public TFloatShortMapDecorator(final TFloatShortMap map) {
        this._map = map;
    }
    
    public TFloatShortMap getMap() {
        return this._map;
    }
    
    public Short put(final Float key, final Short value) {
        float k;
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
        final short v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Float, Short>> entrySet() {
        return new AbstractSet<Entry<Float, Short>>() {
            public int size() {
                return TFloatShortMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TFloatShortMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TFloatShortMapDecorator.this.containsKey(k) && TFloatShortMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Float, Short>> iterator() {
                return new Iterator<Entry<Float, Short>>() {
                    private final TFloatShortIterator it = TFloatShortMapDecorator.this._map.iterator();
                    
                    public Entry<Float, Short> next() {
                        this.it.advance();
                        final float ik = this.it.key();
                        final Float key = (ik == TFloatShortMapDecorator.this._map.getNoEntryKey()) ? null : TFloatShortMapDecorator.this.wrapKey(ik);
                        final short iv = this.it.value();
                        final Short v = (iv == TFloatShortMapDecorator.this._map.getNoEntryValue()) ? null : TFloatShortMapDecorator.this.wrapValue(iv);
                        return new Entry<Float, Short>() {
                            private Short val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Float getKey() {
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
                                return TFloatShortMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Float, Short> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Float key = ((Entry)o).getKey();
                    TFloatShortMapDecorator.this._map.remove(TFloatShortMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Float, Short>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TFloatShortMapDecorator.this.clear();
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
        return key instanceof Float && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Float, ? extends Short> map) {
        final Iterator<? extends Entry<? extends Float, ? extends Short>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Float, ? extends Short> e = (Entry<? extends Float, ? extends Short>)it.next();
            this.put((Float)e.getKey(), (Short)e.getValue());
        }
    }
    
    protected Float wrapKey(final float k) {
        return k;
    }
    
    protected float unwrapKey(final Object key) {
        return (float)key;
    }
    
    protected Short wrapValue(final short k) {
        return k;
    }
    
    protected short unwrapValue(final Object value) {
        return (short)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TFloatShortMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
