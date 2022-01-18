// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.PlatformDependent;

abstract class SpdyHeaderBlockCompressor
{
    static SpdyHeaderBlockCompressor newInstance(final int version, final int compressionLevel, final int windowBits, final int memLevel) {
        if (PlatformDependent.javaVersion() >= 7) {
            return new SpdyHeaderBlockZlibCompressor(version, compressionLevel);
        }
        return new SpdyHeaderBlockJZlibCompressor(version, compressionLevel, windowBits, memLevel);
    }
    
    abstract void setInput(final ByteBuf p0);
    
    abstract void encode(final ByteBuf p0);
    
    abstract void end();
}
