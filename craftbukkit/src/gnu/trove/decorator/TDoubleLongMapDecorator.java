// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TDoubleLongIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TDoubleLongMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TDoubleLongMapDecorator extends AbstractMap<Double, Long> implements Map<Double, Long>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TDoubleLongMap _map;
    
    public TDoubleLongMapDecorator() {
    }
    
    public TDoubleLongMapDecorator(final TDoubleLongMap map) {
        this._map = map;
    }
    
    public TDoubleLongMap getMap() {
        return this._map;
    }
    
    public Long put(final Double key, final Long value) {
        double k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        long v;
        if (value == null) {
            v = this._map.getNoEntryValue();
        }
        else {
            v = this.unwrapValue(value);
        }
        final long retval = this._map.put(k, v);
        if (retval == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(retval);
    }
    
    public Long get(final Object key) {
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
        final long v = this._map.get(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Long remove(final Object key) {
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
        final long v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Double, Long>> entrySet() {
        return new AbstractSet<Entry<Double, Long>>() {
            public int size() {
                return TDoubleLongMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TDoubleLongMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TDoubleLongMapDecorator.this.containsKey(k) && TDoubleLongMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Double, Long>> iterator() {
                return new Iterator<Entry<Double, Long>>() {
                    private final TDoubleLongIterator it = TDoubleLongMapDecorator.this._map.iterator();
                    
                    public Entry<Double, Long> next() {
                        this.it.advance();
                        final double ik = this.it.key();
                        final Double key = (ik == TDoubleLongMapDecorator.this._map.getNoEntryKey()) ? null : TDoubleLongMapDecorator.this.wrapKey(ik);
                        final long iv = this.it.value();
                        final Long v = (iv == TDoubleLongMapDecorator.this._map.getNoEntryValue()) ? null : TDoubleLongMapDecorator.this.wrapValue(iv);
                        return new Entry<Double, Long>() {
                            private Long val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Double getKey() {
                                return key;
                            }
                            
                            public Long getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Long setValue(final Long value) {
                                this.val = value;
                                return TDoubleLongMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Double, Long> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Double key = ((Entry)o).getKey();
                    TDoubleLongMapDecorator.this._map.remove(TDoubleLongMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Double, Long>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TDoubleLongMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Long && this._map.containsValue(this.unwrapValue(val));
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
    
    public void putAll(final Map<? extends Double, ? extends Long> map) {
        final Iterator<? extends Entry<? extends Double, ? extends Long>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Double, ? extends Long> e = (Entry<? extends Double, ? extends Long>)it.next();
            this.put((Double)e.getKey(), (Long)e.getValue());
        }
    }
    
    protected Double wrapKey(final double k) {
        return k;
    }
    
    protected double unwrapKey(final Object key) {
        return (double)key;
    }
    
    protected Long wrapValue(final long k) {
        return k;
    }
    
    protected long unwrapValue(final Object value) {
        return (long)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TDoubleLongMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
