// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import io.netty.buffer.ReferenceCounted;
import io.netty.buffer.ByteBufHolder;
import java.util.Iterator;
import java.util.Map;
import io.netty.util.internal.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DefaultLastHttpContent extends DefaultHttpContent implements LastHttpContent
{
    private final HttpHeaders trailingHeaders;
    
    public DefaultLastHttpContent() {
        this(Unpooled.buffer(0));
    }
    
    public DefaultLastHttpContent(final ByteBuf content) {
        super(content);
        this.trailingHeaders = new DefaultHttpHeaders() {
            @Override
            void validateHeaderName0(final String name) {
                super.validateHeaderName0(name);
                if (name.equalsIgnoreCase("Content-Length") || name.equalsIgnoreCase("Transfer-Encoding") || name.equalsIgnoreCase("Trailer")) {
                    throw new IllegalArgumentException("prohibited trailing header: " + name);
                }
            }
        };
    }
    
    @Override
    public LastHttpContent copy() {
        final DefaultLastHttpContent copy = new DefaultLastHttpContent(this.data().copy());
        copy.trailingHeaders().set(this.trailingHeaders());
        return copy;
    }
    
    @Override
    public LastHttpContent retain(final int increment) {
        super.retain(increment);
        return this;
    }
    
    @Override
    public LastHttpContent retain() {
        super.retain();
        return this;
    }
    
    @Override
    public HttpHeaders trailingHeaders() {
        return this.trailingHeaders;
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder(super.toString());
        buf.append(StringUtil.NEWLINE);
        this.appendHeaders(buf);
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }
    
    private void appendHeaders(final StringBuilder buf) {
        for (final Map.Entry<String, String> e : this.trailingHeaders()) {
            buf.append(e.getKey());
            buf.append(": ");
            buf.append(e.getValue());
            buf.append(StringUtil.NEWLINE);
        }
    }
}
