// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
final class BstCountBasedBalancePolicies
{
    private static final int SINGLE_ROTATE_RATIO = 4;
    private static final int SECOND_ROTATE_RATIO = 2;
    
    public static <N extends BstNode<?, N>> BstBalancePolicy<N> noRebalancePolicy(final BstAggregate<N> countAggregate) {
        Preconditions.checkNotNull(countAggregate);
        return new BstBalancePolicy<N>() {
            public N balance(final BstNodeFactory<N> nodeFactory, final N source, @Nullable final N left, @Nullable final N right) {
                return Preconditions.checkNotNull(nodeFactory).createNode(source, left, right);
            }
            
            @Nullable
            public N combine(final BstNodeFactory<N> nodeFactory, @Nullable final N left, @Nullable final N right) {
                if (left == null) {
                    return right;
                }
                if (right == null) {
                    return left;
                }
                if (countAggregate.treeValue(left) > countAggregate.treeValue(right)) {
                    return nodeFactory.createNode(left, ((BstNode<K, N>)left).childOrNull(BstSide.LEFT), this.combine(nodeFactory, ((BstNode<K, N>)left).childOrNull(BstSide.RIGHT), right));
                }
                return nodeFactory.createNode(right, this.combine(nodeFactory, left, ((BstNode<K, N>)right).childOrNull(BstSide.LEFT)), ((BstNode<K, N>)right).childOrNull(BstSide.RIGHT));
            }
        };
    }
    
    public static <K, N extends BstNode<K, N>> BstBalancePolicy<N> singleRebalancePolicy(final BstAggregate<N> countAggregate) {
        Preconditions.checkNotNull(countAggregate);
        return new BstBalancePolicy<N>() {
            public N balance(final BstNodeFactory<N> nodeFactory, final N source, @Nullable final N left, @Nullable final N right) {
                final int countL = countAggregate.treeValue(left);
                final int countR = countAggregate.treeValue(right);
                if (countL + countR > 1) {
                    if (countR >= 4 * countL) {
                        return this.rotateL(nodeFactory, source, left, right);
                    }
                    if (countL >= 4 * countR) {
                        return this.rotateR(nodeFactory, source, left, right);
                    }
                }
                return nodeFactory.createNode(source, left, right);
            }
            
            private N rotateL(final BstNodeFactory<N> nodeFactory, final N source, @Nullable final N left, N right) {
                Preconditions.checkNotNull(right);
                final N rl = ((BstNode<K, N>)right).childOrNull(BstSide.LEFT);
                final N rr = ((BstNode<K, N>)right).childOrNull(BstSide.RIGHT);
                if (countAggregate.treeValue(rl) >= 2 * countAggregate.treeValue(rr)) {
                    right = this.singleR(nodeFactory, right, rl, rr);
                }
                return this.singleL(nodeFactory, source, left, right);
            }
            
            private N rotateR(final BstNodeFactory<N> nodeFactory, final N source, N left, @Nullable final N right) {
                Preconditions.checkNotNull(left);
                final N lr = ((BstNode<K, N>)left).childOrNull(BstSide.RIGHT);
                final N ll = ((BstNode<K, N>)left).childOrNull(BstSide.LEFT);
                if (countAggregate.treeValue(lr) >= 2 * countAggregate.treeValue(ll)) {
                    left = this.singleL(nodeFactory, left, ll, lr);
                }
                return this.singleR(nodeFactory, source, left, right);
            }
            
            private N singleL(final BstNodeFactory<N> nodeFactory, final N source, @Nullable final N left, final N right) {
                Preconditions.checkNotNull(right);
                return nodeFactory.createNode(right, nodeFactory.createNode(source, left, ((BstNode<K, N>)right).childOrNull(BstSide.LEFT)), ((BstNode<K, N>)right).childOrNull(BstSide.RIGHT));
            }
            
            private N singleR(final BstNodeFactory<N> nodeFactory, final N source, final N left, @Nullable final N right) {
                Preconditions.checkNotNull(left);
                return nodeFactory.createNode(left, ((BstNode<K, N>)left).childOrNull(BstSide.LEFT), nodeFactory.createNode(source, ((BstNode<K, N>)left).childOrNull(BstSide.RIGHT), right));
            }
            
            @Nullable
            public N combine(final BstNodeFactory<N> nodeFactory, @Nullable N left, @Nullable N right) {
                if (left == null) {
                    return right;
                }
                if (right == null) {
                    return left;
                }
                N newRootSource;
                if (countAggregate.treeValue(left) > countAggregate.treeValue(right)) {
                    final BstMutationResult<K, N> extractLeftMax = BstOperations.extractMax(left, nodeFactory, this);
                    newRootSource = extractLeftMax.getOriginalTarget();
                    left = extractLeftMax.getChangedRoot();
                }
                else {
                    final BstMutationResult<K, N> extractRightMin = BstOperations.extractMin(right, nodeFactory, this);
                    newRootSource = extractRightMin.getOriginalTarget();
                    right = extractRightMin.getChangedRoot();
                }
                return nodeFactory.createNode(newRootSource, left, right);
            }
        };
    }
    
    public static <K, N extends BstNode<K, N>> BstBalancePolicy<N> fullRebalancePolicy(final BstAggregate<N> countAggregate) {
        Preconditions.checkNotNull(countAggregate);
        final BstBalancePolicy<N> singleBalancePolicy = (BstBalancePolicy<N>)singleRebalancePolicy((BstAggregate<BstNode>)countAggregate);
        return new BstBalancePolicy<N>() {
            public N balance(final BstNodeFactory<N> nodeFactory, final N source, @Nullable final N left, @Nullable final N right) {
                if (left == null) {
                    return BstOperations.insertMin(right, source, nodeFactory, singleBalancePolicy);
                }
                if (right == null) {
                    return BstOperations.insertMax(left, source, nodeFactory, singleBalancePolicy);
                }
                final int countL = countAggregate.treeValue(left);
                final int countR = countAggregate.treeValue(right);
                if (4 * countL <= countR) {
                    final N resultLeft = this.balance(nodeFactory, source, left, ((BstNode<K, N>)right).childOrNull(BstSide.LEFT));
                    return singleBalancePolicy.balance(nodeFactory, right, resultLeft, ((BstNode<K, N>)right).childOrNull(BstSide.RIGHT));
                }
                if (4 * countR <= countL) {
                    final N resultRight = this.balance(nodeFactory, source, ((BstNode<K, N>)left).childOrNull(BstSide.RIGHT), right);
                    return singleBalancePolicy.balance(nodeFactory, left, ((BstNode<K, N>)left).childOrNull(BstSide.LEFT), resultRight);
                }
                return nodeFactory.createNode(source, left, right);
            }
            
            @Nullable
            public N combine(final BstNodeFactory<N> nodeFactory, @Nullable final N left, @Nullable final N right) {
                if (left == null) {
                    return right;
                }
                if (right == null) {
                    return left;
                }
                final int countL = countAggregate.treeValue(left);
                final int countR = countAggregate.treeValue(right);
                if (4 * countL <= countR) {
                    final N resultLeft = this.combine(nodeFactory, left, ((BstNode<K, N>)right).childOrNull(BstSide.LEFT));
                    return singleBalancePolicy.balance(nodeFactory, right, resultLeft, ((BstNode<K, N>)right).childOrNull(BstSide.RIGHT));
                }
                if (4 * countR <= countL) {
                    final N resultRight = this.combine(nodeFactory, ((BstNode<K, N>)left).childOrNull(BstSide.RIGHT), right);
                    return singleBalancePolicy.balance(nodeFactory, left, ((BstNode<K, N>)left).childOrNull(BstSide.LEFT), resultRight);
                }
                return singleBalancePolicy.combine(nodeFactory, left, right);
            }
        };
    }
}
