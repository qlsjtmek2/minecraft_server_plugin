// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

public class NoSuchBufferException extends ChannelPipelineException
{
    private static final long serialVersionUID = -131650785896627090L;
    
    public NoSuchBufferException() {
    }
    
    public NoSuchBufferException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public NoSuchBufferException(final String message) {
        super(message);
    }
    
    public NoSuchBufferException(final Throwable cause) {
        super(cause);
    }
}
