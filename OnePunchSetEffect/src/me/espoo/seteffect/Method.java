package me.espoo.seteffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class Method {
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "§1");
        message = message.replaceAll("&2", "§2");
        message = message.replaceAll("&3", "§3");
        message = message.replaceAll("&4", "§4");
        message = message.replaceAll("&5", "§5");
        message = message.replaceAll("&6", "§6");
        message = message.replaceAll("&7", "§7");
        message = message.replaceAll("&8", "§8");
        message = message.replaceAll("&9", "§9");
        message = message.replaceAll("&0", "§0");
        message = message.replaceAll("&a", "§a");
        message = message.replaceAll("&b", "§b");
        message = message.replaceAll("&c", "§c");
        message = message.replaceAll("&d", "§d");
        message = message.replaceAll("&e", "§e");
        message = message.replaceAll("&f", "§f");
        message = message.replaceAll("&k", "§k");
        message = message.replaceAll("&l", "§l");
        message = message.replaceAll("&m", "§m");
        message = message.replaceAll("&n", "§n");
        message = message.replaceAll("&o", "§o");
        message = message.replaceAll("&r", "§r");
        return message;
	}
	
	public static boolean isHelmetItem(Player p, String in) {
		String i = ChatColor.stripColor(in);
		if (p.getInventory().getHelmet() != null) {
			String y = ChatColor.stripColor(p.getInventory().getHelmet().getItemMeta().getDisplayName());
			if (y == null) return false;
			if (y.equalsIgnoreCase(i))
				return true;
		} return false;
	}
	
	public static boolean isChestplateItem(Player p, String in) {
		String i = ChatColor.stripColor(in);
		if (p.getInventory().getChestplate() != null) {
			String y = ChatColor.stripColor(p.getInventory().getChestplate().getItemMeta().getDisplayName());
			if (y == null) return false;
			if (y.equalsIgnoreCase(i))
				return true;
		} return false;
	}
	
	public static boolean isLeggingsItem(Player p, String in) {
		String i = ChatColor.stripColor(in);
		if (p.getInventory().getLeggings() != null) {
			String y = ChatColor.stripColor(p.getInventory().getLeggings().getItemMeta().getDisplayName());
			if (y == null) return false;
			if (y.equalsIgnoreCase(i))
				return true;
		} return false;
	}
	
	public static boolean isBootsItem(Player p, String in) {
		String i = ChatColor.stripColor(in);
		if (p.getInventory().getBoots() != null) {
			String y = ChatColor.stripColor(p.getInventory().getBoots().getItemMeta().getDisplayName());
			if (y == null) return false;
			if (y.equalsIgnoreCase(i))
				return true;
		} return false;
	}
	
	public static boolean isSlotWeapon(Player p, PlayerInventory inv, int slot) {
		if (inv.getItem(slot) == null) return false;
		if (!inv.getItem(slot).hasItemMeta()) return false;
		if (!inv.getItem(slot).getItemMeta().hasDisplayName()) return false;
		
		String[] str = ChatColor.stripColor(inv.getItem(slot).getItemMeta().getDisplayName()).split(" ");
		String[] name = null;
		
		for (int i = 0; i < main.Waepon.size(); i++) {
			if (str.length > 1) {
				if (str[1].equalsIgnoreCase(main.Waepon.get(i))) {
					for (int y = 0; y < main.Waepon.size(); y++) {
						if (slot != 0) {
							ItemStack item = inv.getItem(0);
							if (item != null) {
								if (item.hasItemMeta()) {
									if (item.getTypeId() != 298 && item.getTypeId() != 299 && item.getTypeId() != 300 && item.getTypeId() != 301 &&
										item.getTypeId() != 302 && item.getTypeId() != 303 && item.getTypeId() != 304 && item.getTypeId() != 305 &&
										item.getTypeId() != 306 && item.getTypeId() != 307 && item.getTypeId() != 308 && item.getTypeId() != 309 &&
										item.getTypeId() != 310 && item.getTypeId() != 311 && item.getTypeId() != 312 && item.getTypeId() != 313 &&
										item.getTypeId() != 314 && item.getTypeId() != 315 && item.getTypeId() != 316 && item.getTypeId() != 317)
									{
										if (item.getItemMeta().hasDisplayName()) {
											if (item.getItemMeta().getDisplayName().contains(" ")) {
												name = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" ");
												if (name[1].equalsIgnoreCase(main.Waepon.get(y))) {
													return true;
												}
											}
										}
									}
								}
							}
						}
						
						if (slot != 1) {
							ItemStack item = inv.getItem(1);
							if (item != null) {
								if (item.hasItemMeta()) {
									if (item.getTypeId() != 298 && item.getTypeId() != 299 && item.getTypeId() != 300 && item.getTypeId() != 301 &&
										item.getTypeId() != 302 && item.getTypeId() != 303 && item.getTypeId() != 304 && item.getTypeId() != 305 &&
										item.getTypeId() != 306 && item.getTypeId() != 307 && item.getTypeId() != 308 && item.getTypeId() != 309 &&
										item.getTypeId() != 310 && item.getTypeId() != 311 && item.getTypeId() != 312 && item.getTypeId() != 313 &&
										item.getTypeId() != 314 && item.getTypeId() != 315 && item.getTypeId() != 316 && item.getTypeId() != 317)
									{
										if (item.getItemMeta().hasDisplayName()) {
											if (item.getItemMeta().getDisplayName().contains(" ")) {
												name = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" ");
												if (name[1].equalsIgnoreCase(main.Waepon.get(y))) {
													return true;
												}
											}
										}
									}
								}
							}
						}
						
						if (slot != 2) {
							ItemStack item = inv.getItem(2);
							if (item != null) {
								if (item.hasItemMeta()) {
									if (item.getTypeId() != 298 && item.getTypeId() != 299 && item.getTypeId() != 300 && item.getTypeId() != 301 &&
										item.getTypeId() != 302 && item.getTypeId() != 303 && item.getTypeId() != 304 && item.getTypeId() != 305 &&
										item.getTypeId() != 306 && item.getTypeId() != 307 && item.getTypeId() != 308 && item.getTypeId() != 309 &&
										item.getTypeId() != 310 && item.getTypeId() != 311 && item.getTypeId() != 312 && item.getTypeId() != 313 &&
										item.getTypeId() != 314 && item.getTypeId() != 315 && item.getTypeId() != 316 && item.getTypeId() != 317)
									{
										if (item.getItemMeta().hasDisplayName()) {
											if (item.getItemMeta().getDisplayName().contains(" ")) {
												name = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" ");
												if (name[1].equalsIgnoreCase(main.Waepon.get(y))) {
													return true;
												}
											}
										}
									}
								}
							}
						}
						
						if (slot != 3) {
							ItemStack item = inv.getItem(3);
							if (item != null) {
								if (item.hasItemMeta()) {
									if (item.getTypeId() != 298 && item.getTypeId() != 299 && item.getTypeId() != 300 && item.getTypeId() != 301 &&
										item.getTypeId() != 302 && item.getTypeId() != 303 && item.getTypeId() != 304 && item.getTypeId() != 305 &&
										item.getTypeId() != 306 && item.getTypeId() != 307 && item.getTypeId() != 308 && item.getTypeId() != 309 &&
										item.getTypeId() != 310 && item.getTypeId() != 311 && item.getTypeId() != 312 && item.getTypeId() != 313 &&
										item.getTypeId() != 314 && item.getTypeId() != 315 && item.getTypeId() != 316 && item.getTypeId() != 317)
									{
										if (item.getItemMeta().hasDisplayName()) {
											if (item.getItemMeta().getDisplayName().contains(" ")) {
												name = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" ");
												if (name[1].equalsIgnoreCase(main.Waepon.get(y))) {
													return true;
												}	
											}
										}
									}
								}
							}
						}
						
						if (slot != 4) {
							ItemStack item = inv.getItem(4);
							if (item != null) {
								if (item.hasItemMeta()) {
									if (item.getTypeId() != 298 && item.getTypeId() != 299 && item.getTypeId() != 300 && item.getTypeId() != 301 &&
										item.getTypeId() != 302 && item.getTypeId() != 303 && item.getTypeId() != 304 && item.getTypeId() != 305 &&
										item.getTypeId() != 306 && item.getTypeId() != 307 && item.getTypeId() != 308 && item.getTypeId() != 309 &&
										item.getTypeId() != 310 && item.getTypeId() != 311 && item.getTypeId() != 312 && item.getTypeId() != 313 &&
										item.getTypeId() != 314 && item.getTypeId() != 315 && item.getTypeId() != 316 && item.getTypeId() != 317)
									{
										if (item.getItemMeta().hasDisplayName()) {
											if (item.getItemMeta().getDisplayName().contains(" ")) {
												name = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" ");
												if (name[1].equalsIgnoreCase(main.Waepon.get(y))) {
													return true;
												}
											}
										}
									}
								}
							}
						}
						
						if (slot != 5) {
							ItemStack item = inv.getItem(5);
							if (item != null) {
								if (item.hasItemMeta()) {
									if (item.getTypeId() != 298 && item.getTypeId() != 299 && item.getTypeId() != 300 && item.getTypeId() != 301 &&
										item.getTypeId() != 302 && item.getTypeId() != 303 && item.getTypeId() != 304 && item.getTypeId() != 305 &&
										item.getTypeId() != 306 && item.getTypeId() != 307 && item.getTypeId() != 308 && item.getTypeId() != 309 &&
										item.getTypeId() != 310 && item.getTypeId() != 311 && item.getTypeId() != 312 && item.getTypeId() != 313 &&
										item.getTypeId() != 314 && item.getTypeId() != 315 && item.getTypeId() != 316 && item.getTypeId() != 317)
									{
										if (item.getItemMeta().hasDisplayName()) {
											if (item.getItemMeta().getDisplayName().contains(" ")) {
												name = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" ");
												if (name[1].equalsIgnoreCase(main.Waepon.get(y))) {
													return true;
												}	
											}
										}
									}
								}
							}
						}
						
						if (slot != 6) {
							ItemStack item = inv.getItem(6);
							if (item != null) {
								if (item.hasItemMeta()) {
									if (item.getTypeId() != 298 && item.getTypeId() != 299 && item.getTypeId() != 300 && item.getTypeId() != 301 &&
										item.getTypeId() != 302 && item.getTypeId() != 303 && item.getTypeId() != 304 && item.getTypeId() != 305 &&
										item.getTypeId() != 306 && item.getTypeId() != 307 && item.getTypeId() != 308 && item.getTypeId() != 309 &&
										item.getTypeId() != 310 && item.getTypeId() != 311 && item.getTypeId() != 312 && item.getTypeId() != 313 &&
										item.getTypeId() != 314 && item.getTypeId() != 315 && item.getTypeId() != 316 && item.getTypeId() != 317)
									{
										if (item.getItemMeta().hasDisplayName()) {
											if (item.getItemMeta().getDisplayName().contains(" ")) {
												name = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" ");
												if (name[1].equalsIgnoreCase(main.Waepon.get(y))) {
													return true;
												}
											}
										}
									}
								}
							}
						}
						
						if (slot != 7) {
							ItemStack item = inv.getItem(7);
							if (item != null) {
								if (item.hasItemMeta()) {
									if (item.getTypeId() != 298 && item.getTypeId() != 299 && item.getTypeId() != 300 && item.getTypeId() != 301 &&
										item.getTypeId() != 302 && item.getTypeId() != 303 && item.getTypeId() != 304 && item.getTypeId() != 305 &&
										item.getTypeId() != 306 && item.getTypeId() != 307 && item.getTypeId() != 308 && item.getTypeId() != 309 &&
										item.getTypeId() != 310 && item.getTypeId() != 311 && item.getTypeId() != 312 && item.getTypeId() != 313 &&
										item.getTypeId() != 314 && item.getTypeId() != 315 && item.getTypeId() != 316 && item.getTypeId() != 317)
									{
										if (item.getItemMeta().hasDisplayName()) {
											if (item.getItemMeta().getDisplayName().contains(" ")) {
												name = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" ");
												if (name[1].equalsIgnoreCase(main.Waepon.get(y))) {
													return true;
												}
											}
										}
									}
								}
							}
						}
						
						if (slot != 8) {
							ItemStack item = inv.getItem(8);
							if (item != null) {
								if (item.hasItemMeta()) {
									if (item.getTypeId() != 298 && item.getTypeId() != 299 && item.getTypeId() != 300 && item.getTypeId() != 301 &&
										item.getTypeId() != 302 && item.getTypeId() != 303 && item.getTypeId() != 304 && item.getTypeId() != 305 &&
										item.getTypeId() != 306 && item.getTypeId() != 307 && item.getTypeId() != 308 && item.getTypeId() != 309 &&
										item.getTypeId() != 310 && item.getTypeId() != 311 && item.getTypeId() != 312 && item.getTypeId() != 313 &&
										item.getTypeId() != 314 && item.getTypeId() != 315 && item.getTypeId() != 316 && item.getTypeId() != 317)
									{
										if (item.getItemMeta().hasDisplayName()) {
											if (item.getItemMeta().getDisplayName().contains(" ")) {
												name = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" ");
												if (name[1].equalsIgnoreCase(main.Waepon.get(y))) {
													return true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} return false;
	}
	
	public static boolean isHandItem2(Player p, ItemStack item, String in, int code, int data) {
		if (item == null) return false;
		if (item.getItemMeta() == null) return false;
		
		String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
		if (name == null) return false;
		String[] str = name.split(" ");
		if (str.length > 1) {
			if (str[1] == null) return false;
			if (!str[1].equalsIgnoreCase(in)) return false;
			if (item.getTypeId() != code) return false;
			if (item.getData().getData() != data) return false;
			return true;	
		} else {
			return false;
		}
	}
	
	public static boolean isHandItem(Player p, String name, int code, int data) {
		String i1 = ChatColor.stripColor(name);
		String i2 = ChatColor.stripColor(name + " +1");
		String i3 = ChatColor.stripColor(name + " +2");
		String i4 = ChatColor.stripColor(name + " +3");
		String i5 = ChatColor.stripColor(name + " +4");
		String i6 = ChatColor.stripColor(name + " +5");
		String i7 = ChatColor.stripColor(name + " +6");
		String i8 = ChatColor.stripColor(name + " +7");
		String i9 = ChatColor.stripColor(name + " +8");
		String i10 = ChatColor.stripColor(name + " +9");
		String i11 = ChatColor.stripColor(name + " +10");
		if (p.getItemInHand() == null) return false;
		if (p.getItemInHand().getItemMeta() == null) return false;
		if (p.getItemInHand().getTypeId() != code) return false;
		if (p.getItemInHand().getData().getData() != data) return false;
		
		String y = ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName());
		if (y == null) return false;
		if (y.equalsIgnoreCase(i1)) return true;
		else if (y.equalsIgnoreCase(i2)) return true;
		else if (y.equalsIgnoreCase(i3)) return true;
		else if (y.equalsIgnoreCase(i4)) return true;
		else if (y.equalsIgnoreCase(i5)) return true;
		else if (y.equalsIgnoreCase(i6)) return true;
		else if (y.equalsIgnoreCase(i7)) return true;
		else if (y.equalsIgnoreCase(i8)) return true;
		else if (y.equalsIgnoreCase(i9)) return true;
		else if (y.equalsIgnoreCase(i10)) return true;
		else if (y.equalsIgnoreCase(i11)) return true;
		else return false;
	}
	
	public static void flyPlayer(Player p, int count) {
		if (!(main.flySpem.contains(p))) {
			main.flySpem.add(p);
			main.flyTime.put(p.getName(), count);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "fly " + p.getName() + " on");
			p.setFlySpeed(1F);
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.PISTON_RETRACT, 2.0F, 2.0F);
			
			p.sendMessage("§e□ §b장비 능력 §f- 타츠마키 플라이 §3스킬을 시전합니다.");
			
			main.flyCool.put(p.getName(), new BukkitRunnable()
			{
				int CoolDown = main.flyTime.get(p.getName());
				@Override
				public void run()
				{
					if (CoolDown <= 0) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "fly " + p.getName() + " off");
						p.setFlySpeed(0.2F);
                		if (main.flySpem.contains(p)) main.flySpem.remove(p);
                		if (main.flyTime.containsKey(p.getName())) main.flyTime.remove(p.getName());
                		Integer id = main.flyCool.remove(p.getName());
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						return;
					}
					
					else if (CoolDown == (count / 2)) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "fly " + p.getName() + " off");
						p.setFlySpeed(0.2F);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.PISTON_RETRACT, 2.0F, 2.0F);
						CoolDown -= 1;
						main.flyTime.put(p.getName(), CoolDown);
					} else {
						CoolDown -= 1;
						main.flyTime.put(p.getName(), CoolDown);
					}
				}
			}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchSetEffect"), 0L, 20L).getTaskId());
		} else {
			p.sendMessage("§3스킬 재 사용시간 까지는 " + main.flyTime.get(p.getName()) + " 초 남았습니다.");
		}
	}
	
	public static void fly2Player(Player p, int count) {
		if (!(main.fly2Spem.contains(p))) {
			main.fly2Spem.add(p);
			main.fly2Time.put(p.getName(), count);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "fly " + p.getName() + " on");
			p.setFlySpeed(0.5F);
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.PISTON_RETRACT, 2.0F, 2.0F);
			
			p.sendMessage("§e□ §b장비 능력 §f- 후부키 플라이 §3스킬을 시전합니다.");
			
			main.fly2Cool.put(p.getName(), new BukkitRunnable()
			{
				int CoolDown = main.fly2Time.get(p.getName());
				@Override
				public void run()
				{
					if (CoolDown <= 0) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "fly " + p.getName() + " off");
						p.setFlySpeed(0.2F);
                		if (main.fly2Spem.contains(p)) main.fly2Spem.remove(p);
                		if (main.fly2Time.containsKey(p.getName())) main.fly2Time.remove(p.getName());
                		Integer id = main.fly2Cool.remove(p.getName());
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						return;
					}
					
					else if (CoolDown == (count / 2)) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "fly " + p.getName() + " off");
						p.setFlySpeed(0.2F);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.PISTON_RETRACT, 2.0F, 2.0F);
						CoolDown -= 1;
						main.fly2Time.put(p.getName(), CoolDown);
					} else {
						CoolDown -= 1;
						main.fly2Time.put(p.getName(), CoolDown);
					}
				}
			}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchSetEffect"), 0L, 20L).getTaskId());
		} else {
			p.sendMessage("§3스킬 재 사용시간 까지는 " + main.fly2Time.get(p.getName()) + " 초 남았습니다.");
		}
	}
	
	public static String searchOnlinePlayer(String target) {
		List<String> list1 = new ArrayList<String>();
		for (int i = 0; i < 16; i++) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getName().toLowerCase().indexOf(target.toLowerCase()) == i) {
					list1.add(player.getName());
				}
			}
			if (list1.size() == 0) {
				continue;
			} else {
				int len = 100;
				List<String> list3 = new ArrayList<String>();
				for (int j = 0; j < list1.size(); j++) {
					int l = list1.get(j).length();
					if (l < len) {
						len = l;
					}
				}
				for (int j = 0; j < list1.size(); j++) {
					if (list1.get(j).length() == len) {
						list3.add(list1.get(j));
					}
				}
				String[] list2 = new String[list3.size()];
				for (int j = 0; j < list3.size(); j++) {
					list2[j] = list3.get(j);
				}
				Arrays.sort(list2);
				return list2[0];
			}
		}
		return null;
	}
	
	public static Player getOnorOffLine(String s) {
		for (Player all : Bukkit.getOnlinePlayers())
		{
			if (all.getName().equals(s))
			{
				return all;
			}
		}
		return null;
	}
	
	public static void switchItems(int s, Inventory inv)
	{
		int num = new Random().nextInt(26) + 9;
		ItemStack i1 = inv.getItem(s);
		ItemStack i2 = inv.getItem(num);
	    inv.setItem(num, i1);
	    inv.setItem(s, i2);
	}
}
