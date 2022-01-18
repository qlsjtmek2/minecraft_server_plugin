// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;

public final class UnpooledByteBufAllocator extends AbstractByteBufAllocator
{
    public static final UnpooledByteBufAllocator DEFAULT;
    
    public UnpooledByteBufAllocator(final boolean preferDirect) {
        super(preferDirect);
    }
    
    @Override
    protected ByteBuf newHeapBuffer(final int initialCapacity, final int maxCapacity) {
        return new UnpooledHeapByteBuf(this, initialCapacity, maxCapacity);
    }
    
    @Override
    protected ByteBuf newDirectBuffer(final int initialCapacity, final int maxCapacity) {
        if (PlatformDependent.hasUnsafe()) {
            return new UnpooledUnsafeDirectByteBuf(this, initialCapacity, maxCapacity);
        }
        return new UnpooledDirectByteBuf(this, initialCapacity, maxCapacity);
    }
    
    static {
        DEFAULT = new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());
    }
}
