// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import java.util.Iterator;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.ArrayList;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import net.milkbowl.vault.item.ItemInfo;
import net.milkbowl.vault.item.Items;
import org.bukkit.ChatColor;
import java.math.RoundingMode;
import java.math.BigDecimal;
import org.bukkit.entity.Player;

public class MarketCore
{
    Market market;
    InterfaceHandler handler;
    MarketStorage storage;
    
    public MarketCore(final Market market, final InterfaceHandler handler, final MarketStorage storage) {
        this.market = market;
        this.handler = handler;
        this.storage = storage;
    }
    
    public void buyListing(final Listing listing, final Player player) {
        if (this.market.autoPayment()) {
            double price = listing.getPrice();
            this.market.getEcon().withdrawPlayer(player.getName(), price);
            if (this.market.cutTransactions()) {
                price -= new BigDecimal(this.market.getCut(price)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            }
            this.market.getEcon().depositPlayer(listing.getSeller(), price);
            if (this.market.getMailTime() > 0 && this.market.queueOnBuy() && !player.hasPermission("globalmarket.noqueue")) {
                this.market.getQueue().queueMail(listing.getItem(), player.getName());
                player.sendMessage(ChatColor.GREEN + this.market.getLocale().get("item_will_send", this.market.getMailTime()));
            }
            else {
                this.storage.storeMail(listing.getItem(), player.getName(), true);
            }
            this.storage.removeListing(listing.getId());
            this.handler.updateAllViewers();
            String itemName = listing.getItem().getType().toString();
            if (!this.market.useBukkitNames()) {
                final ItemInfo itemInfo = Items.itemById(listing.getItem().getTypeId());
                if (itemInfo != null) {
                    itemName = itemInfo.getName();
                }
            }
            this.storage.storeHistory(player.getName(), this.market.getLocale().get("history.item_listed", String.valueOf(itemName) + "x" + listing.getItem().getAmount(), price));
            this.storage.storeHistory(listing.getSeller(), this.market.getLocale().get("history.item_sold", String.valueOf(itemName) + "x" + listing.getItem().getAmount(), price));
            this.notifyPlayer(listing.getSeller(), ChatColor.GREEN + this.market.getLocale().get("you_sold_your_listing", String.valueOf(itemName) + "x" + listing.getItem().getAmount()));
            this.storage.incrementEarned(listing.getSeller(), price);
            this.storage.incrementSpent(player.getName(), price);
        }
        else {
            this.market.getEcon().withdrawPlayer(player.getName(), listing.getPrice());
            this.storage.storePayment(listing.getItem(), listing.getSeller(), listing.getPrice(), true);
            if (this.market.getMailTime() > 0 && this.market.queueOnBuy() && !player.hasPermission("globalmarket.noqueue")) {
                this.market.getQueue().queueMail(listing.getItem(), player.getName());
                player.sendMessage(ChatColor.GREEN + this.market.getLocale().get("item_will_send", this.market.getMailTime()));
            }
            else {
                this.storage.storeMail(listing.getItem(), player.getName(), true);
            }
            this.storage.removeListing(listing.getId());
            this.handler.updateAllViewers();
            String itemName2 = listing.getItem().getType().toString();
            if (!this.market.useBukkitNames()) {
                final ItemInfo itemInfo2 = Items.itemById(listing.getItem().getTypeId());
                if (itemInfo2 != null) {
                    itemName2 = itemInfo2.getName();
                }
            }
            this.storage.storeHistory(player.getName(), this.market.getLocale().get("history.item_listed", String.valueOf(itemName2) + "x" + listing.getItem().getAmount(), listing.getPrice()));
            this.storage.storeHistory(listing.getSeller(), this.market.getLocale().get("history.item_sold", String.valueOf(itemName2) + "x" + listing.getItem().getAmount(), listing.getPrice()));
            this.storage.incrementEarned(listing.getSeller(), listing.getPrice() - this.market.getCut(listing.getPrice()));
            this.storage.incrementSpent(player.getName(), listing.getPrice());
        }
    }
    
    public void removeListing(final Listing listing, final Player player) {
        if (this.market.getMailTime() > 0 && this.market.queueOnBuy() && !player.hasPermission("globalmarket.noqueue")) {
            this.market.getQueue().queueMail(listing.getItem(), listing.getSeller());
            player.sendMessage(ChatColor.GREEN + this.market.getLocale().get("item_will_send", this.market.getMailTime()));
        }
        else {
            this.storage.storeMail(listing.getItem(), listing.getSeller(), true);
        }
        this.storage.removeListing(listing.getId());
        this.handler.updateAllViewers();
        String itemName = listing.getItem().getType().toString();
        if (!this.market.useBukkitNames()) {
            final ItemInfo itemInfo = Items.itemById(listing.getItem().getTypeId());
            if (itemInfo != null) {
                itemName = itemInfo.getName();
            }
        }
        if (listing.getSeller().equalsIgnoreCase(player.getName())) {
            this.storage.storeHistory(player.getName(), this.market.getLocale().get("history.listing_removed", "You", String.valueOf(itemName) + "x" + listing.getItem().getAmount()));
        }
        else {
            this.storage.storeHistory(listing.getSeller(), this.market.getLocale().get("history.listing_removed", player.getName(), String.valueOf(itemName) + "x" + listing.getItem().getAmount()));
        }
    }
    
    public void removeListing(final Listing listing, final String player) {
        this.storage.storeMail(listing.getItem(), listing.getSeller(), true);
        this.storage.removeListing(listing.getId());
        this.handler.updateAllViewers();
        String itemName = listing.getItem().getType().toString();
        if (!this.market.useBukkitNames()) {
            final ItemInfo itemInfo = Items.itemById(listing.getItem().getTypeId());
            if (itemInfo != null) {
                itemName = itemInfo.getName();
            }
        }
        if (listing.getSeller().equalsIgnoreCase(player)) {
            this.storage.storeHistory(player, this.market.getLocale().get("history.listing_removed", "You", String.valueOf(itemName) + "x" + listing.getItem().getAmount()));
        }
        else {
            this.storage.storeHistory(listing.getSeller(), this.market.getLocale().get("history.listing_removed", player, String.valueOf(itemName) + "x" + listing.getItem().getAmount()));
        }
    }
    
    public void retrieveMail(final int id, final String player) {
        final Inventory playerInv = (Inventory)this.market.getServer().getPlayer(player).getInventory();
        playerInv.addItem(new ItemStack[] { this.storage.getMailItem(player, id) });
        this.storage.removeMail(player, id);
    }
    
    public void notifyPlayer(final String player, final String notification) {
        final Player p = this.market.getServer().getPlayer(player);
        if (p != null) {
            p.sendMessage(notification);
        }
    }
    
    public void showHistory(final Player player) {
        final ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta)book.getItemMeta();
        if (meta == null) {
            meta = (BookMeta)this.market.getServer().getItemFactory().getItemMeta(book.getType());
        }
        meta.setTitle(this.market.getLocale().get("history.item_name"));
        meta.setAuthor("Server");
        final Map<String, Long> history = this.storage.getHistory(player.getName(), 15);
        final List<String> pages = new ArrayList<String>();
        final String pagesStr = String.valueOf(this.market.getLocale().get("history.title", player.getName())) + "\n\n" + this.market.getLocale().get("history.total_earned", this.market.getEcon().format(this.storage.getEarned(player.getName()))) + "\n" + this.market.getLocale().get("history.total_spent", this.market.getEcon().format(this.storage.getSpent(player.getName()))) + "\n" + this.market.getLocale().get("history.actual_amount_made", this.market.getEcon().format(this.storage.getEarned(player.getName()) - this.storage.getSpent(player.getName())));
        pages.add(pagesStr);
        pages.set(0, pages.get(0).replace("\ufffdf", "").replace("\ufffd7", "").replace("\ufffd6", ""));
        for (final Map.Entry<String, Long> set : history.entrySet()) {
            final Date date = new Date(set.getValue() * 1000L);
            pages.add(String.valueOf(set.getKey()) + "\n" + this.market.getLocale().get("history.at_time", date.toString()));
        }
        meta.setPages((List)pages);
        book.setItemMeta((ItemMeta)meta);
        player.getInventory().addItem(new ItemStack[] { book });
    }
}
