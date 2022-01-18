// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
final class BstMutationRule<K, N extends BstNode<K, N>>
{
    private final BstModifier<K, N> modifier;
    private final BstBalancePolicy<N> balancePolicy;
    private final BstNodeFactory<N> nodeFactory;
    
    public static <K, N extends BstNode<K, N>> BstMutationRule<K, N> createRule(final BstModifier<K, N> modifier, final BstBalancePolicy<N> balancePolicy, final BstNodeFactory<N> nodeFactory) {
        return new BstMutationRule<K, N>(modifier, balancePolicy, nodeFactory);
    }
    
    private BstMutationRule(final BstModifier<K, N> modifier, final BstBalancePolicy<N> balancePolicy, final BstNodeFactory<N> nodeFactory) {
        this.balancePolicy = Preconditions.checkNotNull(balancePolicy);
        this.nodeFactory = Preconditions.checkNotNull(nodeFactory);
        this.modifier = Preconditions.checkNotNull(modifier);
    }
    
    public BstModifier<K, N> getModifier() {
        return this.modifier;
    }
    
    public BstBalancePolicy<N> getBalancePolicy() {
        return this.balancePolicy;
    }
    
    public BstNodeFactory<N> getNodeFactory() {
        return this.nodeFactory;
    }
}
