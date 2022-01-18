// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class BstNodeFactory<N extends BstNode<?, N>>
{
    public abstract N createNode(final N p0, @Nullable final N p1, @Nullable final N p2);
    
    public final N createLeaf(final N source) {
        return this.createNode(source, null, null);
    }
}
