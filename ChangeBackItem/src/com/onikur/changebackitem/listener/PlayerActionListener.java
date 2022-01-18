// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem.listener;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.GameMode;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import com.onikur.changebackitem.config.MainConfig;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import com.onikur.changebackitem.ChangeBackItem;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class PlayerActionListener implements Listener
{
    public PlayerActionListener() {
        Bukkit.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)ChangeBackItem.get());
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(final BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!ChangeBackItem.get().isValidBlockForStoreItem(event.getBlock())) {
            return;
        }
        final Player p = event.getPlayer();
        if (MainConfig.Disable_Worlds.getStringList().contains(p.getWorld())) {
            return;
        }
        final ItemStack itemStack = event.getItemInHand().clone();
        if (!itemStack.getType().isBlock()) {
            return;
        }
        if (!this.isUniqueItem(itemStack)) {
            return;
        }
        itemStack.setAmount(1);
        event.getBlockPlaced().setMetadata("ChangeBackItem", (MetadataValue)new FixedMetadataValue((Plugin)ChangeBackItem.get(), (Object)itemStack));
        ChangeBackItem.get().getBlockStorage().addBlock(event.getBlockPlaced(), itemStack);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(final BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Block b = event.getBlock();
        if (!ChangeBackItem.get().isValidBlockForStoreItem(event.getBlock())) {
            if (b.hasMetadata("ChangeBackItem")) {
                this.removeBlockData(b);
            }
            return;
        }
        final Player p = event.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) {
            this.removeBlockData(b);
            return;
        }
        if (!b.hasMetadata("ChangeBackItem")) {
            return;
        }
        event.setCancelled(true);
        final ItemStack itemStack = (ItemStack)b.getMetadata("ChangeBackItem").get(0).value();
        if (!itemStack.getType().isBlock()) {
            this.removeBlockData(b);
            return;
        }
        b.getWorld().dropItemNaturally(b.getLocation(), itemStack);
        b.setType(Material.AIR);
        this.removeBlockData(b);
    }
    
    public void removeBlockData(final Block b) {
        b.removeMetadata("ChangeBackItem", (Plugin)ChangeBackItem.get());
        ChangeBackItem.get().getBlockStorage().removeBlock(b);
    }
    
    public boolean isUniqueItem(final ItemStack itemStack) {
        if (itemStack.getEnchantments().size() > 0) {
            return true;
        }
        if (!itemStack.hasItemMeta()) {
            return false;
        }
        final ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.hasDisplayName();
    }
}
