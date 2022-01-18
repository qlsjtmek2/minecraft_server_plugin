// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

public class DefaultByteBufHolder implements ByteBufHolder
{
    private final ByteBuf data;
    
    public DefaultByteBufHolder(final ByteBuf data) {
        if (data == null) {
            throw new NullPointerException("data");
        }
        if (data.unwrap() != null && !(data instanceof SwappedByteBuf)) {
            throw new IllegalArgumentException("Only not-derived ByteBuf instance are supported, you used: " + data.getClass().getSimpleName());
        }
        this.data = data;
    }
    
    @Override
    public ByteBuf data() {
        if (this.data.refCnt() <= 0) {
            throw new IllegalBufferAccessException();
        }
        return this.data;
    }
    
    @Override
    public ByteBufHolder copy() {
        return new DefaultByteBufHolder(this.data.copy());
    }
    
    @Override
    public int refCnt() {
        return this.data.refCnt();
    }
    
    @Override
    public ByteBufHolder retain() {
        this.data.retain();
        return this;
    }
    
    @Override
    public ByteBufHolder retain(final int increment) {
        this.data.retain(increment);
        return this;
    }
    
    @Override
    public boolean release() {
        return this.data.release();
    }
    
    @Override
    public boolean release(final int decrement) {
        return this.data.release(decrement);
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '(' + this.data().toString() + ')';
    }
}
