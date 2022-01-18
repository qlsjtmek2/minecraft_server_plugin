// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
interface SortedMultiset<E> extends Multiset<E>, SortedIterable<E>
{
    Comparator<? super E> comparator();
    
    Entry<E> firstEntry();
    
    Entry<E> lastEntry();
    
    Entry<E> pollFirstEntry();
    
    Entry<E> pollLastEntry();
    
    SortedSet<E> elementSet();
    
    Iterator<E> iterator();
    
    SortedMultiset<E> descendingMultiset();
    
    SortedMultiset<E> headMultiset(final E p0, final BoundType p1);
    
    SortedMultiset<E> subMultiset(final E p0, final BoundType p1, final E p2, final BoundType p3);
    
    SortedMultiset<E> tailMultiset(final E p0, final BoundType p1);
}
