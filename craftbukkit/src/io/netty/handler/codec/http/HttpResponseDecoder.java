// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

public class HttpResponseDecoder extends HttpObjectDecoder
{
    private static final HttpResponseStatus UNKNOWN_STATUS;
    
    public HttpResponseDecoder() {
    }
    
    public HttpResponseDecoder(final int maxInitialLineLength, final int maxHeaderSize, final int maxChunkSize) {
        super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true);
    }
    
    @Override
    protected HttpMessage createMessage(final String[] initialLine) {
        return new DefaultHttpResponse(HttpVersion.valueOf(initialLine[0]), new HttpResponseStatus(Integer.valueOf(initialLine[1]), initialLine[2]));
    }
    
    @Override
    protected HttpMessage createInvalidMessage() {
        return new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseDecoder.UNKNOWN_STATUS);
    }
    
    @Override
    protected boolean isDecodingRequest() {
        return false;
    }
    
    static {
        UNKNOWN_STATUS = new HttpResponseStatus(999, "Unknown");
    }
}
