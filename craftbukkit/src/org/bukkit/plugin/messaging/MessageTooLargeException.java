// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin.messaging;

public class MessageTooLargeException extends RuntimeException
{
    public MessageTooLargeException() {
        this("Attempted to send a plugin message that was too large. The maximum length a plugin message may be is 32766 bytes.");
    }
    
    public MessageTooLargeException(final byte[] message) {
        this(message.length);
    }
    
    public MessageTooLargeException(final int length) {
        this("Attempted to send a plugin message that was too large. The maximum length a plugin message may be is 32766 bytes (tried to send one that is " + length + " bytes long).");
    }
    
    public MessageTooLargeException(final String msg) {
        super(msg);
    }
}
