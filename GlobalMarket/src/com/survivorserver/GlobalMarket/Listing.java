// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import net.milkbowl.vault.item.ItemInfo;
import net.milkbowl.vault.item.Items;
import org.bukkit.inventory.ItemStack;

public class Listing
{
    int id;
    ItemStack item;
    String seller;
    double price;
    Long time;
    String clientName;
    
    public Listing(final Market market, final int id, final ItemStack item, final String seller, final double price, final Long time) {
        this.id = id;
        this.item = new ItemStack(item);
        this.seller = seller;
        this.price = price;
        this.time = time;
        this.clientName = item.getType().toString();
        if (!market.useBukkitNames()) {
            final ItemInfo itemInfo = Items.itemById(item.getTypeId());
            if (itemInfo != null) {
                this.clientName = itemInfo.getName();
            }
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    public ItemStack getItem() {
        return this.item.clone();
    }
    
    public String getSeller() {
        return this.seller;
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public Long getTime() {
        return this.time;
    }
    
    public String getClientName() {
        return this.clientName;
    }
}
