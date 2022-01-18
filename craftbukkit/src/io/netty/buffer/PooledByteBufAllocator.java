// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.util.concurrent.atomic.AtomicInteger;
import java.nio.ByteBuffer;

public class PooledByteBufAllocator extends AbstractByteBufAllocator
{
    private static final int DEFAULT_NUM_HEAP_ARENA;
    private static final int DEFAULT_NUM_DIRECT_ARENA;
    private static final int DEFAULT_PAGE_SIZE = 8192;
    private static final int DEFAULT_MAX_ORDER = 11;
    private static final int MIN_PAGE_SIZE = 4096;
    private static final int MAX_CHUNK_SIZE = 1073741824;
    public static final PooledByteBufAllocator DEFAULT;
    private final PoolArena<byte[]>[] heapArenas;
    private final PoolArena<ByteBuffer>[] directArenas;
    final ThreadLocal<PoolThreadCache> threadCache;
    
    public PooledByteBufAllocator() {
        this(false);
    }
    
    public PooledByteBufAllocator(final boolean preferDirect) {
        this(preferDirect, PooledByteBufAllocator.DEFAULT_NUM_HEAP_ARENA, PooledByteBufAllocator.DEFAULT_NUM_DIRECT_ARENA, 8192, 11);
    }
    
    public PooledByteBufAllocator(final int nHeapArena, final int nDirectArena, final int pageSize, final int maxOrder) {
        this(false, nHeapArena, nDirectArena, pageSize, maxOrder);
    }
    
    public PooledByteBufAllocator(final boolean directByDefault, final int nHeapArena, final int nDirectArena, final int pageSize, final int maxOrder) {
        super(directByDefault);
        this.threadCache = new ThreadLocal<PoolThreadCache>() {
            private final AtomicInteger index = new AtomicInteger();
            
            @Override
            protected PoolThreadCache initialValue() {
                final int idx = Math.abs(this.index.getAndIncrement() % PooledByteBufAllocator.this.heapArenas.length);
                return new PoolThreadCache(PooledByteBufAllocator.this.heapArenas[idx], PooledByteBufAllocator.this.directArenas[idx]);
            }
        };
        final int chunkSize = validateAndCalculateChunkSize(pageSize, maxOrder);
        if (nHeapArena <= 0) {
            throw new IllegalArgumentException("nHeapArena: " + nHeapArena + " (expected: 1+)");
        }
        if (nDirectArena <= 0) {
            throw new IllegalArgumentException("nDirectArea: " + nDirectArena + " (expected: 1+)");
        }
        final int pageShifts = validateAndCalculatePageShifts(pageSize);
        this.heapArenas = newArenaArray(nHeapArena);
        for (int i = 0; i < this.heapArenas.length; ++i) {
            this.heapArenas[i] = new PoolArena.HeapArena(this, pageSize, maxOrder, pageShifts, chunkSize);
        }
        this.directArenas = newArenaArray(nDirectArena);
        for (int i = 0; i < this.directArenas.length; ++i) {
            this.directArenas[i] = new PoolArena.DirectArena(this, pageSize, maxOrder, pageShifts, chunkSize);
        }
    }
    
    private static <T> PoolArena<T>[] newArenaArray(final int size) {
        return (PoolArena<T>[])new PoolArena[size];
    }
    
    private static int validateAndCalculatePageShifts(final int pageSize) {
        if (pageSize < 4096) {
            throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: 4096+)");
        }
        boolean found1 = false;
        int pageShifts = 0;
        for (int i = pageSize; i != 0; i >>= 1) {
            if ((i & 0x1) != 0x0) {
                if (found1) {
                    throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: power of 2");
                }
                found1 = true;
            }
            else if (!found1) {
                ++pageShifts;
            }
        }
        return pageShifts;
    }
    
    private static int validateAndCalculateChunkSize(final int pageSize, final int maxOrder) {
        if (maxOrder > 14) {
            throw new IllegalArgumentException("maxOrder: " + maxOrder + " (expected: 0-14)");
        }
        int chunkSize = pageSize;
        for (int i = maxOrder; i > 0; --i) {
            if (chunkSize > 536870912) {
                throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", pageSize, maxOrder, 1073741824));
            }
            chunkSize <<= 1;
        }
        return chunkSize;
    }
    
    @Override
    protected ByteBuf newHeapBuffer(final int initialCapacity, final int maxCapacity) {
        final PoolThreadCache cache = this.threadCache.get();
        return cache.heapArena.allocate(cache, initialCapacity, maxCapacity);
    }
    
    @Override
    protected ByteBuf newDirectBuffer(final int initialCapacity, final int maxCapacity) {
        final PoolThreadCache cache = this.threadCache.get();
        return cache.directArena.allocate(cache, initialCapacity, maxCapacity);
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(this.heapArenas.length);
        buf.append(" heap arena(s):");
        buf.append(StringUtil.NEWLINE);
        for (final PoolArena<byte[]> a : this.heapArenas) {
            buf.append(a);
        }
        buf.append(this.directArenas.length);
        buf.append(" direct arena(s):");
        buf.append(StringUtil.NEWLINE);
        for (final PoolArena<ByteBuffer> a2 : this.directArenas) {
            buf.append(a2);
        }
        return buf.toString();
    }
    
    static {
        DEFAULT_NUM_HEAP_ARENA = Runtime.getRuntime().availableProcessors();
        DEFAULT_NUM_DIRECT_ARENA = Runtime.getRuntime().availableProcessors();
        DEFAULT = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
    }
}
