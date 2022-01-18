package me.shinkhan.Friends;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class main extends JavaPlugin implements Listener
{
    main plugin;
    
    public void onEnable() {
        this.plugin = this;
        this.getCommand("친구").setExecutor(new friendHandler(this));
        Bukkit.getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
		PluginDescriptionFile pdFile = this.getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
    }
    
	public void onDisable()
	{
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        final Player k = event.getPlayer();
        final Set<Player> a = (Set<Player>)event.getRecipients();
        final String name = k.getName();
        if (this.getConfig().getBoolean("Chat")) {
            event.setCancelled(true);
            for (final Player p : a) {
                if (this.getConfig().getConfigurationSection(String.valueOf(p.getName()) + ".Ignore") != null) {
                    if (this.getConfig().getConfigurationSection(String.valueOf(p.getName()) + ".Ignore").contains(name)) {
                        continue;
                    }
                    p.sendMessage("[" + k.getDisplayName() + "]: " + event.getMessage());
                }
                else {
                    p.sendMessage("[" + k.getDisplayName() + "]: " + event.getMessage());
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerClick(final InventoryClickEvent event) {
        final Player player = (Player)event.getWhoClicked();
        if (event.getInventory().getTitle().equalsIgnoreCase("Friends List")) {
            event.setResult(Event.Result.DENY);
            if (event.getCurrentItem() != null) {
                final ItemStack item = event.getCurrentItem();
                final ItemMeta im = item.getItemMeta();
                final String mname = im.getDisplayName();
                final SkullMeta sm = (SkullMeta)item.getItemMeta();
                final String oname = sm.getOwner();
                if (mname != null) {
                    if (event.isShiftClick()) {
                        player.performCommand(this.getConfig().getString("Commands.Shift Click.Command").replaceAll("@t", mname).replaceAll("@p", player.getName()));
                        event.setCancelled(true);
                        player.closeInventory();
                        return;
                    }
                    if (event.isLeftClick()) {
                        player.performCommand(this.getConfig().getString("Commands.Left Click.Command").replaceAll("@t", mname).replaceAll("@p", player.getName()));
                        event.setCancelled(true);
                        player.closeInventory();
                        return;
                    }
                    if (event.isRightClick()) {
                        player.performCommand(this.getConfig().getString("Commands.Right Click.Command").replaceAll("@t", mname).replaceAll("@p", player.getName()));
                        event.setCancelled(true);
                        player.closeInventory();
                        return;
                    }
                }
                if (event.isShiftClick()) {
                    player.performCommand(this.getConfig().getString("Commands.Shift Click.Command").replaceAll("@t", oname).replaceAll("@p", player.getName()));
                    event.setCancelled(true);
                    player.closeInventory();
                    return;
                }
                if (event.isLeftClick()) {
                    player.performCommand(this.getConfig().getString("Commands.Left Click.Command").replaceAll("@t", oname).replaceAll("@p", player.getName()));
                    event.setCancelled(true);
                    player.closeInventory();
                    return;
                }
                if (event.isRightClick()) {
                    player.performCommand(this.getConfig().getString("Commands.Right Click.Command").replaceAll("@t", oname).replaceAll("@p", player.getName()));
                    event.setCancelled(true);
                    player.closeInventory();
                }
            }
        }
    }
    
    public void friendAccept(final Player player, final Player p) {
        final Integer timer = this.getConfig().getInt("Timer");
        new BukkitRunnable(timer) {
            Integer count = count;
            
            public void run() {
                if (this.count == 0) {
                    player.sendMessage(String.valueOf(p.getName()) + " has declined your friend invite");
                    main.this.getConfig().set("Invited." + p.getName(), (Object)null);
                    main.this.saveConfig();
                    this.cancel();
                    return;
                }
                if (main.this.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends") != null && main.this.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends").contains(p.getName())) {
                    player.sendMessage(String.valueOf(p.getName()) + " has been added to your friends list!");
                    p.sendMessage(String.valueOf(player.getName()) + " has been added to your friends list!");
                    main.this.getConfig().set("Invited." + p.getName(), (Object)null);
                    main.this.saveConfig();
                    this.cancel();
                    return;
                }
                if (!main.this.getConfig().getConfigurationSection("Invited").contains(p.getName())) {
                    player.sendMessage(String.valueOf(p.getName()) + " has declined your friend invite");
                    this.cancel();
                    return;
                }
                --this.count;
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        event.setJoinMessage((String)null);
        if (this.getConfig().contains(player.getName())) {
            for (final String key : this.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends").getKeys(false)) {
                if (Bukkit.getPlayer(key) != null) {
                    final Player p = Bukkit.getPlayer(key);
                    p.sendMessage("짠6" + player.getName() + "짠3 has come online!");
                }
            }
        }
    }
}
