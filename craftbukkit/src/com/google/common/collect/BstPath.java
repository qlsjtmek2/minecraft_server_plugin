// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class BstPath<N extends BstNode<?, N>, P extends BstPath<N, P>>
{
    private final N tip;
    @Nullable
    private final P prefix;
    
    BstPath(final N tip, @Nullable final P prefix) {
        this.tip = Preconditions.checkNotNull(tip);
        this.prefix = prefix;
    }
    
    public final N getTip() {
        return this.tip;
    }
    
    public final boolean hasPrefix() {
        return this.prefix != null;
    }
    
    @Nullable
    public final P prefixOrNull() {
        return this.prefix;
    }
    
    public final P getPrefix() {
        Preconditions.checkState(this.hasPrefix());
        return this.prefix;
    }
}
