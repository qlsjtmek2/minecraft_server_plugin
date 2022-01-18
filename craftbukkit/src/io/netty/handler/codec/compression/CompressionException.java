// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.compression;

import io.netty.handler.codec.CodecException;

public class CompressionException extends CodecException
{
    private static final long serialVersionUID = 5603413481274811897L;
    
    public CompressionException() {
    }
    
    public CompressionException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public CompressionException(final String message) {
        super(message);
    }
    
    public CompressionException(final Throwable cause) {
        super(cause);
    }
}
