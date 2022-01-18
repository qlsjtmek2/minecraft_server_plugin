// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import org.bukkit.block.Block;
import net.milkbowl.vault.item.ItemInfo;
import org.bukkit.OfflinePlayer;
import java.util.HashSet;
import net.milkbowl.vault.item.Items;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Location;
import org.bukkit.event.block.Action;
import org.bukkit.block.Sign;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.util.Iterator;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import java.util.Map;
import com.survivorserver.GlobalMarket.tasks.CleanTask;
import com.survivorserver.GlobalMarket.tasks.ExpireTask;
import java.util.HashMap;
import org.bukkit.plugin.Plugin;
import java.util.List;
import net.milkbowl.vault.economy.Economy;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Market extends JavaPlugin implements Listener
{
    Logger log;
    ArrayList<Integer> tasks;
    static Market market;
    ConfigHandler config;
    MarketStorage storageHandler;
    MarketServer server;
    InterfaceHandler interfaceHandler;
    MarketCore core;
    InterfaceListener listener;
    Economy econ;
    LocaleHandler locale;
    String prefix;
    boolean bukkitItems;
    List<String> searching;
    MarketQueue queue;
    
    public Market() {
        this.bukkitItems = false;
    }
    
    public void onEnable() {
        this.log = this.getLogger();
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        this.tasks = new ArrayList<Integer>();
        (Market.market = this).reloadConfig();
        if (!this.getConfig().isSet("server.enable")) {
            this.getConfig().set("server.enable", (Object)false);
        }
        if (!this.getConfig().isSet("automatic_payments")) {
            this.getConfig().set("automatic_payments", (Object)false);
        }
        if (!this.getConfig().isSet("enable_cut")) {
            this.getConfig().set("enable_cut", (Object)true);
        }
        if (!this.getConfig().isSet("cut_amount")) {
            this.getConfig().set("cut_amount", (Object)0.05);
        }
        else if (this.getConfig().getDouble("cut_amount") >= 1.0) {
            this.getConfig().set("cut_amount", (Object)0.05);
        }
        if (!this.getConfig().isSet("enable_metrics")) {
            this.getConfig().set("enable_metrics", (Object)true);
        }
        if (!this.getConfig().isSet("max_price")) {
            this.getConfig().set("max_price", (Object)0.0);
        }
        if (!this.getConfig().isSet("creation_fee")) {
            this.getConfig().set("creation_fee", (Object)0.05);
        }
        if (!this.getConfig().isSet("queue.trade_time")) {
            this.getConfig().set("queue.trade_time", (Object)0);
        }
        if (!this.getConfig().isSet("queue.mail_time")) {
            this.getConfig().set("queue.mail_time", (Object)30);
        }
        if (!this.getConfig().isSet("queue.queue_on_buy")) {
            this.getConfig().set("queue.queue_mail_on_buy", (Object)true);
        }
        if (!this.getConfig().isSet("queue.queue_on_cancel")) {
            this.getConfig().set("queue.queue_mail_on_cancel", (Object)true);
        }
        if (!this.getConfig().isSet("max_listings_per_player")) {
            this.getConfig().set("max_listings_per_player", (Object)0);
        }
        if (!this.getConfig().isSet("expire_time")) {
            this.getConfig().set("expire_time", (Object)168);
        }
        if (!this.getConfig().isSet("blacklist.item_name")) {
            final List<String> blacklisted = new ArrayList<String>();
            blacklisted.add("Transaction Log");
            this.getConfig().set("blacklist.item_name", (Object)blacklisted);
        }
        if (!this.getConfig().isSet("blacklist.item_id")) {
            final Map<Integer, Integer> blacklisted2 = new HashMap<Integer, Integer>();
            blacklisted2.put(0, 0);
            this.getConfig().set("blacklist.item_id", (Object)blacklisted2);
        }
        if (!this.getConfig().isSet("blacklist.enchant_id")) {
            final List<String> blacklisted = new ArrayList<String>();
            this.getConfig().set("blacklist.enchant_id", (Object)blacklisted);
        }
        if (!this.getConfig().isSet("blacklist.lore")) {
            final List<String> blacklisted = new ArrayList<String>();
            this.getConfig().set("blacklist.lore", (Object)blacklisted);
        }
        if (!this.getConfig().isSet("blacklist.use_with_mail")) {
            this.getConfig().set("blacklist.use_with_mail", (Object)false);
        }
        this.saveConfig();
        final RegisteredServiceProvider<Economy> economyProvider = (RegisteredServiceProvider<Economy>)this.getServer().getServicesManager().getRegistration((Class)Economy.class);
        if (economyProvider != null) {
            this.econ = (Economy)economyProvider.getProvider();
            try {
                Class.forName("net.milkbowl.vault.item.Items");
                Class.forName("net.milkbowl.vault.item.ItemInfo");
            }
            catch (Exception e) {
                this.log.warning("You have an old or corrupt version of Vault that's missing the Vault Items API. Defaulting to Bukkit item names. Please consider updating Vault!");
                this.bukkitItems = true;
            }
            this.config = new ConfigHandler(this);
            this.locale = new LocaleHandler(this.config);
            this.prefix = this.locale.get("cmd.prefix");
            this.storageHandler = new MarketStorage(this.config, this);
            this.interfaceHandler = new InterfaceHandler(this, this.storageHandler);
            if (this.getConfig().getBoolean("server.enable")) {
                this.server = new MarketServer(this, this.storageHandler, this.interfaceHandler);
            }
            this.core = new MarketCore(this, this.interfaceHandler, this.storageHandler);
            this.listener = new InterfaceListener(this, this.interfaceHandler, this.storageHandler, this.core);
            this.queue = new MarketQueue(this, this.storageHandler);
            this.getServer().getPluginManager().registerEvents((Listener)this.listener, (Plugin)this);
            if (this.getExpireTime() > 0) {
                this.tasks.add(this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new ExpireTask(this, this.storageHandler, this.core), 0L, 72000L));
            }
            this.tasks.add(this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new CleanTask(this, this.interfaceHandler), 0L, 20L));
            if (this.getConfig().getBoolean("enable_metrics")) {
                try {
                    final MetricsLite metrics = new MetricsLite((Plugin)this);
                    metrics.start();
                }
                catch (Exception e) {
                    this.log.info("Failed to start Metrics!");
                }
            }
            this.searching = new ArrayList<String>();
            return;
        }
        this.log.severe("Vault has no hooked economy plugin, disabling");
        this.setEnabled(false);
    }
    
    public Economy getEcon() {
        return this.econ;
    }
    
    public static Market getMarket() {
        return Market.market;
    }
    
    public MarketCore getCore() {
        return this.core;
    }
    
    public MarketStorage getStorage() {
        return this.storageHandler;
    }
    
    public LocaleHandler getLocale() {
        return this.locale;
    }
    
    public boolean serverEnabled() {
        return this.server != null;
    }
    
    public MarketServer server() {
        return this.server;
    }
    
    public double getCut(final double amount) {
        if (amount < 10.0 || !this.getConfig().getBoolean("enable_cut")) {
            return 0.0;
        }
        return amount * this.getConfig().getDouble("cut_amount");
    }
    
    public double getCreationFee(final double amount) {
        final double fee = this.getConfig().getDouble("creation_fee");
        return amount * fee;
    }
    
    public boolean autoPayment() {
        return this.getConfig().getBoolean("automatic_payments");
    }
    
    public boolean cutTransactions() {
        return this.getConfig().getBoolean("enable_cut");
    }
    
    public boolean useBukkitNames() {
        try {
            Class.forName("net.milkbowl.vault.item.Items");
            Class.forName("net.milkbowl.vault.item.ItemInfo");
        }
        catch (Exception e) {
            return true;
        }
        return false;
    }
    
    public void addSearcher(final String name) {
        this.searching.add(name);
    }
    
    public double getMaxPrice() {
        return this.getConfig().getDouble("max_price");
    }
    
    public void startSearch(final Player player) {
        player.sendMessage(ChatColor.GREEN + this.getLocale().get("type_your_search"));
        final String name = player.getName();
        if (!this.searching.contains(name)) {
            this.addSearcher(name);
            this.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
                @Override
                public void run() {
                    if (Market.this.searching.contains(name)) {
                        Market.this.searching.remove(name);
                        final Player player = Market.market.getServer().getPlayer(name);
                        if (player != null) {
                            player.sendMessage(String.valueOf(Market.this.prefix) + Market.this.getLocale().get("search_cancelled"));
                        }
                    }
                }
            }, 200L);
        }
    }
    
    public MarketQueue getQueue() {
        return this.queue;
    }
    
    public int getTradeTime() {
        return this.getConfig().getInt("queue.trade_time");
    }
    
    public int getMailTime() {
        return this.getConfig().getInt("queue.mail_time");
    }
    
    public boolean queueOnBuy() {
        return this.getConfig().getBoolean("queue.queue_mail_on_buy");
    }
    
    public boolean queueOnCancel() {
        return this.getConfig().getBoolean("queue.queue_mail_on_cancel");
    }
    
    public int maxListings() {
        return this.getConfig().getInt("max_listings_per_player");
    }
    
    public int getExpireTime() {
        return this.getConfig().getInt("expire_time");
    }
    
    public boolean itemBlacklisted(final ItemStack item) {
        final Map<String, Object> blacklisted = (Map<String, Object>)this.getConfig().getConfigurationSection("blacklist.item_id").getValues(true);
        if (blacklisted.containsKey(Integer.toString(item.getTypeId()))) {
        	int damage = ((Integer)blacklisted.get(Integer.toString(item.getTypeId()))).intValue();
        	if ((damage == item.getDurability()) || (damage == -1)) {
        		return true;
        	}
        }
        if (item.hasItemMeta()) {
            final ItemMeta meta = item.getItemMeta();
            final List<String> bl = (List<String>)this.getConfig().getStringList("blacklist.item_name");
            if (meta.hasDisplayName()) {
                for (final String str : bl) {
                    if (meta.getDisplayName().equalsIgnoreCase(str)) {
                        return true;
                    }
                }
            }
            if (meta instanceof BookMeta && ((BookMeta)meta).hasTitle()) {
                for (final String str : bl) {
                    if (((BookMeta)meta).getTitle().equalsIgnoreCase(str)) {
                        return true;
                    }
                }
            }
            if (meta.hasEnchants()) {
                final List<Integer> ebl = (List<Integer>)this.getConfig().getIntegerList("blacklist.enchant_id");
                for (final Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                    if (ebl.contains(entry.getKey().getId())) {
                        return true;
                    }
                }
            }
            if (meta.hasLore()) {
                final List<String> lbl = (List<String>)this.getConfig().getStringList("blacklist.enchant_id");
                final List<String> lore = (List<String>)meta.getLore();
                for (final String str2 : lbl) {
                    if (lore.contains(str2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean blacklistMail() {
        return this.getConfig().getBoolean("blacklist.use_with_mail");
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (this.searching.contains(player.getName())) {
            event.setCancelled(true);
            final String search = event.getMessage();
            if (search.equalsIgnoreCase("cancel")) {
                this.searching.remove(player.getName());
                this.interfaceHandler.showListings(player, null);
            }
            else {
                this.interfaceHandler.showListings(player, search);
                this.searching.remove(player.getName());
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRightClick(final PlayerInteractEvent event) {
        if (!event.isCancelled() && event.getClickedBlock() != null && (event.getClickedBlock().getType() == Material.CHEST || event.getClickedBlock().getTypeId() == 146 || event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN)) {
            final Player player = event.getPlayer();
            final Location loc = event.getClickedBlock().getLocation();
            final int x = loc.getBlockX();
            final int y = loc.getBlockY();
            final int z = loc.getBlockZ();
            if (this.getConfig().isSet("mailbox." + x + "," + y + "," + z)) {
                event.setCancelled(true);
                this.interfaceHandler.showMail(player);
            }
            if (this.getConfig().isSet("stall." + x + "," + y + "," + z)) {
                event.setCancelled(true);
                if (event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN) {
                    final Sign sign = (Sign)event.getClickedBlock().getState();
                    final String line = sign.getLine(3);
                    if (line != null && line.length() > 0) {
                        this.interfaceHandler.showListings(player, line);
                        return;
                    }
                }
                if (event.getPlayer().isSneaking() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    this.startSearch(player);
                }
                else {
                    this.interfaceHandler.showListings(player, null);
                }
            }
        }
    }
    
    @EventHandler
    public void onLogin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (this.storageHandler.getNumMail(player.getName()) > 0) {
            player.sendMessage(String.valueOf(this.prefix) + this.locale.get("you_have_new_mail"));
        }
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("자유상점")) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("player_context_required"));
                return true;
            }
            if (args.length < 1 || args[0].equalsIgnoreCase("\ub3c4\uc6c0\ub9d0") || args[0].equalsIgnoreCase("?")) {
                sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("cmd.help_legend"));
                sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("cmd.listings_syntax") + " " + this.locale.get("cmd.listings_descr"));
                sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("cmd.create_syntax") + " " + this.locale.get("cmd.create_descr"));
                if (sender.hasPermission("market.quickmail")) {
                    sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("cmd.mail_syntax") + " " + this.locale.get("cmd.mail_descr"));
                }
                if (sender.hasPermission("market.util")) {
                    sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("cmd.mailbox_syntax") + " " + this.locale.get("cmd.mailbox_descr"));
                    sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("cmd.stall_syntax") + " " + this.locale.get("cmd.stall_descr"));
                }
                sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("cmd.history_syntax") + " " + this.locale.get("cmd.history_descr"));
                sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("cmd.send_syntax") + " " + this.locale.get("cmd.send_descr"));
                if (sender.hasPermission("market.admin")) {
                    sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("cmd.reload_syntax") + " " + this.locale.get("cmd.reload_descr"));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("\uba54\uc77c") && sender.hasPermission("globalmarket.quickmail")) {
                final Player player = (Player)sender;
                this.interfaceHandler.showMail(player);
                return true;
            }
            if (args[0].equalsIgnoreCase("\ubcf4\ub0b4\uae30")) {
                if (sender.hasPermission("globalmarket.send")) {
                    final Player player = (Player)sender;
                    if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR && args.length >= 2) {
                        if (this.blacklistMail() && this.itemBlacklisted(player.getItemInHand())) {
                            sender.sendMessage(ChatColor.RED + this.locale.get("item_is_blacklisted_from_mail"));
                            return true;
                        }
                        if (args.length < 2) {
                            sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("cmd.send_syntax"));
                            return true;
                        }
                        if (args[1].equalsIgnoreCase(player.getName())) {
                            sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("cant_mail_to_self"));
                            return true;
                        }
                        final OfflinePlayer off = this.getServer().getOfflinePlayer(args[1]);
                        if (!off.hasPlayedBefore()) {
                            sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("player_not_found", args[1]));
                            return true;
                        }
                        args[1] = off.getName();
                        if (args.length == 3) {
                            int amount = 0;
                            try {
                                amount = Integer.parseInt(args[2]);
                            }
                            catch (Exception e) {
                                player.sendMessage(ChatColor.RED + this.locale.get("not_a_valid_number", args[2]));
                                return true;
                            }
                            if (amount <= 0) {
                                player.sendMessage(ChatColor.RED + this.locale.get("not_a_valid_amount", args[2]));
                                return true;
                            }
                            if (player.getItemInHand().getAmount() < amount) {
                                player.sendMessage(ChatColor.RED + this.locale.get("you_dont_have_x_of_this_item", amount));
                                return true;
                            }
                            final ItemStack toList = new ItemStack(player.getItemInHand());
                            if (player.getItemInHand().getAmount() == amount) {
                                player.setItemInHand(new ItemStack(Material.AIR));
                            }
                            else {
                                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - amount);
                            }
                            toList.setAmount(amount);
                            if (this.getTradeTime() > 0 && !sender.hasPermission("globalmarket.noqueue")) {
                                this.queue.queueMail(toList, args[1]);
                                sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("item_will_send"));
                            }
                            else {
                                this.storageHandler.storeMail(toList, args[1], true);
                                sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("item_sent"));
                            }
                        }
                        else {
                            final ItemStack toList2 = new ItemStack(player.getItemInHand());
                            if (this.getTradeTime() > 0 && !sender.hasPermission("globalmarket.noqueue")) {
                                this.queue.queueMail(toList2, args[1]);
                                sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("item_will_send"));
                            }
                            else {
                                this.storageHandler.storeMail(toList2, args[1], true);
                                sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("item_sent"));
                            }
                            player.setItemInHand(new ItemStack(Material.AIR));
                        }
                    }
                    else {
                        sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("hold_an_item") + " " + this.locale.get("cmd.send_syntax"));
                    }
                    return true;
                }
                sender.sendMessage(ChatColor.YELLOW + this.locale.get("no_permission_for_this_command"));
                return true;
            }
            else if (args[0].equalsIgnoreCase("\ubaa9\ub85d")) {
                if (sender.hasPermission("globalmarket.quicklist")) {
                    final Player player = (Player)sender;
                    String search = null;
                    if (args.length >= 2) {
                        search = args[1];
                        if (args.length > 2) {
                            for (int i = 2; i < args.length; ++i) {
                                search = String.valueOf(search) + " " + args[i];
                            }
                        }
                    }
                    this.interfaceHandler.showListings(player, search);
                    return true;
                }
                sender.sendMessage(ChatColor.YELLOW + this.locale.get("no_permission_for_this_command"));
                return true;
            }
            else {
                if (args[0].equalsIgnoreCase("\uae30\ub85d")) {
                    final Player player = (Player)sender;
                    this.core.showHistory(player);
                    sender.sendMessage(ChatColor.GREEN + this.locale.get("check_your_inventory"));
                    return true;
                }
                if (args[0].equalsIgnoreCase("\ub9cc\ub4e4\uae30")) {
                    if (sender.hasPermission("globalmarket.create")) {
                        final Player player = (Player)sender;
                        if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR && args.length >= 2) {
                            if (this.itemBlacklisted(player.getItemInHand())) {
                                sender.sendMessage(ChatColor.RED + this.locale.get("item_is_blacklisted"));
                                return true;
                            }
                            double price = 0.0;
                            try {
                                price = Double.parseDouble(args[1]);
                            }
                            catch (Exception e) {
                                player.sendMessage(ChatColor.RED + this.locale.get("not_a_valid_number", args[1]));
                                return true;
                            }
                            if (price < 0.01) {
                                sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("price_too_low"));
                                return true;
                            }
                            final double maxPrice = this.getMaxPrice();
                            if (maxPrice > 0.0 && price > maxPrice) {
                                sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("price_too_high"));
                                return true;
                            }
                            final double fee = this.getCreationFee(price);
                            if (this.maxListings() > 0 && this.storageHandler.getNumListings(sender.getName()) >= this.maxListings() && !sender.hasPermission("globalmarket.nolimit")) {
                                sender.sendMessage(ChatColor.RED + this.locale.get("selling_too_many_items"));
                                return true;
                            }
                            if (args.length == 3) {
                                int amount2 = 0;
                                try {
                                    amount2 = Integer.parseInt(args[2]);
                                }
                                catch (Exception e2) {
                                    player.sendMessage(ChatColor.RED + this.locale.get("not_a_valid_number", args[2]));
                                    return true;
                                }
                                if (amount2 <= 0) {
                                    player.sendMessage(ChatColor.RED + this.locale.get("not_a_valid_amount", args[2]));
                                    return true;
                                }
                                if (player.getItemInHand().getAmount() < amount2) {
                                    player.sendMessage(ChatColor.RED + this.locale.get("you_dont_have_x_of_this_item", amount2));
                                    return true;
                                }
                                if (fee > 0.0) {
                                    if (!this.econ.has(sender.getName(), fee)) {
                                        sender.sendMessage(ChatColor.RED + this.locale.get("you_cant_pay_this_fee"));
                                        return true;
                                    }
                                    this.econ.withdrawPlayer(sender.getName(), fee);
                                    this.storageHandler.incrementSpent(sender.getName(), fee);
                                }
                                final ItemStack toList3 = new ItemStack(player.getItemInHand());
                                if (player.getItemInHand().getAmount() == amount2) {
                                    player.setItemInHand(new ItemStack(Material.AIR));
                                }
                                else {
                                    player.getItemInHand().setAmount(player.getItemInHand().getAmount() - amount2);
                                }
                                toList3.setAmount(amount2);
                                if (this.getTradeTime() > 0 && !sender.hasPermission("globalmarket.noqueue")) {
                                    this.queue.queueListing(toList3, player.getName(), price);
                                    sender.sendMessage(ChatColor.GREEN + this.locale.get("item_queued", this.getTradeTime()));
                                }
                                else {
                                    this.storageHandler.storeListing(toList3, player.getName(), price);
                                    sender.sendMessage(ChatColor.GREEN + this.locale.get("item_listed"));
                                }
                                if (fee > 0.0) {
                                    player.sendMessage(ChatColor.GREEN + this.locale.get("charged_fee", this.econ.format(fee)));
                                }
                                String itemName = toList3.getType().toString();
                                if (!this.useBukkitNames()) {
                                    final ItemInfo itemInfo = Items.itemById(toList3.getTypeId());
                                    if (itemInfo != null) {
                                        itemName = itemInfo.getName();
                                    }
                                }
                                this.storageHandler.storeHistory(player.getName(), this.locale.get("history.item_listed", String.valueOf(itemName) + "x" + toList3.getAmount(), price));
                            }
                            else {
                                if (fee > 0.0) {
                                    if (!this.econ.has(sender.getName(), fee)) {
                                        sender.sendMessage(ChatColor.RED + this.locale.get("you_cant_pay_this_fee"));
                                        return true;
                                    }
                                    this.econ.withdrawPlayer(sender.getName(), fee);
                                    this.storageHandler.incrementSpent(sender.getName(), fee);
                                }
                                final ItemStack toList4 = new ItemStack(player.getItemInHand());
                                if (this.getTradeTime() > 0 && !sender.hasPermission("globalmarket.noqueue")) {
                                    this.queue.queueListing(toList4, player.getName(), price);
                                    sender.sendMessage(ChatColor.GREEN + this.locale.get("item_queued", this.getTradeTime()));
                                }
                                else {
                                    this.storageHandler.storeListing(toList4, player.getName(), price);
                                    sender.sendMessage(ChatColor.GREEN + this.locale.get("item_listed"));
                                }
                                if (fee > 0.0) {
                                    player.sendMessage(ChatColor.GREEN + this.locale.get("charged_fee", this.econ.format(fee)));
                                }
                                player.setItemInHand(new ItemStack(Material.AIR));
                                String itemName2 = toList4.getType().toString();
                                if (!this.useBukkitNames()) {
                                    final ItemInfo itemInfo2 = Items.itemById(toList4.getTypeId());
                                    if (itemInfo2 != null) {
                                        itemName2 = itemInfo2.getName();
                                    }
                                }
                                this.storageHandler.storeHistory(player.getName(), this.locale.get("history.item_listed", String.valueOf(itemName2) + "x" + toList4.getAmount(), price));
                            }
                        }
                        else {
                            sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("hold_an_item") + " " + this.locale.get("cmd.create_syntax"));
                        }
                        return true;
                    }
                    sender.sendMessage(ChatColor.YELLOW + this.locale.get("no_permission_for_this_command"));
                    return true;
                }
                else {
                    if (args[0].equalsIgnoreCase("\ub9ac\ub85c\ub4dc") && sender.hasPermission("globalmarket.admin")) {
                        this.reloadConfig();
                        this.config.reloadLocaleYML();
                        sender.sendMessage(String.valueOf(this.prefix) + Market.market.getLocale().get("config_reloaded"));
                        return true;
                    }
                    if (sender.hasPermission("globalmarket.util") && (args[0].equalsIgnoreCase("\uba54\uc77c\ubc15\uc2a4") || args[0].equalsIgnoreCase("\uc9c4\uc5f4\ub300"))) {
                        final Player player = (Player)sender;
                        Location loc = null;
                        final Block block = player.getTargetBlock((HashSet)null, 4);
                        if (block.getType() != Material.CHEST && block.getTypeId() != 146 && block.getType() != Material.SIGN && block.getType() != Material.SIGN_POST && block.getType() != Material.WALL_SIGN) {
                            player.sendMessage(ChatColor.RED + this.locale.get("aim_cursor_at_chest_or_sign"));
                            return true;
                        }
                        loc = block.getLocation();
                        final int x = loc.getBlockX();
                        final int y = loc.getBlockY();
                        final int z = loc.getBlockZ();
                        if (args.length == 2 && args[1].equalsIgnoreCase("\uc0ad\uc81c")) {
                            if (this.getConfig().isSet("mailbox." + x + "," + y + "," + z)) {
                                this.getConfig().set("mailbox." + x + "," + y + "," + z, (Object)null);
                                this.saveConfig();
                                player.sendMessage(ChatColor.YELLOW + this.locale.get("mailbox_removed"));
                                return true;
                            }
                            if (this.getConfig().isSet("stall." + x + "," + y + "," + z)) {
                                this.getConfig().set("stall." + x + "," + y + "," + z, (Object)null);
                                this.saveConfig();
                                player.sendMessage(ChatColor.YELLOW + this.locale.get("stall_removed"));
                                return true;
                            }
                            player.sendMessage(ChatColor.RED + this.locale.get("no_stall_found"));
                            return true;
                        }
                        else {
                            if (this.getConfig().isSet("mailbox." + x + "," + y + "," + z)) {
                                sender.sendMessage(ChatColor.RED + this.locale.get("mailbox_already_exists"));
                                return true;
                            }
                            if (this.getConfig().isSet("stall." + x + "," + y + "," + z)) {
                                sender.sendMessage(ChatColor.RED + this.locale.get("stall_already_exists"));
                                return true;
                            }
                            if (args[0].equalsIgnoreCase("\uba54\uc77c\ubc15\uc2a4")) {
                                this.getConfig().set("mailbox." + x + "," + y + "," + z, (Object)true);
                                this.saveConfig();
                                sender.sendMessage(ChatColor.GREEN + this.locale.get("mailbox_added"));
                                return true;
                            }
                            if (args[0].equalsIgnoreCase("\uc9c4\uc5f4\ub300")) {
                                this.getConfig().set("stall." + x + "," + y + "," + z, (Object)true);
                                this.saveConfig();
                                sender.sendMessage(ChatColor.GREEN + this.locale.get("stall_added"));
                                return true;
                            }
                        }
                    }
                    sender.sendMessage(String.valueOf(this.prefix) + this.locale.get("cmd.help"));
                }
            }
        }
        return true;
    }
    
    public void onDisable() {
        this.interfaceHandler.closeAllInterfaces();
        if (this.getConfig().getBoolean("server.enable")) {
            this.server.setDisabled();
        }
        for (int i = 0; i < this.tasks.size(); ++i) {
            this.getServer().getScheduler().cancelTask((int)this.tasks.get(i));
        }
    }
}
