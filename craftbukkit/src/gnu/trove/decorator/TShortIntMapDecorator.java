// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TShortIntIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TShortIntMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TShortIntMapDecorator extends AbstractMap<Short, Integer> implements Map<Short, Integer>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TShortIntMap _map;
    
    public TShortIntMapDecorator() {
    }
    
    public TShortIntMapDecorator(final TShortIntMap map) {
        this._map = map;
    }
    
    public TShortIntMap getMap() {
        return this._map;
    }
    
    public Integer put(final Short key, final Integer value) {
        short k;
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
        final int v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Short, Integer>> entrySet() {
        return new AbstractSet<Entry<Short, Integer>>() {
            public int size() {
                return TShortIntMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TShortIntMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TShortIntMapDecorator.this.containsKey(k) && TShortIntMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Short, Integer>> iterator() {
                return new Iterator<Entry<Short, Integer>>() {
                    private final TShortIntIterator it = TShortIntMapDecorator.this._map.iterator();
                    
                    public Entry<Short, Integer> next() {
                        this.it.advance();
                        final short ik = this.it.key();
                        final Short key = (ik == TShortIntMapDecorator.this._map.getNoEntryKey()) ? null : TShortIntMapDecorator.this.wrapKey(ik);
                        final int iv = this.it.value();
                        final Integer v = (iv == TShortIntMapDecorator.this._map.getNoEntryValue()) ? null : TShortIntMapDecorator.this.wrapValue(iv);
                        return new Entry<Short, Integer>() {
                            private Integer val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Short getKey() {
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
                                return TShortIntMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Short, Integer> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Short key = ((Entry)o).getKey();
                    TShortIntMapDecorator.this._map.remove(TShortIntMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Short, Integer>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TShortIntMapDecorator.this.clear();
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
        return key instanceof Short && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Short, ? extends Integer> map) {
        final Iterator<? extends Entry<? extends Short, ? extends Integer>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Short, ? extends Integer> e = (Entry<? extends Short, ? extends Integer>)it.next();
            this.put((Short)e.getKey(), (Integer)e.getValue());
        }
    }
    
    protected Short wrapKey(final short k) {
        return k;
    }
    
    protected short unwrapKey(final Object key) {
        return (short)key;
    }
    
    protected Integer wrapValue(final int k) {
        return k;
    }
    
    protected int unwrapValue(final Object value) {
        return (int)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TShortIntMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
