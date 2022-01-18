// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import java.util.Comparator;

final class ImmutableSortedAsList<E> extends ImmutableList<E> implements SortedIterable<E>
{
    private final transient ImmutableSortedSet<E> backingSet;
    private final transient ImmutableList<E> backingList;
    
    ImmutableSortedAsList(final ImmutableSortedSet<E> backingSet, final ImmutableList<E> backingList) {
        this.backingSet = backingSet;
        this.backingList = backingList;
    }
    
    public Comparator<? super E> comparator() {
        return this.backingSet.comparator();
    }
    
    public boolean contains(@Nullable final Object target) {
        return this.backingSet.indexOf(target) >= 0;
    }
    
    public int indexOf(@Nullable final Object target) {
        return this.backingSet.indexOf(target);
    }
    
    public int lastIndexOf(@Nullable final Object target) {
        return this.backingSet.indexOf(target);
    }
    
    public ImmutableList<E> subList(final int fromIndex, final int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size());
        return (fromIndex == toIndex) ? ImmutableList.of() : new RegularImmutableSortedSet<E>(this.backingList.subList(fromIndex, toIndex), this.backingSet.comparator()).asList();
    }
    
    Object writeReplace() {
        return new ImmutableAsList.SerializedForm(this.backingSet);
    }
    
    public UnmodifiableIterator<E> iterator() {
        return this.backingList.iterator();
    }
    
    public E get(final int index) {
        return this.backingList.get(index);
    }
    
    public UnmodifiableListIterator<E> listIterator() {
        return this.backingList.listIterator();
    }
    
    public UnmodifiableListIterator<E> listIterator(final int index) {
        return this.backingList.listIterator(index);
    }
    
    public int size() {
        return this.backingList.size();
    }
    
    public boolean equals(@Nullable final Object obj) {
        return this.backingList.equals(obj);
    }
    
    public int hashCode() {
        return this.backingList.hashCode();
    }
    
    boolean isPartialView() {
        return this.backingList.isPartialView();
    }
}
