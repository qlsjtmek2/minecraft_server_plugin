// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TIntIntIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TIntIntMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TIntIntMapDecorator extends AbstractMap<Integer, Integer> implements Map<Integer, Integer>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TIntIntMap _map;
    
    public TIntIntMapDecorator() {
    }
    
    public TIntIntMapDecorator(final TIntIntMap map) {
        this._map = map;
    }
    
    public TIntIntMap getMap() {
        return this._map;
    }
    
    public Integer put(final Integer key, final Integer value) {
        int k;
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
        final int v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Integer, Integer>> entrySet() {
        return new AbstractSet<Entry<Integer, Integer>>() {
            public int size() {
                return TIntIntMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TIntIntMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TIntIntMapDecorator.this.containsKey(k) && TIntIntMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Integer, Integer>> iterator() {
                return new Iterator<Entry<Integer, Integer>>() {
                    private final TIntIntIterator it = TIntIntMapDecorator.this._map.iterator();
                    
                    public Entry<Integer, Integer> next() {
                        this.it.advance();
                        final int ik = this.it.key();
                        final Integer key = (ik == TIntIntMapDecorator.this._map.getNoEntryKey()) ? null : TIntIntMapDecorator.this.wrapKey(ik);
                        final int iv = this.it.value();
                        final Integer v = (iv == TIntIntMapDecorator.this._map.getNoEntryValue()) ? null : TIntIntMapDecorator.this.wrapValue(iv);
                        return new Entry<Integer, Integer>() {
                            private Integer val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Integer getKey() {
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
                                return TIntIntMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Integer, Integer> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Integer key = ((Entry)o).getKey();
                    TIntIntMapDecorator.this._map.remove(TIntIntMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Integer, Integer>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TIntIntMapDecorator.this.clear();
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
        return key instanceof Integer && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Integer, ? extends Integer> map) {
        final Iterator<? extends Entry<? extends Integer, ? extends Integer>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Integer, ? extends Integer> e = (Entry<? extends Integer, ? extends Integer>)it.next();
            this.put((Integer)e.getKey(), (Integer)e.getValue());
        }
    }
    
    protected Integer wrapKey(final int k) {
        return k;
    }
    
    protected int unwrapKey(final Object key) {
        return (int)key;
    }
    
    protected Integer wrapValue(final int k) {
        return k;
    }
    
    protected int unwrapValue(final Object value) {
        return (int)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TIntIntMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
