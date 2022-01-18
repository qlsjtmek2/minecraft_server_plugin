// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Collection;
import java.util.EnumSet;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
final class ImmutableEnumSet<E extends Enum<E>> extends ImmutableSet<E>
{
    private final transient EnumSet<E> delegate;
    private transient int hashCode;
    
    ImmutableEnumSet(final EnumSet<E> delegate) {
        this.delegate = delegate;
    }
    
    boolean isPartialView() {
        return false;
    }
    
    public UnmodifiableIterator<E> iterator() {
        return Iterators.unmodifiableIterator(this.delegate.iterator());
    }
    
    public int size() {
        return this.delegate.size();
    }
    
    public boolean contains(final Object object) {
        return this.delegate.contains(object);
    }
    
    public boolean containsAll(final Collection<?> collection) {
        return this.delegate.containsAll(collection);
    }
    
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }
    
    public Object[] toArray() {
        return this.delegate.toArray();
    }
    
    public <T> T[] toArray(final T[] array) {
        return this.delegate.toArray(array);
    }
    
    public boolean equals(final Object object) {
        return object == this || this.delegate.equals(object);
    }
    
    public int hashCode() {
        final int result = this.hashCode;
        return (result == 0) ? (this.hashCode = this.delegate.hashCode()) : result;
    }
    
    public String toString() {
        return this.delegate.toString();
    }
    
    Object writeReplace() {
        return new EnumSerializedForm((EnumSet<Enum>)this.delegate);
    }
    
    private static class EnumSerializedForm<E extends Enum<E>> implements Serializable
    {
        final EnumSet<E> delegate;
        private static final long serialVersionUID = 0L;
        
        EnumSerializedForm(final EnumSet<E> delegate) {
            this.delegate = delegate;
        }
        
        Object readResolve() {
            return new ImmutableEnumSet((EnumSet<Enum>)this.delegate.clone());
        }
    }
}
