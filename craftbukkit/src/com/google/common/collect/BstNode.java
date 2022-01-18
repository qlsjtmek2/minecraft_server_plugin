// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Comparator;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
class BstNode<K, N extends BstNode<K, N>>
{
    private final K key;
    @Nullable
    private final N left;
    @Nullable
    private final N right;
    
    BstNode(final K key, @Nullable final N left, @Nullable final N right) {
        this.key = Preconditions.checkNotNull(key);
        this.left = left;
        this.right = right;
    }
    
    public final K getKey() {
        return this.key;
    }
    
    @Nullable
    public final N childOrNull(final BstSide side) {
        switch (side) {
            case LEFT: {
                return this.left;
            }
            case RIGHT: {
                return this.right;
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    public final boolean hasChild(final BstSide side) {
        return this.childOrNull(side) != null;
    }
    
    public final N getChild(final BstSide side) {
        final N child = this.childOrNull(side);
        Preconditions.checkState(child != null);
        return child;
    }
    
    protected final boolean orderingInvariantHolds(final Comparator<? super K> comparator) {
        Preconditions.checkNotNull(comparator);
        boolean result = true;
        if (this.hasChild(BstSide.LEFT)) {
            result &= (comparator.compare(this.getChild(BstSide.LEFT).getKey(), (Object)this.key) < 0);
        }
        if (this.hasChild(BstSide.RIGHT)) {
            result &= (comparator.compare(this.getChild(BstSide.RIGHT).getKey(), (Object)this.key) > 0);
        }
        return result;
    }
}
