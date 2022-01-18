// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.common;

import java.util.ListIterator;
import java.util.Iterator;
import java.util.Collection;
import com.avaje.ebean.bean.BeanCollection;
import java.util.List;

class ModifyList<E> extends ModifyCollection<E> implements List<E>
{
    private final List<E> list;
    
    ModifyList(final BeanCollection<E> owner, final List<E> list) {
        super(owner, list);
        this.list = list;
    }
    
    public void add(final int index, final E element) {
        this.list.add(index, element);
        this.owner.modifyAddition(element);
    }
    
    public boolean addAll(final int index, final Collection<? extends E> co) {
        if (this.list.addAll(index, co)) {
            for (final E o : co) {
                this.owner.modifyAddition(o);
            }
            return true;
        }
        return false;
    }
    
    public E get(final int index) {
        return this.list.get(index);
    }
    
    public int indexOf(final Object o) {
        return this.list.indexOf(o);
    }
    
    public int lastIndexOf(final Object o) {
        return this.list.lastIndexOf(o);
    }
    
    public ListIterator<E> listIterator() {
        return new ModifyListIterator<E>(this.owner, this.list.listIterator());
    }
    
    public ListIterator<E> listIterator(final int index) {
        return new ModifyListIterator<E>(this.owner, this.list.listIterator(index));
    }
    
    public E remove(final int index) {
        final E o = this.list.remove(index);
        this.owner.modifyRemoval(o);
        return o;
    }
    
    public E set(final int index, final E element) {
        final E o = this.list.set(index, element);
        this.owner.modifyAddition(element);
        this.owner.modifyRemoval(o);
        return o;
    }
    
    public List<E> subList(final int fromIndex, final int toIndex) {
        return new ModifyList((BeanCollection<Object>)this.owner, (List<Object>)this.list.subList(fromIndex, toIndex));
    }
}
