// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.HttpMessage;
import io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;

public class RtspResponseEncoder extends RtspObjectEncoder<HttpResponse>
{
    @Override
    public boolean acceptOutboundMessage(final Object msg) throws Exception {
        return msg instanceof FullHttpResponse;
    }
    
    @Override
    protected void encodeInitialLine(final ByteBuf buf, final HttpResponse response) throws Exception {
        buf.writeBytes(response.getProtocolVersion().toString().getBytes(CharsetUtil.US_ASCII));
        buf.writeByte(32);
        buf.writeBytes(String.valueOf(response.getStatus().code()).getBytes(CharsetUtil.US_ASCII));
        buf.writeByte(32);
        buf.writeBytes(String.valueOf(response.getStatus().reasonPhrase()).getBytes(CharsetUtil.US_ASCII));
        buf.writeByte(13);
        buf.writeByte(10);
    }
}
