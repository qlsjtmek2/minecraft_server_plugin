// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TDoubleByteIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TDoubleByteMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TDoubleByteMapDecorator extends AbstractMap<Double, Byte> implements Map<Double, Byte>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TDoubleByteMap _map;
    
    public TDoubleByteMapDecorator() {
    }
    
    public TDoubleByteMapDecorator(final TDoubleByteMap map) {
        this._map = map;
    }
    
    public TDoubleByteMap getMap() {
        return this._map;
    }
    
    public Byte put(final Double key, final Byte value) {
        double k;
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
        final byte v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Double, Byte>> entrySet() {
        return new AbstractSet<Entry<Double, Byte>>() {
            public int size() {
                return TDoubleByteMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TDoubleByteMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TDoubleByteMapDecorator.this.containsKey(k) && TDoubleByteMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Double, Byte>> iterator() {
                return new Iterator<Entry<Double, Byte>>() {
                    private final TDoubleByteIterator it = TDoubleByteMapDecorator.this._map.iterator();
                    
                    public Entry<Double, Byte> next() {
                        this.it.advance();
                        final double ik = this.it.key();
                        final Double key = (ik == TDoubleByteMapDecorator.this._map.getNoEntryKey()) ? null : TDoubleByteMapDecorator.this.wrapKey(ik);
                        final byte iv = this.it.value();
                        final Byte v = (iv == TDoubleByteMapDecorator.this._map.getNoEntryValue()) ? null : TDoubleByteMapDecorator.this.wrapValue(iv);
                        return new Entry<Double, Byte>() {
                            private Byte val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Double getKey() {
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
                                return TDoubleByteMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Double, Byte> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Double key = ((Entry)o).getKey();
                    TDoubleByteMapDecorator.this._map.remove(TDoubleByteMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Double, Byte>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TDoubleByteMapDecorator.this.clear();
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
        return key instanceof Double && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Double, ? extends Byte> map) {
        final Iterator<? extends Entry<? extends Double, ? extends Byte>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Double, ? extends Byte> e = (Entry<? extends Double, ? extends Byte>)it.next();
            this.put((Double)e.getKey(), (Byte)e.getValue());
        }
    }
    
    protected Double wrapKey(final double k) {
        return k;
    }
    
    protected double unwrapKey(final Object key) {
        return (double)key;
    }
    
    protected Byte wrapValue(final byte k) {
        return k;
    }
    
    protected byte unwrapValue(final Object value) {
        return (byte)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TDoubleByteMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
