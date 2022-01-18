// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import io.netty.buffer.ReferenceCounted;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.buffer.ByteBuf;

public class DefaultFullHttpResponse extends DefaultHttpResponse implements FullHttpResponse
{
    private final ByteBuf content;
    private final HttpHeaders trailingHeaders;
    
    public DefaultFullHttpResponse(final HttpVersion version, final HttpResponseStatus status) {
        this(version, status, Unpooled.buffer(0));
    }
    
    public DefaultFullHttpResponse(final HttpVersion version, final HttpResponseStatus status, final ByteBuf content) {
        super(version, status);
        this.trailingHeaders = new DefaultHttpHeaders();
        if (content == null) {
            throw new NullPointerException("content");
        }
        this.content = content;
    }
    
    @Override
    public HttpHeaders trailingHeaders() {
        return this.trailingHeaders;
    }
    
    @Override
    public ByteBuf data() {
        return this.content;
    }
    
    @Override
    public int refCnt() {
        return this.content.refCnt();
    }
    
    @Override
    public FullHttpResponse retain() {
        this.content.retain();
        return this;
    }
    
    @Override
    public FullHttpResponse retain(final int increment) {
        this.content.retain(increment);
        return this;
    }
    
    @Override
    public boolean release() {
        return this.content.release();
    }
    
    @Override
    public boolean release(final int decrement) {
        return this.content.release(decrement);
    }
    
    @Override
    public FullHttpResponse setProtocolVersion(final HttpVersion version) {
        super.setProtocolVersion(version);
        return this;
    }
    
    @Override
    public FullHttpResponse setStatus(final HttpResponseStatus status) {
        super.setStatus(status);
        return this;
    }
    
    @Override
    public FullHttpResponse copy() {
        final DefaultFullHttpResponse copy = new DefaultFullHttpResponse(this.getProtocolVersion(), this.getStatus(), this.data().copy());
        copy.headers().set(this.headers());
        copy.trailingHeaders().set(this.trailingHeaders());
        return copy;
    }
}
