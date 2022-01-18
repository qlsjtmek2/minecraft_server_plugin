package me.espoo.loon;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	GUIMessage G;
	PlayerYml P;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File folder = new File("plugins/OnePunchLoon");
		File folder2 = new File("plugins/OnePunchLoon/Player");
		File f = new File("plugins/OnePunchLoon/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) P.CreatePlayerInfo(f, folder, folder2, config);
	}

	@SuppressWarnings("static-access")
	@EventHandler
    public void onCancelClick(InventoryClickEvent e) {
     	if (!e.getInventory().getName().equalsIgnoreCase("룬 장착")) return;
     	if (e.getCurrentItem() == null) return;
     	
     	ItemStack i = e.getCurrentItem();
		if (e.getCurrentItem().getItemMeta() != null) {
			ItemMeta m = i.getItemMeta();
			if (m.getDisplayName() != null) {
				String[] str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).split(" ");
				if (str.length != 1) {
					for (int num = 0; num < G.Loon.size(); num++) {
						if (str[1].equalsIgnoreCase(G.Loon.get(num))) {
							return;
						}
					} e.setCancelled(true);
				} else e.setCancelled(true);
			} else e.setCancelled(true);
		} 
		
		else if (i.getType() == Material.AIR) return;
		else e.setCancelled(true);
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (!e.getInventory().getName().equalsIgnoreCase("룬 장착")) return;
		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getItem(13) != null) {
			ItemStack i = e.getInventory().getItem(13);
			if (P.getInfoInt(p, "룬.아이템 코드") == i.getTypeId() 
			 && P.getInfoString(p, "룬.아이템 이름").equalsIgnoreCase(i.getItemMeta().getDisplayName()) 
			 && P.getInfoList(p, "룬.아이템 설명").equals(i.getItemMeta().getLore())) return;
			P.setInfoInt(p, "룬.아이템 코드", i.getTypeId());
			P.setInfoString(p, "룬.아이템 이름", i.getItemMeta().getDisplayName());
			P.setInfoList(p, "룬.아이템 설명", i.getItemMeta().getLore());
			p.sendMessage("§a성공적으로 룬을 장착하였습니다!");
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
		} else {
			if (P.getInfoInt(p, "룬.아이템 코드") == 0 && P.getInfoString(p, "룬.아이템 이름") == null && P.getInfoList(p, "룬.아이템 설명").equals(Arrays.asList())) return;
			p.sendMessage("§c성공적으로 룬 장착을 해제하셨습니다.");
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
			P.setInfoInt(p, "룬.아이템 코드", 0);
			P.setInfoString(p, "룬.아이템 이름", "NONE");
			P.setInfoList(p, "룬.아이템 설명", Arrays.asList());
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
			Player p = (Player) e.getDamager();
			LivingEntity en = (LivingEntity) e.getEntity();
			
			if (PlayerYml.getInfoInt(p, "룬.아이템 코드") != 0 && PlayerYml.getInfoString(p, "룬.아이템 이름") != null && !PlayerYml.getInfoList(p, "룬.아이템 설명").equals(Arrays.asList())) {
				String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "룬.아이템 이름")).split(" ");
				if (str[1].equalsIgnoreCase("부패")) {
					if (str.length == 5) {
						int i = Integer.parseInt(str[4].replace("+", ""));
						
						switch (i) {
						case 1:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 0, true));
							break;
							
						case 2:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 0, true));
							break;
							
						case 3:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 1, true));
							break;
							
						case 4:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1, true));
							break;
							
						case 5:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1, true));
							break;
							
						case 6:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 140, 2, true));
							break;
							
						case 7:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 2, true));
							break;
							
						case 8:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 3, true));
							break;
							
						case 9:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 400, 4, true));
							break;
							
						case 10:
							en.removePotionEffect(PotionEffectType.WITHER);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 600, 6, true));
							break;
						}
					} else {
						en.removePotionEffect(PotionEffectType.WITHER);
						en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 0, true));
					}
				}
				
				else if (str[1].equalsIgnoreCase("독")) {
					if (str.length == 5) {
						int i = Integer.parseInt(str[4].replace("+", ""));
						
						switch (i) {
						case 1:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20, 0, true));
							break;
							
						case 2:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 0, true));
							break;
							
						case 3:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 1, true));
							break;
							
						case 4:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 1, true));
							break;
							
						case 5:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1, true));
							break;
							
						case 6:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 140, 2, true));
							break;
							
						case 7:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 2, true));
							break;
							
						case 8:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 3, true));
							break;
							
						case 9:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 400, 4, true));
							break;
							
						case 10:
							en.removePotionEffect(PotionEffectType.POISON);
							en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 600, 6, true));
							break;
						}
					} else {
						en.removePotionEffect(PotionEffectType.POISON);
						en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20, 0, true));
					}
				}
				
				else if (str[1].equalsIgnoreCase("불")) {
					if (str.length == 5) {
						int i = Integer.parseInt(str[4].replace("+", ""));
						
						switch (i) {
						case 1:
							en.setFireTicks(20);
							break;
							
						case 2:
							en.setFireTicks(40);
							break;
							
						case 3:
							en.setFireTicks(60);
							break;
							
						case 4:
							en.setFireTicks(80);
							break;
							
						case 5:
							en.setFireTicks(100);
							Integer id1 = main.Timer.remove(p.getName());
							if (id1 != null) Bukkit.getServer().getScheduler().cancelTask(id1);
							
							M.Timer.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (en.getFireTicks() <= 0) {
										Integer id = main.Timer.remove(p.getName());
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										en.damage(2);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchLoon"), 20L, 20L).getTaskId());
							break;
							
						case 6:
							en.setFireTicks(140);
							Integer id = main.Timer.remove(p.getName());
							if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
							
							M.Timer.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (en.getFireTicks() <= 0) {
										Integer id = main.Timer.remove(p.getName());
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										en.damage(4);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchLoon"), 20L, 20L).getTaskId());
							break;
							
						case 7:
							en.setFireTicks(200);
							Integer id2 = main.Timer.remove(p.getName());
							if (id2 != null) Bukkit.getServer().getScheduler().cancelTask(id2);
							
							M.Timer.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (en.getFireTicks() <= 0) {
										Integer id = main.Timer.remove(p.getName());
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										en.damage(6);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchLoon"), 20L, 20L).getTaskId());
							break;
							
						case 8:
							en.setFireTicks(300);
							Integer id3 = main.Timer.remove(p.getName());
							if (id3 != null) Bukkit.getServer().getScheduler().cancelTask(id3);
							
							M.Timer.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (en.getFireTicks() <= 0) {
										Integer id = main.Timer.remove(p.getName());
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										en.damage(10);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchLoon"), 20L, 20L).getTaskId());
							break;
							
						case 9:
							en.setFireTicks(600);
							Integer id4 = main.Timer.remove(p.getName());
							if (id4 != null) Bukkit.getServer().getScheduler().cancelTask(id4);
							
							M.Timer.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (en.getFireTicks() <= 0) {
										Integer id = main.Timer.remove(p.getName());
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										en.damage(20);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchLoon"), 20L, 20L).getTaskId());
							break;
							
						case 10:
							en.setFireTicks(900);
							Integer id5 = main.Timer.remove(p.getName());
							if (id5 != null) Bukkit.getServer().getScheduler().cancelTask(id5);
							
							M.Timer.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (en.getFireTicks() <= 0) {
										Integer id = main.Timer.remove(p.getName());
										if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									} else {
										en.damage(30);
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("OnePunchLoon"), 20L, 20L).getTaskId());
							break;
						}
					} else {
						en.setFireTicks(20);
					}
				}
				
				else if (str[1].equalsIgnoreCase("얼음")) {
					if (str.length == 5) {
						int i = Integer.parseInt(str[4].replace("+", ""));
						
						switch (i) {
						case 1:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 0, true));
							break;
							
						case 2:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0, true));
							break;
							
						case 3:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1, true));
							break;
							
						case 4:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1, true));
							break;
							
						case 5:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1, true));
							break;
							
						case 6:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 2, true));
							break;
							
						case 7:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 2, true));
							break;
							
						case 8:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 3, true));
							break;
							
						case 9:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 4, true));
							break;
							
						case 10:
							en.removePotionEffect(PotionEffectType.SLOW);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 6, true));
							break;
						}
					} else {
						en.removePotionEffect(PotionEffectType.SLOW);
						en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 0, true));
					}
				}
				
				else if (str[1].equalsIgnoreCase("어둠")) {
					if (str.length == 5) {
						int i = Integer.parseInt(str[4].replace("+", ""));
						
						switch (i) {
						case 1:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20, 0, true));
							en.removePotionEffect(PotionEffectType.WEAKNESS);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20, 0, true));
							break;
							
						case 2:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 0, true));
							en.removePotionEffect(PotionEffectType.WEAKNESS);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40, 0, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 0, true));
							break;
							
						case 3:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 1, true));
							en.removePotionEffect(PotionEffectType.WEAKNESS);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60, 1, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 60, 1, true));
							break;
							
						case 4:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 80, 1, true));
							en.removePotionEffect(PotionEffectType.WEAKNESS);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 1, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 80, 1, true));
							break;
							
						case 5:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 2, true));
							en.removePotionEffect(PotionEffectType.WEAKNESS);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 2, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 2, true));
							break;
							
						case 6:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 140, 2, true));
							en.removePotionEffect(PotionEffectType.WEAKNESS);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 140, 2, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 140, 2, true));
							break;
							
						case 7:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 3, true));
							en.removePotionEffect(PotionEffectType.WEAKNESS);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 3, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 3, true));
							break;
							
						case 8:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 300, 4, true));
							en.removePotionEffect(PotionEffectType.WEAKNESS);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 4, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 4, true));
							break;
							
						case 9:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 400, 5, true));
							en.removePotionEffect(PotionEffectType.WEAKNESS);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 5, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 5, true));
							break;
							
						case 10:
							en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 600, 7, true));
							en.removePotionEffect(PotionEffectType.WEAKNESS);
							en.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 7, true));
							en.removePotionEffect(PotionEffectType.CONFUSION);
							en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 7, true));
							break;
						}
					} else {
						en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
						en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20, 0, true));
						en.removePotionEffect(PotionEffectType.WEAKNESS);
						en.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20, 0, true));
						en.removePotionEffect(PotionEffectType.CONFUSION);
						en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20, 0, true));
					}
				}
			}
		}
	}
}
