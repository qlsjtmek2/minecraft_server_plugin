// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import io.netty.util.internal.StringUtil;

public class DefaultHttpResponse extends DefaultHttpMessage implements HttpResponse
{
    private HttpResponseStatus status;
    
    public DefaultHttpResponse(final HttpVersion version, final HttpResponseStatus status) {
        super(version);
        if (status == null) {
            throw new NullPointerException("status");
        }
        this.status = status;
    }
    
    @Override
    public HttpResponseStatus getStatus() {
        return this.status;
    }
    
    @Override
    public HttpResponse setStatus(final HttpResponseStatus status) {
        this.status = status;
        return this;
    }
    
    @Override
    public HttpResponse setProtocolVersion(final HttpVersion version) {
        super.setProtocolVersion(version);
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(this.getClass().getSimpleName());
        buf.append(", decodeResult: ");
        buf.append(this.getDecoderResult());
        buf.append(')');
        buf.append(StringUtil.NEWLINE);
        buf.append(this.getProtocolVersion().text());
        buf.append(' ');
        buf.append(this.getStatus().toString());
        buf.append(StringUtil.NEWLINE);
        this.appendHeaders(buf);
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }
}
