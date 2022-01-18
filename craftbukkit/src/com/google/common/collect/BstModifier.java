// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
interface BstModifier<K, N extends BstNode<K, N>>
{
    BstModificationResult<N> modify(final K p0, @Nullable final N p1);
}
