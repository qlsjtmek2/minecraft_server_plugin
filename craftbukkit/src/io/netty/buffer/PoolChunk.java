// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

final class PoolChunk<T>
{
    private static final int ST_UNUSED = 0;
    private static final int ST_BRANCH = 1;
    private static final int ST_ALLOCATED = 2;
    private static final int ST_ALLOCATED_SUBPAGE = 3;
    private static final long multiplier = 25214903917L;
    private static final long addend = 11L;
    private static final long mask = 281474976710655L;
    final PoolArena<T> arena;
    final T memory;
    final boolean unpooled;
    private final int[] memoryMap;
    private final PoolSubpage<T>[] subpages;
    private final int subpageOverflowMask;
    private final int pageSize;
    private final int pageShifts;
    private final int chunkSize;
    private final int maxSubpageAllocs;
    private long random;
    private int freeBytes;
    PoolChunkList<T> parent;
    PoolChunk<T> prev;
    PoolChunk<T> next;
    
    PoolChunk(final PoolArena<T> arena, final T memory, final int pageSize, final int maxOrder, final int pageShifts, final int chunkSize) {
        this.random = ((System.nanoTime() ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL);
        this.unpooled = false;
        this.arena = arena;
        this.memory = memory;
        this.pageSize = pageSize;
        this.pageShifts = pageShifts;
        this.chunkSize = chunkSize;
        this.subpageOverflowMask = ~(pageSize - 1);
        this.freeBytes = chunkSize;
        final int chunkSizeInPages = chunkSize >>> pageShifts;
        this.maxSubpageAllocs = 1 << maxOrder;
        this.memoryMap = new int[this.maxSubpageAllocs << 1];
        int memoryMapIndex = 1;
        for (int i = 0; i <= maxOrder; ++i) {
            for (int runSizeInPages = chunkSizeInPages >>> i, j = 0; j < chunkSizeInPages; j += runSizeInPages) {
                this.memoryMap[memoryMapIndex++] = (j << 17 | runSizeInPages << 2 | 0x0);
            }
        }
        this.subpages = this.newSubpageArray(this.maxSubpageAllocs);
    }
    
    PoolChunk(final PoolArena<T> arena, final T memory, final int size) {
        this.random = ((System.nanoTime() ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL);
        this.unpooled = true;
        this.arena = arena;
        this.memory = memory;
        this.memoryMap = null;
        this.subpages = null;
        this.subpageOverflowMask = 0;
        this.pageSize = 0;
        this.pageShifts = 0;
        this.chunkSize = size;
        this.maxSubpageAllocs = 0;
    }
    
    private PoolSubpage<T>[] newSubpageArray(final int size) {
        return (PoolSubpage<T>[])new PoolSubpage[size];
    }
    
    int usage() {
        if (this.freeBytes == 0) {
            return 100;
        }
        final int freePercentage = (int)(this.freeBytes * 100L / this.chunkSize);
        if (freePercentage == 0) {
            return 99;
        }
        return 100 - freePercentage;
    }
    
    long allocate(final int normCapacity) {
        final int firstVal = this.memoryMap[1];
        if ((normCapacity & this.subpageOverflowMask) != 0x0) {
            return this.allocateRun(normCapacity, 1, firstVal);
        }
        return this.allocateSubpage(normCapacity, 1, firstVal);
    }
    
    private long allocateRun(final int normCapacity, int curIdx, int val) {
        while ((val & 0x2) == 0x0) {
            if ((val & 0x1) == 0x0) {
                return this.allocateRunSimple(normCapacity, curIdx, val);
            }
            final int nextIdx = curIdx << 1 ^ this.nextRandom();
            final long res = this.allocateRun(normCapacity, nextIdx, this.memoryMap[nextIdx]);
            if (res > 0L) {
                return res;
            }
            curIdx = (nextIdx ^ 0x1);
            val = this.memoryMap[curIdx];
        }
        return -1L;
    }
    
    private long allocateRunSimple(final int normCapacity, int curIdx, int val) {
        int runLength = this.runLength(val);
        if (normCapacity > runLength) {
            return -1L;
        }
        while (normCapacity != runLength) {
            final int nextIdx = curIdx << 1 ^ this.nextRandom();
            final int unusedIdx = nextIdx ^ 0x1;
            this.memoryMap[curIdx] = ((val & 0xFFFFFFFC) | 0x1);
            this.memoryMap[unusedIdx] = ((this.memoryMap[unusedIdx] & 0xFFFFFFFC) | 0x0);
            runLength >>>= 1;
            curIdx = nextIdx;
            val = this.memoryMap[curIdx];
        }
        this.memoryMap[curIdx] = ((val & 0xFFFFFFFC) | 0x2);
        this.freeBytes -= runLength;
        return curIdx;
    }
    
    private long allocateSubpage(final int normCapacity, final int curIdx, final int val) {
        final int state = val & 0x3;
        if (state == 1) {
            final int nextIdx = curIdx << 1 ^ this.nextRandom();
            final long res = this.branchSubpage(normCapacity, nextIdx);
            if (res > 0L) {
                return res;
            }
            return this.branchSubpage(normCapacity, nextIdx ^ 0x1);
        }
        else {
            if (state == 0) {
                return this.allocateSubpageSimple(normCapacity, curIdx, val);
            }
            if (state != 3) {
                return -1L;
            }
            final PoolSubpage<T> subpage = this.subpages[this.subpageIdx(curIdx)];
            final int elemSize = subpage.elemSize;
            if (normCapacity != elemSize) {
                return -1L;
            }
            return subpage.allocate();
        }
    }
    
    private long allocateSubpageSimple(final int normCapacity, int curIdx, int val) {
        int runLength;
        int nextIdx;
        for (runLength = this.runLength(val); runLength != this.pageSize; runLength >>>= 1, curIdx = nextIdx, val = this.memoryMap[curIdx]) {
            nextIdx = (curIdx << 1 ^ this.nextRandom());
            final int unusedIdx = nextIdx ^ 0x1;
            this.memoryMap[curIdx] = ((val & 0xFFFFFFFC) | 0x1);
            this.memoryMap[unusedIdx] = ((this.memoryMap[unusedIdx] & 0xFFFFFFFC) | 0x0);
        }
        this.memoryMap[curIdx] = ((val & 0xFFFFFFFC) | 0x3);
        this.freeBytes -= runLength;
        final int subpageIdx = this.subpageIdx(curIdx);
        PoolSubpage<T> subpage = this.subpages[subpageIdx];
        if (subpage == null) {
            subpage = new PoolSubpage<T>(this, curIdx, this.runOffset(val), this.pageSize, normCapacity);
            this.subpages[subpageIdx] = subpage;
        }
        else {
            subpage.init(normCapacity);
        }
        return subpage.allocate();
    }
    
    private long branchSubpage(final int normCapacity, final int nextIdx) {
        final int nextVal = this.memoryMap[nextIdx];
        if ((nextVal & 0x3) != 0x2) {
            return this.allocateSubpage(normCapacity, nextIdx, nextVal);
        }
        return -1L;
    }
    
    void free(final long handle) {
        int memoryMapIdx = (int)handle;
        final int bitmapIdx = (int)(handle >>> 32);
        int val = this.memoryMap[memoryMapIdx];
        final int state = val & 0x3;
        if (state == 3) {
            assert bitmapIdx != 0;
            final PoolSubpage<T> subpage = this.subpages[this.subpageIdx(memoryMapIdx)];
            assert subpage != null && subpage.doNotDestroy;
            if (subpage.free(bitmapIdx & 0x3FFFFFFF)) {
                return;
            }
        }
        else {
            assert state == 2 : "state: " + state;
            assert bitmapIdx == 0;
        }
        this.freeBytes += this.runLength(val);
        while (true) {
            this.memoryMap[memoryMapIdx] = ((val & 0xFFFFFFFC) | 0x0);
            if (memoryMapIdx == 1) {
                assert this.freeBytes == this.chunkSize;
            }
            else {
                if ((this.memoryMap[siblingIdx(memoryMapIdx)] & 0x3) != 0x0) {
                    return;
                }
                memoryMapIdx = parentIdx(memoryMapIdx);
                val = this.memoryMap[memoryMapIdx];
            }
        }
    }
    
    void initBuf(final PooledByteBuf<T> buf, final long handle, final int reqCapacity) {
        final int memoryMapIdx = (int)handle;
        final int bitmapIdx = (int)(handle >>> 32);
        if (bitmapIdx == 0) {
            final int val = this.memoryMap[memoryMapIdx];
            assert (val & 0x3) == 0x2 : String.valueOf(val & 0x3);
            buf.init(this, handle, this.runOffset(val), reqCapacity, this.runLength(val));
        }
        else {
            this.initBufWithSubpage(buf, handle, bitmapIdx, reqCapacity);
        }
    }
    
    void initBufWithSubpage(final PooledByteBuf<T> buf, final long handle, final int reqCapacity) {
        this.initBufWithSubpage(buf, handle, (int)(handle >>> 32), reqCapacity);
    }
    
    private void initBufWithSubpage(final PooledByteBuf<T> buf, final long handle, final int bitmapIdx, final int reqCapacity) {
        assert bitmapIdx != 0;
        final int memoryMapIdx = (int)handle;
        final int val = this.memoryMap[memoryMapIdx];
        assert (val & 0x3) == 0x3;
        final PoolSubpage<T> subpage = this.subpages[this.subpageIdx(memoryMapIdx)];
        assert subpage.doNotDestroy;
        assert reqCapacity <= subpage.elemSize;
        buf.init(this, handle, this.runOffset(val) + (bitmapIdx & 0x3FFFFFFF) * subpage.elemSize, reqCapacity, subpage.elemSize);
    }
    
    private static int parentIdx(final int memoryMapIdx) {
        return memoryMapIdx >>> 1;
    }
    
    private static int siblingIdx(final int memoryMapIdx) {
        return memoryMapIdx ^ 0x1;
    }
    
    private int runLength(final int val) {
        return (val >>> 2 & 0x7FFF) << this.pageShifts;
    }
    
    private int runOffset(final int val) {
        return val >>> 17 << this.pageShifts;
    }
    
    private int subpageIdx(final int memoryMapIdx) {
        return memoryMapIdx - this.maxSubpageAllocs;
    }
    
    private int nextRandom() {
        this.random = (this.random * 25214903917L + 11L & 0xFFFFFFFFFFFFL);
        return (int)(this.random >>> 47) & 0x1;
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append("Chunk(");
        buf.append(Integer.toHexString(System.identityHashCode(this)));
        buf.append(": ");
        buf.append(this.usage());
        buf.append("%, ");
        buf.append(this.chunkSize - this.freeBytes);
        buf.append('/');
        buf.append(this.chunkSize);
        buf.append(')');
        return buf.toString();
    }
}
