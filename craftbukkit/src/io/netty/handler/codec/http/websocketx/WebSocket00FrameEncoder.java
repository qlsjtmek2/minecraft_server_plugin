// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class WebSocket00FrameEncoder extends MessageToByteEncoder<WebSocketFrame>
{
    @Override
    protected void encode(final ChannelHandlerContext ctx, final WebSocketFrame msg, final ByteBuf out) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            final ByteBuf data = msg.data();
            out.writeByte(0);
            out.writeBytes(data, data.readerIndex(), data.readableBytes());
            out.writeByte(-1);
        }
        else if (msg instanceof CloseWebSocketFrame) {
            out.writeByte(-1);
            out.writeByte(0);
        }
        else {
            final ByteBuf data = msg.data();
            final int dataLen = data.readableBytes();
            out.ensureWritable(dataLen + 5);
            out.writeByte(-128);
            final int b1 = dataLen >>> 28 & 0x7F;
            final int b2 = dataLen >>> 14 & 0x7F;
            final int b3 = dataLen >>> 7 & 0x7F;
            final int b4 = dataLen & 0x7F;
            if (b1 == 0) {
                if (b2 == 0) {
                    if (b3 == 0) {
                        out.writeByte(b4);
                    }
                    else {
                        out.writeByte(b3 | 0x80);
                        out.writeByte(b4);
                    }
                }
                else {
                    out.writeByte(b2 | 0x80);
                    out.writeByte(b3 | 0x80);
                    out.writeByte(b4);
                }
            }
            else {
                out.writeByte(b1 | 0x80);
                out.writeByte(b2 | 0x80);
                out.writeByte(b3 | 0x80);
                out.writeByte(b4);
            }
            out.writeBytes(data, data.readerIndex(), dataLen);
        }
    }
}
