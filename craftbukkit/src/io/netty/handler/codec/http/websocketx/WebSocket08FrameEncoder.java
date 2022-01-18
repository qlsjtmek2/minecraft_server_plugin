// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.buffer.Unpooled;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.handler.codec.MessageToByteEncoder;

public class WebSocket08FrameEncoder extends MessageToByteEncoder<WebSocketFrame>
{
    private static final InternalLogger logger;
    private static final byte OPCODE_CONT = 0;
    private static final byte OPCODE_TEXT = 1;
    private static final byte OPCODE_BINARY = 2;
    private static final byte OPCODE_CLOSE = 8;
    private static final byte OPCODE_PING = 9;
    private static final byte OPCODE_PONG = 10;
    private final boolean maskPayload;
    
    public WebSocket08FrameEncoder(final boolean maskPayload) {
        this.maskPayload = maskPayload;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext ctx, final WebSocketFrame msg, final ByteBuf out) throws Exception {
        ByteBuf data = msg.data();
        if (data == null) {
            data = Unpooled.EMPTY_BUFFER;
        }
        byte opcode;
        if (msg instanceof TextWebSocketFrame) {
            opcode = 1;
        }
        else if (msg instanceof PingWebSocketFrame) {
            opcode = 9;
        }
        else if (msg instanceof PongWebSocketFrame) {
            opcode = 10;
        }
        else if (msg instanceof CloseWebSocketFrame) {
            opcode = 8;
        }
        else if (msg instanceof BinaryWebSocketFrame) {
            opcode = 2;
        }
        else {
            if (!(msg instanceof ContinuationWebSocketFrame)) {
                throw new UnsupportedOperationException("Cannot encode frame of type: " + msg.getClass().getName());
            }
            opcode = 0;
        }
        final int length = data.readableBytes();
        if (WebSocket08FrameEncoder.logger.isDebugEnabled()) {
            WebSocket08FrameEncoder.logger.debug("Encoding WebSocket Frame opCode=" + opcode + " length=" + length);
        }
        int b0 = 0;
        if (msg.isFinalFragment()) {
            b0 |= 0x80;
        }
        b0 |= msg.rsv() % 8 << 4;
        b0 |= opcode % 128;
        if (opcode == 9 && length > 125) {
            throw new TooLongFrameException("invalid payload for PING (payload length must be <= 125, was " + length);
        }
        final int maskLength = this.maskPayload ? 4 : 0;
        if (length <= 125) {
            out.ensureWritable(2 + maskLength + length);
            out.writeByte(b0);
            final byte b2 = (byte)(this.maskPayload ? (0x80 | (byte)length) : ((byte)length));
            out.writeByte(b2);
        }
        else if (length <= 65535) {
            out.ensureWritable(4 + maskLength + length);
            out.writeByte(b0);
            out.writeByte(this.maskPayload ? 254 : 126);
            out.writeByte(length >>> 8 & 0xFF);
            out.writeByte(length & 0xFF);
        }
        else {
            out.ensureWritable(10 + maskLength + length);
            out.writeByte(b0);
            out.writeByte(this.maskPayload ? 255 : 127);
            out.writeLong(length);
        }
        if (this.maskPayload) {
            final int random = (int)(Math.random() * 2.147483647E9);
            final byte[] mask = ByteBuffer.allocate(4).putInt(random).array();
            out.writeBytes(mask);
            int counter = 0;
            for (int i = data.readerIndex(); i < data.writerIndex(); ++i) {
                final byte byteData = data.getByte(i);
                out.writeByte(byteData ^ mask[counter++ % 4]);
            }
        }
        else {
            out.writeBytes(data, data.readerIndex(), data.readableBytes());
        }
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(WebSocket08FrameEncoder.class);
    }
}
