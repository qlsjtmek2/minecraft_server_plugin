// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

public class ChunkedNioStream implements ChunkedByteInput
{
    private final ReadableByteChannel in;
    private final int chunkSize;
    private long offset;
    private final ByteBuffer byteBuffer;
    
    public ChunkedNioStream(final ReadableByteChannel in) {
        this(in, 8192);
    }
    
    public ChunkedNioStream(final ReadableByteChannel in, final int chunkSize) {
        if (in == null) {
            throw new NullPointerException("in");
        }
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize: " + chunkSize + " (expected: a positive integer)");
        }
        this.in = in;
        this.offset = 0L;
        this.chunkSize = chunkSize;
        this.byteBuffer = ByteBuffer.allocate(chunkSize);
    }
    
    public long transferredBytes() {
        return this.offset;
    }
    
    @Override
    public boolean isEndOfInput() throws Exception {
        if (this.byteBuffer.position() > 0) {
            return false;
        }
        if (!this.in.isOpen()) {
            return true;
        }
        final int b = this.in.read(this.byteBuffer);
        if (b < 0) {
            return true;
        }
        this.offset += b;
        return false;
    }
    
    @Override
    public void close() throws Exception {
        this.in.close();
    }
    
    @Override
    public boolean readChunk(final ByteBuf buffer) throws Exception {
        if (this.isEndOfInput()) {
            return false;
        }
        int readBytes = this.byteBuffer.position();
        do {
            final int localReadBytes = this.in.read(this.byteBuffer);
            if (localReadBytes < 0) {
                break;
            }
            readBytes += localReadBytes;
            this.offset += localReadBytes;
        } while (readBytes != this.chunkSize);
        this.byteBuffer.flip();
        buffer.writeBytes(this.byteBuffer);
        this.byteBuffer.clear();
        return true;
    }
}
