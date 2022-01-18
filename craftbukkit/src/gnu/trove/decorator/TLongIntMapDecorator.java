// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TLongIntIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TLongIntMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TLongIntMapDecorator extends AbstractMap<Long, Integer> implements Map<Long, Integer>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TLongIntMap _map;
    
    public TLongIntMapDecorator() {
    }
    
    public TLongIntMapDecorator(final TLongIntMap map) {
        this._map = map;
    }
    
    public TLongIntMap getMap() {
        return this._map;
    }
    
    public Integer put(final Long key, final Integer value) {
        long k;
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
        final int v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Long, Integer>> entrySet() {
        return new AbstractSet<Entry<Long, Integer>>() {
            public int size() {
                return TLongIntMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TLongIntMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TLongIntMapDecorator.this.containsKey(k) && TLongIntMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Long, Integer>> iterator() {
                return new Iterator<Entry<Long, Integer>>() {
                    private final TLongIntIterator it = TLongIntMapDecorator.this._map.iterator();
                    
                    public Entry<Long, Integer> next() {
                        this.it.advance();
                        final long ik = this.it.key();
                        final Long key = (ik == TLongIntMapDecorator.this._map.getNoEntryKey()) ? null : TLongIntMapDecorator.this.wrapKey(ik);
                        final int iv = this.it.value();
                        final Integer v = (iv == TLongIntMapDecorator.this._map.getNoEntryValue()) ? null : TLongIntMapDecorator.this.wrapValue(iv);
                        return new Entry<Long, Integer>() {
                            private Integer val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Long getKey() {
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
                                return TLongIntMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Long, Integer> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Long key = ((Entry)o).getKey();
                    TLongIntMapDecorator.this._map.remove(TLongIntMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Long, Integer>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TLongIntMapDecorator.this.clear();
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
        return key instanceof Long && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Long, ? extends Integer> map) {
        final Iterator<? extends Entry<? extends Long, ? extends Integer>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Long, ? extends Integer> e = (Entry<? extends Long, ? extends Integer>)it.next();
            this.put((Long)e.getKey(), (Integer)e.getValue());
        }
    }
    
    protected Long wrapKey(final long k) {
        return k;
    }
    
    protected long unwrapKey(final Object key) {
        return (long)key;
    }
    
    protected Integer wrapValue(final int k) {
        return k;
    }
    
    protected int unwrapValue(final Object value) {
        return (int)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TLongIntMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
