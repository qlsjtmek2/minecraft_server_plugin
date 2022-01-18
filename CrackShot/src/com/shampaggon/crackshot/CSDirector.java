// 
// Decompiled by Procyon v0.5.29
// 

package com.shampaggon.crackshot;

import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Chest;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.SkullType;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Minecart;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.GameMode;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Zombie;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.entity.FallingBlock;
import org.bukkit.World;
import org.bukkit.util.BlockIterator;
import org.bukkit.block.BlockFace;
import java.util.ArrayList;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LargeFireball;
import org.bukkit.block.Skull;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.util.Vector;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Item;
import org.bukkit.entity.Painting;
import org.bukkit.entity.TNTPrimed;
import java.util.Random;
import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.ItemFrame;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CSDirector extends JavaPlugin implements Listener
{
    public HashMap<String, Integer> amplifier_storage;
    public HashMap<String, Integer> duration_storage;
    public Map<String, Collection<Integer>> burst_task_IDs;
    public Map<String, Collection<Integer>> global_reload_IDs;
    public Map<String, Boolean> morobust;
    public FileConfiguration weaponConfig;
    public Map<String, Double> dubs;
    public Map<String, String> rdelist;
    public Map<Integer, String> wlist;
    public Map<String, String> boobs;
    public Map<String, Boolean> bools;
    public Map<String, Integer> ints;
    public Map<String, String> strings;
    public Map<String, String> parents;
    public String[] disWorlds;
    public String heading;
    public String version;
    public final CSMinion csminion;
    
    public CSDirector() {
        this.amplifier_storage = new HashMap<String, Integer>();
        this.duration_storage = new HashMap<String, Integer>();
        this.burst_task_IDs = new HashMap<String, Collection<Integer>>();
        this.global_reload_IDs = new HashMap<String, Collection<Integer>>();
        this.morobust = new HashMap<String, Boolean>();
        this.weaponConfig = null;
        this.dubs = new HashMap<String, Double>();
        this.rdelist = new HashMap<String, String>();
        this.wlist = new HashMap<Integer, String>();
        this.boobs = new HashMap<String, String>();
        this.bools = new HashMap<String, Boolean>();
        this.ints = new HashMap<String, Integer>();
        this.strings = new HashMap<String, String>();
        this.parents = new HashMap<String, String>();
        this.disWorlds = new String[0];
        this.heading = "§7\u2591 §c[-§l¬§cº§lc§7§ls§7] §c- §7";
        this.version = "0.92.1";
        this.csminion = new CSMinion(this);
    }
    
    public void onEnable() {
        this.csminion.loadWeapons();
        this.csminion.loadGeneralConfig();
        this.csminion.customRecipes();
        this.printM("Gun-mode activated. Boop!");
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)this);
    }
    
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks((Plugin)this);
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player player = onlinePlayers[i];
            this.removeInertReloadTag(player, 0, true);
            this.unscopePlayer(player);
            this.terminateAllBursts(player);
            this.terminateReload(player);
        }
        this.amplifier_storage.clear();
        this.duration_storage.clear();
        this.burst_task_IDs.clear();
        this.global_reload_IDs.clear();
        this.bools.clear();
        this.ints.clear();
        this.strings.clear();
        this.parents.clear();
        this.morobust.clear();
        this.wlist.clear();
        this.rdelist.clear();
        this.boobs.clear();
        this.csminion.clearRecipes();
    }
    
    public void fillHashMaps(final FileConfiguration config) {
        for (final String string : config.getKeys(true)) {
            Object obj = config.get(string);
            if (obj instanceof Boolean) {
                this.bools.put(string, (Boolean)obj);
            }
            else if (obj instanceof Integer) {
                this.ints.put(string, (Integer)obj);
            }
            else {
                if (!(obj instanceof String)) {
                    continue;
                }
                obj = ((String)obj).replaceAll("&", "§");
                this.strings.put(string, (String)obj);
            }
        }
        for (final String parent_node : config.getKeys(false)) {
            final String[] specials = { ".Item_Information.Item_Type", ".Ammo.Ammo_Item_ID", ".Shooting.Projectile_Subtype", ".Crafting.Ingredients", ".Explosive_Devices.Device_Info", ".Airstrikes.Block_Type", ".Cluster_Bombs.Bomblet_Type", ".Shrapnel.Block_Type", ".Explosions.Damage_Multiplier" };
            String[] array;
            for (int length = (array = specials).length, i = 0; i < length; ++i) {
                final String spec = array[i];
                this.strings.put(String.valueOf(parent_node) + spec, config.getString(String.valueOf(parent_node) + spec));
            }
            final String[] spread = { ".Shooting.Bullet_Spread", ".Sneak.Bullet_Spread", ".Scope.Zoom_Bullet_Spread" };
            String[] array2;
            for (int length2 = (array2 = spread).length, j = 0; j < length2; ++j) {
                final String spre = array2[j];
                this.dubs.put(String.valueOf(parent_node) + spre, config.getDouble(String.valueOf(parent_node) + spre));
            }
            boolean accessory = false;
            final String attachType = this.getString(String.valueOf(parent_node) + ".Item_Information.Attachments.Type");
            if (attachType != null && attachType.equalsIgnoreCase("accessory")) {
                accessory = true;
            }
            if (!accessory) {
                final String it = config.getString(String.valueOf(parent_node) + ".Item_Information.Item_Type");
                final String itemName = config.getString(String.valueOf(parent_node) + ".Item_Information.Item_Has_Durability");
                if (it == null) {
                    this.printM("The weapon '" + parent_node + "' does not have a value for Item_Type.");
                }
                else if (itemName == null && this.csminion.durabilityCheck(it)) {
                    this.morobust.put(parent_node, true);
                }
            }
            String name = config.getString(String.valueOf(parent_node) + ".Item_Information.Item_Name");
            if (accessory) {
                name = "§eAccessory";
            }
            if (name == null) {
                this.printM("The weapon '" + parent_node + "' does not have a value for Item_Name.");
            }
            else {
                name = name.replaceAll("&", "§");
                final String colorCodes = ChatColor.getLastColors(name);
                if (colorCodes != null && colorCodes.length() > 1) {
                    name = String.valueOf(name) + "§" + colorCodes.substring(colorCodes.length() - 1);
                }
                this.strings.put(String.valueOf(parent_node) + ".Item_Information.Item_Name", name);
                this.parents.put(parent_node, name);
            }
            if (config.getBoolean(String.valueOf(parent_node) + ".Explosive_Devices.Enable")) {
                final String rdeOre = config.getString(String.valueOf(parent_node) + ".Explosive_Devices.Device_Info");
                if (rdeOre != null) {
                    final String[] rdeRefined = rdeOre.split("-");
                    if (rdeRefined.length == 3) {
                        this.rdelist.put(rdeRefined[1], parent_node);
                    }
                }
            }
            if (config.getBoolean(String.valueOf(parent_node) + ".Explosive_Devices.Enable")) {
                final String rdeInfo = config.getString(String.valueOf(parent_node) + ".Explosive_Devices.Device_Type");
                if (rdeInfo == null || !rdeInfo.toLowerCase().contains("trap")) {
                    continue;
                }
                this.boobs.put(this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Name"), parent_node);
            }
        }
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String aliasUsed, final String[] args) {
        if (!command.getName().equalsIgnoreCase("crackshot")) {
            return false;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("config") && args[1].equalsIgnoreCase("reload")) {
            if (sender instanceof Player && !sender.hasPermission("crackshot.reloadplugin")) {
                sender.sendMessage(String.valueOf(this.heading) + "You do not have permission to do this.");
                return true;
            }
            this.disWorlds = new String[0];
            this.csminion.clearRecipes();
            this.bools.clear();
            this.ints.clear();
            this.strings.clear();
            this.parents.clear();
            this.morobust.clear();
            this.wlist.clear();
            this.rdelist.clear();
            this.boobs.clear();
            this.csminion.loadWeapons();
            this.csminion.loadGeneralConfig();
            this.csminion.customRecipes();
            if (!(sender instanceof Player)) {
                sender.sendMessage("[CrackShot] Configuration reloaded.");
            }
            else {
                sender.sendMessage(String.valueOf(this.heading) + "Configuration reloaded.");
            }
            return true;
        }
        else if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("[CrackShot] Version: " + this.version);
                return true;
            }
            sender.sendMessage("§7\u2591 §c§l------§r§c[ -§l¬§cºcrack§7shot §c]§l------");
            sender.sendMessage("§7\u2591 §cAuthor: §7Shampaggon");
            sender.sendMessage("§7\u2591 §cVersion: §7" + this.version);
            sender.sendMessage("§7\u2591 §cAliases: §7/shot, /cra, /cs");
            sender.sendMessage("§7\u2591 §cCommands:");
            sender.sendMessage("§7\u2591 §c- §7/shot list [all §lOR§r§7 <page>]");
            sender.sendMessage("§7\u2591 §c- §7/shot give <user> <weapon> <#>");
            sender.sendMessage("§7\u2591 §c- §7/shot get <weapon> <#>");
            sender.sendMessage("§7\u2591 §c- §7/shot reload");
            sender.sendMessage("§7\u2591 §c- §7/shot config reload");
            return true;
        }
        else if ((args.length == 3 || args.length == 4) && args[0].equalsIgnoreCase("give")) {
            String prefix = this.heading;
            String amount = "1";
            if (!(sender instanceof Player)) {
                prefix = "[CrackShot] ";
            }
            if (args.length == 4) {
                amount = args[3];
            }
            final String parent_node = this.csminion.identifyWeapon(args[2]);
            if (parent_node == null) {
                sender.sendMessage(String.valueOf(prefix) + "No weapon matches '" + args[2] + "'.");
                return false;
            }
            if (sender instanceof Player && !sender.hasPermission("crackshot.give." + parent_node) && !sender.hasPermission("crackshot.give.all")) {
                sender.sendMessage(String.valueOf(prefix) + "You do not have permission to give this item.");
                return false;
            }
            final Player receiver = Bukkit.getServer().getPlayer(args[1]);
            if (receiver == null) {
                sender.sendMessage(String.valueOf(prefix) + "No player named '" + args[1] + "' was found.");
                return false;
            }
            if (receiver.getInventory().firstEmpty() != -1) {
                sender.sendMessage(String.valueOf(prefix) + "Package delivered to " + receiver.getName() + ".");
                this.csminion.getWeaponCommand(receiver, parent_node, false, amount, true, false);
                return true;
            }
            sender.sendMessage(String.valueOf(prefix) + receiver.getName() + "'s inventory is full.");
            return false;
        }
        else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("[CrackShot] Invalid command.");
                return false;
            }
            final Player player = (Player)sender;
            if ((args.length == 2 || args.length == 3) && args[0].equalsIgnoreCase("get")) {
                String amount = "1";
                if (args.length == 3) {
                    amount = args[2];
                }
                this.csminion.getWeaponCommand(player, args[1], true, amount, false, false);
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                final String parent_node2 = this.returnParentNode(player);
                if (parent_node2 == null) {
                    player.sendMessage(String.valueOf(this.heading) + "This weapon cannot be reloaded.");
                    return true;
                }
                if (!player.hasPermission("crackshot.use." + parent_node2) && !player.hasPermission("crackshot.use.all")) {
                    player.sendMessage(String.valueOf(this.heading) + "You do not have permission to use this weapon.");
                    return false;
                }
                this.reloadAnimation(player, parent_node2);
                return true;
            }
            else {
                if ((args.length != 1 && args.length != 2) || !args[0].equalsIgnoreCase("list")) {
                    player.sendMessage(String.valueOf(this.heading) + "Invalid command.");
                    return false;
                }
                if (!player.hasPermission("crackshot.list")) {
                    player.sendMessage(String.valueOf(this.heading) + "You do not have permission to do this.");
                    return false;
                }
                this.csminion.listWeapons(player, args);
                return true;
            }
        }
    }
    
    @EventHandler
    public void OnPlayerInteract(final PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) {
            final Player shooter = event.getPlayer();
            final ItemStack item = shooter.getItemInHand();
            this.csminion.convertOld(item);
            if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SKULL && event.getClickedBlock().hasMetadata("CS_transformers")) {
                event.setCancelled(true);
            }
            final String parent_node = this.returnParentNode(shooter);
            if (parent_node == null) {
                return;
            }
            if (!this.regionAndPermCheck(shooter, parent_node, false)) {
                return;
            }
            final boolean rdeEnable = this.getBoolean(String.valueOf(parent_node) + ".Explosive_Devices.Enable");
            final String attachType = this.getString(String.valueOf(parent_node) + ".Item_Information.Attachments.Type");
            if (attachType != null && attachType.equalsIgnoreCase("accessory") && rdeEnable) {
                shooter.sendMessage(String.valueOf(this.heading) + "The weapon '" + parent_node + "' is an attachment. It cannot use the Explosive_Devices module!");
                return;
            }
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                final boolean noBlockDmg = this.getBoolean(String.valueOf(parent_node) + ".Shooting.Cancel_Left_Click_Block_Damage");
                if (noBlockDmg) {
                    event.setCancelled(true);
                }
            }
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                final boolean rightInteract = this.getBoolean(String.valueOf(parent_node) + ".Shooting.Cancel_Right_Click_Interactions");
                if (rightInteract) {
                    event.setCancelled(true);
                }
            }
            final boolean rightShoot = this.getBoolean(String.valueOf(parent_node) + ".Shooting.Right_Click_To_Shoot");
            final boolean dualWield = this.getBoolean(String.valueOf(parent_node) + ".Shooting.Dual_Wield");
            final boolean leftClick = event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK;
            final boolean rightClick = event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK;
            if ((rightShoot && rightClick) || (!rightShoot && leftClick) || dualWield) {
                if (item.getType() != Material.BOW) {
                    this.csminion.weaponInteraction(shooter, parent_node, leftClick);
                }
                if (!rdeEnable) {
                    return;
                }
                final String type = this.getString(String.valueOf(parent_node) + ".Explosive_Devices.Device_Type");
                if (type != null && type.toLowerCase().contains("remote")) {
                    if (item.getItemMeta().hasLore()) {
                        boolean rdeFound = false;
                        final List<String> lore = (List<String>)item.getItemMeta().getLore();
                        final Iterator<String> it = lore.iterator();
                        while (it.hasNext()) {
                            String line = it.next();
                            if (line.contains(String.valueOf('\u1390'))) {
                                line = line.replace(" ", "");
                                final String[] itemInfo = line.split("]§r§e|\\\u1390|,");
                                this.csminion.detonateRDE(shooter, null, itemInfo, true);
                                it.remove();
                                rdeFound = true;
                            }
                        }
                        if (rdeFound) {
                            String capacity = "0";
                            final String[] refinedOre = this.csminion.returnRefinedOre(shooter, parent_node);
                            if (refinedOre != null) {
                                capacity = refinedOre[0];
                            }
                            this.playSoundEffects((Entity)shooter, parent_node, ".Explosive_Devices.Sounds_Alert_Placer", false);
                            if (!this.getBoolean(String.valueOf(parent_node) + ".Extras.One_Time_Use")) {
                                this.csminion.replaceBrackets(item, capacity, parent_node);
                            }
                            else if (item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().contains("«0»")) {
                                shooter.getInventory().setItemInHand((ItemStack)null);
                                shooter.updateInventory();
                                return;
                            }
                            final ItemMeta detmeta = item.getItemMeta();
                            detmeta.setLore((List)lore);
                            item.setItemMeta(detmeta);
                            shooter.getInventory().setItemInHand(item);
                        }
                    }
                }
                else if (type != null && type.toLowerCase().contains("trap") && this.itemIsSafe(item) && item.getItemMeta().getDisplayName().contains("«?»")) {
                    final String item_name = this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Name");
                    this.csminion.setItemName(shooter.getInventory().getItemInHand(), String.valueOf(item_name) + " «" + shooter.getName() + "»");
                    this.playSoundEffects((Entity)shooter, parent_node, ".Explosive_Devices.Sounds_Deploy", false);
                }
            }
            else if (!dualWield && ((rightShoot && leftClick) || (!rightShoot && rightClick))) {
                if (attachType != null && (attachType.equalsIgnoreCase("main") || attachType.equalsIgnoreCase("accessory"))) {
                    final int gunSlot = shooter.getInventory().getHeldItemSlot();
                    if (shooter.hasMetadata(String.valueOf(parent_node) + "shootDelay" + gunSlot + false) || shooter.hasMetadata(String.valueOf(parent_node) + "shootDelay" + gunSlot + true)) {
                        return;
                    }
                    final int toggleDelay = this.getInt(String.valueOf(parent_node) + ".Item_Information.Attachments.Toggle_Delay");
                    this.playSoundEffects((Entity)shooter, parent_node, ".Item_Information.Attachments.Sounds_Toggle", false);
                    this.noShootPeriod(shooter, parent_node, toggleDelay);
                    this.terminateAllBursts(shooter);
                    this.terminateReload(shooter);
                    this.removeInertReloadTag(shooter, 0, true);
                    if (this.itemIsSafe(item)) {
                        final String itemName = item.getItemMeta().getDisplayName();
                        final String triOne = String.valueOf('\u25b6');
                        final String triTwo = String.valueOf('\u25b7');
                        final String triThree = String.valueOf('\u25c0');
                        final String triFour = String.valueOf('\u25c1');
                        if (itemName.contains(triThree)) {
                            this.csminion.setItemName(item, itemName.replaceAll(String.valueOf(triThree) + triTwo, String.valueOf(triFour) + triOne));
                        }
                        else {
                            this.csminion.setItemName(item, itemName.replaceAll(String.valueOf(triFour) + triOne, String.valueOf(triThree) + triTwo));
                        }
                    }
                }
                else {
                    final boolean zoomEnable = this.getBoolean(String.valueOf(parent_node) + ".Scope.Enable");
                    final boolean nightScope = this.getBoolean(String.valueOf(parent_node) + ".Scope.Night_Vision");
                    if (!zoomEnable) {
                        return;
                    }
                    if (shooter.hasMetadata("mark_of_the_reload")) {
                        return;
                    }
                    final int zoomAmount = this.getInt(String.valueOf(parent_node) + ".Scope.Zoom_Amount");
                    if (zoomAmount < 0 || zoomAmount == 0 || zoomAmount > 10) {
                        return;
                    }
                    this.playSoundEffects((Entity)shooter, parent_node, ".Scope.Sounds_Toggle_Zoom", false);
                    if (shooter.hasPotionEffect(PotionEffectType.SPEED)) {
                        for (final PotionEffect pe : shooter.getActivePotionEffects()) {
                            if (pe.getType().toString().contains("SPEED")) {
                                if (shooter.hasMetadata("ironsights")) {
                                    this.unscopePlayer(shooter);
                                    break;
                                }
                                if (!shooter.hasPotionEffect(PotionEffectType.NIGHT_VISION) && nightScope) {
                                    shooter.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(6000, -1));
                                    shooter.setMetadata("night_scoping", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
                                }
                                shooter.setMetadata("ironsights", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
                                this.amplifier_storage.put(shooter.getName(), pe.getAmplifier());
                                this.duration_storage.put(shooter.getName(), pe.getDuration());
                                shooter.removePotionEffect(PotionEffectType.SPEED);
                                shooter.addPotionEffect(PotionEffectType.SPEED.createEffect(6000, -zoomAmount));
                                break;
                            }
                        }
                    }
                    else {
                        if (!shooter.hasPotionEffect(PotionEffectType.NIGHT_VISION) && nightScope) {
                            shooter.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(6000, -1));
                            shooter.setMetadata("night_scoping", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
                        }
                        shooter.setMetadata("ironsights", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
                        shooter.addPotionEffect(PotionEffectType.SPEED.createEffect(6000, -zoomAmount));
                    }
                }
            }
        }
        else if (event.getClickedBlock().getType() == Material.WOOD_PLATE || event.getClickedBlock().getType() == Material.STONE_PLATE) {
            final Player victim = event.getPlayer();
            final List<Entity> l = (List<Entity>)victim.getNearbyEntities(4.0, 4.0, 4.0);
            for (final Entity e : l) {
                if (e instanceof ItemFrame) {
                    this.csminion.boobyAction(event.getClickedBlock(), (Entity)victim, ((ItemFrame)e).getItem());
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(final EntityDamageByEntityEvent event) {
        final Entity entVictim = event.getEntity();
        final Entity entDmger = event.getDamager();
        if (entVictim instanceof Player && entVictim.hasMetadata("CS_singed")) {
            if (!event.isCancelled()) {
                entVictim.setMetadata("CS_singed", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
                event.setCancelled(true);
            }
            else {
                entVictim.removeMetadata("CS_singed", (Plugin)this);
            }
        }
        if (entVictim instanceof Player && entVictim.hasMetadata("deep_fr1ed")) {
            String parent_node = null;
            Player pPlayer = null;
            boolean nodam = false;
            final Player victim = (Player)entVictim;
            final int damage = victim.getMetadata("deep_fr1ed").get(0).asInt();
            victim.removeMetadata("deep_fr1ed", (Plugin)this);
            if (victim.hasMetadata("CS_nodam")) {
                nodam = true;
            }
            if (victim.hasMetadata("CS_potex") && victim.getMetadata("CS_potex") != null) {
                parent_node = victim.getMetadata("CS_potex").get(0).asString();
            }
            if (entDmger instanceof Player) {
                pPlayer = (Player)entDmger;
            }
            victim.removeMetadata("CS_potex", (Plugin)this);
            if (!event.isCancelled()) {
                this.csminion.explosionPackage((LivingEntity)victim, parent_node, pPlayer);
                if (!nodam) {
                    event.setDamage(damage);
                }
                else {
                    event.setCancelled(true);
                }
            }
        }
        if (entDmger instanceof Player && event.getDamage() == 8 && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            final List<Entity> witherNet = (List<Entity>)entVictim.getNearbyEntities(4.0, 4.0, 4.0);
            for (final Entity closeEnt : witherNet) {
                if (closeEnt instanceof WitherSkull && ((Projectile)closeEnt).getShooter() == entDmger) {
                    event.setCancelled(true);
                }
            }
        }
        if (entDmger instanceof Arrow || entDmger instanceof Egg || entDmger instanceof Snowball) {
            for (final String parent_node : this.parents.keySet()) {
                if (entDmger.hasMetadata(parent_node) && entVictim instanceof LivingEntity) {
                    if (event.isCancelled()) {
                        break;
                    }
                    final Projectile objProj = (Projectile)entDmger;
                    final LivingEntity victim2 = (LivingEntity)entVictim;
                    final Player shooter = (Player)objProj.getShooter();
                    final int projDmg = this.getInt(String.valueOf(parent_node) + ".Shooting.Projectile_Damage");
                    final int hsBonus = this.getInt(String.valueOf(parent_node) + ".Headshot.Bonus_Damage");
                    final boolean hitMsgOn = this.getBoolean(String.valueOf(parent_node) + ".Hit_Events.Enable");
                    final boolean headShots = this.getBoolean(String.valueOf(parent_node) + ".Headshot.Enable");
                    final boolean fireEnable = this.getBoolean(String.valueOf(parent_node) + ".Shooting.Projectile_Incendiary.Enable");
                    final int fireDuration = this.getInt(String.valueOf(parent_node) + ".Shooting.Projectile_Incendiary.Duration");
                    final boolean critEnable = this.getBoolean(String.valueOf(parent_node) + ".Critical_Hits.Enable");
                    final int critBonus = this.getInt(String.valueOf(parent_node) + ".Critical_Hits.Bonus_Damage");
                    final int critChance = this.getInt(String.valueOf(parent_node) + ".Critical_Hits.Chance");
                    final boolean impactExplode = this.getBoolean(String.valueOf(parent_node) + ".Explosions.On_Impact_With_Anything");
                    final boolean zapEnable = this.getBoolean(String.valueOf(parent_node) + ".Lightning.Enable");
                    final boolean zapNoDmg = this.getBoolean(String.valueOf(parent_node) + ".Lightning.No_Damage");
                    final boolean zapImpact = this.getBoolean(String.valueOf(parent_node) + ".Lightning.On_Impact_With_Anything");
                    final boolean resetHits = this.getBoolean(String.valueOf(parent_node) + ".Abilities.Reset_Hit_Cooldown");
                    final boolean flightEnable = this.getBoolean(String.valueOf(parent_node) + ".Damage_Based_On_Flight_Time.Enable");
                    final int dmgPerTick = this.getInt(String.valueOf(parent_node) + ".Damage_Based_On_Flight_Time.Bonus_Damage_Per_Tick");
                    final int flightMax = this.getInt(String.valueOf(parent_node) + ".Damage_Based_On_Flight_Time.Maximum_Damage");
                    final int flightMin = this.getInt(String.valueOf(parent_node) + ".Damage_Based_On_Flight_Time.Minimum_Damage");
                    final String makeSpeak = this.getString(String.valueOf(parent_node) + ".Extras.Make_Victim_Speak");
                    final String makeRunCmd = this.getString(String.valueOf(parent_node) + ".Extras.Make_Victim_Run_Commmand");
                    final String runConsole = this.getString(String.valueOf(parent_node) + ".Extras.Run_Console_Command");
                    final boolean bsEnable = this.getBoolean(String.valueOf(parent_node) + ".Backstab.Enable");
                    final int bsBonusDmg = this.getInt(String.valueOf(parent_node) + ".Backstab.Bonus_Damage");
                    final int knockBack = this.getInt(String.valueOf(parent_node) + ".Abilities.Knockback");
                    final String bonusDrops = this.getString(String.valueOf(parent_node) + ".Abilities.Bonus_Drops");
                    final int activTime = this.getInt(String.valueOf(parent_node) + ".Explosions.Projectile_Activation_Time");
                    int projFlight = 0;
                    int projTotalDmg = projDmg;
                    boolean BS = false;
                    boolean crit = false;
                    boolean boomHS = false;
                    if (knockBack != 0) {
                        victim2.setVelocity(shooter.getLocation().getDirection().multiply(knockBack));
                    }
                    if (flightEnable) {
                        projFlight = objProj.getTicksLived();
                        int damage_per_tick_final = dmgPerTick;
                        damage_per_tick_final = projFlight * dmgPerTick;
                        if (damage_per_tick_final < flightMin && flightMin != 0) {
                            damage_per_tick_final = 0;
                        }
                        if (damage_per_tick_final > flightMax && flightMax != 0) {
                            damage_per_tick_final = flightMax;
                        }
                        projTotalDmg += damage_per_tick_final;
                    }
                    if (resetHits) {
                        victim2.setNoDamageTicks(0);
                    }
                    if (!impactExplode && objProj.getTicksLived() >= activTime) {
                        this.projectileExplosion((Entity)victim2, parent_node, false, shooter, false, false, null, null, false, 0);
                    }
                    if (zapEnable && !zapImpact) {
                        if (zapNoDmg) {
                            objProj.getWorld().strikeLightningEffect(objProj.getLocation());
                        }
                        else {
                            objProj.getWorld().strikeLightning(objProj.getLocation());
                        }
                    }
                    if (bsEnable) {
                        final double faceAngle = victim2.getLocation().getDirection().dot(shooter.getLocation().getDirection());
                        if (faceAngle > 0.0) {
                            BS = true;
                            projTotalDmg += bsBonusDmg;
                            this.playSoundEffects((Entity)shooter, parent_node, ".Backstab.Sounds_Shooter", false);
                            this.playSoundEffects((Entity)victim2, parent_node, ".Backstab.Sounds_Victim", false);
                            this.csminion.giveParticleEffects((Entity)victim2, parent_node, ".Particles.Particle_Backstab", false);
                            this.csminion.displayFireworks((Entity)victim2, parent_node, ".Fireworks.Firework_Backstab");
                            this.csminion.givePotionEffects((LivingEntity)shooter, parent_node, ".Potion_Effects.Potion_Effect_Shooter", "back");
                            this.csminion.givePotionEffects(victim2, parent_node, ".Potion_Effects.Potion_Effect_Victim", "back");
                        }
                    }
                    final Random ranGen = new Random();
                    final int Chance = ranGen.nextInt(100);
                    if (critEnable && Chance <= critChance) {
                        crit = true;
                        projTotalDmg += critBonus;
                        this.playSoundEffects((Entity)shooter, parent_node, ".Critical_Hits.Sounds_Shooter", false);
                        this.playSoundEffects((Entity)victim2, parent_node, ".Critical_Hits.Sounds_Victim", false);
                        this.csminion.giveParticleEffects((Entity)victim2, parent_node, ".Particles.Particle_Critical", false);
                        this.csminion.displayFireworks((Entity)victim2, parent_node, ".Fireworks.Firework_Critical");
                        this.csminion.givePotionEffects((LivingEntity)shooter, parent_node, ".Potion_Effects.Potion_Effect_Shooter", "crit");
                        this.csminion.givePotionEffects(victim2, parent_node, ".Potion_Effects.Potion_Effect_Victim", "crit");
                    }
                    if (fireEnable && fireDuration != 0) {
                        victim2.setFireTicks(fireDuration);
                    }
                    if (headShots && this.csminion.getHeadshot(objProj, (Entity)victim2)) {
                        boomHS = true;
                        projTotalDmg += hsBonus;
                        this.playSoundEffects((Entity)shooter, parent_node, ".Headshot.Sounds_Shooter", false);
                        this.playSoundEffects((Entity)victim2, parent_node, ".Headshot.Sounds_Victim", false);
                        this.csminion.giveParticleEffects((Entity)victim2, parent_node, ".Particles.Particle_Headshot", false);
                        this.csminion.displayFireworks((Entity)victim2, parent_node, ".Fireworks.Firework_Headshot");
                        this.csminion.givePotionEffects((LivingEntity)shooter, parent_node, ".Potion_Effects.Potion_Effect_Shooter", "head");
                        this.csminion.givePotionEffects(victim2, parent_node, ".Potion_Effects.Potion_Effect_Victim", "head");
                    }
                    if (projTotalDmg < 0) {
                        projTotalDmg = 0;
                    }
                    event.setDamage(projTotalDmg);
                    final String flyTime = String.valueOf(projFlight);
                    final String dmgTotal = String.valueOf(projTotalDmg);
                    String nameShooter = "Player";
                    String nameVic = "Entity";
                    if (shooter instanceof Player) {
                        nameShooter = shooter.getName();
                    }
                    if (victim2 instanceof Player) {
                        nameVic = ((Player)victim2).getName();
                    }
                    if (boomHS) {
                        this.sendPlayerMessage((LivingEntity)shooter, parent_node, ".Headshot.Message_Shooter", true, nameShooter, nameVic, flyTime, dmgTotal);
                        this.sendPlayerMessage(victim2, parent_node, ".Headshot.Message_Victim", true, nameShooter, nameVic, flyTime, dmgTotal);
                    }
                    if (crit) {
                        this.sendPlayerMessage((LivingEntity)shooter, parent_node, ".Critical_Hits.Message_Shooter", true, nameShooter, nameVic, flyTime, dmgTotal);
                        this.sendPlayerMessage(victim2, parent_node, ".Critical_Hits.Message_Victim", true, nameShooter, nameVic, flyTime, dmgTotal);
                    }
                    if (BS) {
                        this.sendPlayerMessage((LivingEntity)shooter, parent_node, ".Backstab.Message_Shooter", true, nameShooter, nameVic, flyTime, dmgTotal);
                        this.sendPlayerMessage(victim2, parent_node, ".Backstab.Message_Victim", true, nameShooter, nameVic, flyTime, dmgTotal);
                    }
                    if (this.spawnEntities(victim2, parent_node, ".Spawn_Entity_On_Hit.EntityType_Baby_Explode_Amount", (LivingEntity)shooter)) {
                        this.sendPlayerMessage((LivingEntity)shooter, parent_node, ".Spawn_Entity_On_Hit.Message_Shooter", true, nameShooter, nameVic, flyTime, dmgTotal);
                        this.sendPlayerMessage(victim2, parent_node, ".Spawn_Entity_On_Hit.Message_Victim", true, nameShooter, nameVic, flyTime, dmgTotal);
                    }
                    this.sendPlayerMessage((LivingEntity)shooter, parent_node, ".Hit_Events.Message_Shooter", hitMsgOn, nameShooter, nameVic, flyTime, dmgTotal);
                    this.sendPlayerMessage(victim2, parent_node, ".Hit_Events.Message_Victim", hitMsgOn, nameShooter, nameVic, flyTime, dmgTotal);
                    this.playSoundEffects((Entity)shooter, parent_node, ".Hit_Events.Sounds_Shooter", false);
                    this.playSoundEffects((Entity)victim2, parent_node, ".Hit_Events.Sounds_Victim", false);
                    if (victim2 instanceof Player) {
                        if (makeSpeak != null) {
                            ((Player)victim2).chat(this.variableParser(makeSpeak, nameShooter, nameVic, flyTime, dmgTotal));
                        }
                        if (makeRunCmd != null) {
                            this.executeCommands(victim2, parent_node, ".Extras.Make_Victim_Run_Commmand", nameShooter, nameVic, flyTime, dmgTotal, false);
                        }
                    }
                    if (runConsole != null) {
                        this.executeCommands((LivingEntity)shooter, parent_node, ".Extras.Run_Console_Command", nameShooter, nameVic, flyTime, dmgTotal, true);
                    }
                    this.csminion.giveParticleEffects((Entity)victim2, parent_node, ".Particles.Particle_Hit", false);
                    this.csminion.displayFireworks((Entity)victim2, parent_node, ".Fireworks.Firework_Hit");
                    this.csminion.givePotionEffects((LivingEntity)shooter, parent_node, ".Potion_Effects.Potion_Effect_Shooter", "hit");
                    this.csminion.givePotionEffects(victim2, parent_node, ".Potion_Effects.Potion_Effect_Victim", "hit");
                    if (!(victim2 instanceof Player) && victim2.getHealth() <= 0 && bonusDrops != null) {
                        final String[] args = bonusDrops.split(",");
                        String[] array;
                        for (int length = (array = args).length, i = 0; i < length; ++i) {
                            final String arg = array[i];
                            try {
                                shooter.getWorld().dropItemNaturally(victim2.getLocation(), new ItemStack(Material.getMaterial((int)Integer.valueOf(arg))));
                            }
                            catch (IllegalArgumentException ex) {
                                this.printM("'" + arg + "' of weapon '" + parent_node + "' for 'Bonus_Drops' is not a valid item ID!");
                                break;
                            }
                        }
                        break;
                    }
                    break;
                }
            }
        }
        if (entDmger instanceof TNTPrimed && entDmger.hasMetadata("CS_Label")) {
            if (entVictim instanceof LivingEntity) {
                ((LivingEntity)entVictim).setNoDamageTicks(0);
            }
            if (entDmger.hasMetadata("nullify") && (entVictim instanceof Painting || entVictim instanceof ItemFrame || entVictim instanceof Item)) {
                event.setCancelled(true);
            }
            if (entDmger.hasMetadata("CS_nodam") || entVictim.hasMetadata("CS_shrapnel")) {
                if (entVictim instanceof Player) {
                    entVictim.setMetadata("CS_nodam", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
                    this.csminion.tempVars((Player)entVictim, "CS_nodam", 2L);
                }
                event.setCancelled(true);
            }
            String parent_node = null;
            if (entDmger.hasMetadata("CS_potex")) {
                final String dormant = entDmger.getMetadata("CS_potex").get(0).asString();
                if (event.getDamage() > 1) {
                    parent_node = dormant;
                }
                if (dormant != null) {
                    try {
                        final String multiString = this.getString(String.valueOf(dormant) + ".Explosions.Damage_Multiplier");
                        if (multiString != null) {
                            final double multip = Integer.valueOf(multiString) * 0.01;
                            event.setDamage((int)(event.getDamage() * multip));
                        }
                    }
                    catch (IllegalArgumentException ex3) {}
                }
            }
            final int knockBack2 = this.getInt(String.valueOf(parent_node) + ".Explosions.Knockback");
            if (knockBack2 != 0) {
                final Vector vector = this.csminion.getAlignedDirection(entDmger.getLocation(), entVictim.getLocation());
                if (!entVictim.hasMetadata("CS_shrapnel")) {
                    entVictim.setVelocity(vector.multiply(knockBack2 * 0.1));
                }
            }
            Player pPlayer2 = null;
            String pName;
            try {
                pName = entDmger.getMetadata("CS_pName").get(0).asString();
                pPlayer2 = Bukkit.getServer().getPlayer(pName);
            }
            catch (IndexOutOfBoundsException ex2) {
                pName = "-";
            }
            if (entVictim instanceof Player) {
                final Player victim3 = (Player)entVictim;
                if (entDmger.hasMetadata("0wner_nodam") && victim3.getName().equals(pName)) {
                    event.setCancelled(true);
                    return;
                }
                if (entDmger.hasMetadata("CS_ffcheck")) {
                    if (victim3.getName().equals(pName)) {
                        this.csminion.explosionPackage((LivingEntity)victim3, parent_node, pPlayer2);
                    }
                    else if (pPlayer2 != null) {
                        victim3.setMetadata("deep_fr1ed", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)event.getDamage()));
                        if (parent_node != null) {
                            victim3.setMetadata("CS_potex", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)parent_node));
                        }
                        this.csminion.illegalSlap(pPlayer2, victim3);
                        event.setCancelled(true);
                    }
                }
                else {
                    this.csminion.explosionPackage((LivingEntity)victim3, parent_node, pPlayer2);
                }
            }
            else if (entVictim instanceof LivingEntity) {
                this.csminion.explosionPackage((LivingEntity)entVictim, parent_node, pPlayer2);
            }
        }
        if (entVictim instanceof Player) {
            final Player blocker = (Player)entVictim;
            if (!blocker.isBlocking()) {
                return;
            }
            final String parent_node2 = this.returnParentNode(blocker);
            if (parent_node2 == null) {
                return;
            }
            int durabPerHit = this.getInt(String.valueOf(parent_node2) + ".Riot_Shield.Durability_Loss_Per_Hit");
            final boolean riotEnable = this.getBoolean(String.valueOf(parent_node2) + ".Riot_Shield.Enable");
            final boolean durabDmg = this.getBoolean(String.valueOf(parent_node2) + ".Riot_Shield.Durablity_Based_On_Damage");
            final boolean noProj = this.getBoolean(String.valueOf(parent_node2) + ".Riot_Shield.Do_Not_Block_Projectiles");
            final boolean noMelee = this.getBoolean(String.valueOf(parent_node2) + ".Riot_Shield.Do_Not_Block_Melee_Attacks");
            final boolean forceField = this.getBoolean(String.valueOf(parent_node2) + ".Riot_Shield.Forcefield_Mode");
            if (!riotEnable) {
                return;
            }
            if (!this.regionAndPermCheck(blocker, parent_node2, false)) {
                return;
            }
            if (entDmger instanceof Projectile) {
                if (noProj) {
                    return;
                }
                if (!forceField) {
                    final Projectile objProj2 = (Projectile)entDmger;
                    final double face_angle = blocker.getLocation().getDirection().dot(objProj2.getShooter().getLocation().getDirection());
                    if (face_angle > 0.0 && !(objProj2.getShooter() instanceof Skeleton)) {
                        return;
                    }
                }
            }
            else {
                if (noMelee) {
                    return;
                }
                if (!forceField) {
                    final double faceAngle2 = blocker.getLocation().getDirection().dot(entDmger.getLocation().getDirection());
                    if (faceAngle2 > 0.0) {
                        return;
                    }
                }
            }
            if (durabDmg) {
                durabPerHit *= event.getDamage();
            }
            final ItemStack shield = blocker.getInventory().getItemInHand();
            shield.setDurability((short)(shield.getDurability() + durabPerHit));
            this.playSoundEffects((Entity)blocker, parent_node2, ".Riot_Shield.Sounds_Blocked", false);
            if (shield.getType().getMaxDurability() <= shield.getDurability()) {
                this.playSoundEffects((Entity)blocker, parent_node2, ".Riot_Shield.Sounds_Break", false);
                blocker.getInventory().clear(blocker.getInventory().getHeldItemSlot());
                blocker.updateInventory();
            }
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onProjectileHit(final ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow || event.getEntity() instanceof Egg || event.getEntity() instanceof Snowball) {
            for (final String parent_node : this.parents.keySet()) {
                if (event.getEntity().hasMetadata(parent_node) && event.getEntity().getShooter() instanceof Player) {
                    final Player shooter = (Player)event.getEntity().getShooter();
                    final Projectile objProj = event.getEntity();
                    objProj.removeMetadata(parent_node, (Plugin)this);
                    final boolean airstrike = this.getBoolean(String.valueOf(parent_node) + ".Airstrikes.Enable");
                    final boolean zapEnable = this.getBoolean(String.valueOf(parent_node) + ".Lightning.Enable");
                    final boolean zapNoDam = this.getBoolean(String.valueOf(parent_node) + ".Lightning.No_Damage");
                    final boolean zapImpact = this.getBoolean(String.valueOf(parent_node) + ".Lightning.On_Impact_With_Anything");
                    final boolean arrowImpact = this.getBoolean(String.valueOf(parent_node) + ".Shooting.Remove_Arrows_On_Impact");
                    final boolean explodeImpact = this.getBoolean(String.valueOf(parent_node) + ".Explosions.On_Impact_With_Anything");
                    final int actTime = this.getInt(String.valueOf(parent_node) + ".Explosions.Projectile_Activation_Time");
                    this.csminion.giveParticleEffects(event.getEntity().getLocation(), parent_node, ".Airstrikes.Particle_Call_Airstrike");
                    if (airstrike) {
                        this.csminion.callAirstrike((Entity)event.getEntity(), parent_node, shooter);
                    }
                    this.playSoundEffects((Entity)objProj, parent_node, ".Hit_Events.Sounds_Impact", false);
                    this.csminion.giveParticleEffects((Entity)objProj, parent_node, ".Particles.Particle_Impact_Anything", false);
                    if (arrowImpact && objProj.getType() == EntityType.ARROW) {
                        objProj.remove();
                    }
                    if (zapEnable && zapImpact) {
                        this.csminion.projectileLightning(objProj, zapNoDam);
                    }
                    if (explodeImpact && objProj.getTicksLived() >= actTime) {
                        this.projectileExplosion((Entity)objProj, parent_node, false, shooter, false, false, null, null, false, 0);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    @EventHandler
    public void onEntityExplode(final EntityExplodeEvent event) {
        final Entity boomer = event.getEntity();
        if (boomer instanceof TNTPrimed) {
            if (boomer.hasMetadata("CS_potex")) {
                final String parent_node = boomer.getMetadata("CS_potex").get(0).asString();
                this.playSoundEffects(boomer, parent_node, ".Explosions.Sounds_Explode", false);
            }
            if (boomer.hasMetadata("nullify") && event.blockList() != null) {
                event.blockList().clear();
            }
            if (boomer.getLocation().getBlock().getType() == Material.SKULL && !boomer.hasMetadata("C4_Friendly")) {
                final BlockState state = boomer.getLocation().getBlock().getState();
                if (state instanceof Skull) {
                    try {
                        final Skull skull = (Skull)state;
                        if (skull.getOwner().contains("\u060c")) {
                            boomer.getLocation().getBlock().removeMetadata("CS_transformers", (Plugin)this);
                            boomer.getLocation().getBlock().setType(Material.AIR);
                        }
                    }
                    catch (ClassCastException ex) {}
                }
            }
        }
        else if (boomer instanceof WitherSkull || boomer instanceof LargeFireball) {
            for (final String parent_node : this.parents.keySet()) {
                if (boomer.hasMetadata(parent_node) && ((Projectile)boomer).getShooter() instanceof Player) {
                    final Player shooter = (Player)((Projectile)boomer).getShooter();
                    if (boomer.getTicksLived() >= this.getInt(String.valueOf(parent_node) + ".Explosions.Projectile_Activation_Time")) {
                        this.projectileExplosion(boomer, parent_node, false, shooter, false, false, null, null, false, 0);
                    }
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }
    
    public void playSoundEffects(final Entity player, final String parent_node, final String child_node, final boolean reload) {
        if (this.getString(String.valueOf(parent_node) + child_node) == null) {
            return;
        }
        final String[] sound_effects = this.getString(String.valueOf(parent_node) + child_node).split(",");
        if (sound_effects != null) {
            String[] array;
            for (int length = (array = sound_effects).length, i = 0; i < length; ++i) {
                final String sound_effect = array[i];
                final String space_filtered = sound_effect.replace(" ", "");
                final String[] args = space_filtered.split("-");
                if (args.length == 4) {
                    final int task_ID = Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            try {
                                player.getWorld().playSound(player.getLocation(), Sound.valueOf(args[0].toUpperCase()), Float.parseFloat(args[1]), Float.parseFloat(args[2]));
                            }
                            catch (IllegalArgumentException ex) {
                                CSDirector.this.printM("'" + space_filtered + "' of weapon '" + parent_node + "' contains either an invalid number or sound!");
                            }
                        }
                    }, (long)Long.valueOf(args[3]));
                    if (reload) {
                        final Player reloader = (Player)player;
                        final String user = reloader.getName();
                        Collection<Integer> values_reload = this.global_reload_IDs.get(user);
                        if (values_reload == null) {
                            values_reload = new ArrayList<Integer>();
                            this.global_reload_IDs.put(user, values_reload);
                        }
                        values_reload.add(task_ID);
                    }
                }
                else {
                    this.printM("'" + space_filtered + "' of weapon '" + parent_node + "' has an invalid format! The correct format is: Sound-Volume-Pitch-Delay!");
                }
            }
        }
    }
    
    public void playSoundEffects(final Location loc, final String parent_node, final String child_node) {
        if (this.getString(String.valueOf(parent_node) + child_node) == null) {
            return;
        }
        final String[] sound_effects = this.getString(String.valueOf(parent_node) + child_node).split(",");
        if (sound_effects != null) {
            String[] array;
            for (int length = (array = sound_effects).length, i = 0; i < length; ++i) {
                final String sound_effect = array[i];
                final String space_filtered = sound_effect.replace(" ", "");
                final String[] args = space_filtered.split("-");
                if (args.length == 4) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            try {
                                loc.getWorld().playSound(loc, Sound.valueOf(args[0].toUpperCase()), Float.parseFloat(args[1]), Float.parseFloat(args[2]));
                            }
                            catch (IllegalArgumentException ex) {
                                CSDirector.this.printM("'" + space_filtered + "' of weapon '" + parent_node + "' contains either an invalid number or sound!");
                            }
                        }
                    }, (long)Long.valueOf(args[3]));
                }
                else {
                    this.printM("'" + space_filtered + "' of weapon '" + parent_node + "' has an invalid format! The correct format is: Sound-Volume-Pitch-Delay!");
                }
            }
        }
    }
    
    public void fireProjectile(final Player player, final String parent_node, final boolean leftClick) {
        final int gunSlot = player.getInventory().getHeldItemSlot();
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrackShot");
        final int shootDelay = this.getInt(String.valueOf(parent_node) + ".Shooting.Delay_Between_Shots");
        final int projAmount = this.getInt(String.valueOf(parent_node) + ".Shooting.Projectile_Amount");
        final boolean ammoEnable = this.getBoolean(String.valueOf(parent_node) + ".Ammo.Enable");
        final boolean oneTime = this.getBoolean(String.valueOf(parent_node) + ".Extras.One_Time_Use");
        final boolean mines = this.getBoolean(String.valueOf(parent_node) + ".Explosive_Devices.Enable");
        final String deviceType = this.getString(String.valueOf(parent_node) + ".Explosive_Devices.Device_Type");
        final String proType = this.getString(String.valueOf(parent_node) + ".Shooting.Projectile_Type");
        final boolean burstEnable = this.getBoolean(String.valueOf(parent_node) + ".Burstfire.Enable");
        int burstShots = this.getInt(String.valueOf(parent_node) + ".Burstfire.Shots_Per_Burst");
        final int burstDelay = this.getInt(String.valueOf(parent_node) + ".Burstfire.Delay_Between_Shots_In_Burst");
        final boolean shootDisable = this.getBoolean(String.valueOf(parent_node) + ".Shooting.Disable");
        final boolean reloadOn = this.getBoolean(String.valueOf(parent_node) + ".Reload.Enable");
        final boolean dualWield = this.getBoolean(String.valueOf(parent_node) + ".Shooting.Dual_Wield");
        final ItemStack item = player.getInventory().getItemInHand();
        if (shootDisable) {
            return;
        }
        final Vector shiftVector = this.determinePosition(player, dualWield, leftClick);
        final Location projLoc = player.getEyeLocation().toVector().add(shiftVector.multiply(0.2)).toLocation(player.getWorld());
        if (player.hasMetadata(String.valueOf(parent_node) + "shootDelay" + gunSlot + leftClick)) {
            return;
        }
        if (oneTime && ammoEnable) {
            player.sendMessage(String.valueOf(this.heading) + "For '" + parent_node + "' - the 'One_Time_Use' node is incompatible with weapons using the Ammo module.");
            return;
        }
        if (proType != null && (proType.equalsIgnoreCase("grenade") || proType.equalsIgnoreCase("flare")) && projAmount == 0) {
            player.sendMessage(String.valueOf(this.heading) + "The weapon '" + parent_node + "' is missing a value for 'Projectile_Amount'.");
            return;
        }
        player.setMetadata(String.valueOf(parent_node) + "shootDelay" + gunSlot + leftClick, (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
        this.csminion.tempVars(player, String.valueOf(parent_node) + "shootDelay" + gunSlot + leftClick, (long)shootDelay);
        if (mines && deviceType != null && deviceType.equalsIgnoreCase("landmine")) {
            this.csminion.oneTime(player);
            this.playSoundEffects((Entity)player, parent_node, ".Explosive_Devices.Sounds_Deploy", false);
            this.deployMine(player, parent_node);
            return;
        }
        if (this.itemIsSafe(item) && item.getItemMeta().getDisplayName().contains("\u1d3f")) {
            if (this.getAmmoBetweenBrackets(player, parent_node, item) <= 0) {
                this.reloadAnimation(player, parent_node);
                return;
            }
            if (!dualWield) {
                this.terminateReload(player);
                this.removeInertReloadTag(player, 0, true);
            }
            else {
                final int[] ammoReading = this.grabDualAmmo(item);
                if ((ammoReading[0] > 0 && leftClick) || (ammoReading[1] > 0 && !leftClick)) {
                    this.terminateReload(player);
                    this.removeInertReloadTag(player, 0, true);
                }
            }
        }
        if (this.checkBoltPosition(player, parent_node)) {
            return;
        }
        final String ammoInfo = this.getString(String.valueOf(parent_node) + ".Ammo.Ammo_Item_ID");
        final boolean ammoPerShot = this.getBoolean(String.valueOf(parent_node) + ".Ammo.Take_Ammo_Per_Shot");
        final double zoomAcc = this.getDouble(String.valueOf(parent_node) + ".Scope.Zoom_Bullet_Spread");
        final boolean sneakOn = this.getBoolean(String.valueOf(parent_node) + ".Sneak.Enable");
        final boolean sneakToShoot = this.getBoolean(String.valueOf(parent_node) + ".Sneak.Sneak_Before_Shooting");
        final boolean sneakNoRec = this.getBoolean(String.valueOf(parent_node) + ".Sneak.No_Recoil");
        final double sneakAcc = this.getDouble(String.valueOf(parent_node) + ".Sneak.Bullet_Spread");
        final boolean exploDevs = this.getBoolean(String.valueOf(parent_node) + ".Explosive_Devices.Enable");
        final boolean takeAmmo = this.getBoolean(String.valueOf(parent_node) + ".Reload.Take_Ammo_On_Reload");
        if (ammoEnable && !takeAmmo && !ammoPerShot) {
            player.sendMessage(String.valueOf(this.heading) + "The weapon '" + parent_node + "' has enabled the Ammo module, but at least one of the following nodes need to be set to true: Take_Ammo_On_Reload, Take_Ammo_Per_Shot.");
            return;
        }
        if (sneakToShoot && (!player.isSneaking() || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR)) {
            return;
        }
        if (ammoEnable && !this.csminion.containsItemStack(player, ammoInfo, 1)) {
            if (ammoPerShot) {
                this.playSoundEffects((Entity)player, parent_node, ".Ammo.Sounds_Shoot_With_No_Ammo", false);
                return;
            }
            if (takeAmmo && this.getAmmoBetweenBrackets(player, parent_node, item) == 0) {
                this.playSoundEffects((Entity)player, parent_node, ".Ammo.Sounds_Shoot_With_No_Ammo", false);
                return;
            }
        }
        if (!burstEnable) {
            burstShots = 1;
        }
        final double projectile_speed = this.getInt(String.valueOf(parent_node) + ".Shooting.Projectile_Speed") * 0.1;
        if (this.getBoolean(String.valueOf(parent_node) + ".Scope.Zoom_Before_Shooting") && !player.hasMetadata("ironsights")) {
            return;
        }
        for (int burst = 0; burst < burstShots; ++burst) {
            final int burstFinal = burst;
            final int burstShotsFinal = burstShots;
            final int task_ID = Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
                @Override
                public void run() {
                    if (burstFinal >= burstShotsFinal - 1) {
                        CSDirector.this.burst_task_IDs.remove(player.getName());
                    }
                    final ItemStack item = player.getInventory().getItemInHand();
                    final String item_name = CSDirector.this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Name");
                    final String item_ID = CSDirector.this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Type");
                    if (!oneTime) {
                        if (CSDirector.this.switchedTheItem(player, item_name, item_ID, parent_node)) {
                            CSDirector.this.unscopePlayer(player);
                            CSDirector.this.terminateAllBursts(player);
                            return;
                        }
                        final String actType = CSDirector.this.getString(String.valueOf(parent_node) + ".Firearm_Action.Type");
                        boolean normalAction = false;
                        if (actType == null) {
                            normalAction = true;
                            final String attachType = CSDirector.this.getString(String.valueOf(parent_node) + ".Item_Information.Attachments.Type");
                            final String filter = item.getItemMeta().getDisplayName();
                            if (attachType == null || !attachType.equalsIgnoreCase("accessory")) {
                                if (item.getItemMeta().getDisplayName().contains("\u25aa «")) {
                                    CSDirector.this.csminion.setItemName(item, filter.replaceAll("\u25aa «", "«"));
                                }
                                else if (item.getItemMeta().getDisplayName().contains("\u25ab «")) {
                                    CSDirector.this.csminion.setItemName(item, filter.replaceAll("\u25ab «", "«"));
                                }
                                else if (item.getItemMeta().getDisplayName().contains("\u06d4 «")) {
                                    CSDirector.this.csminion.setItemName(item, filter.replaceAll("\u06d4 «", "«"));
                                }
                            }
                        }
                        else if (!actType.toLowerCase().contains("bolt") && !actType.toLowerCase().contains("lever") && !actType.toLowerCase().contains("pump")) {
                            normalAction = true;
                        }
                        if (ammoEnable && ammoPerShot && !CSDirector.this.csminion.containsItemStack(player, ammoInfo, 1)) {
                            CSDirector.this.burst_task_IDs.remove(player.getName());
                            return;
                        }
                        if (reloadOn) {
                            if (item.getItemMeta().getDisplayName().contains("\u1d3f")) {
                                return;
                            }
                            final int detected_ammo = CSDirector.this.getAmmoBetweenBrackets(player, parent_node, item);
                            if (normalAction) {
                                if (detected_ammo <= 0) {
                                    CSDirector.this.reloadAnimation(player, parent_node);
                                    return;
                                }
                                if (!dualWield) {
                                    CSDirector.this.ammoOperation(player, parent_node, detected_ammo, item);
                                }
                                else if (!CSDirector.this.ammoSpecOps(player, parent_node, detected_ammo, item, leftClick)) {
                                    return;
                                }
                            }
                        }
                        else if (!item.getItemMeta().getDisplayName().contains(String.valueOf('\u00d7')) && !exploDevs) {
                            CSDirector.this.csminion.replaceBrackets(item, String.valueOf('\u00d7'), parent_node);
                        }
                    }
                    double bulletSpread = CSDirector.this.getDouble(String.valueOf(parent_node) + ".Shooting.Bullet_Spread");
                    if (player.isSneaking() && sneakOn) {
                        bulletSpread = sneakAcc;
                    }
                    if (player.hasMetadata("ironsights")) {
                        bulletSpread = zoomAcc;
                    }
                    if (bulletSpread == 0.0) {
                        bulletSpread = 0.1;
                    }
                    final double recoil_amount = CSDirector.this.getInt(String.valueOf(parent_node) + ".Shooting.Recoil_Amount") * 0.1;
                    if (recoil_amount != 0.0 && (!sneakOn || !sneakNoRec || !player.isSneaking())) {
                        player.setVelocity(player.getLocation().getDirection().multiply(-recoil_amount));
                    }
                    final boolean clear_fall = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Shooting.Reset_Fall_Distance");
                    if (clear_fall) {
                        player.setFallDistance(0.0f);
                    }
                    CSDirector.this.playSoundEffects((Entity)player, parent_node, ".Shooting.Sounds_Shoot", false);
                    CSDirector.this.csminion.giveParticleEffects((Entity)player, parent_node, ".Particles.Particle_Player_Shoot", true);
                    CSDirector.this.csminion.displayFireworks((Entity)player, parent_node, ".Fireworks.Firework_Player_Shoot");
                    final String projectile_type = CSDirector.this.getString(String.valueOf(parent_node) + ".Shooting.Projectile_Type");
                    int timer = CSDirector.this.getInt(String.valueOf(parent_node) + ".Explosions.Explosion_Delay");
                    final boolean airstrike = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Airstrikes.Enable");
                    if (airstrike) {
                        timer = CSDirector.this.getInt(String.valueOf(parent_node) + ".Airstrikes.Flare_Activation_Delay");
                    }
                    for (int i = 0; i < projAmount; ++i) {
                        final Random r = new Random();
                        final double yaw = Math.toRadians(-player.getLocation().getYaw() - 90.0f);
                        final double pitch = Math.toRadians(-player.getLocation().getPitch());
                        final double[] spread = { 1.0, 1.0, 1.0 };
                        for (int t = 0; t < 3; ++t) {
                            spread[t] = (r.nextDouble() - r.nextDouble()) * bulletSpread * 0.1;
                        }
                        final Vector dirVel = new Vector(Math.cos(pitch) * Math.cos(yaw) + spread[0], Math.sin(pitch) + spread[1], -Math.sin(yaw) * Math.cos(pitch) + spread[2]).multiply(projectile_speed);
                        if (proType != null && (proType.toLowerCase().contains("grenade") || proType.toLowerCase().contains("flare"))) {
                            CSDirector.this.launchGrenade(player, parent_node, timer, dirVel, null, 0);
                        }
                        else {
                            Projectile snowball;
                            if (projectile_type.equalsIgnoreCase("arrow")) {
                                snowball = (Projectile)player.getWorld().spawnEntity(projLoc, EntityType.ARROW);
                            }
                            else if (projectile_type.equalsIgnoreCase("egg")) {
                                snowball = (Projectile)player.getWorld().spawnEntity(projLoc, EntityType.EGG);
                                snowball.setMetadata("CS_Hardboiled", (MetadataValue)new FixedMetadataValue(plugin, (Object)true));
                            }
                            else if (projectile_type.equalsIgnoreCase("fireball")) {
                                snowball = player.launchProjectile((Class)LargeFireball.class);
                            }
                            else if (projectile_type.equalsIgnoreCase("witherskull")) {
                                snowball = player.launchProjectile((Class)WitherSkull.class);
                            }
                            else {
                                snowball = (Projectile)player.getWorld().spawnEntity(projLoc, EntityType.SNOWBALL);
                            }
                            snowball.setShooter((LivingEntity)player);
                            snowball.setVelocity(dirVel);
                            snowball.setMetadata(parent_node, (MetadataValue)new FixedMetadataValue(plugin, (Object)true));
                            CSDirector.this.playSoundEffects((Entity)snowball, parent_node, ".Shooting.Sounds_Projectile", false);
                        }
                    }
                }
            }, (long)Long.valueOf(burstDelay * burst));
            if (oneTime && burst == 0 && (deviceType == null || !deviceType.toLowerCase().contains("remote"))) {
                this.csminion.oneTime(player);
            }
            final String user = player.getName();
            Collection<Integer> values = this.burst_task_IDs.get(user);
            if (values == null) {
                values = new ArrayList<Integer>();
                this.burst_task_IDs.put(user, values);
            }
            values.add(task_ID);
        }
    }
    
    public void reloadAnimation(final Player player, final String parent_node) {
        if (!this.getBoolean(String.valueOf(parent_node) + ".Reload.Enable") || player.hasMetadata("mark_of_the_reload")) {
            return;
        }
        int relDuration = this.getInt(String.valueOf(parent_node) + ".Reload.Reload_Duration");
        final ItemStack held = player.getInventory().getItemInHand();
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrackShot");
        final String itemName = this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Name");
        final boolean takeAsMag = this.getBoolean(String.valueOf(parent_node) + ".Reload.Take_Ammo_As_Magazine");
        final boolean takeAmmo = this.getBoolean(String.valueOf(parent_node) + ".Reload.Take_Ammo_On_Reload");
        final boolean reloadIndie = this.getBoolean(String.valueOf(parent_node) + ".Reload.Reload_Bullets_Individually");
        final boolean ammoEnable = this.getBoolean(String.valueOf(parent_node) + ".Ammo.Enable");
        final String ammoInfo = this.getString(String.valueOf(parent_node) + ".Ammo.Ammo_Item_ID");
        final String itemID = this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Type");
        final int openTime = this.getInt(String.valueOf(parent_node) + ".Firearm_Action.Open_Duration");
        final int closeTime = this.getInt(String.valueOf(parent_node) + ".Firearm_Action.Close_Duration");
        String reloadSound = ".Reload.Sounds_Reloading";
        final boolean dualWield = this.getBoolean(String.valueOf(parent_node) + ".Shooting.Dual_Wield");
        final int reloadAmt = dualWield ? (this.getInt(String.valueOf(parent_node) + ".Reload.Reload_Amount") * 2) : this.getInt(String.valueOf(parent_node) + ".Reload.Reload_Amount");
        final String replacer = dualWield ? (String.valueOf(reloadAmt / 2) + " | " + reloadAmt / 2) : String.valueOf(reloadAmt);
        final String actionType = dualWield ? null : this.getString(String.valueOf(parent_node) + ".Firearm_Action.Type");
        boolean pumpAct = false;
        boolean breakAct = false;
        boolean slide = false;
        if (actionType != null) {
            if (actionType.equalsIgnoreCase("pump")) {
                pumpAct = true;
            }
            if (actionType.equalsIgnoreCase("break") || actionType.equalsIgnoreCase("revolver")) {
                breakAct = true;
            }
            if (actionType.equalsIgnoreCase("slide")) {
                slide = true;
            }
        }
        final boolean finalBreakAct = breakAct;
        if (this.switchedTheItem(player, itemName, itemID, parent_node) || (takeAmmo && ammoEnable && !this.csminion.containsItemStack(player, ammoInfo, 1))) {
            player.removeMetadata("mark_of_the_reload", (Plugin)this);
            return;
        }
        if (!dualWield) {
            final String attachType = this.getString(String.valueOf(parent_node) + ".Item_Information.Attachments.Type");
            final String displayName = held.getItemMeta().getDisplayName();
            if (displayName.contains("«" + reloadAmt + "»") || (attachType != null && attachType.equalsIgnoreCase("accessory") && displayName.contains(String.valueOf(reloadAmt) + "»")) || (attachType != null && attachType.equalsIgnoreCase("main") && displayName.contains("«" + reloadAmt))) {
                if (finalBreakAct) {
                    this.checkBoltPosition(player, parent_node);
                }
                else {
                    this.correctBoltPosition(player, parent_node, false, closeTime, false, true, pumpAct, false);
                }
                player.removeMetadata("mark_of_the_reload", (Plugin)this);
                return;
            }
            if (!pumpAct && !slide && (attachType == null || !attachType.equalsIgnoreCase("accessory"))) {
                if (!finalBreakAct && (displayName.contains("\u25aa") || displayName.contains("\u25ab"))) {
                    this.correctBoltPosition(player, parent_node, true, openTime, true, false, false, false);
                    return;
                }
                if (displayName.contains("\u25aa")) {
                    this.correctBoltPosition(player, parent_node, true, openTime, true, false, false, false);
                    return;
                }
            }
        }
        else {
            final int[] ammoReading = this.grabDualAmmo(held);
            if (ammoReading[0] + ammoReading[1] >= reloadAmt) {
                player.removeMetadata("mark_of_the_reload", (Plugin)this);
                return;
            }
            if (ammoReading[0] == reloadAmt / 2 || ammoReading[1] == reloadAmt / 2 || (takeAmmo && ammoEnable && this.csminion.countItemStacks(player, ammoInfo) == 1)) {
                relDuration = this.getInt(String.valueOf(parent_node) + ".Reload.Dual_Wield.Single_Reload_Duration");
                reloadSound = ".Reload.Dual_Wield.Sounds_Single_Reload";
            }
        }
        this.terminateReload(player);
        this.removeInertReloadTag(player, 0, true);
        this.unscopePlayer(player);
        player.setMetadata("mark_of_the_reload", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
        this.terminateAllBursts(player);
        if (!held.getItemMeta().getDisplayName().contains("\u1d3f")) {
            this.csminion.setItemName(held, String.valueOf(held.getItemMeta().getDisplayName()) + '\u1d3f');
        }
        if (!reloadIndie) {
            this.playSoundEffects((Entity)player, parent_node, reloadSound, true);
        }
        final int task_ID = Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                if (takeAmmo && ammoEnable && !CSDirector.this.csminion.containsItemStack(player, ammoInfo, 1)) {
                    CSDirector.this.removeInertReloadTag(player, 0, true);
                    return;
                }
                CSDirector.this.terminateReload(player);
                if (CSDirector.this.switchedTheItem(player, itemName, itemID, parent_node)) {
                    return;
                }
                final ItemStack item = player.getInventory().getItemInHand();
                if (item.getItemMeta().getDisplayName().contains("\u1d3f")) {
                    CSDirector.this.removeInertReloadTag(player, 0, true);
                    int currentAmmo = CSDirector.this.getAmmoBetweenBrackets(player, parent_node, item);
                    if (takeAmmo && ammoEnable) {
                        if (reloadIndie && !dualWield) {
                            ++currentAmmo;
                            CSDirector.this.playSoundEffects((Entity)player, parent_node, ".Reload.Sounds_Reloading", false);
                            CSDirector.this.csminion.replaceBrackets(item, String.valueOf(currentAmmo), parent_node);
                            CSDirector.this.csminion.removeNamedItem(player, ammoInfo, 1, parent_node);
                            CSDirector.this.reloadAnimation(player, parent_node);
                            return;
                        }
                        if (!takeAsMag) {
                            int invAmmo = CSDirector.this.csminion.countItemStacks(player, ammoInfo);
                            final int fillAmt = reloadAmt - currentAmmo;
                            currentAmmo += invAmmo;
                            if (currentAmmo > reloadAmt) {
                                currentAmmo = reloadAmt;
                            }
                            if (!dualWield) {
                                CSDirector.this.csminion.replaceBrackets(item, String.valueOf(currentAmmo), parent_node);
                            }
                            else if (currentAmmo < reloadAmt) {
                                final int[] ammoReading = CSDirector.this.grabDualAmmo(item);
                                int leftGun = ammoReading[0];
                                int rightGun = ammoReading[1];
                                while (invAmmo > 0) {
                                    if (leftGun == reloadAmt / 2 || leftGun > rightGun) {
                                        ++rightGun;
                                    }
                                    else if (rightGun == reloadAmt / 2 || rightGun > leftGun || leftGun == rightGun) {
                                        ++leftGun;
                                    }
                                    --invAmmo;
                                }
                                CSDirector.this.csminion.replaceBrackets(item, String.valueOf(String.valueOf(leftGun) + " | " + rightGun), parent_node);
                            }
                            else {
                                CSDirector.this.csminion.replaceBrackets(item, String.valueOf(replacer), parent_node);
                            }
                            CSDirector.this.csminion.removeNamedItem(player, ammoInfo, fillAmt, parent_node);
                        }
                        else if (!dualWield) {
                            CSDirector.this.csminion.replaceBrackets(item, String.valueOf(replacer), parent_node);
                            CSDirector.this.csminion.removeNamedItem(player, ammoInfo, 1, parent_node);
                        }
                        else {
                            int invAmmo = CSDirector.this.csminion.countItemStacks(player, ammoInfo);
                            final int[] ammoReading2 = CSDirector.this.grabDualAmmo(item);
                            int amtToRemove = 0;
                            for (int i = 0; i < 2; ++i) {
                                if (ammoReading2[i] != reloadAmt / 2 && invAmmo > 0) {
                                    ammoReading2[i] = reloadAmt / 2;
                                    ++amtToRemove;
                                    --invAmmo;
                                }
                            }
                            CSDirector.this.csminion.replaceBrackets(item, String.valueOf(String.valueOf(ammoReading2[0])) + " | " + String.valueOf(ammoReading2[1]), parent_node);
                            CSDirector.this.csminion.removeNamedItem(player, ammoInfo, amtToRemove, parent_node);
                        }
                        if (!finalBreakAct) {
                            CSDirector.this.correctBoltPosition(player, parent_node, false, closeTime, false, true, false, false);
                        }
                        else {
                            CSDirector.this.checkBoltPosition(player, parent_node);
                        }
                        CSDirector.this.removeInertReloadTag(player, 0, true);
                    }
                    else {
                        if (reloadIndie && !dualWield) {
                            ++currentAmmo;
                            CSDirector.this.playSoundEffects((Entity)player, parent_node, ".Reload.Sounds_Reloading", false);
                            CSDirector.this.csminion.replaceBrackets(item, String.valueOf(currentAmmo), parent_node);
                            CSDirector.this.reloadAnimation(player, parent_node);
                            return;
                        }
                        player.removeMetadata("mark_of_the_reload", plugin);
                        CSDirector.this.csminion.replaceBrackets(item, String.valueOf(replacer), parent_node);
                        if (!finalBreakAct) {
                            CSDirector.this.correctBoltPosition(player, parent_node, false, closeTime, false, true, false, false);
                        }
                        else {
                            CSDirector.this.checkBoltPosition(player, parent_node);
                        }
                    }
                }
            }
        }, (long)Long.valueOf(relDuration));
        final String user = player.getName();
        Collection<Integer> values_reload = this.global_reload_IDs.get(user);
        if (values_reload == null) {
            values_reload = new ArrayList<Integer>();
            this.global_reload_IDs.put(user, values_reload);
        }
        values_reload.add(task_ID);
    }
    
    public void projectileExplosion(final Entity obj_projectile, final String parent_node, final boolean grenade, final Player player, final boolean landmine, final boolean rde, final Location loc, final Block c4, final boolean trap, final int cTimes) {
        if (!this.getBoolean(String.valueOf(parent_node) + ".Explosions.Enable")) {
            return;
        }
        if (!this.csminion.regionCheck(obj_projectile, parent_node)) {
            return;
        }
        int delay = this.getInt(String.valueOf(parent_node) + ".Explosions.Explosion_Delay");
        if (grenade) {
            delay = 0;
        }
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrackShot");
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                Location location = null;
                World world = null;
                if (!rde) {
                    world = obj_projectile.getWorld();
                    location = obj_projectile.getLocation().getBlock().getLocation().add(0.5, 0.5, 0.5);
                    if (landmine) {
                        obj_projectile.remove();
                    }
                    if (!grenade && !landmine) {
                        final BlockIterator checker = new BlockIterator(obj_projectile.getWorld(), obj_projectile.getLocation().toVector(), obj_projectile.getVelocity().normalize().multiply(-1), 0.0, 4);
                        Block block = null;
                        while (checker.hasNext()) {
                            block = checker.next();
                            if (block.getTypeId() == 0) {
                                break;
                            }
                        }
                        if (block.getTypeId() == 0) {
                            location = block.getLocation().add(0.5, 0.5, 0.5);
                        }
                    }
                }
                else if (!trap) {
                    c4.removeMetadata("CS_transformers", plugin);
                    c4.setType(Material.AIR);
                    location = loc;
                    world = loc.getWorld();
                }
                else {
                    c4.removeMetadata("CS_btrap", plugin);
                    location = c4.getRelative(BlockFace.UP).getLocation().add(0.5, 0.5, 0.5);
                    world = c4.getLocation().getWorld();
                }
                final boolean airstrike = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Airstrikes.Enable");
                final boolean cEnable = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Cluster_Bombs.Enable");
                final int cOfficialTimes = CSDirector.this.getInt(String.valueOf(parent_node) + ".Cluster_Bombs.Number_Of_Splits");
                if (!cEnable || airstrike || cTimes >= cOfficialTimes) {
                    final boolean shrapEnable = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Shrapnel.Enable");
                    if (shrapEnable) {
                        final String shrapType = CSDirector.this.getString(String.valueOf(parent_node) + ".Shrapnel.Block_Type");
                        final int shrapAmount = CSDirector.this.getInt(String.valueOf(parent_node) + ".Shrapnel.Amount");
                        final int shrapSpeed = CSDirector.this.getInt(String.valueOf(parent_node) + ".Shrapnel.Speed");
                        final boolean placeBlocks = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Shrapnel.Place_Blocks");
                        String[] blockInfo = shrapType.split("~");
                        if (blockInfo.length < 2) {
                            blockInfo = new String[] { blockInfo[0], "0" };
                        }
                        final Material blockMat = Material.getMaterial((int)Integer.valueOf(blockInfo[0]));
                        if (blockMat == null) {
                            player.sendMessage(String.valueOf(CSDirector.this.heading) + "'" + shrapType + "' of weapon '" + parent_node + "' is not a valid block-type.");
                            return;
                        }
                        Byte secData;
                        try {
                            secData = Byte.valueOf(blockInfo[1]);
                        }
                        catch (NumberFormatException ex) {
                            player.sendMessage(String.valueOf(CSDirector.this.heading) + "'" + shrapType + "' of weapon '" + parent_node + "' has an invalid secondary data value.");
                            return;
                        }
                        final Random r = new Random();
                        for (int i = 0; i < shrapAmount; ++i) {
                            location.setPitch((float)(-(r.nextInt(90) + r.nextInt(90))));
                            location.setYaw((float)r.nextInt(360));
                            final FallingBlock shrapnel = location.getWorld().spawnFallingBlock(location, blockMat, (byte)secData);
                            if (!placeBlocks) {
                                shrapnel.setMetadata("CS_shrapnel", (MetadataValue)new FixedMetadataValue(plugin, (Object)true));
                            }
                            shrapnel.setDropItem(false);
                            final double shrapSpeedF = shrapSpeed * ((100 - (r.nextInt(25) - r.nextInt(25))) * 0.001);
                            shrapnel.setVelocity(location.getDirection().multiply(shrapSpeedF));
                        }
                    }
                    CSDirector.this.csminion.displayFireworks(obj_projectile, parent_node, ".Fireworks.Firework_Explode");
                    final boolean ownerNoDam = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Explosions.Enable_Owner_Immunity");
                    final boolean noDam = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Explosions.Explosion_No_Damage");
                    final boolean frenFire = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Explosions.Enable_Friendly_Fire");
                    final boolean noGrief = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Explosions.Explosion_No_Grief");
                    final boolean incendiary = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Explosions.Explosion_Incendiary");
                    int boomRadius = CSDirector.this.getInt(String.valueOf(parent_node) + ".Explosions.Explosion_Radius");
                    if (boomRadius > 20) {
                        boomRadius = 20;
                    }
                    final TNTPrimed tnt = (TNTPrimed)world.spawn(location, (Class)TNTPrimed.class);
                    tnt.setYield((float)boomRadius);
                    tnt.setIsIncendiary(incendiary);
                    tnt.setFuseTicks(0);
                    tnt.setMetadata("CS_Label", (MetadataValue)new FixedMetadataValue(plugin, (Object)true));
                    tnt.setMetadata("CS_potex", (MetadataValue)new FixedMetadataValue(plugin, (Object)parent_node));
                    if (!rde) {
                        tnt.setMetadata("C4_Friendly", (MetadataValue)new FixedMetadataValue(plugin, (Object)true));
                    }
                    if (noGrief) {
                        tnt.setMetadata("nullify", (MetadataValue)new FixedMetadataValue(plugin, (Object)true));
                    }
                    if (noDam) {
                        tnt.setMetadata("CS_nodam", (MetadataValue)new FixedMetadataValue(plugin, (Object)true));
                    }
                    if (player != null) {
                        tnt.setMetadata("CS_pName", (MetadataValue)new FixedMetadataValue(plugin, (Object)player.getName()));
                        if (!frenFire) {
                            tnt.setMetadata("CS_ffcheck", (MetadataValue)new FixedMetadataValue(plugin, (Object)true));
                        }
                        if (ownerNoDam) {
                            tnt.setMetadata("0wner_nodam", (MetadataValue)new FixedMetadataValue(plugin, (Object)true));
                        }
                    }
                    return;
                }
                final int cAmount = CSDirector.this.getInt(String.valueOf(parent_node) + ".Cluster_Bombs.Number_Of_Bomblets");
                final int cSpeed = CSDirector.this.getInt(String.valueOf(parent_node) + ".Cluster_Bombs.Speed_Of_Bomblets");
                final int timer = CSDirector.this.getInt(String.valueOf(parent_node) + ".Cluster_Bombs.Delay_Before_Detonation");
                final Random r2 = new Random();
                final int totalAmount = (int)Math.pow(cAmount, cOfficialTimes);
                if (totalAmount > 1000) {
                    if (player != null) {
                        player.sendMessage(String.valueOf(CSDirector.this.heading) + cAmount + " to the power of " + cOfficialTimes + " equates to " + totalAmount + " bomblets and consequent explosions! For your safety, CrackShot does not accept total bomblet amounts of over 1000. Please lower the value for 'Number_Of_Splits' and/or 'Number_Of_Bomblets' for the weapon '" + parent_node + "'.");
                    }
                    return;
                }
                for (int j = 0; j < cAmount; ++j) {
                    location.setPitch((float)(-(r2.nextInt(90) + r2.nextInt(90))));
                    location.setYaw((float)r2.nextInt(360));
                    final double cSpeedF = cSpeed * ((100 - (r2.nextInt(25) - r2.nextInt(25))) * 0.001);
                    CSDirector.this.launchGrenade(player, parent_node, timer, location.getDirection().multiply(cSpeedF), location, cTimes + 1);
                }
                CSDirector.this.csminion.giveParticleEffects(location, parent_node, ".Cluster_Bombs.Particle_Release");
                CSDirector.this.playSoundEffects(location, parent_node, ".Cluster_Bombs.Sounds_Release");
            }
        }, (long)Long.valueOf(Math.abs(delay)));
    }
    
    @EventHandler
    public void onPlayerItemHeld(final PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();
        this.removeInertReloadTag(player, event.getPreviousSlot(), false);
        this.removeInertReloadTag(player, event.getNewSlot(), false);
        this.unscopePlayer(player);
        this.terminateAllBursts(player);
        this.terminateReload(player);
        final String[] pc = this.itemParentNode(player.getInventory().getItem(event.getNewSlot()), player);
        if (pc == null) {
            return;
        }
        if (!Boolean.valueOf(pc[1])) {
            return;
        }
        final ItemStack weapon = this.csminion.vendingMachine(pc[0]);
        weapon.setAmount(player.getInventory().getItem(event.getNewSlot()).getAmount());
        player.getInventory().setItem(event.getNewSlot(), weapon);
    }
    
    @EventHandler
    public void onPlayerDisconnect(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        this.removeInertReloadTag(player, 0, true);
        this.unscopePlayer(player);
        this.terminateAllBursts(player);
        this.terminateReload(player);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGunThrow(final PlayerDropItemEvent event) {
        final ItemStack trash = event.getItemDrop().getItemStack();
        final String[] pc = this.itemParentNode(trash, event.getPlayer());
        if (pc == null) {
            return;
        }
        if (!this.getBoolean(String.valueOf(pc[0]) + ".Reload.Enable")) {
            return;
        }
        final Player player = event.getPlayer();
        player.getInventory().getHeldItemSlot();
        if (!player.hasMetadata("dr0p_authorised")) {
            event.setCancelled(true);
            this.delayedReload(player, pc[0]);
        }
    }
    
    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        final Player player = event.getEntity();
        this.removeInertReloadTag(player, 0, true);
        this.unscopePlayer(player);
        this.terminateAllBursts(player);
        this.terminateReload(player);
        if (event.getDeathMessage() != null) {
            String message = event.getDeathMessage().replaceAll("(?<=«).*?(?=»)", "");
            message = message.replaceAll(" «", "");
            message = message.replaceAll(String.valueOf('\u1d3f'), "");
            message = message.replaceAll("[»\u25aa\u25ab\u06d4]", "");
            event.setDeathMessage(message);
        }
        if (event.getEntity().getKiller() instanceof Player) {
            final Player shooter = event.getEntity().getKiller();
            final String parent_node = this.returnParentNode(shooter);
            if (parent_node == null) {
                return;
            }
            String msg = this.getString(String.valueOf(parent_node) + ".Custom_Death_Message.Normal");
            if (msg == null) {
                return;
            }
            msg = msg.replaceAll("<shooter>", shooter.getName());
            msg = msg.replaceAll("<victim>", player.getName());
            event.setDeathMessage(msg);
        }
    }
    
    @EventHandler
    public void clickGun(final InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            final Player player = (Player)event.getWhoClicked();
            if (event.getSlotType() == InventoryType.SlotType.QUICKBAR) {
                this.removeInertReloadTag(player, event.getSlot(), false);
                this.unscopePlayer(player);
                this.terminateAllBursts(player);
                this.terminateReload(player);
            }
            if (event.getSlot() != -1 && event.getCurrentItem() != null) {
                final String[] pc = this.itemParentNode(event.getCurrentItem(), player);
                if (pc == null) {
                    return;
                }
                if (!Boolean.valueOf(pc[1])) {
                    return;
                }
                final ItemStack weapon = this.csminion.vendingMachine(pc[0]);
                weapon.setAmount(event.getCurrentItem().getAmount());
                event.setCurrentItem(weapon);
            }
            if (event.getSlot() == -999) {
                final ItemStack trash = event.getCursor();
                final String[] pc2 = this.itemParentNode(trash, player);
                if (pc2 == null) {
                    return;
                }
                player.setMetadata("dr0p_authorised", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
                this.csminion.tempVars(player, "dr0p_authorised", 1L);
            }
        }
    }
    
    public void unscopePlayer(final Player player) {
        if (player.hasMetadata("ironsights")) {
            final String pName = player.getName();
            player.removeMetadata("ironsights", (Plugin)this);
            player.removePotionEffect(PotionEffectType.SPEED);
            if (player.hasMetadata("night_scoping")) {
                player.removeMetadata("night_scoping", (Plugin)this);
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
            try {
                player.addPotionEffect(PotionEffectType.SPEED.createEffect((int)this.duration_storage.get(pName), (int)this.amplifier_storage.get(pName)));
            }
            catch (NullPointerException ex) {}
            this.amplifier_storage.remove(pName);
            this.duration_storage.remove(pName);
        }
    }
    
    public void removeInertReloadTag(final Player player, final int item_slot, final boolean no_slot) {
        ItemStack item = player.getInventory().getItem(item_slot);
        if (no_slot) {
            item = player.getInventory().getItemInHand();
        }
        if (item != null && this.itemIsSafe(item) && item.getItemMeta().getDisplayName().contains(String.valueOf('\u1d3f'))) {
            final String cleaner = item.getItemMeta().getDisplayName().replaceAll(String.valueOf('\u1d3f'), "");
            if (no_slot) {
                this.csminion.setItemName(player.getInventory().getItemInHand(), cleaner);
            }
            else {
                this.csminion.setItemName(player.getInventory().getItem(item_slot), cleaner);
            }
        }
    }
    
    public boolean switchedTheItem(final Player player, final String iName, final String itemID, final String parent_node) {
        final String attachType = this.getString(String.valueOf(parent_node) + ".Item_Information.Attachments.Type");
        if (attachType != null && attachType.equalsIgnoreCase("accessory")) {
            return false;
        }
        if (itemID == null) {
            return true;
        }
        String[] itemSplit = itemID.split("~");
        if (itemSplit.length < 2) {
            itemSplit = new String[] { itemSplit[0], "0" };
        }
        final boolean durab = this.hasDurab(parent_node);
        final ItemStack item = player.getInventory().getItemInHand();
        return item == null || item.getTypeId() != Integer.valueOf(itemSplit[0]) || (item.getDurability() != Short.valueOf(itemSplit[1]) && !durab) || !this.itemIsSafe(item) || !item.getItemMeta().getDisplayName().contains(iName);
    }
    
    public void terminateAllBursts(final Player player) {
        final Collection<Integer> values = this.burst_task_IDs.get(player.getName());
        if (values != null) {
            for (final Integer value : values) {
                Bukkit.getScheduler().cancelTask((int)value);
            }
        }
        this.burst_task_IDs.remove(player.getName());
    }
    
    public void terminateReload(final Player player) {
        final Collection<Integer> values = this.global_reload_IDs.get(player.getName());
        if (values != null) {
            for (final Integer value : values) {
                Bukkit.getScheduler().cancelTask((int)value);
            }
        }
        this.global_reload_IDs.remove(player.getName());
        player.removeMetadata("mark_of_the_reload", (Plugin)this);
    }
    
    public int getAmmoBetweenBrackets(final Player player, final String parent_node, final ItemStack item) {
        final boolean reloadEnable = this.getBoolean(String.valueOf(parent_node) + ".Reload.Enable");
        final boolean dualWield = this.getBoolean(String.valueOf(parent_node) + ".Shooting.Dual_Wield");
        int reloadAmt = this.getInt(String.valueOf(parent_node) + ".Reload.Reload_Amount");
        final String replacer = dualWield ? (String.valueOf(reloadAmt) + " | " + reloadAmt) : String.valueOf(reloadAmt);
        if (dualWield) {
            reloadAmt *= 2;
        }
        final String itemName = this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Name");
        final String attachType = this.getString(String.valueOf(parent_node) + ".Item_Information.Attachments.Type");
        final Pattern ammoBrackets = Pattern.compile("\\«([^»]*)\\»");
        final Matcher bracketChecker = ammoBrackets.matcher(item.getItemMeta().getDisplayName());
        if (bracketChecker.find()) {
            int detectedAmmo = reloadAmt;
            try {
                if (attachType != null) {
                    final int[] ammoReading = this.grabDualAmmo(item);
                    if (attachType.equalsIgnoreCase("main")) {
                        detectedAmmo = ammoReading[0];
                    }
                    else if (attachType.equalsIgnoreCase("accessory")) {
                        detectedAmmo = ammoReading[1];
                    }
                }
                else if (dualWield) {
                    String strInBracks = bracketChecker.group(1);
                    strInBracks = strInBracks.replaceAll(" ", "");
                    final String[] dualAmmo = strInBracks.split("\\|");
                    if (dualAmmo[0].equals(String.valueOf('\u00d7')) || dualAmmo[1].equals(String.valueOf('\u00d7'))) {
                        return 125622;
                    }
                    detectedAmmo = Integer.valueOf(dualAmmo[0]) + Integer.valueOf(dualAmmo[1]);
                }
                else {
                    if (String.valueOf(bracketChecker.group(1)).equals(String.valueOf('\u00d7')) && !reloadEnable) {
                        return 125622;
                    }
                    detectedAmmo = Integer.valueOf(bracketChecker.group(1));
                }
            }
            catch (Exception ex) {
                this.csminion.replaceBrackets(item, String.valueOf(replacer), parent_node);
            }
            if (detectedAmmo > reloadAmt) {
                this.csminion.replaceBrackets(item, String.valueOf(replacer), parent_node);
            }
            return detectedAmmo;
        }
        this.csminion.setItemName(player.getInventory().getItemInHand(), String.valueOf(itemName) + " «" + replacer + "»");
        return reloadAmt;
    }
    
    public void executeCommands(final LivingEntity player, final String parent_node, final String child_node, final String shooter_name, final String victim_name, final String flight_time, final String total_damage, final boolean console) {
        final String[] commands = this.getString(String.valueOf(parent_node) + child_node).split("\\|");
        String[] array;
        for (int length = (array = commands).length, i = 0; i < length; ++i) {
            final String command = array[i];
            if (console) {
                this.getServer().dispatchCommand((CommandSender)this.getServer().getConsoleSender(), this.variableParser(command, shooter_name, victim_name, flight_time, total_damage));
            }
            else {
                ((Player)player).performCommand(this.variableParser(command, shooter_name, victim_name, flight_time, total_damage));
            }
        }
    }
    
    public String variableParser(String filter, final String shooter, final String victim, final String flight_time, final String total_damage) {
        filter = filter.replaceAll("<shooter>", shooter);
        filter = filter.replaceAll("<victim>", victim);
        filter = filter.replaceAll("<damage>", total_damage);
        filter = filter.replaceAll("<flight>", flight_time);
        return filter;
    }
    
    public void sendPlayerMessage(final LivingEntity player, final String parent_node, final String child_node, final boolean enable, final String shooter_name, final String victim_name, final String flight_time, final String total_damage) {
        if (!enable) {
            return;
        }
        final String message = this.getString(String.valueOf(parent_node) + child_node);
        if (message == null) {
            return;
        }
        if (player instanceof Player) {
            ((Player)player).sendMessage(this.variableParser(message, shooter_name, victim_name, flight_time, total_damage));
        }
    }
    
    public boolean spawnEntities(final LivingEntity player, final String parent_node, final String child_node, final LivingEntity tamer) {
        final boolean enabled = this.getBoolean(String.valueOf(parent_node) + ".Spawn_Entity_On_Hit.Enable");
        final boolean targetVictim = this.getBoolean(String.valueOf(parent_node) + ".Spawn_Entity_On_Hit.Make_Entities_Target_Victim");
        final boolean noDrop = this.getBoolean(String.valueOf(parent_node) + ".Spawn_Entity_On_Hit.Entity_Disable_Drops");
        final int timedDeath = this.getInt(String.valueOf(parent_node) + ".Spawn_Entity_On_Hit.Timed_Death");
        final int spawnChance = this.getInt(String.valueOf(parent_node) + ".Spawn_Entity_On_Hit.Chance");
        if (!enabled) {
            return false;
        }
        if (this.getString(String.valueOf(parent_node) + child_node) == null) {
            return false;
        }
        final Random generator = new Random();
        final int Chance = generator.nextInt(100);
        if (Chance <= spawnChance) {
            final String[] entities = this.getString(String.valueOf(parent_node) + child_node).split(",");
            String[] array;
            for (int length = (array = entities).length, j = 0; j < length; ++j) {
                final String entity = array[j];
                final String space_filtered = entity.replace(" ", "");
                final String[] args = space_filtered.split("-");
                if (args.length == 4) {
                    for (int i = 0; i < Integer.parseInt(args[3]); ++i) {
                        try {
                            String mobEnum = args[0].toUpperCase();
                            if (args[0].equals("ZOMBIE_VILLAGER")) {
                                mobEnum = "ZOMBIE";
                            }
                            else if (args[0].equals("WITHER_SKELETON")) {
                                mobEnum = "SKELETON";
                            }
                            else if (args[0].equals("TAMED_WOLF")) {
                                mobEnum = "WOLF";
                            }
                            final LivingEntity spawnedMob = (LivingEntity)player.getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(mobEnum));
                            if (Boolean.valueOf(args[1])) {
                                if (spawnedMob instanceof Zombie) {
                                    ((Zombie)spawnedMob).setBaby(true);
                                }
                                else if (spawnedMob instanceof Creeper) {
                                    ((Creeper)spawnedMob).setPowered(true);
                                }
                                else if (spawnedMob instanceof Ageable) {
                                    ((Ageable)spawnedMob).setBaby();
                                }
                            }
                            if (args[0].equalsIgnoreCase("ZOMBIE_VILLAGER")) {
                                ((Zombie)spawnedMob).setVillager(true);
                            }
                            else if (args[0].equalsIgnoreCase("WITHER_SKELETON")) {
                                ((Skeleton)spawnedMob).setSkeletonType(Skeleton.SkeletonType.WITHER);
                            }
                            else if (args[0].equalsIgnoreCase("TAMED_WOLF") && tamer instanceof AnimalTamer) {
                                ((Wolf)spawnedMob).setOwner((AnimalTamer)tamer);
                            }
                            if (Boolean.parseBoolean(args[2])) {
                                spawnedMob.setMetadata("ka-boom", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
                            }
                            if (noDrop) {
                                spawnedMob.setMetadata("no-drops", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
                            }
                            if (targetVictim) {
                                spawnedMob.damage(0, (Entity)player);
                            }
                            if (timedDeath != 0) {
                                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
                                    @Override
                                    public void run() {
                                        spawnedMob.damage(100);
                                    }
                                }, (long)Long.valueOf(timedDeath));
                            }
                        }
                        catch (IllegalArgumentException ex) {
                            this.printM("'" + args[0] + "' of weapon '" + parent_node + "' is not a valid entity!");
                            break;
                        }
                    }
                }
                else {
                    this.printM("'" + space_filtered + "' of weapon '" + parent_node + "' has an invalid format! The correct format is: EntityType-Baby-ExplodeOnDeath-Amount!");
                }
            }
            return true;
        }
        return false;
    }
    
    @EventHandler
    public void onSpawnedEntityDeath(final EntityDeathEvent event) {
        if (event.getEntity().hasMetadata("ka-boom")) {
            final TNTPrimed tnt = (TNTPrimed)event.getEntity().getWorld().spawn(event.getEntity().getLocation(), (Class)TNTPrimed.class);
            tnt.setYield(2.0f);
            tnt.setFuseTicks(0);
            tnt.setMetadata("nullify", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
        }
        if (event.getEntity().hasMetadata("no-drops")) {
            event.setDroppedExp(0);
            event.getDrops().clear();
        }
    }
    
    @EventHandler
    public void createGunShop(final SignChangeEvent event) {
        if (event.getLine(0).contains("[CS]")) {
            final String filter = event.getLine(0).replaceAll(Pattern.quote("[CS]"), "");
            try {
                Integer.valueOf(filter);
            }
            catch (NumberFormatException ex) {
                return;
            }
            for (final String parent_node : this.parents.keySet()) {
                if (this.getBoolean(String.valueOf(parent_node) + ".SignShops.Enable")) {
                    if (!event.getPlayer().hasPermission("crackshot.shops." + parent_node) && !event.getPlayer().hasPermission("crackshot.shops.all")) {
                        event.getPlayer().sendMessage(String.valueOf(this.heading) + "You do not have permission to create this store.");
                        return;
                    }
                    final Integer gunID = this.getInt(String.valueOf(parent_node) + ".SignShops.Sign_Gun_ID");
                    if (gunID != 0 && gunID.equals(Integer.valueOf(filter))) {
                        event.setLine(0, "§fStore No\u1390 " + gunID);
                        event.getPlayer().sendMessage(String.valueOf(this.heading) + "Store successfully created!");
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    @EventHandler
    public void onSignUse(final PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.WALL_SIGN) {
            final Sign signState = (Sign)event.getClickedBlock().getState();
            if (signState.getLine(0).contains("§fStore No\u1390")) {
                final String signLineOne = signState.getLine(0).replaceAll(Pattern.quote("§fStore No\u1390 "), "");
                try {
                    Integer.valueOf(signLineOne);
                }
                catch (NumberFormatException ex) {
                    return;
                }
                for (final String parent_node : this.parents.keySet()) {
                    if (this.getBoolean(String.valueOf(parent_node) + ".SignShops.Enable") && this.getString(String.valueOf(parent_node) + ".SignShops.Price") != null) {
                        final Integer gunID = this.getInt(String.valueOf(parent_node) + ".SignShops.Sign_Gun_ID");
                        final String[] signInfo = this.getString(String.valueOf(parent_node) + ".SignShops.Price").split("-");
                        try {
                            final int currency = Integer.valueOf(signInfo[0]);
                            final int amount = Integer.valueOf(signInfo[1]);
                            if (!gunID.equals(Integer.valueOf(signLineOne))) {
                                continue;
                            }
                            final Player player = event.getPlayer();
                            if (player.hasPermission("crackshot.buy." + parent_node) || player.hasPermission("crackshot.buy.all")) {
                                if (player.getGameMode() != GameMode.CREATIVE) {
                                    if (this.csminion.countItemStacks(player, signInfo[0]) < amount) {
                                        player.sendMessage(String.valueOf(this.heading) + "You cannot afford to purchase this item.");
                                        player.sendMessage(String.valueOf(this.heading) + "You need " + amount + " " + String.valueOf(Material.getMaterial(currency)) + ".");
                                        return;
                                    }
                                    if (player.getInventory().firstEmpty() != -1) {
                                        this.csminion.removeNamedItem(player, signInfo[0], amount, parent_node);
                                        this.csminion.getWeaponCommand(player, parent_node, false, null, false, false);
                                        String milk = this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Name");
                                        milk = new StringBuffer(milk).insert(2, "§r§7").toString();
                                        player.sendMessage(String.valueOf(this.heading) + "Item purchased - " + milk);
                                    }
                                }
                            }
                            else {
                                player.sendMessage(String.valueOf(this.heading) + "You do not have permission to purchase this item.");
                            }
                            if (player.getGameMode() == GameMode.CREATIVE) {
                                break;
                            }
                            if (player.hasPermission("crackshot.store." + parent_node) || player.hasPermission("crackshot.store.all")) {
                                return;
                            }
                            event.setCancelled(true);
                            break;
                        }
                        catch (Exception ex2) {
                            this.printM("'" + signInfo + "' of weapon '" + parent_node + "' needs some double-checking! The correct format is: ItemID-Amount!");
                        }
                    }
                }
            }
        }
    }
    
    public boolean checkBoltPosition(final Player player, final String parent_node) {
        final String actType = this.getString(String.valueOf(parent_node) + ".Firearm_Action.Type");
        if (actType == null) {
            return false;
        }
        final String[] validTypes = { "bolt", "lever", "pump", "break", "revolver", "slide" };
        if (this.getBoolean(String.valueOf(parent_node) + ".Shooting.Dual_Wield")) {
            return false;
        }
        String[] array;
        for (int length = (array = validTypes).length, i = 0; i < length; ++i) {
            final String str = array[i];
            if (actType.equalsIgnoreCase(str)) {
                break;
            }
            if (str.equals("slide")) {
                this.printM("'" + actType + "' of weapon '" + parent_node + "' is not a valid firearm action! The accepted values are slide, bolt, lever, pump, break or revolver!");
                return false;
            }
        }
        final int openTime = this.getInt(String.valueOf(parent_node) + ".Firearm_Action.Open_Duration");
        final int closeTime = this.getInt(String.valueOf(parent_node) + ".Firearm_Action.Close_Duration");
        final ItemStack item = player.getInventory().getItemInHand();
        if (!this.itemIsSafe(item)) {
            return false;
        }
        final String itemName = item.getItemMeta().getDisplayName();
        if (actType.toLowerCase().contains("break") || actType.toLowerCase().contains("revolver") || actType.toLowerCase().contains("slide")) {
            final int detected_ammo = this.getAmmoBetweenBrackets(player, parent_node, item);
            if (detected_ammo <= 0) {
                this.reloadAnimation(player, parent_node);
                final boolean ammoEnable = this.getBoolean(String.valueOf(parent_node) + ".Ammo.Enable");
                final String ammoInfo = this.getString(String.valueOf(parent_node) + ".Ammo.Ammo_Item_ID");
                final boolean takeAmmo = this.getBoolean(String.valueOf(parent_node) + ".Reload.Take_Ammo_On_Reload");
                if (ammoEnable && takeAmmo && !this.csminion.containsItemStack(player, ammoInfo, 1)) {
                    this.playSoundEffects((Entity)player, parent_node, ".Ammo.Sounds_Shoot_With_No_Ammo", false);
                }
                return true;
            }
            if (itemName.contains("\u25ab")) {
                this.correctBoltPosition(player, parent_node, false, closeTime, false, false, false, true);
                return true;
            }
            return false;
        }
        else {
            if (itemName.contains("\u25aa")) {
                this.csminion.setItemName(item, itemName.replaceAll("\u25aa", "\u25ab"));
                this.correctBoltPosition(player, parent_node, true, openTime, false, false, false, false);
                return false;
            }
            if (!itemName.contains("\u25ab") && !itemName.contains("\u06d4")) {
                this.csminion.setItemName(item, itemName.replaceAll("«", "\u25aa «"));
                return true;
            }
            if (itemName.contains("\u25ab")) {
                this.correctBoltPosition(player, parent_node, true, openTime, false, false, false, false);
                return true;
            }
            if (itemName.contains("\u06d4")) {
                this.correctBoltPosition(player, parent_node, false, closeTime, false, false, false, false);
                return true;
            }
            return true;
        }
    }
    
    public void correctBoltPosition(final Player player, final String parent_node, final boolean boltPull, final int delay, final boolean reloadPrep, final boolean reloadFin, final boolean pumpExit, final boolean breakAct) {
        final String actType = this.getString(String.valueOf(parent_node) + ".Firearm_Action.Type");
        if (actType == null) {
            return;
        }
        final String[] validTypes = { "bolt", "lever", "pump", "break", "revolver", "slide" };
        if (this.getBoolean(String.valueOf(parent_node) + ".Shooting.Dual_Wield")) {
            return;
        }
        String[] array;
        for (int length = (array = validTypes).length, i = 0; i < length; ++i) {
            final String str = array[i];
            if (actType.equalsIgnoreCase(str)) {
                break;
            }
            if (str.equals("slide")) {
                this.printM("'" + actType + "' of weapon '" + parent_node + "' is not a valid firearm action! The accepted values are slide, bolt, lever, pump, break or revolver!");
                return;
            }
        }
        if (player.hasMetadata("fiddling")) {
            return;
        }
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrackShot");
        player.setMetadata("fiddling", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                player.removeMetadata("fiddling", plugin);
                final ItemStack item = player.getInventory().getItemInHand();
                final String itemID = CSDirector.this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Type");
                final String officialName = CSDirector.this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Name");
                final int closeTime = CSDirector.this.getInt(String.valueOf(parent_node) + ".Firearm_Action.Close_Duration");
                if (!CSDirector.this.itemIsSafe(item)) {
                    return;
                }
                final String itemName = item.getItemMeta().getDisplayName();
                if (itemName.contains(String.valueOf('\u25b6'))) {
                    return;
                }
                if (itemName.contains("\u1d3f")) {
                    return;
                }
                if (CSDirector.this.switchedTheItem(player, officialName, itemID, parent_node)) {
                    return;
                }
                if (reloadFin && itemName.contains("\u25aa")) {
                    return;
                }
                if (breakAct) {
                    String filter = itemName;
                    CSDirector.this.playSoundEffects((Entity)player, parent_node, ".Firearm_Action.Sound_Close", false);
                    filter = filter.replaceAll("\u25ab", "\u25aa");
                    CSDirector.this.csminion.setItemName(item, filter);
                    CSDirector.this.noShootPeriod(player, parent_node, 4);
                    return;
                }
                if (pumpExit && itemName.contains("\u25ab")) {
                    CSDirector.this.correctBoltPosition(player, parent_node, true, 0, false, false, false, false);
                    return;
                }
                if (reloadPrep) {
                    String firstFilter = itemName.replaceAll("\u25aa", "\u25ab");
                    if (!actType.equalsIgnoreCase("break") && !actType.equalsIgnoreCase("revolver")) {
                        firstFilter = firstFilter.replaceAll("\u25ab", "\u06d4");
                    }
                    if (!itemName.contains("\u1d3f")) {
                        CSDirector.this.csminion.setItemName(item, String.valueOf(firstFilter) + '\u1d3f');
                    }
                    else {
                        CSDirector.this.csminion.setItemName(item, firstFilter);
                    }
                    CSDirector.this.playSoundEffects((Entity)player, parent_node, ".Firearm_Action.Sound_Open", false);
                    CSDirector.this.reloadAnimation(player, parent_node);
                    return;
                }
                if (boltPull) {
                    CSDirector.this.playSoundEffects((Entity)player, parent_node, ".Firearm_Action.Sound_Open", false);
                    CSDirector.this.csminion.setItemName(item, itemName.replaceAll("\u25ab", "\u06d4"));
                    CSDirector.this.correctBoltPosition(player, parent_node, false, closeTime, false, false, false, false);
                }
                else {
                    if (actType.equalsIgnoreCase("slide") && itemName.contains("\u25ab")) {
                        CSDirector.this.csminion.setItemName(item, itemName.replaceAll("\u25ab", "\u25aa"));
                        CSDirector.this.playSoundEffects((Entity)player, parent_node, ".Firearm_Action.Sound_Close", false);
                        CSDirector.this.noShootPeriod(player, parent_node, 4);
                        return;
                    }
                    final int detected_ammo = CSDirector.this.getAmmoBetweenBrackets(player, parent_node, item);
                    if (detected_ammo <= 0) {
                        CSDirector.this.reloadAnimation(player, parent_node);
                        return;
                    }
                    String filter2 = itemName;
                    filter2 = filter2.replaceAll("\u06d4", "\u25aa");
                    CSDirector.this.csminion.setItemName(item, filter2);
                    CSDirector.this.playSoundEffects((Entity)player, parent_node, ".Firearm_Action.Sound_Close", false);
                    CSDirector.this.noShootPeriod(player, parent_node, 4);
                    if (detected_ammo == 125622) {
                        return;
                    }
                    CSDirector.this.ammoOperation(player, parent_node, detected_ammo, item);
                }
            }
        }, (long)Long.valueOf(delay));
    }
    
    public void ammoOperation(final Player player, final String parent_node, int detectedAmmo, final ItemStack item) {
        final boolean ammoEnable = this.getBoolean(String.valueOf(parent_node) + ".Ammo.Enable");
        final String ammoInfo = this.getString(String.valueOf(parent_node) + ".Ammo.Ammo_Item_ID");
        final boolean takeAmmo = this.getBoolean(String.valueOf(parent_node) + ".Ammo.Take_Ammo_Per_Shot");
        --detectedAmmo;
        this.csminion.replaceBrackets(item, String.valueOf(detectedAmmo), parent_node);
        if (ammoEnable && takeAmmo) {
            this.csminion.removeNamedItem(player, ammoInfo, 1, parent_node);
        }
        if (detectedAmmo == 0) {
            final String actType = this.getString(String.valueOf(parent_node) + ".Firearm_Action.Type");
            this.playSoundEffects((Entity)player, parent_node, ".Reload.Sounds_Out_Of_Ammo", false);
            if (!this.itemIsSafe(item)) {
                return;
            }
            final String itemName = item.getItemMeta().getDisplayName();
            if (actType != null) {
                if (actType.equalsIgnoreCase("bolt") || actType.equalsIgnoreCase("lever") || actType.equalsIgnoreCase("pump")) {
                    if (!itemName.contains("\u25aa")) {
                        this.delayedReload(player, parent_node);
                    }
                }
                else if (actType.equalsIgnoreCase("break") || actType.equalsIgnoreCase("revolver") || actType.equalsIgnoreCase("slide")) {
                    if (actType.toLowerCase().contains("slide") && itemName.contains("\u25aa")) {
                        this.playSoundEffects((Entity)player, parent_node, ".Firearm_Action.Sound_Open", false);
                        this.csminion.setItemName(item, itemName.replaceAll("\u25aa", "\u25ab"));
                    }
                    this.delayedReload(player, parent_node);
                }
            }
            else {
                this.delayedReload(player, parent_node);
            }
        }
    }
    
    public boolean ammoSpecOps(final Player player, final String parent_node, final int detectedAmmo, final ItemStack item, final boolean leftClick) {
        final boolean ammoEnable = this.getBoolean(String.valueOf(parent_node) + ".Ammo.Enable");
        final boolean takeAmmo = this.getBoolean(String.valueOf(parent_node) + ".Ammo.Take_Ammo_Per_Shot");
        final String ammoInfo = this.getString(String.valueOf(parent_node) + ".Ammo.Ammo_Item_ID");
        final int[] ammoReading = this.grabDualAmmo(item);
        int ammoAmount;
        if (leftClick) {
            if (ammoReading[0] <= 0) {
                this.playSoundEffects((Entity)player, parent_node, ".Reload.Dual_Wield.Sounds_Shoot_With_No_Ammo", false);
                return false;
            }
            ammoAmount = ammoReading[0] - 1;
            this.csminion.replaceBrackets(item, String.valueOf(ammoAmount) + " | " + ammoReading[1], parent_node);
        }
        else {
            if (ammoReading[1] <= 0) {
                this.playSoundEffects((Entity)player, parent_node, ".Reload.Dual_Wield.Sounds_Shoot_With_No_Ammo", false);
                return false;
            }
            ammoAmount = ammoReading[1] - 1;
            this.csminion.replaceBrackets(item, String.valueOf(ammoReading[0]) + " | " + ammoAmount, parent_node);
        }
        if (ammoAmount <= 0) {
            this.playSoundEffects((Entity)player, parent_node, ".Reload.Sounds_Out_Of_Ammo", false);
        }
        if (ammoEnable && takeAmmo) {
            this.csminion.removeNamedItem(player, ammoInfo, 1, parent_node);
        }
        if (detectedAmmo - 1 == 0) {
            this.reloadAnimation(player, parent_node);
        }
        return true;
    }
    
    public int[] grabDualAmmo(final ItemStack item) {
        final Pattern ammoBrackets = Pattern.compile("\\«([^»]*)\\»");
        final Matcher bracketChecker = ammoBrackets.matcher(item.getItemMeta().getDisplayName());
        if (bracketChecker.find()) {
            try {
                final String strInBracks = bracketChecker.group(1);
                final String[] dualAmmo = strInBracks.split(" ");
                int leftGun;
                if (dualAmmo[0].equals(String.valueOf('\u00d7'))) {
                    leftGun = 1;
                }
                else {
                    leftGun = Integer.valueOf(dualAmmo[0]);
                }
                int rightGun;
                if (dualAmmo[2].equals(String.valueOf('\u00d7'))) {
                    rightGun = 1;
                }
                else {
                    rightGun = Integer.valueOf(dualAmmo[2]);
                }
                return new int[] { leftGun, rightGun };
            }
            catch (Exception ex) {
                return new int[2];
            }
        }
        return new int[2];
    }
    
    @EventHandler
    public void explosiveTipCrossbow(final EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player shooter = (Player)event.getEntity();
            final String parent_node = this.returnParentNode(shooter);
            if (parent_node == null) {
                return;
            }
            event.setCancelled(true);
            if (event.getForce() != 1.0f) {
                return;
            }
            if (!this.regionAndPermCheck(shooter, parent_node, false)) {
                return;
            }
            this.csminion.weaponInteraction(shooter, parent_node, false);
        }
    }
    
    public String returnParentNode(final Player player) {
        for (final String parentNode : this.parents.keySet()) {
            final ItemStack item = player.getInventory().getItemInHand();
            final boolean durab = this.hasDurab(parentNode);
            final String itemType = this.getString(String.valueOf(parentNode) + ".Item_Information.Item_Type");
            if (item != null && itemType != null) {
                String[] itemID = itemType.split("~");
                if (itemID.length < 2) {
                    itemID = new String[] { itemID[0], "0" };
                }
                if (item.getTypeId() != Integer.valueOf(itemID[0]) || (item.getDurability() != Short.valueOf(itemID[1]) && !durab)) {
                    continue;
                }
                final String itemName = this.parents.get(parentNode);
                final String enchantKey = this.getString(String.valueOf(parentNode) + ".Item_Information.Enchantment_To_Check");
                if (!this.csminion.hasAnItemNamed(player, itemName, null)) {
                    final boolean skipName = this.getBoolean(String.valueOf(parentNode) + ".Item_Information.Skip_Name_Check");
                    if (this.csminion.holdingEnchantedItem(player, enchantKey, null) || skipName) {
                        if (!this.regionAndPermCheck(player, parentNode, false)) {
                            return null;
                        }
                        this.csminion.removeEnchantments(item);
                        final ItemStack weapon = this.csminion.vendingMachine(parentNode);
                        weapon.setAmount(player.getItemInHand().getAmount());
                        player.setItemInHand(weapon);
                    }
                }
                if (!this.csminion.hasAnItemNamed(player, itemName, null)) {
                    continue;
                }
                if (player.getItemInHand().getItemMeta().getDisplayName().contains(String.valueOf('\u25b6'))) {
                    final String attachInfo = this.getString(String.valueOf(parentNode) + ".Item_Information.Attachments.Info");
                    return attachInfo;
                }
                return parentNode;
            }
        }
        return null;
    }
    
    @EventHandler
    public void onCraft(final CraftItemEvent event) {
        for (final String parent_node : this.parents.keySet()) {
            if (this.getBoolean(String.valueOf(parent_node) + ".Crafting.Enable")) {
                final ItemStack weapon = this.csminion.vendingMachine(parent_node);
                if (!event.getRecipe().getResult().isSimilar(weapon)) {
                    continue;
                }
                if (!(event.getWhoClicked() instanceof Player)) {
                    break;
                }
                final Player crafter = (Player)event.getWhoClicked();
                if (!crafter.hasPermission("crackshot.craft." + parent_node) && !crafter.hasPermission("crackshot.craft.all")) {
                    event.setCancelled(true);
                    crafter.sendMessage(String.valueOf(this.heading) + "You do not have permission to craft this.");
                    break;
                }
                break;
            }
        }
    }
    
    public boolean giveWeapon(final Player receiver, final String parentNode, final int amount) {
        boolean retVal = false;
        if (receiver != null && receiver.getInventory().firstEmpty() != -1) {
            this.csminion.getWeaponCommand(receiver, parentNode, false, String.valueOf(amount), true, true);
            retVal = true;
        }
        return retVal;
    }
    
    void printM(final String msg) {
        System.out.print("[CrackShot] " + msg);
    }
    
    public double getDouble(final String nodes) {
        final Double result = this.dubs.get(nodes);
        return (result != null) ? result : 0.0;
    }
    
    public boolean getBoolean(final String nodes) {
        final Boolean result = this.bools.get(nodes);
        return result != null && result;
    }
    
    public int getInt(final String nodes) {
        final Integer result = this.ints.get(nodes);
        return (result != null) ? result : 0;
    }
    
    public String getString(final String nodes) {
        final String result = this.strings.get(nodes);
        return (result != null) ? result : null;
    }
    
    public boolean hasDurab(final String nodes) {
        final Boolean result = this.morobust.get(nodes);
        return result != null && result;
    }
    
    public boolean regionAndPermCheck(final Player shooter, final String parent_node, final boolean noMsg) {
        String[] disWorlds;
        for (int length = (disWorlds = this.disWorlds).length, i = 0; i < length; ++i) {
            final String worName = disWorlds[i];
            if (worName == null) {
                break;
            }
            final World world = Bukkit.getWorld(worName);
            if (world == shooter.getWorld()) {
                return false;
            }
        }
        if (!shooter.hasPermission("crackshot.use." + parent_node) && !shooter.hasPermission("crackshot.use.all")) {
            if (!noMsg) {
                shooter.sendMessage(String.valueOf(this.heading) + "You do not have permission to use this.");
            }
            return false;
        }
        if (!shooter.hasPermission("crackshot.bypass." + parent_node) && !shooter.hasPermission("crackshot.bypass.all") && !this.csminion.regionCheck((Entity)shooter, parent_node)) {
            if (!noMsg && this.getString(String.valueOf(parent_node) + ".Region_Check.Message_Of_Denial") != null) {
                shooter.sendMessage(this.getString(String.valueOf(parent_node) + ".Region_Check.Message_Of_Denial"));
            }
            return false;
        }
        return true;
    }
    
    @EventHandler
    public void onEggSplat(final PlayerEggThrowEvent event) {
        if (event.getEgg().hasMetadata("CS_Hardboiled")) {
            event.setHatching(false);
        }
    }
    
    public void noShootPeriod(final Player player, final String parent_node, final int wait) {
        final int gun_slot = player.getInventory().getHeldItemSlot();
        player.setMetadata(String.valueOf(parent_node) + "shootDelay" + gun_slot + "true", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
        player.setMetadata(String.valueOf(parent_node) + "shootDelay" + gun_slot + "false", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)true));
        this.csminion.tempVars(player, String.valueOf(parent_node) + "shootDelay" + gun_slot + "true", (long)wait);
        this.csminion.tempVars(player, String.valueOf(parent_node) + "shootDelay" + gun_slot + "false", (long)wait);
    }
    
    public void launchGrenade(final Player player, final String parent_node, int delay, final Vector vel, final Location splitLoc, final int cTimes) {
        final boolean cEnable = this.getBoolean(String.valueOf(parent_node) + ".Cluster_Bombs.Enable");
        final int cOfficialTimes = this.getInt(String.valueOf(parent_node) + ".Cluster_Bombs.Number_Of_Splits");
        String itemType = this.getString(String.valueOf(parent_node) + ".Shooting.Projectile_Subtype");
        String nodeName = "Projectile_Subtype:";
        if (cEnable && cTimes != 0) {
            nodeName = "Bomblet_Type:";
            itemType = this.getString(String.valueOf(parent_node) + ".Cluster_Bombs.Bomblet_Type");
        }
        if (itemType == null) {
            player.sendMessage(String.valueOf(this.heading) + "The '" + nodeName + "' node of '" + parent_node + "' has not been defined.");
            return;
        }
        final ItemStack item = this.csminion.parseItemStack(itemType);
        if (item == null) {
            player.sendMessage(String.valueOf(this.heading) + "The '" + nodeName + "' node of '" + parent_node + "' has an incorrect value.");
            return;
        }
        Location loc = player.getEyeLocation();
        if (splitLoc != null) {
            loc = splitLoc;
        }
        final Entity grenade = (Entity)player.getWorld().dropItem(loc, item);
        grenade.setVelocity(vel);
        ((Item)grenade).setPickupDelay(delay + 20);
        final ItemStack grenStack = ((Item)grenade).getItemStack();
        this.csminion.setItemName(grenStack, "\u0aee" + String.valueOf(grenade.getUniqueId()));
        ((Item)grenade).setItemStack(grenStack);
        final boolean airstrike = this.getBoolean(String.valueOf(parent_node) + ".Airstrikes.Enable");
        final int cDelay = this.getInt(String.valueOf(parent_node) + ".Cluster_Bombs.Delay_Before_Split");
        final int cDelayDiff = this.getInt(String.valueOf(parent_node) + ".Cluster_Bombs.Detonation_Delay_Variation");
        if (cEnable && !airstrike && cTimes < cOfficialTimes) {
            if (cTimes == 0) {
                this.playSoundEffects(grenade, parent_node, ".Shooting.Sounds_Projectile", false);
            }
            delay = cDelay;
        }
        else if (cEnable) {
            if (cDelay != 0 && cDelayDiff != 0) {
                final Random r = new Random();
                delay += r.nextInt(cDelayDiff) - r.nextInt(cDelayDiff);
            }
        }
        else {
            this.playSoundEffects(grenade, parent_node, ".Shooting.Sounds_Projectile", false);
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                final boolean zapEnable = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Lightning.Enable");
                final boolean zapNoDam = CSDirector.this.getBoolean(String.valueOf(parent_node) + ".Lightning.No_Damage");
                if (!airstrike) {
                    if (zapEnable) {
                        if (zapNoDam) {
                            grenade.getWorld().strikeLightningEffect(grenade.getLocation());
                        }
                        else {
                            grenade.getWorld().strikeLightning(grenade.getLocation());
                        }
                    }
                    CSDirector.this.projectileExplosion(grenade, parent_node, true, player, true, false, null, null, false, cTimes);
                }
                else {
                    CSDirector.this.csminion.callAirstrike(grenade, parent_node, player);
                }
                grenade.remove();
            }
        }, (long)Long.valueOf(delay));
    }
    
    @EventHandler
    public void onAnyDamage(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }
        final Player shooter = (Player)event.getEntity();
        final String parent_node = this.returnParentNode(shooter);
        if (parent_node == null) {
            return;
        }
        if (this.getBoolean(String.valueOf(parent_node) + ".Abilities.No_Fall_Damage")) {
            event.setCancelled(true);
        }
    }
    
    public void delayedReload(final Player player, final String parent_node) {
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                CSDirector.this.reloadAnimation(player, parent_node);
            }
        }, 1L);
    }
    
    public String[] itemParentNode(final ItemStack item, final Player player) {
        for (final String parent_node : this.parents.keySet()) {
            if (this.regionAndPermCheck(player, parent_node, true)) {
                final boolean durab = this.hasDurab(parent_node);
                final String item_b = this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Type");
                if (item_b == null) {
                    continue;
                }
                String[] item_ID = item_b.split("~");
                if (item_ID.length < 2) {
                    item_ID = new String[] { item_ID[0], "0" };
                }
                if (item == null || (item.getDurability() != Short.valueOf(item_ID[1]) && !durab) || item.getTypeId() != Integer.valueOf(item_ID[0])) {
                    continue;
                }
                final String item_name = this.parents.get(parent_node);
                final String enchantKey = this.getString(String.valueOf(parent_node) + ".Item_Information.Enchantment_To_Check");
                if (!this.csminion.hasAnItemNamed(null, item_name, item)) {
                    final boolean skipName = this.getBoolean(String.valueOf(parent_node) + ".Item_Information.Skip_Name_Check");
                    if (this.csminion.holdingEnchantedItem(null, enchantKey, item) || skipName) {
                        this.csminion.removeEnchantments(item);
                        return new String[] { parent_node, "true" };
                    }
                }
                if (!this.csminion.hasAnItemNamed(null, item_name, item)) {
                    continue;
                }
                if (item.getItemMeta().getDisplayName().contains(String.valueOf('\u25b6'))) {
                    final String attachInfo = this.getString(String.valueOf(parent_node) + ".Item_Information.Attachments.Info");
                    return new String[] { attachInfo, "false" };
                }
                return new String[] { parent_node, "false" };
            }
        }
        return null;
    }
    
    @EventHandler
    public void onPickUp(final PlayerPickupItemEvent event) {
        if (this.csminion.fastenSeatbelts(event.getItem()) != null) {
            this.csminion.happilyEverAfter(event.getItem());
            event.setCancelled(true);
            if (!(event.getItem().getVehicle() instanceof Minecart)) {
                event.getItem().remove();
            }
        }
        else {
            final ItemStack item = event.getItem().getItemStack();
            if (this.itemIsSafe(item) && item.getItemMeta().getDisplayName().contains("\u0aee")) {
                event.setCancelled(true);
                event.getItem().remove();
                return;
            }
            for (final String name : this.boobs.keySet()) {
                if (this.itemIsSafe(item) && item.getItemMeta().getDisplayName().contains(name)) {
                    final String parent_node = this.boobs.get(name);
                    if (!this.csminion.getBoobean(2, parent_node)) {
                        return;
                    }
                    final Pattern ammo_brackets = Pattern.compile("\\«([^»]*)\\»");
                    final Matcher bracket_detector = ammo_brackets.matcher(item.getItemMeta().getDisplayName());
                    if (!bracket_detector.find()) {
                        continue;
                    }
                    final Player picker = event.getPlayer();
                    final String detectedName = bracket_detector.group(1);
                    if (detectedName.equals("?")) {
                        return;
                    }
                    final Player planter = Bukkit.getServer().getPlayer(detectedName);
                    if (planter == picker) {
                        return;
                    }
                    event.getItem().setPickupDelay(60);
                    this.slapAndReaction(picker, planter, event.getItem().getLocation().getBlock(), parent_node, null, null, detectedName, event.getItem());
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler
    public void onItemSpawn(final ItemSpawnEvent event) {
        if (event.getEntity().getItemStack().getType() == Material.SKULL_ITEM) {
            final SkullMeta skull = (SkullMeta)event.getEntity().getItemStack().getItemMeta();
            if (skull.hasOwner() && skull.getOwner().contains("\u060c")) {
                event.setCancelled(true);
                event.getEntity().remove();
            }
        }
    }
    
    @EventHandler
    public void onShotgun(final PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Minecart) {
            this.csminion.happilyEverAfter((Vehicle)event.getRightClicked());
            if (event.getRightClicked().getPassenger() instanceof Item) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void tagDespawn(final ItemDespawnEvent event) {
        if (this.csminion.fastenSeatbelts(event.getEntity()) != null) {
            event.setCancelled(true);
        }
        final ItemStack item = event.getEntity().getItemStack();
        for (final String name : this.boobs.keySet()) {
            if (this.itemIsSafe(item) && item.getItemMeta().getDisplayName().contains(name)) {
                final String parent_node = this.boobs.get(name);
                if (!this.csminion.getBoobean(1, parent_node)) {
                    return;
                }
                final boolean durab = this.hasDurab(parent_node);
                final String item_b = this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Type");
                if (item_b == null) {
                    continue;
                }
                String[] item_ID = item_b.split("~");
                if (item_ID.length < 2) {
                    item_ID = new String[] { item_ID[0], "0" };
                }
                if ((item.getDurability() != Short.valueOf(item_ID[1]) && !durab) || item.getTypeId() != Integer.valueOf(item_ID[0]) || !this.csminion.getBoobean(5, parent_node)) {
                    continue;
                }
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onMobShotgun(final VehicleEnterEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            this.csminion.happilyEverAfter(event.getVehicle());
            if (event.getVehicle().getPassenger() instanceof Item) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onBoatMine(final VehicleEntityCollisionEvent event) {
        if (!(event.getVehicle() instanceof Minecart)) {
            return;
        }
        this.csminion.happilyEverAfter(event.getVehicle());
        if (event.getVehicle().getPassenger() instanceof Item && event.getEntity() instanceof LivingEntity) {
            final Entity victim = event.getEntity();
            final Item psngr = (Item)event.getVehicle().getPassenger();
            final String[] seagull_info = this.csminion.fastenSeatbelts(psngr);
            if (seagull_info == null) {
                return;
            }
            event.setCancelled(true);
            final Player fisherman = Bukkit.getServer().getPlayer(seagull_info[1]);
            if (fisherman != null && victim instanceof Player) {
                if (((Player)victim).getName().equals(seagull_info[1])) {
                    event.setCancelled(false);
                }
                else {
                    this.csminion.callAndResponse((Player)victim, fisherman, event.getVehicle(), seagull_info, false);
                }
            }
            else {
                this.csminion.mineAction(event.getVehicle(), seagull_info, fisherman, false, victim.getType().getName(), victim);
            }
        }
    }
    
    @EventHandler
    public void onBoatMineShoot(final VehicleDamageEvent event) {
        if (!(event.getVehicle() instanceof Minecart)) {
            return;
        }
        this.csminion.happilyEverAfter(event.getVehicle());
        if (event.getVehicle().getPassenger() instanceof Item) {
            final Entity attacker = event.getAttacker();
            final Item psngr = (Item)event.getVehicle().getPassenger();
            final String[] seagull_info = this.csminion.fastenSeatbelts(psngr);
            if (seagull_info == null) {
                return;
            }
            event.setCancelled(true);
            final Player fisherman = Bukkit.getServer().getPlayer(seagull_info[1]);
            if (attacker instanceof Player) {
                if (((Player)attacker).getName().equals(seagull_info[1])) {
                    this.csminion.mineAction(event.getVehicle(), seagull_info, fisherman, true, null, attacker);
                }
                else {
                    this.csminion.callAndResponse((Player)attacker, fisherman, event.getVehicle(), seagull_info, true);
                }
            }
            else {
                this.csminion.mineAction(event.getVehicle(), seagull_info, fisherman, true, null, attacker);
            }
        }
    }
    
    public void deployMine(final Player player, final String parent_node) {
        final ItemStack fuseItem = this.csminion.parseItemStack(this.getString(String.valueOf(parent_node) + ".Explosive_Devices.Device_Info"));
        if (fuseItem == null) {
            player.sendMessage(String.valueOf(this.heading) + "No valid item-ID for 'Device_Info' of the weapon '" + parent_node + "' has been provided.");
            return;
        }
        final Entity mine = player.getWorld().spawnEntity(player.getLocation(), EntityType.MINECART);
        final ItemMeta meta_psngr = fuseItem.getItemMeta();
        meta_psngr.setDisplayName("§cS3AGULLL~" + player.getName() + "~" + parent_node + "~" + mine.getUniqueId().toString());
        fuseItem.setItemMeta(meta_psngr);
        final Entity fusePassenger = (Entity)player.getWorld().dropItem(player.getLocation(), fuseItem);
        mine.setPassenger(fusePassenger);
    }
    
    @EventHandler
    public void airstrikeKaboom(final EntityChangeBlockEvent event) {
        if (event.getEntity().hasMetadata("CS_strike")) {
            final Entity bomb = event.getEntity();
            final String info = bomb.getMetadata("CS_strike").get(0).asString();
            final String[] parsedInfo = info.split("~");
            final Player player = Bukkit.getServer().getPlayer(parsedInfo[1]);
            this.projectileExplosion(bomb, parsedInfo[0], false, player, true, false, null, null, false, 0);
            bomb.remove();
            event.setCancelled(true);
        }
        else if (event.getEntity().hasMetadata("CS_shrapnel")) {
            event.getEntity().remove();
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onC4Place(final BlockPlaceEvent event) {
        final Player placer = event.getPlayer();
        final String[] parent_node = this.itemParentNode(event.getItemInHand(), placer);
        if (parent_node == null) {
            return;
        }
        if (!this.regionAndPermCheck(placer, parent_node[0], false)) {
            return;
        }
        if (!this.getBoolean(String.valueOf(parent_node[0]) + ".Explosive_Devices.Enable")) {
            return;
        }
        placer.updateInventory();
        final String type = this.getString(String.valueOf(parent_node[0]) + ".Explosive_Devices.Device_Type");
        if (type == null || !type.toLowerCase().contains("remote")) {
            return;
        }
        if (this.itemIsSafe(event.getItemInHand()) && event.getItemInHand().getItemMeta().getDisplayName().contains("«0»")) {
            event.setCancelled(true);
            return;
        }
        final boolean placeAnywhere = this.getBoolean(String.valueOf(parent_node[0]) + ".Explosive_Devices.Remote_Place_Anywhere");
        final boolean facepalmed = event.isCancelled();
        event.setCancelled(true);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                if (!facepalmed || (placeAnywhere && event.canBuild())) {
                    final Block block = event.getBlockPlaced();
                    block.setType(Material.SKULL);
                    block.setData((byte)1);
                    final BlockState state = block.getState();
                    if (state instanceof Skull) {
                        try {
                            int capacity = 0;
                            String uniqueID = null;
                            final Skull skull = (Skull)state;
                            skull.setSkullType(SkullType.PLAYER);
                            final String[] refinedOre = CSDirector.this.csminion.returnRefinedOre(placer, parent_node[0]);
                            if (refinedOre != null) {
                                capacity = Integer.valueOf(refinedOre[0]);
                                uniqueID = refinedOre[1];
                            }
                            String storedOwner = placer.getName();
                            if (storedOwner.length() > 13) {
                                storedOwner = String.valueOf(storedOwner.substring(0, 12)) + '\u0638';
                            }
                            skull.setOwner(String.valueOf(uniqueID) + "\u060c" + storedOwner);
                            skull.setRotation(CSDirector.this.getBlockDirection(placer.getLocation().getYaw()));
                            skull.update(true);
                            final String world = placer.getWorld().getName();
                            final String x = String.valueOf(block.getLocation().getBlockX());
                            final String y = String.valueOf(block.getLocation().getBlockY());
                            final String z = String.valueOf(block.getLocation().getBlockZ());
                            final ItemStack detonator = event.getItemInHand();
                            final boolean ammoEnable = CSDirector.this.getBoolean(String.valueOf(parent_node[0]) + ".Ammo.Enable");
                            final String ammoInfo = CSDirector.this.getString(String.valueOf(parent_node[0]) + ".Ammo.Ammo_Item_ID");
                            final boolean takeAmmo = CSDirector.this.getBoolean(String.valueOf(parent_node[0]) + ".Ammo.Take_Ammo_Per_Shot");
                            final String itemName = CSDirector.this.getString(String.valueOf(parent_node[0]) + ".Item_Information.Item_Name");
                            final Pattern ammo_brackets = Pattern.compile("\\«([^»]*)\\»");
                            final Matcher bracket_detector = ammo_brackets.matcher(detonator.getItemMeta().getDisplayName());
                            if (!bracket_detector.find()) {
                                CSDirector.this.csminion.setItemName(placer.getInventory().getItemInHand(), String.valueOf(itemName) + " «" + capacity + "»");
                                block.setType(Material.AIR);
                                return;
                            }
                            int detected_ammo = 0;
                            try {
                                detected_ammo = Integer.valueOf(bracket_detector.group(1));
                            }
                            catch (NumberFormatException ex) {}
                            if (detected_ammo <= 0) {
                                block.setType(Material.AIR);
                                return;
                            }
                            if (ammoEnable && takeAmmo) {
                                if (!CSDirector.this.csminion.containsItemStack(placer, ammoInfo, 1)) {
                                    CSDirector.this.playSoundEffects((Entity)placer, parent_node[0], ".Ammo.Sounds_Shoot_With_No_Ammo", false);
                                    block.setType(Material.AIR);
                                    return;
                                }
                                CSDirector.this.csminion.removeNamedItem(placer, ammoInfo, 1, parent_node[0]);
                            }
                            CSDirector.this.csminion.replaceBrackets(detonator, String.valueOf(detected_ammo - 1), parent_node[0]);
                            if (detonator.getItemMeta().hasLore()) {
                                final List<String> lore = (List<String>)detonator.getItemMeta().getLore();
                                final Pattern numberBrackets = Pattern.compile("\\[([^]]*)\\]");
                                final Matcher sqrbraDetector = numberBrackets.matcher(lore.get(lore.size() - 1));
                                if (sqrbraDetector.find() && lore.get(lore.size() - 1).contains(String.valueOf('\u1390'))) {
                                    final int lastNumber = Integer.valueOf(sqrbraDetector.group(1));
                                    if (lastNumber >= capacity) {
                                        block.setType(Material.AIR);
                                        return;
                                    }
                                    lore.add("§e§l[" + (lastNumber + 1) + "]§r§e " + world.toUpperCase() + '\u1390' + " " + x + ", " + y + ", " + z);
                                }
                                else {
                                    lore.add("§e§l[1]§r§e " + world.toUpperCase() + '\u1390' + " " + x + ", " + y + ", " + z);
                                }
                                final ItemMeta detmeta = detonator.getItemMeta();
                                detmeta.setLore((List)lore);
                                detonator.setItemMeta(detmeta);
                                placer.getInventory().setItemInHand(detonator);
                                CSDirector.this.playSoundEffects((Entity)placer, parent_node[0], ".Explosive_Devices.Sounds_Deploy", false);
                            }
                        }
                        catch (ClassCastException ex2) {}
                    }
                }
            }
        }, 0L);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void breakC4(final BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.SKULL) {
            final BlockState state = event.getBlock().getState();
            if (state instanceof Skull && !event.isCancelled()) {
                try {
                    final Skull skull = (Skull)state;
                    final String ownerOre = skull.getOwner();
                    if (ownerOre.contains("\u060c")) {
                        final String[] refinedOwner = ownerOre.split("\u060c");
                        final Block block = event.getBlock();
                        final Player breaker = event.getPlayer();
                        Player placer = null;
                        final List<Player> candidates = (List<Player>)Bukkit.matchPlayer(refinedOwner[1].replace(String.valueOf('\u0638'), ""));
                        if (candidates != null && candidates.size() > 0) {
                            placer = candidates.get(0);
                        }
                        final String world = block.getWorld().getName();
                        final String x = String.valueOf(block.getLocation().getBlockX());
                        final String y = String.valueOf(block.getLocation().getBlockY());
                        final String z = String.valueOf(block.getLocation().getBlockZ());
                        final String[] itemInfo = { "-", world, x, y, z };
                        for (final String ids : this.rdelist.keySet()) {
                            if (ids.equals(refinedOwner[0])) {
                                final String parent_node = this.rdelist.get(ids);
                                if (breaker != placer) {
                                    this.csminion.callAndResponse(breaker, placer, null, itemInfo, false);
                                }
                                else {
                                    final String msg = this.getString(String.valueOf(parent_node) + ".Explosive_Devices.Message_Disarm");
                                    if (msg != null) {
                                        breaker.sendMessage(msg);
                                    }
                                    block.removeMetadata("CS_transformers", (Plugin)this);
                                    block.setType(Material.AIR);
                                }
                                event.setCancelled(true);
                            }
                        }
                    }
                }
                catch (ClassCastException ex) {}
            }
        }
    }
    
    @EventHandler
    public void liquidContact(final BlockFromToEvent event) {
        if (event.getToBlock().getType() == Material.SKULL) {
            final BlockState state = event.getToBlock().getState();
            if (state instanceof Skull) {
                try {
                    final Skull skull = (Skull)state;
                    if (skull.getOwner().contains("\u060c")) {
                        event.setCancelled(true);
                    }
                }
                catch (ClassCastException ex) {}
            }
        }
    }
    
    public Vector determinePosition(final Player player, final boolean dualWield, final boolean leftClick) {
        int leftOrRight = 90;
        if (dualWield && leftClick) {
            leftOrRight = -90;
        }
        final double playerYaw = (player.getLocation().getYaw() + 90.0f + leftOrRight) * 3.141592653589793 / 180.0;
        final double x = Math.cos(playerYaw);
        final double y = Math.sin(playerYaw);
        final Vector vector = new Vector(x, 0.0, y);
        return vector;
    }
    
    public boolean itemIsSafe(final ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().getDisplayName() != null;
    }
    
    public float findNormal(float yaw) {
        while (yaw <= -180.0f) {
            yaw += 360.0f;
        }
        while (yaw > 180.0f) {
            yaw -= 360.0f;
        }
        return yaw;
    }
    
    public BlockFace getBlockDirection(float yaw) {
        yaw = this.findNormal(yaw);
        switch ((int)yaw) {
            case 0: {
                return BlockFace.NORTH;
            }
            case 90: {
                return BlockFace.EAST;
            }
            case 180: {
                return BlockFace.SOUTH;
            }
            case 270: {
                return BlockFace.WEST;
            }
            default: {
                if (yaw >= -45.0f && yaw < 45.0f) {
                    return BlockFace.NORTH;
                }
                if (yaw >= 45.0f && yaw < 135.0f) {
                    return BlockFace.EAST;
                }
                if (yaw >= -135.0f && yaw < -45.0f) {
                    return BlockFace.WEST;
                }
                return BlockFace.SOUTH;
            }
        }
    }
    
    @EventHandler
    public void trapCard(final InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.CHEST && event.getPlayer() instanceof Player) {
            final Player opener = (Player)event.getPlayer();
            final Inventory chest = event.getInventory();
            Block block = null;
            if (chest.getHolder() instanceof Chest) {
                final Chest chestHolder = (Chest)chest.getHolder();
                if (chestHolder != null) {
                    block = chestHolder.getBlock();
                }
            }
            else if (chest.getHolder() instanceof DoubleChest) {
                final DoubleChest chestHolder2 = ((DoubleChestInventory)chest).getHolder();
                if (chestHolder2 != null) {
                    block = opener.getWorld().getBlockAt(new Location(opener.getWorld(), chestHolder2.getX(), chestHolder2.getY(), chestHolder2.getZ()));
                }
            }
            if (block == null) {
                return;
            }
            if (block.hasMetadata("CS_btrap")) {
                event.setCancelled(true);
                return;
            }
            final ItemStack[] content = chest.getContents();
            ItemStack[] array;
            for (int length = (array = content).length, i = 0; i < length; ++i) {
                final ItemStack item = array[i];
                if (item != null) {
                    for (final String name : this.boobs.keySet()) {
                        if (this.itemIsSafe(item) && item.getItemMeta().getDisplayName().contains(name)) {
                            final String parent_node = this.boobs.get(name);
                            if (!this.csminion.getBoobean(1, parent_node)) {
                                return;
                            }
                            final boolean durab = this.hasDurab(parent_node);
                            final String item_b = this.getString(String.valueOf(parent_node) + ".Item_Information.Item_Type");
                            if (item_b == null) {
                                continue;
                            }
                            String[] item_ID = item_b.split("~");
                            if (item_ID.length < 2) {
                                item_ID = new String[] { item_ID[0], "0" };
                            }
                            if ((item.getDurability() != Short.valueOf(item_ID[1]) && !durab) || item.getTypeId() != Integer.valueOf(item_ID[0])) {
                                continue;
                            }
                            final Pattern ammo_brackets = Pattern.compile("\\«([^»]*)\\»");
                            final Matcher bracket_detector = ammo_brackets.matcher(item.getItemMeta().getDisplayName());
                            if (!bracket_detector.find()) {
                                continue;
                            }
                            final String detectedName = bracket_detector.group(1);
                            if (detectedName.equals("?")) {
                                break;
                            }
                            final Player planter = Bukkit.getServer().getPlayer(detectedName);
                            if (planter == event.getPlayer()) {
                                break;
                            }
                            if (!this.csminion.getBoobean(4, parent_node)) {
                                item.setAmount(item.getAmount() - 1);
                            }
                            this.slapAndReaction(opener, planter, block, parent_node, chest, content, detectedName, null);
                            return;
                        }
                    }
                }
            }
        }
    }
    
    public void activateTrapCard(final Player opener, final Player planter, final Block block, final String parent_node, final Inventory chest, final ItemStack[] content, final String planterName, final Item picked) {
        if (planter != null) {
            this.sendPlayerMessage((LivingEntity)planter, parent_node, ".Explosive_Devices.Message_Trigger_Placer", true, planterName, opener.getName(), "<flight>", "<damage>");
            this.playSoundEffects((Entity)planter, parent_node, ".Explosive_Devices.Sounds_Alert_Placer", false);
        }
        this.sendPlayerMessage((LivingEntity)opener, parent_node, ".Explosive_Devices.Message_Trigger_Victim", true, planterName, opener.getName(), "<flight>", "<damage>");
        if (picked == null) {
            this.projectileExplosion(null, parent_node, false, planter, false, true, null, block, true, 0);
            block.setMetadata("CS_btrap", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)false));
            if (!this.csminion.getBoobean(4, parent_node)) {
                chest.setContents(content);
            }
        }
        else {
            this.projectileExplosion(null, parent_node, false, planter, false, true, null, block.getRelative(BlockFace.DOWN), true, 0);
            if (!this.csminion.getBoobean(4, parent_node)) {
                picked.remove();
            }
        }
        this.playSoundEffects(block.getLocation().add(0.5, 0.5, 0.5), parent_node, ".Explosive_Devices.Sounds_Trigger");
    }
    
    public void slapAndReaction(final Player opener, final Player planter, final Block block, final String parent_node, final Inventory chest, final ItemStack[] content, final String planterName, final Item picked) {
        if (opener.hasMetadata("CS_trigDelay")) {
            return;
        }
        if (planter == null) {
            this.activateTrapCard(opener, planter, block, parent_node, chest, content, planterName, picked);
            return;
        }
        opener.setMetadata("CS_trigDelay", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)false));
        this.csminion.tempVars(opener, "CS_trigDelay", 200L);
        opener.setMetadata("CS_singed", (MetadataValue)new FixedMetadataValue((Plugin)this, (Object)false));
        this.csminion.illegalSlap(planter, opener);
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrackShot");
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                if (opener.hasMetadata("CS_singed")) {
                    final boolean confirmed = opener.getMetadata("CS_singed").get(0).asBoolean();
                    if (confirmed) {
                        opener.removeMetadata("CS_singed", plugin);
                        opener.removeMetadata("CS_trigDelay", plugin);
                        CSDirector.this.activateTrapCard(opener, planter, block, parent_node, chest, content, planterName, picked);
                    }
                }
            }
        }, 1L);
    }
    
    @EventHandler
    public void onTrapDispense(final BlockDispenseEvent event) {
        final Block block = event.getBlock();
        if (block.getType() != Material.DISPENSER) {
            return;
        }
        if (this.csminion.boobyAction(block, null, event.getItem())) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPressurePlate(final EntityInteractEvent event) {
        if ((event.getBlock().getType() == Material.WOOD_PLATE || event.getBlock().getType() == Material.STONE_PLATE) && event.getEntity() instanceof LivingEntity) {
            final List<Entity> l = (List<Entity>)event.getEntity().getNearbyEntities(4.0, 4.0, 4.0);
            for (final Entity e : l) {
                if (e instanceof ItemFrame) {
                    this.csminion.boobyAction(event.getBlock(), event.getEntity(), ((ItemFrame)e).getItem());
                }
            }
        }
    }
}
