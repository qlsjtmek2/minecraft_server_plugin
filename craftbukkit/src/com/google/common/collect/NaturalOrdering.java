// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collections;
import java.util.List;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;

@GwtCompatible(serializable = true)
final class NaturalOrdering extends Ordering<Comparable> implements Serializable
{
    static final NaturalOrdering INSTANCE;
    private static final long serialVersionUID = 0L;
    
    public int compare(final Comparable left, final Comparable right) {
        Preconditions.checkNotNull(right);
        if (left == right) {
            return 0;
        }
        return left.compareTo(right);
    }
    
    public <S extends Comparable> Ordering<S> reverse() {
        return (Ordering<S>)ReverseNaturalOrdering.INSTANCE;
    }
    
    public int binarySearch(final List<? extends Comparable> sortedList, final Comparable key) {
        return Collections.binarySearch((List<? extends Comparable<? super Comparable>>)sortedList, key);
    }
    
    public <E extends Comparable> List<E> sortedCopy(final Iterable<E> iterable) {
        final List<E> list = (List<E>)Lists.newArrayList((Iterable<?>)iterable);
        Collections.sort(list);
        return list;
    }
    
    private Object readResolve() {
        return NaturalOrdering.INSTANCE;
    }
    
    public String toString() {
        return "Ordering.natural()";
    }
    
    static {
        INSTANCE = new NaturalOrdering();
    }
}
