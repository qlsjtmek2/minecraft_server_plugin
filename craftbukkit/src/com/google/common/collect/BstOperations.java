// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
final class BstOperations
{
    @Nullable
    public static <K, N extends BstNode<K, N>> N seek(final Comparator<? super K> comparator, @Nullable final N tree, final K key) {
        Preconditions.checkNotNull(comparator);
        if (tree == null) {
            return null;
        }
        final int cmp = comparator.compare((Object)key, ((BstNode<? super K, N>)tree).getKey());
        if (cmp == 0) {
            return tree;
        }
        final BstSide side = (cmp < 0) ? BstSide.LEFT : BstSide.RIGHT;
        return (N)seek((Comparator<? super Object>)comparator, ((BstNode<K, BstNode>)tree).childOrNull(side), (Object)key);
    }
    
    public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> mutate(final Comparator<? super K> comparator, final BstMutationRule<K, N> mutationRule, @Nullable final N tree, final K key) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(mutationRule);
        Preconditions.checkNotNull(key);
        if (tree != null) {
            final int cmp = comparator.compare((Object)key, ((BstNode<? super K, N>)tree).getKey());
            if (cmp != 0) {
                final BstSide side = (cmp < 0) ? BstSide.LEFT : BstSide.RIGHT;
                final BstMutationResult<K, N> mutation = (BstMutationResult<K, N>)mutate((Comparator<? super Object>)comparator, (BstMutationRule<Object, BstNode>)mutationRule, ((BstNode<K, BstNode>)tree).childOrNull(side), key);
                return mutation.lift(tree, side, mutationRule.getNodeFactory(), mutationRule.getBalancePolicy());
            }
        }
        return modify(tree, key, mutationRule);
    }
    
    public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> mutate(BstInOrderPath<N> path, final BstMutationRule<K, N> mutationRule) {
        Preconditions.checkNotNull(path);
        Preconditions.checkNotNull(mutationRule);
        final BstBalancePolicy<N> balancePolicy = mutationRule.getBalancePolicy();
        final BstNodeFactory<N> nodeFactory = mutationRule.getNodeFactory();
        final BstModifier<K, N> modifier = mutationRule.getModifier();
        final N target = (N)path.getTip();
        final K key = ((BstNode<K, N>)target).getKey();
        BstMutationResult<K, N> result = modify(target, key, mutationRule);
        while (path.hasPrefix()) {
            final BstInOrderPath<N> prefix = (BstInOrderPath<N>)path.getPrefix();
            result = result.lift((N)prefix.getTip(), path.getSideOfExtension(), nodeFactory, balancePolicy);
            path = prefix;
        }
        return result;
    }
    
    private static <K, N extends BstNode<K, N>> BstMutationResult<K, N> modify(@Nullable final N tree, final K key, final BstMutationRule<K, N> mutationRule) {
        final BstBalancePolicy<N> rebalancePolicy = mutationRule.getBalancePolicy();
        final BstNodeFactory<N> nodeFactory = mutationRule.getNodeFactory();
        final BstModifier<K, N> modifier = mutationRule.getModifier();
        final N originalTarget = (N)((tree == null) ? null : nodeFactory.createLeaf(tree));
        final BstModificationResult<N> modResult = modifier.modify(key, originalTarget);
        N originalLeft = null;
        N originalRight = null;
        if (tree != null) {
            originalLeft = ((BstNode<K, N>)tree).childOrNull(BstSide.LEFT);
            originalRight = ((BstNode<K, N>)tree).childOrNull(BstSide.RIGHT);
        }
        N changedRoot = null;
        switch (modResult.getType()) {
            case IDENTITY: {
                changedRoot = tree;
                break;
            }
            case REBUILDING_CHANGE: {
                if (modResult.getChangedTarget() != null) {
                    changedRoot = nodeFactory.createNode(modResult.getChangedTarget(), originalLeft, originalRight);
                    break;
                }
                if (tree == null) {
                    changedRoot = null;
                    break;
                }
                throw new AssertionError((Object)"Modification result is a REBUILDING_CHANGE, but rebalancing required");
            }
            case REBALANCING_CHANGE: {
                if (modResult.getChangedTarget() != null) {
                    changedRoot = rebalancePolicy.balance(nodeFactory, modResult.getChangedTarget(), originalLeft, originalRight);
                    break;
                }
                if (tree != null) {
                    changedRoot = rebalancePolicy.combine(nodeFactory, originalLeft, originalRight);
                    break;
                }
                changedRoot = null;
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        return BstMutationResult.mutationResult(key, tree, changedRoot, modResult);
    }
    
    public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> extractMin(final N root, final BstNodeFactory<N> nodeFactory, final BstBalancePolicy<N> balancePolicy) {
        Preconditions.checkNotNull(root);
        Preconditions.checkNotNull(nodeFactory);
        Preconditions.checkNotNull(balancePolicy);
        if (root.hasChild(BstSide.LEFT)) {
            final BstMutationResult<K, N> subResult = (BstMutationResult<K, N>)extractMin(((BstNode<K, BstNode>)root).getChild(BstSide.LEFT), (BstNodeFactory<BstNode>)nodeFactory, (BstBalancePolicy<BstNode>)balancePolicy);
            return subResult.lift(root, BstSide.LEFT, nodeFactory, balancePolicy);
        }
        return BstMutationResult.mutationResult(((BstNode<K, N>)root).getKey(), root, ((BstNode<K, N>)root).childOrNull(BstSide.RIGHT), (BstModificationResult<N>)BstModificationResult.rebalancingChange(root, (N)null));
    }
    
    public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> extractMax(final N root, final BstNodeFactory<N> nodeFactory, final BstBalancePolicy<N> balancePolicy) {
        Preconditions.checkNotNull(root);
        Preconditions.checkNotNull(nodeFactory);
        Preconditions.checkNotNull(balancePolicy);
        if (root.hasChild(BstSide.RIGHT)) {
            final BstMutationResult<K, N> subResult = (BstMutationResult<K, N>)extractMax(((BstNode<K, BstNode>)root).getChild(BstSide.RIGHT), (BstNodeFactory<BstNode>)nodeFactory, (BstBalancePolicy<BstNode>)balancePolicy);
            return subResult.lift(root, BstSide.RIGHT, nodeFactory, balancePolicy);
        }
        return BstMutationResult.mutationResult(((BstNode<K, N>)root).getKey(), root, ((BstNode<K, N>)root).childOrNull(BstSide.LEFT), (BstModificationResult<N>)BstModificationResult.rebalancingChange(root, (N)null));
    }
    
    public static <N extends BstNode<?, N>> N insertMin(@Nullable final N root, final N entry, final BstNodeFactory<N> nodeFactory, final BstBalancePolicy<N> balancePolicy) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(nodeFactory);
        Preconditions.checkNotNull(balancePolicy);
        if (root == null) {
            return nodeFactory.createLeaf(entry);
        }
        return balancePolicy.balance(nodeFactory, root, (N)insertMin(((BstNode<K, BstNode>)root).childOrNull(BstSide.LEFT), entry, (BstNodeFactory<BstNode>)nodeFactory, (BstBalancePolicy<BstNode>)balancePolicy), ((BstNode<K, N>)root).childOrNull(BstSide.RIGHT));
    }
    
    public static <N extends BstNode<?, N>> N insertMax(@Nullable final N root, final N entry, final BstNodeFactory<N> nodeFactory, final BstBalancePolicy<N> balancePolicy) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(nodeFactory);
        Preconditions.checkNotNull(balancePolicy);
        if (root == null) {
            return nodeFactory.createLeaf(entry);
        }
        return balancePolicy.balance(nodeFactory, root, ((BstNode<K, N>)root).childOrNull(BstSide.LEFT), (N)insertMax(((BstNode<K, BstNode>)root).childOrNull(BstSide.RIGHT), entry, (BstNodeFactory<BstNode>)nodeFactory, (BstBalancePolicy<BstNode>)balancePolicy));
    }
}
