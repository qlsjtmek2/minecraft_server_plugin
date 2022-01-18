// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TShortLongIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TShortLongMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TShortLongMapDecorator extends AbstractMap<Short, Long> implements Map<Short, Long>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TShortLongMap _map;
    
    public TShortLongMapDecorator() {
    }
    
    public TShortLongMapDecorator(final TShortLongMap map) {
        this._map = map;
    }
    
    public TShortLongMap getMap() {
        return this._map;
    }
    
    public Long put(final Short key, final Long value) {
        short k;
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
        short k;
        if (key != null) {
            if (!(key instanceof Short)) {
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
        short k;
        if (key != null) {
            if (!(key instanceof Short)) {
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
    
    public Set<Entry<Short, Long>> entrySet() {
        return new AbstractSet<Entry<Short, Long>>() {
            public int size() {
                return TShortLongMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TShortLongMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TShortLongMapDecorator.this.containsKey(k) && TShortLongMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Short, Long>> iterator() {
                return new Iterator<Entry<Short, Long>>() {
                    private final TShortLongIterator it = TShortLongMapDecorator.this._map.iterator();
                    
                    public Entry<Short, Long> next() {
                        this.it.advance();
                        final short ik = this.it.key();
                        final Short key = (ik == TShortLongMapDecorator.this._map.getNoEntryKey()) ? null : TShortLongMapDecorator.this.wrapKey(ik);
                        final long iv = this.it.value();
                        final Long v = (iv == TShortLongMapDecorator.this._map.getNoEntryValue()) ? null : TShortLongMapDecorator.this.wrapValue(iv);
                        return new Entry<Short, Long>() {
                            private Long val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Short getKey() {
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
                                return TShortLongMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Short, Long> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Short key = ((Entry)o).getKey();
                    TShortLongMapDecorator.this._map.remove(TShortLongMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Short, Long>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TShortLongMapDecorator.this.clear();
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
        return key instanceof Short && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Short, ? extends Long> map) {
        final Iterator<? extends Entry<? extends Short, ? extends Long>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Short, ? extends Long> e = (Entry<? extends Short, ? extends Long>)it.next();
            this.put((Short)e.getKey(), (Long)e.getValue());
        }
    }
    
    protected Short wrapKey(final short k) {
        return k;
    }
    
    protected short unwrapKey(final Object key) {
        return (short)key;
    }
    
    protected Long wrapValue(final long k) {
        return k;
    }
    
    protected long unwrapValue(final Object value) {
        return (long)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TShortLongMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
