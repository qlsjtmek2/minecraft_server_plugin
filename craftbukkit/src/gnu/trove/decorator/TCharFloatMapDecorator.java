// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TCharFloatIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TCharFloatMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TCharFloatMapDecorator extends AbstractMap<Character, Float> implements Map<Character, Float>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TCharFloatMap _map;
    
    public TCharFloatMapDecorator() {
    }
    
    public TCharFloatMapDecorator(final TCharFloatMap map) {
        this._map = map;
    }
    
    public TCharFloatMap getMap() {
        return this._map;
    }
    
    public Float put(final Character key, final Float value) {
        char k;
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
        char k;
        if (key != null) {
            if (!(key instanceof Character)) {
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
        char k;
        if (key != null) {
            if (!(key instanceof Character)) {
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
    
    public Set<Entry<Character, Float>> entrySet() {
        return new AbstractSet<Entry<Character, Float>>() {
            public int size() {
                return TCharFloatMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TCharFloatMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TCharFloatMapDecorator.this.containsKey(k) && TCharFloatMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Character, Float>> iterator() {
                return new Iterator<Entry<Character, Float>>() {
                    private final TCharFloatIterator it = TCharFloatMapDecorator.this._map.iterator();
                    
                    public Entry<Character, Float> next() {
                        this.it.advance();
                        final char ik = this.it.key();
                        final Character key = (ik == TCharFloatMapDecorator.this._map.getNoEntryKey()) ? null : TCharFloatMapDecorator.this.wrapKey(ik);
                        final float iv = this.it.value();
                        final Float v = (iv == TCharFloatMapDecorator.this._map.getNoEntryValue()) ? null : TCharFloatMapDecorator.this.wrapValue(iv);
                        return new Entry<Character, Float>() {
                            private Float val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Character getKey() {
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
                                return TCharFloatMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Character, Float> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Character key = ((Entry)o).getKey();
                    TCharFloatMapDecorator.this._map.remove(TCharFloatMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Character, Float>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TCharFloatMapDecorator.this.clear();
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
        return key instanceof Character && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Character, ? extends Float> map) {
        final Iterator<? extends Entry<? extends Character, ? extends Float>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Character, ? extends Float> e = (Entry<? extends Character, ? extends Float>)it.next();
            this.put((Character)e.getKey(), (Float)e.getValue());
        }
    }
    
    protected Character wrapKey(final char k) {
        return k;
    }
    
    protected char unwrapKey(final Object key) {
        return (char)key;
    }
    
    protected Float wrapValue(final float k) {
        return k;
    }
    
    protected float unwrapValue(final Object value) {
        return (float)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TCharFloatMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
