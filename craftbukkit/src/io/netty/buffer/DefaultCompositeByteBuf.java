// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import java.util.ArrayDeque;
import java.nio.channels.ScatteringByteChannel;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import io.netty.util.internal.PlatformDependent;
import java.nio.channels.GatheringByteChannel;
import java.util.ListIterator;
import java.util.Collections;
import java.util.Iterator;
import java.util.Collection;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Queue;
import java.util.List;
import io.netty.util.ResourceLeak;
import java.nio.ByteBuffer;

public class DefaultCompositeByteBuf extends AbstractReferenceCountedByteBuf implements CompositeByteBuf
{
    private static final ByteBuffer[] EMPTY_NIOBUFFERS;
    private final ResourceLeak leak;
    private final ByteBufAllocator alloc;
    private final boolean direct;
    private final List<Component> components;
    private final int maxNumComponents;
    private Component lastAccessed;
    private int lastAccessedId;
    private boolean freed;
    private Queue<ByteBuf> suspendedDeallocations;
    
    public DefaultCompositeByteBuf(final ByteBufAllocator alloc, final boolean direct, final int maxNumComponents) {
        super(Integer.MAX_VALUE);
        this.leak = DefaultCompositeByteBuf.leakDetector.open(this);
        this.components = new ArrayList<Component>();
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        this.alloc = alloc;
        this.direct = direct;
        this.maxNumComponents = maxNumComponents;
    }
    
    public DefaultCompositeByteBuf(final ByteBufAllocator alloc, final boolean direct, final int maxNumComponents, final ByteBuf... buffers) {
        super(Integer.MAX_VALUE);
        this.leak = DefaultCompositeByteBuf.leakDetector.open(this);
        this.components = new ArrayList<Component>();
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        if (maxNumComponents < 2) {
            throw new IllegalArgumentException("maxNumComponents: " + maxNumComponents + " (expected: >= 2)");
        }
        this.alloc = alloc;
        this.direct = direct;
        this.maxNumComponents = maxNumComponents;
        this.addComponents0(0, buffers);
        this.consolidateIfNeeded();
        this.setIndex(0, this.capacity());
    }
    
    public DefaultCompositeByteBuf(final ByteBufAllocator alloc, final boolean direct, final int maxNumComponents, final Iterable<ByteBuf> buffers) {
        super(Integer.MAX_VALUE);
        this.leak = DefaultCompositeByteBuf.leakDetector.open(this);
        this.components = new ArrayList<Component>();
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        if (maxNumComponents < 2) {
            throw new IllegalArgumentException("maxNumComponents: " + maxNumComponents + " (expected: >= 2)");
        }
        this.alloc = alloc;
        this.direct = direct;
        this.maxNumComponents = maxNumComponents;
        this.addComponents0(0, buffers);
        this.consolidateIfNeeded();
        this.setIndex(0, this.capacity());
    }
    
    @Override
    public CompositeByteBuf addComponent(final ByteBuf buffer) {
        this.addComponent0(this.components.size(), buffer);
        this.consolidateIfNeeded();
        return this;
    }
    
    @Override
    public CompositeByteBuf addComponents(final ByteBuf... buffers) {
        this.addComponents0(this.components.size(), buffers);
        this.consolidateIfNeeded();
        return this;
    }
    
    @Override
    public CompositeByteBuf addComponents(final Iterable<ByteBuf> buffers) {
        this.addComponents0(this.components.size(), buffers);
        this.consolidateIfNeeded();
        return this;
    }
    
    @Override
    public CompositeByteBuf addComponent(final int cIndex, final ByteBuf buffer) {
        this.addComponent0(cIndex, buffer);
        this.consolidateIfNeeded();
        return this;
    }
    
    private int addComponent0(final int cIndex, final ByteBuf buffer) {
        this.checkComponentIndex(cIndex);
        if (buffer == null) {
            throw new NullPointerException("buffer");
        }
        if (buffer instanceof Iterable) {
            final Iterable<ByteBuf> composite = (Iterable<ByteBuf>)buffer;
            return this.addComponents0(cIndex, composite);
        }
        final int readableBytes = buffer.readableBytes();
        if (readableBytes == 0) {
            return cIndex;
        }
        final Component c = new Component(buffer.order(ByteOrder.BIG_ENDIAN).slice());
        if (cIndex == this.components.size()) {
            this.components.add(c);
            if (cIndex == 0) {
                c.endOffset = readableBytes;
            }
            else {
                final Component prev = this.components.get(cIndex - 1);
                c.offset = prev.endOffset;
                c.endOffset = c.offset + readableBytes;
            }
        }
        else {
            this.components.add(cIndex, c);
            this.updateComponentOffsets(cIndex);
        }
        return cIndex;
    }
    
    @Override
    public CompositeByteBuf addComponents(final int cIndex, final ByteBuf... buffers) {
        this.addComponents0(cIndex, buffers);
        this.consolidateIfNeeded();
        return this;
    }
    
    private int addComponents0(int cIndex, final ByteBuf... buffers) {
        this.checkComponentIndex(cIndex);
        if (buffers == null) {
            throw new NullPointerException("buffers");
        }
        int readableBytes = 0;
        for (final ByteBuf b : buffers) {
            if (b == null) {
                break;
            }
            readableBytes += b.readableBytes();
        }
        if (readableBytes == 0) {
            return cIndex;
        }
        for (final ByteBuf b : buffers) {
            if (b == null) {
                break;
            }
            if (b.isReadable()) {
                cIndex = this.addComponent0(cIndex, b) + 1;
                final int size = this.components.size();
                if (cIndex > size) {
                    cIndex = size;
                }
            }
            else {
                b.release();
            }
        }
        return cIndex;
    }
    
    @Override
    public CompositeByteBuf addComponents(final int cIndex, final Iterable<ByteBuf> buffers) {
        this.addComponents0(cIndex, buffers);
        this.consolidateIfNeeded();
        return this;
    }
    
    private int addComponents0(final int cIndex, final Iterable<ByteBuf> buffers) {
        if (buffers == null) {
            throw new NullPointerException("buffers");
        }
        if (buffers instanceof DefaultCompositeByteBuf) {
            final DefaultCompositeByteBuf compositeBuf = (DefaultCompositeByteBuf)buffers;
            final List<Component> list = compositeBuf.components;
            final ByteBuf[] array = new ByteBuf[list.size()];
            for (int i = 0; i < array.length; ++i) {
                array[i] = list.get(i).buf.retain();
            }
            compositeBuf.release();
            return this.addComponents0(cIndex, array);
        }
        if (buffers instanceof CompositeByteBuf) {
            final CompositeByteBuf compositeBuf2 = (CompositeByteBuf)buffers;
            final int nComponents = compositeBuf2.numComponents();
            final ByteBuf[] array = new ByteBuf[nComponents];
            for (int i = 0; i < nComponents; ++i) {
                array[i] = compositeBuf2.component(i).retain();
            }
            compositeBuf2.release();
            return this.addComponents0(cIndex, array);
        }
        if (buffers instanceof List) {
            final List<ByteBuf> list2 = (List<ByteBuf>)(List)buffers;
            final ByteBuf[] array2 = new ByteBuf[list2.size()];
            for (int j = 0; j < array2.length; ++j) {
                array2[j] = list2.get(j);
            }
            return this.addComponents0(cIndex, array2);
        }
        if (buffers instanceof Collection) {
            final Collection<ByteBuf> col = (Collection<ByteBuf>)(Collection)buffers;
            final ByteBuf[] array2 = new ByteBuf[col.size()];
            int j = 0;
            for (final ByteBuf b : col) {
                array2[j++] = b;
            }
            return this.addComponents0(cIndex, array2);
        }
        final List<ByteBuf> list2 = new ArrayList<ByteBuf>();
        for (final ByteBuf b2 : buffers) {
            list2.add(b2);
        }
        return this.addComponents0(cIndex, (ByteBuf[])list2.toArray(new ByteBuf[list2.size()]));
    }
    
    private void consolidateIfNeeded() {
        final int numComponents = this.components.size();
        if (numComponents > this.maxNumComponents) {
            final int capacity = this.components.get(numComponents - 1).endOffset;
            final ByteBuf consolidated = this.allocBuffer(capacity);
            for (int i = 0; i < numComponents; ++i) {
                final Component c = this.components.get(i);
                final ByteBuf b = c.buf;
                consolidated.writeBytes(b);
                c.freeIfNecessary();
            }
            final Component c2 = new Component(consolidated);
            c2.endOffset = c2.length;
            this.components.clear();
            this.components.add(c2);
        }
    }
    
    private void checkComponentIndex(final int cIndex) {
        assert !this.freed;
        if (cIndex < 0 || cIndex > this.components.size()) {
            throw new IndexOutOfBoundsException(String.format("cIndex: %d (expected: >= 0 && <= numComponents(%d))", cIndex, this.components.size()));
        }
    }
    
    private void checkComponentIndex(final int cIndex, final int numComponents) {
        assert !this.freed;
        if (cIndex < 0 || cIndex + numComponents > this.components.size()) {
            throw new IndexOutOfBoundsException(String.format("cIndex: %d, numComponents: %d (expected: cIndex >= 0 && cIndex + numComponents <= totalNumComponents(%d))", cIndex, numComponents, this.components.size()));
        }
    }
    
    private void updateComponentOffsets(int cIndex) {
        final Component c = this.components.get(cIndex);
        this.lastAccessed = c;
        this.lastAccessedId = cIndex;
        if (cIndex == 0) {
            c.offset = 0;
            c.endOffset = c.length;
            ++cIndex;
        }
        for (int i = cIndex; i < this.components.size(); ++i) {
            final Component prev = this.components.get(i - 1);
            final Component cur = this.components.get(i);
            cur.offset = prev.endOffset;
            cur.endOffset = cur.offset + cur.length;
        }
    }
    
    @Override
    public CompositeByteBuf removeComponent(final int cIndex) {
        this.checkComponentIndex(cIndex);
        this.components.remove(cIndex);
        this.updateComponentOffsets(cIndex);
        return this;
    }
    
    @Override
    public CompositeByteBuf removeComponents(final int cIndex, final int numComponents) {
        this.checkComponentIndex(cIndex, numComponents);
        this.components.subList(cIndex, cIndex + numComponents).clear();
        this.updateComponentOffsets(cIndex);
        return this;
    }
    
    @Override
    public Iterator<ByteBuf> iterator() {
        assert !this.freed;
        final List<ByteBuf> list = new ArrayList<ByteBuf>(this.components.size());
        for (final Component c : this.components) {
            list.add(c.buf);
        }
        return list.iterator();
    }
    
    @Override
    public List<ByteBuf> decompose(final int offset, final int length) {
        this.checkIndex(offset, length);
        if (length == 0) {
            return Collections.emptyList();
        }
        int componentId = this.toComponentIndex(offset);
        final List<ByteBuf> slice = new ArrayList<ByteBuf>(this.components.size());
        final Component firstC = this.components.get(componentId);
        final ByteBuf first = firstC.buf.duplicate();
        first.readerIndex(offset - firstC.offset);
        ByteBuf buf = first;
        int bytesToSlice = length;
        do {
            final int readableBytes = buf.readableBytes();
            if (bytesToSlice <= readableBytes) {
                buf.writerIndex(buf.readerIndex() + bytesToSlice);
                slice.add(buf);
                break;
            }
            slice.add(buf);
            bytesToSlice -= readableBytes;
            ++componentId;
            buf = this.components.get(componentId).buf.duplicate();
        } while (bytesToSlice > 0);
        for (int i = 0; i < slice.size(); ++i) {
            slice.set(i, slice.get(i).slice());
        }
        return slice;
    }
    
    @Override
    public boolean isDirect() {
        return this.components.size() == 1 && this.components.get(0).buf.isDirect();
    }
    
    @Override
    public boolean hasArray() {
        return this.components.size() == 1 && this.components.get(0).buf.hasArray();
    }
    
    @Override
    public byte[] array() {
        if (this.components.size() == 1) {
            return this.components.get(0).buf.array();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int arrayOffset() {
        if (this.components.size() == 1) {
            return this.components.get(0).buf.arrayOffset();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean hasMemoryAddress() {
        return this.components.size() == 1 && this.components.get(0).buf.hasMemoryAddress();
    }
    
    @Override
    public long memoryAddress() {
        if (this.components.size() == 1) {
            return this.components.get(0).buf.memoryAddress();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int capacity() {
        if (this.components.isEmpty()) {
            return 0;
        }
        return this.components.get(this.components.size() - 1).endOffset;
    }
    
    @Override
    public CompositeByteBuf capacity(final int newCapacity) {
        assert !this.freed;
        if (newCapacity < 0 || newCapacity > this.maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + newCapacity);
        }
        final int oldCapacity = this.capacity();
        if (newCapacity > oldCapacity) {
            final int paddingLength = newCapacity - oldCapacity;
            final int nComponents = this.components.size();
            if (nComponents < this.maxNumComponents) {
                final ByteBuf padding = this.allocBuffer(paddingLength);
                padding.setIndex(0, paddingLength);
                this.addComponent0(this.components.size(), padding);
            }
            else {
                final ByteBuf padding = this.allocBuffer(paddingLength);
                padding.setIndex(0, paddingLength);
                this.addComponent0(this.components.size(), padding);
                this.consolidateIfNeeded();
            }
        }
        else if (newCapacity < oldCapacity) {
            int bytesToTrim = oldCapacity - newCapacity;
            final ListIterator<Component> i = this.components.listIterator(this.components.size());
            while (i.hasPrevious()) {
                final Component c = i.previous();
                if (bytesToTrim < c.length) {
                    final Component newC = new Component(c.buf.slice(0, c.length - bytesToTrim));
                    newC.offset = c.offset;
                    newC.endOffset = newC.offset + newC.length;
                    i.set(newC);
                    break;
                }
                bytesToTrim -= c.length;
                i.remove();
            }
            if (this.readerIndex() > newCapacity) {
                this.setIndex(newCapacity, newCapacity);
            }
            else if (this.writerIndex() > newCapacity) {
                this.writerIndex(newCapacity);
            }
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
    public int numComponents() {
        return this.components.size();
    }
    
    @Override
    public int maxNumComponents() {
        return this.maxNumComponents;
    }
    
    @Override
    public int toComponentIndex(final int offset) {
        assert !this.freed;
        this.checkIndex(offset);
        Component c = this.lastAccessed;
        if (c == null) {
            c = (this.lastAccessed = this.components.get(0));
        }
        if (offset >= c.offset) {
            if (offset < c.endOffset) {
                return this.lastAccessedId;
            }
            for (int i = this.lastAccessedId + 1; i < this.components.size(); ++i) {
                c = this.components.get(i);
                if (offset < c.endOffset) {
                    this.lastAccessedId = i;
                    this.lastAccessed = c;
                    return i;
                }
            }
        }
        else {
            for (int i = this.lastAccessedId - 1; i >= 0; --i) {
                c = this.components.get(i);
                if (offset >= c.offset) {
                    this.lastAccessedId = i;
                    this.lastAccessed = c;
                    return i;
                }
            }
        }
        throw new IllegalStateException("should not reach here - concurrent modification?");
    }
    
    @Override
    public int toByteIndex(final int cIndex) {
        this.checkComponentIndex(cIndex);
        return this.components.get(cIndex).offset;
    }
    
    @Override
    public byte getByte(final int index) {
        return this._getByte(index);
    }
    
    @Override
    protected byte _getByte(final int index) {
        final Component c = this.findComponent(index);
        return c.buf.getByte(index - c.offset);
    }
    
    @Override
    protected short _getShort(final int index) {
        final Component c = this.findComponent(index);
        if (index + 2 <= c.endOffset) {
            return c.buf.getShort(index - c.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (short)((this._getByte(index) & 0xFF) << 8 | (this._getByte(index + 1) & 0xFF));
        }
        return (short)((this._getByte(index) & 0xFF) | (this._getByte(index + 1) & 0xFF) << 8);
    }
    
    @Override
    protected int _getUnsignedMedium(final int index) {
        final Component c = this.findComponent(index);
        if (index + 3 <= c.endOffset) {
            return c.buf.getUnsignedMedium(index - c.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (this._getShort(index) & 0xFFFF) << 8 | (this._getByte(index + 2) & 0xFF);
        }
        return (this._getShort(index) & 0xFFFF) | (this._getByte(index + 2) & 0xFF) << 16;
    }
    
    @Override
    protected int _getInt(final int index) {
        final Component c = this.findComponent(index);
        if (index + 4 <= c.endOffset) {
            return c.buf.getInt(index - c.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (this._getShort(index) & 0xFFFF) << 16 | (this._getShort(index + 2) & 0xFFFF);
        }
        return (this._getShort(index) & 0xFFFF) | (this._getShort(index + 2) & 0xFFFF) << 16;
    }
    
    @Override
    protected long _getLong(final int index) {
        final Component c = this.findComponent(index);
        if (index + 8 <= c.endOffset) {
            return c.buf.getLong(index - c.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (this._getInt(index) & 0xFFFFFFFFL) << 32 | (this._getInt(index + 4) & 0xFFFFFFFFL);
        }
        return (this._getInt(index) & 0xFFFFFFFFL) | (this._getInt(index + 4) & 0xFFFFFFFFL) << 32;
    }
    
    @Override
    public CompositeByteBuf getBytes(int index, final byte[] dst, int dstIndex, int length) {
        this.checkDstIndex(index, length, dstIndex, dst.length);
        if (length == 0) {
            return this;
        }
        int localLength;
        for (int i = this.toComponentIndex(index); length > 0; length -= localLength, ++i) {
            final Component c = this.components.get(i);
            final ByteBuf s = c.buf;
            final int adjustment = c.offset;
            localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
        }
        return this;
    }
    
    @Override
    public CompositeByteBuf getBytes(int index, final ByteBuffer dst) {
        final int limit = dst.limit();
        int length = dst.remaining();
        this.checkIndex(index, length);
        if (length == 0) {
            return this;
        }
        int i = this.toComponentIndex(index);
        try {
            while (length > 0) {
                final Component c = this.components.get(i);
                final ByteBuf s = c.buf;
                final int adjustment = c.offset;
                final int localLength = Math.min(length, s.capacity() - (index - adjustment));
                dst.limit(dst.position() + localLength);
                s.getBytes(index - adjustment, dst);
                index += localLength;
                length -= localLength;
                ++i;
            }
        }
        finally {
            dst.limit(limit);
        }
        return this;
    }
    
    @Override
    public CompositeByteBuf getBytes(int index, final ByteBuf dst, int dstIndex, int length) {
        this.checkDstIndex(index, length, dstIndex, dst.capacity());
        int localLength;
        for (int i = this.toComponentIndex(index); length > 0; length -= localLength, ++i) {
            final Component c = this.components.get(i);
            final ByteBuf s = c.buf;
            final int adjustment = c.offset;
            localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
        }
        return this;
    }
    
    @Override
    public int getBytes(final int index, final GatheringByteChannel out, final int length) throws IOException {
        if (PlatformDependent.javaVersion() < 7) {
            return out.write(this.copiedNioBuffer(index, length));
        }
        final long writtenBytes = out.write(this.nioBuffers(index, length));
        if (writtenBytes > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int)writtenBytes;
    }
    
    @Override
    public CompositeByteBuf getBytes(int index, final OutputStream out, int length) throws IOException {
        this.checkIndex(index, length);
        if (length == 0) {
            return this;
        }
        int localLength;
        for (int i = this.toComponentIndex(index); length > 0; length -= localLength, ++i) {
            final Component c = this.components.get(i);
            final ByteBuf s = c.buf;
            final int adjustment = c.offset;
            localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.getBytes(index - adjustment, out, localLength);
            index += localLength;
        }
        return this;
    }
    
    @Override
    public CompositeByteBuf setByte(final int index, final int value) {
        final Component c = this.findComponent(index);
        c.buf.setByte(index - c.offset, value);
        return this;
    }
    
    @Override
    protected void _setByte(final int index, final int value) {
        this.setByte(index, value);
    }
    
    @Override
    public CompositeByteBuf setShort(final int index, final int value) {
        return (CompositeByteBuf)super.setShort(index, value);
    }
    
    @Override
    protected void _setShort(final int index, final int value) {
        final Component c = this.findComponent(index);
        if (index + 2 <= c.endOffset) {
            c.buf.setShort(index - c.offset, value);
        }
        else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setByte(index, (byte)(value >>> 8));
            this._setByte(index + 1, (byte)value);
        }
        else {
            this._setByte(index, (byte)value);
            this._setByte(index + 1, (byte)(value >>> 8));
        }
    }
    
    @Override
    public CompositeByteBuf setMedium(final int index, final int value) {
        return (CompositeByteBuf)super.setMedium(index, value);
    }
    
    @Override
    protected void _setMedium(final int index, final int value) {
        final Component c = this.findComponent(index);
        if (index + 3 <= c.endOffset) {
            c.buf.setMedium(index - c.offset, value);
        }
        else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setShort(index, (short)(value >> 8));
            this._setByte(index + 2, (byte)value);
        }
        else {
            this._setShort(index, (short)value);
            this._setByte(index + 2, (byte)(value >>> 16));
        }
    }
    
    @Override
    public CompositeByteBuf setInt(final int index, final int value) {
        return (CompositeByteBuf)super.setInt(index, value);
    }
    
    @Override
    protected void _setInt(final int index, final int value) {
        final Component c = this.findComponent(index);
        if (index + 4 <= c.endOffset) {
            c.buf.setInt(index - c.offset, value);
        }
        else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setShort(index, (short)(value >>> 16));
            this._setShort(index + 2, (short)value);
        }
        else {
            this._setShort(index, (short)value);
            this._setShort(index + 2, (short)(value >>> 16));
        }
    }
    
    @Override
    public CompositeByteBuf setLong(final int index, final long value) {
        return (CompositeByteBuf)super.setLong(index, value);
    }
    
    @Override
    protected void _setLong(final int index, final long value) {
        final Component c = this.findComponent(index);
        if (index + 8 <= c.endOffset) {
            c.buf.setLong(index - c.offset, value);
        }
        else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setInt(index, (int)(value >>> 32));
            this._setInt(index + 4, (int)value);
        }
        else {
            this._setInt(index, (int)value);
            this._setInt(index + 4, (int)(value >>> 32));
        }
    }
    
    @Override
    public CompositeByteBuf setBytes(int index, final byte[] src, int srcIndex, int length) {
        this.checkSrcIndex(index, length, srcIndex, src.length);
        int localLength;
        for (int i = this.toComponentIndex(index); length > 0; length -= localLength, ++i) {
            final Component c = this.components.get(i);
            final ByteBuf s = c.buf;
            final int adjustment = c.offset;
            localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.setBytes(index - adjustment, src, srcIndex, localLength);
            index += localLength;
            srcIndex += localLength;
        }
        return this;
    }
    
    @Override
    public CompositeByteBuf setBytes(int index, final ByteBuffer src) {
        final int limit = src.limit();
        int length = src.remaining();
        this.checkIndex(index, length);
        int i = this.toComponentIndex(index);
        try {
            while (length > 0) {
                final Component c = this.components.get(i);
                final ByteBuf s = c.buf;
                final int adjustment = c.offset;
                final int localLength = Math.min(length, s.capacity() - (index - adjustment));
                src.limit(src.position() + localLength);
                s.setBytes(index - adjustment, src);
                index += localLength;
                length -= localLength;
                ++i;
            }
        }
        finally {
            src.limit(limit);
        }
        return this;
    }
    
    @Override
    public CompositeByteBuf setBytes(int index, final ByteBuf src, int srcIndex, int length) {
        this.checkSrcIndex(index, length, srcIndex, src.capacity());
        int localLength;
        for (int i = this.toComponentIndex(index); length > 0; length -= localLength, ++i) {
            final Component c = this.components.get(i);
            final ByteBuf s = c.buf;
            final int adjustment = c.offset;
            localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.setBytes(index - adjustment, src, srcIndex, localLength);
            index += localLength;
            srcIndex += localLength;
        }
        return this;
    }
    
    @Override
    public int setBytes(int index, final InputStream in, int length) throws IOException {
        this.checkIndex(index, length);
        int i = this.toComponentIndex(index);
        int readBytes = 0;
        do {
            final Component c = this.components.get(i);
            final ByteBuf s = c.buf;
            final int adjustment = c.offset;
            final int localLength = Math.min(length, s.capacity() - (index - adjustment));
            final int localReadBytes = s.setBytes(index - adjustment, in, localLength);
            if (localReadBytes < 0) {
                if (readBytes == 0) {
                    return -1;
                }
                break;
            }
            else if (localReadBytes == localLength) {
                index += localLength;
                length -= localLength;
                readBytes += localLength;
                ++i;
            }
            else {
                index += localReadBytes;
                length -= localReadBytes;
                readBytes += localReadBytes;
            }
        } while (length > 0);
        return readBytes;
    }
    
    @Override
    public int setBytes(int index, final ScatteringByteChannel in, int length) throws IOException {
        this.checkIndex(index, length);
        int i = this.toComponentIndex(index);
        int readBytes = 0;
        do {
            final Component c = this.components.get(i);
            final ByteBuf s = c.buf;
            final int adjustment = c.offset;
            final int localLength = Math.min(length, s.capacity() - (index - adjustment));
            final int localReadBytes = s.setBytes(index - adjustment, in, localLength);
            if (localReadBytes == 0) {
                break;
            }
            if (localReadBytes < 0) {
                if (readBytes == 0) {
                    return -1;
                }
                break;
            }
            else if (localReadBytes == localLength) {
                index += localLength;
                length -= localLength;
                readBytes += localLength;
                ++i;
            }
            else {
                index += localReadBytes;
                length -= localReadBytes;
                readBytes += localReadBytes;
            }
        } while (length > 0);
        return readBytes;
    }
    
    @Override
    public ByteBuf copy(final int index, final int length) {
        this.checkIndex(index, length);
        final ByteBuf dst = Unpooled.buffer(length);
        this.copyTo(index, length, this.toComponentIndex(index), dst);
        return dst;
    }
    
    private void copyTo(int index, int length, final int componentId, final ByteBuf dst) {
        int dstIndex = 0;
        int localLength;
        for (int i = componentId; length > 0; length -= localLength, ++i) {
            final Component c = this.components.get(i);
            final ByteBuf s = c.buf;
            final int adjustment = c.offset;
            localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
        }
        dst.writerIndex(dst.capacity());
    }
    
    @Override
    public ByteBuf component(final int cIndex) {
        this.checkComponentIndex(cIndex);
        return this.components.get(cIndex).buf;
    }
    
    @Override
    public ByteBuf componentAtOffset(final int offset) {
        return this.findComponent(offset).buf;
    }
    
    private Component findComponent(final int offset) {
        assert !this.freed;
        this.checkIndex(offset);
        Component c = this.lastAccessed;
        if (c == null) {
            c = (this.lastAccessed = this.components.get(0));
        }
        if (offset >= c.offset) {
            if (offset < c.endOffset) {
                return c;
            }
            for (int i = this.lastAccessedId + 1; i < this.components.size(); ++i) {
                c = this.components.get(i);
                if (offset < c.endOffset) {
                    this.lastAccessedId = i;
                    return this.lastAccessed = c;
                }
            }
        }
        else {
            for (int i = this.lastAccessedId - 1; i >= 0; --i) {
                c = this.components.get(i);
                if (offset >= c.offset) {
                    this.lastAccessedId = i;
                    return this.lastAccessed = c;
                }
            }
        }
        throw new IllegalStateException("should not reach here - concurrent modification?");
    }
    
    @Override
    public int nioBufferCount() {
        return this.components.size();
    }
    
    @Override
    public ByteBuffer nioBuffer(final int index, final int length) {
        if (this.components.size() == 1) {
            return this.components.get(0).buf.nioBuffer(index, length);
        }
        throw new UnsupportedOperationException();
    }
    
    private ByteBuffer copiedNioBuffer(final int index, final int length) {
        assert !this.freed;
        if (this.components.size() == 1) {
            return toNioBuffer(this.components.get(0).buf, index, length);
        }
        final ByteBuffer[] buffers = this.nioBuffers(index, length);
        final ByteBuffer merged = ByteBuffer.allocate(length).order(this.order());
        for (final ByteBuffer b : buffers) {
            merged.put(b);
        }
        merged.flip();
        return merged;
    }
    
    @Override
    public ByteBuffer[] nioBuffers(int index, int length) {
        this.checkIndex(index, length);
        if (length == 0) {
            return DefaultCompositeByteBuf.EMPTY_NIOBUFFERS;
        }
        final List<ByteBuffer> buffers = new ArrayList<ByteBuffer>(this.components.size());
        int localLength;
        for (int i = this.toComponentIndex(index); length > 0; length -= localLength, ++i) {
            final Component c = this.components.get(i);
            final ByteBuf s = c.buf;
            final int adjustment = c.offset;
            localLength = Math.min(length, s.capacity() - (index - adjustment));
            buffers.add(s.nioBuffer(index - adjustment, localLength));
            index += localLength;
        }
        return buffers.toArray(new ByteBuffer[buffers.size()]);
    }
    
    private static ByteBuffer toNioBuffer(final ByteBuf buf, final int index, final int length) {
        if (buf.nioBufferCount() == 1) {
            return buf.nioBuffer(index, length);
        }
        return buf.copy(index, length).nioBuffer(0, length);
    }
    
    @Override
    public CompositeByteBuf consolidate() {
        assert !this.freed;
        final int numComponents = this.numComponents();
        if (numComponents <= 1) {
            return this;
        }
        final Component last = this.components.get(numComponents - 1);
        final int capacity = last.endOffset;
        final ByteBuf consolidated = this.allocBuffer(capacity);
        for (int i = 0; i < numComponents; ++i) {
            final Component c = this.components.get(i);
            final ByteBuf b = c.buf;
            consolidated.writeBytes(b);
            c.freeIfNecessary();
        }
        this.components.clear();
        this.components.add(new Component(consolidated));
        this.updateComponentOffsets(0);
        return this;
    }
    
    @Override
    public CompositeByteBuf consolidate(final int cIndex, final int numComponents) {
        this.checkComponentIndex(cIndex, numComponents);
        if (numComponents <= 1) {
            return this;
        }
        final int endCIndex = cIndex + numComponents;
        final Component last = this.components.get(endCIndex - 1);
        final int capacity = last.endOffset - this.components.get(cIndex).offset;
        final ByteBuf consolidated = this.allocBuffer(capacity);
        for (int i = cIndex; i < endCIndex; ++i) {
            final Component c = this.components.get(i);
            final ByteBuf b = c.buf;
            consolidated.writeBytes(b);
            c.freeIfNecessary();
        }
        this.components.subList(cIndex + 1, endCIndex).clear();
        this.components.set(cIndex, new Component(consolidated));
        this.updateComponentOffsets(cIndex);
        return this;
    }
    
    @Override
    public CompositeByteBuf discardReadComponents() {
        assert !this.freed;
        final int readerIndex = this.readerIndex();
        if (readerIndex == 0) {
            return this;
        }
        final int writerIndex = this.writerIndex();
        if (readerIndex == writerIndex && writerIndex == this.capacity()) {
            for (final Component c : this.components) {
                c.freeIfNecessary();
            }
            this.components.clear();
            this.setIndex(0, 0);
            this.adjustMarkers(readerIndex);
            return this;
        }
        final int firstComponentId = this.toComponentIndex(readerIndex);
        for (int i = 0; i < firstComponentId; ++i) {
            this.components.get(i).freeIfNecessary();
        }
        this.components.subList(0, firstComponentId).clear();
        final Component first = this.components.get(0);
        this.updateComponentOffsets(0);
        this.setIndex(readerIndex - first.offset, writerIndex - first.offset);
        this.adjustMarkers(first.offset);
        return this;
    }
    
    @Override
    public CompositeByteBuf discardReadBytes() {
        assert !this.freed;
        final int readerIndex = this.readerIndex();
        if (readerIndex == 0) {
            return this;
        }
        final int writerIndex = this.writerIndex();
        if (readerIndex == writerIndex && writerIndex == this.capacity()) {
            for (final Component c : this.components) {
                c.freeIfNecessary();
            }
            this.components.clear();
            this.setIndex(0, 0);
            this.adjustMarkers(readerIndex);
            return this;
        }
        final int firstComponentId = this.toComponentIndex(readerIndex);
        for (int i = 0; i < firstComponentId; ++i) {
            this.components.get(i).freeIfNecessary();
        }
        this.components.subList(0, firstComponentId).clear();
        Component c = this.components.get(0);
        final int adjustment = readerIndex - c.offset;
        if (adjustment == c.length) {
            this.components.remove(0);
        }
        else {
            final Component newC = new Component(c.buf.slice(adjustment, c.length - adjustment));
            this.components.set(0, newC);
        }
        this.updateComponentOffsets(0);
        this.setIndex(0, writerIndex - readerIndex);
        this.adjustMarkers(readerIndex);
        return this;
    }
    
    private ByteBuf allocBuffer(final int capacity) {
        if (this.direct) {
            return this.alloc().directBuffer(capacity);
        }
        return this.alloc().heapBuffer(capacity);
    }
    
    @Override
    public String toString() {
        String result = super.toString();
        result = result.substring(0, result.length() - 1);
        return result + ", components=" + this.components.size() + ')';
    }
    
    @Override
    public CompositeByteBuf readerIndex(final int readerIndex) {
        return (CompositeByteBuf)super.readerIndex(readerIndex);
    }
    
    @Override
    public CompositeByteBuf writerIndex(final int writerIndex) {
        return (CompositeByteBuf)super.writerIndex(writerIndex);
    }
    
    @Override
    public CompositeByteBuf setIndex(final int readerIndex, final int writerIndex) {
        return (CompositeByteBuf)super.setIndex(readerIndex, writerIndex);
    }
    
    @Override
    public CompositeByteBuf clear() {
        return (CompositeByteBuf)super.clear();
    }
    
    @Override
    public CompositeByteBuf markReaderIndex() {
        return (CompositeByteBuf)super.markReaderIndex();
    }
    
    @Override
    public CompositeByteBuf resetReaderIndex() {
        return (CompositeByteBuf)super.resetReaderIndex();
    }
    
    @Override
    public CompositeByteBuf markWriterIndex() {
        return (CompositeByteBuf)super.markWriterIndex();
    }
    
    @Override
    public CompositeByteBuf resetWriterIndex() {
        return (CompositeByteBuf)super.resetWriterIndex();
    }
    
    @Override
    public CompositeByteBuf ensureWritable(final int minWritableBytes) {
        return (CompositeByteBuf)super.ensureWritable(minWritableBytes);
    }
    
    @Override
    public CompositeByteBuf getBytes(final int index, final ByteBuf dst) {
        return (CompositeByteBuf)super.getBytes(index, dst);
    }
    
    @Override
    public CompositeByteBuf getBytes(final int index, final ByteBuf dst, final int length) {
        return (CompositeByteBuf)super.getBytes(index, dst, length);
    }
    
    @Override
    public CompositeByteBuf getBytes(final int index, final byte[] dst) {
        return (CompositeByteBuf)super.getBytes(index, dst);
    }
    
    @Override
    public CompositeByteBuf setBoolean(final int index, final boolean value) {
        return (CompositeByteBuf)super.setBoolean(index, value);
    }
    
    @Override
    public CompositeByteBuf setChar(final int index, final int value) {
        return (CompositeByteBuf)super.setChar(index, value);
    }
    
    @Override
    public CompositeByteBuf setFloat(final int index, final float value) {
        return (CompositeByteBuf)super.setFloat(index, value);
    }
    
    @Override
    public CompositeByteBuf setDouble(final int index, final double value) {
        return (CompositeByteBuf)super.setDouble(index, value);
    }
    
    @Override
    public CompositeByteBuf setBytes(final int index, final ByteBuf src) {
        return (CompositeByteBuf)super.setBytes(index, src);
    }
    
    @Override
    public CompositeByteBuf setBytes(final int index, final ByteBuf src, final int length) {
        return (CompositeByteBuf)super.setBytes(index, src, length);
    }
    
    @Override
    public CompositeByteBuf setBytes(final int index, final byte[] src) {
        return (CompositeByteBuf)super.setBytes(index, src);
    }
    
    @Override
    public CompositeByteBuf setZero(final int index, final int length) {
        return (CompositeByteBuf)super.setZero(index, length);
    }
    
    @Override
    public CompositeByteBuf readBytes(final ByteBuf dst) {
        return (CompositeByteBuf)super.readBytes(dst);
    }
    
    @Override
    public CompositeByteBuf readBytes(final ByteBuf dst, final int length) {
        return (CompositeByteBuf)super.readBytes(dst, length);
    }
    
    @Override
    public CompositeByteBuf readBytes(final ByteBuf dst, final int dstIndex, final int length) {
        return (CompositeByteBuf)super.readBytes(dst, dstIndex, length);
    }
    
    @Override
    public CompositeByteBuf readBytes(final byte[] dst) {
        return (CompositeByteBuf)super.readBytes(dst);
    }
    
    @Override
    public CompositeByteBuf readBytes(final byte[] dst, final int dstIndex, final int length) {
        return (CompositeByteBuf)super.readBytes(dst, dstIndex, length);
    }
    
    @Override
    public CompositeByteBuf readBytes(final ByteBuffer dst) {
        return (CompositeByteBuf)super.readBytes(dst);
    }
    
    @Override
    public CompositeByteBuf readBytes(final OutputStream out, final int length) throws IOException {
        return (CompositeByteBuf)super.readBytes(out, length);
    }
    
    @Override
    public CompositeByteBuf skipBytes(final int length) {
        return (CompositeByteBuf)super.skipBytes(length);
    }
    
    @Override
    public CompositeByteBuf writeBoolean(final boolean value) {
        return (CompositeByteBuf)super.writeBoolean(value);
    }
    
    @Override
    public CompositeByteBuf writeByte(final int value) {
        return (CompositeByteBuf)super.writeByte(value);
    }
    
    @Override
    public CompositeByteBuf writeShort(final int value) {
        return (CompositeByteBuf)super.writeShort(value);
    }
    
    @Override
    public CompositeByteBuf writeMedium(final int value) {
        return (CompositeByteBuf)super.writeMedium(value);
    }
    
    @Override
    public CompositeByteBuf writeInt(final int value) {
        return (CompositeByteBuf)super.writeInt(value);
    }
    
    @Override
    public CompositeByteBuf writeLong(final long value) {
        return (CompositeByteBuf)super.writeLong(value);
    }
    
    @Override
    public CompositeByteBuf writeChar(final int value) {
        return (CompositeByteBuf)super.writeChar(value);
    }
    
    @Override
    public CompositeByteBuf writeFloat(final float value) {
        return (CompositeByteBuf)super.writeFloat(value);
    }
    
    @Override
    public CompositeByteBuf writeDouble(final double value) {
        return (CompositeByteBuf)super.writeDouble(value);
    }
    
    @Override
    public CompositeByteBuf writeBytes(final ByteBuf src) {
        return (CompositeByteBuf)super.writeBytes(src);
    }
    
    @Override
    public CompositeByteBuf writeBytes(final ByteBuf src, final int length) {
        return (CompositeByteBuf)super.writeBytes(src, length);
    }
    
    @Override
    public CompositeByteBuf writeBytes(final ByteBuf src, final int srcIndex, final int length) {
        return (CompositeByteBuf)super.writeBytes(src, srcIndex, length);
    }
    
    @Override
    public CompositeByteBuf writeBytes(final byte[] src) {
        return (CompositeByteBuf)super.writeBytes(src);
    }
    
    @Override
    public CompositeByteBuf writeBytes(final byte[] src, final int srcIndex, final int length) {
        return (CompositeByteBuf)super.writeBytes(src, srcIndex, length);
    }
    
    @Override
    public CompositeByteBuf writeBytes(final ByteBuffer src) {
        return (CompositeByteBuf)super.writeBytes(src);
    }
    
    @Override
    public CompositeByteBuf writeZero(final int length) {
        return (CompositeByteBuf)super.writeZero(length);
    }
    
    @Override
    public CompositeByteBuf retain(final int increment) {
        return (CompositeByteBuf)super.retain(increment);
    }
    
    @Override
    public CompositeByteBuf retain() {
        return (CompositeByteBuf)super.retain();
    }
    
    @Override
    public ByteBuffer[] nioBuffers() {
        return this.nioBuffers(this.readerIndex(), this.readableBytes());
    }
    
    @Override
    public CompositeByteBuf discardSomeReadBytes() {
        return this.discardReadComponents();
    }
    
    @Override
    protected void deallocate() {
        if (this.freed) {
            return;
        }
        this.freed = true;
        this.resumeIntermediaryDeallocations();
        for (final Component c : this.components) {
            c.freeIfNecessary();
        }
        this.leak.close();
    }
    
    @Override
    public CompositeByteBuf suspendIntermediaryDeallocations() {
        this.ensureAccessible();
        if (this.suspendedDeallocations == null) {
            this.suspendedDeallocations = new ArrayDeque<ByteBuf>(2);
        }
        return this;
    }
    
    @Override
    public CompositeByteBuf resumeIntermediaryDeallocations() {
        if (this.suspendedDeallocations == null) {
            return this;
        }
        final Queue<ByteBuf> suspendedDeallocations = this.suspendedDeallocations;
        this.suspendedDeallocations = null;
        for (final ByteBuf buf : suspendedDeallocations) {
            buf.release();
        }
        return this;
    }
    
    @Override
    public ByteBuf unwrap() {
        return null;
    }
    
    static {
        EMPTY_NIOBUFFERS = new ByteBuffer[0];
    }
    
    private final class Component
    {
        final ByteBuf buf;
        final int length;
        int offset;
        int endOffset;
        
        Component(final ByteBuf buf) {
            this.buf = buf;
            this.length = buf.readableBytes();
        }
        
        void freeIfNecessary() {
            if (DefaultCompositeByteBuf.this.suspendedDeallocations == null) {
                this.buf.release();
            }
            else {
                DefaultCompositeByteBuf.this.suspendedDeallocations.add(this.buf);
            }
        }
    }
}
