// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.SortedSet;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.Beta;

@Beta
@GwtCompatible
public abstract class ContiguousSet<C extends Comparable> extends ImmutableSortedSet<C>
{
    final DiscreteDomain<C> domain;
    
    ContiguousSet(final DiscreteDomain<C> domain) {
        super(Ordering.natural());
        this.domain = domain;
    }
    
    public ContiguousSet<C> headSet(final C toElement) {
        return this.headSet(Preconditions.checkNotNull(toElement), false);
    }
    
    ContiguousSet<C> headSet(final C toElement, final boolean inclusive) {
        return this.headSetImpl(Preconditions.checkNotNull(toElement), inclusive);
    }
    
    public ContiguousSet<C> subSet(final C fromElement, final C toElement) {
        Preconditions.checkNotNull(fromElement);
        Preconditions.checkNotNull(toElement);
        Preconditions.checkArgument(this.comparator().compare((Object)fromElement, (Object)toElement) <= 0);
        return this.subSet(fromElement, true, toElement, false);
    }
    
    ContiguousSet<C> subSet(final C fromElement, final boolean fromInclusive, final C toElement, final boolean toInclusive) {
        Preconditions.checkNotNull(fromElement);
        Preconditions.checkNotNull(toElement);
        Preconditions.checkArgument(this.comparator().compare((Object)fromElement, (Object)toElement) <= 0);
        return this.subSetImpl(fromElement, fromInclusive, toElement, toInclusive);
    }
    
    public ContiguousSet<C> tailSet(final C fromElement) {
        return this.tailSet(Preconditions.checkNotNull(fromElement), true);
    }
    
    ContiguousSet<C> tailSet(final C fromElement, final boolean inclusive) {
        return this.tailSetImpl(Preconditions.checkNotNull(fromElement), inclusive);
    }
    
    abstract ContiguousSet<C> headSetImpl(final C p0, final boolean p1);
    
    abstract ContiguousSet<C> subSetImpl(final C p0, final boolean p1, final C p2, final boolean p3);
    
    abstract ContiguousSet<C> tailSetImpl(final C p0, final boolean p1);
    
    public abstract ContiguousSet<C> intersection(final ContiguousSet<C> p0);
    
    public abstract Range<C> range();
    
    public abstract Range<C> range(final BoundType p0, final BoundType p1);
    
    public String toString() {
        return this.range().toString();
    }
}
