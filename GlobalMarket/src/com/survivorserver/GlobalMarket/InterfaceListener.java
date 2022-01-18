// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import java.util.Iterator;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;

public class InterfaceListener implements Listener
{
    Market market;
    InterfaceHandler handler;
    MarketStorage storage;
    MarketCore core;
    
    public InterfaceListener(final Market market, final InterfaceHandler handler, final MarketStorage storage, final MarketCore core) {
        this.market = market;
        this.handler = handler;
        this.storage = storage;
        this.core = core;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public synchronized void handleClickEvent(final InventoryClickEvent event) {
        final InterfaceViewer viewer = this.handler.findViewer(event.getWhoClicked().getName());
        if (event.getInventory().getTitle().equalsIgnoreCase(this.market.getLocale().get("interface.listings_title")) || event.getInventory().getTitle().equalsIgnoreCase(this.market.getLocale().get("interface.mail_title"))) {
            event.setCancelled(true);
            if (viewer != null) {
                if (event.getSlot() < 45 && !event.isRightClick()) {
                    if (viewer.getViewType() == InterfaceViewer.ViewType.MAIL && this.isMarketItem(event.getCurrentItem())) {
                        this.handleMailAction(event, viewer);
                    }
                    if (viewer.getViewType() == InterfaceViewer.ViewType.LISTINGS && this.isMarketItem(event.getCurrentItem())) {
                        this.handleListingsAction(event, viewer);
                    }
                }
                else if (event.isRightClick()) {
                    viewer.setLastAction(null);
                    viewer.setLastActionSlot(-1);
                }
                else {
                    if (event.getSlot() == 47 && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                        if (viewer.getSearch() == null) {
                            final Player player = (Player)event.getWhoClicked();
                            player.closeInventory();
                            this.handler.removeViewer(viewer);
                            this.market.startSearch(player);
                            return;
                        }
                        viewer.setSearch(null);
                        viewer.setLastAction(null);
                    }
                    if (event.getSlot() == 53 && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                        viewer.setPage(viewer.getPage() + 1);
                        viewer.setLastAction(null);
                    }
                    if (event.getSlot() == 45 && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                        viewer.setPage(viewer.getPage() - 1);
                        viewer.setLastAction(null);
                    }
                }
                this.handler.refreshViewer(viewer);
            }
            else {
                event.getWhoClicked().closeInventory();
            }
        }
    }
    
    public void handleListingsAction(final InventoryClickEvent event, final InterfaceViewer viewer) {
        if (viewer.getLastAction() == null) {
            if (event.isShiftClick()) {
                viewer.setLastAction(InterfaceViewer.InterfaceAction.SHIFTCLICK);
                viewer.setLastActionSlot(event.getSlot());
            }
            else if (event.isLeftClick()) {
                viewer.setLastAction(InterfaceViewer.InterfaceAction.LEFTCLICK);
                viewer.setLastActionSlot(event.getSlot());
            }
        }
        else if (viewer.getLastActionSlot() == event.getSlot()) {
            if (event.isShiftClick() && viewer.getLastAction() == InterfaceViewer.InterfaceAction.SHIFTCLICK) {
                if (viewer.getBoundSlots().containsKey(event.getSlot())) {
                    final Listing listing = this.storage.getListing(viewer.getBoundSlots().get(event.getSlot()));
                    if (listing != null && (viewer.getViewer().equalsIgnoreCase(listing.getSeller()) || this.handler.isAdmin(viewer.getViewer()))) {
                        this.core.removeListing(listing, (Player)event.getWhoClicked());
                    }
                }
                viewer.setLastAction(null);
                viewer.setLastActionSlot(-1);
            }
            else if (event.isLeftClick() && viewer.getLastAction() == InterfaceViewer.InterfaceAction.LEFTCLICK) {
                if (viewer.getBoundSlots().containsKey(event.getSlot())) {
                    final Listing listing = this.storage.getListing(viewer.getBoundSlots().get(event.getSlot()));
                    if (listing != null && !listing.getSeller().equalsIgnoreCase(event.getWhoClicked().getName()) && this.market.getEcon().has(event.getWhoClicked().getName(), listing.price)) {
                        this.core.buyListing(listing, (Player)event.getWhoClicked());
                    }
                }
                viewer.setLastAction(null);
                viewer.setLastActionSlot(-1);
            }
            else {
                viewer.setLastAction(null);
                viewer.setLastActionSlot(-1);
            }
        }
        else {
            viewer.setLastAction(null);
            viewer.setLastActionSlot(-1);
        }
    }
    
    public void handleMailAction(final InventoryClickEvent event, final InterfaceViewer viewer) {
        if (event.isLeftClick() && viewer.getBoundSlots().containsKey(event.getSlot())) {
            final PlayerInventory playerInv = event.getWhoClicked().getInventory();
            if (playerInv.firstEmpty() >= 0) {
                if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() instanceof BookMeta) {
                    final BookMeta meta = (BookMeta)event.getCurrentItem().getItemMeta();
                    if (meta.hasTitle() && meta.getTitle().equalsIgnoreCase(this.market.getLocale().get("transaction_log.item_name"))) {
                        final double amount = this.storage.getPaymentAmount(viewer.getBoundSlots().get(event.getSlot()), viewer.getViewer());
                        if (amount > 0.0) {
                            this.market.getEcon().depositPlayer(viewer.getViewer(), amount);
                            this.storage.nullifyPayment(viewer.getBoundSlots().get(event.getSlot()), viewer.getViewer());
                            ((Player)event.getWhoClicked()).sendMessage(ChatColor.GREEN + this.market.getLocale().get("picked_up_your_earnings", this.market.getEcon().format(this.market.getEcon().getBalance(viewer.getViewer()))));
                        }
                    }
                }
                this.core.retrieveMail(viewer.getBoundSlots().get(event.getSlot()), event.getWhoClicked().getName());
                viewer.setLastAction(null);
                viewer.setLastActionSlot(-1);
            }
            else {
                viewer.setLastAction(InterfaceViewer.InterfaceAction.LEFTCLICK);
                viewer.setLastActionSlot(event.getSlot());
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public synchronized void handleInventoryClose(final InventoryCloseEvent event) {
        final InterfaceViewer viewer = this.handler.findViewer(event.getPlayer().getName());
        if (viewer != null) {
            this.handler.removeViewer(viewer);
        }
        final ItemStack[] items = event.getPlayer().getInventory().getContents();
        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null && this.isMarketItem(items[i])) {
                event.getPlayer().getInventory().remove(items[i]);
            }
        }
    }
    
    public boolean isMarketItem(final ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            final ItemMeta meta = item.getItemMeta();
            if (meta.hasLore()) {
                for (final String lore : meta.getLore()) {
                    if (lore.contains(this.market.getLocale().get("price")) || lore.contains(this.market.getLocale().get("click_to_retrieve"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
