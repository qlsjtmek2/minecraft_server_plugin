// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.ListIterator;
import com.avaje.ebean.bean.BeanCollection;
import java.util.Iterator;
import java.util.Collection;
import java.io.ObjectStreamException;
import com.avaje.ebean.bean.SerializeControl;
import com.avaje.ebean.bean.BeanCollectionLoader;
import java.util.ArrayList;
import com.avaje.ebean.bean.BeanCollectionAdd;
import java.util.List;

public final class BeanList<E> extends AbstractBeanCollection<E> implements List<E>, BeanCollectionAdd
{
    private List<E> list;
    
    public BeanList(final List<E> list) {
        this.list = list;
    }
    
    public BeanList() {
        this((List)new ArrayList());
    }
    
    public BeanList(final BeanCollectionLoader loader, final Object ownerBean, final String propertyName) {
        super(loader, ownerBean, propertyName);
    }
    
    Object readResolve() throws ObjectStreamException {
        if (SerializeControl.isVanillaCollections()) {
            return this.list;
        }
        return this;
    }
    
    Object writeReplace() throws ObjectStreamException {
        if (SerializeControl.isVanillaCollections()) {
            return this.list;
        }
        return this;
    }
    
    public void addBean(final Object bean) {
        this.list.add((E)bean);
    }
    
    public void internalAdd(final Object bean) {
        this.list.add((E)bean);
    }
    
    public boolean checkEmptyLazyLoad() {
        if (this.list == null) {
            this.list = new ArrayList<E>();
            return true;
        }
        return false;
    }
    
    private void initClear() {
        synchronized (this) {
            if (this.list == null) {
                if (this.modifyListening) {
                    this.lazyLoadCollection(true);
                }
                else {
                    this.list = new ArrayList<E>();
                }
            }
            this.touched();
        }
    }
    
    private void init() {
        synchronized (this) {
            if (this.list == null) {
                this.lazyLoadCollection(false);
            }
            this.touched();
        }
    }
    
    public void setActualList(final List<?> list) {
        this.list = (List<E>)list;
    }
    
    public List<E> getActualList() {
        return this.list;
    }
    
    public Collection<E> getActualDetails() {
        return this.list;
    }
    
    public Object getActualCollection() {
        return this.list;
    }
    
    public boolean isPopulated() {
        return this.list != null;
    }
    
    public boolean isReference() {
        return this.list == null;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("BeanList ");
        if (this.isSharedInstance()) {
            sb.append("sharedInstance ");
        }
        else if (this.isReadOnly()) {
            sb.append("readOnly ");
        }
        if (this.list == null) {
            sb.append("deferred ");
        }
        else {
            sb.append("size[").append(this.list.size()).append("] ");
            sb.append("hasMoreRows[").append(this.hasMoreRows).append("] ");
            sb.append("list").append(this.list).append("");
        }
        return sb.toString();
    }
    
    public boolean equals(final Object obj) {
        this.init();
        return this.list.equals(obj);
    }
    
    public int hashCode() {
        this.init();
        return this.list.hashCode();
    }
    
    public void add(final int index, final E element) {
        this.checkReadOnly();
        this.init();
        if (this.modifyAddListening) {
            this.modifyAddition(element);
        }
        this.list.add(index, element);
    }
    
    public boolean add(final E o) {
        this.checkReadOnly();
        this.init();
        if (!this.modifyAddListening) {
            return this.list.add(o);
        }
        if (this.list.add(o)) {
            this.modifyAddition(o);
            return true;
        }
        return false;
    }
    
    public boolean addAll(final Collection<? extends E> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyAddListening) {
            this.getModifyHolder().modifyAdditionAll(c);
        }
        return this.list.addAll(c);
    }
    
    public boolean addAll(final int index, final Collection<? extends E> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyAddListening) {
            this.getModifyHolder().modifyAdditionAll(c);
        }
        return this.list.addAll(index, c);
    }
    
    public void clear() {
        this.checkReadOnly();
        this.initClear();
        if (this.modifyRemoveListening) {
            for (int i = 0; i < this.list.size(); ++i) {
                this.getModifyHolder().modifyRemoval(this.list.get(i));
            }
        }
        this.list.clear();
    }
    
    public boolean contains(final Object o) {
        this.init();
        return this.list.contains(o);
    }
    
    public boolean containsAll(final Collection<?> c) {
        this.init();
        return this.list.containsAll(c);
    }
    
    public E get(final int index) {
        this.init();
        return this.list.get(index);
    }
    
    public int indexOf(final Object o) {
        this.init();
        return this.list.indexOf(o);
    }
    
    public boolean isEmpty() {
        this.init();
        return this.list.isEmpty();
    }
    
    public Iterator<E> iterator() {
        this.init();
        if (this.isReadOnly()) {
            return new ReadOnlyListIterator<E>(this.list.listIterator());
        }
        if (this.modifyListening) {
            final Iterator<E> it = this.list.iterator();
            return new ModifyIterator<E>(this, it);
        }
        return this.list.iterator();
    }
    
    public int lastIndexOf(final Object o) {
        this.init();
        return this.list.lastIndexOf(o);
    }
    
    public ListIterator<E> listIterator() {
        this.init();
        if (this.isReadOnly()) {
            return new ReadOnlyListIterator<E>(this.list.listIterator());
        }
        if (this.modifyListening) {
            final ListIterator<E> it = this.list.listIterator();
            return new ModifyListIterator<E>(this, it);
        }
        return this.list.listIterator();
    }
    
    public ListIterator<E> listIterator(final int index) {
        this.init();
        if (this.isReadOnly()) {
            return new ReadOnlyListIterator<E>(this.list.listIterator(index));
        }
        if (this.modifyListening) {
            final ListIterator<E> it = this.list.listIterator(index);
            return new ModifyListIterator<E>(this, it);
        }
        return this.list.listIterator(index);
    }
    
    public E remove(final int index) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            final E o = this.list.remove(index);
            this.modifyRemoval(o);
            return o;
        }
        return this.list.remove(index);
    }
    
    public boolean remove(final Object o) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            final boolean isRemove = this.list.remove(o);
            if (isRemove) {
                this.modifyRemoval(o);
            }
            return isRemove;
        }
        return this.list.remove(o);
    }
    
    public boolean removeAll(final Collection<?> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            boolean changed = false;
            for (final Object o : c) {
                if (this.list.remove(o)) {
                    this.modifyRemoval(o);
                    changed = true;
                }
            }
            return changed;
        }
        return this.list.removeAll(c);
    }
    
    public boolean retainAll(final Collection<?> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            boolean changed = false;
            final Iterator<E> it = this.list.iterator();
            while (it.hasNext()) {
                final Object o = it.next();
                if (!c.contains(o)) {
                    it.remove();
                    this.modifyRemoval(o);
                    changed = true;
                }
            }
            return changed;
        }
        return this.list.retainAll(c);
    }
    
    public E set(final int index, final E element) {
        this.checkReadOnly();
        this.init();
        if (this.modifyListening) {
            final E o = this.list.set(index, element);
            this.modifyAddition(element);
            this.modifyRemoval(o);
            return o;
        }
        return this.list.set(index, element);
    }
    
    public int size() {
        this.init();
        return this.list.size();
    }
    
    public List<E> subList(final int fromIndex, final int toIndex) {
        this.init();
        if (this.isReadOnly()) {
            return Collections.unmodifiableList((List<? extends E>)this.list.subList(fromIndex, toIndex));
        }
        if (this.modifyListening) {
            return new ModifyList<E>(this, this.list.subList(fromIndex, toIndex));
        }
        return this.list.subList(fromIndex, toIndex);
    }
    
    public Object[] toArray() {
        this.init();
        return this.list.toArray();
    }
    
    public <T> T[] toArray(final T[] a) {
        this.init();
        return this.list.toArray(a);
    }
    
    private static class ReadOnlyListIterator<E> implements ListIterator<E>, Serializable
    {
        private static final long serialVersionUID = 3097271091406323699L;
        private final ListIterator<E> i;
        
        ReadOnlyListIterator(final ListIterator<E> i) {
            this.i = i;
        }
        
        public void add(final E o) {
            throw new IllegalStateException("This collection is in ReadOnly mode");
        }
        
        public void remove() {
            throw new IllegalStateException("This collection is in ReadOnly mode");
        }
        
        public void set(final E o) {
            throw new IllegalStateException("This collection is in ReadOnly mode");
        }
        
        public boolean hasNext() {
            return this.i.hasNext();
        }
        
        public boolean hasPrevious() {
            return this.i.hasPrevious();
        }
        
        public E next() {
            return this.i.next();
        }
        
        public int nextIndex() {
            return this.i.nextIndex();
        }
        
        public E previous() {
            return this.i.previous();
        }
        
        public int previousIndex() {
            return this.i.previousIndex();
        }
    }
}
