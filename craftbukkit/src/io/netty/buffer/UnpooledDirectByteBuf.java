// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import java.util.Iterator;
import java.util.ArrayDeque;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ScatteringByteChannel;
import java.io.InputStream;
import java.nio.channels.GatheringByteChannel;
import java.io.IOException;
import java.io.OutputStream;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteOrder;
import java.util.Queue;
import java.nio.ByteBuffer;
import io.netty.util.ResourceLeak;

final class UnpooledDirectByteBuf extends AbstractReferenceCountedByteBuf
{
    private final ResourceLeak leak;
    private final ByteBufAllocator alloc;
    private ByteBuffer buffer;
    private ByteBuffer tmpNioBuf;
    private int capacity;
    private boolean doNotFree;
    private Queue<ByteBuffer> suspendedDeallocations;
    
    UnpooledDirectByteBuf(final ByteBufAllocator alloc, final int initialCapacity, final int maxCapacity) {
        super(maxCapacity);
        this.leak = UnpooledDirectByteBuf.leakDetector.open(this);
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity: " + initialCapacity);
        }
        if (maxCapacity < 0) {
            throw new IllegalArgumentException("maxCapacity: " + maxCapacity);
        }
        if (initialCapacity > maxCapacity) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", initialCapacity, maxCapacity));
        }
        this.alloc = alloc;
        this.setByteBuffer(ByteBuffer.allocateDirect(initialCapacity));
    }
    
    UnpooledDirectByteBuf(final ByteBufAllocator alloc, final ByteBuffer initialBuffer, final int maxCapacity) {
        super(maxCapacity);
        this.leak = UnpooledDirectByteBuf.leakDetector.open(this);
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        if (initialBuffer == null) {
            throw new NullPointerException("initialBuffer");
        }
        if (!initialBuffer.isDirect()) {
            throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
        }
        if (initialBuffer.isReadOnly()) {
            throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
        }
        final int initialCapacity = initialBuffer.remaining();
        if (initialCapacity > maxCapacity) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", initialCapacity, maxCapacity));
        }
        this.alloc = alloc;
        this.doNotFree = true;
        this.setByteBuffer(initialBuffer.slice().order(ByteOrder.BIG_ENDIAN));
        this.writerIndex(initialCapacity);
    }
    
    private void setByteBuffer(final ByteBuffer buffer) {
        final ByteBuffer oldBuffer = this.buffer;
        if (oldBuffer != null) {
            if (this.doNotFree) {
                this.doNotFree = false;
            }
            else if (this.suspendedDeallocations == null) {
                PlatformDependent.freeDirectBuffer(oldBuffer);
            }
            else {
                this.suspendedDeallocations.add(oldBuffer);
            }
        }
        this.buffer = buffer;
        this.tmpNioBuf = null;
        this.capacity = buffer.remaining();
    }
    
    @Override
    public boolean isDirect() {
        return true;
    }
    
    @Override
    public int capacity() {
        return this.capacity;
    }
    
    @Override
    public ByteBuf capacity(final int newCapacity) {
        this.ensureAccessible();
        if (newCapacity < 0 || newCapacity > this.maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + newCapacity);
        }
        final int readerIndex = this.readerIndex();
        int writerIndex = this.writerIndex();
        final int oldCapacity = this.capacity;
        if (newCapacity > oldCapacity) {
            final ByteBuffer oldBuffer = this.buffer;
            final ByteBuffer newBuffer = ByteBuffer.allocateDirect(newCapacity);
            oldBuffer.position(readerIndex).limit(writerIndex);
            newBuffer.position(readerIndex).limit(writerIndex);
            newBuffer.put(oldBuffer);
            newBuffer.clear();
            this.setByteBuffer(newBuffer);
        }
        else if (newCapacity < oldCapacity) {
            final ByteBuffer oldBuffer = this.buffer;
            final ByteBuffer newBuffer = ByteBuffer.allocateDirect(newCapacity);
            if (readerIndex < newCapacity) {
                if (writerIndex > newCapacity) {
                    writerIndex = newCapacity;
                    this.writerIndex(newCapacity);
                }
                oldBuffer.position(readerIndex).limit(writerIndex);
                newBuffer.position(readerIndex).limit(writerIndex);
                newBuffer.put(oldBuffer);
                newBuffer.clear();
            }
            else {
                this.setIndex(newCapacity, newCapacity);
            }
            this.setByteBuffer(newBuffer);
        }
        return this;
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.alloc;
    }
    
    @Override
    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }
    
    @Override
    public boolean hasArray() {
        return false;
    }
    
    @Override
    public byte[] array() {
        throw new UnsupportedOperationException("direct buffer");
    }
    
    @Override
    public int arrayOffset() {
        throw new UnsupportedOperationException("direct buffer");
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
    public byte getByte(final int index) {
        this.ensureAccessible();
        return this._getByte(index);
    }
    
    @Override
    protected byte _getByte(final int index) {
        return this.buffer.get(index);
    }
    
    @Override
    public short getShort(final int index) {
        this.ensureAccessible();
        return this._getShort(index);
    }
    
    @Override
    protected short _getShort(final int index) {
        return this.buffer.getShort(index);
    }
    
    @Override
    public int getUnsignedMedium(final int index) {
        this.ensureAccessible();
        return this._getUnsignedMedium(index);
    }
    
    @Override
    protected int _getUnsignedMedium(final int index) {
        return (this.getByte(index) & 0xFF) << 16 | (this.getByte(index + 1) & 0xFF) << 8 | (this.getByte(index + 2) & 0xFF);
    }
    
    @Override
    public int getInt(final int index) {
        this.ensureAccessible();
        return this._getInt(index);
    }
    
    @Override
    protected int _getInt(final int index) {
        return this.buffer.getInt(index);
    }
    
    @Override
    public long getLong(final int index) {
        this.ensureAccessible();
        return this._getLong(index);
    }
    
    @Override
    protected long _getLong(final int index) {
        return this.buffer.getLong(index);
    }
    
    @Override
    public ByteBuf getBytes(int index, final ByteBuf dst, final int dstIndex, final int length) {
        this.checkDstIndex(index, length, dstIndex, dst.capacity());
        if (dst.hasArray()) {
            this.getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
        }
        else if (dst.nioBufferCount() > 0) {
            for (final ByteBuffer bb : dst.nioBuffers(dstIndex, length)) {
                final int bbLen = bb.remaining();
                this.getBytes(index, bb);
                index += bbLen;
            }
        }
        else {
            dst.setBytes(dstIndex, this, index, length);
        }
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int index, final byte[] dst, final int dstIndex, final int length) {
        this.checkDstIndex(index, length, dstIndex, dst.length);
        if (dst == null) {
            throw new NullPointerException("dst");
        }
        if (dstIndex < 0 || dstIndex > dst.length - length) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", dstIndex, length, dst.length));
        }
        final ByteBuffer tmpBuf = this.internalNioBuffer();
        tmpBuf.clear().position(index).limit(index + length);
        tmpBuf.get(dst, dstIndex, length);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int index, final ByteBuffer dst) {
        this.checkIndex(index);
        if (dst == null) {
            throw new NullPointerException("dst");
        }
        final int bytesToCopy = Math.min(this.capacity() - index, dst.remaining());
        final ByteBuffer tmpBuf = this.internalNioBuffer();
        tmpBuf.clear().position(index).limit(index + bytesToCopy);
        dst.put(tmpBuf);
        return this;
    }
    
    @Override
    public ByteBuf setByte(final int index, final int value) {
        this.ensureAccessible();
        this._setByte(index, value);
        return this;
    }
    
    @Override
    protected void _setByte(final int index, final int value) {
        this.buffer.put(index, (byte)value);
    }
    
    @Override
    public ByteBuf setShort(final int index, final int value) {
        this.ensureAccessible();
        this._setShort(index, value);
        return this;
    }
    
    @Override
    protected void _setShort(final int index, final int value) {
        this.buffer.putShort(index, (short)value);
    }
    
    @Override
    public ByteBuf setMedium(final int index, final int value) {
        this.ensureAccessible();
        this._setMedium(index, value);
        return this;
    }
    
    @Override
    protected void _setMedium(final int index, final int value) {
        this.setByte(index, (byte)(value >>> 16));
        this.setByte(index + 1, (byte)(value >>> 8));
        this.setByte(index + 2, (byte)value);
    }
    
    @Override
    public ByteBuf setInt(final int index, final int value) {
        this.ensureAccessible();
        this._setInt(index, value);
        return this;
    }
    
    @Override
    protected void _setInt(final int index, final int value) {
        this.buffer.putInt(index, value);
    }
    
    @Override
    public ByteBuf setLong(final int index, final long value) {
        this.ensureAccessible();
        this._setLong(index, value);
        return this;
    }
    
    @Override
    protected void _setLong(final int index, final long value) {
        this.buffer.putLong(index, value);
    }
    
    @Override
    public ByteBuf setBytes(int index, final ByteBuf src, final int srcIndex, final int length) {
        this.checkSrcIndex(index, length, srcIndex, src.capacity());
        if (this.buffer.hasArray()) {
            src.getBytes(srcIndex, this.buffer.array(), index + this.buffer.arrayOffset(), length);
        }
        else if (src.nioBufferCount() > 0) {
            for (final ByteBuffer bb : src.nioBuffers(srcIndex, length)) {
                final int bbLen = bb.remaining();
                this.setBytes(index, bb);
                index += bbLen;
            }
        }
        else {
            src.getBytes(srcIndex, this, index, length);
        }
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int index, final byte[] src, final int srcIndex, final int length) {
        this.checkSrcIndex(index, length, srcIndex, src.length);
        final ByteBuffer tmpBuf = this.internalNioBuffer();
        tmpBuf.clear().position(index).limit(index + length);
        tmpBuf.put(src, srcIndex, length);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int index, ByteBuffer src) {
        this.ensureAccessible();
        final ByteBuffer tmpBuf = this.internalNioBuffer();
        if (src == tmpBuf) {
            src = src.duplicate();
        }
        tmpBuf.clear().position(index).limit(index + src.remaining());
        tmpBuf.put(src);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int index, final OutputStream out, final int length) throws IOException {
        this.ensureAccessible();
        if (length == 0) {
            return this;
        }
        if (this.buffer.hasArray()) {
            out.write(this.buffer.array(), index + this.buffer.arrayOffset(), length);
        }
        else {
            final byte[] tmp = new byte[length];
            final ByteBuffer tmpBuf = this.internalNioBuffer();
            tmpBuf.clear().position(index);
            tmpBuf.get(tmp);
            out.write(tmp);
        }
        return this;
    }
    
    @Override
    public int getBytes(final int index, final GatheringByteChannel out, final int length) throws IOException {
        this.ensureAccessible();
        if (length == 0) {
            return 0;
        }
        final ByteBuffer tmpBuf = this.internalNioBuffer();
        tmpBuf.clear().position(index).limit(index + length);
        return out.write(tmpBuf);
    }
    
    @Override
    public int setBytes(final int index, final InputStream in, final int length) throws IOException {
        this.ensureAccessible();
        if (this.buffer.hasArray()) {
            return in.read(this.buffer.array(), this.buffer.arrayOffset() + index, length);
        }
        final byte[] tmp = new byte[length];
        final int readBytes = in.read(tmp);
        if (readBytes <= 0) {
            return readBytes;
        }
        final ByteBuffer tmpNioBuf = this.internalNioBuffer();
        tmpNioBuf.clear().position(index);
        tmpNioBuf.put(tmp, 0, readBytes);
        return readBytes;
    }
    
    @Override
    public int setBytes(final int index, final ScatteringByteChannel in, final int length) throws IOException {
        this.ensureAccessible();
        final ByteBuffer tmpNioBuf = this.internalNioBuffer();
        tmpNioBuf.clear().position(index).limit(index + length);
        try {
            return in.read(tmpNioBuf);
        }
        catch (ClosedChannelException e) {
            return -1;
        }
    }
    
    @Override
    public int nioBufferCount() {
        return 1;
    }
    
    @Override
    public ByteBuffer nioBuffer(final int index, final int length) {
        this.ensureAccessible();
        if (index == 0 && length == this.capacity()) {
            return this.buffer.duplicate();
        }
        return ((ByteBuffer)this.internalNioBuffer().clear().position(index).limit(index + length)).slice();
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int index, final int length) {
        return new ByteBuffer[] { this.nioBuffer(index, length) };
    }
    
    @Override
    public ByteBuf copy(final int index, final int length) {
        this.ensureAccessible();
        ByteBuffer src;
        try {
            src = (ByteBuffer)this.internalNioBuffer().clear().position(index).limit(index + length);
        }
        catch (IllegalArgumentException e) {
            throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (index + length));
        }
        final ByteBuffer dst = src.isDirect() ? ByteBuffer.allocateDirect(length) : ByteBuffer.allocate(length);
        dst.put(src);
        dst.order(this.order());
        dst.clear();
        return new UnpooledDirectByteBuf(this.alloc(), dst, this.maxCapacity());
    }
    
    private ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf = this.tmpNioBuf;
        if (tmpNioBuf == null) {
            tmpNioBuf = (this.tmpNioBuf = this.buffer.duplicate());
        }
        return tmpNioBuf;
    }
    
    @Override
    protected void deallocate() {
        final ByteBuffer buffer = this.buffer;
        if (buffer == null) {
            return;
        }
        this.buffer = null;
        this.resumeIntermediaryDeallocations();
        if (!this.doNotFree) {
            PlatformDependent.freeDirectBuffer(buffer);
        }
        this.leak.close();
    }
    
    @Override
    public ByteBuf suspendIntermediaryDeallocations() {
        this.ensureAccessible();
        if (this.suspendedDeallocations == null) {
            this.suspendedDeallocations = new ArrayDeque<ByteBuffer>(2);
        }
        return this;
    }
    
    @Override
    public ByteBuf resumeIntermediaryDeallocations() {
        if (this.suspendedDeallocations == null) {
            return this;
        }
        final Queue<ByteBuffer> suspendedDeallocations = this.suspendedDeallocations;
        this.suspendedDeallocations = null;
        for (final ByteBuffer buf : suspendedDeallocations) {
            PlatformDependent.freeDirectBuffer(buf);
        }
        return this;
    }
    
    @Override
    public ByteBuf unwrap() {
        return null;
    }
}
