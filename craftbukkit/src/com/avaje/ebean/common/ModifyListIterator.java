// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.common;

import com.avaje.ebean.bean.BeanCollection;
import java.util.ListIterator;

class ModifyListIterator<E> implements ListIterator<E>
{
    private final BeanCollection<E> owner;
    private final ListIterator<E> it;
    private E last;
    
    ModifyListIterator(final BeanCollection<E> owner, final ListIterator<E> it) {
        this.owner = owner;
        this.it = it;
    }
    
    public void add(final E bean) {
        this.owner.modifyAddition(bean);
        this.last = null;
        this.it.add(bean);
    }
    
    public boolean hasNext() {
        return this.it.hasNext();
    }
    
    public boolean hasPrevious() {
        return this.it.hasPrevious();
    }
    
    public E next() {
        return this.last = this.it.next();
    }
    
    public int nextIndex() {
        return this.it.nextIndex();
    }
    
    public E previous() {
        return this.last = this.it.previous();
    }
    
    public int previousIndex() {
        return this.it.previousIndex();
    }
    
    public void remove() {
        this.owner.modifyRemoval(this.last);
        this.last = null;
        this.it.remove();
    }
    
    public void set(final E o) {
        if (this.last != null) {
            this.owner.modifyRemoval(this.last);
            this.owner.modifyAddition(o);
        }
        this.it.set(o);
    }
}
