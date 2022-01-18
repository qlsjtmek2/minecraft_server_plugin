// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
final class BstModificationResult<N extends BstNode<?, N>>
{
    @Nullable
    private final N originalTarget;
    @Nullable
    private final N changedTarget;
    private final ModificationType type;
    
    static <N extends BstNode<?, N>> BstModificationResult<N> identity(@Nullable final N target) {
        return new BstModificationResult<N>(target, target, ModificationType.IDENTITY);
    }
    
    static <N extends BstNode<?, N>> BstModificationResult<N> rebuildingChange(@Nullable final N originalTarget, @Nullable final N changedTarget) {
        return new BstModificationResult<N>(originalTarget, changedTarget, ModificationType.REBUILDING_CHANGE);
    }
    
    static <N extends BstNode<?, N>> BstModificationResult<N> rebalancingChange(@Nullable final N originalTarget, @Nullable final N changedTarget) {
        return new BstModificationResult<N>(originalTarget, changedTarget, ModificationType.REBALANCING_CHANGE);
    }
    
    private BstModificationResult(@Nullable final N originalTarget, @Nullable final N changedTarget, final ModificationType type) {
        this.originalTarget = originalTarget;
        this.changedTarget = changedTarget;
        this.type = Preconditions.checkNotNull(type);
    }
    
    @Nullable
    N getOriginalTarget() {
        return this.originalTarget;
    }
    
    @Nullable
    N getChangedTarget() {
        return this.changedTarget;
    }
    
    ModificationType getType() {
        return this.type;
    }
    
    enum ModificationType
    {
        IDENTITY, 
        REBUILDING_CHANGE, 
        REBALANCING_CHANGE;
    }
}
