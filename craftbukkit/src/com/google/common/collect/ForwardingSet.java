// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collection;
import com.google.common.annotations.Beta;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;
import java.util.Set;

@GwtCompatible
public abstract class ForwardingSet<E> extends ForwardingCollection<E> implements Set<E>
{
    protected abstract Set<E> delegate();
    
    public boolean equals(@Nullable final Object object) {
        return object == this || this.delegate().equals(object);
    }
    
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    @Beta
    protected boolean standardEquals(@Nullable final Object object) {
        return Sets.equalsImpl(this, object);
    }
    
    @Beta
    protected int standardHashCode() {
        return Sets.hashCodeImpl(this);
    }
}
