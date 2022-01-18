// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.util;

import java.util.EmptyStackException;
import java.util.ArrayList;

public class ArrayStack<E>
{
    private final ArrayList<E> list;
    
    public ArrayStack(final int size) {
        this.list = new ArrayList<E>(size);
    }
    
    public ArrayStack() {
        this.list = new ArrayList<E>();
    }
    
    public E push(final E item) {
        this.list.add(item);
        return item;
    }
    
    public E pop() {
        final int len = this.list.size();
        final E obj = this.peek();
        this.list.remove(len - 1);
        return obj;
    }
    
    protected E peekZero(final boolean retNull) {
        final int len = this.list.size();
        if (len != 0) {
            return this.list.get(len - 1);
        }
        if (retNull) {
            return null;
        }
        throw new EmptyStackException();
    }
    
    public E peek() {
        return this.peekZero(false);
    }
    
    public E peekWithNull() {
        return this.peekZero(true);
    }
    
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    public int size() {
        return this.list.size();
    }
}
