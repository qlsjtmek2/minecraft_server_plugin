// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.handler.codec.TooLongFrameException;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.ByteToMessageDecoder;

public class SpdyFrameDecoder extends ByteToMessageDecoder
{
    private final int spdyVersion;
    private final int maxChunkSize;
    private final int maxHeaderSize;
    private final SpdyHeaderBlockDecompressor headerBlockDecompressor;
    private State state;
    private SpdySettingsFrame spdySettingsFrame;
    private SpdyHeaderBlock spdyHeaderBlock;
    private byte flags;
    private int length;
    private int version;
    private int type;
    private int streamID;
    private int headerSize;
    private int numHeaders;
    private ByteBuf decompressed;
    
    public SpdyFrameDecoder(final int version) {
        this(version, 8192, 16384);
    }
    
    public SpdyFrameDecoder(final int version, final int maxChunkSize, final int maxHeaderSize) {
        if (version < 2 || version > 3) {
            throw new IllegalArgumentException("unsupported version: " + version);
        }
        if (maxChunkSize <= 0) {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + maxChunkSize);
        }
        if (maxHeaderSize <= 0) {
            throw new IllegalArgumentException("maxHeaderSize must be a positive integer: " + maxHeaderSize);
        }
        this.spdyVersion = version;
        this.maxChunkSize = maxChunkSize;
        this.maxHeaderSize = maxHeaderSize;
        this.headerBlockDecompressor = SpdyHeaderBlockDecompressor.newInstance(version);
        this.state = State.READ_COMMON_HEADER;
    }
    
    public Object decodeLast(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        try {
            return this.decode(ctx, in);
        }
        finally {
            this.headerBlockDecompressor.end();
        }
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final ByteBuf buffer) throws Exception {
        switch (this.state) {
            case READ_COMMON_HEADER: {
                this.state = this.readCommonHeader(buffer);
                if (this.state == State.FRAME_ERROR) {
                    if (this.version != this.spdyVersion) {
                        fireProtocolException(ctx, "Unsupported version: " + this.version);
                    }
                    else {
                        this.fireInvalidControlFrameException(ctx);
                    }
                }
                if (this.length == 0) {
                    if (this.state == State.READ_DATA_FRAME) {
                        if (this.streamID == 0) {
                            this.state = State.FRAME_ERROR;
                            fireProtocolException(ctx, "Received invalid data frame");
                            return null;
                        }
                        final SpdyDataFrame spdyDataFrame = new DefaultSpdyDataFrame(this.streamID);
                        spdyDataFrame.setLast((this.flags & 0x1) != 0x0);
                        this.state = State.READ_COMMON_HEADER;
                        return spdyDataFrame;
                    }
                    else {
                        this.state = State.READ_COMMON_HEADER;
                    }
                }
                return null;
            }
            case READ_CONTROL_FRAME: {
                try {
                    final Object frame = this.readControlFrame(buffer);
                    if (frame != null) {
                        this.state = State.READ_COMMON_HEADER;
                    }
                    return frame;
                }
                catch (IllegalArgumentException e2) {
                    this.state = State.FRAME_ERROR;
                    this.fireInvalidControlFrameException(ctx);
                    return null;
                }
            }
            case READ_SETTINGS_FRAME: {
                if (this.spdySettingsFrame == null) {
                    if (buffer.readableBytes() < 4) {
                        return null;
                    }
                    final int numEntries = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                    buffer.skipBytes(4);
                    this.length -= 4;
                    if ((this.length & 0x7) != 0x0 || this.length >> 3 != numEntries) {
                        this.state = State.FRAME_ERROR;
                        this.fireInvalidControlFrameException(ctx);
                        return null;
                    }
                    this.spdySettingsFrame = new DefaultSpdySettingsFrame();
                    final boolean clear = (this.flags & 0x1) != 0x0;
                    this.spdySettingsFrame.setClearPreviouslyPersistedSettings(clear);
                }
                final int readableEntries = Math.min(buffer.readableBytes() >> 3, this.length >> 3);
                for (int i = 0; i < readableEntries; ++i) {
                    int ID;
                    byte ID_flags;
                    if (this.version < 3) {
                        ID = ((buffer.readByte() & 0xFF) | (buffer.readByte() & 0xFF) << 8 | (buffer.readByte() & 0xFF) << 16);
                        ID_flags = buffer.readByte();
                    }
                    else {
                        ID_flags = buffer.readByte();
                        ID = SpdyCodecUtil.getUnsignedMedium(buffer, buffer.readerIndex());
                        buffer.skipBytes(3);
                    }
                    final int value = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
                    buffer.skipBytes(4);
                    if (ID == 0) {
                        this.state = State.FRAME_ERROR;
                        this.spdySettingsFrame = null;
                        this.fireInvalidControlFrameException(ctx);
                        return null;
                    }
                    if (!this.spdySettingsFrame.isSet(ID)) {
                        final boolean persistVal = (ID_flags & 0x1) != 0x0;
                        final boolean persisted = (ID_flags & 0x2) != 0x0;
                        this.spdySettingsFrame.setValue(ID, value, persistVal, persisted);
                    }
                }
                this.length -= 8 * readableEntries;
                if (this.length == 0) {
                    this.state = State.READ_COMMON_HEADER;
                    final Object frame2 = this.spdySettingsFrame;
                    this.spdySettingsFrame = null;
                    return frame2;
                }
                return null;
            }
            case READ_HEADER_BLOCK_FRAME: {
                try {
                    this.spdyHeaderBlock = this.readHeaderBlockFrame(buffer);
                    if (this.spdyHeaderBlock != null) {
                        if (this.length == 0) {
                            this.state = State.READ_COMMON_HEADER;
                            final Object frame2 = this.spdyHeaderBlock;
                            this.spdyHeaderBlock = null;
                            return frame2;
                        }
                        this.state = State.READ_HEADER_BLOCK;
                    }
                    return null;
                }
                catch (IllegalArgumentException e3) {
                    this.state = State.FRAME_ERROR;
                    this.fireInvalidControlFrameException(ctx);
                    return null;
                }
            }
            case READ_HEADER_BLOCK: {
                final int compressedBytes = Math.min(buffer.readableBytes(), this.length);
                this.length -= compressedBytes;
                try {
                    this.decodeHeaderBlock(buffer.readSlice(compressedBytes));
                }
                catch (Exception e) {
                    this.state = State.FRAME_ERROR;
                    this.spdyHeaderBlock = null;
                    this.decompressed = null;
                    ctx.fireExceptionCaught((Throwable)e);
                    return null;
                }
                if (this.spdyHeaderBlock != null && this.spdyHeaderBlock.isInvalid()) {
                    final Object frame3 = this.spdyHeaderBlock;
                    this.spdyHeaderBlock = null;
                    this.decompressed = null;
                    if (this.length == 0) {
                        this.state = State.READ_COMMON_HEADER;
                    }
                    return frame3;
                }
                if (this.length == 0) {
                    final Object frame3 = this.spdyHeaderBlock;
                    this.spdyHeaderBlock = null;
                    this.state = State.READ_COMMON_HEADER;
                    return frame3;
                }
                return null;
            }
            case READ_DATA_FRAME: {
                if (this.streamID == 0) {
                    this.state = State.FRAME_ERROR;
                    fireProtocolException(ctx, "Received invalid data frame");
                    return null;
                }
                final int dataLength = Math.min(this.maxChunkSize, this.length);
                if (buffer.readableBytes() < dataLength) {
                    return null;
                }
                final ByteBuf data = ctx.alloc().buffer(dataLength);
                data.writeBytes(buffer, dataLength);
                final SpdyDataFrame spdyDataFrame2 = new DefaultSpdyDataFrame(this.streamID, data);
                this.length -= dataLength;
                if (this.length == 0) {
                    spdyDataFrame2.setLast((this.flags & 0x1) != 0x0);
                    this.state = State.READ_COMMON_HEADER;
                }
                return spdyDataFrame2;
            }
            case DISCARD_FRAME: {
                final int numBytes = Math.min(buffer.readableBytes(), this.length);
                buffer.skipBytes(numBytes);
                this.length -= numBytes;
                if (this.length == 0) {
                    this.state = State.READ_COMMON_HEADER;
                }
                return null;
            }
            case FRAME_ERROR: {
                buffer.skipBytes(buffer.readableBytes());
                return null;
            }
            default: {
                throw new Error("Shouldn't reach here.");
            }
        }
    }
    
    private State readCommonHeader(final ByteBuf buffer) {
        if (buffer.readableBytes() < 8) {
            return State.READ_COMMON_HEADER;
        }
        final int frameOffset = buffer.readerIndex();
        final int flagsOffset = frameOffset + 4;
        final int lengthOffset = frameOffset + 5;
        buffer.skipBytes(8);
        final boolean control = (buffer.getByte(frameOffset) & 0x80) != 0x0;
        this.flags = buffer.getByte(flagsOffset);
        this.length = SpdyCodecUtil.getUnsignedMedium(buffer, lengthOffset);
        if (!control) {
            this.streamID = SpdyCodecUtil.getUnsignedInt(buffer, frameOffset);
            return State.READ_DATA_FRAME;
        }
        this.version = (SpdyCodecUtil.getUnsignedShort(buffer, frameOffset) & 0x7FFF);
        final int typeOffset = frameOffset + 2;
        this.type = SpdyCodecUtil.getUnsignedShort(buffer, typeOffset);
        if (this.version != this.spdyVersion || !this.isValidControlFrameHeader()) {
            return State.FRAME_ERROR;
        }
        State nextState = null;
        if (this.willGenerateControlFrame()) {
            switch (this.type) {
                case 1:
                case 2:
                case 8: {
                    nextState = State.READ_HEADER_BLOCK_FRAME;
                    break;
                }
                case 4: {
                    nextState = State.READ_SETTINGS_FRAME;
                    break;
                }
                default: {
                    nextState = State.READ_CONTROL_FRAME;
                    break;
                }
            }
        }
        else if (this.length != 0) {
            nextState = State.DISCARD_FRAME;
        }
        else {
            nextState = State.READ_COMMON_HEADER;
        }
        return nextState;
    }
    
    private Object readControlFrame(final ByteBuf buffer) {
        switch (this.type) {
            case 3: {
                if (buffer.readableBytes() < 8) {
                    return null;
                }
                final int streamID = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                final int statusCode = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex() + 4);
                buffer.skipBytes(8);
                return new DefaultSpdyRstStreamFrame(streamID, statusCode);
            }
            case 6: {
                if (buffer.readableBytes() < 4) {
                    return null;
                }
                final int ID = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
                buffer.skipBytes(4);
                return new DefaultSpdyPingFrame(ID);
            }
            case 7: {
                final int minLength = (this.version < 3) ? 4 : 8;
                if (buffer.readableBytes() < minLength) {
                    return null;
                }
                final int lastGoodStreamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                buffer.skipBytes(4);
                if (this.version < 3) {
                    return new DefaultSpdyGoAwayFrame(lastGoodStreamId);
                }
                final int statusCode = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
                buffer.skipBytes(4);
                return new DefaultSpdyGoAwayFrame(lastGoodStreamId, statusCode);
            }
            case 9: {
                if (buffer.readableBytes() < 8) {
                    return null;
                }
                final int streamID = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                final int deltaWindowSize = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex() + 4);
                buffer.skipBytes(8);
                return new DefaultSpdyWindowUpdateFrame(streamID, deltaWindowSize);
            }
            default: {
                throw new Error("Shouldn't reach here.");
            }
        }
    }
    
    private SpdyHeaderBlock readHeaderBlockFrame(final ByteBuf buffer) {
        switch (this.type) {
            case 1: {
                final int minLength = (this.version < 3) ? 12 : 10;
                if (buffer.readableBytes() < minLength) {
                    return null;
                }
                final int offset = buffer.readerIndex();
                final int streamID = SpdyCodecUtil.getUnsignedInt(buffer, offset);
                final int associatedToStreamId = SpdyCodecUtil.getUnsignedInt(buffer, offset + 4);
                byte priority = (byte)(buffer.getByte(offset + 8) >> 5 & 0x7);
                if (this.version < 3) {
                    priority >>= 1;
                }
                buffer.skipBytes(10);
                this.length -= 10;
                if (this.version < 3 && this.length == 2 && buffer.getShort(buffer.readerIndex()) == 0) {
                    buffer.skipBytes(2);
                    this.length = 0;
                }
                final SpdySynStreamFrame spdySynStreamFrame = new DefaultSpdySynStreamFrame(streamID, associatedToStreamId, priority);
                spdySynStreamFrame.setLast((this.flags & 0x1) != 0x0);
                spdySynStreamFrame.setUnidirectional((this.flags & 0x2) != 0x0);
                return spdySynStreamFrame;
            }
            case 2: {
                final int minLength = (this.version < 3) ? 8 : 4;
                if (buffer.readableBytes() < minLength) {
                    return null;
                }
                final int streamID = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                buffer.skipBytes(4);
                this.length -= 4;
                if (this.version < 3) {
                    buffer.skipBytes(2);
                    this.length -= 2;
                }
                if (this.version < 3 && this.length == 2 && buffer.getShort(buffer.readerIndex()) == 0) {
                    buffer.skipBytes(2);
                    this.length = 0;
                }
                final SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamID);
                spdySynReplyFrame.setLast((this.flags & 0x1) != 0x0);
                return spdySynReplyFrame;
            }
            case 8: {
                if (buffer.readableBytes() < 4) {
                    return null;
                }
                if (this.version < 3 && this.length > 4 && buffer.readableBytes() < 8) {
                    return null;
                }
                final int streamID = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                buffer.skipBytes(4);
                this.length -= 4;
                if (this.version < 3 && this.length != 0) {
                    buffer.skipBytes(2);
                    this.length -= 2;
                }
                if (this.version < 3 && this.length == 2 && buffer.getShort(buffer.readerIndex()) == 0) {
                    buffer.skipBytes(2);
                    this.length = 0;
                }
                final SpdyHeadersFrame spdyHeadersFrame = new DefaultSpdyHeadersFrame(streamID);
                spdyHeadersFrame.setLast((this.flags & 0x1) != 0x0);
                return spdyHeadersFrame;
            }
            default: {
                throw new Error("Shouldn't reach here.");
            }
        }
    }
    
    private boolean ensureBytes(final int bytes) throws Exception {
        if (this.decompressed.readableBytes() >= bytes) {
            return true;
        }
        boolean done;
        int numBytes;
        do {
            numBytes = this.headerBlockDecompressor.decode(this.decompressed);
            done = (this.decompressed.readableBytes() >= bytes);
        } while (!done && numBytes > 0);
        return done;
    }
    
    private int readLengthField() {
        if (this.version < 3) {
            return this.decompressed.readUnsignedShort();
        }
        return this.decompressed.readInt();
    }
    
    private void decodeHeaderBlock(final ByteBuf buffer) throws Exception {
        if (this.decompressed == null) {
            this.headerSize = 0;
            this.numHeaders = -1;
            this.decompressed = Unpooled.buffer(8192);
        }
        this.headerBlockDecompressor.setInput(buffer);
        this.headerBlockDecompressor.decode(this.decompressed);
        if (this.spdyHeaderBlock == null) {
            this.decompressed = null;
            return;
        }
        final int lengthFieldSize = (this.version < 3) ? 2 : 4;
        if (this.numHeaders == -1) {
            if (this.decompressed.readableBytes() < lengthFieldSize) {
                return;
            }
            this.numHeaders = this.readLengthField();
            if (this.numHeaders < 0) {
                this.spdyHeaderBlock.setInvalid();
                return;
            }
        }
        while (this.numHeaders > 0) {
            int headerSize = this.headerSize;
            this.decompressed.markReaderIndex();
            if (!this.ensureBytes(lengthFieldSize)) {
                this.decompressed.resetReaderIndex();
                this.decompressed.discardReadBytes();
                return;
            }
            final int nameLength = this.readLengthField();
            if (nameLength <= 0) {
                this.spdyHeaderBlock.setInvalid();
                return;
            }
            headerSize += nameLength;
            if (headerSize > this.maxHeaderSize) {
                throw new TooLongFrameException("Header block exceeds " + this.maxHeaderSize);
            }
            if (!this.ensureBytes(nameLength)) {
                this.decompressed.resetReaderIndex();
                this.decompressed.discardReadBytes();
                return;
            }
            final byte[] nameBytes = new byte[nameLength];
            this.decompressed.readBytes(nameBytes);
            final String name = new String(nameBytes, "UTF-8");
            if (this.spdyHeaderBlock.headers().contains(name)) {
                this.spdyHeaderBlock.setInvalid();
                return;
            }
            if (!this.ensureBytes(lengthFieldSize)) {
                this.decompressed.resetReaderIndex();
                this.decompressed.discardReadBytes();
                return;
            }
            final int valueLength = this.readLengthField();
            if (valueLength < 0) {
                this.spdyHeaderBlock.setInvalid();
                return;
            }
            if (valueLength == 0) {
                if (this.version < 3) {
                    this.spdyHeaderBlock.setInvalid();
                    return;
                }
                this.spdyHeaderBlock.headers().add(name, "");
                --this.numHeaders;
                this.headerSize = headerSize;
            }
            else {
                headerSize += valueLength;
                if (headerSize > this.maxHeaderSize) {
                    throw new TooLongFrameException("Header block exceeds " + this.maxHeaderSize);
                }
                if (!this.ensureBytes(valueLength)) {
                    this.decompressed.resetReaderIndex();
                    this.decompressed.discardReadBytes();
                    return;
                }
                final byte[] valueBytes = new byte[valueLength];
                this.decompressed.readBytes(valueBytes);
                int index = 0;
                int offset = 0;
                while (index < valueLength) {
                    while (index < valueBytes.length && valueBytes[index] != 0) {
                        ++index;
                    }
                    if (index < valueBytes.length && valueBytes[index + 1] == 0) {
                        this.spdyHeaderBlock.setInvalid();
                        return;
                    }
                    final String value = new String(valueBytes, offset, index - offset, "UTF-8");
                    try {
                        this.spdyHeaderBlock.headers().add(name, value);
                    }
                    catch (IllegalArgumentException e) {
                        this.spdyHeaderBlock.setInvalid();
                        return;
                    }
                    offset = ++index;
                }
                --this.numHeaders;
                this.headerSize = headerSize;
            }
        }
        this.decompressed = null;
    }
    
    private boolean isValidControlFrameHeader() {
        switch (this.type) {
            case 1: {
                return (this.version < 3) ? (this.length >= 12) : (this.length >= 10);
            }
            case 2: {
                return (this.version < 3) ? (this.length >= 8) : (this.length >= 4);
            }
            case 3: {
                return this.flags == 0 && this.length == 8;
            }
            case 4: {
                return this.length >= 4;
            }
            case 5: {
                return this.length == 0;
            }
            case 6: {
                return this.length == 4;
            }
            case 7: {
                return (this.version < 3) ? (this.length == 4) : (this.length == 8);
            }
            case 8: {
                if (this.version < 3) {
                    return this.length == 4 || this.length >= 8;
                }
                return this.length >= 4;
            }
            case 9: {
                return this.length == 8;
            }
            default: {
                return true;
            }
        }
    }
    
    private boolean willGenerateControlFrame() {
        switch (this.type) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private void fireInvalidControlFrameException(final ChannelHandlerContext ctx) {
        String message = "Received invalid control frame";
        switch (this.type) {
            case 1: {
                message = "Received invalid SYN_STREAM control frame";
                break;
            }
            case 2: {
                message = "Received invalid SYN_REPLY control frame";
                break;
            }
            case 3: {
                message = "Received invalid RST_STREAM control frame";
                break;
            }
            case 4: {
                message = "Received invalid SETTINGS control frame";
                break;
            }
            case 5: {
                message = "Received invalid NOOP control frame";
                break;
            }
            case 6: {
                message = "Received invalid PING control frame";
                break;
            }
            case 7: {
                message = "Received invalid GOAWAY control frame";
                break;
            }
            case 8: {
                message = "Received invalid HEADERS control frame";
                break;
            }
            case 9: {
                message = "Received invalid WINDOW_UPDATE control frame";
                break;
            }
            case 10: {
                message = "Received invalid CREDENTIAL control frame";
                break;
            }
        }
        fireProtocolException(ctx, message);
    }
    
    private static void fireProtocolException(final ChannelHandlerContext ctx, final String message) {
        ctx.fireExceptionCaught((Throwable)new SpdyProtocolException(message));
    }
    
    private enum State
    {
        READ_COMMON_HEADER, 
        READ_CONTROL_FRAME, 
        READ_SETTINGS_FRAME, 
        READ_HEADER_BLOCK_FRAME, 
        READ_HEADER_BLOCK, 
        READ_DATA_FRAME, 
        DISCARD_FRAME, 
        FRAME_ERROR;
    }
}
