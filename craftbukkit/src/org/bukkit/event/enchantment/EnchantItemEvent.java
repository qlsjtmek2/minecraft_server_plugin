// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.enchantment;

import java.util.HashMap;
import org.bukkit.inventory.InventoryView;
import org.bukkit.entity.Player;
import org.bukkit.enchantments.Enchantment;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryEvent;

public class EnchantItemEvent extends InventoryEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Block table;
    private final ItemStack item;
    private int level;
    private boolean cancelled;
    private final Map<Enchantment, Integer> enchants;
    private final Player enchanter;
    private int button;
    
    public EnchantItemEvent(final Player enchanter, final InventoryView view, final Block table, final ItemStack item, final int level, final Map<Enchantment, Integer> enchants, final int i) {
        super(view);
        this.enchanter = enchanter;
        this.table = table;
        this.item = item;
        this.level = level;
        this.enchants = new HashMap<Enchantment, Integer>(enchants);
        this.cancelled = false;
        this.button = i;
    }
    
    public Player getEnchanter() {
        return this.enchanter;
    }
    
    public Block getEnchantBlock() {
        return this.table;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public int getExpLevelCost() {
        return this.level;
    }
    
    public void setExpLevelCost(final int level) {
        this.level = level;
    }
    
    public Map<Enchantment, Integer> getEnchantsToAdd() {
        return this.enchants;
    }
    
    public int whichButton() {
        return this.button;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return EnchantItemEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EnchantItemEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
