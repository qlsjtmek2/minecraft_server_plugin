// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;

@GwtCompatible
final class Count implements Serializable
{
    private int value;
    
    Count() {
        this(0);
    }
    
    Count(final int value) {
        this.value = value;
    }
    
    public int get() {
        return this.value;
    }
    
    public int getAndAdd(final int delta) {
        final int result = this.value;
        this.value = result + delta;
        return result;
    }
    
    public int addAndGet(final int delta) {
        return this.value += delta;
    }
    
    public void set(final int newValue) {
        this.value = newValue;
    }
    
    public int getAndSet(final int newValue) {
        final int result = this.value;
        this.value = newValue;
        return result;
    }
    
    public int hashCode() {
        return this.value;
    }
    
    public boolean equals(@Nullable final Object obj) {
        return obj instanceof Count && ((Count)obj).value == this.value;
    }
    
    public String toString() {
        return Integer.toString(this.value);
    }
}
