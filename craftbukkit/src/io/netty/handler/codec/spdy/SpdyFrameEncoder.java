// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.util.CharsetUtil;
import io.netty.buffer.Unpooled;
import java.util.Iterator;
import java.util.Set;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.buffer.ByteBuf;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SpdyFrameEncoder extends MessageToByteEncoder<SpdyDataOrControlFrame>
{
    private final int version;
    private volatile boolean finished;
    private final SpdyHeaderBlockCompressor headerBlockCompressor;
    
    public SpdyFrameEncoder(final int version) {
        this(version, 6, 15, 8);
    }
    
    public SpdyFrameEncoder(final int version, final int compressionLevel, final int windowBits, final int memLevel) {
        if (version < 2 || version > 3) {
            throw new IllegalArgumentException("unknown version: " + version);
        }
        this.version = version;
        this.headerBlockCompressor = SpdyHeaderBlockCompressor.newInstance(version, compressionLevel, windowBits, memLevel);
    }
    
    @Override
    public void beforeAdd(final ChannelHandlerContext ctx) throws Exception {
        ctx.channel().closeFuture().addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture future) throws Exception {
                synchronized (SpdyFrameEncoder.this.headerBlockCompressor) {
                    if (SpdyFrameEncoder.this.finished) {
                        return;
                    }
                    SpdyFrameEncoder.this.finished = true;
                    SpdyFrameEncoder.this.headerBlockCompressor.end();
                }
            }
        });
    }
    
    @Override
    protected void encode(final ChannelHandlerContext ctx, final SpdyDataOrControlFrame msg, final ByteBuf out) throws Exception {
        if (msg instanceof SpdyDataFrame) {
            final SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
            final ByteBuf data = spdyDataFrame.data();
            final byte flags = (byte)(spdyDataFrame.isLast() ? 1 : 0);
            out.ensureWritable(8 + data.readableBytes());
            out.writeInt(spdyDataFrame.getStreamId() & Integer.MAX_VALUE);
            out.writeByte(flags);
            out.writeMedium(data.readableBytes());
            out.writeBytes(data, data.readerIndex(), data.readableBytes());
        }
        else if (msg instanceof SpdySynStreamFrame) {
            final SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
            final ByteBuf data = this.compressHeaderBlock(encodeHeaderBlock(this.version, spdySynStreamFrame));
            byte flags = (byte)(spdySynStreamFrame.isLast() ? 1 : 0);
            if (spdySynStreamFrame.isUnidirectional()) {
                flags |= 0x2;
            }
            final int headerBlockLength = data.readableBytes();
            int length;
            if (this.version < 3) {
                length = ((headerBlockLength == 0) ? 12 : (10 + headerBlockLength));
            }
            else {
                length = 10 + headerBlockLength;
            }
            out.ensureWritable(8 + length);
            out.writeShort(this.version | 0x8000);
            out.writeShort(1);
            out.writeByte(flags);
            out.writeMedium(length);
            out.writeInt(spdySynStreamFrame.getStreamId());
            out.writeInt(spdySynStreamFrame.getAssociatedToStreamId());
            if (this.version < 3) {
                byte priority = spdySynStreamFrame.getPriority();
                if (priority > 3) {
                    priority = 3;
                }
                out.writeShort((priority & 0xFF) << 14);
            }
            else {
                out.writeShort((spdySynStreamFrame.getPriority() & 0xFF) << 13);
            }
            if (this.version < 3 && data.readableBytes() == 0) {
                out.writeShort(0);
            }
            out.writeBytes(data, data.readerIndex(), headerBlockLength);
        }
        else if (msg instanceof SpdySynReplyFrame) {
            final SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
            final ByteBuf data = this.compressHeaderBlock(encodeHeaderBlock(this.version, spdySynReplyFrame));
            final byte flags = (byte)(spdySynReplyFrame.isLast() ? 1 : 0);
            final int headerBlockLength = data.readableBytes();
            int length;
            if (this.version < 3) {
                length = ((headerBlockLength == 0) ? 8 : (6 + headerBlockLength));
            }
            else {
                length = 4 + headerBlockLength;
            }
            out.ensureWritable(8 + length);
            out.writeShort(this.version | 0x8000);
            out.writeShort(2);
            out.writeByte(flags);
            out.writeMedium(length);
            out.writeInt(spdySynReplyFrame.getStreamId());
            if (this.version < 3) {
                if (headerBlockLength == 0) {
                    out.writeInt(0);
                }
                else {
                    out.writeShort(0);
                }
            }
            out.writeBytes(data, data.readerIndex(), headerBlockLength);
        }
        else if (msg instanceof SpdyRstStreamFrame) {
            final SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
            out.ensureWritable(16);
            out.writeShort(this.version | 0x8000);
            out.writeShort(3);
            out.writeInt(8);
            out.writeInt(spdyRstStreamFrame.getStreamId());
            out.writeInt(spdyRstStreamFrame.getStatus().getCode());
        }
        else if (msg instanceof SpdySettingsFrame) {
            final SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
            final byte flags2 = (byte)(spdySettingsFrame.clearPreviouslyPersistedSettings() ? 1 : 0);
            final Set<Integer> IDs = spdySettingsFrame.getIds();
            final int numEntries = IDs.size();
            final int length = 4 + numEntries * 8;
            out.ensureWritable(8 + length);
            out.writeShort(this.version | 0x8000);
            out.writeShort(4);
            out.writeByte(flags2);
            out.writeMedium(length);
            out.writeInt(numEntries);
            for (final Integer ID : IDs) {
                final int id = ID;
                byte ID_flags = 0;
                if (spdySettingsFrame.isPersistValue(id)) {
                    ID_flags |= 0x1;
                }
                if (spdySettingsFrame.isPersisted(id)) {
                    ID_flags |= 0x2;
                }
                if (this.version < 3) {
                    out.writeByte(id & 0xFF);
                    out.writeByte(id >> 8 & 0xFF);
                    out.writeByte(id >> 16 & 0xFF);
                    out.writeByte(ID_flags);
                }
                else {
                    out.writeByte(ID_flags);
                    out.writeMedium(id);
                }
                out.writeInt(spdySettingsFrame.getValue(id));
            }
        }
        else if (msg instanceof SpdyNoOpFrame) {
            out.ensureWritable(8);
            out.writeShort(this.version | 0x8000);
            out.writeShort(5);
            out.writeInt(0);
        }
        else if (msg instanceof SpdyPingFrame) {
            final SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
            out.ensureWritable(12);
            out.writeShort(this.version | 0x8000);
            out.writeShort(6);
            out.writeInt(4);
            out.writeInt(spdyPingFrame.getId());
        }
        else if (msg instanceof SpdyGoAwayFrame) {
            final SpdyGoAwayFrame spdyGoAwayFrame = (SpdyGoAwayFrame)msg;
            final int length2 = (this.version < 3) ? 4 : 8;
            out.ensureWritable(8 + length2);
            out.writeShort(this.version | 0x8000);
            out.writeShort(7);
            out.writeInt(length2);
            out.writeInt(spdyGoAwayFrame.getLastGoodStreamId());
            if (this.version >= 3) {
                out.writeInt(spdyGoAwayFrame.getStatus().getCode());
            }
        }
        else if (msg instanceof SpdyHeadersFrame) {
            final SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
            final ByteBuf data = this.compressHeaderBlock(encodeHeaderBlock(this.version, spdyHeadersFrame));
            final byte flags = (byte)(spdyHeadersFrame.isLast() ? 1 : 0);
            final int headerBlockLength = data.readableBytes();
            int length;
            if (this.version < 3) {
                length = ((headerBlockLength == 0) ? 4 : (6 + headerBlockLength));
            }
            else {
                length = 4 + headerBlockLength;
            }
            out.ensureWritable(8 + length);
            out.writeShort(this.version | 0x8000);
            out.writeShort(8);
            out.writeByte(flags);
            out.writeMedium(length);
            out.writeInt(spdyHeadersFrame.getStreamId());
            if (this.version < 3 && headerBlockLength != 0) {
                out.writeShort(0);
            }
            out.writeBytes(data, data.readerIndex(), headerBlockLength);
        }
        else {
            if (!(msg instanceof SpdyWindowUpdateFrame)) {
                throw new UnsupportedMessageTypeException(msg, (Class<?>[])new Class[0]);
            }
            final SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame)msg;
            out.ensureWritable(16);
            out.writeShort(this.version | 0x8000);
            out.writeShort(9);
            out.writeInt(8);
            out.writeInt(spdyWindowUpdateFrame.getStreamId());
            out.writeInt(spdyWindowUpdateFrame.getDeltaWindowSize());
        }
    }
    
    private static void writeLengthField(final int version, final ByteBuf buffer, final int length) {
        if (version < 3) {
            buffer.writeShort(length);
        }
        else {
            buffer.writeInt(length);
        }
    }
    
    private static void setLengthField(final int version, final ByteBuf buffer, final int writerIndex, final int length) {
        if (version < 3) {
            buffer.setShort(writerIndex, length);
        }
        else {
            buffer.setInt(writerIndex, length);
        }
    }
    
    private static ByteBuf encodeHeaderBlock(final int version, final SpdyHeaderBlock headerFrame) throws Exception {
        final Set<String> names = headerFrame.headers().names();
        final int numHeaders = names.size();
        if (numHeaders == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (numHeaders > 65535) {
            throw new IllegalArgumentException("header block contains too many headers");
        }
        final ByteBuf headerBlock = Unpooled.buffer();
        writeLengthField(version, headerBlock, numHeaders);
        for (final String name : names) {
            final byte[] nameBytes = name.getBytes(CharsetUtil.UTF_8);
            writeLengthField(version, headerBlock, nameBytes.length);
            headerBlock.writeBytes(nameBytes);
            final int savedIndex = headerBlock.writerIndex();
            int valueLength = 0;
            writeLengthField(version, headerBlock, valueLength);
            for (final String value : headerFrame.headers().getAll(name)) {
                final byte[] valueBytes = value.getBytes(CharsetUtil.UTF_8);
                if (valueBytes.length > 0) {
                    headerBlock.writeBytes(valueBytes);
                    headerBlock.writeByte(0);
                    valueLength += valueBytes.length + 1;
                }
            }
            if (valueLength == 0) {
                if (version < 3) {
                    throw new IllegalArgumentException("header value cannot be empty: " + name);
                }
            }
            else {
                --valueLength;
            }
            if (valueLength > 65535) {
                throw new IllegalArgumentException("header exceeds allowable length: " + name);
            }
            if (valueLength <= 0) {
                continue;
            }
            setLengthField(version, headerBlock, savedIndex, valueLength);
            headerBlock.writerIndex(headerBlock.writerIndex() - 1);
        }
        return headerBlock;
    }
    
    private synchronized ByteBuf compressHeaderBlock(final ByteBuf uncompressed) throws Exception {
        if (uncompressed.readableBytes() == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        final ByteBuf compressed = Unpooled.buffer();
        synchronized (this.headerBlockCompressor) {
            if (!this.finished) {
                this.headerBlockCompressor.setInput(uncompressed);
                this.headerBlockCompressor.encode(compressed);
            }
        }
        return compressed;
    }
}
