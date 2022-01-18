// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import io.netty.buffer.Unpooled;
import io.netty.buffer.ReferenceCounted;
import io.netty.buffer.Buf;
import java.nio.charset.Charset;
import java.nio.channels.ScatteringByteChannel;
import java.io.InputStream;
import java.nio.ByteOrder;
import io.netty.buffer.ByteBufIndexFinder;
import java.io.OutputStream;
import java.nio.channels.GatheringByteChannel;
import java.nio.ByteBuffer;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.BufType;
import io.netty.buffer.SwappedByteBuf;
import io.netty.util.Signal;
import io.netty.buffer.ByteBuf;

final class ReplayingDecoderBuffer implements ByteBuf
{
    private static final Signal REPLAY;
    private final ByteBuf buffer;
    private final SwappedByteBuf swapped;
    private boolean terminated;
    static final ReplayingDecoderBuffer EMPTY_BUFFER;
    
    ReplayingDecoderBuffer(final ByteBuf buffer) {
        this.buffer = buffer;
        this.swapped = new SwappedByteBuf(this);
    }
    
    void terminate() {
        this.terminated = true;
    }
    
    @Override
    public int capacity() {
        if (this.terminated) {
            return this.buffer.capacity();
        }
        return Integer.MAX_VALUE;
    }
    
    @Override
    public ByteBuf capacity(final int newCapacity) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public int maxCapacity() {
        return this.capacity();
    }
    
    @Override
    public BufType type() {
        return BufType.BYTE;
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.buffer.alloc();
    }
    
    @Override
    public boolean isDirect() {
        return this.buffer.isDirect();
    }
    
    @Override
    public boolean hasArray() {
        return false;
    }
    
    @Override
    public byte[] array() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int arrayOffset() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean hasMemoryAddress() {
        return false;
    }
    
    @Override
    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ByteBuf clear() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public boolean equals(final Object obj) {
        return this == obj;
    }
    
    @Override
    public int compareTo(final ByteBuf buffer) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf copy() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf copy(final int index, final int length) {
        this.checkIndex(index, length);
        return this.buffer.copy(index, length);
    }
    
    @Override
    public ByteBuf discardReadBytes() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf ensureWritable(final int writableBytes) {
        throw new UnreplayableOperationException();
    }
    
    @Deprecated
    @Override
    public ByteBuf ensureWritableBytes(final int writableBytes) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public int ensureWritable(final int minWritableBytes, final boolean force) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf duplicate() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public boolean getBoolean(final int index) {
        this.checkIndex(index, 1);
        return this.buffer.getBoolean(index);
    }
    
    @Override
    public byte getByte(final int index) {
        this.checkIndex(index, 1);
        return this.buffer.getByte(index);
    }
    
    @Override
    public short getUnsignedByte(final int index) {
        this.checkIndex(index, 1);
        return this.buffer.getUnsignedByte(index);
    }
    
    @Override
    public ByteBuf getBytes(final int index, final byte[] dst, final int dstIndex, final int length) {
        this.checkIndex(index, length);
        this.buffer.getBytes(index, dst, dstIndex, length);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int index, final byte[] dst) {
        this.checkIndex(index, dst.length);
        this.buffer.getBytes(index, dst);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int index, final ByteBuffer dst) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf getBytes(final int index, final ByteBuf dst, final int dstIndex, final int length) {
        this.checkIndex(index, length);
        this.buffer.getBytes(index, dst, dstIndex, length);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int index, final ByteBuf dst, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf getBytes(final int index, final ByteBuf dst) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public int getBytes(final int index, final GatheringByteChannel out, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf getBytes(final int index, final OutputStream out, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public int getInt(final int index) {
        this.checkIndex(index, 4);
        return this.buffer.getInt(index);
    }
    
    @Override
    public long getUnsignedInt(final int index) {
        this.checkIndex(index, 4);
        return this.buffer.getUnsignedInt(index);
    }
    
    @Override
    public long getLong(final int index) {
        this.checkIndex(index, 8);
        return this.buffer.getLong(index);
    }
    
    @Override
    public int getMedium(final int index) {
        this.checkIndex(index, 3);
        return this.buffer.getMedium(index);
    }
    
    @Override
    public int getUnsignedMedium(final int index) {
        this.checkIndex(index, 3);
        return this.buffer.getUnsignedMedium(index);
    }
    
    @Override
    public short getShort(final int index) {
        this.checkIndex(index, 2);
        return this.buffer.getShort(index);
    }
    
    @Override
    public int getUnsignedShort(final int index) {
        this.checkIndex(index, 2);
        return this.buffer.getUnsignedShort(index);
    }
    
    @Override
    public char getChar(final int index) {
        this.checkIndex(index, 2);
        return this.buffer.getChar(index);
    }
    
    @Override
    public float getFloat(final int index) {
        this.checkIndex(index, 4);
        return this.buffer.getFloat(index);
    }
    
    @Override
    public double getDouble(final int index) {
        this.checkIndex(index, 8);
        return this.buffer.getDouble(index);
    }
    
    @Override
    public int hashCode() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public int indexOf(final int fromIndex, final int toIndex, final byte value) {
        final int endIndex = this.buffer.indexOf(fromIndex, toIndex, value);
        if (endIndex < 0) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return endIndex;
    }
    
    @Override
    public int indexOf(final int fromIndex, final int toIndex, final ByteBufIndexFinder indexFinder) {
        final int endIndex = this.buffer.indexOf(fromIndex, toIndex, indexFinder);
        if (endIndex < 0) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return endIndex;
    }
    
    @Override
    public int bytesBefore(final byte value) {
        final int bytes = this.buffer.bytesBefore(value);
        if (bytes < 0) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return bytes;
    }
    
    @Override
    public int bytesBefore(final ByteBufIndexFinder indexFinder) {
        final int bytes = this.buffer.bytesBefore(indexFinder);
        if (bytes < 0) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return bytes;
    }
    
    @Override
    public int bytesBefore(final int length, final byte value) {
        this.checkReadableBytes(length);
        final int bytes = this.buffer.bytesBefore(length, value);
        if (bytes < 0) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return bytes;
    }
    
    @Override
    public int bytesBefore(final int length, final ByteBufIndexFinder indexFinder) {
        this.checkReadableBytes(length);
        final int bytes = this.buffer.bytesBefore(length, indexFinder);
        if (bytes < 0) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return bytes;
    }
    
    @Override
    public int bytesBefore(final int index, final int length, final byte value) {
        final int bytes = this.buffer.bytesBefore(index, length, value);
        if (bytes < 0) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return bytes;
    }
    
    @Override
    public int bytesBefore(final int index, final int length, final ByteBufIndexFinder indexFinder) {
        final int bytes = this.buffer.bytesBefore(index, length, indexFinder);
        if (bytes < 0) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
        return bytes;
    }
    
    @Override
    public ByteBuf markReaderIndex() {
        this.buffer.markReaderIndex();
        return this;
    }
    
    @Override
    public ByteBuf markWriterIndex() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteOrder order() {
        return this.buffer.order();
    }
    
    @Override
    public ByteBuf order(final ByteOrder endianness) {
        if (endianness == null) {
            throw new NullPointerException("endianness");
        }
        if (endianness == this.order()) {
            return this;
        }
        return this.swapped;
    }
    
    @Override
    public boolean isReadable() {
        return !this.terminated || this.buffer.isReadable();
    }
    
    @Deprecated
    @Override
    public boolean readable() {
        return this.isReadable();
    }
    
    @Override
    public boolean isReadable(final int size) {
        return !this.terminated || this.buffer.isReadable(size);
    }
    
    @Override
    public int readableBytes() {
        if (this.terminated) {
            return this.buffer.readableBytes();
        }
        return Integer.MAX_VALUE - this.buffer.readerIndex();
    }
    
    @Override
    public boolean readBoolean() {
        this.checkReadableBytes(1);
        return this.buffer.readBoolean();
    }
    
    @Override
    public byte readByte() {
        this.checkReadableBytes(1);
        return this.buffer.readByte();
    }
    
    @Override
    public short readUnsignedByte() {
        this.checkReadableBytes(1);
        return this.buffer.readUnsignedByte();
    }
    
    @Override
    public ByteBuf readBytes(final byte[] dst, final int dstIndex, final int length) {
        this.checkReadableBytes(length);
        this.buffer.readBytes(dst, dstIndex, length);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final byte[] dst) {
        this.checkReadableBytes(dst.length);
        this.buffer.readBytes(dst);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuffer dst) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf dst, final int dstIndex, final int length) {
        this.checkReadableBytes(length);
        this.buffer.readBytes(dst, dstIndex, length);
        return this;
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf dst, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf dst) {
        this.checkReadableBytes(dst.writableBytes());
        this.buffer.readBytes(dst);
        return this;
    }
    
    @Override
    public int readBytes(final GatheringByteChannel out, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf readBytes(final int length) {
        this.checkReadableBytes(length);
        return this.buffer.readBytes(length);
    }
    
    @Override
    public ByteBuf readSlice(final int length) {
        this.checkReadableBytes(length);
        return this.buffer.readSlice(length);
    }
    
    @Override
    public ByteBuf readBytes(final OutputStream out, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public int readerIndex() {
        return this.buffer.readerIndex();
    }
    
    @Override
    public ByteBuf readerIndex(final int readerIndex) {
        this.buffer.readerIndex(readerIndex);
        return this;
    }
    
    @Override
    public int readInt() {
        this.checkReadableBytes(4);
        return this.buffer.readInt();
    }
    
    @Override
    public long readUnsignedInt() {
        this.checkReadableBytes(4);
        return this.buffer.readUnsignedInt();
    }
    
    @Override
    public long readLong() {
        this.checkReadableBytes(8);
        return this.buffer.readLong();
    }
    
    @Override
    public int readMedium() {
        this.checkReadableBytes(3);
        return this.buffer.readMedium();
    }
    
    @Override
    public int readUnsignedMedium() {
        this.checkReadableBytes(3);
        return this.buffer.readUnsignedMedium();
    }
    
    @Override
    public short readShort() {
        this.checkReadableBytes(2);
        return this.buffer.readShort();
    }
    
    @Override
    public int readUnsignedShort() {
        this.checkReadableBytes(2);
        return this.buffer.readUnsignedShort();
    }
    
    @Override
    public char readChar() {
        this.checkReadableBytes(2);
        return this.buffer.readChar();
    }
    
    @Override
    public float readFloat() {
        this.checkReadableBytes(4);
        return this.buffer.readFloat();
    }
    
    @Override
    public double readDouble() {
        this.checkReadableBytes(8);
        return this.buffer.readDouble();
    }
    
    @Override
    public ByteBuf resetReaderIndex() {
        this.buffer.resetReaderIndex();
        return this;
    }
    
    @Override
    public ByteBuf resetWriterIndex() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setBoolean(final int index, final boolean value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setByte(final int index, final int value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setBytes(final int index, final byte[] src, final int srcIndex, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setBytes(final int index, final byte[] src) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setBytes(final int index, final ByteBuffer src) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setBytes(final int index, final ByteBuf src, final int srcIndex, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setBytes(final int index, final ByteBuf src, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setBytes(final int index, final ByteBuf src) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public int setBytes(final int index, final InputStream in, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setZero(final int index, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public int setBytes(final int index, final ScatteringByteChannel in, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setIndex(final int readerIndex, final int writerIndex) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setInt(final int index, final int value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setLong(final int index, final long value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setMedium(final int index, final int value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setShort(final int index, final int value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setChar(final int index, final int value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setFloat(final int index, final float value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf setDouble(final int index, final double value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf skipBytes(final int length) {
        this.checkReadableBytes(length);
        this.buffer.skipBytes(length);
        return this;
    }
    
    @Override
    public ByteBuf slice() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf slice(final int index, final int length) {
        this.checkIndex(index, length);
        return this.buffer.slice(index, length);
    }
    
    @Override
    public int nioBufferCount() {
        return this.buffer.nioBufferCount();
    }
    
    @Override
    public ByteBuffer nioBuffer() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuffer nioBuffer(final int index, final int length) {
        this.checkIndex(index, length);
        return this.buffer.nioBuffer(index, length);
    }
    
    @Override
    public ByteBuffer[] nioBuffers() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int index, final int length) {
        this.checkIndex(index, length);
        return this.buffer.nioBuffers(index, length);
    }
    
    @Override
    public String toString(final int index, final int length, final Charset charset) {
        this.checkIndex(index, length);
        return this.buffer.toString(index, length, charset);
    }
    
    @Override
    public String toString(final Charset charsetName) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '(' + "ridx=" + this.readerIndex() + ", " + "widx=" + this.writerIndex() + ')';
    }
    
    @Override
    public boolean isWritable() {
        return false;
    }
    
    @Deprecated
    @Override
    public boolean writable() {
        return false;
    }
    
    @Override
    public boolean isWritable(final int size) {
        return false;
    }
    
    @Override
    public int writableBytes() {
        return 0;
    }
    
    @Override
    public int maxWritableBytes() {
        return 0;
    }
    
    @Override
    public ByteBuf writeBoolean(final boolean value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeByte(final int value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] src, final int srcIndex, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] src) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuffer src) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf src, final int srcIndex, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf src, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf src) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public int writeBytes(final InputStream in, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public int writeBytes(final ScatteringByteChannel in, final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeInt(final int value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeLong(final long value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeMedium(final int value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeZero(final int length) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public int writerIndex() {
        return this.buffer.writerIndex();
    }
    
    @Override
    public ByteBuf writerIndex(final int writerIndex) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeShort(final int value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeChar(final int value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeFloat(final float value) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf writeDouble(final double value) {
        throw new UnreplayableOperationException();
    }
    
    private void checkIndex(final int index, final int length) {
        if (index + length > this.buffer.writerIndex()) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
    }
    
    private void checkReadableBytes(final int readableBytes) {
        if (this.buffer.readableBytes() < readableBytes) {
            throw ReplayingDecoderBuffer.REPLAY;
        }
    }
    
    @Override
    public ByteBuf discardSomeReadBytes() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public int refCnt() {
        return this.buffer.refCnt();
    }
    
    @Override
    public ByteBuf retain() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf retain(final int increment) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public boolean release() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public boolean release(final int decrement) {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf suspendIntermediaryDeallocations() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf resumeIntermediaryDeallocations() {
        throw new UnreplayableOperationException();
    }
    
    @Override
    public ByteBuf unwrap() {
        throw new UnreplayableOperationException();
    }
    
    static {
        REPLAY = ReplayingDecoder.REPLAY;
        (EMPTY_BUFFER = new ReplayingDecoderBuffer(Unpooled.EMPTY_BUFFER)).terminate();
    }
}
