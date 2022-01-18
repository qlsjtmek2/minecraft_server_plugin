// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TCharCharIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TCharCharMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TCharCharMapDecorator extends AbstractMap<Character, Character> implements Map<Character, Character>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TCharCharMap _map;
    
    public TCharCharMapDecorator() {
    }
    
    public TCharCharMapDecorator(final TCharCharMap map) {
        this._map = map;
    }
    
    public TCharCharMap getMap() {
        return this._map;
    }
    
    public Character put(final Character key, final Character value) {
        char k;
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
        final char v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Character, Character>> entrySet() {
        return new AbstractSet<Entry<Character, Character>>() {
            public int size() {
                return TCharCharMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TCharCharMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TCharCharMapDecorator.this.containsKey(k) && TCharCharMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Character, Character>> iterator() {
                return new Iterator<Entry<Character, Character>>() {
                    private final TCharCharIterator it = TCharCharMapDecorator.this._map.iterator();
                    
                    public Entry<Character, Character> next() {
                        this.it.advance();
                        final char ik = this.it.key();
                        final Character key = (ik == TCharCharMapDecorator.this._map.getNoEntryKey()) ? null : TCharCharMapDecorator.this.wrapKey(ik);
                        final char iv = this.it.value();
                        final Character v = (iv == TCharCharMapDecorator.this._map.getNoEntryValue()) ? null : TCharCharMapDecorator.this.wrapValue(iv);
                        return new Entry<Character, Character>() {
                            private Character val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Character getKey() {
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
                                return TCharCharMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Character, Character> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Character key = ((Entry)o).getKey();
                    TCharCharMapDecorator.this._map.remove(TCharCharMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Character, Character>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TCharCharMapDecorator.this.clear();
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
        return key instanceof Character && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Character, ? extends Character> map) {
        final Iterator<? extends Entry<? extends Character, ? extends Character>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Character, ? extends Character> e = (Entry<? extends Character, ? extends Character>)it.next();
            this.put((Character)e.getKey(), (Character)e.getValue());
        }
    }
    
    protected Character wrapKey(final char k) {
        return k;
    }
    
    protected char unwrapKey(final Object key) {
        return (char)key;
    }
    
    protected Character wrapValue(final char k) {
        return k;
    }
    
    protected char unwrapValue(final Object value) {
        return (char)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TCharCharMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
