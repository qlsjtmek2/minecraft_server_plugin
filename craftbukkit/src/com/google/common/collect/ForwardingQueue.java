// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collection;
import java.util.NoSuchElementException;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.util.Queue;

@GwtCompatible
public abstract class ForwardingQueue<E> extends ForwardingCollection<E> implements Queue<E>
{
    protected abstract Queue<E> delegate();
    
    public boolean offer(final E o) {
        return this.delegate().offer(o);
    }
    
    public E poll() {
        return this.delegate().poll();
    }
    
    public E remove() {
        return this.delegate().remove();
    }
    
    public E peek() {
        return this.delegate().peek();
    }
    
    public E element() {
        return this.delegate().element();
    }
    
    @Beta
    protected boolean standardOffer(final E e) {
        try {
            return this.add(e);
        }
        catch (IllegalStateException caught) {
            return false;
        }
    }
    
    @Beta
    protected E standardPeek() {
        try {
            return this.element();
        }
        catch (NoSuchElementException caught) {
            return null;
        }
    }
    
    @Beta
    protected E standardPoll() {
        try {
            return this.remove();
        }
        catch (NoSuchElementException caught) {
            return null;
        }
    }
}
