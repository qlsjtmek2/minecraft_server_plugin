// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TLongCharIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TLongCharMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TLongCharMapDecorator extends AbstractMap<Long, Character> implements Map<Long, Character>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TLongCharMap _map;
    
    public TLongCharMapDecorator() {
    }
    
    public TLongCharMapDecorator(final TLongCharMap map) {
        this._map = map;
    }
    
    public TLongCharMap getMap() {
        return this._map;
    }
    
    public Character put(final Long key, final Character value) {
        long k;
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
        long k;
        if (key != null) {
            if (!(key instanceof Long)) {
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
        long k;
        if (key != null) {
            if (!(key instanceof Long)) {
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
    
    public Set<Entry<Long, Character>> entrySet() {
        return new AbstractSet<Entry<Long, Character>>() {
            public int size() {
                return TLongCharMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TLongCharMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TLongCharMapDecorator.this.containsKey(k) && TLongCharMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Long, Character>> iterator() {
                return new Iterator<Entry<Long, Character>>() {
                    private final TLongCharIterator it = TLongCharMapDecorator.this._map.iterator();
                    
                    public Entry<Long, Character> next() {
                        this.it.advance();
                        final long ik = this.it.key();
                        final Long key = (ik == TLongCharMapDecorator.this._map.getNoEntryKey()) ? null : TLongCharMapDecorator.this.wrapKey(ik);
                        final char iv = this.it.value();
                        final Character v = (iv == TLongCharMapDecorator.this._map.getNoEntryValue()) ? null : TLongCharMapDecorator.this.wrapValue(iv);
                        return new Entry<Long, Character>() {
                            private Character val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Long getKey() {
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
                                return TLongCharMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Long, Character> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Long key = ((Entry)o).getKey();
                    TLongCharMapDecorator.this._map.remove(TLongCharMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Long, Character>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TLongCharMapDecorator.this.clear();
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
        return key instanceof Long && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Long, ? extends Character> map) {
        final Iterator<? extends Entry<? extends Long, ? extends Character>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Long, ? extends Character> e = (Entry<? extends Long, ? extends Character>)it.next();
            this.put((Long)e.getKey(), (Character)e.getValue());
        }
    }
    
    protected Long wrapKey(final long k) {
        return k;
    }
    
    protected long unwrapKey(final Object key) {
        return (long)key;
    }
    
    protected Character wrapValue(final char k) {
        return k;
    }
    
    protected char unwrapValue(final Object value) {
        return (char)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TLongCharMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
