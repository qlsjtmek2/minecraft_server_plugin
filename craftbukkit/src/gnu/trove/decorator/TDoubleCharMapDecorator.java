// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TDoubleCharIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TDoubleCharMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TDoubleCharMapDecorator extends AbstractMap<Double, Character> implements Map<Double, Character>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TDoubleCharMap _map;
    
    public TDoubleCharMapDecorator() {
    }
    
    public TDoubleCharMapDecorator(final TDoubleCharMap map) {
        this._map = map;
    }
    
    public TDoubleCharMap getMap() {
        return this._map;
    }
    
    public Character put(final Double key, final Character value) {
        double k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        char v;
        if (value == null) {
            v = this._map.getNoEntryValue();
        }
        else {
            v = this.unwrapValue(value);
        }
        final char retval = this._map.put(k, v);
        if (retval == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(retval);
    }
    
    public Character get(final Object key) {
        double k;
        if (key != null) {
            if (!(key instanceof Double)) {
                return null;
            }
            k = this.unwrapKey(key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        final char v = this._map.get(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Character remove(final Object key) {
        double k;
        if (key != null) {
            if (!(key instanceof Double)) {
                return null;
            }
            k = this.unwrapKey(key);
        }
        else {
            k = this._map.getNoEntryKey();
        }
        final char v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Double, Character>> entrySet() {
        return new AbstractSet<Entry<Double, Character>>() {
            public int size() {
                return TDoubleCharMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TDoubleCharMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TDoubleCharMapDecorator.this.containsKey(k) && TDoubleCharMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Double, Character>> iterator() {
                return new Iterator<Entry<Double, Character>>() {
                    private final TDoubleCharIterator it = TDoubleCharMapDecorator.this._map.iterator();
                    
                    public Entry<Double, Character> next() {
                        this.it.advance();
                        final double ik = this.it.key();
                        final Double key = (ik == TDoubleCharMapDecorator.this._map.getNoEntryKey()) ? null : TDoubleCharMapDecorator.this.wrapKey(ik);
                        final char iv = this.it.value();
                        final Character v = (iv == TDoubleCharMapDecorator.this._map.getNoEntryValue()) ? null : TDoubleCharMapDecorator.this.wrapValue(iv);
                        return new Entry<Double, Character>() {
                            private Character val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Double getKey() {
                                return key;
                            }
                            
                            public Character getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Character setValue(final Character value) {
                                this.val = value;
                                return TDoubleCharMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Double, Character> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Double key = ((Entry)o).getKey();
                    TDoubleCharMapDecorator.this._map.remove(TDoubleCharMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Double, Character>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TDoubleCharMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Character && this._map.containsValue(this.unwrapValue(val));
    }
    
    public boolean containsKey(final Object key) {
        if (key == null) {
            return this._map.containsKey(this._map.getNoEntryKey());
        }
        return key instanceof Double && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Double, ? extends Character> map) {
        final Iterator<? extends Entry<? extends Double, ? extends Character>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Double, ? extends Character> e = (Entry<? extends Double, ? extends Character>)it.next();
            this.put((Double)e.getKey(), (Character)e.getValue());
        }
    }
    
    protected Double wrapKey(final double k) {
        return k;
    }
    
    protected double unwrapKey(final Object key) {
        return (double)key;
    }
    
    protected Character wrapValue(final char k) {
        return k;
    }
    
    protected char unwrapValue(final Object value) {
        return (char)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TDoubleCharMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
