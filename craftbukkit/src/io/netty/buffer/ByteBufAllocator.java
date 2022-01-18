// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

public interface ByteBufAllocator
{
    ByteBuf buffer();
    
    ByteBuf buffer(final int p0);
    
    ByteBuf buffer(final int p0, final int p1);
    
    ByteBuf ioBuffer();
    
    ByteBuf ioBuffer(final int p0);
    
    ByteBuf ioBuffer(final int p0, final int p1);
    
    ByteBuf heapBuffer();
    
    ByteBuf heapBuffer(final int p0);
    
    ByteBuf heapBuffer(final int p0, final int p1);
    
    ByteBuf directBuffer();
    
    ByteBuf directBuffer(final int p0);
    
    ByteBuf directBuffer(final int p0, final int p1);
    
    CompositeByteBuf compositeBuffer();
    
    CompositeByteBuf compositeBuffer(final int p0);
    
    CompositeByteBuf compositeHeapBuffer();
    
    CompositeByteBuf compositeHeapBuffer(final int p0);
    
    CompositeByteBuf compositeDirectBuffer();
    
    CompositeByteBuf compositeDirectBuffer(final int p0);
}
