// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import io.netty.handler.codec.PrematureChannelClosureException;
import io.netty.buffer.Buf;
import io.netty.buffer.MessageBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOperationHandler;
import io.netty.channel.ChannelStateHandler;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Queue;
import io.netty.channel.ChannelOutboundMessageHandler;
import io.netty.channel.ChannelInboundByteHandler;
import io.netty.channel.CombinedChannelDuplexHandler;

public final class HttpClientCodec extends CombinedChannelDuplexHandler implements ChannelInboundByteHandler, ChannelOutboundMessageHandler<HttpObject>
{
    private final Queue<HttpMethod> queue;
    private volatile boolean done;
    private final AtomicLong requestResponseCounter;
    private final boolean failOnMissingResponse;
    
    public HttpClientCodec() {
        this(4096, 8192, 8192, false);
    }
    
    public void setSingleDecode(final boolean singleDecode) {
        this.decoder().setSingleDecode(singleDecode);
    }
    
    public boolean isSingleDecode() {
        return this.decoder().isSingleDecode();
    }
    
    public HttpClientCodec(final int maxInitialLineLength, final int maxHeaderSize, final int maxChunkSize) {
        this(maxInitialLineLength, maxHeaderSize, maxChunkSize, false);
    }
    
    public HttpClientCodec(final int maxInitialLineLength, final int maxHeaderSize, final int maxChunkSize, final boolean failOnMissingResponse) {
        this.queue = new ArrayDeque<HttpMethod>();
        this.requestResponseCounter = new AtomicLong();
        this.init(new Decoder(maxInitialLineLength, maxHeaderSize, maxChunkSize), new Encoder());
        this.failOnMissingResponse = failOnMissingResponse;
    }
    
    private Decoder decoder() {
        return (Decoder)this.stateHandler();
    }
    
    private Encoder encoder() {
        return (Encoder)this.operationHandler();
    }
    
    @Override
    public ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return this.decoder().newInboundBuffer(ctx);
    }
    
    @Override
    public void discardInboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        this.decoder().discardInboundReadBytes(ctx);
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.decoder().freeInboundBuffer(ctx);
    }
    
    @Override
    public MessageBuf<HttpObject> newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return this.encoder().newOutboundBuffer(ctx);
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        this.encoder().freeOutboundBuffer(ctx);
    }
    
    private final class Encoder extends HttpRequestEncoder
    {
        @Override
        protected void encode(final ChannelHandlerContext ctx, final HttpObject msg, final ByteBuf out) throws Exception {
            if (msg instanceof HttpRequest && !HttpClientCodec.this.done) {
                HttpClientCodec.this.queue.offer(((HttpRequest)msg).getMethod());
            }
            super.encode(ctx, msg, out);
            if (HttpClientCodec.this.failOnMissingResponse && msg instanceof LastHttpContent) {
                HttpClientCodec.this.requestResponseCounter.incrementAndGet();
            }
        }
    }
    
    private final class Decoder extends HttpResponseDecoder
    {
        Decoder(final int maxInitialLineLength, final int maxHeaderSize, final int maxChunkSize) {
            super(maxInitialLineLength, maxHeaderSize, maxChunkSize);
        }
        
        @Override
        protected Object decode(final ChannelHandlerContext ctx, final ByteBuf buffer) throws Exception {
            if (!HttpClientCodec.this.done) {
                final Object msg = super.decode(ctx, buffer);
                if (HttpClientCodec.this.failOnMissingResponse) {
                    this.decrement(msg);
                }
                return msg;
            }
            final int readable = this.actualReadableBytes();
            if (readable == 0) {
                return null;
            }
            return buffer.readBytes(readable);
        }
        
        private void decrement(final Object msg) {
            if (msg == null) {
                return;
            }
            if (msg instanceof LastHttpContent) {
                HttpClientCodec.this.requestResponseCounter.decrementAndGet();
            }
            else if (msg instanceof Object[]) {
                final Object[] arr$;
                final Object[] objects = arr$ = (Object[])msg;
                for (final Object obj : arr$) {
                    this.decrement(obj);
                }
            }
        }
        
        @Override
        protected boolean isContentAlwaysEmpty(final HttpMessage msg) {
            final int statusCode = ((HttpResponse)msg).getStatus().code();
            if (statusCode == 100) {
                return true;
            }
            final HttpMethod method = HttpClientCodec.this.queue.poll();
            final char firstChar = method.name().charAt(0);
            switch (firstChar) {
                case 'H': {
                    if (HttpMethod.HEAD.equals(method)) {
                        return true;
                    }
                    break;
                }
                case 'C': {
                    if (statusCode == 200 && HttpMethod.CONNECT.equals(method)) {
                        HttpClientCodec.this.done = true;
                        HttpClientCodec.this.queue.clear();
                        return true;
                    }
                    break;
                }
            }
            return super.isContentAlwaysEmpty(msg);
        }
        
        @Override
        public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            if (HttpClientCodec.this.failOnMissingResponse) {
                final long missingResponses = HttpClientCodec.this.requestResponseCounter.get();
                if (missingResponses > 0L) {
                    ctx.fireExceptionCaught((Throwable)new PrematureChannelClosureException("channel gone inactive with " + missingResponses + " missing response(s)"));
                }
            }
        }
    }
}
