// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.compression;

import java.util.zip.CRC32;
import io.netty.buffer.ByteBuf;

final class SnappyChecksumUtil
{
    static void validateChecksum(final ByteBuf slice, final int checksum) {
        if (calculateChecksum(slice) != checksum) {
            throw new CompressionException("Uncompressed data did not match checksum");
        }
    }
    
    static int calculateChecksum(final ByteBuf slice) {
        final CRC32 crc32 = new CRC32();
        try {
            final byte[] array = new byte[slice.readableBytes()];
            slice.markReaderIndex();
            slice.readBytes(array);
            slice.resetReaderIndex();
            crc32.update(array);
            return maskChecksum((int)crc32.getValue());
        }
        finally {
            crc32.reset();
        }
    }
    
    static int maskChecksum(final int checksum) {
        return (checksum >> 15 | checksum << 17) - 1568478504;
    }
}
