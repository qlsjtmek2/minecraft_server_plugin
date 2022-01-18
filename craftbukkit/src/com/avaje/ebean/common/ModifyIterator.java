// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.common;

import com.avaje.ebean.bean.BeanCollection;
import java.util.Iterator;

class ModifyIterator<E> implements Iterator<E>
{
    private final BeanCollection<E> owner;
    private final Iterator<E> it;
    private E last;
    
    ModifyIterator(final BeanCollection<E> owner, final Iterator<E> it) {
        this.owner = owner;
        this.it = it;
    }
    
    public boolean hasNext() {
        return this.it.hasNext();
    }
    
    public E next() {
        return this.last = this.it.next();
    }
    
    public void remove() {
        this.owner.modifyRemoval(this.last);
        this.it.remove();
    }
}
