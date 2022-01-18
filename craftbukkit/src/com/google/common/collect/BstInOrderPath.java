// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.NoSuchElementException;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.base.Optional;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
final class BstInOrderPath<N extends BstNode<?, N>> extends BstPath<N, BstInOrderPath<N>>
{
    private final BstSide sideExtension;
    private transient Optional<BstInOrderPath<N>> prevInOrder;
    private transient Optional<BstInOrderPath<N>> nextInOrder;
    
    public static <N extends BstNode<?, N>> BstPathFactory<N, BstInOrderPath<N>> inOrderFactory() {
        return new BstPathFactory<N, BstInOrderPath<N>>() {
            public BstInOrderPath<N> extension(final BstInOrderPath<N> path, final BstSide side) {
                return (BstInOrderPath<N>)extension((BstInOrderPath<BstNode>)path, side);
            }
            
            public BstInOrderPath<N> initialPath(final N root) {
                return new BstInOrderPath<N>(root, null, null, null);
            }
        };
    }
    
    private static <N extends BstNode<?, N>> BstInOrderPath<N> extension(final BstInOrderPath<N> path, final BstSide side) {
        Preconditions.checkNotNull(path);
        final N tip = (N)path.getTip();
        return new BstInOrderPath<N>(((BstNode<K, N>)tip).getChild(side), side, path);
    }
    
    private BstInOrderPath(final N tip, @Nullable final BstSide sideExtension, @Nullable final BstInOrderPath<N> tail) {
        super(tip, tail);
        this.sideExtension = sideExtension;
        assert sideExtension == null == (tail == null);
    }
    
    private Optional<BstInOrderPath<N>> computeNextInOrder(final BstSide side) {
        if (this.getTip().hasChild(side)) {
            BstInOrderPath<N> path = extension(this, side);
            for (BstSide otherSide = side.other(); path.getTip().hasChild(otherSide); path = extension(path, otherSide)) {}
            return Optional.of(path);
        }
        BstInOrderPath<N> current;
        for (current = this; current.sideExtension == side; current = current.getPrefix()) {}
        current = current.prefixOrNull();
        return Optional.fromNullable(current);
    }
    
    private Optional<BstInOrderPath<N>> nextInOrder(final BstSide side) {
        switch (side) {
            case LEFT: {
                final Optional<BstInOrderPath<N>> result = this.prevInOrder;
                return (result == null) ? (this.prevInOrder = this.computeNextInOrder(side)) : result;
            }
            case RIGHT: {
                final Optional<BstInOrderPath<N>> result = this.nextInOrder;
                return (result == null) ? (this.nextInOrder = this.computeNextInOrder(side)) : result;
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    public boolean hasNext(final BstSide side) {
        return this.nextInOrder(side).isPresent();
    }
    
    public BstInOrderPath<N> next(final BstSide side) {
        if (!this.hasNext(side)) {
            throw new NoSuchElementException();
        }
        return this.nextInOrder(side).get();
    }
    
    public BstSide getSideOfExtension() {
        return this.sideExtension;
    }
}
