// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToByteEncoder;

public class SnappyFramedEncoder extends ByteToByteEncoder
{
    private static final int MIN_COMPRESSIBLE_LENGTH = 18;
    private static final byte[] STREAM_START;
    private final Snappy snappy;
    private boolean started;
    
    public SnappyFramedEncoder() {
        this.snappy = new Snappy();
    }
    
    @Override
    protected void encode(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) throws Exception {
        if (!in.isReadable()) {
            return;
        }
        if (!this.started) {
            this.started = true;
            out.writeBytes(SnappyFramedEncoder.STREAM_START);
        }
        int dataLength = in.readableBytes();
        if (dataLength > 18) {
            while (true) {
                final int lengthIdx = out.writerIndex() + 1;
                if (dataLength < 18) {
                    final ByteBuf slice = in.readSlice(dataLength);
                    writeUnencodedChunk(slice, out, dataLength);
                    break;
                }
                out.writeInt(0);
                if (dataLength <= 32767) {
                    final ByteBuf slice = in.readSlice(dataLength);
                    calculateAndWriteChecksum(slice, out);
                    this.snappy.encode(slice, out, dataLength);
                    setChunkLength(out, lengthIdx);
                    break;
                }
                final ByteBuf slice = in.readSlice(32767);
                calculateAndWriteChecksum(slice, out);
                this.snappy.encode(slice, out, 32767);
                setChunkLength(out, lengthIdx);
                dataLength -= 32767;
            }
        }
        else {
            writeUnencodedChunk(in, out, dataLength);
        }
    }
    
    private static void writeUnencodedChunk(final ByteBuf in, final ByteBuf out, final int dataLength) {
        out.writeByte(1);
        writeChunkLength(out, dataLength + 4);
        calculateAndWriteChecksum(in, out);
        out.writeBytes(in, dataLength);
    }
    
    private static void setChunkLength(final ByteBuf out, final int lengthIdx) {
        final int chunkLength = out.writerIndex() - lengthIdx - 3;
        if (chunkLength >>> 24 != 0) {
            throw new CompressionException("compressed data too large: " + chunkLength);
        }
        out.setByte(lengthIdx, chunkLength & 0xFF);
        out.setByte(lengthIdx + 1, chunkLength >>> 8 & 0xFF);
        out.setByte(lengthIdx + 2, chunkLength >>> 16 & 0xFF);
    }
    
    private static void writeChunkLength(final ByteBuf out, final int chunkLength) {
        out.writeByte(chunkLength & 0xFF);
        out.writeByte(chunkLength >>> 8 & 0xFF);
        out.writeByte(chunkLength >>> 16 & 0xFF);
    }
    
    private static void calculateAndWriteChecksum(final ByteBuf slice, final ByteBuf out) {
        final int checksum = SnappyChecksumUtil.calculateChecksum(slice);
        out.writeByte(checksum & 0xFF);
        out.writeByte(checksum >>> 8 & 0xFF);
        out.writeByte(checksum >>> 16 & 0xFF);
        out.writeByte(checksum >>> 24);
    }
    
    static {
        STREAM_START = new byte[] { -128, 6, 0, 0, 115, 78, 97, 80, 112, 89 };
    }
}
