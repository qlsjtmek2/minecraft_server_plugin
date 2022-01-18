// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;

abstract class SpdyHeaderBlockDecompressor
{
    static SpdyHeaderBlockDecompressor newInstance(final int version) {
        return new SpdyHeaderBlockZlibDecompressor(version);
    }
    
    abstract void setInput(final ByteBuf p0);
    
    abstract int decode(final ByteBuf p0) throws Exception;
    
    abstract void end();
}
