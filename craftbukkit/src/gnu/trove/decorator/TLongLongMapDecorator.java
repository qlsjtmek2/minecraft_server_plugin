// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TLongLongIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TLongLongMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TLongLongMapDecorator extends AbstractMap<Long, Long> implements Map<Long, Long>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TLongLongMap _map;
    
    public TLongLongMapDecorator() {
    }
    
    public TLongLongMapDecorator(final TLongLongMap map) {
        this._map = map;
    }
    
    public TLongLongMap getMap() {
        return this._map;
    }
    
    public Long put(final Long key, final Long value) {
        long k;
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
        final long v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Long, Long>> entrySet() {
        return new AbstractSet<Entry<Long, Long>>() {
            public int size() {
                return TLongLongMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TLongLongMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TLongLongMapDecorator.this.containsKey(k) && TLongLongMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Long, Long>> iterator() {
                return new Iterator<Entry<Long, Long>>() {
                    private final TLongLongIterator it = TLongLongMapDecorator.this._map.iterator();
                    
                    public Entry<Long, Long> next() {
                        this.it.advance();
                        final long ik = this.it.key();
                        final Long key = (ik == TLongLongMapDecorator.this._map.getNoEntryKey()) ? null : TLongLongMapDecorator.this.wrapKey(ik);
                        final long iv = this.it.value();
                        final Long v = (iv == TLongLongMapDecorator.this._map.getNoEntryValue()) ? null : TLongLongMapDecorator.this.wrapValue(iv);
                        return new Entry<Long, Long>() {
                            private Long val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Long getKey() {
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
                                return TLongLongMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Long, Long> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Long key = ((Entry)o).getKey();
                    TLongLongMapDecorator.this._map.remove(TLongLongMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Long, Long>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TLongLongMapDecorator.this.clear();
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
        return key instanceof Long && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Long, ? extends Long> map) {
        final Iterator<? extends Entry<? extends Long, ? extends Long>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Long, ? extends Long> e = (Entry<? extends Long, ? extends Long>)it.next();
            this.put((Long)e.getKey(), (Long)e.getValue());
        }
    }
    
    protected Long wrapKey(final long k) {
        return k;
    }
    
    protected long unwrapKey(final Object key) {
        return (long)key;
    }
    
    protected Long wrapValue(final long k) {
        return k;
    }
    
    protected long unwrapValue(final Object value) {
        return (long)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TLongLongMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
