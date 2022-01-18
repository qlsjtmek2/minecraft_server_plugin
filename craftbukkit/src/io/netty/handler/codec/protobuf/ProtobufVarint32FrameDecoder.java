// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.protobuf;

import io.netty.handler.codec.CorruptedFrameException;
import com.google.protobuf.CodedInputStream;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ProtobufVarint32FrameDecoder extends ByteToMessageDecoder
{
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        in.markReaderIndex();
        final byte[] buf = new byte[5];
        int i = 0;
        while (i < buf.length) {
            if (!in.isReadable()) {
                in.resetReaderIndex();
                return null;
            }
            buf[i] = in.readByte();
            if (buf[i] >= 0) {
                final int length = CodedInputStream.newInstance(buf, 0, i + 1).readRawVarint32();
                if (length < 0) {
                    throw new CorruptedFrameException("negative length: " + length);
                }
                if (in.readableBytes() < length) {
                    in.resetReaderIndex();
                    return null;
                }
                return in.readBytes(length);
            }
            else {
                ++i;
            }
        }
        throw new CorruptedFrameException("length wider than 32-bit");
    }
}
