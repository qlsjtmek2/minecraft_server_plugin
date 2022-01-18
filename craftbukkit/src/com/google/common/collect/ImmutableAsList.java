// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.Serializable;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
final class ImmutableAsList<E> extends RegularImmutableList<E>
{
    private final transient ImmutableCollection<E> collection;
    
    ImmutableAsList(final Object[] array, final ImmutableCollection<E> collection) {
        super(array, 0, array.length);
        this.collection = collection;
    }
    
    public boolean contains(final Object target) {
        return this.collection.contains(target);
    }
    
    private void readObject(final ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }
    
    Object writeReplace() {
        return new SerializedForm(this.collection);
    }
    
    static class SerializedForm implements Serializable
    {
        final ImmutableCollection<?> collection;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final ImmutableCollection<?> collection) {
            this.collection = collection;
        }
        
        Object readResolve() {
            return this.collection.asList();
        }
    }
}
