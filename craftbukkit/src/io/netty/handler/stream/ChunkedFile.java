// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.io.File;
import java.io.RandomAccessFile;

public class ChunkedFile implements ChunkedByteInput
{
    private final RandomAccessFile file;
    private final long startOffset;
    private final long endOffset;
    private final int chunkSize;
    private long offset;
    
    public ChunkedFile(final File file) throws IOException {
        this(file, 8192);
    }
    
    public ChunkedFile(final File file, final int chunkSize) throws IOException {
        this(new RandomAccessFile(file, "r"), chunkSize);
    }
    
    public ChunkedFile(final RandomAccessFile file) throws IOException {
        this(file, 8192);
    }
    
    public ChunkedFile(final RandomAccessFile file, final int chunkSize) throws IOException {
        this(file, 0L, file.length(), chunkSize);
    }
    
    public ChunkedFile(final RandomAccessFile file, final long offset, final long length, final int chunkSize) throws IOException {
        if (file == null) {
            throw new NullPointerException("file");
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
        this.file = file;
        this.startOffset = offset;
        this.offset = offset;
        this.endOffset = offset + length;
        this.chunkSize = chunkSize;
        file.seek(offset);
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
        return this.offset >= this.endOffset || !this.file.getChannel().isOpen();
    }
    
    @Override
    public void close() throws Exception {
        this.file.close();
    }
    
    @Override
    public boolean readChunk(final ByteBuf buffer) throws Exception {
        final long offset = this.offset;
        if (offset >= this.endOffset) {
            return false;
        }
        final int chunkSize = (int)Math.min(this.chunkSize, this.endOffset - offset);
        final byte[] chunk = new byte[chunkSize];
        this.file.readFully(chunk);
        buffer.writeBytes(chunk);
        this.offset = offset + chunkSize;
        return true;
    }
}
