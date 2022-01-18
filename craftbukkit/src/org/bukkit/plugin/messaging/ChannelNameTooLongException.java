// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin.messaging;

public class ChannelNameTooLongException extends RuntimeException
{
    public ChannelNameTooLongException() {
        super("Attempted to send a Plugin Message to a channel that was too large. The maximum length a channel may be is 16 chars.");
    }
    
    public ChannelNameTooLongException(final String channel) {
        super("Attempted to send a Plugin Message to a channel that was too large. The maximum length a channel may be is 16 chars (attempted " + channel.length() + " - '" + channel + ".");
    }
}
