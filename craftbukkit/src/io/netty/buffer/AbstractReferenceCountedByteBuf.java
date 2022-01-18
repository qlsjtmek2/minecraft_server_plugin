// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public abstract class AbstractReferenceCountedByteBuf extends AbstractByteBuf
{
    private static final AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> refCntUpdater;
    private static final long REFCNT_FIELD_OFFSET;
    private volatile int refCnt;
    
    protected AbstractReferenceCountedByteBuf(final int maxCapacity) {
        super(maxCapacity);
        this.refCnt = 1;
    }
    
    @Override
    public final int refCnt() {
        if (AbstractReferenceCountedByteBuf.REFCNT_FIELD_OFFSET >= 0L) {
            return PlatformDependent.getInt(this, AbstractReferenceCountedByteBuf.REFCNT_FIELD_OFFSET);
        }
        return this.refCnt;
    }
    
    @Override
    public ByteBuf retain() {
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt == 0) {
                throw new IllegalBufferAccessException();
            }
            if (refCnt == Integer.MAX_VALUE) {
                throw new IllegalBufferAccessException("refCnt overflow");
            }
            if (AbstractReferenceCountedByteBuf.refCntUpdater.compareAndSet(this, refCnt, refCnt + 1)) {
                return this;
            }
        }
    }
    
    @Override
    public ByteBuf retain(final int increment) {
        if (increment <= 0) {
            throw new IllegalArgumentException("increment: " + increment + " (expected: > 0)");
        }
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt == 0) {
                throw new IllegalBufferAccessException();
            }
            if (refCnt > Integer.MAX_VALUE - increment) {
                throw new IllegalBufferAccessException("refCnt overflow");
            }
            if (AbstractReferenceCountedByteBuf.refCntUpdater.compareAndSet(this, refCnt, refCnt + increment)) {
                return this;
            }
        }
    }
    
    @Override
    public final boolean release() {
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt == 0) {
                throw new IllegalBufferAccessException();
            }
            if (!AbstractReferenceCountedByteBuf.refCntUpdater.compareAndSet(this, refCnt, refCnt - 1)) {
                continue;
            }
            if (refCnt == 1) {
                this.deallocate();
                return true;
            }
            return false;
        }
    }
    
    @Override
    public final boolean release(final int decrement) {
        if (decrement <= 0) {
            throw new IllegalArgumentException("decrement: " + decrement + " (expected: > 0)");
        }
        while (true) {
            final int refCnt = this.refCnt;
            if (refCnt < decrement) {
                throw new IllegalBufferAccessException();
            }
            if (!AbstractReferenceCountedByteBuf.refCntUpdater.compareAndSet(this, refCnt, refCnt - decrement)) {
                continue;
            }
            if (refCnt == decrement) {
                this.deallocate();
                return true;
            }
            return false;
        }
    }
    
    protected abstract void deallocate();
    
    static {
        refCntUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCountedByteBuf.class, "refCnt");
        long refCntFieldOffset = -1L;
        try {
            if (PlatformDependent.hasUnsafe()) {
                refCntFieldOffset = PlatformDependent.objectFieldOffset(AbstractReferenceCountedByteBuf.class.getDeclaredField("refCnt"));
            }
        }
        catch (Throwable t) {}
        REFCNT_FIELD_OFFSET = refCntFieldOffset;
    }
}
