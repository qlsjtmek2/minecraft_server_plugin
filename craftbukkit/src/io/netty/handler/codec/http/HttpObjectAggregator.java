// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import io.netty.util.CharsetUtil;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.BufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToMessageDecoder;

public class HttpObjectAggregator extends MessageToMessageDecoder<HttpObject>
{
    public static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;
    private static final ByteBuf CONTINUE;
    private final int maxContentLength;
    private FullHttpMessage currentMessage;
    private int maxCumulationBufferComponents;
    private ChannelHandlerContext ctx;
    
    public HttpObjectAggregator(final int maxContentLength) {
        this.maxCumulationBufferComponents = 1024;
        if (maxContentLength <= 0) {
            throw new IllegalArgumentException("maxContentLength must be a positive integer: " + maxContentLength);
        }
        this.maxContentLength = maxContentLength;
    }
    
    public final int getMaxCumulationBufferComponents() {
        return this.maxCumulationBufferComponents;
    }
    
    public final void setMaxCumulationBufferComponents(final int maxCumulationBufferComponents) {
        if (maxCumulationBufferComponents < 2) {
            throw new IllegalArgumentException("maxCumulationBufferComponents: " + maxCumulationBufferComponents + " (expected: >= 2)");
        }
        if (this.ctx == null) {
            this.maxCumulationBufferComponents = maxCumulationBufferComponents;
            return;
        }
        throw new IllegalStateException("decoder properties cannot be changed once the decoder is added to a pipeline.");
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final HttpObject msg) throws Exception {
        FullHttpMessage currentMessage = this.currentMessage;
        if (msg instanceof HttpMessage) {
            assert currentMessage == null;
            final HttpMessage m = (HttpMessage)msg;
            if (HttpHeaders.is100ContinueExpected(m)) {
                ctx.write(HttpObjectAggregator.CONTINUE.duplicate());
            }
            if (!m.getDecoderResult().isSuccess()) {
                HttpHeaders.removeTransferEncodingChunked(m);
                this.currentMessage = null;
                BufUtil.retain(m);
                return m;
            }
            if (msg instanceof HttpRequest) {
                final HttpRequest header = (HttpRequest)msg;
                currentMessage = (this.currentMessage = new DefaultFullHttpRequest(header.getProtocolVersion(), header.getMethod(), header.getUri(), Unpooled.compositeBuffer(this.maxCumulationBufferComponents)));
            }
            else {
                if (!(msg instanceof HttpResponse)) {
                    throw new Error();
                }
                final HttpResponse header2 = (HttpResponse)msg;
                currentMessage = (this.currentMessage = new DefaultFullHttpResponse(header2.getProtocolVersion(), header2.getStatus(), Unpooled.compositeBuffer(this.maxCumulationBufferComponents)));
            }
            currentMessage.headers().set(m.headers());
            HttpHeaders.removeTransferEncodingChunked(currentMessage);
            return null;
        }
        else {
            if (!(msg instanceof HttpContent)) {
                throw new Error();
            }
            assert currentMessage != null;
            final HttpContent chunk = (HttpContent)msg;
            final CompositeByteBuf content = (CompositeByteBuf)currentMessage.data();
            if (content.readableBytes() > this.maxContentLength - chunk.data().readableBytes()) {
                throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes.");
            }
            if (chunk.data().isReadable()) {
                chunk.retain();
                content.addComponent(chunk.data());
                content.writerIndex(content.writerIndex() + chunk.data().readableBytes());
            }
            boolean last;
            if (!chunk.getDecoderResult().isSuccess()) {
                currentMessage.setDecoderResult(DecoderResult.failure(chunk.getDecoderResult().cause()));
                last = true;
            }
            else {
                last = (chunk instanceof LastHttpContent);
            }
            if (last) {
                this.currentMessage = null;
                if (chunk instanceof LastHttpContent) {
                    final LastHttpContent trailer = (LastHttpContent)chunk;
                    currentMessage.headers().add(trailer.trailingHeaders());
                }
                currentMessage.headers().set("Content-Length", String.valueOf(content.readableBytes()));
                return currentMessage;
            }
            return null;
        }
    }
    
    @Override
    public void beforeAdd(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
    
    static {
        CONTINUE = Unpooled.copiedBuffer("HTTP/1.1 100 Continue\r\n\r\n", CharsetUtil.US_ASCII);
    }
}
