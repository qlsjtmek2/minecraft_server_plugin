// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;

final class UTF8Output
{
    private static final int UTF8_ACCEPT = 0;
    private static final int UTF8_REJECT = 12;
    private static final byte[] TYPES;
    private static final byte[] STATES;
    private int state;
    private int codep;
    private final StringBuilder stringBuilder;
    
    UTF8Output(final ByteBuf buffer) {
        this.state = 0;
        this.stringBuilder = new StringBuilder(buffer.readableBytes());
        this.write(buffer);
    }
    
    public void write(final ByteBuf buffer) {
        for (int i = buffer.readerIndex(); i < buffer.writerIndex(); ++i) {
            this.write(buffer.getByte(i));
        }
    }
    
    public void write(final byte[] bytes) {
        for (final byte b : bytes) {
            this.write(b);
        }
    }
    
    public void write(final int b) {
        final byte type = UTF8Output.TYPES[b & 0xFF];
        this.codep = ((this.state != 0) ? ((b & 0x3F) | this.codep << 6) : (255 >> type & b));
        this.state = UTF8Output.STATES[this.state + type];
        if (this.state == 0) {
            this.stringBuilder.append((char)this.codep);
        }
        else if (this.state == 12) {
            throw new UTF8Exception("bytes are not UTF-8");
        }
    }
    
    @Override
    public String toString() {
        if (this.state != 0) {
            throw new UTF8Exception("bytes are not UTF-8");
        }
        return this.stringBuilder.toString();
    }
    
    static {
        TYPES = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 11, 6, 6, 6, 5, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8 };
        STATES = new byte[] { 0, 12, 24, 36, 60, 96, 84, 12, 12, 12, 48, 72, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0, 12, 12, 12, 12, 12, 0, 12, 0, 12, 12, 12, 24, 12, 12, 12, 12, 12, 24, 12, 24, 12, 12, 12, 12, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 12, 12, 24, 12, 12, 12, 12, 12, 12, 12, 12, 12, 36, 12, 36, 12, 12, 12, 36, 12, 12, 12, 12, 12, 36, 12, 36, 12, 12, 12, 36, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12 };
    }
}
