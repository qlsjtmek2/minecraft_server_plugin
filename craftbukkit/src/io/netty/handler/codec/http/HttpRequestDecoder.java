// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

public class HttpRequestDecoder extends HttpObjectDecoder
{
    public HttpRequestDecoder() {
    }
    
    public HttpRequestDecoder(final int maxInitialLineLength, final int maxHeaderSize, final int maxChunkSize) {
        super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true);
    }
    
    @Override
    protected HttpMessage createMessage(final String[] initialLine) throws Exception {
        return new DefaultHttpRequest(HttpVersion.valueOf(initialLine[2]), HttpMethod.valueOf(initialLine[0]), initialLine[1]);
    }
    
    @Override
    protected HttpMessage createInvalidMessage() {
        return new DefaultHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, "/bad-request");
    }
    
    @Override
    protected boolean isDecodingRequest() {
        return true;
    }
}
