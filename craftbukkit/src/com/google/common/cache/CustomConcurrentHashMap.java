// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.cache;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.NoSuchElementException;
import com.google.common.collect.AbstractLinkedIterator;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.lang.ref.Reference;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.ref.WeakReference;
import java.lang.ref.SoftReference;
import com.google.common.base.Equivalences;
import com.google.common.collect.Iterators;
import java.util.AbstractQueue;
import java.lang.ref.ReferenceQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.ExecutionException;
import com.google.common.primitives.Ints;
import java.util.logging.Level;
import com.google.common.annotations.VisibleForTesting;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.Map;
import java.util.Collection;
import java.util.Set;
import com.google.common.base.Ticker;
import java.util.Queue;
import com.google.common.base.Equivalence;
import java.util.logging.Logger;
import java.util.concurrent.ConcurrentMap;
import java.util.AbstractMap;

class CustomConcurrentHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>
{
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_SEGMENTS = 65536;
    static final int CONTAINS_VALUE_RETRIES = 3;
    static final int DRAIN_THRESHOLD = 63;
    static final int DRAIN_MAX = 16;
    private static final Logger logger;
    final int segmentMask;
    final int segmentShift;
    final Segment<K, V>[] segments;
    final CacheLoader<? super K, V> loader;
    final int concurrencyLevel;
    final Equivalence<Object> keyEquivalence;
    final Equivalence<Object> valueEquivalence;
    final Strength keyStrength;
    final Strength valueStrength;
    final int maximumSize;
    final long expireAfterAccessNanos;
    final long expireAfterWriteNanos;
    final Queue<RemovalNotification<K, V>> removalNotificationQueue;
    final RemovalListener<K, V> removalListener;
    final EntryFactory entryFactory;
    final Ticker ticker;
    static final ValueReference<Object, Object> UNSET;
    static final Queue<?> DISCARDING_QUEUE;
    Set<K> keySet;
    Collection<V> values;
    Set<Map.Entry<K, V>> entrySet;
    
    CustomConcurrentHashMap(final CacheBuilder<? super K, ? super V> builder, final Supplier<? extends AbstractCache.StatsCounter> statsCounterSupplier, final CacheLoader<? super K, V> loader) {
        this.loader = Preconditions.checkNotNull(loader);
        this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
        this.keyStrength = builder.getKeyStrength();
        this.valueStrength = builder.getValueStrength();
        this.keyEquivalence = builder.getKeyEquivalence();
        this.valueEquivalence = builder.getValueEquivalence();
        this.maximumSize = builder.maximumSize;
        this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
        this.expireAfterWriteNanos = builder.getExpireAfterWriteNanos();
        this.entryFactory = EntryFactory.getFactory(this.keyStrength, this.expires(), this.evictsBySize());
        this.ticker = builder.getTicker();
        this.removalListener = builder.getRemovalListener();
        this.removalNotificationQueue = ((this.removalListener == CacheBuilder.NullListener.INSTANCE) ? discardingQueue() : new ConcurrentLinkedQueue<RemovalNotification<K, V>>());
        int initialCapacity = Math.min(builder.getInitialCapacity(), 1073741824);
        if (this.evictsBySize()) {
            initialCapacity = Math.min(initialCapacity, this.maximumSize);
        }
        int segmentShift = 0;
        int segmentCount;
        for (segmentCount = 1; segmentCount < this.concurrencyLevel && (!this.evictsBySize() || segmentCount * 2 <= this.maximumSize); segmentCount <<= 1) {
            ++segmentShift;
        }
        this.segmentShift = 32 - segmentShift;
        this.segmentMask = segmentCount - 1;
        this.segments = this.newSegmentArray(segmentCount);
        int segmentCapacity = initialCapacity / segmentCount;
        if (segmentCapacity * segmentCount < initialCapacity) {
            ++segmentCapacity;
        }
        int segmentSize;
        for (segmentSize = 1; segmentSize < segmentCapacity; segmentSize <<= 1) {}
        if (this.evictsBySize()) {
            int maximumSegmentSize = this.maximumSize / segmentCount + 1;
            final int remainder = this.maximumSize % segmentCount;
            for (int i = 0; i < this.segments.length; ++i) {
                if (i == remainder) {
                    --maximumSegmentSize;
                }
                this.segments[i] = this.createSegment(segmentSize, maximumSegmentSize, (AbstractCache.StatsCounter)statsCounterSupplier.get());
            }
        }
        else {
            for (int j = 0; j < this.segments.length; ++j) {
                this.segments[j] = this.createSegment(segmentSize, -1, (AbstractCache.StatsCounter)statsCounterSupplier.get());
            }
        }
    }
    
    boolean evictsBySize() {
        return this.maximumSize != -1;
    }
    
    boolean expires() {
        return this.expiresAfterWrite() || this.expiresAfterAccess();
    }
    
    boolean expiresAfterWrite() {
        return this.expireAfterWriteNanos > 0L;
    }
    
    boolean expiresAfterAccess() {
        return this.expireAfterAccessNanos > 0L;
    }
    
    boolean usesKeyReferences() {
        return this.keyStrength != Strength.STRONG;
    }
    
    boolean usesValueReferences() {
        return this.valueStrength != Strength.STRONG;
    }
    
    static <K, V> ValueReference<K, V> unset() {
        return (ValueReference<K, V>)CustomConcurrentHashMap.UNSET;
    }
    
    static <K, V> ReferenceEntry<K, V> nullEntry() {
        return (ReferenceEntry<K, V>)NullEntry.INSTANCE;
    }
    
    static <E> Queue<E> discardingQueue() {
        return (Queue<E>)CustomConcurrentHashMap.DISCARDING_QUEUE;
    }
    
    static int rehash(int h) {
        h += (h << 15 ^ 0xFFFFCD7D);
        h ^= h >>> 10;
        h += h << 3;
        h ^= h >>> 6;
        h += (h << 2) + (h << 14);
        return h ^ h >>> 16;
    }
    
    @GuardedBy("Segment.this")
    @VisibleForTesting
    ReferenceEntry<K, V> newEntry(final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
        return this.segmentFor(hash).newEntry(key, hash, next);
    }
    
    @GuardedBy("Segment.this")
    @VisibleForTesting
    ReferenceEntry<K, V> copyEntry(final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newNext) {
        final int hash = original.getHash();
        return this.segmentFor(hash).copyEntry(original, newNext);
    }
    
    @GuardedBy("Segment.this")
    @VisibleForTesting
    ValueReference<K, V> newValueReference(final ReferenceEntry<K, V> entry, final V value) {
        final int hash = entry.getHash();
        return this.valueStrength.referenceValue(this.segmentFor(hash), entry, value);
    }
    
    int hash(final Object key) {
        final int h = this.keyEquivalence.hash(key);
        return rehash(h);
    }
    
    void reclaimValue(final ValueReference<K, V> valueReference) {
        final ReferenceEntry<K, V> entry = valueReference.getEntry();
        final int hash = entry.getHash();
        this.segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
    }
    
    void reclaimKey(final ReferenceEntry<K, V> entry) {
        final int hash = entry.getHash();
        this.segmentFor(hash).reclaimKey(entry, hash);
    }
    
    @VisibleForTesting
    boolean isLive(final ReferenceEntry<K, V> entry) {
        return this.segmentFor(entry.getHash()).getLiveValue(entry) != null;
    }
    
    Segment<K, V> segmentFor(final int hash) {
        return this.segments[hash >>> this.segmentShift & this.segmentMask];
    }
    
    Segment<K, V> createSegment(final int initialCapacity, final int maxSegmentSize, final AbstractCache.StatsCounter statsCounter) {
        return new Segment<K, V>(this, initialCapacity, maxSegmentSize, statsCounter);
    }
    
    V getLiveValue(final ReferenceEntry<K, V> entry) {
        if (entry.getKey() == null) {
            return null;
        }
        final V value = entry.getValueReference().get();
        if (value == null) {
            return null;
        }
        if (this.expires() && this.isExpired(entry)) {
            return null;
        }
        return value;
    }
    
    boolean isExpired(final ReferenceEntry<K, V> entry) {
        return this.isExpired(entry, this.ticker.read());
    }
    
    boolean isExpired(final ReferenceEntry<K, V> entry, final long now) {
        return now - entry.getExpirationTime() > 0L;
    }
    
    @GuardedBy("Segment.this")
    static <K, V> void connectExpirables(final ReferenceEntry<K, V> previous, final ReferenceEntry<K, V> next) {
        previous.setNextExpirable(next);
        next.setPreviousExpirable(previous);
    }
    
    @GuardedBy("Segment.this")
    static <K, V> void nullifyExpirable(final ReferenceEntry<K, V> nulled) {
        final ReferenceEntry<K, V> nullEntry = nullEntry();
        nulled.setNextExpirable(nullEntry);
        nulled.setPreviousExpirable(nullEntry);
    }
    
    void processPendingNotifications() {
        RemovalNotification<K, V> notification;
        while ((notification = this.removalNotificationQueue.poll()) != null) {
            try {
                this.removalListener.onRemoval(notification);
            }
            catch (Exception e) {
                CustomConcurrentHashMap.logger.log(Level.WARNING, "Exception thrown by removal listener", e);
            }
        }
    }
    
    @GuardedBy("Segment.this")
    static <K, V> void connectEvictables(final ReferenceEntry<K, V> previous, final ReferenceEntry<K, V> next) {
        previous.setNextEvictable(next);
        next.setPreviousEvictable(previous);
    }
    
    @GuardedBy("Segment.this")
    static <K, V> void nullifyEvictable(final ReferenceEntry<K, V> nulled) {
        final ReferenceEntry<K, V> nullEntry = nullEntry();
        nulled.setNextEvictable(nullEntry);
        nulled.setPreviousEvictable(nullEntry);
    }
    
    final Segment<K, V>[] newSegmentArray(final int ssize) {
        return (Segment<K, V>[])new Segment[ssize];
    }
    
    public void cleanUp() {
        for (final Segment<?, ?> segment : this.segments) {
            segment.cleanUp();
        }
    }
    
    public boolean isEmpty() {
        long sum = 0L;
        final Segment<K, V>[] segments = this.segments;
        for (int i = 0; i < segments.length; ++i) {
            if (segments[i].count != 0) {
                return false;
            }
            sum += segments[i].modCount;
        }
        if (sum != 0L) {
            for (int i = 0; i < segments.length; ++i) {
                if (segments[i].count != 0) {
                    return false;
                }
                sum -= segments[i].modCount;
            }
            if (sum != 0L) {
                return false;
            }
        }
        return true;
    }
    
    long longSize() {
        final Segment<K, V>[] segments = this.segments;
        long sum = 0L;
        for (int i = 0; i < segments.length; ++i) {
            sum += segments[i].count;
        }
        return sum;
    }
    
    public int size() {
        return Ints.saturatedCast(this.longSize());
    }
    
    public V get(@Nullable final Object key) {
        if (key == null) {
            return null;
        }
        final int hash = this.hash(key);
        return this.segmentFor(hash).get(key, hash);
    }
    
    V getOrCompute(final K key) throws ExecutionException {
        final int hash = this.hash(Preconditions.checkNotNull(key));
        return this.segmentFor(hash).getOrCompute(key, hash, this.loader);
    }
    
    ReferenceEntry<K, V> getEntry(@Nullable final Object key) {
        if (key == null) {
            return null;
        }
        final int hash = this.hash(key);
        return this.segmentFor(hash).getEntry(key, hash);
    }
    
    ReferenceEntry<K, V> getLiveEntry(@Nullable final Object key) {
        if (key == null) {
            return null;
        }
        final int hash = this.hash(key);
        return this.segmentFor(hash).getLiveEntry(key, hash);
    }
    
    public boolean containsKey(@Nullable final Object key) {
        if (key == null) {
            return false;
        }
        final int hash = this.hash(key);
        return this.segmentFor(hash).containsKey(key, hash);
    }
    
    public boolean containsValue(@Nullable final Object value) {
        if (value == null) {
            return false;
        }
        final Segment<K, V>[] segments = this.segments;
        long last = -1L;
        for (int i = 0; i < 3; ++i) {
            long sum = 0L;
            for (final Segment<K, V> segment : segments) {
                final int c = segment.count;
                final AtomicReferenceArray<ReferenceEntry<K, V>> table = segment.table;
                for (int j = 0; j < table.length(); ++j) {
                    for (ReferenceEntry<K, V> e = table.get(j); e != null; e = e.getNext()) {
                        final V v = segment.getLiveValue(e);
                        if (v != null && this.valueEquivalence.equivalent(value, v)) {
                            return true;
                        }
                    }
                }
                sum += segment.modCount;
            }
            if (sum == last) {
                break;
            }
            last = sum;
        }
        return false;
    }
    
    public V put(final K key, final V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        final int hash = this.hash(key);
        return this.segmentFor(hash).put(key, hash, value, false);
    }
    
    public V putIfAbsent(final K key, final V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        final int hash = this.hash(key);
        return this.segmentFor(hash).put(key, hash, value, true);
    }
    
    public void putAll(final Map<? extends K, ? extends V> m) {
        for (final Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            this.put(e.getKey(), e.getValue());
        }
    }
    
    public V remove(@Nullable final Object key) {
        if (key == null) {
            return null;
        }
        final int hash = this.hash(key);
        return this.segmentFor(hash).remove(key, hash);
    }
    
    public boolean remove(@Nullable final Object key, @Nullable final Object value) {
        if (key == null || value == null) {
            return false;
        }
        final int hash = this.hash(key);
        return this.segmentFor(hash).remove(key, hash, value);
    }
    
    public boolean replace(final K key, @Nullable final V oldValue, final V newValue) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(newValue);
        if (oldValue == null) {
            return false;
        }
        final int hash = this.hash(key);
        return this.segmentFor(hash).replace(key, hash, oldValue, newValue);
    }
    
    public V replace(final K key, final V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        final int hash = this.hash(key);
        return this.segmentFor(hash).replace(key, hash, value);
    }
    
    public void clear() {
        for (final Segment<K, V> segment : this.segments) {
            segment.clear();
        }
    }
    
    public Set<K> keySet() {
        final Set<K> ks = this.keySet;
        return (ks != null) ? ks : (this.keySet = new KeySet());
    }
    
    public Collection<V> values() {
        final Collection<V> vs = this.values;
        return (vs != null) ? vs : (this.values = new Values());
    }
    
    public Set<Map.Entry<K, V>> entrySet() {
        final Set<Map.Entry<K, V>> es = this.entrySet;
        return (es != null) ? es : (this.entrySet = new EntrySet());
    }
    
    Cache<K, V> cacheSerializationProxy() {
        return new SerializationProxy<K, V>(this.loader, this.keyStrength, this.valueStrength, this.keyEquivalence, this.valueEquivalence, this.expireAfterWriteNanos, this.expireAfterAccessNanos, this.maximumSize, this.concurrencyLevel, this.removalListener, this.ticker);
    }
    
    static {
        logger = Logger.getLogger(CustomConcurrentHashMap.class.getName());
        UNSET = new ValueReference<Object, Object>() {
            public Object get() {
                return null;
            }
            
            public ReferenceEntry<Object, Object> getEntry() {
                return null;
            }
            
            public ValueReference<Object, Object> copyFor(final ReferenceQueue<Object> queue, final ReferenceEntry<Object, Object> entry) {
                return this;
            }
            
            public boolean isComputingReference() {
                return false;
            }
            
            public Object waitForValue() {
                return null;
            }
            
            public void notifyNewValue(final Object newValue) {
            }
        };
        DISCARDING_QUEUE = new AbstractQueue<Object>() {
            public boolean offer(final Object o) {
                return true;
            }
            
            public Object peek() {
                return null;
            }
            
            public Object poll() {
                return null;
            }
            
            public int size() {
                return 0;
            }
            
            public Iterator<Object> iterator() {
                return Iterators.emptyIterator();
            }
        };
    }
    
    enum Strength
    {
        STRONG {
             <K, V> ValueReference<K, V> referenceValue(final Segment<K, V> segment, final ReferenceEntry<K, V> entry, final V value) {
                return new StrongValueReference<K, V>(value);
            }
            
            Equivalence<Object> defaultEquivalence() {
                return Equivalences.equals();
            }
        }, 
        SOFT {
             <K, V> ValueReference<K, V> referenceValue(final Segment<K, V> segment, final ReferenceEntry<K, V> entry, final V value) {
                return new SoftValueReference<K, V>(segment.valueReferenceQueue, value, entry);
            }
            
            Equivalence<Object> defaultEquivalence() {
                return Equivalences.identity();
            }
        }, 
        WEAK {
             <K, V> ValueReference<K, V> referenceValue(final Segment<K, V> segment, final ReferenceEntry<K, V> entry, final V value) {
                return new WeakValueReference<K, V>(segment.valueReferenceQueue, value, entry);
            }
            
            Equivalence<Object> defaultEquivalence() {
                return Equivalences.identity();
            }
        };
        
        abstract <K, V> ValueReference<K, V> referenceValue(final Segment<K, V> p0, final ReferenceEntry<K, V> p1, final V p2);
        
        abstract Equivalence<Object> defaultEquivalence();
    }
    
    enum EntryFactory
    {
        STRONG {
             <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> segment, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
                return new StrongEntry<K, V>(key, hash, next);
            }
        }, 
        STRONG_EXPIRABLE {
             <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> segment, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
                return new StrongExpirableEntry<K, V>(key, hash, next);
            }
            
             <K, V> ReferenceEntry<K, V> copyEntry(final Segment<K, V> segment, final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newNext) {
                final ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyExpirableEntry(original, newEntry);
                return newEntry;
            }
        }, 
        STRONG_EVICTABLE {
             <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> segment, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
                return new StrongEvictableEntry<K, V>(key, hash, next);
            }
            
             <K, V> ReferenceEntry<K, V> copyEntry(final Segment<K, V> segment, final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newNext) {
                final ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyEvictableEntry(original, newEntry);
                return newEntry;
            }
        }, 
        STRONG_EXPIRABLE_EVICTABLE {
             <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> segment, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
                return new StrongExpirableEvictableEntry<K, V>(key, hash, next);
            }
            
             <K, V> ReferenceEntry<K, V> copyEntry(final Segment<K, V> segment, final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newNext) {
                final ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyExpirableEntry(original, newEntry);
                this.copyEvictableEntry(original, newEntry);
                return newEntry;
            }
        }, 
        SOFT {
             <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> segment, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
                return new SoftEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
            }
        }, 
        SOFT_EXPIRABLE {
             <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> segment, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
                return new SoftExpirableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
            }
            
             <K, V> ReferenceEntry<K, V> copyEntry(final Segment<K, V> segment, final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newNext) {
                final ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyExpirableEntry(original, newEntry);
                return newEntry;
            }
        }, 
        SOFT_EVICTABLE {
             <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> segment, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
                return new SoftEvictableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
            }
            
             <K, V> ReferenceEntry<K, V> copyEntry(final Segment<K, V> segment, final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newNext) {
                final ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyEvictableEntry(original, newEntry);
                return newEntry;
            }
        }, 
        SOFT_EXPIRABLE_EVICTABLE {
             <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> segment, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
                return new SoftExpirableEvictableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
            }
            
             <K, V> ReferenceEntry<K, V> copyEntry(final Segment<K, V> segment, final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newNext) {
                final ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyExpirableEntry(original, newEntry);
                this.copyEvictableEntry(original, newEntry);
                return newEntry;
            }
        }, 
        WEAK {
             <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> segment, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
                return new WeakEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
            }
        }, 
        WEAK_EXPIRABLE {
             <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> segment, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
                return new WeakExpirableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
            }
            
             <K, V> ReferenceEntry<K, V> copyEntry(final Segment<K, V> segment, final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newNext) {
                final ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyExpirableEntry(original, newEntry);
                return newEntry;
            }
        }, 
        WEAK_EVICTABLE {
             <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> segment, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
                return new WeakEvictableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
            }
            
             <K, V> ReferenceEntry<K, V> copyEntry(final Segment<K, V> segment, final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newNext) {
                final ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyEvictableEntry(original, newEntry);
                return newEntry;
            }
        }, 
        WEAK_EXPIRABLE_EVICTABLE {
             <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> segment, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
                return new WeakExpirableEvictableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
            }
            
             <K, V> ReferenceEntry<K, V> copyEntry(final Segment<K, V> segment, final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newNext) {
                final ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyExpirableEntry(original, newEntry);
                this.copyEvictableEntry(original, newEntry);
                return newEntry;
            }
        };
        
        static final int EXPIRABLE_MASK = 1;
        static final int EVICTABLE_MASK = 2;
        static final EntryFactory[][] factories;
        
        static EntryFactory getFactory(final Strength keyStrength, final boolean expireAfterWrite, final boolean evictsBySize) {
            final int flags = (expireAfterWrite ? 1 : 0) | (evictsBySize ? 2 : 0);
            return EntryFactory.factories[keyStrength.ordinal()][flags];
        }
        
        abstract <K, V> ReferenceEntry<K, V> newEntry(final Segment<K, V> p0, final K p1, final int p2, @Nullable final ReferenceEntry<K, V> p3);
        
        @GuardedBy("Segment.this")
         <K, V> ReferenceEntry<K, V> copyEntry(final Segment<K, V> segment, final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newNext) {
            return this.newEntry(segment, original.getKey(), original.getHash(), newNext);
        }
        
        @GuardedBy("Segment.this")
         <K, V> void copyExpirableEntry(final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newEntry) {
            newEntry.setExpirationTime(original.getExpirationTime());
            CustomConcurrentHashMap.connectExpirables(original.getPreviousExpirable(), newEntry);
            CustomConcurrentHashMap.connectExpirables(newEntry, original.getNextExpirable());
            CustomConcurrentHashMap.nullifyExpirable(original);
        }
        
        @GuardedBy("Segment.this")
         <K, V> void copyEvictableEntry(final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newEntry) {
            CustomConcurrentHashMap.connectEvictables(original.getPreviousEvictable(), newEntry);
            CustomConcurrentHashMap.connectEvictables(newEntry, original.getNextEvictable());
            CustomConcurrentHashMap.nullifyEvictable(original);
        }
        
        static {
            factories = new EntryFactory[][] { { EntryFactory.STRONG, EntryFactory.STRONG_EXPIRABLE, EntryFactory.STRONG_EVICTABLE, EntryFactory.STRONG_EXPIRABLE_EVICTABLE }, { EntryFactory.SOFT, EntryFactory.SOFT_EXPIRABLE, EntryFactory.SOFT_EVICTABLE, EntryFactory.SOFT_EXPIRABLE_EVICTABLE }, { EntryFactory.WEAK, EntryFactory.WEAK_EXPIRABLE, EntryFactory.WEAK_EVICTABLE, EntryFactory.WEAK_EXPIRABLE_EVICTABLE } };
        }
    }
    
    private enum NullEntry implements ReferenceEntry<Object, Object>
    {
        INSTANCE;
        
        public ValueReference<Object, Object> getValueReference() {
            return null;
        }
        
        public void setValueReference(final ValueReference<Object, Object> valueReference) {
        }
        
        public ReferenceEntry<Object, Object> getNext() {
            return null;
        }
        
        public int getHash() {
            return 0;
        }
        
        public Object getKey() {
            return null;
        }
        
        public long getExpirationTime() {
            return 0L;
        }
        
        public void setExpirationTime(final long time) {
        }
        
        public ReferenceEntry<Object, Object> getNextExpirable() {
            return this;
        }
        
        public void setNextExpirable(final ReferenceEntry<Object, Object> next) {
        }
        
        public ReferenceEntry<Object, Object> getPreviousExpirable() {
            return this;
        }
        
        public void setPreviousExpirable(final ReferenceEntry<Object, Object> previous) {
        }
        
        public ReferenceEntry<Object, Object> getNextEvictable() {
            return this;
        }
        
        public void setNextEvictable(final ReferenceEntry<Object, Object> next) {
        }
        
        public ReferenceEntry<Object, Object> getPreviousEvictable() {
            return this;
        }
        
        public void setPreviousEvictable(final ReferenceEntry<Object, Object> previous) {
        }
    }
    
    abstract static class AbstractReferenceEntry<K, V> implements ReferenceEntry<K, V>
    {
        public ValueReference<K, V> getValueReference() {
            throw new UnsupportedOperationException();
        }
        
        public void setValueReference(final ValueReference<K, V> valueReference) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getNext() {
            throw new UnsupportedOperationException();
        }
        
        public int getHash() {
            throw new UnsupportedOperationException();
        }
        
        public K getKey() {
            throw new UnsupportedOperationException();
        }
        
        public long getExpirationTime() {
            throw new UnsupportedOperationException();
        }
        
        public void setExpirationTime(final long time) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getNextExpirable() {
            throw new UnsupportedOperationException();
        }
        
        public void setNextExpirable(final ReferenceEntry<K, V> next) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getPreviousExpirable() {
            throw new UnsupportedOperationException();
        }
        
        public void setPreviousExpirable(final ReferenceEntry<K, V> previous) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getNextEvictable() {
            throw new UnsupportedOperationException();
        }
        
        public void setNextEvictable(final ReferenceEntry<K, V> next) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getPreviousEvictable() {
            throw new UnsupportedOperationException();
        }
        
        public void setPreviousEvictable(final ReferenceEntry<K, V> previous) {
            throw new UnsupportedOperationException();
        }
    }
    
    static class StrongEntry<K, V> implements ReferenceEntry<K, V>
    {
        final K key;
        final int hash;
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference;
        
        StrongEntry(final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            this.valueReference = CustomConcurrentHashMap.unset();
            this.key = key;
            this.hash = hash;
            this.next = next;
        }
        
        public K getKey() {
            return this.key;
        }
        
        public long getExpirationTime() {
            throw new UnsupportedOperationException();
        }
        
        public void setExpirationTime(final long time) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getNextExpirable() {
            throw new UnsupportedOperationException();
        }
        
        public void setNextExpirable(final ReferenceEntry<K, V> next) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getPreviousExpirable() {
            throw new UnsupportedOperationException();
        }
        
        public void setPreviousExpirable(final ReferenceEntry<K, V> previous) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getNextEvictable() {
            throw new UnsupportedOperationException();
        }
        
        public void setNextEvictable(final ReferenceEntry<K, V> next) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getPreviousEvictable() {
            throw new UnsupportedOperationException();
        }
        
        public void setPreviousEvictable(final ReferenceEntry<K, V> previous) {
            throw new UnsupportedOperationException();
        }
        
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }
        
        public void setValueReference(final ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }
        
        public int getHash() {
            return this.hash;
        }
        
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }
    }
    
    static final class StrongExpirableEntry<K, V> extends StrongEntry<K, V> implements ReferenceEntry<K, V>
    {
        volatile long time;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousExpirable;
        
        StrongExpirableEntry(final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            super(key, hash, next);
            this.time = Long.MAX_VALUE;
            this.nextExpirable = CustomConcurrentHashMap.nullEntry();
            this.previousExpirable = CustomConcurrentHashMap.nullEntry();
        }
        
        public long getExpirationTime() {
            return this.time;
        }
        
        public void setExpirationTime(final long time) {
            this.time = time;
        }
        
        public ReferenceEntry<K, V> getNextExpirable() {
            return this.nextExpirable;
        }
        
        public void setNextExpirable(final ReferenceEntry<K, V> next) {
            this.nextExpirable = next;
        }
        
        public ReferenceEntry<K, V> getPreviousExpirable() {
            return this.previousExpirable;
        }
        
        public void setPreviousExpirable(final ReferenceEntry<K, V> previous) {
            this.previousExpirable = previous;
        }
    }
    
    static final class StrongEvictableEntry<K, V> extends StrongEntry<K, V> implements ReferenceEntry<K, V>
    {
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextEvictable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousEvictable;
        
        StrongEvictableEntry(final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            super(key, hash, next);
            this.nextEvictable = CustomConcurrentHashMap.nullEntry();
            this.previousEvictable = CustomConcurrentHashMap.nullEntry();
        }
        
        public ReferenceEntry<K, V> getNextEvictable() {
            return this.nextEvictable;
        }
        
        public void setNextEvictable(final ReferenceEntry<K, V> next) {
            this.nextEvictable = next;
        }
        
        public ReferenceEntry<K, V> getPreviousEvictable() {
            return this.previousEvictable;
        }
        
        public void setPreviousEvictable(final ReferenceEntry<K, V> previous) {
            this.previousEvictable = previous;
        }
    }
    
    static final class StrongExpirableEvictableEntry<K, V> extends StrongEntry<K, V> implements ReferenceEntry<K, V>
    {
        volatile long time;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextEvictable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousEvictable;
        
        StrongExpirableEvictableEntry(final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            super(key, hash, next);
            this.time = Long.MAX_VALUE;
            this.nextExpirable = CustomConcurrentHashMap.nullEntry();
            this.previousExpirable = CustomConcurrentHashMap.nullEntry();
            this.nextEvictable = CustomConcurrentHashMap.nullEntry();
            this.previousEvictable = CustomConcurrentHashMap.nullEntry();
        }
        
        public long getExpirationTime() {
            return this.time;
        }
        
        public void setExpirationTime(final long time) {
            this.time = time;
        }
        
        public ReferenceEntry<K, V> getNextExpirable() {
            return this.nextExpirable;
        }
        
        public void setNextExpirable(final ReferenceEntry<K, V> next) {
            this.nextExpirable = next;
        }
        
        public ReferenceEntry<K, V> getPreviousExpirable() {
            return this.previousExpirable;
        }
        
        public void setPreviousExpirable(final ReferenceEntry<K, V> previous) {
            this.previousExpirable = previous;
        }
        
        public ReferenceEntry<K, V> getNextEvictable() {
            return this.nextEvictable;
        }
        
        public void setNextEvictable(final ReferenceEntry<K, V> next) {
            this.nextEvictable = next;
        }
        
        public ReferenceEntry<K, V> getPreviousEvictable() {
            return this.previousEvictable;
        }
        
        public void setPreviousEvictable(final ReferenceEntry<K, V> previous) {
            this.previousEvictable = previous;
        }
    }
    
    static class SoftEntry<K, V> extends SoftReference<K> implements ReferenceEntry<K, V>
    {
        final int hash;
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference;
        
        SoftEntry(final ReferenceQueue<K> queue, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            super(key, queue);
            this.valueReference = CustomConcurrentHashMap.unset();
            this.hash = hash;
            this.next = next;
        }
        
        public K getKey() {
            return this.get();
        }
        
        public long getExpirationTime() {
            throw new UnsupportedOperationException();
        }
        
        public void setExpirationTime(final long time) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getNextExpirable() {
            throw new UnsupportedOperationException();
        }
        
        public void setNextExpirable(final ReferenceEntry<K, V> next) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getPreviousExpirable() {
            throw new UnsupportedOperationException();
        }
        
        public void setPreviousExpirable(final ReferenceEntry<K, V> previous) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getNextEvictable() {
            throw new UnsupportedOperationException();
        }
        
        public void setNextEvictable(final ReferenceEntry<K, V> next) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getPreviousEvictable() {
            throw new UnsupportedOperationException();
        }
        
        public void setPreviousEvictable(final ReferenceEntry<K, V> previous) {
            throw new UnsupportedOperationException();
        }
        
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }
        
        public void setValueReference(final ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }
        
        public int getHash() {
            return this.hash;
        }
        
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }
    }
    
    static final class SoftExpirableEntry<K, V> extends SoftEntry<K, V> implements ReferenceEntry<K, V>
    {
        volatile long time;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousExpirable;
        
        SoftExpirableEntry(final ReferenceQueue<K> queue, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
            this.time = Long.MAX_VALUE;
            this.nextExpirable = CustomConcurrentHashMap.nullEntry();
            this.previousExpirable = CustomConcurrentHashMap.nullEntry();
        }
        
        public long getExpirationTime() {
            return this.time;
        }
        
        public void setExpirationTime(final long time) {
            this.time = time;
        }
        
        public ReferenceEntry<K, V> getNextExpirable() {
            return this.nextExpirable;
        }
        
        public void setNextExpirable(final ReferenceEntry<K, V> next) {
            this.nextExpirable = next;
        }
        
        public ReferenceEntry<K, V> getPreviousExpirable() {
            return this.previousExpirable;
        }
        
        public void setPreviousExpirable(final ReferenceEntry<K, V> previous) {
            this.previousExpirable = previous;
        }
    }
    
    static final class SoftEvictableEntry<K, V> extends SoftEntry<K, V> implements ReferenceEntry<K, V>
    {
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextEvictable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousEvictable;
        
        SoftEvictableEntry(final ReferenceQueue<K> queue, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
            this.nextEvictable = CustomConcurrentHashMap.nullEntry();
            this.previousEvictable = CustomConcurrentHashMap.nullEntry();
        }
        
        public ReferenceEntry<K, V> getNextEvictable() {
            return this.nextEvictable;
        }
        
        public void setNextEvictable(final ReferenceEntry<K, V> next) {
            this.nextEvictable = next;
        }
        
        public ReferenceEntry<K, V> getPreviousEvictable() {
            return this.previousEvictable;
        }
        
        public void setPreviousEvictable(final ReferenceEntry<K, V> previous) {
            this.previousEvictable = previous;
        }
    }
    
    static final class SoftExpirableEvictableEntry<K, V> extends SoftEntry<K, V> implements ReferenceEntry<K, V>
    {
        volatile long time;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextEvictable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousEvictable;
        
        SoftExpirableEvictableEntry(final ReferenceQueue<K> queue, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
            this.time = Long.MAX_VALUE;
            this.nextExpirable = CustomConcurrentHashMap.nullEntry();
            this.previousExpirable = CustomConcurrentHashMap.nullEntry();
            this.nextEvictable = CustomConcurrentHashMap.nullEntry();
            this.previousEvictable = CustomConcurrentHashMap.nullEntry();
        }
        
        public long getExpirationTime() {
            return this.time;
        }
        
        public void setExpirationTime(final long time) {
            this.time = time;
        }
        
        public ReferenceEntry<K, V> getNextExpirable() {
            return this.nextExpirable;
        }
        
        public void setNextExpirable(final ReferenceEntry<K, V> next) {
            this.nextExpirable = next;
        }
        
        public ReferenceEntry<K, V> getPreviousExpirable() {
            return this.previousExpirable;
        }
        
        public void setPreviousExpirable(final ReferenceEntry<K, V> previous) {
            this.previousExpirable = previous;
        }
        
        public ReferenceEntry<K, V> getNextEvictable() {
            return this.nextEvictable;
        }
        
        public void setNextEvictable(final ReferenceEntry<K, V> next) {
            this.nextEvictable = next;
        }
        
        public ReferenceEntry<K, V> getPreviousEvictable() {
            return this.previousEvictable;
        }
        
        public void setPreviousEvictable(final ReferenceEntry<K, V> previous) {
            this.previousEvictable = previous;
        }
    }
    
    static class WeakEntry<K, V> extends WeakReference<K> implements ReferenceEntry<K, V>
    {
        final int hash;
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference;
        
        WeakEntry(final ReferenceQueue<K> queue, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            super(key, queue);
            this.valueReference = CustomConcurrentHashMap.unset();
            this.hash = hash;
            this.next = next;
        }
        
        public K getKey() {
            return this.get();
        }
        
        public long getExpirationTime() {
            throw new UnsupportedOperationException();
        }
        
        public void setExpirationTime(final long time) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getNextExpirable() {
            throw new UnsupportedOperationException();
        }
        
        public void setNextExpirable(final ReferenceEntry<K, V> next) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getPreviousExpirable() {
            throw new UnsupportedOperationException();
        }
        
        public void setPreviousExpirable(final ReferenceEntry<K, V> previous) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getNextEvictable() {
            throw new UnsupportedOperationException();
        }
        
        public void setNextEvictable(final ReferenceEntry<K, V> next) {
            throw new UnsupportedOperationException();
        }
        
        public ReferenceEntry<K, V> getPreviousEvictable() {
            throw new UnsupportedOperationException();
        }
        
        public void setPreviousEvictable(final ReferenceEntry<K, V> previous) {
            throw new UnsupportedOperationException();
        }
        
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }
        
        public void setValueReference(final ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }
        
        public int getHash() {
            return this.hash;
        }
        
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }
    }
    
    static final class WeakExpirableEntry<K, V> extends WeakEntry<K, V> implements ReferenceEntry<K, V>
    {
        volatile long time;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousExpirable;
        
        WeakExpirableEntry(final ReferenceQueue<K> queue, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
            this.time = Long.MAX_VALUE;
            this.nextExpirable = CustomConcurrentHashMap.nullEntry();
            this.previousExpirable = CustomConcurrentHashMap.nullEntry();
        }
        
        public long getExpirationTime() {
            return this.time;
        }
        
        public void setExpirationTime(final long time) {
            this.time = time;
        }
        
        public ReferenceEntry<K, V> getNextExpirable() {
            return this.nextExpirable;
        }
        
        public void setNextExpirable(final ReferenceEntry<K, V> next) {
            this.nextExpirable = next;
        }
        
        public ReferenceEntry<K, V> getPreviousExpirable() {
            return this.previousExpirable;
        }
        
        public void setPreviousExpirable(final ReferenceEntry<K, V> previous) {
            this.previousExpirable = previous;
        }
    }
    
    static final class WeakEvictableEntry<K, V> extends WeakEntry<K, V> implements ReferenceEntry<K, V>
    {
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextEvictable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousEvictable;
        
        WeakEvictableEntry(final ReferenceQueue<K> queue, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
            this.nextEvictable = CustomConcurrentHashMap.nullEntry();
            this.previousEvictable = CustomConcurrentHashMap.nullEntry();
        }
        
        public ReferenceEntry<K, V> getNextEvictable() {
            return this.nextEvictable;
        }
        
        public void setNextEvictable(final ReferenceEntry<K, V> next) {
            this.nextEvictable = next;
        }
        
        public ReferenceEntry<K, V> getPreviousEvictable() {
            return this.previousEvictable;
        }
        
        public void setPreviousEvictable(final ReferenceEntry<K, V> previous) {
            this.previousEvictable = previous;
        }
    }
    
    static final class WeakExpirableEvictableEntry<K, V> extends WeakEntry<K, V> implements ReferenceEntry<K, V>
    {
        volatile long time;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousExpirable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> nextEvictable;
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> previousEvictable;
        
        WeakExpirableEvictableEntry(final ReferenceQueue<K> queue, final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
            this.time = Long.MAX_VALUE;
            this.nextExpirable = CustomConcurrentHashMap.nullEntry();
            this.previousExpirable = CustomConcurrentHashMap.nullEntry();
            this.nextEvictable = CustomConcurrentHashMap.nullEntry();
            this.previousEvictable = CustomConcurrentHashMap.nullEntry();
        }
        
        public long getExpirationTime() {
            return this.time;
        }
        
        public void setExpirationTime(final long time) {
            this.time = time;
        }
        
        public ReferenceEntry<K, V> getNextExpirable() {
            return this.nextExpirable;
        }
        
        public void setNextExpirable(final ReferenceEntry<K, V> next) {
            this.nextExpirable = next;
        }
        
        public ReferenceEntry<K, V> getPreviousExpirable() {
            return this.previousExpirable;
        }
        
        public void setPreviousExpirable(final ReferenceEntry<K, V> previous) {
            this.previousExpirable = previous;
        }
        
        public ReferenceEntry<K, V> getNextEvictable() {
            return this.nextEvictable;
        }
        
        public void setNextEvictable(final ReferenceEntry<K, V> next) {
            this.nextEvictable = next;
        }
        
        public ReferenceEntry<K, V> getPreviousEvictable() {
            return this.previousEvictable;
        }
        
        public void setPreviousEvictable(final ReferenceEntry<K, V> previous) {
            this.previousEvictable = previous;
        }
    }
    
    static final class WeakValueReference<K, V> extends WeakReference<V> implements ValueReference<K, V>
    {
        final ReferenceEntry<K, V> entry;
        
        WeakValueReference(final ReferenceQueue<V> queue, final V referent, final ReferenceEntry<K, V> entry) {
            super(referent, queue);
            this.entry = entry;
        }
        
        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }
        
        public void notifyNewValue(final V newValue) {
            this.clear();
        }
        
        public ValueReference<K, V> copyFor(final ReferenceQueue<V> queue, final ReferenceEntry<K, V> entry) {
            return new WeakValueReference((ReferenceQueue<Object>)queue, this.get(), (ReferenceEntry<Object, Object>)entry);
        }
        
        public boolean isComputingReference() {
            return false;
        }
        
        public V waitForValue() {
            return this.get();
        }
    }
    
    static final class SoftValueReference<K, V> extends SoftReference<V> implements ValueReference<K, V>
    {
        final ReferenceEntry<K, V> entry;
        
        SoftValueReference(final ReferenceQueue<V> queue, final V referent, final ReferenceEntry<K, V> entry) {
            super(referent, queue);
            this.entry = entry;
        }
        
        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }
        
        public void notifyNewValue(final V newValue) {
            this.clear();
        }
        
        public ValueReference<K, V> copyFor(final ReferenceQueue<V> queue, final ReferenceEntry<K, V> entry) {
            return new SoftValueReference((ReferenceQueue<Object>)queue, this.get(), (ReferenceEntry<Object, Object>)entry);
        }
        
        public boolean isComputingReference() {
            return false;
        }
        
        public V waitForValue() {
            return this.get();
        }
    }
    
    static final class StrongValueReference<K, V> implements ValueReference<K, V>
    {
        final V referent;
        
        StrongValueReference(final V referent) {
            this.referent = referent;
        }
        
        public V get() {
            return this.referent;
        }
        
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }
        
        public ValueReference<K, V> copyFor(final ReferenceQueue<V> queue, final ReferenceEntry<K, V> entry) {
            return this;
        }
        
        public boolean isComputingReference() {
            return false;
        }
        
        public V waitForValue() {
            return this.get();
        }
        
        public void notifyNewValue(final V newValue) {
        }
    }
    
    static class Segment<K, V> extends ReentrantLock
    {
        final CustomConcurrentHashMap<K, V> map;
        volatile int count;
        int modCount;
        int threshold;
        volatile AtomicReferenceArray<ReferenceEntry<K, V>> table;
        final int maxSegmentSize;
        final ReferenceQueue<K> keyReferenceQueue;
        final ReferenceQueue<V> valueReferenceQueue;
        final Queue<ReferenceEntry<K, V>> recencyQueue;
        final AtomicInteger readCount;
        @GuardedBy("Segment.this")
        final Queue<ReferenceEntry<K, V>> evictionQueue;
        @GuardedBy("Segment.this")
        final Queue<ReferenceEntry<K, V>> expirationQueue;
        final AbstractCache.StatsCounter statsCounter;
        
        Segment(final CustomConcurrentHashMap<K, V> map, final int initialCapacity, final int maxSegmentSize, final AbstractCache.StatsCounter statsCounter) {
            this.readCount = new AtomicInteger();
            this.map = map;
            this.maxSegmentSize = maxSegmentSize;
            this.statsCounter = statsCounter;
            this.initTable(this.newEntryArray(initialCapacity));
            this.keyReferenceQueue = (map.usesKeyReferences() ? new ReferenceQueue<K>() : null);
            this.valueReferenceQueue = (map.usesValueReferences() ? new ReferenceQueue<V>() : null);
            this.recencyQueue = ((map.evictsBySize() || map.expiresAfterAccess()) ? new ConcurrentLinkedQueue<ReferenceEntry<K, V>>() : CustomConcurrentHashMap.discardingQueue());
            this.evictionQueue = (Queue<ReferenceEntry<K, V>>)(map.evictsBySize() ? new EvictionQueue() : CustomConcurrentHashMap.discardingQueue());
            this.expirationQueue = (Queue<ReferenceEntry<K, V>>)(map.expires() ? new ExpirationQueue() : CustomConcurrentHashMap.discardingQueue());
        }
        
        AtomicReferenceArray<ReferenceEntry<K, V>> newEntryArray(final int size) {
            return new AtomicReferenceArray<ReferenceEntry<K, V>>(size);
        }
        
        void initTable(final AtomicReferenceArray<ReferenceEntry<K, V>> newTable) {
            this.threshold = newTable.length() * 3 / 4;
            if (this.threshold == this.maxSegmentSize) {
                ++this.threshold;
            }
            this.table = newTable;
        }
        
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> newEntry(final K key, final int hash, @Nullable final ReferenceEntry<K, V> next) {
            return this.map.entryFactory.newEntry(this, key, hash, next);
        }
        
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> copyEntry(final ReferenceEntry<K, V> original, final ReferenceEntry<K, V> newNext) {
            final ValueReference<K, V> valueReference = original.getValueReference();
            final ReferenceEntry<K, V> newEntry = this.map.entryFactory.copyEntry(this, original, newNext);
            newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, newEntry));
            return newEntry;
        }
        
        @GuardedBy("Segment.this")
        void setValue(final ReferenceEntry<K, V> entry, final V value) {
            final ValueReference<K, V> previous = entry.getValueReference();
            final ValueReference<K, V> valueReference = this.map.valueStrength.referenceValue(this, entry, value);
            entry.setValueReference(valueReference);
            this.recordWrite(entry);
            previous.notifyNewValue(value);
        }
        
        V getOrCompute(final K key, final int hash, final CacheLoader<? super K, V> loader) throws ExecutionException {
            try {
                V value;
                ReferenceEntry<K, V> e;
                do {
                    e = null;
                    if (this.count != 0) {
                        e = this.getEntry(key, hash);
                        if (e != null) {
                            value = this.getLiveValue(e);
                            if (value != null) {
                                this.recordRead(e);
                                this.statsCounter.recordHit();
                                return value;
                            }
                        }
                    }
                    if (e == null || !e.getValueReference().isComputingReference()) {
                        boolean createNewEntry = true;
                        ComputingValueReference<K, V> computingValueReference = null;
                        this.lock();
                        try {
                            this.preWriteCleanup();
                            final int newCount = this.count - 1;
                            final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                            final int index = hash & table.length() - 1;
                            final ReferenceEntry<K, V> first = e = table.get(index);
                            while (e != null) {
                                final K entryKey = e.getKey();
                                if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                                    final ValueReference<K, V> valueReference = e.getValueReference();
                                    if (valueReference.isComputingReference()) {
                                        createNewEntry = false;
                                        break;
                                    }
                                    final V value2 = e.getValueReference().get();
                                    if (value2 == null) {
                                        this.enqueueNotification(entryKey, hash, value2, RemovalCause.COLLECTED);
                                    }
                                    else {
                                        if (!this.map.expires() || !this.map.isExpired(e)) {
                                            this.recordLockedRead(e);
                                            this.statsCounter.recordHit();
                                            return value2;
                                        }
                                        this.enqueueNotification(entryKey, hash, value2, RemovalCause.EXPIRED);
                                    }
                                    this.evictionQueue.remove(e);
                                    this.expirationQueue.remove(e);
                                    this.count = newCount;
                                    break;
                                }
                                else {
                                    e = e.getNext();
                                }
                            }
                            if (createNewEntry) {
                                computingValueReference = new ComputingValueReference<K, V>(loader);
                                if (e == null) {
                                    e = this.newEntry(key, hash, first);
                                    e.setValueReference(computingValueReference);
                                    table.set(index, e);
                                }
                                else {
                                    e.setValueReference(computingValueReference);
                                }
                            }
                        }
                        finally {
                            this.unlock();
                            this.postWriteCleanup();
                        }
                        if (createNewEntry) {
                            return (V)this.compute(key, hash, (ReferenceEntry<K, Object>)e, (ComputingValueReference<K, Object>)computingValueReference);
                        }
                    }
                    Preconditions.checkState(!Thread.holdsLock(e), (Object)"Recursive computation");
                    value = e.getValueReference().waitForValue();
                } while (value == null);
                this.recordRead(e);
                this.statsCounter.recordConcurrentMiss();
                return value;
            }
            finally {
                this.postReadCleanup();
            }
        }
        
        V compute(final K key, final int hash, final ReferenceEntry<K, V> e, final ComputingValueReference<K, V> computingValueReference) throws ExecutionException {
            V value = null;
            final long start = System.nanoTime();
            try {
                synchronized (e) {
                    value = computingValueReference.compute(key, hash);
                }
                final long end = System.nanoTime();
                this.statsCounter.recordLoadSuccess(end - start);
                final V oldValue = this.put(key, hash, value, true);
                if (oldValue != null) {
                    this.enqueueNotification(key, hash, value, RemovalCause.REPLACED);
                }
                return value;
            }
            finally {
                if (value == null) {
                    final long end2 = System.nanoTime();
                    this.statsCounter.recordLoadException(end2 - start);
                    this.clearValue(key, hash, computingValueReference);
                }
            }
        }
        
        void tryDrainReferenceQueues() {
            if (this.tryLock()) {
                try {
                    this.drainReferenceQueues();
                }
                finally {
                    this.unlock();
                }
            }
        }
        
        @GuardedBy("Segment.this")
        void drainReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                this.drainKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                this.drainValueReferenceQueue();
            }
        }
        
        @GuardedBy("Segment.this")
        void drainKeyReferenceQueue() {
            int i = 0;
            Reference<? extends K> ref;
            while ((ref = this.keyReferenceQueue.poll()) != null) {
                final ReferenceEntry<K, V> entry = (ReferenceEntry<K, V>)(ReferenceEntry)ref;
                this.map.reclaimKey(entry);
                if (++i == 16) {
                    break;
                }
            }
        }
        
        @GuardedBy("Segment.this")
        void drainValueReferenceQueue() {
            int i = 0;
            Reference<? extends V> ref;
            while ((ref = this.valueReferenceQueue.poll()) != null) {
                final ValueReference<K, V> valueReference = (ValueReference<K, V>)(ValueReference)ref;
                this.map.reclaimValue(valueReference);
                if (++i == 16) {
                    break;
                }
            }
        }
        
        void clearReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                this.clearKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                this.clearValueReferenceQueue();
            }
        }
        
        void clearKeyReferenceQueue() {
            while (this.keyReferenceQueue.poll() != null) {}
        }
        
        void clearValueReferenceQueue() {
            while (this.valueReferenceQueue.poll() != null) {}
        }
        
        void recordRead(final ReferenceEntry<K, V> entry) {
            if (this.map.expiresAfterAccess()) {
                this.recordExpirationTime(entry, this.map.expireAfterAccessNanos);
            }
            this.recencyQueue.add(entry);
        }
        
        @GuardedBy("Segment.this")
        void recordLockedRead(final ReferenceEntry<K, V> entry) {
            this.evictionQueue.add(entry);
            if (this.map.expiresAfterAccess()) {
                this.recordExpirationTime(entry, this.map.expireAfterAccessNanos);
                this.expirationQueue.add(entry);
            }
        }
        
        @GuardedBy("Segment.this")
        void recordWrite(final ReferenceEntry<K, V> entry) {
            this.drainRecencyQueue();
            this.evictionQueue.add(entry);
            if (this.map.expires()) {
                final long expiration = this.map.expiresAfterAccess() ? this.map.expireAfterAccessNanos : this.map.expireAfterWriteNanos;
                this.recordExpirationTime(entry, expiration);
                this.expirationQueue.add(entry);
            }
        }
        
        @GuardedBy("Segment.this")
        void drainRecencyQueue() {
            ReferenceEntry<K, V> e;
            while ((e = this.recencyQueue.poll()) != null) {
                if (this.evictionQueue.contains(e)) {
                    this.evictionQueue.add(e);
                }
                if (this.map.expiresAfterAccess() && this.expirationQueue.contains(e)) {
                    this.expirationQueue.add(e);
                }
            }
        }
        
        void recordExpirationTime(final ReferenceEntry<K, V> entry, final long expirationNanos) {
            entry.setExpirationTime(this.map.ticker.read() + expirationNanos);
        }
        
        void tryExpireEntries() {
            if (this.tryLock()) {
                try {
                    this.expireEntries();
                }
                finally {
                    this.unlock();
                }
            }
        }
        
        @GuardedBy("Segment.this")
        void expireEntries() {
            this.drainRecencyQueue();
            if (this.expirationQueue.isEmpty()) {
                return;
            }
            final long now = this.map.ticker.read();
            ReferenceEntry<K, V> e;
            while ((e = this.expirationQueue.peek()) != null && this.map.isExpired(e, now)) {
                if (!this.removeEntry(e, e.getHash(), RemovalCause.EXPIRED)) {
                    throw new AssertionError();
                }
            }
        }
        
        void enqueueNotification(final ReferenceEntry<K, V> entry, final RemovalCause cause) {
            this.enqueueNotification(entry.getKey(), entry.getHash(), entry.getValueReference().get(), cause);
        }
        
        void enqueueNotification(@Nullable final K key, final int hash, @Nullable final V value, final RemovalCause cause) {
            if (cause.wasEvicted()) {
                this.statsCounter.recordEviction();
            }
            if (this.map.removalNotificationQueue != CustomConcurrentHashMap.DISCARDING_QUEUE) {
                final RemovalNotification<K, V> notification = new RemovalNotification<K, V>(key, value, cause);
                this.map.removalNotificationQueue.offer(notification);
            }
        }
        
        @GuardedBy("Segment.this")
        boolean evictEntries() {
            if (!this.map.evictsBySize() || this.count < this.maxSegmentSize) {
                return false;
            }
            this.drainRecencyQueue();
            final ReferenceEntry<K, V> e = this.evictionQueue.remove();
            if (!this.removeEntry(e, e.getHash(), RemovalCause.SIZE)) {
                throw new AssertionError();
            }
            return true;
        }
        
        ReferenceEntry<K, V> getFirst(final int hash) {
            final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
            return table.get(hash & table.length() - 1);
        }
        
        ReferenceEntry<K, V> getEntry(final Object key, final int hash) {
            for (ReferenceEntry<K, V> e = this.getFirst(hash); e != null; e = e.getNext()) {
                if (e.getHash() == hash) {
                    final K entryKey = e.getKey();
                    if (entryKey == null) {
                        this.tryDrainReferenceQueues();
                    }
                    else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
                        return e;
                    }
                }
            }
            return null;
        }
        
        ReferenceEntry<K, V> getLiveEntry(final Object key, final int hash) {
            final ReferenceEntry<K, V> e = this.getEntry(key, hash);
            if (e == null) {
                return null;
            }
            if (this.map.expires() && this.map.isExpired(e)) {
                this.tryExpireEntries();
                return null;
            }
            return e;
        }
        
        V get(final Object key, final int hash) {
            try {
                if (this.count == 0) {
                    return null;
                }
                final ReferenceEntry<K, V> e = this.getLiveEntry(key, hash);
                if (e == null) {
                    return null;
                }
                final V value = e.getValueReference().get();
                if (value != null) {
                    this.recordRead(e);
                }
                else {
                    this.tryDrainReferenceQueues();
                }
                return value;
            }
            finally {
                this.postReadCleanup();
            }
        }
        
        boolean containsKey(final Object key, final int hash) {
            try {
                if (this.count != 0) {
                    final ReferenceEntry<K, V> e = this.getLiveEntry(key, hash);
                    return e != null && e.getValueReference().get() != null;
                }
                return false;
            }
            finally {
                this.postReadCleanup();
            }
        }
        
        @VisibleForTesting
        boolean containsValue(final Object value) {
            try {
                if (this.count != 0) {
                    final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                    for (int length = table.length(), i = 0; i < length; ++i) {
                        for (ReferenceEntry<K, V> e = table.get(i); e != null; e = e.getNext()) {
                            final V entryValue = this.getLiveValue(e);
                            if (entryValue != null) {
                                if (this.map.valueEquivalence.equivalent(value, entryValue)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }
            finally {
                this.postReadCleanup();
            }
        }
        
        V put(final K key, final int hash, final V value, final boolean onlyIfAbsent) {
            this.lock();
            try {
                this.preWriteCleanup();
                int newCount = this.count + 1;
                if (newCount > this.threshold) {
                    this.expand();
                    newCount = this.count + 1;
                }
                final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                final int index = hash & table.length() - 1;
                ReferenceEntry<K, V> e;
                final ReferenceEntry<K, V> first = e = table.get(index);
                while (e != null) {
                    final K entryKey = e.getKey();
                    if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                        final ValueReference<K, V> valueReference = e.getValueReference();
                        final V entryValue = valueReference.get();
                        if (entryValue == null) {
                            ++this.modCount;
                            this.setValue(e, value);
                            if (!valueReference.isComputingReference()) {
                                this.enqueueNotification(key, hash, entryValue, RemovalCause.COLLECTED);
                                newCount = this.count;
                            }
                            else if (this.evictEntries()) {
                                newCount = this.count + 1;
                            }
                            this.count = newCount;
                            return null;
                        }
                        if (onlyIfAbsent) {
                            this.recordLockedRead(e);
                            return entryValue;
                        }
                        ++this.modCount;
                        this.enqueueNotification(key, hash, entryValue, RemovalCause.REPLACED);
                        this.setValue(e, value);
                        return entryValue;
                    }
                    else {
                        e = e.getNext();
                    }
                }
                ++this.modCount;
                final ReferenceEntry<K, V> newEntry = this.newEntry(key, hash, first);
                this.setValue(newEntry, value);
                table.set(index, newEntry);
                if (this.evictEntries()) {
                    newCount = this.count + 1;
                }
                this.count = newCount;
                return null;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }
        
        @GuardedBy("Segment.this")
        void expand() {
            final AtomicReferenceArray<ReferenceEntry<K, V>> oldTable = this.table;
            final int oldCapacity = oldTable.length();
            if (oldCapacity >= 1073741824) {
                return;
            }
            int newCount = this.count;
            final AtomicReferenceArray<ReferenceEntry<K, V>> newTable = this.newEntryArray(oldCapacity << 1);
            this.threshold = newTable.length() * 3 / 4;
            final int newMask = newTable.length() - 1;
            for (int oldIndex = 0; oldIndex < oldCapacity; ++oldIndex) {
                final ReferenceEntry<K, V> head = oldTable.get(oldIndex);
                if (head != null) {
                    final ReferenceEntry<K, V> next = head.getNext();
                    final int headIndex = head.getHash() & newMask;
                    if (next == null) {
                        newTable.set(headIndex, head);
                    }
                    else {
                        ReferenceEntry<K, V> tail = head;
                        int tailIndex = headIndex;
                        for (ReferenceEntry<K, V> e = next; e != null; e = e.getNext()) {
                            final int newIndex = e.getHash() & newMask;
                            if (newIndex != tailIndex) {
                                tailIndex = newIndex;
                                tail = e;
                            }
                        }
                        newTable.set(tailIndex, tail);
                        for (ReferenceEntry<K, V> e = head; e != tail; e = e.getNext()) {
                            if (this.isCollected(e)) {
                                this.removeCollectedEntry(e);
                                --newCount;
                            }
                            else {
                                final int newIndex = e.getHash() & newMask;
                                final ReferenceEntry<K, V> newNext = newTable.get(newIndex);
                                final ReferenceEntry<K, V> newFirst = this.copyEntry(e, newNext);
                                newTable.set(newIndex, newFirst);
                            }
                        }
                    }
                }
            }
            this.table = newTable;
            this.count = newCount;
        }
        
        boolean replace(final K key, final int hash, final V oldValue, final V newValue) {
            this.lock();
            try {
                this.preWriteCleanup();
                final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                final int index = hash & table.length() - 1;
                ReferenceEntry<K, V> e;
                final ReferenceEntry<K, V> first = e = table.get(index);
                while (e != null) {
                    final K entryKey = e.getKey();
                    if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                        final ValueReference<K, V> valueReference = e.getValueReference();
                        final V entryValue = valueReference.get();
                        if (entryValue == null) {
                            if (this.isCollected(valueReference)) {
                                int newCount = this.count - 1;
                                ++this.modCount;
                                this.enqueueNotification(entryKey, hash, entryValue, RemovalCause.COLLECTED);
                                final ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                                newCount = this.count - 1;
                                table.set(index, newFirst);
                                this.count = newCount;
                            }
                            return false;
                        }
                        if (this.map.valueEquivalence.equivalent(oldValue, entryValue)) {
                            ++this.modCount;
                            this.enqueueNotification(key, hash, entryValue, RemovalCause.REPLACED);
                            this.setValue(e, newValue);
                            return true;
                        }
                        this.recordLockedRead(e);
                        return false;
                    }
                    else {
                        e = e.getNext();
                    }
                }
                return false;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }
        
        V replace(final K key, final int hash, final V newValue) {
            this.lock();
            try {
                this.preWriteCleanup();
                final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                final int index = hash & table.length() - 1;
                ReferenceEntry<K, V> e;
                final ReferenceEntry<K, V> first = e = table.get(index);
                while (e != null) {
                    final K entryKey = e.getKey();
                    if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                        final ValueReference<K, V> valueReference = e.getValueReference();
                        final V entryValue = valueReference.get();
                        if (entryValue == null) {
                            if (this.isCollected(valueReference)) {
                                int newCount = this.count - 1;
                                ++this.modCount;
                                this.enqueueNotification(entryKey, hash, entryValue, RemovalCause.COLLECTED);
                                final ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                                newCount = this.count - 1;
                                table.set(index, newFirst);
                                this.count = newCount;
                            }
                            return null;
                        }
                        ++this.modCount;
                        this.enqueueNotification(key, hash, entryValue, RemovalCause.REPLACED);
                        this.setValue(e, newValue);
                        return entryValue;
                    }
                    else {
                        e = e.getNext();
                    }
                }
                return null;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }
        
        V remove(final Object key, final int hash) {
            this.lock();
            try {
                this.preWriteCleanup();
                int newCount = this.count - 1;
                final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                final int index = hash & table.length() - 1;
                ReferenceEntry<K, V> e;
                for (ReferenceEntry<K, V> first = e = table.get(index); e != null; e = e.getNext()) {
                    final K entryKey = e.getKey();
                    if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                        final ValueReference<K, V> valueReference = e.getValueReference();
                        final V entryValue = valueReference.get();
                        RemovalCause cause;
                        if (entryValue != null) {
                            cause = RemovalCause.EXPLICIT;
                        }
                        else {
                            if (!this.isCollected(valueReference)) {
                                return null;
                            }
                            cause = RemovalCause.COLLECTED;
                        }
                        ++this.modCount;
                        this.enqueueNotification(entryKey, hash, entryValue, cause);
                        final ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                        newCount = this.count - 1;
                        table.set(index, newFirst);
                        this.count = newCount;
                        return entryValue;
                    }
                }
                return null;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }
        
        boolean remove(final Object key, final int hash, final Object value) {
            this.lock();
            try {
                this.preWriteCleanup();
                int newCount = this.count - 1;
                final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                final int index = hash & table.length() - 1;
                ReferenceEntry<K, V> e;
                for (ReferenceEntry<K, V> first = e = table.get(index); e != null; e = e.getNext()) {
                    final K entryKey = e.getKey();
                    if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                        final ValueReference<K, V> valueReference = e.getValueReference();
                        final V entryValue = valueReference.get();
                        RemovalCause cause;
                        if (this.map.valueEquivalence.equivalent(value, entryValue)) {
                            cause = RemovalCause.EXPLICIT;
                        }
                        else {
                            if (!this.isCollected(valueReference)) {
                                return false;
                            }
                            cause = RemovalCause.COLLECTED;
                        }
                        ++this.modCount;
                        this.enqueueNotification(entryKey, hash, entryValue, cause);
                        final ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                        newCount = this.count - 1;
                        table.set(index, newFirst);
                        this.count = newCount;
                        return cause == RemovalCause.EXPLICIT;
                    }
                }
                return false;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }
        
        void clear() {
            if (this.count != 0) {
                this.lock();
                try {
                    final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                    if (this.map.removalNotificationQueue != CustomConcurrentHashMap.DISCARDING_QUEUE) {
                        for (int i = 0; i < table.length(); ++i) {
                            for (ReferenceEntry<K, V> e = table.get(i); e != null; e = e.getNext()) {
                                if (!e.getValueReference().isComputingReference()) {
                                    this.enqueueNotification(e, RemovalCause.EXPLICIT);
                                }
                            }
                        }
                    }
                    for (int i = 0; i < table.length(); ++i) {
                        table.set(i, null);
                    }
                    this.clearReferenceQueues();
                    this.evictionQueue.clear();
                    this.expirationQueue.clear();
                    this.readCount.set(0);
                    ++this.modCount;
                    this.count = 0;
                }
                finally {
                    this.unlock();
                    this.postWriteCleanup();
                }
            }
        }
        
        @GuardedBy("Segment.this")
        ReferenceEntry<K, V> removeFromChain(final ReferenceEntry<K, V> first, final ReferenceEntry<K, V> entry) {
            this.evictionQueue.remove(entry);
            this.expirationQueue.remove(entry);
            int newCount = this.count;
            ReferenceEntry<K, V> newFirst = entry.getNext();
            for (ReferenceEntry<K, V> e = first; e != entry; e = e.getNext()) {
                if (this.isCollected(e)) {
                    this.removeCollectedEntry(e);
                    --newCount;
                }
                else {
                    newFirst = this.copyEntry(e, newFirst);
                }
            }
            this.count = newCount;
            return newFirst;
        }
        
        void removeCollectedEntry(final ReferenceEntry<K, V> entry) {
            this.enqueueNotification(entry, RemovalCause.COLLECTED);
            this.evictionQueue.remove(entry);
            this.expirationQueue.remove(entry);
        }
        
        boolean reclaimKey(final ReferenceEntry<K, V> entry, final int hash) {
            this.lock();
            try {
                int newCount = this.count - 1;
                final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                final int index = hash & table.length() - 1;
                ReferenceEntry<K, V> e;
                for (ReferenceEntry<K, V> first = e = table.get(index); e != null; e = e.getNext()) {
                    if (e == entry) {
                        ++this.modCount;
                        this.enqueueNotification(e.getKey(), hash, e.getValueReference().get(), RemovalCause.COLLECTED);
                        final ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                        newCount = this.count - 1;
                        table.set(index, newFirst);
                        this.count = newCount;
                        return true;
                    }
                }
                return false;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }
        
        boolean reclaimValue(final K key, final int hash, final ValueReference<K, V> valueReference) {
            this.lock();
            try {
                int newCount = this.count - 1;
                final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                final int index = hash & table.length() - 1;
                ReferenceEntry<K, V> e;
                final ReferenceEntry<K, V> first = e = table.get(index);
                while (e != null) {
                    final K entryKey = e.getKey();
                    if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                        final ValueReference<K, V> v = e.getValueReference();
                        if (v == valueReference) {
                            ++this.modCount;
                            this.enqueueNotification(key, hash, valueReference.get(), RemovalCause.COLLECTED);
                            final ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                            newCount = this.count - 1;
                            table.set(index, newFirst);
                            this.count = newCount;
                            return true;
                        }
                        return false;
                    }
                    else {
                        e = e.getNext();
                    }
                }
                return false;
            }
            finally {
                this.unlock();
                if (!this.isHeldByCurrentThread()) {
                    this.postWriteCleanup();
                }
            }
        }
        
        boolean clearValue(final K key, final int hash, final ValueReference<K, V> valueReference) {
            this.lock();
            try {
                final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                final int index = hash & table.length() - 1;
                ReferenceEntry<K, V> e;
                final ReferenceEntry<K, V> first = e = table.get(index);
                while (e != null) {
                    final K entryKey = e.getKey();
                    if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                        final ValueReference<K, V> v = e.getValueReference();
                        if (v == valueReference) {
                            final ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                            table.set(index, newFirst);
                            return true;
                        }
                        return false;
                    }
                    else {
                        e = e.getNext();
                    }
                }
                return false;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }
        
        @GuardedBy("Segment.this")
        boolean removeEntry(final ReferenceEntry<K, V> entry, final int hash, final RemovalCause cause) {
            int newCount = this.count - 1;
            final AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
            final int index = hash & table.length() - 1;
            ReferenceEntry<K, V> e;
            for (ReferenceEntry<K, V> first = e = table.get(index); e != null; e = e.getNext()) {
                if (e == entry) {
                    ++this.modCount;
                    this.enqueueNotification(e.getKey(), hash, e.getValueReference().get(), cause);
                    final ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                    newCount = this.count - 1;
                    table.set(index, newFirst);
                    this.count = newCount;
                    return true;
                }
            }
            return false;
        }
        
        boolean isCollected(final ReferenceEntry<K, V> entry) {
            return entry.getKey() == null || this.isCollected(entry.getValueReference());
        }
        
        boolean isCollected(final ValueReference<K, V> valueReference) {
            return !valueReference.isComputingReference() && valueReference.get() == null;
        }
        
        V getLiveValue(final ReferenceEntry<K, V> entry) {
            if (entry.getKey() == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            final V value = entry.getValueReference().get();
            if (value == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            if (this.map.expires() && this.map.isExpired(entry)) {
                this.tryExpireEntries();
                return null;
            }
            return value;
        }
        
        void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 0x3F) == 0x0) {
                this.cleanUp();
            }
        }
        
        @GuardedBy("Segment.this")
        void preWriteCleanup() {
            this.runLockedCleanup();
        }
        
        void postWriteCleanup() {
            this.runUnlockedCleanup();
        }
        
        void cleanUp() {
            this.runLockedCleanup();
            this.runUnlockedCleanup();
        }
        
        void runLockedCleanup() {
            if (this.tryLock()) {
                try {
                    this.drainReferenceQueues();
                    this.expireEntries();
                    this.readCount.set(0);
                }
                finally {
                    this.unlock();
                }
            }
        }
        
        void runUnlockedCleanup() {
            if (!this.isHeldByCurrentThread()) {
                this.map.processPendingNotifications();
            }
        }
    }
    
    private static final class ComputedUncheckedException<V> implements ComputedValue<V>
    {
        final RuntimeException e;
        
        ComputedUncheckedException(final RuntimeException e) {
            this.e = e;
        }
        
        public V get() {
            throw new UncheckedExecutionException(this.e);
        }
    }
    
    private static final class ComputedException<V> implements ComputedValue<V>
    {
        final Exception e;
        
        ComputedException(final Exception e) {
            this.e = e;
        }
        
        public V get() throws ExecutionException {
            throw new ExecutionException(this.e);
        }
    }
    
    private static final class ComputedError<V> implements ComputedValue<V>
    {
        final Error e;
        
        ComputedError(final Error e) {
            this.e = e;
        }
        
        public V get() {
            throw new ExecutionError(this.e);
        }
    }
    
    private static final class ComputedReference<V> implements ComputedValue<V>
    {
        final V value;
        
        ComputedReference(final V value) {
            this.value = Preconditions.checkNotNull(value);
        }
        
        public V get() {
            return this.value;
        }
    }
    
    private static final class ComputedNull<K, V> implements ComputedValue<V>
    {
        final String msg;
        
        public ComputedNull(final CacheLoader<? super K, V> loader, final K key) {
            this.msg = loader + " returned null for key " + key + ".";
        }
        
        public V get() {
            throw new NullPointerException(this.msg);
        }
    }
    
    static final class ComputingValueReference<K, V> implements ValueReference<K, V>
    {
        final CacheLoader<? super K, V> loader;
        @GuardedBy("ComputingValueReference.this")
        volatile ComputedValue<V> computedValue;
        
        public ComputingValueReference(final CacheLoader<? super K, V> loader) {
            this.computedValue = null;
            this.loader = loader;
        }
        
        public boolean isComputingReference() {
            return true;
        }
        
        public V waitForValue() throws ExecutionException {
            if (this.computedValue == null) {
                boolean interrupted = false;
                try {
                    synchronized (this) {
                        while (this.computedValue == null) {
                            try {
                                this.wait();
                            }
                            catch (InterruptedException ie) {
                                interrupted = true;
                            }
                        }
                    }
                }
                finally {
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            return this.computedValue.get();
        }
        
        public void notifyNewValue(final V newValue) {
            this.setComputedValue(new ComputedReference<V>(newValue));
        }
        
        V compute(final K key, final int hash) throws ExecutionException {
            ComputedValue<V> valueWrapper;
            try {
                final V value = this.loader.load((Object)key);
                if (value == null) {
                    valueWrapper = new ComputedNull<Object, V>(this.loader, key);
                }
                else {
                    valueWrapper = new ComputedReference<V>(value);
                }
            }
            catch (RuntimeException e) {
                valueWrapper = new ComputedUncheckedException<V>(e);
            }
            catch (Exception e2) {
                valueWrapper = new ComputedException<V>(e2);
            }
            catch (Error e3) {
                valueWrapper = new ComputedError<V>(e3);
            }
            this.setComputedValue(valueWrapper);
            return valueWrapper.get();
        }
        
        void setComputedValue(final ComputedValue<V> newValue) {
            synchronized (this) {
                if (this.computedValue == null) {
                    this.computedValue = newValue;
                    this.notifyAll();
                }
            }
        }
        
        public V get() {
            return null;
        }
        
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }
        
        public ValueReference<K, V> copyFor(final ReferenceQueue<V> queue, final ReferenceEntry<K, V> entry) {
            return this;
        }
    }
    
    static final class EvictionQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>>
    {
        final ReferenceEntry<K, V> head;
        
        EvictionQueue() {
            this.head = new AbstractReferenceEntry<K, V>() {
                ReferenceEntry<K, V> nextEvictable = this;
                ReferenceEntry<K, V> previousEvictable = this;
                
                public ReferenceEntry<K, V> getNextEvictable() {
                    return this.nextEvictable;
                }
                
                public void setNextEvictable(final ReferenceEntry<K, V> next) {
                    this.nextEvictable = next;
                }
                
                public ReferenceEntry<K, V> getPreviousEvictable() {
                    return this.previousEvictable;
                }
                
                public void setPreviousEvictable(final ReferenceEntry<K, V> previous) {
                    this.previousEvictable = previous;
                }
            };
        }
        
        public boolean offer(final ReferenceEntry<K, V> entry) {
            CustomConcurrentHashMap.connectEvictables(entry.getPreviousEvictable(), entry.getNextEvictable());
            CustomConcurrentHashMap.connectEvictables(this.head.getPreviousEvictable(), entry);
            CustomConcurrentHashMap.connectEvictables(entry, this.head);
            return true;
        }
        
        public ReferenceEntry<K, V> peek() {
            final ReferenceEntry<K, V> next = this.head.getNextEvictable();
            return (next == this.head) ? null : next;
        }
        
        public ReferenceEntry<K, V> poll() {
            final ReferenceEntry<K, V> next = this.head.getNextEvictable();
            if (next == this.head) {
                return null;
            }
            this.remove(next);
            return next;
        }
        
        public boolean remove(final Object o) {
            final ReferenceEntry<K, V> e = (ReferenceEntry<K, V>)o;
            final ReferenceEntry<K, V> previous = e.getPreviousEvictable();
            final ReferenceEntry<K, V> next = e.getNextEvictable();
            CustomConcurrentHashMap.connectEvictables(previous, next);
            CustomConcurrentHashMap.nullifyEvictable(e);
            return next != NullEntry.INSTANCE;
        }
        
        public boolean contains(final Object o) {
            final ReferenceEntry<K, V> e = (ReferenceEntry<K, V>)o;
            return e.getNextEvictable() != NullEntry.INSTANCE;
        }
        
        public boolean isEmpty() {
            return this.head.getNextEvictable() == this.head;
        }
        
        public int size() {
            int size = 0;
            for (ReferenceEntry<K, V> e = this.head.getNextEvictable(); e != this.head; e = e.getNextEvictable()) {
                ++size;
            }
            return size;
        }
        
        public void clear() {
            ReferenceEntry<K, V> next;
            for (ReferenceEntry<K, V> e = this.head.getNextEvictable(); e != this.head; e = next) {
                next = e.getNextEvictable();
                CustomConcurrentHashMap.nullifyEvictable(e);
            }
            this.head.setNextEvictable(this.head);
            this.head.setPreviousEvictable(this.head);
        }
        
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractLinkedIterator<ReferenceEntry<K, V>>(this.peek()) {
                protected ReferenceEntry<K, V> computeNext(final ReferenceEntry<K, V> previous) {
                    final ReferenceEntry<K, V> next = previous.getNextEvictable();
                    return (next == EvictionQueue.this.head) ? null : next;
                }
            };
        }
    }
    
    static final class ExpirationQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>>
    {
        final ReferenceEntry<K, V> head;
        
        ExpirationQueue() {
            this.head = new AbstractReferenceEntry<K, V>() {
                ReferenceEntry<K, V> nextExpirable = this;
                ReferenceEntry<K, V> previousExpirable = this;
                
                public long getExpirationTime() {
                    return Long.MAX_VALUE;
                }
                
                public void setExpirationTime(final long time) {
                }
                
                public ReferenceEntry<K, V> getNextExpirable() {
                    return this.nextExpirable;
                }
                
                public void setNextExpirable(final ReferenceEntry<K, V> next) {
                    this.nextExpirable = next;
                }
                
                public ReferenceEntry<K, V> getPreviousExpirable() {
                    return this.previousExpirable;
                }
                
                public void setPreviousExpirable(final ReferenceEntry<K, V> previous) {
                    this.previousExpirable = previous;
                }
            };
        }
        
        public boolean offer(final ReferenceEntry<K, V> entry) {
            CustomConcurrentHashMap.connectExpirables(entry.getPreviousExpirable(), entry.getNextExpirable());
            CustomConcurrentHashMap.connectExpirables(this.head.getPreviousExpirable(), entry);
            CustomConcurrentHashMap.connectExpirables(entry, this.head);
            return true;
        }
        
        public ReferenceEntry<K, V> peek() {
            final ReferenceEntry<K, V> next = this.head.getNextExpirable();
            return (next == this.head) ? null : next;
        }
        
        public ReferenceEntry<K, V> poll() {
            final ReferenceEntry<K, V> next = this.head.getNextExpirable();
            if (next == this.head) {
                return null;
            }
            this.remove(next);
            return next;
        }
        
        public boolean remove(final Object o) {
            final ReferenceEntry<K, V> e = (ReferenceEntry<K, V>)o;
            final ReferenceEntry<K, V> previous = e.getPreviousExpirable();
            final ReferenceEntry<K, V> next = e.getNextExpirable();
            CustomConcurrentHashMap.connectExpirables(previous, next);
            CustomConcurrentHashMap.nullifyExpirable(e);
            return next != NullEntry.INSTANCE;
        }
        
        public boolean contains(final Object o) {
            final ReferenceEntry<K, V> e = (ReferenceEntry<K, V>)o;
            return e.getNextExpirable() != NullEntry.INSTANCE;
        }
        
        public boolean isEmpty() {
            return this.head.getNextExpirable() == this.head;
        }
        
        public int size() {
            int size = 0;
            for (ReferenceEntry<K, V> e = this.head.getNextExpirable(); e != this.head; e = e.getNextExpirable()) {
                ++size;
            }
            return size;
        }
        
        public void clear() {
            ReferenceEntry<K, V> next;
            for (ReferenceEntry<K, V> e = this.head.getNextExpirable(); e != this.head; e = next) {
                next = e.getNextExpirable();
                CustomConcurrentHashMap.nullifyExpirable(e);
            }
            this.head.setNextExpirable(this.head);
            this.head.setPreviousExpirable(this.head);
        }
        
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractLinkedIterator<ReferenceEntry<K, V>>(this.peek()) {
                protected ReferenceEntry<K, V> computeNext(final ReferenceEntry<K, V> previous) {
                    final ReferenceEntry<K, V> next = previous.getNextExpirable();
                    return (next == ExpirationQueue.this.head) ? null : next;
                }
            };
        }
    }
    
    abstract class HashIterator
    {
        int nextSegmentIndex;
        int nextTableIndex;
        Segment<K, V> currentSegment;
        AtomicReferenceArray<ReferenceEntry<K, V>> currentTable;
        ReferenceEntry<K, V> nextEntry;
        WriteThroughEntry nextExternal;
        WriteThroughEntry lastReturned;
        
        HashIterator() {
            this.nextSegmentIndex = CustomConcurrentHashMap.this.segments.length - 1;
            this.nextTableIndex = -1;
            this.advance();
        }
        
        final void advance() {
            this.nextExternal = null;
            if (this.nextInChain()) {
                return;
            }
            if (this.nextInTable()) {
                return;
            }
            while (this.nextSegmentIndex >= 0) {
                this.currentSegment = CustomConcurrentHashMap.this.segments[this.nextSegmentIndex--];
                if (this.currentSegment.count != 0) {
                    this.currentTable = this.currentSegment.table;
                    this.nextTableIndex = this.currentTable.length() - 1;
                    if (this.nextInTable()) {
                        return;
                    }
                    continue;
                }
            }
        }
        
        boolean nextInChain() {
            if (this.nextEntry != null) {
                this.nextEntry = this.nextEntry.getNext();
                while (this.nextEntry != null) {
                    if (this.advanceTo(this.nextEntry)) {
                        return true;
                    }
                    this.nextEntry = this.nextEntry.getNext();
                }
            }
            return false;
        }
        
        boolean nextInTable() {
            while (this.nextTableIndex >= 0) {
                final ReferenceEntry<K, V> nextEntry = this.currentTable.get(this.nextTableIndex--);
                this.nextEntry = nextEntry;
                if (nextEntry != null && (this.advanceTo(this.nextEntry) || this.nextInChain())) {
                    return true;
                }
            }
            return false;
        }
        
        boolean advanceTo(final ReferenceEntry<K, V> entry) {
            try {
                final K key = entry.getKey();
                final V value = CustomConcurrentHashMap.this.getLiveValue(entry);
                if (value != null) {
                    this.nextExternal = new WriteThroughEntry(key, value);
                    return true;
                }
                return false;
            }
            finally {
                this.currentSegment.postReadCleanup();
            }
        }
        
        public boolean hasNext() {
            return this.nextExternal != null;
        }
        
        WriteThroughEntry nextEntry() {
            if (this.nextExternal == null) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.nextExternal;
            this.advance();
            return this.lastReturned;
        }
        
        public void remove() {
            Preconditions.checkState(this.lastReturned != null);
            CustomConcurrentHashMap.this.remove(this.lastReturned.getKey());
            this.lastReturned = null;
        }
    }
    
    final class KeyIterator extends HashIterator implements Iterator<K>
    {
        public K next() {
            return this.nextEntry().getKey();
        }
    }
    
    final class ValueIterator extends HashIterator implements Iterator<V>
    {
        public V next() {
            return this.nextEntry().getValue();
        }
    }
    
    final class WriteThroughEntry implements Map.Entry<K, V>
    {
        final K key;
        V value;
        
        WriteThroughEntry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() {
            return this.key;
        }
        
        public V getValue() {
            return this.value;
        }
        
        public boolean equals(@Nullable final Object object) {
            if (object instanceof Map.Entry) {
                final Map.Entry<?, ?> that = (Map.Entry<?, ?>)object;
                return this.key.equals(that.getKey()) && this.value.equals(that.getValue());
            }
            return false;
        }
        
        public int hashCode() {
            return this.key.hashCode() ^ this.value.hashCode();
        }
        
        public V setValue(final V newValue) {
            throw new UnsupportedOperationException();
        }
        
        public String toString() {
            return this.getKey() + "=" + this.getValue();
        }
    }
    
    final class EntryIterator extends HashIterator implements Iterator<Map.Entry<K, V>>
    {
        public Map.Entry<K, V> next() {
            return this.nextEntry();
        }
    }
    
    final class KeySet extends AbstractSet<K>
    {
        public Iterator<K> iterator() {
            return new KeyIterator();
        }
        
        public int size() {
            return CustomConcurrentHashMap.this.size();
        }
        
        public boolean isEmpty() {
            return CustomConcurrentHashMap.this.isEmpty();
        }
        
        public boolean contains(final Object o) {
            return CustomConcurrentHashMap.this.containsKey(o);
        }
        
        public boolean remove(final Object o) {
            return CustomConcurrentHashMap.this.remove(o) != null;
        }
        
        public void clear() {
            CustomConcurrentHashMap.this.clear();
        }
    }
    
    final class Values extends AbstractCollection<V>
    {
        public Iterator<V> iterator() {
            return new ValueIterator();
        }
        
        public int size() {
            return CustomConcurrentHashMap.this.size();
        }
        
        public boolean isEmpty() {
            return CustomConcurrentHashMap.this.isEmpty();
        }
        
        public boolean contains(final Object o) {
            return CustomConcurrentHashMap.this.containsValue(o);
        }
        
        public void clear() {
            CustomConcurrentHashMap.this.clear();
        }
    }
    
    final class EntrySet extends AbstractSet<Map.Entry<K, V>>
    {
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }
        
        public boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
            final Object key = e.getKey();
            if (key == null) {
                return false;
            }
            final V v = CustomConcurrentHashMap.this.get(key);
            return v != null && CustomConcurrentHashMap.this.valueEquivalence.equivalent(e.getValue(), v);
        }
        
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
            final Object key = e.getKey();
            return key != null && CustomConcurrentHashMap.this.remove(key, e.getValue());
        }
        
        public int size() {
            return CustomConcurrentHashMap.this.size();
        }
        
        public boolean isEmpty() {
            return CustomConcurrentHashMap.this.isEmpty();
        }
        
        public void clear() {
            CustomConcurrentHashMap.this.clear();
        }
    }
    
    static final class SerializationProxy<K, V> extends ForwardingCache<K, V> implements Serializable
    {
        private static final long serialVersionUID = 1L;
        final CacheLoader<? super K, V> loader;
        final Strength keyStrength;
        final Strength valueStrength;
        final Equivalence<Object> keyEquivalence;
        final Equivalence<Object> valueEquivalence;
        final long expireAfterWriteNanos;
        final long expireAfterAccessNanos;
        final int maximumSize;
        final int concurrencyLevel;
        final RemovalListener<? super K, ? super V> removalListener;
        final Ticker ticker;
        transient Cache<K, V> delegate;
        
        SerializationProxy(final CacheLoader<? super K, V> loader, final Strength keyStrength, final Strength valueStrength, final Equivalence<Object> keyEquivalence, final Equivalence<Object> valueEquivalence, final long expireAfterWriteNanos, final long expireAfterAccessNanos, final int maximumSize, final int concurrencyLevel, final RemovalListener<? super K, ? super V> removalListener, final Ticker ticker) {
            this.loader = loader;
            this.keyStrength = keyStrength;
            this.valueStrength = valueStrength;
            this.keyEquivalence = keyEquivalence;
            this.valueEquivalence = valueEquivalence;
            this.expireAfterWriteNanos = expireAfterWriteNanos;
            this.expireAfterAccessNanos = expireAfterAccessNanos;
            this.maximumSize = maximumSize;
            this.concurrencyLevel = concurrencyLevel;
            this.removalListener = removalListener;
            this.ticker = ticker;
        }
        
        private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            final CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel);
            builder.removalListener((RemovalListener<? super Object, ? super Object>)this.removalListener);
            if (this.expireAfterWriteNanos > 0L) {
                builder.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
            }
            if (this.expireAfterAccessNanos > 0L) {
                builder.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
            }
            if (this.maximumSize != -1) {
                builder.maximumSize(this.maximumSize);
            }
            if (this.ticker != Ticker.systemTicker()) {
                builder.ticker(this.ticker);
            }
            this.delegate = builder.build(this.loader);
        }
        
        private Object readResolve() {
            return this.delegate;
        }
        
        protected Cache<K, V> delegate() {
            return this.delegate;
        }
    }
    
    interface ReferenceEntry<K, V>
    {
        ValueReference<K, V> getValueReference();
        
        void setValueReference(final ValueReference<K, V> p0);
        
        ReferenceEntry<K, V> getNext();
        
        int getHash();
        
        K getKey();
        
        long getExpirationTime();
        
        void setExpirationTime(final long p0);
        
        ReferenceEntry<K, V> getNextExpirable();
        
        void setNextExpirable(final ReferenceEntry<K, V> p0);
        
        ReferenceEntry<K, V> getPreviousExpirable();
        
        void setPreviousExpirable(final ReferenceEntry<K, V> p0);
        
        ReferenceEntry<K, V> getNextEvictable();
        
        void setNextEvictable(final ReferenceEntry<K, V> p0);
        
        ReferenceEntry<K, V> getPreviousEvictable();
        
        void setPreviousEvictable(final ReferenceEntry<K, V> p0);
    }
    
    interface ValueReference<K, V>
    {
        V get();
        
        V waitForValue() throws ExecutionException;
        
        ReferenceEntry<K, V> getEntry();
        
        ValueReference<K, V> copyFor(final ReferenceQueue<V> p0, final ReferenceEntry<K, V> p1);
        
        void notifyNewValue(final V p0);
        
        boolean isComputingReference();
    }
    
    private interface ComputedValue<V>
    {
        V get() throws ExecutionException;
    }
}
