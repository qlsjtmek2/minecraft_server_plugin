// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import java.util.Iterator;
import java.util.ArrayDeque;
import java.nio.ByteOrder;
import java.util.Queue;
import java.nio.ByteBuffer;
import io.netty.util.ResourceLeak;

abstract class PooledByteBuf<T> extends AbstractReferenceCountedByteBuf
{
    private final ResourceLeak leak;
    protected PoolChunk<T> chunk;
    protected long handle;
    protected T memory;
    protected int offset;
    protected int length;
    private int maxLength;
    private ByteBuffer tmpNioBuf;
    private Queue<Allocation<T>> suspendedDeallocations;
    
    protected PooledByteBuf(final int maxCapacity) {
        super(maxCapacity);
        this.leak = PooledByteBuf.leakDetector.open(this);
    }
    
    void init(final PoolChunk<T> chunk, final long handle, final int offset, final int length, final int maxLength) {
        assert handle >= 0L;
        assert chunk != null;
        this.chunk = chunk;
        this.handle = handle;
        this.memory = chunk.memory;
        this.offset = offset;
        this.length = length;
        this.maxLength = maxLength;
        this.setIndex(0, 0);
        this.tmpNioBuf = null;
    }
    
    void initUnpooled(final PoolChunk<T> chunk, final int length) {
        assert chunk != null;
        this.chunk = chunk;
        this.handle = 0L;
        this.memory = chunk.memory;
        this.offset = 0;
        this.maxLength = length;
        this.length = length;
        this.setIndex(0, 0);
        this.tmpNioBuf = null;
    }
    
    @Override
    public final int capacity() {
        return this.length;
    }
    
    @Override
    public final ByteBuf capacity(final int newCapacity) {
        this.ensureAccessible();
        if (this.chunk.unpooled) {
            if (newCapacity == this.length) {
                return this;
            }
        }
        else if (newCapacity > this.length) {
            if (newCapacity <= this.maxLength) {
                this.length = newCapacity;
                return this;
            }
        }
        else {
            if (newCapacity >= this.length) {
                return this;
            }
            if (newCapacity > this.maxLength >>> 1) {
                if (this.maxLength > 512) {
                    this.length = newCapacity;
                    this.setIndex(Math.min(this.readerIndex(), newCapacity), Math.min(this.writerIndex(), newCapacity));
                    return this;
                }
                if (newCapacity > this.maxLength - 16) {
                    this.length = newCapacity;
                    this.setIndex(Math.min(this.readerIndex(), newCapacity), Math.min(this.writerIndex(), newCapacity));
                    return this;
                }
            }
        }
        if (this.suspendedDeallocations == null) {
            this.chunk.arena.reallocate(this, newCapacity, true);
        }
        else {
            final Allocation<T> old = new Allocation<T>(this.chunk, this.handle);
            this.chunk.arena.reallocate(this, newCapacity, false);
            this.suspendedDeallocations.add(old);
        }
        return this;
    }
    
    @Override
    public final ByteBufAllocator alloc() {
        return this.chunk.arena.parent;
    }
    
    @Override
    public final ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }
    
    @Override
    public final ByteBuf unwrap() {
        return null;
    }
    
    protected final ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf = this.tmpNioBuf;
        if (tmpNioBuf == null) {
            tmpNioBuf = (this.tmpNioBuf = this.newInternalNioBuffer(this.memory));
        }
        return tmpNioBuf;
    }
    
    protected abstract ByteBuffer newInternalNioBuffer(final T p0);
    
    @Override
    public final ByteBuf suspendIntermediaryDeallocations() {
        this.ensureAccessible();
        if (this.suspendedDeallocations == null) {
            this.suspendedDeallocations = new ArrayDeque<Allocation<T>>(2);
        }
        return this;
    }
    
    @Override
    public final ByteBuf resumeIntermediaryDeallocations() {
        if (this.suspendedDeallocations == null) {
            return this;
        }
        final Queue<Allocation<T>> suspendedDeallocations = this.suspendedDeallocations;
        this.suspendedDeallocations = null;
        if (suspendedDeallocations.isEmpty()) {
            return this;
        }
        for (final Allocation<T> a : suspendedDeallocations) {
            a.chunk.arena.free(a.chunk, a.handle);
        }
        return this;
    }
    
    @Override
    protected final void deallocate() {
        if (this.handle >= 0L) {
            this.resumeIntermediaryDeallocations();
            final long handle = this.handle;
            this.handle = -1L;
            this.memory = null;
            this.chunk.arena.free(this.chunk, handle);
            this.leak.close();
        }
    }
    
    protected final int idx(final int index) {
        return this.offset + index;
    }
    
    private static final class Allocation<T>
    {
        final PoolChunk<T> chunk;
        final long handle;
        
        Allocation(final PoolChunk<T> chunk, final long handle) {
            this.chunk = chunk;
            this.handle = handle;
        }
    }
}
