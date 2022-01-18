// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TByteIntIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TByteIntMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TByteIntMapDecorator extends AbstractMap<Byte, Integer> implements Map<Byte, Integer>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TByteIntMap _map;
    
    public TByteIntMapDecorator() {
    }
    
    public TByteIntMapDecorator(final TByteIntMap map) {
        this._map = map;
    }
    
    public TByteIntMap getMap() {
        return this._map;
    }
    
    public Integer put(final Byte key, final Integer value) {
        byte k;
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
        final int v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Byte, Integer>> entrySet() {
        return new AbstractSet<Entry<Byte, Integer>>() {
            public int size() {
                return TByteIntMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TByteIntMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TByteIntMapDecorator.this.containsKey(k) && TByteIntMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Byte, Integer>> iterator() {
                return new Iterator<Entry<Byte, Integer>>() {
                    private final TByteIntIterator it = TByteIntMapDecorator.this._map.iterator();
                    
                    public Entry<Byte, Integer> next() {
                        this.it.advance();
                        final byte ik = this.it.key();
                        final Byte key = (ik == TByteIntMapDecorator.this._map.getNoEntryKey()) ? null : TByteIntMapDecorator.this.wrapKey(ik);
                        final int iv = this.it.value();
                        final Integer v = (iv == TByteIntMapDecorator.this._map.getNoEntryValue()) ? null : TByteIntMapDecorator.this.wrapValue(iv);
                        return new Entry<Byte, Integer>() {
                            private Integer val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Byte getKey() {
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
                                return TByteIntMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Byte, Integer> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Byte key = ((Entry)o).getKey();
                    TByteIntMapDecorator.this._map.remove(TByteIntMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Byte, Integer>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TByteIntMapDecorator.this.clear();
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
        return key instanceof Byte && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Byte, ? extends Integer> map) {
        final Iterator<? extends Entry<? extends Byte, ? extends Integer>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Byte, ? extends Integer> e = (Entry<? extends Byte, ? extends Integer>)it.next();
            this.put((Byte)e.getKey(), (Integer)e.getValue());
        }
    }
    
    protected Byte wrapKey(final byte k) {
        return k;
    }
    
    protected byte unwrapKey(final Object key) {
        return (byte)key;
    }
    
    protected Integer wrapValue(final int k) {
        return k;
    }
    
    protected int unwrapValue(final Object value) {
        return (int)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TByteIntMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
