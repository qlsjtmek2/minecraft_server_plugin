// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.ListIterator;
import java.util.Iterator;
import com.avaje.ebean.Page;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class LimitOffsetList<T> implements List<T>
{
    private final LimitOffsetPagingQuery<T> owner;
    private List<T> localCopy;
    
    public LimitOffsetList(final LimitOffsetPagingQuery<T> owner) {
        this.owner = owner;
    }
    
    private void ensureLocalCopy() {
        if (this.localCopy == null) {
            this.localCopy = new ArrayList<T>();
            int pgIndex = 0;
            Page<T> page;
            do {
                page = this.owner.getPage(pgIndex++);
                final List<T> list = page.getList();
                this.localCopy.addAll((Collection<? extends T>)list);
            } while (page.hasNext());
        }
    }
    
    private boolean hasNext(final int position) {
        return this.owner.hasNext(position);
    }
    
    public void clear() {
        this.localCopy = new ArrayList<T>();
    }
    
    public T get(final int index) {
        if (this.localCopy != null) {
            return this.localCopy.get(index);
        }
        return this.owner.get(index);
    }
    
    public boolean isEmpty() {
        if (this.localCopy != null) {
            return this.localCopy.isEmpty();
        }
        return this.owner.getTotalRowCount() == 0;
    }
    
    public int size() {
        if (this.localCopy != null) {
            return this.localCopy.size();
        }
        return this.owner.getTotalRowCount();
    }
    
    public Iterator<T> iterator() {
        if (this.localCopy != null) {
            return this.localCopy.iterator();
        }
        return new ListItr(this, 0);
    }
    
    public ListIterator<T> listIterator() {
        if (this.localCopy != null) {
            return this.localCopy.listIterator();
        }
        return new ListItr(this, 0);
    }
    
    public ListIterator<T> listIterator(final int index) {
        if (this.localCopy != null) {
            return this.localCopy.listIterator(index);
        }
        return new ListItr(this, index);
    }
    
    public List<T> subList(final int fromIndex, final int toIndex) {
        if (this.localCopy != null) {
            return this.localCopy.subList(fromIndex, toIndex);
        }
        throw new RuntimeException("Not implemented at this point");
    }
    
    public int lastIndexOf(final Object o) {
        this.ensureLocalCopy();
        return this.localCopy.lastIndexOf(o);
    }
    
    public void add(final int index, final T element) {
        this.ensureLocalCopy();
        this.localCopy.add(index, element);
    }
    
    public boolean add(final T o) {
        this.ensureLocalCopy();
        return this.localCopy.add(o);
    }
    
    public boolean addAll(final Collection<? extends T> c) {
        this.ensureLocalCopy();
        return this.localCopy.addAll(c);
    }
    
    public boolean addAll(final int index, final Collection<? extends T> c) {
        this.ensureLocalCopy();
        return this.localCopy.addAll(index, c);
    }
    
    public boolean contains(final Object o) {
        this.ensureLocalCopy();
        return this.localCopy.contains(o);
    }
    
    public boolean containsAll(final Collection<?> c) {
        this.ensureLocalCopy();
        return this.localCopy.containsAll(c);
    }
    
    public int indexOf(final Object o) {
        this.ensureLocalCopy();
        return this.localCopy.indexOf(o);
    }
    
    public T remove(final int index) {
        this.ensureLocalCopy();
        return this.localCopy.remove(index);
    }
    
    public boolean remove(final Object o) {
        this.ensureLocalCopy();
        return this.localCopy.remove(o);
    }
    
    public boolean removeAll(final Collection<?> c) {
        this.ensureLocalCopy();
        return this.localCopy.removeAll(c);
    }
    
    public boolean retainAll(final Collection<?> c) {
        this.ensureLocalCopy();
        return this.localCopy.retainAll(c);
    }
    
    public T set(final int index, final T element) {
        this.ensureLocalCopy();
        return this.localCopy.set(index, element);
    }
    
    public Object[] toArray() {
        this.ensureLocalCopy();
        return this.localCopy.toArray();
    }
    
    public <K> K[] toArray(final K[] a) {
        this.ensureLocalCopy();
        return this.localCopy.toArray(a);
    }
    
    private class ListItr implements ListIterator<T>
    {
        private LimitOffsetList<T> ownerList;
        private int position;
        
        ListItr(final LimitOffsetList<T> ownerList, final int position) {
            this.ownerList = ownerList;
            this.position = position;
        }
        
        public void add(final T o) {
            this.ownerList.add(this.position++, o);
        }
        
        public boolean hasNext() {
            return this.ownerList.hasNext(this.position);
        }
        
        public boolean hasPrevious() {
            return this.position > 0;
        }
        
        public T next() {
            return this.ownerList.get(this.position++);
        }
        
        public int nextIndex() {
            return this.position;
        }
        
        public T previous() {
            final LimitOffsetList this$0 = LimitOffsetList.this;
            final int n = this.position - 1;
            this.position = n;
            return this$0.get(n);
        }
        
        public int previousIndex() {
            return this.position - 1;
        }
        
        public void remove() {
            throw new RuntimeException("Not supported yet");
        }
        
        public void set(final T o) {
            throw new RuntimeException("Not supported yet");
        }
    }
}
