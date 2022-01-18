// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TIntCharIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TIntCharMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TIntCharMapDecorator extends AbstractMap<Integer, Character> implements Map<Integer, Character>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TIntCharMap _map;
    
    public TIntCharMapDecorator() {
    }
    
    public TIntCharMapDecorator(final TIntCharMap map) {
        this._map = map;
    }
    
    public TIntCharMap getMap() {
        return this._map;
    }
    
    public Character put(final Integer key, final Character value) {
        int k;
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
        int k;
        if (key != null) {
            if (!(key instanceof Integer)) {
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
        int k;
        if (key != null) {
            if (!(key instanceof Integer)) {
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
    
    public Set<Entry<Integer, Character>> entrySet() {
        return new AbstractSet<Entry<Integer, Character>>() {
            public int size() {
                return TIntCharMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TIntCharMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TIntCharMapDecorator.this.containsKey(k) && TIntCharMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Integer, Character>> iterator() {
                return new Iterator<Entry<Integer, Character>>() {
                    private final TIntCharIterator it = TIntCharMapDecorator.this._map.iterator();
                    
                    public Entry<Integer, Character> next() {
                        this.it.advance();
                        final int ik = this.it.key();
                        final Integer key = (ik == TIntCharMapDecorator.this._map.getNoEntryKey()) ? null : TIntCharMapDecorator.this.wrapKey(ik);
                        final char iv = this.it.value();
                        final Character v = (iv == TIntCharMapDecorator.this._map.getNoEntryValue()) ? null : TIntCharMapDecorator.this.wrapValue(iv);
                        return new Entry<Integer, Character>() {
                            private Character val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Integer getKey() {
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
                                return TIntCharMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Integer, Character> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Integer key = ((Entry)o).getKey();
                    TIntCharMapDecorator.this._map.remove(TIntCharMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Integer, Character>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TIntCharMapDecorator.this.clear();
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
        return key instanceof Integer && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Integer, ? extends Character> map) {
        final Iterator<? extends Entry<? extends Integer, ? extends Character>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Integer, ? extends Character> e = (Entry<? extends Integer, ? extends Character>)it.next();
            this.put((Integer)e.getKey(), (Character)e.getValue());
        }
    }
    
    protected Integer wrapKey(final int k) {
        return k;
    }
    
    protected int unwrapKey(final Object key) {
        return (int)key;
    }
    
    protected Character wrapValue(final char k) {
        return k;
    }
    
    protected char unwrapValue(final Object value) {
        return (char)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TIntCharMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
