// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

public class IllegalBufferAccessException extends IllegalStateException
{
    private static final long serialVersionUID = -6734326916218551327L;
    
    public IllegalBufferAccessException() {
    }
    
    public IllegalBufferAccessException(final String message) {
        super(message);
    }
    
    public IllegalBufferAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public IllegalBufferAccessException(final Throwable cause) {
        super(cause);
    }
}
