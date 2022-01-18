// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
final class BstRangeOps
{
    public static <K, N extends BstNode<K, N>> int totalInRange(final BstAggregate<? super N> aggregate, final GeneralRange<K> range, @Nullable final N root) {
        Preconditions.checkNotNull(aggregate);
        Preconditions.checkNotNull(range);
        if (root == null || range.isEmpty()) {
            return 0;
        }
        int total = aggregate.treeValue((Object)root);
        if (range.hasLowerBound()) {
            total -= totalBeyondRangeToSide(aggregate, range, BstSide.LEFT, root);
        }
        if (range.hasUpperBound()) {
            total -= totalBeyondRangeToSide(aggregate, range, BstSide.RIGHT, root);
        }
        return total;
    }
    
    private static <K, N extends BstNode<K, N>> int totalBeyondRangeToSide(final BstAggregate<? super N> aggregate, final GeneralRange<K> range, final BstSide side, @Nullable N root) {
        int accum = 0;
        while (root != null) {
            if (beyond(range, ((BstNode<K, N>)root).getKey(), side)) {
                accum += aggregate.entryValue((Object)root);
                accum += aggregate.treeValue(((BstNode<K, ? super N>)root).childOrNull(side));
                root = ((BstNode<K, N>)root).childOrNull(side.other());
            }
            else {
                root = ((BstNode<K, N>)root).childOrNull(side);
            }
        }
        return accum;
    }
    
    @Nullable
    public static <K, N extends BstNode<K, N>> N minusRange(final GeneralRange<K> range, final BstBalancePolicy<N> balancePolicy, final BstNodeFactory<N> nodeFactory, @Nullable final N root) {
        Preconditions.checkNotNull(range);
        Preconditions.checkNotNull(balancePolicy);
        Preconditions.checkNotNull(nodeFactory);
        final N higher = (N)(range.hasUpperBound() ? subTreeBeyondRangeToSide(range, balancePolicy, nodeFactory, BstSide.RIGHT, root) : null);
        final N lower = (N)(range.hasLowerBound() ? subTreeBeyondRangeToSide(range, balancePolicy, nodeFactory, BstSide.LEFT, root) : null);
        return balancePolicy.combine(nodeFactory, lower, higher);
    }
    
    @Nullable
    private static <K, N extends BstNode<K, N>> N subTreeBeyondRangeToSide(final GeneralRange<K> range, final BstBalancePolicy<N> balancePolicy, final BstNodeFactory<N> nodeFactory, final BstSide side, @Nullable final N root) {
        if (root == null) {
            return null;
        }
        if (beyond(range, ((BstNode<K, N>)root).getKey(), side)) {
            N left = ((BstNode<K, N>)root).childOrNull(BstSide.LEFT);
            N right = ((BstNode<K, N>)root).childOrNull(BstSide.RIGHT);
            switch (side) {
                case LEFT: {
                    right = (N)subTreeBeyondRangeToSide((GeneralRange<Object>)range, (BstBalancePolicy<BstNode>)balancePolicy, (BstNodeFactory<BstNode>)nodeFactory, BstSide.LEFT, right);
                    break;
                }
                case RIGHT: {
                    left = (N)subTreeBeyondRangeToSide((GeneralRange<Object>)range, (BstBalancePolicy<BstNode>)balancePolicy, (BstNodeFactory<BstNode>)nodeFactory, BstSide.RIGHT, left);
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
            return balancePolicy.balance(nodeFactory, root, left, right);
        }
        return (N)subTreeBeyondRangeToSide((GeneralRange<Object>)range, (BstBalancePolicy<BstNode>)balancePolicy, (BstNodeFactory<BstNode>)nodeFactory, side, ((BstNode<K, BstNode>)root).childOrNull(side));
    }
    
    @Nullable
    public static <K, N extends BstNode<K, N>, P extends BstPath<N, P>> P furthestPath(final GeneralRange<K> range, final BstSide side, final BstPathFactory<N, P> pathFactory, @Nullable final N root) {
        Preconditions.checkNotNull(range);
        Preconditions.checkNotNull(pathFactory);
        Preconditions.checkNotNull(side);
        if (root == null) {
            return null;
        }
        final P path = pathFactory.initialPath(root);
        return furthestPath(range, side, pathFactory, path);
    }
    
    private static <K, N extends BstNode<K, N>, P extends BstPath<N, P>> P furthestPath(final GeneralRange<K> range, final BstSide side, final BstPathFactory<N, P> pathFactory, P currentPath) {
        final N tip = ((BstPath<N, P>)currentPath).getTip();
        final K tipKey = ((BstNode<K, N>)tip).getKey();
        if (!beyond(range, tipKey, side)) {
            if (tip.hasChild(side)) {
                P alphaPath = pathFactory.extension(currentPath, side);
                alphaPath = (P)furthestPath((GeneralRange<Object>)range, side, (BstPathFactory<BstNode, BstPath>)pathFactory, alphaPath);
                if (alphaPath != null) {
                    return alphaPath;
                }
            }
            return (P)(beyond(range, tipKey, side.other()) ? null : currentPath);
        }
        if (tip.hasChild(side.other())) {
            currentPath = pathFactory.extension(currentPath, side.other());
            return (P)furthestPath((GeneralRange<Object>)range, side, (BstPathFactory<BstNode, BstPath>)pathFactory, currentPath);
        }
        return null;
    }
    
    public static <K> boolean beyond(final GeneralRange<K> range, final K key, final BstSide side) {
        Preconditions.checkNotNull(range);
        Preconditions.checkNotNull(key);
        switch (side) {
            case LEFT: {
                return range.tooLow(key);
            }
            case RIGHT: {
                return range.tooHigh(key);
            }
            default: {
                throw new AssertionError();
            }
        }
    }
}
