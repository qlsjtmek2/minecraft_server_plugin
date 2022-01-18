// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

public class IncompleteFlushException extends ChannelException
{
    private static final long serialVersionUID = -9049491093800487565L;
    
    public IncompleteFlushException() {
    }
    
    public IncompleteFlushException(final String message) {
        super(message);
    }
    
    public IncompleteFlushException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public IncompleteFlushException(final Throwable cause) {
        super(cause);
    }
}
