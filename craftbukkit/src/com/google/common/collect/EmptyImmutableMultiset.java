// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Set;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true)
final class EmptyImmutableMultiset extends ImmutableMultiset<Object>
{
    static final EmptyImmutableMultiset INSTANCE;
    private static final long serialVersionUID = 0L;
    
    public int count(@Nullable final Object element) {
        return 0;
    }
    
    public ImmutableSet<Object> elementSet() {
        return ImmutableSet.of();
    }
    
    public int size() {
        return 0;
    }
    
    UnmodifiableIterator<Multiset.Entry<Object>> entryIterator() {
        return Iterators.emptyIterator();
    }
    
    int distinctElements() {
        return 0;
    }
    
    boolean isPartialView() {
        return false;
    }
    
    ImmutableSet<Multiset.Entry<Object>> createEntrySet() {
        return ImmutableSet.of();
    }
    
    static {
        INSTANCE = new EmptyImmutableMultiset();
    }
}
