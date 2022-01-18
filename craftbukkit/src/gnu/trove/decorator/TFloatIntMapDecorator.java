// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TFloatIntIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TFloatIntMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TFloatIntMapDecorator extends AbstractMap<Float, Integer> implements Map<Float, Integer>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TFloatIntMap _map;
    
    public TFloatIntMapDecorator() {
    }
    
    public TFloatIntMapDecorator(final TFloatIntMap map) {
        this._map = map;
    }
    
    public TFloatIntMap getMap() {
        return this._map;
    }
    
    public Integer put(final Float key, final Integer value) {
        float k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        int v;
        if (value == null) {
            v = this._map.getNoEntryValue();
        }
        else {
            v = this.unwrapValue(value);
        }
        final int retval = this._map.put(k, v);
        if (retval == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(retval);
    }
    
    public Integer get(final Object key) {
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
        final int v = this._map.get(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Integer remove(final Object key) {
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
        final int v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Float, Integer>> entrySet() {
        return new AbstractSet<Entry<Float, Integer>>() {
            public int size() {
                return TFloatIntMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TFloatIntMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TFloatIntMapDecorator.this.containsKey(k) && TFloatIntMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Float, Integer>> iterator() {
                return new Iterator<Entry<Float, Integer>>() {
                    private final TFloatIntIterator it = TFloatIntMapDecorator.this._map.iterator();
                    
                    public Entry<Float, Integer> next() {
                        this.it.advance();
                        final float ik = this.it.key();
                        final Float key = (ik == TFloatIntMapDecorator.this._map.getNoEntryKey()) ? null : TFloatIntMapDecorator.this.wrapKey(ik);
                        final int iv = this.it.value();
                        final Integer v = (iv == TFloatIntMapDecorator.this._map.getNoEntryValue()) ? null : TFloatIntMapDecorator.this.wrapValue(iv);
                        return new Entry<Float, Integer>() {
                            private Integer val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Float getKey() {
                                return key;
                            }
                            
                            public Integer getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Integer setValue(final Integer value) {
                                this.val = value;
                                return TFloatIntMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Float, Integer> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Float key = ((Entry)o).getKey();
                    TFloatIntMapDecorator.this._map.remove(TFloatIntMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Float, Integer>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TFloatIntMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Integer && this._map.containsValue(this.unwrapValue(val));
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
    
    public void putAll(final Map<? extends Float, ? extends Integer> map) {
        final Iterator<? extends Entry<? extends Float, ? extends Integer>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Float, ? extends Integer> e = (Entry<? extends Float, ? extends Integer>)it.next();
            this.put((Float)e.getKey(), (Integer)e.getValue());
        }
    }
    
    protected Float wrapKey(final float k) {
        return k;
    }
    
    protected float unwrapKey(final Object key) {
        return (float)key;
    }
    
    protected Integer wrapValue(final int k) {
        return k;
    }
    
    protected int unwrapValue(final Object value) {
        return (int)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TFloatIntMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
