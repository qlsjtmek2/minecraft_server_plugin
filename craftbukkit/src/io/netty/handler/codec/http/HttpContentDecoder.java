// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.BufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedByteChannel;
import io.netty.handler.codec.MessageToMessageDecoder;

public abstract class HttpContentDecoder extends MessageToMessageDecoder<HttpObject>
{
    private EmbeddedByteChannel decoder;
    private HttpMessage message;
    private boolean decodeStarted;
    private boolean continueResponse;
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final HttpObject msg) throws Exception {
        if (msg instanceof HttpResponse && ((HttpResponse)msg).getStatus().code() == 100) {
            BufUtil.retain(msg);
            if (!(msg instanceof LastHttpContent)) {
                this.continueResponse = true;
            }
            return msg;
        }
        if (this.continueResponse) {
            if (msg instanceof LastHttpContent) {
                this.continueResponse = false;
            }
            BufUtil.retain(msg);
            return msg;
        }
        if (msg instanceof HttpMessage) {
            assert this.message == null;
            this.message = (HttpMessage)msg;
            this.decodeStarted = false;
            this.cleanup();
        }
        if (!(msg instanceof HttpContent)) {
            return null;
        }
        final HttpContent c = (HttpContent)msg;
        if (!this.decodeStarted) {
            this.decodeStarted = true;
            final HttpMessage message = this.message;
            final HttpHeaders headers = message.headers();
            this.message = null;
            String contentEncoding = headers.get("Content-Encoding");
            if (contentEncoding != null) {
                contentEncoding = contentEncoding.trim();
            }
            else {
                contentEncoding = "identity";
            }
            final EmbeddedByteChannel contentDecoder = this.newContentDecoder(contentEncoding);
            this.decoder = contentDecoder;
            if (contentDecoder != null) {
                final String targetContentEncoding = this.getTargetContentEncoding(contentEncoding);
                if ("identity".equals(targetContentEncoding)) {
                    headers.remove("Content-Encoding");
                }
                else {
                    headers.set("Content-Encoding", targetContentEncoding);
                }
                final Object[] decoded = this.decodeContent(message, c);
                if (headers.contains("Content-Length")) {
                    headers.set("Content-Length", Integer.toString(((ByteBufHolder)decoded[1]).data().readableBytes()));
                }
                return decoded;
            }
            return new Object[] { message, c.retain() };
        }
        else {
            if (this.decoder != null) {
                return this.decodeContent(null, c);
            }
            return c.retain();
        }
    }
    
    private Object[] decodeContent(final HttpMessage header, final HttpContent c) {
        final ByteBuf newContent = Unpooled.buffer();
        final ByteBuf content = c.data();
        this.decode(content, newContent);
        if (c instanceof LastHttpContent) {
            final ByteBuf lastProduct = Unpooled.buffer();
            this.finishDecode(lastProduct);
            if (lastProduct.isReadable()) {
                if (header == null) {
                    return new Object[] { new DefaultHttpContent(newContent), new DefaultLastHttpContent(lastProduct) };
                }
                return new Object[] { header, new DefaultHttpContent(newContent), new DefaultLastHttpContent(lastProduct) };
            }
            else {
                if (header == null) {
                    return new Object[] { new DefaultLastHttpContent(newContent) };
                }
                return new Object[] { header, new DefaultLastHttpContent(newContent) };
            }
        }
        else {
            if (header == null) {
                return new Object[] { new DefaultHttpContent(newContent) };
            }
            return new Object[] { header, new DefaultHttpContent(newContent) };
        }
    }
    
    protected abstract EmbeddedByteChannel newContentDecoder(final String p0) throws Exception;
    
    protected String getTargetContentEncoding(final String contentEncoding) throws Exception {
        return "identity";
    }
    
    @Override
    public void afterRemove(final ChannelHandlerContext ctx) throws Exception {
        this.cleanup();
        super.afterRemove(ctx);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        this.cleanup();
        super.channelInactive(ctx);
    }
    
    private void cleanup() {
        if (this.decoder != null) {
            this.finishDecode(Unpooled.buffer());
        }
    }
    
    private void decode(final ByteBuf in, final ByteBuf out) {
        this.decoder.writeInbound(in);
        this.fetchDecoderOutput(out);
    }
    
    private void finishDecode(final ByteBuf out) {
        if (this.decoder.finish()) {
            this.fetchDecoderOutput(out);
        }
        this.decoder = null;
    }
    
    private void fetchDecoderOutput(final ByteBuf out) {
        while (true) {
            final ByteBuf buf = (ByteBuf)this.decoder.readInbound();
            if (buf == null) {
                break;
            }
            out.writeBytes(buf);
        }
    }
}
