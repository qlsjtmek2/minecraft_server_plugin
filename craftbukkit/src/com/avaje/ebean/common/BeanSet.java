// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.common;

import java.io.Serializable;
import com.avaje.ebean.bean.BeanCollection;
import java.util.Iterator;
import java.util.Collection;
import java.io.ObjectStreamException;
import com.avaje.ebean.bean.SerializeControl;
import com.avaje.ebean.bean.BeanCollectionLoader;
import java.util.LinkedHashSet;
import com.avaje.ebean.bean.BeanCollectionAdd;
import java.util.Set;

public final class BeanSet<E> extends AbstractBeanCollection<E> implements Set<E>, BeanCollectionAdd
{
    private Set<E> set;
    
    public BeanSet(final Set<E> set) {
        this.set = set;
    }
    
    public BeanSet() {
        this((Set)new LinkedHashSet());
    }
    
    public BeanSet(final BeanCollectionLoader loader, final Object ownerBean, final String propertyName) {
        super(loader, ownerBean, propertyName);
    }
    
    Object readResolve() throws ObjectStreamException {
        if (SerializeControl.isVanillaCollections()) {
            return this.set;
        }
        return this;
    }
    
    Object writeReplace() throws ObjectStreamException {
        if (SerializeControl.isVanillaCollections()) {
            return this.set;
        }
        return this;
    }
    
    public void addBean(final Object bean) {
        this.set.add((E)bean);
    }
    
    public void internalAdd(final Object bean) {
        this.set.add((E)bean);
    }
    
    public boolean isPopulated() {
        return this.set != null;
    }
    
    public boolean isReference() {
        return this.set == null;
    }
    
    public boolean checkEmptyLazyLoad() {
        if (this.set == null) {
            this.set = new LinkedHashSet<E>();
            return true;
        }
        return false;
    }
    
    private void initClear() {
        synchronized (this) {
            if (this.set == null) {
                if (this.modifyListening) {
                    this.lazyLoadCollection(true);
                }
                else {
                    this.set = new LinkedHashSet<E>();
                }
            }
            this.touched();
        }
    }
    
    private void init() {
        synchronized (this) {
            if (this.set == null) {
                this.lazyLoadCollection(true);
            }
            this.touched();
        }
    }
    
    public void setActualSet(final Set<?> set) {
        this.set = (Set<E>)set;
    }
    
    public Set<E> getActualSet() {
        return this.set;
    }
    
    public Collection<E> getActualDetails() {
        return this.set;
    }
    
    public Object getActualCollection() {
        return this.set;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("BeanSet ");
        if (this.isSharedInstance()) {
            sb.append("sharedInstance ");
        }
        else if (this.isReadOnly()) {
            sb.append("readOnly ");
        }
        if (this.set == null) {
            sb.append("deferred ");
        }
        else {
            sb.append("size[").append(this.set.size()).append("]");
            sb.append(" hasMoreRows[").append(this.hasMoreRows).append("]");
            sb.append(" set").append(this.set);
        }
        return sb.toString();
    }
    
    public boolean equals(final Object obj) {
        this.init();
        return this.set.equals(obj);
    }
    
    public int hashCode() {
        this.init();
        return this.set.hashCode();
    }
    
    public boolean add(final E o) {
        this.checkReadOnly();
        this.init();
        if (!this.modifyAddListening) {
            return this.set.add(o);
        }
        if (this.set.add(o)) {
            this.modifyAddition(o);
            return true;
        }
        return false;
    }
    
    public boolean addAll(final Collection<? extends E> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyAddListening) {
            boolean changed = false;
            for (final E o : c) {
                if (this.set.add(o)) {
                    this.modifyAddition(o);
                    changed = true;
                }
            }
            return changed;
        }
        return this.set.addAll(c);
    }
    
    public void clear() {
        this.checkReadOnly();
        this.initClear();
        if (this.modifyRemoveListening) {
            for (final E e : this.set) {
                this.modifyRemoval(e);
            }
        }
        this.set.clear();
    }
    
    public boolean contains(final Object o) {
        this.init();
        return this.set.contains(o);
    }
    
    public boolean containsAll(final Collection<?> c) {
        this.init();
        return this.set.containsAll(c);
    }
    
    public boolean isEmpty() {
        this.init();
        return this.set.isEmpty();
    }
    
    public Iterator<E> iterator() {
        this.init();
        if (this.isReadOnly()) {
            return new ReadOnlyIterator<E>(this.set.iterator());
        }
        if (this.modifyListening) {
            return new ModifyIterator<E>(this, this.set.iterator());
        }
        return this.set.iterator();
    }
    
    public boolean remove(final Object o) {
        this.checkReadOnly();
        this.init();
        if (!this.modifyRemoveListening) {
            return this.set.remove(o);
        }
        if (this.set.remove(o)) {
            this.modifyRemoval(o);
            return true;
        }
        return false;
    }
    
    public boolean removeAll(final Collection<?> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            boolean changed = false;
            for (final Object o : c) {
                if (this.set.remove(o)) {
                    this.modifyRemoval(o);
                    changed = true;
                }
            }
            return changed;
        }
        return this.set.removeAll(c);
    }
    
    public boolean retainAll(final Collection<?> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            boolean changed = false;
            final Iterator<?> it = this.set.iterator();
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
        return this.set.retainAll(c);
    }
    
    public int size() {
        this.init();
        return this.set.size();
    }
    
    public Object[] toArray() {
        this.init();
        return this.set.toArray();
    }
    
    public <T> T[] toArray(final T[] a) {
        this.init();
        return this.set.toArray(a);
    }
    
    private static class ReadOnlyIterator<E> implements Iterator<E>, Serializable
    {
        private static final long serialVersionUID = 2577697326745352605L;
        private final Iterator<E> it;
        
        ReadOnlyIterator(final Iterator<E> it) {
            this.it = it;
        }
        
        public boolean hasNext() {
            return this.it.hasNext();
        }
        
        public E next() {
            return this.it.next();
        }
        
        public void remove() {
            throw new IllegalStateException("This collection is in ReadOnly mode");
        }
    }
}
