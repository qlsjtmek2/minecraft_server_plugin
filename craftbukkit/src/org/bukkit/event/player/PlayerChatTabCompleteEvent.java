// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import java.util.Collection;
import org.bukkit.event.HandlerList;

public class PlayerChatTabCompleteEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final String message;
    private final String lastToken;
    private final Collection<String> completions;
    
    public PlayerChatTabCompleteEvent(final Player who, final String message, final Collection<String> completions) {
        super(who);
        Validate.notNull(message, "Message cannot be null");
        Validate.notNull(completions, "Completions cannot be null");
        this.message = message;
        final int i = message.lastIndexOf(32);
        if (i < 0) {
            this.lastToken = message;
        }
        else {
            this.lastToken = message.substring(i + 1);
        }
        this.completions = completions;
    }
    
    public String getChatMessage() {
        return this.message;
    }
    
    public String getLastToken() {
        return this.lastToken;
    }
    
    public Collection<String> getTabCompletions() {
        return this.completions;
    }
    
    public HandlerList getHandlers() {
        return PlayerChatTabCompleteEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerChatTabCompleteEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
