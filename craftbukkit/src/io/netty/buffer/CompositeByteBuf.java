// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.List;

public interface CompositeByteBuf extends ByteBuf, Iterable<ByteBuf>
{
    CompositeByteBuf addComponent(final ByteBuf p0);
    
    CompositeByteBuf addComponent(final int p0, final ByteBuf p1);
    
    CompositeByteBuf addComponents(final ByteBuf... p0);
    
    CompositeByteBuf addComponents(final Iterable<ByteBuf> p0);
    
    CompositeByteBuf addComponents(final int p0, final ByteBuf... p1);
    
    CompositeByteBuf addComponents(final int p0, final Iterable<ByteBuf> p1);
    
    CompositeByteBuf removeComponent(final int p0);
    
    CompositeByteBuf removeComponents(final int p0, final int p1);
    
    int numComponents();
    
    int maxNumComponents();
    
    ByteBuf component(final int p0);
    
    ByteBuf componentAtOffset(final int p0);
    
    CompositeByteBuf discardReadComponents();
    
    CompositeByteBuf consolidate();
    
    CompositeByteBuf consolidate(final int p0, final int p1);
    
    int toComponentIndex(final int p0);
    
    int toByteIndex(final int p0);
    
    List<ByteBuf> decompose(final int p0, final int p1);
    
    CompositeByteBuf capacity(final int p0);
    
    CompositeByteBuf readerIndex(final int p0);
    
    CompositeByteBuf writerIndex(final int p0);
    
    CompositeByteBuf setIndex(final int p0, final int p1);
    
    CompositeByteBuf clear();
    
    CompositeByteBuf markReaderIndex();
    
    CompositeByteBuf resetReaderIndex();
    
    CompositeByteBuf markWriterIndex();
    
    CompositeByteBuf resetWriterIndex();
    
    CompositeByteBuf discardReadBytes();
    
    CompositeByteBuf discardSomeReadBytes();
    
    CompositeByteBuf ensureWritable(final int p0);
    
    CompositeByteBuf getBytes(final int p0, final ByteBuf p1);
    
    CompositeByteBuf getBytes(final int p0, final ByteBuf p1, final int p2);
    
    CompositeByteBuf getBytes(final int p0, final ByteBuf p1, final int p2, final int p3);
    
    CompositeByteBuf getBytes(final int p0, final byte[] p1);
    
    CompositeByteBuf getBytes(final int p0, final byte[] p1, final int p2, final int p3);
    
    CompositeByteBuf getBytes(final int p0, final ByteBuffer p1);
    
    CompositeByteBuf getBytes(final int p0, final OutputStream p1, final int p2) throws IOException;
    
    CompositeByteBuf setBoolean(final int p0, final boolean p1);
    
    CompositeByteBuf setByte(final int p0, final int p1);
    
    CompositeByteBuf setShort(final int p0, final int p1);
    
    CompositeByteBuf setMedium(final int p0, final int p1);
    
    CompositeByteBuf setInt(final int p0, final int p1);
    
    CompositeByteBuf setLong(final int p0, final long p1);
    
    CompositeByteBuf setChar(final int p0, final int p1);
    
    CompositeByteBuf setFloat(final int p0, final float p1);
    
    CompositeByteBuf setDouble(final int p0, final double p1);
    
    CompositeByteBuf setBytes(final int p0, final ByteBuf p1);
    
    CompositeByteBuf setBytes(final int p0, final ByteBuf p1, final int p2);
    
    CompositeByteBuf setBytes(final int p0, final ByteBuf p1, final int p2, final int p3);
    
    CompositeByteBuf setBytes(final int p0, final byte[] p1);
    
    CompositeByteBuf setBytes(final int p0, final byte[] p1, final int p2, final int p3);
    
    CompositeByteBuf setBytes(final int p0, final ByteBuffer p1);
    
    CompositeByteBuf setZero(final int p0, final int p1);
    
    CompositeByteBuf readBytes(final ByteBuf p0);
    
    CompositeByteBuf readBytes(final ByteBuf p0, final int p1);
    
    CompositeByteBuf readBytes(final ByteBuf p0, final int p1, final int p2);
    
    CompositeByteBuf readBytes(final byte[] p0);
    
    CompositeByteBuf readBytes(final byte[] p0, final int p1, final int p2);
    
    CompositeByteBuf readBytes(final ByteBuffer p0);
    
    CompositeByteBuf readBytes(final OutputStream p0, final int p1) throws IOException;
    
    CompositeByteBuf skipBytes(final int p0);
    
    CompositeByteBuf writeBoolean(final boolean p0);
    
    CompositeByteBuf writeByte(final int p0);
    
    CompositeByteBuf writeShort(final int p0);
    
    CompositeByteBuf writeMedium(final int p0);
    
    CompositeByteBuf writeInt(final int p0);
    
    CompositeByteBuf writeLong(final long p0);
    
    CompositeByteBuf writeChar(final int p0);
    
    CompositeByteBuf writeFloat(final float p0);
    
    CompositeByteBuf writeDouble(final double p0);
    
    CompositeByteBuf writeBytes(final ByteBuf p0);
    
    CompositeByteBuf writeBytes(final ByteBuf p0, final int p1);
    
    CompositeByteBuf writeBytes(final ByteBuf p0, final int p1, final int p2);
    
    CompositeByteBuf writeBytes(final byte[] p0);
    
    CompositeByteBuf writeBytes(final byte[] p0, final int p1, final int p2);
    
    CompositeByteBuf writeBytes(final ByteBuffer p0);
    
    CompositeByteBuf writeZero(final int p0);
    
    CompositeByteBuf suspendIntermediaryDeallocations();
    
    CompositeByteBuf resumeIntermediaryDeallocations();
    
    CompositeByteBuf retain(final int p0);
    
    CompositeByteBuf retain();
}
