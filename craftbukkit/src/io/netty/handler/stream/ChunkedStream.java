// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class ChunkedStream implements ChunkedByteInput
{
    static final int DEFAULT_CHUNK_SIZE = 8192;
    private final PushbackInputStream in;
    private final int chunkSize;
    private long offset;
    
    public ChunkedStream(final InputStream in) {
        this(in, 8192);
    }
    
    public ChunkedStream(final InputStream in, final int chunkSize) {
        if (in == null) {
            throw new NullPointerException("in");
        }
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize: " + chunkSize + " (expected: a positive integer)");
        }
        if (in instanceof PushbackInputStream) {
            this.in = (PushbackInputStream)in;
        }
        else {
            this.in = new PushbackInputStream(in);
        }
        this.chunkSize = chunkSize;
    }
    
    public long transferredBytes() {
        return this.offset;
    }
    
    @Override
    public boolean isEndOfInput() throws Exception {
        final int b = this.in.read();
        if (b < 0) {
            return true;
        }
        this.in.unread(b);
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
        final int availableBytes = this.in.available();
        int chunkSize;
        if (availableBytes <= 0) {
            chunkSize = this.chunkSize;
        }
        else {
            chunkSize = Math.min(this.chunkSize, this.in.available());
        }
        this.offset += buffer.writeBytes(this.in, chunkSize);
        return true;
    }
}
