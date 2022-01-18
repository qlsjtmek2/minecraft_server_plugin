// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.compression.CompressionException;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.Deflater;

class SpdyHeaderBlockJZlibCompressor extends SpdyHeaderBlockCompressor
{
    private final Deflater z;
    
    public SpdyHeaderBlockJZlibCompressor(final int version, final int compressionLevel, final int windowBits, final int memLevel) {
        this.z = new Deflater();
        if (version < 2 || version > 3) {
            throw new IllegalArgumentException("unsupported version: " + version);
        }
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        if (windowBits < 9 || windowBits > 15) {
            throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
        }
        if (memLevel < 1 || memLevel > 9) {
            throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
        }
        int resultCode = this.z.deflateInit(compressionLevel, windowBits, memLevel, JZlib.W_ZLIB);
        if (resultCode != 0) {
            throw new CompressionException("failed to initialize an SPDY header block deflater: " + resultCode);
        }
        if (version < 3) {
            resultCode = this.z.deflateSetDictionary(SpdyCodecUtil.SPDY2_DICT, SpdyCodecUtil.SPDY2_DICT.length);
        }
        else {
            resultCode = this.z.deflateSetDictionary(SpdyCodecUtil.SPDY_DICT, SpdyCodecUtil.SPDY_DICT.length);
        }
        if (resultCode != 0) {
            throw new CompressionException("failed to set the SPDY dictionary: " + resultCode);
        }
    }
    
    public void setInput(final ByteBuf decompressed) {
        final byte[] in = new byte[decompressed.readableBytes()];
        decompressed.readBytes(in);
        this.z.next_in = in;
        this.z.next_in_index = 0;
        this.z.avail_in = in.length;
    }
    
    public void encode(final ByteBuf compressed) {
        try {
            final byte[] out = new byte[(int)Math.ceil(this.z.next_in.length * 1.001) + 12];
            this.z.next_out = out;
            this.z.next_out_index = 0;
            this.z.avail_out = out.length;
            final int resultCode = this.z.deflate(2);
            if (resultCode != 0) {
                throw new CompressionException("compression failure: " + resultCode);
            }
            if (this.z.next_out_index != 0) {
                compressed.writeBytes(out, 0, this.z.next_out_index);
            }
        }
        finally {
            this.z.next_in = null;
            this.z.next_out = null;
        }
    }
    
    public void end() {
        this.z.deflateEnd();
        this.z.next_in = null;
        this.z.next_out = null;
    }
}
