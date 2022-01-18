// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import java.util.IllegalFormatException;
import org.bukkit.entity.Player;
import java.util.Set;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class AsyncPlayerChatEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private String message;
    private String format;
    private final Set<Player> recipients;
    
    public AsyncPlayerChatEvent(final boolean async, final Player who, final String message, final Set<Player> players) {
        super(who, async);
        this.cancel = false;
        this.format = "<%1$s> %2$s";
        this.message = message;
        this.recipients = players;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public String getFormat() {
        return this.format;
    }
    
    public void setFormat(final String format) throws IllegalFormatException, NullPointerException {
        try {
            String.format(format, this.player, this.message);
        }
        catch (RuntimeException ex) {
            ex.fillInStackTrace();
            throw ex;
        }
        this.format = format;
    }
    
    public Set<Player> getRecipients() {
        return this.recipients;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public HandlerList getHandlers() {
        return AsyncPlayerChatEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return AsyncPlayerChatEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
