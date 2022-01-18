// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.decorator;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import gnu.trove.iterator.TShortShortIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Set;
import gnu.trove.map.TShortShortMap;
import java.io.Externalizable;
import java.util.Map;
import java.util.AbstractMap;

public class TShortShortMapDecorator extends AbstractMap<Short, Short> implements Map<Short, Short>, Externalizable, Cloneable
{
    static final long serialVersionUID = 1L;
    protected TShortShortMap _map;
    
    public TShortShortMapDecorator() {
    }
    
    public TShortShortMapDecorator(final TShortShortMap map) {
        this._map = map;
    }
    
    public TShortShortMap getMap() {
        return this._map;
    }
    
    public Short put(final Short key, final Short value) {
        short k;
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
        final short v = this._map.remove(k);
        if (v == this._map.getNoEntryValue()) {
            return null;
        }
        return this.wrapValue(v);
    }
    
    public Set<Entry<Short, Short>> entrySet() {
        return new AbstractSet<Entry<Short, Short>>() {
            public int size() {
                return TShortShortMapDecorator.this._map.size();
            }
            
            public boolean isEmpty() {
                return TShortShortMapDecorator.this.isEmpty();
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Entry) {
                    final Object k = ((Entry)o).getKey();
                    final Object v = ((Entry)o).getValue();
                    return TShortShortMapDecorator.this.containsKey(k) && TShortShortMapDecorator.this.get(k).equals(v);
                }
                return false;
            }
            
            public Iterator<Entry<Short, Short>> iterator() {
                return new Iterator<Entry<Short, Short>>() {
                    private final TShortShortIterator it = TShortShortMapDecorator.this._map.iterator();
                    
                    public Entry<Short, Short> next() {
                        this.it.advance();
                        final short ik = this.it.key();
                        final Short key = (ik == TShortShortMapDecorator.this._map.getNoEntryKey()) ? null : TShortShortMapDecorator.this.wrapKey(ik);
                        final short iv = this.it.value();
                        final Short v = (iv == TShortShortMapDecorator.this._map.getNoEntryValue()) ? null : TShortShortMapDecorator.this.wrapValue(iv);
                        return new Entry<Short, Short>() {
                            private Short val = v;
                            
                            public boolean equals(final Object o) {
                                return o instanceof Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(this.val);
                            }
                            
                            public Short getKey() {
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
                                return TShortShortMapDecorator.this.put(key, value);
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
            
            public boolean add(final Entry<Short, Short> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(final Object o) {
                boolean modified = false;
                if (this.contains(o)) {
                    final Short key = ((Entry)o).getKey();
                    TShortShortMapDecorator.this._map.remove(TShortShortMapDecorator.this.unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(final Collection<? extends Entry<Short, Short>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TShortShortMapDecorator.this.clear();
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
        return key instanceof Short && this._map.containsKey(this.unwrapKey(key));
    }
    
    public int size() {
        return this._map.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public void putAll(final Map<? extends Short, ? extends Short> map) {
        final Iterator<? extends Entry<? extends Short, ? extends Short>> it = map.entrySet().iterator();
        int i = map.size();
        while (i-- > 0) {
            final Entry<? extends Short, ? extends Short> e = (Entry<? extends Short, ? extends Short>)it.next();
            this.put((Short)e.getKey(), (Short)e.getValue());
        }
    }
    
    protected Short wrapKey(final short k) {
        return k;
    }
    
    protected short unwrapKey(final Object key) {
        return (short)key;
    }
    
    protected Short wrapValue(final short k) {
        return k;
    }
    
    protected short unwrapValue(final Object value) {
        return (short)value;
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        this._map = (TShortShortMap)in.readObject();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeObject(this._map);
    }
}
