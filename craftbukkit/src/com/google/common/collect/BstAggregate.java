// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
interface BstAggregate<N extends BstNode<?, N>>
{
    int treeValue(@Nullable final N p0);
    
    int entryValue(final N p0);
}
