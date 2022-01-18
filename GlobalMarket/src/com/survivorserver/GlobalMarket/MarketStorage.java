// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.configuration.ConfigurationSection;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.inventory.meta.ItemMeta;
import java.math.RoundingMode;
import java.math.BigDecimal;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import java.util.Iterator;
import net.milkbowl.vault.item.ItemInfo;
import net.milkbowl.vault.item.Items;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class MarketStorage
{
    ConfigHandler config;
    Market market;
    
    public MarketStorage(final ConfigHandler config, final Market market) {
        this.config = config;
        this.market = market;
    }
    
    public void storeListing(final ItemStack item, final String player, final double price) {
        final int id = this.getListingsIndex() + 1;
        final String path = "listings." + id;
        this.config.getListingsYML().set(String.valueOf(path) + ".item", (Object)item);
        this.config.getListingsYML().set(String.valueOf(path) + ".seller", (Object)player);
        this.config.getListingsYML().set(String.valueOf(path) + ".price", (Object)price);
        this.config.getListingsYML().set(String.valueOf(path) + ".time", (Object)(System.currentTimeMillis() / 1000L));
        this.incrementListingsIndex();
        this.config.saveListingsYML();
        this.market.interfaceHandler.updateAllViewers();
    }
    
    public int getNumListings() {
        if (!this.config.getListingsYML().isSet("listings")) {
            return 0;
        }
        return this.config.getListingsYML().getConfigurationSection("listings").getKeys(false).size();
    }
    
    public int getListingsIndex() {
        if (!this.config.getListingsYML().isSet("index")) {
            this.config.getListingsYML().set("index", (Object)0);
        }
        return this.config.getListingsYML().getInt("index");
    }
    
    public void incrementListingsIndex() {
        final int index = this.getListingsIndex() + 1;
        this.config.getListingsYML().set("index", (Object)index);
    }
    
    public void removeListing(final int id) {
        final String path = "listings." + id;
        if (!this.config.getListingsYML().isSet(path)) {
            return;
        }
        this.config.getListingsYML().set(path, (Object)null);
        this.config.saveListingsYML();
    }
    
    public List<Listing> getAllListings() {
        final List<Listing> listings = new ArrayList<Listing>();
        for (int i = this.getListingsIndex(); i >= 1; --i) {
            final String path = "listings." + i;
            if (this.config.getListingsYML().isSet(path)) {
                final Listing listing = new Listing(this.market, i, this.config.getListingsYML().getItemStack(String.valueOf(path) + ".item").clone(), this.config.getListingsYML().getString(String.valueOf(path) + ".seller"), this.config.getListingsYML().getDouble(String.valueOf(path) + ".price"), this.config.getListingsYML().getLong(String.valueOf(path) + ".time"));
                listings.add(listing);
            }
        }
        return listings;
    }
    
    public List<Listing> getAllListings(final String search) {
        final List<Listing> listings = new ArrayList<Listing>();
        for (int i = this.getListingsIndex(); i >= 1; --i) {
            final String path = "listings." + i;
            if (this.config.getListingsYML().isSet(path)) {
                final String seller = this.config.getListingsYML().getString(String.valueOf(path) + ".seller");
                final ItemStack item = this.config.getListingsYML().getItemStack(String.valueOf(path) + ".item").clone();
                String itemName = item.getType().toString();
                if (!this.market.useBukkitNames()) {
                    final ItemInfo itemInfo = Items.itemById(item.getTypeId());
                    if (itemInfo != null) {
                        itemName = itemInfo.getName();
                    }
                }
                if (itemName.toLowerCase().contains(search.toLowerCase()) || this.isItemId(search, item.getTypeId()) || this.isInDisplayName(search.toLowerCase(), item) || this.isInEnchants(search.toLowerCase(), item) || this.isInLore(search.toLowerCase(), item) || seller.toLowerCase().contains(search.toLowerCase())) {
                    listings.add(new Listing(this.market, i, item, seller, this.config.getListingsYML().getDouble(String.valueOf(path) + ".price"), this.config.getListingsYML().getLong(String.valueOf(path) + ".time")));
                }
            }
        }
        return listings;
    }
    
    public int getNumListings(final String seller) {
        int n = 0;
        for (final Listing listing : this.getAllListings(seller)) {
            if (listing.getSeller().equalsIgnoreCase(seller)) {
                ++n;
            }
        }
        return n;
    }
    
    public Listing getListing(final int id) {
        final String path = "listings." + id;
        return new Listing(this.market, id, this.config.getListingsYML().getItemStack(String.valueOf(path) + ".item").clone(), this.config.getListingsYML().getString(String.valueOf(path) + ".seller"), this.config.getListingsYML().getDouble(String.valueOf(path) + ".price"), this.config.getListingsYML().getLong(String.valueOf(path) + ".time"));
    }
    
    public void storeMail(final ItemStack item, final String player, final boolean notify) {
        final int id = this.getMailIndex(player) + 1;
        final String path = String.valueOf(player) + "." + id;
        this.config.getMailYML().set(String.valueOf(path) + ".item", (Object)item);
        this.config.getMailYML().set(String.valueOf(path) + ".time", (Object)(System.currentTimeMillis() / 1000L));
        this.incrementMailIndex(player);
        if (notify) {
            final Player reciever = this.market.getServer().getPlayer(player);
            if (reciever != null) {
                reciever.sendMessage(ChatColor.GREEN + this.market.getLocale().get("you_have_new_mail"));
            }
        }
    }
    
    public void storePayment(final ItemStack item, final String player, final double amount, final boolean notify) {
        final ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta)book.getItemMeta();
        if (meta == null) {
            meta = (BookMeta)this.market.getServer().getItemFactory().getItemMeta(book.getType());
        }
        meta.setTitle(this.market.getLocale().get("transaction_log.item_name"));
        final double cut = new BigDecimal(this.market.getCut(amount)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        String itemName = item.getType().toString();
        if (!this.market.useBukkitNames()) {
            final ItemInfo itemInfo = Items.itemById(item.getTypeId());
            if (itemInfo != null) {
                itemName = itemInfo.getName();
            }
        }
        final String logStr = String.valueOf(this.market.getLocale().get("transaction_log.title")) + "\n\n" + this.market.getLocale().get("transaction_log.item_sold", String.valueOf(itemName) + "x" + item.getAmount()) + "\n\n" + this.market.getLocale().get("transaction_log.sale_price", amount) + "\n\n" + this.market.getLocale().get("transaction_log.market_cut", cut) + "\n\n" + this.market.getLocale().get("transaction_log.amount_recieved", amount - cut);
        meta.setPages(new String[] { logStr });
        book.setItemMeta((ItemMeta)meta);
        final int id = this.getMailIndex(player) + 1;
        final String path = String.valueOf(player) + "." + id;
        this.config.getMailYML().set(String.valueOf(path) + ".item", (Object)book);
        this.config.getMailYML().set(String.valueOf(path) + ".time", (Object)(System.currentTimeMillis() / 1000L));
        this.config.getMailYML().set(String.valueOf(path) + ".amount", (Object)(amount - cut));
        this.incrementMailIndex(player);
        if (notify) {
            final Player reciever = this.market.getServer().getPlayer(player);
            if (reciever != null) {
                reciever.sendMessage(ChatColor.GREEN + this.market.getLocale().get("listing_purchased_mailbox", itemName));
            }
        }
    }
    
    public double getPaymentAmount(final int id, final String player) {
        if (!this.config.getMailYML().isSet(String.valueOf(player) + "." + id + ".amount")) {
            return 0.0;
        }
        return this.config.getMailYML().getDouble(String.valueOf(player) + "." + id + ".amount");
    }
    
    public void nullifyPayment(final int id, final String player) {
        if (!this.config.getMailYML().isSet(String.valueOf(player) + "." + id + ".amount")) {
            return;
        }
        this.config.getMailYML().set(String.valueOf(player) + "." + id + ".amount", (Object)null);
        this.config.saveMailYML();
    }
    
    public int getMailIndex(final String player) {
        if (!this.config.getMailYML().isSet("index." + player)) {
            this.config.getMailYML().set("index." + player, (Object)0);
            this.config.saveMailYML();
        }
        return this.config.getMailYML().getInt("index." + player);
    }
    
    public void incrementMailIndex(final String player) {
        final int index = this.getMailIndex(player) + 1;
        this.config.getMailYML().set("index." + player, (Object)index);
        this.config.saveMailYML();
    }
    
    public int getNumMail(final String player) {
        if (!this.config.getMailYML().isSet(player)) {
            return 0;
        }
        return this.config.getMailYML().getConfigurationSection(player).getKeys(false).size();
    }
    
    public Map<Integer, ItemStack> getAllMailFor(final String player) {
        final Map<Integer, ItemStack> mail = new HashMap<Integer, ItemStack>();
        for (int i = 1; i <= this.getMailIndex(player); ++i) {
            final String path = String.valueOf(player) + "." + i;
            if (this.config.getMailYML().isSet(path)) {
                mail.put(i, this.config.getMailYML().getItemStack(String.valueOf(path) + ".item").clone());
            }
        }
        return mail;
    }
    
    public ItemStack getMailItem(final String player, final int id) {
        if (this.config.getMailYML().isSet(String.valueOf(player) + "." + id)) {
            return this.config.getMailYML().getItemStack(String.valueOf(player) + "." + id + ".item").clone();
        }
        return null;
    }
    
    public void removeMail(final String player, final int id) {
        final String path = String.valueOf(player) + "." + id;
        if (!this.config.getMailYML().isSet(path)) {
            return;
        }
        this.config.getMailYML().set(path, (Object)null);
        this.config.saveMailYML();
    }
    
    public int getNumHistory(final String player) {
        if (!this.config.getHistoryYML().isSet(player)) {
            return 0;
        }
        return this.config.getHistoryYML().getConfigurationSection(player).getKeys(false).size();
    }
    
    public void storeHistory(final String player, final String info) {
        final int id = this.getNumHistory(player) + 1;
        this.config.getHistoryYML().set(String.valueOf(player) + "." + id + ".info", (Object)info);
        this.config.getHistoryYML().set(String.valueOf(player) + "." + id + ".time", (Object)(System.currentTimeMillis() / 1000L));
        this.config.saveHistoryYML();
    }
    
    public Map<String, Long> getHistory(final String player, final int stop) {
        final Map<String, Long> history = new HashMap<String, Long>();
        int p = 0;
        for (int i = this.getNumHistory(player); i > 0; --i) {
            history.put(this.config.getHistoryYML().getString(String.valueOf(player) + "." + i + ".info"), this.config.getHistoryYML().getLong(String.valueOf(player) + "." + i + ".time"));
            if (++p >= stop) {
                break;
            }
        }
        if (history.isEmpty()) {
            history.put("No history! ...yet ;)", 0L);
        }
        return history;
    }
    
    public void incrementSpent(final String player, final double amount) {
        this.config.getHistoryYML().set("spent." + player, (Object)(this.getSpent(player) + amount));
    }
    
    public double getSpent(final String player) {
        if (!this.config.getHistoryYML().isSet("spent." + player)) {
            return 0.0;
        }
        return this.config.getHistoryYML().getDouble("spent." + player);
    }
    
    public void incrementEarned(final String player, final double amount) {
        this.config.getHistoryYML().set("earned." + player, (Object)(this.getEarned(player) + amount));
    }
    
    public double getEarned(final String player) {
        if (!this.config.getHistoryYML().isSet("earned." + player)) {
            return 0.0;
        }
        return this.config.getHistoryYML().getDouble("earned." + player);
    }
    
    public int getQueueIndex() {
        if (!this.config.getQueueYML().isSet("index")) {
            this.config.getQueueYML().set("index", (Object)0);
        }
        return this.config.getQueueYML().getInt("index");
    }
    
    public void incrementQueueIndex() {
        final int index = this.getQueueIndex() + 1;
        this.config.getQueueYML().set("index", (Object)index);
    }
    
    public void storeQueueItem(final MarketQueue.QueueType type, final Object... args) {
        final int id = this.getQueueIndex();
        final String path = "queue." + id;
        this.config.getQueueYML().set(String.valueOf(path) + ".type", (Object)type.toString());
        for (int i = 0; i < args.length; ++i) {
            this.config.getQueueYML().set(String.valueOf(path) + "." + i, args[i]);
        }
        this.config.getQueueYML().set(String.valueOf(path) + ".time", (Object)System.currentTimeMillis());
        this.incrementQueueIndex();
        this.config.saveQueueYML();
    }
    
    public Map<Integer, List<Object>> getAllQueueItems() {
        final Map<Integer, List<Object>> items = new HashMap<Integer, List<Object>>();
        for (int i = 0; i < this.getQueueIndex(); ++i) {
            if (this.config.getQueueYML().isSet("queue." + i)) {
                final List<Object> obs = new ArrayList<Object>();
                final ConfigurationSection section = this.config.getQueueYML().getConfigurationSection("queue." + i);
                for (final String key : section.getKeys(false)) {
                    obs.add(section.get(key));
                }
                items.put(i, obs);
            }
        }
        return items;
    }
    
    public void removeQueueItem(final int id) {
        this.config.getQueueYML().set("queue." + id, (Object)null);
        this.config.saveQueueYML();
    }
    
    public boolean isItemId(final String search, final int typeId) {
        return search.equalsIgnoreCase(Integer.toString(typeId));
    }
    
    public boolean isInDisplayName(final String search, final ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().toLowerCase().contains(search);
    }
    
    public boolean isInEnchants(final String search, final ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
            for (final Map.Entry<Enchantment, Integer> entry : item.getItemMeta().getEnchants().entrySet()) {
                if (entry.getKey().getName().toLowerCase().contains(search)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isInLore(final String search, final ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            for (final String l : item.getItemMeta().getLore()) {
                if (l.toLowerCase().contains(search)) {
                    return true;
                }
            }
        }
        return false;
    }
}
