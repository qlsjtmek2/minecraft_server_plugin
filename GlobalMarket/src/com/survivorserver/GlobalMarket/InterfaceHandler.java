// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.inventory.Inventory;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class InterfaceHandler
{
    Market market;
    MarketStorage storage;
    List<InterfaceViewer> viewers;
    int maxListingsPerPage;
    UUID versionId;
    
    public InterfaceHandler(final Market market, final MarketStorage storage) {
        this.maxListingsPerPage = 45;
        this.market = market;
        this.storage = storage;
        this.viewers = new ArrayList<InterfaceViewer>();
        this.setVersionId();
    }
    
    public void setVersionId() {
        this.versionId = UUID.randomUUID();
    }
    
    public UUID getVersionId() {
        return this.versionId;
    }
    
    public InterfaceViewer addViewer(final String player, Inventory gui) {
        for (final InterfaceViewer viewer : this.viewers) {
            if (viewer.getViewer().equalsIgnoreCase(player)) {
                gui = null;
                return viewer;
            }
        }
        InterfaceViewer viewer = new InterfaceViewer(player, gui);
        this.viewers.add(viewer);
        return viewer;
    }
    
    public InterfaceViewer findViewer(final String player) {
        for (final InterfaceViewer viewer : this.viewers) {
            if (viewer.getViewer().equalsIgnoreCase(player)) {
                return viewer;
            }
        }
        return null;
    }
    
    public synchronized void removeViewer(final InterfaceViewer viewer) {
        this.viewers.remove(viewer);
    }
    
    public synchronized void openGui(final InterfaceViewer viewer) {
        this.market.getServer().getPlayer(viewer.getViewer()).openInventory(viewer.getGui());
    }
    
    public void prepareListings(final InterfaceViewer viewer) {
        final Map<Integer, Integer> boundSlots = new HashMap<Integer, Integer>();
        final Inventory gui = viewer.getGui();
        gui.clear();
        List<Listing> listings = this.storage.getAllListings();
        final ItemStack[] contents = new ItemStack[54];
        if (viewer.getSearch() != null) {
            listings = this.storage.getAllListings(viewer.getSearch());
        }
        this.setSearch(viewer.getSearch(), contents);
        int slot = 0;
        int p = 0;
        final int n = viewer.getPage() * this.maxListingsPerPage;
        for (final Listing listing : listings) {
            if (n > this.maxListingsPerPage && p < n - this.maxListingsPerPage) {
                ++p;
            }
            else {
                ++p;
                if (slot < this.maxListingsPerPage) {
                    boundSlots.put(slot, listing.getId());
                    final ItemStack item = listing.getItem();
                    if (item == null || item.getType() == Material.AIR) {
                        this.storage.removeListing(listing.getId());
                        this.market.log.warning("The item in listing " + listing.getId() + " is null, removing");
                        continue;
                    }
                    final ItemMeta meta = item.getItemMeta().clone();
                    List<String> lore = (List<String>)meta.getLore();
                    if (!meta.hasLore()) {
                        lore = new ArrayList<String>();
                    }
                    final String price = ChatColor.WHITE + this.market.getLocale().get("price") + this.market.getEcon().format(listing.getPrice());
                    final String seller = ChatColor.WHITE + this.market.getLocale().get("seller") + ChatColor.GRAY + ChatColor.ITALIC + listing.getSeller();
                    lore.add(price);
                    lore.add(seller);
                    if (!viewer.getViewer().equalsIgnoreCase(listing.seller)) {
                        String buyMsg = ChatColor.YELLOW + this.market.getLocale().get("click_to_buy");
                        if (viewer.getLastAction() != null && viewer.getLastAction() == InterfaceViewer.InterfaceAction.LEFTCLICK && viewer.getLastActionSlot() == slot) {
                            if (this.market.getEcon().has(viewer.getViewer(), listing.price)) {
                                buyMsg = ChatColor.GREEN + this.market.getLocale().get("click_again_to_confirm");
                            }
                            else {
                                buyMsg = ChatColor.RED + this.market.getLocale().get("not_enough_money", this.market.getEcon().currencyNamePlural());
                                viewer.setLastAction(InterfaceViewer.InterfaceAction.RIGHTCLICK);
                            }
                        }
                        lore.add(buyMsg);
                    }
                    if (viewer.getViewer().equalsIgnoreCase(listing.seller) || this.isAdmin(viewer.getViewer())) {
                        String removeMsg = ChatColor.DARK_GRAY + this.market.getLocale().get("shift_click_to_remove");
                        if (viewer.getLastAction() != null && viewer.getLastAction() == InterfaceViewer.InterfaceAction.SHIFTCLICK && viewer.getLastActionSlot() == slot) {
                            removeMsg = ChatColor.GREEN + this.market.getLocale().get("shift_click_again_to_confirm");
                        }
                        lore.add(removeMsg);
                    }
                    meta.setLore((List)lore);
                    item.setItemMeta(meta);
                    contents[slot] = item;
                }
                ++slot;
            }
        }
        this.setCurPage(contents, viewer);
        if (n < listings.size()) {
            this.setNextPage(contents, viewer);
        }
        if (n > this.maxListingsPerPage) {
            this.setPrevPage(contents, viewer);
        }
        viewer.setBoundSlots(boundSlots);
        gui.setContents(contents);
    }
    
    public void showListings(final Player player, final String search) {
        final InterfaceViewer viewer = this.addViewer(player.getName(), this.market.getServer().createInventory((InventoryHolder)player, 54, this.market.getLocale().get("interface.listings_title")));
        viewer.setViewType(InterfaceViewer.ViewType.LISTINGS);
        viewer.setSearch(search);
        this.prepareListings(viewer);
        this.openGui(viewer);
    }
    
    public void prepareMail(final InterfaceViewer viewer) {
        final Map<Integer, Integer> boundSlots = new HashMap<Integer, Integer>();
        final Inventory gui = viewer.getGui();
        gui.clear();
        final Map<Integer, ItemStack> mail = this.storage.getAllMailFor(viewer.getViewer());
        final ItemStack[] contents = new ItemStack[54];
        int slot = 0;
        int p = 0;
        final int n = viewer.getPage() * this.maxListingsPerPage;
        for (final Map.Entry<Integer, ItemStack> entry : mail.entrySet()) {
            if (n > this.maxListingsPerPage && p < n - this.maxListingsPerPage) {
                ++p;
            }
            else {
                ++p;
                if (slot < this.maxListingsPerPage) {
                    boundSlots.put(slot, entry.getKey());
                    if (entry.getValue() == null || entry.getValue().getType() == Material.AIR) {
                        this.storage.removeMail(viewer.getViewer(), entry.getKey());
                        this.market.log.warning("The item in " + viewer.getViewer() + "'s mail id " + entry.getKey() + " is null");
                        continue;
                    }
                    final ItemStack item = new ItemStack((ItemStack)entry.getValue());
                    final ItemMeta meta = item.getItemMeta().clone();
                    List<String> lore = (List<String>)meta.getLore();
                    if (!meta.hasLore()) {
                        lore = new ArrayList<String>();
                    }
                    if (meta instanceof BookMeta) {
                        final BookMeta bookMeta = (BookMeta)meta;
                        if (bookMeta.hasTitle() && bookMeta.getTitle().equalsIgnoreCase(this.market.getLocale().get("transaction_log.item_name"))) {
                            final double amount = this.storage.getPaymentAmount(entry.getKey(), viewer.getViewer());
                            if (amount > 0.0) {
                                lore.add(ChatColor.WHITE + this.market.getLocale().get("amount") + this.market.getEcon().format(amount));
                            }
                        }
                    }
                    String instructions = ChatColor.YELLOW + this.market.getLocale().get("click_to_retrieve");
                    if (viewer.getLastAction() != null && viewer.getLastAction() == InterfaceViewer.InterfaceAction.LEFTCLICK && viewer.getLastActionSlot() == slot) {
                        instructions = ChatColor.RED + this.market.getLocale().get("full_inventory");
                    }
                    lore.add(instructions);
                    meta.setLore((List)lore);
                    item.setItemMeta(meta);
                    contents[slot] = item;
                }
                ++slot;
            }
        }
        this.setCurPage(contents, viewer);
        if (n < this.storage.getNumMail(viewer.getViewer())) {
            this.setNextPage(contents, viewer);
        }
        if (n > this.maxListingsPerPage) {
            this.setPrevPage(contents, viewer);
        }
        viewer.setBoundSlots(boundSlots);
        gui.setContents(contents);
    }
    
    public void showMail(final Player player) {
        final InterfaceViewer viewer = this.addViewer(player.getName(), this.market.getServer().createInventory((InventoryHolder)player, 54, this.market.getLocale().get("interface.mail_title")));
        viewer.setViewType(InterfaceViewer.ViewType.MAIL);
        this.prepareMail(viewer);
        this.openGui(viewer);
    }
    
    public void setNextPage(final ItemStack[] contents, final InterfaceViewer viewer) {
        final ItemStack nextPage = new ItemStack(Material.PAPER, viewer.getPage() + 1);
        ItemMeta nextMeta = nextPage.getItemMeta();
        if (nextMeta == null) {
            nextMeta = this.market.getServer().getItemFactory().getItemMeta(nextPage.getType());
        }
        nextMeta.setDisplayName(ChatColor.WHITE + this.market.getLocale().get("interface.page", viewer.getPage() + 1));
        final List<String> nextLore = new ArrayList<String>();
        nextLore.add(ChatColor.YELLOW + this.market.getLocale().get("interface.next_page"));
        nextMeta.setLore((List)nextLore);
        nextPage.setItemMeta(nextMeta);
        contents[53] = nextPage;
    }
    
    public void setCurPage(final ItemStack[] contents, final InterfaceViewer viewer) {
        final ItemStack curPage = new ItemStack(Material.PAPER, viewer.getPage());
        ItemMeta curMeta = curPage.getItemMeta();
        if (curMeta == null) {
            curMeta = this.market.getServer().getItemFactory().getItemMeta(curPage.getType());
        }
        curMeta.setDisplayName(ChatColor.WHITE + this.market.getLocale().get("interface.page", viewer.getPage()));
        final List<String> curLore = new ArrayList<String>();
        curLore.add(ChatColor.YELLOW + this.market.getLocale().get("interface.cur_page"));
        curMeta.setLore((List)curLore);
        curPage.setItemMeta(curMeta);
        contents[49] = curPage;
    }
    
    public void setPrevPage(final ItemStack[] contents, final InterfaceViewer viewer) {
        final ItemStack prevPage = new ItemStack(Material.PAPER, viewer.getPage() - 1);
        ItemMeta prevMeta = prevPage.getItemMeta();
        if (prevMeta == null) {
            prevMeta = this.market.getServer().getItemFactory().getItemMeta(prevPage.getType());
        }
        prevMeta.setDisplayName(ChatColor.WHITE + this.market.getLocale().get("interface.page", viewer.getPage() - 1));
        final List<String> prevLore = new ArrayList<String>();
        prevLore.add(ChatColor.YELLOW + this.market.getLocale().get("interface.prev_page"));
        prevMeta.setLore((List)prevLore);
        prevPage.setItemMeta(prevMeta);
        contents[45] = prevPage;
    }
    
    public void setSearch(final String search, final ItemStack[] contents) {
        final ItemStack searchItem = new ItemStack(Material.PAPER);
        if (search == null) {
            ItemMeta meta = searchItem.getItemMeta();
            if (meta == null) {
                meta = this.market.getServer().getItemFactory().getItemMeta(searchItem.getType());
            }
            meta.setDisplayName(ChatColor.WHITE + this.market.getLocale().get("interface.search"));
            final List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.YELLOW + this.market.getLocale().get("interface.start_search"));
            meta.setLore((List)lore);
            searchItem.setItemMeta(meta);
            contents[47] = searchItem;
        }
        else {
            ItemMeta meta = searchItem.getItemMeta();
            if (meta == null) {
                meta = this.market.getServer().getItemFactory().getItemMeta(searchItem.getType());
            }
            meta.setDisplayName(ChatColor.WHITE + this.market.getLocale().get("interface.cancel_search"));
            final List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.YELLOW + this.market.getLocale().get("interface.searching_for", search));
            meta.setLore((List)lore);
            searchItem.setItemMeta(meta);
            contents[47] = searchItem;
        }
    }
    
    public void refreshViewer(final InterfaceViewer viewer) {
        if (viewer.getViewType() == InterfaceViewer.ViewType.MAIL) {
            this.prepareMail(viewer);
        }
        else {
            this.prepareListings(viewer);
        }
    }
    
    public void updateAllViewers() {
        final List<InterfaceViewer> inactive = new ArrayList<InterfaceViewer>();
        for (final InterfaceViewer viewer : this.viewers) {
            final Player player = this.market.getServer().getPlayer(viewer.getViewer());
            if (player == null) {
                inactive.add(viewer);
            }
            else if (player.getOpenInventory() == null) {
                inactive.add(viewer);
            }
            else if (viewer.getViewType() == InterfaceViewer.ViewType.MAIL) {
                this.prepareMail(viewer);
            }
            else {
                this.prepareListings(viewer);
            }
        }
        if (!inactive.isEmpty()) {
            for (final InterfaceViewer viewer : inactive) {
                this.removeViewer(viewer);
            }
        }
        this.setVersionId();
    }
    
    public void closeAllInterfaces() {
        for (final InterfaceViewer viewer : this.viewers) {
            final Player player = this.market.getServer().getPlayer(viewer.getViewer());
            if (player != null) {
                player.closeInventory();
                player.sendMessage(new StringBuilder().append(ChatColor.DARK_GRAY).append(ChatColor.ITALIC).append(this.market.getLocale().get("interface_closed_due_to_reload")).toString());
            }
        }
    }
    
    public List<InterfaceViewer> getAllViewers() {
        return this.viewers;
    }
    
    public boolean isAdmin(final String name) {
        final Player player = this.market.getServer().getPlayer(name);
        return player != null && player.hasPermission("globalmarket.admin");
    }
}
