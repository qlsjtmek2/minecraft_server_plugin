package me.espoo.socket;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	
	public mainCommand(main instance)
	{
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("家南")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					GUI.openGUI(p);
					return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				GUI.openGUI(p);
				return false;
			} else {
				return false;
			}
		} 
		
		try {
			if (commandLabel.equalsIgnoreCase("家南坷锹")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (!p.isOp()) return false;
					if (args.length != 1) return false;
					if (p.getItemInHand() == null) return false;
					if (!p.getItemInHand().hasItemMeta()) return false;
					if (!p.getItemInHand().getItemMeta().hasDisplayName()) return false;
					
					if (args[0].equalsIgnoreCase("1")) {
						ItemStack item = new MaterialData(263, (byte) 0).toItemStack(1);
						ItemMeta item_Meta = item.getItemMeta();
						item_Meta.setDisplayName("∽6皑沥等 家南籍");
						item_Meta.setLore(API.getOneLore());
						item.setItemMeta(item_Meta);
						
			            int t = 0;
			            ItemStack[] contents;
			            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			                ItemStack it = contents[j];
			                if (it == null) {
			                    ++t;
			                }
			            }
			            
			            if (t < 1) {
			            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 弥窍鞭家南籍 give " + p.getName() + " 1");
			            	p.sendMessage("∽c牢亥配府俊 傍埃阑 茄沫捞扼档 巢扁绊 荤侩秦 林矫扁 官而聪促.");
			            	if (me.espoo.option.PlayerYml.getInfoBoolean(p, "瓤苞澜")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
			            	return false;
			            } p.getInventory().addItem(item); API.castLvup(p);
					}

					else if (args[0].equalsIgnoreCase("2")) {
						ItemStack item = new MaterialData(318, (byte) 0).toItemStack(1);
						ItemMeta item_Meta = item.getItemMeta();
						item_Meta.setDisplayName("∽6皑沥等 家南籍");
						item_Meta.setLore(API.getTwoLore());
						item.setItemMeta(item_Meta);
						
			            int t = 0;
			            ItemStack[] contents;
			            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			                ItemStack it = contents[j];
			                if (it == null) {
			                    ++t;
			                }
			            }
			            
			            if (t < 1) {
			            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 窍鞭家南籍 give " + p.getName() + " 1");
			            	p.sendMessage("∽c牢亥配府俊 傍埃阑 茄沫捞扼档 巢扁绊 荤侩秦 林矫扁 官而聪促.");
			            	if (me.espoo.option.PlayerYml.getInfoBoolean(p, "瓤苞澜")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
			            	return false;
			            } p.getInventory().addItem(item); API.castLvup(p);
					}

					else if (args[0].equalsIgnoreCase("3")) {
						ItemStack item = new MaterialData(388, (byte) 0).toItemStack(1);
						ItemMeta item_Meta = item.getItemMeta();
						item_Meta.setDisplayName("∽6皑沥等 家南籍");
						item_Meta.setLore(API.getThreeLore());
						item.setItemMeta(item_Meta);
						
			            int t = 0;
			            ItemStack[] contents;
			            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			                ItemStack it = contents[j];
			                if (it == null) {
			                    ++t;
			                }
			            }
			            
			            if (t < 1) {
			            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 吝鞭家南籍 give " + p.getName() + " 1");
			            	p.sendMessage("∽c牢亥配府俊 傍埃阑 茄沫捞扼档 巢扁绊 荤侩秦 林矫扁 官而聪促.");
			            	if (me.espoo.option.PlayerYml.getInfoBoolean(p, "瓤苞澜")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
			            	return false;
			            } p.getInventory().addItem(item); API.castLvup(p);
					}

					else if (args[0].equalsIgnoreCase("4")) {
						ItemStack item = new MaterialData(264, (byte) 0).toItemStack(1);
						ItemMeta item_Meta = item.getItemMeta();
						item_Meta.setDisplayName("∽6皑沥等 家南籍");
						item_Meta.setLore(API.getFourLore());
						item.setItemMeta(item_Meta);
						
			            int t = 0;
			            ItemStack[] contents;
			            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			                ItemStack it = contents[j];
			                if (it == null) {
			                    ++t;
			                }
			            }
			            
			            if (t < 1) {
			            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 惑鞭家南籍 give " + p.getName() + " 1");
			            	p.sendMessage("∽c牢亥配府俊 傍埃阑 茄沫捞扼档 巢扁绊 荤侩秦 林矫扁 官而聪促.");
			            	if (me.espoo.option.PlayerYml.getInfoBoolean(p, "瓤苞澜")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
			            	return false;
			            } p.getInventory().addItem(item); API.castLvup(p);
					}

					else if (args[0].equalsIgnoreCase("5")) {
						ItemStack item = new MaterialData(399, (byte) 0).toItemStack(1);
						ItemMeta item_Meta = item.getItemMeta();
						item_Meta.setDisplayName("∽6皑沥等 家南籍");
						item_Meta.setLore(API.getFiveLore());
						item.setItemMeta(item_Meta);
						
			            int t = 0;
			            ItemStack[] contents;
			            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			                ItemStack it = contents[j];
			                if (it == null) {
			                    ++t;
			                }
			            }
			            
			            if (t < 1) {
			            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 弥惑鞭家南籍 give " + p.getName() + " 1");
			            	p.sendMessage("∽c牢亥配府俊 傍埃阑 茄沫捞扼档 巢扁绊 荤侩秦 林矫扁 官而聪促.");
			            	if (me.espoo.option.PlayerYml.getInfoBoolean(p, "瓤苞澜")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
			            	return false;
			            } p.getInventory().addItem(item); API.castLvup(p);
					}
					
					return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				GUI.openGUI(p);
				return false;
			} else {
				return false;
			}
		} return false;
	}
}
