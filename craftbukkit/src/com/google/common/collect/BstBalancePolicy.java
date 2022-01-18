// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
interface BstBalancePolicy<N extends BstNode<?, N>>
{
    N balance(final BstNodeFactory<N> p0, final N p1, @Nullable final N p2, @Nullable final N p3);
    
    @Nullable
    N combine(final BstNodeFactory<N> p0, @Nullable final N p1, @Nullable final N p2);
}
