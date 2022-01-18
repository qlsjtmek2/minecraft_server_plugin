// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import java.util.Iterator;
import java.util.Map;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public abstract class HttpObjectEncoder<H extends HttpMessage> extends MessageToByteEncoder<HttpObject>
{
    private static final int ST_INIT = 0;
    private static final int ST_CONTENT_NON_CHUNK = 1;
    private static final int ST_CONTENT_CHUNK = 2;
    private int state;
    
    public HttpObjectEncoder() {
        this.state = 0;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext ctx, final HttpObject msg, final ByteBuf out) throws Exception {
        if (msg instanceof HttpMessage) {
            if (this.state != 0) {
                throw new IllegalStateException("unexpected message type: " + msg.getClass().getSimpleName());
            }
            final H m = (H)msg;
            this.encodeInitialLine(out, m);
            encodeHeaders(out, m);
            out.writeByte(13);
            out.writeByte(10);
            this.state = (HttpHeaders.isTransferEncodingChunked(m) ? 2 : 1);
        }
        if (msg instanceof HttpContent) {
            if (this.state == 0) {
                throw new IllegalStateException("unexpected message type: " + msg.getClass().getSimpleName());
            }
            final HttpContent chunk = (HttpContent)msg;
            final ByteBuf content = chunk.data();
            final int contentLength = content.readableBytes();
            if (this.state == 1) {
                if (contentLength > 0) {
                    out.writeBytes(content, content.readerIndex(), content.readableBytes());
                }
                if (chunk instanceof LastHttpContent) {
                    this.state = 0;
                }
            }
            else {
                if (this.state != 2) {
                    throw new Error();
                }
                if (contentLength > 0) {
                    out.writeBytes(Unpooled.copiedBuffer(Integer.toHexString(contentLength), CharsetUtil.US_ASCII));
                    out.writeByte(13);
                    out.writeByte(10);
                    out.writeBytes(content, content.readerIndex(), contentLength);
                    out.writeByte(13);
                    out.writeByte(10);
                }
                if (chunk instanceof LastHttpContent) {
                    out.writeByte(48);
                    out.writeByte(13);
                    out.writeByte(10);
                    encodeTrailingHeaders(out, (LastHttpContent)chunk);
                    out.writeByte(13);
                    out.writeByte(10);
                    this.state = 0;
                }
            }
        }
    }
    
    private static void encodeHeaders(final ByteBuf buf, final HttpMessage message) {
        for (final Map.Entry<String, String> h : message.headers()) {
            encodeHeader(buf, h.getKey(), h.getValue());
        }
    }
    
    private static void encodeTrailingHeaders(final ByteBuf buf, final LastHttpContent trailer) {
        for (final Map.Entry<String, String> h : trailer.trailingHeaders()) {
            encodeHeader(buf, h.getKey(), h.getValue());
        }
    }
    
    private static void encodeHeader(final ByteBuf buf, final String header, final String value) {
        buf.writeBytes(header.getBytes(CharsetUtil.US_ASCII));
        buf.writeByte(58);
        buf.writeByte(32);
        buf.writeBytes(value.getBytes(CharsetUtil.US_ASCII));
        buf.writeByte(13);
        buf.writeByte(10);
    }
    
    protected abstract void encodeInitialLine(final ByteBuf p0, final H p1) throws Exception;
}
