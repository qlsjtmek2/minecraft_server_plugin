// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

public interface HttpMessage extends HttpObject
{
    HttpVersion getProtocolVersion();
    
    HttpMessage setProtocolVersion(final HttpVersion p0);
    
    HttpHeaders headers();
}
