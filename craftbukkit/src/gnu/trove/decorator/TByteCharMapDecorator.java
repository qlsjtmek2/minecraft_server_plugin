// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TByteCharIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TByteCharMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TByteCharMapDecorator extends AbstractMap<Byte, Character> implements Map<Byte, Character>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TByteCharMap _map;
    
    public TByteCharMapDecorator() {
    }
    
    public TByteCharMapDecorator(final TByteCharMap map) {
        this._map = map;
    }
    
    public TByteCharMap getMap() {
        return this._map;
    }
    
    public Character put(final Byte key, final Character value) {
        byte k;
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
        byte k;
        if (key != null) {
            if (!(key instanceof Byte)) {
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
        byte k;
        if (key != null) {
            if (!(key instanceof Byte)) {
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
    
    public Set<Entry<Byte, Character>> entrySet() {
        return new AbstractSet<Entry<Byte, Character>>() {
            public int size() {
                return TByteCharMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TByteCharMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TByteCharMapDecorator.this.containsKey(k) && TByteCharMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Byte, Character>> iterator() {
                return new Iterator<Entry<Byte, Character>>() {
                    private final TByteCharIterator it = TByteCharMapDecorator.this._map.iterator();
                    
                    public Entry<Byte, Character> next() {
                        this.it.advance();
                        final byte ik = this.it.key();
                        final Byte key = (ik == TByteCharMapDecorator.this._map.getNoEntryKey()) ? null : TByteCharMapDecorator.this.wrapKey(ik);
                        final char iv = this.it.value();
                        final Character v = (iv == TByteCharMapDecorator.this._map.getNoEntryValue()) ? null : TByteCharMapDecorator.this.wrapValue(iv);
                        return new Entry<Byte, Character>() {
                            private Character val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Byte getKey() {
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
                                return TByteCharMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Byte, Character> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Byte key = ((Entry)o).getKey();
                    TByteCharMapDecorator.this._map.remove(TByteCharMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Byte, Character>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TByteCharMapDecorator.this.clear();
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
        return key instanceof Byte && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Byte, ? extends Character> map) {
        final Iterator<? extends Entry<? extends Byte, ? extends Character>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Byte, ? extends Character> e = (Entry<? extends Byte, ? extends Character>)it.next();
            this.put((Byte)e.getKey(), (Character)e.getValue());
        }
    }
    
    protected Byte wrapKey(final byte k) {
        return k;
    }
    
    protected byte unwrapKey(final Object key) {
        return (byte)key;
    }
    
    protected Character wrapValue(final char k) {
        return k;
    }
    
    protected char unwrapValue(final Object value) {
        return (char)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TByteCharMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
