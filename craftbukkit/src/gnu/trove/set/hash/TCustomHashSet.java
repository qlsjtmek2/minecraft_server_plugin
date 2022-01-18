// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.set.hash;

import gnu.trove.impl.HashFunctions;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.Iterator;
import gnu.trove.impl.hash.TObjectHash;
import gnu.trove.iterator.hash.TObjectHashIterator;
import java.lang.reflect.Array;
import gnu.trove.procedure.array.ToObjectArrayProceedure;
import java.util.Arrays;
import gnu.trove.procedure.TObjectProcedure;
import java.util.Collection;
import gnu.trove.strategy.HashingStrategy;
import java.io.Externalizable;
import java.util.Set;
import gnu.trove.impl.hash.TCustomObjectHash;

public class TCustomHashSet<E> extends TCustomObjectHash<E> implements Set<E>, Iterable<E>, Externalizable
{
    static final long serialVersionUID = 1L;
    
    public TCustomHashSet() {
    }
    
    public TCustomHashSet(final HashingStrategy<? super E> strategy) {
        super(strategy);
    }
    
    public TCustomHashSet(final HashingStrategy<? super E> strategy, final int initialCapacity) {
        super(strategy, initialCapacity);
    }
    
    public TCustomHashSet(final HashingStrategy<? super E> strategy, final int initialCapacity, final float loadFactor) {
        super(strategy, initialCapacity, loadFactor);
    }
    
    public TCustomHashSet(final HashingStrategy<? super E> strategy, final Collection<? extends E> collection) {
        this(strategy, collection.size());
        this.addAll(collection);
    }
    
    public boolean add(final E obj) {
        final int index = this.insertKey(obj);
        if (index < 0) {
            return false;
        }
        this.postInsertHook(this.consumeFreeSlot);
        return true;
    }
    
    public boolean equals(final Object other) {
        if (!(other instanceof Set)) {
            return false;
        }
        final Set that = (Set)other;
        return that.size() == this.size() && this.containsAll(that);
    }
    
    public int hashCode() {
        final HashProcedure p = new HashProcedure();
        this.forEach(p);
        return p.getHashCode();
    }
    
    protected void rehash(final int newCapacity) {
        final int oldCapacity = this._set.length;
        final int oldSize = this.size();
        final Object[] oldSet = this._set;
        Arrays.fill(this._set = new Object[newCapacity], TCustomHashSet.FREE);
        int i = oldCapacity;
        while (i-- > 0) {
            final E o = (E)oldSet[i];
            if (o != TCustomHashSet.FREE && o != TCustomHashSet.REMOVED) {
                final int index = this.insertKey(o);
                if (index >= 0) {
                    continue;
                }
                this.throwObjectContractViolation(this._set[-index - 1], o, this.size(), oldSize, oldSet);
            }
        }
    }
    
    public Object[] toArray() {
        final Object[] result = new Object[this.size()];
        this.forEach(new ToObjectArrayProceedure<Object>((Object[])result));
        return result;
    }
    
    public <T> T[] toArray(T[] a) {
        final int size = this.size();
        if (a.length < size) {
            a = (T[])Array.newInstance(a.getClass().getComponentType(), size);
        }
        this.forEach(new ToObjectArrayProceedure<Object>((Object[])a));
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }
    
    public void clear() {
        super.clear();
        Arrays.fill(this._set, 0, this._set.length, TCustomHashSet.FREE);
    }
    
    public boolean remove(final Object obj) {
        final int index = this.index(obj);
        if (index >= 0) {
            this.removeAt(index);
            return true;
        }
        return false;
    }
    
    public TObjectHashIterator<E> iterator() {
        return new TObjectHashIterator<E>(this);
    }
    
    public boolean containsAll(final Collection<?> collection) {
        final Iterator i = collection.iterator();
        while (i.hasNext()) {
            if (!this.contains(i.next())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean addAll(final Collection<? extends E> collection) {
        boolean changed = false;
        int size = collection.size();
        this.ensureCapacity(size);
        final Iterator<? extends E> it = collection.iterator();
        while (size-- > 0) {
            if (this.add(it.next())) {
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean removeAll(final Collection<?> collection) {
        boolean changed = false;
        int size = collection.size();
        final Iterator it = collection.iterator();
        while (size-- > 0) {
            if (this.remove(it.next())) {
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean retainAll(final Collection<?> collection) {
        boolean changed = false;
        int size = this.size();
        final Iterator<E> it = this.iterator();
        while (size-- > 0) {
            if (!collection.contains(it.next())) {
                it.remove();
                changed = true;
            }
        }
        return changed;
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.forEach(new TObjectProcedure<E>() {
            private boolean first = true;
            
            public boolean execute(final Object value) {
                if (this.first) {
                    this.first = false;
                }
                else {
                    buf.append(", ");
                }
                buf.append(value);
                return true;
            }
        });
        buf.append("}");
        return buf.toString();
    }
    
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(1);
        super.writeExternal(out);
        out.writeInt(this._size);
        int i = this._set.length;
        while (i-- > 0) {
            if (this._set[i] != TCustomHashSet.REMOVED && this._set[i] != TCustomHashSet.FREE) {
                out.writeObject(this._set[i]);
            }
        }
    }
    
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        final byte version = in.readByte();
        if (version != 0) {
            super.readExternal(in);
        }
        int size = in.readInt();
        this.setUp(size);
        while (size-- > 0) {
            final E val = (E)in.readObject();
            this.add(val);
        }
    }
    
    private final class HashProcedure implements TObjectProcedure<E>
    {
        private int h;
        
        private HashProcedure() {
            this.h = 0;
        }
        
        public int getHashCode() {
            return this.h;
        }
        
        public final boolean execute(final E key) {
            this.h += HashFunctions.hash(key);
            return true;
        }
    }
}
