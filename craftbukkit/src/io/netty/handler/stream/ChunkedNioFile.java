// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.stream;

import java.nio.channels.ScatteringByteChannel;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.nio.channels.FileChannel;

public class ChunkedNioFile implements ChunkedByteInput
{
    private final FileChannel in;
    private final long startOffset;
    private final long endOffset;
    private final int chunkSize;
    private long offset;
    
    public ChunkedNioFile(final File in) throws IOException {
        this(new FileInputStream(in).getChannel());
    }
    
    public ChunkedNioFile(final File in, final int chunkSize) throws IOException {
        this(new FileInputStream(in).getChannel(), chunkSize);
    }
    
    public ChunkedNioFile(final FileChannel in) throws IOException {
        this(in, 8192);
    }
    
    public ChunkedNioFile(final FileChannel in, final int chunkSize) throws IOException {
        this(in, 0L, in.size(), chunkSize);
    }
    
    public ChunkedNioFile(final FileChannel in, final long offset, final long length, final int chunkSize) throws IOException {
        if (in == null) {
            throw new NullPointerException("in");
        }
        if (offset < 0L) {
            throw new IllegalArgumentException("offset: " + offset + " (expected: 0 or greater)");
        }
        if (length < 0L) {
            throw new IllegalArgumentException("length: " + length + " (expected: 0 or greater)");
        }
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize: " + chunkSize + " (expected: a positive integer)");
        }
        if (offset != 0L) {
            in.position(offset);
        }
        this.in = in;
        this.chunkSize = chunkSize;
        this.startOffset = offset;
        this.offset = offset;
        this.endOffset = offset + length;
    }
    
    public long startOffset() {
        return this.startOffset;
    }
    
    public long endOffset() {
        return this.endOffset;
    }
    
    public long currentOffset() {
        return this.offset;
    }
    
    @Override
    public boolean isEndOfInput() throws Exception {
        return this.offset >= this.endOffset || !this.in.isOpen();
    }
    
    @Override
    public void close() throws Exception {
        this.in.close();
    }
    
    @Override
    public boolean readChunk(final ByteBuf buffer) throws Exception {
        final long offset = this.offset;
        if (offset >= this.endOffset) {
            return false;
        }
        final int chunkSize = (int)Math.min(this.chunkSize, this.endOffset - offset);
        int readBytes = 0;
        do {
            final int localReadBytes = buffer.writeBytes(this.in, chunkSize - readBytes);
            if (localReadBytes < 0) {
                break;
            }
            readBytes += localReadBytes;
        } while (readBytes != chunkSize);
        this.offset += readBytes;
        return true;
    }
}
