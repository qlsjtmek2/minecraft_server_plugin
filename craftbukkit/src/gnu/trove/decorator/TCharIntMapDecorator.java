// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TCharIntIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TCharIntMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TCharIntMapDecorator extends AbstractMap<Character, Integer> implements Map<Character, Integer>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TCharIntMap _map;
    
    public TCharIntMapDecorator() {
    }
    
    public TCharIntMapDecorator(final TCharIntMap map) {
        this._map = map;
    }
    
    public TCharIntMap getMap() {
        return this._map;
    }
    
    public Integer put(final Character key, final Integer value) {
        char k;
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
        final int v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Character, Integer>> entrySet() {
        return new AbstractSet<Entry<Character, Integer>>() {
            public int size() {
                return TCharIntMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TCharIntMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TCharIntMapDecorator.this.containsKey(k) && TCharIntMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Character, Integer>> iterator() {
                return new Iterator<Entry<Character, Integer>>() {
                    private final TCharIntIterator it = TCharIntMapDecorator.this._map.iterator();
                    
                    public Entry<Character, Integer> next() {
                        this.it.advance();
                        final char ik = this.it.key();
                        final Character key = (ik == TCharIntMapDecorator.this._map.getNoEntryKey()) ? null : TCharIntMapDecorator.this.wrapKey(ik);
                        final int iv = this.it.value();
                        final Integer v = (iv == TCharIntMapDecorator.this._map.getNoEntryValue()) ? null : TCharIntMapDecorator.this.wrapValue(iv);
                        return new Entry<Character, Integer>() {
                            private Integer val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Character getKey() {
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
                                return TCharIntMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Character, Integer> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Character key = ((Entry)o).getKey();
                    TCharIntMapDecorator.this._map.remove(TCharIntMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Character, Integer>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TCharIntMapDecorator.this.clear();
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
        return key instanceof Character && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Character, ? extends Integer> map) {
        final Iterator<? extends Entry<? extends Character, ? extends Integer>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Character, ? extends Integer> e = (Entry<? extends Character, ? extends Integer>)it.next();
            this.put((Character)e.getKey(), (Integer)e.getValue());
        }
    }
    
    protected Character wrapKey(final char k) {
        return k;
    }
    
    protected char unwrapKey(final Object key) {
        return (char)key;
    }
    
    protected Integer wrapValue(final int k) {
        return k;
    }
    
    protected int unwrapValue(final Object value) {
        return (int)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TCharIntMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
