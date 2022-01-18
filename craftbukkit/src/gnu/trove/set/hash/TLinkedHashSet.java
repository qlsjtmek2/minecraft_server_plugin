// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.set.hash;

import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.impl.hash.TObjectHash;
import gnu.trove.iterator.hash.TObjectHashIterator;
import java.io.IOException;
import gnu.trove.procedure.TIntProcedure;
import java.io.ObjectOutput;
import gnu.trove.iterator.TIntIterator;
import java.util.Arrays;
import gnu.trove.list.linked.TIntLinkedList;
import java.util.Iterator;
import gnu.trove.list.array.TIntArrayList;
import java.util.Collection;
import gnu.trove.list.TIntList;

public class TLinkedHashSet<E> extends THashSet<E>
{
    TIntList order;
    
    public TLinkedHashSet() {
    }
    
    public TLinkedHashSet(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public TLinkedHashSet(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public TLinkedHashSet(final Collection<? extends E> es) {
        super(es);
    }
    
    public int setUp(final int initialCapacity) {
        this.order = new TIntArrayList(initialCapacity) {
            public void ensureCapacity(final int capacity) {
                if (capacity > this._data.length) {
                    final int newCap = Math.max(TLinkedHashSet.this._set.length, capacity);
                    final int[] tmp = new int[newCap];
                    System.arraycopy(this._data, 0, tmp, 0, this._data.length);
                    this._data = tmp;
                }
            }
        };
        return super.setUp(initialCapacity);
    }
    
    public void clear() {
        super.clear();
        this.order.clear();
    }
    
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        boolean first = true;
        final Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            if (first) {
                first = false;
            }
            else {
                buf.append(", ");
            }
            buf.append(it.next());
        }
        buf.append("}");
        return buf.toString();
    }
    
    public boolean add(final E obj) {
        final int index = this.insertKey(obj);
        if (index < 0) {
            return false;
        }
        if (!this.order.add(index)) {
            throw new IllegalStateException("Order not changed after insert");
        }
        this.postInsertHook(this.consumeFreeSlot);
        return true;
    }
    
    protected void removeAt(final int index) {
        this.order.remove(index);
        super.removeAt(index);
    }
    
    protected void rehash(final int newCapacity) {
        final TIntLinkedList oldOrder = new TIntLinkedList(this.order);
        final int oldSize = this.size();
        final Object[] oldSet = this._set;
        this.order.clear();
        Arrays.fill(this._set = new Object[newCapacity], TLinkedHashSet.FREE);
        for (final int i : oldOrder) {
            final E o = (E)oldSet[i];
            if (o == TLinkedHashSet.FREE || o == TLinkedHashSet.REMOVED) {
                throw new IllegalStateException("Iterating over empty location while rehashing");
            }
            if (o == TLinkedHashSet.FREE || o == TLinkedHashSet.REMOVED) {
                continue;
            }
            final int index = this.insertKey(o);
            if (index < 0) {
                this.throwObjectContractViolation(this._set[-index - 1], o, this.size(), oldSize, oldSet);
            }
            if (!this.order.add(index)) {
                throw new IllegalStateException("Order not changed after insert");
            }
        }
    }
    
    protected void writeEntries(final ObjectOutput out) throws IOException {
        final WriteProcedure writeProcedure = new WriteProcedure(out);
        if (!this.order.forEach(writeProcedure)) {
            throw writeProcedure.getIoException();
        }
    }
    
    public TObjectHashIterator<E> iterator() {
        return new TObjectHashIterator<E>(this) {
            TIntIterator localIterator = TLinkedHashSet.this.order.iterator();
            int lastIndex;
            
            public E next() {
                this.lastIndex = this.localIterator.next();
                return this.objectAtIndex(this.lastIndex);
            }
            
            public boolean hasNext() {
                return this.localIterator.hasNext();
            }
            
            public void remove() {
                this.localIterator.remove();
                try {
                    this._hash.tempDisableAutoCompaction();
                    TLinkedHashSet.this.removeAt(this.lastIndex);
                }
                finally {
                    this._hash.reenableAutoCompaction(false);
                }
            }
        };
    }
    
    public boolean forEach(final TObjectProcedure<? super E> procedure) {
        final ForEachProcedure forEachProcedure = new ForEachProcedure(this._set, procedure);
        return this.order.forEach(forEachProcedure);
    }
    
    class WriteProcedure implements TIntProcedure
    {
        final ObjectOutput output;
        IOException ioException;
        
        WriteProcedure(final ObjectOutput output) {
            this.output = output;
        }
        
        public IOException getIoException() {
            return this.ioException;
        }
        
        public boolean execute(final int value) {
            try {
                this.output.writeObject(TLinkedHashSet.this._set[value]);
            }
            catch (IOException e) {
                this.ioException = e;
                return false;
            }
            return true;
        }
    }
    
    class ForEachProcedure implements TIntProcedure
    {
        boolean changed;
        final Object[] set;
        final TObjectProcedure<? super E> procedure;
        
        public ForEachProcedure(final Object[] set, final TObjectProcedure<? super E> procedure) {
            this.changed = false;
            this.set = set;
            this.procedure = procedure;
        }
        
        public boolean execute(final int value) {
            return this.procedure.execute((Object)this.set[value]);
        }
    }
}
