// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Bukkit;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerEditBookEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final BookMeta previousBookMeta;
    private final int slot;
    private BookMeta newBookMeta;
    private boolean isSigning;
    private boolean cancel;
    
    public PlayerEditBookEvent(final Player who, final int slot, final BookMeta previousBookMeta, final BookMeta newBookMeta, final boolean isSigning) {
        super(who);
        Validate.isTrue(slot >= 0 && slot <= 8, "Slot must be in range 0-8 inclusive");
        Validate.notNull(previousBookMeta, "Previous book meta must not be null");
        Validate.notNull(newBookMeta, "New book meta must not be null");
        Bukkit.getItemFactory().equals(previousBookMeta, newBookMeta);
        this.previousBookMeta = previousBookMeta;
        this.newBookMeta = newBookMeta;
        this.slot = slot;
        this.isSigning = isSigning;
        this.cancel = false;
    }
    
    public BookMeta getPreviousBookMeta() {
        return this.previousBookMeta.clone();
    }
    
    public BookMeta getNewBookMeta() {
        return this.newBookMeta.clone();
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public void setNewBookMeta(final BookMeta newBookMeta) throws IllegalArgumentException {
        Validate.notNull(newBookMeta, "New book meta must not be null");
        Bukkit.getItemFactory().equals(newBookMeta, null);
        this.newBookMeta = newBookMeta.clone();
    }
    
    public boolean isSigning() {
        return this.isSigning;
    }
    
    public void setSigning(final boolean signing) {
        this.isSigning = signing;
    }
    
    public HandlerList getHandlers() {
        return PlayerEditBookEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerEditBookEvent.handlers;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    static {
        handlers = new HandlerList();
    }
}
