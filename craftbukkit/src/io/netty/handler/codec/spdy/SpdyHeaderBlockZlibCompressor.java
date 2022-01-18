// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import java.util.zip.Deflater;

class SpdyHeaderBlockZlibCompressor extends SpdyHeaderBlockCompressor
{
    private final byte[] out;
    private final Deflater compressor;
    
    public SpdyHeaderBlockZlibCompressor(final int version, final int compressionLevel) {
        this.out = new byte[8192];
        if (version < 2 || version > 3) {
            throw new IllegalArgumentException("unsupported version: " + version);
        }
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        this.compressor = new Deflater(compressionLevel);
        if (version < 3) {
            this.compressor.setDictionary(SpdyCodecUtil.SPDY2_DICT);
        }
        else {
            this.compressor.setDictionary(SpdyCodecUtil.SPDY_DICT);
        }
    }
    
    public void setInput(final ByteBuf decompressed) {
        final byte[] in = new byte[decompressed.readableBytes()];
        decompressed.readBytes(in);
        this.compressor.setInput(in);
    }
    
    public void encode(final ByteBuf compressed) {
        int numBytes = this.out.length;
        while (numBytes == this.out.length) {
            numBytes = this.compressor.deflate(this.out, 0, this.out.length, 2);
            compressed.writeBytes(this.out, 0, numBytes);
        }
    }
    
    public void end() {
        this.compressor.end();
    }
}
