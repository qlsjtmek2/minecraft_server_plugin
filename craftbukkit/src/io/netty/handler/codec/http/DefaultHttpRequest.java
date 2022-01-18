// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import io.netty.util.internal.StringUtil;

public class DefaultHttpRequest extends DefaultHttpMessage implements HttpRequest
{
    private HttpMethod method;
    private String uri;
    
    public DefaultHttpRequest(final HttpVersion httpVersion, final HttpMethod method, final String uri) {
        super(httpVersion);
        if (method == null) {
            throw new NullPointerException("getMethod");
        }
        if (uri == null) {
            throw new NullPointerException("getUri");
        }
        this.method = method;
        this.uri = uri;
    }
    
    @Override
    public HttpMethod getMethod() {
        return this.method;
    }
    
    @Override
    public String getUri() {
        return this.uri;
    }
    
    @Override
    public HttpRequest setMethod(final HttpMethod method) {
        this.method = method;
        return this;
    }
    
    @Override
    public HttpRequest setUri(final String uri) {
        this.uri = uri;
        return this;
    }
    
    @Override
    public HttpRequest setProtocolVersion(final HttpVersion version) {
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
        buf.append(this.getMethod().toString());
        buf.append(' ');
        buf.append(this.getUri());
        buf.append(' ');
        buf.append(this.getProtocolVersion().text());
        buf.append(StringUtil.NEWLINE);
        this.appendHeaders(buf);
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }
}
