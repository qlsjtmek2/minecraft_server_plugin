// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TLongShortIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TLongShortMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TLongShortMapDecorator extends AbstractMap<Long, Short> implements Map<Long, Short>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TLongShortMap _map;
    
    public TLongShortMapDecorator() {
    }
    
    public TLongShortMapDecorator(final TLongShortMap map) {
        this._map = map;
    }
    
    public TLongShortMap getMap() {
        return this._map;
    }
    
    public Short put(final Long key, final Short value) {
        long k;
        if (key == null) {
            k = this._map.getNoEntryKey();
        }
        else {
            k = this.unwrapKey(key);
        }
        short v;
        if (value == null) {
            v = this._map.getNoEntryValue();
        }
        else {
            v = this.unwrapValue(value);
        }
        final short retval = this._map.put(k, v);
        if (retval == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(retval);
    }
    
    public Short get(final Object key) {
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
        final short v = this._map.get(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public void clear() {
        this._map.clear();
    }
    
    public Short remove(final Object key) {
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
        final short v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Long, Short>> entrySet() {
        return new AbstractSet<Entry<Long, Short>>() {
            public int size() {
                return TLongShortMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TLongShortMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TLongShortMapDecorator.this.containsKey(k) && TLongShortMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Long, Short>> iterator() {
                return new Iterator<Entry<Long, Short>>() {
                    private final TLongShortIterator it = TLongShortMapDecorator.this._map.iterator();
                    
                    public Entry<Long, Short> next() {
                        this.it.advance();
                        final long ik = this.it.key();
                        final Long key = (ik == TLongShortMapDecorator.this._map.getNoEntryKey()) ? null : TLongShortMapDecorator.this.wrapKey(ik);
                        final short iv = this.it.value();
                        final Short v = (iv == TLongShortMapDecorator.this._map.getNoEntryValue()) ? null : TLongShortMapDecorator.this.wrapValue(iv);
                        return new Entry<Long, Short>() {
                            private Short val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Long getKey() {
                                return key;
                            }
                            
                            public Short getValue() {
                                return this.val;
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + this.val.hashCode();
                            }
                            
                            public Short setValue(final Short value) {
                                this.val = value;
                                return TLongShortMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Long, Short> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Long key = ((Entry)o).getKey();
                    TLongShortMapDecorator.this._map.remove(TLongShortMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Long, Short>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TLongShortMapDecorator.this.clear();
            }
        };
    }
    
    public boolean containsValue(final Object val) {
        return val instanceof Short && this._map.containsValue(this.unwrapValue(val));
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
    
    public void putAll(final Map<? extends Long, ? extends Short> map) {
        final Iterator<? extends Entry<? extends Long, ? extends Short>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Long, ? extends Short> e = (Entry<? extends Long, ? extends Short>)it.next();
            this.put((Long)e.getKey(), (Short)e.getValue());
        }
    }
    
    protected Long wrapKey(final long k) {
        return k;
    }
    
    protected long unwrapKey(final Object key) {
        return (long)key;
    }
    
    protected Short wrapValue(final short k) {
        return k;
    }
    
    protected short unwrapValue(final Object value) {
        return (short)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TLongShortMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
