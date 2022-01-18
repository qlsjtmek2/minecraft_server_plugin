// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import java.util.ConcurrentModificationException;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;

final class SortedTreeMultiset<E> extends AbstractSortedMultiset<E>
{
    private final GeneralRange<E> range;
    private final AtomicReference<Node> rootReference;
    private final transient BstPathFactory<Node, BstInOrderPath<Node>> pathFactory;
    private final transient BstAggregate<Node> distinctAggregate;
    private final transient BstAggregate<Node> sizeAggregate;
    private final transient BstNodeFactory<Node> nodeFactory;
    
    public static <E extends Comparable> SortedTreeMultiset<E> create() {
        return new SortedTreeMultiset<E>(Ordering.natural());
    }
    
    public static <E> SortedTreeMultiset<E> create(final Comparator<? super E> comparator) {
        return new SortedTreeMultiset<E>(comparator);
    }
    
    public static <E extends Comparable> SortedTreeMultiset<E> create(final Iterable<? extends E> elements) {
        final SortedTreeMultiset<E> multiset = create();
        Iterables.addAll(multiset, elements);
        return multiset;
    }
    
    private SortedTreeMultiset(final Comparator<? super E> comparator) {
        super(comparator);
        this.pathFactory = BstInOrderPath.inOrderFactory();
        this.distinctAggregate = new BstAggregate<Node>() {
            public int entryValue(final Node entry) {
                return 1;
            }
            
            public int treeValue(@Nullable final Node tree) {
                return SortedTreeMultiset.this.distinctOrZero(tree);
            }
        };
        this.sizeAggregate = new BstAggregate<Node>() {
            public int entryValue(final Node entry) {
                return entry.elemOccurrences;
            }
            
            public int treeValue(@Nullable final Node tree) {
                return SortedTreeMultiset.this.sizeOrZero(tree);
            }
        };
        this.nodeFactory = new BstNodeFactory<Node>() {
            public Node createNode(final Node source, @Nullable final Node left, @Nullable final Node right) {
                return new Node(((BstNode<Object, N>)source).getKey(), source.elemOccurrences, left, right);
            }
        };
        this.range = GeneralRange.all(comparator);
        this.rootReference = new AtomicReference<Node>();
    }
    
    private SortedTreeMultiset(final GeneralRange<E> range, final AtomicReference<Node> root) {
        super(range.comparator());
        this.pathFactory = BstInOrderPath.inOrderFactory();
        this.distinctAggregate = new BstAggregate<Node>() {
            public int entryValue(final Node entry) {
                return 1;
            }
            
            public int treeValue(@Nullable final Node tree) {
                return SortedTreeMultiset.this.distinctOrZero(tree);
            }
        };
        this.sizeAggregate = new BstAggregate<Node>() {
            public int entryValue(final Node entry) {
                return entry.elemOccurrences;
            }
            
            public int treeValue(@Nullable final Node tree) {
                return SortedTreeMultiset.this.sizeOrZero(tree);
            }
        };
        this.nodeFactory = new BstNodeFactory<Node>() {
            public Node createNode(final Node source, @Nullable final Node left, @Nullable final Node right) {
                return new Node(((BstNode<Object, N>)source).getKey(), source.elemOccurrences, left, right);
            }
        };
        this.range = range;
        this.rootReference = root;
    }
    
    E checkElement(final Object o) {
        Preconditions.checkNotNull(o);
        return (E)o;
    }
    
    int distinctElements() {
        final Node root = this.rootReference.get();
        return BstRangeOps.totalInRange(this.distinctAggregate, this.range, root);
    }
    
    public int size() {
        final Node root = this.rootReference.get();
        return BstRangeOps.totalInRange(this.sizeAggregate, this.range, root);
    }
    
    public int count(@Nullable final Object element) {
        if (element == null) {
            return 0;
        }
        try {
            final E e = this.checkElement(element);
            if (this.range.contains(e)) {
                final Node node = BstOperations.seek(this.comparator(), this.rootReference.get(), e);
                return (node == null) ? 0 : node.elemOccurrences;
            }
            return 0;
        }
        catch (ClassCastException e2) {
            return 0;
        }
    }
    
    private int mutate(final E e, final MultisetModifier modifier) {
        final BstMutationRule<E, Node> mutationRule = BstMutationRule.createRule((BstModifier<E, Node>)modifier, (BstBalancePolicy<Node>)BstCountBasedBalancePolicies.singleRebalancePolicy((BstAggregate<N>)this.distinctAggregate), this.nodeFactory);
        final BstMutationResult<E, Node> mutationResult = BstOperations.mutate(this.comparator(), mutationRule, this.rootReference.get(), e);
        if (!this.rootReference.compareAndSet(mutationResult.getOriginalRoot(), mutationResult.getChangedRoot())) {
            throw new ConcurrentModificationException();
        }
        final Node original = mutationResult.getOriginalTarget();
        return (original == null) ? 0 : original.elemOccurrences;
    }
    
    public int add(final E element, final int occurrences) {
        Preconditions.checkNotNull(element);
        if (occurrences == 0) {
            return this.count(element);
        }
        Preconditions.checkArgument(this.range.contains(element));
        return this.mutate(element, new AddModifier(occurrences));
    }
    
    public int remove(@Nullable final Object element, final int occurrences) {
        if (element == null) {
            return 0;
        }
        if (occurrences == 0) {
            return this.count(element);
        }
        try {
            final E e = this.checkElement(element);
            return this.range.contains(e) ? this.mutate(e, new RemoveModifier(occurrences)) : 0;
        }
        catch (ClassCastException e2) {
            return 0;
        }
    }
    
    public boolean setCount(final E element, final int oldCount, final int newCount) {
        Preconditions.checkNotNull(element);
        Preconditions.checkArgument(this.range.contains(element));
        return this.mutate(element, new ConditionalSetCountModifier(oldCount, newCount)) == oldCount;
    }
    
    public int setCount(final E element, final int count) {
        Preconditions.checkNotNull(element);
        Preconditions.checkArgument(this.range.contains(element));
        return this.mutate(element, new SetCountModifier(count));
    }
    
    Iterator<Multiset.Entry<E>> entryIterator() {
        final Node root = this.rootReference.get();
        final BstInOrderPath<Node> startingPath = BstRangeOps.furthestPath(this.range, BstSide.LEFT, this.pathFactory, root);
        return this.iteratorInDirection(startingPath, BstSide.RIGHT);
    }
    
    Iterator<Multiset.Entry<E>> descendingEntryIterator() {
        final Node root = this.rootReference.get();
        final BstInOrderPath<Node> startingPath = BstRangeOps.furthestPath(this.range, BstSide.RIGHT, this.pathFactory, root);
        return this.iteratorInDirection(startingPath, BstSide.LEFT);
    }
    
    private Iterator<Multiset.Entry<E>> iteratorInDirection(@Nullable final BstInOrderPath<Node> start, final BstSide direction) {
        final Iterator<BstInOrderPath<Node>> pathIterator = new AbstractLinkedIterator<BstInOrderPath<Node>>(start) {
            protected BstInOrderPath<Node> computeNext(final BstInOrderPath<Node> previous) {
                if (!previous.hasNext(direction)) {
                    return null;
                }
                final BstInOrderPath<Node> next = previous.next(direction);
                return SortedTreeMultiset.this.range.contains(((BstNode<Object, N>)next.getTip()).getKey()) ? next : null;
            }
        };
        return new Iterator<Multiset.Entry<E>>() {
            E toRemove = null;
            
            public boolean hasNext() {
                return pathIterator.hasNext();
            }
            
            public Multiset.Entry<E> next() {
                final BstInOrderPath<Node> path = pathIterator.next();
                final E key = ((BstNode<E, N>)path.getTip()).getKey();
                this.toRemove = key;
                return Multisets.immutableEntry(key, path.getTip().elemOccurrences);
            }
            
            public void remove() {
                Preconditions.checkState(this.toRemove != null);
                SortedTreeMultiset.this.setCount(this.toRemove, 0);
                this.toRemove = null;
            }
        };
    }
    
    public void clear() {
        final Node root = this.rootReference.get();
        final Node cleared = BstRangeOps.minusRange(this.range, (BstBalancePolicy<Node>)BstCountBasedBalancePolicies.fullRebalancePolicy((BstAggregate<N>)this.distinctAggregate), this.nodeFactory, root);
        if (!this.rootReference.compareAndSet(root, cleared)) {
            throw new ConcurrentModificationException();
        }
    }
    
    public SortedMultiset<E> headMultiset(final E upperBound, final BoundType boundType) {
        Preconditions.checkNotNull(upperBound);
        return new SortedTreeMultiset((GeneralRange<Object>)this.range.intersect(GeneralRange.upTo(this.comparator, upperBound, boundType)), this.rootReference);
    }
    
    public SortedMultiset<E> tailMultiset(final E lowerBound, final BoundType boundType) {
        Preconditions.checkNotNull(lowerBound);
        return new SortedTreeMultiset((GeneralRange<Object>)this.range.intersect(GeneralRange.downTo(this.comparator, lowerBound, boundType)), this.rootReference);
    }
    
    private int sizeOrZero(@Nullable final Node node) {
        return (node == null) ? 0 : node.size;
    }
    
    private int distinctOrZero(@Nullable final Node node) {
        return (node == null) ? 0 : node.distinct;
    }
    
    private final class Node extends BstNode<E, Node>
    {
        private final int elemOccurrences;
        private final int size;
        private final int distinct;
        
        private Node(final E key, @Nullable final int elemCount, @Nullable final Node left, final Node right) {
            super(SortedTreeMultiset.this.checkElement(key), left, right);
            Preconditions.checkArgument(elemCount > 0);
            this.elemOccurrences = elemCount;
            this.size = elemCount + SortedTreeMultiset.this.sizeOrZero(left) + SortedTreeMultiset.this.sizeOrZero(right);
            this.distinct = 1 + SortedTreeMultiset.this.distinctOrZero(left) + SortedTreeMultiset.this.distinctOrZero(right);
        }
        
        private Node(final SortedTreeMultiset sortedTreeMultiset, final E key, final int elemCount) {
            this(key, elemCount, null, null);
        }
    }
    
    private abstract class MultisetModifier implements BstModifier<E, Node>
    {
        abstract int newCount(final int p0);
        
        @Nullable
        public BstModificationResult<Node> modify(final E key, @Nullable final Node originalEntry) {
            final int oldCount = (originalEntry == null) ? 0 : originalEntry.elemOccurrences;
            final int newCount = this.newCount(oldCount);
            if (oldCount == newCount) {
                return BstModificationResult.identity(originalEntry);
            }
            if (newCount == 0) {
                return BstModificationResult.rebalancingChange(originalEntry, null);
            }
            if (oldCount == 0) {
                return BstModificationResult.rebalancingChange(null, new Node((Object)key, newCount));
            }
            return BstModificationResult.rebuildingChange(originalEntry, new Node((Object)key, newCount));
        }
    }
    
    private final class AddModifier extends MultisetModifier
    {
        private final int countToAdd;
        
        private AddModifier(final int countToAdd) {
            Preconditions.checkArgument(countToAdd > 0);
            this.countToAdd = countToAdd;
        }
        
        int newCount(final int oldCount) {
            return oldCount + this.countToAdd;
        }
    }
    
    private final class RemoveModifier extends MultisetModifier
    {
        private final int countToRemove;
        
        private RemoveModifier(final int countToRemove) {
            Preconditions.checkArgument(countToRemove > 0);
            this.countToRemove = countToRemove;
        }
        
        int newCount(final int oldCount) {
            return Math.max(0, oldCount - this.countToRemove);
        }
    }
    
    private final class SetCountModifier extends MultisetModifier
    {
        private final int countToSet;
        
        private SetCountModifier(final int countToSet) {
            Preconditions.checkArgument(countToSet >= 0);
            this.countToSet = countToSet;
        }
        
        int newCount(final int oldCount) {
            return this.countToSet;
        }
    }
    
    private final class ConditionalSetCountModifier extends MultisetModifier
    {
        private final int expectedCount;
        private final int setCount;
        
        private ConditionalSetCountModifier(final int expectedCount, final int setCount) {
            Preconditions.checkArgument(setCount >= 0 & expectedCount >= 0);
            this.expectedCount = expectedCount;
            this.setCount = setCount;
        }
        
        int newCount(final int oldCount) {
            return (oldCount == this.expectedCount) ? this.setCount : oldCount;
        }
    }
}
