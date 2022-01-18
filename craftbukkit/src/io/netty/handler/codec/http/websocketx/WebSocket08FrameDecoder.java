// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.handler.codec.ReplayingDecoder;

public class WebSocket08FrameDecoder extends ReplayingDecoder<State>
{
    private static final InternalLogger logger;
    private static final byte OPCODE_CONT = 0;
    private static final byte OPCODE_TEXT = 1;
    private static final byte OPCODE_BINARY = 2;
    private static final byte OPCODE_CLOSE = 8;
    private static final byte OPCODE_PING = 9;
    private static final byte OPCODE_PONG = 10;
    private UTF8Output fragmentedFramesText;
    private int fragmentedFramesCount;
    private final long maxFramePayloadLength;
    private boolean frameFinalFlag;
    private int frameRsv;
    private int frameOpcode;
    private long framePayloadLength;
    private ByteBuf framePayload;
    private int framePayloadBytesRead;
    private ByteBuf maskingKey;
    private final boolean allowExtensions;
    private final boolean maskedPayload;
    private boolean receivedClosingHandshake;
    
    public WebSocket08FrameDecoder(final boolean maskedPayload, final boolean allowExtensions, final int maxFramePayloadLength) {
        super(State.FRAME_START);
        this.maskedPayload = maskedPayload;
        this.allowExtensions = allowExtensions;
        this.maxFramePayloadLength = maxFramePayloadLength;
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        if (this.receivedClosingHandshake) {
            in.skipBytes(this.actualReadableBytes());
            return null;
        }
        switch (this.state()) {
            case FRAME_START: {
                this.framePayloadBytesRead = 0;
                this.framePayloadLength = -1L;
                this.framePayload = null;
                byte b = in.readByte();
                this.frameFinalFlag = ((b & 0x80) != 0x0);
                this.frameRsv = (b & 0x70) >> 4;
                this.frameOpcode = (b & 0xF);
                if (WebSocket08FrameDecoder.logger.isDebugEnabled()) {
                    WebSocket08FrameDecoder.logger.debug("Decoding WebSocket Frame opCode={}", (Object)this.frameOpcode);
                }
                b = in.readByte();
                final boolean frameMasked = (b & 0x80) != 0x0;
                final int framePayloadLen1 = b & 0x7F;
                if (this.frameRsv != 0 && !this.allowExtensions) {
                    this.protocolViolation(ctx, "RSV != 0 and no extension negotiated, RSV:" + this.frameRsv);
                    return null;
                }
                if (this.maskedPayload && !frameMasked) {
                    this.protocolViolation(ctx, "unmasked client to server frame");
                    return null;
                }
                if (this.frameOpcode > 7) {
                    if (!this.frameFinalFlag) {
                        this.protocolViolation(ctx, "fragmented control frame");
                        return null;
                    }
                    if (framePayloadLen1 > 125) {
                        this.protocolViolation(ctx, "control frame with payload length > 125 octets");
                        return null;
                    }
                    if (this.frameOpcode != 8 && this.frameOpcode != 9 && this.frameOpcode != 10) {
                        this.protocolViolation(ctx, "control frame using reserved opcode " + this.frameOpcode);
                        return null;
                    }
                    if (this.frameOpcode == 8 && framePayloadLen1 == 1) {
                        this.protocolViolation(ctx, "received close control frame with payload len 1");
                        return null;
                    }
                }
                else {
                    if (this.frameOpcode != 0 && this.frameOpcode != 1 && this.frameOpcode != 2) {
                        this.protocolViolation(ctx, "data frame using reserved opcode " + this.frameOpcode);
                        return null;
                    }
                    if (this.fragmentedFramesCount == 0 && this.frameOpcode == 0) {
                        this.protocolViolation(ctx, "received continuation data frame outside fragmented message");
                        return null;
                    }
                    if (this.fragmentedFramesCount != 0 && this.frameOpcode != 0 && this.frameOpcode != 9) {
                        this.protocolViolation(ctx, "received non-continuation data frame while inside fragmented message");
                        return null;
                    }
                }
                if (framePayloadLen1 == 126) {
                    this.framePayloadLength = in.readUnsignedShort();
                    if (this.framePayloadLength < 126L) {
                        this.protocolViolation(ctx, "invalid data frame length (not using minimal length encoding)");
                        return null;
                    }
                }
                else if (framePayloadLen1 == 127) {
                    this.framePayloadLength = in.readLong();
                    if (this.framePayloadLength < 65536L) {
                        this.protocolViolation(ctx, "invalid data frame length (not using minimal length encoding)");
                        return null;
                    }
                }
                else {
                    this.framePayloadLength = framePayloadLen1;
                }
                if (this.framePayloadLength > this.maxFramePayloadLength) {
                    this.protocolViolation(ctx, "Max frame length of " + this.maxFramePayloadLength + " has been exceeded.");
                    return null;
                }
                if (WebSocket08FrameDecoder.logger.isDebugEnabled()) {
                    WebSocket08FrameDecoder.logger.debug("Decoding WebSocket Frame length={}", (Object)this.framePayloadLength);
                }
                this.checkpoint(State.MASKING_KEY);
            }
            case MASKING_KEY: {
                if (this.maskedPayload) {
                    this.maskingKey = in.readBytes(4);
                }
                this.checkpoint(State.PAYLOAD);
            }
            case PAYLOAD: {
                final int rbytes = this.actualReadableBytes();
                ByteBuf payloadBuffer = null;
                final long willHaveReadByteCount = this.framePayloadBytesRead + rbytes;
                if (willHaveReadByteCount == this.framePayloadLength) {
                    payloadBuffer = ctx.alloc().buffer(rbytes);
                    payloadBuffer.writeBytes(in, rbytes);
                }
                else {
                    if (willHaveReadByteCount < this.framePayloadLength) {
                        if (this.framePayload == null) {
                            this.framePayload = ctx.alloc().buffer(toFrameLength(this.framePayloadLength));
                        }
                        this.framePayload.writeBytes(in, rbytes);
                        this.framePayloadBytesRead += rbytes;
                        return null;
                    }
                    if (willHaveReadByteCount > this.framePayloadLength) {
                        if (this.framePayload == null) {
                            this.framePayload = ctx.alloc().buffer(toFrameLength(this.framePayloadLength));
                        }
                        this.framePayload.writeBytes(in, toFrameLength(this.framePayloadLength - this.framePayloadBytesRead));
                    }
                }
                this.checkpoint(State.FRAME_START);
                if (this.framePayload == null) {
                    this.framePayload = payloadBuffer;
                }
                else if (payloadBuffer != null) {
                    this.framePayload.writeBytes(payloadBuffer);
                }
                if (this.maskedPayload) {
                    this.unmask(this.framePayload);
                }
                if (this.frameOpcode == 9) {
                    return new PingWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload);
                }
                if (this.frameOpcode == 10) {
                    return new PongWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload);
                }
                if (this.frameOpcode == 8) {
                    this.checkCloseFrameBody(ctx, this.framePayload);
                    this.receivedClosingHandshake = true;
                    return new CloseWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload);
                }
                String aggregatedText = null;
                if (this.frameFinalFlag) {
                    if (this.frameOpcode != 9) {
                        this.fragmentedFramesCount = 0;
                        if (this.frameOpcode == 1 || this.fragmentedFramesText != null) {
                            this.checkUTF8String(ctx, this.framePayload);
                            aggregatedText = this.fragmentedFramesText.toString();
                            this.fragmentedFramesText = null;
                        }
                    }
                }
                else {
                    if (this.fragmentedFramesCount == 0) {
                        this.fragmentedFramesText = null;
                        if (this.frameOpcode == 1) {
                            this.checkUTF8String(ctx, this.framePayload);
                        }
                    }
                    else if (this.fragmentedFramesText != null) {
                        this.checkUTF8String(ctx, this.framePayload);
                    }
                    ++this.fragmentedFramesCount;
                }
                if (this.frameOpcode == 1) {
                    return new TextWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload);
                }
                if (this.frameOpcode == 2) {
                    return new BinaryWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload);
                }
                if (this.frameOpcode == 0) {
                    return new ContinuationWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload, aggregatedText);
                }
                throw new UnsupportedOperationException("Cannot decode web socket frame with opcode: " + this.frameOpcode);
            }
            case CORRUPT: {
                in.readByte();
                return null;
            }
            default: {
                throw new Error("Shouldn't reach here.");
            }
        }
    }
    
    private void unmask(final ByteBuf frame) {
        for (int i = frame.readerIndex(); i < frame.writerIndex(); ++i) {
            frame.setByte(i, frame.getByte(i) ^ this.maskingKey.getByte(i % 4));
        }
    }
    
    private void protocolViolation(final ChannelHandlerContext ctx, final String reason) {
        this.checkpoint(State.CORRUPT);
        if (ctx.channel().isActive()) {
            ctx.flush().addListener((GenericFutureListener<? extends Future<Void>>)ChannelFutureListener.CLOSE);
        }
        throw new CorruptedFrameException(reason);
    }
    
    private static int toFrameLength(final long l) {
        if (l > 2147483647L) {
            throw new TooLongFrameException("Length:" + l);
        }
        return (int)l;
    }
    
    private void checkUTF8String(final ChannelHandlerContext ctx, final ByteBuf buffer) {
        try {
            if (this.fragmentedFramesText == null) {
                this.fragmentedFramesText = new UTF8Output(buffer);
            }
            else {
                this.fragmentedFramesText.write(buffer);
            }
        }
        catch (UTF8Exception ex) {
            this.protocolViolation(ctx, "invalid UTF-8 bytes");
        }
    }
    
    protected void checkCloseFrameBody(final ChannelHandlerContext ctx, final ByteBuf buffer) {
        if (buffer == null || buffer.capacity() == 0) {
            return;
        }
        if (buffer.capacity() == 1) {
            this.protocolViolation(ctx, "Invalid close frame body");
        }
        final int idx = buffer.readerIndex();
        buffer.readerIndex(0);
        final int statusCode = buffer.readShort();
        if ((statusCode >= 0 && statusCode <= 999) || (statusCode >= 1004 && statusCode <= 1006) || (statusCode >= 1012 && statusCode <= 2999)) {
            this.protocolViolation(ctx, "Invalid close frame getStatus code: " + statusCode);
        }
        if (buffer.isReadable()) {
            try {
                new UTF8Output(buffer);
            }
            catch (UTF8Exception ex) {
                this.protocolViolation(ctx, "Invalid close frame reason text. Invalid UTF-8 bytes");
            }
        }
        buffer.readerIndex(idx);
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(WebSocket08FrameDecoder.class);
    }
    
    enum State
    {
        FRAME_START, 
        MASKING_KEY, 
        PAYLOAD, 
        CORRUPT;
    }
}
