// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.BufUtil;
import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayDeque;
import io.netty.channel.embedded.EmbeddedByteChannel;
import java.util.Queue;
import io.netty.handler.codec.MessageToMessageCodec;

public abstract class HttpContentEncoder extends MessageToMessageCodec<HttpMessage, HttpObject>
{
    private final Queue<String> acceptEncodingQueue;
    private EmbeddedByteChannel encoder;
    private HttpMessage message;
    private boolean encodeStarted;
    private boolean continueResponse;
    
    public HttpContentEncoder() {
        this.acceptEncodingQueue = new ArrayDeque<String>();
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final HttpMessage msg) throws Exception {
        String acceptedEncoding = msg.headers().get("Accept-Encoding");
        if (acceptedEncoding == null) {
            acceptedEncoding = "identity";
        }
        this.acceptEncodingQueue.add(acceptedEncoding);
        BufUtil.retain(msg);
        return msg;
    }
    
    @Override
    protected Object encode(final ChannelHandlerContext ctx, final HttpObject msg) throws Exception {
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
        if (msg instanceof FullHttpMessage && !((FullHttpMessage)msg).data().isReadable()) {
            final String acceptEncoding = this.acceptEncodingQueue.poll();
            if (acceptEncoding == null) {
                throw new IllegalStateException("cannot send more responses than requests");
            }
            return ((FullHttpMessage)msg).retain();
        }
        else {
            if (msg instanceof HttpMessage) {
                assert this.message == null;
                if (msg instanceof HttpContent) {
                    if (msg instanceof HttpRequest) {
                        final HttpRequest req = (HttpRequest)msg;
                        this.message = new DefaultHttpRequest(req.getProtocolVersion(), req.getMethod(), req.getUri());
                        this.message.headers().set(req.headers());
                    }
                    else {
                        if (!(msg instanceof HttpResponse)) {
                            return msg;
                        }
                        final HttpResponse res = (HttpResponse)msg;
                        this.message = new DefaultHttpResponse(res.getProtocolVersion(), res.getStatus());
                        this.message.headers().set(res.headers());
                    }
                }
                else {
                    this.message = (HttpMessage)msg;
                }
                this.cleanup();
            }
            if (!(msg instanceof HttpContent)) {
                return null;
            }
            final HttpContent c = (HttpContent)msg;
            if (!this.encodeStarted) {
                this.encodeStarted = true;
                final HttpMessage message = this.message;
                final HttpHeaders headers = message.headers();
                this.message = null;
                final String acceptEncoding2 = this.acceptEncodingQueue.poll();
                if (acceptEncoding2 == null) {
                    throw new IllegalStateException("cannot send more responses than requests");
                }
                final Result result = this.beginEncode(message, c, acceptEncoding2);
                if (result != null) {
                    this.encoder = result.contentEncoder();
                    headers.set("Content-Encoding", result.targetContentEncoding());
                    final Object[] encoded = this.encodeContent(message, c);
                    if (!HttpHeaders.isTransferEncodingChunked(message) && encoded.length == 3 && headers.contains("Content-Length")) {
                        final long length = ((ByteBufHolder)encoded[1]).data().readableBytes() + ((ByteBufHolder)encoded[2]).data().readableBytes();
                        headers.set("Content-Length", Long.toString(length));
                    }
                    return encoded;
                }
                if (c instanceof LastHttpContent) {
                    return new Object[] { message, new DefaultLastHttpContent(c.data().retain()) };
                }
                return new Object[] { message, new DefaultHttpContent(c.data().retain()) };
            }
            else {
                if (this.encoder != null) {
                    return this.encodeContent(null, c);
                }
                return c.retain();
            }
        }
    }
    
    private Object[] encodeContent(final HttpMessage header, final HttpContent c) {
        final ByteBuf newContent = Unpooled.buffer();
        final ByteBuf content = c.data();
        this.encode(content, newContent);
        if (c instanceof LastHttpContent) {
            final ByteBuf lastProduct = Unpooled.buffer();
            this.finishEncode(lastProduct);
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
    
    protected abstract Result beginEncode(final HttpMessage p0, final HttpContent p1, final String p2) throws Exception;
    
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
        if (this.encoder != null) {
            this.finishEncode(Unpooled.buffer());
        }
    }
    
    private void encode(final ByteBuf in, final ByteBuf out) {
        this.encoder.writeOutbound(in);
        this.fetchEncoderOutput(out);
    }
    
    private void finishEncode(final ByteBuf out) {
        if (this.encoder.finish()) {
            this.fetchEncoderOutput(out);
        }
        this.encodeStarted = false;
        this.encoder = null;
    }
    
    private void fetchEncoderOutput(final ByteBuf out) {
        while (true) {
            final ByteBuf buf = this.encoder.readOutbound();
            if (buf == null) {
                break;
            }
            out.writeBytes(buf);
        }
    }
    
    public static final class Result
    {
        private final String targetContentEncoding;
        private final EmbeddedByteChannel contentEncoder;
        
        public Result(final String targetContentEncoding, final EmbeddedByteChannel contentEncoder) {
            if (targetContentEncoding == null) {
                throw new NullPointerException("targetContentEncoding");
            }
            if (contentEncoder == null) {
                throw new NullPointerException("contentEncoder");
            }
            this.targetContentEncoding = targetContentEncoding;
            this.contentEncoder = contentEncoder;
        }
        
        public String targetContentEncoding() {
            return this.targetContentEncoding;
        }
        
        public EmbeddedByteChannel contentEncoder() {
            return this.contentEncoder;
        }
    }
}
