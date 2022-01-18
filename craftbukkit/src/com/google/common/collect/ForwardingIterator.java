// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Iterator;

@GwtCompatible
public abstract class ForwardingIterator<T> extends ForwardingObject implements Iterator<T>
{
    protected abstract Iterator<T> delegate();
    
    public boolean hasNext() {
        return this.delegate().hasNext();
    }
    
    public T next() {
        return this.delegate().next();
    }
    
    public void remove() {
        this.delegate().remove();
    }
}
