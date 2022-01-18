// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.apache.commons.lang.Validate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Arrays;
import org.bukkit.entity.Player;
import java.util.Set;
import org.bukkit.event.HandlerList;
import org.bukkit.Warning;
import org.bukkit.event.Cancellable;

@Deprecated
@Warning(reason = "Listening to this event forces chat to wait for the main thread, delaying chat messages.")
public class PlayerChatEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private String message;
    private String format;
    private final Set<Player> recipients;
    
    public PlayerChatEvent(final Player player, final String message) {
        super(player);
        this.cancel = false;
        this.message = message;
        this.format = "<%1$s> %2$s";
        this.recipients = new HashSet<Player>(Arrays.asList(player.getServer().getOnlinePlayers()));
    }
    
    public PlayerChatEvent(final Player player, final String message, final String format, final Set<Player> recipients) {
        super(player);
        this.cancel = false;
        this.message = message;
        this.format = format;
        this.recipients = recipients;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public void setPlayer(final Player player) {
        Validate.notNull(player, "Player cannot be null");
        this.player = player;
    }
    
    public String getFormat() {
        return this.format;
    }
    
    public void setFormat(final String format) {
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
    
    public HandlerList getHandlers() {
        return PlayerChatEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerChatEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
