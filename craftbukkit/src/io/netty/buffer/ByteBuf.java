// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import java.nio.charset.Charset;
import java.nio.channels.ScatteringByteChannel;
import java.io.InputStream;
import java.nio.channels.GatheringByteChannel;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public interface ByteBuf extends Buf, Comparable<ByteBuf>
{
    int capacity();
    
    ByteBuf capacity(final int p0);
    
    int maxCapacity();
    
    ByteBufAllocator alloc();
    
    ByteOrder order();
    
    ByteBuf order(final ByteOrder p0);
    
    ByteBuf unwrap();
    
    boolean isDirect();
    
    int readerIndex();
    
    ByteBuf readerIndex(final int p0);
    
    int writerIndex();
    
    ByteBuf writerIndex(final int p0);
    
    ByteBuf setIndex(final int p0, final int p1);
    
    int readableBytes();
    
    int writableBytes();
    
    int maxWritableBytes();
    
    boolean isReadable();
    
    @Deprecated
    boolean readable();
    
    boolean isWritable();
    
    @Deprecated
    boolean writable();
    
    ByteBuf clear();
    
    ByteBuf markReaderIndex();
    
    ByteBuf resetReaderIndex();
    
    ByteBuf markWriterIndex();
    
    ByteBuf resetWriterIndex();
    
    ByteBuf discardReadBytes();
    
    ByteBuf discardSomeReadBytes();
    
    ByteBuf ensureWritable(final int p0);
    
    @Deprecated
    ByteBuf ensureWritableBytes(final int p0);
    
    int ensureWritable(final int p0, final boolean p1);
    
    boolean getBoolean(final int p0);
    
    byte getByte(final int p0);
    
    short getUnsignedByte(final int p0);
    
    short getShort(final int p0);
    
    int getUnsignedShort(final int p0);
    
    int getMedium(final int p0);
    
    int getUnsignedMedium(final int p0);
    
    int getInt(final int p0);
    
    long getUnsignedInt(final int p0);
    
    long getLong(final int p0);
    
    char getChar(final int p0);
    
    float getFloat(final int p0);
    
    double getDouble(final int p0);
    
    ByteBuf getBytes(final int p0, final ByteBuf p1);
    
    ByteBuf getBytes(final int p0, final ByteBuf p1, final int p2);
    
    ByteBuf getBytes(final int p0, final ByteBuf p1, final int p2, final int p3);
    
    ByteBuf getBytes(final int p0, final byte[] p1);
    
    ByteBuf getBytes(final int p0, final byte[] p1, final int p2, final int p3);
    
    ByteBuf getBytes(final int p0, final ByteBuffer p1);
    
    ByteBuf getBytes(final int p0, final OutputStream p1, final int p2) throws IOException;
    
    int getBytes(final int p0, final GatheringByteChannel p1, final int p2) throws IOException;
    
    ByteBuf setBoolean(final int p0, final boolean p1);
    
    ByteBuf setByte(final int p0, final int p1);
    
    ByteBuf setShort(final int p0, final int p1);
    
    ByteBuf setMedium(final int p0, final int p1);
    
    ByteBuf setInt(final int p0, final int p1);
    
    ByteBuf setLong(final int p0, final long p1);
    
    ByteBuf setChar(final int p0, final int p1);
    
    ByteBuf setFloat(final int p0, final float p1);
    
    ByteBuf setDouble(final int p0, final double p1);
    
    ByteBuf setBytes(final int p0, final ByteBuf p1);
    
    ByteBuf setBytes(final int p0, final ByteBuf p1, final int p2);
    
    ByteBuf setBytes(final int p0, final ByteBuf p1, final int p2, final int p3);
    
    ByteBuf setBytes(final int p0, final byte[] p1);
    
    ByteBuf setBytes(final int p0, final byte[] p1, final int p2, final int p3);
    
    ByteBuf setBytes(final int p0, final ByteBuffer p1);
    
    int setBytes(final int p0, final InputStream p1, final int p2) throws IOException;
    
    int setBytes(final int p0, final ScatteringByteChannel p1, final int p2) throws IOException;
    
    ByteBuf setZero(final int p0, final int p1);
    
    boolean readBoolean();
    
    byte readByte();
    
    short readUnsignedByte();
    
    short readShort();
    
    int readUnsignedShort();
    
    int readMedium();
    
    int readUnsignedMedium();
    
    int readInt();
    
    long readUnsignedInt();
    
    long readLong();
    
    char readChar();
    
    float readFloat();
    
    double readDouble();
    
    ByteBuf readBytes(final int p0);
    
    ByteBuf readSlice(final int p0);
    
    ByteBuf readBytes(final ByteBuf p0);
    
    ByteBuf readBytes(final ByteBuf p0, final int p1);
    
    ByteBuf readBytes(final ByteBuf p0, final int p1, final int p2);
    
    ByteBuf readBytes(final byte[] p0);
    
    ByteBuf readBytes(final byte[] p0, final int p1, final int p2);
    
    ByteBuf readBytes(final ByteBuffer p0);
    
    ByteBuf readBytes(final OutputStream p0, final int p1) throws IOException;
    
    int readBytes(final GatheringByteChannel p0, final int p1) throws IOException;
    
    ByteBuf skipBytes(final int p0);
    
    ByteBuf writeBoolean(final boolean p0);
    
    ByteBuf writeByte(final int p0);
    
    ByteBuf writeShort(final int p0);
    
    ByteBuf writeMedium(final int p0);
    
    ByteBuf writeInt(final int p0);
    
    ByteBuf writeLong(final long p0);
    
    ByteBuf writeChar(final int p0);
    
    ByteBuf writeFloat(final float p0);
    
    ByteBuf writeDouble(final double p0);
    
    ByteBuf writeBytes(final ByteBuf p0);
    
    ByteBuf writeBytes(final ByteBuf p0, final int p1);
    
    ByteBuf writeBytes(final ByteBuf p0, final int p1, final int p2);
    
    ByteBuf writeBytes(final byte[] p0);
    
    ByteBuf writeBytes(final byte[] p0, final int p1, final int p2);
    
    ByteBuf writeBytes(final ByteBuffer p0);
    
    int writeBytes(final InputStream p0, final int p1) throws IOException;
    
    int writeBytes(final ScatteringByteChannel p0, final int p1) throws IOException;
    
    ByteBuf writeZero(final int p0);
    
    int indexOf(final int p0, final int p1, final byte p2);
    
    int indexOf(final int p0, final int p1, final ByteBufIndexFinder p2);
    
    int bytesBefore(final byte p0);
    
    int bytesBefore(final ByteBufIndexFinder p0);
    
    int bytesBefore(final int p0, final byte p1);
    
    int bytesBefore(final int p0, final ByteBufIndexFinder p1);
    
    int bytesBefore(final int p0, final int p1, final byte p2);
    
    int bytesBefore(final int p0, final int p1, final ByteBufIndexFinder p2);
    
    ByteBuf copy();
    
    ByteBuf copy(final int p0, final int p1);
    
    ByteBuf slice();
    
    ByteBuf slice(final int p0, final int p1);
    
    ByteBuf duplicate();
    
    int nioBufferCount();
    
    ByteBuffer nioBuffer();
    
    ByteBuffer nioBuffer(final int p0, final int p1);
    
    ByteBuffer[] nioBuffers();
    
    ByteBuffer[] nioBuffers(final int p0, final int p1);
    
    boolean hasArray();
    
    byte[] array();
    
    int arrayOffset();
    
    boolean hasMemoryAddress();
    
    long memoryAddress();
    
    String toString(final Charset p0);
    
    String toString(final int p0, final int p1, final Charset p2);
    
    ByteBuf suspendIntermediaryDeallocations();
    
    ByteBuf resumeIntermediaryDeallocations();
    
    int hashCode();
    
    boolean equals(final Object p0);
    
    int compareTo(final ByteBuf p0);
    
    String toString();
    
    ByteBuf retain(final int p0);
    
    ByteBuf retain();
}
