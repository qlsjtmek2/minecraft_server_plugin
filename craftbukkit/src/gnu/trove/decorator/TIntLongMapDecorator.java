// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TIntLongIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TIntLongMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TIntLongMapDecorator extends AbstractMap<Integer, Long> implements Map<Integer, Long>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TIntLongMap _map;
    
    public TIntLongMapDecorator() {
    }
    
    public TIntLongMapDecorator(final TIntLongMap map) {
        this._map = map;
    }
    
    public TIntLongMap getMap() {
        return this._map;
    }
    
    public Long put(final Integer key, final Long value) {
        int k;
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
        final long v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Integer, Long>> entrySet() {
        return new AbstractSet<Entry<Integer, Long>>() {
            public int size() {
                return TIntLongMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TIntLongMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TIntLongMapDecorator.this.containsKey(k) && TIntLongMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Integer, Long>> iterator() {
                return new Iterator<Entry<Integer, Long>>() {
                    private final TIntLongIterator it = TIntLongMapDecorator.this._map.iterator();
                    
                    public Entry<Integer, Long> next() {
                        this.it.advance();
                        final int ik = this.it.key();
                        final Integer key = (ik == TIntLongMapDecorator.this._map.getNoEntryKey()) ? null : TIntLongMapDecorator.this.wrapKey(ik);
                        final long iv = this.it.value();
                        final Long v = (iv == TIntLongMapDecorator.this._map.getNoEntryValue()) ? null : TIntLongMapDecorator.this.wrapValue(iv);
                        return new Entry<Integer, Long>() {
                            private Long val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Integer getKey() {
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
                                return TIntLongMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Integer, Long> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Integer key = ((Entry)o).getKey();
                    TIntLongMapDecorator.this._map.remove(TIntLongMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Integer, Long>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TIntLongMapDecorator.this.clear();
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
        return key instanceof Integer && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Integer, ? extends Long> map) {
        final Iterator<? extends Entry<? extends Integer, ? extends Long>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Integer, ? extends Long> e = (Entry<? extends Integer, ? extends Long>)it.next();
            this.put((Integer)e.getKey(), (Long)e.getValue());
        }
    }
    
    protected Integer wrapKey(final int k) {
        return k;
    }
    
    protected int unwrapKey(final Object key) {
        return (int)key;
    }
    
    protected Long wrapValue(final long k) {
        return k;
    }
    
    protected long unwrapValue(final Object value) {
        return (long)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TIntLongMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
