// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import io.netty.util.internal.StringUtil;

public abstract class DefaultHttpMessage extends DefaultHttpObject implements HttpMessage
{
    private HttpVersion version;
    private final HttpHeaders headers;
    
    protected DefaultHttpMessage(final HttpVersion version) {
        this.headers = new DefaultHttpHeaders();
        if (version == null) {
            throw new NullPointerException("version");
        }
        this.version = version;
    }
    
    @Override
    public HttpHeaders headers() {
        return this.headers;
    }
    
    @Override
    public HttpVersion getProtocolVersion() {
        return this.version;
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(this.getClass().getSimpleName());
        buf.append("(version: ");
        buf.append(this.getProtocolVersion().text());
        buf.append(", keepAlive: ");
        buf.append(HttpHeaders.isKeepAlive(this));
        buf.append(')');
        buf.append(StringUtil.NEWLINE);
        this.appendHeaders(buf);
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }
    
    @Override
    public HttpMessage setProtocolVersion(final HttpVersion version) {
        this.version = version;
        return this;
    }
    
    void appendHeaders(final StringBuilder buf) {
        for (final Map.Entry<String, String> e : this.headers()) {
            buf.append(e.getKey());
            buf.append(": ");
            buf.append(e.getValue());
            buf.append(StringUtil.NEWLINE);
        }
    }
}
