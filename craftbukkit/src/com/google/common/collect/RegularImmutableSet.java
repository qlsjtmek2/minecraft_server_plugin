// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
final class RegularImmutableSet<E> extends ArrayImmutableSet<E>
{
    @VisibleForTesting
    final transient Object[] table;
    private final transient int mask;
    private final transient int hashCode;
    
    RegularImmutableSet(final Object[] elements, final int hashCode, final Object[] table, final int mask) {
        super(elements);
        this.table = table;
        this.mask = mask;
        this.hashCode = hashCode;
    }
    
    public boolean contains(final Object target) {
        if (target == null) {
            return false;
        }
        int i = Hashing.smear(target.hashCode());
        while (true) {
            final Object candidate = this.table[i & this.mask];
            if (candidate == null) {
                return false;
            }
            if (candidate.equals(target)) {
                return true;
            }
            ++i;
        }
    }
    
    public int hashCode() {
        return this.hashCode;
    }
    
    boolean isHashCodeFast() {
        return true;
    }
}
