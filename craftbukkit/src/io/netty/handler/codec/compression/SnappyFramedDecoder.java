// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.compression;

import java.nio.charset.Charset;
import java.util.Arrays;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToByteDecoder;

public class SnappyFramedDecoder extends ByteToByteDecoder
{
    private static final byte[] SNAPPY;
    private final Snappy snappy;
    private final boolean validateChecksums;
    private boolean started;
    private boolean corrupted;
    
    public SnappyFramedDecoder() {
        this(false);
    }
    
    public SnappyFramedDecoder(final boolean validateChecksums) {
        this.snappy = new Snappy();
        this.validateChecksums = validateChecksums;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) throws Exception {
        if (this.corrupted) {
            in.skipBytes(in.readableBytes());
            return;
        }
        try {
            final int idx = in.readerIndex();
            final int inSize = in.writerIndex() - idx;
            if (inSize < 4) {
                return;
            }
            final int chunkTypeVal = in.getUnsignedByte(idx);
            final ChunkType chunkType = mapChunkType((byte)chunkTypeVal);
            final int chunkLength = in.getUnsignedByte(idx + 1) | in.getUnsignedByte(idx + 2) << 8 | in.getUnsignedByte(idx + 3) << 16;
            switch (chunkType) {
                case STREAM_IDENTIFIER: {
                    if (chunkLength != SnappyFramedDecoder.SNAPPY.length) {
                        throw new CompressionException("Unexpected length of stream identifier: " + chunkLength);
                    }
                    if (inSize < 4 + SnappyFramedDecoder.SNAPPY.length) {
                        break;
                    }
                    final byte[] identifier = new byte[chunkLength];
                    in.skipBytes(4).readBytes(identifier);
                    if (!Arrays.equals(identifier, SnappyFramedDecoder.SNAPPY)) {
                        throw new CompressionException("Unexpected stream identifier contents. Mismatched snappy protocol version?");
                    }
                    this.started = true;
                    break;
                }
                case RESERVED_SKIPPABLE: {
                    if (!this.started) {
                        throw new CompressionException("Received RESERVED_SKIPPABLE tag before STREAM_IDENTIFIER");
                    }
                    if (inSize < 4 + chunkLength) {
                        return;
                    }
                    in.skipBytes(4 + chunkLength);
                    break;
                }
                case RESERVED_UNSKIPPABLE: {
                    throw new CompressionException("Found reserved unskippable chunk type: 0x" + Integer.toHexString(chunkTypeVal));
                }
                case UNCOMPRESSED_DATA: {
                    if (!this.started) {
                        throw new CompressionException("Received UNCOMPRESSED_DATA tag before STREAM_IDENTIFIER");
                    }
                    if (chunkLength > 65540) {
                        throw new CompressionException("Received UNCOMPRESSED_DATA larger than 65540 bytes");
                    }
                    if (inSize < 4 + chunkLength) {
                        return;
                    }
                    in.skipBytes(4);
                    if (this.validateChecksums) {
                        final int checksum = in.readUnsignedByte() | in.readUnsignedByte() << 8 | in.readUnsignedByte() << 16 | in.readUnsignedByte() << 24;
                        final ByteBuf data = in.readSlice(chunkLength - 4);
                        SnappyChecksumUtil.validateChecksum(data, checksum);
                        out.writeBytes(data);
                        break;
                    }
                    in.skipBytes(4);
                    in.readBytes(out, chunkLength - 4);
                    break;
                }
                case COMPRESSED_DATA: {
                    if (!this.started) {
                        throw new CompressionException("Received COMPRESSED_DATA tag before STREAM_IDENTIFIER");
                    }
                    if (inSize < 4 + chunkLength) {
                        return;
                    }
                    in.skipBytes(4);
                    final int checksum = in.readUnsignedByte() | in.readUnsignedByte() << 8 | in.readUnsignedByte() << 16 | in.readUnsignedByte() << 24;
                    if (this.validateChecksums) {
                        final ByteBuf uncompressed = ctx.alloc().buffer();
                        this.snappy.decode(in.readSlice(chunkLength - 4), uncompressed);
                        SnappyChecksumUtil.validateChecksum(uncompressed, checksum);
                        out.writeBytes(uncompressed);
                    }
                    else {
                        this.snappy.decode(in.readSlice(chunkLength - 4), out);
                    }
                    this.snappy.reset();
                    break;
                }
            }
        }
        catch (Exception e) {
            this.corrupted = true;
            throw e;
        }
    }
    
    static ChunkType mapChunkType(final byte type) {
        if (type == 0) {
            return ChunkType.COMPRESSED_DATA;
        }
        if (type == 1) {
            return ChunkType.UNCOMPRESSED_DATA;
        }
        if (type == -128) {
            return ChunkType.STREAM_IDENTIFIER;
        }
        if ((type & 0x80) == 0x80) {
            return ChunkType.RESERVED_SKIPPABLE;
        }
        return ChunkType.RESERVED_UNSKIPPABLE;
    }
    
    static {
        SNAPPY = "sNaPpY".getBytes(Charset.forName("US-ASCII"));
    }
    
    enum ChunkType
    {
        STREAM_IDENTIFIER, 
        COMPRESSED_DATA, 
        UNCOMPRESSED_DATA, 
        RESERVED_UNSKIPPABLE, 
        RESERVED_SKIPPABLE;
    }
}
