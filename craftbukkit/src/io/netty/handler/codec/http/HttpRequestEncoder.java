// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;

public class HttpRequestEncoder extends HttpObjectEncoder<HttpRequest>
{
    private static final char SLASH = '/';
    
    @Override
    public boolean acceptOutboundMessage(final Object msg) throws Exception {
        return super.acceptOutboundMessage(msg) && !(msg instanceof HttpResponse);
    }
    
    @Override
    protected void encodeInitialLine(final ByteBuf buf, final HttpRequest request) throws Exception {
        buf.writeBytes(request.getMethod().toString().getBytes(CharsetUtil.US_ASCII));
        buf.writeByte(32);
        String uri = request.getUri();
        final int start = uri.indexOf("://");
        if (start != -1) {
            final int startIndex = start + 3;
            if (uri.lastIndexOf(47) <= startIndex) {
                uri += '/';
            }
        }
        buf.writeBytes(uri.getBytes("UTF-8"));
        buf.writeByte(32);
        buf.writeBytes(request.getProtocolVersion().toString().getBytes(CharsetUtil.US_ASCII));
        buf.writeByte(13);
        buf.writeByte(10);
    }
}
