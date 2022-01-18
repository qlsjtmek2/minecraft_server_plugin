// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
final class BstMutationResult<K, N extends BstNode<K, N>>
{
    private final K targetKey;
    @Nullable
    private N originalRoot;
    @Nullable
    private N changedRoot;
    private final BstModificationResult<N> modificationResult;
    
    public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> mutationResult(final K targetKey, @Nullable final N originalRoot, @Nullable final N changedRoot, final BstModificationResult<N> modificationResult) {
        return new BstMutationResult<K, N>(targetKey, originalRoot, changedRoot, modificationResult);
    }
    
    private BstMutationResult(final K targetKey, @Nullable final N originalRoot, @Nullable final N changedRoot, final BstModificationResult<N> modificationResult) {
        this.targetKey = Preconditions.checkNotNull(targetKey);
        this.originalRoot = originalRoot;
        this.changedRoot = changedRoot;
        this.modificationResult = Preconditions.checkNotNull(modificationResult);
    }
    
    public K getTargetKey() {
        return this.targetKey;
    }
    
    @Nullable
    public N getOriginalRoot() {
        return this.originalRoot;
    }
    
    @Nullable
    public N getChangedRoot() {
        return this.changedRoot;
    }
    
    @Nullable
    public N getOriginalTarget() {
        return this.modificationResult.getOriginalTarget();
    }
    
    @Nullable
    public N getChangedTarget() {
        return this.modificationResult.getChangedTarget();
    }
    
    BstModificationResult.ModificationType modificationType() {
        return this.modificationResult.getType();
    }
    
    public BstMutationResult<K, N> lift(final N liftOriginalRoot, final BstSide side, final BstNodeFactory<N> nodeFactory, final BstBalancePolicy<N> balancePolicy) {
        assert liftOriginalRoot != null & side != null & nodeFactory != null & balancePolicy != null;
        switch (this.modificationType()) {
            case IDENTITY: {
                this.changedRoot = liftOriginalRoot;
                this.originalRoot = liftOriginalRoot;
                return this;
            }
            case REBUILDING_CHANGE:
            case REBALANCING_CHANGE: {
                this.originalRoot = liftOriginalRoot;
                N resultLeft = ((BstNode<K, N>)liftOriginalRoot).childOrNull(BstSide.LEFT);
                N resultRight = ((BstNode<K, N>)liftOriginalRoot).childOrNull(BstSide.RIGHT);
                switch (side) {
                    case LEFT: {
                        resultLeft = this.changedRoot;
                        break;
                    }
                    case RIGHT: {
                        resultRight = this.changedRoot;
                        break;
                    }
                    default: {
                        throw new AssertionError();
                    }
                }
                if (this.modificationType() == BstModificationResult.ModificationType.REBUILDING_CHANGE) {
                    this.changedRoot = nodeFactory.createNode(liftOriginalRoot, resultLeft, resultRight);
                }
                else {
                    this.changedRoot = balancePolicy.balance(nodeFactory, liftOriginalRoot, resultLeft, resultRight);
                }
                return this;
            }
            default: {
                throw new AssertionError();
            }
        }
    }
}
