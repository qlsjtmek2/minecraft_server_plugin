// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

public abstract class AbstractDerivedByteBuf extends AbstractByteBuf
{
    protected AbstractDerivedByteBuf(final int maxCapacity) {
        super(maxCapacity);
    }
    
    @Override
    public final int refCnt() {
        return this.unwrap().refCnt();
    }
    
    @Override
    public final ByteBuf retain() {
        this.unwrap().retain();
        return this;
    }
    
    @Override
    public final ByteBuf retain(final int increment) {
        this.unwrap().retain(increment);
        return this;
    }
    
    @Override
    public final boolean release() {
        return this.unwrap().release();
    }
    
    @Override
    public final boolean release(final int decrement) {
        return this.unwrap().release(decrement);
    }
    
    @Override
    public final ByteBuf suspendIntermediaryDeallocations() {
        throw new UnsupportedOperationException("derived");
    }
    
    @Override
    public final ByteBuf resumeIntermediaryDeallocations() {
        throw new UnsupportedOperationException("derived");
    }
}
