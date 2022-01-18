// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.logging;

import io.netty.buffer.Buf;
import io.netty.channel.ChannelPromise;
import io.netty.buffer.Unpooled;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundMessageHandler;
import io.netty.channel.ChannelInboundMessageHandler;

public class MessageLoggingHandler extends LoggingHandler implements ChannelInboundMessageHandler<Object>, ChannelOutboundMessageHandler<Object>
{
    public MessageLoggingHandler() {
    }
    
    public MessageLoggingHandler(final Class<?> clazz, final LogLevel level) {
        super(clazz, level);
    }
    
    public MessageLoggingHandler(final Class<?> clazz) {
        super(clazz);
    }
    
    public MessageLoggingHandler(final LogLevel level) {
        super(level);
    }
    
    public MessageLoggingHandler(final String name, final LogLevel level) {
        super(name, level);
    }
    
    public MessageLoggingHandler(final String name) {
        super(name);
    }
    
    @Override
    public MessageBuf<Object> newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return Unpooled.messageBuffer();
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
    }
    
    @Override
    public MessageBuf<Object> newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return Unpooled.messageBuffer();
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        final MessageBuf<Object> buf = ctx.inboundMessageBuffer();
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(ctx, this.formatBuffer("RECEIVED", buf)));
        }
        final MessageBuf<Object> out = ctx.nextInboundMessageBuffer();
        while (true) {
            final Object o = buf.poll();
            if (o == null) {
                break;
            }
            out.add(o);
        }
        ctx.fireInboundBufferUpdated();
    }
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        final MessageBuf<Object> buf = ctx.outboundMessageBuffer();
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, this.format(ctx, this.formatBuffer("WRITE", buf)));
        }
        final MessageBuf<Object> out = ctx.nextOutboundMessageBuffer();
        while (true) {
            final Object o = buf.poll();
            if (o == null) {
                break;
            }
            out.add(o);
        }
        ctx.flush(promise);
    }
    
    protected String formatBuffer(final String message, final MessageBuf<Object> buf) {
        return message + '(' + buf.size() + "): " + buf;
    }
}
