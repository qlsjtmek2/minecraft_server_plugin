// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import io.netty.buffer.BufUtil;
import io.netty.channel.ChannelHandlerContext;
import java.util.LinkedList;
import java.util.Queue;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.MessageToMessageCodec;

public class SpdyHttpResponseStreamIdHandler extends MessageToMessageCodec<Object, HttpMessage>
{
    private static final Integer NO_ID;
    private final Queue<Integer> ids;
    
    public SpdyHttpResponseStreamIdHandler() {
        this.ids = new LinkedList<Integer>();
    }
    
    @Override
    public boolean acceptInboundMessage(final Object msg) throws Exception {
        return msg instanceof HttpMessage || msg instanceof SpdyRstStreamFrame;
    }
    
    @Override
    protected Object encode(final ChannelHandlerContext ctx, final HttpMessage msg) throws Exception {
        final Integer id = this.ids.poll();
        if (id != null && id != (int)SpdyHttpResponseStreamIdHandler.NO_ID && !msg.headers().contains("X-SPDY-Stream-ID")) {
            SpdyHttpHeaders.setStreamId(msg, id);
        }
        BufUtil.retain(msg);
        return msg;
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        if (msg instanceof HttpMessage) {
            final boolean contains = ((HttpMessage)msg).headers().contains("X-SPDY-Stream-ID");
            if (!contains) {
                this.ids.add(SpdyHttpResponseStreamIdHandler.NO_ID);
            }
            else {
                this.ids.add(SpdyHttpHeaders.getStreamId((HttpMessage)msg));
            }
        }
        else if (msg instanceof SpdyRstStreamFrame) {
            this.ids.remove(((SpdyRstStreamFrame)msg).getStreamId());
        }
        BufUtil.retain(msg);
        return msg;
    }
    
    static {
        NO_ID = -1;
    }
}
