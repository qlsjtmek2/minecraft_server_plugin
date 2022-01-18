// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.HttpMessage;
import io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;

public class RtspRequestEncoder extends RtspObjectEncoder<HttpRequest>
{
    @Override
    public boolean acceptOutboundMessage(final Object msg) throws Exception {
        return msg instanceof FullHttpRequest;
    }
    
    @Override
    protected void encodeInitialLine(final ByteBuf buf, final HttpRequest request) throws Exception {
        buf.writeBytes(request.getMethod().toString().getBytes(CharsetUtil.US_ASCII));
        buf.writeByte(32);
        buf.writeBytes(request.getUri().getBytes(CharsetUtil.UTF_8));
        buf.writeByte(32);
        buf.writeBytes(request.getProtocolVersion().toString().getBytes(CharsetUtil.US_ASCII));
        buf.writeByte(13);
        buf.writeByte(10);
    }
}
