// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.NoSuchElementException;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.Beta;

@Beta
@GwtCompatible
public abstract class AbstractLinkedIterator<T> extends UnmodifiableIterator<T>
{
    private T nextOrNull;
    
    protected AbstractLinkedIterator(@Nullable final T firstOrNull) {
        this.nextOrNull = firstOrNull;
    }
    
    protected abstract T computeNext(final T p0);
    
    public final boolean hasNext() {
        return this.nextOrNull != null;
    }
    
    public final T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        try {
            return this.nextOrNull;
        }
        finally {
            this.nextOrNull = this.computeNext(this.nextOrNull);
        }
    }
}
