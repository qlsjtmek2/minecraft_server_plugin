// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TIntByteIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TIntByteMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TIntByteMapDecorator extends AbstractMap<Integer, Byte> implements Map<Integer, Byte>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TIntByteMap _map;
    
    public TIntByteMapDecorator() {
    }
    
    public TIntByteMapDecorator(final TIntByteMap map) {
        this._map = map;
    }
    
    public TIntByteMap getMap() {
        return this._map;
    }
    
    public Byte put(final Integer key, final Byte value) {
        int k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        byte v;
        if (value == null) {
            v = this._map.getNoEntryValue();
        }
        else {
            v = this.unwrapValue(value);
        }
        final byte retval = this._map.put(k, v);
        if (retval == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(retval);
    }
    
    public Byte get(final Object key) {
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
        final byte v = this._map.get(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Byte remove(final Object key) {
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
        final byte v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Integer, Byte>> entrySet() {
        return new AbstractSet<Entry<Integer, Byte>>() {
            public int size() {
                return TIntByteMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TIntByteMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TIntByteMapDecorator.this.containsKey(k) && TIntByteMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Integer, Byte>> iterator() {
                return new Iterator<Entry<Integer, Byte>>() {
                    private final TIntByteIterator it = TIntByteMapDecorator.this._map.iterator();
                    
                    public Entry<Integer, Byte> next() {
                        this.it.advance();
                        final int ik = this.it.key();
                        final Integer key = (ik == TIntByteMapDecorator.this._map.getNoEntryKey()) ? null : TIntByteMapDecorator.this.wrapKey(ik);
                        final byte iv = this.it.value();
                        final Byte v = (iv == TIntByteMapDecorator.this._map.getNoEntryValue()) ? null : TIntByteMapDecorator.this.wrapValue(iv);
                        return new Entry<Integer, Byte>() {
                            private Byte val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Integer getKey() {
                                return key;
                            }
                            
                            public Byte getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Byte setValue(final Byte value) {
                                this.val = value;
                                return TIntByteMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Integer, Byte> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Integer key = ((Entry)o).getKey();
                    TIntByteMapDecorator.this._map.remove(TIntByteMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Integer, Byte>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TIntByteMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Byte && this._map.containsValue(this.unwrapValue(val));
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
    
    public void putAll(final Map<? extends Integer, ? extends Byte> map) {
        final Iterator<? extends Entry<? extends Integer, ? extends Byte>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Integer, ? extends Byte> e = (Entry<? extends Integer, ? extends Byte>)it.next();
            this.put((Integer)e.getKey(), (Byte)e.getValue());
        }
    }
    
    protected Integer wrapKey(final int k) {
        return k;
    }
    
    protected int unwrapKey(final Object key) {
        return (int)key;
    }
    
    protected Byte wrapValue(final byte k) {
        return k;
    }
    
    protected byte unwrapValue(final Object value) {
        return (byte)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TIntByteMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
