// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import java.util.zip.DataFormatException;
import io.netty.buffer.ByteBuf;
import java.util.zip.Inflater;

class SpdyHeaderBlockZlibDecompressor extends SpdyHeaderBlockDecompressor
{
    private final int version;
    private final byte[] out;
    private final Inflater decompressor;
    
    public SpdyHeaderBlockZlibDecompressor(final int version) {
        this.out = new byte[8192];
        this.decompressor = new Inflater();
        if (version < 2 || version > 3) {
            throw new IllegalArgumentException("unsupported version: " + version);
        }
        this.version = version;
    }
    
    public void setInput(final ByteBuf compressed) {
        final byte[] in = new byte[compressed.readableBytes()];
        compressed.readBytes(in);
        this.decompressor.setInput(in);
    }
    
    public int decode(final ByteBuf decompressed) throws Exception {
        try {
            int numBytes = this.decompressor.inflate(this.out);
            if (numBytes == 0 && this.decompressor.needsDictionary()) {
                if (this.version < 3) {
                    this.decompressor.setDictionary(SpdyCodecUtil.SPDY2_DICT);
                }
                else {
                    this.decompressor.setDictionary(SpdyCodecUtil.SPDY_DICT);
                }
                numBytes = this.decompressor.inflate(this.out);
            }
            decompressed.writeBytes(this.out, 0, numBytes);
            return numBytes;
        }
        catch (DataFormatException e) {
            throw new SpdyProtocolException("Received invalid header block", e);
        }
    }
    
    public void end() {
        this.decompressor.end();
    }
}
